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
        },
        {dataIndex:'ORMTQT_NOW',header:'兑现数量',width:75
         	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 //metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 if(record.get("ORMTQT_NOW")!=record.get("ORMTQT")){
	        			metaData.tdStyle = 'color:red;background-color:#FFFF66;' ;
	        		} else {
	        			metaData.tdStyle = 'background-color:#CD9B9B;' ;
	        		}
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
		{name:'SUITNO',type:'string'},
		{name:'ORMTQT',type:'string'},
		{name:'MTORNO',type:'string'},
		{name:'MLORNO',type:'string'},
		{name:'MLORVN',type:'string'}
		
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
      
      
      
      if(PRDPK_columns.length!=0){
      		PRDPK_columns.unshift({
      		 	header:"小计",
      		 	dataIndex:"PRDPK___SUBTOTAL"
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
      if(STDSZ_columns.length!=0){
      		STDSZ_columns.unshift({
      		 	header:"小计",
      		 	dataIndex:"STDSZ___SUBTOTAL"
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
      	
      	if(STDSZPRDPK_columns.length!=0){
      		STDSZPRDPK_columns.unshift({
      		 	header:"小计",
      		 	dataIndex:"STDSZPRDPK___SUBTOTAL"
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
	  	var originalValue =context.originalValue 
	  	if(typeof(originalValue)=='undefined'){
			originalValue=0;
		}
	  	
		//var params=grid.getStore().getProxy().extraParams;
		//params.sizeno=field;
		//params.value=value;
	  	var aaa=field.split("___");
	  	
	  	Ext.Ajax.request({
			url:Ext.ContextPath+'/ord/sizeVO/updateOrdszdtl.do',
			params:{
				mtorno:record.get("MTORNO"),
				sampno:record.get("SAMPNO"),
				suitno:record.get("SUITNO"),
				// ,
				//sizety:record.get("SIZETY"),
				//sizeno:record.get("SIZENO"),
				mlorno:record.get("MLORNO"),
				sztype:record.get("SZTYPE"),
				orszst:0,
				sizety:aaa[0]=='STDSZPRDPK'?"STDSZ":aaa[0],
				sizeno:aaa[1],
				isSTDSZPRDPK:aaa[0]=='STDSZPRDPK'?true:false,
				value:value
				//orbgqt:
				//orszqt:
	
			},
			success:function(){
				//me.getStore().reload();
				
				
				//更新小计
				//ORMTQT_NOW兑现数量
				var ORMTQT_NOW=record.get("ORMTQT_NOW");
				if(typeof(ORMTQT_NOW)=='undefined'){
					ORMTQT_NOW=0;
				}
				if(aaa[0]=="STDSZ" || aaa[0]=="STDSZPRDPK"){
					var subtotal=record.get(aaa[0]+"___SUBTOTAL");
					if(typeof(subtotal)=='undefined'){
						subtotal=0;
					}
	
					subtotal=subtotal-originalValue+value;
					record.set(aaa[0]+"___SUBTOTAL",subtotal);
					
					ORMTQT_NOW=ORMTQT_NOW-originalValue+value;
					record.set("ORMTQT_NOW",ORMTQT_NOW);
				} else if(aaa[0]=="PRDPK"){
					var subtotal=record.get(aaa[0]+"___SUBTOTAL");
					if(typeof(subtotal)=='undefined'){
						subtotal=0;
					}
					
					subtotal=subtotal-(originalValue)+(value);
					record.set(aaa[0]+"___SUBTOTAL",subtotal);
					
					var packqt=record.get("PACKQT");
					if(typeof(packqt)=='undefined'){
						subtotal=1;
					}
					ORMTQT_NOW=ORMTQT_NOW-(originalValue*packqt)+(value*packqt);
					record.set("ORMTQT_NOW",ORMTQT_NOW);
					record.commit();
				}
				
			}
						
		});
	  	
	  });
       
      me.callParent();
	}
	
});
