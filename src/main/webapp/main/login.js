Ext.require("y.main.LoginWindow");
Ext.onReady(function(){
	var win=Ext.create('y.main.LoginWindow',{
		frame:true
	});
	win.show();
});