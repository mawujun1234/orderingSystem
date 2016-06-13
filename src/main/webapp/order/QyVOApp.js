Ext.require("y.order.QyVO");
Ext.require("y.order.QyVOGrid");
Ext.onReady(function(){
	var grid=Ext.create('y.order.QyVOGrid',{
		region:'center'
		//title:''
	});
	
	var total_panel=Ext.create("Ext.panel.Panel",{
		region:'south',
		height:40,
		defaults: {
            flex: 1
        },
        layout: 'hbox',
        defaultType: 'textfield',
        items: [
                {
                    fieldLabel  : '原始数量',
                    labelWidth:65,
                    readOnly:true,
                    fieldStyle:'background-color:#CDC9C9;',
                    //value: '111',
                    itemId : 'ormtqs'
                }, {
                    fieldLabel  : '原始数量折算',
                    labelWidth:85,
                    readOnly:true,
                    fieldStyle:'background-color:#CDC9C9;',
                    //value: '22',
                    itemId : 'ormtqs_zhes'
                },{
                    fieldLabel  : '平衡数量',
                    labelWidth:65,
                    readOnly:true,
                    fieldStyle:'background-color:#CDC9C9;',
                    //value: '33',
                    itemId : 'ormtqt'
                },{
                    fieldLabel  : '平衡数量折算',
                    labelWidth:85,
                    readOnly:true,
                    fieldStyle:'background-color:#CDC9C9;',
                   // value: '44',
                    itemId : 'ormtqt_zhes'
                },{
                    fieldLabel  : '出厂金额',
                    labelWidth:65,
                    readOnly:true,
                    fieldStyle:'background-color:#CDC9C9;',
                    //value: '55',
                    itemId : 'spftpr_jine'
                },{
                    fieldLabel  : '零售金额',
                    labelWidth:65,
                    readOnly:true,
                    fieldStyle:'background-color:#CDC9C9;',
                    //value: '66',
                    itemId : 'sprtpr_jine'
                }
        ]
	});
	grid.total_panel=total_panel;
	
	var viewPort=Ext.create('Ext.container.Viewport',{
		layout:'border',
		items:[grid,total_panel]
	});



});