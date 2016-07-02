Ext.define('y.plan.PlanHdGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.plan.PlanHd'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
      	{dataIndex:'orgnm',header:'渠道代码'
        },
		{dataIndex:'bradno_name',header:'品牌'
        },
		{dataIndex:'spclno_name',header:'大类'
        },
		
		{dataIndex:'plmtqt',header:'指标数量',xtype: 'numbercolumn', format:'0.00',align : 'right',
					renderer:function(value, metaData, record, rowIndex, colIndex, store){
						if(record.get("plstat")==0 && record.get("isTotal")==false){
							metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
						} else if(record.get("isTotal")==true){
							 metaData.tdStyle = 'background-color:#CD9B9B;' ;
						}
						
		            	return value;
		            },editor: {
		                xtype: 'numberfield',
		                allowDecimals:false,
		                selectOnFocus:true 
		            }
		},
		{dataIndex:'plmtam',header:'指标金额',xtype: 'numbercolumn', format:'0.00',align : 'right',
					renderer:function(value, metaData, record, rowIndex, colIndex, store){
						if(record.get("plstat")==0 && record.get("isTotal")==false){
							metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
						} else if(record.get("isTotal")==true){
							 metaData.tdStyle = 'background-color:#CD9B9B;' ;
						}
		            	return value;
		            },editor: {
		                xtype: 'numberfield',
		                allowDecimals:false,
		                selectOnFocus:true 
		            }
		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.plan.PlanHd',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/planHd/queryHdGrid.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json'
//					rootProperty:'root',
//					successProperty:'success',
//					totalProperty:'total'		
				}
			}
	  });
	  
	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1 
      });     
	  this.plugins = [this.cellEditing];
	  
	   this.cellEditing.on("beforeedit",function(editor, context){
	   		var record=context.record;
	   		if(record.get("plstat")!=0 || record.get("isTotal")==true){
	   			return false;
	   		}
	   });
	  this.cellEditing.on("edit",function(editor, context){
	    var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	
	  	Ext.Ajax.request({
						url:Ext.ContextPath+'/planHd/update.do',
						jsonData:record.getData(),
						success:function(response){
							var obj=Ext.decode(response.responseText);
							if(obj.success==false){
								Ext.Msg.alert("消息",obj.msg);
								return;
							}
							record.commit();
							//me.getStore().reload();
						}
						
					});
	  	
	  });

	  me.dockedItems=[];
//      me.dockedItems.push({
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  });
	  
	 me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				listeners:{
					select:function( combo, record, eOpts ) {	

					}
				}
			},{
		  		fieldLabel: '营销公司',
		  		labelWidth:75,
		  		width:175,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'yxgsno',
				xtype:'orgcombo',
				selFirst:false,
				listeners:{
					select:function( combo, record, eOpts ) {
//						var regioncombo=combo.nextSibling("#qyno");
//		        		regioncombo.reload(record.get("orgno"));
					}
				}
			},{
		        fieldLabel: '品牌',
		        itemId: 'bradno',
		        labelWidth:40,
		        width:160,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            //value:'Y',
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
		        	select:function( combo, record, eOpts ) {
//		        		var toolbar=combo.up("toolbar");
//		        		var suitno=toolbar.down("#suitno");
//		        		suitno.changeBradno(record.get("itno"));
//		        		suitno.getStore().reload();
		        	}	
		        }
		    },{
		        fieldLabel: '大类',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
//	            allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"大类不允许为空",
	            // selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'0',
		        listeners:{
		        	select:function( combo, record, eOpts ) {		
//		        		var spseno=combo.nextSibling("#spseno");
//		        		spseno.reload(record.get("itno"));
		        	}	
		        }
		    },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			},{
				text: '刷新合计',
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});
	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '通过',
				handler: function(btn){
					me.onPass();
				},
				iconCls: 'icon-ok'
			},{
			   text: '导出',
				handler: function(btn){
					me.createNew();
				},
				iconCls: 'icon-download-alt'
			}]
		});

       
      me.callParent();
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var params={
			"ormtno":toolbars[0].down("#ordmtcombo").getValue(),
			"yxgsno":toolbars[0].down("#yxgsno").getValue(),
			
			//"ortyno":toolbars[1].down("#ortyno").getValue(),
			"bradno":toolbars[0].down("#bradno").getValue(),
			"spclno":toolbars[0].down("#spclno").getValue()
		};
		return params;
	},
	onPass:function(){
		var me=this;
	 	Ext.Msg.confirm("消息","确定通过，提交审批吗?",function(btn){
	 		//console.log(btn);
	 		if(btn=='yes'){
				var toolbars=me.getDockedItems('toolbar[dock="top"]');
	 			Ext.Ajax.request({
	 				url:Ext.ContextPath+'/planHd/onPass.do',
	 				params:me.getParams(),
					success:function(response){
						var obj=Ext.decode(response.responseText);
						if(obj.success==false){
							Ext.Msg.alert("消息",obj.msg);
							return;
						}
						me.getStore().reload();
					}
	 			});
	 		}
	 	});
	}
	
});
