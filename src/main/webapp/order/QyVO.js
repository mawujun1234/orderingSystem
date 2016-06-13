Ext.define("y.order.QyVO",{
	extend:"Ext.data.Model",
	fields:[
		{name:'mtorno',type:'string'},
		{name:'channo',type:'string'},
		{name:'ordorg',type:'string'},
		{name:'sptyno',type:'string'},
		{name:'spseno',type:'string'},
		{name:'plspno',type:'string'},
		{name:'sampno',type:'string'},
		{name:'packqt',type:'string'},
		{name:'suitno',type:'string'},
		{name:'ormtqs',type:'int'},
		{name:'ormtqt',type:'int'},
		{name:'orstat',type:'int'},
		{name:'ormark',type:'string'},
		{name:'spftpr',type:'string'},
		{name:'sprtpr',type:'string'},
		{name:'channo_name',type:'string'},
		{name:'ordorg_name',type:'string'},
		{name:'ormtqt_zhes',type:'float'},
		{name:'ormtqs_zhes',type:'float'}
		
//		{name:'orgno',type:'string'},
//		{name:'ormtno',type:'string'},
//		{name:'ortyno',type:'string'},
//		{name:'bradno',type:'string'},
//		{name:'spclno',type:'string'}
	]
//	proxy:{
//		type:'ajax',
//		actionMethods: { read: 'POST' },
//		timeout :600000,
//		headers:{ 'Accept':'application/json;'},
//		writer:{
//			type:'json',
//			writeRecordId:true,
//			writeAllFields:true
//		},
//		reader:{
//			type:'json',
//			root:'root',
//			successProperty:'success',
//			totalProperty:'total'		
//		},
//		api:{
//			read:Ext.ContextPath+'/qyVO/load.do',
//			//load : Ext.ContextPath+'/qyVO/load.do',
//			create:Ext.ContextPath+'/qyVO/create.do',
//			update:Ext.ContextPath+'/qyVO/update.do',
//			destroy:Ext.ContextPath+'/qyVO/destroy.do'
//		}
//	}
});