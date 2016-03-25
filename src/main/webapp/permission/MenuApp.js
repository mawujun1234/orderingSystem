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
		title:'界面元素'
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,grid]
	});
	
	tree.on("",function(){
	
	});



});