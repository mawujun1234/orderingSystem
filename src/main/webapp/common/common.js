Ext.Ajax.timeout=60000000;
//Ext.Ajax.defaultHeaders={ 'Accept':'application/json;'};
Ext.Ajax.on({
	requestexception:function(conn, response, options, eOpts ){
		var status = response.status;
 		var text = response.responseText;
 		switch (status) {
 			case 400 :
 				Ext.MessageBox.alert("错误", "请检查是否有数据未输入!" );
				break;
 			case 401 :
 				//表示Unauthorized ，没有登录
 				alert("用户未登录");
				break;
			case 403 :
				//表示没有权限
				Ext.MessageBox.alert("错误", "没有权限访问!" );
			case 404 :
				top.Ext.MessageBox.alert("错误", "加载数据时发生错误:请求url不可用");
				break;
			case 200 :
				if (text.length > 0) {
					var data = Ext.decode(text);
					if (data && data.error) {
						top.Ext.MessageBox.alert("错误", "加载数据时发生错误:<br/>"
										+ data.error);
					} else {
						top.Ext.MessageBox
								.alert("错误", "加载数据时发生错误:<br/>" + text);
					}
				}
				break;
			case 0 :
				top.Ext.MessageBox.alert("错误", "加载数据时发生错误:<br/>" + "远程服务器无响应");
				break;
			default :
				var data = Ext.decode(text);
				if (data && data.errorMsg) {
					//top.Ext.MessageBox.alert("错误", "加载数据时发生错误<br/>错误码:"+ status + "<br/>错误信息:" + data.message);
					top.Ext.MessageBox.alert("错误",  data.errorMsg);
				} else {
					top.Ext.MessageBox.alert("错误",  "操作失败，请稍候重试!如果多次操作失败，请联系管理员!");
				}

				break;
		}
	}
});

//创建读取公共属性的combobox
Ext.define('y.common.PubCode',{
	extend:'Ext.form.field.ComboBox',
	xtype:'pubcodecombo',
	//fieldLabel: '角色类型',
	//		name: 'roleType',
	//value:'rolegroup',
	tyno:'',
	selFirst:false,
	//fitno:'',
	autoLoad:true,
	
	
	
	queryMode: 'local',
	editable:false,
	forceSelection:true,
	displayField: 'itnm',
	valueField: 'itno',
//    allowBlank: false,
//    afterLabelTextTpl: Ext.required,
//    blankText:"菜单类型不允许为空",
	initComponent: function () {
		var me=this;

		if(!me.tyno){
			alert("设置tyno属性！");
			return;
		}
//		if(!me.allowBlank){
//			me.afterLabelTextTpl=Ext.required;
//			me.blankText=me.fieldLabel+"不允许为空";
//		}
		
		me.store=Ext.create('Ext.data.Store',{
			fields: ['itno', 'itnm'],
			autoLoad:me.autoLoad,
			proxy: {
			    
			    type: 'ajax',
			    extraParams:{
			    	tyno:me.tyno
					//fitno:me.fitno
			    },
			    url: Ext.ContextPath+'/pubCodeType/query4Combo.do',
			    reader: {
			        type: 'json'
			        //rootProperty: '${propertyColumn.property}'
			    }
			}
			
		});
		
		if(!me.value && me.selFirst){
			me.store.on("load",function(myStore){
				if(myStore.getCount( ) >0){
			 		var r=myStore.getAt(0);
			 		me.select( r );
			 	}
			})
		}
		me.callParent();
	},
	reload:function(fitno){
		var me=this;
		me.reloaded=false;
		if(!fitno){
			alert("请输入fitno参数!");
			return;
		}
		
		me.getStore().getProxy().extraParams=Ext.apply(me.getStore().getProxy().extraParams,{
	        			fitno:fitno
	    })
	    me.getStore().reload();
	    me.reloaded=true;
	}
	
	
});


Ext.define('y.common.OrdmtCombo',{
	extend:'Ext.form.field.ComboBox',
	xtype:'ordmtcombo',
	//fieldLabel: '角色类型',
	emptyText:'请选择订货会',
	//		name: 'roleType',
	autoLoad:true,
	queryMode: 'local',
	editable:false,
	forceSelection:true,
	displayField: 'ormtnm',
	valueField: 'ormtno',
//    allowBlank: false,
//    afterLabelTextTpl: Ext.required,
//    blankText:"菜单类型不允许为空",
	initComponent: function () {
		var me=this;

		me.store=Ext.create('Ext.data.Store',{
			fields: ['ormtno', 'ormtnm'],
			autoLoad:me.autoLoad,
			proxy: {
			    
			    type: 'ajax',
			    url: Ext.ContextPath+'/ordmt/query4Combo.do',
			    reader: {
			        type: 'json'
			        //rootProperty: '${propertyColumn.property}'
			    }
			},
			listeners:{
				load:function(myStore){
					if(myStore.getCount( ) >0){
			 			var r=myStore.getAt(0);
			 			me.select( r );
			 		}
				}
			}
			
		});
		me.callParent();
	}
});


Ext.define('y.common.PubSunoCombo',{
	extend:'Ext.form.field.ComboBox',
	xtype:'pubsunocombo',
	//fieldLabel: '角色类型',
	emptyText:'可输入关键字过滤',
	//		name: 'roleType',
	autoLoad:true,
	queryMode: 'local',
	editable:true,
	forceSelection:true,
	displayField: 'idsunm',
	valueField: 'idsuno',
//    allowBlank: false,
//    afterLabelTextTpl: Ext.required,
//    blankText:"菜单类型不允许为空",
	initComponent: function () {
		var me=this;

		me.store=Ext.create('Ext.data.Store',{
			fields: ['idsuno', 'idsunm'],
			autoLoad:me.autoLoad,
			proxy: {
			    
			    type: 'ajax',
			    url: Ext.ContextPath+'/pubSuno/query4Combo.do',
			    reader: {
			        type: 'json'
			        //rootProperty: '${propertyColumn.property}'
			    }
			},
			listeners:{
				load:function(myStore){
					if(myStore.getCount( ) >0){
			 			var r=myStore.getAt(0);
			 			me.select( r );
			 		}
				}
			}
			
		});
		me.callParent();
	}
});