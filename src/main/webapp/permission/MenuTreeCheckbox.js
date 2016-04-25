/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('y.permission.MenuTreeCheckbox', {
    extend: 'Ext.tree.Panel',
    requires:['y.permission.Menu'],
    displayField:"name",
    initComponent: function () {
		var me = this;

        me.store = Ext.create('Ext.data.TreeStore', {
	       	autoLoad:true,
	       	nodeParam :'parent_id',//传递到后台的数据，默认是node
	       	model:'y.permission.Menu',
			root: {
			    expanded: true,
			    //checked:false,
			    name:"选择菜单"
			},
			proxy:{
				type: 'ajax',
				url:Ext.ContextPath+"/menu/query_checkbox.do"
			}
		});
		//me.initAction();
		
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
					Ext.Ajax.request({
						url:Ext.ContextPath+"/role/checkMenuNodes.do",
						params:{
							ids:me.menuTreeChecked_node,
							checked:checked,
							role_id:window.selected_role.get("id")
						},
						success:function(){
							
						}
					});
					me.menuTreeChecked_node=[];
				} else {
					Ext.Msg.confirm('消息','是否要选择/取消下面所有的菜单',function(btn){
						if(btn=='yes'){
							node.cascadeBy(function (n) { 
								if (n != node && !n.isRoot()) {
									me.menuTreeChecked_node.push(n.get("id"));
									n.set('checked', checked); 
								}
							});
							checkParent(node);
						} else {
							checkParent(node);
						}
						
						Ext.Ajax.request({
							url:Ext.ContextPath+"/role/checkMenuNodes.do",
							params:{
								ids:me.menuTreeChecked_node,
								checked:checked,
								role_id:window.selected_role.get("id")
							},
							success:function(){
								
							}
						});
						me.menuTreeChecked_node=[];
					})
				}
		});
       
		me.callParent(arguments);
    },
    /**
     * 查询这个角色选择了的菜单
     */
    query_checked_node:function(){
    	var me=this;
    	Ext.Ajax.request({
			url : Ext.ContextPath + "/menu/query_checked_node.do",
			params : {
				role_id : window.selected_role.get("id")
			},
			success : function(response) {
				var obj=Ext.decode(response.responseText);
				
				me.getRootNode().cascadeBy(function (node) {
					if(!node.isRoot()){
						if(obj[node.get("id")]==true){
							node.set('checked', true); 
						} else {
							node.set('checked', false); 
						}
					}	
				});
			}
		});
    }

    
    
});
