Ext.require("y.order.QyVO");
Ext.require("y.order.QyVOGrid");
Ext.onReady(function(){
	var grid=Ext.create('y.order.QyVOGrid',{
		region:'center'
		//title:''
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});