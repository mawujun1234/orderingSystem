
/**
 * 系统主页的顶部区域，主要放置系统名称，菜单，和一些快捷按钮
 */
Ext.define('y.main.region.Top', {

			extend : 'Ext.toolbar.Toolbar',

			alias : 'widget.maintop', // 定义了这个组件的xtype类型为maintop

			uses : ['y.main.ux.ButtonTransparent', 'y.main.menu.ButtonMainMenu',
					'y.main.menu.SettingMenu'],

			defaults : {
				xtype : 'buttontransparent'
			},

			style : 'background-color : #cde6c7',

			height : 40,

			items : [
					{
						xtype : 'image',
						bind : { // 数据绑定到MainModel中data的system.iconUrl
							hidden : '{!system.iconUrl}', // 如果system.iconUrl未设置，则此image不显示
							src : '{system.iconUrl}' // 根据system.iconUrl的设置来加载图片
						}
					},
					{
						xtype : 'label',
						bind : {
							text : '{system.name}'
						},
						style : 'font-size:20px;color:grey;'
					}, {
						xtype : 'label',
						style : 'color:grey;',
						bind : {
							text : '({system.version})'
						}
					}, '->', 
//					{
//						xtype : 'buttonmainmenu',
//						hidden : true,
//						bind : {
//							hidden : '{!isButtonMenu}'
//						}
//					}, ' ', ' ', {
//						text : '首页',
//						glyph : 0xf015,
//						handler : 'onHomePageButtonClick'
//					}, {
//						xtype : 'settingmenu'
//					}, {
//						text : '帮助',
//						glyph : 0xf059
//					}, {
//						text : '关于',
//						glyph : 0xf06a
//					}, '->', '->', {
//						text : '搜索',
//						glyph : 0xf002
//					}, {
//						text : '导入',
//						handler : function() {
//							Ext.MessageBox.prompt('增加模块', '请输入要增加模块的类名称:',
//									function(btn, text) {
//										if (btn == 'ok') {
//											Ext.Ajax.request({
//														scope : this,
//														url : 'systemframe/addmodule.do',
//														params : {
//															moduleName : text
//														},
//														success : function(response) {
//															if (response.responseText)
//																Ext.MessageBox.show({
//																			title : '导入失败',
//																			msg : '导入模块失败<br/><br/>'
//																					+ response.responseText,
//																			buttons : Ext.MessageBox.OK,
//																			icon : Ext.MessageBox.ERROR
//																		});
//
//															else {
//																Ext.toastInfo('增加模块','模块:' + text
//																		+ '的定义和grid,form定义已经到加系统中!');
//															}
//														},
//														failure : function() {
//															window.alert(text + '保存失败!')
//														}
//													})
//										}
//									})
//
//						}
//					}, 
					{

						text : '修改密码',
						glyph : 0xf044,
						handler:function(){
							var win=Ext.create('y.main.UpdatePwdWindow',{});
							win.show();	
						}
					}, 
					{

						text : '注销',
						glyph : 0xf011,
						handler:function(){
							window.location.href=Ext.ContextPath+"/user/logout.do"
						}
					}, {
						glyph : 0xf102,
						handler : 'hiddenTopBottom',
						tooltip : '隐藏顶部和底部区域',
						disableMouseOver : true
					}]

		});