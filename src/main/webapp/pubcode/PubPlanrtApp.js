Ext.require("y.pubcode.PubPlanrt");
Ext.require("y.pubcode.PubPlanrtGrid");

Ext.onReady(function(){
	var grid=Ext.create('y.pubcode.PubPlanrtGrid',{
		region:'center'
		//title:'XXX表格'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});