Ext.define("y.ordmt.OrdmtScde",{
	extend:"Ext.data.Model",
	fields:[
		{name:'mtstdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'mtfidt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'mtsttm',type:'date',dateFormat: 'H:i'},
		{name:'mtfitm',type:'date',dateFormat: 'H:i'},
		{name:'lmsp',type:'string'},
		{name:'lmdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'ormtno',type:'string'},
		{name:'orgty',type:'string'}
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
			read:Ext.ContextPath+'/ordmtScde/query.do',
			load : Ext.ContextPath+'/ordmtScde/load.do',
			create:Ext.ContextPath+'/ordmtScde/create.do',
			update:Ext.ContextPath+'/ordmtScde/update.do',
			destroy:Ext.ContextPath+'/ordmtScde/destroy.do'
		}
	}
});