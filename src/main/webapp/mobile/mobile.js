Ext={};
Ext.ContextPath="/od";
if(location.pathname.indexOf("/od")==-1){
	Ext.ContextPath="";
}

//显示和隐藏今日订货会快结束的信息
window.od_closeing_info=null;//提高性能
window.canOrd=null;//判断是否可以订货
window.setTimeout_id=null;
function show_od_closeing_info(){
	//刷新的时候调用
	if(!sessionStorage["user"]){
		return;
	}
	if(window.setTimeout_id){
		clearTimeout(window.setTimeout_id) ;
	}
	//
	$.post(Ext.ContextPath+'/ord/mobile/checked_closeing_info.do', {  }, function(response){
		if(response.success==false){
			if(response.msg){
				$.alert(response.msg);return;
			} else {
				return;
			}
		}
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
		//如果已经确认了的话
		if(response.canConfirm==4){
			//$("#od_mypage_confirm_button").hide();
			//$("#od_info").html("<div style='margin:110px auto;text-align:center;color:green;'>订单已经确认，不能再扫描!</div>");
			$("#od_mypage_confirm_button").hide();
			$("#od_mypage_over_button").hide();
			$("#od_info_cannot_order").show();
			$("#od_info_cannot_order").html("订货已结束，不能扫描!");
			$("#qrcode_button").hide();
			
			//大区领导进来的界面，要显示现场订货完成
			$("#od_yxgs_ordering_over").show();
		} else if(response.canConfirm==3){
			$("#od_mypage_confirm_button").hide();
			$("#od_mypage_over_button").show().css("display","block");
			$("#qrcode_button").show();
			$("#od_info_cannot_order").hide();
		} else if(response.canConfirm==2){
			//$("#od_mypage_over_button").show().css("display","block");
			$("#od_info_cannot_order").show();
			$("#od_info_cannot_order").html("订单未审批，不能平衡!");
			$("#qrcode_button").hide();
			//$("#od_info").html("<div style='margin:110px auto;text-align:center;color:green;'>订单未审批，不能平衡!</div>");
		} else {
			$("#od_mypage_confirm_button").show().css("display","block");	
			//$("#od_mypage_over_button").hide();
			//$("#qrcode_button").show();
		}
	});
	window.setTimeout_id=setTimeout("show_od_closeing_info()",120*1000);
}
show_od_closeing_info();

$(function(){

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
		window.nav_click_aaaa=true;
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
	

});

