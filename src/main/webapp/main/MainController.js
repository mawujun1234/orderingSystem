/**
 * This class is the main view for the application. It is specified in app.js as
 * the "autoCreateViewport" property. That setting automatically applies the
 * "viewport" plugin to promote that instance of this class to the body element.
 * 
 * TODO - Replace this content of this view to suite the needs of your
 * application.
 */
Ext.define('y.main.MainController', {
			extend : 'Ext.app.ViewController',

			//requires : ['Ext.MessageBox', 'Ext.window.Toast'],

			//uses : ['app.view.module.Module'],

			alias : 'controller.main',

			mixins : {
						//gridController : 'app.view.main.controller.GridController'
					},

			init : function() {
				//console.log('mainController init');
				//this.mixins.gridController.init.call(this);
				
//				this.control({
////		            'userlist': {
////		                itemdblclick: this.editUser
////		            },
//		            'mainmenutree': {
//		                itemclick: this.onMainMenuTree_itemclick
//		            }
//		        });

			},

			// 单击了顶部的 首页 按钮
			onHomePageButtonClick : function(menuitem) {
				this.getView().down('maincenter').setActiveTab(0);
			},

			// 选择了主菜单上的菜单后执行
			onMainMenuTree_itemclick : function(view,menuitem) {//alert(menuitem);
				//console.log(menuitem);
				if(!menuitem.get("url")){
					return;
				}
				var maincenter = this.getView().down('maincenter');
				
				var tabs=maincenter.items;
	    		
	    		for(var i=0;i<tabs.length;i++){
	    			var tab=tabs.items[i];
	    			if(tab.menu_id==menuitem.get('id')){
	    				maincenter.setActiveTab(tab);
	    				return;
	    			}
	    		}

				maincenter.setActiveTab(maincenter.add({
					xtype : 'modulepanel',
					menu_id:menuitem.get("id"),
					title:menuitem.get("name"),
					url:menuitem.get("url"),
					closable : true,
					reorderable : true
				}));
			},

			// 隐藏顶部和底部的按钮事件
			hiddenTopBottom : function() {
				// 如果要操纵控件，最好的办法是根据相对路径来找到该控件，用down或up最好，尽量少用getCmp()函数。
				this.getView().down('maintop').hide();
				this.getView().down('mainbottom').hide();
				if (!this.showButton) { // 显示顶部和底部的一个控件，在顶部和底部隐藏了以后，显示在页面的最右上角
					this.showButton = Ext.widget('component', {
								glyph : 0xf013,
								view : this.getView(),
								floating : true,
								x : document.body.clientWidth - 32,
								y : 0,
								height : 8,
								width : 26,
								style : 'background-color:#cde6c7',
								listeners : {
									el : {
										click : function(el) {
											var c = Ext.getCmp(el.target.id); // 取得component的id值
											c.view.down('maintop').show();
											c.view.down('mainbottom').show();
											c.hide();
										}
									}
								}
							})
				};
				this.showButton.show();
			},

			// 如果窗口的大小改变了，并且顶部和底部都隐藏了，就要调整显示顶和底的那个控件的位置
			onMainResize : function() {
				if (this.showButton && !this.showButton.hidden) {
					this.showButton.setX(document.body.clientWidth - 32);
				}
			},

			// 显示菜单条，隐藏左边菜单区域和顶部的按钮菜单。
			showMainMenuToolbar : function(button) {

				this.getView().getViewModel().set('menuType.value', 'toolbar');

			},
			// 显示左边菜单区域,隐藏菜单条和顶部的按钮菜单。
			showLeftMenuRegion : function(button) {

				this.getView().getViewModel().set('menuType.value', 'tree');

			},
			// 显示顶部的按钮菜单,隐藏菜单条和左边菜单区域。
			showButtonMenu : function(button) {
				this.getView().getViewModel().set('menuType.value', 'button');
			}
		});
