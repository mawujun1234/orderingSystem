Ext.define("y.plan.PlanHd",{
	extend:"Ext.data.Model",
	fields:[
		{name:'ormtno',type:'string'},
		{name:'bradno',type:'string'},
		{name:'spclno',type:'string'},
		{name:'orgno',type:'string'},
		{name:'channo',type:'string'},
		{name:'plmtqt',type:'float'},
		{name:'plmtam',type:'float'},
		{name:'plstat',type:'int'}
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
			read:Ext.ContextPath+'/planHd/load.do',
			//load : Ext.ContextPath+'/planHd/load.do',
			create:Ext.ContextPath+'/planHd/create.do',
			update:Ext.ContextPath+'/planHd/update.do',
			destroy:Ext.ContextPath+'/planHd/destroy.do'
		}
	}
});