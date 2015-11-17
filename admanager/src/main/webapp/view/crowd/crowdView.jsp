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
					<link href="/resources/css/jquery-ui-1.8.21.custom.css"
						rel="stylesheet">
						<link href='/resources/css/fullcalendar.css' rel='stylesheet'>
							<link href='/resources/css/fullcalendar.print.css'
								rel='stylesheet' media='print'>
								<link href='/resources/css/chosen.css' rel='stylesheet'>
									<link href='/resources/css/uniform.default.css'
										rel='stylesheet'>
										<link href='/resources/css/colorbox.css' rel='stylesheet'>
											<link href='/resources/css/jquery.cleditor.css'
												rel='stylesheet'>
												<link href='/resources/css/jquery.noty.css' rel='stylesheet'>
													<link href='/resources/css/noty_theme_default.css'
														rel='stylesheet'>
														<link href='/resources/css/elfinder.min.css'
															rel='stylesheet'>
															<link href='/resources/css/elfinder.theme.css'
																rel='stylesheet'>
																<link href='/resources/css/jquery.iphone.toggle.css'
																	rel='stylesheet'>
																	<link href='/resources/css/opa-icons.css'
																		rel='stylesheet'>
																		<link href='/resources/css/uploadify.css'
																			rel='stylesheet'>

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
						<li><a href="#">首页</a> <span class="divider">/</span></li>
						<li><a href="#">受众人群明细</a></li>
					</ul>
				</div>

				<div class="row-fluid">
					<div class="box span12">
						<div class="box-header well">
							<h2>受众人群:${crowd.crowdName }</h2>
							<div class="box-icon">
								<a href="#" class="btn btn-setting btn-round"><i
									class="icon-cog"></i></a> <a href="#"
									class="btn btn-minimize btn-round"><i
									class="icon-chevron-up"></i></a> <a href="#"
									class="btn btn-close btn-round"><i class="icon-remove"></i></a>
							</div>
						</div>
						<div class="box-content">

							<form class="form-horizontal">
								<fieldset>
									<legend>基本信息</legend>
									<div class="control-group">
										<label class="control-label" for="focusedInput">受众人群名称</label> <input
											type="hidden" value="${crowd.crowdId }" />
										<div class="controls">
											<c:out value="${crowd.crowdName }"></c:out>

										</div>

									</div>
									<div class="control-group">
										<label class="control-label">描述</label>

										<div class="controls">
											<c:out value="${crowd.crowdDesc }"></c:out>

										</div>
									</div>
								</fieldset>

								<fieldset>
									<legend> 搜索关键词 (总共:<c:out value="${fn:length(ckList)}"></c:out> ) </legend>

									<table>

										<tr>
											<td>从关键词海洋中挑选的关键词</td>

											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

											<td>自定义关键词:</td>

										</tr>


										<tr>
											<td><select multiple="multiple"
												id="searchKwFromOceanSel" name="searchKwFromOceanSel"
												size="15">

													<c:if test="${not empty ckList }">
														<c:forEach var="ck" items="${ckList}">
															<c:if test="${ck.sourceType == 'O' }">
																<option value="${ck.keyword }">${ck.keyword }</option>
															</c:if>
														</c:forEach>
													</c:if>


											</select></td>

											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

											<td><textarea
													style="resize: none; width: 300px; height: 240px;"
													id="searchKwFormCust" name="searchKwFormCust" >${htmlKeywordsCust }</textarea></td>

										</tr>
										<tr>


											<td colspan="3">匹配<input type="text" name="searchKwNum"
												style="width: 15px" id="searchKwNum"
												value="${crowdRule.kwNum}" readonly="readonly" /> 个关键词,介于
												现在 和<input type="text" name="searchKwTimeNum"
												style="width: 15px" id="searchKwTimeNum"
												value="${crowdRule.kwTimeNum }" readonly="readonly" /><select
												id="searchKwTimeType" name="searchKwTimeType"
												style="width: 80px" disabled="disabled">

													<option value="H"
														<c:if test="${crowdRule.kwTimeType =='H' }">selected</c:if>>小时</option>
													<option value="D"
														<c:if test="${crowdRule.kwTimeType =='D' }">selected</c:if>>天</option>
													<option value="M"
														<c:if test="${crowdRule.kwTimeType =='M' }">selected</c:if>>分钟</option>
											</select> 之前
											</td>
										</tr>

									</table>


								</fieldset>


								<fieldset>
									<legend> URL(总共:<c:out value="${fn:length(cuList)}"></c:out> ) </legend>

									<table>

										<tr>
											<td>从url海洋中挑选的关键词</td>

											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

											<td>自定义url:</td>

										</tr>


										<tr>
											<td><select multiple="multiple" id="urlFromOceanSel"
												name="urlFromOceanSel" size="15">
													<c:if test="${not empty cuList}">
														<c:forEach var="cu" items="${cuList}">
															<c:if test="${cu.sourceType == 'O' }">
																<option value="${cu.url }">${cu.url }</option>
															</c:if>
														</c:forEach>
													</c:if>
											</select> <input type="hidden" id="urlFromOcean" name="urlFromOcean" />
											</td>

											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

											<td><textarea
													style="resize: none; width: 300px; height: 240px;"
													id="urlFromCust" name="urlFromCust">${htmlUrlsCust }</textarea></td>

										</tr>
										<tr>


											<td colspan="3">匹配<input type="text" name="urlNum"
												style="width: 15px" id="urlNum" value="${crowdRule.urlNum }"
												readonly="readonly" /> 个url,介 现在 和<input type="text"
												name="urlTimeNum" style="width: 15px" id="urlTimeNum"
												value="${crowdRule.urlTimeNum }" readonly="readonly" /><select
												id="urlTimeType" name="urlTimeType" style="width: 80px" disabled="disabled">
													<option value="H"
														<c:if test="${crowdRule.urlTimeType =='H' }">selected</c:if>>小时</option>
													<option value="D"
														<c:if test="${crowdRule.urlTimeType =='D' }">selected</c:if>>天</option>
													<option value="M"
														<c:if test="${crowdRule.urlTimeType =='M' }">selected</c:if>>分钟</option>

											</select> 之前
											</td>
										</tr>

									</table>

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
	<hr>
	<footer>
	<p class="pull-leftt">
		<a href="http://www.vaolan.com" target="_blank">Vaolan Corp. 2014</a>
	</p>
	<p class="pull-right">
		Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
	</p>
	</footer>
	</div>
	<!--/.fluid-container-->
	<%@include file="../inc/footer.jsp"%>
</body>
</html>
