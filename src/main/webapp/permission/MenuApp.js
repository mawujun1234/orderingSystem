Ext.require("y.permission.Menu");
//Ext.require("y.permission.MenuGrid");
Ext.require("y.permission.MenuTree");
Ext.require("y.permission.MenuForm");
Ext.onReady(function(){

	var tree=Ext.create('y.permission.MenuTree',{
		title:'菜单树',
		width:400,
		split:true,
		collapsible : true,
		region:'west'
	});

	var form=Ext.create('y.permission.MenuForm',{
		region:'center',
		split: true,
		//collapsible: true,
		title:'表单',
		listeners:{
			saved:function(){
				grid.getStore().reload();
			}
		}
	});
	

//	grid.form=form;
//	form.grid=grid;
//	grid.on('itemclick',function(view,record,item,index){
//		//var basicForm=form.getForm();
//		form.loadRecord(record);
//	});
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[tree,form]
	});

});