

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
      	{dataIndex:'SAMPNO',header:'图片',width:55
      		,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 
	            	 return "<a href='#' onclick='clickShowPhoto(\""+value+"\")'>查看</a>";
            }
        },
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
        {dataIndex:'ORMTQT',header:'兑现数量',width:75
         	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 return value;
            }
        },
        {dataIndex:'ORMTQT_NOW',header:'规格合计',width:75
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
	            	//console.log(window.szsta);
	            	//单规+包装箱
	            	if(window.szstat==0 && record.get("ORSZST")!=1){
	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	} else {
	            		//metaData.tdStyle = 'background-color:#98FB98;' ;
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
	            	//自动成箱 后 ORD_ORDSZDTL.ORSZST=1 时，规格 合计 下的单元格不允许 编辑，标准箱  下的单元格 允许编辑；
	            	if(window.szstat==0 && record.get("SZTYPE")==0 && record.get("ORSZST")==1){
	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	} else if(window.szstat==0 && record.get("SZTYPE")!=0 && record.get("ORSZST")==0){
	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	}
	            	 return value;
            	}
      		 });
      	}  else if(initColumn.SIZETY=='STDSZPRDPK'){
      		STDSZPRDPK_columns.push({
      		 	header:initColumn["SIZENM"],
      		 	dataIndex:initColumn["SIZENO"]
      		 	,width: 80
//      		 	,editor: {
//	                xtype: 'numberfield',
//	                allowDecimals:false,
//	                selectOnFocus:true 
//	            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
//	            	//自动成箱 后 ORD_ORDSZDTL.ORSZST=1 时，规格 合计 下的单元格不允许 编辑，标准箱  下的单元格 允许编辑；
//	            	if(record.get("SZTYPE")==0 ){
//	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
//	            	} else if(record.get("SZTYPE")!=0 && record.get("ORSZST")!=1){
//	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
//	            	}
//	            	 
//	            	 return value;
//            	}
      		 });
      	}

      	 fields.push({name:initColumn["SIZENO"],type:'int'});
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
	  
	  this.cellEditing.on("beforeedit",function(editor, context){
	  	var record=context.record;
	  	var field =context.field ;
	  	var aaa=field.split("___");
	  	if(window.szstat==0) {
		  	//自动成箱 后 ORD_ORDSZDTL.ORSZST=1 时，规格 合计 下的单元格不允许 编辑，标准箱  下的单元格 允许编辑；
		    if(record.get("SZTYPE")==0 && aaa[0]=="STDSZ"  && record.get("ORSZST")==0){
		       //metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
		    	return true;
		    } else if(record.get("SZTYPE")==0 && aaa[0]=="PRDPK"  && record.get("ORSZST")==1){
		    	return true;
		    } else if(record.get("SZTYPE")!=0 && record.get("ORSZST")!=1) {
		        return true;
		    }
	  	}
	    return false;
	  });
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
			success:function(response){
				//me.getStore().reload();
				var obj=Ext.decode(response.responseText);
				if(obj.success==false){
				 	Ext.Msg.alert("消息",obj.msg);return;
				}
				
				if(record.get("SZTYPE")==0){
					
				}
				
				
				//更新小计
				//ORMTQT_NOW兑现数量
				var ORMTQT_NOW=record.get("ORMTQT_NOW");
				if(typeof(ORMTQT_NOW)=='undefined'){
					ORMTQT_NOW=0;
				}
				//如果是整箱上报,需要把数量加到规格合计里面，其他两种情况，只需要算单规就可以了
				if(record.get("SZTYPE")==2){
					if(aaa[0]=="STDSZ"){
						var subtotal=record.get(aaa[0]+"___SUBTOTAL");
						if(typeof(subtotal)=='undefined'){
							subtotal=0;
						}
		
						subtotal=subtotal-originalValue+value;
						record.set(aaa[0]+"___SUBTOTAL",subtotal);
						
						ORMTQT_NOW=ORMTQT_NOW-originalValue+value;
						record.set("ORMTQT_NOW",ORMTQT_NOW);
						record.commit();
					} else{
						var subtotal=record.get(aaa[0]+"___SUBTOTAL");
						if(typeof(subtotal)=='undefined'){
							subtotal=0;
						}
						
						subtotal=subtotal-(originalValue)+(value);
						record.set(aaa[0]+"___SUBTOTAL",subtotal);
						
						//"PRDPK___PACKQT___"+aaa[1]
						var packqt=record.get("PRDPK___PACKQT___"+aaa[1]);//record.get("PACKQT");
						if(typeof(packqt)=='undefined'){
							subtotal=1;
						}
						ORMTQT_NOW=ORMTQT_NOW-(originalValue*packqt)+(value*packqt);
						record.set("ORMTQT_NOW",ORMTQT_NOW);
						record.commit();
					}
				} else if(record.get("SZTYPE")==0){//整箱+单规 上报的时候
					if(aaa[0]=="STDSZ"){
						var subtotal=record.get(aaa[0]+"___SUBTOTAL");
						if(typeof(subtotal)=='undefined'){
							subtotal=0;
						}
		
						subtotal=subtotal-originalValue+value;
						record.set(aaa[0]+"___SUBTOTAL",subtotal);
						
						ORMTQT_NOW=ORMTQT_NOW-originalValue+value;
						record.set("ORMTQT_NOW",ORMTQT_NOW);
						record.commit();
					} else{
						grid.getStore().reload();
//						var subtotal=record.get(aaa[0]+"___SUBTOTAL");
//						if(typeof(subtotal)=='undefined'){
//							subtotal=0;
//						}
//						var packqt=record.get("PRDPK___PACKQT___"+aaa[1]);//record.get("PACKQT");
//						//alert(packqt);
//						if(typeof(packqt)=='undefined'){
//							subtotal=1;
//						}
//						subtotal=subtotal-(originalValue*packqt)+(value*packqt);
//						record.set(aaa[0]+"___SUBTOTAL",subtotal);
//	
//						record.commit();
					}
				} else {
					if(aaa[0]=="STDSZ"){
						var subtotal=record.get(aaa[0]+"___SUBTOTAL");
						if(typeof(subtotal)=='undefined'){
							subtotal=0;
						}
		
						subtotal=subtotal-originalValue+value;
						record.set(aaa[0]+"___SUBTOTAL",subtotal);
						
						ORMTQT_NOW=ORMTQT_NOW-originalValue+value;
						record.set("ORMTQT_NOW",ORMTQT_NOW);
						record.commit();
					} 
					record.commit();
				}

				
			}
						
		});
	  	
	  });
       
      me.callParent();
	}
	
});
