Ext.define("y.plan.PlanOrgdtlVO",{
	extend:"Ext.data.Model",
	fields:[
		{name:'plorno',type:'string'},
		{name:'spclno',type:'string'},
		{name:'sptyno',type:'string'},
		{name:'spseno',type:'string'},
		{name:'qymtqt',type:'float'},
		{name:'qymtam',type:'float'},
		{name:'txmtqt',type:'float'},
		{name:'txmtam',type:'float'},
		{name:'ormtno',type:'string'},
		{name:'ordorg',type:'string'},
		{name:'bradno',type:'string'},
		{name:'plstat',type:'int'},
		{name:'orgnm',type:'string'},
		{name:'spclnm',type:'string'},
		{name:'sptynm',type:'string'},
		{name:'spsenm',type:'string'}
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
			read:Ext.ContextPath+'/planOrgdtlVO/load.do',
			//load : Ext.ContextPath+'/planOrgdtlVO/load.do',
			create:Ext.ContextPath+'/planOrgdtlVO/create.do',
			update:Ext.ContextPath+'/planOrgdtlVO/update.do',
			destroy:Ext.ContextPath+'/planOrgdtlVO/destroy.do'
		}
	}
});