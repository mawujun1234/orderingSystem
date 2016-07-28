Ext.require('y.order.BwQyGrid');
Ext.onReady(function(){
	var panel=Ext.create('Ext.panel.Panel',{
		region:'north',
		height:120
	});
	panel.addDocked({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				listeners:{
					select:function( combo, record, eOpts ) {	
						window.query_stat_ready++;
						panel.query_stat();
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
		        showBlank:false,
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
	             showBlank:false,
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
	panel.addDocked({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		fieldLabel: '营销公司',
		  		labelWidth:60,
		  		width:160,
		  		itemId:'compcombo',
				xtype:'orgcombo',
				showBlank:false,
				listeners:{
					select:function( combo, record, eOpts ) {
						//var regioncombo=combo.nextSibling("#regioncombo");
		        		//regioncombo.reload(record.get("orgno"));
						queryColumns(record.get("orgno"));
						
						window.query_stat_ready++;
						panel.query_stat();
					}
				}
			},{
		        fieldLabel: '订货样衣编号',
		        labelWidth:85,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    },{
				fieldLabel: '订单状态',
				labelWidth:65,
		        width:165,
				itemId: 'orstat',
				fieldStyle:'background-color:#CDC9C9;',
				readOnly:true,
				xtype:'textfield'

			 },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					//var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=panel.getParams();
					grid.getStore().reload();
					panel.query_stat();

				},
				iconCls: 'icon-refresh'
			}]
	   });
	   
	panel.addDocked({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		text: '提交审批',
		  		itemId:'approve_btn',
		  		hidden:!Permision.canShow('order_bw_approve'),
		  		disabled:true,
				handler: function(btn){
					panel.onApprove();
				},
				iconCls: 'icon-download-alt'
		  	},{
		  		text: '审批通过',
		  		itemId:'over_btn',
		  		hidden:!Permision.canShow('order_bw_over'),
		  		disabled:true,
				handler: function(btn){
					panel.over();
				},
				iconCls: ' icon-coffee'
		  	}]
	});
	   
	
	panel.getParams=function(){
		var toolbars=panel.getDockedItems('toolbar[dock="top"]');
		var params={
			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
			"params['bradno']":toolbars[0].down("#bradno").getValue(),
			"params['spclno']":toolbars[0].down("#spclno").getValue(),
			"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
			"params['spseno']":toolbars[0].down("#spseno").getValue(),
			"params['yxgsno']":toolbars[1].down("#compcombo").getValue(),
			"params['sampnm']":toolbars[1].down("#sampnm").getValue()
			//"params['orstat']":toolbars[1].down("#orstat").getValue()
		};
		return params;
	}
	   
	  //获取营销公司的组成column
	 function queryColumns(yxgsno){
		Ext.Ajax.request({
			url:Ext.ContextPath+"/bw/queryQyColumns.do",
			params:{
				yxgsno:yxgsno
			},
			success:function(response){
			 	//console.log(response.responseText);
				var obj=Ext.decode(response.responseText);
				//panel.removeAll(true);
				viewPort.remove(grid,true);
				grid=createTpQyGrid(obj);
				viewPort.add(grid);
			}
		});
	 }
	 
	 //当查询条件准备好后，才能进行查询
	 window.query_stat_ready=0;
	 panel.query_stat=function(){
	 	
	 	if(window.query_stat_ready<2){
	 		return;
	 	}
	 	var toolbars=panel.getDockedItems('toolbar[dock="top"]');
	 	Ext.Ajax.request({
							url:Ext.ContextPath+'/bw/qy_getStat.do',
							params:{
								yxgsno:toolbars[1].down("#compcombo").getValue(),
								ormtno:toolbars[0].down("#ordmtcombo").getValue(),
								spclno:toolbars[0].down("#spclno").getValue()
							},
							success:function(response){
								var obj=Ext.decode(response.responseText);
								window.stat=obj.stat;
								var toolbars=panel.getDockedItems('toolbar[dock="top"]');
								if(obj.stat==0){
									var over_btn=toolbars[2].down("#approve_btn");
									over_btn.enable();
									var orstat=toolbars[1].down("#orstat");
									orstat.setValue("编辑中");
								} else if(obj.stat==2){
									var approve_btn=toolbars[2].down("#approve_btn");
									approve_btn.disable();
									var over_btn=toolbars[2].down("#over_btn");
									over_btn.enable();
									var orstat=toolbars[1].down("#orstat");
									orstat.setValue("总部审批中");
								}else {
									var approve_btn=toolbars[2].down("#approve_btn");
									approve_btn.disable();
									var over_btn=toolbars[2].down("#over_btn");
									over_btn.disable();
									var orstat=toolbars[1].down("#orstat");
									orstat.setValue("审批通过");
								}
								//grid.getStore().reload();
							}
						});
	 	
	 }
	 panel.onApprove=function(){
	 	//Ext.Msg.confirm("确定要提交吗?","将按照‘大类+品牌’的方式进行提交审批!",function(btn){
	 	Ext.Msg.confirm("确定要提交吗?","将会把该营销公司下所有品牌+大类的订单都进行提交审批!",function(btn){
	 		if(btn=='yes'){
				var toolbars=panel.getDockedItems('toolbar[dock="top"]');
				Ext.Ajax.request({
					url:Ext.ContextPath+'/bw/qy_approve.do',
					params:{
						yxgsno:toolbars[1].down("#compcombo").getValue(),
						ormtno:toolbars[0].down("#ordmtcombo").getValue(),
						bradno:toolbars[0].down("#bradno").getValue(),
						spclno:toolbars[0].down("#spclno").getValue()
					},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						if(obj.success==false){
							Ext.Msg.alert("消息",obj.msg);
							return;
						}
//						var approve_btn=toolbars[2].down("#approve_btn");
//						approve_btn.disable();
//						window.stat=2;
						panel.query_stat();
						grid.getStore().reload();
					}
				});
	 		}
		});
	 	
	 }
	 panel.over=function(){
	 	//Ext.Msg.confirm("确定通过吗","将按照‘大类+品牌’的方式审批通过!",function(btn){
	 	Ext.Msg.confirm("确定通过吗","将会把该营销公司内所有品牌+大类的订单都审批通过!",function(btn){
	 		if(btn=='yes'){
				var toolbars=panel.getDockedItems('toolbar[dock="top"]');
				Ext.Ajax.request({
					url:Ext.ContextPath+'/bw/qy_over.do',
					params:{
						yxgsno:toolbars[1].down("#compcombo").getValue(),
						ormtno:toolbars[0].down("#ordmtcombo").getValue(),
						bradno:toolbars[0].down("#bradno").getValue(),
						spclno:toolbars[0].down("#spclno").getValue()
					},
					success:function(response){
						var obj=Ext.decode(response.responseText);
						if(obj.success==false){
							Ext.Msg.alert("消息",obj.msg);
							return;
						}
//						var over_btn=toolbars[2].down("#over_btn");
//						over_btn.disable();
//						window.stat=3;
						panel.query_stat();
						grid.getStore().reload();
					}
				});
	 		}
		});
	}
	
//	panel.onExport=function(){
//    	var params=panel.getParams();
//    	var url=Ext.ContextPath+"/tp/tpQy_export.do?"+Ext.urlEncode(params);
//    	window.open(url);
//    }
    
	 function createTpQyGrid(initColumns){
	 	var params=panel.getParams();
		var　grid=Ext.create('y.order.BwQyGrid',{
			region:'center',
			//params:params,
			initColumns:initColumns
		});
		return grid;
	 }
	 
	 
	var grid=Ext.create('Ext.grid.Panel',{
		region:'center',
		html:''
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[panel]
	});



});