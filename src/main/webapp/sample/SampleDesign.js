Ext.define("y.sample.SampleDesign",{
	extend:"Ext.data.Model",
	fields:[
		{name:'sampno',type:'string'},
		{name:'sampnm',type:'string'},
		{name:'plspno',type:'string'},
		{name:'versno',type:'string'},
		{name:'photno',type:'string'},
		{name:'stseno',type:'string'},
		{name:'desgno',type:'string'},
		{name:'buspno',type:'string'},
		{name:'spmtno',type:'string'},
		{name:'gustno',type:'string'},
		{name:'colrno',type:'string'},
		{name:'pattno',type:'string'},
		{name:'stylno',type:'string'},
		{name:'stylgp',type:'string'},
		{name:'sexno',type:'string'},
		{name:'slveno',type:'string'},
		{name:'suitty',type:'string'},
		{name:'desp',type:'string'},
		{name:'sizegp',type:'string'},
		{name:'packqt',type:'int'},
		{name:'spltmk',type:'int'},
		{name:'print',type:'int'},
		
		{name:'sampst',type:'int'},
		{name:'spstat',type:'int'},
		{name:'rgsp',type:'string'},
		{name:'rgdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'lmsp',type:'string'},
		{name:'lmdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		
		{name:'plspnm',type:'string'}
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
			type:'json'	
		},
		api:{
			read:Ext.ContextPath+'/sampleDesign/load.do',
			//load : Ext.ContextPath+'/sampleDesign/load.do',
			create:Ext.ContextPath+'/sampleDesign/create.do',
			update:Ext.ContextPath+'/sampleDesign/update.do',
			destroy:Ext.ContextPath+'/sampleDesign/destroy.do'
		}
	}
});