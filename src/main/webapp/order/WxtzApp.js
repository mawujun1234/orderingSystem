
Ext.require("y.order.WxtzGrid");
Ext.onReady(function(){
	var grid=Ext.create('y.order.WxtzGrid',{
		region:'center'
		//title:''
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});