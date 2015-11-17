<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<div class="tcLsyer_title">
	<span>审核</span>
	<a href="javascript:;" class="close"></a>
</div>
<div class="tcLsyer_content_tab">
	<table class="tablesorter" cellspacing="0"> 
		<thead> 
			<tr> 
				<th>审核人名称</th> 
				<th>审核人部门</th> 
				<th>审核状态</th> 
				<th>审核意见</th> 
				<th>审核时间</th> 
			</tr> 
		</thead> 
	    <tbody>
	      <c:forEach items="${adCheckHistory}" var="checkHistory"> 
	        <tr class="tbody_tr"> 
				<td>${checkHistory.checkUsername}</td> 
				<td>${checkHistory.checkDeptname}</td> 
				<td>
				  <c:if test="${checkHistory.checkSts =='1' }">待审核</c:if>
				  <c:if test="${checkHistory.checkSts =='2' }">审核通过</c:if>
				  <c:if test="${checkHistory.checkSts =='3' }">审核失败</c:if>
				</td> 
				<td>${checkHistory.checkDesc}</td> 
				<td>${fn:substringBefore(checkHistory.checkDate,".0")}</td>
			</tr> 	
		  </c:forEach>																		 
		</tbody> 
	</table>
</div>