Ext.define("y.sample.SampleClhd",{
	extend:"Ext.data.Model",
	fields:[
		{name:'clppno',type:'string'},
		{name:'clppnm',type:'string'},
		{name:'ormtno',type:'string'},
		{name:'bradno',type:'string'},
		{name:'spbano',type:'string'},
		{name:'clppmk',type:'string'},
		{name:'clppst',type:'int'},
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
			type:'json'
			///rootProperty:'root',
			//successProperty:'success',
			//totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/sampleClhd/load.do',
			//load : Ext.ContextPath+'/sampleClhd/load.do',
			create:Ext.ContextPath+'/sampleClhd/create.do',
			update:Ext.ContextPath+'/sampleClhd/update.do',
			destroy:Ext.ContextPath+'/sampleClhd/destroy.do'
		}
	}
});