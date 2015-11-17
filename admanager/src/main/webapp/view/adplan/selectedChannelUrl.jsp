<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<c:if test="${not empty channelSiteRel}">
     <c:forEach items="${channelSiteRel}" var="channelSiteRel">
		<tr>
			<td class="center">${channelSiteRel.channelName}</td>
            <td class="center">${channelSiteRel.siteDesc}</td>
            <td class="center">${channelSiteRel.siteUrl}</td>
			<td><a href="javascript:viod(0)" class="removeUrl" value="${channelSiteRel.id}">移除</a></td>
		</tr>
	</c:forEach>
</c:if>
<c:if test="${not empty adSiteList}">
    <c:forEach items="${adSiteList}" var="adSite">
		<tr>
			<td class="center">${adSite.channelName}</td>
            <td class="center">${adSite.siteDesc}</td>
            <td class="center">${adSite.url}</td>
			<td><a href="javascript:void(0)" onclick="deleteChannelUrl()">删除</a></td>
		</tr>
	</c:forEach>
</c:if>
