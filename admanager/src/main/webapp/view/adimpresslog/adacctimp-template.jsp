<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>

<c:if test="${not empty adImpressLogDtoList}">
	<c:forEach var="obj" items="${adImpressLogDtoList}" varStatus="status">
		<tr>
			<td>${status.index + 1}</td> 
			<td>${obj.impressUrl}</td>			
			<td>${obj.ts}</td>
		</tr>
	</c:forEach>
</c:if>