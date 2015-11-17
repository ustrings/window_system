<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/view/inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
	<meta charset="utf-8"/>
	<title>精准弹窗控制平台广告统计列表查看</title>
	
	<link rel="stylesheet" href="/resources/css/layout.css" type="text/css" media="screen" />
	<!--[if lt IE 9]>
	<link rel="stylesheet" href="css/ie.css" type="text/css" media="screen" />
	
	<![endif]-->
	<script src="/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="/resources/js/hideshow.js" type="text/javascript"></script>
	<script src="/resources/js/jquery.tablesorter.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="/resources/js/jquery.equalHeight.js"></script>
	<script src="/resources/js/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
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
		<%@include file="/view/inc/header.jsp" %>
		<div class="index_content_all">
			<%@include file="/view/inc/menu.jsp"%>
	
			<section id="main" class="column">
				<div class="module_content">
					<div class="seach_one">
							<span class="search_span">广告名称：</span>
							<input type="text" name="text" class="search_inp" id="keyword"/>
							<span class="search_span">投放状态：</span>
							<select id="selectSts">
								<option value="-2">全部</option>
								<option value="2">投放中</option>
								<option value="3">投放暂停</option>
								<option value="4">投放结束</option>
								<option value="6">投放停止</option>
								
							</select>
					</div>
					<div class="search_one">
						<div class="search_DIV_time" style="width:90%;">
							<span class="search_span">起止时间：</span>
							<input id="start_date" name="startTime" type="text" class="Wdate" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
							<span class="search_span_value">至</span>
							<!--<input type="text" name="text" class="search_inp" />-->
							<input id="end_date" name="endTime" 　type="text" class="Wdate" value="" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
							<input type="submit" value="查询" class="alt_btn" id="search">
							<div class="clear"></div>
						</div>
					</div>
				</div>
				<span style="margin-left: 30px; margin-top: 50px; font-size: 13px;">说明:开启按钮, 表明广告正在投放中, 点击可以暂定投放; 关闭按钮, 表明广告处于暂停投放状态, 点击可以继续广告投放; 废除广告, 表明该广告不再投放</span>
				<div id="tabDIV" class="tabDIV_list">
			
			
				</div>
			</section>
		</div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	searchPageInfo(1);
}).on("click", "#search",function(){
	searchPageInfo(1);
}).on("click","span[name='planSts']", function(){
	//计划状态的开关
			var adId = $(this).attr("id");
			if($(this).attr("class") == "btn-oval-close" ){
				$(this).removeClass();
				$(this).attr("class","btn-oval-open");
				$(this).attr("value","2");
				
			}else if($(this).attr("class") == "btn-oval-open"){
				$(this).removeClass();
				$(this).attr("class","btn-oval-close");
				$(this).attr("value","3");
			}
			var sts = $(this).attr("value");
			$.ajax({
				url:'/ad_count/updateSts',
				data:{sts:sts,adId:adId},
				type:'GET',
				dataType:'json',
				async:true,
				success: function(result){
					if(result == "1"){
						ssdbLoad();
						if(sts == '2'){
							alert("开启成功");
						}else{
							alert("关闭成功");
						}
					}else{
						alert("操作失败");
					}
				},error:function(){
					alert("操作失败");
				}
			});
			
}).on("click","a[name='stopA']", function(){
	var pageNo = $('#curPage').val();
	if(window.confirm("你确定要废除广告吗?")){
		var adId = $(this).attr("value");
		$.ajax({
			url:'/ad_count/updateSts',
			data:{sts:'6',adId:adId},
			type:'get',
			dataType:'json',
			async:true,
			success: function(result){
				if(result == "1"){
					ssdbLoad();
					alert("操作成功");
					searchPageInfo(pageNo);
				}else{
					alert("操作失败");
				}
			},error:function(){
				alert("error");
			}
		});
	}
});

//加载表格内容 
function searchPageInfo(pageNo) {
	$("#tabDIV").empty();
	$("#tabDIV").append("<input type='image' class='loading_img'  src='/resources/images/load.gif' title='加载中,请稍后...'>");
	var keyword = $("#keyword").val();
	var startTime = $("#start_date").val();
	var endTime = $("#end_date").val();
	var adTFsts = $("#selectSts").children('option:selected').attr("value");
	$.ajax({
		url:'/ad_count/list',
		data:{curPage:pageNo, keyword:keyword, startTime:startTime, endTime:endTime, adTFsts:adTFsts},
		type:'POST',
		dataType:'html',
		async:true,
		success: function(result){
			$("#tabDIV").empty();
			$("#tabDIV").append(result);
		},error:function(){
			alert("error");
			$("#tabDIV").empty();
		}
	});
}

function ssdbLoad(){
	$.ajax({
		url:'http://180.96.26.130:8060/reload ',
		type:'GET',
		dataType:'json',
		async:true,
		success: function(result){
			
		},error:function(){
			
		}
	});
}

</script>
</html>