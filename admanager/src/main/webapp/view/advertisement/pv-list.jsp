<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>广告投放明细</title>

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
			
			<div id="content" class="span10" >
			<div>
				<ul class="breadcrumb">
					<li>
						<a href="#">首页</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#">广告pv对比</a>
					</li>
				</ul>
			</div>
			
			
			<div>
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i></i> 广告pv对比</h2>
					</div>
					<div class="box-content" style="text-align: center">
					<form class="form-horizontal" method="post" action="/adimpresslog/list" id="queryForm">
						<table style="margin: 20px;padding: 20px;text-align: center;">
							<tr style="padding: 5px;">
								<td style="padding-left: 20px;">广告名称：</td>
								<td>
									<select id="adId" name="adId" onchange="sendForCompare()">
										<c:if test="${not empty adList}">
											<c:forEach var="obj" items="${adList}" varStatus="status">
												  	<option value="${obj.id}">${obj.adName}</option>
											</c:forEach>
										</c:if>
									</select>
								</td>
								
							</tr>
						
						</table>
						</form>
					</div>
					<div id="contentDiv" style="overflow: scroll;">
						         
					</div>
				</div><!--/span-->
			</div><!--/row-->    
					<!-- content ends -->
			</div><!--/#content.span10-->
				</div><!--/fluid-row-->
	</div>
	<footer>
	<p class="pull-leftt" >
		<a href="http://www.vaolan.com" target="_blank">Vaolan Corp. 2014</a>
	</p>
	<p class="pull-right">
		Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
	</p>
	</footer>
	
	<%@include file="../inc/footer.jsp"%>
</body>
<script>
$(document).ready(function(){
	WaitingBar.show();
	sendForChange();
	WaitingBar.hide();
}).on("click","#queryBtn",function(){
	WaitingBar.show();
	$("#queryForm").submit();
});

function sendForCompare() {
	WaitingBar.show();
	sendForChange();
	WaitingBar.hide();
}

// 获取广告对比结果
function sendForChange() {
	//jQuery("#select1  option:selected").text();
	 var adId = $("#adId").val();
	 //alert(adId);
	// alert(adStatus);
	 // 设置请求的 url
     var url = "/material/pvcompare/" + adId;
	// 检查广告是不是有设置广告类目，有就继续，否则就提示设置广告类目
	$.ajax({
		url:url,
		type:'GET',
		dataType:'html',
		async:true,
		success: function(result){
			//alert(result);
			$("#contentDiv").empty();
			$("#contentDiv").append(result);
			//contentDiv.append(result);
		},error:function(){
			alert("error");
		}
	});
}

</script>
</html>
