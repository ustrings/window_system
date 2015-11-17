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
			
			<div id="content" class="span10">
			<div>
				<ul class="breadcrumb">
					<li>
						<a href="#">首页</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#">广告投放明细</a>
					</li>
				</ul>
			</div>
			
			
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i></i> 广告投放明细</h2>
					</div>
					<div class="box-content" style="text-align: center">
					<form class="form-horizontal" method="post" action="/adimpresslog/list" id="queryForm">
						<table style="margin-top: 20px;padding: 20px;text-align: center;">
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
								<td style="padding-left: 50px;">曝光IP：</td>
								<td><input type="text" id="visitor_ip"  name="visitor_ip" value="${adImpressLogDto.visitor_ip}"></input></td>
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
								<td style="padding-top: 15px;padding-left: 20px">是否点击：</td>
								<td style="padding-top: 15px">
									<select id="isClicked" name="isClicked">
										<option value="0" <c:if test="${isClicked=='0'}">selected</c:if>> 否</option>
										<option value="1" <c:if test="${isClicked=='1'}">selected</c:if>>是</option>
									</select>
												<span id="startTimeTips" class="help-inline" style="display:none" ></span></td>
								<td style="padding-top: 15px;padding-left: 50px;">
									<a class="btn" target="_blank"  id="queryBtn">
											<i class="icon-zoom-in icon-white"></i>查询	
									</a>
								</td>
								
								<td style="padding-top: 15px">
									
									</td>
							</tr>
						
						</table>
						</form>
					</div>
					<div class="box-content" >
						<table id="initTable" class="table table-striped table-bordered bootstrap-datatable datatable" style="width: 100%;height: 100%;margin-top:10px;">
						  <thead>
							  <tr>
							  	  <th>序号</th>
								  <th>广告名称</th>
								  <th>曝光页面</th>
								  <th>曝光时间</th>
								  <th>曝光IP</th>
								  <c:if test="${isClicked=='1'}">
								  <th>点击次数</th>
								  </c:if>
								  <th>用户投放轨迹</th>
								  <th>投放依据</th>
							  </tr>
						  </thead>   
						  <tbody>
							<c:if test="${not empty adImpressLogDtoList}">
								<c:forEach var="obj" items="${adImpressLogDtoList}" varStatus="status">
									<tr>
										<td style="width:5%">${ status.index + 1}</td> 
										<td style="width:20%">${obj.adName}</td>
										<td style="width:30%;word-break:break-all;">${obj.impressUrl}</td>
										<td style="width:10%">${obj.ts}</td>
										<td style="width:10%">${obj.visitor_ip}</td>
										 <c:if test="${isClicked=='1'}">
										<td style="width:10%">${obj.isClicked}</td>
										 </c:if>
										 
										 <td>
										 		<a class="adAcctImpClass"  href="javascript:void(1)" ad_id="${obj.adId}" ad_acct="${obj.ad_acct}" >
										 		查看
										 		</a>
										 </td>
										
										<td style="width:15%">
										    <c:choose> 
										      <c:when test="${not empty obj.ad_acct}" >   
										      	<c:if test="${fn:length(obj.ad_acct)!=40}">
										       		<a  target="_blank"  class="viewA" adilogId="${obj.id}"  ad_id="${obj.adId}" ad_acct="${obj.ad_acct}" defaultTime="${obj.ts}">
														<i class="icon-zoom-in icon-white"></i> 查看
													</a>
												</c:if>
										      </c:when> 
										      <c:otherwise>   
										        	
										      </c:otherwise> 
										    </c:choose> 
										</td>
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
	<!--/.fluid-container-->
	<div class="modal hide" id="viewModel"  style="height: 500px;width: 800px;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>投放依据</h3>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
			<div class="box-content" style="text-align: center">
				<form class="form-horizontal" >
					<input type="hidden" id="currentAd_ilog_id" value=""/>
					<input type="hidden" id="current_ad_acct" value=""/>
					<input type="hidden" id="current_ad_id" value=""/>
					<table  style="margin: 20px;padding: 20px;text-align: center;">
							<tr>
								<td style="padding-top: 15px;padding-left: 50px;">开始时间：</td>
								<td style="padding-top: 15px">															
									<input class="datepicker autogrow" id="historyStartTime" name="historyStartTime" value="${adImpressLogDto.startTime}"></input>
												<span id="historyStartTimeTips" class="help-inline" style="display:none" ></span></td>
								<td style="padding-top: 15px;padding-left: 50px;">结束时间：</td>
								<td style="padding-top: 15px">
									<input class="autogrow datepicker"
												id="historyEndTime" name="historyEndTime" value="${adImpressLogDto.endTime}"></input>
												<span id="historyEndTimeTips" class="help-inline" style="display:none"></span>
									</td>
							</tr>
							<tr>
								<td style="padding-top: 15px;padding-left: 50px;">用户ip：</td>
								<td style="padding-top: 15px">															
									<input id="src_ip" name="src_ip"  value=""></input>
								</td>
							</tr>
							<tr>
								<td	colspan="4" style=" border-top-width: 20px;padding-top: 15px;">
									<a class="btn" target="_blank"  id="historyQueryBtn">
											<i class="icon-zoom-in icon-white"></i>查询	
									</a>
								</td>
							</tr>
						</table>
					<table class="table table-bordered"  style="width: 733px;" id="materialTable" >
						  <thead>
							  <tr>
							  	  <th width="10%;">序号</th>
							  	  <th width="20%;">人群名称</th>
							  	  <th width="20%;">定向方式</th>
							  	  <th width="20%;">定向依据</th>
								  <th width="30%;">访问时间</th>
								  <!-- 
								  <th width="15%;">用户ip</th>
								   -->
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
		
	<div class="modal hide" id="adAcctImphistory"  style="height:500px;width:800px;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>该用户历史曝光轨迹</h3>
		</div>
		<div class="modal-body">
			<div class="row-fluid">
			<div class="box-content" style="text-align: center">
				
				<form class="form-horizontal" id="adAcctImpHistoryForm" >
					<input type="hidden" id="adAcctImp" value=""/>
					<input type="hidden" id="adIdImp" value=""/>
						
					<table  style="margin: 20px;padding: 20px;text-align: center;">
							<tr>
								<td style="padding-top: 15px;padding-left: 50px;">开始时间：</td>
								<td style="padding-top: 15px">															
									<input class="datepicker autogrow" id="adAcctImphistoryStartTime" name="adAcctImphistoryEndTime" value="${adImpressLogDto.startTime}"></input>
												<span id="historyStartTimeTips" class="help-inline" style="display:none" ></span></td>
								<td style="padding-top: 15px;padding-left: 50px;">结束时间：</td>
								<td style="padding-top: 15px">
									<input class="autogrow datepicker"
												id="adAcctImphistoryEndTime" name="adAcctImphistoryEndTime" value="${adImpressLogDto.endTime}"></input>
												<span id="historyEndTimeTips" class="help-inline" style="display:none"></span>
								</td>
							</tr>
							<tr>
								<td	colspan="4" style=" border-top-width: 20px;padding-top: 15px;">
									<a class="btn"  target="_blank"  id="adAcctImphistoryQueryBtn">
											<i class="icon-zoom-in icon-white"></i>查询	
									</a>
								</td>
							</tr>
						</table>
				</form>
						
					
					<table class="table table-bordered"  style="width: 733px;" id="adAcctImpTable" >
						  <thead>
							  <tr>
							  	  <th >序号</th>
								  <th >曝光页面</th>
								  <th >曝光时间</th>
								  
							  </tr>
						  </thead> 
						  <tbody id="adAcctHistoryImpBody">
						  </tbody>
					   </table>  
			</div>
			</div>
		</div>
	</div>
	<footer>
    <p class="pull-leftt" >
        <a href="http://www.vaolan.com" target="_blank">Vaolan Corp. 2014</a>
    </p>
    <p class="pull-right_t">
        Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
    </p>
