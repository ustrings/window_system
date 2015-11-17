<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>财务记录</title>

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
						<a href="#">财务记录</a>
					</li>
				</ul>
			</div>
			
			
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i></i> 财务记录</h2>
						<!-- <div class="box-icon">
							<a href="#" class="btn btn-setting btn-round"><i class="icon-cog"></i></a>
							<a href="#" class="btn btn-minimize btn-round"><i class="icon-chevron-up"></i></a>
							<a href="#" class="btn btn-close btn-round"><i class="icon-remove"></i></a>
						</div> -->
					</div>
					<div class="box-content">
						<table class="table table-striped table-bordered bootstrap-datatable datatable" style="margin-top:10px;">
						  <thead>
							  <tr>
								  <th>日期</th>
								  <th>类型</th>
								  <th>支出</th>
								  <th>支入</th>
								  <th>余额</th>
								  <th>备注</th>
							  </tr>
						  </thead>   
						  <tbody>
							<c:if test="${not empty userAccounts}">
								<c:forEach var="acct" items="${userAccounts}">
									<tr>
										<td>${acct.accountDate}</td>
										<td class="center">
											<c:choose>
												<c:when test="${acct.accountType == '1'}">广告消耗</c:when>
												<c:when test="${acct.accountType == '2'}">充值</c:when>
												<c:otherwise>--</c:otherwise>
											</c:choose>
										</td>
										<td class="center">
											<c:choose>
												<c:when test="${acct.accountType == '1'}">-${acct.accountAmount}</c:when>
												<c:when test="${acct.accountType == '2'}">--</c:when>
												<c:otherwise>--</c:otherwise>
											</c:choose>
										</td>
										<td class="center">
											<c:choose>
												<c:when test="${acct.accountType == '1'}">--</c:when>
												<c:when test="${acct.accountType == '2'}">+${acct.accountAmount}</c:when>
												<c:otherwise>--</c:otherwise>
											</c:choose>
										</td>
										<td class="center">${acct.accountBalance}</td>
										<td>${acct.remark}</td>
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
		<hr>

		<!-- <div class="modal hide fade" id="myModal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">×</button>
				<h3>Settings</h3>
			</div>
			<div class="modal-body">
				<p>Here settings can be configured...</p>
			</div>
			<div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">Close</a>
				<a href="#" class="btn btn-primary">Save changes</a>
			</div>
		</div> -->

		<footer>
			<p class="pull-leftt" style="font-size:14px;">
				<a href="http://www.vaolan.com" target="_blank">Vaolan Corp.
					2014</a>
			</p>
			<p class="pull-right_t" style="font-size:14px;">
				Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
			</p>
		</footer>
		
	</div><!--/.fluid-container-->
	<%@include file="../inc/footer.jsp"%>

<script>
$(document).ready(function(){
	$('.datatable').dataTable({
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
	
});
</script>
</body>
</html>
