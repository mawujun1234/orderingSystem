Ext.require('y.order.TpYxgsGrid');
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
	panel.addDocked({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		fieldLabel: '营销公司',
		  		labelWidth:60,
		  		width:160,
		  		itemId:'compcombo',
				xtype:'orgcombo',
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
		        fieldLabel: '设计样衣编号',
		        labelWidth:85,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					//var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=panel.getParams();
					grid.getStore().reload();

				},
				iconCls: 'icon-refresh'
			}]
	   });
	   
	panel.addDocked({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		text: '订单完成',
		  		itemId:'over_btn',
		  		disabled:true,
				handler: function(btn){
					panel.over();
				},
				iconCls: ' icon-coffee'
		  	},{
		  		text: '导出',
				handler: function(btn){
					me.createNew();
				},
				iconCls: 'icon-download-alt'
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
			url:Ext.ContextPath+"/tp/queryTpQyColumns.do",
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
	 
	 window.query_stat_ready=0;
	 panel.query_stat=function(){
	 	
	 	if(window.query_stat_ready<2){
	 		return;
	 	}
	 	var toolbars=panel.getDockedItems('toolbar[dock="top"]');
	 	Ext.Ajax.request({
							url:Ext.ContextPath+'/tp/tpQy_getStat.do',
							params:{
								yxgsno:toolbars[1].down("#compcombo").getValue(),
								ormtno:toolbars[0].down("#ordmtcombo").getValue()
							},
							success:function(response){
								var obj=Ext.decode(response.responseText);
								window.stat=obj.stat;
								
								if(obj.stat==0){
									var toolbars=panel.getDockedItems('toolbar[dock="top"]');
									var over_btn=toolbars[2].down("#over_btn");
									over_btn.disable();
								} else {
									var toolbars=panel.getDockedItems('toolbar[dock="top"]');
									var over_btn=toolbars[2].down("#over_btn");
									over_btn.enable();
								}
								//grid.getStore().reload();
							}
						});
	 	
	 }
	 panel.over=function(){
	 	Ext.Msg.confirm("消息","确定要完成吗?",function(btn){
	 		if(btn=='yes'){
		var toolbars=panel.getDockedItems('toolbar[dock="top"]');
		Ext.Ajax.request({
			url:Ext.ContextPath+'/tp/tpQy_over.do',
			params:{
				yxgsno:toolbars[1].down("#compcombo").getValue(),
				ormtno:toolbars[0].down("#ordmtcombo").getValue()
			},
			success:function(response){
				var obj=Ext.decode(response.responseText);
				if(obj.success==false){
					Ext.Msg.alert("消息",obj.msg);
					return;
				}
				var over_btn=toolbars[2].down("#over_btn");
				over_btn.disable();
				window.stat=0;
				grid.getStore().reload();
			}
		});
	 		}
		});
	}
	
	 function createTpQyGrid(initColumns){
	 	var params=panel.getParams();
		var　grid=Ext.create('y.order.TpQyGrid',{
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