</footer>
<%@include file="../inc/footer.jsp"%>
</body>
<script>
$(document).ready(function(){
	WaitingBar.show();
	$('#initTable').dataTable({
		"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"bAutoWidth":"false",
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
	WaitingBar.hide();
	
	
}).on("click","#queryBtn",function(){
	WaitingBar.show();
	$("#queryForm").submit();
}).on("click",".viewA",function(){
	var id = $(this).attr("adilogId");
	var ad_acct = $(this).attr("ad_acct");
	var ad_id = $(this).attr("ad_id");
	
	var defaultTime = $(this).attr("defaultTime");
	defaultTime = defaultTime.substring(0,10);
	$("#historyStartTime").val("");
	$("#historyEndTime").val("");
	$("#currentAd_ilog_id").val(id);
	$("#current_ad_acct").val(ad_acct);
	$("#current_ad_id").val(ad_id);
	$("#src_ip").val("");
	historyQueryAjax(id,ad_acct,ad_id,"",defaultTime);
	//$("#historyQueryBtn").click();
}).on("click","#viewModel .modal-header .close",function(){
	$('body').removeClass('modal-open');
	$('body .modal-backdrop').remove();
	$("#viewModel").modal("hide");
}).on("click","#historyQueryBtn",function(){
	var id = $("#currentAd_ilog_id").val();
	var ad_acct = $("#current_ad_acct").val();
	var ad_id = $("#current_ad_id").val();
	var src_ip = $("#src_ip").val();
	var isClicked = $("#isClicked").val();
	historyQueryAjax(id,ad_acct,ad_id,src_ip,"", isClicked);
}).on("click",".adAcctImpClass",function(){
	var ad_acct = $(this).attr("ad_acct");
	var ad_id = $(this).attr("ad_id");
	
	$("#adAcctImp").val(ad_acct);
	$("#adIdImp").val(ad_id);

	$("#adAcctImphistoryQueryBtn").click();
	
	$("#adAcctImphistory").modal('show');
	
	
}).on("click","#adAcctImphistoryQueryBtn",function(){
	
	var adAcctImphistoryStartTime = $("#adAcctImphistoryStartTime").val();
	var adAcctImphistoryEndTime = $("#adAcctImphistoryEndTime").val();
	var ad_acct = $("#adAcctImp").val();

	var ad_id = $("#adIdImp").val();

	$.ajax({
		url:"/adimpresslog/adAcctImp/",
		data:{adAcctImphistoryStartTime:adAcctImphistoryStartTime,adAcctImphistoryEndTime:adAcctImphistoryEndTime,ad_acct:ad_acct,ad_id:ad_id},
		async:false,
		type:'GET',
		success:function(data){
			if(oTable){
				 oTable.fnDestroy();
				 $('#adAcctImpTable').attr("class","table table-bordered");
			}
			$("#adAcctHistoryImpBody").empty();
			$("#adAcctHistoryImpBody").html(data);
			
				oTable =  $('#adAcctImpTable').dataTable({
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
			WaitingBar.hide();
		},error:function(){
			WaitingBar.hide();
		}
	});
	
	
	
});


var oTable;
function historyQueryAjax(id,ad_acct,ad_id,src_ip,defaultTime, isClicked){
	WaitingBar.show();
	var historyStartTime = $("#historyStartTime").val();
	var historyEndTime = $("#historyEndTime").val();
	$.ajax({
		url:"/adimpresslog/histortylist/"+id,
		data:{historyStartTime:historyStartTime,historyEndTime:historyEndTime,defaultTime:defaultTime,ad_acct:ad_acct,ad_id:ad_id,src_ip:src_ip, isClicked:isClicked},
		async:false,
		type:'GET',
		success:function(data){
			
			if(oTable){
				 oTable.fnDestroy();
				 $('#materialTable').attr("class","table table-bordered");
			}
			$("#materialBody").empty();
			$("#materialBody").html(data);
			
			var paramStartTime = $("#paramStartTime").val();
			var paramEndTime = $("#paramEndTime").val();
		
			if(paramStartTime!=""){
				$("#historyStartTime").val(paramStartTime);
			}
			if(paramEndTime!=""){
				$("#historyEndTime").val(paramEndTime);
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
			oTable.fnAdjustColumnSizing();
			$("#viewModel").modal('show');
			WaitingBar.hide();
		},error:function(){
			WaitingBar.hide();
		}
	});
}
</script>
</html>
