Ext.define('y.sample.SampleCldtlForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.sample.SampleCldtl'
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
	        fieldLabel: '搭配代码',
	        name: 'clppno',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
	    {
	        fieldLabel: '订货会编号',
	        name: 'ormtno',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '品牌代码',
	        name: 'bradno',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '订货样衣编号',
	        name: 'sampno',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
	    {
	        fieldLabel: '样衣编号',
	        name: 'sampnm',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '搭配样衣序号',
	        name: 'clspno',
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '搭配说明',
	        name: 'clspmk',
            selectOnFocus:true,
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
