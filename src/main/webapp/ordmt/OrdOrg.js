Ext.define("y.ordmt.OrdOrg",{
	extend:"Ext.data.Model",
	fields:[
		{name:'ormtno',type:'string'},
		{name:'ordorg',type:'string'},
		{name:'channo',type:'string'},
		{name:'sztype',type:'int'},
		{name:'print',type:'int'}
	],
	proxy:{
		type:'ajax',
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		writer:{
			type:'json',
			writeRecordId:true,
			writeAllFields:true
		},
		reader:{
			type:'json',
			root:'root',
			successProperty:'success',
			totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/ordOrg/load.do',
			//load : Ext.ContextPath+'/ordOrg/load.do',
			create:Ext.ContextPath+'/ordOrg/create.do',
			update:Ext.ContextPath+'/ordOrg/update.do',
			destroy:Ext.ContextPath+'/ordOrg/destroy.do'
		}
	}
});