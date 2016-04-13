Ext.define("y.ordmt.OrdmtSeason",{
	extend:"Ext.data.Model",
	fields:[
		{name:'lmsp',type:'string'},
		{name:'lmdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'ormtno',type:'string'},
		{name:'seasno',type:'string'}
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
			root:'root',
			successProperty:'success',
			totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/ordmtSeason/query.do',
			load : Ext.ContextPath+'/ordmtSeason/load.do',
			create:Ext.ContextPath+'/ordmtSeason/create.do',
			update:Ext.ContextPath+'/ordmtSeason/update.do',
			destroy:Ext.ContextPath+'/ordmtSeason/destroy.do'
		}
	}
});