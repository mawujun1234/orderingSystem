Ext.require("y.cg.CgOrdhd");
Ext.require("y.cg.CgOrdhdGrid");
Ext.require("y.cg.CgOrdhdForm");
Ext.onReady(function(){
	var cgOrdhdGrid=Ext.create('y.cg.CgOrdhdGrid',{
		region:'west',
		split: true,
		collapsible: true,
		width:450
	});
	var cgOrddtlGrid=Ext.create('y.cg.CgOrddtlGrid',{
		region:'center',
		split:true
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[cgOrdhdGrid,cgOrddtlGrid]
	});



});