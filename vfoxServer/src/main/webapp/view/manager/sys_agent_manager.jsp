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
						<span class="search_span">账户名称：</span>
						<input type="text" name="text" class="search_inp" id="userName"/>
				</div>
				<div class="search_one">
					<span class="search_span">更新时间：</span>
						<input id="start_date" name="startTime" type="text" class="Wdate" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
						<span class="search_span_value">至</span>
						<input id="end_date" name="endTime" 　type="text" class="Wdate" value="" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
						<input type="submit" value="查询" class="alt_btn" id="search">
						<input type="submit" value="新增管理员账户" id="add_role">
				</div>
			</div>
			<div id="tabDIV" class="tabDIV_list">
			
			
			</div>
		</section>
		<div id="floatBoxBg"></div>
		<!--添加弹层开始-->
		<form action="/system/add" method="post" id="addForm">
		<div class="tcLsyer" style="display:none;">
			<div class="tcLsyer_title">
				<span>新增管理员账户</span>
				<a href="javascript:;" class="close"></a>
			</div>
			<div class="tcLayer_content_whiteList">
				<div class="content_DIV">
					<span class="is_check">账号名称：</span>
					<input type="text" name="userName" class="tc_inp" id="userDto.userName"/>
					<span class="tips" style="display: none" id="userNameTips">*账号名称不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="is_check">账号密码：</span>
					<input type="password" name="passWord" class="tc_inp" id="userDto.passWord"/>
					<span class="tips" style="display: none;" id="passWordTips">*账号密码不能为空</span>
				</div>
				<div class="content_DIV">
					<input type="button" value="确定" class="buttonClass" style="margin-left:40%;" id="okAdd"/>
					<input type="button" value="取消"  class="buttonClass" id="closeAdd"/>
				</div>
			</div>
		</div>
		</form>
		<!--添加弹层结束-->
		<!--修改弹层开始-->
		<!-- <div class="tcLsyer_all" style="display:none;">
			<div class="tcLsyer_title">
				<span>新增</span>
				<a href="#" class="close_all"></a>
			</div>
			<div class="tcLayer_content_whiteList">
				<div class="content_DIV">
					<span class="is_check">账号名称：</span>
					<input type="text" name="text" class="tc_inp" value="东方丽人公司"/>
					<span class="tips">*账号名称不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="is_check">账号密码：</span>
					<input type="password" name="password" class="tc_inp" value="123123123"/>
					<span class="tips">*账号密码不能为空</span>
				</div>
				<div class="content_DIV">
					<input type="submit" value="确定" class="alt_btn" style="margin-left:17%;"/>
					<input type="reset" value="重置" />
				</div>
			</div>
			
		</div> -->
		<!--修改弹层结束-->
	</div>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	searchPageInfo(1);
}).on("click", "#search",function(){
	searchPageInfo(1);
}).on("blur","#userDto\\.passWord",function(){
	var passWord = $(this).val();
	if($.trim(passWord) == ''){
		$("#passWordTips").show();
	}
}).on("blur","#userDto\\.userName",function(){
	var userName = $(this).val();
	if($.trim(userName) == ''){
		$("#userNameTips").show();
	}
}).on("click","#closeAdd",function(){//取消
	$(".close").click();
}).on("focus","#userDto\\.passWord",function(){
	$("#passWordTips").hide();
}).on("focus","#userDto\\.userName",function(){
	$("#userNameTips").hide();
}).on("click","#okAdd",function(){ //添加提交 
	if(checkInput()){
		$("#addForm").submit();
	}
}).on("click","input[name='deleteUser']", function(){
	var userId = $(this).attr("id");
	window.location.href = "/system/delete?userId=" + userId;
});


//加载表格内容 
function searchPageInfo(pageNo) {
	$("#tabDIV").empty();
	$("#tabDIV").append("<input type='image' class='loading_img'  src='/resources/images/load.gif' title='加载中,请稍后...'>");
	var userName = $("#userName").val();
	var startTime = $("#start_date").val();
	var endTime = $("#end_date").val();
	$.ajax({
		url:'/system/list',
		data:{curPage:pageNo, userName:userName, startTime:startTime,  endTime:endTime},
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

function checkInput(){
	var flag = true;
	var userName = $("#userDto\\.userName").val();
	var passWord = $("#userDto\\.passWord").val();
	
	if($.trim(userName) == ''){
		flag = false;
		$("#userNameTips").show();
	}else if($.trim(passWord) == ''){
		flag = false;
		$("#passWordTips").show();
	}
	return flag;
}

 

</script>
</html>