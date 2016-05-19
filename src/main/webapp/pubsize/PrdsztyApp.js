Ext.require("y.pubsize.PubSize");
Ext.require("y.pubsize.PrdpGrid");
Ext.require("y.pubsize.PrdpForm");
Ext.require('y.pubsize.PrdsztyPrdpkGrid');
Ext.require('y.pubsize.PrdsztyStdszGrid');
Ext.require('y.pubsize.PrdsztyGrid');
Ext.onReady(function(){
	var grid=Ext.create('y.pubsize.PrdsztyGrid',{
		region:'center'
	});
	
	var prdsztyStdszGrid=Ext.create('y.pubsize.PrdsztyStdszGrid',{
		title:'单规规格',	
		listeners:{
			render:function(){
				//prdsztyStdszGrid.mask();
			}
		}
	});
	var prdsztyPrdpkGrid=Ext.create('y.pubsize.PrdsztyPrdpkGrid',{
		title:'包装规格',
		listeners:{
			render:function(){
				//prdsztyPrdpkGrid.mask();
			}
		}
	});
	
	var tabpanel=Ext.create('Ext.tab.Panel',{
		region:'east',
		split:true,
		utoRender:true,
		width:350,
		items:[prdsztyStdszGrid,prdsztyPrdpkGrid],
		listeners:{
			render:function(){
				tabpanel.mask();
			}
		}
	});
	
	grid.on("itemclick",function(view, record, item, index, e, eOpts){
		tabpanel.unmask();
		
		prdsztyStdszGrid.getStore().getProxy().extraParams={
			fszty:record.get("sizety"),
			fszno:record.get("sizeno")
		};
		prdsztyStdszGrid.getStore().reload();
		


		prdsztyPrdpkGrid.getStore().getProxy().extraParams={
			fszty:record.get("sizety"),
			fszno:record.get("sizeno")
		};
		prdsztyPrdpkGrid.getStore().reload();
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabpanel]
	});



});