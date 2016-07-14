Ext.define('y.plan.PlanOrgdtlVOGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.plan.PlanOrgdtlVO'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
      	{dataIndex:'orgnm',header:'区域',sortable:false,
      		renderer:function(value, metaData, record, rowIndex, colIndex, store){
						if(record.get("isTotal")==true){
							 metaData.tdStyle = 'background-color:#CD9B9B;' ;
							 return "<span style='font-weight:bold;'>"+value+"</span>";
						}
		            	return value;
		            }
        },
        {dataIndex:'spclnm',header:'大类',sortable:false,
        	renderer:function(value, metaData, record, rowIndex, colIndex, store){
						if(record.get("isTotal")==true){
							 metaData.tdStyle = 'background-color:#CD9B9B;' ;
							 return "<span style='font-weight:bold;'>"+value+"</span>";
						}
		            	return value;
		            }
        },
		{dataIndex:'sptynm',header:'小类',sortable:false,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
						if(record.get("isTotal")==true){
							 metaData.tdStyle = 'background-color:#CD9B9B;' ;
							 return "<span style='font-weight:bold;'>"+value+"</span>";
						}
		            	return value;
		            }
        },
		{dataIndex:'spsenm',header:'系列',sortable:false,
					renderer:function(value, metaData, record, rowIndex, colIndex, store){
						if(record.get("isTotal")==true){
							 metaData.tdStyle = 'background-color:#CD9B9B;' ;
						}
		            	return value;
		            }
        },
        {
        	header:'区域',
        	columns:[
        		{
        			dataIndex:'qymtqt',header:'区域指标数量',xtype: 'numbercolumn', format:'0.00',align : 'right',width:120,sortable:false,
        			renderer:function(value, metaData, record, rowIndex, colIndex, store){
						if(record.get("plstat")==0  && record.get("isTotal")==false){
							metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
						}  else if(record.get("isTotal")==true){
							 metaData.tdStyle = 'background-color:#CD9B9B;' ;
						}
		            	return value;
		            },editor: {
		                xtype: 'numberfield',
		                allowDecimals:false,
		                selectOnFocus:true 
		            }
				},{
					dataIndex:'qymtam',header:'区域指标金额(万元)',xtype: 'numbercolumn', format:'0.00',align : 'right',width:150,sortable:false,
					renderer:function(value, metaData, record, rowIndex, colIndex, store){
						if(record.get("plstat")==0  && record.get("isTotal")==false){
							metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
						}  else if(record.get("isTotal")==true){
							 metaData.tdStyle = 'background-color:#CD9B9B;' ;
						}
		            	return value;
		            },editor: {
		                xtype: 'numberfield',
		                allowDecimals:true,
		                selectOnFocus:true 
		            }
			}]
        },
        {
        	header:'特许',
        	columns:[{
        		dataIndex:'txmtqt',header:'特许指标数量',xtype: 'numbercolumn', format:'0.00',align : 'right',width:120,sortable:false,
        			renderer:function(value, metaData, record, rowIndex, colIndex, store){
						if(record.get("plstat")==0  && record.get("isTotal")==false){
							metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
						} else if(record.get("isTotal")==true){
							 metaData.tdStyle = 'background-color:#CD9B9B;' ;
						}
		            	return value;
		            },editor: {
		                xtype: 'numberfield',
		                allowDecimals:true,
		                selectOnFocus:true 
		            }
			},
			{
				dataIndex:'txmtam',header:'特许指标金额(万元)',xtype: 'numbercolumn', format:'0.00',align : 'right',width:150,sortable:false,
					renderer:function(value, metaData, record, rowIndex, colIndex, store){
						if(record.get("plstat")==0 && record.get("isTotal")==false){
							metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
						}  else if(record.get("isTotal")==true){
							 metaData.tdStyle = 'background-color:#CD9B9B;' ;
						}
		            	return value;
		            },editor: {
		                xtype: 'numberfield',
		                allowDecimals:false,
		                selectOnFocus:true 
		            }
			}]
        }
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.plan.PlanOrgdtlVO',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/planOrg/queryPlanOrgdtlVO.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json'
//					rootProperty:'root',
//					successProperty:'success',
//					totalProperty:'total'		
				}
			}
	  });

	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1 
      });     
	  this.plugins = [this.cellEditing];
	  
	   this.cellEditing.on("beforeedit",function(editor, context){
	   		var record=context.record;
	   		if(record.get("plstat")!=0 || record.get("isTotal")==true){
	   			return false;
	   		}
	   		return true;
	   });
	  this.cellEditing.on("edit",function(editor, context){
	    var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	
	  	Ext.Ajax.request({
						url:Ext.ContextPath+'/planOrg/createOrUpdate.do',
						jsonData:record.getData(),
						success:function(response){
							var obj=Ext.decode(response.responseText);
							if(obj.success==false){
								Ext.Msg.alert("消息",obj.msg);
								return;
							}
							record.commit();
							//me.getStore().reload();
						}
						
					});
	  	
	  });
	  
	  me.dockedItems=[];
