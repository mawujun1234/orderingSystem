Ext.require("y.cg.CgOrdhd");
Ext.require("y.cg.CgOrdhdGrid");
Ext.require("y.cg.CgOrdhdForm");
Ext.onReady(function(){
	var cgOrdhdGrid=Ext.create('y.cg.CgOrdhdGrid',{
		region:'north',
		split: true,
		collapsible: true,
		height:260
	});
	var cgOrddtlGrid=Ext.create('y.cg.CgOrddtlGrid',{
		region:'center',
		split:true
	});
	cgOrdhdGrid.on("itemclick",function( view , record , item , index , e , eOpts){
		cgOrddtlGrid.getStore().getProxy().extraParams=Ext.apply(cgOrddtlGrid.getStore().getProxy().extraParams,{
			"params['ormtno']":cgOrdhdGrid.getStore().getProxy().extraParams["params['ormtno']"],
			"params['bradno']":cgOrdhdGrid.getStore().getProxy().extraParams["params['bradno']"],
			"params['spclno']":record.get("spclno"),
			"params['orcgno']":cgOrdhdGrid.getStore().getProxy().extraParams["params['orcgno']"],
			"params['cgorno']":record.get("cgorno")
		});
		cgOrddtlGrid.getStore().reload();
		cgOrddtlGrid.CgOrdhd_orstat=record.get("orstat");
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[cgOrdhdGrid,cgOrddtlGrid]
	});



});