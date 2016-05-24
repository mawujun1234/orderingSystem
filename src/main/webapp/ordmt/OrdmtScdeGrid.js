Ext.define('y.ordmt.OrdmtScdeGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.ordmt.OrdmtScde'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	//{xtype: 'rownumberer'},
      	
      	{dataIndex:'channo_name',header:'订货单位类型'
        },
		{dataIndex:'mtstdt',header:'开始日期',xtype: 'datecolumn', format:'Y-m-d',width:150
		},
		{dataIndex:'mtfidt',header:'结束日期',xtype: 'datecolumn', format:'Y-m-d',width:150
		},
		{dataIndex:'mtsttm',header:'开始时间',xtype: 'datecolumn', format:'H:i'
        },
		{dataIndex:'mtfitm',header:'结束时间',xtype: 'datecolumn', format:'H:i'
        },
		{dataIndex:'mtlmsp',header:'修改人'
        },
		{dataIndex:'mtlmdt',header:'修改日期',xtype: 'datecolumn', format:'Y-m-d H:i:s',width:150
		}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'y.ordmt.OrdmtScde',
			autoLoad:false
	  });
	  me.dockedItems=[];

	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{text:'订货日程:',xtype: 'label'},{
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
		var child=Ext.create('y.ordmt.OrdmtScde',{

		});
		child.set("ormtno",me.ormtno);
		
		var formpanel=Ext.create('y.ordmt.OrdmtScdeForm',{});
		formpanel.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
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

		var formpanel=Ext.create('y.ordmt.OrdmtScdeForm',{});
		formpanel.loadRecord(node);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'更新',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel]
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
