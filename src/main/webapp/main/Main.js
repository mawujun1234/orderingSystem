/**
 * This class is the main view for the application. It is specified in app.js as
 * the "autoCreateViewport" property. That setting automatically applies the
 * "viewport" plugin to promote that instance of this class to the body element.
 * 
 * TODO - Replace this content of this view to suite the needs of your
 * application.
 */
Ext.define('y.main.Main', {
			extend : 'Ext.container.Container',

			xtype : 'app-main',

			uses : ['y.main.region.Center', 'y.main.region.Top',
					'y.main.region.Bottom', 'y.main.menu.MainMenuToolbar',
					'y.main.region.Left' ],

			controller : 'main',
			// MVVM架构的控制器的名称，会在当前路径中根据‘Main’ + Controller 来确定文件名
			// 这个我没找到其他任何可以自动加载MainController.js的依据，只能作上面的判断了
			viewModel : {
				type : 'main'
				// MVVM架构的viewModel的类型，会在当前路径中根据‘Main’ + Model 来确定文件名
			},

			initComponent : function() {
				//Ext.setGlyphFontFamily('FontAwesome'); // 设置图标字体文件，只有设置了以后才能用glyph属性
				
				// 在此处取得各个modelView的参数，通过ajax也行
				
				this.getViewModel().set('monetary.value','tenthousand');

				
				this.callParent();
			},

			layout : {
				type : 'border' // 系统的主页面的布局
			},

			listeners : {
				resize : function(container) {
					container.getController().onMainResize();
				}
			},

			items : [{
						xtype : 'maintop',
						height:38,
						region : 'north' // 把他放在最顶上
					},
//					{
//						xtype : 'mainmenutoolbar',
//						region : 'north', // 把他放在maintop的下面
//						hidden : true,
//						bind : {
//							hidden : '{!isToolbarMenu}'
//						}
//					},
					{
						xtype : 'mainbottom',
						region : 'south' // 把他放在最底下
					}, 
					{
						xtype : 'mainmenutree',
						itemId:'mainmenutree',
						region : 'west', // 左边面板
						title : '导航菜单',
						// hidden : Cookies.get('menutoolbar', 'true') == 'true',
						width : 220,
						collapsible : true,
						split : true,
						hidden : false
//						bind : {
//							hidden : '{!isTreeMenu}'
//						}
					}, 
					{
						region : 'center', // 中间面版
						xtype : 'maincenter'
			}]//items
			
		});
