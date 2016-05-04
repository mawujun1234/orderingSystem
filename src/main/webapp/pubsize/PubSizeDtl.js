Ext.define("y.pubsize.PubSizeDtl",{
	extend:"Ext.data.Model",
	fields:[
		{name:'fszty',type:'string'},
		{name:'fszno',type:'string'},
		{name:'sizety',type:'string'},
		{name:'sizeno',type:'string'},
		{name:'sizeqt',type:'int'},
		{name:'sizemk',type:'string'},
		{name:'sizest',type:'int'},
		{name:'szsast',type:'int'},
		{name:'rgsp',type:'string'},
		{name:'rgdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'lmsp',type:'string'},
		{name:'lmdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		
		{name:'sizenm',type:'string'}
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
			read:Ext.ContextPath+'/pubSizeDtl/load.do',
			//load : Ext.ContextPath+'/pubSizeDtl/load.do',
			create:Ext.ContextPath+'/pubSizeDtl/create.do',
			update:Ext.ContextPath+'/pubSizeDtl/update.do',
			destroy:Ext.ContextPath+'/pubSizeDtl/destroy.do'
		}
	}
});