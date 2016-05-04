Ext.require("y.pubsize.PubSize");
Ext.require("y.pubsize.PrdpGrid");
Ext.require("y.pubsize.PrdpForm");
Ext.onReady(function(){
	var grid=Ext.create('y.pubsize.PrdpGrid',{
		region:'center'
	});
	
	var prdpStdszGrid=Ext.create('y.pubsize.PrdpStdszGrid',{
		region:'east',
		split:true,
		width:350,
		listeners:{
			render:function(){
				prdpStdszGrid.mask();
			}
		}
	});
	
	grid.on("itemclick",function(view, record, item, index, e, eOpts){
		prdpStdszGrid.unmask();
		prdpStdszGrid.getStore().getProxy().extraParams={
			fszno:record.get("sizeno")
		};
		prdpStdszGrid.getStore().reload();
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,prdpStdszGrid]
	});



});