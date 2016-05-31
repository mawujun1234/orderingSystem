Ext.define('y.sample.SamplePlanGridQuery',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.sample.SamplePlan'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'plspnm',header:'企划样衣编号'
        },
		{dataIndex:'bradno_name',header:'品牌'
        },
		{dataIndex:'spyear',header:'年份'
        },
		{dataIndex:'spsean_name',header:'季节'
        },
		{dataIndex:'spbseno_name',header:'大系列'
        },
		{dataIndex:'sprseno_name',header:'品牌系列'
        },
		{dataIndex:'spclno_name',header:'大类'
        },
		{dataIndex:'sptyno_name',header:'小类'
        },
		{dataIndex:'spseno_name',header:'系列'
        },
		{dataIndex:'splcno_name',header:'定位'
        },
		{dataIndex:'spbano_name',header:'上市批次'
        },
		{dataIndex:'spftpr',header:'出厂价',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'sprtpr',header:'零售价',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'spplrd',header:'企划倍率',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'plctpr',header:'企划成本价',xtype: 'numbercolumn', format:'0.00',align : 'right'
		},
		{dataIndex:'pldate',header:'计划交货期',xtype: 'datecolumn', format:'Y-m-d',width:150
		},
		{dataIndex:'mldate',header:'面料交货期',xtype: 'datecolumn', format:'Y-m-d',width:150
		},
		{dataIndex:'rgsp',header:'创建人'
        },
		{dataIndex:'rgdt',header:'创建日期',xtype: 'datecolumn', format:'Y-m-d H:i:s',width:150
		},
		{dataIndex:'lmsp',header:'修改人'
        },
		{dataIndex:'lmdt',header:'修改日期',xtype: 'datecolumn', format:'Y-m-d H:i:s',width:150
		}
      ];
      
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: 'y.sample.SamplePlan',
			autoLoad:false
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
	  		//enableOverflow:true,
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				readOnly:true
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
		        tyno:'1'
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
		    },{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					var toolbars=grid.getDockedItems('toolbar[dock="top"]');
		
    				//var ordmtcombo=toolbars[0].down("#ordmtcombo");
    				grid.getStore().getProxy().extraParams={
    					"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
    					"params['bradno']":toolbars[0].down("#bradno").getValue(),
    					"params['spclno']":toolbars[0].down("#spclno").getValue(),
    					"params['sptyno']":toolbars[0].down("#sptyno").getValue(),
    					"params['spseno']":toolbars[0].down("#spseno").getValue()
    					//"params['spbseno']":toolbars[1].down("#spbseno").getValue()
    				};
    	
					grid.getStore().reload();
					
//					//预先读取该品牌大类下的规格系列
//					var sizegpField=grid.tabpanel.down("form#sampleDesignForm").getForm().findField("sizegp");
//					sizegpField.getStore().getProxy().extraParams={
//						szbrad:toolbars[0].down("#bradno").getValue(),
//						szclno:toolbars[0].down("#spclno").getValue()
//					};
//					sizegpField.getStore().reload();
					
					//grid.tabpanel.down("form#sampleDesignForm").down("grid#sampleDesignSizegpGrid").reloadEditor(toolbars[0].down("#bradno").getValue(),toolbars[0].down("#spclno").getValue());
				},
				iconCls: 'icon-refresh'
			}]
		});
		
	  
       
      me.callParent();
	}
});
