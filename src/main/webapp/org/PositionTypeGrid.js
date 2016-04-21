Ext.define('y.org.PositionTypeGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.org.PositionType'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
      	{dataIndex:'id',header:'职位编码'
        },
		{dataIndex:'name',header:'职位名称'
        },
		{dataIndex:'remark',header:'备注'
        },
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: 'y.org.PositionType',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/positionType/query.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
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
		var child=Ext.create('y.org.PositionType',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.org.PositionTypeForm',{});
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

		var formpanel=Ext.create('y.org.PositionTypeForm',{});
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
