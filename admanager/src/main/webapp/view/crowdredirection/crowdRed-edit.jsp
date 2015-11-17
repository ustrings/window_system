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
						<li><a href="#">重定向人群</a></li>
					</ul>
				</div>

				<div class="row-fluid">
					<div class="box span12">
						<div class="box-header well">
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">

							<form class="form-horizontal" method="post" action="/redirection/update" id="editVisitorFrom">
							 <c:if test="${not empty visitordto }">
							 <input type="hidden" value="${visitordto.vcId}" name = "vcId"/>
								<fieldset>
									<legend id="namePOS">编辑基本信息</legend>
									<div class="control-group">
										<label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>名称</label>
										<div class="controls">
											<input class="input-xlarge focused" id="visitor.vcName"
												name="name" type="text" value="${visitordto.name}" /> 
												<span id="vcNameTips" class="help-inline" style="display:none"></span>
										</div>

									</div>
									<div class="control-group">
										<label class="control-label"><b style="color: #FF0000;">*</b>网站类型</label>
										<div class="controls">
										
											<select name="vcSiteType">
												<c:if test="${not empty list}">
													<c:forEach var="category"  items="${list}">
														<c:choose >
														 <c:when test="${visitordto.vcSiteType == category.code}">
															<option   value="${category.code }" selected>
																${category.name }
															</option>
														 </c:when>	
														 <c:otherwise>
															<option   value="${category.code }">
																${category.name }
															</option>
														 </c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">网站描述</label>
										<div class="controls">
											<textarea class="autogrow"
												id="vcSiteDesc" name="vcSiteDesc">${visitordto.vcSiteDesc }</textarea>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>网站域名</label>
										<div class="controls">
											<input class="input-xlarge focused" id="visitor.vcSiteHost"
												name="vcSiteHost" type="text" value="${visitordto.vcSiteHost }" /> 
												<span id="vcSiteHostTips" class="help-inline" style="display:none"></span>
										</div>

									</div>
								</fieldset>
								<br/>
								<div class="form-actions">
									<button type="button" class="btn btn-primary" id="editCrowdRed">保存</button>
									<button class="btn"  type="reset" id="back">返回</button>
								</div>
							</c:if>
							</form>

						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	

	

	<footer>
	<p class="pull-leftt" >
		<a href="http://www.vaolan.com" target="_blank">Vaolan Corp. 2014</a>
	</p>
	<p class="pull-right">
		Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
	</p>
	</footer>
	<!--/.fluid-container-->

	<%@include file="../inc/footer.jsp"%>
</body>
<script id="tmpl" type="text/x-jsrender">
		<tr>
			<td>
				<input type="hidden" name="materialId" value="{{: id}}"/>
				<span>{{: name}}</span>
				{{if mType == 1}}
					<div><img style="width:50px;height:50px;" src="{{: linkUrl}}"/> </div>
				{{else mType == 2}}
					<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0">
						<param name="movie" value="0.swf">
						<param name="quality" value="high">
						<embed src="{{: linkUrl}}" style="width:50px;height:50px;" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"/>
					</object>
				{{else}}
					<div>{{: richText}}</div>
				{{/if}}
			</td>
			<td class="center">{{: type}}</td>
			<td class="center">{{: materialValue}}</td>
			<td class="center">
				<a href="javascript:;" id="{{: id}}" class="edit">修改</a>&nbsp;<a href="javascript:;" class="del">移除</a>
			</td>                                     
		</tr>
</script>
<script id="addMeterialTmpl" type="text/x-jsrender">
		<tr>
			<td>
				<input type="hidden" name="materialId" value="{{: id}}"/>
				<span>{{: materialName}}</span>
				{{if mtype == 1}}
					<div><img style="width:50px;height:50px;" src="{{: linkUrl}}"/> </div>
				{{else mtype == 2}}
					<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0">
						<param name="movie" value="0.swf">
						<param name="quality" value="high">
						<embed src="{{: linkUrl}}" style="width:50px;height:50px;" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"/>
					</object>
				{{else}}
					<div>{{: richText}}</div>
				{{/if}}
			</td>
			<td class="center">
				{{if mtype == 1}}图片
				{{else mtype == 2}}flash
				{{else}}富媒体
				{{/if}}
			</td>
			<td class="center">
				{{: {material_size_value}}
			</td>
			<td class="center">
				<a href="javascript:;" id="{{: id}}" class="edit">修改</a>&nbsp;<a href="javascript:;" class="del">移除</a>
			</td>                                     
		</tr>
</script>
<script type="text/javascript" src="/resources/js/common/common.js"></script>
<script type="text/javascript" src="/resources/js/jquey.form.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.json.min.js"></script>
<script type="text/javascript" src="/resources/js/jsrender.min.js"></script>
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
	});
<!--验证输入框的内容是否合法，是否为空    周晓明-->
	$(document).ready(function(){
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
		
		$("#editCrowdRed").click(function(){
			/* var $name = $("#visitor\\.vcName").val();
			var $type = $("#visitor\\.vcSiteType").val();
			var $desc = $("#visitor\\.vcSiteDesc").val();
			var $host = $("#visitor\\.vcSiteHost").val();  */
			if(checkInput_adVistor()){
				$("#editVisitorFrom").submit();
			}
		});
		
		$("#back").click(function(){
			window.location.href = "/redirection/showlist";	
		});
	
</script>
</html>
