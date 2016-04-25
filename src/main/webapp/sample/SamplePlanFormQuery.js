Ext.define('y.sample.SamplePlanFormQuery',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.sample.SamplePlan'
	],
	title:'商品企划',
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
	        fieldLabel: '企划样衣编号',
	        name: 'plspnm',
	        readOnly:true,
            allowBlank: false,
            selectOnFocus:true,
            afterLabelTextTpl: Ext.required,
            blankText:"企划样衣编号不允许为空",
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '品牌',
	        name: 'bradno_name',
	        readOnly:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            //value:'Y',
            blankText:"品牌不允许为空",
	        xtype:'textfield',
	        tyno:'1'
	    },
		{
	        fieldLabel: '年份',
	        name: 'spyear',
	        readOnly:true,
            allowBlank: false,
            selectOnFocus:true,
            afterLabelTextTpl: Ext.required,
            blankText:"年份不允许为空",
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '季节',
	        name: 'spsean_name',
	        readOnly:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"季节不允许为空",
	        xtype:'textfield',
	        tyno:'8'
	    },
		{
	        fieldLabel: '大系列',
	        name: 'spbseno_name',
	        readOnly:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"大系列不允许为空",
	        xtype:'textfield',
	        tyno:'17'
	    },
		{
	        fieldLabel: '品牌系列',
	        name: 'sprseno_name',
	        readOnly:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"品牌系列不允许为空",
	        xtype:'textfield',
	        tyno:'10'
	    },
		{
	        fieldLabel: '大类',
	        name: 'spclno_name',
            allowBlank: false,
            readOnly :true,
            afterLabelTextTpl: Ext.required,
            blankText:"大类不允许为空",
	        xtype:'textfield',
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
		{
	        fieldLabel: '小类',
	        name: 'sptyno_name',
            allowBlank: false,
            readOnly:true,
            afterLabelTextTpl: Ext.required,
            blankText:"小类不允许为空",
            autoLoad:false,
	        xtype:'textfield',
	        tyno:'2'
	    },
		{
	        fieldLabel: '系列',
	        name: 'spseno_name',
	        readOnly:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"系列不允许为空",
            autoLoad:false,
	        xtype:'textfield',
	        tyno:'5'
	    },
		{
	        fieldLabel: '定位',
	        name: 'splcno_name',
	        readOnly:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"定位不允许为空",
	        xtype:'textfield',
	        tyno:'18'
	    },
		{
	        fieldLabel: '上市批次',
	        name: 'spbano_name',
	        readOnly:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"上市批次不允许为空",
	        xtype:'textfield',
	        tyno:'23'
	    },
		{
	        fieldLabel: '出厂价',
	        name: 'spftpr',
	        readOnly:true,
            allowBlank: false,
            selectOnFocus:true,
            afterLabelTextTpl: Ext.required,
            blankText:"出厂价不允许为空",
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '零售价',
	        name: 'sprtpr',
	        readOnly:true,
	        selectOnFocus:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"零售价不允许为空",
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '企划倍率',
	        name: 'spplrd',
	        readOnly:true,
	        selectOnFocus:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"企划倍率不允许为空",
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '企划成本价',
	        name: 'plctpr',
	        readOnly:true,
	        selectOnFocus:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"企划成本价不允许为空",
	        xtype:'numberfield'   
	    },
		{
            fieldLabel: '计划交货期',
            name: 'pldate',
            readOnly:true,
            selectOnFocus:true,
            editable:false,
             allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"计划交货期不允许为空",
            xtype: 'datefield',
            format: 'Y-m-d'   
        },
		{
            fieldLabel: '面料交货期',
            name: 'mldate',
            readOnly:true,
            editable:false,
             allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"面料交货期不允许为空",
            xtype: 'datefield',
            format: 'Y-m-d'   
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
            editable:false,
            hidden:true,
            selectOnFocus:true,
            xtype: 'datefield',
            format: 'Y-m-d'   
        },
		{
	        fieldLabel: '修改人',
	        name: 'lmsp',
	        hidden:true,
	        xtype:'textfield'
	    },
		{
            fieldLabel: '修改日期',
            name: 'lmdt',
            editable:false,
            hidden:true,
            selectOnFocus:true,
            xtype: 'datefield',
            format: 'Y-m-d'   
        },
		{
	        fieldLabel: '企划样衣代码',
	        name: 'plspno',
            hidden:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '订货会批号',
	        name: 'ormtno',
            hidden:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '样衣状态',
	        name: 'plstat',
            hidden:true,
            allowDecimals:false,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '锁定状态',
	        name: 'plspst',
            hidden:true,
            allowDecimals:false,
	        xtype:'numberfield'   
	    }
	  ];   
	  
//	  var samplePlanStprGrid=Ext.create('y.sample.SamplePlanStprGrid',{
//	  	readOnly:true,
//		itemId:'samplePlanStprGrid'
//	  });
//	  var fieldset={
//        // Fieldset in Column 1 - collapsible via toggle button
//        xtype:'fieldset',
//        //columnWidth: 0.5,
//        title: '套件价格',
//        collapsible: true,
//        //defaultType: 'textfield',
//        defaults: {anchor: '100%'},
//        layout: 'anchor',
//        items :[samplePlanStprGrid]
//    }
//	  me.items.push(fieldset);
	  

      me.callParent();
	},
	loadRecord:function(record){
//		this.mask("正在刷新....");
//		var sptyno=this.down("pubcodecombo[name=sptyno]")
//		sptyno.reload(record.get("spclno"));
//		var spseno=this.down("pubcodecombo[name=spseno]")
//		spseno.reload(record.get("spclno"));


		
		this.getForm().loadRecord(record);
//		this.unmask();
	}
});
