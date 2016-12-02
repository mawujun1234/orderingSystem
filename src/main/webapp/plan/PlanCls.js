Ext.define("y.plan.PlanCls",{
	extend:"Ext.data.Model",
	fields:[
		{name:'ormtno',type:'string'},
		{name:'bradno',type:'string'},
		{name:'spclno',type:'string'},
		{name:'spseno',type:'string'},
		{name:'ordqty',type:'int'}
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
			read:Ext.ContextPath+'/planCls/load.do',
			//load : Ext.ContextPath+'/planCls/load.do',
			create:Ext.ContextPath+'/planCls/create.do',
			update:Ext.ContextPath+'/planCls/update.do',
			destroy:Ext.ContextPath+'/planCls/destroy.do'
		}
	}
});