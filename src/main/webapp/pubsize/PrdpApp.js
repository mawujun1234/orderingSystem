Ext.require("y.pubsize.PubSize");
Ext.require("y.pubsize.PrdpGrid");
Ext.require("y.pubsize.PrdpForm");
Ext.onReady(function(){
	var grid=Ext.create('y.pubsize.PrdpGrid',{
		region:'center'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});