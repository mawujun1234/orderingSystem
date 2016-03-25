<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)>
Ext.define('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Grid',{
	extend:'Ext.grid.Panel',
	requires: [
	     '${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}'
	],
	columnLines :true,
	stripeRows:true,

	initComponent: function () {
      var me = this;
     <#-----------------------------------------生成列--------------------------------- ----->
      me.columns=[
      <#list propertyColumns as propertyColumn>	
      	<#if propertyColumn.hidden==false>
		<#if propertyColumn.jsType=='date'>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.property_label!propertyColumn.property}',xtype: 'datecolumn',   format:'Y-m-d'}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='bool'>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.property_label!propertyColumn.property}',xtype: 'checkcolumn'}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int' >
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.property_label!propertyColumn.property}',xtype: 'numbercolumn', format:'0',align : 'right',}<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='float'>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.property_label!propertyColumn.property}',xtype: 'numbercolumn', format:'0.00',align : 'right',}<#if propertyColumn_has_next>,</#if>
		<#else>
		{dataIndex:'${propertyColumn.property}',text:'${propertyColumn.property_label!propertyColumn.property}'}<#if propertyColumn_has_next>,</#if>
		</#if>
		</#if><#-- <#if propertyColumn.hidden==false> -->
	  </#list>
      ];
      
      <#-----------------------------------------生成store--------------------------------- ----->
      <#if extenConfig.extjs_grid_store_userModel==true>
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			model: '${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}',
			autoLoad:true
	  });
	  <#else>
	  me.store=Ext.create('Ext.data.Store',{
			autoSync:false,
			pageSize:50,
			autoLoad:true,
			model: '${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}',
			proxy:{
				type: 'ajax',
			    url : Ext.ContextPath+'/${simpleClassNameFirstLower}/query.do',
			    headers:{ 'Accept':'application/json;'},
			    actionMethods: { read: 'POST' },
			    extraParams:{limit:50},
			    reader:{
					type:'json',
					rootProperty:'root',
					successProperty:'success',
					totalProperty:'total'		
				}
			}
	  });
	  </#if>
	  
	  <#-----------------------------------------是否启用page--------------------------------- ----->
      me.dockedItems= [{
	        xtype: 'pagingtoolbar',
	        store: me.store,  
	        dock: 'bottom',
	        displayInfo: true
	  }];
	  
	  <#-----------------------------------------生成工具栏--------------------------------- ----->
	  <#if extenConfig.extjs_grid_createDelUpd_button=true>
	  me.tbar=	[{
			text: '新增',
			itemId:'create',
			handler: function(btn){
				me.onCreate();
			},
			iconCls: 'icon-plus'
		},{
		    text: '更新',
		    itemId:'update',
		    handler: function(){
		    	me.onUpdate();
				
		    },
		    iconCls: 'icon-pencil'
		},{
		    text: '删除',
		    itemId:'destroy',
		    handler: function(){
		    	me.onDelete();    
		    },
		    iconCls: 'icon-remove'
		},{
			text: '刷新',
			itemId:'reload',
			disabled:me.disabledAction,
			handler: function(btn){
				var grid=btn.up("grid");
				grid.getStore().reload();
			},
			iconCls: 'icon-refresh'
		}]
	  </#if>
       
      me.callParent();
	},
	onCreate:function(){
    	var me=this;
		
    	var form=Ext.create('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Form',{});

		var child=Ext.create('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}',{

		});
		child.set("id",null);
		form.loadRecord(child);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'新增',
    		modal:true,
    		width:400,
    		height:300,
    		items:[form],
    		listeners:{
    			close:function(){
    				me.getStore().reload();
    			}
    		}
    	});
    	win.show();
    },
    
     onUpdate:function(){
    	var me=this;
		
    	var form=Ext.create('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Form',{});
    	
    	var node=me.getSelectionModel( ).getLastSelected();
    	if(node==null){
    		Ext.Msg.alert("提醒","请选择一行数据!");
    		return;
    	}

		form.loadRecord(node);
		
    	var win=Ext.create('Ext.window.Window',{
    		layout:'fit',
    		title:'更新',
    		modal:true,
    		width:400,
    		height:300,
    		items:[form]
    	});
    	win.show();
    },
    
    onDelete:function(){
    	var me=this;
    	var node=me.getSelectionModel( ).getLastSelected( );

		if(!node){
		    Ext.Msg.alert("消息","请先选择一行数据");	
			return;
		}
		var parent=node.parentNode;
		Ext.Msg.confirm("删除",'确定要删除吗?', function(btn, text){
				if (btn == 'yes'){
					node.erase({
					    failure: function(record, operation) {
			            	me.getStore().reload();
					    },
					    success:function(){
					    	me.getStore().reload();
					    }
				});
			}
		});
    }
});
