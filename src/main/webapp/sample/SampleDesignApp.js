Ext.require("y.sample.SamplePlan");
Ext.require("y.sample.SamplePlanGrid");
Ext.require("y.sample.SamplePlanForm");
Ext.require('y.sample.SampleDesignStprGrid');
Ext.require('y.sample.SampleColthForm');
Ext.require('y.sample.SampleDesignGrid');
Ext.require('y.sample.SamplePlanFormQuery');
Ext.require('y.sample.SampleDesignForm');
Ext.require('y.sample.SampleMateGrid');
Ext.require('y.sample.SampleMateForm');
Ext.require('y.sample.SamplePhotoShow');
Ext.require('y.sample.SamplePlanGridQuery');
Ext.require('y.sample.SamplePhotoForm');
Ext.onReady(function(){
	window.stat_xtrydeeeeeeeee="1";
	var sampleDesignGrid=Ext.create('y.sample.SampleDesignGrid',{
		itemId:'sampleDesignGrid',
		region:'center'
		//title:'XXX表格'
	});
	
	var samplePlanForm=Ext.create('y.sample.SamplePlanFormQuery',{
		title:'商品企划',
		id:'samplePlanFormQuery',
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
//	sampleMateGrid.getStore().on("load",function(store,records){
//		if(records.length>0){
//			sampleMateGrid.getSelectionModel( ).select(records[0]);
//			//就是为了显示设计编号，没什么实际用处，如果form中的sampnm去掉，那这个也可以去掉了
//			records[0].set("sampnm",window.sampno.sampnm);
//			sampleMateForm.loadRecord(records[0]);
//		}
//		
//	});
	sampleMateGrid.on("itemclick",function(view, record, item, index, e, eOpts){
		
		sampleMateForm.loadRecord(record);
		var mtsuno_combo=sampleMateForm.down("#mtsuno");
		var mtsuno_model= mtsuno_combo.getStore().createModel({idsuno:record.get("mtsuno"),idsunm:record.get("mtsuno_name")});
		mtsuno_combo.setValue(mtsuno_model);
		sampleMateForm.unmask();
	});
	
	//成衣信息
	var sampleColthForm=Ext.create('y.sample.SampleColthForm',{
		title:'成衣信息',
		itemId:'sampleColthForm'
	});
	var sampleColth=Ext.create('y.sample.SampleColth',{
					sampno:null
	});
	sampleColthForm.getForm().loadRecord(sampleColth);
	window.sampleColthForm=sampleColthForm;
	
	//图片信息
	var samplePhotoShow=Ext.create('y.sample.SamplePhotoShow',{
		title:'产品图片',
		itemId:'samplePhotoShow'
	});
	
	
	var tabpanel=Ext.create('Ext.tab.Panel', {
	    region:'east',
	    width:300,
	    title:'样衣信息',
	    split:true,
	    collapsible : true,
	    collapsed :true,
	    deferredRender:false,
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
		window.sampleDesign=record;
		
		if(record.get("spstat")==1){
			window.stat_xtrydeeeeeeeee=0;
		} else {
			if(window.ordmt_record.get("ormtst")==true){
				window.stat_xtrydeeeeeeeee=0;
			}
		}
//		//用于获取设计样衣的当季属性
		sampleDesignForm.reloadPubcode(record.get("bradno"));
		sampleDesignForm.getSampleDesignSizegpGrid().reloadEditor(record.get("ormtno"),record.get("bradno"),record.get("spclno"));
		
//		if(sampleDesignGrid.sampno==record.get("sampno")){
//			return;
//		}
		
		sampleDesignGrid.sampno=record.get("sampno");
		
		//tabpanel.mask("正在刷新.....");
		tabpanel.setTitle("编辑样衣:"+record.get("plspnm"));
		samplePlanForm.loadRecord(record);
		tabpanel.unmask();
		tabpanel.expand();
		tabpanel.items.getAt(2).enable();
		tabpanel.items.getAt(3).enable();
		tabpanel.items.getAt(4).enable();
		
		//alert(record.get("sptyno"));
		
		//更新设计开发，数据
		sampleDesignForm.reset();
		y.sample.SampleDesign.load(record.get("sampno"), {
			scope: this,
		    success: function(sampleDesign) {
		    	//console.log(sampleDesign);
		       sampleDesign.set("plspnm",record.get("plspnm"));
		       sampleDesignForm.loadRecord(sampleDesign);
		       sampleColthForm.loadGrid(sampleDesign);
		    }
		});
		sampleDesignForm.getForm().findField("sampnm").setReadOnly(true);
		
		//面料信息,并且默认选中第一行
		sampleMateGrid.getStore().getProxy().extraParams={
			sampno:record.get("sampno")
		};
		sampleMateGrid.getStore().reload();
		sampleMateForm.reset();
		sampleMateForm.mask();
		sampleMateForm.lockOrUnlock(record.get("matest"));
		sampleMateGrid.lockOrUnlock(record.get("matest"));
		
		
		
		
		//成衣信息
		sampleColthForm.reset();
		y.sample.SampleColth.load(record.get("sampno"), {
			scope: this,
		    success: function(sampleColth) {
		    	//console.log(sampleDesign);
		       //sampleDesign.set("plspnm",record.get("plspnm"));
		    	//var suitty_field=sampleDesignForm.getForm().findField("suitty");
		    	//alert(sampleColth.get("sprtpr_spftpr"));
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