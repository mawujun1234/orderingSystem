Ext.require("y.org.PositionType");
Ext.require("y.org.PositionTypeGrid");
Ext.require("y.org.PositionTypeForm");
Ext.onReady(function(){
	var grid=Ext.create('y.org.PositionTypeGrid',{
		region:'center',
		title:'XXX表格'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});