Ext.define('y.sample.SampleDesignForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.sample.SampleDesign',
	     'y.sample.SampleDesignSizegpGrid'
	],
	
    frame: true,
    autoScroll : true,
	buttonAlign : 'center',
    bodyPadding: '5 0 0',


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
	        fieldLabel: '企划样衣id',
	        name: 'plspno',
	        hidden:true,
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"企划样衣编号不允许为空",
//            selectOnFocus:true,
	        xtype:'textfield'
	    },{
	        fieldLabel: '企划样衣编号',
	        name: 'plspnm',
            allowBlank: false,
            readOnly:true,
            afterLabelTextTpl: Ext.required,
            blankText:"企划样衣编号不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },{
	        fieldLabel: '出样样衣编号',
	        name: 'sampnm1',
	        itemId: 'sampnm1',
            allowBlank: false,
            //readOnly:sampnm_readOnly,
            afterLabelTextTpl: Ext.required,
            blankText:"出样样衣编号不允许为空",
            selectOnFocus:true,
	        xtype:'textfield'
	    },{
	        fieldLabel: '订货样衣编号',
	        name: 'sampnm',
            allowBlank: false,
            //readOnly:sampnm_readOnly,
            afterLabelTextTpl: Ext.required,
            blankText:"订货样衣编号不允许为空",
            selectOnFocus:true,
	        xtype:'textfield',
	        listeners:{
	        	change:function(field, newValue, oldValue){
	        		//var sampnm1=field.nextSibling("#sampnm1");
	        		//sampnm1.setValue(newValue);
	        	}
	        }
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
            allowBlank: true,
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
            queryMode: 'remote',
	        xtype:'pubcodecombo',
	        tyno:'21'
	    },
	    {
			fieldLabel: '设计师',
			name: 'desgno',
			queryMode: 'local',
			editable:true,
			allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"设计师不允许为空",
            selectOnFocus:true,
			forceSelection:true,
		    displayField: 'name',
		    valueField: 'id',
		    store: {
		    	autoLoad:true,
			    fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	extraParams:{positionType_id:'sjs'},
			    	url:Ext.ContextPath+'/user/querySjs.do'
			    }
			},
            afterLabelTextTpl: Ext.required,
            blankText:"职位类型不允许为空",
			xtype:'combobox'
		},
		{
	        fieldLabel: '外买样衣编号',
	        name: 'buspno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"外买样衣编号不允许为空",
            selectOnFocus:true,
            value:'无',
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '生产类型',
	        name: 'spmtno',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"生产类型不允许为空",
            selectOnFocus:true,
	        xtype:'pubcodecombo',
	        tyno:'29',
	        listeners:{
	       		change:function(field, newValue, oldValue){
	       			var gustnoField=field.nextSibling("textfield[name='gustno']");
	       			var form=field.up("form");
	       			if(newValue=='WG'){
	       				var aa=Ext.create('Ext.form.field.Text',{
					        fieldLabel: '客供编号',
					        name: 'gustno',
				            allowBlank: false,
				            afterLabelTextTpl: Ext.required,
				            blankText:"客供编号不允许为空",
				            selectOnFocus:true,
					        xtype:'textfield'
					    });
	       				form.remove(gustnoField,true);
	       				//form.add(aa);
	       				form.moveAfter( aa, field );
	       				//外购的时候，预计成本价是自己填写，而不是计算出来的
	       				var tabpanel=field.up("tabpanel");
	       				var sampleColthForm=tabpanel.down("form#sampleColthForm");
	       				sampleColthForm.getForm().findField("spctpr").setReadOnly(false);
	       				sampleColthForm.spctpr_sum=false;
	       			} else if(newValue=='ZC') {
	       				form.remove(gustnoField,true);
	       				
	       				var tabpanel=field.up("tabpanel");
	       				var sampleColthForm=tabpanel.down("form#sampleColthForm");
	       				sampleColthForm.getForm().findField("spctpr").setReadOnly(true);
	       				sampleColthForm.spctpr_sum=true;
	       			}
	       		}
	        
	        }
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
            blankText:"颜色不允许为空",
	        xtype:'pubcodecombo',
	        queryMode: 'remote',
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
	        fieldLabel: '规格版型说明',
	        name: 'desp',
            allowBlank: false,
            afterLabelTextTpl: Ext.required,
            blankText:"规格版型说明不允许为空",
            selectOnFocus:true,
	        xtype:'textareafield',
	        grow      : true
	    },


