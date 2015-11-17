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
					<link
						href="/resources/css/jque<%@include file="/view/inc/directive.inc"%>ry-ui-1.8.21.custom.css"
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
						<li><a href="#">受众人群定制</a></li>
					</ul>
				</div>

				<div class="row-fluid">
					<div class="box span12">
						<div class="box-header well">
							<h2>
								<i class="icon-edit"></i> 受众人群定制
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

							<form class="form-horizontal" method="post">
								<fieldset>
									<legend id="crowdNamePos">基本信息</legend>
									<div class="control-group">
										<label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>受众人群名称</label>
										<div class="controls">
											<!--  <a name="crowdNamePos" id="crowdNamePos"></a>--> 
											<input
												class="input-xlarge focused" id="crowdName" name="crowdName"
												type="text" value="" onfocus="">
												<span id="crowdNameTips" class="help-inline" style="display:none"></span>
										</div>

									</div>
									<div class="control-group">
										<label class="control-label">描述</label>
										<div class="controls">
											<textarea class="autogrow" id="crowdDesc" name="crowdDesc" style="width:320px;"></textarea>
										</div>
									</div>
								</fieldset>

								<fieldset>
									<legend id="KWPos">
										搜索关键词设置 <input type="checkbox" id="searchKwSwitch"
											name="searchKwSwitch" value="S" checked="checked">
									</legend>

									<table>

										<tr>
											<td><a href="#" class="btn btn-primary "
												id="searchKwOceanSel">从关键词海洋中选取</a><a class="btn"
												href="#" id="searchKwOceanDel" name="searchKwOceanDel" style="margin-left:5px;">
													<i class="icon-trash icon-white"></i>移除
											</a></td>

											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

											<td>自定义关键词:</td>

										</tr>


										<tr>
											<td><select multiple="multiple"
												id="searchKwFromOceanSel" name="searchKwFromOceanSel"
												size="15">

											</select> <input type="hidden" id="searchKwFromOcean"
												name="searchKwFromOcean" /></td>

											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

											<td><textarea
													style="resize: none; width: 300px; height: 240px;"
													id="searchKwFormCust" name="searchKwFormCust"></textarea></td>

										</tr>
										<tr>

											<td colspan="3" style="margin-top:5px;"><b style="color: #FF0000;">*</b>匹配<input type="text" name="searchKwNum"
												style="width: 15px;margin-left:5px;" id="searchKwNum" /> 个关键词,介于 现在 和<input
												type="text" name="searchKwTimeNum" style="width: 15px;margin-right:5px;margin-left:5px;"
												id="searchKwTimeNum" /><select id="searchKwTimeType"
												name="searchKwTimeType" style="width: 80px;margin-right:5px;">
													<option value="H">小时</option>
													<option value="D">天</option>
													<option value="M">分钟</option>

											</select> 之前<span id="searchKwTips" class="help-inline" style="display:none; color: #BD4247"></span>
											</td>
										</tr>

									</table>


								</fieldset>


								<fieldset>
									<legend id="URLPos">
										URL设置 <input type="checkbox" id="urlSwitch" name="urlSwitch"
											checked="checked" value="U">
									</legend>



									<table>

										<tr>
											<td><a href="#" class="btn btn-primary" id="urlOceanSel">从URL海洋中选取</a><a
												class="btn" href="#" id="urlOceanDel" style="margin-left:5px;"
												name="urlOceanDel"> <i class="icon-trash icon-white"></i>移除
											</a></td>

											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

											<td>自定义url:</td>

										</tr>


										<tr>
											<td><select multiple="multiple" id="urlFromOceanSel"
												name="urlFromOceanSel" size="15" style="width: 400px;">
											</select> <input type="hidden" id="urlFromOcean" name="urlFromOcean" />
											</td>

											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

											<td><textarea
													style="resize: none; width: 400px; height: 240px;"
													id="urlFromCust" name="urlFromCust"></textarea></td>

										</tr>
										<tr>


											<td colspan="3"><b style="color: #FF0000;">*</b>匹配<input type="text" name="urlNum"
												style="width: 15px;margin-left:5px;" id="urlNum" /> 个url,介 现在 和<input
												type="text" name="urlTimeNum" style="width: 15px;margin-right:5px;margin-left:5px;"
												id="urlTimeNum" /><select id="urlTimeType"
												name="urlTimeType" style="width: 80px;margin-right:5px;">
													<option value="H">小时</option>
													<option value="D">天</option>
													<option value="M">分钟</option>

											</select> 之前<span id="urlTimeTips" class="help-inline" style="display:none; color: #BD4247"></span>
											</td>
										</tr>

									</table>

								</fieldset>

								<div class="form-actions">

									<button type="button" class="btn btn-primary" id="crowdAddSave">保存</button>
									<button class="btn">取消</button>
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


	<div class="modal hide fade" id="keywordOceanModal">

		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>关键词海洋</h3>
		</div>


		<div class="modal-body">
			<div class="row-fluid">
				<div class="box span12">

					<div class="box-content">
						<input class="input-xlarge" id="searchKw" name="searchKw"
							type="text" value="" />

						<table
							class="table table-striped table-bordered bootstrap-datatable datatable">
							<thead>
								<tr>
									<th>关键词</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody id="keywordBody">

							</tbody>
						</table>
					</div>
				</div>


			</div>
		</div>

		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
		</div>

	</div>



	<div class="modal hide fade" id="urlOceanModal">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>url海洋</h3>
		</div>


		<div class="modal-body">
			<div class="row-fluid">
				<div class="box span12">

					<div class="box-content">
						<input class="input-xlarge" id="searchUrlContent"
							name="searchUrlContent" type="text" value="" />

						<table
							class="table table-striped table-bordered bootstrap-datatable datatable">
							<thead>
								<tr>
									<th>url</th>
									<th>内容</th>
									<th>Actions</th>
								</tr>
							</thead>
							<tbody id="urlBody">


							</tbody>
						</table>
					</div>
				</div>

			</div>
		</div>

		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
		</div>

	</div>
	<footer>
	<p class="pull-leftt">
		<a href="http://www.vaolan.com" target="_blank">Vaolan Corp. 2014</a>
	</p>
	<p class="pull-right_t">
		Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
	</p>
	</footer>
	<!--/.fluid-container-->

