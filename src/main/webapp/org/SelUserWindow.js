Ext.define('y.org.SelUserWindow',{
	extend:'Ext.window.Window',
	requires: [
	     'y.org.OrgTree',
	     'y.org.UserGridQuery'
	],
	layout:'border',
    title:'双击选择用户',
    modal:true,
    width:700,
    height:500,
    closeAction:'hide',
    initComponent: function () {
        var me = this;
        var tree=Ext.create('y.org.OrgTree',{
			//title:'选择组织',
			width:250,
			split:true,
			collapsible : true,
			region:'west'
		});
		var usergrid=Ext.create('y.org.UserGridQuery',{
			//title:'双击选择用户',
			region:'center',
			listeners:{
		    	render:function(){
		    		usergrid.mask();
		    	},
		    	itemdblclick:function(view, record, item, index, e, eOpts){
		    		//usergrid.getStore().remove(record);
		    		usergrid.getStore().reload();
		    	    me.fireEvent("userdbclick",record);
		    	}
		    }
		
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
		me.items=[tree,usergrid];
		
		//me.addEvents("userdbclick");
        me.callParent();
    }
	
	
});