Ext.require("y.plan.PlanCls");
Ext.require("y.plan.PlanClsGrid");
Ext.require("y.plan.PlanClsForm");
Ext.onReady(function(){
	var grid=Ext.create('y.plan.PlanClsGrid',{
		region:'center'
		//title:'XXX表格'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});