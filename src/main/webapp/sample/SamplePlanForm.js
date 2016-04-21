Ext.define('y.sample.SamplePlanForm',{
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
            allowBlank: false,
            selectOnFocus:true,
            afterLabelTextTpl: Ext.required,
            blankText:"企划样衣编号不允许为空",
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '品牌',
	        name: 'bradno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            //value:'Y',
            blankText:"品牌不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'1'
	    },
		{
	        fieldLabel: '年份',
	        name: 'spyear',
            allowBlank: false,
            selectOnFocus:true,
            afterLabelTextTpl: Ext.required,
            blankText:"年份不允许为空",
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '季节',
	        name: 'spsean',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"季节不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'8'
	    },
		{
	        fieldLabel: '大系列',
	        name: 'spbseno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"大系列不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'17'
	    },
		{
	        fieldLabel: '品牌系列',
	        name: 'sprseno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"品牌系列不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'10'
	    },
		{
	        fieldLabel: '大类',
	        name: 'spclno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"大类不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'0',
	        listeners:{
	        	select:function( combo, record, eOpts ) {
	        		var sptyno=combo.nextSibling("pubcodecombo[name=sptyno]");
	        		sptyno.reload(record.get("itno"));
	        		
	        		var spseno=combo.nextSibling("pubcodecombo[name=spseno]");
	        		spseno.reload(record.get("itno"));
	        	}
	        }
	    },
		{
	        fieldLabel: '小类',
	        name: 'sptyno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"小类不允许为空",
            autoLoad:false,
	        xtype:'pubcodecombo',
	        tyno:'2'
	    },
		{
	        fieldLabel: '系列',
	        name: 'spseno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"系列不允许为空",
            autoLoad:false,
	        xtype:'pubcodecombo',
	        tyno:'5'
	    },
		{
	        fieldLabel: '定位',
	        name: 'splcno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"定位不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'18'
	    },
		{
	        fieldLabel: '上市批次',
	        name: 'spbano',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"上市批次不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'23'
	    },
		{
	        fieldLabel: '出厂价',
	        name: 'spftpr',
            allowBlank: false,
            selectOnFocus:true,
            afterLabelTextTpl: Ext.required,
            blankText:"出厂价不允许为空",
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '零售价',
	        name: 'sprtpr',
	        selectOnFocus:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"零售价不允许为空",
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '企划倍率',
	        name: 'spplrd',
	        selectOnFocus:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"企划倍率不允许为空",
	        xtype:'numberfield',
	        listeners:{
	        	change:function( spplrdField, newValue, oldValue, eOpts ) {
	        		//alert(newValue);
	        		//console.log(newValue);
	        		if(!spplrdField.getValue()){
	        			return;
	        		}
	        		
	        		var sprtprField=spplrdField.previousSibling("numberfield[name=sprtpr]");//出厂价
	        		//console.log(sprtprField.getValue()+"=======");
	        		var plctprField=spplrdField.nextSibling("numberfield[name=plctpr]");
	        		plctprField.setValue(sprtprField.getValue()/newValue);
	        	}
	        }
	    },
		{
	        fieldLabel: '企划成本价',
	        name: 'plctpr',
	        //editable:false,
//	        selectOnFocus:true,
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
            blankText:"企划成本价不允许为空",
	        xtype:'numberfield'   
	    },
		{
            fieldLabel: '计划交货期',
            name: 'pldate',
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
				var record=button.up('form').getForm().getRecord();
//				var samplePlanStpres=samplePlanStprGrid.getStore().getRange();
//				
//				var aa=[];
//				for(var i=0;i<samplePlanStpres.length;i++){
//					aa.push({
//						plspno:samplePlanStpres[i].get("plspno"),
//						suitno:samplePlanStpres[i].get("suitno"),
//						spftpr:samplePlanStpres[i].get("spftpr"),
//						sprtpr:samplePlanStpres[i].get("sprtpr")
//					
//					});
//				}
//				record.set("samplePlanStpres",aa);
				
				record.save({
					failure: function(record, operation) {
				    },
				    success: function(record, operation) {
				    	//formpanel.reset();
				    	samplePlanStprGrid.getStore().removeAll();
				    	var tabpanel=formpanel.up("tabpanel");
						//tabpanel.unmask();
						var samplePlanGrid=tabpanel.previousSibling("gridpanel#samplePlanGrid") ;
						samplePlanGrid.getStore().reload();
						Ext.Msg.alert("消息","保存成功!");
						//button.up('window').close();
				    }
				});			
				
				}
			});
      me.callParent();
	},
	loadRecord:function(record){
		var me=this;
		var sptyno=this.down("pubcodecombo[name=sptyno]")
		sptyno.reload(record.get("spclno"));
		var spseno=this.down("pubcodecombo[name=spseno]")
		spseno.reload(record.get("spclno"));
////		Ext.Ajax.request({
////			url:Ext.ContextPath+"",
////			params:{
////			
////			},
////			success:function(response){
////				var objes=Ext.decode(response.responseText);
////				me.down("grid#samplePlanStprGrid").getStore().loadData(objes);
////			}
////		});
//		var samplePlanStprGrid_store=me.down("grid#samplePlanStprGrid").getStore();
//		samplePlanStprGrid_store.getProxy().extraParams={
//			plspno:record.get("plspno")
//		};
//		samplePlanStprGrid_store.reload();
		
		//this.loadRecord(record);
		this.getForm().loadRecord(record);
	}
});
