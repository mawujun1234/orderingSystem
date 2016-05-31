Ext.define('y.order.ZgsVOGrid',{
	extend:'Ext.grid.Panel',
	requires: [],
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
	      {dataIndex:'PALTPY',header:'状态'},
	      {dataIndex:'SDTYNO',header:'订单节点'},
	      {dataIndex:'ORSTAT',header:'订单状态'},
	      {dataIndex:'SPTYNM',header:'小类'},
	      {dataIndex:'SPSENM',header:'系列'},
	      {dataIndex:'SAMPNM',header:'样衣编号'},
	      {
           header: '原始数量',columns:[
		      {dataIndex:'ORMTQS00',header:'标准',width: 80,renderer:function(value){
		      	if(value==0){
		      		return "";
		      	}
		      	return value;
		      }},
		      {dataIndex:'ORMTQS01',header:'上衣',width: 80,renderer:function(value){
		      	if(value==0){
		      		return "";
		      	}
		      	return value;
		      }},
		      {dataIndex:'ORMTQS02',header:'裤子',width: 80,renderer:function(value){
		      	if(value==0){
		      		return "";
		      	}
		      	return value;
		      }},
		      {dataIndex:'ORMTQS04',header:'裙子',width: 80,renderer:function(value){
		      	if(value==0){
		      		return "";
		      	}
		      	return value;
		      }}
	      ]},
	      {header: '确认数量',columns:[
		      {dataIndex:'ORMTQT00',header:'标准',width: 80,renderer:function(value){
		      	if(value==0){
		      		return "";
		      	}
		      	return value;
		      }},
		      {dataIndex:'ORMTQT01',header:'上衣',width: 80,renderer:function(value){
		      	if(value==0){
		      		return "";
		      	}
		      	return value;
		      }},
		      {dataIndex:'ORMTQT02',header:'裤子',width: 80,renderer:function(value){
		      	if(value==0){
		      		return "";
		      	}
		      	return value;
		      }},
		      {dataIndex:'ORMTQT04',header:'裙子',width: 80,renderer:function(value){
		      	if(value==0){
		      		return "";
		      	}
		      	return value;
		      }}
	      ]},
	      {dataIndex:'PLSPNM',header:'目标样衣编号'}
      ];
      var fields=['PALTPY','SDTYNO','ORSTAT','SPTYNM','SPSENM','SAMPNO','SAMPNM','ORMTQS00',
      	'ORMTQS01','ORMTQS02','ORMTQS04','ORMTQT00','ORMTQT01','ORMTQT02','ORMTQT04','PLSPNM']
      
      	
	  
	  
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields:fields,
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ord/zgsVO/queryZgsVO.do',
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
				load:function(store,records){
//					if(records && records.length>0){
//						if(!me.createNew_btn){
//							var toolbars=me.getDockedItems('toolbar[dock="top"]');
//							var createNew=toolbars[2].down("#createNew");
//							var updateApprove=toolbars[2].down("#updateApprove");
//							
//							me.createNew_btn=createNew;
//							me.updateApprove_btn=updateApprove;
//						}
//						
//						if(records[0].get("orstat")==0 || records[0].get("orstat")==4 ){
//							me.createNew_btn.enable();
//							me.updateApprove_btn.enable();
//						} else {
//							me.createNew_btn.disable();
//							me.updateApprove_btn.disable();
//						}
//						
//					}
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
				xtype:'ordmtcombo',
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				listeners:{
					select:function( combo, record, eOpts ) {	
//						
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
		        tyno:'1',
		        listeners:{
		        	select:function( combo, record, eOpts ) {

		        	}	
		        }
		    },{
		        fieldLabel: '大类',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"大类不允许为空",
	             selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'0',
		        listeners:{
		        	select:function( combo, record, eOpts ) {
		        		var sptyno=combo.nextSibling("#sptyno");
		        		sptyno.reload(record.get("itno"));
		        		
		        		var spseno=combo.nextSibling("#spseno");
		        		spseno.reload(record.get("itno"));
		        	}	
		        }
		    },{
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
		    }]
	  });
	   me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		        fieldLabel: '样衣编号',
		        labelWidth:65,
		        itemId: 'sampno',
	            xtype:'textfield'
		    },{
				fieldLabel: '订货状态',
				labelWidth:65,
		        width:165,
				itemId: 'orstat',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'id',
			    store: {
				    fields: ['id', 'name'],
				    data:[{id:'0',name:'编辑中'},{id:'1',name:'审批中'},{id:'2',name:'大区审批通过'},{id:'3',name:'总部审批通过'},{id:'4',name:'退回'}]
				},
	            hidden:false,
				xtype:'combobox'
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
	   
	    me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		text: '取消',
				handler: function(btn){
					me.clearNum();
				},
				iconCls: 'icon-remove'
		  	},{
		  		text: '合并',
				handler: function(btn){
					me.meger_all();
				},
				iconCls: 'icon-download-alt'
		  	},{
		  		text: '拆分',
				handler: function(btn){
					me.meger_comp();
				},
				iconCls: ' icon-signout' 
		  	},{
		  		text: '恢复',
				handler: function(btn){
					me.recover();
				},
				iconCls: 'icon-reply'
		  	},{
		  		text: '导出',
				handler: function(btn){
					me.createNew();
				},
				iconCls: 'icon-save'
		  	}]
	    });
	  
	  me.callParent(); 
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var params={
			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
			"params['bradno']":toolbars[0].down("#bradno").getValue(),
			"params['spclno']":toolbars[0].down("#spclno").getValue(),
			"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
			"params['spseno']":toolbars[0].down("#spseno").getValue(),

			"params['sampno']":toolbars[1].down("#sampno").getValue(),
			"params['orstat']":toolbars[1].down("#orstat").getValue()
		};
		return params;
	},
	clearNum:function(){
		var grid=this;
		var modles=grid.getSelection( ) ;
		if(!modles || modles.length==0){
			Ext.Msg.alert("消息","请选择一行或多行!");
			return;
		}
		Ext.Msg.confirm("消息","是否确定对选中的样衣数据清零?",function(val){
				if(val=='yes'){
					var toolbars=grid.getDockedItems('toolbar[dock="top"]');
					var ormtno=toolbars[0].down("#ordmtcombo").getValue();
					var sampnos=[];
					for(var i=0;i<modles.length;i++){
						sampnos.push(modles[i].get("SAMPNO"));
					}
					Ext.Ajax.request({
						    url:Ext.ContextPath+'/ord/zgsVO/clearNum.do',
						    params:{
						    	 sampnos:sampnos,
						    	 ormtno:ormtno
						    },
						    method:'POST',
						    success:function(){
						    	grid.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    }
						   });
					} 
				});
	},
	meger_all:function(){
		var me=this;
		var modles=me.getSelection( ) ;
		if(!modles || modles.length==0){
			Ext.Msg.alert("消息","请选择一行或多行!");
			return;
		}
		
		var store=Ext.create('Ext.data.Store', {
		    fields:[ 'SAMPNO', 'SAMPNM', 'PSMPNO'],
		    data: [
		    ]
		});
		for(var i=0;i<modles.length;i++){
			store.add({
				SAMPNO:modles[i].get("SAMPNO"),
				SAMPNM:modles[i].get("SAMPNM"),
				PSMPNO:''
			});
		}
		var cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
        }); 
		var grid=Ext.create("Ext.grid.Panel",{
			store:store,
			plugins : [cellEditing],
			columns: [
		        { text: '源样衣编号', dataIndex: 'SAMPNM', flex: 1 },
		        { text: '目标样衣编号', dataIndex: 'PSMPNO', flex: 1 ,renderer:function(value, metaData, record, rowIndex, colIndex, store){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	return value;
	            },editor: {
	                xtype: 'textfield',
	                allowDecimals:false,
	                selectOnFocus:true 
	            }}
		    ]
		});
		var win=Ext.create('Ext.window.Window',{
			layout:'fit',
			title:'合并',
			width:260,
			height:200,
			modal:true,
			items:grid,
			buttons:[{
				text:'确认',
				handler:function(){
					var data=[];
					var aaa=store.getRange();
					for(var i=0;i<aaa.length;i++){
						if(aaa[i].get("PSMPNO")){
							data.push({
								SAMPNO:aaa[i].get("SAMPNO"),
								PSMPNO:aaa[i].get("PSMPNO")
							});
						}
					}
					Ext.Ajax.request({
						url:Ext.ContextPath+'/ord/zgsVO/meger_all.do',
						method:'POST',
//						params:{
//							data:data
//						},
						jsonData:data,
						success:function(response){
						
						}
					});
				}
			},{
				text:'取消',
				handler:function(){
				 win.close();
				}
			}]
		});
		win.show();
		
	},
	meger_comp:function(){
		var me=this;
		var modles=me.getSelection( ) ;
		if(!modles || modles.length!=1){
			Ext.Msg.alert("消息","请选择一个样衣!只能选择一个!");
			return;
		}
		var model=modles[0];
		var store=Ext.create('Ext.data.Store', {
			autoLoad:true,
		    fields:[ 'SAMPNO','PSMPNO','YXGSNO','YXGSNM','ORMTQT'],
		    proxy:{
		    	url:Ext.ContextPath+'/ord/zgsVO/query_meger_comp.do',
		    	type:'ajax',
		    	actionMethods: { read: 'POST' },
				timeout :600000,
				extraParams:{
					SAMPNO:model.get("SAMPNO")
				}
		    	
		    }
		});

		var cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
        }); 
		var grid=Ext.create("Ext.grid.Panel",{
			store:store,
			plugins : [cellEditing],
			columns: [
		        { text: '营销公司', dataIndex: 'YXGSNM', flex: 1 },
		        { text: '数量', dataIndex: 'ORMTQT', flex: 1 },
		        { text: '目标样衣编号', dataIndex: 'PSMPNO', flex: 1 ,renderer:function(value, metaData, record, rowIndex, colIndex, store){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	return value;
	            },editor: {
	                xtype: 'textfield',
	                allowDecimals:false,
	                selectOnFocus:true 
	            }}
		    ]
		});
		var win=Ext.create('Ext.window.Window',{
			layout:'fit',
			title:'拆分('+model.get("SAMPNM")+')',
			width:460,
			height:400,
			modal:true,
			items:grid,
			buttons:[{
				text:'确认',
				handler:function(){
					var data=[];
					var aaa=store.getRange();
					for(var i=0;i<aaa.length;i++){
						if(aaa[i].get("PSMPNO")){
							data.push({
								SAMPNO:aaa[i].get("SAMPNO"),
								PSMPNO:aaa[i].get("PSMPNO"),
								YXGSNO:aaa[i].get("YXGSNO")
							});
						}
					}
					Ext.Ajax.request({
						url:Ext.ContextPath+'/ord/zgsVO/meger_comp.do',
						method:'POST',
//						params:{
//							data:data
//						},
						jsonData:data,
						success:function(response){
						
						}
					});
				}
			},{
				text:'取消',
				handler:function(){
				 win.close();
				}
			}]
		});
		win.show();
	},
	recover:function(){
	
	}
});