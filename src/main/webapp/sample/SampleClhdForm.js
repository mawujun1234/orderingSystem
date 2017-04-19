Ext.define('y.sample.SampleClhdForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.sample.SampleClhd'
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
	    },{
	    	 fieldLabel: '订货会',
		  		name: 'ormtno',
				xtype:'ordmtcombo',
				listeners:{
		        	select:function( combo, record, eOpts ) {
		        		window.ordmt_record=record;
		        	}	
		        }
		},{
		        fieldLabel: '品牌',
		        name: 'bradno',
	            allowBlank: false,
	            showBlank:false,
	            afterLabelTextTpl: Ext.required,
	            //value:'Y',
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1'
		},{
		        fieldLabel: '上市月份',
		        allowBlank: false,
		        showBlank:false,
	            afterLabelTextTpl: Ext.required,
		        name: 'spbano',
		        xtype:'pubcodecombo',
		        tyno:'23'
		},
		{
	        fieldLabel: '搭配编号',
	        name: 'clppnm',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '搭配说明',
	        name: 'clppmk',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		
		{
	        fieldLabel: '吊牌打印标志',
	        name: 'print',
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'hiddenfield'   
	    },
	    {
	        fieldLabel: '搭配状态',
	        name: 'clppst',
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'hiddenfield'   
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
