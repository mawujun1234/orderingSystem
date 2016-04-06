Ext.define("y.org.Org",{
	extend:"Ext.data.Model",
	fields:[
		{name:'orgno',type:'string'},
		{name:'orgnm',type:'string'},
		{name:'orgsn',type:'string'},
		{name:'orgty',type:'string'},
		{name:'chancl',type:'string'},
		{name:'orgst',type:'string'}
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
			read:Ext.ContextPath+'/org/query.do',
			load : Ext.ContextPath+'/org/load.do',
			create:Ext.ContextPath+'/org/create.do',
			update:Ext.ContextPath+'/org/update.do',
			destroy:Ext.ContextPath+'/org/destroy.do'
		}
	}
});