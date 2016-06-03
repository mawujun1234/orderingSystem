Ext.require("y.ordmt.OrdOrg");
Ext.require("y.ordmt.OrdOrgGrid");
Ext.require("y.ordmt.OrdOrgForm");
Ext.onReady(function(){
	var grid=Ext.create('y.ordmt.OrdOrgGrid',{
		region:'center'
		//title:'XXX表格'
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});