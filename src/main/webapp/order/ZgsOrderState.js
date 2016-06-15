Ext.define('y.order.ZgsOrderState',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'y.pubsize.OrdSzrt'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
		var me = this;
		me.columns=[
			{xtype: 'rownumberer'},
	      	{dataIndex:'ORGNM',header:'订货单位'
	        },
	        {dataIndex:'SDTYNM',header:'订单节点'
	        },
	        {dataIndex:'DEITNM',header:'订单状态'
	        }
		];
		var fields=[
			{name:'ORGNM',type:'string'},
			{name:'SDTYNM',type:'string'},
			{name:'DEITNM',type:'string'}
		  ];
		me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields:fields,
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ord/zgsVO/queryOrderState.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json'
				}
			},
			listeners:{
				load:function(store,records){
	
				
				}
			}
	   });  
	   
	   me.callParent(); 
	}
});