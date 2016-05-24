Ext.require("y.ordmt.Ordmt");
Ext.require("y.ordmt.OrdmtGrid");
Ext.require("y.ordmt.OrdmtForm");
Ext.require('y.ordmt.OrdmtScdeGrid');
Ext.require('y.ordmt.OrdmtScdeForm');
Ext.onReady(function(){
	var ordmtGrid=Ext.create('y.ordmt.OrdmtGrid',{
		region:'center'
		//title:'订货会定义'
	});
	
	
	var ordmtScdeGrid=Ext.create('y.ordmt.OrdmtScdeGrid',{
		region:'south',
		height:200,
		split:true,
		//title:'订货日程',
		listeners:{
			render:function(grid){
				ordmtScdeGrid.mask();
			}
		}
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[ordmtGrid,ordmtScdeGrid]
	});

	
	ordmtGrid.on("itemclick",function(view, record, item, index, e, eOpts ){
		ordmtScdeGrid.unmask();
		
		ordmtScdeGrid.ormtno=record.get("ormtno");
		ordmtScdeGrid.getStore().getProxy().extraParams={
			ormtno:record.get("ormtno")
		}
		ordmtScdeGrid.getStore().reload();
	});


});