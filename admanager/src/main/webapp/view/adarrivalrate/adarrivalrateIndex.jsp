<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>广告到达率查询</title>

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
						<a href="#">广告到达率查询</a>
					</li>
				</ul>
			</div>
			
			
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i></i> 广告到达率查询</h2>
					</div>
					<div class="box-content" style="text-align: center">
					<form class="form-horizontal" method="post" action="" id="queryForm">
						<table  style="margin: 20px;padding: 20px;text-align: center;">
							<tr style="padding: 5px;">
								<td style="padding-left: 20px;">广告名称：</td>
								<td>
									<select id="adId" name="adId" >
										<c:if test="${not empty adList}">
											<c:forEach var="obj" items="${adList}" varStatus="status">
												<c:choose>
												   <c:when test="${obj.id eq adImpressLogDto.adId}">    
												  	<option value="${obj.id}" selected="selected">
														${obj.adName}
													</option>
												   </c:when>
												   <c:otherwise>  
												   		<option value="${obj.id}" >
															${obj.adName}
														</option>
												   </c:otherwise>
												  
												</c:choose>
											</c:forEach>
										</c:if>
									</select>
								</td>
								<td style="padding-left: 50px;"></td>
								<td></td>
							</tr>
							<tr>
								<td style="padding-top: 15px;padding-left: 20px">开始时间：</td>
								<td style="padding-top: 15px">
									<input class="datepicker autogrow" id="startTime" name="startTime" value="${adImpressLogDto.startTime}"></input>
												<span id="startTimeTips" class="help-inline" style="display:none" ></span></td>
								<td style="padding-top: 15px;padding-left: 50px;">结束时间：</td>
								
								<td style="padding-top: 15px">
									<input class="autogrow datepicker"
												id="endTime" name="endTime" value="${adImpressLogDto.endTime}"></input>
												<span id="endTimeTips" class="help-inline" style="display:none"></span>
									</td>
							</tr>
							<tr>
								<td	colspan="5" style=" border-top-width: 20px;padding-top: 15px;">
									
									<a class="btn btn-success"  target="_blank"  id="queryBtn">
											<i class="icon-zoom-in icon-white"></i>查询	
									</a>
									
									
								</td>
							</tr>
						
						</table>
						</form>
						<form id="uploadForm" action="/adarrivalrate/uploadAds"  method="post" enctype="multipart/form-data" target="uploadIframe">
							<table>
								<tr style="text-align: right;" align="right">
									<td style="text-align: right;" align="right">
											<input  id="currentAdId"   type="hidden" name="currentAdId"  />
											<input  id="uploadInput" type="file"  accept=".csv"  name="adsfile"  />
											<a class="btn btn-success"  target="_blank"  id="uploadBtn">
													<i class="icon-zoom-in icon-white"></i>上传	
											</a>								
									</td>
								</tr>
							</table>
						</form>
						<iframe name="uploadIframe" src="" style="display: none;"></iframe>
					</div>
					<div class="box-content" >
						<table id="initTable" class="table table-striped table-bordered bootstrap-datatable datatable" style="width: 100%;height: 100%;">
						  <thead>
							  <tr>
							  	  <th>序号</th>
								  <th>广告名称</th>
								  <th>预投放AD数</th>
								  <th>实际曝光AD数</th>
								  <th>到达率</th>
								  <th>状态</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody id="searchBody">
						
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
	<div class="modal hide" id="viewModel"  style="height: 500px;width: 800px;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>到达明细</h3>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
			<div class="box-content" style="text-align: center">
				<form class="form-horizontal"  id="subForm">
					<input type="hidden" id="currentAd_ilog_id" value=""/>
					<input type="hidden" id="current_ad_acct" value=""/>
					<input type="hidden" id="current_ad_id" value=""/>
					<table id="subQueryParamTable" style="margin: 20px;padding: 20px;text-align: center;">
							<tr style="padding: 5px;">
								<td style="padding-left: 20px;">广告名称：</td>
								<td>
									<select id="subadId" name="subadId" >
										<c:if test="${not empty adList}">
											<c:forEach var="obj" items="${adList}" varStatus="status">
												<c:choose>
												   <c:when test="${obj.id eq adImpressLogDto.adId}">    
												  	<option value="${obj.id}" >
														${obj.adName}
													</option>
												   </c:when>
												   <c:otherwise>  
												   		<option value="${obj.id}" >
															${obj.adName}
														</option>
												   </c:otherwise>
												  
												</c:choose>
											</c:forEach>
										</c:if>
									</select>
								</td>
								<td style="padding-left: 10px;"></td>
								<td></td>
							</tr>
							<tr>
								<td style="padding-top: 15px;padding-left: 10px;">到达次数>=：</td>
								<td style="padding-top: 15px">															
										<input name="subStartArrivalnum"  value=""/>
								</td>
								<td style="padding-top: 15px;padding-left: 10px;">到达次数<=：</td>
								<td style="padding-top: 15px">
										<input name="subEndArrivalNum"  value="" />
								</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;padding-left: 10px;">开始时间：</td>
								<td style="padding-top: 15px">															
									<input class="datepicker autogrow" id="substartTime" name="substartTime" ></input>
												<span id="substartTimeTips" class="help-inline" style="display:none" ></span></td>
								<td style="padding-top: 15px;padding-left: 10px;">结束时间：</td>
								<td style="padding-top: 15px">
									<input class="autogrow datepicker"
												id="subendTime" name="subendTime" ></input>
												<span id="subendTimeTips" class="help-inline" style="display:none"></span>
									</td>
							</tr>
							<tr>
								<td	colspan="4" style=" border-top-width: 20px;padding-top: 15px;">
									<a class="btn btn-success"  target="_blank"  id="subQueryBtn">
											<i class="icon-zoom-in icon-white"></i>查询	
									</a>
									<a class="btn btn-success"  target="_blank"  id="subDowloadBtn">
											<i class="icon-zoom-in icon-white"  ></i>下载	
									</a>
								</td>
							</tr>
						</table>
					<table class="table table-bordered"  style="width: 733px;" id="subTable" >
						  <thead>
							  <tr>
							  	  <th width="10%;">序号</th>
								  <th width="30%;">广告名称</th>
								  <th width="30%;">AD</th>
								  <th width="15%;">到达次数</th>
							  </tr>
						  </thead> 
						  <tbody id="subBody">
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
	query();		
}).on("click","#queryBtn",function(){
	query();
}).on("click","#uploadBtn",function(){
	WaitingBar.show();
	$("#currentAdId").val($("#adId").val());
	if($("#uploadInput").val()==""){
		alert("请选择一个csv文件");
	}else{
		$("#uploadForm").submit();
	}
}).on("click",".viewBtn",function(){
	var ad_id = $(this).attr("ad_id");
	$("#subadId").val(ad_id);
	clearSubParam();
	subQuery(ad_id);
}).on("click","#viewModel .modal-header .close",function(){
	$('body').removeClass('modal-open');
	$('body .modal-backdrop').remove();
	$("#viewModel").modal("hide");
}).on("click","#subQueryBtn",function(){
	var ad_id = $("#subadId").val();
	subQuery(ad_id);
}).on("click","#subDowloadBtn",function(){
	var ad_id = $("#subadId").val();
	location="/adarrivalrate/downloadDetail/"+ad_id+"/yes";  
}).on("click",".calBtn",function(){
	var ad_id = $(this).attr("ad_id");
	calDetail(ad_id);//到达明细
}).on("click",".notArrivalBtn",function(){
	var ad_id = $(this).attr("ad_id");
	location="/adarrivalrate/downloadDetail/"+ad_id+"/no";  
});


