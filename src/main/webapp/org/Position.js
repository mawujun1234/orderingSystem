Ext.define("y.org.Position",{
	extend:"Ext.data.Model",
	fields:[
		{name:'name',type:'string'},
		{name:'remark',type:'string'},
		{name:'id',type:'string'},
		{name:'orgno',type:'string'},
		{name:'positionType_id',type:'string'}
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
		api:{
			read:Ext.ContextPath+'/position/load.do',
			//load : Ext.ContextPath+'/position/load.do',
			create:Ext.ContextPath+'/position/create.do',
			update:Ext.ContextPath+'/position/update.do',
			destroy:Ext.ContextPath+'/position/destroy.do'
		}
	}
});