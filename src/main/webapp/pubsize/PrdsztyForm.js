Ext.define('y.pubsize.PrdsztyForm',{
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
	        value:'PRDSZTY',
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
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            //value:'Y',
            blankText:"品牌不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'1'
	    },
	    {
	        fieldLabel: '大类',
	        name: 'szclno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"大类不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'0'
//	        listeners:{
//	        	select:function( combo, record, eOpts ) {
//	        		var sptyno=combo.nextSibling("pubcodecombo[name=sptyno]");
//	        		sptyno.reload(record.get("itno"));
//	        		
//	        		var spseno=combo.nextSibling("pubcodecombo[name=spseno]");
//	        		spseno.reload(record.get("itno"));
//	        	}
//	        }
	    },
//	    {
//            xtype      : 'fieldcontainer',
//            fieldLabel : '包装种类',
//            defaultType: 'radiofield',
//            defaults: {
//                flex: 1
//            },
//            layout: 'hbox',
//            items: [
//            	{
//                    boxLabel  : '单规装',
//                    name: 'sizety1',
//                     checked:true,
//                    inputValue: '0'
//                },
//           		{
//                    boxLabel  : '混装',
//                    name: 'sizety1',
//                    inputValue: '1'
//                }
//            ]
//        },
//		{
//	        fieldLabel: '数量',
//	        name: 'sizeqt',
//	        // hidden:true,
//            allowDecimals:false,
//            selectOnFocus:true,
//	        xtype:'numberfield'   
//	    },
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
            items: [
            	{
                    boxLabel  : '作废',
                    name: 'sizest',
                    
                    inputValue: '0'
                },
           		{
                    boxLabel  : '有效',
                    name: 'sizest',
                    checked:true,
                    inputValue: '1'
                }
            ]
        },
        {
            xtype      : 'fieldcontainer',
            fieldLabel : '当季状态',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
            	{
                    boxLabel  : '非当季',
                    name: 'szsast',
                    inputValue: '0'
                },
           		{
                    boxLabel  : '当季',
                    name: 'szsast',
                    checked:true,
                    inputValue: '1'
                }
            ]
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
