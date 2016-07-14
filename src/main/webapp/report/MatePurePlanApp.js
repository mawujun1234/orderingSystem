
Ext.onReady(function(){
	
	
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
					me.createNew();
				},
				iconCls: 'icon-download-alt'
		  	}]
	   });


	  
	 var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields:['SAMPNM','PRODNM','IDSUNM','NWSUNM','MTTYPE','MATENO','MLITNO','MATESO','PLDATE','MLDATE','MTPUPR',
				'MTCOMP','YARMCT','MLWDTH','ORMTQT','MTCNQT','ORMLQT','HTTRQT','HTORDT',
				'SPSEANM','COLRNM','VERSNM','SPBSENM','SPCLNM','SPTYNM','SPSENM'],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/report/queryMatePurePlan.do',
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
	var grid=Ext.create('Ext.grid.Panel',{
		region:'center',
		columnLines :true,
		stripeRows:true,
		dockedItems:dockedItems,
		store:store,
		//plugins : [cellEditing],
		columns:[
			{xtype: 'rownumberer'},
			{dataIndex:'SAMPNM',header:'订货样衣编号'},
			{dataIndex:'PRODNM',header:'产品货号'},
			{dataIndex:'IDSUNM',header:'原供应商'},
			{dataIndex:'NWSUNM',header:'新供应商'},
			{dataIndex:'MTTYPE',header:'进口/国产'},
			{dataIndex:'MATENO',header:'供应商面料货号'},
			{dataIndex:'MLITNO',header:'面料货号'},
			{dataIndex:'MATESO',header:'主料/拼料'},
			{dataIndex:'PLDATE',header:'计划成衣交期'},
			{dataIndex:'MLDATE',header:'计划面料交期'},
			{dataIndex:'MTPUPR',header:'面料单价'},
			{dataIndex:'HTTRPR',header:'合同单价'},
			{dataIndex:'MTCOMP',header:'面料成分'},
			{dataIndex:'YARMCT',header:'纱支针数'},
			{dataIndex:'GRAMWT',header:'克重密度'},
			{dataIndex:'MLWDTH',header:'门幅'},
			{dataIndex:'ORMTQT',header:'下单件数'},
			{dataIndex:'MTCNQT',header:'单耗'},
			{dataIndex:'ORMLQT',header:'下单米数'},
			{dataIndex:'HTTRQT',header:'合同数量'},
			{dataIndex:'HTORDT',header:'合同交期'},
			{dataIndex:'SPSEANM',header:'季节'},
			{dataIndex:'SPBANM',header:'上市批次'},
			{dataIndex:'COLRNM',header:'颜色'},
			{dataIndex:'VERSNM',header:'版型'},
			{dataIndex:'SPBSENM',header:'大系列'},
			{dataIndex:'SPCLNM',header:'大类'},
			{dataIndex:'SPTYNM',header:'小类'},
			{dataIndex:'SPSENM',header:'系列'}
		]
		
	});

	grid.getParams=function(){
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');
		var params={
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
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid]
	});



});