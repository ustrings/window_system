<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<c:if test="${not empty channelSiteRel}">
     <c:forEach items="${channelSiteRel}" var="channelSiteRel">
		<tr>
			<td class="center">
				<div class="check"><span><input type="checkbox" name="checkBoxChannel" value="${channelSiteRel.id}"></span></div>			
			</td>
			<td class="center">${channelSiteRel.channelName}</td>
            <td class="center">${channelSiteRel.siteDesc}</td>
            <td class="center">${channelSiteRel.siteUrl}</td>
			<td>删除</td>
		</tr>
	</c:forEach>
</c:if>

            