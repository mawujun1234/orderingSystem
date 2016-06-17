Ext.define('y.sample.SampleColthForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.sample.SampleColth'
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
	        fieldLabel: '开发供应商',
	        name: 'spsuno',
	        itemId: 'spsuno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"开发供应商不允许为空", 
            selectOnFocus:true,
	        xtype:'pubsunocombo',
	        listeners:{
	        	select:function(combo, record, eOpts ){
	        		var prsuno=combo.nextSibling("pubsunocombo#prsuno");

	        		prsuno.select( record );

	        	}
	        }
	    },
		{
	        fieldLabel: '货号采购供应商',
	        name: 'prsuno',
	        itemId: 'prsuno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"货号采购供应商不允许为空",
            xtype:'pubsunocombo'
	    },
	    {
	        fieldLabel: '纱厂',
	        name: 'spcotn',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"纱厂不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '含税工缴',
	        value:0,
	        name: 'sptapa',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"含税工缴不允许为空",
            selectOnFocus:true,
	        xtype:'numberfield',
	        listeners:{
	        	change:function( field, newValue, oldValue, eOpts ) {
	        		field.up("form").sumSpctpr();
	        	}
	        }
	    },
		{
	        fieldLabel: '含税辅料',
	        name: 'spacry',
	        value:0,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"含税辅料不允许为空",
            selectOnFocus:true,
	        xtype:'numberfield' ,
	        listeners:{
	        	change:function( field, newValue, oldValue, eOpts ) {
	        		field.up("form").sumSpctpr();
	        	}
	        }
	    },
		{
	        fieldLabel: '服饰配料',
	        name: 'spclbd',
	        value:0,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"服饰配料不允许为空",
            selectOnFocus:true,
	        xtype:'numberfield' ,
	        listeners:{
	        	change:function( field, newValue, oldValue, eOpts ) {
	        		field.up("form").sumSpctpr();
	        	}
	        }
	    },
	    {
	        fieldLabel: '包装辅料费',
	        name: 'acsyam',
	        value:0,
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"包装辅料费不允许为空",
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield',
	        listeners:{
	        	change:function( field, newValue, oldValue, eOpts ) {
	        		field.up("form").sumSpctpr();
	        	}
	        }
	    },
		{
	        fieldLabel: '新成衣价',
	        name: 'spnwpr',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"新成衣价不允许为空",
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '成衣数量',
	        name: 'contqt',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"成衣数量不允许为空",
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '成衣金额',
	        name: 'contam',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"成衣金额不允许为空",
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '成衣核价克重',
	        name: 'contpr',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"成衣核价克重不允许为空",
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
            fieldLabel: '合同交期',
            name: 'ctdwdt',
            editable:false,
            xtype: 'datefield',
            format: 'Y-m-d'   
        },
		
		{
	        fieldLabel: '预计成本价',
	        name: 'spctpr',
            //allowBlank: false,
            //afterLabelTextTpl: Ext.required,
            //blankText:"预计成本价不允许为空",
            //selectOnFocus:true,
            editable:false,
	        xtype:'numberfield'   
	    },
	    {
	        fieldLabel: '出厂价',
	        name: 'spftpr',
	        fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'numberfield'   
	    },
	     {
	        fieldLabel: '出厂价/预计成本价',
	        name: 'spftpr_spctpr',
	        fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'numberfield'   
	    },
	    {
	        fieldLabel: '零售价',
	        name: 'sprtpr',
	        fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'numberfield'   
	    },
	    {
	        fieldLabel: '零售价/出厂价',
	        name: 'sprtpr_spftpr',
	        fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'numberfield'   
	    },
	    {
	        fieldLabel: '零售价/预计成本价',
	        name: 'sprtpr_spctpr',
	        fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '备注',
	        name: 'sprmk',
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '锁定状态',
	        name: 'spctst',
	        hidden:true,
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
	    }
	  ];   
	  
	  
	   var sampleDesignStprGrid=Ext.create('y.sample.SampleDesignStprGrid',{
	  	itemId:'sampleDesignStprGrid'
	  });
	  var fieldset={
        // Fieldset in Column 1 - collapsible via toggle button
        xtype:'fieldset',
        //columnWidth: 0.5,
        title: '套件价格',
        collapsible: true,
        //defaultType: 'textfield',
        defaults: {anchor: '100%'},
        layout: 'anchor',
        items :[sampleDesignStprGrid]
      }
	  me.items.push(fieldset);
	  
	  
	  this.buttons = [];
		this.buttons.push({
			text : '保存',
			itemId : 'save',
			formBind: true, //only enabled once the form is valid
       		disabled: true,
			glyph : 0xf0c7,
			hidden:!Permision.canShow('sample_design_clothsave'),
			handler : function(button){
				if(!window.sampno){
		    		Ext.Msg.alert("消息","请先建立‘设计开发’中的信息");
		    		return;
		    	}
		    	
				var formpanel = button.up('form');
				formpanel.updateRecord();
				//formpanel.getForm().getRecord().set("sampno",window.sampno.sampno);
				
				var sampleDesignStpres=sampleDesignStprGrid.getStore().getRange();		
				var aa=[];
				//套件的其他值加起来，要等于标准套的值
				var t00_temp={
					spftpr:0,
					sprtpr:0,
					plctpr:0
				}
				var other_temp={
					spftpr:0,
					sprtpr:0,
					plctpr:0
				}
				for(var i=0;i<sampleDesignStpres.length;i++){
					if(sampleDesignStpres[i].get("suitno")=='T00'){
						t00_temp={
							spftpr:sampleDesignStpres[i].get("spftpr"),
							sprtpr:sampleDesignStpres[i].get("sprtpr"),
							plctpr:sampleDesignStpres[i].get("plctpr")
						}
					} else {
						other_temp.spftpr+=sampleDesignStpres[i].get("spftpr");
						other_temp.sprtpr+=sampleDesignStpres[i].get("sprtpr");
						other_temp.plctpr+=sampleDesignStpres[i].get("plctpr");
					}
					aa.push({
						//sampno:sampleDesignStpres[i].get("getSampno"),
						suitno:sampleDesignStpres[i].get("suitno"),
						spftpr:sampleDesignStpres[i].get("spftpr"),
						sprtpr:sampleDesignStpres[i].get("sprtpr"),
						plctpr:sampleDesignStpres[i].get("plctpr")
					});
				}
				//套件的其他值加起来，要等于标准套的值
				if(sampleDesignStpres.length>1){
					if(t00_temp.spftpr!=other_temp.spftpr){
						Ext.Msg.alert("消息","出厂价 ：其他套件的出厂价和不等于标准套的出厂价");
						return;
					}
					if(t00_temp.sprtpr!=other_temp.sprtpr){
						Ext.Msg.alert("消息","零售价：其他套件的零售价和不等于标准套的零售价");
						return;
					}
					if(t00_temp.plctpr!=other_temp.plctpr){
						Ext.Msg.alert("消息","企划成本价 ：其他套件的企划成本价和不等于标准套的企划成本价");
						return;
					}
				}
				var jsonData=formpanel.getForm().getFieldValues();
				//alert(jsonData.ctdwdt);
				jsonData.ctdwdt=Ext.Date.format(jsonData.ctdwdt,'Y-m-d H:i:s');
				jsonData.sampno=window.sampno.sampno;
				jsonData.sampleDesignStpres=aa;
				
				Ext.Ajax.request({
					url:Ext.ContextPath+"/sampleColth/create.do",
					actionMethods: { read: 'POST' },
					timeout :600000,
					headers:{ 'Accept':'application/json;'},
					jsonData:jsonData,
					success:function(response){
						Ext.Msg.alert("消息","保存成功!");
						//如果是锁定状态，就隐藏这个按钮
						//hidden:!Permision.canShow('sample_design_designsave'),
						if(sampleDesign.get("spctst")==1){
							me.down("#save").hide();		
						} else if(Permision.canShow('sample_design_designsave')){
							me.down("#save").show();
						}
					}
					
				});
				
				
//				formpanel.getForm().getRecord().save({
//					failure: function(record, operation) {
//				    },
//				    success: function(record, operation) {
//						Ext.Msg.alert("消息","保存成功!");
//						
//						//如果是锁定状态，就隐藏这个按钮
//						//hidden:!Permision.canShow('sample_design_designsave'),
//						if(sampleDesign.get("spctst")==1){
//							me.down("#save").hide();		
//						} else if(Permision.canShow('sample_design_designsave')){
//							me.down("#save").show();
//						}
//				    }
//				});			
				
				}
			});
      me.callParent();
	},
	/**
	 * 统计预计成本价
	 */
	sumSpctpr:function(){
		var me=this;
		
		var sptapa=this.getForm().findField("sptapa");
		var spacry=this.getForm().findField("spacry");
		var spclbd=this.getForm().findField("spclbd");
		var spctpr=this.getForm().findField("spctpr");
		var acsyam=this.getForm().findField("acsyam");
		
		var value=sptapa.getValue()+spacry.getValue()+spclbd.getValue();
		
		//获取面料的价格
		var tabpanel=me.up("tabpanel");
		if(!tabpanel){
			return;
		}
		//var sampleMateForm=tabpanel.down("#sampleMateForm");
		var sampleMateGrid=tabpanel.down("grid#sampleMateGrid");
		value+=sampleMateGrid.sumMtpupr()+acsyam.getValue();
		//预计成本价
		spctpr.setValue(value);
		
		//alert(1);
		if(value){//alert(2);
			////出厂价/预计成本价
			var spftpr=this.getForm().findField("spftpr");
			var spftpr_spctpr=this.getForm().findField("spftpr_spctpr");
			spftpr_spctpr.setValue(spftpr.getValue()/value);
			//零售价/预计成本价
			var sprtpr=this.getForm().findField("sprtpr");
			var sprtpr_spctpr=this.getForm().findField("sprtpr_spctpr");
			sprtpr_spctpr.setValue(sprtpr.getValue()/value);
		}
		


	},
	loadRecord:function(record){
		var me=this;				
		this.getForm().loadRecord(record);
		//为供应商初始化名称
		var spsuno_combo=me.down("#spsuno");
		var spsuno_model= spsuno_combo.getStore().createModel({idsuno:record.get("spsuno"),idsunm:record.get("spsuno_name")});
		spsuno_combo.setValue(spsuno_model);
		
		var prsuno_combo=me.down("#prsuno");
		var prsuno_model= prsuno_combo.getStore().createModel({idsuno:record.get("prsuno"),idsunm:record.get("prsuno_name")});
		prsuno_combo.setValue(prsuno_model);

	},
	loadGrid:function(sampleDesign){
		var sampleDesignStprGrid_store=this.down("grid#sampleDesignStprGrid").getStore();
		sampleDesignStprGrid_store.removeAll();
		sampleDesignStprGrid_store.getProxy().extraParams={
			suitty:sampleDesign.get("suitty"),
			sampno:sampleDesign.get("sampno")//window.sampno.sampno
		};
		//console.log(sampleDesign.get("suitty"));
		//console.log(sampleDesign.get("sampno"));
		sampleDesignStprGrid_store.reload();
	},
	reset:function(){
		this.getForm().reset();
		var sampleDesignStprGrid_store=this.down("grid#sampleDesignStprGrid").getStore();
		sampleDesignStprGrid_store.removeAll();
	}
});
