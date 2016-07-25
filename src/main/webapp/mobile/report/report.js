//查询条件--组件功能
$(function(){
	$.condition={};
	$.condition.params={};
	$.condition._readyIndex=0;
	$.condition.init=function(config){
		var me=this;
		me.initConfig=config;
		///me.selectFirstConfig=
		me.initCollage();
		//显示品牌的查询条件
		if(config.bradno){
			me.queryBradnoCondition();
			$("#report_query_params_bradno").show();
			
			//品牌中的按钮点击的时候
			$("#report_query_params").on('click', '#report_query_params_bradno .param-btn', function(e){ 
				//$.condition.params.bradno=$(this).data("value");
				$.condition._setParam($(this),'bradno');
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
		} else {
			$("#report_query_params_bradno").hide();
		}
		//现在暂时规则是 只要大类显示，那就显示小类和系列
		if(config.spclno){
			$("#report_query_params_spclno").show();
			$("#report_query_params_sptyno").show();
			$("#report_query_params_spseno").show();
			
			//大类中的按钮点击的时候
			$("#report_query_params").on('click', '#report_query_params_spclno .param-btn', function(e){ 
				//var spclno=$(this).data("value")+"";
//				//这个是临时解决方案
//				if(spclno.length==1){
//					spclno="0"+spclno;
//				}
//				$.condition.params.spclno=spclno;
				$.condition._setParam($(this),'spclno');
				
				$(this).siblings(".button-success").removeClass("button-success");
				$(this).addClass("button-success");
				
				//查询小类
				me.querySptynoCondition($.condition.params.bradno,$.condition.params.spclno);
				//系列
				me.querySpsenoCondition($.condition.params.bradno,$.condition.params.spclno);
				
			});
			
			$("#report_query_params").on('click', '#report_query_params_sptyno .param-btn', function(e){ 
				//$.condition.params.sptyno=$(this).data("value");
				$.condition._setParam($(this),'sptyno');
				//console.log($.condition.params.bradno);
				$(this).siblings(".button-success").removeClass("button-success");
				$(this).addClass("button-success");
			});
			$("#report_query_params").on('click', '#report_query_params_spseno .param-btn', function(e){ 
				//$.condition.params.spseno=$(this).data("value");
				$.condition._setParam($(this),'spseno');
				//console.log($.condition.params.bradno);
				$(this).siblings(".button-success").removeClass("button-success");
				$(this).addClass("button-success");
			});
		} else {
			$("#report_query_params_spclno").hide();
			$("#report_query_params_sptyno").hide();
			$("#report_query_params_spseno").hide();
		}
		//显示订货单位
		if(config.ordorg){
			$("#report_query_params_ordorg").show();
			me.queryOrdorgCondition();
			
			$("#report_query_params").on('click', '#report_query_params_ordorg .param-btn', function(e){ 
				//$.condition.params.ordorg=$(this).data("value");
				$.condition._setParam($(this),'ordorg');
				//console.log($.condition.params.bradno);
				$(this).siblings(".button-success").removeClass("button-success");
				$(this).addClass("button-success");
			});
		} else {
			$("#report_query_params_ordorg").hide();
		}
		
		
	}
	//$.condition._setParam($(this),'bradno');
	$.condition._setParam=function(elm,cond_type){
		if(cond_type=='spclno'){
			var spclno=elm.data("value")+"";
			//这个是临时解决方案
			if(spclno.length==1){
				spclno="0"+spclno;
			}
			$.condition.params.spclno=spclno;
		} else {
			$.condition.params[cond_type]=elm.data("value");
		}
		
				
	}
	
	$.condition.queryBradnoCondition=function(){
		var me=this;
		$.post(Ext.ContextPath+'/mobile/report/queryBradnoCondition.do', {  }, function(response){
			var sel_bradno=response.sel_bradno;
			$.condition.params.bradno=sel_bradno;//这里必须放在前面
			
			$.condition._readyIndex++;
			//alert($.condition._readyIndex);
			if($.condition._readyIndex>=me.initConfig.selectFirstConfig.readyIndex){
				$.condition.initConfig.submit($.condition);
			}
			
			$("#report_query_params_bradno .card-content-inner").html(me.render(response.cond_bradno,'bradno'));
			if(me.initConfig.spclno){
				//查询大类
				me.querySpclnoCondition(sel_bradno);
			}
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
			$.condition.params.ordorg=response.sel_ordorg;//这里必须放在前面
			$.condition._readyIndex++;
			
			//if($.condition._readyIndex>=me.initConfig.selectFirstConfig.readyIndex){
			//	$.condition.initConfig.submit($.condition);
			//}
			
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
	//清空所有的数据
	$.condition.clear=function(){
		$("#report_query_params .card .card-content-inner .button-success").removeClass("button-success");
		$.condition.params={};
		//$.condition.selectFirst();
	}
	//默认设置第一个值为默认选择
	$.condition.selectFirst=function(config){
		if(!config){
			config=this.initConfig.selectFirstConfig;
		} 
		
		if(config.bradno){
			var elm=$("#report_query_params #report_query_params_bradno .param-btn").eq(0);
			//console.log(elm);
			elm.addClass("button-success");
			$.condition._setParam(elm,'bradno');
		}
		if(config.ordorg){
			var elm=$("#report_query_params #report_query_params_ordorg .param-btn").eq(0);
			//elm.addClass("button-success");
			$.condition._setParam(elm,'ordorg');
		}
	}
	//初始化展开，收缩，事件
	$.condition.initCollage=function(){
		$("#report_query_params .card .card-header .icon").off("click").on("click",function(){
			if($(this).hasClass("icon-up")){
				$(this).removeClass("icon-up").addClass("icon-down").parent().next().hide();
			} else {
				$(this).removeClass("icon-down").addClass("icon-up").parent().next().show();
			}
		});
	}
	
	//初始化按钮事件
		$("#report_query_params #report_query_params_reset").on("click",function(){
			$.condition.clear();
			$.condition.selectFirst();
			$.condition.initConfig.submit($.condition);
			$.closePanel();
		});
		$("#report_query_params #report_query_params_complete").on("click",function(){
			//alert("使用事件总线");
			$.condition.initConfig.submit($.condition);
			$.closePanel();
		});
});


//none_abstat :未订的必定款样衣
$(function(){
	$(document).on("pageInit", "#none_abstat", function(e, pageId, $page) {
		document.title="未订的必定款样衣";
		if(document.last_view_pageId=='sample_info'){
			return;
		}
		show_none_abstat_report();
	});
	
	function show_none_abstat_report(){
		$.showPreloader();
		$.post(Ext.ContextPath+"/mobile/report/query_none_abstat.do",{},function(response){
			var html="";
			for(var i=0;i<response.length;i++){
					html+='<li>'+
					'<a  href="#sample_info" class="sample_info_link item-link item-content" style="color:#0894ec;" data-sampnm1="'+response[i].sampnm1+'">'+
					//'<div class="item-media"><i class="icon icon-f7"></i></div>'+
					'<div class="item-inner"><div class="item-title">'+response[i].sampnm1+'</div></div>'+
					'</a>'+
					'</li>';
			}
			$("#none_abstat_ul").html(html);
			
			$.hidePreloader();
		});
	 }
	 
	 $("#none_abstat").on("click","a.sample_info_link",function(){
		window.sample_info_sampno=null;
		window.sample_info_sampnm1=$(this).data("sampnm1");
	 });
});

//报表统计--按商品类
$(function(){
	$(document).on("pageInit", function(e, pageId, $page) {
	  if(pageId == "report_spclno") {
		document.title="订货统计-按商品类";
		if(document.last_view_pageId=='report_sptyno_spseno'){
			return;
		}
		$.showPreloader();
		$.condition.clear();
		showreport();
	  } else {
		  //window.report_spclno_vm.$data.todos=[];
		if(window.report_spclno_vm && pageId!='report_sptyno_spseno'){
			window.report_spclno_vm.$data.todos=[];
		}
		 
		  $.closePanel();
	  }
	});

	function showreport(){
		$.condition.init({
			bradno:true,
			//spclno:true,
			ordorg:true,
			//
			selectFirstConfig:{//等着几个必须的条件准备好后，就自动进行查询
				bradno:true,//后台也要改，临时的
				//ordorg:false,//后台也要改，临时的
				readyIndex:1//临时解决方案，要初始化的查询条件的个数，等查询条件个数完成后，就自动进行查询，
			},
			submit:function(condition){
				//console.log(condition.params)
				$.post(Ext.ContextPath+'/mobile/report/queryReportSplcno.do', condition.params, function(response){
					renderReport(response);
				},'json');
			}
		});
	}
	
	function renderReport(response){
		if(window.report_spclno_vm){
			//console.log(response);
			window.report_spclno_vm.$data.todos=response;
		} else {
			window.report_spclno_vm=new Vue({
			  el: '#report_spclno',
			  data: {
				todos:response
			  }
			});
			$("#report_spclno .content").show();	
		}
		$.hidePreloader();		
	}
	
	
	$("#report_spclno").on("click",".report_spclno_click",function(){
		var spclno=$(this).data("spclno");
		if(spclno.indexOf("total")!=-1){
			$.toast("合计不能下钻!");
			return;
		}
		var buttons1 = [
        {
          text: '请选择下钻到:',
          label: true
        },
        {
          text: '小类',
          bold: true,
          color: 'danger',
          onClick: function() {
            $.condition.params.spclno=spclno;
			//$.condition.params.tyno=2;
			$.showPreloader();
			$.post(Ext.ContextPath+'/mobile/report/queryReportSptyno.do', $.condition.params, function(response){
				renderReportSptynoSpseno(response);
			},'json');
          }
        },
        {
          text: '系列',
          onClick: function() {
            $.condition.params.spclno=spclno;
			//$.condition.params.tyno=5;
			$.showPreloader();
			$.post(Ext.ContextPath+'/mobile/report/queryReportSpseno.do', $.condition.params, function(response){
				renderReportSptynoSpseno(response);
			},'json');
          }
        }
      ];
      var buttons2 = [
        {
          text: '取消',
          bg: 'danger'
        }
      ];
      var groups = [buttons1, buttons2];
      $.actions(groups);
		
	});
	
	function renderReportSptynoSpseno(response){
		if(window.report_sptyno_spseno_vm){
			//console.log(response);
			window.report_sptyno_spseno_vm.$data.todos=response;
		} else {
			window.report_sptyno_spseno_vm=new Vue({//这个不能删除，不然模板没了
			  el: '#report_sptyno_spseno',
			  data: {
				todos:response
			  }
			});
			$("#report_sptyno_spseno .content").show();	
		}
		$.router.load("#report_sptyno_spseno");
		$.hidePreloader();
	}

});


//报表统计--按价位段
$(function(){
	$(document).on("pageInit", function(e, pageId, $page) {
	  if(pageId == "report_money") {
		document.title="订货统计-按价位";
		$.showPreloader();
		$.condition.clear();
		showreport();
	  } else {
		  if(window.report_money_vm){
			   window.report_money_vm.$data.todos=[];
		  }
		  $.closePanel();
	  }
	});
	function showreport(){
		$.condition.init({
			bradno:true,
			spclno:true,
			ordorg:false,
			//
			selectFirstConfig:{//等着几个必须的条件准备好后，就自动进行查询
				bradno:true,//后台也要改，临时的
				//ordorg:true,//后台也要改，临时的
				readyIndex:1//临时解决方案，要初始化的查询条件的个数，等查询条件个数完成后，就自动进行查询，
			},
			submit:function(condition){
				//console.log(condition.params)
				$.post(Ext.ContextPath+'/mobile/report/queryReportMoney.do', condition.params, function(response){
					renderReport(response);
				},'json');
			}
		});
	}
	
	function renderReport(response){
		//alert(window.report_money_vm);
		if(window.report_money_vm){
			window.report_money_vm.$data.todos=response;
		} else {
			window.report_money_vm=new Vue({
			  el: '#report_money',
			  data: {
				todos:response
			  }
			});
			$("#report_money .content").show();	
		}
		$.hidePreloader();
				
	}

});



//报表统计--按渠道
$(function(){
	$(document).on("pageInit", function(e, pageId, $page) {
	  if(pageId == "report_org") {
		document.title="订货统计-按渠道";
		$.showPreloader();
		$.condition.clear();
		showreport();
		//initTouchmove();
	  } else {
		  if(window.report_org_vm){
			  window.report_org_vm.$data.todos=[];
		  }
		  
		  $.closePanel();
	  }
	});

	function showreport(){
		$.condition.init({
			bradno:true,
			spclno:true,
			ordorg:false,
			//
			selectFirstConfig:{//等着几个必须的条件准备好后，就自动进行查询
				bradno:true,//后台也要改，临时的
				//ordorg:false,//后台也要改，临时的
				readyIndex:1//临时解决方案，要初始化的查询条件的个数，等查询条件个数完成后，就自动进行查询，
			},
			submit:function(condition){
				//console.log(condition.params)
				$.post(Ext.ContextPath+'/mobile/report/queryReportOrg.do', condition.params, function(response){
					renderReport(response);
				},'json');
			}
		});
		
	}
	
	function renderReport(response){
		if(window.report_org_vm){
			//console.log(response);
			window.report_org_vm.$data.todos=response;
		} else {
			window.report_org_vm=new Vue({//这个不能删除，不然模板没了
			  el: '#report_org',
			  data: {
				todos:response
			  }
			});
			$("#report_org .content").show();	
		}
		$.hidePreloader();
	}
	
	

});



//报表统计--已订样衣
$(function(){
	$(document).on("pageInit", function(e, pageId, $page) {
		
	  if(pageId == "report_alreadyod") {
		document.title="订货统计-已定样衣";
		if(document.last_view_pageId=='sample_info'){
			return;
		}
		$.showPreloader();
		$.condition.clear();
		showreport();
	  } else {
		  if(window.report_alreadyod_vm && pageId!='sample_info'){
			   window.report_alreadyod_vm.$data.todos=[];
			   window.report_alreadyod_vm.$data.totalData={};
		  }
		
		  $.closePanel();
	  }
	  document.last_view_pageId=pageId;
	});

	function showreport(){
		$.condition.init({
			bradno:true,
			spclno:true,
			ordorg:false,
			//
			selectFirstConfig:{//等着几个必须的条件准备好后，就自动进行查询
				bradno:true,//后台也要改，临时的
				//ordorg:false,//后台也要改，临时的
				readyIndex:1//临时解决方案，要初始化的查询条件的个数，等查询条件个数完成后，就自动进行查询，
			},
			submit:function(condition){
				//console.log(condition.params)
				params=condition.params;
				params.start=0;
				params.limit=itemsPerLoad;
				$.post(Ext.ContextPath+'/mobile/report/queryReportAlreadyOd.do', condition.params, function(response){
					renderReport(response);
					
					
					maxItems=response.total;
					lastIndex = response.numPage;
					if(have_detachInfiniteScroll){
						//$.attachInfiniteScroll("#report_alreadyod .infinite-scroll");
						have_detachInfiniteScroll=false;
			  			$('#report_alreadyod .infinite-scroll-preloader').show();
					}
					if(response.numPage==0){
						$('#report_alreadyod .infinite-scroll-preloader').hide();	
					} else {
						$('#report_alreadyod .infinite-scroll-preloader').show();
					}
					if(response.total<itemsPerLoad){
						$('#report_alreadyod .infinite-scroll-preloader').hide();	
					}
				},'json');
			}
		});
		
	}
	
	function renderReport(response){
		if(window.report_alreadyod_vm){
			//console.log(response);
			window.report_alreadyod_vm.$data.todos=response.root;
			window.report_alreadyod_vm.$data.totalData=response.totalData;
		} else {
			window.report_alreadyod_vm=new Vue({//这个不能删除，不然模板没了
			  el: '#report_alreadyod',
			  data: {
				todos:response.root,
				totalData:response.totalData
			  }
			});
			$("#report_alreadyod .content").show();	
			infinite();
		}
		$.hidePreloader();
	}
	
	var params={};
	// 加载flag
    var loading = false;
    // 最多可加载的条目
    var maxItems = 100;
    // 每次加载添加多少条目
    var itemsPerLoad = 20;
	var lastIndex = itemsPerLoad;
	var have_detachInfiniteScroll=false;
	function infinite(){
		$(document).on('infinite', '.infinite-scroll-bottom',function() {
          // 如果正在加载，则退出
          if (loading) return;
          // 设置flag
          loading = true;
		  params.start=lastIndex;
		  params.limit=itemsPerLoad;
		  
		  if (lastIndex >= maxItems) {
			  //$.detachInfiniteScroll($('#report_alreadyod .infinite-scroll'));
			  have_detachInfiniteScroll=true;
			  $('#report_alreadyod .infinite-scroll-preloader').hide();
			  loading=false;
			  return;
		  }
		  $.post(Ext.ContextPath+'/mobile/report/queryReportAlreadyOd.do',params, function(response){
		  		loading = false;
				if(!response.root || response.root.length==0){
					return;
				}
				 // 添加新条目
				 for(var i=0;i<response.root.length;i++){
					 window.report_alreadyod_vm.$data.todos.push(response.root[i]);
				 }
				  //addItems(itemsPerLoad, lastIndex);
				  // 更新最后加载的序号
				  lastIndex = lastIndex+response.numPage;
				  //容器发生改变,如果是js滚动，需要刷新滚动
				  $.refreshScroller();
				
		  },'json');
				
          
      });//infinite
		
	}
	
	$("#report_alreadyod").on("click",".sample_info_link",function(){
		window.sample_info_sampno=$(this).data("sampno");
		window.sample_info_sampnm1=null;
	});

});


//样衣详细信息
$(function(){
	$(document).on("pageInit", function(e, pageId, $page) {
	  if(pageId == "sample_info") {
		document.title="样衣详细信息";
		$.showPreloader();
		//$.condition.clear();
		showreport();
	  } else {
		  if(window.sample_info_vm){
			  window.sample_info_vm.$data.todos=[];
			  window.sample_info_vm.$data.title="";
		  }
		  
		  $.closePanel();
	  }
	});

	function showreport(){
		$.post(Ext.ContextPath+'/mobile/report/querySampleInfo.do', {sampno:window.sample_info_sampno,sampnm1:window.sample_info_sampnm1}, function(response){
					renderReport(response);
				},'json');
	}
	
	function renderReport(response){
		if(window.sample_info_vm){
			//console.log(response);
			window.sample_info_vm.$data.todos=response.todos;
			window.sample_info_vm.$data.title=response.title;
		} else {
			window.sample_info_vm=new Vue({//这个不能删除，不然模板没了
			  el: '#sample_info',
			  data: {
				todos:response.todos,
				title:response.title
			  }
			});
			$("#sample_info .content").show();	
		}
		$.hidePreloader();
	}

});



