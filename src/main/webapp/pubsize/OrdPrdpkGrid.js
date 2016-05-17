Ext.define('y.pubsize.OrdPrdpkGrid',{
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
      	{dataIndex:'SPSENO_NAME',header:'系列'
        },
        {dataIndex:'VERSNO_NAME',header:'版型'
        }
      ];
      var fields=[
		{name:'ORMTNO',type:'string'},
		{name:'ORDORG',type:'string'},
		{name:'SIZEGP',type:'string'},
		{name:'SIZENO',type:'string'},
		{name:'BRADNO',type:'string'},
		{name:'SPCLNO',type:'string'},
		{name:'SPSENO',type:'string'},
		{name:'SPSENO_NAME',type:'string'},
		{name:'VERSNO',type:'string'},
		{name:'VERSNO_NAME',type:'string'},
		{name:'SIZETY',type:'string'},
		{name:'SZRATE',type:'float'}
	  ];
      
      for(var i=0;i<me.initColumns.length;i++){
      	 me.columns.push({
      	 	dataIndex:me.initColumns[i]["sizeno"],
      	 	header:me.initColumns[i]["sizenm"]
      	 	,editor: {
                xtype: 'numberfield',
                allowDecimals:true,
                maxValue:0.9999,
                selectOnFocus:true 
            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
            	 metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
            	 return value;
            }
      	 });
      	 
      	 fields.push({name:me.initColumns["sizeno"],type:'int'});
      }
      delete me.initColumns;
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			fields: fields,
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ordSzrt/querySzrtData.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:me.params,
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });
	  delete me.params;

	   this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	

	  	Ext.Ajax.request({
			url:Ext.ContextPath+'/ordSzrt/create.do',
			params:{
				ormtno:record.get("ORMTNO"),
				ordorg:record.get("ORDORG"),
				sizegp:record.get("SIZEGP"),
				sizeno:field ,
				bradno:record.get("BRADNO"),
				spclno:record.get("SPCLNO"),
				versno:record.get("VERSNO"),
				spseno:record.get("SPSENO"),
				sizety:'PRDPK',
				szrate:value
			},
			success:function(){
				//me.getStore().reload();
				record.commit();
			}
						
		});
	  	
	  });
       
      me.callParent();
	}
	
});
