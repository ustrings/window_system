<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/view/inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<!--
		Charisma v1.0.0

		Copyright 2012 Muhammad Usman
		Licensed under the Apache License v2.0
		http://www.apache.org/licenses/LICENSE-2.0

		http://usman.it
		http://twitter.com/halalit_usman
	-->
	<meta charset="utf-8">
	<title>诏兰数据广告平台-登录</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
	<meta name="author" content="Muhammad Usman">

	<!-- The styles -->
	<link id="bs-css" href="/resources/css/bootstrap-cerulean.css" rel="stylesheet">
	<style type="text/css">
	  body {
		padding-bottom: 40px;
	  }
	  .sidebar-nav {
		padding: 9px 0;
	  }
	</style>
	<link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="/resources/css/charisma-app.css" rel="stylesheet">
	<link href="/resources/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
	<link href='/resources/css/fullcalendar.css' rel='stylesheet'>
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

	<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
	<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

	<!-- The fav icon -->
	<link rel="shortcut icon" href="/resources/img/favicon.ico">
		
</head>

<body>
		<div class="container-fluid" style="margin-top:50px;">
		<div class="row-fluid">
		
			<div class="row-fluid">
				<div class="span12 center login-header">
					<h2>精准需求管理平台</h2>
				</div><!--/span-->
			</div><!--/row-->
			<div class="row-fluid">
				<div class="well span5 center login-box" style="margin-top:10px;">
					<div class="alert alert-info">
						请输入登录用户名和密码.
					</div>
					<form class="form-horizontal" action="/login" method="post" id="loginForm">
						<fieldset>
							<div class="input-prepend" title="Username" data-rel="tooltip">
								<span class="add-on"><i class="icon-user"></i></span><input autofocus class="input-large span10" name="username" id="username" type="text" value="${username}" />
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend" title="Password" data-rel="tooltip">
								<span class="add-on"><i class="icon-lock"></i></span><input class="input-large span10" name="password" id="password" type="password" value="" />
							</div>
							<div class="clearfix"></div>

							<div class="input-prepend">
							<label class="remember" for="remember"><input type="checkbox" name="remember" value="1" id="remember" />记住我的账户</label>
							</div>
							<div class="clearfix"></div>
					
							<div class="control-group error" id="error" style="display:none">
								<span class="help-inline" id="errorMsg"></span>
							</div>
							<p class="center span5">
							<button type="button" class="btn btn-primary" id="login" style="width:70%">登录</button>
							</p>
						</fieldset>
					</form>
				</div><!--/span-->
			</div><!--/row-->
				</div><!--/fluid-row-->
		
	</div><!--/.fluid-container-->
</body>
<script src="/resources/js/jquery-1.7.2.min.js"></script>
<script>
$(document).on("click","#login",function(){
	if (checkUser()){
		$("#loginForm").submit();
	}
}).on("keydown","#loginForm",function(e){
	if(e.which == 13){
		$("#login").click();
	}
}).on("focus","#username,#password",function(){
	$("#error").hide();
});

function checkUser(){
	var exists = false;
	var username = $("#username").val();
	var password = $("#password").val();
	if ($.trim(username) == '' || $.trim(password) == ''){
		$("#error").show();
		$("#errorMsg").text("用户名和密码不能为空.");
		//exists =  false;
	} else{
		$.ajax({
			url:"/checkuser",
			data:{uname:username,pwd:password},
			async:false,
			type:'GET',
			dataType:'json',
			success:function(data){
				if (typeof data != 'undefined'){
					if(data.result==false) {
						$("#error").show();
						$("#errorMsg").text("用户名或密码错误.");
					}
					if(data.result&&data.resulType!='undefined'&&data.resultType){
						exists = true;
					}else if(data.result&&data.resulType!='undefined'&&data.resultType==false){
						$("#error").show();
						$("#errorMsg").text("该用户没有登录权限.");
					}
				}
			},error:function(){
				alert("error");
			}
		});
	}
	return exists;
}
</script>
</html>
