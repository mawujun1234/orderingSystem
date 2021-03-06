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
       me.show_SelectOrdorgTree=false;
       
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
                    hidden:true,
                    name      : 'addModel',
                    inputValue: 'allQY'
                }, {
                    boxLabel  : '所有特许',
                    hidden:true,
                    name      : 'addModel',
                    inputValue: 'allTX'
                }
            ],
            listeners:{
            	change:function( radiogroup, newValue, oldValue, eOpts ) {
            		
            		//alert(newValue.id);
            		if(newValue.addModel=='single'){
            			radiogroup.nextSibling("#ordorg_fieldcontainer").show();
            			radiogroup.nextSibling("#ordorg_fieldcontainer").down("#ordorg_name").setValue("");
            		} else {
            			radiogroup.nextSibling("#ordorg_fieldcontainer").hide();
            			radiogroup.nextSibling("#ordorg_fieldcontainer").down("#ordorg_name").setValue(newValue.addModel);
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
	        xtype: 'fieldcontainer',
	        itemId:"ordorg_fieldcontainer",
	        fieldLabel: '订货单位',
	        labelWidth: 70,
			allowBlank: false,
	        afterLabelTextTpl: Ext.required,
	        blankText:"订货单位不允许为空",
	        // The body area will contain three text fields, arranged
	        // horizontally, separated by draggable splitters.
	        layout: 'hbox',
	        items: [{
	//	        fieldLabel: '订货单位',
	//	        labelWidth: 65,
		        name: 'ordorg_name',
		        itemId: 'ordorg_name',
		        allowBlank: false,
		        afterLabelTextTpl: Ext.required,
		        blankText:"订货单位不允许为空",
	            selectOnFocus:true,
	            readOnly:true,
	            fieldStyle:'background-color:#CDC9C9;',
		        xtype:'textfield'
//		        listeners:{
//		        	activate:function(field){
//		        		console.log(1);
//		        		if(me.show_SelectOrdorgTree==true){
//		        			return;
//		        		}
//		        		me.show_SelectOrdorgTree=true;
//		        		me.selectOrdOrg(field);
//		        	}
//		        }
		    },{
		    	xtype:'button',
		    	text:'选择订货单位',
		    	margin :'0 0 0 10',
		    	handler:function(btn){
		    		btn.up("form").selectOrdOrg(btn.previousSibling("textfield[name='ordorg_name']"));
		    	}
		    
		    },{
		        fieldLabel: '订货单位',
		        name: 'ordorg',
		        xtype:'textfield',
				hidden:true
		    }]
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
				Ext.getBody().mask("正在执行，请稍候.....");
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
						Ext.getBody().unmask();
						if(obj.success==false){
							Ext.Msg.alert("消息",obj.msg);
							return;
						}
						button.up('window').close();
						//me.grid.getStore().reload();
						me.grid.onReload();
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
			style: {
	            "z-index":190100
	        },
			items:[orgTree],
			listeners:{
				close:function(){
					me.show_SelectOrdorgTree=false;
				}
			}
		});
		win.show();
		//win.focus();
		//alert(1);
	}
});
