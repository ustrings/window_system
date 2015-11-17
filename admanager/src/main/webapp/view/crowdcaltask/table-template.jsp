<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>

<c:if test="${not empty taskDtoList}">
	<c:forEach var="obj" items="${taskDtoList}" varStatus="status">
		<tr>
			<td style="width: 5%">${ status.index + 1}</td> 
			<td style="width: 10%">${obj.name}</td>
			<td style="width: 10%">${obj.typeName}</td>
			<td style="width: 15%">${obj.crowdName}</td>
			<td style="width: 10%" class="center">${obj.start_time}</td>
			<td style="width: 10%" class="center">${obj.end_time}</td>
			<td style="width: 10%" class="center">${obj.sts}</td>
			<td style="width: 10%" class="center">${obj.count}</td>
			<td style="width: 20%;text-align: center;">
		   		<c:if test="${obj.sts=='待执行'}">
					<a class="btn btn-success actionA"  target="_blank"  taskId="${obj.id}">
						<i class="icon-zoom-in icon-white"></i> 执行
					</a>
					<a class="btn btn-success editA"  href="/crowdcaltask/initEdit/${obj.id}">
						<i class="icon-edit icon-white"></i> 修改
					</a>
					
					<a class="btn btn-success delA"  target="_blank"  taskId="${obj.id}">
						<i class="icon-edit icon-white"></i> 删除
					</a>
				</c:if>
				<c:if test="${obj.sts=='执行失败'}">
					<a class="btn btn-success actionA"  target="_blank"  taskId="${obj.id}">
						<i class="icon-zoom-in icon-white"></i> 重新执行
					</a>
					<a class="btn btn-success delA"  target="_blank"  taskId="${obj.id}">
						<i class="icon-edit icon-white"></i> 删除
					</a>
				</c:if>
				
				<c:if test="${obj.sts=='执行成功'}">
					<a class="btn btn-success actionA"  target="_blank"  taskId="${obj.id}">
						<i class="icon-zoom-in icon-white"></i> 重新执行
					</a>
					<c:if test="${obj.type==3}">
						<a class="btn btn-success downloadA1"  href="/crowdcaltask/downloadDetail/${obj.id}"  target="_blank"  taskId="${obj.id}">
							<i class="icon-zoom-in icon-white"></i> 下载明细
						</a>
					</c:if>
					<a class="btn btn-success delA"  target="_blank"  taskId="${obj.id}">
						<i class="icon-edit icon-white"></i> 删除
					</a>
				</c:if>
				
				<c:if test="${obj.sts=='执行中'}">
					
				</c:if>
				
				
			</td>
		</tr>
	</c:forEach>
</c:if>