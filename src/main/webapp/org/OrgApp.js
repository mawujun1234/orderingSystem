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
		region:'center'
	});
	
	var orgAccessTree=Ext.create("y.org.OrgAccessTree",{
		title:'可访问的组织单元'
	});
	
	var tabpanel=Ext.create('Ext.tab.Panel',{
		region:'center',
		items:[usergrid,orgAccessTree],
		listeners:{
	    	render:function(tabpanel){
	    		tabpanel.mask();
	    	}
	    }
	});
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,tabpanel]
	});
	
	tree.on("itemclick",function( view, record, item, index, e, eOpts ){
		//alert(record.get("type"));
		if(record.get("type")=="position"){		
			tabpanel.unmask();
		} else {
			tabpanel.mask();
			return;
		}

		window.selected_position=record;
		usergrid.getStore().getProxy().extraParams=Ext.apply(usergrid.getStore().getProxy().extraParams,{
			position_id:record.get("id"),
			orgno:record.get("orgno")
		});
		usergrid.getStore().reload();

		//刷新整颗权限树
		orgAccessTree.getStore().getProxy().extraParams=Ext.apply(orgAccessTree.getStore().getProxy().extraParams,{
			position_id:record.get("id")
		});
		orgAccessTree.getStore().reload({node:orgAccessTree.getRootNode()});
	});



});