Ext.define('y.pubcode.PubCodeGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.pubcode.PubCode'
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
		{dataIndex:'itno',header:'代码'
        },
		{dataIndex:'itnm',header:'类型名称'
        },
		{dataIndex:'itso',header:'排序'
		},
		{dataIndex:'itst',header:'状态',xtype: 'checkcolumn'		
            ,listeners:{
				checkchange:function( checkcolumn, rowIndex, checked, eOpts ){
					var grid=checkcolumn.up("grid");
					//console.log(grid);
					var record=grid.getStore().getAt(rowIndex);
					record.set('itst',checked?1:0);
					record.save();
				}
			}
		},
		{dataIndex:'stat',header:'当季状态',xtype: 'checkcolumn'	
            ,listeners:{
				checkchange:function( checkcolumn, rowIndex, checked, eOpts ){
					var grid=checkcolumn.up("grid");
					//console.log(grid);
					var record=grid.getStore().getAt(rowIndex);
					record.set('stat',checked?1:0);
					record.save();
				}
			}
		},
		{dataIndex:'itms',header:'描述'
        },
		{dataIndex:'itmk',header:'备注'
        }
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: 'y.pubcode.PubCode',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/pubCode/queryList.do',
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

	  me.dockedItems=[];
//      me.dockedItems.push({
//	        xtype: 'pagingtoolbar',
//	        store: me.store,  
//	        dock: 'bottom',
//	        displayInfo: true
//	  });
	  
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		itemId:'toolbar',
	  		dock:'top',
		  	items:[{
				text: '查询',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					var fitno_combo=grid.down("#toolbar").down("#pubcode_parent");
					if(fitno_combo){
						grid.getStore().getProxy().extraParams=Ext.apply(grid.getStore().getProxy().extraParams,{
							fitno:fitno_combo.getValue()
						});
					}
					
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			}]
		});
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
						url:Ext.ContextPath+'/pubCode/updateSt.do',
						params:{
							itno:record.get("itno"),
							tyno:record.get("tyno"),
							itst:record.get("itst"),
							stat:record.get("stat")
						},
						success:function(){
							//me.getStore().reload();
						}
						
					});
	  	
	  });
       
      me.callParent();
	},
	createParentCombo:function(tyno,tynm){
		//console.log(tyno+"===="+tynm);
		var me=this;
		var combo=Ext.create('Ext.form.field.ComboBox', {
			fieldLabel: tynm,
			labelWidth:50,
		    editable:false,
			forceSelection:true,
			id:'pubcode_parent',
			displayField: 'itnm',
			valueField: 'itno',
			queryMode:'local',
			store:Ext.create('Ext.data.Store',{
				fields: ['itno', 'itnm'],
				autoLoad:true,
				proxy: {    
				    type: 'ajax',
				    extraParams:{
				    	tyno:tyno
				    },
				    url: Ext.ContextPath+'/pubCode/query4Combo.do',
				    reader: {
				        type: 'json'
				        //rootProperty: '${propertyColumn.property}'
				    }
				},
				listeners:{
					load:function(store){
						var r=store.getAt(1);//第一行是无
				 		combo.select( r );
				 		combo.fireEvent("select", combo, r);
						me.getStore().getProxy().extraParams=Ext.apply(me.getStore().getProxy().extraParams,{
							fitno:r.get("itno")
						});
						me.getStore().reload();
					}
				}
				
			})
		   
		});
		this.down("#toolbar").insert(0,combo);
		combo.getStore().reload();

		return combo;
	},
	removeParentCombo:function(){
		this.down("#toolbar").remove("pubcode_parent",true);
	}
});
