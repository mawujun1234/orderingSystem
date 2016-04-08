Ext.require("y.pubcode.PubCodeType");
Ext.require("y.pubcode.PubCodeTypeTree");
Ext.require("y.pubcode.PubCodeTypeForm");
Ext.onReady(function(){
	var tree=Ext.create('y.pubcode.PubCodeTypeTree',{
		title:'树',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,{region:'center',html:"请填写对应的内容!"}]
	});



});