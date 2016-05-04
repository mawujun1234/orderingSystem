Ext.require("y.pubsize.PubSize");
Ext.require("y.pubsize.PrdpGrid");
Ext.require("y.pubsize.PrdpForm");
Ext.onReady(function(){
	var store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			fields: ['bradno','spclno','bradno_name','spclno_name'],
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/pubSize/querySizegpBradnoSpclno.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50
			    	//"params['']"
			    },
			    reader:{
					type:'json'	
				}
			},
			listeners:{
				beforeload:function(store) {
					//var grid=me;//Ext.getCmp("sampleDesignGrid");
					
					var toolbars=grid.getDockedItems('toolbar[dock="top"]');
    				store.getProxy().extraParams={
    					bradno:toolbars[0].down("#bradno").getValue(),
    					spclno:toolbars[0].down("#spclno").getValue()
    				};
				}
			}
	  });
	var grid=Ext.create('Ext.grid.Panel',{
		region:'center',
		columnLines :true,
		stripeRows:true,
		viewConfig:{
			enableTextSelection:true
		},
		columns:[
			{xtype: 'rownumberer'},
			{dataIndex:'bradno_name',header:'品牌'
	        },
	        {dataIndex:'spclno_name',header:'大类'
	        }
		],
		store:store,
		dockedItems:[{
			xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		        fieldLabel: '品牌',
		        itemId: 'bradno',
		        labelWidth:40,
		        width:160,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            //value:'Y',
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
		        	select:function(combo){
		        		store.reload();
		        	}
		        }
		    },{
		        fieldLabel: '大类',
		        itemId: 'spclno',
		        labelWidth:40,
		        width:120,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"大类不允许为空",
	             selFirst:false,
		        xtype:'pubcodecombo',
		        tyno:'0'
//		        listeners:{
//		        	select:function( combo, record, eOpts ) {
//		        		var sptyno=combo.nextSibling("#sptyno");
//		        		sptyno.reload(record.get("itno"));
//		        		
//		        		var spseno=combo.nextSibling("#spseno");
//		        		spseno.reload(record.get("itno"));
//		        	}	
//		        }
		    },{
				text: '查询',
				itemId:'reload',
				//disabled:me.disabledAction,
				handler: function(btn){

    				//var grid=btn.up("grid");
					
					grid.getStore().reload();
					
				},
				iconCls: 'icon-refresh'
			}]
		}]
	});
	
	var sizegpStdszGrid=Ext.create('y.pubsize.SizegpStdszGrid',{
		title:'单规规格',	
		listeners:{
			render:function(){
				//sizegpStdszGrid.mask();
			}
		}
	});
	var sizegpPrdpkGrid=Ext.create('y.pubsize.SizegpPrdpkGrid',{
		title:'包装规格',
		listeners:{
			render:function(){
				//sizegpPrdpkGrid.mask();
			}
		}
	});
	
	var tabpanel=Ext.create('Ext.tab.Panel',{
		region:'east',
		split:true,
		utoRender:true,
		width:350,
		items:[sizegpStdszGrid,sizegpPrdpkGrid],
		listeners:{
			render:function(){
				tabpanel.mask();
			}
		}
	});
	
	grid.on("itemclick",function(view, record, item, index, e, eOpts){
		tabpanel.unmask();
		
		sizegpStdszGrid.getStore().getProxy().extraParams={
			szbrad:record.get("bradno"),
			szclno:record.get("spclno")
		};
		sizegpStdszGrid.getStore().reload();
		


		sizegpPrdpkGrid.getStore().getProxy().extraParams={
			szbrad:record.get("bradno"),
			szclno:record.get("spclno")
		};
		sizegpPrdpkGrid.getStore().reload();
	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,tabpanel]
	});



});