</body>
<script>
	<!--必填字段的验证  周晓明 -->
	$(document).ready(function(){
		//受众人群 名称 
		$("#crowdName").focusout(function(){
			var $crowdName = $("#crowdName").val();
			if($.trim($crowdName) == ''){
				$("#crowdNameTips").text("受众人群名称不能为空");
				$("#crowdNameTips").parent().parent().addClass("error");
				$("#crowdNameTips").show();
			}
		});
		//搜索关键字匹配
		var regx = /^(\+|-)?\d+$/; //判断是否是正整数 
		//border-color: #BD4247
		$("#searchKwNum").focusout(function(){
			var $searchKwNum = $("#searchKwNum").val();
			if($.trim($searchKwNum) == ''){
				$("#searchKwTips").text("匹配关键词个数不能为空");
				$("#searchKwNum").css("border-color","#BD4247");
				$("#searchKwTips").show();
			}else if(!regx.test($.trim($searchKwNum))){
				$("#searchKwTips").text("匹配关键词个数不合法");
				$("#searchKwNum").css("border-color","#BD4247");
				$("#searchKwTips").show();
			}
		});
		$("#searchKwTimeNum").focusout(function(){
			var $searchurlNum = $("#searchKwTimeNum").val();
			if($.trim($searchurlNum) == ''){
				$("#searchKwTips").text("匹配关键词时间段不能为空");
				$("#searchKwTimeNum").css("border-color","#BD4247");
				$("#searchKwTips").show();
			}else if(!regx.test($.trim($searchKwNum))){
				$("#searchKwTips").text("匹配关键词时间段不合法");
				$("#searchKwTimeNum").css("border-color","#BD4247");
				$("#searchKwTips").show();
			}
		});
		//ulr海洋
		$("#searchKwNum").focusout(function(){
			var $searchKwNum = $("#searchKwNum").val();
			if($.trim($searchKwNum) == ''){
				$("#searchKwTips").text("匹配关键词个数不能为空");
				$("#searchKwNum").css("border-color","#BD4247");
				$("#searchKwTips").show();
			}else if(!regx.test($.trim($searchKwNum))){
				$("#searchKwTips").text("匹配关键词个数不合法");
				$("#searchKwNum").css("border-color","#BD4247");
				$("#searchKwTips").show();
			}
		});
		$("#urlNum").focusout(function(){
			var $urlNum = $("#urlNum").val();
			if($.trim($urlNum) == ''){
				$("#urlTimeTips").text("匹配url个数不能为空");
				$("#urlNum").css("border-color","#BD4247");
				$("#urlTimeTips").show();
			}else if(!regx.test($.trim($urlNum))){
				$("#urlTimeTips").text("匹配url个数不合法");
				$("#urlNum").css("border-color","#BD4247");
				$("#urlTimeTips").show();
			}
		});
		$("#urlTimeNum").focusout(function(){
			var $urlTimeNum = $("#urlTimeNum").val();
			if($.trim($urlTimeNum) == ''){
				$("#urlTimeTips").text("匹配url时间段不能为空");
				$("#urlTimeNum").css("border-color","#BD4247");
				$("#urlTimeTips").show();
			} else if(!regx.test($.trim($urlTimeNum))){
				$("#urlTimeTips").text("匹配url时间段不合法");
				$("#urlTimeNum").css("border-color","#BD4247");
				$("#urlTimeTips").show();
			} 
		});
		
	});
