
Ext.onReady(function(){
	var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields:['SPTYNO','SPSENO','SAMPNM','SUITNO','PACKQT','ORMTQT','ORMTQT_TP'],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/tp/tpAllQuery.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',
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
	
	var dockedItems=[];
	
	dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
	  
	 dockedItems.push({
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
	  
	  dockedItems.push({
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
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();

				},
				iconCls: 'icon-refresh'
			}]
	   });
	   
	   dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		text: '恢复定制',
				handler: function(btn){
					me.clearNum();
				},
				iconCls: 'icon-remove'
		  	},{
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
	 
	 var cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	 cellEditing.on("edit",function(editor, context){
	 	return;
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	Ext.Ajax.request({
						url:Ext.ContextPath+'/tp/updateOrmtqt_tp.do',
						params:{
							mtorno:record.get("mtorno"),
							sampno:record.get("sampno"),
							suitno:record.get("suitno"),
							ormtqt:value
						},
						success:function(){
							record.commit();
		
						}
						
		});
	  });
	var grid=Ext.create('Ext.grid.Panel',{
		region:'center',
		columnLines :true,
		stripeRows:true,
		dockedItems:dockedItems,
		store:store,
		plugins : [cellEditing],
		columns:[
			{xtype: 'rownumberer'},
			{dataIndex:'SPTYNO_NAME',header:'小类'
	        },
	        {dataIndex:'SPSENO_NAME',header:'系列'
	        },
	        {dataIndex:'SAMPNM',header:'设计样衣编号'
	        },
	        {dataIndex:'SUITNO_NAME',header:'套件'
	        },
	        {dataIndex:'PACKQT',header:'包装要求'
	        },
	        {dataIndex:'ORMTQT',header:'定制总数'
	        },
	        {dataIndex:'ORMTQT_TP',header:'统配总数',
	        	renderer:function(value, metaData, record, rowIndex, colIndex, store){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	 return value;
	            },editor: {
	                xtype: 'numberfield',
	                allowDecimals:false,
	                selectOnFocus:true 
	            }
	        }
		]
		
	});
	var spb_orgno='10206030000';//商品部的id
	grid.getParams=function(){
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');
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
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});