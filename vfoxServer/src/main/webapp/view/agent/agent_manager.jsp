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
						<span class="search_span">公司名称：</span>
						<input type="text" name="text" class="search_inp" id="companyName"/>
						<span class="search_span">账号昵称：</span>
						<input type="text" name="text" class="search_inp" id="userName"/>
						<span class="search_span">联系人电话：</span>
						<input type="text" name="text" class="search_inp" id="telNbr"/>
					</div>
					<div class="search_one">
						<div class="search_DIV_time" style="width:90%;">
							<span class="search_span">更新时间：</span>
							<input id="start_date" name="startTime" type="text" class="Wdate" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
							<span class="search_span_value">至</span>
							<input id="end_date" name="endTime" 　type="text" class="Wdate" value="" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
							<input type="submit" value="查询" class="alt_btn" style="margin-left:38px" id="search">
							<input type="submit" value="新增代理商账户" id="add_agent">
							<div class="clear"></div>
						</div>
					</div>
				</div>
				<div id="tabDIV" class="tabDIV_list">
			
			
				</div>
			</section>
			
	
			<div id="floatBoxBg"></div>
			<!--添加弹层开始-->		
			<div class="tcLsyer" style="display:none;position:relative;height:400px;width:700px;overflow-y:auto;">
			   			
			</div>
			<!--添加弹层结束-->	
			<!--修改弹层开始-->
			<div class="tcLsyer_all" style="display:none;position:relative;height:400px;width:700px;overflow-y:auto;" id="editDIV">
				<!-- 编辑层加入 -->
				
			</div>
			<!--修改弹层结束-->
		</div>
	</div>
		<script type="text/javascript" src="/resources/js/jquery.uploadify-3.1.min.js"></script>		
		<!--图片上传-->
</body>

<script type="text/javascript">
$(document).ready(function(){
	searchPageInfo(1);
}).on("click", "#search",function(){
	searchPageInfo(1);
}).on("blur", "#userDto\\.companyName_add", function(){ //失去焦点事件 
	var companyName = $(this).val();
	if($.trim(companyName) == ''){
		$("#companyName_addTips").show();
	}
}).on("blur","#userDto\\.companyLeader_add",function(){
	var companyLeader = $(this).val();
	if($.trim(companyLeader) == ''){
		$("#companyLeader_addTips").show();
	}
	
}).on("blur","#userDto\\.telNbr_add",function(){
	var telNbr = $(this).val();
	if($.trim(telNbr) == ''){
		$("#telNbr_addTips").show();
	}
}).on("blur","#userDto\\.passWord_add",function(){
	var passWord = $(this).val();
	if($.trim(passWord) == ''){
		$("#passWord_addTips").show();
	}
}).on("blur","#userDto\\.userName_add",function(){
	var userName = $(this).val();
	if($.trim(userName) == ''){
		$("#userName_addTips").show();
	}
}).on("click","#closeAdd",function(){//取消
	$(".close").click();
}).on("focus", "#userDto\\.companyName_add", function(){//获取焦点事件 
	$("#companyName_addTips").hide();
}).on("focus","#userDto\\.companyLeader_add",function(){
	$("#companyLeader_addTips").hide();
}).on("focus","#userDto\\.telNbr_add",function(){
	$("#telNbr_addTips").hide();
}).on("focus","#userDto\\.passWord_add",function(){
	$("#passWord_addTips").hide();
}).on("focus","#userDto\\.userName_add",function(){
	$("#userName_addTips").hide();
}).on("click","#okAdd",function(){ //添加提交 
	if(checkInput("add")){
		$("#addForm").submit();
	}
}).on("click","input[name='editUser']",function(){
	//初始化 编辑信息 
	var userId = $(this).attr("id");
	editUserInit(userId);
}).on("click","#closeEdit",function(){
	$(".close_all").click();
}).on("click","#okEdit",function(){
	if(checkInput("edit")){
		$("#editForm").submit();
	}
}).on("click","input[name='deleteUser']", function(){
	var userId = $(this).attr("id");
	window.location.href = "/agent/delete?userId=" + userId;
}).on("click","#add_agent",function(){
	$.ajax({
		url:'/agent/addAgent',
		type:'POST',
		dataType:'html',
		async:true,
		success: function(result){
			$(".tcLsyer").empty();
			$(".tcLsyer").append(result);
		},error:function(){
			alert("error");
		}
	});
	showBackGround();
	$(".tcLsyer").show();
	showDiv(".tcLsyer");
});


//加载表格内容 
function searchPageInfo(pageNo) {
	$("#tabDIV").empty();
	$("#tabDIV").append("<input type='image' class='loading_img'  src='/resources/images/load.gif' title='加载中,请稍后...'>");
	var companyName = $("#companyName").val();
	var userName = $("#userName").val();
	var telNbr = $("#telNbr").val();
	var startTime = $("#start_date").val();
	var endTime = $("#end_date").val();
	$.ajax({
		url:'/agent/list',
		data:{curPage:pageNo, companyName:companyName,userName:userName, telNbr:telNbr, startTime:startTime,  endTime:endTime},
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


//加载编辑信息
function editUserInit(userId) {
	$.ajax({
		url:'/agent/editInit',
		data:{userId:userId},
		type:'GET',
		dataType:'html',
		async:true,
		success: function(result){
			$("#editDIV").empty();
			$("#editDIV").append(result);
		},error:function(){
			alert("error");
		}
	});
	showBackGround();
	$(".tcLsyer_all").show();
	showDiv(".tcLsyer_all");
}

function checkInput(type){
	var flag = true;
	var companyName = $("#userDto\\.companyName_" + type).val();
	var companyLeader = $("#userDto\\.companyLeader_" + type).val();
	var telNbr = $("#userDto\\.telNbr_" + type).val();
	var userName = $("#userDto\\.userName_" + type).val();
	var passWord = $("#userDto\\.passWord_" + type).val();
	
	if($.trim(companyName) == ''){
		flag = false;
		$("#companyName_" + type + "Tips").show();
	}else if($.trim(companyLeader) == ''){
		flag = false;
		$("#companyLeader_" + type + "Tips").show();
	}else if($.trim(telNbr) == ''){
		flag = false;
		$("#telNbr_" + type + "Tips").show();
	}else if($.trim(userName) == ''){
		flag = false;
		$("#userName_" + type + "Tips").show();
	}else if($.trim(passWord) == ''){
		flag = false;
		$("#passWord_" + type + "Tipes").show();
	}
	
	return flag;
}

 

</script>
</html>