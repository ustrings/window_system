<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width" />
	<title>精准弹窗控制平台审核进度查看</title>
	
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
							<input type="text" name="adName" id="adName" class="search_inp" />
							<span class="search_span">审核状态：</span>
							<select name="checksts" id="checksts">
								<option value="">--请选择--</option>
								<option value="0">待审核</option>
								<option value="2">审核成功</option>
								<option value="5">审核失败</option>
							</select>
							<div class="clear"></div>
					</div>
					<div class="seach_one">
						<div class="search_DIV_time" style="width:90%;">
							<span class="search_span">起止时间：</span>
							<input id="start_date" name="startTime" type="text" class="Wdate" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
							<span class="search_span_value">至</span>
							<input id="end_date" name="endTime" 　type="text" class="Wdate" value="" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
							<input type="submit" value="查询" id="adSearchBtn" class="alt_btn" style="margin-left:30px">
							<div class="clear"></div>
						</div>
					</div>		
				</div>
				<div id="tabDIV" class="tabDIV_list">
				
				</div>
			</section>
			<div id="floatBoxBg"></div>
			<!--点击审核弹层开始-->
			<div class="tcLsyer" style="display:none;" id="checkHistory">
				
				
			</div>
		
			<!--点击审核弹层结束-->
		</div>
	</div>
<script type="text/javascript">
$(document).ready(function(){
	searchCheckHistoryPageInfo(1);
}).on("mousemove",".materialName",function(e){
	var top=$(this).position().top+30;
	var left=$(this).position().left+30;
	$("#materialViewDiv").css({"position":"absolute","top":top+"px","left":left+"px"}).show();	
}).on("mouseout",".materialName",function(){
	//var id = $(this).attr("materialId");
	$("#materialViewDiv").hide();
}).on("click","#adSearchBtn",function(){   //查询
	searchCheckHistoryPageInfo(1);
}).on("click","input[name='checkhistory']",function(){   //审核历史展示
	var adId = $(this).attr("id");
	
	$.ajax({
		url:'/checkProgress/checkHistoryList',
		data:{adId:adId},
		type:'POST',
		dataType:'html',
		async:true,
		success:function(result){
			$("#checkHistory").empty();
			$("#checkHistory").append(result);
		},error:function(){
		}
	});
	showBackGround();
	$(".tcLsyer").show();
	showDiv(".tcLsyer");
}).on("click",".close",function(){
	$(".tcLsyer").hide();
	$("#floatBoxBg").hide();
});

function searchCheckHistoryPageInfo(curPage){   //分页展示
	$("#tabDIV").empty();
	$("#tabDIV").append("<input type='image' class='loading_img'  src='/resources/images/load.gif' title='加载中,请稍后...'>");   
	var adName = $("#adName").val();
	var checksts = $("#checksts").val();
	var startTime = $("#start_date").val();
    var endTime = $("#end_date").val();
	 
	   $.ajax({
			url:"/checkProgress/qryCheckHistoryPageList",
			data:{curPage:curPage,adName:adName,checksts:checksts,startTime:startTime,endTime:endTime},
			type:'POST',
			dataType:'html',
			async:true,
			success:function(result){
				$("#tabDIV").empty();
				$("#tabDIV").append(result);
			},error:function(){
			}
		});
}
</script>
</body>
</html>