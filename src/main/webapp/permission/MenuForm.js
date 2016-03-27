Ext.define('y.permission.MenuForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.permission.Menu'
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
	        fieldLabel: '菜单名称',
	        //afterLabelTextTpl: Ext.required,
	        name: 'name',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"菜单名称不允许为空",
	        xtype:'textfield'
	    },
		{
			fieldLabel: '菜单类型',
			name: 'menuType',
			queryMode: 'local',
			editable:false,
			forceSelection:true,
		    displayField: 'name',
		    valueField: 'key',
		    store: {
			    fields: ['key', 'name'],
			    data : [
			    	{"key":"menu", "name":"菜单"},
			    	{"key":"element", "name":"界面元素"}
			    ]
			},
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"菜单类型不允许为空",
			xtype:'combobox'
		},
        {
        	fieldLabel: '叶子节点',
            name:'leaf',
            xtype: 'checkbox',
            cls: 'x-grid-checkheader-editor'
        },
		{
            fieldLabel: '创建时间',
            name: 'createDate',
            xtype: 'datefield',
            format: 'Y-m-d H:i:s'   
        },
		{
	        fieldLabel: '地址',
	        //afterLabelTextTpl: Ext.required,
	        name: 'url',
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
	    },
		{
	        fieldLabel: '父id',
	        //afterLabelTextTpl: Ext.required,
	        name: 'parent_id',
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
