Ext.define('y.sample.SampleDesignGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'y.sample.SamplePlan'
	],
	columnLines :true,
	stripeRows:true,
	selModel: {
          selType: 'checkboxmodel'
          //,checkOnly:true
    },
     viewConfig:{
    	enableTextSelection:true
    },
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
      	{dataIndex:'photno',header:'图片',width:65,renderer:function(value, metaData, record, rowIndex, colIndex, store, view){
      		if(value>0){
      			return "已上传";
      		} else {
      			return "";
      		}
      	}
        },
		{dataIndex:'plspnm',header:'企划样衣编号'
        },
        {dataIndex:'sampnm1',header:'出样样衣编号'
        },
        {dataIndex:'sampnm',header:'订货样衣编号'
        },
        {dataIndex:'matest',header:'状态',
        	renderer:function(value, metaData, record, rowIndex, colIndex, store, view){
        		var val="";
        		if(record.get("spstat")==1){
        			val+=",设计开发"
        		}
        		if(record.get("spctst")==1){
        			val+=",成衣信息"
        		}
        		if(value==1){
        			val+=",面料信息"
        		}
        		if(val){
        			val=val.substr(1)+"锁定";
        		}
        		
        		return val;
        	}
        },
        {dataIndex:'abstat',header:'必定',width:50,
        	renderer:function(value, metaData, record, rowIndex, colIndex, store, view){
        		if(value==1){
        			return "<span style='color:red;'>必定</span>";
        		}
        		return "";
        	}
        },
		{dataIndex:'bradno_name',header:'品牌'
        },
		{dataIndex:'spyear',header:'年份'
        },
		{dataIndex:'spsean_name',header:'季节'
        },
		{dataIndex:'spbseno_name',header:'大系列'
        },
		{dataIndex:'sprseno_name',header:'品牌系列'
        },
		{dataIndex:'spclno_name',header:'大类'
        },
		{dataIndex:'sptyno_name',header:'小类'
        },
		{dataIndex:'spseno_name',header:'系列'
        },
		{dataIndex:'splcno_name',header:'定位'
        },
		{dataIndex:'spbano_name',header:'上市月份'
        },
        {dataIndex:'plgrno_name',header:'商品等级'
        },
		{dataIndex:'spftpr',header:'出厂价',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'sprtpr',header:'零售价',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'spplrd',header:'企划倍率',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'plctpr',header:'企划成本价',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
//		{dataIndex:'pldate',header:'计划交货期',xtype: 'datecolumn', format:'Y-m-d',width:150
//		},
//		{dataIndex:'mldate',header:'面料交货期',xtype: 'datecolumn', format:'Y-m-d',width:150
//		},
		{dataIndex:'rgsp',header:'创建人'
        },
		{dataIndex:'rgdt',header:'创建日期',xtype: 'datecolumn', format:'Y-m-d H:i:s',width:150
		},
		{dataIndex:'lmsp',header:'修改人'
        },
		{dataIndex:'lmdt',header:'修改日期',xtype: 'datecolumn', format:'Y-m-d H:i:s',width:150
		}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			//model: 'y.sample.SamplePlan',
			fields:[
				{name:'plspnm',type:'string'},
				{name:'bradno',type:'string'},
				{name:'spyear',type:'string'},
				{name:'spsean',type:'string'},
				{name:'spbseno',type:'string'},
				{name:'sprseno',type:'string'},
				{name:'spclno',type:'string'},
				{name:'sptyno',type:'string'},
				{name:'spseno',type:'string'},
				{name:'splcno',type:'string'},
				{name:'spbano',type:'string'},
				{name:'photno',type:'string'},
				{name:'spftpr',type:'float'},
				{name:'sprtpr',type:'float'},
				{name:'spplrd',type:'float'},
				{name:'plctpr',type:'float'},
				{name:'pldate',type:'date', dateFormat: 'Y-m-d H:i:s'},
				{name:'mldate',type:'date', dateFormat: 'Y-m-d H:i:s'},
				{name:'rgsp',type:'string'},
				{name:'rgdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
				{name:'lmsp',type:'string'},
				{name:'lmdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
				{name:'plspno',type:'string'},
				{name:'ormtno',type:'string'},
				{name:'plstat',type:'int'},
				{name:'plspst',type:'int'},
				
				{name:'matest',type:'int'},
				{name:'spctst',type:'int'},
				{name:'spstat',type:'int'},
				
				{name:'spsean_name',type:'string'},
				{name:'spclno_name',type:'string'},
				{name:'bradno_name',type:'string'},
				{name:'sptyno_name',type:'string'},
				{name:'sprseno_name',type:'string'},
				{name:'spbseno_name',type:'string'},
				{name:'spseno_name',type:'string'},
				{name:'splcno_name',type:'string'},
				{name:'spbano_name',type:'string'},
				
				{name:'sampno',type:'string'},
				{name:'sampnm',type:'string'}
			],
			autoLoad:false,
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/sampleDesign/queryPlanDesign.do',
				
				reader:{
					type:'json',
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			},
			listeners:{
				beforeload:function(store){
				//var grid=btn.up("grid");
					var grid=me;//Ext.getCmp("sampleDesignGrid");
					
					//var toolbars=grid.getDockedItems('toolbar[dock="top"]');
		
    				//var ordmtcombo=toolbars[0].down("#ordmtcombo");
    				//grid.getStore().getProxy().extraParams=grid.getParams();
				},
				load:function(store, records, successful){
					var tabpanel=me.nextSibling("tabpanel");
					if(!store.iscreate){
						//tabpanel.mask();
						store.iscreate=false;
					}
					
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
	 me.initReloadSampleDesign_index=1,
	 me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
	  		//enableOverflow:true,
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				listeners:{
					select:function(combo , record , eOpts){
						window.ordmt_record=record;
						me.initReloadSampleDesign_index++;
						me.reload();
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
	            showBlank:false,
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
					select:function(combo , record , eOpts){
						me.initReloadSampleDesign_index++;
						me.reload();
					}
				}
		    },{
		        fieldLabel: '大类',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
		        showBlank:true,
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
		        		
		        		me.initReloadSampleDesign_index++;
						me.reload();
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
	  
	me.dockedItems.push({
	  	xtype: 'toolbar',
	  	dock:'top',
		items:[{
		        //fieldLabel: '大系列',
		        itemId: 'spbseno',
		        labelWidth:50,
		       width:90,
		        emptyText:'大系列',
//	            allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"大系列不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'17'
		    },{
		        //fieldLabel: '工作室系列',
		    	emptyText:'工作室系列',
		        itemId: 'stseno',
	            //allowBlank: false,
	            //afterLabelTextTpl: Ext.required,
	            //blankText:"工作室系列不允许为空",
	            //selectOnFocus:true,
		        queryMode: 'remote',
		        xtype:'pubcodecombo',
		        tyno:'21'
		    },{
		       // fieldLabel: '生产类型',
		        emptyText:'生产类型',
		         width:90,
		        itemId: 'spmtno',
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"生产类型不允许为空",
	            selectOnFocus:true,
		        xtype:'pubcodecombo',
		        tyno:'29'
		    },{
		        //fieldLabel: '成衣供应商',
		    	emptyText:'成衣供应商',
		         labelWidth:65,
		        itemId: 'spsuno',
//	            allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"供应商不允许为空",
	            xtype:'pubsunocombo'
		    },{
		    	emptyText:'请输入订货样衣编号',
		    	itemId: 'sampnm',
		    	width:90,
		    	xtype:'textfield'
		    	
		    },{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){

    				var grid=btn.up("grid");
    				grid.reload();
//    				grid.getStore().getProxy().extraParams=grid.getParams();
//					grid.getStore().reload();
//					
//					
//					var tabpanel=grid.nextSibling("tabpanel");
//					var params=grid.getStore().getProxy().extraParams;
//					window.ormtno=params["params['ormtno']"];
//					var sampleDesignForm=tabpanel.down("form#sampleDesignForm")
//					//sampleDesignForm.reloadPubcode(params["params['bradno']"]);
//					sampleDesignForm.reloadEditor(params["params['bradno']"],params["params['spclno']"]);
					
					
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
				hidden:!Permision.canShow('sample_design_create'),
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			},{
			    text: '复制',
			    itemId:'copy',
			    hidden:!Permision.canShow('sample_design_copy'),
			    handler: function(){
			    	me.onCopy();    
			    },
			    iconCls: 'icon-copy'
			}
//			,{
//			    text: '复制样衣属性',
//			    itemId:'copySampnoInfo',
//			    hidden:!Permision.canShow('sample_design_copySampnoInfo'),
//			    handler: function(){
//			    	me.onCopySampnoInfo();    
//			    },
//			    iconCls: 'icon-copy'
//			}
			,{
			    text: '必定款',
			    iconCls: 'icon-magnet',
			    hidden:!Permision.canShow('sample_design_abstat'),
			    menu:[{
			    	text: '指定',
			    	hidden:!Permision.canShow('sample_design_abstat_confirm'),
			    	handler: function(){
				    	me.onMustOrder(1);    
				    }
			    },{
			    	text: '取消',
			    	hidden:!Permision.canShow('sample_design_abstat_cancel'),
			    	handler: function(){
				    	me.onMustOrder(0);    
				    }
			    }]
			},{
			    text: '删除',
			    itemId:'destroy',
			    hidden:!Permision.canShow('sample_design_destroy'),
			    handler: function(){
			    	me.onDelete();    
			    },
			    iconCls: 'icon-trash'
			},{
			    text: '锁定',
			    //itemId:'destroy',
			    hidden:!Permision.canShow('sample_design_lock'),
			    iconCls: 'icon-lock',
			    menu:[{
			    	text: '设计开发',
			    	 hidden:!Permision.canShow('sample_design_lock_design'),
			    	 handler:function(btn){
			    	 	var grid=btn.up("grid");
						grid.lockOrUblock('sample_design_lock_design',true);

			    	 }
			    },{
			    	text: '面料信息',
			    	 hidden:!Permision.canShow('sample_design_lock_mate'),
			    	 handler:function(btn){
			    	 	var grid=btn.up("grid");
			    	 	grid.lockOrUblock('sample_design_lock_mate',true);
			    	 }
			    },{
			    	text: '成衣信息',
			    	 hidden:!Permision.canShow('sample_design_lock_cloth'),
			    	 handler:function(btn){
			    	 	var grid=btn.up("grid");
			    	 	grid.lockOrUblock('sample_design_lock_cloth',true);
			    	 }
			    }]
			},{
			    text: '解锁',
			    //itemId:'destroy',
			    hidden:!Permision.canShow('sample_design_unlock'),

			    iconCls: 'icon-unlock',
			    menu:[{
			    	text: '设计开发',
			    	 hidden:!Permision.canShow('sample_design_unlock_design'),
			    	 handler:function(btn){
			    	 	var grid=btn.up("grid");
			    	 	grid.lockOrUblock('sample_design_unlock_design',false);
			    	 }
			    },{
			    	text: '面料信息',
			    	 hidden:!Permision.canShow('sample_design_unlock_mate'),
			    	 handler:function(btn){
			    	 	var grid=btn.up("grid");
			    	 	grid.lockOrUblock('sample_design_unlock_mate',false);
			    	 }
			    },{
			    	text: '成衣信息',
			    	 hidden:!Permision.canShow('sample_design_unlock_cloth'),
			    	 handler:function(btn){
			    	 	var grid=btn.up("grid");
			    	 	grid.lockOrUblock('sample_design_unlock_cloth',false);
			    	 }
			    }]
			},{
			    text: '导出',
			   
			    menu:[{
			    	text: '产品资料导出',
			    	handler:function(){
			    		me.onExportSample(); 
			    	}
			    },{
			    	text: '主面料信息',
			    	handler:function(){
			    		me.onExportSampleMate(); 
			    	}
			    },{
			    	text: '其他面料信息',
			    	handler:function(){
			    		me.onExportSampleMate_other(); 
			    	}
			    }],
			    iconCls: ' icon-download-alt'
			}]
		});

       
      me.callParent();
	},
	
	reload:function(){
		//当3个必须条件都初始化结束，就自动进行查询
		//alert(this.initReloadSampleDesign_index);
		var grid=this;
		if(this.initReloadSampleDesign_index>=3){
			grid.getStore().getProxy().extraParams=grid.getParams();
			grid.getStore().reload({params:{start:0,page:1}});
			
					var tabpanel=grid.nextSibling("tabpanel");
					var params=grid.getStore().getProxy().extraParams;
					window.ormtno=params["params['ormtno']"];
					var sampleDesignForm=tabpanel.down("form#sampleDesignForm")
					//sampleDesignForm.reloadPubcode(params["params['bradno']"]);
					//alert(params["params['spclno']"]);
					sampleDesignForm.reloadEditor(params["params['ormtno']"],params["params['bradno']"],params["params['spclno']"]);
					
		}
	},
	lockOrUblock:function(type,lockOrUnlock){
		var url='';
		if(lockOrUnlock){
			if(type=='sample_design_lock_design'){
				url=Ext.ContextPath+'/sampleDesign/lock.do';
			} else if(type=='sample_design_lock_mate'){
				url=Ext.ContextPath+'/sampleMate/lock.do';
			} else if(type=='sample_design_lock_cloth'){
				url=Ext.ContextPath+'/sampleColth/lock.do';
			}
		} else {
			if(type=='sample_design_unlock_design'){
				url=Ext.ContextPath+'/sampleDesign/unlock.do';
			} else if(type=='sample_design_unlock_mate'){
				url=Ext.ContextPath+'/sampleMate/unlock.do';
			} else if(type=='sample_design_unlock_cloth'){
				url=Ext.ContextPath+'/sampleColth/unlock.do';
			}
		}
		
		var grid=this;
		var modles=grid.getSelection( ) ;
		if(modles && modles.length>0){
			Ext.Msg.confirm("消息","是否对选中的记录进行锁定/解锁?‘<span style='color:red;'>全选</span>’只选中当前页所有数据！<br/>如果未选择，将会按<span style='color:red;'>页面查询条件</span>锁定/解锁所有样衣",function(val){
				if(val=='yes'){
					var sampnos=[];
					for(var i=0;i<modles.length;i++){
						sampnos.push(modles[i].get("sampno"));
					}
					Ext.Ajax.request({
						    url:url,
						    params:{
						    	 sampnos:sampnos
						    },
						    method:'POST',
						    success:function(){
						    	grid.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    }
						   });
					} 
				});	
		} else {
			Ext.Msg.confirm("消息","因为没有选择样衣，是否根据<span style='color:red;'>页面查询条件</span>锁定/解锁所有样衣?",function(val){
				if(val=='yes'){
									
					Ext.Ajax.request({
						    url:url,
						    params:grid.getParams(),
						    method:'POST',
						    success:function(){
						    	grid.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    }
						   });
				} 
			});	
		}
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		
		var params={
						    	 			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
					    					"params['bradno']":toolbars[0].down("#bradno").getValue(),
					    					"params['spclno']":toolbars[0].down("#spclno").getValue(),
					    					"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
					    					"params['spseno']":toolbars[0].down("#spseno").getValue(),
					    					"params['spbseno']":toolbars[1].down("#spbseno").getValue(),
					    					"params['stseno']":toolbars[1].down("#stseno").getValue(),
					    					"params['spsuno']":toolbars[1].down("#spsuno").getValue(),
					    					"params['spmtno']":toolbars[1].down("#spmtno").getValue(),
					    					"params['sampnm']":toolbars[1].down("#sampnm").getValue(),
					    					"params['sampno']":null//修复新建后的样衣编号
						    	 		};
		return params;
	},
	onCreate:function(){
    	var me=this;
//    	var toolbars=me.getDockedItems('toolbar[dock="top"]');
//    	var ordmtcombo=toolbars[0].down("#ordmtcombo");
//
//    	//获取订货会编号
//		var child=Ext.create('y.sample.SamplePlan',{
//			ormtno:ordmtcombo.getValue(),
//			bradno:'Y'
//		});
//		var tabpanel=me.nextSibling("tabpanel");
//		tabpanel.setTitle("新增样衣");
//		tabpanel.unmask();
//		var formpanel=tabpanel.child("form#samplePlanForm") ;
//		formpanel.loadRecord(child);
    	var toolbars=this.getDockedItems('toolbar[dock="top"]');
    	
    	window.sampno={};
    	var tabpanel=me.nextSibling("tabpanel");
    	var samplePlanGridQuery=Ext.create('y.sample.SamplePlanGridQuery',{
    		tabpanel:tabpanel,
    		ormtno:toolbars[0].down("#ordmtcombo").getValue(),
    		bradno:toolbars[0].down("#bradno").getValue(),
    		spclno:toolbars[0].down("#spclno").getValue()
    	});
    	samplePlanGridQuery.down("#sptyno").reload(samplePlanGridQuery.spclno);
    	samplePlanGridQuery.down("#spseno").reload(samplePlanGridQuery.spclno);
    	
    	samplePlanGridQuery.on("itemdblclick",function(view, record, item, index, e, eOpts){
    		
			tabpanel.setTitle("新增样衣:"+record.get("plspnm"));
			tabpanel.setActiveTab( 1 );
			tabpanel.unmask();
			var samplePlanFormQuery=tabpanel.child("form#samplePlanFormQuery") ;
			samplePlanFormQuery.loadRecord(record);
			
			//获取当季的属性
			
			window.sampleDesign=record;
			//设计开发form填充
			var sampleDesign=null;
			if(record.get("bradno")=='Y'){
				sampleDesign=Ext.create('y.sample.SampleDesign',{
					sexno:'Z0',
					plspno:record.get("plspno"),
					plspnm:record.get("plspnm"),
					sampnm1:record.get("plspnm"),
					sampst:1,
					abstat:record.get("plgrno")=="S"?1:0,
					versno:"none",
					stseno:"none",
					spmtno:"none",
					colrno:"none",
					pattno:"none",
					stylno:"none",
					sexno:"none",
					slveno:"none"
				});
			} else {
				sampleDesign=Ext.create('y.sample.SampleDesign',{
					sexno:'Z0',
					plspno:record.get("plspno"),
					plspnm:record.get("plspnm"),
					sampnm1:record.get("plspnm"),
					abstat:record.get("plgrno")=="S"?1:0,
					sampst:1,
					//desgno:"",
					buspno:"无",
					stylgp:"无",
					desp:"无"
				});
			}
			
			var sampleDesignForm=tabpanel.child("form#sampleDesignForm") ;
			//获取当季的属性
			sampleDesignForm.reloadStseno(record.get("spclno"));//这个必须放在前面
			sampleDesignForm.reloadPubcode(record.get("bradno"));
			
			
			sampleDesignForm.reset();
			sampleDesignForm.loadRecord(sampleDesign);
			sampleDesignForm.getForm().findField("sampnm").setReadOnly(false);
			//sampleDesignForm.getForm().findField( "plspno").setValue(record.get("plspno"));
			//sampleDesignForm.getForm().findField( "plspnm").setValue(record.get("plspnm"));
			
		
			win.hide();
			
			var sampleDesignSizegpGrid=tabpanel.down("form#sampleDesignForm").down("grid#sampleDesignSizegpGrid");
			sampleDesignSizegpGrid.reloadEditor(record.get("ormtno"),record.get("bradno"),record.get("spclno"));
			if(record.get("sptyno")=='S10'){
				//me.showsampleDesignSizegpGrid_bool=true;
				sampleDesignForm.showsampleDesignSizegpGrid(true);
			} else {
				//me.showsampleDesignSizegpGrid_bool=false;
				sampleDesignForm.showsampleDesignSizegpGrid(false);
			}

			
			//var tabpanel=field.up("tabpanel");
	       	tabpanel.items.getAt(2).disable();
	       	tabpanel.items.getAt(3).disable();
	       	tabpanel.items.getAt(4).disable();
	       	//重置
	       	tabpanel.down("grid#sampleMateGrid").getStore().removeAll();
	       	tabpanel.down("form#sampleMateForm").reset();
	       	tabpanel.down("form#sampleMateForm").mask();

	       	tabpanel.down("form#sampleColthForm").reset();
	       //	tabpanel.down("grid#sampleDesignStprGrid").getStore().removeAll();
	       	tabpanel.down("#samplePhotoShow").getStore().removeAll();
	       	
	       	window.sampleDesign=null;
	       	
	       	tabpanel.expand();
		});
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'双击选择“企划样衣”',
    		modal:true,
//    		width:400,
//    		height:300,
    		maximized:true,
    		closeAction:'hide',
    		items:[samplePlanGridQuery]
    	});
    	win.show();
	     
		
    },
    

    
    onDelete:function(){
    	var me=this;
    	var records=me.getSelectionModel( ).getSelection( );

		if(!records || records.length==0){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}
		var sampnos=[];
		for(var i=0;i<records.length;i++){
			sampnos.push(records[i].get("sampno"));
		}
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
			if (btn == 'yes'){
				Ext.Ajax.request({//
					url:Ext.ContextPath+'/sampleDesign/deleteById.do',
					params:{
						ormtno:me.getStore().getProxy().extraParams["params['ormtno']"],
						sampnos:sampnos
					},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						if(obj.success==false){
							Ext.Msg.alert("消息",obj.msg);
							return;
						}
						me.getStore().reload();
						me.nextSibling("tabpanel").mask();
					}
					
				})
			}
		});
    },
    onCopy:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );
		
		if(!record){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}
