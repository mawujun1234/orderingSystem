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
	        itemId: 'plspnm',
            allowBlank: false,
            selectOnFocus:true,
            afterLabelTextTpl: Ext.required,
            blankText:"企划样衣编号不允许为空",
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '品牌',
	        name: 'bradno',
	        //selFirst:true,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            //value:'Y',
            blankText:"品牌不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'1',
	        listeners:{
				select:function(combo , record , eOpts){
					
				}
			}
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
	        //selFirst:true,
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
	        fieldLabel: '上市月份',
	        name: 'spbano',
	        itemId:'spbano',
            allowBlank: true,
            //afterLabelTextTpl: Ext.required,
            showBlank:true,
            blankText:"上市月份不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'23',
	        listeners:{
	        	select:function( combo, record, eOpts ) {
	        		var spbadt=combo.nextSibling("combobox[name=spbadt]");
	        		var rawvalue=combo.getRawValue();
	        		//rawvalue='三月份';
	        		
	        		//spbadt.getStore().removeAll();
	        		var data=[];
	        		if(rawvalue=='一月'){
	        			data=[{key:'1230',name:'1230'},{key:'0110',name:'0110'},{key:'0120',name:'0120'}];
	        		} else if(rawvalue=='二月'){
	        			data=[{key:'0131',name:'0131'},{key:'0210',name:'0210'},{key:'0220',name:'0220'}];
	        		} else if(rawvalue=='三月'){
	        			 var year = (new Date()).getFullYear();
	        			if(!!((year & 3) == 0 && (year % 100 || (year % 400 == 0 && year)))){
	        				data=[{key:'0229',name:'0229'},{key:'0310',name:'0310'},{key:'0320',name:'0320'}];
	        			} else {
	        				data=[{key:'0228',name:'0228'},{key:'0310',name:'0310'},{key:'0320',name:'0320'}];
	        			}
	        		} else if(rawvalue=='四月'){
	        			data=[{key:'0331',name:'0331'},{key:'0410',name:'0410'},{key:'0420',name:'0420'}];
	        		} else if(rawvalue=='五月'){
	        			data=[{key:'0430',name:'0430'},{key:'0510',name:'0510'},{key:'0520',name:'0520'}];
	        		} else if(rawvalue=='六月'){
	        			data=[{key:'0531',name:'0531'},{key:'0610',name:'0610'},{key:'0620',name:'0620'}];
	        		} else if(rawvalue=='七月'){
	        			data=[{key:'0630',name:'0630'},{key:'0710',name:'0710'},{key:'0720',name:'0720'}];
	        		} else if(rawvalue=='八月'){
	        			data=[{key:'0731',name:'0731'},{key:'0810',name:'0810'},{key:'0820',name:'0820'}];
	        		} else if(rawvalue=='九月'){
	        			data=[{key:'0831',name:'0831'},{key:'0910',name:'0910'},{key:'0920',name:'0920'}];
	        		} else if(rawvalue=='十月'){
	        			data=[{key:'0930',name:'0930'},{key:'1010',name:'1010'},{key:'1020',name:'1020'}];
	        		} else if(rawvalue=='十一月'){
	        			data=[{key:'1031',name:'1031'},{key:'1110',name:'1110'},{key:'1120',name:'1120'}];
	        		} else if(rawvalue=='十二月'){
	        			data=[{key:'1130',name:'1130'},{key:'1210',name:'1210'},{key:'1220',name:'1220'}];
	        		}
	        		spbadt.getStore().loadData(data);
	        	}
	        }
	    },
	    {
	        fieldLabel: '上市日期',
	        name: 'spbadt',
            allowBlank: true,
            //afterLabelTextTpl: Ext.required,
            showBlank:true,
            blankText:"上市日期不允许为空",
	        xtype:'combobox',
	        editable:false,
	        queryMode: 'local',
		    displayField: 'name',
		    valueField: 'key',
		    store: Ext.create('Ext.data.Store', {
			    fields: ['key', 'name'],
			    data : [
			        
			    ]
			})
	    },
	    {
	        fieldLabel: '工厂生产周期 ',
	        name: 'spfpcy',
            allowBlank: false,
            selectOnFocus:true,
            afterLabelTextTpl: Ext.required,
            blankText:"工厂生产周期不允许为空",
            minValue:0,
            allowDecimals:false,
	        xtype:'numberfield'
	    },
	    {
	        fieldLabel: '商品等级',
	        name: 'plgrno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"商品等级批次不允许为空",
	        xtype:'pubcodecombo',
	        tyno:'30'
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
	        xtype:'numberfield',
	        listeners:{
	        	change:function( sprtprField, newValue, oldValue, eOpts ) {
					var spplrdField=sprtprField.nextSibling("numberfield[name=spplrd]");
					if(!spplrdField.getValue()){
	        			return;
	        		}
	        		var plctprField=spplrdField.nextSibling("numberfield[name=plctpr]");
	        		plctprField.setValue(newValue/spplrdField.getValue());
	        	}
	        }
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
	        fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
            blankText:"企划成本价不允许为空",
	        xtype:'numberfield'   
	    },
