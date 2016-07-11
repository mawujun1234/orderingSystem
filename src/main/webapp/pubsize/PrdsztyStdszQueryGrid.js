Ext.define('y.pubsize.PrdsztyStdszQueryGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.pubsize.PubSizeDtl'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	selModel: {
          selType: 'checkboxmodel'
          //,checkOnly:true
    },
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
		{dataIndex:'sizeno',header:'规格代码'
        },
		{dataIndex:'sizenm',header:'规格名称'
        }
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.pubsize.PubSizeDtl',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/pubSize/queryPrdsztyStdsz4Size.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50
			    	//"params['']"
			    },
			    reader:{
					type:'json'//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
//					rootProperty:'root',
//					successProperty:'success',
//					totalProperty:'total'		
				}
			},
			listeners:{
				beforeload:function(store){
					//var grid=me;//Ext.getCmp("sampleDesignGrid");
    				//grid.getStore().getProxy().extraParams=grid.getParams();
				}
			}
	  });

	  me.dockedItems=[];

		me.dockedItems.push({
		  		xtype: 'toolbar',
		  		dock:'top',
			  	items:[{
			  		text: '选择',
					handler: function(btn){
						var records=btn.up("grid").getSelectionModel().getSelection();
						if(records==null || records.length==0){
							Ext.Msg.alert("消息","请先选择行!");
							return;
						}
						me.fireEvent("selRecord",me,records);
					},
					iconCls: 'icon-check'
				}]
		});
		
		me.on("itemdblclick",function(view, record, item, index, e, eOpts){
			me.fireEvent("selRecord",me,[record]);
		});

       
      me.callParent();
	}
});
