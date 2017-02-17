Ext.define('y.order.WxtzGrid',{
	extend:'Ext.grid.Panel',
	requires: [
		//'y.order.ZgsOrderState'
	],
	columnLines :true,
	stripeRows:true,
//	selModel: {
//          selType: 'checkboxmodel'
//          //,checkOnly:true
//    },
    viewConfig:{
    	enableTextSelection:true
    },
	initComponent: function () {
      var me = this;
      me.columns=[
	      {xtype: 'rownumberer'},
	      {dataIndex:'SPCLNM',header:'大类'},
	      {dataIndex:'SPTYNM',header:'小类'},
	      {dataIndex:'SPSENM',header:'系列'},
	      {dataIndex:'SAMPNM',header:'订货样衣编号'},
	      {dataIndex:'PACKQT',header:'包装要求'},
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
	      ]}
      ];
      var fields=['SDTYNO','ORSTAT','SPTYNM','SPSENM','SAMPNO','SAMPNM','PACKQT','ORMTQS00',
      	'ORMTQS01','ORMTQS02','ORMTQS04','ORMTQT00','ORMTQT01','ORMTQT02','ORMTQT04','PLSPNM']
      
      	
	  
	  
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields:fields,
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ord/wxtz/queryWx.do',
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
		        fieldLabel: '订货样衣编号',
		        labelWidth:85,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    },{
				fieldLabel: '订单状态',
				labelWidth:65,
		        width:165,
				itemId: 'zgs_orstat',
				fieldStyle:'background-color:#CDC9C9;',
				readOnly:true,
				xtype:'textfield'

			 },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload({params:{start:0,page:1}});
					
					me.check_stat();
				},
				iconCls: 'icon-refresh'
			},{
		  		text: '导出',
				handler: function(btn){
					me.onExport();
				},
				iconCls: ' icon-download-alt'
		  	}]
	   });
	   
	    me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
	  		disabled:true,
		  	items:[{
		  		text: '尾箱调整',
				handler: function(btn){
					me.comp_wx();
				},
				iconCls: 'icon-download-alt'
		  	},{
		  		text: '尾箱调整-去尾',
				handler: function(btn){
					me.comp_wx_qw();
				},
				iconCls: 'icon-download-alt'
		  	},{
		  		text: '调整完成',
				handler: function(btn){
					me.comp_wxps();
				},
				iconCls: ' icon-legal'
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

			"params['sampnm']":toolbars[1].down("#sampnm").getValue()
			//"params['orstat']":toolbars[1].down("#orstat").getValue()
		};
		return params;
	},
	check_stat:function(){
		var me=this;
		//var params=me.getStore().getProxy().extraParams;
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		Ext.Ajax.request({
			url:Ext.ContextPath+'/ord/wxtz/check_stat.do',
			params:{
				ormtno:toolbars[0].down("#ordmtcombo").getValue(),
				bradno:toolbars[0].down("#bradno").getValue(),
				spclno:toolbars[0].down("#spclno").getValue()
			},
			method:'POST',
			success:function(response){
				var obj=Ext.decode(response.responseText);
				if(obj.success==false){
					Ext.Msg.alert("消息",obj.msg);
					return;
				}
				if(obj.stat=="不可操作" || obj.stat=="审批通过"){
					toolbars[2].disable();
					//toolbars[1].down("#zgs_orstat").setValue("可操作");
				} else {
					toolbars[2].enable();
					//toolbars[1].down("#zgs_orstat").setValue("不可操作");
				}
				toolbars[1].down("#zgs_orstat").setValue(obj.stat);
				
			}
		});
		
	},
	
	/**
	 * 尾箱调整
	 * 
	 * @type
	 */
	comp_wx:function(){
		var me=this;
		var extraParams=this.getStore().getProxy().extraParams;
		Ext.Msg.confirm("消息","确定进行尾箱调整?",function(btn){
			if(btn=='yes'){
				Ext.getBody().mask("正在处理....");
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/ord/wxtz/comp_wx.do',
						    params:{
								ormtno:extraParams["params['ormtno']"],
								bradno:extraParams["params['bradno']"],
								spclno:extraParams["params['spclno']"]
							},
						    //jsonData:data,
						    method:'POST',
						    timeout: 100000000,
						    success:function(response){
						    	var obj=Ext.decode(response.responseText);
								if(obj.success==false){
									Ext.Msg.alert("消息",obj.msg);
									return;
								}
						    	me.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    	Ext.getBody().unmask();
						    },
						    failure:function(){
						    	Ext.getBody().unmask();
						    }
						   });
			}
		});
			
	},
	comp_wx_qw:function(){
		var me=this;
		var extraParams=this.getStore().getProxy().extraParams;
		Ext.Msg.confirm("消息","确定进行尾箱调整去尾?",function(btn){
			if(btn=='yes'){
				Ext.getBody().mask("正在处理....");
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/ord/wxtz/comp_wx_qw.do',
						    params:{
								ormtno:extraParams["params['ormtno']"],
								bradno:extraParams["params['bradno']"],
								spclno:extraParams["params['spclno']"]
							},
						    //jsonData:data,
						    method:'POST',
						    timeout: 100000000,
						    success:function(response){
						    	var obj=Ext.decode(response.responseText);
								if(obj.success==false){
									Ext.Msg.alert("消息",obj.msg);
									return;
								}
						    	me.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    	Ext.getBody().unmask();
						    },
						    failure:function(){
						    	Ext.getBody().unmask();
						    }
						   });
			}
		});
			
	},
	/**
	 * 尾箱调整完成
	 * 
	 * @type
	 */
	comp_wxps:function(){
		var me=this;
		var extraParams=this.getStore().getProxy().extraParams;
		Ext.Msg.confirm("消息","确定尾箱调整完成了?",function(btn){
			if(btn=='yes'){
				Ext.Ajax.request({
						    url:Ext.ContextPath+'/ord/wxtz/comp_wxps.do',
						    params:{
								ormtno:extraParams["params['ormtno']"],
								bradno:extraParams["params['bradno']"],
								spclno:extraParams["params['spclno']"]
							},
						   // jsonData:data,
						    method:'POST',
						    success:function(response){
						    	var obj=Ext.decode(response.responseText);
								if(obj.success==false){
									Ext.Msg.alert("消息",obj.msg);
									return;
								}
						    	me.getStore().reload();
						    	Ext.Msg.alert("消息","成功");
						    }
						   });
			}
		});
			
	},
	onExport:function(){
    	var params=this.getParams();
    	var url=Ext.ContextPath+"/ord/wxtz/export.do?"+Ext.urlEncode(params);
    	window.open(url);
    }
});