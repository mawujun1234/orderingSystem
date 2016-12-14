Ext.require("y.sample.SampleClhd");
Ext.require("y.sample.SampleClhdGrid");
Ext.require("y.sample.SampleClhdForm");
Ext.onReady(function(){
	var grid=Ext.create('y.sample.SampleClhdGrid',{
		region:'west',
		width:400,
		split:true,
		
		title:'搭配表'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,{region:'center',html:'tabpanel'}]
	});



});