//		var rec = record.copy(null);
//		rec.set("plspnm",null);
//		rec.set("plspno",null);
//		var tabpanel=me.nextSibling("tabpanel");
//		tabpanel.setTitle("复制样衣");
//		tabpanel.unmask();
//		var formpanel=tabpanel.child("form#samplePlanForm") ;
//		formpanel.reset();
//		formpanel.loadRecord(rec);
		
		//跳转到设计样衣 把订货样衣编号名称和订货样衣编号设置为null
		//当点保存后，就把所有form中的订货样衣编号改成新的
		var tabpanel=me.nextSibling("tabpanel");
		var sampleDesignForm=tabpanel.down("form#sampleDesignForm") ;
		var new_record=record.copy(null);
		new_record.set("sampnm","");
		//new_record.set("sampnm1","");
		//new_record.set("sampno","");故意保存，用来复制原始的数据
		new_record.set("spstat",0);
		new_record.set("photno",null);
		//sampleDesignForm.showOrHidden_saveButton(0);
		window.sampleDesign=new_record;
		window.sampleDesignForm_url_dfgdfg="/sampleDesign/copy.do";
		sampleDesignForm.loadRecord(new_record);
		sampleDesignForm.getForm().findField("sampnm").setReadOnly(false);
		
		tabpanel.setActiveTab(1);
		tabpanel.items.getAt(2).disable();
	    tabpanel.items.getAt(3).disable();
	    tabpanel.items.getAt(4).disable();
	    

	    
	    sampleDesignForm.on("create",function( record){
//	    	me.getStore().getProxy().extraParams=Ext.apply(me.getStore().getProxy().extraParams,{
//	    		sampno: sampleDesign.get("sampno")
//	    	});
	    	window.sampleDesignForm_url_dfgdfg=null;
	    	
	    	//面料信息,并且默认选中第一行
		    var sampleMateGrid=tabpanel.down("grid#sampleMateGrid") ;
		    var sampleMateForm=tabpanel.down("form#sampleMateForm") ;
			sampleMateGrid.getStore().getProxy().extraParams={
				sampno:record.get("sampno")
			};
			sampleMateGrid.getStore().reload();
			sampleMateForm.reset();
			sampleMateForm.lockOrUnlock(record.get("matest"));
			sampleMateGrid.lockOrUnlock(record.get("matest"));
			
			//成衣信息
			var sampleColthForm=tabpanel.down("form#sampleColthForm") ;
			sampleColthForm.reset();
			y.sample.SampleColth.load(record.get("sampno"), {
			    success: function(sampleColth) {
			    	//console.log(sampleDesign);
			       //sampleDesign.set("plspnm",record.get("plspnm"));
			    	//var suitty_field=sampleDesignForm.getForm().findField("suitty");
			       sampleColthForm.loadRecord(sampleColth);
			    }
			});
		    
		    tabpanel.down("#samplePhotoShow").getStore().removeAll();
	    });
		
		
		
    },
