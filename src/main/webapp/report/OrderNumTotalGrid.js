Ext.define('y.report.OrderNumTotalGrid',{
	extend:'Ext.grid.Panel',
	requires: [
//	     'y.order.QyVO',
//	     'y.order.QyVONewForm'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
        {dataIndex:'yxgsnm',header:'营销公司'
        },
		{dataIndex:'qynm',header:'区域'
        },
		{dataIndex:'orgnm',header:'订货单位'
        },
        {dataIndex:'bradno_name',header:'品牌'
        },
        {dataIndex:'spclno_name',header:'大类'
        },
        {dataIndex:'sptyno_name',header:'小类'
        },
		{dataIndex:'spseno_name',header:'系列'
        },
        {dataIndex:'colrno_name',header:'颜色'
        },
        {dataIndex:'spsean_name',header:'季节'
        },
        {dataIndex:'spbano_name',header:'上市批次'
        },
        {dataIndex:'versno_name',header:'版型'
        },
        {dataIndex:'prodnm',header:'产品货号'
        },
		{dataIndex:'sampnm',header:'订货样衣编号'
        },
        {dataIndex:'suitno_name',header:'套件'
        },
        {dataIndex:'ormtqt',header:'数量'
        },
        {dataIndex:'spftpr',header:'出厂价'
        },
        {dataIndex:'spftpr_jine',header:'出厂金额'
        },
        {dataIndex:'sprtpr',header:'零售价'
        },
        {dataIndex:'sprtpr_jine_wan',header:'零售金额(万元)'
        }
      ];
	 
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			//model: 'y.order.QyVO',
			fields:['yxgsnm','qynm','orgnm','bradno_name','spclno_name','sptyno_name','spseno_name','colrno_name','spsean_name','spbano_name'
				,'versno_name','prodnm','sampnm','suitno_name','ormtqt','spftpr','spftpr_jine','sprtpr','sprtpr_jine'],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ordernumtotal/query.do',
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
		        		
		        		var channo=combo.nextSibling("#channo");
		        		channo.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			ormtno:record.get("ormtno")
		        		});
		        		channo.getStore().reload();
					}
				}
			},{
		  		fieldLabel: '营销公司',
		  		labelWidth:75,
		  		width:175,
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
		        		ordorg.reload();
		        		//ordorg.getStore().reload();
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
				xtype:'combobox',
				reload_flag:0,
				reload:function(){
					var me=this;
					me.reload_flag++;
					if(me.reload_flag>=3){
						me.getStore().reload();
					}
				}
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
		        showBlank:false,
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
		        //showBlank:false,
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
		        fieldLabel: '生产类型',
		        itemId: 'spmtno',
	            //allowBlank: false,
	            //afterLabelTextTpl: Ext.required,
	           // blankText:"生产类型不允许为空",
		        labelWidth:75,
		        width:160,
	            selectOnFocus:true,
		        xtype:'pubcodecombo',
		        tyno:'29',
		        listeners:{
		       		change:function(field, newValue, oldValue){

		       		}
		        
		        }
		    },{
		        fieldLabel: '订货样衣编号',
		        labelWidth:85,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    }
//		    ,{
//				fieldLabel: '订单状态',
//				labelWidth:65,
//		        width:165,
//				itemId: 'orstat',
//				queryMode: 'local',
//				editable:false,
//				forceSelection:true,
//			    displayField: 'name',
//			    valueField: 'id',
//			    store: {
//				    fields: ['id', 'name'],
//				    data:[{id:'0',name:'编辑中'},{id:'1',name:'大区审批中'},{id:'2',name:'总部审批中'},{id:'3',name:'审批通过'},{id:'4',name:'退回'}]
//				},
//	            hidden:false,
//				xtype:'combobox'
//			 }
			 ,{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
//					//重新统计汇总数据
//					grid.reloadTotal(grid.getStore().getProxy().extraParams);
				},
				iconCls: 'icon-refresh'
			},{
		  		text: '打印特许',
				handler: function(btn){
					var grid=btn.up("grid");
					var toolbars=grid.getDockedItems('toolbar[dock="top"]');
			    	if(!toolbars[0].down("#ordorg").getValue()){
			    		Ext.Msg.alert("消息","打印特许的时候，只能一家一家的打印，请先选择‘订货单位’!");
			    		return;
			    	}
			    	
			    	var params=grid.getParams();
			    	params["params['yxgsnm']"]=toolbars[0].down("#yxgsno").getRawValue();
			    	params["params['qynm']"]=toolbars[0].down("#qyno").getRawValue();
			    	params["params['orgnm']"]=toolbars[0].down("#ordorg").getRawValue();
			    	
			    	var url=Ext.ContextPath+"/ordernumtotal/export_print.do?"+Ext.urlEncode(params);
			    	window.open(url);
				},
				iconCls: 'icon-download-alt'
		  	},{
		  		text: '导出',
				handler: function(btn){
					var grid=btn.up("grid");
					
					
			    	var params=grid.getParams();
			    	var url=Ext.ContextPath+"/ordernumtotal/export.do?"+Ext.urlEncode(params);
			    	window.open(url);
				},
				iconCls: 'icon-download-alt'
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
			
			"params['spmtno']":toolbars[2].down("#spmtno").getValue(),
			"params['sampnm']":toolbars[2].down("#sampnm").getValue()
			//"params['orstat']":toolbars[2].down("#orstat").getValue()
		};
		return params;
	}
