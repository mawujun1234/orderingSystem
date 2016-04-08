Ext.require("y.permission.Menu");
Ext.require("y.permission.MenuTree");
Ext.require("y.permission.MenuGrid");
Ext.require("y.permission.MenuForm");
Ext.onReady(function(){
	var tree=Ext.create('y.permission.MenuTree',{
		title:'菜单树',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});
	
	var grid=Ext.create('y.permission.MenuGrid',{
		region:'center',
		title:'界面元素',
		listeners:{
	    	render:function(){
	    		grid.mask();
	    	}
	    }
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,grid]
	});
	
	tree.on("itemclick",function(view, record, item, index, e, eOpts ){
		grid.unmask();
		
		grid.parent_id=record.get("id");
		grid.getStore().getProxy().extraParams={
			menuType:'element',
			parent_id:record.get("id")
		}
		grid.getStore().reload();
	});



});