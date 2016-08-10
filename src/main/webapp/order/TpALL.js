
Ext.onReady(function(){
	var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields:['ORMTNO','BRADNO','SPCLNO','SPTYNO','SPSENO','SAMPNM','SUITNO','PACKQT','ORMTQT','ORMTQT_TP'],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/tp/zgs_tpAllQuery.do',
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
//					if(records!=null && records.length>0){
//						window.orstat=records[0].get("ORSTAT");
//					
//					}
				}
			}
	});
	
	var dockedItems=[];
	
//	dockedItems.push({
//	        xtype: 'pagingtoolbar',
//	        store: store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  });
	  
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
	    				grid.query_stat();
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
		    }]
	  });
	  
	  dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		        fieldLabel: '订货样衣编号',
		        labelWidth:85,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
    				
					grid.getStore().reload();
					
					grid.query_stat();

				},
				iconCls: 'icon-refresh'
			}]
	   });
	   
	   dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		text: '恢复订制',
		  		disabled:true,
		  		itemId:'restoreDZ_btn',
				handler: function(btn){
					grid.restoreDZ();
				},
				iconCls: 'icon-remove'
		  	},{
		  		text: '订单完成',
		  		itemId:'over_btn',
		  		disabled:true,
				handler: function(btn){
					grid.over();
				},
				iconCls: ' icon-coffee'
		  	},{
		  		text: '导出',
				handler: function(btn){
					grid.onExport();
				},
				iconCls: 'icon-download-alt'
		  	}]
	    });
	 
	 var cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
      
      cellEditing.on("beforeedit",function(editor, context){
//      	var record=context.record;
//	  	var grid=context.grid;
//	  	var field =context.field ;
//	  	var value=context.value;
//	  	var PACKQT=record.get("PACKQT");
	  	if(window.orstat!=0){
	  		return false;
	  	}
	  	
      	
      })
	 cellEditing.on("edit",function(editor, context){
	 	
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	var originalValue=context.originalValue;
	  	var PACKQT=record.get("PACKQT");

	  	//商品部录入统配总量，要求统配总量为包装要求的整数倍
	  	if(record.get("SUITNO")!='T02' && (value/PACKQT-parseInt(value/PACKQT))!=0){
	  		Ext.Msg.alert("消息","统配数量必须是包装数量的整数倍!");
	  		record.set(field,originalValue);
	  		return false;
	  	}
	  	
	  	Ext.Ajax.request({
						url:Ext.ContextPath+'/tp/zgs_updateOrmtqt_tp.do',
						params:{
							//ordorg:spb_orgno,
							ormtno:record.get("ORMTNO"),
							sampno:record.get("SAMPNO"),
							bradno:record.get("BRADNO"),
							spclno:record.get("SPCLNO"),
							suitno:record.get("SUITNO"),
							ormtqs:record.get("ORMTQT"),
							ormtqt:value
						},
						success:function(){
							record.commit();
		
						}
						
		});
	  });
	var grid=Ext.create('Ext.grid.Panel',{
		region:'center',
		columnLines :true,
		stripeRows:true,
		dockedItems:dockedItems,
		store:store,
		plugins : [cellEditing],
		columns:[
			{xtype: 'rownumberer'},
			{dataIndex:'SAMPNO',header:'图片',width:55
	      		,renderer:function(value, metaData, record, rowIndex, colIndex, store){
		            	 
		            	 return "<a href='#' onclick='clickShowPhoto(\""+value+"\")'>查看</a>";
	            }
	        },
			{dataIndex:'SPTYNO_NAME',header:'小类'
	        },
	        {dataIndex:'SPSENO_NAME',header:'系列'
	        },
	        {dataIndex:'SAMPNM',header:'订货样衣编号'
	        },
	        {dataIndex:'SUITNO_NAME',header:'套件'
	        },
	        {dataIndex:'PACKQT',header:'包装要求'
	        },
	        {dataIndex:'ORMTQT',header:'订制总数'
	        },
	        {dataIndex:'ORMTQT_TP',header:'统配总数',
	        	renderer:function(value, metaData, record, rowIndex, colIndex, store){
	        		if(window.orstat==0){
	        			metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	        		}
					
	            	 return value;
	            },editor: {
	                xtype: 'numberfield',
	                allowDecimals:false,
	                selectOnFocus:true 
	            }
	        }
		]
		
	});
	//var spb_orgno='10206030000';//商品部的id
	grid.getParams=function(){
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');
		var params={
			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
			"params['bradno']":toolbars[0].down("#bradno").getValue(),
			"params['spclno']":toolbars[0].down("#spclno").getValue(),
			"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
			"params['spseno']":toolbars[0].down("#spseno").getValue(),
			//"params['mtorno']":toolbars[0].down("#ordmtcombo").getValue()+"_TP_"+spb_orgno,
			"params['sampnm']":toolbars[1].down("#sampnm").getValue()
			//"params['orstat']":toolbars[1].down("#orstat").getValue()
		};
		return params;
	}
	grid.query_stat=function(){
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');
		
		Ext.Ajax.request({
							url:Ext.ContextPath+'/tp/zgs_getOrstat.do',
							params:{
								//ordorg:spb_orgno,
								ormtno:toolbars[0].down("#ordmtcombo").getValue(),
								bradno:toolbars[0].down("#bradno").getValue(),
								spclno:toolbars[0].down("#spclno").getValue()
							},
							success:function(response){
								var obj=Ext.decode(response.responseText);
								window.orstat=obj.orstat;
								if(obj.orstat!=0){
									//var toolbars=grid.getDockedItems('toolbar[dock="top"]');
									var over_btn=toolbars[2].down("#over_btn");
									over_btn.disable();
									var restoreDZ_btn=toolbars[2].down("#restoreDZ_btn");
									restoreDZ_btn.disable();
									
								} else {
									//var toolbars=grid.getDockedItems('toolbar[dock="top"]');
									var over_btn=toolbars[2].down("#over_btn");
									over_btn.enable();
									var restoreDZ_btn=toolbars[2].down("#restoreDZ_btn");
									restoreDZ_btn.enable();
								}
								//grid.getStore().reload();
							}
						});
	}
	grid.restoreDZ=function(){
		Ext.Msg.confirm("消息","确定要回复定制吗?",function(btn){
			if(btn=='yes'){
		var records=grid.getSelectionModel().getSelection();
		if(records==null || records.length==0){
			Ext.Msg.alert("消息","请先选择行!");
			return;
		}
		var record=records[0];
		Ext.Ajax.request({
			url:Ext.ContextPath+'/tp/zgs_restoreDZ.do',
			params:{
				//ordorg:spb_orgno,
				ormtno:record.get("ORMTNO"),
				sampno:record.get("SAMPNO"),
				bradno:record.get("BRADNO"),
				spclno:record.get("SPCLNO"),
				suitno:record.get("SUITNO")
			},
			success:function(){
				grid.getStore().reload();
			}
		});
			}
		});
	
	}
	
	grid.over=function(){
		Ext.Msg.confirm("消息","确定要按‘品牌+大类’完成吗?",function(btn){
			if(btn=='yes'){
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');
		Ext.Ajax.request({
			url:Ext.ContextPath+'/tp/zgs_over.do',
			params:{
				//ordorg:spb_orgno,
				ormtno:toolbars[0].down("#ordmtcombo").getValue(),
				bradno:toolbars[0].down("#bradno").getValue(),
				spclno:toolbars[0].down("#spclno").getValue()
			},
			success:function(){
				
//				var over_btn=toolbars[2].down("#over_btn");
//				over_btn.disable();
//				var restoreDZ_btn=toolbars[2].down("#restoreDZ_btn");
//				restoreDZ_btn.disable();
//				window.orstat=3;
				grid.query_stat();
				grid.getStore().reload();
			}
		});
			}
		});
	}
	
	grid.onExport=function(){
    	//var me=this;
    	var params=grid.getParams();
    	var url=Ext.ContextPath+"/tp/zgs_tpAllExport.do?"+Ext.urlEncode(params);
    	window.open(url);
    }
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});