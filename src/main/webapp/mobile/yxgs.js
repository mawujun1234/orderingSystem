Ext={};
Ext.ContextPath="/od";
if(location.pathname.indexOf("/od")==-1){
	Ext.ContextPath="";
}
$.ajaxSettings.accepts.json="application/json;charset=UTF-8";
$(document).on('ajaxSuccess',function(e,xhr,options,response){
	handlerReturn(response);
	return;
});
$(document).on('ajaxError',function(e,xhr,options,response){
	handlerReturn(response);
	return;
});
function handlerReturn(response){
	
	if(response.success==false){
			if(response.msg){
				$.alert(response.msg);
				if(response.errorCode=='nologin'){
					$.router.load("#od_loginpage"); 
				}
				return;
			} else {
				return;
			}
			$.hidePreloader();
	}
}

$(function(){
	function check(){
		$.post(Ext.ContextPath+'/ord/mobile/yxgs/getOrstat.do', {  }, function(response){
			//alert(response.canConfirm);
			document.title = response.orgnm;
			if(response.canConfirm==0){//不显示按钮
				od_yxgs_confirm_btn.hide();
			} else if(response.canConfirm==1){
				od_yxgs_confirm_btn.show().css("display","block");
			} else if(response.canConfirm==2){
				od_yxgs_confirm_btn.show().css("display","block").removeClass("disabled");
			} else if(response.canConfirm==3){
				od_yxgs_confirm_btn.show().css("display","block");
				od_yxgs_confirm_btn.html("订单已确认");
			}
		},'json');
	}
	check();
	
	var od_yxgs_confirm_btn=$("#od_yxgs_confirm_btn");
	od_yxgs_confirm_btn.click(function(){
		if($(this).hasClass("disabled")){
			return;
		}
		$.post(Ext.ContextPath+'/ord/mobile/confirm_yxgs.do', {  }, function(response){
			if(response.success==false){
				alert(response.msg);
				return;
			}
			od_yxgs_confirm_btn.hide();
		});
	});
});