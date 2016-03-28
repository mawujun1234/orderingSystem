Ext.define("y.permission.Role",{
	extend:"Ext.data.Model",
	fields:[
		{name:'roleType',type:'string'},
		{name:'name',type:'string'},
		{name:'remark',type:'string'},
		{name:'id',type:'string'},
		{name:'parent_id',type:'string'}
	],
	proxy:{
		type:'ajax',
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		writer:{
			type:'json',
			writeAllFields:true
		},
		api:{
			read:Ext.ContextPath+'/role/query.do',
			load : Ext.ContextPath+'/role/load.do',
			create:Ext.ContextPath+'/role/create.do',
			update:Ext.ContextPath+'/role/update.do',
			destroy:Ext.ContextPath+'/role/destroy.do'
		}
	}
});