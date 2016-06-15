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
	panel.addDocked({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
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
				handler: function(btn){
					me.meger_all();
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
	   
	var spb_orgno='10206030000';//商品部的id
	panel.getParams=function(){
		var toolbars=panel.getDockedItems('toolbar[dock="top"]');
		var params={
			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
			"params['bradno']":toolbars[0].down("#bradno").getValue(),
			"params['spclno']":toolbars[0].down("#spclno").getValue(),
			"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
			"params['spseno']":toolbars[0].down("#spseno").getValue(),
			"params['mtorno']":toolbars[0].down("#ordmtcombo").getValue()+"_TP_"+spb_orgno,
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