<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/view/inc/directive.inc"%>
<!doctype html>
<html lang="en">

<head>
	<title>精准弹窗控制平台投放广告报告</title>
	
	<link rel="stylesheet" href="/resources/css/layout.css" type="text/css" media="screen" />
	<script src="/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="/resources/js/hideshow.js" type="text/javascript"></script>
	<script src="/resources/js/jquery.tablesorter.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="/resources/js/jquery.equalHeight.js"></script>
	<script src="/resources/js/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="/resources/js/kkpager.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/resources/css/kkpager.css" />
	<script type="text/javascript">
	$(document).ready(function() 
    	{ 
      	  $(".tablesorter").tablesorter(); 
   	 } 
	);
	$(document).ready(function() {

	//When page loads...
	$(".tab_content").hide(); //Hide all content
	$("ul.tabs li:first").addClass("active").show(); //Activate first tab
	$(".tab_content:first").show(); //Show first tab content

	//On Click Event
	$("ul.tabs li").click(function() {

		$("ul.tabs li").removeClass("active"); //Remove any "active" class
		$(this).addClass("active"); //Add "active" class to selected tab
		$(".tab_content").hide(); //Hide all tab content

		var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
		$(activeTab).fadeIn(); //Fade in the active ID content
		return false;
	});

});
    </script>
    <script type="text/javascript">
    $(function(){
        $('.column').equalHeight();
    });
</script>
</head>
<body>
	<div class="index_all">
		<%@include file="/view/inc/header.jsp"%>
		<div class="index_content_all">
		<%@include file="/view/inc/menu.jsp"%>
	
	<section id="main" class="column">
		<!-- <div class="module_content">
			<span class="search_span">时间筛选：</span>
			<a href="#" class="report_a">今天</a>
			<span class="report_line">|</span>
			<a href="#" class="report_a">昨天</a>
			<span class="report_line">|</span>
			<a href="#" class="report_a">本周</a>
			<span class="report_line">|</span>
			<a href="#" class="report_a">上周</a>
			<span class="report_line">|</span>
			<a href="#" class="report_a">本月</a>
			<span class="report_line">|</span>
			<a href="#" class="report_a">上月</a>			
		</div> -->
		<div class="module_content">
			<span class="search_span">自定义时间：</span>
			<span class="custom_begin">从</span>
			<input id="start_date" name="startTime" type="text" class="Wdate" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
			<span class="custom_begin">到</span>
			<input id="end_date" name="endTime" 　type="text" class="Wdate" value="" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
		</div>
		<div class="module_content">
			<span class="search_span">广告名称&nbsp;&nbsp;&nbsp;&nbsp;：</span>
			<input type="text" name="text" class="search_inp" style="margin-left:30px;" id="keyword"/>
			<span class="search_span" style="margin-left:20px;">投放状态&nbsp;&nbsp;：</span>
			<select style="margin-left:30px;" id="selectSts">
								<option value="-2">全部</option>
								<option value="2">投放中</option>
								<option value="3">投放暂停</option>
								<option value="4">投放结束</option>
								<option value="6">投放停止</option>
			</select>
		</div>
		<div class="module_content">
			<span class="search_span">代理商账户：</span>
			<select style="margin-left:30px;width:210px;" id="selectUser">
				<option value="-1">全部</option>
				<c:if test = "${not empty list}">
					<c:forEach var="oneuser" items="${list}">
						<option value="${oneuser.userId }">${oneuser.userName }</option>
					</c:forEach>
				</c:if>
			</select>
			<span class="search_span" style="margin-left:20px;">创意类型&nbsp;&nbsp;：</span>
			<select style="margin-left:30px;" id="selectType">
				<option value="-1">全部</option>
				<option value="E">投放链接</option>
				<option value="M">广告物料</option>
			</select>
			<input type="submit" value="查询" class="alt_btn" style="margin-left:38px" id="search">
		</div>
		<!-- <article class="module width_3_quarter">
			<header>
				<h3 class="tabs_involved">图表对比走势图</h3>
				<ul class="tabs">
					<li><a href="#tab1">展现量</a></li>
					<li><a href="#tab2">点击量</a></li>
				</ul>
			</header>

		<div class="tab_container">
			<div id="tab1" class="tab_content">
				<div class="module_content">
					<article class="stats_graph">
						<img src="/resources/assets/chart.png" width="520" height="140" alt="" />
					</article>
					<article class="stats_overview">
						<div class="overview_today">
							<p class="overview_day">Today</p>
							<p class="overview_count">1,876</p>
							<p class="overview_type">Hits</p>
							<p class="overview_count">2,103</p>
							<p class="overview_type">Views</p>
						</div>
						<div class="overview_previous">
							<p class="overview_day">Yesterday</p>
							<p class="overview_count">1,646</p>
							<p class="overview_type">Hits</p>
							<p class="overview_count">2,054</p>
							<p class="overview_type">Views</p>
						</div>
					</article>
					<div class="clear"></div>
				</div>
			</div>end of #tab1
			
			<div id="tab2" class="tab_content">
				<div class="module_content">
					<article class="stats_graph">
						<img src="/resources/assets/click.png" width="520" height="140" alt="" />
					</article>
					<article class="stats_overview">
						<div class="overview_today">
							<p class="overview_day">Today</p>
							<p class="overview_count">1,876</p>
							<p class="overview_type">Hits</p>
							<p class="overview_count">2,103</p>
							<p class="overview_type">Views</p>
						</div>
						<div class="overview_previous">
							<p class="overview_day">Yesterday</p>
							<p class="overview_count">1,646</p>
							<p class="overview_type">Hits</p>
							<p class="overview_count">2,054</p>
							<p class="overview_type">Views</p>
						</div>
					</article>
					<div class="clear"></div>
				</div>
			</div>end of #tab2
			
		</div>end of .tab_container
		
		</article> -->
		<input id="reportExcel" type="submit" value="导出数据" style="margin-left:3%;margin-top:10px;"/>
		<div id="tabDIV" class="tabDIV_list">
			
			
		</div>
		
		
	</section>
		<div id="floatBoxBg"></div>
		<!--详情查看弹层-->
		<div class="detail_layer" style="display:none;" >
			<div class="detail_Layer_title">
				<input type="hidden" id="adId" value=""/>
				<input type="hidden" id="adName" value=""/>
				<span class="is_check" id="headDetailName" value=""></span>
				<a href="javascript:;" class="close"></a>
				</div>
			<div class="detail_content" id="detailDIV">
				<!-- 详情 -->
			</div>
				
		</div>
		<!--详情查看弹层-->
		<!-- 导出确认弹层 -->
		<div style="display: none" id="excelChoose" class="sure_export">
			<div class="tc_layer_content">
				<input type="radio" value="0" checked="checked" name="radio" style="float: left; line-height: 50px; height: 50px; display: block;margin:0;padding:0;" /><span style="float:left;margin-right:10px;font-size:12px;color:#666;">导出统计数据</span>
				<input type="radio" value="1" name="radio" style="float: left; line-height: 50px; height: 50px; display: block;margin:0;padding:0;"/><span style="float:left;font-size:12px;color:#666;">导出详情数据</span>
			</div>
			<div class="tc_layer_radio">
				<input type="button" value="确认" id="okDIV" class="sure_btn"/>
				<input type="button" value="取消" id="closeDIV" class="reset_btn"/>
			</div>
		</div>
		<form action="/report/upload" method="post" id="download">
			<input type="hidden" id="adIds" value="" name="adIds">
			<input type="hidden" id="excelType" value="" name="type">
		</form>
