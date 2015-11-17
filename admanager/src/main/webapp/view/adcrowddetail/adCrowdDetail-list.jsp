<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>广告人群明细</title>

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
						<a href="#">首页</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#">广告人群明细</a>
					</li>
				</ul>
			</div>
			
			
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i></i> 广告人群明细</h2>
					</div>
					<div class="box-content" style="text-align: center">
					<form class="form-horizontal" method="post" action="/adCrowdDetail/list" id="queryForm">
						<table style="margin: 20px;padding: 20px;text-align: center;">
							<tr style="padding: 5px;">
								<td style="padding-left: 20px;">用户ip：</td>
								<td>
									<input  id="src_ip" name="src_ip" value="${adCrowdBaseInfoDto.src_ip}"></input>
								</td>
								<td style="padding-left: 50px;">人群名称</td>
								<td>
									<select name="crowd_id">
										<option>
												请选择
										</option>
										<c:if test="${not empty crowdList}">
											<c:forEach var="obj" items="${crowdList}" varStatus="status">
														<c:choose>
															<c:when test="${obj.crowdId==adCrowdBaseInfoDto.crowd_id}">
              													<option value="${obj.crowdId}" selected="selected">
																		${obj.crowdName}
																</option>
       															</c:when>
															<c:otherwise>
													             <option value="${obj.crowdId}">
																		${obj.crowdName}
																</option>
													       </c:otherwise>
														</c:choose>
													</c:forEach>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;padding-left: 20px">开始时间：</td>
								<td style="padding-top: 15px">
									<input class="datepicker autogrow" id="startTime" name="historyStartTime" value="${adCrowdBaseInfoDto.historyStartTime}"></input>
												<span id="startTimeTips" class="help-inline" style="display:none" ></span></td>
								<td style="padding-top: 15px;padding-left: 50px;">结束时间：</td>
								
								<td style="padding-top: 15px">
									<input class="autogrow datepicker"
												id="endTime" name="historyEndTime" value="${adCrowdBaseInfoDto.historyEndTime}"></input>
												<span id="endTimeTips" class="help-inline" style="display:none"></span>
									</td>
							</tr>
							<tr>
								<td	colspan="5" style=" border-top-width: 20px;padding-top: 15px;">
									<a class="btn btn-success"  target="_blank"  id="queryBtn" >
											<i class="icon-zoom-in icon-white"></i>查询	
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
								  <th width="10%;">序号</th>
							  	  <th width="20%;">用户AD</th>
							  	  <th width="20%;">用户ip</th>
								  <th width="20%;">访问域名</th>
								  <th width="20%;">搜索关键词</th>
								  <th width="10%;">访问时间</th>
							  </tr>
						  </thead>   
						  <tbody>
							<c:if test="${not empty adCrowdBaseInfoDtoList}">
								<c:forEach var="obj" items="${adCrowdBaseInfoDtoList}" varStatus="status">
									<tr>
										<td>${ status.index + 1}</td>
										<td>${obj.ad_acct}</td>
										<td>${obj.src_ip}</td>
										<td>${obj.host}</td>
										<td >${obj.match_content}</td>
										<td>${obj.ts}</td>
									</tr>
								</c:forEach>
							</c:if>
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
	<div class="modal hide" id="viewModel"  style="height: 500px;width: 870px;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>访问记录</h3>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
			<div class="box-content" style="text-align: center">
				<form class="form-horizontal" >
					<input type="hidden" id="crowdId" value=""/>
					<table >
							<tr>
								<td>开始时间：</td>
								<td>															
									<input class="datepicker autogrow" id="historyStartTime" name="historyStartTime" ></input>
												<span id="historyStartTimeTips" class="help-inline" style="display:none" ></span></td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束时间：</td>
								<td>
									<input class="autogrow datepicker"
												id="historyEndTime" name="historyEndTime" ></input>
												<span id="historyEndTimeTips" class="help-inline" style="display:none"></span>
									</td>
							</tr>
							<tr>
								<td	colspan="4" style="border-top-width: 20px;padding-top: 15px;">
									<a class="btn btn-success"  target="_blank"  id="historyQueryBtn">
											<i class="icon-zoom-in icon-white"></i>查询	
									</a>
								</td>
							</tr>
						</table>
					<table class="table table-bordered"  id="materialTable" style="height: 100%;width: 820px;">
						  <thead>
							  <tr>
							  	  <th width="10%;">序号</th>
							  	  <th width="20%;">用户AD</th>
							  	  <th width="20%;">用户ip</th>
								  <th width="20%;">访问域名</th>
								  <th width="20%;">搜索关键词</th>
								  <th width="10%;">访问时间</th>
							  </tr>
						  </thead> 
						  <tbody id="materialBody">
						  </tbody>
					   </table>  
				</form>
			</div>
			</div>
		</div>
	</div>
	
	<%@include file="../inc/footer.jsp"%>
</body>
<script>
$(document).ready(function(){
	$('#initTable').dataTable({
		"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType": "bootstrap",
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
	
	
}).on("click","#queryBtn",function(){
	WaitingBar.show();
	$("#queryForm").submit();
}).on("click",".viewA",function(){
	var id = $(this).attr("crowdId");
	var defaultTime = $(this).attr("defaultTime");
	$("#historyStartTime").val("");
	$("#historyEndTime").val("");
	$("#crowdId").val(id);
	WaitingBar.show();
	viewQueryAjax(id,defaultTime);
}).on("click","#viewModel .modal-header .close",function(){
	$('body').removeClass('modal-open');
	$('body .modal-backdrop').remove();
	$("#viewModel").modal("hide");
}).on("click","#historyQueryBtn",function(){
	var id = $("#crowdId").val();
	WaitingBar.show();
	viewQueryAjax(id);
}).on("change","#adId",function(){
	var adId = $(this).val();
	getAdCrowds(adId);
});
var oTable;
function viewQueryAjax(id,defaultTime){
	var historyStartTime = $("#historyStartTime").val();
	var historyEndTime = $("#historyEndTime").val();
	$.ajax({
		url:"/adCrowdDetail/viewlist/"+id,
		data:{historyStartTime:historyStartTime,historyEndTime:historyEndTime,defaultTime:defaultTime},
		async:false,
		type:'GET',
		success:function(data){
			if(oTable){
				 oTable.fnDestroy();
				 $('#materialTable').attr("class","table table-bordered");
			}
			
			$("#materialBody").empty();
			$("#materialBody").html(data);
			
			var defaultTime = $("#defaultTime").val();
			if(defaultTime!=""){
				$("#historyStartTime").val(defaultTime);
				$("#historyEndTime").val(defaultTime);
			}
			
				oTable =  $('#materialTable').dataTable({
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
			             "sInfoFiltered": "",
			             "sSearch": "搜索",
			             "oPaginate": {
			                 "sFirst": "首页",
			                 "sPrevious": "上一页",
			                 "sNext": "下一页",
			                 "sLast": "末页"
			             }
			         }
				});
			oTable.fnDraw();
// 			oTable.fnAdjustColumnSizing();
			WaitingBar.hide();
			$("#viewModel").modal('show');
		}
	});
}
/**
 * 获取广告人群
 */
function getAdCrowds(adId){
	$.ajax({
		url:"/adCrowdDetail/getAdCrowds/"+adId,
		data:{},
		async:false,
		type:'GET',
		success:function(data){
			$("#crowdIdSel").html(data);				
		},error:function(){
// 			alert("error");
		}
	});
}

</script>
</html>
