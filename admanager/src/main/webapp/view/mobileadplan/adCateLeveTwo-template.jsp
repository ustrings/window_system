<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>

<c:if test="${not empty adCateList}">
	<ul style="list-style-type:none;text-align: left;width: 100%;height: 100%;overflow:auto;" >
		<c:forEach var="obj" items="${adCateList}">
				<li style="text-align: left;"><span><input class="adCategory_mediaCateCheck" type="checkbox" value="${obj.id}"  parentCode="${obj.parentCode}" name="${obj.name}" />${obj.name}</span></li>
		</c:forEach>
	</ul>
</c:if>