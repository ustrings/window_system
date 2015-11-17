<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
<script src="../resources/date/js/jquery-1.7.2.min.js"></script>
<script src="../resources/date/js/date.init.js"></script>
<script src="../resources/date/js/jquery.ui.core.js"></script>
<script src="../resources/date/js/jquery.ui.widget.js"></script>
<script src="../resources/date/js/jquery.ui.datepicker.js"></script>
<link rel="stylesheet" href="../resources/date/themes/demos.css">
</head>

<body>

	<div class="box-content">
		<h2>诏兰push平台手工reload可投放的广告</h2>
	<br/>
	<br/>
	<h3>今日数据信息</h3>
		<table>
			<tbody>
			<tr style="height: 30px;"><td></td><td></td><td></td></tr>
				<tr>
					<td>当前所有流量速度，QPS: </td>
					<td></td>
					<td><font color="red" size="5">${allOnlineVal }</font></td>
				</tr>
				<tr style="height: 30px;"><td></td><td></td><td></td></tr>
				<tr>
					<td>当前有效流量速度，QPS: </td>
					<td></td>
					<td><font color="red" size="5">${vaildOnlineVal }</font></td>
				</tr>
			</tbody>
		</table>
		
		
		<br> <br>总共:弹窗今日有效请求量: <font color="green" size="5">${todayVal }</font> 被封装的次数：<font color="green" size="5">${frameNum } </font>
				&nbsp;&nbsp; 真实展现的次数：<font color="green" size="5">${adShowNum }
			</font> &nbsp;&nbsp; 打空的次数：<font color="green" size="5">${blankNum }</font>
			
		<br> <br>江苏:弹窗今日有效请求量: <font color="green" size="5">${todayValJS }</font> 被封装的次数：<font color="green" size="5">${frameNumJS } </font>
				&nbsp;&nbsp; 真实展现的次数：<font color="green" size="5">${adShowNumJS }
			</font> &nbsp;&nbsp; 打空的次数：<font color="green" size="5">${blankNumJS }</font>
			
			
		<br> <br>上海:弹窗今日有效请求量: <font color="green" size="5">${todayValSH }</font> 被封装的次数：<font color="green" size="5">${frameNumSH } </font>
				&nbsp;&nbsp; 真实展现的次数：<font color="green" size="5">${adShowNumSH }
			</font> &nbsp;&nbsp; 打空的次数：<font color="green" size="5">${blankNumSH }</font>
			
			
			<br/>
			<br/>
			<h3>历史数据查询</h3>
			
			<div>
			<table class="gridtable">
			<tr>
				<td><strong>查询条件：</strong></td>
				<td>开始时间：</td>
				<td>
                    <!-- 强制设置日期控件设置默认时间 -->
                    <input type="text" id="startTime" size="20" name="startTime"  class="input_datepicker"/>
                </td>
				<td>结束时间：</td>
				<td>
                    <!-- 强制设置日期控件设置默认时间 -->
                    <input type="text" id="endTime" size="20" name="endTime"  class="input_datepicker"/>
                </td>

				<td>
				&nbsp;
					<button onclick="queryHistory()">查询</button>
					<button onclick="downloadHistory()">下载</button>
				</td>
			</tr>
		</table>
			</div>
			
			<div id="contentDIV">
				
			</div>
			
			
			
								<form action="" id="freshForm">
									<input type="hidden" id="ipport" name="ipport"></input>
								</form>
	</div>
	
	
	<h3>正在投放广告信息</h3>
	<c:forEach items="${adFreshInfoMap }" var="item">


		<div class="box-content">
			服务:${item.key}
			<button type="button" ipport="${item.key}" class="freshbutton">点击我刷新</button>
			<br />

			<table
				class="table table-striped table-bordered bootstrap-datatable datatable">
				<thead>
					<tr>
						<th>归属</th>
						<th>广告id</th>
						<th>尺寸</th>
						<th>广告名称</th>
						<th>投放策略</th>
						<th>到期时间</th>
						<th>要求投放量</th>
						<th>投放量</th>
						<th>点击量</th>
						<th>CTR</th>
						<th>状态</th>
						<th>完成比例</th>
						<th>关闭按钮</th>
					</tr>
				</thead>

				<tbody>

					<c:forEach items="${item.value}" var="adfresh">
						<c:if test="${adfresh.adId !='sum' }">
							<tr>
								<td>${adfresh.channel }</td>
								<td>${adfresh.adId }</td>
								<td>${adfresh.size }</td>
								<td><a
									href="http://ad.adtina.com/ad/1/${adfresh.adId }?type=view"
									target="_blank">${adfresh.adName }</a></td>
								<td>${adfresh.crowdOrMt }</td>
								<td>${adfresh.stopTime }</td>
								<td>${adfresh.dayLimitCount }</td>
								<td>${adfresh.adCount }</td>
								<td>${adfresh.clickCount }</td>
								<td><fmt:formatNumber value="${adfresh.clickRate}"
										pattern="##.##" minFractionDigits="2"></fmt:formatNumber>%</td>

								<td><c:choose>
										<c:when test="${adfresh.adStatus =='今日到量停止' }">
											<span class="label label-success">${adfresh.adStatus }</span>
										</c:when>
										<c:otherwise>
											<span class="label label-info">${adfresh.adStatus }</span>
										</c:otherwise>
									</c:choose></td>

								<td><c:choose>
										<c:when test="${adfresh.adStatus =='今日到量停止' }">

											<font color="green"> <fmt:formatNumber
													value="${adfresh.finishRate}" pattern="##.##"
													minFractionDigits="2"></fmt:formatNumber>%
											</font>
										</c:when>
										<c:otherwise>
											<font color="red"> <fmt:formatNumber
													value="${adfresh.finishRate}" pattern="##.##"
													minFractionDigits="2"></fmt:formatNumber>%
											</font>
										</c:otherwise>
									</c:choose></td>
								<td>${adfresh.closeType }</td>
							</tr>
						</c:if>

					</c:forEach>

					<c:forEach items="${item.value}" var="adfresh">
						<c:if test="${adfresh.adId =='sum' }">
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>${adfresh.dayLimitCount }</td>
								<td>${adfresh.adCount }</td>
								<td>${adfresh.clickCount }</td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
							</tr>
						</c:if>

					</c:forEach>

				</tbody>
			</table>
		</div>
	</c:forEach><%@include file="inc/footer.jsp"%>
