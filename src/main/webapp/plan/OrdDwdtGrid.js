Ext.define('y.plan.OrdDwdtGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	selModel: {
          selType: 'checkboxmodel'
          ,checkOnly:true
    },
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'SPTYNM',header:'小类'
        },
        {dataIndex:'SPSENM',header:'系列'
        },
        {dataIndex:'SPBANM',header:'上市批次'
        },
        {dataIndex:'SAMPNM',header:'设计样衣编号'
        },
        {dataIndex:'PRODNM',header:'货号名称'
        },
        {dataIndex:'ORTYNM',header:'订单类型'
        },
        {dataIndex:'IDSUNM',header:'生产单位'
        },
        {dataIndex:'YXGSNM',header:'营销公司'
        },
        {dataIndex:'QYNM',header:'区域'
        },
        {dataIndex:'SUITNM',header:'套件'
        },
        {dataIndex:'ORMTQT',header:'数量'
        },
        {dataIndex:'PLDATE_COUNT',header:'交货批次数'
        },
        {dataIndex:'MLDATE',header:'面料交货期'
        },
        {dataIndex:'PLDATE',header:'成衣交货期'
        },
        {dataIndex:'PPLACE',header:'产地'
        }
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			fields:['ORDORG','SPTYNM','SPSENM','SPBANM','SAMPNO','SAMPNM','PRODNM','ORTYNM','YXGSNM','QYNM','IDSUNM','SUITNO','SUITNM','ORMTQT','PLDATE_COUNT','PLDATE','MLDATE','PPLACE'],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ordDwdt/queryPager1.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });

	  me.dockedItems=[];
      me.dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
	   me.initReload_index_grid=1,
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
	  		//enableOverflow:true,
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				listeners:{
					select:function(combo , record , eOpts){
						window.ordmt_record=record;
						me.initReload_index_grid++;
						me.reload();
					}
				}
			},{
		        fieldLabel: '品牌',
		        itemId: 'bradno',
		        labelWidth:40,
		        width:160,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            //value:'Y',
	            showBlank:false,
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
					select:function(combo , record , eOpts){
						me.initReload_index_grid++;
						me.reload();
					}
				}
		    },{
		        fieldLabel: '大类',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
		        showBlank:false,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"大类不允许为空",
	             selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'0',
		        listeners:{
		        	select:function( combo, record, eOpts ) {
		        		var sptyno=combo.nextSibling("#sptyno");
		        		sptyno.reload(record.get("itno"));
		        		
		        		var spseno=combo.nextSibling("#spseno");
		        		spseno.reload(record.get("itno"));
		        		
		        		me.initReload_index_grid++;
						me.reload();
		        	}	
		        }
		    },{
		        fieldLabel: '小类',
		        itemId: 'sptyno',
		        labelWidth:40,
		        width:140,
	            autoLoad:false,
		        xtype:'pubcodecombo',
		        tyno:'2'
		    },
			{
		        fieldLabel: '系列',
		        itemId: 'spseno',
		        labelWidth:40,
		        width:160,
	            autoLoad:false,
		        xtype:'pubcodecombo',
		        tyno:'5'
		    },{
		        fieldLabel: '上市批次',
		        labelWidth:75,
		  		width:175,
		        itemId: 'spbano',
		        xtype:'pubcodecombo',
		        tyno:'23'
		    }]
		});
		
	 me.dockedItems.push({
	 	xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ortyno',
				xtype:'ordtycombo',
				labelWidth:65,
				 allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				//selFirst:true,
				width:150,
				value:'DZ',
				listeners:{
					select:function( combo, record, eOpts ) {
//						var ordorg=combo.nextSibling("#ordorg");
//		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
//		        			ortyno:record.get("ortyno")
//		        		});
//		        		ordorg.getStore().reload();
					}
				}
			},Ext.create('Ext.form.ComboBox', {
			    fieldLabel: '统计类型',
			    name: 'count_type',
		        itemId: 'count_type',
		        labelWidth:60,
		        width:150,
		        value:'sampno',
			    store: Ext.create('Ext.data.Store', {
				    fields: ['value', 'name'],
				    data : [
				    	{"value":"sampno", "name":"样衣"},
				        {"value":"yxgsno", "name":"到营销公司"},
				        {"value":"qyno", "name":"到区域"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'value'
			}),{
		  		fieldLabel: '营销公司',
		  		labelWidth:75,
		  		width:175,
		  		allowBlank: true,
	           // afterLabelTextTpl: Ext.required,
		  		selFirst:false,
		  		itemId:'yxgsno',
				xtype:'orgcombo',
				showBlank:true,
				listeners:{
					select:function( combo, record, eOpts ) {
						var regioncombo=combo.nextSibling("#qyno");
		        		regioncombo.reload(record.get("orgno"));
					}
				}
			},{
		  		fieldLabel: '区域',
		  		labelWidth:45,
		  		width:170,
//		  		allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
		  		selFirst:false,
		  		itemId:'qyno',
				xtype:'orgcombo',
				autoLoad:false,
				showBlank:true,
				listeners:{
					select:function( combo, record, eOpts ) {
		        		
//						var ordorg=combo.nextSibling("#ordorg");
//		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
//		        			qyno:record.get("orgno")
//		        		});
//		        		ordorg.getStore().reload();
					}
				}
			},{
				fieldLabel: '交货期',
				xtype:'datefield',
				itemId: 'sample_date',
				labelWidth:55,
				format: 'Y-m-d ',
		        width:160
			}]
	 });
	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[Ext.create('Ext.form.ComboBox', {
			    fieldLabel: '产地',
			    name: 'pplace',
		        itemId: 'pplace',
		        labelWidth:60,
		        width:150,
		        //value:'sampno',
			    store: Ext.create('Ext.data.Store', {
				    fields: ['value', 'name'],
				    data : [
				    	{"value":"", "name":"所有"},
				    	{"value":"宁波", "name":"宁波"},
				        {"value":"珲春", "name":"珲春"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'value'
			}),{
		    	emptyText:'请输入订货样衣编号',
		    	itemId: 'sampnm',
		    	width:90,
		    	xtype:'textfield'
		    	
		    },{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.reload();
				},
				iconCls: 'icon-refresh'
			},{
			   text: '指定面料交货期',
			   //hidden:!Permision.canShow('plan_orgdtl_import'),
				handler: function(btn){
					me.onMldate();
				},
				iconCls: 'icon-wrench'
			},{
		  		text: '指定成衣交货期',
				handler: function(btn){
					me.onPldate();
				},
				iconCls: 'icon-wrench'
		  	},{
			   text: '指定产地',
			   //hidden:!Permision.canShow('plan_orgdtl_import'),
				handler: function(btn){
					me.onPplace();
				},
				iconCls: 'icon-wrench'
			}]
		});
		
//		
//	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
//            clicksToEdit : 1  
//      });  
//	  this.plugins = [this.cellEditing];
//	  this.cellEditing.on("edit",function(editor, context){
//	  	var record=context.record;
//	  	var grid=context.grid;
//	  	var field =context.field ;
//	  	var value=context.value;
//	  	var originalValue =context.originalValue 
//	  	//console.log(record.getData());
//	  	Ext.Ajax.request({
//	  		url:Ext.ContextPath+'/sampleProd/createOrUpdate.do',
//	  		jsonData :record.getData(),
//	  		method:'POST',
//	  		success:function(response){
//	  			var obj=Ext.decode(response.responseText);
//	  			record.commit();
//	  			
//	  		}
//	  	
//	  	});
//		
//	  });

       
      me.callParent();
	},
	reload:function(){
		//当3个必须条件都初始化结束，就自动进行查询
		var grid=this;
		if(this.initReload_index_grid>=3){
			grid.getStore().getProxy().extraParams=grid.getParams();
			grid.getStore().reload();
		}
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var params={
				"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
				"params['bradno']":toolbars[0].down("#bradno").getValue(),
				"params['spclno']":toolbars[0].down("#spclno").getValue(),
				"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
				"params['spseno']":toolbars[0].down("#spseno").getValue(),
				"params['spbano']":toolbars[0].down("#spbano").getValue(),
				
				"params['ortyno']":toolbars[1].down("#ortyno").getValue(),
				"params['count_type']":toolbars[1].down("#count_type").getValue(),
				
				"params['yxgsno']":toolbars[1].down("#yxgsno").getValue(),
				"params['qyno']":toolbars[1].down("#qyno").getValue(),
				"params['sample_date']":toolbars[1].down("#sample_date").getValue(),
				"params['pplace']":toolbars[2].down("#pplace").getValue(),
				"params['sampnm']":toolbars[2].down("#sampnm").getValue()
		};
		return params;
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('y.sample.SampleProd',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.sample.SampleProdForm',{});
		formpanel.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    },
    onMldate:function(){
		var me=this;
		var modles=me.getSelection( ) ;
		if(!modles || modles.length==0){
			Ext.Msg.alert("消息","请选择一行或多行!");
			return;
		}	
				
		var extraParams=me.getStore().getProxy().extraParams;
		var datefield=Ext.create('Ext.form.field.Date',{
			fieldLabel: '交货期',
			labelWidth:55,
			format: 'Y-m-d '
		    //width:160
		});
		var win=Ext.create('Ext.Window',{
			layout:'form',
			title:'指定面料交货期',
			modal:true,
			items:[datefield],
			buttons:[{
				text:'取消',
				handler:function(){
					win.hide();
				}
			},{
				text:'确认',
				handler:function(){
					handler();
				}
			}]
		});
		win.show();
		//Ext.Msg.prompt("消息","是否对选中的数据指定面料交货期!",function(btn){
		function handler(){
			if(!datefield.getValue()){
				return;
			}
			var mldate=datefield.getRawValue();
			//if(btn=='yes'){
				
				Ext.getBody().mask("正在处理,请稍候.....");

				var dataes=[];
				for(var i=0;i<modles.length;i++){
					dataes.push({
						
						sampno:modles[i].get("SAMPNO"),
						suitno:modles[i].get("SUITNO"),
						field:'mldate',
						value:mldate
					});
				}
				
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/ordDwdt/updateField.do',
						    jsonData:dataes,
						    params:{
						    	ormtno:extraParams["params['ormtno']"],
								ortyno:extraParams["params['ortyno']"],
								count_type:extraParams["params['count_type']"],
								yxgsno:extraParams["params['yxgsno']"],
								qyno:extraParams["params['qyno']"]
						    },
						    method:'POST',
						    success:function(response){
						    	var obj=Ext.decode(response.responseText);
						    	Ext.getBody().unmask();
								if(obj.success==false){
									Ext.Msg.alert("消息",obj.msg);
									return;
								}
						    	me.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    	win.hide();
						    }
						   });
			//}
		}
		//});
	},
	onPldate:function(){
		var me=this;
		var modles=me.getSelection( ) ;
		if(!modles || modles.length==0){
			Ext.Msg.alert("消息","请选择一行或多行!");
			return;
		}	
				
		var extraParams=me.getStore().getProxy().extraParams;
		var datefield=Ext.create('Ext.form.field.Date',{
			fieldLabel: '交货期',
			labelWidth:55,
			format: 'Y-m-d '
		    //width:160
		});
		var win=Ext.create('Ext.Window',{
			layout:'form',
			title:'指定成衣交货期',
			modal:true,
			items:[datefield],
			buttons:[{
				text:'取消',
				handler:function(){
					win.hide();
				}
			},{
				text:'确认',
				handler:function(){
					handler();
				}
			}]
		});
		win.show();
		//Ext.Msg.prompt("消息","是否对选中的数据指定面料交货期!",function(btn){
		function handler(){
			if(!datefield.getValue()){
				return;
			}
			var mldate=datefield.getRawValue();
			//if(btn=='yes'){
				
				Ext.getBody().mask("正在处理,请稍候.....");

				var dataes=[];
				for(var i=0;i<modles.length;i++){
					dataes.push({
						
						sampno:modles[i].get("SAMPNO"),
						suitno:modles[i].get("SUITNO"),
						field:'pldate',
						value:mldate
					});
				}
				
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/ordDwdt/updateField.do',
						    jsonData:dataes,
						    params:{
						    	ormtno:extraParams["params['ormtno']"],
								ortyno:extraParams["params['ortyno']"],
								count_type:extraParams["params['count_type']"],
								yxgsno:extraParams["params['yxgsno']"],
								qyno:extraParams["params['qyno']"]
						    },
						    method:'POST',
						    success:function(response){
						    	var obj=Ext.decode(response.responseText);
						    	Ext.getBody().unmask();
								if(obj.success==false){
									Ext.Msg.alert("消息",obj.msg);
									return;
								}
						    	me.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    	win.hide();
						    }
						   });
			//}
		}
		//});
	},
    onPplace:function(){
		var me=this;
		var modles=me.getSelection( ) ;
		if(!modles || modles.length==0){
			Ext.Msg.alert("消息","请选择一行或多行!");
			return;
		}	
				
		var extraParams=me.getStore().getProxy().extraParams;
		var pplacefield=Ext.create('Ext.form.ComboBox', {
			    fieldLabel: '产地',
			    name: 'pplace',
		        itemId: 'pplace',
		        labelWidth:60,
		        width:150,
		        //value:'sampno',
			    store: Ext.create('Ext.data.Store', {
				    fields: ['value', 'name'],
				    data : [
				    	//{"value":"", "name":"所有"},
				    	{"value":"宁波", "name":"宁波"},
				        {"value":"珲春", "name":"珲春"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'value'
			});
		var win=Ext.create('Ext.Window',{
			layout:'form',
			title:'指定面料交货期',
			modal:true,
			items:[pplacefield],
			buttons:[{
				text:'取消',
				handler:function(){
					win.hide();
				}
			},{
				text:'确认',
				handler:function(){
					handler();
				}
			}]
		});
		win.show();
		//Ext.Msg.prompt("消息","是否对选中的数据指定面料交货期!",function(btn){
		function handler(){
			if(!pplacefield.getValue()){
				return;
			}
			var pplace=pplacefield.getValue();
			//if(btn=='yes'){
				
				Ext.getBody().mask("正在处理,请稍候.....");

				var dataes=[];
				for(var i=0;i<modles.length;i++){
					dataes.push({
						
						sampno:modles[i].get("SAMPNO"),
						suitno:modles[i].get("SUITNO"),
						field:'pplace',
						value:pplace
					});
				}
				
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/ordDwdt/updateField.do',
						    jsonData:dataes,
						    params:{
						    	ormtno:extraParams["params['ormtno']"],
								ortyno:extraParams["params['ortyno']"],
								count_type:extraParams["params['count_type']"],
								yxgsno:extraParams["params['yxgsno']"],
								qyno:extraParams["params['qyno']"]
						    },
						    method:'POST',
						    success:function(response){
						    	var obj=Ext.decode(response.responseText);
						    	Ext.getBody().unmask();
								if(obj.success==false){
									Ext.Msg.alert("消息",obj.msg);
									return;
								}
						    	me.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    	win.hide();
						    }
						   });
			//}
		}
		//});
	}
   
});
