Ext.define('y.order.SizeVOGrid',{
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
      	{dataIndex:'ORDORG_NAME',header:'订货单位'
        },
        {dataIndex:'SPTYNO_NAME',header:'小类'
        },
        {dataIndex:'SPSENO_NAME',header:'系列'
        },
         {dataIndex:'VERSNO_NAME',header:'版型'
        },
//         {dataIndex:'PLSPNM',header:'企划样衣编号'
//        },
         {dataIndex:'SAMPNM',header:'订货样衣编号'
        },
         {dataIndex:'PRODNM',header:'产品货号'
        },
         {dataIndex:'ORMTQT',header:'平衡数量',width:75
         	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 return value;
            }
        }
      ];
      var fields=[
		{name:'ORDORG',type:'string'},
		{name:'ORDORG_NAME',type:'string'},
		{name:'SPTYNO',type:'string'},
		{name:'SPTYNO_NAME',type:'string'},
		{name:'SPSENO',type:'string'},
		{name:'SPSENO_NAME',type:'string'},
		{name:'VERSNO',type:'string'},
		{name:'VERSNO_NAME',type:'string'},
		{name:'PLSPNM',type:'string'},
		{name:'SAMPNM',type:'string'},
		{name:'ORMTQT',type:'string'}
	  ];
      
	  var STDSZ_columns=[];//单规
	  var PRDPK_columns=[];//包装
	  var STDSZPRDPK_columns=[];//单规包装
      for(var i=0;i<me.initColumns.length;i++){
      	var initColumn=me.initColumns[i];
      	if(initColumn.SIZETY=='STDSZ'){//单规
      		 STDSZ_columns.push({
      		 	header:initColumn["SIZENM"],
      		 	dataIndex:initColumn["SIZENO"]
      		 	,width: 80
      		 	,editor: {
	                xtype: 'numberfield',
	                allowDecimals:false,
	                selectOnFocus:true 
	            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	if(value>0){
	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	} else {
	            		metaData.tdStyle = 'background-color:#98FB98;' ;
	            	}
	            	 return value;
            	}
      		 });
      	} else if(initColumn.SIZETY=='PRDPK'){
      		 PRDPK_columns.push({
      		 	header:initColumn["SIZENM"],
      		 	dataIndex:initColumn["SIZENO"]
      		 	,width: 80
      		 	,editor: {
	                xtype: 'numberfield',
	                allowDecimals:false,
	                selectOnFocus:true 
	            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	if(value>0){
	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	} else {
	            		metaData.tdStyle = 'background-color:#98FB98;' ;
	            	}
	            	 return value;
            	}
      		 });
      	}  else if(initColumn.SIZETY=='STDSZPRDPK'){
      		STDSZPRDPK_columns.push({
      		 	header:initColumn["SIZENM"],
      		 	dataIndex:initColumn["SIZENO"]
      		 	,width: 80
      		 	,editor: {
	                xtype: 'numberfield',
	                allowDecimals:false,
	                selectOnFocus:true 
	            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	if(value>0){
	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	} else {
	            		metaData.tdStyle = 'background-color:#98FB98;' ;
	            	}
	            	 
	            	 return value;
            	}
      		 });
      	}

      	 fields.push({name:initColumn["SIZENO"],type:'int'});
      }
      if(STDSZ_columns.length!=0){
      		STDSZ_columns.unshift({
      		 	header:"小计",
      		 	dataIndex:"STDSZ_SUBTOTL"
      		 	,width: 80,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 return value;
            	}
      		 });
      		me.columns.push({
      			header:'规格合计',
      			columns:STDSZ_columns
      		});
      	}
      	if(PRDPK_columns.length!=0){
      		PRDPK_columns.unshift({
      		 	header:"小计",
      		 	dataIndex:"PRDPK_SUBTOTL"
      		 	,width: 80,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 return value;
            	}
      		 });
      		me.columns.push({
      			header:'标准箱',
      			columns:PRDPK_columns
      		});
      	}
      	if(STDSZPRDPK_columns.length!=0){
      		STDSZPRDPK_columns.unshift({
      		 	header:"小计",
      		 	dataIndex:"STDSZPRDPK_SUBTOTL"
      		 	,width: 80,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 return value;
            	}
      		 });
      		me.columns.push({
      			header:'单规',
      			columns:STDSZPRDPK_columns
      		});
      	}
      	
      	
      delete me.initColumns;
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			fields: fields,
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ord/sizeVO/querySizeVOData.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:me.params,
			    paramsAsJson:true,
			    writer:{
			    	type:'json'
			    },
			    reader:{
					type:'json'
//					rootProperty:'root',
//					successProperty:'success',
//					totalProperty:'total'		
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
	  	

//	  	Ext.Ajax.request({
//			url:Ext.ContextPath+'/ordSzrt/create.do',
//			params:{
//				ormtno:record.get("ORMTNO"),
//				ordorg:record.get("ORDORG"),
//				sizegp:record.get("SIZEGP"),
//				sizeno:field ,
//				bradno:record.get("BRADNO"),
//				spclno:record.get("SPCLNO"),
//				versno:record.get("VERSNO"),
//				spseno:record.get("SPSENO"),
//				sizety:'STDSZ',
//				szrate:value
//			},
//			success:function(){
//				//me.getStore().reload();
//				record.commit();
//			}
//						
//		});
	  	
	  });
       
      me.callParent();
	}
	
});
