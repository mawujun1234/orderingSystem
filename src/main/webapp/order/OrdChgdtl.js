Ext.define("y.order.OrdChgdtl",{
	extend:"Ext.data.Model",
	fields:[
		{name:'orchqt',type:'int'},
		{name:'ormark',type:'string'},
		{name:'ormtno',type:'string'},
		{name:'ordorg',type:'string'},
		{name:'sampno',type:'string'},
		{name:'suitno',type:'string'},
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
			read:Ext.ContextPath+'/ordChgdtl/load.do',
			//load : Ext.ContextPath+'/ordChgdtl/load.do',
			create:Ext.ContextPath+'/ordChgdtl/create.do',
			update:Ext.ContextPath+'/ordChgdtl/update.do',
			destroy:Ext.ContextPath+'/ordChgdtl/destroy.do'
		}
	}
});