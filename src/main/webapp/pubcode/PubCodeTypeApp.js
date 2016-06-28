Ext.require("y.pubcode.PubCodeType");
Ext.require("y.pubcode.PubCodeTypeTree");
Ext.require("y.pubcode.PubCodeTypeForm");
Ext.onReady(function(){
	var pubCodeTypeTree=Ext.create('y.pubcode.PubCodeTypeTree',{
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});
	
	var pubCodeGrid=Ext.create('y.pubcode.PubCodeGrid',{
		region:'center',
		listeners:{
			render:function(panel){
				panel.mask();
			}
		}
	});
	
	pubCodeTypeTree.on("itemclick",function(view , record , item , index , e , eOpts){
		pubCodeGrid.removeParentCombo();
		var parentCombo=null;
		
		pubCodeGrid.getStore().getProxy().extraParams={
			tyno:record.get("tyno"),
			bradno:pubCodeTypeTree.getStore().getProxy().extraParams.bradno,
			fitno:parentCombo?parentCombo.getValue():null
		}
		pubCodeGrid.unmask();
		if(record.get("ftyno")){
			var parent=record.parentNode;
			parentCombo=pubCodeGrid.createParentCombo(parent.get("tyno"),parent.get("tynm"));
		} else {
			pubCodeGrid.getStore().reload();
		}
		
	});
	pubCodeTypeTree.pubCodeGrid=pubCodeGrid;
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[pubCodeTypeTree,pubCodeGrid]
	});



});