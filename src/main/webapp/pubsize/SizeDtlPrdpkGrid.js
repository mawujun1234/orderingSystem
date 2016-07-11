Ext.define('y.pubsize.SizeDtlPrdpkGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.pubsize.SizeDtl'
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
//		{dataIndex:'fszty',header:'上级类型'
//        },
//		{dataIndex:'fszno',header:'上级代码'
//        },
//		{dataIndex:'sizety',header:'规格类型'
//        },
		{dataIndex:'sizeno',header:'规格代码'
        },
        {dataIndex:'sizenm',header:'规格名称'
        }
//		{dataIndex:'ormtno',header:'订货会批号'
//        }
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.pubsize.SizeDtl',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/sizeDtl/query.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json'//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
//					rootProperty:'root',
//					successProperty:'success',
//					totalProperty:'total'		
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
	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '新增',
				itemId:'create',
				hidden:!Permision.canShow('PRDSZFW_PRDPK_create'),
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			},{
			    text: '删除',
			    itemId:'destroy',
			    hidden:!Permision.canShow('PRDSZFW_PRDPK_delete'),
			    handler: function(){
			    	me.onDelete();    
			    },
			    iconCls: 'icon-trash'
			}]
		});

       
      me.callParent();
	},
	onCreate:function(){
    	var me=this;
//		var child=Ext.create('y.pubsize.SizeDtl',{
//
//		});
//		child.set("id",null);
//		
//		var formpanel=Ext.create('y.pubsize.SizeDtlForm',{});
//		formpanel.loadRecord(child);
    	
    	var stdszSelGrid=Ext.create('y.pubsize.PrdsztyPrdpkQueryGrid',{
    		listeners:{
				selRecord:function(view, records){
					//alert(9);
					var sizenos=[];
					for(var i=0;i<records.length;i++){
						sizenos[i]=records[i].get("sizeno")
					}
					//alert(1);
					Ext.Ajax.request({
						url:Ext.ContextPath+'/sizeDtl/create.do',
						params:{
							fszno:me.getStore().getProxy().extraParams.fszno,
							fszty:me.getStore().getProxy().extraParams.fszty,
							sizenos:sizenos//record.get("sizeno")
							,sizety:'PRDPK'
							,ormtno:me.getStore().getProxy().extraParams.ormtno
						},
						success:function(){
							me.getStore().reload();
							stdszSelGrid.getStore().reload();
						}
						
					});
				}
			}
    	});
    	
    	var last_record=window.prdsztyGrid.getSelectionModel().getLastSelected();
    	var last_size_record=window.sizeGrid.getSelectionModel().getLastSelected();
    	stdszSelGrid.getStore().getProxy().extraParams={
			"szbrad":window.prdsztyGrid.getStore().getProxy().extraParams["params['szbrad']"],
			"szclno":window.prdsztyGrid.getStore().getProxy().extraParams["params['szclno']"],
			fszno:last_record.get("sizeno"),
			fszty:last_record.get("sizety"),
			ormtno:last_size_record.get("ormtno"),
			fszty_size:last_size_record.get("sizety"),
			fszno_size:last_size_record.get("sizeno"),
			sizety:"PRDPK"
		}
		stdszSelGrid.getStore().reload();
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[stdszSelGrid],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    },
    
    
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
						url:Ext.ContextPath+'/sizeDtl/deleteById.do',
						params:{
							fszno:me.getStore().getProxy().extraParams.fszno,
							fszty:me.getStore().getProxy().extraParams.fszty,
							sizeno:record.get("sizeno"),
							sizety:record.get("sizety")
						},
						success:function(){
							me.getStore().reload();
						}
						
					});
			}
		});
    }
});
