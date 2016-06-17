Ext.define('y.ordmt.OrdOrgGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.ordmt.OrdOrg'
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
		{dataIndex:'ormtno',header:'订货会编号'
        },
		{dataIndex:'orgnm',header:'订货单位'
        },
		{dataIndex:'channm',header:'订货单位类型'
        },
		{dataIndex:'sztype',header:'上报方式',align : 'right',width:160,
			renderer:function(value){
				if(value=="0"){
					return "单规+整箱上报"
				} else if(value=="1"){
					return "单规上报";
				} else if(value=="2"){
					return "整箱上报";
				}
				return "";
			},
			editor:Ext.create('Ext.form.ComboBox', {
			    store: Ext.create('Ext.data.Store', {
				    fields: ['abbr', 'name'],
				    data : [
				        {"id":"0", "name":"单规+整箱上报"},
				        {"id":"1", "name":"单规上报"},
				        {"id":"2", "name":"整箱上报"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'id'
			})
		},
		{dataIndex:'print',header:'打印状态', align : 'right',
			renderer:function(value){
				if(value==0){
					return "未打印"
				} else if(value==1){
					return "<span style='color:green;'>已打印</span>";
				} 
				return "";
			}
		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: 'y.ordmt.OrdOrg',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ordOrg/queryPager.do',
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
		  	items:[]
	  });
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo'
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
		        		
//						var ordorg=combo.nextSibling("#ordorg");
//		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
//		        			channo:record.get("channo")
//		        		});
//		        		ordorg.getStore().reload();
						
					}
				}
			 },{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					var channo_combo=btn.previousSibling("#channo");
					var ordmtcombo=btn.previousSibling("#ordmtcombo");
					grid.getStore().getProxy().extraParams=Ext.apply(grid.getStore().getProxy().extraParams,{
							"params['ormtno']":ordmtcombo.getValue(),
		        			"params['channo']":channo_combo.getValue()
	        		});
	        		
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			},{
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
			    text: '打印登录',
			    itemId:'printQrcode',
			    handler: function(){
			    	me.onPrintQrcode();
					
			    },
			    iconCls: 'icon-edit'
			}]
		});

      this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
//	  	var grid=context.grid;
//	  	var field =context.field ;
//	  	var value=context.value;
	  	
	  	Ext.Ajax.request({
						url:Ext.ContextPath+'/ordOrg/update.do',
						jsonData:record.getData(),
						success:function(response){
							var obj=Ext.decode(response.responseText);
							if(obj.success==false){
								Ext.Msg.alert("消息",obj.msg);
								return;
							}
							record.commit();
							//me.getStore().reload();
						}
						
					});
	  	
	  });
      me.callParent();
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('y.ordmt.OrdOrg',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.ordmt.OrdOrgForm',{});
		formpanel.loadRecord(child);
		formpanel.grid=me;
		
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
    
    onPrintQrcode:function(){
    	var me=this;

    	var node=me.getSelectionModel( ).getLastSelected();
    	if(node==null){
    		Ext.Msg.alert("提醒","请选择一行数据!");
    		return;
    	}

//		var formpanel=Ext.create('y.ordmt.OrdOrgForm',{});
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
