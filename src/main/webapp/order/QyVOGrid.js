Ext.define('y.order.QyVOGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.order.QyVO',
	     'y.order.QyVONewForm'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
        {dataIndex:'channo_name',header:'渠道类型',hidden:true
        },
		{dataIndex:'ordorg_name',header:'订货单位'
        },
        {dataIndex:'orstat',header:'订货状态',renderer:function(value){
        		if(value==0){
        			return "编辑中";
        		} else if(value==1){
        			return "大区审批中";
        		} else if(value==2){
        			return "总部审批中";
        		} else if(value==3){
        			return "审批通过";
        		} else if(value==4){
        			return "退回";
        		}
        	}
        },
		{dataIndex:'sptyno_name',header:'小类'
        },
		{dataIndex:'spseno_name',header:'系列'
        },
		{dataIndex:'plspnm',header:'企划样衣编号',hidden:true
        },
		{dataIndex:'sampnm',header:'设计样衣编号'
        },
        {dataIndex:'spftpr',header:'出厂价'
        },
        {dataIndex:'sprtpr',header:'零售价'
        },
		{dataIndex:'packqt',header:'包装要求'
        },
		{dataIndex:'suitno_name',header:'套件',width:70
        },
		{dataIndex:'ormtqs',header:'原始数量',width:75
        },
        {dataIndex:'ormtqs_zhes',header:'原始数量折算',width:75
//        	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
//        		var suitno=record.get("suitno");
//        		if("T00"==suitno){
//					return value*1;
//				} else if("T01"==suitno){//上衣
//					return value*0.75; 
//				} else if("T02"==suitno){//裤子
//					return value*0.25; 
//				} else if("T04"==suitno){//裙子
//					return value*0.25;
//				} else {
//					return value*1;
//				}
//        	}
        },
		{dataIndex:'ormtqt',header:'平衡数量',width:75,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				if(record.get("orstat")==0 || record.get("orstat")==1){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
				}

            	 return value;
            },editor: {
                xtype: 'numberfield',
                allowDecimals:false,
                selectOnFocus:true 
            }
        }
        ,{dataIndex:'ormtqt_zhes',header:'平衡数量折算',width:75},
        {dataIndex:'spftpr_jine',header:'出厂金额'
        },
        {dataIndex:'sprtpr_jine',header:'零售金额'
        }
      ];
      
      this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  
	  this.cellEditing.on("beforeedit",function(editor, context){
	   	var record=context.record;
	   	//console.log(record.get("orstat"));
	   	if(record.get("orstat")!=0 && record.get("orstat")!=4){//alert(1);
			return false;
		}
	  });
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	
	  	//区域的平衡数量必须 为包装要求的整数倍,但是裤子可以不是整数倍
	  	if(record.get("channo")=="QY" && record.get("suitno")!="T02"){
	  		var temp=value/record.get("packqt");
	  		if(parseInt(temp)!=temp){
	  			Ext.Msg.alert("消息","修改失败，输入的平衡数量必须是‘包装要求’的整数倍!");
	  			record.set("ormtqt", context.originalValue);
	  			return;
	  		}
	  	}

	  	Ext.Ajax.request({
						url:Ext.ContextPath+'/ord/quVO/updateOrmtqt.do',
						params:{
							mtorno:record.get("mtorno"),
							sampno:record.get("sampno"),
							suitno:record.get("suitno"),
							ormtqt:value
						},
						success:function(){
							record.commit();
							//me.getStore().reload();
							//计算折算值，放在前面好像会报错
							var suitno=record.get("suitno");
			        		if("T00"==suitno){
								record.set("ormtqt_zhes", value);
							} else if("T01"==suitno){//上衣
								record.set("ormtqt_zhes", (value*0.75).toFixed( 2 )); 
							} else if("T02"==suitno){//裤子
								record.set("ormtqt_zhes", (value*0.25).toFixed( 2 )); 
							} else if("T04"==suitno){//裙子
								record.set("ormtqt_zhes", (value*0.25).toFixed( 2 ));
							} else {
								record.set("ormtqt_zhes", value*1);
							}
							record.set("spftpr_jine", (value*record.get("spftpr")).toFixed( 2 )); 
							record.set("sprtpr_jine", (value*record.get("sprtpr")).toFixed( 2 )); 
							//刷新汇总信息
							grid.reloadTotal(grid.getStore().getProxy().extraParams);
						}
						
					});
	  	
	  });
      

	 
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.order.QyVO',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ord/quVO/queryQyVO.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			},
			listeners:{
				load:function(store,records){
					if(records && records.length>0){
						if(!me.createNew_btn){
							var toolbars=me.getDockedItems('toolbar[dock="top"]');
							var createNew=toolbars[2].down("#createNew");
							var updateApprove=toolbars[2].down("#updateApprove");
							
							me.createNew_btn=createNew;
							me.updateApprove_btn=updateApprove;
						}
						
						if(records[0].get("orstat")==0 || records[0].get("orstat")==4 ){
							me.createNew_btn.enable();
							me.updateApprove_btn.enable();
						} else {
							me.createNew_btn.disable();
							me.updateApprove_btn.disable();
						}
						
					}
				}
			}
	  });

