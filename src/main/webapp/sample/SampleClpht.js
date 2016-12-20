Ext.define("y.sample.SampleClpht",{
	extend:"Ext.data.Model",
	fields:[
		{name:'id',type:'string'},
		{name:'clppno',type:'string'},
		{name:'photso',type:'int'},
		{name:'photnm',type:'string'},
		{name:'photms',type:'string'},
		{name:'imgnm',type:'string'}
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
			read:Ext.ContextPath+'/sampleClpht/load.do',
			//load : Ext.ContextPath+'/sampleClpht/load.do',
			create:Ext.ContextPath+'/sampleClpht/create.do',
			update:Ext.ContextPath+'/sampleClpht/update.do',
			destroy:Ext.ContextPath+'/sampleClpht/destroy.do'
		}
	}
});