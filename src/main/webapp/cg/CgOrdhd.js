Ext.define("y.cg.CgOrdhd",{
	extend:"Ext.data.Model",
	fields:[
		{name:'orstat',type:'int'},
		{name:'isfect',type:'int'},
		{name:'orcgno',type:'string'},
		{name:'cgorno',type:'string'},
		{name:'bradno',type:'string'},
		{name:'spclno',type:'string'},
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
			read:Ext.ContextPath+'/cgOrdhd/load.do',
			//load : Ext.ContextPath+'/cgOrdhd/load.do',
			create:Ext.ContextPath+'/cgOrdhd/create.do',
			update:Ext.ContextPath+'/cgOrdhd/update.do',
			destroy:Ext.ContextPath+'/cgOrdhd/destroy.do'
		}
	}
});