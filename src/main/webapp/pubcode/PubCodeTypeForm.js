Ext.define('y.pubcode.PubCodeTypeForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.pubcode.PubCodeType'
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
	        fieldLabel: '类型代码',
	        name: 'tyno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"类型代码不允许为空",
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '类型名称',
	        name: 'tynm',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"类型名称不允许为空",
	        xtype:'textfield'
	    },
        {
            xtype      : 'fieldcontainer',
            fieldLabel : '状态',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
           		{
                    boxLabel  : 'true',
                    name: 'tyst',
                    inputValue: 'true'
                },{
                    boxLabel  : 'false',
                    name: 'tyst',
                    inputValue: 'false'
                }
            ]
        },
		
		{
	        fieldLabel: '创建人',
	        name: 'rgsp',
	        xtype:'textfield'
	    },
		{
            fieldLabel: '创建日期',
            name: 'rgdt',
            xtype: 'datefield',
            format: 'Y-m-d H:i:s'   
        },
		{
	        fieldLabel: '修改人',
	        name: 'lmsp',
	        xtype:'textfield'
	    },
		{
            fieldLabel: '修改日期',
            name: 'lmdt',
            xtype: 'datefield',
            format: 'Y-m-d H:i:s'   
        },
		{
	        fieldLabel: '上级代码',
	        name: 'ftyno',
            hidden:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '描述',
	        name: 'tyms',
            hidden:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '备注',
	        name: 'tymk',
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
				button.up('form').updateRecord();
				button.up('form').getForm().getRecord().save({
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
