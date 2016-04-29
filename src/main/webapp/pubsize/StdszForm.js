Ext.define('y.pubsize.StdszForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.pubsize.PubSize'
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
	        fieldLabel: '规格类型',
	        name: 'sizety',
	        hidden:true,
	        value:'STDSZ',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '规格代码',
	        name: 'sizeno',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '规格名称',
	        name: 'sizenm',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '品牌',
	        name: 'szbrad',
	        hidden:true,
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '大类',
	        name: 'szclno',
	         hidden:true,
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '数量',
	        name: 'sizeqt',
	         hidden:true,
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '备注',
	        name: 'sizemk',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '排序',
	        name: 'sizeso',
	         hidden:true,
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
	    {
            xtype      : 'fieldcontainer',
            fieldLabel : '状态',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            value:'1',
            items: [
            	{
                    boxLabel  : '作废',
                    name: 'sizest',
                     checked:true,
                    inputValue: '0'
                },
           		{
                    boxLabel  : '有效',
                    name: 'sizest',
                    inputValue: '1'
                }
            ]
        },
		{
	        fieldLabel: '当季状态',
	        name: 'szsast',
	        hidden:true,
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
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
