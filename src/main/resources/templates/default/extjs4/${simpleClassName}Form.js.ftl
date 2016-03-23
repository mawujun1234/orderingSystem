<#assign simpleClassNameFirstLower = simpleClassName?uncap_first> 
<#-- //所在模块-->
<#assign module = basepackage?substring(basepackage?last_index_of(".")+1)> 
Ext.define('${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}Form',{
	extend:'Ext.form.Panel',
	requires: [
	     '${extenConfig.extjs_packagePrefix}.${module}.${simpleClassName}'
	],
	
    frame: true,
    autoScroll : true,
	buttonAlign : 'center',
    bodyPadding: '5 5 0',


 	<#if  extenConfig.extjs_form_layoutColumns==-1>
    defaults: {
        msgTarget: 'side',
        labelWidth: 75,
        labelAlign:'right',
        anchor: '90%'
    },
    <#else>
    layout: {
        type: 'table',
        columns: ${extenConfig.extjs_form_layoutColumns}
    },
    defaults: {
    	msgTarget: 'under',
        labelWidth: 75,
        labelAlign:'right',
        width: '90%'
    },
    </#if>
	initComponent: function () {
       var me = this;
       me.items= [
      <#list propertyColumns as propertyColumn>
		<#if propertyColumn.jsType=='date'>
		{
            fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
            name: '${propertyColumn.property}',
            <#if propertyColumn.hidden=='true'>
            hidden:${propertyColumn.hidden},
            </#if>
            <#if propertyColumn.nullable=='false' && propertyColumn.hidden=='false'>
            allowBlank: ${propertyColumn.nullable},
            beforeLabelTextTpl: Ext.required,
            blankText:"${propertyColumn.property_label}不允许为空",
            </#if>
            xtype: 'datefield',
            format: 'Y-m-d'
            
        }<#if propertyColumn_has_next>,</#if>
		<#elseif propertyColumn.jsType=='int' || propertyColumn.jsType=='float'>
		{
	        fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
	        //afterLabelTextTpl: Ext.required,
	        name: '${propertyColumn.property}',
	        <#if propertyColumn.hidden=='true'>
            hidden:${propertyColumn.hidden},
            </#if>
             <#if propertyColumn.nullable=='false' && propertyColumn.hidden=='false'>
            allowBlank: ${propertyColumn.nullable},
            beforeLabelTextTpl: Ext.required,
            blankText:"${propertyColumn.property_label}不允许为空",
            </#if>
	        xtype:'numberfield'
	       
	    }<#if propertyColumn_has_next>,</#if>
		<#else>
		{
	        fieldLabel: '${propertyColumn.property_label!propertyColumn.property}',
	        //afterLabelTextTpl: Ext.required,
	        name: '${propertyColumn.property}',
	       <#if propertyColumn.hidden=='true'>
            hidden:${propertyColumn.hidden},
            </#if>
            <#if propertyColumn.nullable=='false' && propertyColumn.hidden=='false'>
            allowBlank: ${propertyColumn.nullable},
            beforeLabelTextTpl: Ext.required,
            blankText:"${propertyColumn.property_label}不允许为空",
            </#if>
	        xtype:'textfield'
	       
	    }<#if propertyColumn_has_next>,</#if>
		</#if>

	  </#list>   
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
				//button.up('form').updateRecord();
				//button.up('form').getForm().getRecord().save();
				var form = formpanel.getForm();
	            if (form.isValid()) {
	                form.submit({
	                    success: function(form, action) {
	                       Ext.Msg.alert('Success', action.result.msg);
	                    },
	                    failure: function(form, action) {
	                        Ext.Msg.alert('Failed', action.result.msg);
	                    }
	                });
	            }				
				
				}
			},{
				text : '关闭',
				itemId : 'close',
				glyph : 0xf00d,
				handler : function(button){
					button.up('window').hide();
				}
	    });
      me.callParent();
	}
});
