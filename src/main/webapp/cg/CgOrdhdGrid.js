Ext.define('y.cg.CgOrdhdGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.cg.CgOrdhd'
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
      	{dataIndex:'cgorno',header:'采购子批次订单号',align : 'right',width:155
		},
		{dataIndex:'bradno_name',header:'品牌',align : 'right'
		},
		{dataIndex:'spclno_name',header:'大类',align : 'right'
		},
		{dataIndex:'orstat_name',header:'订单状态',align : 'right'
		}
//		{dataIndex:'isfect_name',header:'有效状态',align : 'right'
//		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.cg.CgOrdhd',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/cgOrdhd/queryAll.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });

	  me.dockedItems=[];
//      me.dockedItems.push({
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  });
	  
	  

      me.initToolbar(); 
      me.callParent();
	},
	getParams:function(){
		var grid=this;//Ext.getCmp("sampleDesignGrid");
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');

    	var params={
    		"params['ormtno']":toolbars[0].down("#ormtno").getValue(),
    		"params['orcgno']":toolbars[0].down("#orcgno").getValue(),
    		"params['bradno']":toolbars[1].down("#bradno").getValue(),
    		"params['spclno']":toolbars[1].down("#spclno").getValue()
    					
    	};
    	return params;
	},
	initToolbar:function(){
		var me=this;
		var orcgno=Ext.create('Ext.form.field.ComboBox',{
		    fieldLabel: '子批次编号',
		    labelWidth:80,
		    xtype:'combobox',
		    name:'orcgno',
		    itemId:'orcgno',
		    allowBlank: false,
	        afterLabelTextTpl: Ext.required,
		    queryMode: 'local',
		    displayField: 'orcgnm',
		    valueField: 'orcgno',
		    store: Ext.create('Ext.data.Store',{
		    	autoLoad:false,
		    	proxy:{
					type: 'ajax',
				    url : Ext.ContextPath+'/cgOrdmt/queryAll.do',
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
					load:function(myStore){
						var r=myStore.getAt(0);
						orcgno.select( r );
				 		orcgno.fireEvent("select", orcgno, r);
					}
				
				}
		    })
		    
		});
		me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[ {
		  		itemId:'ormtno',
				xtype:'ordmtcombo',
				name:'ormtno',
				//fieldLabel: '订货会',
				//allowBlank: false,
            	//afterLabelTextTpl: Ext.required,
            	blankText:"订货会采购子批次号不允许为空",
				listeners:{
					select:function(combo , record , eOpts){
						//window.ordmt_record=record;
						//me.initReloadSampleDesign_index++;
						//me.reload();
						orcgno.getStore().getProxy().extraParams=Ext.apply(orcgno.getStore().getProxy().extraParams,{
							ormtno:record.get("ormtno")
						});
						orcgno.getStore().reload();
					}
				}
			},orcgno]
		});
		
		me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		        fieldLabel: '品牌',
		        itemId: 'bradno',
		        name:'bradno',
		         labelWidth:40,
		        width:160,
	            //allowBlank: false,
	            //afterLabelTextTpl: Ext.required,
	            showBlank:false,
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
					select:function(combo , record , eOpts){
						//me.initReloadSampleDesign_index++;
						//me.reload();
					}
				}
		    },{
		        fieldLabel: '大类',
		        name:'spclno',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
		        showBlank:true,
	            //allowBlank: false,
	            //afterLabelTextTpl: Ext.required,
	            blankText:"大类不允许为空",
	             selFirst:false,
		        xtype:'pubcodecombo',
		        tyno:'0',
		        listeners:{
		        	select:function( combo, record, eOpts ) {

		        	}	
		        }
		    },{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().getProxy().extraParams=me.getParams();
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});
	
		
		me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '新增采购批次',
				itemId:'createCg',
				handler: function(btn){
					me.onCreateCg();
				},
				iconCls: 'icon-plus'
			},{
				text: '新增',
				itemId:'create',
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			}
//			,{
//			    text: '更新',
//			    itemId:'update',
//			    handler: function(){
//			    	me.onUpdate();
//					
//			    },
//			    iconCls: 'icon-edit'
//			}
			,{
			    text: '完成',
			    itemId:'confirm',
			    handler: function(){
			    	me.onConfirm();
					
			    },
			    iconCls: 'icon-edit'
			}
			,{
			    text: '删除',
			    itemId:'destroy',
			    handler: function(){
			    	me.onDelete();    
			    },
			    iconCls: 'icon-trash'
			}]
		});
	},
	onCreateCg:function(){
		var child=Ext.create('y.cg.CgOrdmt',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.cg.CgOrdmtForm',{});
		formpanel.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增子订单批次',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
		
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('y.cg.CgOrdhd',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.cg.CgOrdhdForm',{});
		formpanel.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    },
    
//     onUpdate:function(){
//    	var me=this;
//
//    	var record=me.getSelectionModel( ).getLastSelected();
//    	if(record==null){
//    		Ext.Msg.alert("提醒","请选择一行数据!");
//    		return;
//    	}
//
//		var formpanel=Ext.create('y.cg.CgOrdhdForm',{});
//		formpanel.loadRecord(record);
//		
//    	var win=Ext.create('Ext.window.Window',{
//    		layout:'fit',
//    		title:'更新',
//    		modal:true,
//    		width:400,
//    		height:300,
//    		closeAction:'hide',
//    		items:[formpanel]
//    	});
//    	win.show();
//    },
    onConfirm:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

		if(!record){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}
		if(record.get("orstat")==1){
			Ext.Msg.alert("消息","订单已确认，不能再确认!");	
			return;
		}
    	Ext.Msg.confirm("删除",'确定这个子批次订单输入完成了吗?', function(btn, text){
			if (btn == 'yes'){
				Ext.Ajax.request({
					url:Ext.ContextPath+'/cgOrdhd/confirm.do',
					params:{
						orcgno:record.get("orcgno"),
						cgorno:record.get("cgorno")
					},
					success:function(response){
						Ext.Msg.alert("消息","完成");
						me.getStore().reload();
					}
				});		
			}
		});
    },
    
    onDelete:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

		if(!record){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}
		if(record.get("orstat")==1){
			Ext.Msg.alert("消息","订单已确认，不能删除!");	
			return;
		}
		var parent=record.parentNode;
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					record.erase({
					    failure: function(record, operation) {
			            	me.getStore().reload();
					    },
					    success:function(){
					    	me.getStore().reload();
					    }
				});
			}
		});
    }
});
