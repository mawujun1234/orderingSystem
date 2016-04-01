/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('y.main.MainViewModel', {
			extend : 'Ext.app.ViewModel',

			alias : 'viewmodel.main',

			constructor : function() {
				//Ext.log('MainModel constructor');
				var me = this;
				// 这一句是关键，如果没有的话，this还没有初始化完成,下面的Ext.apply(me.data,....)这句就会出错
				this.callParent(arguments);
				// 同步调用取得系统参数
				Ext.Ajax.request({
					url : Ext.ContextPath+'/menu/queryByUser.do',
					async : false, // 同步
					success : function(response) {
						var text = response.responseText;
								// 将字段串转换成本地变量
						var applicationInfo = Ext.decode(text, true);
								// 把从后台传过来的参数加入到data中去
						Ext.apply(me.data.systemMenu, applicationInfo);
					}
				});
			},

			data : {

				monetary : { // 金额单位
					value : 'tenthousand' // 默认万元，以后可以从后台取得个人偏好设置，或者存放在cookies中
				},

				monetaryposition : { // 金额单位放置位置
					value : 'behindnumber'
				},

				autocolumnmode : { // 列宽自动调整
					value : 'onlyfirstload' // 列宽自动调整,默认第一次加载到数据
				},

				name : 'app',

				// 系统信息
				system : {
					name : '订货系统',
					version : '1.0.0',
					iconUrl : ''
				},

				// 用户单位信息和用户信息
				user : {
					company : '武当太极公司',
					department : '工程部',
					name : '张三丰'
				},

				// 服务单位和服务人员信息
				service : {
					company : '无锡熙旺公司',
					name : '蒋锋',
					phonenumber : '1320528----',
					qq : '78580822',
					email : 'jfok1972@qq.com',
					copyright : '熙旺公司'
				},

				menuType : {
					value : 'toolbar'
				}, // 菜单的位置，'button' , 'toolbar' , 'tree'

				// 系统菜单的定义，这个菜单可以是从后台通过ajax传过来的
				systemMenu : []
			},

			formulas : {

				isButtonMenu : function(get) {
					return get('menuType.value') == 'button';
				},

				isToolbarMenu : function(get) {
					return get('menuType.value') == 'toolbar';
				},

				isTreeMenu : function(get) {
					return get('menuType.value') == 'tree';
				}

			},

			getModuleDefine : function(moduleId) {
				var result = null;
				Ext.Array.each(this.get('tf_Modules'), function(module) {
							if (module.tf_moduleId == moduleId + ''
									|| module.tf_moduleName == moduleId) {
								result = module;
								return false;
							}
						})
				return result;
			},

			// 根据data.tf_MenuGroups生成菜单条和菜单按钮下面使用的菜单数据
			getMenus : function() {
				var items = [], me = this;
				Ext.Array.each(this.get('tf_MenuGroups'), function(group) { // 遍历菜单项的数组
							var submenu = [];
							// 对每一个菜单项，遍历菜单条的数组
							Ext.Array.each(group.tf_menuModules, function(menuitem) {
										// 根据moduleId取得该模块的定义
										var module = me.getModuleDefine(menuitem.tf_ModuleId);
										// 如果模块存在（或者具有浏览权限，以后加入）
										if (module) {
											submenu.push({
														mainmenu : 'true',
														moduleName : module.tf_moduleName,
														text : module.tf_title,
														icon : module.tf_icon,
														glyph : module.tf_glyph,
														handler : 'onMainMenuClick' // MainController中的事件处理程序
													})
											// 如果菜单定义了分隔下一条，那么菜单上加一个分隔线
											if (menuitem.tf_addSeparator)
												submenu.push('-');
										}
									})
							var item = {
								text : group.tf_title,
								menu : submenu,
								icon : group.tf_iconURL,
								glyph : group.tf_glyph
							};
							items.push(item);
						})
				return items;
			}
		});