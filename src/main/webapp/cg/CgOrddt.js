Ext.define("y.cg.CgOrddt",{
	extend:"Ext.data.Model",
	fields:[
		{name:'orcgno',type:'string'},
		{name:'cgorno',type:'string'},
		{name:'sampno',type:'string'},
		{name:'suitno',type:'string'},
		{name:'mldate',type:'string'},
		{name:'pldate',type:'string'},
		{name:'pplace',type:'string'},
		{name:'ormtqt',type:'int'},
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
			type:'json'
			///rootProperty:'root',
			//successProperty:'success',
			//totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/cgOrddt/load.do',
			//load : Ext.ContextPath+'/cgOrddt/load.do',
			create:Ext.ContextPath+'/cgOrddt/create.do',
			update:Ext.ContextPath+'/cgOrddt/update.do',
			destroy:Ext.ContextPath+'/cgOrddt/destroy.do'
		}
	}
});