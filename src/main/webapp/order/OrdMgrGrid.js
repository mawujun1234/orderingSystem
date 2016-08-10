Ext.define('y.order.OrdMgrGrid',{
	extend:'Ext.grid.Panel',
	requires: [

	],
	columnLines :true,
	stripeRows:true,
	selModel: {
          selType: 'checkboxmodel'
          //,checkOnly:true
    },
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
      	{dataIndex:'ORTYNM',header:'订单类型'
        },
        {dataIndex:'YXGSNM',header:'营销公司'
        },
        {dataIndex:'QYNM',header:'区域'
        },
        {dataIndex:'ORDORG_NAME',header:'订货单位'
        },
        {dataIndex:'CHANNO_NAME',header:'订货单位类型'
        },
        {dataIndex:'BRADNO_NAME',header:'品牌'
        },
        {dataIndex:'SPCLNO_NAME',header:'大类'
        },
        {dataIndex:'SDTYNO',header:'订单节点'
        	,renderer:function(value){
      			if(value=='10'){
      				return "现场订货";
      			} else if(value=='20'){
      				return "区域平衡";
      			}  else if(value=='30'){
      				return "总公司平衡";
      			}  else if(value=='40'){
      				return "尾箱调整";
      			}    else {
      				return "";
      			}
      		}
        },
        {dataIndex:'ORSTAT',header:'总量状态'
        	,renderer:function(value){
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
        {dataIndex:'SZSTAT',header:'规格状态'
        	,renderer:function(value){
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
        {dataIndex:'MLORNO',header:'审批订单号'
        },
        {dataIndex:'MLORVN',header:'版本号'
        },
        {dataIndex:'ISFECT',header:'有效'
        	,renderer:function(value){
      			if(value=='0'){
      				return "无效";
      			} else if(value=='1'){
      				return "有效";
      			}  else {
      				return "";
      			}
      		}
        },
        {dataIndex:'ORAPDP',header:'审批部门'
        },
        
        {dataIndex:'ORAPDT',header:'处理日期'
        }
      ];
      
     
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			//model: 'y.order.QyVO',
			fields:['ORTYNO','YXGSNM','QYNM','ORDORG','ORDORG_NAME','CHANNO','CHANNO_NAME','MLORNO','MLORVN','ISFECT','SDTYNO','BRADNO','SPCLNO',
				'ORAPDP','ORSTAT','SZSTAT','ORAPDT','BRADNO_NAME','SPCLNO_NAME'],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ord/ordMgr/queryOrdMgr.do',
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
//					if(records && records.length>0){
//						if(!me.createNew_btn){
//							var toolbars=me.getDockedItems('toolbar[dock="top"]');
//							var createNew=toolbars[2].down("#createNew");
//							var updateApprove=toolbars[2].down("#updateApprove");
//							
//							me.createNew_btn=createNew;
//							me.updateApprove_btn=updateApprove;
//						}
//						
//						if(records[0].get("orstat")==0 || records[0].get("orstat")==4 ){
//							me.createNew_btn.enable();
//							me.updateApprove_btn.enable();
//						} else {
//							me.createNew_btn.disable();
//							me.updateApprove_btn.disable();
//						}
//						
//					}
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
		        		ordorg.reload();
		        		//ordorg.getStore().reload();
					}
				}
			},{
		  		fieldLabel: '营销公司',
		  		labelWidth:65,
		  		width:160,
//		  		allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
		  		itemId:'yxgsno',
		  		showBlank:true,
				xtype:'orgcombo',
				listeners:{
					select:function( combo, record, eOpts ) {
						var regioncombo=combo.nextSibling("#qyno");
		        		regioncombo.reload(record.get("orgno"),"QY");
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
				showBlank:true,
				autoLoad:false,
				listeners:{
					select:function( combo, record, eOpts ) {
		        		
						var ordorg=combo.nextSibling("#ordorg");
		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			qyno:record.get("orgno")
		        		});
		        		ordorg.reload();
		        		//ordorg.getStore().reload();
					}
				}
			},{
				fieldLabel: '渠道类型',
				labelWidth:65,
				width:150,
				showBlank: true,
//	            afterLabelTextTpl: Ext.required,
				itemId: 'channo',
				xtype:'channocombo',
				//value:'QY',
				listeners:{
					select:function( combo, record, eOpts ) {
		        		
						var ordorg=combo.nextSibling("#ordorg");
		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			channo:record.get("channo")
		        		});
		        		ordorg.reload();
		        		//ordorg.getStore().reload();
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
			    store: {
				    fields: ['orgno', 'orgnm'],
				    autoLoad:false,
				    proxy: {
				        type: 'ajax',
				        url: Ext.ContextPath+'/ord/queryOrdorg.do'
				    },
				    listeners:{
				    	load:function(myStore){
//				    		if(myStore.getCount( ) >0){
//						 		var r=myStore.getAt(0);
//						 		var ordorg=me.down("#ordorg");
//						 		ordorg.select( r );
//						 		ordorg.fireEvent("select", ordorg, r);
//						 	}
				    	}
				    }
				},
	            hidden:false,
				xtype:'combobox',
				reload_flag:0,
				reload:function(){
					var me=this;
					me.reload_flag++;
					if(me.reload_flag>=3){
						me.getStore().reload();
					}
				}
			 }
			 ,{
				fieldLabel: '代办',
				labelWidth:40,
		        width:125,
				itemId: 'readyHandling',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'id',
			    value:'ready',
			    store: {
				    fields: ['id', 'name'],
				     data:[{id:'all',name:'全部'},{id:'ready',name:'待审'}]
				},
	            hidden:false,
				xtype:'combobox'
			 }
			 ]
	  });
	  
	   me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ortyno',
				xtype:'ordtycombo',
				labelWidth:65,
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
//		        		
//		        		var spseno=combo.nextSibling("#spseno");
//		        		spseno.reload(record.get("itno"));
		        	}	
		        }
		    },{
				fieldLabel: '总量状态',
				labelWidth:65,
		        width:165,
				itemId: 'orstat',
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
				fieldLabel: '规格状态',
				labelWidth:65,
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
				fieldLabel: '有效状态',
				labelWidth:65,
		        width:140,
				itemId: 'isfect',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'value',
			    store: {
				    fields: ['value', 'name'],
				    data:[{value:'',name:'所有'},{value:'0',name:'无效'},{value:'1',name:'有效'}]
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
				},
				iconCls: 'icon-refresh'
			}]
	   });
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
			    text: '审批',
			    hidden:!Permision.canShow('ord_mgr_process2'),
			    handler: function(btn){
			    	Ext.Msg.confirm("消息","确定要'审批'?",function(btn1){
						if(btn1=='yes'){
					    	var grid=btn.up("grid");
					    	var records=grid.getSelectionModel().getSelection();
					    	if(records==null || records.length==0){
					    		Ext.Msg.alert("消息","请先选择行!");
					    		return;
					    	}
					    	//var mlornoes=[];
					    	var params=[];
					    	for(var i=0;i<records.length;i++){
					    		//mlornoes.push(records[i].get("MLORNO"));
					    		params.push({
					    			MLORNO:records[i].get("MLORNO"),
					    			ORSTAT:records[i].get("ORSTAT"),
					    			SZSTAT:records[i].get("SZSTAT")
					    		});
					    	}
					    	Ext.Ajax.request({
					    		url:Ext.ContextPath+'/ord/ordMgr/process2.do',
					    		//params:{mlornoes:mlornoes},
					    		jsonData:params,
					    		method:'POST',
					    		success:function(response){
					    			var obj=Ext.decode(response.responseText);
					    			if(obj.success==false){
					    				Ext.Msg.alert("消息",obj.msg);
					    				return;
					    			}
					    			grid.getStore().reload();
					    		}
					    	});
				    	}
					});
			    },
			    iconCls: 'icon-edit'
			},{
				text: '退回',
				hidden:!Permision.canShow('ord_mgr_back'),
				handler: function(btn){
					Ext.Msg.confirm("消息","确定要'退回'?",function(btn1){
						if(btn1=='yes'){	
						
							var grid=btn.up("grid");
					    	var records=grid.getSelectionModel().getSelection();
					    	if(records==null || records.length==0){
					    		Ext.Msg.alert("消息","请先选择行!");
					    		return;
					    	}
					    	//var mlornoes=[];
					    	var params=[];
					    	for(var i=0;i<records.length;i++){
					    		//mlornoes.push(records[i].get("MLORNO"));
					    		params.push({
					    			MLORNO:records[i].get("MLORNO"),
					    			ORSTAT:records[i].get("ORSTAT"),
					    			SZSTAT:records[i].get("SZSTAT")
					    		});
					    	}
					    	Ext.Ajax.request({
					    		url:Ext.ContextPath+'/ord/ordMgr/back.do',
					    		//params:{mlornoes:mlornoes},
					    		method:'POST',
					    		jsonData:params,
					    		success:function(response){
					    			var obj=Ext.decode(response.responseText);
					    			if(obj.success==false){
					    				Ext.Msg.alert("消息",obj.msg);
					    				return;
					    			}
					    			grid.getStore().reload();
					    		}
					    	});
				    	}
					});
				},
				iconCls: 'icon-reply'
			},{
				text: '作废',
				hidden:!Permision.canShow('ord_mgr_isfect_no'),
				handler: function(btn){
					Ext.Msg.confirm("消息","确定要'作废'?",function(btn1){
						if(btn1=='yes'){
						
						
							var grid=btn.up("grid");
					    	var records=grid.getSelectionModel().getSelection();
					    	if(records==null || records.length==0){
					    		Ext.Msg.alert("消息","请先选择行!");
					    		return;
					    	}
					    	var mlornoes=[];
					    	for(var i=0;i<records.length;i++){
					    		mlornoes.push(records[i].get("MLORNO"));
					    	}
					    	Ext.Ajax.request({
					    		url:Ext.ContextPath+'/ord/ordMgr/isfect_no.do',
					    		params:{mlornoes:mlornoes},
					    		method:'POST',
					    		success:function(response){
					    			var obj=Ext.decode(response.responseText);
					    			if(obj.success==false){
					    				Ext.Msg.alert("消息",obj.msg);
					    				return;
					    			}
					    			grid.getStore().reload();
					    		}
					    	});
					    }
					});
				},
				iconCls: 'icon-trash'
			},{
				text: '新建',
				hidden:true,
				handler: function(btn){
					
				},
				iconCls: 'icon-plus'
			},{
				text: '订单流转',
				hidden:!Permision.canShow('ord_mgr_ordercircle'),
				handler: function(btn){
					//alert("订单的审批版本号，还没有获取，一些规则还没有加进去");
					//return;
					var grid=btn.up("grid");
					var records=grid.getSelectionModel().getLastSelected();
					if(records==null || records.length==0){
					    Ext.Msg.alert("消息","请先选择审批订单号!");
					    return;
					}
					var mlornoes = [];
					for (var i = 0; i < records.length; i++) {
						//总量状态为审批通过，规格状态 为编辑中的订单才允许流转 
						if(records[i].get("ORSTAT")!=3 || records[i].get("ORSTAT")!=0){
							Ext.Msg.alert("消息","总量状态为审批通过，规格状态 为编辑中的订单才允许流转 ,"+records[i].get("MLORNO")+"不符合条件。");
							return;
						}
						mlornoes.push(records[i].get("MLORNO"));
					}
					
					var grid=Ext.create('Ext.grid.Panel', {
					    store: Ext.create('Ext.data.Store', {
						    fields:[ 'sdtyno', 'sdtynm'],
						    data: [
						        { sdtyno: '10', sdtynm: '现场订货' },
						        { sdtyno: '20', sdtynm: '区域平衡' },
						        { sdtyno: '30', sdtynm: '总公司平衡' },
						        { sdtyno: '40', sdtynm: '尾箱调整' }
						    ]
						}),
					    columns: [
					        //{ text: 'Name', dataIndex: 'sdtyno' },
					    	{xtype: 'rownumberer'},
					        { text: '订货节点', dataIndex: 'sdtynm', flex: 1 }
					    ],
					    listeners:{
					    	itemdblclick:function( view , record , item , index , e , eOpts){
					    		Ext.Ajax.request({
						    		url:Ext.ContextPath+'/ord/ordMgr/ordercircle.do',
						    		params:{
						    			mlornoes:mlornoes,
						    			sdtyno:record.get("sdtyno")
						    		},
						    		method:'POST',
						    		success:function(response){
						    			var obj=Ext.decode(response.responseText);
						    			if(obj.success==false){
						    				Ext.Msg.alert("消息",obj.msg);
						    			}
						    			grid.getStore().reload();
						    		}
						    	});
					    	}
					    }
					});
					
					var win=Ext.create("Ext.window.Window",{
						layout:'fit',
						modal:true,
						title:'双击选择新节点',
						items:[grid],
						width:160,
						height:300
					});
					win.show();
					
				},
				iconCls: 'icon-exchange'
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
			"params['readyHandling']":toolbars[0].down("#readyHandling").getValue(),
			
			"params['ortyno']":toolbars[1].down("#ortyno").getValue(),
			"params['bradno']":toolbars[1].down("#bradno").getValue(),
			"params['spclno']":toolbars[1].down("#spclno").getValue(),
			"params['szstat']":toolbars[1].down("#szstat").getValue(),
			"params['orstat']":toolbars[1].down("#orstat").getValue(),
			"params['isfect']":toolbars[1].down("#isfect").getValue()
		};
		return params;
	},
	createNew:function(){
		var me=this;
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var ordorg=toolbars[0].down("#ordorg").getValue();
		if(!ordorg){
			Ext.Msg.alert("消息","请先选择一个订货单位!");
			return;
		}
//		var ortyno=toolbars[0].down("#ortyno").getValue();
//		if(!ortyno){
//			ortyno='DZ';
//		}
		
		
    	var qyVONewForm=Ext.create('y.order.QyVONewForm',{
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
    }
   
    
});
