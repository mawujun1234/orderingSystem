Ext.define("y.pubsize.OrdSzrt",{
	extend:"Ext.data.Model",
	fields:[
		{name:'ormtno',type:'string'},
		{name:'ordorg',type:'string'},
		{name:'sizegp',type:'string'},
		{name:'sizeno',type:'string'},
		{name:'bradno',type:'string'},
		{name:'spclno',type:'string'},
		{name:'spseno',type:'string'},
		{name:'versno',type:'string'},
		{name:'sizety',type:'string'},
		{name:'szrate',type:'float'}
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
			type:'json',
			root:'root',
			successProperty:'success',
			totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/ordSzrt/load.do',
			//load : Ext.ContextPath+'/ordSzrt/load.do',
			create:Ext.ContextPath+'/ordSzrt/create.do',
			update:Ext.ContextPath+'/ordSzrt/update.do',
			destroy:Ext.ContextPath+'/ordSzrt/destroy.do'
		}
	}
});