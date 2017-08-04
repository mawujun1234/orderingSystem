Ext.define('y.cg.CgOrddtlGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.cg.CgOrddtl'
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
      	{dataIndex:'sampnm',header:'订货样衣编号',align : 'left'
		},
		{dataIndex:'suitno_name',header:'套件',align : 'left'
		},
		{dataIndex:'orszqt_now',header:'本次数量',xtype: 'numbercolumn', format:'0',align : 'right'
			,renderer:function(value, metaData, record, rowIndex, colIndex, store){
				if(record.get("orstat")==3 && me.CgOrdhd_orstat==0){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
				}

            	 return value;
            },editor: {
                xtype: 'numberfield',
                allowDecimals:false,
                selectOnFocus:true 
            }
		},
		{dataIndex:'orszqt_residue',header:'剩余订货量',align : 'right'
		},
		{dataIndex:'orszqt_already',header:'已确认量',align : 'right'
		},
		{dataIndex:'orszqt_zhanb',header:'已确认占比',align : 'right'
		},
		{dataIndex:'ormtqt',header:'订货量',align : 'right'
		},
		{dataIndex:'mldate',header:'面料交货期',align : 'left'
		},
		{dataIndex:'pldate',header:'成衣交货期',align : 'left'
		},
		{dataIndex:'pplace',header:'面料交货地',align : 'left'
		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.cg.CgOrddtl',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/cgOrddtl/queryPager.do',
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
	  
	  
	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  
	  this.cellEditing.on("beforeedit",function(editor, context){
	   	var record=context.record;
	   	var grid=context.grid;
	   	//console.log(record.get("orstat"));
	   	
	   	if(record.get("orstat")!=3 || grid.CgOrdhd_orstat==1){//alert(1);
			return false;
		}
	  });
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	var originalValue =context.originalValue;
	  	
	  	//alert(record.get("orszqt_residue"));
		if(value>(parseInt(record.get("orszqt_residue"))+originalValue)){

			//value=record.get("orszqt_residue");
			value=(parseInt(record.get("orszqt_residue"))+originalValue);
		}
		//alert(value);
		//return;

	  	Ext.Ajax.request({
			url : Ext.ContextPath + '/cgOrddtl/update.do',
			params : {
				orcgno : record.get("orcgno"),//me.getStore().getProxy().extraParams["params['orcgno']"],
				cgorno : record.get("cgorno"),//me.getStore().getProxy().extraParams["params['cgorno']"],
				sampno : record.get("sampno"),
				suitno : record.get("suitno"),
				orszqt : value
			},
			success : function() {
				//console.log(record.get("ormtqt"));
				//console.log(record.get("value"));
				//console.log(record.get("orszqt_already"));
				record.set("orszqt_residue",record.get("ormtqt")-value-record.get("orszqt_already"));	
				record.commit();							
			}

		});
	  	
	  });

	  me.dockedItems=[];
      me.dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
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
		        fieldLabel: '上市月份',
		        itemId: 'spbano',
	            //allowBlank: false,
	            //afterLabelTextTpl: Ext.required,
	            //blankText:"上市批次不允许为空",
		        xtype:'pubcodecombo',
		        labelWidth:60,
		        width:150,
		        tyno:'23'
		    },{
		        fieldLabel: '生产类型',
		        itemId: 'spmtno',
	            //allowBlank: false,
	            //afterLabelTextTpl: Ext.required,
	           // blankText:"生产类型不允许为空",
	            selectOnFocus:true,
		        xtype:'pubcodecombo',
		        labelWidth:60,
		        width:150,
		        tyno:'29'
		    },{
		  		fieldLabel:"设计样衣编号",
		  		
		  		itemId:'sampnm',
		  		xtype:'textfield',
		  		labelWidth:90,
		  		width:180
		  	},{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().getProxy().extraParams=Ext.apply(grid.getStore().getProxy().extraParams,grid.getParams());
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});
	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '新增',
				itemId:'create',
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			},{
			    text: '删除',
			    itemId:'destroy',
			    handler: function(){
			    	me.onDelete();    
			    },
			    iconCls: 'icon-trash'
			},{
				text: '导出',
				//itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					//var grid=btn.up("grid");
					//grid.getStore().reload();
					me.onExport(); 
				},
				iconCls: 'icon-download-alt'
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
			   text: '指定面料交货地',
			   //hidden:!Permision.canShow('plan_orgdtl_import'),
				handler: function(btn){
					me.onPplace();
				},
				iconCls: 'icon-wrench'
			}]
		});

       
      me.callParent();
	},
	getParams:function(){
		var grid=this;//Ext.getCmp("sampleDesignGrid");
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');

    	var params={
    		//"params['spclno']":toolbars[0].down("#spclno").getValue(),
    		"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
    		"params['spseno']":toolbars[0].down("#spseno").getValue(),
    		"params['spbano']":toolbars[0].down("#spbano").getValue(),
    		"params['spmtno']":toolbars[0].down("#spmtno").getValue(),
    		"params['sampnm']":toolbars[0].down("#sampnm").getValue()
			
    	};
    	return params;
	},
	onCreate:function(){
    	var me=this;
    	if(me.CgOrdhd_orstat==1){
    		Ext.Msg.alert("消息","子批次订单已经确认，不能操作!");
    		return;
    	}

		var cgOrddt4InsertGrid=Ext.create('y.cg.CgOrddt4InsertGrid',{});
		
		cgOrddt4InsertGrid.getStore().getProxy().extraParams=Ext.apply(me.getStore().getProxy().extraParams,{
			"params['ormtno']":me.getStore().getProxy().extraParams["params['ormtno']"],
			"params['bradno']":me.getStore().getProxy().extraParams["params['bradno']"],
			"params['spclno']":me.getStore().getProxy().extraParams["params['spclno']"],
			"params['orcgno']":me.getStore().getProxy().extraParams["params['orcgno']"],
			"params['cgorno']":me.getStore().getProxy().extraParams["params['cgorno']"],
			"params['sampnm']":null
		});
		
		var spclno=me.getStore().getProxy().extraParams["params['spclno']"];
		cgOrddt4InsertGrid.getStore().reload();
		var sptyno=cgOrddt4InsertGrid.down("#sptyno");
		sptyno.reload(spclno);
		        		
		var spseno=cgOrddt4InsertGrid.down("#spseno");
		spseno.reload(spclno);
		
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增样衣编号',
    		modal:true,
    		//width:600,
    		//height:480,
    		maximized:true,
    		closeAction:'hide',
    		items:[cgOrddt4InsertGrid],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	cgOrddt4InsertGrid.win=win;
    	win.show();
    },
    
