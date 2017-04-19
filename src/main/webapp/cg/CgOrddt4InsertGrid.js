Ext.define('y.cg.CgOrddt4InsertGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.cg.CgOrddtl'
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
      	{dataIndex:'sampnm',header:'订货样衣编号',align : 'left'
		},
		{dataIndex:'suitno_name',header:'套件',align : 'left'
		},
		{dataIndex:'orszqt_now',header:'本次数量',xtype: 'numbercolumn', format:'0',align : 'right'
			,renderer:function(value, metaData, record, rowIndex, colIndex, store){
				if(record.get("orstat")==3){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
				}

            	 return value;
            },editor: {
                xtype: 'numberfield',
                allowDecimals:false,
                selectOnFocus:true 
            }
		},
		{dataIndex:'orszqt_residue',header:'剩余订货量',align : 'right'
		},
		{dataIndex:'orszqt_already',header:'已确认量',align : 'right'
		},
		{dataIndex:'orszqt_zhanb',header:'已发占比',align : 'right'
		},
		{dataIndex:'ormtqt',header:'订货量',align : 'right'
		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.cg.CgOrddtl',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/cgOrddtl/queryPage4Insert.do',
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
	  
	  
	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  
	  this.cellEditing.on("beforeedit",function(editor, context){
	   	var record=context.record;
	   	//console.log(record.get("orstat"));
	   	if(record.get("orstat")!=3){//alert(1);
			return false;
		}
	  });
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	
	  	//alert(record.get("orszqt_residue"));
		if(value>parseInt(record.get("orszqt_residue"))){
			//Ext.Msg.alert("消息","输入数量不能大于剩余订货量!");
			//return;
			value=record.get("orszqt_residue");
		}

	  	Ext.Ajax.request({
			url : Ext.ContextPath + '/cgOrddtl/create.do',
			params : {
				orcgno : grid.getStore().getProxy().extraParams["params['orcgno']"],
				cgorno : grid.getStore().getProxy().extraParams["params['cgorno']"],
				sampno : record.get("sampno"),
				suitno : record.get("suitno"),
				orszqt : value
			},
			success : function() {
				record.set("orszqt_residue",record.get("ormtqt")-value-record.get("orszqt_already"));
				record.commit();							
			}

		});
	  	
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
		    },{
	        fieldLabel: '上市月份',
	        itemId: 'spbano',
            //allowBlank: false,
            //afterLabelTextTpl: Ext.required,
            //blankText:"上市批次不允许为空",
	        xtype:'pubcodecombo',
	        labelWidth:60,
	        width:150,
	        tyno:'23'
	    },{
	        fieldLabel: '生产类型',
	        itemId: 'spmtno',
            //allowBlank: false,
            //afterLabelTextTpl: Ext.required,
           // blankText:"生产类型不允许为空",
            selectOnFocus:true,
	        xtype:'pubcodecombo',
	        labelWidth:60,
	        width:150,
	        tyno:'29'
	    },{
		  		fieldLabel:"设计样衣编号",
		  		itemId:'sampnm',
		  		xtype:'textfield',
		  		labelWidth:90,
		  		width:180
		  	},{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().getProxy().extraParams=Ext.apply(grid.getStore().getProxy().extraParams,grid.getParams());
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});

        me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '初始化"本次数量"',
				//itemId:'reload',
				//disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.initOrszqt();
				},
				iconCls: 'icon-refresh'
			},{
				text: '关闭本窗口',
				//itemId:'reload',
				//disabled:me.disabledAction,
				handler: function(btn){
					me.win.close();
				},
				iconCls: 'icon-remove'
			}]
        });
      me.callParent();
	},
	getParams:function(){
		var grid=this;//Ext.getCmp("sampleDesignGrid");
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');

    	var params={
    		"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
    		"params['spseno']":toolbars[0].down("#spseno").getValue(),
    		"params['sampnm']":toolbars[0].down("#sampnm").getValue(),
    		"params['spbano']":toolbars[0].down("#spbano").getValue(),
    		"params['spmtno']":toolbars[0].down("#spmtno").getValue()
			
    	};
    	return params;
	},
	initOrszqt:function(){
		var me=this;
		Ext.Msg.confirm("删除",'确定要初始化为剩余订货量吗?', function(btn, text){
			if (btn == 'yes'){
				Ext.getBody().mask("正在执行....");
				Ext.Ajax.request({
					url:Ext.ContextPath+'/cgOrddtl/initOrszqt.do',
					params:me.getStore().getProxy().extraParams,
					method:'POST',
					success:function(response){
						Ext.Msg.alert("消息","成功");
						me.getStore().reload();
						Ext.getBody().unmask();
					},
					failure:function(){
						Ext.getBody().unmask();
					}
				});
			}			
		});
	}
//	onCreate:function(){
//    	var me=this;
//		var child=Ext.create('y.cg.CgOrddtl',{
//
//		});
//		child.set("id",null);
//		
//		var formpanel=Ext.create('y.cg.CgOrddtlForm',{});
//		formpanel.loadRecord(child);
//		
//    	var win=Ext.create('Ext.window.Window',{
//    		layout:'fit',
//    		title:'新增',
//    		modal:true,
//    		width:400,
//    		height:300,
//    		closeAction:'hide',
//    		items:[formpanel],
//    		listeners:{
//    			close:function(){
//    				me.getStore().reload();
//    			}
//    		}
//    	});
//    	win.show();
//    },
//    
//     onUpdate:function(){
//    	var me=this;
//
//    	var node=me.getSelectionModel( ).getLastSelected();
//    	if(node==null){
//    		Ext.Msg.alert("提醒","请选择一行数据!");
//    		return;
//    	}
//
//		var formpanel=Ext.create('y.cg.CgOrddtlForm',{});
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
//    
//    onDelete:function(){
//    	var me=this;
//    	var node=me.getSelectionModel( ).getLastSelected( );
//
//		if(!node){
//		    Ext.Msg.alert("消息","请先选择一行数据");	
//			return;
//		}
//		var parent=node.parentNode;
//		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
//				if (btn == 'yes'){
//					node.erase({
//					    failure: function(record, operation) {
//			            	me.getStore().reload();
//					    },
//					    success:function(){
//					    	me.getStore().reload();
//					    }
//				});
//			}
//		});
//    }
});
