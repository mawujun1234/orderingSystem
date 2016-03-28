/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('y.permission.RoleTree', {
    extend: 'Ext.tree.Panel',
    requires:['y.permission.Role'],
    displayField:'name',
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'parent_id',//传递到后台的数据，默认是node
	       	model:'y.permission.Role',
			root: {
			    expanded: true,
			    name:"角色管理" 
			}
		});
		me.initAction();
       
		me.callParent(arguments);
    },

    initAction:function(){
     	var me = this;
     	var actions=[];
     	
       var create = new Ext.Action({
		    text: '新建',
		    itemId:'create',
		    handler: function(b){
		    	me.onCreate(null,b);
		    },
		    iconCls: 'icon-plus'
		});
		//me.addAction(create);
		actions.push(create);
		
		var update = new Ext.Action({
		    text: '更新',
		    itemId:'update',
		    handler: function(){
		    	me.onUpdate();
				
		    },
		    iconCls: 'icon-edit'
		});
		actions.push(update);
		
		var destroy = new Ext.Action({
		    text: '删除',
		    itemId:'destroy',
		    handler: function(){
		    	me.onDelete();    
		    },
		    iconCls: 'icon-trash'
		});
		//me.addAction(destroy);
		actions.push(destroy)
		
		
		var reload = new Ext.Action({
		    text: '刷新',
		    itemId:'reload',
		    handler: function(){
		    	me.onReload();
		    },
		    iconCls: 'icon-refresh'
		});
		//me.addAction(reload);
		actions.push(reload);

		var menu=Ext.create('Ext.menu.Menu', {
			items: actions
		});	
		me.on('itemcontextmenu',function(tree,record,item,index,e){
			menu.showAt(e.getXY());
			e.stopEvent();
		});
		me.contextMenu=menu;
		
    },
    onCreate:function(){
    	var me=this;
		
    	
    	
    	var parent=me.getSelectionModel( ).getLastSelected( )||me.getRootNode( );    
    	if(parent.get("roleType")=='role'){
    		alert("角色不能新建下一级!");
    		return;
    	}
    	
    	var form=Ext.create('y.permission.RoleForm',{});

		var child=Ext.create(parent.self.getName(),{
		    'parent_id':parent.get("id"),
		    text:''
		});
		child.set("id",null);
		form.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[form],
    		listeners:{
    			close:function(){
    				me.onReload(parent);
    			}
    		}
    	});
    	win.show();
    },
    
     onUpdate:function(){
    	var me=this;
		
    	var form=Ext.create('y.permission.RoleForm',{});
    	
    	var node=me.getSelectionModel( ).getLastSelected();
    	if(node==null || node.isRoot()){
    		Ext.Msg.alert("提醒","请选择一个不是根节点的节点!");
    		return;
    	}

		form.loadRecord(node);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'更新',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[form]
    	});
    	win.show();
    },
    
    onDelete:function(){
    	var me=this;
    	var node=me.getSelectionModel( ).getLastSelected( );

		if(!node){
		    Ext.Msg.alert("消息","请先选择节点");	
			return;
		}
		if(node.isRoot()){
			Ext.Msg.alert("消息","根节点不能删除!");	
			return;
		}
		var parent=node.parentNode;
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					node.erase({
					    failure: function(record, operation) {
			            	me.onReload(parent);
					    },
					    success:function(){
					    	me.onReload(parent);
					    }
				});
			}
		});
    },
    onReload:function(node){
    	var me=this;
    	var parent=node||me.getSelectionModel( ).getLastSelected( );
		if(parent){
		    me.getStore().reload({node:parent});
		} else {
		    me.getStore().reload();	
		}      
    },
   
    
    getContextMenu:function(){
    	return this.contextMenu;
    },
    getLastSelected:function(){
    	return this.getSelectionModel( ).getLastSelected( );
    }
    
});
