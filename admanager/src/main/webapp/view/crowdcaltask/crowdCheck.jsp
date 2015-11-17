<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>

<c:if test="${not empty crowdList}">
	<ul
		style="list-style-type: none; text-align: left; width: 100%; height: 100%; overflow: auto;">
		<c:forEach var="obj" items="${crowdList}">
			<li style="text-align: left;"><span><input
					class="crowdCheck" type="checkbox" style="opacity: 100;"
					value="${obj.crowdId}" name="${obj.crowdName}" />${obj.crowdName}</span></li>
		</c:forEach>
	</ul>
</c:if>