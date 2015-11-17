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
				<div class="module_content" style="width:96%;">
					<div class="seach_one">
							<span class="search_span" style="width:80px;">域名：</span>
							<input type="text" name="text" class="search_inp" id="domainName"/>
							<span class="search_span">域名URL：</span>
							<input type="text" name="text" class="search_inp" id="domainUrl"/>
					</div>
					<div class="search_one">
						<div class="search_DIV_time" style="width:90%">
							<span class="search_span" style="width:80px;">域名更新时间：</span>
							<input id="start_date" name="startTime" type="text" class="Wdate" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
							<span class="search_span_value" style="width:66px;">至</span>
							<input id="end_date" name="endTime" 　type="text" class="Wdate" value="" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
							<input type="submit" value="查询" class="alt_btn" id="search">
							<input type="submit" value="新增" id="add_role">
							<div class="clear"></div>
						</div>
					</div>
					
				</div>
				<input type="submit" value="下载" style="float:right;margin-right:30px;">
				<form action="/domain/uploadWhileUrl" enctype="multipart/form-data" method="post" id="uplaodForm">
				   <input type="submit" value="上传" style="float:right;" id="uploadUrl">	
				   <input type="file" name="filepath" id="filePath" style="float:right;width: 180px;"/>		   		   
				</form>
				<div id="tabDIV" class="tabDIV_list">
			
			
				</div>
				<input id="delAll" type="submit" value="批量删除" style="margin-left:3%;margin-top:10px;"/>
			</section>
		<div id="floatBoxBg"></div>
		<!--添加弹层开始-->
		<form action="/domain/add" method="post" id="addForm">
		<div class="tcLsyer" style="display:none;">
			<div class="tcLsyer_title">
				<span>新增</span>
				<a href="javascript:;" class="close"></a>
			</div>
			<div class="tcLayer_content_whiteList">
				<div class="content_DIV">
					<span class="is_check">域名名称：</span>
					<input type="text" name="domainName" class="tc_inp_more" id="whiteDomain.domainName_add"/>
					<span class="tips" style="display: none;" id="domainName_addTips">*域名名称不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="is_check">域名URL：</span>
					<input type="text" name="domainUrl" class="tc_inp_more" id="whiteDomain.domainUrl_add"/>
					<span class="tips" style="display: none;" id="domainUrl_addTips">*域名URL不能为空</span>
				</div>
				<!-- <div class="content_DIV">
					<span class="is_check">域名更新时间：</span>
					<input id="start_date" name="startTime" type="text" class="Wdate" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
					<span class="search_span_value">至</span>
					<input id="end_date" name="endTime" 　type="text" class="Wdate" value="" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
				</div> -->
				<div class="content_DIV">
					<input type="button" value="确定" class="buttonClass" style="margin-left:42%;" id="okAdd"/>
					<input type="button" value="取消"  id="closeAdd"/>
				</div>
			</div>
		</div>
		</form>
		<!--添加弹层结束-->
		
		
		<!--修改弹层开始-->
		<form action="/domain/edit" method="post" id="editForm">
			<input type="hidden" id="whiteDomain.id" name="id" value="">
			<div class="tcLsyer_all" style="display:none;">
				<div class="tcLsyer_title">
					<span>修改</span>
					<a href="javascript:;" class="close_all"></a>
				</div>
				<div class="tcLayer_content_whiteList">
					<div class="content_DIV">
						<span class="is_check">域名名称：</span>
						<input type="text" name="domainName" class="tc_inp_more" value="" id="whiteDomain.domainName_edit"/>
						<span class="tips" style="display: none;" id="domainName_editTips">*域名名称不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="is_check">域名URL：</span>
						<input type="text" name="domainUrl" class="tc_inp_more" value=""  id="whiteDomain.domainUrl_edit"/>
						<span class="tips" style="display: none;" id="domainUrl_editTips">*域名URL不能为空</span>
					</div>
					<!-- <div class="content_DIV">
						<span class="is_check">域名更新时间：</span>
						<input id="start_date" name="startTime" type="text" class="Wdate" value="2014-3-2" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
						<span class="search_span_value">至</span>
						<input id="end_date" name="endTime" 　type="text" class="Wdate" value="2014-5-28" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
					</div> -->
					<div class="content_DIV">
						<input type="button" value="确定" class="alt_btn" style="margin-left:42%;" id="okEdit"/>
					<input type="button" class="alt_btn"  value="取消" id="closeEdit"/>
					</div>
				</div>
			</div>
		</form>
		<!--修改弹层结束-->
	</div>	
	</div>
	
