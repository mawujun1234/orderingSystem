Ext.define('y.pubsize.StdszGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.pubsize.PubSize'
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
//		{dataIndex:'sizeqt',header:'数量',xtype: 'numbercolumn', format:'0',align : 'right'
//		},
		{dataIndex:'sizemk',header:'备注'
        },
//		{dataIndex:'sizeso',header:'排序',xtype: 'numbercolumn', format:'0',align : 'right'
//		},
		{dataIndex:'sizest_name',header:'状态'
		},
		{dataIndex:'szsast_name',header:'当季状态'
		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: 'y.pubsize.PubSize',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/pubSize/queryStdsz.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50
			    	//"params['']"
			    },
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			},
			listeners:{
				beforeload:function(store){
					var grid=me;//Ext.getCmp("sampleDesignGrid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
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
		  		itemId:'sizeno',
		  		fieldLabel:'代码',
		  		labelWidth:40,
				xtype:'textfield'
			},{
		  		itemId:'sizenm',
		  		fieldLabel:'名称',
		  		labelWidth:40,
				xtype:'textfield'
			},{
				fieldLabel: '状态',
				labelWidth:40,
			    queryMode: 'local',
			    xtype:'combobox',
			    itemId:'sizest',
			    displayField: 'name',
			    valueField: 'id',
			    value:'1',
				store:Ext.create('Ext.data.Store', {
				    fields: ['id', 'name'],
				    data : [
				        {"id":"0", "name":"作废"},
				        {"id":"1", "name":"有效"}
				    ]
				})
			},{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
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
			}]
		});

       
      me.callParent();
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var params={
			"params['sizeno']":toolbars[0].down("#sizeno").getValue(),
			"params['sizenm']":toolbars[0].down("#sizenm").getValue(),
			"params['sizest']":toolbars[0].down("#sizest").getValue()
		};
		return params;
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('y.pubsize.PubSize',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.pubsize.StdszForm',{});
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

		var formpanel=Ext.create('y.pubsize.StdszForm',{});
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
