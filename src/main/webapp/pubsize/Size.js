Ext.define("y.pubsize.Size",{
	extend:"Ext.data.Model",
	fields:[
		{name:'sizeno',type:'string'},
		{name:'sizenm',type:'string'},
		{name:'ormtno',type:'string'},
		{name:'sizety',type:'string'},
		{name:'ysizety',type:'string'},
		{name:'ysizeno',type:'string'},
		{name:'szbrad',type:'string'},
		{name:'szclno',type:'string'},
		{name:'sizeso',type:'int'},
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
			read:Ext.ContextPath+'/size/load.do',
			//load : Ext.ContextPath+'/size/load.do',
			create:Ext.ContextPath+'/size/create.do',
			update:Ext.ContextPath+'/size/update.do',
			destroy:Ext.ContextPath+'/size/destroy.do'
		}
	}
});