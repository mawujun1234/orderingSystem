Ext.define("y.org.PositionType",{
	extend:"Ext.data.Model",
	fields:[
		{name:'name',type:'string'},
		{name:'remark',type:'string'},
		{name:'id',type:'string'}
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
			read:Ext.ContextPath+'/positionType/load.do',
			//load : Ext.ContextPath+'/positionType/load.do',
			create:Ext.ContextPath+'/positionType/create.do',
			update:Ext.ContextPath+'/positionType/update.do',
			destroy:Ext.ContextPath+'/positionType/destroy.do'
		}
	}
});