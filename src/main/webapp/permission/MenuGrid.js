Ext.define('y.permission.MenuGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.permission.Menu'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
     var store_menuType=Ext.create('Ext.data.Store',{
     	storeId:'store_menuType',
		fields: ['key', 'name'],
		data : [
			{"key":"menu", "name":"菜单"},
			{"key":"element", "name":"界面元素"}
		]
	});
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'name',text:'菜单名称'
            ,editor: {
                xtype: 'textfield',
                allowBlank: false,
                selectOnFocus:true 
            }
        },
		{dataIndex:'menuType',text:'菜单类型'
			,editor: {
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'key',
			    store: store_menuType,
                allowBlank: false,
                xtype:'combobox'
			},renderer: function(val,metaData,record ,rowIndex ,colIndex ,store,view ){
				var combobox_store=Ext.data.StoreManager.lookup('store_menuType');
	            var record = combobox_store.findRecord('key',val); 
	            if (record != null){
	                return record.get("name"); 
	            } else {
	                return val;
	            }
	        }
        },
		{dataIndex:'leaf',header:'叶子节点',xtype: 'checkcolumn'
//			stopSelection :false,
//			processEvent : function(type) {  
//            	if (type == 'click')  
//                   return false;  
//            },
            ,editor: {
                xtype: 'checkbox',
                cls: 'x-grid-checkheader-editor'
            }
		},
		{dataIndex:'createDate',text:'创建时间',xtype: 'datecolumn',   format:'Y-m-d'
			,editor: {
                xtype: 'datefield',
                format : 'Y-m-d',
                editable : false
            }
		},
		{dataIndex:'url',text:'地址'
            ,editor: {
                xtype: 'textfield',
                selectOnFocus:true 
            }
        },
		{dataIndex:'remark',text:'备注'
            ,editor: {
                xtype: 'textfield',
                selectOnFocus:true 
            }
        }
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: 'y.permission.Menu',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/menu/query.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',
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
	  
	  me.dockedItems.push({
	  	xtype: 'toolbar',
	  	dock:'top',
		items:[
			{
                xtype: 'textfield',
				itemId:'query_name',
                fieldLabel: '菜单名称',
                labelWidth:60,
                width:150,
                selectOnFocus:true 
            },
			{
                xtype: 'textfield',
				itemId:'query_menuType',
                fieldLabel: '菜单类型',
                labelWidth:60,
                width:150,
                selectOnFocus:true 
            },
			{
                xtype: 'checkbox',
                itemId:'query_leaf',
                fieldLabel: '叶子节点',
                labelWidth:60,
                width:100,
                cls: 'x-grid-checkheader-editor'
            },
	    	{
                xtype: 'datefield',
                itemId:'query_createDate_start',
                fieldLabel: '开始时间',//创建时间
	  			labelWidth:60,
	  			width:160,
                format : 'Y-m-d',
                editable : false
            },{
                xtype: 'datefield',
                itemId:'query_createDate_end',
                fieldLabel: '结束时间',//创建时间
	  			labelWidth:60,
	  			width:160,
                format : 'Y-m-d',
                editable : false
            }
	  	]
	  });

	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  //this.selType = 'checkboxmodel';//'rowmodel';
	  this.on('edit', function(editor, e) {
		e.record.save({
	  		success:function(){
	  			e.record.commit();
	  		}
	  	});
	  });
       
      me.callParent();
	},
	onCreate:function(){
    	var me=this;
		
    	var form=Ext.create('y.permission.MenuForm',{});

		var child=Ext.create('y.permission.Menu',{

		});
		child.set("id",null);
		form.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		items:[form],
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
		
    	var form=Ext.create('y.permission.MenuForm',{});
    	
    	var node=me.getSelectionModel( ).getLastSelected();
    	if(node==null){
    		Ext.Msg.alert("提醒","请选择一行数据!");
    		return;
    	}

		form.loadRecord(node);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'更新',
    		modal:true,
    		width:400,
    		height:300,
    		items:[form]
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