//		{
//            fieldLabel: '计划交货期',
//            name: 'pldate',
//            selectOnFocus:true,
//            editable:false,
//             allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"计划交货期不允许为空",
//            xtype: 'datefield',
//            format: 'Y-m-d'   
//        },
//		{
//            fieldLabel: '面料交货期',
//            name: 'mldate',
//            editable:false,
//             allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"面料交货期不允许为空",
//            xtype: 'datefield',
//            format: 'Y-m-d'   
//        },
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
				
				if(record.get("bradno")=="Y"){
						//var spbano=combo.nextSibling("#spbano");
		        		//spbano
		        		//spbadt
					if(!record.get("spbano") || !record.get("spbadt")){
						Ext.Msg.alert("消息","上市日期和上日月份不能为空!");
						return;
					}
				}	
					
				record.save({
					failure: function(record, operation) {
				    },
				    success: function(record, operation) {
				    	//formpanel.reset();
				 
				    	var tabpanel=formpanel.up("tabpanel");
						
						var samplePlanGrid=tabpanel.previousSibling("gridpanel#samplePlanGrid") ;
						samplePlanGrid.getStore().reload();
						Ext.Msg.alert("消息","保存成功!");
						
				    }
				});			
				
				}
			});
      me.callParent();
	},
	loadRecord:function(record){
		var me=this;
		if(record.get("spclno")){
			var sptyno=this.down("pubcodecombo[name=sptyno]")
			sptyno.reload(record.get("spclno"));
			var spseno=this.down("pubcodecombo[name=spseno]")
			spseno.reload(record.get("spclno"));
		}
		
		
		//this.loadRecord(record);
		this.getForm().loadRecord(record);
		
		me.lockOrUnlock(record.get("plspst"));
	},
	lockOrUnlock:function(bool){
		var me=this;
		var save_btn=me.down("#save");
		if(bool){
			save_btn.hide();
		} else {
			save_btn.show();
		}
	},
	
	temp_bradno:'Y',//这是临时解决方案
	stat_xtrydeeeeeeeee:1,
	reloadPubcode:function(bradno){
		var me=this;
		
		//var stat_xtrydeeeeeeeee=spstat==1?0:1;
		
		if(this.temp_bradno!=bradno || me.stat_xtrydeeeeeeeee!=window.stat_xtrydeeeeeeeee){
			me.stat_xtrydeeeeeeeee=window.stat_xtrydeeeeeeeee;
			//window.stat_xtrydeeeeeeeee=stat_xtrydeeeeeeeee;
			
			var spseanField=me.getForm().findField("spsean");
			spseanField.changeBradno(bradno);
			spseanField.getStore().reload();
			
			var spbsenoField=me.getForm().findField("spbseno");
			spbsenoField.changeBradno(bradno);
			spbsenoField.getStore().reload();
			
			var sprsenoField=me.getForm().findField("sprseno");
			sprsenoField.changeBradno(bradno);
			sprsenoField.getStore().reload();
			
			var spclnoField=me.getForm().findField("spclno");
			spclnoField.changeBradno(bradno);
			spclnoField.getStore().reload();
			
			var sptynoField=me.getForm().findField("sptyno");
			sptynoField.changeBradno(bradno);
			//sptynoField.getStore().reload();
			
			var spsenoField=me.getForm().findField("spseno");
			spsenoField.changeBradno(bradno);
			//spsenoField.getStore().reload();
			
			var splcnoField=me.getForm().findField("splcno");
			splcnoField.changeBradno(bradno);
			splcnoField.getStore().reload();
			
			var spbanoField=me.getForm().findField("spbano");
			spbanoField.changeBradno(bradno);
			spbanoField.getStore().reload();
			

			this.temp_bradno=bradno;
		}
	}
});
