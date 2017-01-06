Ext.define('y.pubsize.SizeGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.pubsize.Size'
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
		{dataIndex:'sizeno',header:'规格范围代码',flex:1
        },
		{dataIndex:'sizenm',header:'规格范围名称',flex:1
        }
//		{dataIndex:'ormtno',header:'订货会批号'
//        },
//		{dataIndex:'sizety',header:'规格类型'
//        },
//		{dataIndex:'ysizety',header:'规格系列类型'
//        },
//		{dataIndex:'ysizeno',header:'规格系列代码'
//        },
//		{dataIndex:'szbrad',header:'品牌'
//        },
//		{dataIndex:'szclno',header:'大类'
//        },
//		{dataIndex:'sizeso',header:'排序',xtype: 'numbercolumn', format:'0',align : 'right'
//		},
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.pubsize.Size',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/size/query.do',
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
				listeners:{
					select:function(combo , record , eOpts){
						//me.initReloadSampleDesign_index++;
						//me.reload();
						var grid=combo.up("grid");
						grid.reload();
					}
				}
			},{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.reload();
				},
				iconCls: 'icon-refresh'
			},{
				text: '新增',
				itemId:'create',
				hidden:!Permision.canShow('PRDSZFW_create'),
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			},{
			    text: '更新',
			    itemId:'update',
			    hidden:!Permision.canShow('PRDSZFW_update'),
			    handler: function(){
			    	me.onUpdate();
					
			    },
			    iconCls: 'icon-edit'
			},{
			    text: '删除',
			    itemId:'destroy',
			    hidden:!Permision.canShow('PRDSZFW_delete'),
			    handler: function(){
			    	me.onDelete();    
			    },
			    iconCls: 'icon-trash'
			},{
			    text: '复制到最近订货会',
			    //itemId:'destroy',
			    hidden:!Permision.canShow('PRDSZFW_copy'),
			    handler: function(){
			    	me.onCopy();    
			    },
			    iconCls: 'icon-copy'
			}]
		});

       
      me.callParent();
	},
	reload:function(){
		var grid=this;
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		grid.getStore().getProxy().extraParams=Ext.apply(grid.getStore().getProxy().extraParams,{
			ormtno:toolbars[0].down("#ordmtcombo").getValue()
		});
		grid.getStore().reload();
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('y.pubsize.Size',{
			ormtno:me.getStore().getProxy().extraParams["ormtno"],
			sizety:"PRDSZFW",
			ysizety:me.getStore().getProxy().extraParams["ysizety"],
			ysizeno:me.getStore().getProxy().extraParams["ysizeno"],
			szbrad:window.prdsztyGrid.getStore().getProxy().extraParams["params['szbrad']"],
			szclno:window.prdsztyGrid.getStore().getProxy().extraParams["params['szclno']"]
		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.pubsize.SizeForm',{});
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
    
    onUpdate:function(){
    	var me=this;

    	var node=me.getSelectionModel( ).getLastSelected();
    	if(node==null){
    		Ext.Msg.alert("提醒","请选择一行数据!");
    		return;
    	}

		var formpanel=Ext.create('y.pubsize.SizeForm',{});
		formpanel.loadRecord(node);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'更新',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[formpanel]
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
					node.erase({
					    failure: function(record, operation) {
					    	var obj=Ext.decode(operation.getResponse().responseText);
							Ext.Msg.alert('失败', obj.msg);
			            	me.getStore().reload();
					    },
					    success:function(){
					    	me.getStore().reload();
					    }
				});
			}
		});
    },
    onCopy:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

		if(!record){
		    Ext.Msg.alert("消息","请先选择一行规格范围");	
			return;
		}
		Ext.Msg.confirm("复制",'确定要将当前选中的规格范围复制到最新的订货会吗?', function(btn, text){
				if (btn == 'yes'){
					Ext.Ajax.request({
						url:Ext.ContextPath+"/size/copy.do",
						params:{sizeno_old:record.get("sizeno")},
						success:function(response){
						 var obj=Ext.decode(response.responseText);
						 if(!obj.success){
						 
						 }
						 Ext.Msg.alert("消息","复制成功!");
						}
					});
				}
		});
		
		
//		var win=Ext.create("Ext.Window",{
//			//layout:'fit',
//			title:'复制到指定订货会',
//			modal:true,
//			height:150,
//			width:100,
//			items:[{
//		  		itemId:'ordmtcombo',
//				xtype:'ordmtcombo',
//				listeners:{
//		        	select:function( combo, record, eOpts ) {
//		        		window.ordmt_record=record;
//		        	}	
//		        }
//			}],
//			buttons:[{
//				text:'取消',
//				handler:function(){
//					win.close();
//				}
//			},{
//				text:'确定',
//				handler:function(){
//					var ordmtcombo=win.down("#ordmtcombo");
//					alert(ordmtcombo.get());
//				}
//			}]
//		
//		});
//		win.show();
    }
});