//    onCopySampnoInfo:function(){//复制样衣属性，企划样衣编号要手工输入
//    	//Ext.Msg.confirm("")
//    	var me=this;
//    	var record=me.getSelectionModel( ).getLastSelected( );
//		
//		if(!record){
//		    Ext.Msg.alert("消息","请先选择一行数据");	
//			return;
//		}
//		
//		var form=Ext.create('Ext.form.Panel', {
//		    bodyPadding: 5,
//		    width: 350,
//		    // The form will submit an AJAX request to this URL when submitted
//		    //url: 'save-form.php',
//		
//		    // Fields will be arranged vertically, stretched to full width
//		    layout: 'anchor',
//		    defaults: {
//		        anchor: '100%'
//		    },
//		    // The fields
//		    defaultType: 'textfield',
//		    items: [{
//		        fieldLabel: '企划样衣编号',
//		        name: 'plspnm',
//	            allowBlank: false,
//	            //readOnly:true,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"企划样衣编号不允许为空",
//	            selectOnFocus:true,
//		        xtype:'textfield'
//		    },{
//		        fieldLabel: '出样样衣编号',
//		        name: 'sampnm1',
//		        itemId: 'sampnm1',
//	            allowBlank: false,
//	            //readOnly:sampnm_readOnly,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"出样样衣编号不允许为空",
//	            selectOnFocus:true,
//		        xtype:'textfield'
//		    },{
//		        fieldLabel: '订货样衣编号',
//		        name: 'sampnm',
//	            allowBlank: false,
//	            //readOnly:sampnm_readOnly,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"订货样衣编号不允许为空",
//	            selectOnFocus:true,
//		        xtype:'textfield',
//		        listeners:{
//		        	change:function(field, newValue, oldValue){
//		        		//var sampnm1=field.nextSibling("#sampnm1");
//		        		//sampnm1.setValue(newValue);
//		        	}
//		        }
//		    }],
//		    // Reset and Submit buttons
//		    buttons: [{
//		        text: 'Reset',
//		        handler: function() {
//		            this.up('form').getForm().reset();
//		        }
//		    }, {
//		        text: 'Submit',
//		        formBind: true, //only enabled once the form is valid
//		        disabled: true,
//		        handler: function() {
//		            var form = this.up('form').getForm();
//		            if (form.isValid()) {
//		                form.submit({
//		                    success: function(form, action) {
//		                       Ext.Msg.alert('Success', action.result.msg);
//		                    },
//		                    failure: function(form, action) {
//		                        Ext.Msg.alert('Failed', action.result.msg);
//		                    }
//		                });
//		            }
//		        }
//		    }]
//		});
//		
//		var win=Ext.create('Ext.Window',{
//			layout:'fit',
//			
//			items:[form]
//		});
//		win.show();
//    },
    /**
     * 设置必定款
     */
    onMustOrder:function(abstat){
    	var grid=this;
		var modles=grid.getSelection( ) ;
		if(!modles || modles.length==0){
			Ext.Msg.alert("消息","请选择一行或多行!");
			return;
		}
		Ext.Msg.confirm("消息","将会对选中的<span>具有相同出样样衣编号的</span>样衣指定/取消为必定款?‘<span style='color:red;'>全选</span>’只选中当前页所有数据！",function(val){
				if(val=='yes'){
					var sampnos=[];
					var sampnm1s=[];
					for(var i=0;i<modles.length;i++){
						sampnos.push(modles[i].get("sampno"));
						sampnm1s.push(modles[i].get("sampnm1"));
					}
					Ext.Ajax.request({
						    url:Ext.ContextPath+'/sampleDesign/mustOrder.do',
						    params:{
						    	 sampnos:sampnos,
						    	 sampnm1s:sampnm1s,
						    	 abstat:abstat
						    },
						    method:'POST',
						    success:function(){
						    	grid.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    }
						   });
					} 
				});	
				
    },
    onExportSample:function(){
    	var me=this;
    	var params=me.getParams();
    	var url=Ext.ContextPath+"/sampleDesign/exportSample.do?"+Ext.urlEncode(params);
    	window.open(url);
    },
    onExportSampleMate:function(){
    	var me=this;
    	var params=me.getParams();
    	var url=Ext.ContextPath+"/sampleDesign/exportSampleMate.do?"+Ext.urlEncode(params);
    	window.open(url);
    },
    onExportSampleMate_other:function(){
    	var me=this;
    	var params=me.getParams();
    	var url=Ext.ContextPath+"/sampleDesign/exportSampleMate_other.do?"+Ext.urlEncode(params);
    	window.open(url);
    }
});
