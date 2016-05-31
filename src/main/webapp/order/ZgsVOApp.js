
Ext.require("y.order.ZgsVOGrid");
Ext.onReady(function(){
	var grid=Ext.create('y.order.ZgsVOGrid',{
		region:'center'
		//title:''
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});