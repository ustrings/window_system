<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<div align="right" style="margin-bottom:10px;">
	<input type="button" id="downloadhost" value="数据导出" class="btn btn-primary">
</div>
<table class="table table-bordered" id="table_down">
 <thead>
  <tr>
      <th>时间</th>	  
	  <th>展现量</th>
	  <th>点击量</th>
	  <th>点击率</th>
	  <th>独立访客</th>
	  <th>独立IP</th>
  </tr>
 </thead>   
 <tbody>
 	<c:if test="${not empty hostShowList}">
		<c:forEach var="hostShow" items="${hostShowList}">
			<tr>
				<td class="center" id="time" name="time">${fn:substringBefore(hostShow.ts," ")}</td>
				<td class="center" id = "pv" name="pv">${hostShow.pvNum}</td>
				<td class="center" name="click">${hostShow.clickNum}</td>
				<td class="center" name="clickRate">${hostShow.clickRate} %</td>
				<td class="center" name="uv">${hostShow.uvNum}</td>
				<td class="center" name="ip">${hostShow.ipNum}</td>				
			</tr>
		</c:forEach>
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
	</c:if>
 </tbody>
</table>   
        