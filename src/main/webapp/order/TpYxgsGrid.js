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
			{dataIndex:'SPTYNO_NAME',header:'小类'
	        },
	        {dataIndex:'SPSENO_NAME',header:'系列'
	        },
	        {dataIndex:'SAMPNM',header:'设计样衣编号'
	        },
	        {dataIndex:'SUITNO_NAME',header:'套件'
	        },
	        {dataIndex:'PACKQT',header:'包装要求'
	        },
	        {dataIndex:'ORMTQT_ALL',header:'统配总数'
	        	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 return value;
            	}
	        },
	        {dataIndex:'ORMTQT_TOTAL',header:'合计'
	        	,renderer:function(value, metaData, record, rowIndex, colIndex, store){
	            	 metaData.tdStyle = 'background-color:#CD9B9B;' ;
	            	 return value;
            	}
	        }
      ];
      var fields=[
		'SPTYNO','SPSENO','SAMPNM','SUITNO','PACKQT','ORMTQT_ALL'
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
	            	metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
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