/**
 * 查询
 */
 var oTable1;
 function query(){

	WaitingBar.show();
	$.ajax({
		url:"/adarrivalrate/list",
		data:$("#queryForm").serializeArray(),
		async:true,
		type:'post',
		dataType:'html',
		bSort:false,
		success:function(data){
			if(oTable1){
				 oTable1.fnDestroy();
				 $('#initTable').attr("class","table table-bordered");
			}
			$("#searchBody").html(data);
			oTable1 =  $('#initTable').dataTable({
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
			oTable1.fnDraw();
			oTable1.fnAdjustColumnSizing();
			WaitingBar.hide();
		},error:function(){
			alert("error");
			WaitingBar.hide();
		}
	});
}
function uploadCallBack(result){
	if(result=="true"){
		WaitingBar.hide();
		alert("上传成功");
		query();
	}else{
		WaitingBar.hide();
		alert("上传失败");
	}
}

var oTable;
function subQuery(adid){
	WaitingBar.show();
	$.ajax({
		url:"/adarrivalrate/sublist/"+adid,
		data:$("#subForm").serializeArray(),
		type:'post',
		success:function(data){
			if(oTable){
				 oTable.fnDestroy();
				 $('#subTable').attr("class","table table-bordered");
			}
			$("#subBody").empty();
			$("#subBody").html(data);
			var defaultTime = $("#defaultTime").val();
			if(defaultTime!=""){
				$("#historyStartTime").val(defaultTime);
				$("#historyEndTime").val(defaultTime);
			}
				oTable =  $('#subTable').dataTable({
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
			oTable.fnAdjustColumnSizing();
			$("#viewModel").modal('show');
			WaitingBar.hide();
		},error:function(){
			WaitingBar.hide();
		}
	});
}

function clearSubParam(){
	var inputs = $("#subQueryParamTable").find("input");
	inputs.each(function(){
		$(this).val("");
	});
}
function calDetail(adId){
	WaitingBar.show();
	$.ajax({
		url:"/adarrivalrate/excuteTask/"+adId,
		data:{},
		type:'post',
		dataType:'json',
		success:function(data){
			WaitingBar.hide();
			if(data.result==true){
				query();	
			}else{
				alert("执行失败");
			}
		},error:function(){
			alert("error");
			WaitingBar.hide();
		}
	});
}
</script>
</html>
