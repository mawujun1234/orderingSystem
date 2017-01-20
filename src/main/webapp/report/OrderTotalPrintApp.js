
Ext.onReady(function(){
	
	
	var dockedItems=[];
	
	dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
	  
	 dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				listeners:{
					select:function( combo, record, eOpts ) {	
//						
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
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
		        	select:function( combo, record, eOpts ) {

		        	}	
		        }
		    },{
		  		fieldLabel: '营销公司',
		  		labelWidth:80,
		  		width:180,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'yxgsno',
				xtype:'orgcombo',
				showBlank:true,
				listeners:{
					select:function( combo, record, eOpts ) {
						//var regioncombo=combo.nextSibling("#qyno");
		        		//regioncombo.reload(record.get("orgno"));
					}
				}
			},{
				fieldLabel: '渠道类型',
				labelWidth:65,
				width:150,
				//allowBlank: false,
				showBlank:false,
	            //afterLabelTextTpl: Ext.required,
				itemId: 'channo',
				xtype:'channocombo',
				//value:'QY',
				listeners:{
					select:function( combo, record, eOpts ) {
						var toolbars=grid.getDockedItems('toolbar[dock="top"]');
						var shenp_btn=toolbars[2].down("#shenp_btn");
						if(record.get("channo")=='QY'){
							shenp_btn.enable();
						} else {
							shenp_btn.disable();
						}

					}
				}
			 }]
	  });
	  
	  dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
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
		        fieldLabel: '订货样衣编号',
		        labelWidth:85,
		        width:185,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();

				},
				iconCls: 'icon-refresh'
			}]
	   });
	   dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		text: '打印1',
				handler: function(btn){
			    	var params=grid.getParams();
			    	var url=Ext.ContextPath+"/report/orderTotalPrint/export1.do?"+Ext.urlEncode(params);
			    	window.open(url);
				},
				iconCls: 'icon-download-alt'
		  	},{
		  		text: '打印2',
				handler: function(btn){
					var params=grid.getParams();
			    	var url=Ext.ContextPath+"/report/orderTotalPrint/export2.do?"+Ext.urlEncode(params);
			    	window.open(url);
				},
				iconCls: 'icon-download-alt'
		  	},{
		  		text: '打印3(出样)',
				handler: function(btn){
					var params=grid.getParams();
			    	var url=Ext.ContextPath+"/report/orderTotalPrint/export3.do?"+Ext.urlEncode(params);
			    	window.open(url);
				},
				iconCls: 'icon-download-alt'
		  	},{
		  		text: '打印-搭配',
				handler: function(btn){
					var params=grid.getParams();
			    	var url=Ext.ContextPath+"/report/printDaPei.do?"+Ext.urlEncode(params);
			    	window.open(url);
				},
				iconCls: 'icon-download-alt'
		  	},{
		  		text: '审批通过',
		  		itemId:'shenp_btn',
		  		//disabled:true,
				handler: function(btn){
					Ext.Msg.confirm("消息","确定要提交<span style='color:red;'>品牌+大类</span>下所有区域的订单状态吗?",function(btn){
						if(btn=='yes'){
							Ext.Ajax.request({
								url:Ext.ContextPath+'/ord/process1.do',
								method:'POST',
								params:grid.getStore().getProxy().extraParams,
								success:function(response){
									var obj=Ext.decode(response.responseText);
									if(!obj.success){
										Ext.Msg.alert("消息",obj.msg);
										return;
									}
									Ext.Msg.alert("消息","成功");
								}
							});
						}
					})
				},
				iconCls: 'icon-edit'
		  	}]
	   });
	 
//	 var cellEditing = new Ext.grid.plugin.CellEditing({  
//            clicksToEdit : 1  
//      });  
//	 cellEditing.on("edit",function(editor, context){
//	 	return;
//	  	var record=context.record;
//	  	var grid=context.grid;
//	  	var field =context.field ;
//	  	var value=context.value;
//	  	Ext.Ajax.request({
//						url:Ext.ContextPath+'/tp/updateOrmtqt_tp.do',
//						params:{
//							mtorno:record.get("mtorno"),
//							sampno:record.get("sampno"),
//							suitno:record.get("suitno"),
//							ormtqt:value
//						},
//						success:function(){
//							record.commit();
//		
//						}
//						
//		});
//	  });
	  
	 var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields:['SPCLNM','SPTYNM','SPSENM','SPRSENM','SPSEANM','SPBANM','SAMPNM',
			
			'SPCTPR','ORMTQT'],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/report/orderTotalPrint/query.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'
				}
			},
			listeners:{
				load:function(store,records){
				
				}
			}
	});
	var grid=Ext.create('Ext.grid.Panel',{
		region:'center',
		columnLines :true,
		stripeRows:true,
		dockedItems:dockedItems,
		store:store,
		//plugins : [cellEditing],
		columns:[
			{xtype: 'rownumberer'},
			{dataIndex:'SAMPNM',header:'订货样衣编号'},
			{dataIndex:'BRADNM',header:'品牌'},
			{dataIndex:'SPCLNM',header:'大类'},
			{dataIndex:'SPTYNM',header:'小类'},
			{dataIndex:'SPSENM',header:'系列'},
			{dataIndex:'SPBANM',header:'上市批次'},
			{dataIndex:'SPRTPR',header:'零售价'},
			{dataIndex:'YXGSNM',header:'营销公司'},
			{dataIndex:'ORMTQT',header:'订货总量'},
			{dataIndex:'CHANNM',header:'渠道类型'}
		]
		
	});

	grid.getParams=function(){
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');
		var params={
			start:0,
			page:1,
			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
			"params['bradno']":toolbars[0].down("#bradno").getValue(),
			"params['yxgsno']":toolbars[0].down("#yxgsno").getValue(),
			"params['channo']":toolbars[0].down("#channo").getValue(),
			
			"params['spclno']":toolbars[1].down("#spclno").getValue(),
			"params['sptyno']":toolbars[1].down("#sptyno").getValue(),
			"params['spseno']":toolbars[1].down("#spseno").getValue(),
			"params['sampnm']":toolbars[1].down("#sampnm").getValue()
			
		};
		return params;
	}
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});