//     onUpdate:function(){
//    	var me=this;
//
//    	var node=me.getSelectionModel( ).getLastSelected();
//    	if(node==null){
//    		Ext.Msg.alert("提醒","请选择一行数据!");
//    		return;
//    	}
//
//		var formpanel=Ext.create('y.cg.CgOrddtlForm',{});
//		formpanel.loadRecord(node);
//		
//    	var win=Ext.create('Ext.window.Window',{
//    		layout:'fit',
//    		title:'更新',
//    		modal:true,
//    		width:400,
//    		height:300,
//    		closeAction:'hide',
//    		items:[formpanel]
//    	});
//    	win.show();
//    },
    
    onDelete:function(){
    	var me=this;
    	if(me.CgOrdhd_orstat==1){
    		Ext.Msg.alert("消息","子批次订单已经确认，不能操作!");
    		return;
    	}
    	var recordes=me.getSelection( ) ;
		if(!recordes || recordes.length==0){
			Ext.Msg.alert("消息","请选择一行或多行!");
			return;
		}	
		
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
			if (btn == 'yes'){
				Ext.getBody().mask("正在处理,请稍候.....");

				var dataes=[];
				for(var i=0;i<recordes.length;i++){
					dataes.push({
						orcgno : recordes[i].get("orcgno"),//me.getStore().getProxy().extraParams["params['orcgno']"],
						cgorno : recordes[i].get("cgorno"),//me.getStore().getProxy().extraParams["params['cgorno']"],
						sampno : recordes[i].get("sampno"),
						suitno : recordes[i].get("suitno")
					});
					
				}
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/cgOrddtl/destroyBatch1.do',
						    jsonData:dataes,
						    method:'POST',
						    success:function(response){
						    	var obj=Ext.decode(response.responseText);
						    	Ext.getBody().unmask();
								if(obj.success==false){
									Ext.Msg.alert("消息",obj.msg);
									return;
								}
						    	me.getStore().reload();
						    }
					});
			}
		});
		
