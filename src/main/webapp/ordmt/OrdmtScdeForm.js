Ext.define('y.ordmt.OrdmtScdeForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.ordmt.OrdmtScde'
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
			fieldLabel: '订货单位类型',
			allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"订货单位类型不允许为空",
			name: 'orgty',
			queryMode: 'remote',
			editable:false,
			forceSelection:true,
		    displayField: 'channm',
		    valueField: 'channo',
//		    store: {
//			    fields: ['key', 'name'],
//			    data:[{key:'ZY',name:'自营'},{key:'TX',name:'特许'}]
//			},
		    store: {
			    fields: ['channo', 'channm'],
			    proxy: {
			    	autoLoad:true,
			        type: 'ajax',
			        url: Ext.ContextPath+'/ordmtScde/queryOrgty.do'
//			        reader: {
//			            type: 'json',
//			            rootProperty: 'orgty'
//			        }
			    }
			},
            hidden:false,
			xtype:'combobox'
		},
		{
            fieldLabel: '开始日期',
            name: 'mtstdt',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"开始日期不允许为空",
            xtype: 'datefield',
            format: 'Y-m-d'   
        },
		{
            fieldLabel: '结束日期',
            name: 'mtfidt',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"结束日期不允许为空",
            xtype: 'datefield',
            format: 'Y-m-d'   
        },
		{
	        fieldLabel: '开始时间',
	        name: 'mtsttm',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"开始时间不允许为空",
            format:'H:i',
            submitFormat :'H:i',
            editable:false,
            minValue: '06:00',
        	maxValue: '20:00',
	        xtype:'timefield'
	    },
		{
	        fieldLabel: '结束时间',
	        name: 'mtfitm',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"结束时间不允许为空",
            format:'H:i',
            submitFormat :'H:i',
            editable:false,
             minValue: '06:00',
        	maxValue: '20:00',
	        xtype:'timefield'
	    },
		{
	        fieldLabel: '修改人',
	        name: 'mtlmsp',
	         hidden:true,
	        xtype:'textfield'
	    },
		{
            fieldLabel: '修改日期',
            name: 'mtlmdt',
            xtype: 'datefield',
             hidden:true,
            format: 'Y-m-d'   
        },
		{
	        fieldLabel: '订货会批号',
	        name: 'ormtno',
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
