Ext.require("y.order.OrdChgdtl");
Ext.require("y.order.OrdChgdtlGrid");
Ext.require("y.order.OrdChgdtlCompGrid");
Ext.require("y.order.OrdChgdtlRegnGrid");
Ext.onReady(function(){
	var grid=Ext.create('y.order.OrdChgdtlGrid',{
		region:'center'
	});
	
	var ordChgdtlCompGrid=Ext.create('y.order.OrdChgdtlCompGrid',{
		flex:1
	});
	
	var ordChgdtlRegnGrid=Ext.create('y.order.OrdChgdtlRegnGrid',{
		split:true,
		itemId:'ordChgdtlRegnGrid',
		flex:1
	});
	
	grid.on('itemclick',function(view , record , item , index , e , eOpts) {
		var params=Ext.apply({},grid.getStore().getProxy().extraParams);
		params["params['sampno']"]=record.get("sampno");
		params["params['suitno']"]=record.get("suitno");
		ordChgdtlCompGrid.getStore().getProxy().extraParams=params;
		ordChgdtlCompGrid.getStore().reload();
		
		ordChgdtlRegnGrid.mask();
		grid.nextSibling("#south_panel").unmask();
	});
	
	ordChgdtlCompGrid.on('itemclick',function(view , record , item , index , e , eOpts) {
		var params=Ext.apply({},ordChgdtlCompGrid.getStore().getProxy().extraParams);
		params["params['compno']"]=record.get("compno");
		ordChgdtlRegnGrid.getStore().getProxy().extraParams=params;
		ordChgdtlRegnGrid.getStore().reload();
		
		ordChgdtlRegnGrid.unmask();
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,{
			region:'south',
			itemId:'south_panel',
			height:300,
			split:true,
			layout:{
               type : 'hbox',
               padding : '5',
               align : 'stretch'
            },
			items:[ordChgdtlCompGrid,ordChgdtlRegnGrid]
		}]
	});



});