
Ext.define('y.main.UpdatePwdWindow', {
    extend: 'Ext.window.Window',
    title:'修改密码',
    modal:true,
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
			        fieldLabel: '密码',
			        name: 'password',
		            allowBlank: false,
		            afterLabelTextTpl: Ext.required,
		            blankText:"密码不允许为空",
		            inputType: 'password',
			        xtype:'textfield'
			    },
				{
			        fieldLabel: '新密码',
			        name: 'password_new',
			        allowBlank: false,
			        afterLabelTextTpl: Ext.required,
		            blankText:"新密码不允许为空",
		            inputType: 'password',
			        xtype:'textfield'
			    }],
			 buttons:[{
					text : '保存',
					itemId : 'save',
					formBind: true, //only enabled once the form is valid
		       		disabled: true,
					glyph : 0xf0c7,
					handler : function(button){
						var formpanel = button.up('form');
						
						Ext.Ajax.request({
							url:Ext.ContextPath+'/user/updatePwd.do',
//							params:{
//								password:,
//								password_new:
//							},
							params:formpanel.getForm().getValues(),
							headers:{ 'Accept':'application/json;'},
							success:function(){
								//button.up('window').close();
								//me.onReload(parent);
								button.up('window').close();
							}
						 });	
						
						}
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
