Ext.define('y.order.BwQyGrid',{
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
      		{dataIndex:'SPCLNO_NAME',header:'大类'
	        },
			{dataIndex:'SPTYNO_NAME',header:'小类'
	        },
	        {dataIndex:'SPSENO_NAME',header:'系列'
	        },
	        {dataIndex:'SAMPNM',header:'订货样衣编号'
	        },
	        {dataIndex:'SUITNO_NAME',header:'套件'
	        },
	        {dataIndex:'ORMTQT_TOTAL',header:'合计'
	        	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
//	            	 if(record.get("ORMTQT_TP_YXGS")!=record.get("ORMTQT_TOTAL")){
//	        			metaData.tdStyle = 'color:red;background-color:#FFFF66;' ;
//	        		} else {
	        			metaData.tdStyle = 'background-color:#CD9B9B;' ;
//	        		}
	            	 return value;
            	}
	        }
      ];
      var fields=[
		'ORMTNO','BRADNO','SPCLNO','SPTYNO','SPSENO','SAMPNM','SUITNO','ORMTQT_TOTAL'
	  ];
      

      for(var i=0;i<me.initColumns.length;i++){
      	var initColumn=me.initColumns[i];
      	var bw_qy_field=initColumn.ORGNO+"_ORMTQT";
      	me.columns.push( {header:initColumn.ORGNM,columns:[
      		{
      		 	header:'数量',
      		 	dataIndex:bw_qy_field
      		 	,width: 80
      		 	,editor: {
	                xtype: 'numberfield',
	                allowDecimals:false,
	                minValue :0,
	                selectOnFocus:true 
	            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	if(window.stat==0){
	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	}
	            	 return value;
            	}
      		 }
      	]});

      	 fields.push(bw_qy_field);

      }
     
      delete me.initColumns;
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields: fields,
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/bw/queryQyData.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    //extraParams:me.params,
			    //paramsAsJson:true,
			    writer:{
			    	type:'json'
			    },
			    reader:{
					type:'json',
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
	  this.cellEditing.on("beforeedit",function(editor, context){
	  	if(window.stat==0){
		  		return true;
		} else {
			return false;
		}

      });
	  this.cellEditing.on("edit",function(editor, context){
	  	
	  var record=context.record;
		  	var grid=context.grid;
		  	var field =context.field ;
		  	var value=context.value;	
		  	var originalValue=context.originalValue;
		  	
		  	//判断新的值加进去后，会不会超过 总公司的统配数量
		  	var ORMTQT_TOTAL=record.get("ORMTQT_TOTAL");
		  	//alert(originalValue);
		  	if(typeof(originalValue)=='undefined'){
		  		originalValue=0;
		  	}
		  	ORMTQT_TOTAL=ORMTQT_TOTAL-originalValue+value;
		 
		  	//return;
		  	//获取定制的column
		  	//var column_dz=grid.getHeaderContainer().getHeaderAtIndex(context.colIdx-1);
		  	//alert(column_dz.getInitialConfig ("dataIndex") );
		  	var ordorg=field.split("_")[0];
	//alert(ordorg);
		  	Ext.Ajax.request({
							url:Ext.ContextPath+'/bw/qy_updateOrmtqt.do',
							params:{
								ordorg:ordorg,
								ormtno:record.get("ORMTNO"),
								sampno:record.get("SAMPNO"),
								bradno:record.get("BRADNO"),
								spclno:record.get("SPCLNO"),
								suitno:record.get("SUITNO"),
								ormtqt:value
							},
							success:function(){
								
								//修改合计的值
								
								record.set("ORMTQT_TOTAL",ORMTQT_TOTAL);
								record.commit();
							}
							
			});
	  	
	  });
       
      me.callParent();
	}
	
});
