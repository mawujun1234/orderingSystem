Ext.define("y.permission.Menu",{
	extend:"Ext.data.Model",
	fields:[
		{name:'name',type:'string'},
		{name:'code',type:'string'},
		{name:'menuType',type:'string'},
		{name:'leaf',type:'bool'},
		{name:'createDate',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'url',type:'string'},
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
			read:Ext.ContextPath+'/menu/query.do',
			load : Ext.ContextPath+'/menu/load.do',
			create:Ext.ContextPath+'/menu/create.do',
			update:Ext.ContextPath+'/menu/update.do',
			destroy:Ext.ContextPath+'/menu/destroy.do'
		}
	}
});