<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<c:if test="${not empty materialList}">
	<c:forEach var="m" items="${materialList}">
		<tr>
			<td class="center">
				<div class="check"><span><input type="checkbox" name="materialCheck" value="${m.id}"></span></div>
				<input type="hidden" value="${m.linkUrl}" name="linkUrl"/>
				<input type="hidden" value="${m.MType}" name="mType"/>
			</td>
			<td><a href="/material/view/${m.id}">${m.materialName}</a></td>
			<td class="center">
				<c:choose>
					<c:when test="${m.MType == 1}">图片</c:when>
					<c:when test="${m.MType == 2}">Flash</c:when>
					<c:otherwise>富媒体</c:otherwise>
				</c:choose>
			</td>
			<td class="center">
				${m.materialValue}
			</td>
			<td class="center"><a href="/material/view/${m.id}" target="_blank">预览</a></td>
		</tr>
	</c:forEach>
</c:if>