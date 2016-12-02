Ext.define('y.plan.PlanClsForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.plan.PlanCls'
	],
	
    frame: true,
    autoScroll : true,
	buttonAlign : 'center',
    bodyPadding: '5 5 0',


    defaults: {
        msgTarget: 'under',
        labelWidth: 75,
        labelAlign:'right',
        anchor: '90%'
    },
	initComponent: function () {
       var me = this;
       me.items= [
		{
	        fieldLabel: '订货批号',
	        name: 'ormtno',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '品牌',
	        name: 'bradno',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '大类',
	        name: 'spclno',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '系列',
	        name: 'spseno',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '起订量',
	        name: 'ordqty',
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
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
				formpanel.updateRecord();
				formpanel.getForm().getRecord().save({
					failure: function(record, operation) {
				    },
				    success: function(record, operation) {
						button.up('window').close();
				    }
				});			
				
				}
			},{
				text : '关闭',
				itemId : 'close',
				glyph : 0xf00d,
				handler : function(button){
					button.up('window').close();
				}
	    });
      me.callParent();
	}
});
