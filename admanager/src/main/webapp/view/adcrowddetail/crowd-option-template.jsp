<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<option value="" >
	全部
</option>
<c:if test="${not empty crowdList}">
	<c:forEach var="obj" items="${crowdList}" varStatus="status">
	   		<option value="${obj.crowdId}" >
				${obj.crowdName}
			</option>
	</c:forEach>
</c:if>