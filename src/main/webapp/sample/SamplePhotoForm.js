Ext.define('y.sample.SamplePhotoForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.sample.SamplePhoto'
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
	        xtype: 'filefield',
	        name: 'imageFile',
	       // id:'photo',
	        fieldLabel: '图片名',
	        
	        anchor: '100%',
	        buttonText: '选择图片...',
	        listeners:{
	        	change:function(field, value){
	        		var prevImage=field.up("form").nextSibling("image#prevImage");
	        		var filepath = field.getEl().dom.value;
	        		var f=field.fileInputEl.dom;
	        		if (f && f.files  && f.files[0]) {
						filepath=window.URL.createObjectURL(f.files[0]);
						prevImage.getEl( ).dom.src =filepath;
					} else {
						var imgObj=prevImage.getEl( ).dom;
		                // 两个坑:
		                // 1、在设置filter属性时，元素必须已经存在在DOM树中，动态创建的Node，也需要在设置属性前加入到DOM中，先设置属性在加入，无效；
		                // 2、src属性需要像下面的方式添加，上面的两种方式添加，无效；
		               imgObj.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
		               imgObj.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = value;
					}
	        	}
	        }
	    },
//		{
//	        fieldLabel: '图片名',
//	        name: 'photnm',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"图片名不允许为空",
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
		{
	        fieldLabel: '图片描述',
	        name: 'photms',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"图片描述不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '图片文件名',
	        name: 'imgnm',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"图片文件名不允许为空",
//            selectOnFocus:true,
	        hidden:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '锁定状态',
	        name: 'photst',
            allowDecimals:false,
            selectOnFocus:true,
            hidden:true,
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
        },
		{
	        fieldLabel: 'id',
	        name: 'id',
            hidden:true,
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '设计样衣编号',
	        name: 'sampno',
            hidden:true,
            selectOnFocus:true,
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
				//formpanel.updateRecord();
				formpanel.getForm().submit({
					 waitMsg:'正在上传请稍候',  
                     waitTitle:'提示', 
                     url:Ext.ContextPath+'/samplePhoto/create.do', 
                     method:'POST', 
                     success:function(form,action){  
                     	
                     	button.up('window').close();
                     	button.up("form").nextSibling("image#prevImage").setSrc("");
                     }
				});
				
//				formpanel.getForm().getRecord().save({
//					failure: function(record, operation) {
//				    },
//				    success: function(record, operation) {
//						button.up('window').close();
//				    }
//				});			
				
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
