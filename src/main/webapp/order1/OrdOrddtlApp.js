Ext.require("y.sample.SampleProd");
Ext.require("y.order1.OrdOrddtlQueryGrid");
Ext.require("y.order1.OrdOrddtlGrid");
Ext.onReady(function(){
	var grid=Ext.create('y.order1.OrdOrddtlQueryGrid',{
		region:'center'
		//title:'XXX表格'
	});
	
	
	var ordOrddtlGrid=Ext.create('y.order1.OrdOrddtlGrid',{
		
		//id:'sampleProdForm',
		//itemId:'sampleProdForm',
		split:true,
	    //collapsible : true,
	    //collapsed :true,
	    region:'south',
	    height:260,
	    listeners:{
	    	render:function(){
	    		ordOrddtlGrid.mask();
	    	}
	    }
	});
	
	grid.on("itemclick",function(view, record, item, index, e, eOpts){
		ordOrddtlGrid.unmask();
		//ordOrddtlGrid.expand();
		
		ordOrddtlGrid.getStore().getProxy().extraParams={
			sampno:record.get("sampno"),
			suitno:record.get("suitno"),
			ormtno:grid.getStore().getProxy().extraParams["params['ormtno']"]
		}
		ordOrddtlGrid.getStore().reload();
		//sampleProdForm.getForm().loadRecord(record);
	});
	//ordOrddtlGrid.grid=grid;
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,ordOrddtlGrid]
	});



});