
Ext.require("y.plan.OrdDwdtGrid");
Ext.onReady(function(){
	var grid=Ext.create('y.plan.OrdDwdtGrid',{
		region:'center'
		//title:'XXX表格'
	});
	
	
//	
//	
//	grid.on("itemclick",function(view, record, item, index, e, eOpts){
//		sampleProdForm.unmask();
//		sampleProdForm.expand();
//		
//		sampleProdForm.getForm().loadRecord(record);
//	});
//	sampleProdForm.grid=grid;
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});