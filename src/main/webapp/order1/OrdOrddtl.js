Ext.define("y.order1.OrdOrddtl",{
	extend:"Ext.data.Model",
	fields:[
		{name:'ormtno',type:'string'},
		{name:'suitno',type:'string'},
		{name:'ordseq',type:'string'},
		{name:'ordate',type:'string'},
		{name:'orodqt',type:'int'},
		{name:'ormark',type:'string'},
		{name:'sampno',type:'string'},
		{name:'rgsp',type:'string'},
		{name:'rgdt',type:'string'},
		{name:'lmsp',type:'string'},
		{name:'lmdt',type:'string'}
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
			read:Ext.ContextPath+'/ordOrddtl/load.do',
			//load : Ext.ContextPath+'/ordOrddtl/load.do',
			create:Ext.ContextPath+'/ordOrddtl/create.do',
			update:Ext.ContextPath+'/ordOrddtl/update.do',
			destroy:Ext.ContextPath+'/ordOrddtl/destroy.do'
		}
	}
});