//	    {
//            xtype      : 'fieldcontainer',
//            fieldLabel : '包装要求',
//            defaultType: 'radiofield',
//            defaults: {
//                flex: 1
//            },
//            layout: 'hbox',
//            items: [
//            	{
//                    boxLabel  : '包装',
//                    name: 'packqt',
//                    checked:true,
//                    inputValue: '1'
//                },
//           		{
//                    boxLabel  : '不包装',
//                    name: 'packqt',
//                    inputValue: '0'
//                }
//            ]
//        },
	    {
	        fieldLabel: '包装要求',
	        name: 'packqt',
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'  ,
	         value:1
	    },
	     {
	        fieldLabel: '必定',
	        name: 'abstat',
            hidden:true,
	        xtype:'numberfield'  ,
	         value:0
	    },

       
		{
	        fieldLabel: '吊牌打印标志',
	        name: 'print',
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield',
	        fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        value:0
	    },
//	     {
//            xtype      : 'fieldcontainer',
//            fieldLabel : '是否拆套',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"套装种类不允许为空",
//            itemId:'spltmk_container',
//            defaultType: 'radiofield',
//            defaults: {
//                flex: 1
//            },
//            layout: 'hbox',
//            items: [
//            	{
//                    boxLabel  : '是',
//                    name: 'spltmk',
//                    inputValue: '1'
//                },
//           		{
//                    boxLabel  : '否',
//                    name: 'spltmk',
//                    checked:true,
//                    inputValue: '0'
//                }
//            ]
//        },
//	    {
//	        fieldLabel: '套装种类',
//	        name: 'suitty',
//            allowBlank: false,
//            afterLabelTextTpl: Ext.required,
//            blankText:"套装种类不允许为空",
//            xtype:'pubcodecombo',
//	        tyno:'20',
//	        selFirst:true,
//	        listeners:{
//	        	select:function(combo, record){
//	        		var form=combo.up("form");
//	        		var sampleDesignSizegpGrid_store=form.getSampleDesignSizegpGrid().getStore();
//	        		sampleDesignSizegpGrid_store.removeAll();
////					sampleDesignSizegpGrid_store.getProxy().extraParams={
////						suitty:record.get("itno"),
////						sampno:window.sampno.sampno
////						
////					};
//					sampleDesignSizegpGrid_store.reload({params:{
//						suitty:record.get("itno"),
//						sampno:window.sampno.sampno
//						
//					}});
//					
//					//
//	        	}
//	        }
//	    },
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
	        xtype:'numberfield'  ,
	        value:1
	    },
		{
	        fieldLabel: '锁定状态',
	        name: 'spstat',
            hidden:true,
            allowDecimals:false,
	        xtype:'numberfield'   
	    }
