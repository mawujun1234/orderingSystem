Ext.define("y.sample.SamplePlan",{
	extend:"Ext.data.Model",
	fields:[
		{name:'plspnm',type:'string'},
		{name:'bradno',type:'string'},
		{name:'spyear',type:'string'},
		{name:'spsean',type:'string'},
		{name:'spbseno',type:'string'},
		{name:'sprseno',type:'string'},
		{name:'spclno',type:'string'},
		{name:'sptyno',type:'string'},
		{name:'spseno',type:'string'},
		{name:'splcno',type:'string'},
		{name:'spbano',type:'string'},
		{name:'plgrno',type:'string'},
		{name:'spftpr',type:'float'},
		{name:'sprtpr',type:'float'},
		{name:'spplrd',type:'float'},
		{name:'plctpr',type:'float'},
		{name:'pldate',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'mldate',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'rgsp',type:'string'},
		{name:'rgdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'lmsp',type:'string'},
		{name:'lmdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'plspno',type:'string'},
		{name:'ormtno',type:'string'},
		{name:'plstat',type:'int'},
		{name:'plspst',type:'int'}
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
			read:Ext.ContextPath+'/samplePlan/query.do',
			load : Ext.ContextPath+'/samplePlan/load.do',
			create:Ext.ContextPath+'/samplePlan/create.do',
			update:Ext.ContextPath+'/samplePlan/update.do',
			destroy:Ext.ContextPath+'/samplePlan/destroy.do'
		}
	}
});