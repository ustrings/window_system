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

.hourselno {
	width: 20px;
	height: 20px;
	text-align: center;
	background-color: gray;
}

.hourselyes {
	width: 15px;
	height: 15px;
	text-align: center;
	border-style: solid;
}

.hourselview {
	width: 15px;
	height: 15px;
	text-align: center;
	border-style: double;
}

.hourSum {
	color: red;
}
.uploadify-queue{display:none}
</style>
			<link href="/resources/css/bootstrap-responsive.css" rel="stylesheet" />
			<link href="/resources/css/charisma-app.css" rel="stylesheet" />
			<link href="/resources/css/jquery-ui-1.8.21.custom.css"
				rel="stylesheet" />
			<link href='/resources/css/fullcalendar.css' rel='stylesheet' />
			<link href='/resources/css/fullcalendar.print.css' rel='stylesheet'
				media='print' />
			<link href='/resources/css/chosen.css' rel='stylesheet' />
			<link href='/resources/css/uniform.default.css' rel='stylesheet' />
			<link href='/resources/css/colorbox.css' rel='stylesheet' />
			<link href='/resources/css/jquery.cleditor.css' rel='stylesheet' />
			<link href='/resources/css/jquery.noty.css' rel='stylesheet' />
			<link href='/resources/css/noty_theme_default.css' rel='stylesheet' />
			<link href='/resources/css/elfinder.min.css' rel='stylesheet' />
			<link href='/resources/css/elfinder.theme.css' rel='stylesheet' />
			<link href='/resources/css/jquery.iphone.toggle.css' rel='stylesheet' />
			<link href='/resources/css/opa-icons.css' rel='stylesheet' />
			<link href='/resources/css/uploadify.css' rel='stylesheet' />


			<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
			<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

			<!-- The fav icon -->
			<link rel="shortcut icon" href="/resuorces/img/favicon.ico">
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
						<li><a href="#">首页</a> <span class="divider">/</span></li>
						<li><a href="#">创意审核</a></li>
					</ul>
				</div>

				<div class="row-fluid">
					<div class="box span12">
						<div class="box-header well">
							<h2>
								<i class="icon-edit"></i> 创意审核管理
							</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">
								
								<fieldset>
									<legend> 广告物料 </legend>
									<!-- 广告下拉框  -->
									<legend> 
									<select style="width: 200px;" data-rel="chosen" id="adList" name="adList" onchange="sendForChange()">
										<c:if test="${not empty adList}">
											<c:forEach var="obj" items="${adList}" varStatus="status">
												<option value="${obj.id}">${obj.adName}	</option>
											</c:forEach>
										</c:if>
									</select>
									<select style="width: 100px;"  data-rel="chosen" id="adStatus" name="adStatus" onchange="sendForChange()">
												<option value="ALL">全部</option>
												<option value="CHECKERROR">待审核</option>
												<option value="PASS">通过</option>
												<option value="REFUSE">拒绝</option>
												<option value="WAITING">审核中</option>
									</select>
									</legend>
									</fieldset>
									<div class="" id="contentDiv" style="width: 100%">
										
									</div>
					</div>
				</div>

			</div>
			<!--/row-->
		</div>
	</div>
	
	
	<%@include file="../inc/footer.jsp"%>
</body>

<script >
$(document).ready(function(){
	sendForChange();
});

function sendForChange() {
	//jQuery("#select1  option:selected").text();
	 var adId = $("#adList").val();
	 //alert(adId);
	 var adStatus = $("#adStatus").val();
	// alert(adStatus);
	 // 设置请求的 url
	 var date = new Date().getMilliseconds();
     var url = "/adCheck/list/" + adId + "_" + adStatus +"?time=" + date;
	// 检查广告是不是有设置广告类目，有就继续，否则就提示设置广告类目
	$.ajax({
		url:url,
		type:'GET',
		dataType:'html',
		async:false,
		success: function(result){
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
