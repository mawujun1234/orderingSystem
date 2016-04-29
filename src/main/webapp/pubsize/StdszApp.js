Ext.require("y.pubsize.PubSize");
Ext.require("y.pubsize.StdszGrid");
Ext.require("y.pubsize.StdszForm");
Ext.onReady(function(){
	var grid=Ext.create('y.pubsize.StdszGrid',{
		region:'center'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});