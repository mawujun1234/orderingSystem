Ext.require("y.sample.SamplePlan");
Ext.require("y.sample.SamplePlanGrid");
Ext.require("y.sample.SamplePlanForm");
Ext.onReady(function(){
	var grid=Ext.create('y.sample.SamplePlanGrid',{
		itemId:'samplePlanGrid',
		region:'center'
		//title:'XXX表格'
	});
	
	var samplePlanForm=Ext.create('y.sample.SamplePlanForm',{
		itemId:'samplePlanForm'
	});
	
	var tabpanel=Ext.create('Ext.tab.Panel', {
	    region:'east',
	    width:300,
	    split:true,
	    collapsible : true,
	    items: [samplePlanForm],
	    listeners:{
	    	render:function(){
	    		tabpanel.mask();
	    	}
	    }
	});
	
	grid.on("itemclick",function(view, record, item, index, e, eOpts){
		
//		if(grid.spclno==record.get("spclno")){
//			return;
//		}
		tabpanel.mask("正在更新.....");
		grid.spclno=record.get("spclno");
		tabpanel.setTitle("编辑样衣:"+record.get("plspnm"));
		
		samplePlanForm.reset();
		samplePlanForm.loadRecord(record);
		
		tabpanel.unmask();
		
		
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabpanel]
	});



});