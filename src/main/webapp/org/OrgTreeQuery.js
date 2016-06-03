/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('y.org.OrgTreeQuery', {
    extend: 'Ext.tree.Panel',
    requires:['y.org.Org'],
    displayField:'name',
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'parent_id',//传递到后台的数据，默认是node
	       	//model:'y.org.Org',
	       	fields:['id','name','leaf','orgno','remark','type'],
			root: {
			    expanded: true,
			    name:"雅戈尔" 
			},
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/org/queryOnlyOrg.do',
				actionMethods: { read: 'POST' },
				timeout :600000,
				headers:{ 'Accept':'application/json;'}
			
			}
//			listeners:{
//				beforeload:function(store,operation){
//					//var record=me.getSelectionModel( ).getSelection( );
//					//console.log(record);
//					//store.getProxy().extraParams=Ext.apply(store.getProxy().extraParams,{
//					//	parent_no:"root"//record.get("orgno")
//					//});
//				}
//			}
		});
//		me.initAction();
       
		me.callParent(arguments);
    }
   
});
