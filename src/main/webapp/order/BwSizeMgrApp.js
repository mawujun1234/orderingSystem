
Ext.onReady(function(){
	var bwOrdhdGrid=Ext.create('y.order.BwOrdhdGrid',{
		region:'center',
		title:'子订单'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[bwOrdhdGrid]
	});



});