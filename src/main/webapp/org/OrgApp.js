Ext.require("y.org.Org");
Ext.require("y.org.OrgTree");
Ext.require("y.org.OrgForm");
Ext.onReady(function(){
	var tree=Ext.create('y.org.OrgTree',{
		title:'组织维护',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});
	var usergrid=Ext.create('y.org.UserGrid',{
		title:'用户维护',
		region:'center',
		listeners:{
	    	render:function(){
	    		usergrid.mask();
	    	}
	    }
	
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,usergrid]
	});
	
	tree.on("itemclick",function( view, record, item, index, e, eOpts ){
		if(record.get("type")=="org" || !record.get("type")){
			usergrid.mask();
			return;
		} else {
			usergrid.unmask();
		}

		window.selected_position=record;
		usergrid.getStore().getProxy().extraParams=Ext.apply(usergrid.getStore().getProxy().extraParams,{
			position_id:record.get("id"),
			orgno:record.get("orgno")
		});
		usergrid.getStore().reload();

		
	});



});