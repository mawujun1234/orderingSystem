Ext.define('y.cg.CgOrdhdForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.cg.CgOrdhd'
	],
	
    frame: true,
    autoScroll : true,
	buttonAlign : 'center',
    bodyPadding: '5 5 0',


    defaults: {
        msgTarget: 'under',
        labelWidth: 75,
        labelAlign:'right',
        anchor: '90%'
    },
	initComponent: function () {
       var me = this;
       var orcgno=Ext.create('Ext.form.field.ComboBox',{
		    fieldLabel: '子批次编号',
		     labelWidth:80,
		    xtype:'combobox',
		    name:'orcgno',
		    allowBlank: false,
	        afterLabelTextTpl: Ext.required,
		    queryMode: 'local',
		    displayField: 'orcgnm',
		    valueField: 'orcgno',
		    store: Ext.create('Ext.data.Store',{
		    	autoLoad:true,
		    	proxy:{
					type: 'ajax',
				    url : Ext.ContextPath+'/cgOrdmt/queryAll.do',
				    headers:{ 'Accept':'application/json;'},
				    actionMethods: { read: 'POST' },
				    extraParams:{limit:50},
				    reader:{
						type:'json',//如果没有分页，那么可以把后面三行去掉，而且后台只需要返回一个数组就行了
						rootProperty:'root',
						successProperty:'success',
						totalProperty:'total'		
					}
				},
				listeners:{
					load:function(myStore){
						var r=myStore.getAt(0);
						orcgno.select( r );
				 		orcgno.fireEvent("select", orcgno, r);
					}
				
				}
		    })
		    
		});
       me.items= [
		{
	        fieldLabel: '订单状态',
	        name: 'orstat',
            allowDecimals:false,
            selectOnFocus:true,
            value:0,
	        xtype:'hidden'   
	    },
		{
	        fieldLabel: '有效状态',
	        name: 'isfect',
            allowDecimals:false,
            selectOnFocus:true,
            value:1,
	        xtype:'hidden'   
	    },{
		  		itemId:'ormtno',
				xtype:'ordmtcombo',
				name:'ormtno',
				fieldLabel: '订货会',
				labelWidth:80,
				//allowBlank: false,
            	//afterLabelTextTpl: Ext.required,
            	blankText:"订货会采购子批次号不允许为空",
				listeners:{
					select:function(combo , record , eOpts){
						//window.ordmt_record=record;
						//me.initReloadSampleDesign_index++;
						//me.reload();
						orcgno.getStore().getProxy().extraParams=Ext.apply(orcgno.getStore().getProxy().extraParams,{
							ormtno:record.get("ormtno")
						});
						orcgno.getStore().reload();
					}
				}
			},orcgno
	    ,
		{
		        fieldLabel: '品牌',
		        itemId: 'bradno',
		        name:'bradno',
		         labelWidth:80,
		        width:160,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            showBlank:false,
	            selFirst:true,
	            blankText:"品牌不允许为空",
		        xtype:'pubcodecombo',
		        tyno:'1',
		        listeners:{
					select:function(combo , record , eOpts){
						//me.initReloadSampleDesign_index++;
						//me.reload();
					}
				}
		    },{
		        fieldLabel: '大类',
		        name:'spclno',
		        itemId: 'spclno',
		         labelWidth:80,
		        width:120,
		        showBlank:false,
	            allowBlank: false,
	            afterLabelTextTpl: Ext.required,
	            blankText:"大类不允许为空",
	             selFirst:true,
		        xtype:'pubcodecombo',
		        tyno:'0',
		        listeners:{
		        	select:function( combo, record, eOpts ) {

		        	}	
		        }
		    },
		{
	        fieldLabel: '子批次订单号',
	        labelWidth:80,
	        name: 'cgorno',
//            allowBlank: false,
//	        afterLabelTextTpl: Ext.required,
//            selectOnFocus:true,
//	        xtype:'textfield'
	        xtype:'hiddenfield'
	    },
		
		{
	        fieldLabel: '创建人',
	        name: 'rgsp',
            hidden:true,
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
            fieldLabel: '创建日期',
            name: 'rgdt',
            hidden:true,
            editable:false,
            xtype: 'datefield',
            format: 'Y-m-d'   
        },
		{
	        fieldLabel: '修改人',
	        name: 'lmsp',
            hidden:true,
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
            fieldLabel: '修改日期',
            name: 'lmdt',
            hidden:true,
            editable:false,
            xtype: 'datefield',
            format: 'Y-m-d'   
        }
	  ];   
	  
	  
	  this.buttons = [];
		this.buttons.push({
			text : '保存',
			itemId : 'save',
			formBind: true, //only enabled once the form is valid
       		disabled: true,
			glyph : 0xf0c7,
			handler : function(button){
				var formpanel = button.up('form');
				formpanel.updateRecord();
				formpanel.getForm().getRecord().save({
					failure: function(record, operation) {
				    },
				    success: function(record, operation) {
						button.up('window').close();
				    }
				});			
				
				}
			},{
				text : '关闭',
				itemId : 'close',
				glyph : 0xf00d,
				handler : function(button){
					button.up('window').close();
				}
	    });
      me.callParent();
	}
});
