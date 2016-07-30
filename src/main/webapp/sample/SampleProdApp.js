Ext.require("y.sample.SampleProd");
Ext.require("y.sample.SampleProdGrid");
Ext.require("y.sample.SampleProdForm");
Ext.onReady(function(){
	var grid=Ext.create('y.sample.SampleProdGrid',{
		region:'center'
		//title:'XXX表格'
	});
	
	
	var sampleProdForm=Ext.create('y.sample.SampleProdForm',{
		title:'产品货号',
		id:'sampleProdForm',
		itemId:'sampleProdForm',
		split:true,
	    collapsible : true,
	    collapsed :true,
	    region:'east',
	    width:300,
	    listeners:{
	    	render:function(){
	    		sampleProdForm.mask();
	    	}
	    }
	});
	
	grid.on("itemclick",function(view, record, item, index, e, eOpts){
		sampleProdForm.unmask();
		sampleProdForm.expand();
		
		sampleProdForm.getForm().loadRecord(record);
	});
	sampleProdForm.grid=grid;
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,sampleProdForm]
	});



});