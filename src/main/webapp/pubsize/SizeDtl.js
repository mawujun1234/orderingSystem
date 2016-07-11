Ext.define("y.pubsize.SizeDtl",{
	extend:"Ext.data.Model",
	fields:[
		{name:'fszty',type:'string'},
		{name:'fszno',type:'string'},
		{name:'sizety',type:'string'},
		{name:'sizeno',type:'string'},
		{name:'ormtno',type:'string'}
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
			read:Ext.ContextPath+'/sizeDtl/load.do',
			//load : Ext.ContextPath+'/sizeDtl/load.do',
			create:Ext.ContextPath+'/sizeDtl/create.do',
			update:Ext.ContextPath+'/sizeDtl/update.do',
			destroy:Ext.ContextPath+'/sizeDtl/destroy.do'
		}
	}
});