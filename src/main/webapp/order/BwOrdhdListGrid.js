Ext.define('y.order.BwOrdhdListGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     //'y.pubsize.OrdSzrt'
	],
	columnLines :true,
	stripeRows:true,
//	selModel: {
//          selType: 'checkboxmodel'
//          ,checkOnly:true
//    },
    viewConfig:{
    	enableTextSelection:true
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
      	{dataIndex:'ORGNM',header:'订货单位'
        },
        {dataIndex:'SAMPNM',header:'样衣编号'
        },
        {dataIndex:'SUITNO_NAME',header:'套件'
        },
         {dataIndex:'ORSZQT',header:'数量'
        },
        {dataIndex:'SPTYNO_NAME',header:'小类'
        },
        {dataIndex:'SPSENO_NAME',header:'季节'
        },
        {dataIndex:'VERSNO_NAME',header:'版型'
        },
        {dataIndex:'PRODNM',header:'货号'
        }
       
      ];
      var fields=[
		{name:'ORGNM',type:'string'},
		{name:'SAMPNO',type:'string'},
		{name:'SAMPNM',type:'string'},
		{name:'SUITNO_NAME',type:'string'},
		{name:'SPTYNO_NAME',type:'string'},
		{name:'SPSENO_NAME',type:'string'},
		{name:'VERSNO_NAME',type:'string'},
		{name:'PRODNM',type:'string'},
		{name:'ORSZQT',type:'string'}
	  ];

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields: fields,
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/bwOrdmt/queryBwSizeMgrList.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			   // extraParams:me.params,
			    //paramsAsJson:true,
			    writer:{
			    	type:'json'
			    },
			    reader:{
					type:'json'	
				}
			}
	  });
	 
//	  me.dockedItems=[];
//	  
//	  me.reload_index=0;
//	  me.dockedItems.push({
//	  		xtype: 'toolbar',
//	  		dock:'top',
//		  	items:[{
//		  		itemId:'ordmtcombo',
//				xtype:'ordmtcombo',
//				allowBlank: false,
//				width:160,
//	            afterLabelTextTpl: Ext.required,
//				listeners:{
//					select:function( combo, record, eOpts ) {	
//						var ormmno=combo.nextSibling("#ormmno");
//						ormmno.getStore().getProxy().extraParams={
//							ormtno:record.get("ormtno")
//						};
//						ormmno.getStore().reload();
//						
//
//						var grid=combo.up("grid");
//						grid.reload_index++;
//    					grid.reload();	
//					}
//				}
//			},{
//				xtype:'combobox',
//				itemId:'ormmno',
//				//labelWidth:50,
//				width:150,
//				//fieldLabel: '',
//				emptyText:'请选择子批次',
//			    store: Ext.create('Ext.data.Store', {
//				    fields: ['ormmno'],
//				    autoLoad:false,
//				    proxy:{
//				    	type: 'ajax',
//					    url : Ext.ContextPath+'/bwOrdmt/getOrmmnos.do',
//					    headers:{ 'Accept':'application/json;'},
//					    actionMethods: { read: 'POST' },
//					    writer:{
//					    	type:'json'
//					    },
//					    reader:{
//							type:'json'	
//						}
//				    },
//				    listeners:{
//				    	load:function(myStore){
//				    		if(myStore.getCount( ) >0){
//						 		var r=myStore.getAt(0);//第一行是无
//						 		var combo=me.down("#ormmno");
//						 		combo.select( r );
//						 		combo.fireEvent("select", combo, r);
//						 	}
//				    	}
//				    }
//				}),
//				listeners:{
//				    select:function( combo, record, eOpts){	
//				    	var grid=combo.up("grid");
//				    	grid.reload_index++;
//    					grid.reload();	
//				    }
//				},
//			    queryMode: 'local',
//			    displayField: 'ormmno',
//			    valueField: 'ormmno'
//			}]
//	  });
//	  
//	  me.dockedItems.push({
//	  		xtype: 'toolbar',
//	  		dock:'top',
//		  	items:[{
//		        fieldLabel: '品牌',
//		        itemId: 'bradno',
//		        labelWidth:40,
//		        width:160,
//	            allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            //value:'Y',
//	            selFirst:true,
//	            blankText:"品牌不允许为空",
//		        xtype:'pubcodecombo',
//		        tyno:'1',
//		        listeners:{
//		        	select:function( combo, record, eOpts ) {
//
//		        	}	
//		        }
//		    },{
//		        fieldLabel: '大类',
//		        itemId: 'spclno',
//		        labelWidth:40,
//		        width:120,
//	             selFirst:false,
//		        xtype:'pubcodecombo',
//		        tyno:'0',
//		        listeners:{
//		        	select:function( combo, record, eOpts ) {
//		        	}	
//		        }
//		    }]
//	  });
//	  
//	   me.dockedItems.push({
//	  		xtype: 'toolbar',
//	  		dock:'top',
//		  	items:[{
//				text: '查询',
//				itemId:'reload',
//				handler: function(btn){
//					var grid=btn.up("grid");
//    				grid.reload();			
//				},
//				iconCls: 'icon-refresh'
//			},{
//		  		text: '确认',
//				handler: function(btn){
//					Ext.Msg.confirm("消息","根据 <span style='color:red;'>品牌+大类</span> 进行确认，确认后不允许修改规格数据，是否确认?",function(btnid){
//						if(btnid=='yes'){
//							var extraParams=extraParams;
//							var grid=btn.up("grid");
//							var records=grid.getSelectionModel().getSelection();
//							if(records==null || records.length==0){
//								return;
//							}
//							var mmornoes=[];
//							for(var i=0;i<records.length;i++){
//								mmornoes.push(records[i].get("mmorno"));
//							}
//							Ext.Ajax.request({
//								url:Ext.ContextPath+"/bwOrdmt/approve.do",
//								params:{mmornoes:mmornoes},
//								//method:'GET',
//								success:function(response){
//								 	//console.log(response.responseText);
//									var obj=Ext.decode(response.responseText);
//									if(obj.success==false){
//										Ext.Msg.alert("消息",obj.msg);
//										return;
//									}
//									grid.getStore().reload();
//								}
//							});
//						}
//					});
//				},
//				iconCls: ' icon-download-alt'
//		  	}]
//	  });
       
      me.callParent();
	}
//	reload:function(){
//		var me=this;//alert(me.reload_index);
//		if(me.reload_index>1){
//			var grid=me;
//    		grid.getStore().getProxy().extraParams=grid.getParams();
//			grid.getStore().reload();
//		}
//		
//	},
//	getParams:function(){
//		var toolbars=this.getDockedItems('toolbar[dock="top"]');
//		var params={
//			"ormtno":toolbars[0].down("#ordmtcombo").getValue(),
//			"ormmno":toolbars[0].down("#ormmno").getValue(),
//			"bradno":toolbars[1].down("#bradno").getValue(),
//			"spclno":toolbars[1].down("#spclno").getValue()
//		};
//		return params;
//	}
	
});
