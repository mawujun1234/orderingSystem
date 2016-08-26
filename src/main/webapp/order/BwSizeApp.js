Ext.require('y.order.BwSizeGrid');
Ext.onReady(function(){
	var panel=Ext.create('Ext.panel.Panel',{
		region:'north',
		height:145
	});
	window.load_num=0;
	window.ordorg_load_num=0;
	panel.addDocked({
		xtype: 'toolbar',
	  	dock:'top',
		items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				listeners:{
					select:function( combo, record, eOpts ) {	
						var ordorg=combo.nextSibling("#ordorg");
		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			ormtno:record.get("ormtno")
		        		});
		        		//ordorg.getStore().reload();
		        		window.ordorg_load_num++;
		        		//reloadOrdorg();
		        		
		        		window.load_num++;
						reloadSizegp();
					}
				}
			},{
		  		fieldLabel: '营销公司',
		  		labelWidth:65,
		  		width:160,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            showBlank:false,
		  		itemId:'yxgsno',
				xtype:'orgcombo',
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
		  		//allowBlank: false,
		  		selFirst:false,
	            //afterLabelTextTpl: Ext.required,
	            showBlank:true,
		  		itemId:'qyno',
				xtype:'orgcombo',
				autoLoad:false,
				listeners:{
					select:function( combo, record, eOpts ) {
		        		
						var ordorg=combo.nextSibling("#ordorg");
						var channo=combo.nextSibling("#channo");
		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			qyno:record.get("orgno"),
		        			channo:channo.getValue()
		        		});
		        		//ordorg.getStore().reload();
		        		window.ordorg_load_num++;
		        		reloadOrdorg();
					}
				}
			},{
				fieldLabel: '渠道类型',
				labelWidth:65,
				width:150,
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				itemId: 'channo',
				xtype:'channocombo',
				value:'QY',
				listeners:{
					select:function( combo, record, eOpts ) {
		        		
						var ordorg=combo.nextSibling("#ordorg");
		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			channo:record.get("channo")
		        		});
		        		//ordorg.getStore().reload();
		        		window.ordorg_load_num++;
		        		reloadOrdorg();
					}
				}
			 },{
				fieldLabel: '订货单位',
				labelWidth:65,
				width:270,
				//allowBlank: false,
				showBlank:true,
	            //afterLabelTextTpl: Ext.required,
	            //blankText:"订货单位不允许为空",
				itemId: 'ordorg',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'orgnm',
			    valueField: 'orgno',
			    store: {
				    fields: ['orgno', 'orgnm'],
				    autoLoad:false,
				    proxy: {
				        type: 'ajax',
				        url: Ext.ContextPath+'/ord/queryOrdorg.do'
				    },
				    listeners:{
				    	load:function(myStore){
				    		if(myStore.getCount( ) >0){
						 		var r=null;
						 		var me=panel.down("#ordorg");
								if(me.showBlank==true){
									r=myStore.getAt(1);//第一行是无
								} else {
									r=myStore.getAt(0);//第一行是,正确的数据
								}
						 		me.select( r );
							 	me.fireEvent("select", me, r);
						 	}
				    	}
				    }
				},
	            hidden:false,
				xtype:'combobox',
				listeners:{
					select:function( combo, record, eOpts ) {
		        		
//						//var sztype=combo.nextSibling("#sztype");
//						//从后台获取当前订货单位的规格上报方式
//						var ormtno_combo=combo.previousSibling("#ordmtcombo");
//						Ext.Ajax.request({
//							url:Ext.ContextPath+"/ordOrg/getOrdOrgByOrg.do",
//							params:{
//								ormtno:ormtno_combo.getValue(),
//								orgno:record.get("orgno")
//							},
//							success:function(response){
//								var obj=Ext.decode(response.responseText);
//								//sztype.setValue(obj.sztype);
//								
//							}
//							
//						});
		        		
					}
				}
			 }
