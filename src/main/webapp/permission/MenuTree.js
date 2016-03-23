/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('y.permission.MenuTree', {
    extend: 'Ext.tree.Panel',
    requires:['y.permission.Menu'],
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'id',//传递到后台的数据，默认是node
	       	model:'y.permission.Menu',
			root: {
			    expanded: true,
			    text:"菜单管理" 
			}
		});
		me.initAction();
       
		me.callParent(arguments);
    },

    initAction:function(){
     	var me = this;
     	var actions=[];
     	
       var create = new Ext.Action({
		    text: '新建菜单',
		    itemId:'create',
		    handler: function(b){
		    	me.onCreate(null,b);
		    },
		    iconCls: 'icon-plus'
		});
		//me.addAction(create);
		actions.push(create);
		
		var destroy = new Ext.Action({
		    text: '删除',
		    itemId:'destroy',
		    handler: function(){
		    	me.onDelete();    
		    },
		    iconCls: 'icon-remove'
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
		
//		me.on('containercontextmenu',function(view,e){
//			menu.showAt(e.getXY());
//			e.stopEvent();
//		});
		me.contextMenu=menu;
		
    },
    onCreate:function(values,b){
    	var me=this;

    	values=values||{};

    	var parent=me.getSelectionModel( ).getLastSelected( )||me.getRootNode( );    

		var initValue={
		    'parent_id':parent.get("id"),
		    text:'新节点'
		};

    	values=Ext.applyIf(values,initValue);

		var child=values.isModel?values:Ext.createModel(parent.self.getName(),values);

		child.save({
			success: function(record, operation) {
				parent.set('leaf',false);
				parent.appendChild(child);
				//alert(1);
//				parent.leaf=false;
//				me.getStore().reload({node:parent});
//				parent.expand();
			}
		});
    },
    
    onDelete:function(node){
    	var me=this;
    	var node=node||me.getSelectionModel( ).getLastSelected( );

		if(!node){
		    Ext.Msg.alert("消息","请先选择节点");	
			return;
		}
		if(node.isRoot()){
			Ext.Msg.alert("消息","根节点不能删除!");	
			return;
		}
		if(node&&node.hasChildNodes( ) &&　!me.cascadeDelete){
			Ext.Msg.alert("消息","请先删除子节点!");
		    return;
		}
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
				   var parent=node.parentNode;
				   node.destroy({
				        failure: function(record, operation) {
				        if(parent){
				        	var index=parent.indexOf(node);
		            		me.getStore().reload({node:parent});
		            		//parent.insertChild(index,record);
				        }
				        Ext.Msg.alert("消息","删除失败!");
		            	return;
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
