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
						<li><a href="#">首页</a> <span class="divider">/</span></li>
						<li><a href="#">重定向人群定制</a></li>
					</ul>
				</div>

				<div class="row-fluid">
					<div class="box span12">
						<div class="box-header well">
							<h2>
								<i class="icon-edit"></i> 重定向人群定制
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

							<form class="form-horizontal" method="post" id="addVisitorFrom" action="/redirection/addnew">
								<fieldset>
									<legend id="crowdNamePos">基本信息</legend>
									<div class="control-group">
										<label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>名称</label>
										<div class="controls">
											<input class="input-xlarge focused" id="visitor.vcName"
												name="name" type="text" value="${visitordto.name}" /> 
												<span id="vcNameTips" class="help-inline" style="display:none"></span>
										</div>

									</div>
									<div class="control-group">
										<label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>网站类型</label>
										<div class="controls">
											<select name="vcSiteType">
												<c:if test="${not empty list }">
													<c:forEach var="category" items="${list }">
														<option value="">${category.name }</option>
													</c:forEach>
												</c:if>
											</select>
										</div>

									</div>
									<div class="control-group">
										<label class="control-label">网站描述</label>
										<div class="controls">
											<textarea class="autogrow" id="vcSiteDesc" name="vcSiteDesc"></textarea>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>网站域名</label>
										<div class="controls">
											<input class="input-xlarge focused" id="visitor.vcSiteHost"
												name="vcSiteHost" type="text" /> 
												<span id="vcSiteHostTips" class="help-inline" style="display:none"></span>
										</div>
									</div>
								</fieldset>
								<div class="form-actions">
									<input type="button" class="btn btn-primary" id="addCrowdRed" value="确定"/>
									<input type="button" class="btn btn-primary" value="返回" id="back"/>
								</div>
								</fieldset>
							</form>

						</div>
					</div>
				</div>

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
<script>
<!--当输入内容获取焦点是 事件   周晓明 -->
$(document).ready(function(){
	//名称 
	$("#visitor\\.vcName").focus(function(){
		$("#vcNameTips").parent().parent().removeClass("error");
		$("#vcNameTips").hide();
	});
	//网站域名 
	$("#visitor\\.vcSiteHost").focus(function(){
		$("#vcSiteHostTips").parent().parent().removeClass("error");
		$("#vcSiteHostTips").hide();
	});

	//名称 
	$("#visitor\\.vcName").focusout(function(){
		var $vcName = $("#visitor\\.vcName").val();
		if($.trim($vcName) == ''){
			$("#vcNameTips").text("名称不能为空");
			$("#vcNameTips").parent().parent().addClass("error");
			$("#vcNameTips").show();
		}
	});
	$("#visitor\\.vcSiteHost").focusout(function(){
		var $vcSiteHost = $("#visitor\\.vcSiteHost").val();
		if($.trim($vcSiteHost) == ''){
			$("#vcSiteHostTips").text("时间段不能为空");
			$("#vcSiteHostTips").parent().parent().addClass("error");
			$("#vcSiteHostTips").show();
		}
	});
});

function checkInput_adVistor(){
var flag = true;
var $vcName = $("#visitor\\.vcName").val();
var $vcSiteHost = $("#visitor\\.vcSiteHost").val();
if($.trim($vcName) == ''){
	$("#vcNameTips").text("名称不能为空");
	$("#vcNameTips").parent().parent().addClass("error");
	$("#vcNameTips").show();
	flag = false;
}else if($.trim($vcSiteHost) == ''){
	$("#vcSiteHostTips").text("网站域名 不能为空");
	$("#vcSiteHostTips").parent().parent().addClass("error");
	$("#vcSiteHostTips").show();
	flag = false;
}
return flag;
}

$("#addCrowdRed").click(function(){
	if(checkInput_adVistor()){
		$("#addVisitorFrom").submit();
	}
});

$("#back").click(function(){
	window.location.href = "/redirection/showlist";	
});
</script>

</html>
