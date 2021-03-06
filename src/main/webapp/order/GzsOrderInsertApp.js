 Ext.require('y.order.GzsOrderInsertGrid');
Ext.onReady(function(){
	var panel=Ext.create('Ext.panel.Panel',{
		region:'north',
		height:120
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
		        		reloadOrdorg();
		        		
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
						//regioncombo
		        		regioncombo.reload(record.get("orgno"),'QY');
					}
				}
			},{
		  		fieldLabel: '区域',
		  		labelWidth:45,
		  		width:170,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            showBlank:false,
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
				width:170,
				allowBlank: false,
				showBlank:false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"订货单位不允许为空",
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
		        		
//						var sztype=combo.nextSibling("#sztype");
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
//								sztype.setValue(obj.sztype);
//								//alert(sztype);
//								if(obj.sztype==0){
//				        			panel.down("#sizeVO_auto_box").show();
//				        		} else{
//				        			panel.down("#sizeVO_auto_box").hide();
//				        		}
//							}
//							
//						});
		        		
					}
				}
			 }]
	});
	panel.addDocked({
		xtype: 'toolbar',
	  	dock:'top',
		items:[{
		  		itemId:'ortyno',
				xtype:'ordtycombo',
				labelWidth:65,
				 allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				//selFirst:true,
	            readOnly:true,
				width:150,
				value:'TP',
				listeners:{
					select:function( combo, record, eOpts ) {
//						var ordorg=combo.nextSibling("#ordorg");
//		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
//		        			ortyno:record.get("ortyno")
//		        		});
//		        		ordorg.getStore().reload();
					}
				}
			},{
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
				    	load:function(myStore){
				    		if(myStore.getCount( ) >0){
						 		var r=myStore.getAt(0);//第一行是无
						 		var me=panel.down("#sizegp");
						 		me.select( r );
						 		//me.fireEvent("select", me, r);
						 	}
				    	}
				    }
				}
		        
		    },{
		        fieldLabel: '订货样衣编号',
		        labelWidth:65,
		        width:165,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    },{
				fieldLabel: '订货规格状态',
				labelWidth:85,
		        width:165,
				itemId: 'szstat',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'value',
			    store: {
				    fields: ['value', 'name'],
				    data:[{value:'',name:'所有'},{value:'0',name:'编辑中'},{value:'1',name:'大区审批中'},{value:'2',name:'总部审批中'},{value:'3',name:'审批通过'},{value:'4',name:'退回'}]
				},
	            hidden:false,
				xtype:'combobox'
			 },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
//					var grid=btn.up("grid");
//    				grid.getStore().getProxy().extraParams=panel.getParams();
//					grid.getStore().reload();
					queryColumns();
				},
				iconCls: 'icon-refresh'
			},{
				text: '订单完成',
				itemId:'submit_button',
				disabled:true,
				handler: function(btn){
					//alert("存储过程还没写!");
					//return;
					Ext.Msg.confirm("消息","根据 <span style='color:red;'>订货会+订货单位+品牌</span> 提交，提交后不允许修改规格数据，是否提交?",function(btnid){
						if(btnid=='yes'){
							Ext.Ajax.request({
								url:Ext.ContextPath+"/gzsorderinsert/submit.do",
								params:{
									ormtno:grid.getStore().getProxy().extraParams.ormtno,
									ortyno:grid.getStore().getProxy().extraParams.ortyno,
									ordorg:grid.getStore().getProxy().extraParams.ordorg,
									bradno:grid.getStore().getProxy().extraParams.bradno
								},
								success:function(response){
								 	//console.log(response.responseText);
									var obj=Ext.decode(response.responseText);
									if(obj.success==false){
										Ext.Msg.alert("消息",obj.msg);
										return;
									}
									//grid.getStore().reload();
									queryColumns();
								}
							});
						}
					});
				},
				iconCls: 'icon-edit'
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
//			var spseno=panel.down("#spseno");取得这次订货用到的系列
//			spseno.clearValue( );
//			spseno.getStore().getProxy().extraParams={
//				bradno:panel.down("#bradno").getValue(),
//				spclno:panel.down("#spclno").getValue(),
//				ormtno:panel.down("#ordmtcombo").getValue() 
//			};
//			spseno.getStore().reload();
			
			
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
			
			'ortyno':toolbars[1].down("#ortyno").getValue(),
			'bradno':toolbars[1].down("#bradno").getValue(),
			'spclno':toolbars[1].down("#spclno").getValue(),
			'sptyno':toolbars[1].down("#sptyno").getValue(),
			'spseno':toolbars[1].down("#spseno").getValue(),
			'suitno':toolbars[1].down("#suitno").getValue(),
			
			'versno':toolbars[2].down("#versno").getValue(),
			'sizegp':toolbars[2].down("#sizegp").getValue(),
			'sampnm':toolbars[2].down("#sampnm").getValue(),
			'szstat':toolbars[2].down("#szstat").getValue()
		};
		return params;

	}
	
	function queryColumns(){
		var toolbars=panel.getDockedItems('toolbar[dock="top"]');

		//获取规格范围中的规格
		var sizegp=panel.down("#sizegp");
		Ext.Ajax.request({
			url:Ext.ContextPath+"/gzsorderinsert/queryColumns.do",
			params:{
				sizegp:sizegp.getValue()
			},
			success:function(response){
			 	//console.log(response.responseText);
				var obj=Ext.decode(response.responseText);
				//panel.removeAll(true);
				viewPort.remove(grid,true);
				grid=createSizeVOGrid(obj);
				viewPort.add(grid);
			}
		});
		
		
	}
	window.szstat=0;
	function createSizeVOGrid(initColumns){
		var params=panel.getParams();
		getSzstat(params);
		var　grid=Ext.create('y.order.GzsOrderInsertGrid',{
			region:'center',
			params:params,
			initColumns:initColumns
		});
		return grid;
	}
	
	function getSzstat(params){
		Ext.Ajax.request({
			url:Ext.ContextPath+"/gzsorderinsert/getSzstat.do",
			jsonData:params,
			headers:{ 'Accept':'application/json;'},
			success:function(response){
			 	//console.log(response.responseText);
				var obj=Ext.decode(response.responseText);
				if(obj.success==false){
					Ext.Msg.alert("消息",obj.msg);
					return;
				}
				//alert(obj.szstat);
				window.szstat=obj.szstat;
				if(window.szstat==3){
					panel.down("#submit_button").disable();
				} else {
					panel.down("#submit_button").enable();
				}
				
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



});