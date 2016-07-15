Ext.define('y.pubsize.SizeForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.pubsize.Size'
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
	        fieldLabel: '规格范围代码',
	        name: 'sizeno',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '规格范围名称',
	        name: 'sizenm',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '订货会批号',
	        name: 'ormtno',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '规格类型',
	        name: 'sizety',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '规格系列类型',
	        name: 'ysizety',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '规格系列代码',
	        name: 'ysizeno',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '品牌',
	        name: 'szbrad',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '大类',
	        name: 'szclno',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '排序',
	        name: 'sizeso',
           // allowDecimals:false,
           // selectOnFocus:true,
	        xtype:'numberfield'   
	    }
//		{
//	        fieldLabel: '创建人',
//	        name: 'rgsp',
//            hidden:true,
//            selectOnFocus:true,
//	        xtype:'hiddenfield'
//	    },
//		{
//            fieldLabel: '创建日期',
//            name: 'rgdt',
//            hidden:true,
//            editable:false,
//            xtype: 'hiddenfield'
//        },
//		{
//	        fieldLabel: '修改人',
//	        name: 'lmsp',
//            hidden:true,
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
//		{
//            fieldLabel: '修改日期',
//            name: 'lmdt',
//            hidden:true,
//            editable:false,
//            xtype: 'datefield',
//            format: 'Y-m-d'   
//        }
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
						//console.log(operation._response.responseText);
						var obj=Ext.decode(operation.getResponse().responseText);
						Ext.Msg.alert('失败', obj.msg);
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
