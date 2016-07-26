Ext={};
Ext.ContextPath="/od";
if(location.pathname.indexOf("/od")==-1){
	Ext.ContextPath="";
}
$.ajaxSettings.accepts.json="application/json;charset=UTF-8";
$(function(){
	
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
					//return;
				} else {
					//return;
				}
				$.hidePreloader();
		}
	}

	$("#bottom_bar a.tab-item").click(function(){
		$("#bottom_bar a.tab-item").removeClass("active");
		$(this).addClass("active");
		//window.nav_click_aaaa=true;
	});
	
	
		
})


$(function(){
	function check(){
		$.post(Ext.ContextPath+'/ord/mobile/yxgs/getOrstat.do', {  }, function(response){
			//alert(response.canConfirm);
			document.title = response.orgnm;
			document.last_title=response.orgnm;
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

//首页报表
$(function(){
	$.router.load("#report_first");
	$(document).on("pageInit", function(e, pageId, $page) {
	  if(pageId == "report_first") {//
	  	if(document.last_title){
			document.title=document.last_title;	
		}
		//document.title="概述";
		$.showPreloader();
		//$.condition.clear();
		showreport();
	  }   
//	  else {
//		  if(window.report_first_vm){
//			  window.report_first_vm.$data.todos=[];
//		  }
//		  
//		  $.closePanel();
//	  }
		if(pageId == "report_first" || pageId == "od_yxgs") {
		   $("#bottom_bar").show();
	    } else {
		   $("#bottom_bar").hide();
	    }
	});

	
	$("#report_first").on("click",".bradnoInfo",function(){
		$("#report_first .bradnoInfo .brandName").removeClass("bradnoSelect");
		first_bradno=$(this).children(".brandName").addClass("bradnoSelect").data("bradno");
		queryChartData(first_bradno);
	});

	function showreport(){
		var search=location.search;
		//alert(search);
		$.post(Ext.ContextPath+'/mobile/report/queryReportFirst_allBradno.do'+search, {}, function(response){
					renderReport(response);
		},'json');
	}
	var first_bradno="";
	function renderReport(response){
		if(response.success==false){
			$("#report_first").html(response.msg);
			$("#bottom_bar").hide();
			return;
		}
		if(response.todos.length>0){
			if(!first_bradno){
				first_bradno=response.todos[0].bradno;
			}
		}
		if(window.report_first_vm){
			//console.log(response);
			window.report_first_vm.$data.todos=response.todos;
			window.report_first_vm.$data.orgnm=response.orgnm;
			
			
		} else {
			window.report_first_vm=new Vue({//这个不能删除，不然模板没了
			  el: '#report_first',
			  data: {
				todos:response.todos,
				orgnm:response.orgnm
			  },
			});
			$("#report_first .content").show();	
		}
		queryChartData(first_bradno);
		$.hidePreloader();
		setTimeout(function(){
			$("#report_first .bradnoInfo .brandName").removeClass("bradnoSelect").each(function(index, element) {
					//alert($(this).data("bradno")==first_bradno);
					if($(this).data("bradno")==first_bradno){
						$(element).addClass("bradnoSelect");
					}
				});

		},500)
		
	}
	//setTimeOut("showreport",60000);
	//从后台获取数据
	function queryChartData(bradno){
		$.post(Ext.ContextPath+'/mobile/report/queryReportFirst_bradno.do', {bradno:bradno}, function(response){
			show_ormtam_chart(response);
			show_ormtqm_chart(response);
		},'json');
	}

	 var chart_width=$(window).width()-180;
	 var char_height=$(window).height()/2-35;
	$("#report_first_ormtam_chart").width(chart_width).height(char_height);
	$("#report_first_ormtqt_chart").width(chart_width).height(char_height);
	
	function show_ormtam_chart(response){	
        var myChart = echarts.init(document.getElementById('report_first_ormtam_chart'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                show:false
            },
            tooltip: {show:false},
            legend: {
				show:false
            },
            xAxis: {
                data: ["计划","已订"]
            },
            yAxis: {show : false},
			color: ['#3398DB'],
            series: [{
                //name: '销量',
                type: 'bar',
				label: {
					normal: {
						position: 'top',
						show: true
						//formatter: '{b}'
					}
				},
                data: [	response.qymtam, response.ormtam]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
	}
	function show_ormtqm_chart(response){
        var myChart = echarts.init(document.getElementById('report_first_ormtqt_chart'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                show:false
            },
            tooltip: {show:false},
            legend: {
				show:false
            },
            xAxis: {
                data: ["计划","已订"]
            },
            yAxis: {show : false},
			color: ['#3398DB'],
            series: [{
                //name: '销量',
                type: 'bar',
				label: {
					normal: {
						position: 'top',
						show: true
						//formatter: '{b}'
					}
				},
                data: [	response.qymtqt, response.ormtqt]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
	}
	

});