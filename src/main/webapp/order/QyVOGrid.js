Ext.define('y.order.QyVOGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.order.QyVO',
	     'y.order.QyVONewForm'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
        {dataIndex:'channo_name',header:'渠道类型'
        },
		{dataIndex:'ordorg_name',header:'订货单位'
        },
		{dataIndex:'sptyno_name',header:'小类'
        },
		{dataIndex:'spseno_name',header:'系列'
        },
		{dataIndex:'plspnm',header:'企划样衣编号'
        },
		{dataIndex:'sampnm',header:'设计样衣编号'
        },
		{dataIndex:'packqt',header:'包装要求'
        },
		{dataIndex:'suitno_name',header:'套件',width:70
        },
		{dataIndex:'ormtqs',header:'原始数量',width:75
        },
		{dataIndex:'ormtqt',header:'平衡数量',width:75,
			renderer:function(value, metaData, record, rowIndex, colIndex, store){
				if(record.get("orstat")==0 || record.get("orstat")==1){
					metaData.tdStyle = 'color:red;background-color:#98FB98;' ;
				}
            	 
            	 return value;
            },editor: {
                xtype: 'numberfield',
                allowDecimals:false,
                selectOnFocus:true 
            }
        },
		{dataIndex:'ormark',header:'备注'
        }
      ];
      
      this.cellEditing = new Ext.grid.plugin.CellEditing({  
            clicksToEdit : 1  
      });  
	  this.plugins = [this.cellEditing];
	  
	  this.cellEditing.on("beforeedit",function(editor, context){
	   	var record=context.record;
	   	if(record.get("orstat")!=0 && record.get("orstat")!=1){
			return false;
		}
	  });
	  this.cellEditing.on("edit",function(editor, context){
	  	var record=context.record;
	  	var grid=context.grid;
	  	var field =context.field ;
	  	var value=context.value;
	  	

	  	Ext.Ajax.request({
						url:Ext.ContextPath+'/ord/quVO/updateOrmtqt.do',
						params:{
							mtorno:record.get("mtorno"),
							sampno:record.get("sampno"),
							suitno:record.get("suitno"),
							ormtqt:value
						},
						success:function(){
							record.commit();
							//me.getStore().reload();
						}
						
					});
	  	
	  });
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.order.QyVO',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ord/quVO/queryQyVO.do',
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
				load:function(store,records){
					if(records && records.length>0){
						if(!me.createNew_btn){
							var toolbars=me.getDockedItems('toolbar[dock="top"]');
							var createNew=toolbars[2].down("#createNew");
							var updateApprove=toolbars[2].down("#updateApprove");
							
							me.createNew_btn=createNew;
							me.updateApprove_btn=updateApprove;
						}
						
						if(records[0].get("orstat")==0 || records[0].get("orstat")==4 ){
							me.createNew_btn.enable();
							me.updateApprove_btn.enable();
						} else {
							me.createNew_btn.disable();
							me.updateApprove_btn.disable();
						}
						
					}
				}
			}
	  });

	  me.dockedItems=[];
      me.dockedItems.push({
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ordmtcombo',
				xtype:'ordmtcombo',
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				listeners:{
					select:function( combo, record, eOpts ) {	
						var ordorg=combo.nextSibling("#ordorg");
		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			ormtno:record.get("ormtno")
		        		});
		        		//ordorg.getStore().reload();
					}
				}
			},{
		  		fieldLabel: '营销公司',
		  		labelWidth:60,
		  		width:160,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'yxgsno',
				xtype:'orgcombo',
				listeners:{
					select:function( combo, record, eOpts ) {
						var regioncombo=combo.nextSibling("#qyno");
		        		regioncombo.reload(record.get("orgno"));
					}
				}
			},{
		  		fieldLabel: '区域',
		  		labelWidth:45,
		  		width:170,
		  		allowBlank: false,
	            afterLabelTextTpl: Ext.required,
		  		itemId:'qyno',
				xtype:'orgcombo',
				autoLoad:false,
				listeners:{
					select:function( combo, record, eOpts ) {
		        		
						var ordorg=combo.nextSibling("#ordorg");
		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			qyno:record.get("orgno")
		        		});
		        		ordorg.getStore().reload();
					}
				}
			},{
				fieldLabel: '渠道类型',
				labelWidth:65,
				width:150,
				allowBlank: false,
	            afterLabelTextTpl: Ext.required,
				itemId: 'channo',
				xtype:'channocombo',
				value:'QY',
				listeners:{
					select:function( combo, record, eOpts ) {
		        		
						var ordorg=combo.nextSibling("#ordorg");
		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
		        			channo:record.get("channo")
		        		});
		        		ordorg.getStore().reload();
					}
				}
			 },{
				fieldLabel: '订货单位',
				labelWidth:65,
				width:170,
//				allowBlank: false,
//	            afterLabelTextTpl: Ext.required,
//	            blankText:"订货单位不允许为空",
				itemId: 'ordorg',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'orgnm',
			    valueField: 'orgno',
			    store: {
				    fields: ['orgno', 'orgnm'],
				    autoLoad:false,
				    proxy: {
				        type: 'ajax',
				        url: Ext.ContextPath+'/ord/queryOrdorg.do'
				    }
				},
	            hidden:false,
				xtype:'combobox'
			 }]
	  });
	  
	   me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		  		itemId:'ortyno',
				xtype:'ordtycombo',
				labelWidth:65,
				//selFirst:true,
				width:150,
				listeners:{
					select:function( combo, record, eOpts ) {
//						var ordorg=combo.nextSibling("#ordorg");
//		        		ordorg.getStore().getProxy().extraParams=Ext.apply(ordorg.getStore().getProxy().extraParams,{
//		        			ortyno:record.get("ortyno")
//		        		});
//		        		ordorg.getStore().reload();
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
		        		var toolbar=combo.up("toolbar");
		        		var suitno=toolbar.down("#suitno");
		        		suitno.changeBradno(record.get("itno"));
		        		suitno.getStore().reload();
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
		        		var sptyno=combo.nextSibling("#sptyno");
		        		sptyno.reload(record.get("itno"));
		        		
		        		var spseno=combo.nextSibling("#spseno");
		        		spseno.reload(record.get("itno"));
		        	}	
		        }
		    },{
		        fieldLabel: '小类',
		        itemId: 'sptyno',
		        labelWidth:40,
		        width:140,
	            autoLoad:false,
		        xtype:'pubcodecombo',
		        tyno:'2'
		    },
			{
		        fieldLabel: '系列',
		        itemId: 'spseno',
		        labelWidth:40,
		        width:160,
	            autoLoad:false,
		        xtype:'pubcodecombo',
		        tyno:'5'
		    },{
		        fieldLabel: '套件',
		        itemId: 'suitno',
		        //selFirst:true,
	            xtype:'pubcodecombo',
		        tyno:'3',
		        labelWidth:45,
				width:150
		    }]
	   });
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
		        fieldLabel: '样衣编号',
		        labelWidth:65,
		        itemId: 'sampno',
	            xtype:'textfield'
		    },{
				fieldLabel: '订货状态',
				labelWidth:65,
		        width:165,
				itemId: 'orstat',
				queryMode: 'local',
				editable:false,
				forceSelection:true,
			    displayField: 'name',
			    valueField: 'id',
			    store: {
				    fields: ['id', 'name'],
				    data:[{id:'0',name:'编辑中'},{id:'1',name:'审批中'},{id:'2',name:'大区审批通过'},{id:'3',name:'总部审批通过'},{id:'4',name:'退回'}]
				},
	            hidden:false,
				xtype:'combobox'
			 },{
				text: '查询',
				itemId:'reload',
				handler: function(btn){
					var grid=btn.up("grid");
    				grid.getStore().getProxy().extraParams=grid.getParams();
					grid.getStore().reload();
				},
				iconCls: 'icon-refresh'
			},{
				text: '新增未定样衣',
				itemId:'createNew',
				handler: function(btn){
					me.createNew();
				},
				iconCls: 'icon-plus'
			},{
			    text: '提交审批',
			    itemId:'updateApprove',
			    handler: function(btn){
			    	var toolbars=btn.up("grid").getDockedItems('toolbar[dock="top"]');
			    	var ordorg_name=toolbars[0].down("#ordorg").getRawValue();
			    	if(!ordorg_name){
			    		ordorg_name="当前区域下的<span style='color:red;'>所有订货单位</span>";
			    	} else {
			    		ordorg_name="<span style='color:red;'>"+ordorg_name+"</span>订货单位";
			    	}
					Ext.Msg.confirm("消息","是否确定要提交审批!"+ordorg_name+"和当前选中的<span style='color:red;'>‘品牌+大类’</span>的数据都会被提交!",function(btn){
						if(btn=='yes'){
							me.updateApprove();
						}
					});
			    },
			    iconCls: 'icon-edit'
			}]
		});

       
      me.callParent();
	},
	getParams:function(){
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var params={
			"params['ormtno']":toolbars[0].down("#ordmtcombo").getValue(),
			"params['yxgsno']":toolbars[0].down("#yxgsno").getValue(),
			"params['qyno']":toolbars[0].down("#qyno").getValue(),
			"params['channo']":toolbars[0].down("#channo").getValue(),	
			"params['ordorg']":toolbars[0].down("#ordorg").getValue(),
			
			"params['ortyno']":toolbars[1].down("#ortyno").getValue(),
			"params['bradno']":toolbars[1].down("#bradno").getValue(),
			"params['spclno']":toolbars[1].down("#spclno").getValue(),
			"params['sptyno']":toolbars[1].down("#sptyno").getValue(),
			"params['spseno']":toolbars[1].down("#spseno").getValue(),
			"params['suitno']":toolbars[1].down("#suitno").getValue(),
			"params['sampno']":toolbars[2].down("#sampno").getValue(),
			"params['orstat']":toolbars[2].down("#orstat").getValue()
		};
		return params;
	},
	createNew:function(){
		var me=this;
		var toolbars=this.getDockedItems('toolbar[dock="top"]');
		var ordorg=toolbars[0].down("#ordorg").getValue();
		if(!ordorg){
			Ext.Msg.alert("消息","请先选择一个订货单位!");
			return;
		}
//		var ortyno=toolbars[0].down("#ortyno").getValue();
//		if(!ortyno){
//			ortyno='DZ';
//		}
		
		
    	var qyVONewForm=Ext.create('y.order.QyVONewForm',{
    		params:{
    			ordorg:ordorg,
	    		ortyno:'DZ',
	    		channo:toolbars[0].down("#channo").getValue(),
	    		ormtno:toolbars[0].down("#ordmtcombo").getValue()
    		}
    	});
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		closeAction:'hide',
    		items:[qyVONewForm],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    },
    updateApprove:function(){
    	var me=this;
    	var toolbars=this.getDockedItems('toolbar[dock="top"]');
    	var params={
    		ormtno:toolbars[0].down("#ordmtcombo").getValue(),
    		qyno:toolbars[0].down("#qyno").getValue(),
			channo:toolbars[0].down("#channo").getValue(),
    		ordorg:toolbars[0].down("#ordorg").getValue(),
    		bradno:toolbars[1].down("#bradno").getValue(),
    		spclno:toolbars[1].down("#spclno").getValue()
    		
    	};
    	Ext.Ajax.request({
			url:Ext.ContextPath+'/ord/qyVO/updateApprove.do',
			params:params,
			method:'POST',
			success:function(response){
				var obj=Ext.decode(response.responseText);
				if(obj.success==false){
					Ext.Msg.alert("消息",obj.msg);
					return;
				}
				Ext.Msg.alert("消息","提交成功!");
				//button.up('window').close();
				me.getStore().reload();
			}
		});
    }
   
    
});