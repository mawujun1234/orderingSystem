/**
 *  co从职位上选择用户
 */
Ext.define('y.permission.SelUserWindow',{
	extend:'Ext.window.Window',
	requires: [

	],
	layout:'fit',
    title:'双击选择用户',
    modal:true,
    width:700,
    height:500,
    closeAction:'hide',
    initComponent: function () {
        var me = this;
        var usergrid=Ext.create('y.permission.UserGridQuery',{
        	listeners:{
		    	itemdblclick:function(view, record, item, index, e, eOpts){
		    		//usergrid.getStore().remove(record);
		    		usergrid.getStore().reload();
		    	    me.fireEvent("userdbclick",record);
		    	}
		    }
        });
		me.items=[usergrid];
		
		//me.addEvents("userdbclick");
        me.callParent();
    }
	
	
});