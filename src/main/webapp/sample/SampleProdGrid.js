Ext.define('y.sample.SampleProdGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.sample.SampleProd'
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
		{dataIndex:'sampnm',header:'设计样衣编号'
        },
		{dataIndex:'suitno_name',header:'套件'
        },
		{dataIndex:'prseqm',header:'序号'
		},
//		{dataIndex:'prodno',header:'货号代码'
//        },
		{dataIndex:'prodnm',header:'货号名称'
//			,editor: {
//	                xtype: 'numberfield',
//	                allowDecimals:false,
//	                selectOnFocus:true 
//	        },renderer:function(value, metaData, record, rowIndex, colIndex, store){
//	            metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
//	            return value;
//            }
        },
        {dataIndex:'sample_state',header:'样衣状态'
        	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
        		if(value=='未订货'){
        			 metaData.tdStyle = 'color:red;' ;
        		}
	           
	            return value;
            }
        },
        {dataIndex:'spclno_name',header:'大类'
        },
        {dataIndex:'sptyno_name',header:'小类'
        },
        {dataIndex:'spsean_name',header:'季节'
        },
        {dataIndex:'spseno_name',header:'系列'
        },
        {dataIndex:'splcno_name',header:'定位'
        },
        {dataIndex:'spbano_name',header:'上市批次'
        },
        {dataIndex:'versno_name',header:'版型'
        },
        {dataIndex:'stylno_name',header:'款式'
        },
        {dataIndex:'stylgp',header:'款式组'
        },
        {dataIndex:'sexno_name',header:'性别'
        },
        {dataIndex:'slveno_name',header:'长短袖'
        },
//		{dataIndex:'prodds',header:'货号简称'
//        },
		{dataIndex:'sizegp_name',header:'规格系列'
        },
		{dataIndex:'prtype_name',header:'类别'
        },
		{dataIndex:'prprpt_name',header:'商品性质'
        },
		{dataIndex:'prorgd_name',header:'渠道限制'
        },
		{dataIndex:'prunit',header:'单位'
        },
		{dataIndex:'mateno',header:'面料'
        },
		{dataIndex:'prdcno',header:'对照码'
        },
		{dataIndex:'prftpr',header:'出厂价'
        },
//		{dataIndex:'prsupr',header:'供应价'
//        },
		{dataIndex:'prrtpr',header:'零售价'
        },
		{dataIndex:'prctpr',header:'成本价'
        },
		{dataIndex:'prmtam',header:'加工费'
        },
		{dataIndex:'prmlam',header:'面料费'
        },
		{dataIndex:'prorpr',header:'合同价'
        },
		{dataIndex:'prflam',header:'包装辅料费'
        },
