Ext={};
Ext.ContextPath="";
$(function(){
			$(".card .card-header .item-title.label").click(function(){
				var input_id=$(this).attr("id");
				//如果是订货信息的就不收缩
				if(input_id=='od_info_input'){
					return;
				}
				var car_content_id=input_id.substr(0,input_id.length-6);
				//$(".card .guig_content").toggle();
				var guig_content=$("#"+car_content_id+" .guig_content");
				guig_content.toggle();
				
				var icon=$(this).next().next();//alert(icon.length);
				icon.toggleClass("icon-up");
				icon.toggleClass("icon-down");
			});
			
			$(".card .card-header input").click(function(){
				//$(document).scrollTop();
				
				var input_id=$(this).attr("id");
				if(input_id=='od_info_input'){
					return;
				}
				var car_content_id=input_id.substr(0,input_id.length-6);
				
				
				$(".card .guig_content").hide();
				$("#"+car_content_id+" .guig_content").show();
				
				//alert($(".card .card-header .item-content .icon").length);
				$(".card .card-header .item-content .icon").removeClass("icon-down");
				$(".card .card-header .item-content .icon").addClass("icon-up");
				var icon=$(this).parent().next(".icon");
				icon.removeClass("icon-up");
				icon.addClass("icon-down");
				
				
			});
			
			$(".card .card-header .item-content .icon").click(function(){
				//搜索具体的规格
				$(this).parents(".card").children(".guig_content").toggle();
				
				var icon=$(this);
				icon.toggleClass("icon-up");
				icon.toggleClass("icon-down");
			});
			
	//---------------------------------------------------------------------------------------------------		
	$("#bottom_bar a.tab-item").click(function(){
		$("#bottom_bar a.tab-item").removeClass("active");
		$(this).addClass("active");
	});
	$(document).on("pageInit", function(e, pageId, $page) {
//	   if(pageId == "od_loginpage") {
//		  $("#bottom_bar").hide();
//		} else {
//			$("#bottom_bar").show();
//		}
	});
	

	var $qrcode_button = document.getElementById('qrcode_button');
	$qrcode_button.addEventListener('touchmove', function(event) {
		if (event.targetTouches.length == 1) {
		　　 event.preventDefault(); 
			var touch = event.targetTouches[0];
			$qrcode_button.style.left = touch.pageX-50 + 'px';
			$qrcode_button.style.top = touch.pageY-50 + 'px';
		}
	}, false);
	
	$(document).click(function(event){
		$("#tap_effect").css({
		  position: 'absolute',
		  top: event.pageY-25,
		  left: event.pageX-25
		});
		//console.log($("#tap_effect").css("top"));
		$("#tap_effect").addClass("tap_effect");
		setTimeout(function(){
			$("#tap_effect").removeClass("tap_effect");
		},500)
	});
	
	//alert(location.href)
	//扫一扫
	$("#od_info_scanQRCode_btn").click(function(){
		wx.scanQRCode({
			desc: 'scanQRCode desc',
			needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			success: function (res) {
				var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			}
		});
	});

});

//登录相关
$(function(){
	window.user=1;
	if(!window.user){
		$.router.load("#od_loginpage"); 
	}
	$(document).on("pageInit", function(e, pageId, $page) {
	  if(pageId != "od_loginpage" && !window.user) {
		 $.router.load("#od_loginpage"); 
		 return;
	  }
	  if(pageId == "od_loginpage") {
		  $("#bottom_bar").hide();
	  } else {
		  $("#bottom_bar").show();
	  }
	});
	
	$("#od_loginpage_login_btn").click(function(){
		var od_loginpage_username=$("#od_loginpage_username").val();
		var od_loginpage_password=$("#od_loginpage_password").val();
		if(!od_loginpage_username){
			$.toast("请输入用户名!");return;
		}
		if(!od_loginpage_password){
			$.toast("请输入密码!");return;
		}
		
		$.post(Ext.ContextPath+"/user/mobile/login.do",
			{
				username:od_loginpage_username,
				password:od_loginpage_password,
				url:location.href.split('#')[0]
			},function(response){
				//console.log(response);
				//var obj=response;//JSON.parse(response.responseText);
				//console.log(obj);
				if(response.success==false){
					$.toast(response.msg);
				} else {
					window.user=response;
					$.router.load("#od_info"); 

					response.wxConfig.debug=false;
					response.wxConfig.jsApiList=['scanQRCode'];
					wx.config(response.wxConfig);
					wx.hideAllNonBaseMenuItem();
				}
				
			}
		)
	});
	
});	


