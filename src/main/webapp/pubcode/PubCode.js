Ext.define("y.pubcode.PubCode",{
	extend:"Ext.data.Model",
	fields:[
		{name:'itno',type:'string'},
		{name:'itnm',type:'string'},
		{name:'itms',type:'string'},
		{name:'itmk',type:'string'},
		{name:'itso',type:'int'},
		//{name:'itst',type:'bool'},
		{name:'stat',type:'bool'},
		{name:'tyno',type:'string'},
		{name:'fitno',type:'string'},
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
			read:Ext.ContextPath+'/pubCode/load.do',
			//load : Ext.ContextPath+'/pubCode/load.do',
			create:Ext.ContextPath+'/pubCode/create.do',
			update:Ext.ContextPath+'/pubCode/update.do',
			destroy:Ext.ContextPath+'/pubCode/destroy.do'
		}
	}
});