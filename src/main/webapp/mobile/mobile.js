Ext={};
Ext.ContextPath="";
$(function(){
//			$(".card .card-header .item-title.label").click(function(){
//				var input_id=$(this).attr("id");
//				//如果是订货信息的就不收缩
//				if(input_id=='od_info_input'){
//					return;
//				}
//				var car_content_id=input_id.substr(0,input_id.length-6);
//				//$(".card .guig_content").toggle();
//				var guig_content=$("#"+car_content_id+" .guig_content");
//				guig_content.toggle();
//				
//				var icon=$(this).next().next();//alert(icon.length);
//				icon.toggleClass("icon-up");
//				icon.toggleClass("icon-down");
//			});
			window.card_card__header_item__title_label=function(obj){
				var input_id=$(obj).attr("id");
				//如果是订货信息的就不收缩
				if(input_id=='od_info_input'){
					return;
				}
				var car_content_id=input_id.substr(0,input_id.length-6);
				//$(".card .guig_content").toggle();
				var guig_content=$("#"+car_content_id+" .guig_content");
				guig_content.toggle();
				
				var icon=$(obj).next().next();//alert(icon.length);
				icon.toggleClass("icon-up");
				icon.toggleClass("icon-down");
			}
			
//			$(".card .card-header input").click(function(){
//				//$(document).scrollTop();
//				
//				var input_id=$(this).attr("id");
//				if(input_id=='od_info_input'){
//					return;
//				}
//				var car_content_id=input_id.substr(0,input_id.length-6);
//				alert(car_content_id);
//				
//				$(".card .guig_content").hide();
//				$("#"+car_content_id+" .guig_content").show();
//				
//				//alert($(".card .card-header .item-content .icon").length);
//				$(".card .card-header .item-content .icon").removeClass("icon-down");
//				$(".card .card-header .item-content .icon").addClass("icon-up");
//				var icon=$(this).parent().next(".icon");
//				icon.removeClass("icon-up");
//				icon.addClass("icon-down");
//				
//				
//			});
			window.card_header_input_click=function(obj){	
				var input_id=$(obj).attr("id");
				if(input_id=='od_info_input'){
					return;
				}
				var car_content_id=input_id.substr(0,input_id.length-6);
				//alert(car_content_id);
				
				$(".card .guig_content").hide();
				$("#"+car_content_id+" .guig_content").show();
				
				//alert($(".card .card-header .item-content .icon").length);
				var icones=$(".card .card-header .item-content .icon");
				icones.removeClass("icon-down");
				icones.addClass("icon-up");
				var icon=$(obj).parent().next(".icon");
				icon.removeClass("icon-up");
				icon.addClass("icon-down");
			}
			
//			$(".card .card-header .item-content .icon").click(function(){
//				//搜索具体的规格
//				$(this).parents(".card").children(".guig_content").toggle();
//				
//				var icon=$(this);
//				icon.toggleClass("icon-up");
//				icon.toggleClass("icon-down");
//			});
			window.card_header_item_content_icon=function(obj){
				//搜索具体的规格
				$(obj).parents(".card").children(".guig_content").toggle();
				
				var icon=$(obj);
				icon.toggleClass("icon-up");
				icon.toggleClass("icon-down");
			}
			
	//---------------------------------------------------------------------------------------------------		
	$("#bottom_bar a.tab-item").click(function(){
		$("#bottom_bar a.tab-item").removeClass("active");
		$(this).addClass("active");
	});
//	$(document).on("pageInit", function(e, pageId, $page) {
////	   if(pageId == "od_loginpage") {
////		  $("#bottom_bar").hide();
////		} else {
////			$("#bottom_bar").show();
////		}
//	});
	

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

//=========================================================登录相关
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
		$.showPreloader("正在登录...");
		$.post(Ext.ContextPath+"/user/mobile/login.do",
			{
				username:od_loginpage_username,
				password:od_loginpage_password,
				url:location.href.split('#')[0]
			},function(response){
				//console.log(response);
				//var obj=response;//JSON.parse(response.responseText);
				//console.log(obj);
				//alert(1);
				$.hidePreloader();
				if(response.success==false){
					$.toast(response.msg);
				} else {
					window.user=response;
					$.router.load("#od_info"); 

					response.wxConfig.debug=false;
					response.wxConfig.jsApiList=['scanQRCode'];
					wx.config(response.wxConfig);
					//wx.hideAllNonBaseMenuItem();
				}
				
			}
		)
	});
	
});	


