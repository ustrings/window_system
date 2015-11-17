<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>人群计算任务</title>

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
	<link href='/resources/css/multi-select.css' rel='stylesheet'>
	
	<!-- multiselect start-->
	<link rel="stylesheet" type="text/css" href="/resources/css/jquery.multiselect.css" />
	
	<link rel="stylesheet" type="text/css" href="/resources/css/prettify.css" />
	<!-- multiselect end-->
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
						<a href="#">首页</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#">人群计算任务</a>
					</li>
				</ul>
			</div>
			
			
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i></i> 人群计算任务</h2>
					</div>
					<div class="box-content" style="text-align: center">
					<form class="form-horizontal" method="post" action="/crowdcaltask/list" id="queryForm">
						<table style="margin: 20px;padding: 20px;text-align: center;">
							<tr style="padding: 5px;">
								<td style="padding-left: 20px;">人群名称：</td>
								<td>
									<input type="hidden" id="crowdIds" value="" name="crowdIds"/>
									<select id="crowdIdSel"  >
										<c:if test="${not empty crowdList}">
											<c:forEach var="obj" items="${crowdList}" varStatus="status">
											   		<option value="${obj.crowdId}" >
														${obj.crowdName}
													</option>
											</c:forEach>
										</c:if>
									</select>
								</td>
								<td style="padding-left: 50px;">任务状态：</td>
								<td>
									<input type="hidden" id="stsHidden" value="${crowdCalTaskDto.taskDto.sts}"/>
									<select id="stsSel" name="taskDto.sts" >
										<option value="" >
												全部
										</option>
								   		<option value="A" >
											待执行
										</option>
										<option value="B" >
												执行成功
										</option>
										<option value="C" >
												执行失败
										</option>
										<option value="D" >
												执行中
										</option>
										
									</select>
								</td>
							</tr>
							<tr style="padding: 5px;">
								<td style="padding-left: 20px;">任务类型：</td>
								<td>
									<input type="hidden" id="taskTypeHidden" value="${crowdCalTaskDto.taskDto.type}"/>
									<select id="stsSel" name="taskDto.type"  >
										<option value="" >
												全部
										</option>
										<c:if test="${obj.type=='1'}">
										
										</c:if>
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
								</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;padding-left: 20px">开始时间：</td>
								<td style="padding-top: 15px">
									<input class="datepicker autogrow" id="startTime" name=start_time value="${crowdCalTaskDto.start_time}"></input>
												<span id="startTimeTips" class="help-inline" style="display:none" ></span></td>
								<td style="padding-top: 15px;padding-left: 50px;">结束时间：</td>
								
								<td style="padding-top: 15px">
									<input class="autogrow datepicker"
												id="endTime" name="end_time" value="${crowdCalTaskDto.end_time}"></input>
												<span id="endTimeTips" class="help-inline" style="display:none"></span>
									</td>
							</tr>
							<tr>
								<td	colspan="5" style=" border-top-width: 20px;padding-top: 15px;">
									<a class="btn btn-success"  target="_blank"  id="queryBtn" >
											<i class="icon-zoom-in icon-white"></i>查询	
									</a>
									<a class="btn btn-success"  target="_blank"  id="addBtn">
													<i class="icon-edit icon-white"></i> 任务新建
												</a>
								</td>
							</tr>
						
						</table>
						</form>
					</div>
					<div class="box-content" >
						<hr/>
						<table id="initTable" class="table table-striped table-bordered bootstrap-datatable datatable" style="width: 100%;height: 100%;">
						  <thead>
							  <tr>
							  	  <th>序号</th>
								  <th>任务名称</th>
								  <th>任务类型</th>
								  <th>人群名称</th>
								  <th>开始时间</th>
								  <th>结束时间</th>
								  <th>任务状态</th>
								  <th>人群总数</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody id="taskBody">
							
						  </tbody>
					  </table>            
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
	<!--/.fluid-container-->
	
	<%@include file="../inc/footer.jsp"%>