</body>
<script type="text/javascript">
$(document).ready(function(){
	searchPageInfo(1);
	var error = "${param.error}";
	if(error == 'error'){
		alert("上传的文件过大,请上传小于10M的xls文件");
	}
}).on("click", "#search",function(){
	searchPageInfo(1);
}).on("blur", "#whiteDomain\\.domainName_add", function(){ //失去焦点事件 
	var domainName = $(this).val();
	if($.trim(domainName) == ''){
		$("#domainName_addTips").show();
	}
}).on("blur","#whiteDomain\\.domainUrl_add",function(){
	var domainUrl = $(this).val();
	if($.trim(domainUrl) == ''){
		$("#domainUrl_addTips").show();
	}
	
}).on("blur", "#whiteDomain\\.domainName_edit", function(){ //失去焦点事件 
	var domainName = $(this).val();
	if($.trim(domainName) == ''){
		$("#domainName_editTips").show();
	}
}).on("blur","#whiteDomain\\.domainUrl_edit",function(){
	var domainUrl = $(this).val();
	if($.trim(domainUrl) == ''){
		$("#domainUrl_editTips").show();
	}
	
}).on("click","#closeAdd",function(){//取消
	$(".close").click();
}).on("focus", "#whiteDomain\\.domainName_add", function(){//获取焦点事件 
	$("#domainName_addTips").hide();
}).on("focus","#whiteDomain\\.domainUrl_add",function(){
	$("#domainUrl_addTips").hide();
}).on("click","#okAdd",function(){ //添加提交 
	if(checkInput("add")){
		$("#addForm").submit();
	}
}).on("focus", "#whiteDomain\\.domainName_edit", function(){//获取焦点事件 
	$("#domainName_editTips").hide();
}).on("focus","#whiteDomain\\.domainUrl_edit",function(){
	$("#domainUrl_editTips").hide();
}).on("click","input[name='updateWhiteDomain']",function(){
	//初始化 编辑信息 
	var domainId = $(this).attr("id");
	editUserInit(domainId);
}).on("click","#closeEdit",function(){
	$(".close_all").click();
}).on("click","#okEdit",function(){
	if(checkInput("edit")){
		$("#editForm").submit();
	}
}).on("click","input[name='deleteWhiteDomain']", function(){
	var domainId = $(this).attr("id");
	window.location.href = "/domain/delete?domainId=" + domainId;
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
		var domainIds = "";
		$("input[name='checkBox']").each(function(){
			if($(this).attr("checked") == 'checked'){
				var domainId = $(this).attr("value");
				domainIds += domainId + ",";
			}
		});
		if(window.confirm('你确定要删除吗？')){
			$.ajax({
				url:'/domain/deleteAll',
			    data:{domainIds:domainIds},
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
		alert("请勾选所要删除的域名！");
	}
}).on("click","#uploadUrl",function(){
	if(checkUploadFile()==true){
		$("#uplaodForm").submit();
	}else{
		alert("文件格式不支持,请上传xls格式的文件");
		return ;
	}	
});
//加载表格内容 
function searchPageInfo(pageNo) {
	$("#tabDIV").empty();
	$("#tabDIV").append("<input type='image' class='loading_img'  src='/resources/images/load.gif' title='加载中,请稍后...'>");
	var domainName = $("#domainName").val();
	var domainUrl = $("#domainUrl").val();
	var startTime = $("#start_date").val();
	var endTime = $("#end_date").val();
	$.ajax({
		url:'/domain/list',
		data:{curPage:pageNo,domainName:domainName,domainUrl:domainUrl, startTime:startTime ,endTime:endTime},
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
function editUserInit(domainId) {
	$.ajax({
		url:'/domain/editInit',
		data:{domainId:domainId},
		type:'GET',
		dataType:'json',
		async:true,
		success: function(data){
			var whiteDomain = data.whiteDomain;
			alert(whiteDomain.domainName);
			if(whiteDomain != null){
				$("#whiteDomain\\.domainName_edit").val(whiteDomain.domainName);
				$("#whiteDomain\\.domainUrl_edit").val(whiteDomain.domainUrl);
				$("#whiteDomain\\.id").val(whiteDomain.id);
			}
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
	var domainName = $("#whiteDomain\\.domainName_" + type).val();
	var domainUrl = $("#whiteDomain\\.domainUrl_" + type).val();
	
	if($.trim(domainName) == ''){
		flag = false;
		$("#domainName_" + type + "Tips").show();
	}else if($.trim(domainUrl) == ''){
		flag = false;
		$("#domainUrl_" + type + "Tips").show();
	}
	
	return flag;
}
function checkUploadFile(){
    var flag = false;
	var fileType = $("#filePath").val();
	var type=fileType.split(".");
	if(type[1]=="xls"||type[1]=="XLS"){
		flag = true;
	}
	return flag; 
}
</script>
</html>