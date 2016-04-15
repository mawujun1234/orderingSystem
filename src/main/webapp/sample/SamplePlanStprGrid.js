Ext.define('y.sample.SamplePlanStprGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.sample.SamplePlanStpr'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	//{xtype: 'rownumberer'},
		{dataIndex:'suitno_name',header:'套件',flex:1
        },
		{dataIndex:'spftpr',header:'出厂价',xtype: 'numbercolumn', format:'0.00',align : 'right',width:80
		},
		{dataIndex:'sprtpr',header:'零售价',xtype: 'numbercolumn', format:'0.00',align : 'right',width:80
		}
//		{dataIndex:'spplrd',header:'企划倍率',xtype: 'numbercolumn', format:'0.00',align : 'right'
//		},
//		{dataIndex:'plctpr',header:'企划成本价',xtype: 'numbercolumn', format:'0.00',align : 'right'
//		}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'y.sample.SamplePlanStpr',
			autoLoad:false
	  });
	  me.dockedItems=[];
     if(!me.readOnly) {
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
			}

			]
		});
     }
       
      me.callParent();
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('y.sample.SamplePlanStpr',{

		});
		//child.set("id",null);
		
		var formpanel=Ext.create('y.sample.SamplePlanStprForm',{});
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
    				//me.getStore().reload();
    				me.getStore().add(child);
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

		var formpanel=Ext.create('y.sample.SamplePlanStprForm',{});
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
		me.getStore().remove(node);
//		var parent=node.parentNode;
//		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
//				if (btn == 'yes'){
//					node.erase({
//					    failure: function(record, operation) {
//			            	me.getStore().reload();
//					    },
//					    success:function(){
//					    	me.getStore().reload();
//					    }
//				});
//			}
//		});
    }
});
