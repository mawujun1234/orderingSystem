Ext.define('y.sample.SamplePlanStprForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.sample.SamplePlanStpr'
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
	        fieldLabel: '套件',
	        name: 'suitno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"套件不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'3'
	    },
		{
	        fieldLabel: '出厂价',
	        name: 'spftpr',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"出厂价不允许为空",
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '零售价',
	        name: 'sprtpr',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"零售价不允许为空",
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '企划倍率',
	        name: 'spplrd',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"企划倍率不允许为空",
            selectOnFocus:true,
            hidden:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '企划成本价',
	        name: 'plctpr',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"企划成本价不允许为空",
	         hidden:true,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '企划样衣代码',
	        name: 'plspno',
            hidden:true,
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '创建人',
	        name: 'rgsp',
            hidden:true,
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
            fieldLabel: '创建日期',
            name: 'rgdt',
            hidden:true,
            editable:false,
            xtype: 'datefield',
            format: 'Y-m-d'   
        },
		{
	        fieldLabel: '修改人',
	        name: 'lmsp',
            hidden:true,
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
            fieldLabel: '修改日期',
            name: 'lmdt',
            hidden:true,
            editable:false,
            xtype: 'datefield',
            format: 'Y-m-d'   
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
				var suitno=formpanel.getForm().findField("suitno");
				formpanel.getRecord().set("suitno_name",suitno.getRawValue());
				button.up('window').close();
//				button.up('form').getForm().getRecord().save({
//					failure: function(record, operation) {
//				    },
//				    success: function(record, operation) {
//						button.up('window').close();
//				    }
//				});			
				
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