//	createNew:function(){
//		var me=this;
//		var toolbars=this.getDockedItems('toolbar[dock="top"]');
//		var ordorg=toolbars[0].down("#ordorg").getValue();
////		if(!ordorg){
////			Ext.Msg.alert("消息","请先选择一个订货单位!");
////			return;
////		}
//
//		
//		
//    	var qyVONewForm=Ext.create('y.order.QyVONewForm',{
//    		//ordorg_store:ordorg.getStore(),
//    		params:{
//    			ordorg:ordorg,
//	    		ortyno:'DZ',
//	    		channo:toolbars[0].down("#channo").getValue(),
//	    		ormtno:toolbars[0].down("#ordmtcombo").getValue()
//    		}
//    	});
//		
//    	var win=Ext.create('Ext.window.Window',{
//    		layout:'fit',
//    		title:'新增',
//    		modal:true,
//    		width:400,
//    		height:300,
//    		closeAction:'hide',
//    		items:[qyVONewForm],
//    		listeners:{
//    			close:function(){
//    				me.getStore().reload();
//    			}
//    		}
//    	});
//    	win.show();
//    },
//    updateApprove:function(){
//    	var me=this;
//    	var toolbars=this.getDockedItems('toolbar[dock="top"]');
//    	var params={
//    		ormtno:toolbars[0].down("#ordmtcombo").getValue(),
//    		qyno:toolbars[0].down("#qyno").getValue(),
//			channo:toolbars[0].down("#channo").getValue(),
//    		ordorg:toolbars[0].down("#ordorg").getValue(),
//    		bradno:toolbars[1].down("#bradno").getValue(),
//    		spclno:toolbars[1].down("#spclno").getValue()
//    		
//    	};
//    	Ext.Ajax.request({
//			url:Ext.ContextPath+'/ord/qyVO/updateApprove.do',
//			params:params,
//			method:'POST',
//			success:function(response){
//				
//				var obj=Ext.decode(response.responseText);
//				if(obj.success==false){
//					Ext.Msg.alert("消息",obj.msg);
//					return;
//				}
//				Ext.Msg.alert("消息","提交成功!");
//				//button.up('window').close();
//				me.getStore().reload();
//			}
//		});
//    },
//    reloadTotal:function(params){
//    	var me=this;
//    	Ext.Ajax.request({
//			url:Ext.ContextPath+'/ord/qyVO/reloadTotal.do',
//			params:params,
//			method:'POST',
//			success:function(response){
//				var obj=Ext.decode(response.responseText);
//				if(obj.success==false){
//					Ext.Msg.alert("消息",obj.msg);
//					return;
//				}
//				me.total_panel.down("#ormtqs").setValue(obj.ormtqs);
//				me.total_panel.down("#ormtqs_zhes").setValue(obj.ormtqs_zhes);
//				me.total_panel.down("#ormtqt").setValue(obj.ormtqt);
//				me.total_panel.down("#ormtqt_zhes").setValue(obj.ormtqt_zhes);
//				me.total_panel.down("#spftpr_jine").setValue(obj.spftpr_jine);
//				me.total_panel.down("#sprtpr_jine").setValue(obj.sprtpr_jine);
//				
//			}
//		});
//    }
   
    
});
