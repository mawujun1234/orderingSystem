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
    viewConfig:{
    	enableTextSelection:true
    },
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'ormtno',header:'订货会编号'
        },
        {dataIndex:'yxgsnm',header:'营销公司'
        },
		{dataIndex:'orgnm',header:'订货单位'
        },
		{dataIndex:'channm',header:'订货单位类型'
        },
        {dataIndex:'loginname',header:'账号'
        },
        {dataIndex:'pwd',header:'密码'
        },
		{dataIndex:'sztype',header:'上报方式',align : 'right',width:160,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				if(!me.ormtst){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
				}
				
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
      

	  var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
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
	  me.store=store;

	  me.dockedItems=[];
      me.dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: store,  
	        dock: 'bottom',
	        displayInfo: true
	  });

	  me.ormtst=false;
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				listeners:{
					select:function( combo, record, eOpts ) {
						me.ormtst=record.get("ormtst");//订货会状态
						var grid=combo.up("grid");
						grid.onReload();			
					}
				}
			},{
		  		fieldLabel: '营销公司',
		  		labelWidth:75,
		  		width:175,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'yxgscombo',
				xtype:'orgcombo',
				showBlank:false,
				listeners:{
					select:function( combo, record, eOpts ) {
						//var regioncombo=combo.nextSibling("#regioncombo");
		        		//regioncombo.reload(record.get("orgno"),"QY");
					}
				}
			},{
				fieldLabel: '渠道类型',
				labelWidth:65,
				width:165,
//				allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
				itemId: 'channo',
				xtype:'channocombo',
				selFirst:false,
				showBlank:true,
				//value:'QY',
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
					grid.onReload();
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
			},{
			    text: '打印企业号',
			    itemId:'printQyhQrcode',
			    handler: function(){
			    	me.onPrintQyhQrcode();
					
			    },
			    iconCls: 'icon-edit'
			}]
		});
		me.dockedItems.push({
		 	xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
			    text: '复制到当前订货会',
			    
			    handler: function(){
			    	me.onCopy();
					
			    },
			    iconCls: 'icon-copy'
			}]
		});

      this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  this.cellEditing.on("beforeedit",function(editor, context){
	   return !me.ormtst;
	  })
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
	onReload:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]'); 
		var channo_combo=toolbars[0].down("#channo");
		var ordmtcombo=toolbars[0].down("#ordmtcombo");
		var yxgscombo=toolbars[0].down("#yxgscombo");
		this.getStore().getProxy().extraParams=Ext.apply(this.getStore().getProxy().extraParams,{
			"params['ormtno']":ordmtcombo.getValue(),
			"params['yxgsno']":yxgscombo.getValue(),
		    "params['channo']":channo_combo.getValue()
	    });
	        		
		this.getStore().reload();
		
	},
	onCreate:function(){
    	var me=this;
    	if(me.ormtst){
    		Ext.Msg.alert("消息","订货会已经结束，不能新增");	
			return;
    	}
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

    	var records=me.getSelectionModel( ).getSelection();
    	if(records==null || records.length==0){
    		Ext.Msg.alert("提醒","请选择要打印的订货单位!");
    		return;
    	}
    	//var data=[];
    	var params="ormtno="+me.getStore().getProxy().extraParams["params['ormtno']"];
    	for(var i=0;i<records.length;i++){
//    		data.push({
//    			ordorg:records[i].get("ordorg"),
//    			ormtno:records[i].get("ormtno")
//    		});
    		//data.push({ordorg:records[i].get("ordorg")});
    		params+="&ordorgs="+records[i].get("ordorg");
    	}
    	
    	var url=Ext.ContextPath+"/ordOrg/print.do?"+params;
    	//alert(url);return;
    	window.open(url);

//    	Ext.Ajax.request({
//    		url:Ext.ContextPath+'/ordOrg/print.do',
//    		jsonData:data,
//    		success:function(){
//    		
//    		}
//    	});
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
    onPrintQyhQrcode:function(){
    	Ext.Msg.prompt("消息","输入数量:",function(buttonId,value){
    		//alert(buttonId);
    		if(buttonId=='ok'){
    			var url=Ext.ContextPath+"/ordOrg/print_qyh_qrcode.do?num="+value;
	    		//alert(url);return;
	    		window.open(url);
    		}
    	
    	},this,false,50);
    },
    
    onDelete:function(){
    	var me=this;
    	if(me.ormtst){
    		Ext.Msg.alert("消息","订货会已经结束，不能删除");	
			return;
    	}
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
					    success:function(response){
					    	var obj=Ext.decode(response.responseText);
					    	if(obj.success==false){
								Ext.Msg.alert("消息",obj.msg);
								return;
							}
					    	me.getStore().reload();
					    }
				});
			}
		});
    },
    onCopy:function(){
    	var me=this;
    	var records=me.getSelectionModel( ).getSelection();
    	if(records==null || records.length==0){
    		Ext.Msg.alert("提醒","请选择要打印的订货单位!");
    		return;
    	}
    	Ext.Msg.confirm("删除",'确定要把选中的订货单位复制到最新的订货会吗?', function(btn, text){
			if (btn == 'yes'){
				var datas=[];
				for(var i=0;i<records.length;i++){
					datas.push(records[i].getData());
				}
				Ext.Ajax.request({
					url:Ext.ContextPath+'/ordOrg/copy.do',
					jsonData:datas,
					success:function(response){
						var obj=Ext.decode(response.responseText);
					    if(obj.success==false){
								Ext.Msg.alert("消息",obj.msg);
								return;
						}
						Ext.Msg.alert("消息","复制成功!");
					}
				});
			}
		});
    }
});
