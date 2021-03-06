<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<div align="right" style="margin-bottom:10px;">
	<input type="button" id="downloadurl" value="数据导出" class="btn btn-primary">
</div>
<table class="table table-bordered" id="table_down">
 <thead>
  <tr>
      <th width="3%"><input type="checkbox" name="checkedAll" id="checkedAll"/></th>
	  <th>网址</th>
	  <th>展现量</th>
	  <th>点击量</th>
	  <th>点击率</th>
	  <th>独立访客</th>
	  <th>独立IP</th>
  </tr>
 </thead>   
 <tbody>
 	<c:if test="${not empty reportList}">
		<c:forEach var="report" items="${reportList}">
			<tr>
				<td  width = "3%"><input type="checkbox" name="check_name" value="${report.adId }"/></td>
				<td class="center" id="host" name="host"><a href="/urlCount/hostShow?urlHost=${report.urlHost}&adId=${report.adId}">${report.urlHost}</a></td>
				<td class="center" id = "pv" name="pv">${report.pvNum}</td>
				<td class="center" name="click">${report.clickNum}</td>
				<td class="center" name="clickRate">${report.clickRate} %</td>
				<td class="center" name="uv">${report.uvNum}</td>
				<td class="center" name="ip">${report.ipNum}</td>				
			</tr>
		</c:forEach>
	</c:if>
		<tr style="display: none" id = "sumNum"  bgColor="#DDDDDD">
			    <td  width = "3%"></td>
				<td>统计结果</td>
				<td></td>
				<td class="center" id="pvSum" style="color:blue"></td>
				<td class="center" id="clickSum" style="color:blue"></td>
				<td class="center" id="clickRateSum" style="color:blue"></td>
				<td class="center" id="uvSum" style="color:blue"></td>
				<td class="center" id="ipSum" style="color:blue"></td>
		</tr>
 </tbody>
</table>   
        