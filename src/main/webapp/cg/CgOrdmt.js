Ext.define("y.cg.CgOrdmt",{
	extend:"Ext.data.Model",
	fields:[
		{name:'orcgno',type:'string'},
		{name:'orcgnm',type:'string'},
		{name:'ormtno',type:'string'},
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
			read:Ext.ContextPath+'/cgOrdmt/load.do',
			//load : Ext.ContextPath+'/cgOrdmt/load.do',
			create:Ext.ContextPath+'/cgOrdmt/create.do',
			update:Ext.ContextPath+'/cgOrdmt/update.do',
			destroy:Ext.ContextPath+'/cgOrdmt/destroy.do'
		}
	}
});