//		{
//	        fieldLabel: '创建人',
//	        name: 'rgsp',
//            hidden:true,
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
//		{
//            fieldLabel: '创建日期',
//            name: 'rgdt',
//            hidden:true,
//            editable:false,
//            xtype: 'datefield',
//            format: 'Y-m-d'   
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
	  
	 var sampleDesignSizegpGrid=Ext.create('y.sample.SampleDesignSizegpGrid',{
	  	itemId:'sampleDesignSizegpGrid'
	  });
	  var fieldset={
        // Fieldset in Column 1 - collapsible via toggle button
        xtype:'fieldset',
        //columnWidth: 0.5,
        title: '套件规格',
        itemId:'sampleDesignSizegpGrid_fieldset',
        collapsible: true,
        //defaultType: 'textfield',
        defaults: {anchor: '100%'},
        layout: 'anchor',
        items :[sampleDesignSizegpGrid]
      }
	  me.items.push(fieldset);
	  
	  
	  this.buttons = [];
		this.buttons.push({
			text : '保存',
			itemId : 'save',
			formBind: true, //only enabled once the form is valid
       		//disabled: true,
			hidden:!Permision.canShow('sample_design_designsave'),
			glyph : 0xf0c7,
			handler : function(button){
				var formpanel = button.up('form');
				if(!formpanel.isValid()){
					alert("请先填写必填项!");
					return;
				}
				//formpanel.updateRecord();
				//var record=button.up('form').getForm().getRecord();
				var sampleDesignSizegpes=sampleDesignSizegpGrid.getStore().getRange();
				
				var aa=[];
				for(var i=0;i<sampleDesignSizegpes.length;i++){
					if(!sampleDesignSizegpes[i].get("sizegp")){
						Ext.Msg.alert("消息","规格范围必须选择!");
						return;
					}
					aa.push({
						//sampno:sampleDesignStpres[i].get("getSampno"),
						suitno:sampleDesignSizegpes[i].get("suitno"),
						sizegp:sampleDesignSizegpes[i].get("sizegp")
					});
				}
				//record.set("sampleDesignStpres",aa);
				var jsonData=formpanel.getForm().getFieldValues();
				jsonData.ormtno=window.ormtno;
				jsonData.sampleDesignSizegpes=aa;
				var plspnm=jsonData.plspnm;
				
				var url="/sampleDesign/create.do";
		       //var sampnm_readOnly=false;
				if(window.sampleDesign &&　window.sampleDesign.get("sampno")){
					url="/sampleDesign/update.do";
				}
				//如果有复制，就使用复制的
				if(window.sampleDesignForm_url_dfgdfg){
					url=window.sampleDesignForm_url_dfgdfg;
				}
				
				
				//console.log(aa);
				Ext.Ajax.request({
					url:Ext.ContextPath+url,
					actionMethods: { read: 'POST' },
					timeout :600000,
					headers:{ 'Accept':'application/json;'},
					jsonData:jsonData,
					success:function(response){
						var obj=Ext.decode(response.responseText);
						Ext.Msg.alert("消息","保存成功!");
						//button.up('window').close();
				    	//formpanel.reset();
				    	//samplePlanStprGrid.getStore().removeAll();
				    	var tabpanel=formpanel.up("tabpanel");
						//tabpanel.unmask();
						var sampleDesignGrid=tabpanel.previousSibling("gridpanel#sampleDesignGrid") ;
						
						if(url=="/sampleDesign/create.do"){//如果是新建的时候，就只查出这个一个
//							sampleDesignGrid.getStore().getProxy().extraParams={
//								params:{"params['sampno']":obj.sampleDesign.sampno}
//							}
							sampleDesignGrid.getStore().iscreate=true;
							sampleDesignGrid.getStore().reload();
						}
						
						//用于后面的面料信息
						window.sampno={
							sampno:obj.sampleDesign.sampno,//,record.get("sampno"),
							sampnm:obj.sampleDesign.sampnm//record.get("sampnm")
						};
						
						//var tabpanel=btn.up("tabpanel");
				       	tabpanel.items.getAt(2).enable();
				       	tabpanel.items.getAt(3).enable();
				       	tabpanel.items.getAt(4).enable();
				       	
				       	if(url=="/sampleDesign/create.do"){
				       		var sampleMate=Ext.create('y.sample.SampleMate',{
								sampno:window.sampno.sampno,
								sampnm:window.sampno.sampnm
							});
							tabpanel.down("form#sampleMateForm").loadRecord(sampleMate);
							
							var　samplePlanFormQuery=tabpanel.down("form#samplePlanFormQuery");
							var sampleColth=Ext.create('y.sample.SampleColth',{
								sampno:window.sampno.sampno,
								sampnm:window.sampno.sampnm,
								spftpr:samplePlanFormQuery.getForm().findField("spftpr").getValue(),//出厂价
								sprtpr:samplePlanFormQuery.getForm().findField("sprtpr").getValue()//零售价
								
							});
							tabpanel.down("form#sampleColthForm").loadRecord(sampleColth);
				       	}
				       	
				       	//这里需要测试，当新建成功后，成衣里面能不能出现标准的套件
				       	//formpanel.getForm().updateRecord();
				       	obj.sampleDesign.plspnm=plspnm;
				       	var user = Ext.create('y.sample.SampleDesign', obj.sampleDesign);
				       	user.set("sampst",1);
				       	tabpanel.down("#sampleColthForm").loadGrid(user);
				       	window.sampleDesign=user;
				       	
				       	//formpanel.getForm().findField("sampno").setValue(obj.sampleDesign.sampno);
				       	formpanel.getForm().findField("sampnm").setReadOnly(true);
				       	
				       	me.loadRecord(user);
				       	me.fireEvent("create",user);
					}
					
				});
		
				
				}
			});
      me.callParent();
	},
	loadRecord:function(record){
		var me=this;
		//如果小类是套西的话，就隐藏套装种类
		if(window.sampleDesign.get("sptyno")=='S10'){
			//me.showsampleDesignSizegpGrid_bool=true;
			me.showsampleDesignSizegpGrid(true,record.get("suitty"));
		} else {
			//me.showsampleDesignSizegpGrid_bool=false;
			me.showsampleDesignSizegpGrid(false,record.get("suitty"));
		}
		
		//if(me.showsampleDesignSizegpGrid_bool){
			var sampleDesignSizegpGrid_store=this.getSampleDesignSizegpGrid().getStore();
			sampleDesignSizegpGrid_store.removeAll();
		if(record.get("suitty")){//排除新建的时候，第一次选择套装类型，不会更新的问题
			sampleDesignSizegpGrid_store.click_loading=true;
		}
				

		
		me.getForm().loadRecord(record);
		
		me.showOrHidden_saveButton(record.get("spstat"));
					
		
				
		sampleDesignSizegpGrid_store.reload({
			params:{
				suitty:record.get("suitty"),
				sampno:record.get("sampno")//window.sampno.sampno
			}
		});
		
	},
	/**
	 * 根据品牌重新刷新所有的衣服属性
	 * @param {} bradno 品牌
	 * @param {} spclno 大类
	 */
	temp_bradno:'Y',//这是临时解决的
	reloadEditor:function(ormtno,bradno,spclno){
		//刷新规格组的数据
		if(this.temp_bradno!=bradno || this.temp_spclno!=spclno){
			this.getSampleDesignSizegpGrid().reloadEditor(ormtno,bradno,spclno);
		}
		
		this.up("#sample_tabpanel").mask();
		//刷新工作室系列
		this.reloadStseno(spclno);
		var stsenoField=this.getForm().findField("stseno");
		stsenoField.getStore().reload();
	},
	getSampleDesignSizegpGrid:function(){
		if(this.sampleDesignSizegpGrid){
			return this.sampleDesignSizegpGrid;
		}
		this.sampleDesignSizegpGrid=this.down("grid#sampleDesignSizegpGrid");
		return this.sampleDesignSizegpGrid;
	},
	/**
	 * 显示或隐藏按钮，
	 * @param {} spstat 是否锁定，如果锁定了就不能显示
	 */
	showOrHidden_saveButton:function(spstat){
		var me=this;
		if(spstat==1){
			me.down("#save").hide();		
		} else if(Permision.canShow('sample_design_designsave')){
			me.down("#save").show();
		}
	},
	stat_xtrydeeeeeeeee:1,
	reloadPubcode:function(bradno){
		if(!bradno){
			alert("请传递品牌参数");
			return;
		}
		
		var me=this;
		
		
		//var stat_xtrydeeeeeeeee=spstat==1?0:1;
		
		if(this.temp_bradno!=bradno || me.stat_xtrydeeeeeeeee!=window.stat_xtrydeeeeeeeee){//alert(stat_xtrydeeeeeeeee);
			me.stat_xtrydeeeeeeeee=window.stat_xtrydeeeeeeeee;
			var versnoField=me.getForm().findField("versno");
			versnoField.changeBradno(bradno);
			versnoField.getStore().reload();
			
			var stsenoField=me.getForm().findField("stseno");
			stsenoField.changeBradno(bradno);
			stsenoField.getStore().reload();
			
			var spmtnoField=me.getForm().findField("spmtno");
			spmtnoField.changeBradno(bradno);
			spmtnoField.getStore().reload();
			
			var colrnoField=me.getForm().findField("colrno");
			colrnoField.changeBradno(bradno);
			colrnoField.getStore().reload();
			
			var pattnoField=me.getForm().findField("pattno");
			pattnoField.changeBradno(bradno);
			pattnoField.getStore().reload();
			
			var stylnoField=me.getForm().findField("stylno");
			stylnoField.changeBradno(bradno);
			stylnoField.getStore().reload();
			
			var sexnoField=me.getForm().findField("sexno");
			sexnoField.changeBradno(bradno);
			sexnoField.getStore().reload();
			
			var slvenoField=me.getForm().findField("slveno");
			slvenoField.changeBradno(bradno);
			slvenoField.getStore().reload();
			
			var suittyField=me.getForm().findField("suitty");
			if(suittyField){
				suittyField.changeBradno(bradno);
				suittyField.getStore().reload();
			}
			
			
			this.temp_bradno=bradno;
		}
		

		
	},
	/**
	 * 根据大类加载工作室系列
	 * @param {} spclno
	 */
	reloadStseno:function(spclno){
		var stsenoField=this.getForm().findField("stseno");
		stsenoField.getStore().getProxy().extraParams=Ext.apply(stsenoField.getStore().getProxy().extraParams,{
			fitno:spclno
		});
		//stsenoField.reload(spclno);
	},
	reset:function(){
		this.getForm().reset();
		var sampleDesignSizegpGrid_store=this.getSampleDesignSizegpGrid().getStore();
		sampleDesignSizegpGrid_store.removeAll();
	},
	//如果小类是套西的时候，就显示套装种类
	showsampleDesignSizegpGrid:function(bool,suitty){
		var me=this;
		this.showsampleDesignSizegpGrid_bool=bool;
		//小类为套西时，是否拆套和套装种类 必填，其他情况不显示；
		if(!bool){
			var suittyField=me.getForm().findField("suitty");
			//suittyField.hide();
			me.remove(suittyField,true);

			
			var spltmk_container=me.down("#spltmk_container");
			//spltmk_container.hide();
			me.remove(spltmk_container,true);
			

		} else {
			var suittyField=me.getForm().findField("suitty");
//			suittyField.show();
//			
//			var spltmk_container=me.down("#spltmk_container");
//			spltmk_container.show();
			if(!suittyField){
				me.add([{
		            xtype      : 'fieldcontainer',
		            fieldLabel : '是否拆套',
		            allowBlank: false,
		            afterLabelTextTpl: Ext.required,
		            blankText:"套装种类不允许为空",
		            itemId:'spltmk_container',
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
			        fieldLabel: '套装种类',
			        name: 'suitty',
			        itemId:'suitty',
		            allowBlank: false,
		            showBlank:false,
		            afterLabelTextTpl: Ext.required,
		            blankText:"套装种类不允许为空",
		            xtype:'pubcodecombo',
			        tyno:'20',
			        selFirst:false,
			        value:suitty,
			        listeners:{
			        	select:function(combo, record){
			        		var form=combo.up("form");
			        		var sampleDesignSizegpGrid_store=form.getSampleDesignSizegpGrid().getStore();
			        		if(sampleDesignSizegpGrid_store.click_loading==true){
			        			sampleDesignSizegpGrid_store.click_loading=false;
			        			return;
			        		}
			        		sampleDesignSizegpGrid_store.removeAll();
		//					sampleDesignSizegpGrid_store.getProxy().extraParams={
		//						suitty:record.get("itno"),
		//						sampno:window.sampno.sampno
		//						
		//					};
			        		//console.log("==========="+(new Date()).getTime()+"===="+record.get("itno"));
							sampleDesignSizegpGrid_store.reload({params:{
								suitty:record.get("itno"),
								sampno:window.sampno.sampno
								
							}});
							
							//
			        	}
			        }
			    }]);
			    var sampleDesignSizegpGrid_fieldset=me.down("#sampleDesignSizegpGrid_fieldset");
		    	me.moveBefore( me.down("#spltmk_container"), sampleDesignSizegpGrid_fieldset );
		    	me.moveBefore( me.down("#suitty"), sampleDesignSizegpGrid_fieldset )
			}
		    
		    
			

		}
		
		var sampleDesignSizegpGrid_store=this.getSampleDesignSizegpGrid().getStore();
		sampleDesignSizegpGrid_store.removeAll();
		sampleDesignSizegpGrid_store.reload();
	}
});
