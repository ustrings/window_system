<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.beanutils.DynaBean"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>营销管控系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  
	<link rel="stylesheet" href="<%=path%>/statics/dist/css/skins/_all-skins.min.css">
	<link rel="stylesheet" href="<%=path%>/statics/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="http://cdn.bootcss.com/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="<%=path%>/statics/plugins/bootstrap-table/bootstrap-table.min.css">
    <link rel="stylesheet" href="<%=path%>/statics/plugins/select2/select2.min.css">
    <link rel="stylesheet" href="<%=path%>/statics/plugins/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="<%=path%>/statics/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="<%=path%>/statics/dist/css/skins/skin-red.min.css">
    <link rel="stylesheet" href="<%=path%>/statics/admin/css/admin.css">
    <link rel="stylesheet" href="<%=path%>/statics/plugins/daterangepicker/daterangepicker.css">
  </head>
  
 <body class="hold-transition skin-blue sidebar-mini">
    <script src="<%=path%>/statics/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="<%=path%>/statics/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=path%>/statics/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="<%=path%>/statics/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
	<script src="<%=path%>/statics/plugins/select2/select2.full.min.js"></script>
	<script src="<%=path%>/statics/plugins/bootbox/bootbox.min.js"></script>
    <script type="text/javascript" src="<%=path%>/statics/plugins/daterangepicker/moment.min.js"></script>
    <script src="<%=path%>/statics/plugins/daterangepicker/daterangepicker.js"></script>

	<script src="<%=path%>/statics/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
	<script src="<%=path%>/statics/plugins/slimScroll/jquery.slimscroll.min.js"></script>
	<script src="<%=path%>/statics/plugins/fastclick/fastclick.js"></script>
	<script src="<%=path%>/statics/dist/js/demo.js"></script>
	<script src="<%=path%>/statics/dist/js/app.min.js"></script>
	<script src="<%=path%>/statics/admin/js/admin.js"></script>
	<script src="<%=path%>/statics/admin/js/utils.js"></script>
<div class="wrapper">
<%@include file="/jsp/public/header.jsp"%>
  <%@include file="/jsp/public/sidebar.jsp"%>
  <div class="content-wrapper">


    <!-- Main content -->
    <section class="content">
            <!--
            <div class="callout callout-info">
                <h4>Notice!</h4>
                <p>今天下午 15:00-17:00 之间进行办公室内大扫除，请同志们提前安排好手头工作，请周知！</p>
            </div>
            -->
            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">登录信息</h3>
                </div>
                <div class="box-body">
			                    您好，${sessionScope.user.nickname}  [ <a href="{:U('Users/proinfo')}">${sessionScope.user.username}</a> ]，
			                    这是您第 <font color="red">${sessionScope.user.loginnum}</font> 次登录系统<br/>
			                    所属角色∶超级管理员 <br/>
			                    ------------------------------------- <br/>
			                    上次登录时间∶${sessionScope.user.lastdate}<br/>
			                    上次登录IP∶${sessionScope.user.lastip}
                </div><!-- /.box-body -->
            </div>
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">服务器信息</h3>
                </div>
                <div class="box-body">
                       ThinkPHP版本∶ThinkPHP {$Think.THINK_VERSION} <br/>
				                    操作系统∶{:java_uname()}<br/>
				                    服务器软件∶{$SERVER_SOFTWARE}<br/>
				                    MySQL版本∶{:mysql_get_server_info()}<br/>
				                    上传文件限制∶
                </div><!-- /.box-body -->
            </div>

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">技术支持</h3>
                </div>
                <div class="box-body">
			                    版权所有：CooperLiu <br/>
			       UI 设计：adminlte <br/>
			                    个人博客：<a href="http://www.haiyaotech.com" target="_blank">http://www.haiyaotech.com</a> <br/>
			                    个人QQ：707399420 <br/>
			                    联系方式：15915492613
                </div><!-- /.box-body -->
            </div><!-- /.box -->
        </section><!-- /.content -->
       
    </div><!-- /.content-wrapper -->
    <%@include file="/jsp/public/footer.jsp"%>
  </div>
</body>
</html>