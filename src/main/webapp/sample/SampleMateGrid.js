Ext.define('y.sample.SampleMateGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.sample.SampleMate'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	//{xtype: 'rownumberer'},

		{dataIndex:'mateso',header:'面料编号',xtype: 'numbercolumn', format:'0',align : 'right'
		},
		
		{dataIndex:'mateno',header:'面料货号'
        },
        {dataIndex:'mtsuno_name',header:'供应商'
        }
//		{dataIndex:'mtbrad',header:'面料品牌'
//        },
//		{dataIndex:'mttype',header:'进口/国产'
//        },
//		{dataIndex:'mtcomp',header:'面料成分'
//        },
//		{dataIndex:'yarmct',header:'纱支规格'
//        },
//		{dataIndex:'gramwt',header:'克重/密度'
//        },
//		{dataIndex:'aftrmt',header:'后整理'
//        },
//		{dataIndex:'width',header:'门幅'
//        },
//		{dataIndex:'mtpupr',header:'面料单价'
//        },
//		{dataIndex:'mtcnqt',header:'单件用料'
//        },
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'y.sample.SampleMate',
			autoLoad:false,
			listeners:{
				load:function(store){
					if(!store.sampleMateForm_saved_record){
						return;
					}
					var record=store.sampleMateForm_saved_record;
					store.sampleMateForm_saved_record=false;
					//alert(1);
					//如果衣服是外购的话 预计成本价，就取输入的，所以就不需要重新计算了
					if(window.sampleColthForm.spctpr_sum){
						var spctpr=window.sampleColthForm.sumSpctpr();
						    	Ext.Ajax.request({
						    		url:Ext.ContextPath+"/sampleColth/updateSpctpr.do",
						    		params:{
						    			sampno:record.get("sampno"),
						    			spctpr:spctpr
						    		},
						    		success:function(response){
						    			var obj=Ext.decode(response.responseText);
						    			if(obj.success==false){
						    				Ext.Msg.alert("消息","更新成衣信息中的预计成本价失败!请去手工保存成衣信息!");
						    				return;
						    			}
						    		}
						    	});
					}
					
				}
			}
	  });
	  me.dockedItems=[];

	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '新增',
				itemId:'create',
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			},{
			    text: '删除',
			    itemId:'destroy',
			    handler: function(){
			    	me.onDelete();    
			    },
			    iconCls: 'icon-trash'
			}]
		});

       
      me.callParent();
	},
	onCreate:function(){
    	var me=this;
    	if(!window.sampno){
    		Ext.Msg.alert("消息","请先建立‘设计开发’中的信息");
    		return;
    	}
		var child=Ext.create('y.sample.SampleMate',{
			sampno:window.sampno.sampno,
			sampnm:window.sampno.sampnm
		});
		
		me.getStore().getProxy().extraParams={
			sampno:window.sampno.sampno
		}

		
		var formpanel=me.nextSibling("form#sampleMateForm");
		formpanel.loadRecord(child);
		formpanel.unmask();
		
  
    },
    
//     onUpdate:function(){
//    	var me=this;
//
//    	var node=me.getSelectionModel( ).getLastSelected();
//    	if(node==null){
//    		Ext.Msg.alert("提醒","请选择一行数据!");
//    		return;
//    	}
//
//		var formpanel=Ext.create('y.sample.SampleMateForm',{});
//		formpanel.loadRecord(node);
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
			            	me.getStore().reload();
					    },
					    success:function(){
					    	var formpanel=me.nextSibling("form#sampleMateForm");
					    	formpanel.reset();
					    	me.getStore().reload();
					    }
				});
			}
		});
    },
    lockOrUnlock:function(matest){
		var me=this;
		if(matest==1){
			me.down("toolbar").hide();
		} else if(Permision.canShow('sample_design_designsave')){
			me.down("toolbar").show();
		}
	},
	//获取面料的价格
    sumMtpupr:function(){
    	var me=this;
    	var store=me.getStore();
    	var records=store.getRange();
    	var val=0;
    	if(!records || records.length==0){
    		return val;
    	}
    	
    	for(var i=0;i<records.length;i++){
    		val+=records[i].get("mtpupr")*records[i].get("mtcnqt");
    	}
    	return val;
    	
    }
});
