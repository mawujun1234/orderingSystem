/**
 * 功能的扩展，添加自定义的怎，删，改
 * 添加右键菜单，增，删，改，并且增加工具栏，增，删，改。
 * 后台的类最好继承TreeNode类，这样就可以少写很多代码
 */
Ext.define('y.main.LoginWindow', {
    extend: 'Ext.window.Window',
    title:'登录',
    initComponent: function () {
		var me = this;
		var formpanel=Ext.create('Ext.form.Panel',{
			defaults: {
		        msgTarget: 'under',
		        labelWidth: 75,
		        labelAlign:'right',
		        anchor: '90%'
		    },
			items: [
				{
			        fieldLabel: '用户名',
			        name: 'username',
		            allowBlank: false,
		            afterLabelTextTpl: Ext.required,
		            blankText:"登录名不允许为空",
			        xtype:'textfield'
			    },
				{
			        fieldLabel: '密码',
			        name: 'password',
			        afterLabelTextTpl: Ext.required,
		            blankText:"密码不允许为空",
		            inputType: 'password',
			        xtype:'textfield',
			        listeners: {
		                specialkey: function(field, e){
		                    // e.HOME, e.END, e.PAGE_UP, e.PAGE_DOWN,
		                    // e.TAB, e.ESC, arrow keys: e.LEFT, e.RIGHT, e.UP, e.DOWN
		                    if (e.getKey() == e.ENTER) {
		                        //var form = field.up('form').getForm();
		                        //form.submit();
		                    	
		                    	var formpanel = field.up('form');
		                    	formpanel.down("#loginform_save").fireEvent("click",formpanel.down("#loginform_save"));
		                    }
		                }
		            }
			    }],
			 buttons:[{
					text : '登录',
					itemId : 'loginform_save',
					formBind: true, //only enabled once the form is valid
		       		disabled: true,
					glyph : 0xf0c7,
					listeners:{
					click : function(button){
						var formpanel = button.up('form');
						formpanel.submit({ 
							headers:{ 'Accept':'application/json;'},
				            waitMsg : '正在登录......', 
				            url : Ext.ContextPath+'/user/login.do', 
				            
				            success : function(form, action) {
				            	if(action.result.success){
				            		 //window.location.href = Ext.ContextPath+action.result.root;//'index.jsp';
				            		top.window.location.href = Ext.ContextPath+'/main/index.jsp';
				            	}
				            }, 
				            failure : function(form, action) {
					            form.reset();
								switch (action.failureType) {
									case Ext.form.Action.CLIENT_INVALID:
												    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
										Ext.Msg.alert('提示','数据错误，非法提交');  
									    break;
									case Ext.form.Action.CONNECT_FAILURE:
												    //服务器指定的路径链接不上时  
										Ext.Msg.alert('连接错误','认证失败!'); 
									    break;
									case Ext.form.Action.SERVER_INVALID:
									            	//服务器端你自己返回success为false时  
										Ext.Msg.alert('友情提示', action.result.msg);	
										break;
									default:			 //其它类型的错误  
						                ShowMessage('警告', '服务器数据传输失败：'+action.response.responseText); 
										break;
									}
				            	}
				            		  
				           });		
						
						}
					}//listener
					},{
						text : '关闭',
						itemId : 'close',
						glyph : 0xf00d,
						handler : function(button){
							button.up('window').close();
						}
			    }]
		});
		this.items=[formpanel];

//		this.buttons = [{
//			text : '登录',
//			itemId : 'save',
//			formBind: true, //only enabled once the form is valid
//       		disabled: true,
//			glyph : 0xf0c7,
//			handler : function(button){
//				var formpanel = button.up('form');
//				formpanel.submit({ 
//		            waitMsg : '正在登录......', 
//		            url : Ext.ContextPath+'/login.do', 
//		            success : function(form, action) {
//		            	if(action.result.success){
//		            		 //window.location.href = Ext.ContextPath+action.result.root;//'index.jsp';
//		            		window.location.href = Ext.ContextPath+'/index.jsp';
//		            	}
//		            }, 
//		            failure : function(form, action) {
//			            form.reset();
//						switch (action.failureType) {
//							case Ext.form.Action.CLIENT_INVALID:
//										    //客户端数据验证失败的情况下，例如客户端验证邮件格式不正确的情况下提交表单  
//							ShowMessage('提示','数据错误，非法提交');  
//							    break;
//							case Ext.form.Action.CONNECT_FAILURE:
//										    //服务器指定的路径链接不上时  
//								ShowMessage('连接错误','指定路径连接错误!'); 
//							                break;
//							case Ext.form.Action.SERVER_INVALID:
//							            	//服务器端你自己返回success为false时  
//								ShowMessage('友情提示', action.result.root);	
//								break;
//							default:
//											 //其它类型的错误  
//				                ShowMessage('警告', '服务器数据传输失败：'+action.response.responseText); 
//								break;
//							}
//		            	}
//		            		  
//		           });		
//				
//				}
//			},{
//				text : '关闭',
//				itemId : 'close',
//				glyph : 0xf00d,
//				handler : function(button){
//					button.up('window').close();
//				}
//	    }];
//		this.buttons.push();
       
		me.callParent(arguments);
    }
});
