<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
<meta charset="utf-8">
	<title>诏兰PUSH</title>

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
								<link href='/resources/css/uniform.default.css' rel='stylesheet'>
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
																		<link rel="shortcut icon"
																			href="/resources/img/favicon.ico">
																			
																			<link rel="stylesheet" href="./date/themes/base/jquery.ui.all.css">
																			</head>
		<table class="table table-striped table-bordered bootstrap-datatable datatable_num">
		<thead>
			<th>日期</th>
			<th>总数：有效请求量</th>
			<th>总数：被封装次数</th>
			<th>总数：真实展现次数</th>
			<th>总数：打空次数</th>
			
			<th>江苏：有效请求量</th>
			<th>江苏：被封装次数</th>
			<th>江苏：真实展现次数</th>
			<th>江苏：打空次数</th>
			
			<th>上海：有效请求量</th>
			<th>上海：被封装次数</th>
			<th>上海：真实展现次数</th>
			<th>上海：打空次数</th>
			
		</thead>
		
		<c:forEach items="${numEntities}" var="numEntitity">
		<tr>
			<td>${numEntitity.curDate}</td>
			
			<td>${numEntitity.todayVal}</td>
			<td>${numEntitity.frameNum}</td>
			<td>${numEntitity.adShowNum}</td>
			<td>${numEntitity.blankNum}</td>
			
			<td>${numEntitity.todayValJS}</td>
			<td>${numEntitity.frameNumJS}</td>
			<td>${numEntitity.adShowNumJS}</td>
			<td>${numEntitity.blankNumJS}</td>
			
			<td>${numEntitity.todayValSH}</td>
			<td>${numEntitity.frameNumSH}</td>
			<td>${numEntitity.adShowNumSH}</td>
			<td>${numEntitity.blankNumSH}</td>
			
		</tr>
		</c:forEach>
		
		</table>
