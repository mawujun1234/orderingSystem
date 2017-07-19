Ext.define('y.order1.OrdOrddtlForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.order1.OrdOrddtl'
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
	        fieldLabel: '订货会编号',
	        name: 'ormtno',
            selectOnFocus:true,
	        xtype:'hidden'
	    },
		{
	        fieldLabel: '套件',
	        name: 'suitno',
            selectOnFocus:true,
	        xtype:'hidden'
	    },
		{
	        fieldLabel: '下单序号',
	        name: 'ordseq',
            selectOnFocus:true,
	        xtype:'hidden'
	    },
		{
            fieldLabel: '下单日期',
            name: 'ordate',
            editable:false,
            allowBlank:false,
            afterLabelTextTpl: Ext.required,
            xtype: 'datefield',
            format: 'Y-m-d'   
        },
		{
	        fieldLabel: '下单数量',
	        name: 'orodqt',
            allowDecimals:false,
            allowBlank:false,
            afterLabelTextTpl: Ext.required,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '备注',
	        name: 'ormark',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '设计样衣代码',
	        name: 'sampno',
            hidden:true,
            selectOnFocus:true,
	        xtype:'hidden'
	    },
		{
	        fieldLabel: '创建人',
	        name: 'rgsp',
            hidden:true,
            selectOnFocus:true,
	        xtype:'hidden'
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
				formpanel.getForm().getRecord().set("ordate",formpanel.getForm().findField("ordate").getRawValue());
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
