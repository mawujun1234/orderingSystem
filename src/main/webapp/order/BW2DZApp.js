
Ext.require("y.order.BW2DZGrid");
Ext.onReady(function(){
	var grid=Ext.create('y.order.BW2DZGrid',{
		region:'center'
		//title:''
	});

	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});