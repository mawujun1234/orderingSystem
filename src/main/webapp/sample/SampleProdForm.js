Ext.define('y.sample.SampleProdForm',{
	extend:'Ext.form.Panel',
	requires: [
	     'y.sample.SampleProd'
	],
	
    frame: true,
    autoScroll : true,
	buttonAlign : 'center',
    bodyPadding: '5 5 0',


    defaults: {
        msgTarget: 'under',
        labelWidth: 75,
        labelAlign:'right',
        anchor: '90%'
    },
	initComponent: function () {
       var me = this;
       me.items= [
		{
	        fieldLabel: '设计样衣编号',
	        name: 'sampnm',
            fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'textfield'
	    },
	    {
	        fieldLabel: '设计样衣编号',
	        name: 'sampno',
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '套件',
	        name: 'suitno',
	        xtype:'hiddenfield'
	    },
	    {
	        fieldLabel: '套件',
	        name: 'suitno_name',
            fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '序号',
	        name: 'prseqm',
            allowDecimals:false,
            selectOnFocus:true,
	        xtype:'numberfield'   
	    },
		{
	        fieldLabel: '货号代码',
	        name: 'prodno',
            selectOnFocus:true,
	        xtype:'hiddenfield'
	    },
		{
	        fieldLabel: '货号名称',
	        name: 'prodnm',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
//		{
//	        fieldLabel: '货号简称',
//	        name: 'prodds',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
		{
	        fieldLabel: '规格系列',
	        name: 'sizegp_name',
            fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'textfield'
	    },
	    {
	        fieldLabel: '规格系列',
	        name: 'sizegp',
	        xtype:'hiddenfield'
	    },
	    {
	        fieldLabel: '类别',
	        name: 'prtype',
	        xtype:'pubcodecombo',
	        tyno:'4'
	    },
	    {
	        fieldLabel: '商品性质',
	        name: 'prprpt',
	        xtype:'pubcodecombo',
	        tyno:'15'
	    },
	    {
	        fieldLabel: '渠道限制',
	        name: 'prorgd',
	        xtype:'pubcodecombo',
	        tyno:'16'
	    },
		{
	        fieldLabel: '单位',
	        name: 'prunit',
            selectOnFocus:true,
	        xtype:'combobox',
	        displayField: 'name',
		    valueField: 'id',
		    queryMode:'local',
		    store: {
		    	autoLoad:true,
			    fields: ['id', 'name'],
			    proxy:{
			    	type:'ajax',
			    	//extraParams:{positionType_id:'sjs'},
			    	url:Ext.ContextPath+'/pubCode/queryUnms4Combo.do'
			    }
			}
	    },
		{
	        fieldLabel: '面料',
	        name: 'mateno',
            fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '对照码',
	        name: 'prdcno',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '出厂价',
	        name: 'prftpr',
            fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'textfield'
	    },
//		{
//	        fieldLabel: '供应价',
//	        name: 'prsupr',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
		{
	        fieldLabel: '零售价',
	        name: 'prrtpr',
            fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '成本价',
	        name: 'prctpr',
            fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '加工费',
	        name: 'prmtam',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '面料费',
	        name: 'prmlam',
            fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '合同价',
	        name: 'prorpr',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '包装辅料费',
	        name: 'prflam',
            fieldStyle:'background-color:#CDC9C9;',
	        readOnly:true,
	        xtype:'textfield'
	    },
//		{
//	        fieldLabel: '打印系列',
//	        name: 'prprnt',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
//		{
//	        fieldLabel: '其他',
//	        name: 'prqtnm',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
//		{
//	        fieldLabel: '品名',
//	        name: 'prname',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
//		{
//	        fieldLabel: '安全标准',
//	        name: 'prsasd',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
//		{
//	        fieldLabel: '执行标准',
//	        name: 'prdosd',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
//		{
//	        fieldLabel: '洗涤图标',
//	        name: 'prwapt',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
//		{
//	        fieldLabel: '洗涤说明',
//	        name: 'prwash',
//            selectOnFocus:true,
//	        xtype:'textfield'
//	    },
		{
	        fieldLabel: '备注',
	        name: 'prmark',
            selectOnFocus:true,
	        xtype:'textfield'
	    },
		{
	        fieldLabel: '订货会批号',
	        name: 'ormtno',
            hidden:true,
            selectOnFocus:true,
	        xtype:'textfield'
	    }
	  ];   
	  
	  
	  this.buttons = [];
		this.buttons.push({
			text : '保存',
			itemId : 'save',
			formBind: true, //only enabled once the form is valid
       		disabled: true,
			glyph : 0xf0c7,
			handler : function(button){
				var formpanel = button.up('form');
				formpanel.updateRecord();
				var record=formpanel.getForm().getRecord();
				Ext.Ajax.request({
			  		url:Ext.ContextPath+'/sampleProd/createOrUpdate.do',
			  		jsonData :record.getData(),
			  		method:'POST',
			  		success:function(response){
			  			var obj=Ext.decode(response.responseText);
			  			record.commit();
			  			formpanel.grid.getStore().reload();
			  			
			  		}
			  	
			  	});
//				formpanel.getForm().getRecord().save({
//					failure: function(record, operation) {
//				    },
//				    success: function(record, operation) {
//						button.up('window').close();
//				    }
//				});			
				
				}
			},{
				text : '隐藏',
				itemId : 'close',
				glyph : 0xf00d,
				handler : function(button){
					button.up("form").collapse();
				}
	    });
      me.callParent();
	}
});
