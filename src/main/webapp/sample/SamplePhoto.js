Ext.define("y.sample.SamplePhoto",{
	extend:"Ext.data.Model",
	fields:[
		{name:'photnm',type:'string'},
		{name:'photms',type:'string'},
		{name:'imgnm',type:'string'},
		{name:'photst',type:'int'},
		{name:'rgsp',type:'string'},
		{name:'rgdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'lmsp',type:'string'},
		{name:'lmdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'id',type:'string'},
		{name:'sampno',type:'string'}
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
			root:'root',
			successProperty:'success',
			totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/samplePhoto/query.do',
			load : Ext.ContextPath+'/samplePhoto/load.do',
			create:Ext.ContextPath+'/samplePhoto/create.do',
			update:Ext.ContextPath+'/samplePhoto/update.do',
			destroy:Ext.ContextPath+'/samplePhoto/destroy.do'
		}
	}
});