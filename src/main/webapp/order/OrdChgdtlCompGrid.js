Ext.define('y.order.OrdChgdtlCompGrid',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.order.OrdChgdtl'
	],
	columnLines :true,
	stripeRows:true,
	viewConfig:{
		enableTextSelection:true
	},
	selModel: {
          selType: 'checkboxmodel'
          //,checkOnly:true
    },
	initComponent: function () {
      var me = this;
      me.columns=[
      	{xtype: 'rownumberer'},
      	{dataIndex:'compnm',header:'营销公司'
        },
        
        {dataIndex:'ormtqt',header:'订货数量'
		},
		{dataIndex:'orchqt',header:'调整数量'
		},
		{dataIndex:'ormtqt_orchqt',header:'调整后数量'
		}
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:false,
			model: 'y.order.OrdChgdtl',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/ordChgdtl/query4comp.do',
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
     
	  me.dockedItems.push({
	  		xtype: 'toolbar',
	  		dock:'top',
		  	items:[{
				text: '刷新',
				itemId:'reload',
				disabled:me.disabledAction,
				handler: function(btn){
					var grid=btn.up("grid");
					grid.getStore().reload();
					
					grid.nextSibling("#ordChgdtlRegnGrid").mask();
				},
				iconCls: 'icon-refresh'
			},{
				text: '取消',
				itemId:'cancel',
				
				handler: function(btn){
					btn.up("grid").cancel();
				},
				iconCls: 'icon-remove'
			}]
		});

       
      me.callParent();
	},
	cancel:function(){
		var me=this;
		var records=me.getSelectionModel().getSelection();
		if(records==null || records.length==0){
			Ext.Msg.alert("消息","请先选择营销公司!");
			return;
		}
		Ext.Msg.confirm("消息","确认取消!",function(aa){
			if(aa=='yes'){
			
			
		//alert(records.length);
		var compnos=[];
		for(var i=0;i<records.length;i++){
			//alert(records[i].get("compno"));
			compnos.push(records[i].get("compno"));
		}
		var extraParams=me.getStore().getProxy().extraParams;
		
		//alert(compnos.length);
		//return;
		Ext.Ajax.request({
	  		url:Ext.ContextPath+'/ordChgdtl/cancel.do',
	  		params:{
	  			ormtno:extraParams["params['ormtno']"],
	  			sampno:extraParams["params['sampno']"],
	  			suitno:extraParams["params['suitno']"],
	  			bradno:extraParams["params['bradno']"],
	  			spclno:extraParams["params['spclno']"],
	  			compnos:compnos
	  		},
	  		success:function(response){
	  			me.getStore().reload();
	  			me.nextSibling("#ordChgdtlRegnGrid").mask();
	  			//me.nextSibling("#ordChgdtlRegnGrid").getStore().reload();
	  		}
	  	});
	  	}//if(aa=='yes'){
		})
	}
});
