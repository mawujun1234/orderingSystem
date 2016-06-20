Ext.require("y.plan.PlanOrgdtlVO");
Ext.require("y.plan.PlanOrgdtlVOGrid");

Ext.onReady(function(){
	var grid=Ext.create('y.plan.PlanOrgdtlVOGrid',{
		region:'center'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});