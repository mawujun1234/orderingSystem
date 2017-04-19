Ext.define('y.sample.SampleClhdGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.sample.SampleClhd'
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
		{dataIndex:'clppnm',header:'搭配编号'
        },
//		{dataIndex:'bradno',header:'品牌'
//        },
		{dataIndex:'spbano_name',header:'上市月份'
        },
        {dataIndex:'clppst',header:'搭配状态',xtype: 'numbercolumn', format:'0',align : 'right',
        	renderer:function(value){
        		if(value=='1'){
        			return "有效";
        		}
        		return "<span style='color:red;'>无效</span>";
        	}
		},
		{dataIndex:'clppmk',header:'搭配说明',flex:1
        }
		
//		{dataIndex:'print',header:'吊牌打印标志',xtype: 'numbercolumn', format:'0',align : 'right'
//		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.sample.SampleClhd',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/sampleClhd/queryPager.do',
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
				xtype:'ordmtcombo',
				listeners:{
		        	select:function( combo, record, eOpts ) {
		        		window.ordmt_record=record;
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
		        tyno:'1'
		    }]
		});
	 me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
	  		//enableOverflow:true,
		  	items:[{
		        fieldLabel: '上市月份',
		        labelWidth:60,
		        width:160,
		        itemId: 'spbano',
		        xtype:'pubcodecombo',
		        tyno:'23'
		    },{
		    	fieldLabel: '搭配编号',
		        labelWidth:60,
		        width:160,
		        itemId: 'clppnm',
		        xtype:'textfield'
		        
		    }]
		});
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
	  		//enableOverflow:true,
		  	items:[{
		    	fieldLabel: '搭配说明',
		        labelWidth:60,
		        width:160,
		        itemId: 'print',
		        xtype:'textfield'
		        
		    },{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					//var grid=btn.up("grid");
					//grid.getStore().reload();
					var grid=btn.up("grid");
					
					var params=grid.getParams();
					grid.getStore().getProxy().extraParams=params;
					grid.getStore().reload();
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
				handler: function(btn){
					me.onCreate();
				},
				iconCls: 'icon-plus'
			},{
			    text: '更新',
			    itemId:'update',
			    handler: function(){
			    	me.onUpdate();
					
			    },
			    iconCls: 'icon-edit'
			},{
			    text: '有效/无效',
			    itemId:'destroy',
			    handler: function(){
			    	me.onEnable_disable();    
			    },
			    iconCls: 'icon-retweet'
			}]
		});

       
      me.callParent();
	},
	
	getParams:function(){
		var grid=this;//Ext.getCmp("sampleDesignGrid");
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');

    	var params={
    		"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
    		"params['bradno']":toolbars[0].down("#bradno").getValue(),
    		"params['spbano']":toolbars[1].down("#spbano").getValue(),
    		"params['clppnm']":toolbars[1].down("#clppnm").getValue(),
    		"params['print']":toolbars[2].down("#print").getValue()
    					
    	};
    	return params;
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('y.sample.SampleClhd',{
			clppst:1
		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.sample.SampleClhdForm',{});
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

		var formpanel=Ext.create('y.sample.SampleClhdForm',{});
		formpanel.loadRecord(node);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'更新',
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
    
    onEnable_disable:function(){
    	var me=this;
    	var record=me.getSelectionModel( ).getLastSelected( );

		if(!record){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}
		Ext.Ajax.request({
			url:Ext.ContextPath+"/sampleClhd/enable_disable.do",
			params:{
				clppno:record.get("clppno")
			},
			success:function(){
				me.getStore().reload();
			}
		});
    }
});