//			 ,{
//		        fieldLabel: '上报方式',
//		        labelWidth:65,
//				width:170,
//		        itemId: 'sztype',
//	            queryMode: 'local',
//				editable:false,
//				readOnly:true,
//				forceSelection:true,
//				displayField: 'name',
//				valueField: 'id',
//				//value:'0',
//				store: {
//					    fields: ['id', 'name'],
//					    autoLoad:false,
//					    data:[{id:'0',name:'单规+整箱上报'},{id:'1',name:'单规上报'},{id:'2',name:'整箱上报'}]
//				},
//		        hidden:false,
//				xtype:'combobox' 
//		    }
		    ]
	});
	panel.addDocked({
		xtype: 'toolbar',
	  	dock:'top',
		items:[
//			{
//		  		itemId:'ortyno',
//				xtype:'ordtycombo',
//				labelWidth:65,
//				 allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//				//selFirst:true,
//				width:150,
//				value:'DZ',
//				listeners:{
//					select:function( combo, record, eOpts ) {
////						var ordorg=combo.nextSibling("#ordorg");
////		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
////		        			ortyno:record.get("ortyno")
////		        		});
////		        		ordorg.getStore().reload();
//					}
//				}
//			},
			{
		        fieldLabel: '品牌',
		        itemId: 'bradno',
		        labelWidth:40,
		        width:160,
	            allowBlank: false,
	            showBlank:false,
	            afterLabelTextTpl: Ext.required,
	            //value:'Y',
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
		        	select:function( combo, record, eOpts ) {
		        		var toolbar=combo.up("toolbar");
		        		var suitno=toolbar.down("#suitno");
		        		suitno.changeBradno(record.get("itno"));
		        		suitno.getStore().reload();
		        		
		        		window.load_num++;
						reloadSizegp();
		        	}	
		        }
		    },{
		        fieldLabel: '大类',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
	            allowBlank: false,
	            showBlank:false,
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
		        		
		        		window.load_num++;
						reloadSizegp();
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
		        fieldLabel: '套件',
		        itemId: 'suitno',
		        //selFirst:true,
	            xtype:'pubcodecombo',
	             allowBlank: false,
	             showBlank:false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"大类不允许为空",
		        tyno:'3',
		        value:'T00',
		        labelWidth:45,
				width:150
		    }]
	});
	panel.addDocked({
		xtype: 'toolbar',
	  	dock:'top',
		items:[{
		        fieldLabel: '版型',
		        labelWidth:50,
		        width:150,
		        itemId: 'versno',
//	            allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"版型不允许为空",
	            xtype:'combo',
		        tyno:'13',
		        queryMode: 'local',
		        displayField: 'itnm',
			    valueField: 'itno',
			    editable:false,
			    store: {
			    	autoLoad:false,
				    fields: ['itno', 'itnm'],
				    proxy:{
				    	type:'ajax',
				    	//extraParams:{szbrad:'sjs'},
				    	url:Ext.ContextPath+'/pubCodeType/queryVersno4Ordmt.do'
				    }
				},
				listeners:{
		        	select:function( combo, record, eOpts ) {
		        		//window.load_num++;
						reloadSizegp1();
		        	}	
		        }
		    },{
		        fieldLabel: '规格范围',
		        labelWidth:70,
		        itemId: 'sizegp',
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"规格范围不允许为空",
	            selectOnFocus:true,
		        xtype:'combobox',
		        queryMode: 'local',
				editable:true,
		        selectOnFocus:true,
				forceSelection:true,
			    displayField: 'sizenm',
			    valueField: 'sizeno',
			    store: {
			    	autoLoad:false,
				    fields: ['sizeno', 'sizenm'],
				    proxy:{
				    	type:'ajax',
				    	//extraParams:{szbrad:'sjs'},
				    	url:Ext.ContextPath+'/pubSize/queryPRDSZFW4Ordmt.do'
				    },
				    listeners:{
//				    	load:function(myStore){
//				    		if(myStore.getCount( ) >0){
//						 		var r=myStore.getAt(0);//第一行是无
//						 		var me=panel.down("#sizegp");
//						 		me.select( r );
//						 		//me.fireEvent("select", me, r);
//						 	}
//				    	}
				    }
				}
		        
		    },{
		        fieldLabel: '订货样衣编号',
		        labelWidth:85,
		        width:165,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    }
		    ,{
		        fieldLabel: '货号',
		        labelWidth:40,
		        width:165,
		        itemId: 'prodnm',
	            xtype:'textfield'
		    }
//		    ,{
//				fieldLabel: '订货规格状态',
//				labelWidth:85,
//		        width:165,
//				itemId: 'szstat',
//				queryMode: 'local',
//				editable:false,
//				forceSelection:true,
//			    displayField: 'name',
//			    valueField: 'value',
//			    store: {
//				    fields: ['value', 'name'],
//				    data:[{value:'',name:'所有'},{value:'0',name:'编辑中'},{value:'1',name:'大区审批中'},{value:'2',name:'总部审批中'},{value:'3',name:'审批通过'},{value:'4',name:'退回'}]
//				},
//	            hidden:false,
//				xtype:'combobox'
//			 }
			 ,{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
//					var grid=btn.up("grid");
//    				grid.getStore().getProxy().extraParams=panel.getParams();
//					grid.getStore().reload();
					queryColumns();
				},
				iconCls: 'icon-refresh'
			}
//			,{
//				text: '提交',
//				handler: function(btn){
//					//alert("存储过程还没写!");
//					//return;
//					Ext.Msg.confirm("消息","根据 <span style='color:red;'>品牌+大类</span> 提交审批，提交后不允许修改规格数据，是否提交?",function(btnid){
//						if(btnid=='yes'){
//							var extraParams=extraParams;
//							Ext.Ajax.request({
//								url:Ext.ContextPath+"/bwOrdmt/approve.do",
//								params:extraParams,
//								success:function(response){
//								 	//console.log(response.responseText);
//									var obj=Ext.decode(response.responseText);
//									if(obj.success==false){
//										Ext.Msg.alert("消息",obj.msg);
//										return;
//									}
//									grid.getStore().reload();
//								}
//							});
//						}
//					});
//				},
//				iconCls: 'icon-edit'
//			}
			]
	});
	
	panel.addDocked({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[
//		  	Ext.create('Ext.form.ComboBox', {
//			    fieldLabel: '产地',
//			    name: 'pplace',
//		        itemId: 'pplace',
//		        labelWidth:60,
//		        width:150,
//		        //value:'sampno',
//			    store: Ext.create('Ext.data.Store', {
//				    fields: ['value', 'name'],
//				    data : [
//				    	{"value":"", "name":"所有"},
//				    	{"value":"宁波", "name":"宁波"},
//				        {"value":"珲春", "name":"珲春"}
//				    ]
//				}),
//			    queryMode: 'local',
//			    displayField: 'name',
//			    valueField: 'value'
//			}),{
//		       // fieldLabel: '生产类型',
//		        emptyText:'生产类型',
//		         width:90,
//		        itemId: 'spmtno',
//	            selectOnFocus:true,
//		        xtype:'pubcodecombo',
//		        tyno:'29'
//		    }
		    {
			   text: '指定面料交货期',
			   //hidden:!Permision.canShow('plan_orgdtl_import'),
				handler: function(btn){
					panel.onMldate();
				},
				iconCls: 'icon-wrench'
			},{
		  		text: '指定成衣交货期',
				handler: function(btn){
					panel.onPldate();
				},
				iconCls: 'icon-wrench'
		  	},{
			   text: '指定产地',
			   //hidden:!Permision.canShow('plan_orgdtl_import'),
				handler: function(btn){
					panel.onPplace();
				},
				iconCls: 'icon-wrench'
			}]
		});
	
	function reloadOrdorg(){
		if(window.ordorg_load_num>1){
			var ordorg=panel.down("#ordorg");//combo.nextSibling("#ordorg");
			//ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
			//        ormtno:record.get("ormtno")
			//});
			ordorg.getStore().reload();
		}
	}
	
	function reloadSizegp(){
		//console.log(window.load_num);
		
		if(window.load_num>=3){

			
			var versno=panel.down("#versno");
			versno.clearValue( );
			versno.getStore().getProxy().extraParams={
				showBlank:true,
				bradno:panel.down("#bradno").getValue(),
				spclno:panel.down("#spclno").getValue(),
				ormtno:panel.down("#ordmtcombo").getValue() 
			};
			versno.getStore().reload();
			
			var sizegp=panel.down("#sizegp");
			sizegp.clearValue( );
			sizegp.getStore().getProxy().extraParams={
				showBlank:true,
				szbrad:panel.down("#bradno").getValue(),
				szclno:panel.down("#spclno").getValue(),
				ormtno:panel.down("#ordmtcombo").getValue() 
			};
			sizegp.getStore().reload();
		}
	}
	function reloadSizegp1(){
		//versno,spseno
		var sizegp=panel.down("#sizegp");
		sizegp.clearValue( );
//			sizegp.getStore().getProxy().extraParams={
//				szbrad:tabpanel.down("#bradno").getValue(),
//				szclno:tabpanel.down("#spclno").getValue(),
//				ormtno:tabpanel.down("#ordmtcombo").getValue() 
//			};
		sizegp.getStore().getProxy().extraParams=Ext.apply(sizegp.getStore().getProxy().extraParams,{
				versno:panel.down("#versno").getValue()
		});
		sizegp.getStore().reload();
	}
	
	panel.getParams=function(){
		var toolbars=panel.getDockedItems('toolbar[dock="top"]');
		var params={
			'ormtno':toolbars[0].down("#ordmtcombo").getValue(),
			'yxgsno':toolbars[0].down("#yxgsno").getValue(),
			'qyno':toolbars[0].down("#qyno").getValue(),
			'channo':toolbars[0].down("#channo").getValue(),	
			'ordorg':toolbars[0].down("#ordorg").getValue(),
			'ordorg_name':toolbars[0].down("#ordorg").getRawValue(),
			//'sztype':toolbars[0].down("#sztype").getValue(),
			
			//'ortyno':toolbars[1].down("#ortyno").getValue(),
			'bradno':toolbars[1].down("#bradno").getValue(),
			'spclno':toolbars[1].down("#spclno").getValue(),
			'sptyno':toolbars[1].down("#sptyno").getValue(),
			'spseno':toolbars[1].down("#spseno").getValue(),
			'suitno':toolbars[1].down("#suitno").getValue(),
			
			'versno':toolbars[2].down("#versno").getValue(),
			'sizegp':toolbars[2].down("#sizegp").getValue(),
			'sampnm':toolbars[2].down("#sampnm").getValue(),
			'prodnm':toolbars[2].down("#prodnm").getValue()
			//'szstat':toolbars[2].down("#szstat").getValue()
		};
		return params;

	}
	
	function queryColumns(){
		var toolbars=panel.getDockedItems('toolbar[dock="top"]');
//		var sztype=toolbars[0].down("#sztype").getValue();//规格上报方式
//		if(!sztype){
//			Ext.Msg.alert("消息","请先为这个订货单位设置规格上报方式!");
//			return;
//		}
		var params=panel.getParams();
		//获取规格范围中的规格
		var sizegp=panel.down("#sizegp");
		Ext.Ajax.request({
			url:Ext.ContextPath+"/bwOrdmt/querySizeVOColumns.do",
			jsonData:{
				sizegp:sizegp.getValue(),
				ormtno:params.ormtno,
				channo:params.channo,
				ordorg:params.ordorg,
				bradno:params.bradno,
				spclno:params.spclno,
				suitno:params.suitno
			},
			success:function(response){
			 	//console.log(response.responseText);
				var obj=Ext.decode(response.responseText);
				//panel.removeAll(true);
				viewPort.remove(grid,true);
				grid=createBwSizeGrid(obj,params);
				viewPort.add(grid);
			}
		});
		
		
	}
	window.orstat=0;
	function createBwSizeGrid(initColumns,params){
		
		getorstat(params);
		var　grid=Ext.create('y.order.BwSizeGrid',{
			region:'center',
			params:params,
			initColumns:initColumns
		});
		return grid;
	}
	
	function getorstat(params){
		Ext.Ajax.request({
			url:Ext.ContextPath+"/bwOrdmt/getBwOrdhdOrstat.do",
			params:params,
			//headers:{ 'Accept':'application/json;'},
			success:function(response){
			 	//console.log(response.responseText);
				var obj=Ext.decode(response.responseText);
				if(obj.success==false){
					Ext.Msg.alert("消息",obj.msg);
					return;
				}
				//alert(obj);
				window.orstat=obj.orstat;
				
			}
		});
	}
	
	var grid=Ext.create('Ext.grid.Panel',{
		region:'center',
		html:''
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[panel,grid]
	});


	
	panel.onMldate=function(){
		var me=grid;
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
						ormmno:modles[i].get("ORMMNO"),
						mmorno:modles[i].get("MMORNO"),
						ordorg:modles[i].get("ORDORG"),
						sampnm:modles[i].get("SAMPNM"),
						
						mldate:mldate,
						ormtqt:modles[i].get("ORSZQT_SUBTOTAL")
					});
				}
				
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/bwOrdmt/updateBwOrddt.do',
						    jsonData:dataes,
						    params:{
						    	field:'mldate'
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
						    	Ext.Msg.alert("消息",obj.msg?obj.msg:"成功");
						    	win.hide();
						    }
						   });
			//}
		}
		//});
	},
	panel.onPldate=function(){
		var me=grid;
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
						ormmno:modles[i].get("ORMMNO"),
						mmorno:modles[i].get("MMORNO"),
						ordorg:modles[i].get("ORDORG"),
						sampnm:modles[i].get("SAMPNM"),
						
						pldate:mldate,
						ormtqt:modles[i].get("ORSZQT_SUBTOTAL")
					});
				}
				
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/bwOrdmt/updateBwOrddt.do',
						    jsonData:dataes,
						    params:{
						    	field:'pldate'
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
						    	Ext.Msg.alert("消息",obj.msg?obj.msg:"成功");
						    	win.hide();
						    }
						   });
			//}
		}
		//});
	},
    panel.onPplace=function(){
		var me=grid;
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
						ormmno:modles[i].get("ORMMNO"),
						mmorno:modles[i].get("MMORNO"),
						ordorg:modles[i].get("ORDORG"),
						sampnm:modles[i].get("SAMPNM"),
						
						pplace:pplace,
						ormtqt:modles[i].get("ORSZQT_SUBTOTAL")
					});
				}
				
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/bwOrdmt/updateBwOrddt.do',
						    jsonData:dataes,
						    params:{
						    	field:'pplace'
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
						    	Ext.Msg.alert("消息",obj.msg?obj.msg:"成功");
						    	win.hide();
						    }
						   });
			//}
		}
		//});
	}

});