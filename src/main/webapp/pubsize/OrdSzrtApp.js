Ext.require("y.pubsize.OrdSzrt");
Ext.require("y.pubsize.OrdSzrtGrid");
//Ext.require("y.pubsize.OrdSzrtForm");
Ext.onReady(function(){
	
	window.load_num=0;
	var tabpanel=Ext.create('Ext.tab.Panel',{
		//items:[grid],
		dockedItems:[{
	  		xtype: 'toolbar',
	  		dock:'top',
	  		//enableOverflow:true,
		  	items:[{
		  		fieldLabel: '订货会',
		  		labelWidth:50,
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				listeners:{
					select:function( combo, record, eOpts ) {
						window.load_num++;
						reloadSizegp();
					}
				}
			},{
		  		fieldLabel: '营销公司',
		  		labelWidth:75,
		  		width:175,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'compcombo',
				xtype:'orgcombo',
				showBlank:false,
				listeners:{
					select:function( combo, record, eOpts ) {
						var regioncombo=combo.nextSibling("#regioncombo");
		        		regioncombo.reload(record.get("orgno"),"QY");
					}
				}
			},{
		  		fieldLabel: '区域',
		  		labelWidth:50,
		  		allowBlank: false,
		  		showBlank:false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'regioncombo',
				xtype:'orgcombo',
				showBlank:false,
				autoLoad:false,
				listeners:{
					select:function( combo, record, eOpts ) {
						
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
//		        		var sptyno=combo.nextSibling("#sptyno");
//		        		sptyno.reload(record.get("itno"));
//		        		
//		        		var spseno=combo.nextSibling("#spseno");
//		        		spseno.reload(record.get("itno"));
		        		
		        		window.load_num++;
						reloadSizegp();
		        	}	
		        }
		    },{
		        fieldLabel: '小类',
		        itemId: 'sptyno',
		        labelWidth:40,
		        hidden:true,
		        width:140,
	            autoLoad:false,
	            // selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'2'
		    }]
		},{
	  		xtype: 'toolbar',
	  		dock:'top',
	  		//enableOverflow:true,
		  	items:[{
		        fieldLabel: '版型',
		        labelWidth:50,
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
						 		var me=tabpanel.down("#sizegp");
						 		me.select( r );
						 		me.fireEvent("select", me, r);
						 	}
				    	}
				    }
				}
		        
		    },
			{
		        fieldLabel: '系列',
		        itemId: 'spseno',
		        labelWidth:40,
		        width:160,
	            autoLoad:false,
		        xtype:'combo',
		         queryMode: 'local',
		        tyno:'5',
		        queryMode: 'remote',
		        displayField: 'itnm',
			    valueField: 'itno',
			    editable:false,
			    store: {
			    	autoLoad:false,
				    fields: ['itno', 'itnm'],
				    proxy:{
				    	type:'ajax',
				    	//extraParams:{szbrad:'sjs'},
				    	url:Ext.ContextPath+'/pubCodeType/querySpseno4Ordmt.do'
				    }
				},
				listeners:{
		        	select:function( combo, record, eOpts ) {
		        		//window.load_num++;
						reloadSizegp1();
		        	}	
		        }
		    },{
		    	xtype:'button',
		    	text:'查询',
		    	handler: function(btn){
					//var grid=btn.up("grid");
					//grid.getStore().reload();
		    		show();
				},
				iconCls: 'icon-refresh'
		    }]
		}]
	});

	function reloadSizegp(){
		//console.log(window.load_num);
		
		if(window.load_num>=3){
			var spseno=tabpanel.down("#spseno");
			spseno.clearValue( );
			spseno.getStore().getProxy().extraParams={
				showBlank:true,
				bradno:tabpanel.down("#bradno").getValue(),
				spclno:tabpanel.down("#spclno").getValue(),
				ormtno:tabpanel.down("#ordmtcombo").getValue()
				
			};
			spseno.getStore().reload();
			
			
			var versno=tabpanel.down("#versno");
			versno.clearValue( );
			versno.getStore().getProxy().extraParams={
				showBlank:true,
				bradno:tabpanel.down("#bradno").getValue(),
				spclno:tabpanel.down("#spclno").getValue(),
				ormtno:tabpanel.down("#ordmtcombo").getValue() 
			};
			versno.getStore().reload();
			
			var sizegp=tabpanel.down("#sizegp");
			sizegp.clearValue( );
			sizegp.getStore().getProxy().extraParams={
				szbrad:tabpanel.down("#bradno").getValue(),
				szclno:tabpanel.down("#spclno").getValue(),
				ormtno:tabpanel.down("#ordmtcombo").getValue() 
			};
			sizegp.getStore().reload();
		}
	}
	function reloadSizegp1(){
		//versno,spseno
		var sizegp=tabpanel.down("#sizegp");
		sizegp.clearValue( );
//			sizegp.getStore().getProxy().extraParams={
//				szbrad:tabpanel.down("#bradno").getValue(),
//				szclno:tabpanel.down("#spclno").getValue(),
//				ormtno:tabpanel.down("#ordmtcombo").getValue() 
//			};
		sizegp.getStore().getProxy().extraParams=Ext.apply(sizegp.getStore().getProxy().extraParams,{
				versno:tabpanel.down("#versno").getValue(),
				spseno:tabpanel.down("#spseno").getValue()
		});
		sizegp.getStore().reload();
	}
	
	function show(){
		//获取规格范围中的规格
		var sizegp=tabpanel.down("#sizegp");
		Ext.Ajax.request({
			url:Ext.ContextPath+"/ordSzrt/queryColumns.do",
			params:{
				sizegp:sizegp.getValue()
			},
			success:function(response){
			 	//console.log(response.responseText);
				var obj=Ext.decode(response.responseText);
				tabpanel.removeAll(true);
				createOrdSzrtGrid(obj.szrtColumns);
				createPrdpkGrid(obj.prdpkColumns);
			}
		});
	}
	function getParams(){
		var params={
			ordorg:tabpanel.down("#regioncombo").getValue() ,
			ormtno:tabpanel.down("#ordmtcombo").getValue() ,
			bradno:tabpanel.down("#bradno").getValue(),
			spclno:tabpanel.down("#spclno").getValue(),
			spseno:tabpanel.down("#spseno").getValue(),
			versno:tabpanel.down("#versno").getValue(),
			sizegp:tabpanel.down("#sizegp").getValue()
			
		}
		return params;
	}
	function getParams1(){
		var params={
			"params['ordorg']":tabpanel.down("#regioncombo").getValue() ,
			"params['ormtno']":tabpanel.down("#ordmtcombo").getValue() ,
			"params['bradno']":tabpanel.down("#bradno").getValue(),
			"params['spclno']":tabpanel.down("#spclno").getValue(),
			"params['spseno']":tabpanel.down("#spseno").getValue(),
			"params['versno']":tabpanel.down("#versno").getValue(),
			"params['sizegp']":tabpanel.down("#sizegp").getValue()
			
		}
		return params;
	}
	//单规比例设置
	function createOrdSzrtGrid(initColumns){
		var params=getParams();
//		if(!params.versno){
//			Ext.Msg.alert("消息","请先选择版型!");
//			return;
//		}
		if(!params.sizegp){
			Ext.Msg.alert("消息","请先选择规格范围!");
			return;
		}
		params.sizety='STDSZ';
		var grid=Ext.create('y.pubsize.OrdSzrtGrid',{
			//title:'规格比例设置',
			//activeTab :0,
			region:'center',
			params:params,
			initColumns:initColumns
		});
		//alert(1);
		
		var container=Ext.create('Ext.panel.Panel',{
			title:'规格比例设置',
			layout:'border',
			items:[grid]
		});

		tabpanel.add(container);
		tabpanel.setActiveItem(0);
		//alert(1);
		showSaleHisGrid(container);
	}
	//包装箱比例设置
	function createPrdpkGrid(initColumns){
		var params=getParams();
//		if(!params.versno){
//			Ext.Msg.alert("消息","请先选择版型!");
//			return;
//		}
		if(!params.sizegp){
			Ext.Msg.alert("消息","请先选择规格范围!");
			return;
		}
		params.sizety='PRDPK';
		var grid=Ext.create('y.pubsize.OrdPrdpkGrid',{
			title:'包装箱比例设置',
			//activeTab :0,
			tbar:[{
				xtyle:'label',
				text:'注意：这里只能输入小数，例如0.2，表示这个包装箱占比20%'
			}],
			params:params,
			initColumns:initColumns
		});
		//alert(1);
		tabpanel.add(grid);
	}
	
	function showSaleHisGrid(container){
		var params=getParams1();
		Ext.Ajax.request({
			url:Ext.ContextPath+"/ordSzrt/querySaleHisGrid.do",
			params:params,
			success:function(response){
			 	//console.log(response.responseText);
				var obj=Ext.decode(response.responseText);
				var saleHisGrid=Ext.create("y.pubsize.SaleHisGrid",{
					region:'south',
					height:260,
					initColumns:obj.list_columns
				});
				saleHisGrid.getStore().loadData(obj.list_data);
				container.add(saleHisGrid);
			}
		});
	}
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[tabpanel]
	});



});