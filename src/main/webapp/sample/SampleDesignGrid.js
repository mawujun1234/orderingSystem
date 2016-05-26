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
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'plspnm',header:'企划样衣编号'
        },
        {dataIndex:'sampnm',header:'设计样衣编号'
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
		{dataIndex:'spbano_name',header:'上市批次'
        },
		{dataIndex:'spftpr',header:'出厂价',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'sprtpr',header:'零售价',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'spplrd',header:'企划倍率',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'plctpr',header:'企划成本价',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'pldate',header:'计划交货期',xtype: 'datecolumn', format:'Y-m-d',width:150
		},
		{dataIndex:'mldate',header:'面料交货期',xtype: 'datecolumn', format:'Y-m-d',width:150
		},
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
					if(!store.getProxy().extraParams.sampno){
						tabpanel.mask();
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
	  
	 me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
	  		//enableOverflow:true,
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo'
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
		        tyno:'1'
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
		    }]
		});
	  
	me.dockedItems.push({
	  	xtype: 'toolbar',
	  	dock:'top',
		items:[{
		        fieldLabel: '大系列',
		        itemId: 'spbseno',
		        labelWidth:50,
		        width:150,
//	            allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"大系列不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'17'
		    },{
		        fieldLabel: '成衣供应商',
		        itemId: 'spsuno',
//	            allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"供应商不允许为空",
	            xtype:'pubsunocombo'
		    },{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){

    				var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
					
					
					var tabpanel=grid.nextSibling("tabpanel");
					var params=grid.getStore().getProxy().extraParams;
					tabpanel.down("form#sampleDesignForm").reloadPubcode(params["params['bradno']"],params["params['spclno']"]);
					
					
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
			}]
		});

       
      me.callParent();
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
					    					"params['spsuno']":toolbars[1].down("#spsuno").getValue(),
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
    	window.sampno={};
    	var tabpanel=me.nextSibling("tabpanel");
    	var samplePlanGridQuery=Ext.create('y.sample.SamplePlanGridQuery',{
    		tabpanel:tabpanel
    	});
    	samplePlanGridQuery.on("itemdblclick",function(view, record, item, index, e, eOpts){
    		
			tabpanel.setTitle("新增样衣:"+record.get("plspnm"));
			tabpanel.setActiveTab( 1 );
			tabpanel.unmask();
			var samplePlanFormQuery=tabpanel.child("form#samplePlanFormQuery") ;
			samplePlanFormQuery.loadRecord(record);
			
//			//设计开发form填充
//			var sampleDesign=Ext.create('y.sample.SampleDesign',{
//				plspno:record.get("plspno"),
//				plspnm:record.get("plspnm")
//			});
			
			var sampleDesignForm=tabpanel.child("form#sampleDesignForm") ;
			//sampleDesignForm.loadRecord(sampleDesign);
			sampleDesignForm.reset();
			sampleDesignForm.getForm().findField( "plspno").setValue(record.get("plspno"));
			sampleDesignForm.getForm().findField( "plspnm").setValue(record.get("plspnm"));
		
			win.hide();
			
			var sampleDesignSizegpGrid=tabpanel.down("form#sampleDesignForm").down("grid#sampleDesignSizegpGrid");
			sampleDesignSizegpGrid.reloadEditor(record.get("bradno"),record.get("spclno"));
			if(record.get("sptyno")=='S10'){
				//me.showsampleDesignSizegpGrid_bool=true;
				sampleDesignForm.showsampleDesignSizegpGrid(true);
			} else {
				//me.showsampleDesignSizegpGrid_bool=false;
				sampleDesignForm.showsampleDesignSizegpGrid(false);
			}
//			var sampleDesignSizegpGrid_store=sampleDesignSizegpGrid.getStore();
//			sampleDesignSizegpGrid_store.removeAll();
//			sampleDesignSizegpGrid_store.getProxy().extraParams={
//				suitty:record.get("suitty"),
//				sampno:window.sampno.sampno
//			};
//			sampleDesignSizegpGrid_store.reload();
			
//			var tabpanel=me.nextSibling("tabpanel");
//			newCard.getItemId()
			
			//var tabpanel=field.up("tabpanel");
	       	tabpanel.items.getAt(2).disable();
	       	tabpanel.items.getAt(3).disable();
	       	tabpanel.items.getAt(4).disable();
	       	
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
    	var node=me.getSelectionModel( ).getLastSelected( );

		if(!node){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}
		var parent=node.parentNode;
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
			if (btn == 'yes'){
				Ext.Ajax.request({
					url:Ext.ContextPath+'/sampleDesign/deleteById.do',
					params:{
						sampno:node.get("sampno")
					},
					success:function(){
						me.getStore().reload();
					}
					
				})
			}
		});
    }
});