</body>
<script>

$(document).ready(function(){
	$('.datatable').dataTable({
		"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType": "bootstrap",
		"aLengthMenu" : [100, 200, 300], //更改显示记录数选项  
		"iDisplayLength" : 100, //默认显示的记录数
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
}).on("click", ".freshbutton", function() {
	var ipport = $(this).attr("ipport");
	
    $("#ipport").val(ipport);
	$("#freshForm").attr("action", "/reloadinit_new");
	$("#freshForm").submit();
});

function downloadHistory() {
	if(!validateDate("startTime","endTime")) {
		return;
	}
	 var startTime = $("#startTime").val();
	 var endTime = $("#endTime").val();
    var url="downloadExcel?startTime=" + startTime + "&endTime=" + endTime;
    window.open(url);
}


/**
 * 检查结束时间是不是大于开始时间
 */
function validateDate(startId,endId) {
    var startTime = $("#" + startId).val();
    var endTime = $("#" + endId).val();

    var startDate=Date.parse(startTime.replace(/-/g,"/"));
    var endDate=Date.parse(endTime.replace(/-/g,"/"));
    if(startDate > endDate) {
        alert("开始时间大于结束时间");
        $("#startTime").val(endTime);
        return false;
    }
    return true;
}

// /**
//  *  验证结束时间是不是大于等于开始时间
//  */
// function validateDate() {
// 	 var startTime = $("#startTime").val();
// 	 var endTime = $("#endTime").val();
	 
// 	 var startDate=Date.parse(startTime.replace(/-/g,"/"));
// 	 var endDate=Date.parse(endTime.replace(/-/g,"/"));
// 	 if(startDate > endDate) {
// 		 alert("开始时间大于结束时间");
// 		 $("#startTime").val(endTime);
// 		 return false;
// 	 }
// 	 return true;
// }

/**
 * 查询历史数据
 */
function queryHistory() {
	if(!validateDate("startTime","endTime")) {
		return;
	}
	 var startTime = $("#startTime").val();
	 var endTime = $("#endTime").val();
	 
	
	 // 设置请求的 url
	
    var url = "/queryHistoryNum";
	// 检查广告是不是有设置广告类目，有就继续，否则就提示设置广告类目
	$.ajax({
		url:url,
		data:{startTime:startTime, endTime:endTime},
		type:'GET',
		dataType:'html',
		async:true,
		success: function(result){
			$("#contentDIV").empty();
			$("#contentDIV").append(result);
			
			$('.datatable_num').dataTable({
				"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
				"sPaginationType": "bootstrap",
				"aLengthMenu" : [10, 20, 30], //更改显示记录数选项  
				"iDisplayLength" : 10, //默认显示的记录数
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
		},error:function(){
			alert("error");
		}
	});
}


</script>
</html>
