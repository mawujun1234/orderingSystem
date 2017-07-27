Ext.define('y.permission.UserGridQuery',{
	extend:'Ext.grid.Panel',
	requires: [
	     'y.permission.User'
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
		{dataIndex:'name',header:'姓名'
        },
		{dataIndex:'loginName',header:'登录名'
        },
//		{dataIndex:'pwd',header:'密码'
//        },
        {dataIndex:'state_name',header:'状态'
        },
        {dataIndex:'phone',header:'电话'
        },
        {dataIndex:'mobile',header:'手机'
        },
        {dataIndex:'email',header:'邮件'
        },
		{dataIndex:'remark',header:'备注'
        }
      ];
      

	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: 'y.permission.User',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/user/queryPage.do',
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
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  });
	  me.dockedItems.push({
	  	xtype: 'toolbar',
	  	dock:'top',
	  	//enableOverflow:true,
		items:[
			{
                xtype: 'textfield',
				itemId:'name',
                fieldLabel: '姓名',
                labelWidth:60,
                width:150,
                selectOnFocus:true 
            },
			{
                xtype: 'textfield',
				itemId:'loginName',
                fieldLabel: '登录名',
                labelWidth:60,
                width:150,
                selectOnFocus:true 
            },
	    	{
            	text:'查询',
            	iconCls:'icon-search',
            	handler:function(btn){
            		var grid=btn.up("grid");
	            	grid.getStore().getProxy().extraParams=Ext.apply(grid.getStore().getProxy().extraParams,{
						"params['name']":grid.down("#name").getValue(),
						"params['loginName']":grid.down("#loginName").getValue()
	                });
            		grid.getStore().reload();
            	}
            }
	  	]
	  });


       
      me.callParent();
	}
});
