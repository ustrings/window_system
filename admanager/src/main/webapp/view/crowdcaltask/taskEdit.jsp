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
						<li><a href="#">人群计算任务</a></li>
					</ul>
				</div>

				<div class="row-fluid">
					<div class="box span12">
						<div class="box-header well">
							<h2>
								<i class="icon-edit"></i> 编辑任务
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

							<form class="form-horizontal" method="post" action="/crowdcaltask/edit/${taskDto.id}" id="saveForm">
								<fieldset>
									<legend id="namePOS">任务基本信息</legend>
									<div class="control-group">
										<label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>任务名称</label>
										<div class="controls">
											<input class="input-xlarge focused" id="name"
												name="taskDto.name" type="text" value="${taskDto.name}" /> 
												<span id="nameTips" class="help-inline" style="display:none"></span>
										</div>

									</div>
									<div class="control-group">
										<label class="control-label" for="focusedInput"><b
											style="color: #FF0000;">*</b>任务类型</label>
										<div class="controls">
												<select id="typeSel" name="taskDto.type" seltype="${taskDto.type}">
													<option value="" >
															全部
													</option>
											   		<option value="1" >
															人群计算
													</option>
													<option value="2" >
															人群总数
													</option>
													<option value="3" >
															人群明细
													</option>
												</select>
												<span id="typeSelTips" class="help-inline" style="display: none"></span>
										</div>

									</div>
									<div class="control-group">
										<label class="control-label"><b style="color: #FF0000;">*</b>开始时间</label>
										<div class="controls">
											<input class="input-small datepicker autogrow"
												id="start_time" name="taskDto.start_time" value="${taskDto.start_time}"></input>
												<span id="start_timeTips" class="help-inline" style="display:none"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b style="color: #FF0000;">*</b>结束时间</label>
										<div class="controls">
											<input class="autogrow input-small datepicker"
												id="end_time" name="taskDto.end_time" value="${taskDto.end_time}"></input>
												<span id="end_timeTips" class="help-inline" style="display:none"></span>
												<span id="esTimeTips" class="help-inline" style="display:none;color: #BD4247"></span>
										</div>
									</div>
									</fieldset>
									
									<fieldset>
									<legend id="crowdLegend">人群名称</legend>
									<div class="" style="" id="selectedDiv">
										<input type="hidden" name="crowdIds" id="crowdIds"/>
										<a style="cursor: pointer;" id="selAll">全选</a>/<a id="cancelSelAll" style="cursor: pointer;">全不选</a>	
										<span id="crowdNameTips" class="help-inline" style="display:none;color:#BD4247"></span>
										<div class="row-fluid sortable">
											<div class="box span6" >
												<div id="crowdDiv" style="border: 0px solid  rgb(96,96,96);height: 300px;width: 98%;">
								  					
								  				</div>
								  				
											</div>
										</div>
									</div>
								</fieldset>
								<div class="form-actions">
									<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
									<button class="btn"  type="reset">取消</button>
								</div>

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
	<p class="pull-leftt" >
		<a href="http://www.vaolan.com" target="_blank">Vaolan Corp. 2014</a>
	</p>
	<p class="pull-right">
		Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
	</p>
	</footer>
	<!--/.fluid-container-->

	<%@include file="../inc/footer.jsp"%>
	
	<c:if test="${not empty crowdSelList}">
	<c:forEach var="obj" items="${crowdSelList}" varStatus="status">
		<input name="crowdSelHid"  type="hidden" value="${obj.crowd_id}"/>
	</c:forEach>
</c:if>
</body>


<script type="text/javascript" src="/resources/js/common/common.js"></script>
<script type="text/javascript" src="/resources/js/jquey.form.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.json.min.js"></script>
<script type="text/javascript" src="/resources/js/jsrender.min.js"></script>