</script>
<script>
	<!--获取焦点事件  周晓明 -->
	$(document).ready(function(){
		$("#crowdName").focus(function(){
			$("#crowdNameTips").parent().parent().removeClass("error");
			$("#crowdNameTips").hide();
		});
		
		$("#searchKwNum").focus(function(){
			$("#searchKwNum").css("border-color","");
			$("#searchKwTips").hide();
		});
		
		$("#searchKwTimeNum").focus(function(){
			$("#searchKwTimeNum").css("border-color","");
			$("#searchKwTips").hide();
		});
		$("#urlNum").focus(function(){
			$("#urlNum").css("border-color","");
			$("#urlTimeTips").hide();
		});
		$("#urlTimeNum").focus(function(){
			$("#urlTimeNum").css("border-color","");
			$("#urlTimeTips").hide();
		});
		
	});
</script>
<script>
//聚焦错误位置 函数 
	function moveHtml(id){
		var scroll_offset = $("#" + id).offset().top;
		$("body,html").animate({
			scrollTop : scroll_offset 
		//让body的scrollTop等于pos的top，就实现了滚动
		}, 500);
	}
</script>
<script>
	$(document)
			.on(
					"click",
					"#crowdAddSave",
					function() {
					/* 	if (crowdName == '') {
							var $crowdNamerequired = $('<font color="red">名称不能为空</font>'); //创建元素  
							$("#crowdName").after($crowdNamerequired);

							var scroll_offset = $("#crowdNamePos").offset().top;
							$("body,html").animate({
								scrollTop : scroll_offset 
							//让body的scrollTop等于pos的top，就实现了滚动
							}, 0);

						} */ 
						var $crowdName = $("#crowdName").val();
						var regx = /^(\+|-)?\d+$/; //判断是否是正整数 
						var $searchKwNum = $("#searchKwNum").val();
						var $searchurlNum = $("#searchKwTimeNum").val();
						var $urlNum = $("#urlNum").val();
						var $urlTimeNum = $("#urlTimeNum").val();
						if($.trim($crowdName) == ''){
							$("#crowdNameTips").text("受众人群名称不能为空");
							$("#crowdNameTips").parent().parent().addClass("error");
							$("#crowdNameTips").show();
							
							/* var scroll_offset = $("#crowdNamePos").offset().top;
							$("body,html").animate({
								scrollTop : scroll_offset 
							//让body的scrollTop等于pos的top，就实现了滚动
							}, 500); */
							moveHtml("crowdNamePos");
						} else if($.trim($searchKwNum) == ''){
							$("#searchKwTips").text("匹配关键词个数不能为空");
							$("#searchKwNum").css("border-color","#BD4247");
							$("#searchKwTips").show();
							moveHtml("KWPos");
						} else if(!regx.test($.trim($searchKwNum))){
							$("#searchKwTips").text("匹配关键词个数不合法");
							$("#searchKwNum").css("border-color","#BD4247");
							$("#searchKwTips").show();
							moveHtml("KWPos");
						}else if($.trim($searchurlNum) == ''){
							$("#searchKwTips").text("匹配关键词时间段不能为空");
							$("#searchKwTimeNum").css("border-color","#BD4247");
							$("#searchKwTips").show();
							moveHtml("KWPos");
						}else if(!regx.test($.trim($searchKwNum))){
							$("#searchKwTips").text("匹配关键词时间段不合法");
							$("#searchKwTimeNum").css("border-color","#BD4247");
							$("#searchKwTips").show();
							moveHtml("KWPos");
						}else if($.trim($urlNum) == ''){
							$("#urlTimeTips").text("匹配url个数不能为空");
							$("#urlNum").css("border-color","#BD4247");
							$("#urlTimeTips").show();
							moveHtml("UrlPos");
						}else if(!regx.test($.trim($urlNum))){
							$("#urlTimeTips").text("匹配url个数不合法");
							$("#urlNum").css("border-color","#BD4247");
							$("#urlTimeTips").show();
							moveHtml("UrlPos");
						}else if($.trim($urlTimeNum) == ''){
							$("#urlTimeTips").text("匹配url时间段不能为空");
							$("#urlTimeNum").css("border-color","#BD4247");
							$("#urlTimeTips").show();
							moveHtml("UrlPos");
						} else if(!regx.test($.trim($urlTimeNum))){
							$("#urlTimeTips").text("匹配url时间段不合法");
							$("#urlTimeNum").css("border-color","#BD4247");
							$("#urlTimeTips").show();
							moveHtml("UrlPos");
						} 	
						else {
							var oceanKeywords = "";
							$("#searchKwFromOceanSel option").each(function() {

								var kw = $(this).val();
								oceanKeywords += kw + ",";

							});

							if (oceanKeywords != "") {
								oceanKeywords = oceanKeywords.substring(0,
										oceanKeywords.length - 1);
							}

							$("#searchKwFromOcean")
									.attr("value", oceanKeywords);

							var oceanUrls = "";
							$("#urlFromOceanSel option").each(function() {

								var url = $(this).val();
								oceanUrls += url + ",";

							});

							if (oceanUrls != "") {
								oceanUrls = oceanUrls.substring(0,
										oceanUrls.length - 1);
							}

							$("#urlFromOcean").attr("value", oceanUrls);

							$(".form-horizontal").attr("action",
									"/crowd/crowdaddsaveaction");
							$(".form-horizontal").submit();
						}

					}).on("click", "#searchKwSwitch", function() {

				/*
				if($(this).attr("checked")){
					
					   
					$("#searchKwOceanSel").attr("disabled",false);
					$("#searchKwOceanDel").attr("disabled",false);
					
					$("#searchKwNum").attr("disabled",false);
					$("#searchKwTimeType").attr("disabled",false);
					$("#searchKwTimeNum").attr("disabled",false);
					
					$("#searchKwFromOcean").attr("disabled",false);
					$("#searchKwFormCust").attr("disabled",false);
					
					
					
				}else{
					
					
				   
					$("#searchKwOceanSel").attr("disabled","disabled");
					$("#searchKwOceanDel").attr("disabled","disabled");
					
					$("#searchKwNum").attr("disabled","disabled");
					$("#searchKwTimeType").attr("disabled","disabled");
					$("#searchKwTimeNum").attr("disabled","disabled");
					
					$("#searchKwFromOcean").attr("disabled","disabled");
					$("#searchKwFormCust").attr("disabled","disabled");
					
				} */

			}).on("click", "#urlSwitch", function() {

				/*
				if($(this).attr("checked")){
					
					
					
					$("#urlOceanSel").attr("disabled",false);
					$("#urlOceanDel").attr("disabled",false);
					
					$("#urlNum").attr("disabled",false);
					$("#urlTimeType").attr("disabled",false);
					$("#urlTimeNum").attr("disabled",false);
					
					
					$("#urlFromOcean").attr("disabled",false);
					$("#urlFromCust").attr("disabled",false);
					
					
				}else{
					$("#urlOceanSel").attr("disabled","disabled");
					$("#urlOceanDel").attr("disabled","disabled");
					
					$("#urlNum").attr("disabled","disabled");
					$("#urlTimeType").attr("disabled","disabled");
					$("#urlTimeNum").attr("disabled","disabled");
					
					
					$("#urlFromOcean").attr("disabled","disabled");
					$("#urlFromCust").attr("disabled","disabled");
				}
				 */

			}).on("click", "#searchKwOceanDel", function() {

				if ($("#searchKwFromOceanSel option:selected").length > 0) {

					$("#searchKwFromOceanSel option:selected").each(function() {
						$(this).remove();
					});

				}

			}).on("click", "#urlOceanDel", function() {

				if ($("#urlFromOceanSel option:selected").length > 0) {

					$("#urlFromOceanSel option:selected").each(function() {
						$(this).remove();
					});
				}
			}).on("click", "#searchKwOceanSel", function(e) {

				e.preventDefault();
				$("#keywordOceanModal").modal('show');

				$("#searchKw").focus();

			}).on("keydown", "#searchKw", function(e) {

				if (e.which == 13) {

					$.ajax({
						url : '/crowd/keywordoceanaction',
						type : 'get',
						data : "searchKw=" + $("#searchKw").val(),
						success : function(data) {

							$("#keywordBody").html(data);
						}
					});

				}
			}).on("click", "#urlOceanSel", function(e) {
				e.preventDefault();
				$('#urlOceanModal').modal('show');

				$("#searchUrlContent").focus();

				$("#urlOceanModal").attr("style", "width: 80%");

				$("#urlOceanModal").css("left", "30%");

			}).on(
					"keydown",
					"#searchUrlContent",
					function(e) {
						if (e.which == 13) {

							$.ajax({
								url : '/crowd/urloceanaction',
								type : 'get',
								data : "searchUrlContent="
										+ $("#searchUrlContent").val(),
								success : function(data) {

									$("#urlBody").html(data);
								}
							});

						}
					});
</script>
</html>
