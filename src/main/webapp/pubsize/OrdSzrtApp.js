Ext.require("y.pubsize.OrdSzrt");
Ext.require("y.pubsize.OrdSzrtGrid");
//Ext.require("y.pubsize.OrdSzrtForm");
Ext.onReady(function(){
	var grid=Ext.create('y.pubsize.OrdSzrtGrid',{
		region:'center',
		title:'规格比例设置'
	});
	
	var tabpanel=Ext.create('Ext.tab.Panel',{
		items:[grid],
		load_num:0,
		dockedItems:[{
	  		xtype: 'toolbar',
	  		dock:'top',
	  		//enableOverflow:true,
		  	items:[{
		  		fieldLabel: '订货会',
		  		labelWidth:50,
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				listeners:{
					select:function( combo, record, eOpts ) {
						tabpanel.load_num++;
						reloadSizegp();
					}
				}
			},{
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
					select:function( combo, record, eOpts ) {
						tabpanel.load_num++;
						reloadSizegp();
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
	             selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'0',
		        listeners:{
		        	select:function( combo, record, eOpts ) {
//		        		var sptyno=combo.nextSibling("#sptyno");
//		        		sptyno.reload(record.get("itno"));
//		        		
//		        		var spseno=combo.nextSibling("#spseno");
//		        		spseno.reload(record.get("itno"));
		        		
		        		tabpanel.load_num++;
						reloadSizegp();
		        	}	
		        }
		    },{
		        fieldLabel: '小类',
		        itemId: 'sptyno',
		        labelWidth:40,
		        hidden:true,
		        width:140,
	            autoLoad:false,
	            // selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'2'
		    },
			{
		        fieldLabel: '系列',
		        itemId: 'spseno',
		        labelWidth:40,
		        width:160,
	            autoLoad:false,
		        xtype:'combo',
		         queryMode: 'local',
		        tyno:'5',
		        queryMode: 'remote',
		        displayField: 'itnm',
			    valueField: 'itno',
			    store: {
			    	autoLoad:false,
				    fields: ['itno', 'itnm'],
				    proxy:{
				    	type:'ajax',
				    	//extraParams:{szbrad:'sjs'},
				    	url:Ext.ContextPath+'/pubCodeType/querySpseno4Ordmt.do'
				    }
				}
		    }]
		},{
	  		xtype: 'toolbar',
	  		dock:'top',
	  		//enableOverflow:true,
		  	items:[{
		        fieldLabel: '版型',
		        labelWidth:50,
		        itemId: 'versno',
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"版型不允许为空",
	            xtype:'combo',
		        tyno:'13',
		        queryMode: 'local',
		        displayField: 'itnm',
			    valueField: 'itno',
			    store: {
			    	autoLoad:false,
				    fields: ['itno', 'itnm'],
				    proxy:{
				    	type:'ajax',
				    	//extraParams:{szbrad:'sjs'},
				    	url:Ext.ContextPath+'/pubCodeType/queryVersno4Ordmt.do'
				    }
				}
		    },{
		        fieldLabel: '规格范围',
		        labelWidth:70,
		        itemId: 'sizegp',
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"规格范围不允许为空",
	            selectOnFocus:true,
		        xtype:'combobox',
		        queryMode: 'local',
				editable:true,
		        selectOnFocus:true,
				forceSelection:true,
			    displayField: 'sizenm',
			    valueField: 'sizeno',
			    store: {
			    	autoLoad:false,
				    fields: ['sizeno', 'sizenm'],
				    proxy:{
				    	type:'ajax',
				    	//extraParams:{szbrad:'sjs'},
				    	url:Ext.ContextPath+'/pubSize/queryPRDSZTY4Ordmt.do'
				    }
				}
		        
		    },{
		    	xtype:'button',
		    	text:'查询',
		    	handler: function(btn){
					//var grid=btn.up("grid");
					//grid.getStore().reload();
		    		show();
				},
				iconCls: 'icon-refresh'
		    }]
		}]
	});

	function reloadSizegp(){
		//console.log(tabpanel.load_num);
		
		if(tabpanel.load_num>=3){
			var spseno=tabpanel.down("#spseno");
			spseno.clearValue( );
			spseno.getStore().getProxy().extraParams={
				bradno:tabpanel.down("#bradno").getValue(),
				spclno:tabpanel.down("#spclno").getValue(),
				ormtno:tabpanel.down("#ordmtcombo").getValue() 
			};
			spseno.getStore().reload();
			
			
			var versno=tabpanel.down("#versno");
			versno.clearValue( );
			versno.getStore().getProxy().extraParams={
				bradno:tabpanel.down("#bradno").getValue(),
				spclno:tabpanel.down("#spclno").getValue(),
				ormtno:tabpanel.down("#ordmtcombo").getValue() 
			};
			versno.getStore().reload();
			
			var sizegp=tabpanel.down("#sizegp");
			sizegp.clearValue( );
			sizegp.getStore().getProxy().extraParams={
				szbrad:tabpanel.down("#bradno").getValue(),
				szclno:tabpanel.down("#spclno").getValue(),
				ormtno:tabpanel.down("#ordmtcombo").getValue() 
			};
			sizegp.getStore().reload();
		}
	}
	
	function show(){
		//获取规格范围中的规格
		var sizegp=tabpanel.down("#sizegp");
		Ext.Ajax.request({
			url:Ext.ContextPath+"/ordSzrt/queryColumns.do",
			params:{
				sizegp:sizegp.getValue()
			},
			succes:function(response){
			 console.log(response.responseText);
			
			}
		});
	}
	
	
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'fit',
		items:[tabpanel]
	});



});