//=========================================================登录相关
$(function(){
	$.post(Ext.ContextPath+'/ord/mobile/getOrdmt.do', {  }, function(response){
		$("#od_loginpage_title").html(response.ormtnm);	
	},'json');
	
	window.user=null;
	if(!window.user){
		//$.router.load("#od_loginpage"); 
		restoreuser();
	}
	function storeuser(user){
		sessionStorage["user"]=JSON.stringify(user);
	}
	//从localstore中恢复用户的信息
	function restoreuser(){
		var user_str=sessionStorage["user"];
		if(user_str){
			window.user=JSON.parse(user_str);
		}
		//alert(sessionStorage["active_nav_id"]);
		if(sessionStorage["active_nav_id"]){//alert(2);
			$("#bottom_bar").show();
			$(sessionStorage["active_nav_id"]).addClass("active");
		}
	}
	$(document).on("pageInit", function(e, pageId, $page) {
	  window.nav_click_aaaa=false;
	  if(pageId != "od_loginpage" && !window.user) {
		 $.router.load("#od_loginpage"); 
		 return;
	  }

 	  if(pageId == "od_info" || pageId == "od_mypage") {
		   $("#bottom_bar").show();
		  
		  $("#bottom_bar a.tab-item").removeClass("active");
	      $("#bottom_bar_"+pageId).addClass("active");
		  
		  sessionStorage["active_nav_id"]="#bottom_bar_"+pageId;
		  
	  } else {
		 $("#bottom_bar").hide();
	  }
	 // if(pageId == "od_loginpage") {
//		  $("#bottom_bar").hide();
//	  } else {
//		  $("#bottom_bar").show();
//		  
//		  $("#bottom_bar a.tab-item").removeClass("active");
//	      $("#bottom_bar_"+pageId).addClass("active");
//		  
//		  sessionStorage["active_nav_id"]="#bottom_bar_"+pageId;
//	  }
	  
	 
	});
//	$(document).on("beforePageSwitch", function(e, pageId, $page) {
//		//还要添加一个判断，这个判断是说不是通过菜单进行按的
//		if(!window.nav_click_aaaa && (pageId=="od_info" || pageId=="od_mypage")) {
//			alert("准备后退");
//			return false;
//		}
//		
//	});
	
	function login(loginname,password,isscan){
		$.showPreloader("正在登录...");
		$.post(Ext.ContextPath+"/user/mobile/login.do",
			{
				username:loginname,
				password:password,
				isscan:isscan
			},function(response){
				$.hidePreloader();
				if(response.success==false){
					$.toast(response.msg);
				} else {
					window.user=response;
					storeuser(window.user);
					//如果是营销公司，就进入到营销公司的界面
					if(window.user.channo=='YXGS'){
						//$.router.load("#od_yxgs"); 
						window.location.href="./yxgs.html";
					} else {
						$.router.load("#od_info"); 
					}
					
					document.title = response.orgnm;
					
					show_od_closeing_info();
				}
				
			}
		)//$.post
	}
	$("#od_loginpage_login_btn").click(function(){
		var od_loginpage_username=$("#od_loginpage_username").val();
		var od_loginpage_password=$("#od_loginpage_password").val();
		if(!od_loginpage_username){
			$.toast("请输入用户名!");return;
		}
		if(!od_loginpage_password){
			$.toast("请输入密码!");return;
		}
		login(od_loginpage_username,od_loginpage_password,false);
	//	$.showPreloader("正在登录...");
//		$.post(Ext.ContextPath+"/user/mobile/login.do",
//			{
//				username:od_loginpage_username,
//				password:od_loginpage_password,
//				url:location.href.split('#')[0]
//			},function(response){
//				//console.log(response);
//				//var obj=response;//JSON.parse(response.responseText);
//				//console.log(obj);
//				//alert(1);
//				$.hidePreloader();
//				if(response.success==false){
//					$.toast(response.msg);
//				} else {
//					window.user=response;
//					$.router.load("#od_info"); 
//
//					//response.wxConfig.debug=false;
//					//response.wxConfig.jsApiList=['scanQRCode'];
//					//wx.config(response.wxConfig);
//					////wx.hideAllNonBaseMenuItem();
//					
//					document.title = response.orgnm;
//					
//					show_od_closeing_info();
//				}
//				
//			}
//		)
	});
	
	//注册微信的配置信息
	$.post(Ext.ContextPath+'/user/mobile/getWxConfig.do', { url:location.href.split('#')[0] }, function(response){
		//console.log(response);
		response.debug=false;
		response.jsApiList=['scanQRCode'];
		wx.config(response);
		//wx.hideAllNonBaseMenuItem();
	},'json');
	//扫描登录
	$("#od_loginpage_scanQRCode_btn").click(function(){
		//alert(1);
		wx.scanQRCode({
			desc: 'scanQRCode desc',
			needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			success: function (res) {
				var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
				//alert(result);
				var str=result.split("+##+");
				//alert(str[0]);
				//alert(str[1]);
				login(str[0],str[1],true);
			}
		});
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
	
	$("#od_info_input").on('focus', function(e){ 
	
		$(this).get(0).select();
		//alert($(this).get(0));
	});
	$("#od_info_scan_button").click(function(){
		var val=$("#od_info_input").val();

		if(!val){
			return;
		}
		val=val.replace('(必定款)','');
		
		if(!od_info_data_issaved()){
			$.confirm('数据未保存！确定要进行切换吗？', function () {
				scan(val);
			});//$.confirm('Are you sure?', function () {
		}//if(od_info_data_issaved()){	
		else {
			scan(val);
		}
		
	});
	//扫一扫
	$("#od_info_scanQRCode_btn").click(function(){
		wx.scanQRCode({
			desc: 'scanQRCode desc',
			needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			success: function (res) {
				var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
//alert(result);
				var codes=result.split(',');
				if(codes.length>1){
					scan(codes[1]);
				} else {
					scan(result);
				}
				
			}
		});
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
					//clearSampleInfo();
					if(window.last_query_sampnm ){
						window.vm_sampleVO.sampnm=window.last_query_sampnm;
					}
					return;
				}
				window.last_query_sampnm=sampnm;
				
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
							
							var ormtqt=suitVO.ormtqt;//输入的总的数量
							var used_orszqt=0;//已经使用掉的数量
							var max_szrate_sizeVO=sizeVOs[0];//持有最大比率的规格,默认是第一个
							var max_szrate=0;//最大的比率
							for(var i=0;i<sizeVOs.length;i++){
								//如果是标准箱的时候
								if(sizeVOs[i].sizety=='PRDPK') {
									//可以凑成几箱
									var xiang_temp=Math.floor((suitVO.ormtqt*sizeVOs[i].szrate)/sizeVOs[i].sizeqt);
									sizeVOs[i].orszqt=xiang_temp;
									//还有多少数量
									ormtqt=ormtqt-xiang_temp*sizeVOs[i].sizeqt;
									
								} else {//计算单规的数量
									//获取持有最大比率的单规
									if(sizeVOs[i].szrate>max_szrate){
										max_szrate=sizeVOs[i].szrate;
										max_szrate_sizeVO=sizeVOs[i];
									}
									sizeVOs[i].orszqt=Math.floor(ormtqt*(sizeVOs[i].szrate/szrate_sum));
									used_orszqt+=sizeVOs[i].orszqt;
								}
							};
							//把剩余的量加到比率最大的规格上
							max_szrate_sizeVO.orszqt=max_szrate_sizeVO.orszqt+(ormtqt-used_orszqt);
							
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
								ormtqt+=parseInt(sizeVOs[i].orszqt*sizeVOs[i].sizeqt);
							}
							suitVO.ormtqt=ormtqt;
							
						}
					  }
					});
				}
				$.hidePreloader();
				
				if(response.sampleVO.abstat==1){
					$("#od_info_input").val(sampnm+"(必定款)");	
				}
				$("#od_info_sample_info .card-content").show(200);
				$("#od_info_saveAndclear_button").show(200);
			}
		)
		
		
		
	}//function scan()
	
	$("#od_info").on('click', 'input[type=number]', function(e){ 
		//console.log(this.value);
		$(this).get(0).select();
	});
	$("#od_info").on('input', 'input[type=number]', function(e){ //alert(1);
		//console.log(this.value);
		this.value=this.value.replace(/\D/g,'');//.replace('/^[0-9]*[1-9][0-9]*$/g');//this.value.replace('/[^0-9]/g');
		this.value=parseInt(this.value);//.replace('.','')
		console.log(e.keyCode);
		//console.log(this.value+"====");
	});
	
	function clearSampleInfo(){
		if(window.vm_sampleVO){
			window.vm_sampleVO.$data={};
		}
		if(window.vm_od_info_suitVOs){
			window.vm_od_info_suitVOs.suitVOs=[];//splice
		}
	}

	$("#od_info_clear_button").click(function(){
		var sampno=window.vm_sampleVO.sampno
		//$.post(Ext.ContextPath+"/ord/mobile/clearSampno.do",{sampno:sampno},function(response){
			//clearSampleInfo();
			//只是把数量全部清空0，不是清除数据
			var suitVOs=window.vm_od_info_suitVOs.suitVOs;
			for(var i=0;i<suitVOs.length;i++){
				suitVOs[i].ormtqt=0;
				var sizeVOs=suitVOs[i].sizeVOs;
				for(var j=0;j<sizeVOs.length;j++){
					sizeVOs[j].orszqt=0;
				}
				
			}
			
			showOd_info_unsave_tips(true);
		//},"json");
	});
	$("#od_info_save_button").click(function(){
		
		$.showPreloader("正在保存订货信息...");
		var suitVOs=window.vm_od_info_suitVOs.suitVOs;
		//console.log(suitVOs);
		//判断一个套件中的总量和各个规格加起来是否相等
		var aa=0;
		for(var i=0;i<suitVOs.length;i++){
			if(!suitVOs[i].ormtqt){
				continue;
			}
			var orszqt_sum=0;
			for(var j=0;j<suitVOs[i].sizeVOs.length;j++){
				orszqt_sum+=parseInt(suitVOs[i].sizeVOs[j].orszqt*suitVOs[i].sizeVOs[j].sizeqt);
			}
			//console.log(suitVOs[i].ormtqt+"===="+orszqt_sum);
			if(suitVOs[i].ormtqt!=orszqt_sum){
				//alert("<"+suitVOs[i].suitno_name+">的数据不一致!");
				$.alert("<"+suitVOs[i].suitno_name+">的数据不一致!");
				$.hidePreloader();
				return;
			}
			aa+=orszqt_sum;
		}
		if(window.vm_sampleVO.abstat==1 && aa==0){
			$.alert("必定款的数量不能输入0");
			$.hidePreloader();
			return;
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
	function queryMyInfoVO (){
		$.post(Ext.ContextPath+"/ord/mobile/queryMyInfoVO.do",{},function(response){
			//od_mypage_myinfo_card
			$("#od_mypage_myinfo_card").show();
			//样衣信息
			if(window.vm_myinfo_card){//alert(1);
				window.vm_myinfo_card.$data=response;
			} else {
				window.vm_myinfo_card=new Vue({
					el: '#od_mypage_myinfo_card',
					data: response
				});
			}
		},"json");
	}
	//当切换到我的页面，然后刷新页面的时候，也要重新获取这个数据
	if( sessionStorage["active_nav_id"]=="#bottom_bar_od_mypage"){
		queryMyInfoVO ();
	}
	//点击菜单切换过来的时候
	$(document).on("pageInit", function(e, pageId, $page) {
	  if(pageId == "od_mypage") {
		  queryMyInfoVO ();
	  } 
	});
	
	$("#od_mypage_confirm_button").click(function(){
		$.post(Ext.ContextPath+"/ord/mobile/confirm.do",{},function(response){
			//od_mypage_myinfo_card
			if(response.success==false){
				//$.toast(response.msg);
				//$.popup('.popup_abstat');
				var sampnms=JSON.parse(response.msg);
				//var sampnms=response.msg;
				var html="";
				for(var i=0;i<sampnms.length;i++){
					html+='<li class="item-content">'+
					'<div class="item-media"><i class="icon icon-f7"></i></div>'+
					'<div class="item-inner"><div class="item-title">'+sampnms[i]+'</div></div>'+
					'</li>';
				}
				 var popupHTML = 
				 '<div class="popup">'+
				 	''+
                    '<div class="content-block">'+
                      '<div class="content-block-title">下列出样样衣编号是必定款: <a style="display:block;width:60px;position:absolute;top:0px;right:0px;" class="close-popup">关闭</a></div>'+
                      '<div class="list-block">'+
					  '<ul>'+
					  //'<li class="item-content">'+
					 // '<div class="item-media"><i class="icon icon-f7"></i></div>'+
					 // '<div class="item-inner">'+
					  html+
					  //'<div class="item-title">商品名称</div>'+
					  //'<div class="item-after">杜蕾斯</div>'+
					 // '</div>'+
					 // '</li>'+
					  '</ul>'+
					  '</div>'+
                    '</div>'+
                  '</div>'
  				$.popup(popupHTML);
				return;
			}
			//$("#od_mypage_confirm_button").hide();
			//$("#od_info").html("<div style='margin:110px auto;text-align:center;color:green;'>订单已经确认，不能再扫描!</div>");
			$("#od_mypage_confirm_button").hide();
			$("#od_info_cannot_order").show();
			$("#od_info_cannot_order").html("订单未审批，不能平衡!");
			$("#qrcode_button").hide();
		},"json");
	});
	$("#od_mypage_over_button").click(function(){
		 $.showPreloader('正在处理...');
		$.post(Ext.ContextPath+"/ord/mobile/confirm2.do",{},function(response){
			$.hidePreloader();
			if(response.success==false){
				return;
			}
			show_od_closeing_info();
		});
	});
	
});
