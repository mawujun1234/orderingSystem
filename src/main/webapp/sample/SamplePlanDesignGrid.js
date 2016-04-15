Ext.define('y.sample.SamplePlanDesignGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'y.sample.SamplePlan'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'plspnm',header:'企划样衣编号'
        },
        {dataIndex:'sampnm',header:'设计样衣编号'
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
			autoLoad:true,
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/samplePlan/queryPlanDesign.do',
				reader:{
					type:'json',
					root:'root',
					successProperty:'success',
					totalProperty:'total'		
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
				xtype:'ordmtcombo'
			}]
		});
	  
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
			},{
				text: '刷新',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});

       
      me.callParent();
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
    	var samplePlanGridQuery=Ext.create('y.sample.SamplePlanGridQuery',{
    	
    	});
    	samplePlanGridQuery.on("itemdblclick",function(view, record, item, index, e, eOpts){
    		var tabpanel=me.nextSibling("tabpanel");
			tabpanel.setTitle("新增样衣:"+record.get("plspnm"));
			tabpanel.unmask();
			var samplePlanFormQuery=tabpanel.child("form#samplePlanFormQuery") ;
			samplePlanFormQuery.loadRecord(record);
			
			//设计开发form填充
			var sampleDesign=Ext.create('y.sample.SampleDesign',{
				plspno:record.get("plspno"),
				plspnm:record.get("plspnm")
			});
			var sampleDesignForm=tabpanel.child("form#sampleDesignForm") ;
			sampleDesignForm.loadRecord(sampleDesign);
		
			win.hide();
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
