<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>

<c:if test="${not empty adArrivalRateDtoList}">
	<c:forEach var="obj" items="${adArrivalRateDtoList}" varStatus="status">
		<tr>
			<td style="width: 5%">${ status.index + 1}</td> 
			<td style="width: 10%">${obj.adName}</td>
			<td style="width: 10%">${obj.adnum}</td>
			<td style="width: 15%">${obj.impressNum}</td>
			<td style="width: 10%">${obj.arrivalRate}%</td>
			<td style="width: 10%">
				<c:if test="${obj.sts=='A'}">
					待计算
				</c:if>
				<c:if test="${obj.sts=='B'}">
					计算成功
				</c:if>
				<c:if test="${obj.sts=='C'}">
					计算失败
				</c:if>
				<c:if test="${obj.sts=='D'}">
					计算中
				</c:if>
			</td>
			<td style="width: 40%">
				<c:if test="${obj.sts=='A'}">
					<a class="btn btn-success calBtn"  target="_blank"  ad_id="${obj.adId}" >
						<i class="icon-zoom-in icon-white"></i>计算明细	
					</a>
				</c:if>
				<c:if test="${obj.sts=='B'}">
					<a class="btn btn-success calBtn"  target="_blank"  ad_id="${obj.adId}" >
						<i class="icon-zoom-in icon-white"></i>重新计算明细	
					</a>
					<a class="btn btn-success viewBtn"  target="_blank"  ad_id="${obj.adId}" >
							<i class="icon-zoom-in icon-white"></i>到达明细查看	
					</a>
					<a class="btn btn-success notArrivalBtn"  target="_blank"  ad_id="${obj.adId}" >
							<i class="icon-zoom-in icon-white"></i>未到达明细下载
					</a>
				</c:if>
				<c:if test="${obj.sts=='C'}">
					<a class="btn btn-success calBtn"  target="_blank"  ad_id="${obj.adId}" >
						<i class="icon-zoom-in icon-white"></i>计算明细	
					</a>
				</c:if>
				<c:if test="${obj.sts=='D'}">
					
				</c:if>
				
			</td>
		</tr>
	</c:forEach>
</c:if>