//		{dataIndex:'prprnt',header:'打印系列'
//        },
//		{dataIndex:'prqtnm',header:'其他'
//        },
//		{dataIndex:'prname',header:'品名'
//        },
//		{dataIndex:'prsasd',header:'安全标准'
//        },
//		{dataIndex:'prdosd',header:'执行标准'
//        },
//		{dataIndex:'prwapt',header:'洗涤图标'
//        },
//		{dataIndex:'prwash',header:'洗涤说明'
//        },
		{dataIndex:'prmark',header:'备注'
        }
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: 'y.sample.SampleProd',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/sampleProd/queryPager.do',
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

	  me.dockedItems=[];
      me.dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
	   me.initReload_index_grid=1,
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
	  		//enableOverflow:true,
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				listeners:{
					select:function(combo , record , eOpts){
						window.ordmt_record=record;
						me.initReload_index_grid++;
						me.reload();
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
					select:function(combo , record , eOpts){
						me.initReload_index_grid++;
						me.reload();
					}
				}
		    },{
		        fieldLabel: '大类',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
		        showBlank:true,
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
		        		
		        		me.initReload_index_grid++;
						me.reload();
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
		        fieldLabel: '生产单位',
		        labelWidth:60,
		        width:220,
		        name: 'prsuno',
		        itemId: 'prsuno',
	            xtype:'pubsunocombo'
		    },Ext.create('Ext.form.ComboBox', {
			    fieldLabel: '货号状态',
			    name: 'prod_state',
		        itemId: 'prod_state',
		        labelWidth:60,
		        width:150,
			    store: Ext.create('Ext.data.Store', {
				    fields: ['value', 'name'],
				    data : [
				    	{"value":"all", "name":"所有"},
				        {"value":"ok", "name":"已填写"},
				        {"value":"no", "name":"未填写"}
				    ]
				}),
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'value'
			}),{
		    	emptyText:'请输入订货样衣编号',
		    	itemId: 'sampnm',
		    	width:90,
		    	xtype:'textfield'
		    	
		    },{
		    	emptyText:'请输入货号',
		    	itemId: 'prodnm',
		    	width:90,
		    	xtype:'textfield'
		    	
		    },Ext.create('Ext.form.ComboBox', {
			    fieldLabel: '样衣状态',
			    name: 'sample_state',
		        itemId: 'sample_state',
		        labelWidth:60,
		        width:150,
			    store: Ext.create('Ext.data.Store', {
				    fields: ['value', 'name'],
				    data : [
				    	{"value":"all", "name":"所有"},
				        {"value":"ok", "name":"已订货"},
				        {"value":"no", "name":"未订货"}
				    ]
				}),
				value:'ok',
			    queryMode: 'local',
			    displayField: 'name',
			    valueField: 'value'
			})]
	 });
	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.reload();
				},
				iconCls: 'icon-refresh'
			},{
		  		text: '导出',
				handler: function(btn){
					var grid=btn.up("grid");
					var params=grid.getParams();
			    	var url=Ext.ContextPath+"/sampleProd/download.do?"+Ext.urlEncode(params);
			    	window.open(url);
				},
				iconCls: 'icon-download-alt'
		  	},{
			   text: '导入',
			   itemId:'onImport',
			   //hidden:!Permision.canShow('plan_orgdtl_import'),
				handler: function(btn){
					me.onImport();
				},
				iconCls: 'icon-upload-alt'
			}]
		});
		
		
	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	var originalValue =context.originalValue 
	  	//console.log(record.getData());
	  	Ext.Ajax.request({
	  		url:Ext.ContextPath+'/sampleProd/createOrUpdate.do',
	  		jsonData :record.getData(),
	  		method:'POST',
	  		success:function(response){
	  			var obj=Ext.decode(response.responseText);
	  			record.commit();
	  			
	  		}
	  	
	  	});
		
	  });

       
      me.callParent();
	},
	reload:function(){
		//当3个必须条件都初始化结束，就自动进行查询
		var grid=this;
		if(this.initReload_index_grid>=3){
			grid.getStore().getProxy().extraParams=grid.getParams();
			grid.getStore().reload();
		}
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var params={
				"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
				"params['bradno']":toolbars[0].down("#bradno").getValue(),
				"params['spclno']":toolbars[0].down("#spclno").getValue(),
				"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
				"params['spseno']":toolbars[0].down("#spseno").getValue(),
				
				"params['prsuno']":toolbars[1].down("#prsuno").getValue(),
				"params['prod_state']":toolbars[1].down("#prod_state").getValue(),
				"params['sampnm']":toolbars[1].down("#sampnm").getValue(),
				"params['prodnm']":toolbars[1].down("#prodnm").getValue(),
				"params['sample_state']":toolbars[1].down("#sample_state").getValue()
		};
		return params;
	},
	onCreate:function(){
    	var me=this;
		var child=Ext.create('y.sample.SampleProd',{

		});
		child.set("id",null);
		
		var formpanel=Ext.create('y.sample.SampleProdForm',{});
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
    onImport:function(){
		var me=this;
		var extraParams=me.getStore().getProxy().extraParams;
		var formpanel=Ext.create('Ext.form.Panel',{
			items:[{
				xtype:'hiddenfield',
				name:'spclno',
				value:extraParams[params['spclno']]
			},{
		        xtype: 'filefield',
		        name: 'imageFile',
		       // id:'photo',
		        labelWidth:60,
		        fieldLabel: '文件名',
		        allowBlank: false,
		        anchor: '100%',
		        buttonText: '选择文件...',
		        listeners:{
		        	change:function(field, value){
	
		        	}
		        }
		    }]
		});
		
		var win=Ext.create('Ext.window.Window',{
			items:[formpanel],
			modal:true,
			height:120,
			width:320,
			buttons:[{
				text : '上传',
				//formBind: true, //only enabled once the form is valid
	       		//disabled: true,
				glyph : 0xf0c7,
				handler : function(button){
					
					formpanel.getForm().submit({
						 waitMsg:'正在上传请稍候',  
	                     waitTitle:'提示', 
	                     url:Ext.ContextPath+'/sampleProd/import.do', 
	                     //method:'POST', 
	                     success:function(form,action){   	
	                     	button.up('window').close();
	                     	me.getStore().reload();
	                     },
	                     failure:function(form,action){
	                     	Ext.Msg.alert("警告",action.result.msg);                    
	                     }
					});	
				
				}
			}]
		});
		win.show();
	}
    
   
});
