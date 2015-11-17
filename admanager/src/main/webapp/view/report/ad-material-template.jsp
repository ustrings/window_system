<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<table class="table table-bordered">
 <thead>
  <tr>
	  <th>广告物料</th>
	  <th>展现量</th>
	  <th>独立访客</th>
	  <th>独立IP</th>
	  <th>点击量</th>
	  <th>点击率</th>
  </tr>
 </thead>   
 <tbody>
 	<c:if test="${not empty reportList}">
		<c:forEach var="r" items="${reportList}">
			<tr>
				<td><a href="#">${r.materialName}</a></td>
				<td class="center">${r.pvNum}</td>
				<td class="center">${r.uvNum}</td>
				<td class="center">${r.ipNum}</td>
				<td class="center">${r.clickNum}</td>
				<td class="center">${r.clickRate}%</td>
			</tr>
		</c:forEach>
	</c:if>
 </tbody>
</table>            