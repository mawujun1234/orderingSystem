/**
 * 职位可以访问的组织单元
 */
Ext.define('y.org.OrgAccessTree', {
    extend: 'Ext.tree.Panel',
    displayField:'name',
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:false,
	       	nodeParam :'parent_id',//传递到后台的数据，默认是node
	       	//model:'y.org.Org',
	       	fields:['id','name','leaf','orgno','remark','type'],
			root: {
			    expanded: true,
			    name:"雅戈尔" 
			},
			proxy:{
				type:'ajax',
				url:Ext.ContextPath+'/org/queryOrgAccess.do',
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
		
		//只存放需要重新添加或取消的节点
		me.menuTreeChecked_node=[];
		function checkParent(node) {
					var parent = node.parentNode;
					if(parent.isRoot()){
						return;	
					}
					if (!parent)
						return;
					var checkP = false;
					parent.cascadeBy(function(n) {
								if (n != parent) {
									if (n.get('checked') == true) {
										checkP = true;
										return;
									}
								}
							});
					if(parent.get('checked')!=checkP){
						me.menuTreeChecked_node.push(parent.get("id"));
					}
					parent.set('checked', checkP);
					checkParent(parent);
		}
		me.on("checkchange",function(node, checked){
				me.menuTreeChecked_node.push(node.get("id"));
				if(node.isLeaf()){
//					Ext.Ajax.request({
//						url:Ext.ContextPath+"/org/checkOrgNodes.do",
//						params:{
//							orgno:node.get("id"),
//							orgty:node.get("orgty"),
//							checked:checked,
//							position_id:window.selected_position.get("id")
//						},
//						success:function(){
//							
//						}
//					});
//					me.menuTreeChecked_node=[];
				} else {
					Ext.Msg.confirm('消息','是否要选择/取消下面所有的菜单',function(btn){
						if(btn=='yes'){
							node.cascadeBy(function (n) { 
								if (n != node && !n.isRoot()) {
									//me.menuTreeChecked_node.push(n.get("id"));
									n.set('checked', checked); 
								}
							});
							//只收集父节点的id，这样后台只需要添加子节点的就可以了
							checkParent(node);
						} else {
							checkParent(node);
						}
						
						Ext.Ajax.request({
							url:Ext.ContextPath+"/org/checkOrgNodes.do",
							params:{
//								ids:me.menuTreeChecked_node,
//								checked:checked,
//								role_id:window.selected_role.get("id")
								parent_orgnos:me.menuTreeChecked_node,
								orgno:node.get("id"),
								orgty:node.get("orgty"),
								checked:checked,
								position_id:window.selected_position.get("id")
							},
							success:function(){
								
							}
						});
						me.menuTreeChecked_node=[];
					})
				}
		});
       
		me.callParent(arguments);
    }
   
});