</body>
<script>
$(document).ready(function(){
	init();//
}).on("click","#queryBtn",function(){
	query();
}).on("click","#addBtn",function(){
	//添加
	window.location.href = "/crowdcaltask/initadd";
}).on("click",".actionA",function(){
	var taskId = $(this).attr("taskId");
	excutTask(taskId);
	
}).on("click",".downloadA",function(){
	var taskId = $(this).attr("taskId");
	downloadDetail(taskId);
}).on("click",".delA",function(){
	var taskId = $(this).attr("taskId");
	delTask(taskId)
});
function delTask(taskId){
	//删除任务
	WaitingBar.show();
	$.ajax({
		url:"/crowdcaltask/delTask/"+taskId,
		async:true,
		type:'GET',
		dataType:'text',
		success:function(data){
			WaitingBar.hide();
			if(data=="true"){
				alert("删除成功");
				location="/crowdcaltask/index";
			}else{
				alert("系统忙，请稍后重试。");	
			}
		},error:function(){
			alert("error");
			WaitingBar.hide();
		}
	});
}
function excutTask(taskId){
	WaitingBar.show();
	$.ajax({
		url:"/crowdcaltask/excuteTask/"+taskId,
		async:true,
		type:'POST',
		dataType:'json',
		success:function(data){
			WaitingBar.hide();
			if(data.result=="true"){
				query();	
			}else{
				alert(data.msg);	
			}
		},error:function(){
			alert("error");
			WaitingBar.hide();
		}
	});
}

function downloadDetail(taskId){
	//WaitingBar.show();
	$.ajax({
		url:"/crowdcaltask/downloadDetail/"+taskId,
		async:true,
		type:'GET',
		dataType:'text',
		success:function(data){
			WaitingBar.hide();
			alert("success");
		},error:function(){
			alert("error");
			//WaitingBar.hide();
		}
	});
}
function init(){
	
	if($("#stsHidden").val()!=""){
		$("#stsSel option").each(function(){
			if($(this).val()==$("#stsHidden").val()){
				$(this).attr("selected","selected");
			}
		});
	}
	
	$("#crowdIdSel").multiselect({
        noneSelectedText: "==请选择==",
        checkAllText: "全选",
        uncheckAllText: '全不选',
        selectedList:5
    });
	$("#crowdIdSel").multiselect("uncheckAll");
	 query();
}
/**
 * 查询
 */
function query(){
	WaitingBar.show();
	var crowdIds = "";
	var crowdArr = [];
	$("input[name='multiselect_crowdIdSel']").each(function(){
		if($(this).attr("aria-selected")=="true"){
			 crowdArr.push($(this).val());
		 }
	});
	crowdIds = crowdArr.join(",");
	
	$("#crowdIds").val(crowdIds);
	$.ajax({
		url:"/crowdcaltask/list",
		data:$("#queryForm").serializeArray(),
		async:true,
		type:'post',
		success:function(data){
			$("#taskBody").html(data);
			
			$('#initTable').dataTable({
				"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
				"sPaginationType": "bootstrap",
				"bRetrieve": true,
				 "oLanguage": {
		             "sProcessing": "正在加载中......",
		             "sLengthMenu": "每页显示 _MENU_ 条记录",
		             "sZeroRecords": "对不起，查询不到相关数据！",
		             "sEmptyTable": "表中无数据存在！",
		             "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
		             "sInfoEmpty":"当前显示 0 到 0 条，共 0 条记录",
		             "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
		             "sSearch": "搜索",
		             "oPaginate": {
		                 "sFirst": "首页",
		                 "sPrevious": "上一页",
		                 "sNext": "下一页",
		                 "sLast": "末页"
		             }
		         }
			} );
			
			WaitingBar.hide();
		},error:function(){
			WaitingBar.hide();
		}
	});
}
</script>
</html>
