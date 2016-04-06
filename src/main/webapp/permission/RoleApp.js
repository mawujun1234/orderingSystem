Ext.require("y.permission.Role");
Ext.require("y.permission.RoleTree");
Ext.require("y.permission.RoleForm");
Ext.require('y.permission.RoleUserGrid');
Ext.require('y.permission.MenuTreeCheckbox');
Ext.onReady(function(){
	var tree=Ext.create('y.permission.RoleTree',{
		title:'角色管理',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});
	
	var userGrid=Ext.create('y.permission.RoleUserGrid',{
		
		title:'用户选择'
	});
	
	
	var menuTreeCheckbox=Ext.create('y.permission.MenuTreeCheckbox',{
		title:'菜单选择'
	})
	
	var tabpanel=Ext.create('Ext.tab.Panel', {
	    region:'center',
	    items: [userGrid,menuTreeCheckbox],
	    listeners:{
	    	render:function(){
	    		tabpanel.mask();
	    	}
	    }
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
		
		menuTreeCheckbox.getStore().getProxy().extraParams=Ext.apply(menuTreeCheckbox.getStore().getProxy().extraParams,{
			role_id:record.get("id")
		});
		menuTreeCheckbox.query_checked_node();
		
	});
	
	

	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,tabpanel]
	});



});