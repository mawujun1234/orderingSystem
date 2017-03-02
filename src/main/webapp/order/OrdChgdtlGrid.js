Ext.define('y.order.OrdChgdtlGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.order.OrdChgdtl'
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
      	{dataIndex:'spclno_name',header:'大类'
        },
        {dataIndex:'sptyno_name',header:'小类'
        },
        {dataIndex:'spsean_name',header:'季节'
        },
        {dataIndex:'spseno_name',header:'系列'
        },
        {dataIndex:'sampnm',header:'订货样衣编号'
        },
        {dataIndex:'prodnm',header:'货号',width:160
        },
        {dataIndex:'suitno_name',header:'套件'
        },
        {dataIndex:'ormtqt',header:'订货数量'
		},
		{dataIndex:'orchqt',header:'调整数量'
		},
		{dataIndex:'ormtqt_orchqt',header:'调整后数量'
		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.order.OrdChgdtl',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ordChgdtl/queryPager.do',
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
//		        		var toolbar=combo.up("toolbar");
//		        		var suitno=toolbar.down("#suitno");
//		        		suitno.changeBradno(record.get("itno"));
//		        		suitno.getStore().reload();
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
		        fieldLabel: '生产类型',
		        itemId: 'spmtno',
		        labelWidth:65,
		        width:155,
	            //allowBlank: false,
	            //afterLabelTextTpl: Ext.required,
	            //blankText:"生产类型不允许为空",
	            selectOnFocus:true,
		        xtype:'pubcodecombo',
		        tyno:'29'
		    },{
		        fieldLabel: '订货样衣编号',
		        labelWidth:85,
		        width:180,
		        itemId: 'sampnm',
	            xtype:'textfield'
		    },{
		    	emptyText:'请输入货号',
		    	itemId: 'prodnm',
		    	width:150,
		    	xtype:'textfield'
		    	
		    },{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
					
					grid.nextSibling("#south_panel").mask();
				},
				iconCls: 'icon-refresh'
			}]
		});

       
      me.callParent();
	},
	getParams:function(){
		var grid=this;//Ext.getCmp("sampleDesignGrid");
		var toolbars=grid.getDockedItems('toolbar[dock="top"]');

    	var params={
				"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
				"params['bradno']":toolbars[0].down("#bradno").getValue(),
				"params['spclno']":toolbars[0].down("#spclno").getValue(),
				"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
				"params['spseno']":toolbars[0].down("#spseno").getValue(),
				
				"params['spmtno']":toolbars[1].down("#spmtno").getValue(),
	
				"params['sampnm']":toolbars[1].down("#sampnm").getValue(),
				"params['prodnm']":toolbars[1].down("#prodnm").getValue()
		};
    	return params;
	}
});
