Ext.define("y.sample.SampleMate",{
	extend:"Ext.data.Model",
	fields:[
		{name:'sampno',type:'string'},
		{name:'mateso',type:'int'},
		{name:'mtsuno',type:'string'},
		{name:'mateno',type:'string'},
		{name:'mtbrad',type:'string'},
		{name:'mttype',type:'string'},
		{name:'mtcomp',type:'string'},
		{name:'yarmct',type:'string'},
		{name:'gramwt',type:'string'},
		{name:'aftrmt',type:'string'},
		{name:'width',type:'string'},
		{name:'mtpupr',type:'string'},
		{name:'mtcnqt',type:'string'},
		{name:'matest',type:'int'}
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
			read:Ext.ContextPath+'/sampleMate/query.do',
			load : Ext.ContextPath+'/sampleMate/load.do',
			create:Ext.ContextPath+'/sampleMate/create.do',
			update:Ext.ContextPath+'/sampleMate/update.do',
			destroy:Ext.ContextPath+'/sampleMate/destroy.do'
		}
	}
});