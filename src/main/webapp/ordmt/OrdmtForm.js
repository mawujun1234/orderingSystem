Ext.define('y.ordmt.OrdmtForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.ordmt.Ordmt'
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
	        fieldLabel: '订货会批号',
	        name: 'ormtno',
            allowBlank: false,
            editable:false,
            afterLabelTextTpl: Ext.required,
            blankText:"订货会批号不允许为空",
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '订货会名称',
	        name: 'ormtnm',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"订货会名称不允许为空",
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '订货会简称',
	        name: 'ormtsn',
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '产品年份',
	        name: 'pryear',
            allowDecimals:false,
	        xtype:'numberfield'   
	    },
		{
            fieldLabel: '开始日期',
            name: 'mtstdt',
            xtype: 'datefield',
            format: 'Y-m-d'   
        },
		{
            fieldLabel: '结束日期',
            name: 'mtfidt',
            xtype: 'datefield',
            format: 'Y-m-d'   
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
                    boxLabel  : '进行中',
                    name: 'ormtst',
                     checked:true,
                    inputValue: 'false'
                },
           		{
                    boxLabel  : '完成',
                    name: 'ormtst',
                    inputValue: 'true'
                }
            ]
        },
		
        {
            xtype      : 'fieldcontainer',
            fieldLabel : '跟踪状态',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
            	{
                    boxLabel  : '未跟踪',
                    name: 'ormtfg',
                    
                    inputValue: 'false'
                },
           		{
                    boxLabel  : '跟踪',
                     checked:true,
                    name: 'ormtfg',
                    inputValue: 'true'
                }
            ]
        },
		{
            xtype      : 'checkboxgroup',
             columns: 3,
        	vertical: true,
            fieldLabel : '季节',
            itemId:"seasnos",
            defaultType: 'checkboxfield',
            defaults: {
                flex: 1,
                margin:"0 10 0 0"
            },
//            layout: {
//            	type: 'checkboxgroup',
//            	columns:3
//            },
            items: [
            	{
                    boxLabel  : '季节1',
                    name: 'seasno',
                    inputValue: '11'
                },
           		{
                    boxLabel  : '季节2',
                    name: 'seasno',
                    inputValue: '22'
                },
                {
                    boxLabel  : '季节3',
                    name: 'seasno',
                    inputValue: '33'
                },
                {
                    boxLabel  : '季节4',
                    name: 'seasno',
                    inputValue: '44'
                }
            ]
        },
		{
	        fieldLabel: '备注',
	        name: 'ormtmk',
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '创建人',
	        name: 'mtrgsp',
	        xtype:'textfield',
	        hidden:true
	    },
		{
            fieldLabel: '创建日期',
            name: 'mtrgdt',
            xtype: 'datefield',
            format: 'Y-m-d H:i:s' ,
            hidden:true
        },
		{
	        fieldLabel: '修改人',
	        name: 'mtlmsp',
	        xtype:'textfield',
	        hidden:true
	    },
		{
            fieldLabel: '修改日期',
            name: 'mtlmdt',
            xtype: 'datefield',
            format: 'Y-m-d H:i:s'  ,
            hidden:true
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
				
				var seasnos=formpanel.getComponent("seasnos").getValue();
				//alert(seasnos.length);
				//console.log(aa);
				button.up('form').updateRecord();
				button.up('form').getForm().getRecord().save({
					params:{seasnos:seasnos },
					failure: function(record, operation) {
				    },
				    success: function(record, operation) {
				    	//record.set("seasnos",seasnos);
				    	
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
