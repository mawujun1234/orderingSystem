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
	});
	//品牌
	var  brandRolePubCodeGrid=Ext.create('y.permission.RolePubCodeGrid',{
		title:'可访问品牌',
		tyno:1,
		listeners:{
	    	select:function( selModel, record, index){
	    		Ext.Ajax.request({
						url:Ext.ContextPath+'/role/selBrand.do',
						params:{
							itno:record.get("itno"),
							role_id:window.selected_role.get("id")
						},
						headers:{ 'Accept':'application/json;'},
						success:function(){
							//button.up('window').close();
							//me.getStore().reload();
						}
				});
	    	},
	    	deselect:function(selModel, record, index){
	    		Ext.Ajax.request({
						url:Ext.ContextPath+'/role/deselBrand.do',
						params:{
							itno:record.get("itno"),
							role_id:window.selected_role.get("id")
						},
						headers:{ 'Accept':'application/json;'},
						success:function(){
							//button.up('window').close();
							//me.getStore().reload();
						}
				});
	    	}
	    }
	});
	//大类
	var  classRolePubCodeGrid=Ext.create('y.permission.RolePubCodeGrid',{
		title:'可访问大类',
		tyno:0,
		listeners:{
	    	select:function( selModel, record, index){
	    		Ext.Ajax.request({
						url:Ext.ContextPath+'/role/selClass.do',
						params:{
							itno:record.get("itno"),
							role_id:window.selected_role.get("id")
						},
						headers:{ 'Accept':'application/json;'},
						success:function(){
							//button.up('window').close();
							//me.getStore().reload();
						}
				});
	    	},
	    	deselect:function(selModel, record, index){
	    		Ext.Ajax.request({
						url:Ext.ContextPath+'/role/deselClass.do',
						params:{
							itno:record.get("itno"),
							role_id:window.selected_role.get("id")
						},
						headers:{ 'Accept':'application/json;'},
						success:function(){
							//button.up('window').close();
							//me.getStore().reload();
						}
				});
	    	}
	    }
	});
	
	var tabpanel=Ext.create('Ext.tab.Panel', {
	    region:'center',
	    items: [userGrid,menuTreeCheckbox,brandRolePubCodeGrid,classRolePubCodeGrid],
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
		
		//获取可以访问的品牌和大类权限
		Ext.Ajax.request({
			url:Ext.ContextPath+'/role/querySelBrandAndClass.do',
			params:{
				role_id:window.selected_role.get("id")
			},
			headers:{ 'Accept':'application/json;'},
			success:function(response){
				var obj=Ext.decode(response.responseText);
				brandRolePubCodeGrid.checkSel(obj.brands);
				classRolePubCodeGrid.checkSel(obj.classes);
			}
		});
		
	});
	
	

	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,tabpanel]
	});



});