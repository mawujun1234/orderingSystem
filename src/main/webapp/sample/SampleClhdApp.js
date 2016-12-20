Ext.require("y.sample.SampleClhd");
Ext.require("y.sample.SampleClhdGrid");
Ext.require("y.sample.SampleClhdForm");
Ext.require("y.sample.SampleCldtlGrid");
Ext.onReady(function(){
	var grid=Ext.create('y.sample.SampleClhdGrid',{
		region:'west',
		width:400,
		split:true,
		
		title:'搭配表'
	});
	
	var sampleCldtlGrid=Ext.create('y.sample.SampleCldtlGrid',{
		title:'包含样衣'
	});
	
	var samplePhotoShow=Ext.create('y.sample.SampleClphtShow',{
		title:'搭配图片'
	});
	
	grid.on("itemclick",function(view , record , item , index , e , eOpts){
		sampleCldtlGrid.getStore().getProxy().extraParams={
			clppno:record.get("clppno"),
			ormtno:grid.getStore().getProxy().extraParams["params['ormtno']"],
			bradno:grid.getStore().getProxy().extraParams["params['bradno']"]
		};
		sampleCldtlGrid.getStore().reload();
		
		samplePhotoShow.getStore().getProxy().extraParams={
			clppno:record.get("clppno"),
			ormtno:grid.getStore().getProxy().extraParams["params['ormtno']"]
		}
		samplePhotoShow.getStore().reload();
	});
	
	var tabpanel=Ext.create('Ext.tab.Panel',{
		region:'center',
		items:[sampleCldtlGrid,samplePhotoShow]
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabpanel]
	});



});