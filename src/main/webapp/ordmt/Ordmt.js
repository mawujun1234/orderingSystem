Ext.define("y.ordmt.Ordmt",{
	extend:"Ext.data.Model",
	fields:[
		{name:'ormtno',type:'string'},
		{name:'ormtnm',type:'string'},
		{name:'ormtsn',type:'string'},
		{name:'pryear',type:'int'},
		{name:'mtstdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'mtfidt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'ormtst',type:'string'},
		{name:'ormtfg',type:'string'},
		{name:'ormtmk',type:'string'},
		{name:'rgsp',type:'string'},
		{name:'rgdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'lmsp',type:'string'},
		{name:'lmdt',type:'date', dateFormat: 'Y-m-d H:i:s'}
	],
	proxy:{
		type:'ajax',
		actionMethods: { read: 'POST' },
		timeout :600000,
		headers:{ 'Accept':'application/json;'},
		writer:{
			type:'json',
			writeRecordId:false,
			writeAllFields:true
		},
		reader:{
			type:'json',
			rootProperty:'root',
			successProperty:'success',
			totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/ordmt/query.do',
			load : Ext.ContextPath+'/ordmt/load.do',
			create:Ext.ContextPath+'/ordmt/create.do',
			update:Ext.ContextPath+'/ordmt/update.do',
			destroy:Ext.ContextPath+'/ordmt/destroy.do'
		}
	}
});