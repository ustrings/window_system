<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>


<input id="paramStartTime" value ="${adCrowdBaseInfoDto.historyStartTime}" type="hidden"/>
<input id="paramEndTime" value ="${adCrowdBaseInfoDto.historyEndTime}" type="hidden"/>
<c:if test="${not empty adCrowdBaseInfoDtoList}">
	<c:forEach var="obj" items="${adCrowdBaseInfoDtoList}" varStatus="status">
		<tr>
			<td>${ status.index + 1}</td> 
			<td>${obj.crowdName}</td>
			<td>
				<c:choose>
					<c:when test="${obj.match_type=='mll'}">
						<c:choose>
								<c:when test="${obj.match_value=='model'}">
									智能模型
								</c:when>
								<c:when test="${obj.match_value=='label'}">
									兴趣标签
								</c:when>
								<c:when test="${obj.match_value=='lbs'}">
									LBS位置
								</c:when>
						</c:choose>
					</c:when>
					<c:when test="${obj.match_type=='keyword'}">
						关键词
					</c:when>
					<c:when test="${obj.match_type=='url'}">
						url匹配
					</c:when>
					<c:when test="${obj.match_type=='rt_tanx'}">
						实时搜索
					</c:when>
				</c:choose>
			</td>
			<td >
			<c:choose>
					<c:when test="${obj.match_type=='mll'}">
						
					</c:when>
					<c:otherwise>
						${obj.match_value}
					</c:otherwise>
				</c:choose>
			</td>
			<td>${obj.ts}</td>
			<!-- 
			<td>${obj.src_ip}</td>
			 -->
		</tr>
	</c:forEach>
</c:if>