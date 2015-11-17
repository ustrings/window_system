<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!-- Meta -->
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- End of Meta -->
		
		<!-- Page title -->
		<title>精准弹窗控制平台登陆</title>
		<!-- End of Page title -->
		
		<!-- Libraries -->
		<link type="text/css" href="/resources/css/login.css" rel="stylesheet" />	
		
		<script type="text/javascript" src="/resources/js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="/resources/js/easyTooltip.js"></script>
		<script type="text/javascript" src="/resources/js/jquery-ui-1.7.2.custom.min.js"></script>
		<!-- End of Libraries -->	
	</head>
	<body>
	<div id="container">
		<div class="logo">
			<a href="#"><img src="/resources/assets/logo.png" alt="" /></a>
		</div>
		<div id="box">
			<form id="loginForm" action="/login" method="post">
			<p class="main">
				<label>用户名： </label>
				<input type="text" name="username" id="username"/> 
				<label>密码：</label>
				<input type="password" name="password" id="password"/>	
			</p>

			<p class="space">
				<span><input type="checkbox" name="remember" class="check"  value="1"/>
				<label>记住用户状态</label>
				记住密码</span>
				<div id="error" style="display:none;margin-left: 45px;">
				   <span style="color: red" id="errorMsg"></span>
			    </div>
				<input type="button"  value="登陆"  class="login" id="login" />
			</p>
			</form>
		</div>
	</div>
<script type="text/javascript">
// 登陆
$(document).on("click","#login",function() {
   if(checkUser()){
      $("#loginForm").submit();
   }
}).on("keydown","#loginForm",function(e){
	if(e.which==13) {
		$("#login").click();
	}
});	

function checkUser(){
	var exists = false;
	var username = $("#username").val();
	var password = $("#password").val();
	if ($.trim(username) == '' || $.trim(password) == ''){
		$("#error").show();
		$("#errorMsg").text("用户名和密码不能为空.");
	} else{
		$.ajax({
			url:"/checkuser",
			data:{username:username,password:password},
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
	</body>
</html>