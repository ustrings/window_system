<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>

<c:if test="${not empty adArrivalRateDtoList}">
	<c:forEach var="obj" items="${adArrivalRateDtoList}" varStatus="status">
		<tr>
			<td style="width: 5%">${ status.index + 1}</td> 
			<td style="width: 15%">${obj.adName}</td>
			<td style="width: 15%">${obj.ad_acct}</td>
			<td style="width: 15%">${obj.arriveSum}</td>
		</tr>
	</c:forEach>
</c:if>