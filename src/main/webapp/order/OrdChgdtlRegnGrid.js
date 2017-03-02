Ext.define('y.order.OrdChgdtlRegnGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.order.OrdChgdtl'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
      	{dataIndex:'qynm',header:'区域'
        },
        
        {dataIndex:'ormtqt',header:'订货数量'
		},
		{dataIndex:'orchqt',header:'调整数量',
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				//if(record.get("orstat")==0 || record.get("orstat")==1){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
				//}

            	 return value;
            },editor: {
                xtype: 'numberfield',
                allowDecimals:false,
                selectOnFocus:true 
            }
		},
		{dataIndex:'ormark',header:'备注',
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				//if(record.get("orstat")==0 || record.get("orstat")==1){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
				//}

            	 return value;
            },editor: {
                xtype: 'textfield',
                //allowDecimals:false,
                selectOnFocus:true 
            }
		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.order.OrdChgdtl',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ordChgdtl/query4qy.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });
	  
	  this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  
//	  this.cellEditing.on("beforeedit",function(editor, context){
//	   	var record=context.record;
//	   	//console.log(record.get("orstat"));
//	   	if(record.get("orstat")!=0 && record.get("orstat")!=4){//alert(1);
//			return false;
//		}
//	  });
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	var extraParams=grid.getStore().getProxy().extraParams;
	  	
	  	Ext.Ajax.request({
	  		url:Ext.ContextPath+'/ordChgdtl/updateValue.do',
	  		params:{
	  			ormtno:extraParams["params['ormtno']"],
	  			ordorg:record.get("qyno"),
	  			sampno:extraParams["params['sampno']"],
	  			suitno:extraParams["params['suitno']"],
	  			field:field,
	  			value:value
	  		},
	  		success:function(response){
	  			record.commit();
	  		}
	  	});
	  });

	  me.dockedItems=[];
     
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '刷新',
				itemId:'reload',
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});

       
      me.callParent();
	}
});
