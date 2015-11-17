<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>广告</title>

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
			<div>
				<ul class="breadcrumb">
					<li>
						<a href="javascript:;">首页</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="javascript:;">广告报告</a>
					</li>
				</ul>
			</div>
			
			
			<div class="row-fluid sortable">		
				<div class="box span12">
						<div class="row-fluid" >
								<ul class="nav nav-tabs">
									<li class="active"><a href="#home" data-toggle="tab">投放报告</a></li>
									<!-- <li><a href="#profile" data-toggle="tab" onclick="mRpClick()">物料报告</a></li> -->
								    <!--<li><a href="#urlShow" data-toggle="tab" onclick="urlClick()">网址报告</a></li> -->
								</ul>
								
								<div class="tab-content" style="overflow: visible;">
									<div class="tab-pane active" id="home">
										<jsp:include page="/ad/report" flush="true" ></jsp:include>
									</div>
									<div class="tab-pane" id="profile">
										<!--  -->
										<iframe onload="ChangeFrameHeight('materialReportFrame') "id="materialReportFrame" src="" style="width: 100%;" frameborder="0" bordercolor="#00000000" scrolling="no" border="0" vspace="0" hspace="0" marginheight="0" marginwidth="0" >
										</iframe>
									</div>
									<div class="tab-pane" id="urlShow">
									    <iframe onload="ChangeFrameHeight('urlReportFrame') " id="urlReportFrame" src="" style="width: 100%;" frameborder="0" bordercolor="#00000000" scrolling="no" border="0" vspace="0" hspace="0" marginheight="0" marginwidth="0" >
										</iframe>
									</div>
								</div>
						</div>
				</div><!--/span-->
			</div><!--/row-->    
					<!-- content ends -->
			</div><!--/#content.span10-->
				</div><!--/fluid-row-->
		
	</div><!--/.fluid-container-->
	<footer>
    <p class="pull-leftt" >
        <a href="http://www.vaolan.com" target="_blank">Vaolan Corp. 2014</a>
    </p>
    <p class="pull-right_t">
        Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
    </p>
</footer>
<%@include file="../inc/footer.jsp"%>
</body>
<script type="text/javascript" src="/resources/js/common/common.js"></script>
<script type="text/javascript"  src="/resources/js/report/ad-report.js">
</script>
<script>
function ChangeFrameHeight(id) {
    var count = 1;
    
    (function() {
    var frm = document.getElementById(id);
    var subWeb = document.frames ? document.frames[id].document : frm.contentDocument;

        if (subWeb != null) {
            var height = Math.max(subWeb.body.scrollHeight, subWeb.documentElement.scrollHeight);
            frm.height = height;
        }
        if (count < 3) {
            count = count + 1;
            window.setTimeout(arguments.callee, 2000);
        }
    })();
}
function mRpClick(){
	$("#materialReportFrame").attr("src","/material/report");
}
function urlClick(){
	
	$("#urlReportFrame").attr("src","/urlCount/report");
}
</script>

</html>