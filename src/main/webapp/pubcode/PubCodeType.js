Ext.define("y.pubcode.PubCodeType",{
	extend:"Ext.data.Model",
	idProperty:'tyno',
	fields:[
		{name:'tyno',type:'string'},
		{name:'tynm',type:'string'},
		{name:'tyst',type:'bool'},
		{name:'rgsp',type:'string'},
		{name:'rgdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'lmsp',type:'string'},
		{name:'lmdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'ftyno',type:'string'},
		{name:'tyms',type:'string'},
		{name:'tymk',type:'string'}
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
		api:{
			read:Ext.ContextPath+'/pubCodeType/query.do',
			load : Ext.ContextPath+'/pubCodeType/load.do',
			create:Ext.ContextPath+'/pubCodeType/create.do',
			update:Ext.ContextPath+'/pubCodeType/update.do',
			destroy:Ext.ContextPath+'/pubCodeType/destroy.do'
		}
	}
});