//	  me.ordorg_store=Ext.create('Ext.data.Store',{
//				    fields: ['orgno', 'orgnm'],
//				    autoLoad:false,
//				    proxy: {
//				        type: 'ajax',
//				        url: Ext.ContextPath+'/ord/queryOrdorg.do'
//				    }
//				});
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
		        		
		        		var channo=combo.nextSibling("#channo");
		        		channo.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			ormtno:record.get("ormtno")
		        		});
		        		channo.getStore().reload();
					}
				}
			},{
		  		fieldLabel: '营销公司',
		  		labelWidth:60,
		  		width:160,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'yxgsno',
		  		showBlank:false,
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
		  		allowBlank: false,
		  		showBlank:false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'qyno',
				xtype:'orgcombo',
				autoLoad:false,
				listeners:{
					select:function( combo, record, eOpts ) {
		        		
						var ordorg=combo.nextSibling("#ordorg");
		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			qyno:record.get("orgno")
		        		});
		        		ordorg.getStore().reload();
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
		        		ordorg.getStore().reload();
					}
				}
			 },{
				fieldLabel: '订货单位',
				labelWidth:65,
				width:170,
//				allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"订货单位不允许为空",
				itemId: 'ordorg',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'orgnm',
			    valueField: 'orgno',
			    //store:me.ordorg_store,
			    store:{
				    fields: ['orgno', 'orgnm'],
				    storeId:'ordorg_storeId',
				    autoLoad:false,
				    proxy: {
				        type: 'ajax',
				        url: Ext.ContextPath+'/ord/queryOrdorg.do'
				    }
//				    listeners:{
//				    	load:function(store){
//				    		var toolbars=this.getDockedItems('toolbar[dock="top"]');
//				    		var ordorg_combo=toolbars.down("#ordorg");
//				    		if(!ordorg_combo.value ){
//								if(myStore.getCount( ) >0){
//						 			var r=myStore.getAt(1);
//						 			me.select( r );
//						 			me.fireEvent("select", me, r);
//						 		}
//							}
//				    	}
//				    }
				},
	            hidden:false,
				xtype:'combobox'
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
	            value:'DZ',
				//selFirst:true,
				width:150,
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
		        fieldLabel: '套件',
		        itemId: 'suitno',
		        //selFirst:true,
	            xtype:'pubcodecombo',
		        tyno:'3',
		        labelWidth:45,
				width:150
		    }]
	   });
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		        fieldLabel: '订货样衣编号',
		        labelWidth:85,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    },{
				fieldLabel: '订单状态',
				labelWidth:65,
		        width:165,
				itemId: 'orstat',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'id',
			    store: {
				    fields: ['id', 'name'],
				    data:[{id:'0',name:'编辑中'},{id:'1',name:'大区审批中'},{id:'2',name:'总部审批中'},{id:'3',name:'审批通过'},{id:'4',name:'退回'}]
				},
	            hidden:false,
				xtype:'combobox'
			 },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
					//重新统计汇总数据
					grid.reloadTotal(grid.getStore().getProxy().extraParams);
				},
				iconCls: 'icon-refresh'
			},{
				text: '新增未定样衣',
				itemId:'createNew',
				handler: function(btn){
					me.createNew();
				},
				iconCls: 'icon-plus'
			},{
			    text: '提交审批',
			    itemId:'updateApprove',
			    handler: function(btn){
			    	var toolbars=btn.up("grid").getDockedItems('toolbar[dock="top"]');
			    	var ordorg_name=toolbars[0].down("#ordorg").getRawValue();
			    	if(!ordorg_name){
			    		ordorg_name="当前区域下的<span style='color:red;'>所有订货单位</span>";
			    	} else {
			    		ordorg_name="<span style='color:red;'>"+ordorg_name+"</span>订货单位";
			    	}
	
			    	
					Ext.Msg.confirm("消息","是否确定要提交审批!"+ordorg_name+"和当前选中的<span style='color:red;'>‘"+toolbars[1].down("#bradno").getRawValue()+"+"+toolbars[1].down("#spclno").getRawValue()+"’</span>的数据都会被提交!",function(btn){
						if(btn=='yes'){
							me.updateApprove();
						}
					});
			    },
			    iconCls: 'icon-edit'
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
			"params['channo']":toolbars[0].down("#channo").getValue(),	
			"params['ordorg']":toolbars[0].down("#ordorg").getValue(),
			
			"params['ortyno']":toolbars[1].down("#ortyno").getValue(),
			"params['bradno']":toolbars[1].down("#bradno").getValue(),
			"params['spclno']":toolbars[1].down("#spclno").getValue(),
			"params['sptyno']":toolbars[1].down("#sptyno").getValue(),
			"params['spseno']":toolbars[1].down("#spseno").getValue(),
			"params['suitno']":toolbars[1].down("#suitno").getValue(),
			"params['sampnm']":toolbars[2].down("#sampnm").getValue(),
			"params['orstat']":toolbars[2].down("#orstat").getValue()
		};
		return params;
	},
	createNew:function(){
		var me=this;
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var ordorg=toolbars[0].down("#ordorg").getValue();
//		if(!ordorg){
//			Ext.Msg.alert("消息","请先选择一个订货单位!");
//			return;
//		}

		
		
    	var qyVONewForm=Ext.create('y.order.QyVONewForm',{
    		//ordorg_store:ordorg.getStore(),
    		params:{
    			ordorg:ordorg,
	    		ortyno:'DZ',
	    		channo:toolbars[0].down("#channo").getValue(),
	    		ormtno:toolbars[0].down("#ordmtcombo").getValue()
    		}
    	});
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[qyVONewForm],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    },
    updateApprove:function(){
    	var me=this;
    	var toolbars=this.getDockedItems('toolbar[dock="top"]');
    	var params={
    		ormtno:toolbars[0].down("#ordmtcombo").getValue(),
    		qyno:toolbars[0].down("#qyno").getValue(),
			channo:toolbars[0].down("#channo").getValue(),
    		ordorg:toolbars[0].down("#ordorg").getValue(),
    		bradno:toolbars[1].down("#bradno").getValue(),
    		spclno:toolbars[1].down("#spclno").getValue()
    		
    	};
    	Ext.Ajax.request({
			url:Ext.ContextPath+'/ord/qyVO/updateApprove.do',
			params:params,
			method:'POST',
			success:function(response){
				
				var obj=Ext.decode(response.responseText);
				if(obj.success==false){
					Ext.Msg.alert("消息",obj.msg);
					return;
				}
				Ext.Msg.alert("消息","提交成功!");
				//button.up('window').close();
				me.getStore().reload();
			}
		});
    },
    reloadTotal:function(params){
    	var me=this;
    	Ext.Ajax.request({
			url:Ext.ContextPath+'/ord/qyVO/reloadTotal.do',
			params:params,
			method:'POST',
			success:function(response){
				var obj=Ext.decode(response.responseText);
				if(obj.success==false){
					Ext.Msg.alert("消息",obj.msg);
					return;
				}
				me.total_panel.down("#ormtqs").setValue(obj.ormtqs);
				me.total_panel.down("#ormtqs_zhes").setValue(obj.ormtqs_zhes);
				me.total_panel.down("#ormtqt").setValue(obj.ormtqt);
				me.total_panel.down("#ormtqt_zhes").setValue(obj.ormtqt_zhes);
				me.total_panel.down("#spftpr_jine").setValue(obj.spftpr_jine);
				me.total_panel.down("#sprtpr_jine").setValue(obj.sprtpr_jine);
				
			}
		});
    }
   
    
});
