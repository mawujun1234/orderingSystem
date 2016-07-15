Ext.define('y.pubcode.PubPlanrtGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.pubcode.PubPlanrt'
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
		{dataIndex:'spclnm',header:'大类'
        },
		{dataIndex:'sptynm',header:'小类'
        },
		{dataIndex:'planrt',header:'指标上报范围',
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
				if(value=="1"){
					return "按小类"
				} else if(value=="2"){
					return "按系列";
				} else {
					return "";
				}
				
            	return value;
			},
			editor:Ext.create('Ext.form.ComboBox', {
			    store: Ext.create('Ext.data.Store', {
				    fields: ['abbr', 'name'],
				    data : [
				        {"id":"1", "name":"按小类"},
				        {"id":"2", "name":"按系列"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'id'
			})
		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.pubcode.PubPlanrt',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/pubPlanrt/queryGrid.do',
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
	  
	  this.cellEditing.on("edit",function(editor, context){
	    var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	
	  	Ext.Ajax.request({
						url:Ext.ContextPath+'/pubPlanrt/update.do',
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
		        fieldLabel: '品牌',
		        itemId: 'bradno',
		        labelWidth:40,
		        width:160,
	            allowBlank: false,
	             showBlank:false,
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
	            allowBlank: false,
	             showBlank:false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"大类不允许为空",
	             selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'0',
		        listeners:{
		        	select:function( combo, record, eOpts ) {
		        		var sptyno=combo.nextSibling("#sptyno");
		        		if(!record.get("itno")){
		        			sptyno.getStore().removeAll();
		        			return;
		        		}
		        		
		        		sptyno.reload(record.get("itno"));
		        		
		        	}	
		        }
		    },{
		        fieldLabel: '小类',
		        itemId: 'sptyno',
		        labelWidth:40,
		        width:140,
	            autoLoad:false,
		        xtype:'pubcodecombo',
		        tyno:'2'
		    },{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");

					var toolbars=grid.getDockedItems('toolbar[dock="top"]');
					var params={
						"bradno":toolbars[0].down("#bradno").getValue(),
						"spclno":toolbars[0].down("#spclno").getValue(),
						"sptyno":toolbars[0].down("#sptyno").getValue()
					};
    				grid.getStore().getProxy().extraParams=params;

					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});

       
      me.callParent();
	}
});
