Ext.define("y.sample.SampleDesignSizegp",{
	extend:"Ext.data.Model",
	fields:[
		{name:'suitno',type:'string'},
		{name:'sizegp',type:'string'},
		{name:'sampno',type:'string'}
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
			read:Ext.ContextPath+'/sampleDesignSizegp/load.do',
			//load : Ext.ContextPath+'/sampleDesignSizegp/load.do',
			create:Ext.ContextPath+'/sampleDesignSizegp/create.do',
			update:Ext.ContextPath+'/sampleDesignSizegp/update.do',
			destroy:Ext.ContextPath+'/sampleDesignSizegp/destroy.do'
		}
	}
});