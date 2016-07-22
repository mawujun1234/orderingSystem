Ext={};
Ext.ContextPath="";
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
		});
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