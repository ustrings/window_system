<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
	<title>诏兰数据广告平台</title>
	<meta name="description"
		content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
		<!-- The styles -->
		<link id="bs-css" href="/resources/css/bootstrap-cerulean.css"
			rel="stylesheet">
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
	<link href='/resources/css/fullcalendar.print.css' rel='stylesheet'  media='print'>
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
			<div id="content" class="span10">
				<!-- content starts -->
				<div>
					<ul class="breadcrumb">
						<li><a href="#">异常信息</a></li>
					</ul>
				</div>

				<c:set value="${exception}" var="exception" />
				<jsp:useBean id="exception" type="java.lang.Exception" />

				<c:if test="${not empty cause  }">
					<c:set value="${cause}" var="cause" />
				</c:if>


				异常信息:${exception}
				<c:if test="${not empty cause  }">${cause}</c:if>
				<br /> <br />


				<c:choose>
					<c:when test="${not empty cause  }">
						<jsp:useBean id="cause" type="java.lang.Throwable" />
						<%
							cause.printStackTrace(new java.io.PrintWriter(out));
						%>
					</c:when>
					<c:otherwise>
						<%
							exception.printStackTrace(new java.io.PrintWriter(out));
						%>
					</c:otherwise>
				</c:choose>


			</div>



			<!--/row-->
		</div>
		<!--/#content.span10-->
	</div>
	<!--/fluid-row-->


	<footer>
	<p class="pull-leftt">
		<a href="http://www.vaolan.com" target="_blank">Vaolan Corp. 2014</a>
	</p>
	<p class="pull-right">
		Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
	</p>
	</footer>
	<!--/.fluid-container-->

	<%@include file="../inc/footer.jsp"%>
</body>

</html>
