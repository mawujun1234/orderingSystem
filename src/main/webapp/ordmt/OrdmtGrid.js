Ext.define('y.ordmt.OrdmtGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.ordmt.Ordmt'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'ormtno',header:'订货会批号'
        },
		{dataIndex:'ormtnm',header:'订货会名称'
        },
		{dataIndex:'ormtsn',header:'订货会简称'
        },
		{dataIndex:'pryear',header:'产品年份',xtype: 'numbercolumn', format:'0',align : 'right',width:100
		},
		{dataIndex:'mtstdt',header:'开始日期',xtype: 'datecolumn', format:'Y-m-d',width:100
		},
		{dataIndex:'mtfidt',header:'结束日期',xtype: 'datecolumn', format:'Y-m-d',width:100
		},
//		{dataIndex:'ormtst',header:'状态',xtype: 'checkcolumn'	
//            ,stopSelection :false,
//			processEvent : function(type) {  
//            	if (type == 'click')  
//                   return false;  
//            }
//		},
		{dataIndex:'ormtst_name',header:'状态'},
//		{dataIndex:'ormtfg',header:'跟踪状态',xtype: 'checkcolumn'	
//            ,stopSelection :false,
//			processEvent : function(type) {  
//            	if (type == 'click')  
//                   return false;  
//            }
//		},
		{dataIndex:'ormtfg_name',header:'跟踪状态'},
		{dataIndex:'seasnos_name',header:'季节'
        },
		{dataIndex:'ormtmk',header:'备注'
        },
		{dataIndex:'mtrgsp',header:'创建人'
        },
		{dataIndex:'mtrgdt',header:'创建日期',xtype: 'datecolumn', format:'Y-m-d H:i:s',width:150
		},
		{dataIndex:'mtlmsp',header:'修改人'
        },
		{dataIndex:'mtlmdt',header:'修改日期',xtype: 'datecolumn', format:'Y-m-d H:i:s',width:150
		}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'y.ordmt.Ordmt',
			autoLoad:true
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
				text: '新增',
				itemId:'create',
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			},{
			    text: '更新',
			    itemId:'update',
			    handler: function(){
			    	me.onUpdate();
					
			    },
			    iconCls: 'icon-edit'
			},{
			    text: '删除',
			    itemId:'destroy',
			    handler: function(){
			    	me.onDelete();    
			    },
			    iconCls: 'icon-trash'
			},{
				text: '刷新',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});

       
      me.callParent();
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('y.ordmt.Ordmt',{

		});
		var ormtno=Ext.Date.format( new Date(), 'Ym' );
		child.set("ormtno",ormtno);
		
		
		var formpanel=Ext.create('y.ordmt.OrdmtForm',{

		});
		formpanel.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:500,
    		closeAction:'hide',
    		items:[formpanel],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    },
    
     onUpdate:function(){
    	var me=this;

    	var node=me.getSelectionModel( ).getLastSelected();
    	if(node==null){
    		Ext.Msg.alert("提醒","请选择一行数据!");
    		return;
    	}

		var formpanel=Ext.create('y.ordmt.OrdmtForm',{});
		formpanel.loadRecord(node);
		
		//console.log(node.get("seasnos") instanceof Array);
		var aa=node.get("seasnos");
		var seasnos=[];
		for(var i=0;i<aa.length;i++){
			seasnos.push(aa[i].seasno);
		}
		//console.log(seasnos);
		formpanel.getComponent("seasnos").setValue({"seasno":seasnos});
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'更新',
    		modal:true,
    		width:400,
    		height:500,
    		closeAction:'hide',
    		items:[formpanel],
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
    	var node=me.getSelectionModel( ).getLastSelected( );

		if(!node){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}
		var parent=node.parentNode;
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					node.erase({
					    failure: function(record, operation) {
			            	me.getStore().reload();
					    },
					    success:function(){
					    	me.getStore().reload();
					    }
				});
			}
		});
    }
});
