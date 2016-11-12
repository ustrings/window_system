<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html><head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | Registration Page</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<%=path%>/statics/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=path%>/statics/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="<%=path%>/statics/plugins/iCheck/square/blue.css">
    <link rel="stylesheet" href="<%=path%>/statics/dist/css/AdminLTE.css">
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition register-page">
<div class="register-box" style="width:30%;">
  <div class="register-logo">
    <a href="../../index2.html"><b>客户管理</b>系统</a>
  </div>

  <div class="register-box-body" style="padding:15%">

    <form id="registerForm">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" name="name" placeholder="姓名">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="text" class="form-control" name="username" placeholder="用户名">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" id="password" name="password" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" id="qrpassword" name="qrpassword" placeholder="确认密码，不能为空">
        <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
      </div>
      <div class="row">
      	<div class="col-xs-8">
          <div class="checkbox icheck">
            <a href="Login.jsp" class="text-center">返回登录</a>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button id="refisterBtn"  type="button" class="btn btn-primary btn-block btn-flat">注册</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
    
  </div>
  <!-- /.form-box -->
</div>
<!-- /.register-box -->

<!-- jQuery 2.2.3 -->
<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<!-- iCheck -->
<script src="<%=path%>/statics/plugins/iCheck/icheck.min.js"></script>
<script src="<%=path%>/statics/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=path%>/statics/plugins/bootbox/bootbox.min.js"></script>
<script type="text/javascript" src="statics/admin/js/utils.js"></script>
<script>
	$(function(){
		$("#refisterBtn").click(function(){
			$.post("<%=path %>/dr?p=insertUser",$("#registerForm").serializeObject(),
		    function(result){
		    	 alert(result);
		    	 if("注册成功"==result){
		    	 	location.href="Login.jsp";
		    	 }else{
		    	 	bootbox.alert({size: "small", message: result});
		    	 }
		    });
		});
	});
</script>


</body></html>
