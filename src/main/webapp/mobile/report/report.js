//查询条件--组件功能
$(function(){
	$.condition={};
	$.condition.params={};
	$.condition.init=function(config){
		var me=this;
		me.initConfig=config;
		me.initCollage();
		//显示大类的查询条件
		if(config.bradno){
			me.queryBradnoCondition();
			$("#report_query_params_bradno").show();
			
			//品牌中的按钮点击的时候
			$("#report_query_params").on('click', '#report_query_params_bradno .param-btn', function(e){ 
				$.condition.params.bradno=$(this).data("value");
				//console.log($.condition.params.bradno);
				$(this).siblings(".button-success").removeClass("button-success");
				$(this).addClass("button-success");
				if(config.spclno){
					//查询小类
					me.querySptynoCondition($.condition.params.bradno,$.condition.params.spclno);
					//系列
					me.querySpsenoCondition($.condition.params.bradno,$.condition.params.spclno);
				}
			});
		}
		//现在暂时规则是 只要大类显示，那就显示小类和系列
		if(config.spclno){
			$("#report_query_params_spclno").show();
			$("#report_query_params_sptyno").show();
			$("#report_query_params_spseno").show();
			
			//大类中的按钮点击的时候
			$("#report_query_params").on('click', '#report_query_params_spclno .param-btn', function(e){ 
				var spclno=$(this).data("value")+"";
				//这个是临时解决方案
				if(spclno.length==1){
					spclno="0"+spclno;
				}
				$.condition.params.spclno=spclno;
				$(this).siblings(".button-success").removeClass("button-success");
				$(this).addClass("button-success");
				
				//查询小类
				me.querySptynoCondition($.condition.params.bradno,$.condition.params.spclno);
				//系列
				me.querySpsenoCondition($.condition.params.bradno,$.condition.params.spclno);
				
			});
			
			$("#report_query_params").on('click', '#report_query_params_sptyno .param-btn', function(e){ 
				$.condition.params.sptyno=$(this).data("value");
				//console.log($.condition.params.bradno);
				$(this).siblings(".button-success").removeClass("button-success");
				$(this).addClass("button-success");
			});
			$("#report_query_params").on('click', '#report_query_params_spseno .param-btn', function(e){ 
				$.condition.params.spseno=$(this).data("value");
				//console.log($.condition.params.bradno);
				$(this).siblings(".button-success").removeClass("button-success");
				$(this).addClass("button-success");
			});
		}
		//显示订货单位
		if(config.ordorg){
			$("#report_query_params_ordorg").show();
			me.queryOrdorgCondition();
			
			$("#report_query_params").on('click', '#report_query_params_ordorg .param-btn', function(e){ 
				$.condition.params.ordorg=$(this).data("value");
				//console.log($.condition.params.bradno);
				$(this).siblings(".button-success").removeClass("button-success");
				$(this).addClass("button-success");
			});
		}
		
		
	}
	
	$.condition.queryBradnoCondition=function(){
		var me=this;
		$.post(Ext.ContextPath+'/mobile/report/queryBradnoCondition.do', {  }, function(response){
			var sel_bradno=response.sel_bradno;
			$.condition.params.bradno=sel_bradno;//这里必须放在前面
			
			$("#report_query_params_bradno .card-content-inner").html(me.render(response.cond_bradno,'bradno'));
			//查询大类
			me.querySpclnoCondition(sel_bradno);
		},'json');
	}
	$.condition.querySpclnoCondition=function(bradno){
		if(!bradno){
			$.alert("请先选择品牌!");return;
		}
		var me=this;
		$.post(Ext.ContextPath+'/mobile/report/querySpclnoCondition.do', { bradno:bradno }, function(response){
			$("#report_query_params_spclno .card-content-inner").html(me.render(response.cond_spclno,'spclno'));
		},'json');
	}
	$.condition.querySptynoCondition=function(bradno,spclno){
		if(!bradno){
			$.alert("请先选择品牌!");return;
		}
		if(!spclno){
			//$.alert("请先选择大类!");
			return;
		}
		var me=this;
		$.post(Ext.ContextPath+'/mobile/report/querySptynoCondition.do', {bradno:bradno,spclno:spclno  }, function(response){
			//$("#od_loginpage_title").html(response.ormtnm);
			$("#report_query_params_sptyno .card-content-inner").html(me.render(response.cond_sptyno,'sptyno'));
		},'json');
	}
	$.condition.querySpsenoCondition=function(bradno,spclno){
		if(!bradno){
			$.alert("请先选择品牌!");return;
		}
		if(!spclno){
			//$.alert("请先选择大类!");
			return;
		}
		var me=this;
		$.post(Ext.ContextPath+'/mobile/report/querySpsenoCondition.do', {bradno:bradno, spclno:spclno }, function(response){
			//$("#od_loginpage_title").html(response.ormtnm);
			$("#report_query_params_spseno .card-content-inner").html(me.render(response.cond_spseno),'spseno');
		},'json');
	}
	$.condition.queryOrdorgCondition=function(){
		var me=this;
		$.post(Ext.ContextPath+'/mobile/report/queryOrdorgCondition.do', { }, function(response){
			$("#report_query_params_ordorg .card-content-inner").html(me.render(response.cond_ordorg,'ordorg'));
		},'json');
	}
	$.condition.render=function(array,cond_type){
		var me=this;
		//当重选品牌和大类后，需要对小类和系列重新进行筛选,如果发现选中的参数，在重新筛选中没有发现，
		//则要把这个参数设置为null
		//还有换大类后，这两个参数也要进行清空
		var bool=false;
		var html='';
		for(var i=0;i<array.length;i++){
			var btn_class="";
			if(array[i].value==me.params[cond_type]){
				bool=true;
				btn_class='button-success';
			}
			html+='<a href="#" class="button button-light param-btn  button-fill '+btn_class+'"  data-value="'+array[i].value+'" data-name="'+array[i].name+'">'+array[i].name+'</a>'
			
		}
		html+='<div style="clear:both;"></div>';
		if(!bool){
			delete me.params[cond_type];
		}
		//console.log(html);
		return html;
	}
	
	$.condition.clear=function(){
		$("#report_query_params .card .card-content-inner .button-success").removeClass("button-success");
		$.condition.params={};
	}
	//初始化展开，收缩，事件
	$.condition.initCollage=function(){
		$("#report_query_params .card .card-header .icon").on("click",function(){
			if($(this).hasClass("icon-up")){
				$(this).removeClass("icon-up").addClass("icon-down").parent().next().hide();
			} else {
				$(this).removeClass("icon-down").addClass("icon-up").parent().next().show();
			}
		});
		
		//初始化按钮事件
		$("#report_query_params #report_query_params_reset").on("click",function(){
			$.condition.clear();
		});
		$("#report_query_params #report_query_params_complete").on("click",function(){
			//alert("使用事件总线");
			$.condition.initConfig.submit($.condition);
			$.closePanel();
		});
	}
});


//none_abstat :未订的必定款样衣
$(function(){
	$(document).on("pageInit", "#none_abstat", function(e, pageId, $page) {
		document.title="未订的必定款样衣";
		show_none_abstat_report();
	});
	
	function show_none_abstat_report(){
		$.showPreloader();
		$.post(Ext.ContextPath+"/mobile/report/query_none_abstat.do",{},function(response){
			////
//			new Vue({
//			  el: '#none_abstat',
//			  data: {
//				sanmpes: response
//			  }
//			});
			var html="";
			for(var i=0;i<response.length;i++){
					html+='<li class="item-content">'+
					'<div class="item-media"><i class="icon icon-f7"></i></div>'+
					'<div class="item-inner"><div class="item-title">'+response[i].sampnm1+'</div></div>'+
					'</li>';
			}
			$("#none_abstat_ul").html(html);
			
			$.hidePreloader();
		});
	}
});

//报表统计--按商品类
$(function(){
	$.condition.init({
		bradno:true,
		//spclno:true,
		ordorg:true,
		submit:function(condition){
			console.log(condition.params)
		}
	});

});