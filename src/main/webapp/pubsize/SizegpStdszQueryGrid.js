/**
 * 用于规格范围选择规格池里的单规的时候
 */
Ext.define('y.pubsize.SizegpStdszQueryGrid',{
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
//		{dataIndex:'sizety',header:'规格类型'
//        },
		{dataIndex:'sizeno',header:'规格代码'
        },
		{dataIndex:'sizenm',header:'规格名称'
        }
//		{dataIndex:'szbrad',header:'品牌'
//        },
//		{dataIndex:'szclno',header:'大类'
//        },
//      	{dataIndex:'sizety1_name',header:'包装类型'
//        },
//		{dataIndex:'sizeqt',header:'数量',xtype: 'numbercolumn', format:'0',align : 'right',editor: {
//                xtype: 'numberfield',
//                allowDecimals:false,
//                selectOnFocus:true 
//            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
//            	 metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
//            	 return value;
//            }
//		}
//		{dataIndex:'sizemk',header:'备注'
//        },
////		{dataIndex:'sizeso',header:'排序',xtype: 'numbercolumn', format:'0',align : 'right'
////		},
//		{dataIndex:'sizest_name',header:'状态'
//		}
//		{dataIndex:'szsast_name',header:'当季状态'
//		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.pubsize.PubSizeDtl',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/pubSize/querySizegpPrdszty.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{
				    limit:50
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
		  		text: '确认',
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
