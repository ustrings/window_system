<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>广告物料预览</title>
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
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			
			<div id="content" class="span10">
			<!-- content starts -->
			
			<div>
				<ul class="breadcrumb">
					<li>
						<a href="#">首页</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#">广告物料-预览</a>
					</li>
				</ul>
			</div>

			<div class="row-fluid sortable">
				<div class="box span8">
					<div class="box-header well" data-original-title>
						<h2>广告物料</h2>
					</div>
					<div class="box-content">
						  <div class="page-header">
							  <h1>${material.materialName} 
							  	<span class="label label-success">
							  		<c:choose>
										<c:when test="${material.MType == 1}">图片</c:when>
										<c:when test="${material.MType == 2}">Flash</c:when>
										<c:otherwise>富媒体</c:otherwise>
									</c:choose>
							  	</span>
							  </h1>
						  </div>     
						  <div class="row-fluid ">            
							  <div class="span8">
								<c:choose>
									<c:when test="${material.MType == 1}">
										<div><img src="${material.linkUrl}"/> </div>
									</c:when>
									<c:when test="${material.MType == 2}">
										<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0">
											<param name="movie" value="0.swf">
											<param name="quality" value="high">
											<embed src="${material.linkUrl}" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"/>
										</object>
									</c:when>
									<c:otherwise>
										<div>${material.richText}</div>
									</c:otherwise>
								</c:choose>
							  </div>
						  </div><!--/row -->                           
					  </div>
				</div><!--/span-->
			</div><!--/row-->
					<!-- content ends -->
			</div><!--/#content.span10-->
		</div><!--/fluid-row-->
	</div><!--/.fluid-container-->
<%@include file="../inc/footer.jsp"%>
</body>
</html>