<script type="text/javascript" >
$(document).ready(function(){
	init();
}).on("click","#saveBtn",function(){
	var crowdIds = getCrowdIds();
	$("#crowdIds").val(crowdIds);
	if(checkInput()){
		$("#saveForm").submit();
	}
}).on("click","#selAll",function(){
	$(".crowdCheck").each(function(){
		$(this).prop("checked",true);
	});
	$("#crowdNameTips").parent().parent().removeClass("error");
	$("#crowdNameTips").hide();
}).on("click","#cancelSelAll",function(){
	$(".crowdCheck").each(function(){
		$(this).removeAttr("checked");
	});
	$("#crowdNameTips").text("人群名称不能为空");
	$("#crowdNameTips").parent().parent().addClass("error");
	$("#crowdNameTips").show();
}).on("click",".crowdCheck",function(){
	$("#crowdNameTips").parent().parent().removeClass("error");
	$("#crowdNameTips").hide();
});
function init(){
	checkNull("name","任务名称不能为空");
	inputFocus("name");
	inputFocus("start_time");
	inputFocus("end_time");
	inputFocus("typeSel");
	removeCheckBoxStyle();
	getCrowd();
	var seltype = $("#typeSel").attr("seltype");
	$("#typeSel option").each(function(){
		if(seltype==$(this).val()){
			$(this).attr("selected","selected");
			return;
		}
	});
}
function removeCheckBoxStyle(){
	$(".crowdCheck").each(function(){
		$(this).attr("style","opacity: 100;");
	});
}
function checkNull(inputId,errorText){
	$("#"+inputId).focusout(function(){
		var $val = $(this).val();
		if($.trim($val) == ''){
			$("#"+inputId+"Tips").text(errorText);
			$("#"+inputId+"Tips").parent().parent().addClass("error");
			$("#"+inputId+"Tips").show();
		}
	});
}

function inputFocus(inputId){
	$("#"+inputId).focus(function(){
		$("#"+inputId+"Tips").parent().parent().removeClass("error");
		$("#"+inputId+"Tips").hide();
	});
}
/**
 * 获取人群ids
 */
function getCrowdIds(){
	var idArray = [];
	$(".crowdCheck").each(function(){
		if($(this).attr("checked")){
			idArray.push($(this).val());
		}
	})
	return idArray.join(",");
}
function moveHtml(id){
	var scroll_offset = $("#" + id).offset().top;
	$("body,html").animate({
		scrollTop : scroll_offset 
	//让body的scrollTop等于pos的top，就实现了滚动
	}, 500);
}
function checkInput(){
	var flag = true;
	
	var $name = $("#name").val();
	var $startTime = $("#start_time").val();
	var $endTime = $("#end_time").val();
	
	if($.trim($name) != ''){
		$("#nameTips").parent().parent().removeClass("error");
		$("#nameTips").hide();
	}
	
	if($.trim($startTime) != ''){
		$("#start_timeTips").parent().parent().removeClass("error");
		$("#start_timeTips").hide();
	}
	if($.trim($endTime) != ''){
		$("#end_timeTips").parent().parent().removeClass("error");
		$("#end_timeTips").hide();
	}
	if(!($.trim($startTime) > $.trim($endTime))){
		$("#esTimeTips").parent().parent().removeClass("error");
		$("#esTimeTips").hide();
	}
	if($.trim($name) == ''){
		$("#nameTips").text("任务名称不能为空");
		$("#nameTips").parent().parent().addClass("error");
		$("#nameTips").show();
		moveHtml("namePOS");
		flag = false;
	}else if($.trim($("#typeSel").val())==""){
		$("#typeSelTips").text("任务类型不能为空");
		$("#typeSelTips").parent().parent().addClass("error");
		$("#typeSelTips").show();
		moveHtml("namePOS");
		flag = false;
	}else if($.trim($startTime) == ''){
		$("#start_timeTips").text("起始时间不能为空");
		$("#start_timeTips").parent().parent().addClass("error");
		$("#start_timeTips").show();
		moveHtml("namePOS");
		flag = false;
	}else if($.trim($endTime) == ''){
		$("#end_timeTips").text("结束时间不能为空");
		$("#end_timeTips").parent().parent().addClass("error");
		$("#end_timeTips").show();
		moveHtml("namePOS");
		flag = false;
	}else if($.trim($startTime) != '' && !$.trim($endTime) == '' && $endTime < $startTime){
		$("#esTimeTips").text("结束时间不能小于起始时间");
		$("#esTimeTips").parent().parent().addClass("error");
		$("#esTimeTips").show();
		moveHtml("namePOS");
		flag = false;
	}else if($.trim($("#crowdIds").val())==""){
		$("#crowdNameTips").text("人群名称不能为空");
		$("#crowdNameTips").parent().parent().addClass("error");
		$("#crowdNameTips").show();
		moveHtml("crowdLegend");
		flag = false;
	}
	
	return flag;
}
/**
 * 获取人群
 */
function getCrowd(){
	$.ajax({
		url:'/crowdcaltask/getCrowdList',
		type:'GET',
		success: function(data){
			$("#crowdDiv").html(data);
			initCrowdSel();
		},error:function(){
			alert("error");
		}
	});
}

/**
 * 初始化   人群选中
 */
function initCrowdSel(){
	
	$("input[name='crowdSelHid']").each(function(){
			var selCrowdId = $(this).val();
			$(".crowdCheck").each(function(){
				if($(this).val()==selCrowdId){
					$(this).attr("checked","checked");
					return;
				}
			});
	});
}
</script>

</html>