//      me.dockedItems.push({
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  });
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				listeners:{
					select:function( combo, record, eOpts ) {	
//						var ordorg=combo.nextSibling("#ordorg");
//		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
//		        			ormtno:record.get("ormtno")
//		        		});
//		        		//ordorg.getStore().reload();
//		        		
//		        		var channo=combo.nextSibling("#channo");
//		        		channo.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
//		        			ormtno:record.get("ormtno")
//		        		});
//		        		channo.getStore().reload();
						if(record.get("ormtst")==true){
							var toolbars=me.getDockedItems('toolbar[dock="top"]');
							var onImport=toolbars[2].down("#onImport");
							onImport.disable();
						} else {
							var toolbars=me.getDockedItems('toolbar[dock="top"]');
							var onImport=toolbars[2].down("#onImport");
							onImport.enable();
						}
					}
				}
			},{
		  		fieldLabel: '营销公司',
		  		labelWidth:75,
		  		width:175,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'yxgsno',
				xtype:'orgcombo',
				showBlank:false,
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
			}]
		});
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
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
//		        		var toolbar=combo.up("toolbar");
//		        		var suitno=toolbar.down("#suitno");
//		        		suitno.changeBradno(record.get("itno"));
//		        		suitno.getStore().reload();
		        	}	
		        }
		    },{
		        fieldLabel: '大类',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"大类不允许为空",
	             selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'0',
		        listeners:{
		        	select:function( combo, record, eOpts ) {
//		        		var sptyno=combo.nextSibling("#sptyno");
//		        		sptyno.reload(record.get("itno"));
		        		if(record.get("itno")){
		        			var spseno=combo.nextSibling("#spseno");
		        			spseno.reload(record.get("itno"));
		        		} else {
		        			var spseno=combo.nextSibling("#spseno");
		        			spseno.getStore().removeAll();
		        		}
		        		
		        	}	
		        }
		    },{
		        fieldLabel: '系列',
		        itemId: 'spseno',
		        labelWidth:40,
		        width:160,
	            autoLoad:false,
		        xtype:'pubcodecombo',
		        tyno:'5'
		    },{
				fieldLabel: '订单状态',
				labelWidth:65,
		        width:165,
				itemId: 'plstat',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'value',
			    store: {
				    fields: ['value', 'name'],
				    data:[{value:'',name:'无'},{value:'0',name:'编辑中'},{value:'1',name:'审批中'},{value:'2',name:'大区审批通过'},{value:'3',name:'总部审批通过'},{value:'4',name:'退回'}]
				},
	            hidden:false,
				xtype:'combobox'
			 },{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			},{
				text: '刷新小计/合计',
				//itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});
		
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '通过',
				hidden:!Permision.canShow('plan_orgdtl_pass'),
				handler: function(btn){
					me.onPass();
				},
				iconCls: 'icon-ok'
			},{
				text: '提交审批',
				hidden:!Permision.canShow('plan_orgdtl_submit'),
				handler: function(btn){
					me.onSubmit();
				},
				iconCls: 'icon-ok'
			},{
			    text: '退回',
			    hidden:!Permision.canShow('plan_orgdtl_back'),
			    //itemId:'update',
			    handler: function(){
			    	me.onBack();
					
			    },
			    iconCls: 'icon-reply'
			},{
			   text: '导出',
				handler: function(btn){
					me.onExport();
				},
				iconCls: 'icon-download-alt'
			},{
			   text: '导入',
			   itemId:'onImport',
			   hidden:!Permision.canShow('plan_orgdtl_import'),
				handler: function(btn){
					me.onImport();
				},
				iconCls: 'icon-upload-alt'
			}]
		});

       
      me.callParent();
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var params={
			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
			"params['yxgsno']":toolbars[0].down("#yxgsno").getValue(),
			"params['qyno']":toolbars[0].down("#qyno").getValue(),
			//"params['channo']":toolbars[0].down("#channo").getValue(),	
			//"params['ordorg']":toolbars[0].down("#ordorg").getValue(),
			
			//"params['ortyno']":toolbars[1].down("#ortyno").getValue(),
			"params['bradno']":toolbars[1].down("#bradno").getValue(),
			"params['spclno']":toolbars[1].down("#spclno").getValue(),
			//"params['sptyno']":toolbars[1].down("#sptyno").getValue(),
			"params['spseno']":toolbars[1].down("#spseno").getValue(),
			//"params['suitno']":toolbars[1].down("#suitno").getValue(),
			//"params['sampno']":toolbars[2].down("#sampno").getValue(),
			"params['plstat']":toolbars[1].down("#plstat").getValue()
		};
		return params;
	},
	onSubmit:function(){
		var me=this;
	 	Ext.Msg.confirm("消息","确定要提交审批吗?",function(btn){
	 		//console.log(btn);
	 		if(btn=='yes'){
				var toolbars=me.getDockedItems('toolbar[dock="top"]');
	 			Ext.Ajax.request({
	 				url:Ext.ContextPath+'/planOrg/onSubmit.do',
	 				params:{
	 					"ormtno":toolbars[0].down("#ordmtcombo").getValue(),
	 					"yxgsno":toolbars[0].down("#yxgsno").getValue(),
	 					"ordorg":toolbars[0].down("#qyno").getValue(),
	 					"bradno":toolbars[1].down("#bradno").getValue()
	 				},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						if(obj.success==false){
							Ext.Msg.alert("消息",obj.msg);
							return;
						}
						me.getStore().reload();
					}
	 			});
	 		}
	 	});
	},
	onPass:function(){
		var me=this;
	 	Ext.Msg.confirm("消息","确定通过吗?",function(btn){
	 		//console.log(btn);
	 		if(btn=='yes'){
				var toolbars=me.getDockedItems('toolbar[dock="top"]');
	 			Ext.Ajax.request({
	 				url:Ext.ContextPath+'/planOrg/onPass.do',
	 				params:{
	 					"ormtno":toolbars[0].down("#ordmtcombo").getValue(),
	 					"yxgsno":toolbars[0].down("#yxgsno").getValue(),
	 					"ordorg":toolbars[0].down("#qyno").getValue(),
	 					"bradno":toolbars[1].down("#bradno").getValue()
	 				},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						if(obj.success==false){
							Ext.Msg.alert("消息",obj.msg);
							return;
						}
						me.getStore().reload();
					}
	 			});
	 		}
	 	});
	},
	onBack:function(){
		var me=this;
	 	Ext.Msg.confirm("消息","确定退回，重新让区域填写吗?",function(btn){
	 		//console.log(btn);
	 		if(btn=='yes'){
//	 			//获取第一条记录
//	 			var  record=me.getStore().getAt(0);
//	 			if(record.get("plstat")){
//	 			
//	 			}
	 			var toolbars=me.getDockedItems('toolbar[dock="top"]');
	 			Ext.Ajax.request({
	 				url:Ext.ContextPath+'/planOrg/onBack.do',
	 				params:{
	 					"ormtno":toolbars[0].down("#ordmtcombo").getValue(),
	 					"yxgsno":toolbars[0].down("#yxgsno").getValue(),
	 					"ordorg":toolbars[0].down("#qyno").getValue(),
	 					"bradno":toolbars[1].down("#bradno").getValue()
	 				},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						if(obj.success==false){
							Ext.Msg.alert("消息",obj.msg);
							return;
						}
						me.getStore().reload();
					}
	 			});
	 		}
	 	});
	},
	onExport:function(){
		var me=this;
    	var params=me.getParams();
    	var url=Ext.ContextPath+"/planOrg/export.do?"+Ext.urlEncode(params);
    	window.open(url);
	},
	onImport:function(){
		var me=this;
		var formpanel=Ext.create('Ext.form.Panel',{
			items:[{
		        xtype: 'filefield',
		        name: 'imageFile',
		       // id:'photo',
		        labelWidth:60,
		        fieldLabel: '文件名',
		        allowBlank: false,
		        anchor: '100%',
		        buttonText: '选择文件...',
		        listeners:{
		        	change:function(field, value){
	
		        	}
		        }
		    }]
		});
		
		var win=Ext.create('Ext.window.Window',{
			items:[formpanel],
			modal:true,
			height:120,
			width:320,
			buttons:[{
				text : '上传',
				//formBind: true, //only enabled once the form is valid
	       		//disabled: true,
				glyph : 0xf0c7,
				handler : function(button){
					
					formpanel.getForm().submit({
						 waitMsg:'正在上传请稍候',  
	                     waitTitle:'提示', 
	                     url:Ext.ContextPath+'/planOrg/import.do', 
	                     //method:'POST', 
	                     success:function(form,action){   	
	                     	button.up('window').close();
	                     	me.getStore().reload();
	                     },
	                     failure:function(form,action){
	                     	Ext.Msg.alert("警告",action.result.msg);                    
	                     }
					});	
				
				}
			}]
		});
		win.show();
	}
});
