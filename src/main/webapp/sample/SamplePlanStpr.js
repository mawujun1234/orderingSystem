Ext.define("y.sample.SamplePlanStpr",{
	extend:"Ext.data.Model",
	fields:[
		{name:'suitno',type:'string'},
		{name:'spftpr',type:'float'},
		{name:'sprtpr',type:'float'},
		{name:'spplrd',type:'float'},
		{name:'plctpr',type:'float'},
		{name:'plspno',type:'string'},
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
			read:Ext.ContextPath+'/samplePlanStpr/query.do',
			load : Ext.ContextPath+'/samplePlanStpr/load.do',
			create:Ext.ContextPath+'/samplePlanStpr/create.do',
			update:Ext.ContextPath+'/samplePlanStpr/update.do',
			destroy:Ext.ContextPath+'/samplePlanStpr/destroy.do'
		}
	}
});