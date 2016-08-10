Ext.define('y.order.TpYxgsGrid',{
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
			{dataIndex:'SPTYNO_NAME',header:'小类'
	        },
	        {dataIndex:'SPSENO_NAME',header:'系列'
	        },
	        {dataIndex:'SAMPNM',header:'订货样衣编号'
	        },
	        {dataIndex:'SUITNO_NAME',header:'套件'
	        },
	        {dataIndex:'PACKQT',header:'包装要求'
	        },
	        {dataIndex:'ORMTQT_TP_GSBB',header:'统配总数'
	        	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 return value;
            	}
	        },
	        {dataIndex:'ORMTQT_TOTAL',header:'合计'
	        	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	        		if(record.get("ORMTQT_TP_GSBB")!=record.get("ORMTQT_TOTAL")){
	        			metaData.tdStyle = 'color:red;background-color:#FFFF66;' ;
	        		} else {
	        			metaData.tdStyle = 'background-color:#CD9B9B;' ;
	        		}
	            	 
	            	 return value;
            	}
	        }
      ];
      var fields=[
		'ORMTNO','BRADNO','SPCLNO','SPTYNO','SPSENO','SAMPNM','SUITNO','PACKQT','ORMTQT_TP_GSBB'
	  ];
      

      for(var i=0;i<me.initColumns.length;i++){
      	var initColumn=me.initColumns[i];
      	var dz_field=initColumn.ORGNO+"_DZ";
      	var tp_field=initColumn.ORGNO+"_TP";
      	me.columns.push( {header:initColumn.ORGNM,columns:[
      		{
      		 	header:'订制',
      		 	dataIndex:dz_field
      		 	,width: 80
      		 },{
      		 	header:'统配',
      		 	dataIndex:tp_field
      		 	,width: 80
      		 	,editor: {
	                xtype: 'numberfield',
	                allowDecimals:false,
	                selectOnFocus:true 
	            },renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	if(window.stat==3){
	            		metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
	            	}
	            	
	            	 return value;
            	}
      		 }
      	]});
      	

      	 fields.push(dz_field);
      	 fields.push(tp_field);
      }
     
      delete me.initColumns;
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields: fields,
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/tp/tpYxgsQuery.do',
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
	  	if(window.stat==3){
	  		return true;
	  	} else {
	  		return false;
	  	}

      });
	  this.cellEditing.on("edit",function(editor, context){
	  		if(window.stat==0){
		  		return false;
		  	}
		  	var record=context.record;
		  	var grid=context.grid;
		  	var field =context.field ;
		  	var value=context.value;	
		  	var originalValue=context.originalValue;
		  	
			var PACKQT=record.get("PACKQT");
	
		  	//商品部录入统配总量，要求统配总量为包装要求的整数倍
		  	if(record.get("SUITNO")!='T02' && (value/PACKQT-parseInt(value/PACKQT))!=0){
		  		Ext.Msg.alert("消息","统配数量必须是包装数量的整数倍!");
		  		record.set(field,originalValue);
		  		return false;
		  	}
		  	//判断新的值加进去后，会不会超过 总公司的统配数量
		  	var ORMTQT_TOTAL=record.get("ORMTQT_TOTAL");
		  	//alert(originalValue);
		  	if(typeof(originalValue)=='undefined'){
		  		originalValue=0;
		  	}
		  	ORMTQT_TOTAL=ORMTQT_TOTAL-originalValue+value;
		 
		  	if(ORMTQT_TOTAL>record.get("ORMTQT_TP_GSBB")){
		  		Ext.Msg.alert("消息","合计超过了总公司统配总数!");
		  		record.set(field,originalValue);
		  		record.commit();
		  		return false;
		  	}
		  	//return;
		  	//获取定制的column
		  	//var column_dz=grid.getHeaderContainer().getHeaderAtIndex(context.colIdx-1);
		  	//alert(column_dz.getInitialConfig ("dataIndex") );
		  	var ordorg=field.split("_")[0];
		  	var column_dz=ordorg+"_DZ";
		  	//return;
		  	Ext.Ajax.request({
							url:Ext.ContextPath+'/tp/tpYxgs_updateOrmtqt_tp.do',
							params:{
								ordorg:ordorg,
								ormtno:record.get("ORMTNO"),
								sampno:record.get("SAMPNO"),
								bradno:record.get("BRADNO"),
								spclno:record.get("SPCLNO"),
								suitno:record.get("SUITNO"),
								ormtqs:record.get(column_dz),
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
