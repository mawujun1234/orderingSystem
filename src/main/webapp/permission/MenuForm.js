Ext.define('y.permission.MenuForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.permission.Menu'
	],
	
    frame: true,
    autoScroll : true,
	buttonAlign : 'center',
    bodyPadding: '5 5 0',


    layout: {
        type: 'table',
        columns: 2
    },
    defaults: {
    	msgTarget: 'under',
        labelWidth: 75,
        labelAlign:'right',
        width: '90%'
    },
	initComponent: function () {
       var me = this;
       me.items= [
		{
	        fieldLabel: '菜单名称',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name',
	        xtype:'textfield'
	       
	    },

		{
	        fieldLabel: '地址',
	        //afterLabelTextTpl: Ext.required,
	        name: 'url',
	        xtype:'textfield'
	       
	    },

		{
	        fieldLabel: '菜单类型',
	        //afterLabelTextTpl: Ext.required,
	        name: 'menuType',
	        xtype:'textfield'
	       
	    },

		{
	        fieldLabel: '备注',
	        //afterLabelTextTpl: Ext.required,
	        name: 'remark',
	        xtype:'textfield'
	       
	    },

		{
	        fieldLabel: 'id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'id',
            hidden:true,
	        xtype:'textfield'
	       
	    }

	  ];   
	  
	  
	  this.buttons = [];
		this.buttons.push({
			text : '保存',
			itemId : 'save',
			formBind: true, //only enabled once the form is valid
       		disabled: true,
			glyph : 0xf0c7,
			handler : function(button){
				var formpanel = button.up('form');
				//button.up('form').updateRecord();
				//button.up('form').getForm().getRecord().save();
				var form = formpanel.getForm();
	            if (form.isValid()) {
	                form.submit({
	                    success: function(form, action) {
	                       Ext.Msg.alert('Success', action.result.msg);
	                    },
	                    failure: function(form, action) {
	                        Ext.Msg.alert('Failed', action.result.msg);
	                    }
	                });
	            }				
				
				}
			},{
				text : '关闭',
				itemId : 'close',
				glyph : 0xf00d,
				handler : function(button){
					button.up('window').hide();
				}
	    });
      me.callParent();
	}
});
