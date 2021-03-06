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
	            showBlank:false,
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
		        fieldLabel: '订货样衣编号',
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
					
					panel.query_stat();

				},
				iconCls: 'icon-refresh'
			}]
	   });
	   
	panel.addDocked({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		text: '订单完成',
		  		disabled:true,
		  		itemId:'over_btn',
				handler: function(btn){
					panel.over();
				},
				iconCls: ' icon-coffee'
		  	},{
		  		text: '导出',
				handler: function(btn){
					panel.onExport();
				},
				iconCls: 'icon-download-alt'
		  	}]
	});
	   
	//var spb_orgno='10206030000';//商品部的id
	panel.getParams=function(){
		var toolbars=panel.getDockedItems('toolbar[dock="top"]');
		var params={
			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
			"params['bradno']":toolbars[0].down("#bradno").getValue(),
			"params['spclno']":toolbars[0].down("#spclno").getValue(),
			"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
			"params['spseno']":toolbars[0].down("#spseno").getValue(),
			//"params['mtorno']":toolbars[0].down("#ordmtcombo").getValue()+"_TP_"+spb_orgno,
			"params['sampnm']":toolbars[1].down("#sampnm").getValue()
			//"params['orstat']":toolbars[1].down("#orstat").getValue()
		};
		return params;
	}
	   
	  //获取营销公司的组成column
	 function queryColumns(){
		Ext.Ajax.request({
			url:Ext.ContextPath+"/tp/queryTpYxgsColumns.do",
//			params:{
//				sizegp:sizegp.getValue(),
//				sztype:sztype
//			},
			success:function(response){
			 	//console.log(response.responseText);
				var obj=Ext.decode(response.responseText);
				//panel.removeAll(true);
				viewPort.remove(grid,true);
				grid=createTpYxgsGrid(obj);
				viewPort.add(grid);
			}
		});
	 }
	 queryColumns();
	 panel.query_stat=function(){

	 	var toolbars=panel.getDockedItems('toolbar[dock="top"]');
	 	Ext.Ajax.request({
							url:Ext.ContextPath+'/tp/tpYxgs_getStat.do',
							params:{
								ormtno:toolbars[0].down("#ordmtcombo").getValue(),
								bradno:toolbars[0].down("#bradno").getValue(),
								spclno:toolbars[0].down("#spclno").getValue()
							},
							success:function(response){
								var obj=Ext.decode(response.responseText);
								window.stat=obj.stat;
								
								if(obj.stat==0){
									var toolbars=panel.getDockedItems('toolbar[dock="top"]');
									var over_btn=toolbars[2].down("#over_btn");
									over_btn.disable();
								} else if(obj.stat==3){
									var toolbars=panel.getDockedItems('toolbar[dock="top"]');
									var over_btn=toolbars[2].down("#over_btn");
									over_btn.enable();
								}
								//grid.getStore().reload();
							}
						});
	 	
	 }
	 panel.over=function(){
	 	Ext.Msg.confirm("消息","确定要按‘大类+品牌’完成吗?",function(btn){
	 		if(btn=='yes'){
	 			Ext.getBody().mask("正在处理....");
		var toolbars=panel.getDockedItems('toolbar[dock="top"]');
		Ext.Ajax.request({
			url:Ext.ContextPath+'/tp/tpYxgs_over.do',
			params:{
				//ordorg:,
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
//				var over_btn=toolbars[2].down("#over_btn");
//				over_btn.disable();
//				window.stat=0;
				 panel.query_stat();
				grid.getStore().reload();
				Ext.getBody().unmask();
			},
			failure:function(){
				Ext.getBody().unmask();
			}
		});
	 		}
		});
	}
	
	panel.onExport=function(){
    	var params=panel.getParams();
    	var url=Ext.ContextPath+"/tp/tpYxgsExport.do?"+Ext.urlEncode(params);
    	window.open(url);
    }
	 
	 function createTpYxgsGrid(initColumns){
	 	var params=panel.getParams();
		var　grid=Ext.create('y.order.TpYxgsGrid',{
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