<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>广告计划</title>

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
						<a href="#">人群管理</a>
					</li>
				</ul>
			</div>
			
			
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i></i> 人群重定向列表</h2>
					</div>
					<div class="box-content">
						<div class="row-fluid">
							<button type="button" id="addcrowRed" class="btn btn-primary">新建人群重定向</button>
						</div>
						<hr/>
						
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
								  <th>名        称</th>
								  <th>网站类型</th>
								  <th>网站描述</th>
								  <th>网站域名</th>
								  <th>操        作</th>
							  </tr>
						  </thead>  
						  <tbody>
							<c:if test="${not empty visitorList}">
								<c:forEach var="visitor" items="${visitorList}">
									<tr >
										<td class="center">${visitor.name}</td>
										<td class="center">${visitor.vcSiteType}</td>
										<td class="center">${visitor.vcSiteDesc}</td>
										<td class="center">${visitor.vcSiteHost}</td>
										<td>
											<a class="btn btn-info" href="/redirection/initEdit/${visitor.vcId}">
												<i class="icon-edit icon-white"></i>  
												编辑                                            
											</a>
											<a class="btn btn-danger" href="/redirection/del/${visitor.vcId }">
												<i class="icon-trash icon-white"></i> 
												删除
											</a>
											<a class="btn btn-success" onclick="showCode('${visitor.vcId}')"  id="showbutton${visitor.vcId}">
												<i class="icon-info-sign icon-white"></i>获取代码
											</a>
											<a  style="display: none;" class="btn btn-success" onclick="hiddenCode('${visitor.vcId}')" id="hidebutton${visitor.vcId }">
												<i class="icon-info-sign icon-white"></i>收回代码
											</a>
										</td>
									</tr>
									<tr style="display: none" id="codeNum${visitor.vcId }"><td colspan="5"><div><textarea readonly="readonly" style="width: 769px; height: 68px;">${visitor.vcCode}</textarea></td></tr>
								</c:forEach>
							</c:if>
						  </tbody>
					  </table>   
        
					</div>
				</div>
			</div>  
		</div>
		</div>
		<hr>
		</div>
	<%@include file="../inc/footer.jsp"%>
</body>
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
	});
});
	function showCode(id){
		$("#hidebutton" + id).show();
		$("#codeNum" + id).show();
		$("#showbutton" + id).hide();
	}
	function hiddenCode(id){
		$("#codeNum" + id).hide();
		$("#showbutton" + id).show();
		$("#hidebutton" + id).hide();
	}
	$("#addcrowRed").click(function(){
		window.location.href = "/redirection/initadd";
	});

</script>
</html>