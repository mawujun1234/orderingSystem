Ext.define('y.cg.CgOrdmtForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.cg.CgOrdmt'
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
		  		itemId:'ormtno',
				xtype:'ordmtcombo',
				name:'ormtno',
				fieldLabel: '订货会',
				allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"订货会采购子批次号不允许为空",
				listeners:{
					select:function(combo , record , eOpts){
						//window.ordmt_record=record;
						//me.initReloadSampleDesign_index++;
						//me.reload();
					}
				}
			},
		{
	        fieldLabel: '采购批次号',
	        name: 'orcgno',
            //allowBlank: false,
           // afterLabelTextTpl: Ext.required,
            //blankText:"订货会采购采购批次号不允许为空",
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '采购批次名称',
	        name: 'orcgnm',
	        allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"订货会采购采购批次名称不允许为空",
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
