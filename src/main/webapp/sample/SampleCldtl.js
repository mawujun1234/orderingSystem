Ext.define("y.sample.SampleCldtl",{
	extend:"Ext.data.Model",
	fields:[
		{name:'clppno',type:'string'},
		{name:'sampno',type:'string'},
		{name:'clspno',type:'int'},
		{name:'clspmk',type:'string'},
		
		{name:'sampnm',type:'string'},
		{name:'imgnm',type:'string'},
		{name:'sampno_imgnm',type:'string'},
		{name:'sampno_thumb',type:'string'}
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
			type:'json'
			///rootProperty:'root',
			//successProperty:'success',
			//totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/sampleCldtl/load.do',
			//load : Ext.ContextPath+'/sampleCldtl/load.do',
			create:Ext.ContextPath+'/sampleCldtl/create.do',
			update:Ext.ContextPath+'/sampleCldtl/update.do',
			destroy:Ext.ContextPath+'/sampleCldtl/destroy.do'
		}
	}
});