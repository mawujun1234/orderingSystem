
Ext.onReady(function(){
	 var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields:['SPCLNM','SPTYNM','SPSENM','SPRSENM','SPSEANM','SPBANM','SAMPNM',
			'PRODNM','GUSTNO','COLRNM','MTTYPE','MTCOMP','YARMCT','GRAMWT',
			'DESP','SPCTPR','aaaa','SPFTPR','SPRTPR','PLDTCT','PLDATE','IDSUNM','SPMTNM','ORMTQT'],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/report/queryClothPurePlan.do',
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
		        fieldLabel: '订货样衣编号',
		        labelWidth:85,
		        width:185,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    },{
		        fieldLabel: '货号',
		        labelWidth:40,
		        width:165,
		        itemId: 'prodnm',
	            xtype:'textfield'
		    },{
		        fieldLabel: '生产类型',
		        itemId: 'spmtno',
		        labelWidth:65,
		        width:145,
		        xtype:'pubcodecombo',
		        tyno:'29',
		        value:'WG',
		        listeners:{
		       		change:function(field, newValue, oldValue){

		       		}
		        
		        }
		    },{
		        fieldLabel: '生产单位',
		        labelWidth:65,
		        width:165,
		        itemId: 'idsunm',
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
			},{
		  		text: '导出',
				handler: function(btn){
					var grid=btn.up("grid");
					grid.onExport();
				},
				iconCls: 'icon-download-alt'
		  	}]
	   });

	 
//	 var cellEditing = new Ext.grid.plugin.CellEditing({  
//            clicksToEdit : 1  
//      });  
//	 cellEditing.on("edit",function(editor, context){
//	 	return;
//	  	var record=context.record;
//	  	var grid=context.grid;
//	  	var field =context.field ;
//	  	var value=context.value;
//	  	Ext.Ajax.request({
//						url:Ext.ContextPath+'/tp/updateOrmtqt_tp.do',
//						params:{
//							mtorno:record.get("mtorno"),
//							sampno:record.get("sampno"),
//							suitno:record.get("suitno"),
//							ormtqt:value
//						},
//						success:function(){
//							record.commit();
//		
//						}
//						
//		});
//	  });
	  
	
	var grid=Ext.create('Ext.grid.Panel',{
		region:'center',
		columnLines :true,
		stripeRows:true,
		dockedItems:dockedItems,
		store:store,
		//plugins : [cellEditing],
		columns:[
			{xtype: 'rownumberer'},
			{dataIndex:'SPCLNM',header:'大类'},
			{dataIndex:'SPTYNM',header:'小类'},
			{dataIndex:'SPSENM',header:'系列'},
			{dataIndex:'SPRSENM',header:'风格'},
			{dataIndex:'SPSEANM',header:'季节'},
			{dataIndex:'SPLCNM',header:'定位'},
			{dataIndex:'PLGRNM',header:'商品等级'},
			{dataIndex:'SPBANM',header:'上市月份'},
			{dataIndex:'SAMPNM',header:'订货样衣编号'},
			{dataIndex:'PRODNM',header:'成品货号'},
			{dataIndex:'GUSTNO',header:'客供编号'},
			{dataIndex:'COLRNM',header:'颜色'},
			{dataIndex:'MTTYPE',header:'进口/国产'},
			{dataIndex:'MTCOMP',header:'面料成分'},
			{dataIndex:'YARMCT',header:'纱支'},
			{dataIndex:'GRAMWT',header:'克重'},
			{dataIndex:'DESP',header:'规格版型特殊说明'},
			{dataIndex:'SPCTPR',header:'成衣原价'},
			{dataIndex:'aaaa',header:'新成本价'},
			{dataIndex:'SPFTPR',header:'出厂价'},
			{dataIndex:'SPRTPR',header:'零售价'},
			{dataIndex:'PLDTCT',header:'交货批次'},
			{dataIndex:'PLDATE',header:'成衣交期'},
			{dataIndex:'IDSUNM',header:'生产单位'},
			{dataIndex:'SPMTNM',header:'生产类型'},
			{dataIndex:'ORMTQT',header:'订货总量'}
		]
		
	});

	grid.getParams=function(){
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');
		var params={
			start:0,
			page:1,
			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
			"params['bradno']":toolbars[0].down("#bradno").getValue(),
			"params['spclno']":toolbars[0].down("#spclno").getValue(),
			"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
			"params['spseno']":toolbars[0].down("#spseno").getValue(),
			"params['sampnm']":toolbars[1].down("#sampnm").getValue(),
			"params['prodnm']":toolbars[1].down("#prodnm").getValue(),
			"params['spmtno']":toolbars[1].down("#spmtno").getValue(),
			"params['idsunm']":toolbars[1].down("#idsunm").getValue()
		};
		return params;
	}
	
	grid.onExport=function(){
		
		var params=grid.getParams();
		var url=Ext.ContextPath+"/report/exportClothPurePlan.do?"+Ext.urlEncode(params);
		window.open(url);
	}
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});