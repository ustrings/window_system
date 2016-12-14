<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="<%=path%>/statics/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=path%>/statics/dist/css/AdminLTE.css">
  </head>
  
  <body class="hold-transition login-page">
  <script type="text/javascript" src="/DspTow/statics/plugins/jQuery/jquery-2.2.3.min.js"></script>
  <script src="<%=path%>/statics/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path%>/statics/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="<%=path%>/statics/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="<%=path%>/statics/plugins/select2/select2.full.min.js"></script>
<script src="<%=path%>/statics/plugins/bootbox/bootbox.min.js"></script>
<script src="<%=path%>/statics/dist/js/app.min.js"></script>
<script src="<%=path%>/statics/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="/DspTow/statics/admin/js/utils.js"></script>


<script>

	$(function(){
		var username="${sessionScope.user.username}";
		if(username!=""){
			location.href="<%=path%>/jsp/Admin/system.jsp";
		}
		$("#loginBtn").click(function(){
			$.post("<%=path%>/dspUserAction/doLogin.do",$("#loginForm").serializeObject(),
		    function(result){
		    	 if("1"==result.trim()){
		    	 	location.href="<%=path%>/jsp/Admin/system.jsp";
		    	 	//$.post("<%=path%>/dspUserAction/toSystemPage.do");
		    	 }else{
		    	 	bootbox.alert({size: "small", message: "登录失败"});
		    	 }
		    });
		});
	});
</script>
<div class="login-box" style="width:30%;">
  <div class="login-logo">
    <a><b>Dsp</b>manager</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body" style="padding:15%">

    <form id="loginForm">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" name="username" placeholder="用户名">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" name="password" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox" style="position: absolute; top: -20%; left: -20%; display: block; width: 140%; height: 140%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);"><ins class="iCheck-helper" style="position: absolute; top: -20%; left: -20%; display: block; width: 140%; height: 140%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);"></ins> 记住密码
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button id="loginBtn" type="button" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
    <!-- /.social-auth-links -->
    <a href="register.jsp" class="text-center">没有密码？去注册</a>

  </div>
  <!-- /.login-box-body -->
</div>


	

</body>
</html>
