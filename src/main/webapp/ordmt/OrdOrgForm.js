Ext.define('y.ordmt.OrdOrgForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.ordmt.OrdOrg'
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
//		{
//	        fieldLabel: '订货会编号',
//	        name: 'ormtno',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
        {
            xtype      : 'radiogroup',
            fieldLabel : '新增模式',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'hbox',
            items: [
                {
                    boxLabel  : '单个组织',
                    name      : 'addModel',
                    inputValue: 'single',
                    checked:true
                }, {
                    boxLabel  : '所有区域',
                    name      : 'addModel',
                    inputValue: 'allQY'
                }, {
                    boxLabel  : '所有特许',
                    name      : 'addModel',
                    inputValue: 'allTX'
                }
            ],
            listeners:{
            	change:function( radiogroup, newValue, oldValue, eOpts ) {
            		
            		//alert(newValue.id);
            		if(newValue.addModel=='single'){
            			radiogroup.nextSibling("textfield[name='ordorg_name']").show();
            			radiogroup.nextSibling("textfield[name='ordorg_name']").setValue("");
            		} else {
            			radiogroup.nextSibling("textfield[name='ordorg_name']").hide();
            			radiogroup.nextSibling("textfield[name='ordorg_name']").setValue(newValue.addModel);
            		}
            		if(newValue.addModel=='allQY'){
 						var sztype_combo=radiogroup.nextSibling("combobox[name='sztype']");
 						sztype_combo.setValue(0);
            		} else if(newValue.addModel=='allTX'){
            			var sztype_combo=radiogroup.nextSibling("combobox[name='sztype']");
 						sztype_combo.setValue(1);
            		}
            	}
            }
        },
		{
	        fieldLabel: '订货单位',
	        name: 'ordorg_name',
	        allowBlank: false,
	        afterLabelTextTpl: Ext.required,
	        blankText:"订货单位不允许为空",
            selectOnFocus:true,
	        xtype:'textfield',
	        listeners:{
	        	focus:function(field){
	        		me.selectOrdOrg(field);
	        	}
	        }
	    },
	    {
	        fieldLabel: '订货单位',
	        name: 'ordorg',
	        xtype:'textfield',
			hidden:true
	    },
//		{
//	        fieldLabel: '订货会类型',
//	        name: 'channo',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
		{
	        fieldLabel: '上报方式',
	        name: 'sztype',
            queryMode: 'local',
            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"上报方式不允许为空",
			editable:false,
			forceSelection:true,
			displayField: 'name',
			valueField: 'id',
			value:'0',
			store: {
				    fields: ['id', 'name'],
				    autoLoad:false,
				    data:[{id:'0',name:'规格+包装上报'},{id:'1',name:'规格上报'},{id:'2',name:'包装上报'}]
			},
	        hidden:false,
			xtype:'combobox'  
	    },{
				fieldLabel: '角色',
				labelWidth:65,
				width:170,
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"角色不允许为空",
				name: 'role_id',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'id',
			    store: {
				    fields: ['id', 'name'],
				    autoLoad:true,
				    proxy: {
				        type: 'ajax',
				        url: Ext.ContextPath+'/role/query4Combo.do'
				    }
				},
	            hidden:false,
				xtype:'combobox'
		}
//		{
//	        fieldLabel: '打印状态',
//	        name: 'print',
//            allowDecimals:false,
//            selectOnFocus:true,
//	        xtype:'numberfield'   
//	    }
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
				var values=formpanel.getForm().getValues();
				//formpanel.updateRecord();
				Ext.Ajax.request({
					url:Ext.ContextPath+'/ordOrg/create.do',
					method:'POST',
					params:{
						addModel:values.addModel,
						ordorg:values.ordorg,
						sztype:values.sztype,
						role_id:values.role_id
					},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						if(obj.success==false){
							Ext.Msg.alert("消息",obj.msg);
							return;
						}
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
	},
	selectOrdOrg:function(field){
		var me=this;
		var orgTree=Ext.create('y.org.OrgTreeQuery',{
			
			listeners:{
				itemdblclick:function(view, record, item, index, e, eOpts){
					field.setValue(record.get("name"));
					field.nextSibling("textfield[name='ordorg']").setValue(record.get("id"));
					win.close();
				}
			}
		});
		var win=Ext.create('Ext.window.Window',{
			layout:'fit',
			title:'双击选择',
			modal:true,
			width:350,
			height:560,
			items:[orgTree]
		});
		win.show();
	}
});
