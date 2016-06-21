Ext.require("y.plan.PlanHd");
Ext.require("y.plan.PlanHdGrid");
Ext.onReady(function(){
	var grid=Ext.create('y.plan.PlanHdGrid',{
		region:'center'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});