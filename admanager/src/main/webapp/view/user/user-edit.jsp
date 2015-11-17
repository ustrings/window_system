<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!DOCTYPE html>
<head>
<meta charset="utf-8">
	<title>密码修改</title>
<!-- The styles -->
<link id="bs-css" href="/resources/css/bootstrap-cerulean.css" rel="stylesheet">
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
.uploadify-queue{display:none}
</style>
<link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
<link href="/resources/css/charisma-app.css" rel="stylesheet">
<link href="/resources/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
<link href='/resources/css/fullcalendar.css' rel='stylesheet'>
<link href='/resources/css/fullcalendar.print.css' rel='stylesheet' media='print'>
<link href='/resources/css/chosen.css' rel='stylesheet'>
<link href='/resources/css/uniform.default.css' rel='stylesheet'>
<link href='/resources/css/colorbox.css' rel='stylesheet'>
<link href='/resources/css/jquery.cleditor.css' rel='stylesheet'>
<link href='/resources/css/jquery.noty.css' rel='stylesheet'>
<link href='/resources/css/noty_theme_default.css' rel='stylesheet'>
<link href='/resources/css/elfinder.min.css' rel='stylesheet'>
<link href='/resources/css/elfinder.theme.css' rel='stylesheet'>
<link href='/resources/css/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='/resources/css/opa-icons.css' rel='stylesheet'>
<link href='/resources/css/uploadify.css' rel='stylesheet'>

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon" href="/resources/img/favicon.ico">
</head>

<body>
	<%@include file="../inc/header.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<%@include file="../inc/menu.jsp"%>
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.
					</p>
				</div>
			</noscript>
			<div id="content" class="span10">
				<!-- content starts -->
				<div>
					<ul class="breadcrumb">
						<li><a href="/">首页</a> <span class="divider">/</span></li>
						<li><a href="#">密码修改</a></li>
					</ul>
				</div>
				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-edit"></i> 密码修改
							</h2>
						</div>
						<div class="box-content">
							<input type="hidden" value="${jsession}" id="jsession"/>
							<form class="form-horizontal" method="post"  id="saveForm">
								<fieldset>
									<div class="control-group">
										<label class="control-label" for="materialName" ><b style="color: #FF0000;">*</b>原始密码：</label>
										<div class="controls">
											<input type="password" class="input-xlarge focused" id="oldpassword" name="oldpassword" maxlength="50" value="">
											<span id="oldpasswordTips" class="help-inline" style="display:none"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="materialName" ><b style="color: #FF0000;">*</b>新密码：</label>
										<div class="controls">
											<input type="password" class="input-xlarge focused" id="newpassword" name="newpassword" maxlength="50" value="">
											<span id="newpasswordTips" class="help-inline" style="display:none"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="materialName" ><b style="color: #FF0000;">*</b>确认密码：</label>
										<div class="controls">
											<input type="password" class="input-xlarge focused" id="confirmpassword" name="confirmpassword" maxlength="50" value="">
											<span id="confirmpasswordTips" class="help-inline" style="display:none"></span>
										</div>
									</div>
									<div class="form-actions">
										<button id="saveBtn" type="button" class="btn btn-primary">保存</button>
										<!-- 
										<button type="button" class="btn" id="back">返回</button>
										 -->
										 <button type="reset" class="btn" >重置</button>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
					<!--/span-->

				</div>
				<!--/row-->
				<!-- content ends -->
			</div>
			<!--/#content.span10-->
		</div>
		<!--/fluid-row-->
			<footer>
			<p class="pull-leftt" style="font-size:14px;">
				<a href="http://www.vaolan.com" target="_blank">Vaolan Corp.
					2014</a>
			</p>
			<p class="pull-right_t" style="font-size:14px;">
				Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
			</p>
			</footer>
	</div>
	<!--/.fluid-container-->
	<%@include file="../inc/footer.jsp"%>
	<script type="text/javascript" charset="utf-8" src="/resources/ueditor/editor_config.js"></script>
	<script type="text/javascript" charset="utf-8" src="/resources/ueditor/editor_all.js"></script>
</body>
<script type="text/javascript">
<!-- 失去焦点事件：判断文本框内容不可以为空的函数。     周晓明-->
$(document).ready(function(){
	$("#saveBtn").click(function(){
		editUser();
	});
	checkNull("oldpassword","原始密码不能为空");
	//检查广告名称
// 	$("#oldpassword").focusout (function(){
// 		var $oldpassword = $("#oldpassword").val();
// 		if ($.trim($oldpassword) == ''){
// 			$("#oldpasswordTips").text("原始密码不能为空");
// 			$("#oldpasswordTips").parent().parent().addClass("error");
// 			$("#oldpasswordTips").show();
// 		} 
// 	});
	checkNull("newpassword","新密码不能为空");
// 	$("#newpassword").focusout (function(){
// 		var $newpassword = $("#newpassword").val();
// 		if ($.trim($newpassword) == ''){
// 			$("#newpasswordTips").text("新密码不能为空");
// 			$("#newpasswordTips").parent().parent().addClass("error");
// 			$("#newpasswordTips").show();
// 		} 
// 	});
	checkNull("confirmpassword","确认密码");
	
}).on("focus","#oldpassword",function(){
	$("#oldpasswordTips").parent().parent().removeClass("error");
	$("#oldpasswordTips").hide();
}).on("focus","#newpassword",function(){
	$("#newpasswordTips").parent().parent().removeClass("error");
	$("#newpasswordTips").hide();
});

function checkNull(inputId,msg){
		
	$("#"+inputId).focusout (function(){
		var $newpassword = $("#"+inputId).val();
		if ($.trim($newpassword) == ''){
			$("#"+inputId+"Tips").text(msg);
			$("#"+inputId+"Tips").parent().parent().addClass("error");
			$("#"+inputId+"Tips").show();
		} 
	});
	$("#"+inputId).focus(function(){
		$("#"+inputId+"Tips").parent().parent().removeClass("error");
		$("#"+inputId+"Tips").hide();
	});
}
function editUser(){
	if($("#confirmpassword").val()!=$("#newpassword").val()){
		alert("新密码与确认密码不一致");
		return;
	}
	$.ajax({
		url:"/user/edit",
		data:$("#saveForm").serialize(),
		dataType:"json",
		type:'GET',
		success:function(data){
			
			if(data.result=="1"){
				alert("修改成功");
			}else{
				alert(data.msg);
			}	
		}
	});
}
</script>

</html>