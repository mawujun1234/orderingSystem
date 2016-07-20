//查询条件--组件功能
$(function(){
	$.condition={};
	$.condition.params={};
	$.condition.init=function(config){
		var me=this;
//		//获取品牌和大类
//		$.post(Ext.ContextPath+'/mobile/report/queryCondition.do', {  }, function(response){
//			//$("#od_loginpage_title").html(response.ormtnm);
//			$("#report_query_params_bradno .card-content-inner").html(me.render(response.cond_bradno));
//			$("#report_query_params_spclno .card-content-inner").html(me.render(response.cond_spclno));
//		},'json');
		
		me.queryBradnoCondition();
		
		//品牌中的按钮点击的时候
		$("#report_query_params").on('click', '#report_query_params_bradno .param-btn', function(e){ 
			$.condition.params.bradno=$(this).data("value");
			//console.log($.condition.params.bradno);
			$(this).siblings(".button-success").removeClass("button-success");
			$(this).addClass("button-success");
			
			//查询小类
			me.querySptynoCondition($.condition.params.bradno,$.condition.params.spclno);
			//系列
			me.querySpsenoCondition($.condition.params.bradno,$.condition.params.spclno);
		});
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
	$.condition.render=function(array,aaaaa){
		var me=this;
		//当重选品牌和大类后，需要对小类和系列重新进行筛选,如果发现选中的参数，在重新筛选中没有发现，
		//则要把这个参数设置为null
		//还有换大类后，这两个参数也要进行清空
		var bool=false;
		var html='';
		for(var i=0;i<array.length;i++){
			var btn_class="";
			if(array[i].value==me.params[aaaaa]){
				bool=true;
				btn_class='button-success';
			}
			html+='<a href="#" class="button button-light param-btn  button-fill '+btn_class+'"  data-value="'+array[i].value+'" data-name="'+array[i].name+'">'+array[i].name+'</a>'
			
		}
		html+='<div style="clear:both;"></div>';
		if(!bool){
			delete me.params[aaaaa];
		}
		//console.log(html);
		return html;
	}
});

//报表统计--按商品类
$(function(){
	$.condition.init();
});