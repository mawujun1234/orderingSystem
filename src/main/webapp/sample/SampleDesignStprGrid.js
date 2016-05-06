Ext.define('y.sample.SampleDesignStprGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.sample.SampleDesignStpr'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'suitno_name',header:'套件',width:60
//            ,editor: {
//                xtype: 'textfield',
//                allowBlank: false,
//                selectOnFocus:true 
//            }
        },
		{dataIndex:'spftpr',header:'出厂价',xtype: 'numbercolumn', format:'0.00',align : 'right',width:80
			,editor: {
                xtype: 'numberfield',
                allowBlank: false,
                selectOnFocus:true 
            }
		},
		{dataIndex:'sprtpr',header:'零售价',xtype: 'numbercolumn', format:'0.00',align : 'right',width:80
			,editor: {
                xtype: 'numberfield',
                allowBlank: false,
                selectOnFocus:true 
            }
		},
		{dataIndex:'plctpr',header:'预计成本价',xtype: 'numbercolumn', format:'0.00',align : 'right',width:100
			,editor: {
                xtype: 'numberfield',
                allowBlank: false,
                selectOnFocus:true 
            }
		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.sample.SampleDesignStpr',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/sampleDesignStpr/query.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json'
//					rootProperty:'root',
//					successProperty:'success',
//					totalProperty:'total'		
				}
			},
			listeners:{
						load:function(store, records, successful){
							//如果标准套的数据没有，那就从商品企划中获取标准套的价格
							for(var i=0;i<records.length;i++){
								if(records[i].get("suitno")=='T00'){
									if(!records[i].get("spftpr")){
										var tabpanel=me.up("tabpanel");
										var samplePlanFormQuery=tabpanel.down("#samplePlanFormQuery");
										var samplePlan=samplePlanFormQuery.getRecord();
										records[i].set("spftpr",samplePlan.get("spftpr"));
										records[i].set("sprtpr",samplePlan.get("sprtpr"));
									}
									return;
								}
							}
						}
			}
	  });

	  me.dockedItems=[];

	  
//	  me.dockedItems.push({
//	  		xtype: 'toolbar',
//	  		dock:'top',
//		  	items:[{
//				text: '新增',
//				itemId:'create',
//				handler: function(btn){
//					me.onCreate();
//				},
//				iconCls: 'icon-plus'
//			},{
//			    text: '更新',
//			    itemId:'update',
//			    handler: function(){
//			    	me.onUpdate();
//					
//			    },
//			    iconCls: 'icon-edit'
//			},{
//			    text: '删除',
//			    itemId:'destroy',
//			    handler: function(){
//			    	me.onDelete();    
//			    },
//			    iconCls: 'icon-trash'
//			},{
//				text: '刷新',
//				itemId:'reload',
//				disabled:me.disabledAction,
//				handler: function(btn){
//					var grid=btn.up("grid");
//					grid.getStore().reload();
//				},
//				iconCls: 'icon-refresh'
//			}]
//		});

	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1 ,
            listeners:{
            	beforeedit:function( editor, context, eOpts ) {
            		var record=context.record;
            		//标准套件不准修改
            		if(record.get("suitno")=='T00'){
            			return false;
            		} else {
            			return true;
            		}
            		
            	}          		
            }       
      });  
	  this.plugins = [this.cellEditing];
//	  //this.selType = 'cellmodel';//'rowmodel','checkboxmodel';
//	  this.on('edit', function(editor, e) {
//		e.record.save({
//	  		success:function(){
//	  			e.record.commit();
//	  		}
//	  	});
//	  });
       
      me.callParent();
	}
	
});
