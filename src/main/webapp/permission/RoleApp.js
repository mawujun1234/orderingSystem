Ext.require("y.permission.Role");
Ext.require("y.permission.RoleTree");
Ext.require("y.permission.RoleForm");
Ext.require('y.permission.UserGrid');
Ext.onReady(function(){
	var tree=Ext.create('y.permission.RoleTree',{
		title:'角色管理',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});
	
	var userGrid=Ext.create('y.permission.UserGrid',{
		
		title:'用户管理'
	});
	
	
	var tabpanel=Ext.create('Ext.tab.Panel', {
	    region:'center',
	    items: [userGrid]
	});
	
	
	tree.on("itemclick",function( view, record, item, index, e, eOpts ){
		if(record.get("roleType")=="rolegroup" || !record.get("roleType")){
			tabpanel.mask();
			return;
		} else {
			tabpanel.unmask();
		}

		window.selected_role=record;
		userGrid.getStore().getProxy().extraParams=Ext.apply(userGrid.getStore().getProxy().extraParams,{
			role_id:record.get("id")
		});
		userGrid.getStore().reload();
		
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,tabpanel]
	});



});