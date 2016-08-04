Ext.define('y.order.BW2DZGrid',{
	extend:'Ext.grid.Panel',
	requires: [

	],
	columnLines :true,
	stripeRows:true,
	selModel: {
          selType: 'checkboxmodel'
          ,checkOnly:true
    },
    viewConfig:{
    	enableTextSelection:true
    },
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
        {dataIndex:'SPTYNM',header:'小类'
        },
        {dataIndex:'SPSENM',header:'系列'
        },
        {dataIndex:'SAMPNM',header:'设计样衣编号'
        },
        {dataIndex:'SUITNM',header:'套件'
        },
        {dataIndex:'YXGSNM',header:'营销公司'
        },
        {dataIndex:'QYNM',header:'区域'
        },
        {dataIndex:'DZMTQT',header:'订制数量'
        },
        {dataIndex:'BWMTQT',header:'备忘数量'
        },
        {dataIndex:'NUM',header:'转移数量'
        	,editor: {
	                xtype: 'numberfield',
	                allowDecimals:false,
	                selectOnFocus:true 
	            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	 return value;
            	}
        }
      ];
      
      this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  
//	  this.cellEditing.on("beforeedit",function(editor, context){
//	   	var record=context.record;
//	   	var value=context.value;
//	   	
//	  });
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	    var originalValue=context.originalValue;
	    if(value>record.get("BWMTQT")){
	    	Ext.Msg.alert("消息","最大只能设置"+record.get("BWMTQT"));
	    	record.set(field,record.get("BWMTQT"));
	    	//return;
	    }
	    if(value<0 && Math.abs(value)>record.get("DZMTQT")){
	    	Ext.Msg.alert("消息","最大只能设置-"+record.get("DZMTQT"));
	    	record.set(field,-record.get("DZMTQT"));
	    	//return;
	    }
		if(value){
			grid.getSelectionModel().select(record,true);
		} else {
			grid.getSelectionModel().deselect(record);
		}
		
	  	
	  });
      

	 
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields:['SPTYNM','ORMTNO','SPSENM','SAMPNO','SAMPNM','SUITNO','SUITNM','YXGSNM','QYNO','QYNM','DZMTQT','BWMTQT','NUM'],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/bw2dz/queryPager.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			},
			listeners:{
				load:function(store,records){
					
				}
			}
	  });

	  me.dockedItems=[];
      me.dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
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
//						var ordorg=combo.nextSibling("#ordorg");
//		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
//		        			ormtno:record.get("ormtno")
//		        		});
//		        		//ordorg.getStore().reload();
//		        		
//		        		var channo=combo.nextSibling("#channo");
//		        		channo.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
//		        			ormtno:record.get("ormtno")
//		        		});
//		        		channo.getStore().reload();
					}
				}
			},{
		  		fieldLabel: '营销公司',
		  		labelWidth:75,
		  		width:175,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'yxgsno',
		  		showBlank:true,
		  		allowBlank:true,
				xtype:'orgcombo',
				listeners:{
					select:function( combo, record, eOpts ) {
						var regioncombo=combo.nextSibling("#qyno");
		        		regioncombo.reload(record.get("orgno"));
					}
				}
			},{
		  		fieldLabel: '区域',
		  		labelWidth:45,
		  		width:170,
		  		allowBlank: true,
		  		showBlank:true,
		  		selFirst:false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'qyno',
				xtype:'orgcombo',
				autoLoad:false,
				listeners:{
					select:function( combo, record, eOpts ) {
		        		
//						var ordorg=combo.nextSibling("#ordorg");
//		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
//		        			qyno:record.get("orgno")
//		        		});
//		        		ordorg.getStore().reload();
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
		        		//var toolbar=combo.up("toolbar");
		        		//var suitno=toolbar.down("#suitno");
		        		var toolbar=combo.up("grid").getDockedItems('toolbar[dock="top"]')[1];
		        		var suitno=toolbar.down("#suitno");
		        		suitno.changeBradno(record.get("itno"));
		        		suitno.getStore().reload();
		        	}	
		        }
		    },{
		        fieldLabel: '大类',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"大类不允许为空",
	             selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'0',
		        listeners:{
		        	select:function( combo, record, eOpts ) {
		        		//var sptyno=combo.nextSibling("#sptyno");
		        		var toolbar=combo.up("grid").getDockedItems('toolbar[dock="top"]')[1];
		        		var sptyno=toolbar.down("#sptyno");
		        		sptyno.reload(record.get("itno"));
		        		
		        		//var spseno=combo.nextSibling("#spseno");
		        		var spseno=toolbar.down("#spseno");
		        		spseno.reload(record.get("itno"));
		        	}	
		        }
		    }]
	  });
	  
	   me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[
//		  		{
//		  		itemId:'ortyno',
//				xtype:'ordtycombo',
//				labelWidth:65,
//				allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            value:'DZ',
//				//selFirst:true,
//				width:150,
//				listeners:{
//					select:function( combo, record, eOpts ) {
////						var ordorg=combo.nextSibling("#ordorg");
////		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
////		        			ortyno:record.get("ortyno")
////		        		});
////		        		ordorg.getStore().reload();
//					}
//				}
//			},
			{
		        fieldLabel: '小类',
		        itemId: 'sptyno',
		        labelWidth:40,
		        width:140,
	            autoLoad:false,
		        xtype:'pubcodecombo',
		        tyno:'2'
		    },
			{
		        fieldLabel: '系列',
		        itemId: 'spseno',
		        labelWidth:40,
		        width:160,
	            autoLoad:false,
		        xtype:'pubcodecombo',
		        tyno:'5'
		    },{
		        fieldLabel: '套件',
		        itemId: 'suitno',
		        //selFirst:true,
	            xtype:'pubcodecombo',
		        tyno:'3',
		        labelWidth:45,
				width:150
		    },{
		        fieldLabel: '订货样衣编号',
		        labelWidth:85,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
			
				},
				iconCls: 'icon-refresh'
			}]
	   });
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '转移',
				itemId:'createNew',
				handler: function(btn){
					me.transform();
				},
				iconCls: 'icon-plus'
			},{
				xtype:'label',
				html:'<span style="color:red;">输入为正数20，表示备忘数量减掉20，订制数量加上20</span>.输入-20，表示备忘数量加20，订制数量减掉20.'
			}]
		});

       
      me.callParent();
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var params={
			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
			"params['yxgsno']":toolbars[0].down("#yxgsno").getValue(),
			"params['qyno']":toolbars[0].down("#qyno").getValue(),
			//"params['channo']":toolbars[0].down("#channo").getValue(),	
			//"params['ordorg']":toolbars[0].down("#ordorg").getValue(),
			
			"params['bradno']":toolbars[0].down("#bradno").getValue(),
			"params['spclno']":toolbars[0].down("#spclno").getValue(),
			"params['sptyno']":toolbars[1].down("#sptyno").getValue(),
			"params['spseno']":toolbars[1].down("#spseno").getValue(),
			"params['suitno']":toolbars[1].down("#suitno").getValue(),
			"params['sampnm']":toolbars[1].down("#sampnm").getValue()
		};
		return params;
	},
	transform:function(){
		var me=this;
		var extraParams=me.getStore().getProxy().extraParams;
		//订制转备忘
		Ext.Msg.confirm("消息","是否对选中的数据进行转移!",function(btn){
			if(btn=='yes'){
				Ext.getBody().mask("正在处理,请稍候.....");
				var modles=me.getSelection( ) ;
				if(!modles || modles.length==0){
					Ext.Msg.alert("消息","请选择一行或多行!");
					return;
				}	
				
				var dataes=[];
				for(var i=0;i<modles.length;i++){
					if(modles[i].get("NUM")){
						
						dataes.push({
							ormtno:modles[i].get("ORMTNO"),
							ordorg:modles[i].get("QYNO"),
							sampno:modles[i].get("SAMPNO"),
							suitno:modles[i].get("SUITNO"),
							bradno:extraParams["params['bradno']"],
							spclno:extraParams["params['spclno']"],
							num:modles[i].get("NUM")
						});
					}	
				}
				
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/bw2dz/transform.do',
						    jsonData:dataes,
						    method:'POST',
						    success:function(response){
						    	var obj=Ext.decode(response.responseText);
						    	Ext.getBody().unmask();
								if(obj.success==false){
									Ext.Msg.alert("消息",obj.msg);
									return;
								}
						    	me.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    }
						   });
			}
		});
		
	}
    
});
