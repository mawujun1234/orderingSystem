Ext.define("y.cg.CgOrddtl",{
	extend:"Ext.data.Model",
	fields:[
		{name:'orszqt',type:'int'},
		{name:'cgorno',type:'string'},
		{name:'sampno',type:'string'},
		{name:'suitno',type:'string'},
		{name:'ormark',type:'string'},
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
			read:Ext.ContextPath+'/cgOrddtl/load.do',
			//load : Ext.ContextPath+'/cgOrddtl/load.do',
			create:Ext.ContextPath+'/cgOrddtl/create.do',
			update:Ext.ContextPath+'/cgOrddtl/update.do',
			destroy:Ext.ContextPath+'/cgOrddtl/destroy.do'
		}
	}
});