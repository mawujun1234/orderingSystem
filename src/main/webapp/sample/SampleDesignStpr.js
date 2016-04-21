Ext.define("y.sample.SampleDesignStpr",{
	extend:"Ext.data.Model",
	fields:[
		{name:'suitno',type:'string'},
		{name:'spftpr',type:'float'},
		{name:'sprtpr',type:'float'},
		{name:'plctpr',type:'float'},
		{name:'sampno',type:'string'},
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
			read:Ext.ContextPath+'/sampleDesignStpr/load.do',
			//load : Ext.ContextPath+'/sampleDesignStpr/load.do',
			create:Ext.ContextPath+'/sampleDesignStpr/create.do',
			update:Ext.ContextPath+'/sampleDesignStpr/update.do',
			destroy:Ext.ContextPath+'/sampleDesignStpr/destroy.do'
		}
	}
});