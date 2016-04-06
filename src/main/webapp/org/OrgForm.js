Ext.define('y.org.OrgForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.org.Org'
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
	        fieldLabel: '组织代码',
	        name: 'orgno',
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '组织名称',
	        name: 'orgnm',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"组织名称不允许为空",
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '组织短名称',
	        name: 'orgsn',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"组织短名称不允许为空",
	        xtype:'textfield'
	    },
		{
			fieldLabel: '组织类型',
			name: 'orgty',
			queryMode: 'local',
			editable:false,
			forceSelection:true,
		    displayField: 'name',
		    valueField: 'key',
		    store: {
			    fields: ['key', 'name'],
			    data : [
			    	{"key":"SHOP", "name":"门店"},
			    	{"key":"COMP", "name":"公司"},
			    	{"key":"OTH", "name":"其它"},
			    	{"key":"AREA", "name":"片区"},
			    	{"key":"CK", "name":"仓库"},
			    	{"key":"BRCH", "name":"分公司"},
			    	{"key":"DEPT", "name":"部门"},
			    	{"key":"REGN", "name":"区域"}
			    ]
			},
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"组织类型不允许为空",
			xtype:'combobox'
		},
		{
			fieldLabel: '渠道类型',
			name: 'chancl',
			queryMode: 'local',
			editable:false,
			forceSelection:true,
		    displayField: 'name',
		    valueField: 'key',
		    store: {
			    fields: ['key', 'name'],
			    data : [
			    	{"key":"SC", "name":"商场"},
			    	{"key":"WX", "name":"网销"},
			    	{"key":"PQ", "name":"片区"},
			    	{"key":"YXGS", "name":"营销公司"},
			    	{"key":"FGS", "name":"分公司"},
			    	{"key":"TX", "name":"特许"},
			    	{"key":"QY", "name":"区域"},
			    	{"key":"CK", "name":"仓库"},
			    	{"key":"GSBB", "name":"公司本部"},
			    	{"key":"FS", "name":"服饰"},
			    	{"key":"ZY", "name":"自营"}
			    ]
			},
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"渠道类型不允许为空",
			xtype:'combobox'
		},
		{
			fieldLabel: '状态',
			name: 'orgst',
			queryMode: 'local',
			editable:false,
			forceSelection:true,
		    displayField: 'name',
		    valueField: 'key',
		    store: {
			    fields: ['key', 'name'],
			    data : [
			    	{"key":"GB", "name":"关闭"},
			    	{"key":"DKQ", "name":"待开启"},
			    	{"key":"KQ", "name":"开启"}
			    ]
			},
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"状态不允许为空",
			xtype:'combobox'
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