//-----------------------------------扫描样衣编号的模块
$(function(){
	$("#od_info_scan_button").click(function(){
		var val=$("#od_info_input").val();
		if(!val){
			return;
		}
		scan(val);
	});
	//开始扫描某个样衣
	function scan(sampnm){
		$.showPreloader("正在获取样衣信息...");
		$.post(Ext.ContextPath+"/ord/querySample.do",
			{
				sampnm:sampnm
			},function(response){
				if(response.success==false){
					$.toast(response.msg);
					$.hidePreloader();
					return;
				}
				//样衣信息
				if(window.vm_sampleVO){
					window.vm_sampleVO.$data=response.sampleVO;
				} else {
					window.vm_sampleVO=new Vue({
					  el: '#od_info_sample_info',
					  data: response.sampleVO
					});
				}
				
				
				//od_info_suitVOs
				//套件信息
				if(window.vm_od_info_suitVOs){
					window.vm_od_info_suitVOs.suitVOs=response.suitVOs;
				} else {
					window.vm_od_info_suitVOs=new Vue({
					  el: '#od_info_suitVOs',
					  data:{suitVOs:response.suitVOs},//{suitvos:response.suitVOs}
					  methods: {
						distributeOrmtqs:function(event){
							var vm=this;
							var index=event.target.dataset.index;
							var suitVO=vm.suitVOs[index];
							var sizeVOs=suitVO.sizeVOs;
							var szrate_sum=suitVO.szrate_sum;
							
							var used_orszqt=0;//已经使用掉的数量
							var max_szrate_sizeVO=null;//持有最大比率的规格
							var max_szrate=0;//最大的比率
							for(var i=0;i<sizeVOs.length;i++){
								if(sizeVOs[i].szrate>max_szrate){
									max_szrate=sizeVOs[i].szrate;
									max_szrate_sizeVO=sizeVOs[i];
								}
								sizeVOs[i].orszqt=Math.floor(suitVO.ormtqs*(sizeVOs[i].szrate/szrate_sum));
								used_orszqt+=sizeVOs[i].orszqt;
							};
							//把剩余的量加到比率最大的规格上
							max_szrate_sizeVO.orszqt=max_szrate_sizeVO.orszqt+(suitVO.ormtqs-used_orszqt);
							
						}, //distribute 
						sumOrszqt:function(event){
							var vm=this;
							var suitno=event.target.dataset.suitno;
							//console.log(index);
							var suitVO=null;//vm.suitVOs[index];
							for(var i=0;i<vm.suitVOs.length;i++){
								if(vm.suitVOs[i].suitno==suitno){
									suitVO=vm.suitVOs[i];
									break;
								}
							}
							
							var sizeVOs=suitVO.sizeVOs;
							var ormtqs=0;
							for(var i=0;i<sizeVOs.length;i++){
								ormtqs+=parseInt(sizeVOs[i].orszqt);
							}
							suitVO.ormtqs=ormtqs;
							
						}
					  }
					});
				}
				$.hidePreloader();
				
				$("#od_info_sample_info .card-content").show(200);
			}
		)
	}//function scan()
	
	//Vue.component('component-suitvos', {
//  		template: '#template-suitvos',
//		props: {
//			suitvos: Array
//		}
//	});
	
	$("#od_info_save_button").click(function(){
		var suitVOs=window.vm_od_info_suitVOs.suitVOs;
		//console.log(suitVOs);
		//判断一个套件中的总量和各个规格加起来是否相等
		for(var i=0;i<suitVOs.length;i++){
			if(!suitVOs[i].ormtqs){
				continue;
			}
			var orszqt_sum=0;
			for(var j=0;j<suitVOs[i].sizeVOs.length;j++){
				orszqt_sum+=parseInt(suitVOs[i].sizeVOs[j].orszqt);
			}
			//console.log(suitVOs[i].ormtqs+"===="+orszqt_sum);
			if(suitVOs[i].ormtqs!=orszqt_sum){
				alert("<"+suitVOs[i].suitno_name+">的数据不一致!");
				//$.toast("<"+suitVOs[i].suitno_name+">的数据不一致!", 2345, 'success top');
				return;
			}
		}
		
		//console.log(data["suitVOs"]);
		//$.post(Ext.ContextPath+"/user/mobile/login.do",window.vm_od_info.$data.suitVOs,function(response){
			
		//})
	});
});

