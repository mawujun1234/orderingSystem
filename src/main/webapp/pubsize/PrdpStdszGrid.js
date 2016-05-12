Ext.define('y.pubsize.PrdpStdszGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.pubsize.PubSizeDtl'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	selModel:'cellmodel',
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
//		{dataIndex:'sizety',header:'规格类型'
//        },
		{dataIndex:'sizeno',header:'规格代码'
        },
		{dataIndex:'sizenm',header:'规格名称'
        },
//		{dataIndex:'szbrad',header:'品牌'
//        },
//		{dataIndex:'szclno',header:'大类'
//        },
//      	{dataIndex:'sizety1_name',header:'包装类型'
//        },
		{dataIndex:'sizeqt',header:'数量',xtype: 'numbercolumn', format:'0',align : 'right',editor: {
                xtype: 'numberfield',
                allowDecimals:false,
                selectOnFocus:true 
            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
            	 metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
            	 return value;
            }
		}
//		{dataIndex:'sizemk',header:'备注'
//        },
////		{dataIndex:'sizeso',header:'排序',xtype: 'numbercolumn', format:'0',align : 'right'
////		},
//		{dataIndex:'sizest_name',header:'状态'
//		}
//		{dataIndex:'szsast_name',header:'当季状态'
//		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.pubsize.PubSizeDtl',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/pubSize/queryPrdpStdsz.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50
			    	//"params['']"
			    },
			    reader:{
					type:'json'//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
//					rootProperty:'root',
//					successProperty:'success',
//					totalProperty:'total'		
				}
			},
			listeners:{
				beforeload:function(store){
					//var grid=me;//Ext.getCmp("sampleDesignGrid");
    				//grid.getStore().getProxy().extraParams=grid.getParams();
				}
			}
	  });

	  me.dockedItems=[];
//      me.dockedItems.push({
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  });
//	   me.dockedItems.push({
//	  		xtype: 'toolbar',
//	  		dock:'top',
//		  	items:[{
//		  		itemId:'sizeno',
//		  		fieldLabel:'代码',
//		  		labelWidth:40,
//		  		width:140,
//				xtype:'textfield'
//			},{
//		  		itemId:'sizenm',
//		  		fieldLabel:'名称',
//		  		labelWidth:40,
//		  		width:140,
//				xtype:'textfield'
//			},{
//				fieldLabel: '状态',
//				labelWidth:40,
//				width:140,
//			    queryMode: 'local',
//			    xtype:'combobox',
//			    itemId:'sizest',
//			    displayField: 'name',
//			    valueField: 'id',
//			    value:'1',
//				store:Ext.create('Ext.data.Store', {
//				    fields: ['id', 'name'],
//				    data : [
//				        {"id":"0", "name":"作废"},
//				        {"id":"1", "name":"有效"}
//				    ]
//				})
//			},{
//				text: '查询',
//				itemId:'reload',
//				disabled:me.disabledAction,
//				handler: function(btn){
//					var grid=btn.up("grid");
//					grid.getStore().reload();
//				},
//				iconCls: 'icon-refresh'
//			}]
//	   });
	  
	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '新增',
				itemId:'create',
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			},{
			    text: '删除',
			    itemId:'destroy',
			    handler: function(){
			    	me.onDelete();    
			    },
			    iconCls: 'icon-trash'
			}]
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
						url:Ext.ContextPath+'/pubSize/updatePrdpStdszSizeqt.do',
						params:{
							fszno:me.getStore().getProxy().extraParams.fszno,
							sizety:record.get("sizety"),
							sizeno:record.get("sizeno"),
							sizeqt:value
						},
						success:function(){
							me.getStore().reload();
						}
						
					});
	  	
	  });
       
      me.callParent();
	},
//	getParams:function(){
//		var toolbars=this.getDockedItems('toolbar[dock="top"]');
//		var params={
//			"params['sizeno']":toolbars[0].down("#sizeno").getValue(),
//			"params['sizenm']":toolbars[0].down("#sizenm").getValue(),
//			"params['sizest']":toolbars[0].down("#sizest").getValue()
//		};
//		return params;
//	},
	onCreate:function(){
    	var me=this;
//		var child=Ext.create('y.pubsize.PubSize',{
//			sizety:'PRDPK'
//		});
//		child.set("id",null);
		
		var stdszSelGrid=Ext.create('y.pubsize.StdszSelGrid',{
			listeners:{
				itemdblclick:function(view, record, item, index, e, eOpts){
					Ext.Ajax.request({
						url:Ext.ContextPath+'/pubSize/selPrdpStdsz.do',
						params:{
							fszno:me.getStore().getProxy().extraParams.fszno,
							sizety:record.get("sizety"),
							sizeno:record.get("sizeno")
						},
						success:function(){
							me.getStore().reload();
						}
						
					});
				}
			}
		});
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'双击选择',
    		modal:true,
    		width:700,
    		height:300,
    		closeAction:'hide',
    		items:[stdszSelGrid],
    		listeners:{
    			close:function(){
    				//me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    },
    
//     onUpdate:function(){
//    	var me=this;
//
//    	var node=me.getSelectionModel( ).getLastSelected();
//    	if(node==null){
//    		Ext.Msg.alert("提醒","请选择一行数据!");
//    		return;
//    	}
//
//		var formpanel=Ext.create('y.pubsize.PrdpForm',{});
//		formpanel.loadRecord(node);
//		
//    	var win=Ext.create('Ext.window.Window',{
//    		layout:'fit',
//    		title:'更新',
//    		modal:true,
//    		width:400,
//    		height:300,
//    		closeAction:'hide',
//    		items:[formpanel]
//    	});
//    	win.show();
//    },
    
    onDelete:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

		if(!record){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}

		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
			if (btn == 'yes'){
				Ext.Ajax.request({
						url:Ext.ContextPath+'/pubSize/deletePrdpStdszSizeqt.do',
						params:{
							fszno:me.getStore().getProxy().extraParams.fszno,
							sizety:record.get("sizety"),
							sizeno:record.get("sizeno")
						},
						success:function(){
							me.getStore().reload();
						}
						
					});
			}
		});
    }
});