</div>
</div>
		
</body>
<script type="text/javascript">
$(document).ready(function(){
	searchPageInfo(1);
}).on("click","a[name=details]", function(){
	var adId = $(this).attr("value");
	var adName = $(this).attr("adName");
	$("#headDetailName").val(adId);
	$("#headDetailName").text(adName);
	$("#adId").attr("value",adId);
	$("#adName").attr("value",adName);
	monthDetail(adId,"");
}).on("click",".close",function(){
	$(".detail_layer").hide();
	$("#floatBoxBg").hide();
}).on("click","#search",function(){
	$("#contentDIV").empty();
	searchPageInfo(1);
}).on("click","a[name=monthA]", function(){
	var adId = $("#adId").attr("value");
	var month = $(this).attr("value");
	monthDetail(adId,month);
}).on("click","#checkedAll",function(){
	//全选与反选 
	if($(this).attr("checked") == 'checked'){
		$("input[name='checkBox']").each(function(){
			$(this).attr("checked","checked");
		});
	}else{
		$("input[name='checkBox']").each(function(){
			$(this).attr("checked",false);
		});
	}
}).on("click","#reportExcel",function(){
	var flag = false;
	$("input[name='checkBox']").each(function(){
		if($(this).attr("checked") == 'checked'){
			flag = true;
			return;
		}
	});
	if(flag){
		var adIds = "";
		$("input[name='checkBox']").each(function(){
			if($(this).attr("checked") == 'checked'){
				var adId = $(this).attr("value");
				adIds += adId + ",";
			}
		});
		$("#adIds").val(adIds);
		showBackGround();
		$(".sure_export").show();
		showDiv(".sure_export");
	}else{
		alert("请勾选所要导出的广告！");
	}
}).on("click","#okDIV",function(){
	$("input[name='radio']").each(function(){
		if($(this).attr("checked") == "checked"){
			var type = $(this).attr("value");
			$("#excelType").attr("value",type);
		}
	});
	$("#download").submit();
	$(".reset_btn").click();
});

function getParameter(name) { 
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r!=null) return unescape(r[2]); return null;
}

function monthDetail(adId, month){
	$("#detailDIV").empty();
	$.ajax({
		url:'/report/month',
		data:{adId:adId, month:month},
		type:'GET',
		dataType:'html',
		async:true,
		success: function(result){
			$("#detailDIV").empty();
			$("#detailDIV").append(result);
		},error:function(){
			alert("error");
		}
	});
	showBackGround();
	$(".detail_layer").show();
}
//加载表格内容  
function searchPageInfo(pageNo) {
	$("#tabDIV").empty();
	$("#tabDIV").append("<input type='image' class='loading_img'  src='/resources/images/load.gif' title='加载中,请稍后...'>");
	var keyword = $("#keyword").val();
	var startTime = $("#start_date").val();
	var endTime = $("#end_date").val();
	var stsTF = $("#selectSts").children('option:selected').attr("value");
	var userId = $("#selectUser").children('option:selected').attr("value");
	var linkType = $("#selectType").children('option:selected').attr("value");
	$.ajax({
		url:'/report/list',
		data:{curPage:pageNo, keyword:keyword, statTime:startTime, endTime:endTime, stsTF:stsTF, userId:userId, linkType:linkType},
		type:'POST',
		dataType:'html',
		async:true,
		success: function(result){
			$("#tabDIV").empty();
			$("#tabDIV").append(result);
		},error:function(){
			alert("error");
		}
	});
}
</script>
</html>