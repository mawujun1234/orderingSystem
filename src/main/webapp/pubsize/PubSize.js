Ext.define("y.pubsize.PubSize",{
	extend:"Ext.data.Model",
	fields:[
		{name:'sizety',type:'string'},
		{name:'sizeno',type:'string'},
		{name:'sizenm',type:'string'},
		{name:'szbrad',type:'string'},
		{name:'szclno',type:'string'},
		{name:'sizety1',type:'string'},
		{name:'sizeqt',type:'int'},
		{name:'sizemk',type:'string'},
		{name:'sizeso',type:'int'},
		{name:'sizest',type:'int'},
		{name:'szsast',type:'int'},
		{name:'rgsp',type:'string'},
		{name:'rgdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		{name:'lmsp',type:'string'},
		{name:'lmdt',type:'date', dateFormat: 'Y-m-d H:i:s'},
		
		{name:'sizest_name',type:'string'},
		{name:'szsast_name',type:'string'}
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
			rootProperty:'root',
			successProperty:'success',
			totalProperty:'total'		
		},
		api:{
			read:Ext.ContextPath+'/pubSize/load.do',
			//load : Ext.ContextPath+'/pubSize/load.do',
			create:Ext.ContextPath+'/pubSize/create.do',
			update:Ext.ContextPath+'/pubSize/update.do',
			destroy:Ext.ContextPath+'/pubSize/destroy.do'
		}
	}
});