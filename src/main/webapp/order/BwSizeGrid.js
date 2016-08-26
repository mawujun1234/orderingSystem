

Ext.define('y.order.BwSizeGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'y.pubsize.OrdSzrt'
	],
	columnLines :true,
	stripeRows:true,
	selModel: {
          selType: 'checkboxmodel'
          ,checkOnly:true
    },
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
         {dataIndex:'MLDATE',header:'面料交货期'
        },
         {dataIndex:'PLDATE',header:'成衣交货期'
        },
         {dataIndex:'PPLACE',header:'产地'
        },
        {dataIndex:'ORBGQT_SUBTOTAL',header:'备忘合计',width:75
         	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 return value;
            }
        },
        {dataIndex:'ORSZQT_SUBTOTAL',header:'数量合计',width:75
         	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 //metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 if(record.get("ORSZQT_SUBTOTAL")!=record.get("ORBGQT_SUBTOTAL")){
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
		{name:'ORBGQT_SUBTOTAL',type:'string'},
		{name:'ORSZQT_SUBTOTAL',type:'string'},
		{name:'ORMMNO',type:'string'},
		{name:'MMORNO',type:'string'},
		{name:'MLDATE',type:'string'},
		{name:'PLDATE',type:'string'},
		{name:'PPLACE',type:'string'}
		
	  ];
      
	  var STDSZ_columns=[];//单规
	  var PRDPK_columns=[];//标准箱
	 // var STDSZPRDPK_columns=[];//单规包装
      for(var i=0;i<me.initColumns.length;i++){
      	var initColumn=me.initColumns[i];
      	if(initColumn.SIZETY=='PRDPK'){
      		PRDPK_columns.push({
	      		header:initColumn["SIZENM"],
	      		columns:[{
	      			header:'可用',
	      			dataIndex:"ORBGQT_"+initColumn["SIZENO"],
	      			width: 60
	      		},{
	      			header:'数量',
	      			dataIndex:"ORSZQT_"+initColumn["SIZENO"],
	      			width: 60
	      			,editor: {
		                xtype: 'numberfield',
		                allowDecimals:false,
		                selectOnFocus:true 
		            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
		            	//console.log(record.get("PACKQT___"));
		            	//单规+包装箱
		            	if(window.orstat==0 && record.get("ORDORG")!='TOTAL'){
		            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
		            	} else {
		            		//metaData.tdStyle = 'background-color:#98FB98;' ;
		            	}
		            	 return value;
	            	}
	      		}]
	      	});
      	} else if(initColumn.SIZETY=='STDSZ'){
      		STDSZ_columns.push({
	      		header:initColumn["SIZENM"],
	      		columns:[{
	      			header:'可用',
	      			dataIndex:"ORBGQT_"+initColumn["SIZENO"],
	      			width: 60
	      		},{
	      			header:'数量',
	      			dataIndex:"ORSZQT_"+initColumn["SIZENO"],
	      			width: 60
	      			,editor: {
		                xtype: 'numberfield',
		                allowDecimals:false,
		                selectOnFocus:true 
		            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
		            	//console.log(window.szsta);
		            	//单规+包装箱
		            	if(window.orstat==0 && record.get("ORDORG")!='TOTAL'){
		            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
		            	} else {
		            		//metaData.tdStyle = 'background-color:#98FB98;' ;
		            	}
		            	 return value;
	            	}
	      		}]
	      	});
      	}
      	

      	 fields.push({name:"ORBGQT_"+initColumn["SIZENO"],type:'int'});
      	 fields.push({name:"ORSZQT_"+initColumn["SIZENO"],type:'int'});
      }
      
      
       if(STDSZ_columns.length!=0){
       		me.columns.push({
      			header:'标准箱',
      			columns:PRDPK_columns
      		});
      		me.columns.push({
      			header:'规格',
      			columns:STDSZ_columns
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
			    url : Ext.ContextPath+'/bwOrdmt/querySizeVOData.do',
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
	  	//var aaa=field.split("___");
	  	if(window.orstat==0  && record.get("ORDORG")!='TOTAL') {
		  	return true
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
		
		
	  	
		
		//params.sizeno=field;
		//params.value=value;
	  	var aaa=field.split("_");
	  	if(record.get("ORBGQT_"+aaa[1])<value){
			Ext.Msg.alert("消息","输入数据不能大于剩余备忘数量!");
			record.set(field,originalValue);
			return;
		}
	  	
	  	var params=grid.getStore().getProxy().extraParams;
	  	Ext.Ajax.request({
			url:Ext.ContextPath+'/bwOrdmt/updateOrszqt.do',
			params:{
				ormtno:params.ormtno,
				ordorg:params.ordorg,
				bradno:params.bradno,
				spclno:params.spclno,
				
				
				ormmno:record.get("ORMMNO"),//子批次编号
				mmorno:record.get("MMORNO"),//子批次订单号
				sampno:record.get("SAMPNO"),
				suitno:params.suitno,
				
				sizety:record.get("SIZETY___"+aaa[1]),
				sizeno:aaa[1],
				orszqt:value

	
			},
			success:function(response){
				//me.getStore().reload();
				var obj=Ext.decode(response.responseText);
				if(obj.success==false){
					record.set(field,originalValue);
				 	Ext.Msg.alert("消息",obj.msg);
				 	return;
				}
				

				
				//更新小计
				//ORMTQT_NOW兑现数量
				var ORSZQT_SUBTOTAL=record.get("ORSZQT_SUBTOTAL");
				if(typeof(ORSZQT_SUBTOTAL)=='undefined') {
					ORSZQT_SUBTOTAL=0;
				}
				
				var packqt=record.get("PACKQT___"+aaa[1]);//record.get("PACKQT");
				if(typeof(packqt)=='undefined'){
					packqt=1;
				}
				ORSZQT_SUBTOTAL=ORSZQT_SUBTOTAL-(originalValue*packqt)+(value*packqt);
				record.set("ORSZQT_SUBTOTAL",ORSZQT_SUBTOTAL);
				record.commit();		
//				ORSZQT_SUBTOTAL=ORSZQT_SUBTOTAL-originalValue+value;
//				record.set("ORSZQT_SUBTOTAL",ORSZQT_SUBTOTAL);
//				record.commit();

			}
						
		});
	  	
	  });
       
      me.callParent();
	}
	
});
