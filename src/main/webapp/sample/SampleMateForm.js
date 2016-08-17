Ext.define('y.sample.SampleMateForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.sample.SampleMate'
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
	        fieldLabel: '设计样衣id',
	        name: 'sampno',
			hidden:true,
	        xtype:'textfield'
	    },
//	    {
//	        fieldLabel: '设计样衣代码',
//	        name: 'sampnm',
//            allowBlank: false,
//            readOnly:true,
////            afterLabelTextTpl: Ext.required,
////            blankText:"设计样衣代码不允许为空",
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
		{
	        fieldLabel: '面料编号',
	        name: 'mateso',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"面料编号不允许为空",
//            allowDecimals:false,
//            selectOnFocus:true,
	        hidden:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '供应商',
	        name: 'mtsuno',
	        itemId: 'mtsuno',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"供应商不允许为空",
            xtype:'pubsunocombo'
	    },
		{
	        fieldLabel: '供应商面料货号',
	        name: 'mateno',
//            allowBlank: false,
//            
//            afterLabelTextTpl: Ext.required,
//            blankText:"供应商面料货号不允许为空",
	        maxLength:30,
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '面料品牌',
	        name: 'mtbrad',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"面料品牌不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },

	    {
            xtype      : 'fieldcontainer',
            fieldLabel : '进口/国产',
            defaultType: 'radiofield',
           //  allowBlank: false,
            afterLabelTextTpl: Ext.required,
            //blankText:"进口/国产不允许为空",
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
            	{
                    boxLabel  : '国产',
                    name: 'mttype',
                    checked:true,
                    inputValue: 'gc'
                },
           		{
                    boxLabel  : '进口',
                    name: 'mttype',
                    inputValue: 'jk'
                }
            ]
        },
		{
	        fieldLabel: '面料成分',
	        name: 'mtcomp',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"面料成分不允许为空",
            //selectOnFocus:true,
	        xtype:'textarea',
	        listeners:{
	        	change:function( field, newValue, oldValue, eOpts ) {
	        		//field.setValue(field.sevenReturn(newValue));
	        	},
	        	blur:function(field,event){
	        		//var val=field.getValue();
	        		//field.setValue(field.sevenReturn(val));
	        		field.setValue(field.sevenReturn(field.getValue()));
	        	}
	        },
	        sevenReturn:function(val){
	        	var resultStr = val.replace(/\ +/g, ""); //去掉空格
			        resultStr = val.replace(/[ ]/g, "");    //去掉空格
			        resultStr = val.replace(/[\r\n]/g, ""); //去掉回车换行
			        //resultStr.substr(0,7);
			        var arry=[];
			        while(resultStr.length>7){
			        	arry.push(resultStr.substr(0,7));
			        	resultStr=resultStr.substr(7);
			        }
			        arry.push(resultStr);
			        return arry.join("\r\n");
	        }
	    },
		{
	        fieldLabel: '纱支规格',
	        name: 'yarmct',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"纱支规格不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '克重/密度',
	        name: 'gramwt',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"克重/密度不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '后整理',
	        name: 'aftrmt',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"后整理不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '门幅',
	        name: 'width',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"门幅不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '面料单价',
	        name: 'mtpupr',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"面料单价不允许为空",
            selectOnFocus:true,
	        xtype:'numberfield'
	    },
		{
	        fieldLabel: '单件用料',
	        name: 'mtcnqt',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"单件用料不允许为空",
            selectOnFocus:true,
            decimalPrecision:3,
	        xtype:'numberfield'
	    },
		{
	        fieldLabel: '锁定状态',
	        name: 'matest',
            hidden:true,
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    }
	  ];   
	  
	  
	  this.buttons = [];
		this.buttons.push({
			text : '保存',
			itemId : 'save',
			formBind: true, //only enabled once the form is valid
       		disabled: true,
			glyph : 0xf0c7,
			hidden:!Permision.canShow('sample_design_matesave'),
			handler : function(button){
				
				var formpanel = button.up('form');
				//var sampno_field=formpanel.getForm().findField("sampno");
				//sampno_field.setValue(window.sampno.);
				formpanel.updateRecord();
				formpanel.getForm().getRecord().save({
					failure: function(record, operation) {
				    },
				    success: function(record, operation) {
				    	Ext.Msg.alert("消息","保存成功!");
						//button.up('window').close();
				    	var sampleMateGrid=me.previousSibling("grid#sampleMateGrid");
				    	sampleMateGrid.getStore().reload();
				    	sampleMateGrid.getStore().sampleMateForm_saved_record=record;//用来表示是面料信息进行更新了，这个时候需要重新计算预计成本价
//				    	me.previousSibling("grid#sampleMateGrid").getStore().reload(
//				    		function(records, operation, success){
//				    			alert(1);
//				    			//触发成衣信息中的“预计成本价”的计算sumSpctpr,
//						    	var spctpr=window.sampleColthForm.sumSpctpr();
//						    	Ext.Ajax.request({
//						    		url:Ext.ContextPath+"/sampleColth/updateSpctpr.do",
//						    		params:{
//						    			sampno:record.get("sampno"),
//						    			spctpr:spctpr
//						    		},
//						    		success:function(response){
//						    			var obj=Ext.decode(response.responseText);
//						    			if(obj.success==false){
//						    				Ext.Msg.alert("消息","更新成衣信息中的预计成本价失败!请去手工保存成衣信息!");
//						    				return;
//						    			}
//						    		}
//						    	});
//				    		}
//				    	);
				    	
				    	
				    }
				});			
				
				}
			});
	me.on("render",function(panel){
		panel.mask();	
	});
      me.callParent();
	},
	lockOrUnlock:function(matest){
		//console.log(matest);
		var me=this;
		if(matest==1){
			me.down("#save").hide();
		} else if(Permision.canShow('sample_design_designsave')){
			me.down("#save").show();
		}
	}
});
