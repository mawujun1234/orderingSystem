Ext.require("y.sample.SamplePlan");
Ext.require("y.sample.SamplePlanGrid");
Ext.require("y.sample.SamplePlanForm");
Ext.onReady(function(){
	var sampleDesignGrid=Ext.create('y.sample.SampleDesignGrid',{
		itemId:'sampleDesignGrid',
		region:'center'
		//title:'XXX表格'
	});
	
	var samplePlanForm=Ext.create('y.sample.SamplePlanFormQuery',{
		title:'商品企划',
		itemId:'samplePlanFormQuery'
	});
	//设计开发
	var sampleDesignForm=Ext.create('y.sample.SampleDesignForm',{
		title:'设计开发',
		itemId:'sampleDesignForm'
	});
	//面料信息
	var sampleMateGrid=Ext.create('y.sample.SampleMateGrid',{
		region:'north',
		height:200,
		itemId:'sampleMateGrid'
	}); 
	var sampleMateForm=Ext.create('y.sample.SampleMateForm',{
		region:'center',
		itemId:'sampleMateForm'
	});
	var sampleMatePanel=Ext.create('Ext.panel.Panel',{
		title:'面料信息',
		layout:'border',
		items:[sampleMateGrid,sampleMateForm]
	});
	sampleMateGrid.getStore().on("load",function(store,records){
		if(records.length>0){
			sampleMateGrid.getSelectionModel( ).select(records[0]);
			//就是为了显示设计编号，没什么实际用处，如果form中的sampnm去掉，那这个也可以去掉了
			records[0].set("sampnm",window.sampno.sampnm);
			sampleMateForm.loadRecord(records[0]);
		}
		
	});
	sampleMateGrid.on("itemclick",function(view, record, item, index, e, eOpts){
		sampleMateForm.loadRecord(record);
	});
	
	//成衣信息
	var sampleColthForm=Ext.create('y.sample.SampleColthForm',{
		title:'成衣信息',
		itemId:'sampleColthForm'
	});
	var sampleColth=Ext.create('y.sample.SampleColth',{
					sampno:null
	});
	sampleColthForm.loadRecord(sampleColth);
	
	//图片信息
	var samplePhotoShow=Ext.create('y.sample.SamplePhotoShow',{
		title:'产品图片',
		itemId:'samplePhotoShow'
	});
	
	
	var tabpanel=Ext.create('Ext.tab.Panel', {
	    region:'east',
	    width:300,
	    split:true,
	    collapsible : true,
	    items: [samplePlanForm,sampleDesignForm,sampleMatePanel,sampleColthForm,samplePhotoShow],
	    listeners:{
	    	render:function(){
	    		tabpanel.mask();
	    	}
	    }
	});
	
	
	
	sampleDesignGrid.on("itemclick",function(view, record, item, index, e, eOpts){
		window.sampno={
			sampno:record.get("sampno"),
			sampnm:record.get("sampnm")
		};//当前选中的设计样衣编号
		
		if(sampleDesignGrid.sampno==record.get("sampno")){
			return;
		}
		
		sampleDesignGrid.sampno=record.get("sampno");
		
		//tabpanel.mask("正在刷新.....");
		tabpanel.setTitle("编辑样衣:"+record.get("plspnm"));
		samplePlanForm.loadRecord(record);
		tabpanel.unmask();
		tabpanel.items.getAt(2).enable();
		tabpanel.items.getAt(3).enable();
		tabpanel.items.getAt(4).enable();
		
		//更新设计开发，数据
		y.sample.SampleDesign.load(record.get("sampno"), {
		    success: function(sampleDesign) {
		    	//console.log(sampleDesign);
		       sampleDesign.set("plspnm",record.get("plspnm"));
		       sampleDesignForm.loadRecord(sampleDesign);
		    }
		});
		
		//面料信息,并且默认选中第一行
		sampleMateGrid.getStore().getProxy().extraParams={
			sampno:window.sampno.sampno
		};
		sampleMateGrid.getStore().reload();
		
		//成衣信息
		y.sample.SampleColth.load(record.get("sampno"), {
		    success: function(sampleColth) {
		    	//console.log(sampleDesign);
		       //sampleDesign.set("plspnm",record.get("plspnm"));
		       sampleColthForm.loadRecord(sampleColth);
		    }
		});
		
		//产品图片
		samplePhotoShow.getStore().getProxy().extraParams={
			sampno:window.sampno.sampno
		};
		//samplePhotoShow.down("#samplePhotoView").refresh( );
		samplePhotoShow.getStore().reload();
		
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[sampleDesignGrid,tabpanel]
	});



});