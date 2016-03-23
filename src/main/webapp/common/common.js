Ext.define("y.common.Model",{
	extend:"Ext.data.Model",
	fields:[
	],
	proxy:{
				type:'ajax',
				actionMethods: { create: 'POST', read: 'POST', update: 'POST', destroy: 'POST' },
				timeout :600000,
				headers:{ 'Accept':'application/json;'},
//				reader:{
//						type:'json',
//						root:'root',
//						successProperty:'success',
//						totalProperty:'total'
//						
//				}
//				,writer:{
//					type:'json'
//				},
				api:{
					read:Ext.ContextPath+'/'+path+'/query.do',
					load : Ext.ContextPath+'/'+path+'/load.do',
					create:Ext.ContextPath+'/'+path+'/create.do',
					update:Ext.ContextPath+'/'+path+'/update.do',
					destroy:Ext.ContextPath+'/'+path+'/destroy.do'
				}
			}
});



///**
// * 
// * 一个form窗口的基类
// * 
// */
//
//Ext.define('y.common.BaseForm', {
//			extend : 'Ext.form.Panel',
//			alias : 'widget.baseform',
//			autoScroll : true,
//			buttonAlign : 'center',
//			initComponent : function() {
//				var me = this;
//				this.buttons = [];
//				this.buttons.push({
//							text : '保存',
//							itemId : 'save',
//							glyph : 0xf0c7,
//							handler : function(button) {
//								var form = button.up('form');
//
//								button.up('form').updateRecord();
//								button.up('form').getForm().getRecord().save();
//							}
//						}, {
//							text : '关闭',
//							itemId : 'close',
//							glyph : 0xf148,
//							handler : function(button) {
//								button.up('window').hide();
//							}
//						});
//				//me.items = [];
//				
//
//				me.callParent(arguments);
//			}
//
//		});