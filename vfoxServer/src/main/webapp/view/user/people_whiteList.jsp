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
	
	<%@include file="/view/inc/header.jsp" %>
	
	<%@include file="/view/inc/menu.jsp"%>
	
	<section id="main" class="column">
		<div class="module_content" style="width:96%;">
			<div class="seach_one">
				<span class="search_span" style="width:80px;">用户ID：</span>
				<input type="text" name="text" class="search_inp" id="peopleId"/>
			</div>
			<div class="search_one">
				<span class="search_span" style="width:80px;">域名更新时间：</span>
				<input id="start_date" name="startTime" type="text" class="Wdate" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
				<span class="search_span_value">至</span>
				<input id="end_date" name="endTime" 　type="text" class="Wdate" value="" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
				<input type="submit" value="查询" class="alt_btn" id="search">
				<input type="submit" value="新增用户" id="add_role">
			</div>
			
		</div>
		<input type="submit" value="下载" style="float:right;margin-right:30px;">
		<input type="submit" value="上传" style="float:right;">
		<div id="tabDIV" class="tabDIV_list">
			
		</div>
		<input id="delAll" type="submit" value="批量删除" style="margin-left:3%;margin-top:10px;"/>
	</section>
		<div id="floatBoxBg"></div>
		<!--添加弹层开始-->
		<form action="/people/add" method="post" id="addForm">
			<div class="tcLsyer" style="display:none;">
				<div class="tcLsyer_title">
					<span>新增</span>
					<a href="javaScript:;" class="close"></a>
				</div>
				<div class="tcLayer_content_whiteList">
					<div class="content_DIV">
						<span class="is_check">用户ID：</span>
						<input type="text" class="tc_inp" id="whiteUser.userMd5Id" name="userMd5Id"/>
						<span class="tips" style="display: none;" id="userMd5IdTips">*用户Id不能为空</span>
					</div>
					<!--<div class="content_DIV">
						<span class="is_check">域名更新时间：</span>
						<input id="start_date" name="startTime" type="text" class="Wdate" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
						<span class="search_span_value">至</span>
						<input id="end_date" name="endTime" 　type="text" class="Wdate" value="" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
					</div>-->
					<div class="content_DIV">
						<input type="button" value="确定" class="buttonClass" style="margin-left:45%;" id="okAdd"/>
						<input type="button" value="重置"  class="buttonClass" id="closeAdd"/>
					</div>
				</div>
			</div>
		</form>
		<!--添加弹层结束-->
		<!--修改弹层开始-->
<!-- 		<div class="tcLsyer_all" style="display:none;">
			<div class="tcLsyer_title">
				<span>修改</span>
				<a href="#" class="close_all"></a>
			</div>
			<div class="tcLayer_content_whiteList">
				<div class="content_DIV">
					<span class="is_check">用户ID：</span>
					<input type="text" name="text" class="tc_inp" value="新浪网址"/>
				</div>
				<div class="content_DIV">
					<span class="is_check">域名更新时间：</span>
					<input id="start_date" name="startTime" type="text" class="Wdate" value="2014-3-2" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
					<span class="search_span_value">至</span>
					<input id="end_date" name="endTime" 　type="text" class="Wdate" value="2014-5-28" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
				</div>
				<div class="content_DIV">
					<input type="submit" value="确定" class="alt_btn" style="margin-left:22%;"/>
				<input type="submit" value="重置" />
				</div>
			</div>
			
		</div> -->
		<!--修改弹层结束-->

</body>
<script type="text/javascript">
$(document).ready(function(){
	searchPageInfo(1);
}).on("click", "#search",function(){
	searchPageInfo(1);
}).on("blur", "#whiteUser\\.userMd5Id", function(){ //失去焦点事件 
	var userMd5Id = $(this).val();
	if($.trim(userMd5Id) == ''){
		$("#userMd5IdTips").show();
	}
}).on("click","#closeAdd",function(){//取消
	$(".close").click();
}).on("focus", "#whiteUser\\.userMd5Id", function(){//获取焦点事件 
	$("#userMd5IdTips").hide();
}).on("click","#okAdd",function(){ //添加提交 
	if(checkInput()){
		$("#addForm").submit();
	}
}).on("click","input[name='delWhiteUser']", function(){
	var id = $(this).attr("id");
	window.location.href = "/people/delete?id=" + id;
}).on("click","#checkedAll", function(){
	if($(this).attr("checked") == 'checked'){
		$("input[name='checkBox']").each(function(){
			$(this).attr("checked","checked");
		})
	}else{
		$("input[name='checkBox']").each(function(){
			$(this).attr("checked",false);
		})
	}
}).on("click","#delAll",function(){
	var flag = false;
	$("input[name='checkBox']").each(function(){
		if($(this).attr("checked") == 'checked'){
			flag = true;
			return;
		}
	});
	if(flag){
		var ids = "";
		$("input[name='checkBox']").each(function(){
			if($(this).attr("checked") == 'checked'){
				var id = $(this).attr("value");
				ids += id + ",";
			}
		});
		if(window.confirm('你确定要删除吗？')){
			$.ajax({
				url:'/people/deleteAll',
			    data:{ids:ids},
			    type:'POST',
			    dataType:'json',		
			    success: function(result){
			    	if(result=="1"){
			    		searchPageInfo(1);
			    	}else{
			    		alert("删除失败");
			    	}
			    },error: function(){
			    	alert("error");
			    }
			});
		}
	}else{
		alert("请勾选所要删除的用户ID！");
	}
});


//加载表格内容 
function searchPageInfo(pageNo) {
	$("#tabDIV").empty();
	$("#tabDIV").append("<input type='image' class='loading_img'  src='/resources/images/load.gif' title='加载中,请稍后...'>");
	var peopleId = $("#peopleId").val();
	var startTime = $("#start_date").val();
	var endTime = $("#end_date").val();
	$.ajax({
		url:'/people/list',
		data:{curPage:pageNo,peopleId:peopleId, startTime:startTime ,endTime:endTime},
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
	var userMd5Id = $("#whiteUser\\.userMd5Id").val();
	
	if($.trim(userMd5Id) == ''){
		flag = false;
		$("#userMd5IdTips").show();
	}
	return flag;
}

 

</script>
</html>