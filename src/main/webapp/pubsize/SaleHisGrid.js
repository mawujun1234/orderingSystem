Ext.define('y.pubsize.SaleHisGrid',{
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
      	{dataIndex:'PRSENM',header:'系列'
        },
        {dataIndex:'BANDNM',header:'版型'
        },
        {dataIndex:'PRYEAR',header:'年份'
        }
      ];
      var fields=[
		{name:'PRSENM',type:'string'},
		{name:'BANDNM',type:'string'},
		{name:'PRYEAR',type:'string'}
	  ];
      
      for(var i=0;i<me.initColumns.length;i++){
      	 me.columns.push({
      	 	dataIndex:me.initColumns[i]["sizeno"],
      	 	header:me.initColumns[i]["sizenm"]
      	 });
      	 
      	 fields.push({name:me.initColumns["sizeno"],type:'int'});
      }
      delete me.initColumns;
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields: fields,
			data :[]
			
	  });
	  delete me.params;

      me.callParent();
	}
	
});
