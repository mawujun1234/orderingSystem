
Ext.onReady(function(){
	var bwOrdhdGrid=Ext.create('y.order.BwOrdhdGrid',{
		region:'west',
		split:true,
		collapsible:true,
		width:350,
		title:'子订单'
	});
	
	var bwOrdhdListGrid=Ext.create('y.order.BwOrdhdListGrid',{
		
		region:'center',
		title:'按样衣编号明细'
	});
	
	bwOrdhdGrid.on("itemclick",function(view,record , item , index , e , eOpts){
		bwOrdhdListGrid.getStore().getProxy().extraParams={
			mmorno:record.get("mmorno")
		}
		bwOrdhdListGrid.getStore().reload();
		bwOrdhdListGrid.unmask();
	});
	bwOrdhdGrid.bwOrdhdListGrid=bwOrdhdListGrid;
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[bwOrdhdGrid,bwOrdhdListGrid]
	});



});