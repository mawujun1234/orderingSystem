Ext.require("y.pubsize.PubSize");
Ext.require("y.pubsize.PrdpGrid");
Ext.require("y.pubsize.PrdpForm");
Ext.require('y.pubsize.PrdsztyPrdpkGrid');
Ext.require('y.pubsize.PrdsztyStdszGrid');
Ext.require('y.pubsize.PrdsztyGrid');
Ext.onReady(function(){
	//现在变成了规格系列
	var grid=Ext.create('y.pubsize.PrdsztyGrid',{
		region:'center'
	});
	window.prdsztyGrid=grid;
	
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
	
	
	
	grid.on("itemclick",function(view, record, item, index, e, eOpts){
		tabpanel.unmask();

		tabpanel.items.getAt(0).enable();
	    tabpanel.items.getAt(1).enable();
	    tabpanel.items.getAt(2).disable();
	    tabpanel.items.getAt(3).disable();
	    tabpanel.setActiveTab(0);  
		
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
		
		sizeGrid.unmask();
		sizeGrid.getStore().getProxy().extraParams={
			ysizety:record.get("sizety"),
			ysizeno:record.get("sizeno")
		}
		sizeGrid.reload();
		
	});
	
	//规格范围
	var sizeGrid=Ext.create('y.pubsize.SizeGrid',{
		region:"south",
		height:350,
		listeners:{
			render:function(panel){
				panel.mask();
			}
		}
	});
	window.sizeGrid=sizeGrid;
	sizeGrid.on("itemclick",function(view, record, item, index, e, eOpts){
//		//tabpanel.remove(prdsztyStdszGrid);
//		//tabpanel.remove(prdsztyPrdpkGrid);
//		tabpanel.removeAll (false);
//		tabpanel.add(sizeDtlGrid_STDSZ);
//		tabpanel.add(sizeDtlGrid_PRDPK);
//		tabpanel.setActiveTab(sizeDtlGrid_STDSZ);  
		
		//tabpanel.items.getAt(0).disable();
	    //tabpanel.items.getAt(1).disable();
	    tabpanel.items.getAt(2).enable();
	    tabpanel.items.getAt(3).enable();
	    tabpanel.setActiveTab(2);  
		
		sizeDtlGrid_STDSZ.show();
		sizeDtlGrid_STDSZ.getStore().getProxy().extraParams={
			sizety:'STDSZ',
			fszty:record.get("sizety"),
			fszno:record.get("sizeno"),
			ormtno:record.get("ormtno")
		}
		sizeDtlGrid_STDSZ.getStore().reload();
		
		sizeDtlGrid_PRDPK.show();
		sizeDtlGrid_PRDPK.getStore().getProxy().extraParams={
			sizety:'PRDPK',
			fszty:record.get("sizety"),
			fszno:record.get("sizeno"),
			ormtno:record.get("ormtno")
		}
		sizeDtlGrid_PRDPK.getStore().reload();
	});
	
	var sizeDtlGrid_STDSZ=Ext.create("y.pubsize.SizeDtlStdszGrid",{
		//hidden:true,
		title:'单规规格(范围)'
	});
	
	var sizeDtlGrid_PRDPK=Ext.create("y.pubsize.SizeDtlPrdpkGrid",{
		//hidden:true,
		title:'包装规格(范围)'
	});
	
	var tabpanel=Ext.create('Ext.tab.Panel',{
		region:'east',
		autoDestroy:false,//不让它销毁  
		split:true,
		//autoRender:true,
		width:350,
		items:[prdsztyStdszGrid,prdsztyPrdpkGrid,sizeDtlGrid_STDSZ,sizeDtlGrid_PRDPK],
		listeners:{
			render:function(){
				tabpanel.mask();
			}
		}
	});
	//tabpanel.add(sizeDtlGrid_STDSZ);
	//tabpanel.add(sizeDtlGrid_PRDPK);
	//tabpanel.items.getAt(2).disable();
	//tabpanel.items.getAt(3).disable();
	
	
	var centerPanel=Ext.create("Ext.panel.Panel",{
		region:'center',
		layout:'border',
		items:[grid,sizeGrid]
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[centerPanel,tabpanel]
	});



});