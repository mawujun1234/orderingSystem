/**
 * 一个模块的主控界面的容器，用来安放各个模块控件以及协调他们之间的关系
 */
Ext.define('y.main.Module', {
			extend : 'Ext.panel.Panel',

			alias : 'widget.modulepanel',

//			requires : ['app.view.module.ModuleController',
//					'app.view.module.ModuleModel', 'app.view.module.factory.ModelFactory'],
//
//			uses : ['app.view.module.region.Navigate', 'app.view.module.region.Grid',
//					'app.view.module.region.Detail' , 'app.view.module.window.BaseWindow'],
//			referenceHolder: true,
//
//			controller : 'module',
//			// MVVM架构的控制器的名称，main控制器会自动加载，这个控制器不会自动加载，需要在requires中指定，不知道是为什么
//			viewModel : {
//				type : 'module'
//			},
//			bind : {
//				// glyph : '{tf_glyph}', // 这一个绑定是无效的，在tabPanel渲染过后，再修改这个值，将不会有任何效果。
//				title : '{tf_title}' // 这个绑定是有效的，可以根据ModuleModel中的值来设置title
//			},
			layout : 'fit', // 模块采用border布局

			initComponent : function() {
				this.html="<iframe scrolling='no' id='iframe_" + this.menu_id + "' width='100%' height='100%'  frameborder='0' src='" + this.url + "'></iframe>",

				this.callParent();
			}

		})