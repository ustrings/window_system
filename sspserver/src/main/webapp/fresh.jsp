<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta charset="UTF-8">
<style>
* {
	margin: 0px;
	padding: 0px;
}

table.gridtable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}

table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}

table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}

.freshbutton {
	Height: 20px;
	BACKGROUND-COLOR: #ffffff;
	COLOR: #009080;
	LINE-HEIGHT: 9pt;
	PADDING-LEFT: 0px;
	PADDING-RIGHT: 0px;
	PADDING-TOP: 2px;
	PADDING-BOTTOM: 2px;
	CURSOR: hand;
	border-width: 2px;
	border-style: solid;
	FONT-FAMILY: 宋体, Sans-serif;
	FONT-SIZE: 9pt;
}
</style>

</head>
<body>
	<h2>诏兰push平台手工reload可投放的广告</h2>
	<br>
	<br>
	<br>


	<form action="" id="freshForm">
	
    <input type="hidden" id="ipport" name="ipport">
	<c:forEach items="${adFreshInfoMap }" var="item">

服务:${item.key}  
<button type="button" ipport="${item.key}"  class="freshbutton">点击我刷新</button>


<br>
<br>
弹窗今日请求量:  <font color="green" size="5" >${todayVal }  </font>
<br>
<br>
当前流量速度，上个10秒: <font color="red" size="5" >${onlineVal }  </font>

		<br>
		<br>
		
		
		正在投方中的广告列表
	
		<table class="gridtable">
			<tr>
				<th>广告归属</th>
				<th>广告id</th>
				<th>尺寸</th>
				<th>广告名称</th>
				<th>投放策略</th>
				<th>广告到期时间</th>
				<th>今日要求投放量</th>
				<th>已经投放量</th>
				<th>已经点击量</th>
				<th>点击率</th>
				<th>状态</th>
				<th>关闭按钮</th>
			</tr>
			
			<c:forEach items="${item.value}" var="adfresh">
			   <c:if test="${adfresh.adId !='sum' }">
				   <tr>
						<td>${adfresh.channel }</td>
						<td>${adfresh.adId }</td>
						<td>${adfresh.size }</td>
						<td>${adfresh.adName }</td>
						<td>${adfresh.crowdOrMt }</td>
						<td>${adfresh.stopTime }</td>
						<td>${adfresh.dayLimitCount }</td>
						<td>${adfresh.adCount }</td>
						<td>${adfresh.clickCount }</td>
						  
						<td><fmt:formatNumber value="${adfresh.clickRate}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>%</td>
						<td>${adfresh.adStatus }</td>
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
					</tr>
			   </c:if>
				
			</c:forEach>
			
			
		</table>


		<br>
		<br>
		<br>
	</c:forEach>

</form>
</body>
</html>
<script type="text/javascript"
	src=" http://code.jquery.com/jquery-1.8.2.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

	}).on("click", ".freshbutton", function() {
		var ipport = $(this).attr("ipport");
		
	    $("#ipport").val(ipport);
		$("#freshForm").attr("action", "/reloadinit");
		$("#freshForm").submit();
	});
</script>
