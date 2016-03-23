<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
Ext.require("y.${module}.${simpleClassName}");
Ext.require("y.${module}.${simpleClassName}Grid");
Ext.require("y.${module}.${simpleClassName}Tree");
Ext.require("y.${module}.${simpleClassName}Form");
Ext.onReady(function(){
	var grid=Ext.create('y.${module}.${simpleClassName}Grid',{
		region:'west',
		split: true,
		collapsible: true,
		title:'XXX表格',
		width:400
	});

	var tree=Ext.create('y.${module}.${simpleClassName}Tree',{
		title:'树',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});

	var form=Ext.create('y.${module}.${simpleClassName}Form',{
		region:'center',
		split: true,
		//collapsible: true,
		title:'表单',
		listeners:{
			saved:function(){
				grid.getStore().reload();
			}
		}
	});
	grid.form=form;
	form.grid=grid;
	grid.on('itemclick',function(view,record,item,index){
		//var basicForm=form.getForm();
		form.loadRecord(record);
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tree,form]
	});

});