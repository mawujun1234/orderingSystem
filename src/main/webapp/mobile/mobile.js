Ext={};
Ext.ContextPath="";
//显示和隐藏今日订货会快结束的信息
window.od_closeing_info=null;//提高性能
window.canOrd=null;//判断是否可以订货
function show_od_closeing_info(){
	$.post(Ext.ContextPath+'/ord/mobile/checked_closeing_info.do', {  }, function(response){
		window.canOrd=response.canOrd;
		if(!window.od_closeing_info){
				window.od_closeing_info=$("#od_closeing_info");
		}
		if(response.show){
			window.od_closeing_info.html(response.msg);
			window.od_closeing_info.show();
		} else {
			window.od_closeing_info.hide();
		}
	});
	setTimeout("show_od_closeing_info()",120*1000);
}
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
					
					document.title = response.orgnm;
					
					show_od_closeing_info();
				}
				
			}
		)
	});
	
});	


//-----------------------------------扫描样衣编号的模块
$(function(){
	//显示或隐藏 未保存提示框
	window.od_info_data_issaved_bool=true;
	function showOd_info_unsave_tips(bool){
		window.od_info_data_issaved_bool=!bool;
		if(bool){
			$("#od_info_unsave_tips").show();
		} else {
			$("#od_info_unsave_tips").hide();	
		}
	}
	//判断能否切换页面或者重新扫描，如果未保存的话
	//返回true，表示可以进行切换
	function od_info_data_issaved() {
		//console.log(window.od_info_data_issaved_bool);
		return window.od_info_data_issaved_bool;
	}
	
	$("#od_info_scan_button").click(function(){
		var val=$("#od_info_input").val();
		if(!val){
			return;
		}
		
		if(!od_info_data_issaved()){
			$.confirm('数据未保存！确定要进行切换吗？', function () {
				scan(val);
			});//$.confirm('Are you sure?', function () {
		}//if(od_info_data_issaved()){	
		else {
			scan(val);
		}
		
	});
	//开始扫描某个样衣
	function scan(sampnm){
		showOd_info_unsave_tips(false);
				  //$.alert('You clicked Ok button');
		
		$.showPreloader("正在获取样衣信息...");
		$.post(Ext.ContextPath+"/ord/mobile/querySample.do",
			{
				sampnm:sampnm
			},function(response){
				if(response.success==false){
					$.toast(response.msg);
					$.hidePreloader();
					return;
				}
				//样衣信息
				if(window.vm_sampleVO){//alert(1);
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
						distributeormtqt:function(event){
							showOd_info_unsave_tips(true);
							
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
								sizeVOs[i].orszqt=Math.floor(suitVO.ormtqt*(sizeVOs[i].szrate/szrate_sum));
								used_orszqt+=sizeVOs[i].orszqt;
							};
							//把剩余的量加到比率最大的规格上
							max_szrate_sizeVO.orszqt=max_szrate_sizeVO.orszqt+(suitVO.ormtqt-used_orszqt);
							
						}, //distribute 
						sumOrszqt:function(event){
							showOd_info_unsave_tips(true);
							
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
							var ormtqt=0;
							for(var i=0;i<sizeVOs.length;i++){
								ormtqt+=parseInt(sizeVOs[i].orszqt);
							}
							suitVO.ormtqt=ormtqt;
							
						}
					  }
					});
				}
				$.hidePreloader();
				
				$("#od_info_sample_info .card-content").show(200);
				$("#od_info_saveAndclear_button").show(200);
			}
		)
		
		
		
	}//function scan()

	$("#od_info_clear_button").click(function(){
		var sampno=window.vm_sampleVO.sampno
		$.post(Ext.ContextPath+"/ord/mobile/clearSampno.do",{sampno:sampno},function(response){
			window.vm_sampleVO.$data={};
			window.vm_od_info_suitVOs.suitVOs=[];//splice
			
			showOd_info_unsave_tips(false);
		},"json");
	});
	$("#od_info_save_button").click(function(){
		$.showPreloader("正在保存订货信息...");
		var suitVOs=window.vm_od_info_suitVOs.suitVOs;
		//console.log(suitVOs);
		//判断一个套件中的总量和各个规格加起来是否相等
		for(var i=0;i<suitVOs.length;i++){
			if(!suitVOs[i].ormtqt){
				continue;
			}
			var orszqt_sum=0;
			for(var j=0;j<suitVOs[i].sizeVOs.length;j++){
				orszqt_sum+=parseInt(suitVOs[i].sizeVOs[j].orszqt);
			}
			//console.log(suitVOs[i].ormtqt+"===="+orszqt_sum);
			if(suitVOs[i].ormtqt!=orszqt_sum){
				alert("<"+suitVOs[i].suitno_name+">的数据不一致!");
				//$.toast("<"+suitVOs[i].suitno_name+">的数据不一致!", 2345, 'success top');
				return;
			}
		}
		
		//console.log(data["suitVOs"]);
		//$.post(Ext.ContextPath+"/ord/mobile/create.do",suitVOs,function(response){
			
		//},"json");
		$.ajax({
			type:'post',
			url:Ext.ContextPath+"/ord/mobile/createOrddtl.do",
			data:JSON.stringify(suitVOs),
			dataType:'json',
			contentType :'application/json;charset=utf-8',
			//header 
			success:function(response, status, xhr) {
				if(response.success==false){
					$.toast(response.msg);
					$.hidePreloader();
					return;
				}
				$.toast("保存成功!");
				$.hidePreloader();
				showOd_info_unsave_tips(false);
			}
		});
	});
});


//我  里面的内容开发
$(function(){
	
	
});