//    	var node=me.getSelectionModel( ).getLastSelected( );
//
//		if(!node){
//		    Ext.Msg.alert("消息","请先选择一行数据");	
//			return;
//		}
//		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
//				if (btn == 'yes'){
//					node.erase({
//					    failure: function(record, operation) {
//			            	me.getStore().reload();
//					    },
//					    success:function(){
//					    	me.getStore().reload();
//					    }
//				});
//			}
//		});
		
    },
    onExport:function(){
    	var me=this;
    	var params=me.getStore().getProxy().extraParams;
    	var url=Ext.ContextPath+"/cgOrddtl/export.do?"+Ext.urlEncode(params);
    	window.open(url);
    },
     onMldate:function(){
		var me=this;
		if(me.CgOrdhd_orstat==1){
    		Ext.Msg.alert("消息","子批次订单已经确认，不能操作!");
    		return;
    	}
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
						orcgno : modles[i].get("orcgno"),//me.getStore().getProxy().extraParams["params['orcgno']"],
						cgorno : modles[i].get("cgorno"),//me.getStore().getProxy().extraParams["params['cgorno']"],
						sampno : modles[i].get("sampno"),
						suitno : modles[i].get("suitno"),
						ormtqt : modles[i].get("orszqt_now"),
						
						pldate:modles[i].get("pldate"),
						pplace:modles[i].get("pplace"),
						mldate:mldate
					});
				}
				
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/cgOrddt/updateBatch1.do',
						    jsonData:dataes,
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
		if(me.CgOrdhd_orstat==1){
    		Ext.Msg.alert("消息","子批次订单已经确认，不能操作!");
    		return;
    	}
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
			var pldate=datefield.getRawValue();
			//if(btn=='yes'){
				
				Ext.getBody().mask("正在处理,请稍候.....");

				var dataes=[];
				for(var i=0;i<modles.length;i++){
					dataes.push({
						
						orcgno : modles[i].get("orcgno"),//me.getStore().getProxy().extraParams["params['orcgno']"],
						cgorno : modles[i].get("cgorno"),//me.getStore().getProxy().extraParams["params['cgorno']"],
						sampno : modles[i].get("sampno"),
						suitno : modles[i].get("suitno"),
						ormtqt : modles[i].get("orszqt_now"),
						
						pldate:pldate,
						pplace:modles[i].get("pplace"),
						mldate:modles[i].get("mldate")

					});
				}
				
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/cgOrddt/updateBatch1.do',
						    jsonData:dataes,
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
		if(me.CgOrdhd_orstat==1){
    		Ext.Msg.alert("消息","子批次订单已经确认，不能操作!");
    		return;
    	}
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
						
						orcgno : modles[i].get("orcgno"),//me.getStore().getProxy().extraParams["params['orcgno']"],
						cgorno : modles[i].get("cgorno"),//me.getStore().getProxy().extraParams["params['cgorno']"],
						sampno : modles[i].get("sampno"),
						suitno : modles[i].get("suitno"),
						ormtqt : modles[i].get("orszqt_now"),
						
						pldate:modles[i].get("pldate"),
						pplace:pplace,
						mldate:modles[i].get("mldate")

					});
				}
				
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/cgOrddt/updateBatch1.do',
						    jsonData:dataes,
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
