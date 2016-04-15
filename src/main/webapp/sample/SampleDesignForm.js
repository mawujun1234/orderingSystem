Ext.define('y.sample.SampleDesignForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.sample.SampleDesign'
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
	        fieldLabel: '设计样衣编号',
	        name: 'sampnm',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"设计样衣编号不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '企划样衣id',
	        name: 'plspno',
	        hidden:true,
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"企划样衣编号不允许为空",
//            selectOnFocus:true,
	        xtype:'textfield'
	    },
	    {
	        fieldLabel: '企划样衣编号',
	        name: 'plspnm',
            allowBlank: false,
            readOnly:true,
            afterLabelTextTpl: Ext.required,
            blankText:"企划样衣编号不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '版型',
	        name: 'versno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"版型不允许为空",
            xtype:'pubcodecombo',
	        tyno:'13'
	    },
		{
	        fieldLabel: '照片编号',
	        name: 'photno',
            allowBlank: false,
            hidden:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '工作室系列',
	        name: 'stseno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"工作室系列不允许为空",
            selectOnFocus:true,
	        xtype:'pubcodecombo',
	        tyno:'21'
	    },
		{
	        fieldLabel: '设计师',
	        name: 'desgno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"设计师不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '外买样衣编号',
	        name: 'buspno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"外买样衣编号不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '生产类型',
	        name: 'spmtno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"生产类型不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '客供编号',
	        name: 'gustno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"客供编号不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '颜色',
	        name: 'colrno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"大系列不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'9'
	    },
		{
	        fieldLabel: '花型',
	        name: 'pattno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"花型不允许为空",
            xtype:'pubcodecombo',
	        tyno:'12'
	    },
		{
	        fieldLabel: '款式',
	        name: 'stylno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"款式不允许为空",
            xtype:'pubcodecombo',
	        tyno:'14'
	    },
		{
	        fieldLabel: '款式组',
	        name: 'stylgp',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"款式组不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '性别',
	        name: 'sexno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"性别不允许为空",
            xtype:'pubcodecombo',
	        tyno:'6'
	    },
		{
	        fieldLabel: '长短袖',
	        name: 'slveno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"长短袖不允许为空",
            xtype:'pubcodecombo',
	        tyno:'7'
	    },
		{
	        fieldLabel: '套装种类',
	        name: 'suitty',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"套装种类不允许为空",
            xtype:'pubcodecombo',
	        tyno:'20'
	    },
		{
	        fieldLabel: '规格版型说明',
	        name: 'desp',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"规格版型说明不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '规格系列',
	        name: 'sizegp',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"规格系列不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
//		{
//	        fieldLabel: '包装要求',
//	        name: 'packqt',
//            allowDecimals:false,
//            selectOnFocus:true,
//	        xtype:'numberfield'   
//	    },
	    {
            xtype      : 'fieldcontainer',
            fieldLabel : '包装要求',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
            	{
                    boxLabel  : '包装',
                    name: 'packqt',
                    checked:true,
                    inputValue: '1'
                },
           		{
                    boxLabel  : '不包装',
                    name: 'packqt',
                    inputValue: '0'
                }
            ]
        },
//		{
//	        fieldLabel: '是否拆套',
//	        name: 'spltmk',
//            allowDecimals:false,
//            selectOnFocus:true,
//	        xtype:'numberfield'   
//	    },
        {
            xtype      : 'fieldcontainer',
            fieldLabel : '是否拆套',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
            	{
                    boxLabel  : '是',
                    name: 'spltmk',
                    inputValue: '1'
                },
           		{
                    boxLabel  : '否',
                    name: 'spltmk',
                    checked:true,
                    inputValue: '0'
                }
            ]
        },
		{
	        fieldLabel: '吊牌打印标志',
	        name: 'print',
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '设计样衣代码',
	        name: 'sampno',
            hidden:true,
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '样衣状态',
	        name: 'sampst',
            hidden:true,
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '锁定状态',
	        name: 'spstat',
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
				    	Ext.Msg.alert("消息","保存成功!");
						//button.up('window').close();
				    	//formpanel.reset();
				    	//samplePlanStprGrid.getStore().removeAll();
				    	var tabpanel=formpanel.up("tabpanel");
						//tabpanel.unmask();
						var samplePlanDesignGrid=tabpanel.previousSibling("gridpanel#samplePlanDesignGrid") ;
						samplePlanDesignGrid.getStore().reload();
						//用于后面的面料信息
						window.sampno={
							sampno:record.get("sampno"),
							sampnm:record.get("sampnm")
						};
				    }
				});			
				
				}
			});
      me.callParent();
	},
	loadRecord:function(record){
		//this.mask("正在刷新....");
//		var sptyno=this.down("pubcodecombo[name=sptyno]")
//		sptyno.reload(record.get("spclno"));
//		var spseno=this.down("pubcodecombo[name=spseno]")
//		spseno.reload(record.get("spclno"));
//
//		this.down("grid#samplePlanStprGrid").getStore().loadData(record.get("samplePlanStpres"));
//		//this.loadRecord(record);
		
		//this.unmask();
		
		this.getForm().loadRecord(record);
	}
});
