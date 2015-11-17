<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
</head>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

.shadow {
	top: 0px;
	left: 0px;
	position: absolute;
	background-color: #FFFFFF;
	opacity: 0;
	filter: alpha(opacity = 0);
	display: block;
	z-index: 2000;
}
</style>
<body>

	<input type="hidden" id='impuuid' name="impuuid" value="${impuuid}" />
	<input type="hidden" id='isCollect' name="isCollect"
		value="${isCollect}" />
	<input type="hidden" id='materialId' name="materialId"
		value="${material.ad_m_id}" />
	<input type="hidden" id='adforcerReferer' name='adforcerReferer'
		value="${adforcerReferer}" />
	<input type="hidden" id='adId' name='adId' value="${adId}" />
	<input type="hidden" id='userId' name='userId' value="${userId}" />
	<input type="hidden" id='userIdType' name='userIdType'
		value="${userIdType}" />

	<input type="hidden" id='pv_collect_url' namd='pv_collect_url'
		value="${pv_collect_url}" />
	<input type="hidden" id='click_collect_url' namd='click_collect_url'
		value="${click_collect_url}" />
		
	<div id="picture"
		style="width:${material.width}px; height:${material.height}px;">
		<c:if test="${not empty material}">
			<c:if test="${material.m_type=='1'}">
				<c:choose>
					<c:when test="${d=='m'}">
						<a href="${material.target_url}"
							target="_top" title="${material.material_name}"> <img
							src="${material.link_url}" id="responseUrl" alt="" border=0
							style="width:${material.width}px;height:${material.height}px;" />
						</a>
					</c:when>
					<c:otherwise>
						<a href="${material.target_url}"
							target="_blank" title="${material.material_name}"> <img
							src="${material.link_url}" id="responseUrl" alt="" border=0
							style="width:${material.width}px;height:${material.height}px;" />
						</a>
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:if test="${material.m_type=='2'}">
				<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
					codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"
					style="width:${material.width}px; height:${material.height}px;">
					<param name="movie" value="${material.link_url}">
					<param name="quality" value="high">
					<param name="wmode" value="opaque">
					<embed src="${material.link_url}" quality="high"
						style="width:${material.width}px; height:${material.height}px;"
						wmode="opaque"
						pluginspage="http://www.macromedia.com/go/getflashplayer"
						type="application/x-shockwave-flash" />
				</object>

				<c:choose>

					<c:when test="${material.coverFlag=='1'}">
						<a class="shadow"
									style="width:1px; height:1px;"
									href="${material.target_url}"
									target="_blank" id="responseUrl"></a>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${d=='m'}">
								<a class="shadow"
									style="width:${material.width}px; height:${material.height}px;"
									href="${material.target_url}"
									target="_top" id="responseUrl"></a>
							</c:when>
							<c:otherwise>
								<a class="shadow"
									style="width:${material.width}px; height:${material.height}px;"
									href="${material.target_url}"
									target="_blank" id="responseUrl"></a>
							</c:otherwise>
						</c:choose>

					</c:otherwise>
				</c:choose>



			</c:if>


			<c:if test="${material.m_type=='3'}">
				<div id="responseUrl">${material.richText}</div>

			</c:if>
		</c:if>
	</div>




	<c:if test="${isPutStatCode =='1'}">
		<c:if test="${isCollect =='1'}">
			<div style="display: none">
				<script language="javascript" type="text/javascript"
					src="http://static.vaolan.com/static/js/vlstat_4sh.js?ver=${ver}"></script>
				${material.ad_3stat_code}
			</div>
		</c:if>
	</c:if>

	<c:if test="${not empty material.ad_3stat_code_sub }">
		<c:if test="${isCollect =='1'}">
			<div style="display: none">${material.ad_3stat_code_sub}</div>
		</c:if>
	</c:if>
</body>

</html>

<script src="http://static.sxite.com/static/js/vldsp_4sh.js?ver=${ver}"
	type="text/javascript"></script>
<%--<script src="/resources/js/vldsp_4sh.js?ver=${ver}" type="text/javascript"></script>--%>