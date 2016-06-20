Ext.define("y.pubcode.PubPlanrt",{
	extend:"Ext.data.Model",
	fields:[
		{name:'bradno',type:'string'},
		{name:'spclno',type:'string'},
		{name:'sptyno',type:'string'},
		{name:'planrt',type:'int'}
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
			read:Ext.ContextPath+'/pubPlanrt/load.do',
			//load : Ext.ContextPath+'/pubPlanrt/load.do',
			create:Ext.ContextPath+'/pubPlanrt/create.do',
			update:Ext.ContextPath+'/pubPlanrt/update.do',
			destroy:Ext.ContextPath+'/pubPlanrt/destroy.do'
		}
	}
});