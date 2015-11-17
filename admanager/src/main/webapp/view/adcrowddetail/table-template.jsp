<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>

<input id="defaultTime" value ="${defaultTime}" type="hidden"/>
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