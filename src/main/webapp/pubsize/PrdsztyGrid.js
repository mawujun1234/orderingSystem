Ext.define('y.pubsize.PrdsztyGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.pubsize.PubSize'
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
//		{dataIndex:'sizety',header:'规格类型'
//        },
      	{dataIndex:'szbrad_name',header:'品牌'
        },
		{dataIndex:'szclno_name',header:'大类'
        },
		{dataIndex:'sizeno',header:'规格代码'
        },
		{dataIndex:'sizenm',header:'规格名称',flex:1
        },

//
//		{dataIndex:'sizest',header:'状态',xtype:'checkcolumn',listeners:{
//				checkchange:function( checkcolumn, rowIndex, checked, eOpts ){
//					var grid=checkcolumn.up("grid");
//					//console.log(grid);
//					var record=grid.getStore().getAt(rowIndex);
//					record.set('sizest',checked?1:0);
//					record.save();
//				}
//			}
//		},
		{dataIndex:'szsast',header:'当季状态',xtype:'checkcolumn',
			processEvent : function(type) {  
            	if (type == 'click' && Permision.canShow('PRDSZTY_szsast_edit'))  
                   return false;  
            },

			listeners:{
				checkchange:function( checkcolumn, rowIndex, checked, eOpts ){//Permision.canShow('sample_design_unlock'),
					var grid=checkcolumn.up("grid");
					//console.log(grid);
					var record=grid.getStore().getAt(rowIndex);
					record.set('szsast',checked?1:0);
					record.save();
				}
			}
		},
		{dataIndex:'sizemk',header:'备注'
        }
//		{dataIndex:'sizeso',header:'排序',xtype: 'numbercolumn', format:'0',align : 'right'
//		}

      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.pubsize.PubSize',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/pubSize/queryPrdszty.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50
			    	//"params['']"
			    },
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			},
			listeners:{
				beforeload:function(store){
					var grid=me;//Ext.getCmp("sampleDesignGrid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
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
		        fieldLabel: '品牌',
		        itemId: 'szbrad',
		        labelWidth:40,
		        width:160,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            //value:'Y',
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
//		        	select:function(combo){
//		        		//store.reload();
//		        	}
		        }
		    },{
		        fieldLabel: '大类',
		        itemId: 'szclno',
		        labelWidth:40,
		        width:120,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"大类不允许为空",
	            selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'0'
		    },{
		  		itemId:'sizeno',
		  		fieldLabel:'代码',
		  		labelWidth:40,
		  		width:140,
				xtype:'textfield'
			},{
		  		itemId:'sizenm',
		  		fieldLabel:'名称',
		  		labelWidth:40,
		  		width:140,
				xtype:'textfield'
			}]
	  });
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[
//		  		{
//				fieldLabel: '状态',
//				labelWidth:40,
//				width:140,
//			    queryMode: 'local',
//			    xtype:'combobox',
//			    itemId:'sizest',
//			    displayField: 'name',
//			    valueField: 'id',
//			    value:'1',
//				store:Ext.create('Ext.data.Store', {
//				    fields: ['id', 'name'],
//				    data : [
//				        {"id":"0", "name":"作废"},
//				        {"id":"1", "name":"有效"}
//				    ]
//				})
//			},
			{
				fieldLabel: '当季',
				labelWidth:40,
				width:140,
			    queryMode: 'local',
			    xtype:'combobox',
			    itemId:'szsast',
			    displayField: 'name',
			    valueField: 'id',
			    value:'1',
				store:Ext.create('Ext.data.Store', {
				    fields: ['id', 'name'],
				    data : [
				        {"id":"0", "name":"非当季"},
				        {"id":"1", "name":"当季"}
				    ]
				})
			},{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
	   });
//	  me.dockedItems.push({
//	  		xtype: 'toolbar',
//	  		dock:'top',
//		  	items:[{
//				text: '新增',
//				itemId:'create',
//				handler: function(btn){
//					me.onCreate();
//				},
//				iconCls: 'icon-plus'
//			},{
//			    text: '更新',
//			    itemId:'update',
//			    handler: function(){
//			    	me.onUpdate();
//					
//			    },
//			    iconCls: 'icon-edit'
//			}]
//		});
		
		
	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	

	  	Ext.Ajax.request({
						url:Ext.ContextPath+'/pubSize/updatePrdpStdszSizeqt.do',
						params:{
							fszno:me.getStore().getProxy().extraParams.fszno,
							sizety:record.get("sizety"),
							sizeno:record.get("sizeno"),
							sizeqt:value
						},
						success:function(){
							me.getStore().reload();
						}
						
					});
	  	
	  });

       
      me.callParent();
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var params={
			"params['szbrad']":toolbars[0].down("#szbrad").getValue(),
    		"params['szclno']":toolbars[0].down("#szclno").getValue(),
			"params['sizeno']":toolbars[0].down("#sizeno").getValue(),
			"params['sizenm']":toolbars[0].down("#sizenm").getValue(),
			//"params['sizest']":toolbars[1].down("#sizest").getValue(),
			"params['szsast']":toolbars[1].down("#szsast").getValue()
		};
		return params;
	},
	onCreate:function(){
    	var me=this;
    	var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var child=Ext.create('y.pubsize.PubSize',{
			sizety:'PRDSZTY',
			szsast:1,
			sizest:1,
			szbrad:toolbars[0].down("#szbrad").getValue(),
			szclno:toolbars[0].down("#szclno").getValue()
		});
		child.set("id",null);
		//alert(1);
		
		var formpanel=Ext.create('y.pubsize.PrdsztyForm',{
			
		});
		formpanel.loadRecord(child);
		formpanel.down("#sizeno").setReadOnly(false);
		
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

		var formpanel=Ext.create('y.pubsize.PrdsztyForm',{
			
		});
		formpanel.loadRecord(node);
		formpanel.down("#sizeno").setReadOnly(true);
		
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
