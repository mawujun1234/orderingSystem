Ext.define("y.sample.SampleColth",{
	extend:"Ext.data.Model",
	fields:[
		{name:'spcotn',type:'string'},
		{name:'spsuno',type:'string'},
		{name:'prsuno',type:'string'},
		{name:'sptapa',type:'float',defaultValue: 0},
		{name:'spacry',type:'float',defaultValue: 0},
		{name:'spclbd',type:'float',defaultValue: 0},
		{name:'spnwpr',type:'float'},
		{name:'contqt',type:'int'},
		{name:'contam',type:'float'},
		{name:'contpr',type:'float'},
		{name:'ctdwdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'acsyam',type:'int',defaultValue: 0},
		{name:'spctpr',type:'float'},
		{name:'sprmk',type:'string'},
		{name:'spctst',type:'int'},
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
			rootProperty:'root',
			successProperty:'success',
			totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/sampleColth/query.do',
			load : Ext.ContextPath+'/sampleColth/load.do',
			create:Ext.ContextPath+'/sampleColth/create.do',
			update:Ext.ContextPath+'/sampleColth/update.do',
			destroy:Ext.ContextPath+'/sampleColth/destroy.do'
		}
	}
});