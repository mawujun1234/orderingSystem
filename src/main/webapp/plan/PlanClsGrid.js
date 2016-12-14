Ext.define('y.plan.PlanClsGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.plan.PlanCls'
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
//		{dataIndex:'ormtno',header:'订货批号'
//        },
//		{dataIndex:'bradno',header:'品牌'
//        },
		{dataIndex:'spclno_name',header:'大类'
        },
		{dataIndex:'spseno_name',header:'系列'
        },
		{dataIndex:'ordqty',header:'起订量',xtype: 'numbercolumn', format:'0',align : 'right'
			,editor: {
	                xtype: 'numberfield',
	                allowDecimals:false,
	                allowNegative : false, 
	                selectOnFocus:true 
	            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	//console.log(window.szsta);
	            	//if(window.szstat==0 && record.get("ORSZST")!=1){
	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	//} else {
	            		//metaData.tdStyle = 'background-color:#98FB98;' ;
	            	//}
	            	 return value;
            	}
		}
		
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.plan.PlanCls',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/planCls/queryAll.do',
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
	  	var field =context.field ;
	  	
	  });
	   
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	var originalValue =context.originalValue 
	  	
	  	if(value<0){
	  	 record.set(field,Math.abs(value));
	  	 value=Math.abs(value);
	  	}
	  	Ext.Ajax.request({
	  		url:Ext.ContextPath+'/planCls/createOrupdate.do',
	  		method:'POST',
	  		//header:{ 'Accept':'application/json;'},
	  		params:{
	  			ordqty:value,
	  			ormtno:grid.getStore().getProxy().extraParams.ormtno,
	  			bradno:grid.getStore().getProxy().extraParams.bradno,
	  			spclno:grid.getStore().getProxy().extraParams.spclno,
	  			spseno:record.get("spseno")
	  		},
	  		success:function(response){
	  			var obj=Ext.decode(response.responseText);
	  			if(obj.success){
	  				record.commit();
	  			}
	  			
	  		}
	  	});
	  	
	  });


      me.initTbar();
      me.callParent();
	},
	initTbar:function(){
		var me=this;
		me.dockedItems=[];
		me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				listeners:{
					select:function( combo, record, eOpts ) {	

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
	            showBlank:false,
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
		        	select:function( combo, record, eOpts ) {
//		        		var toolbar=combo.up("toolbar");
//		        		var suitno=toolbar.down("#suitno");
//		        		suitno.changeBradno(record.get("itno"));
//		        		suitno.getStore().reload();
		        	}	
		        }
		    },{
		        fieldLabel: '大类',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
//	            allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"大类不允许为空",
	            selFirst:true,
	            showBlank:false,
	            afterLabelTextTpl: Ext.required,
		        xtype:'pubcodecombo',
		        tyno:'0',
		        listeners:{
		        	select:function( combo, record, eOpts ) {		
//		        		var spseno=combo.nextSibling("#spseno");
//		        		spseno.reload(record.get("itno"));
		        	}	
		        }
		    },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var params={
			"ormtno":toolbars[0].down("#ordmtcombo").getValue(),
			"bradno":toolbars[0].down("#bradno").getValue(),
			"spclno":toolbars[0].down("#spclno").getValue()
		};
		return params;
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('y.plan.PlanCls',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.plan.PlanClsForm',{});
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

		var formpanel=Ext.create('y.plan.PlanClsForm',{});
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
