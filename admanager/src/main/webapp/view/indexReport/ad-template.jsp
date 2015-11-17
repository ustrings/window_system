<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>

<div align="right" style="margin-bottom:10px;">
	<input type="button" id="download" value="数据导出" class="btn btn-primary" style="margin-right:5px;">
</div>
<table class="table table-bordered" id="table_down">
 <thead>
  <tr>
      <th width="3%"><input type="checkbox" name="checkedAll" id="checkedAll" checked="checked" /></th>
	  <th>广告</th>
	  <th>类型</th>
	  <th>展现量</th>
	  <th>独立访客</th>
	  <th>独立IP</th>
	  <th>点击量</th>
	  <th>点击率</th>
	  <th>移动端展现量</th>
	  <th>移动端点击量</th>
	  <th>关闭按钮点击量</th>
	  <th>CPM单价（元）</th>
	  <th>总消耗金额（元）</th>
  </tr>
 </thead>   
 <tbody>
 	<c:if test="${not empty reportList}">
		<c:forEach var="report" items="${reportList}">
			<tr>
				<td  width = "3%"><input type="checkbox"  checked="checked" name="check_name" value="${report.adId }" /></td>
				<td><a href="/adplan/initedit/${report.adId }" title="adplan/initedit/${report.adId }">${report.adName}</a></td>
				<c:if test="${report.adLinkType == 'M' }">
					<td class="center" name="linkType" title="此类创意,展现量和点击量都准确。">物料</td>
				</c:if>
				<c:if test="${report.adLinkType == 'E' }">
					<td class="center" name="linkType" title="此类创意,展现量是准确的,点击量只能作参考。">链接</td>
				</c:if>
				<c:if test="${report.adLinkType == 'J' }">
					<td class="center" name="linkType" title="此类创意,展现量是准确去的,点击量统计不到。">代码</td>
				</c:if>
				<td class="center" id = "pv" name="pv">${report.pvNum}</td>
				<td class="center" name="uv">${report.uvNum}</td>
				<td class="center" name="ip">${report.ipNum}</td>
				<td class="center" name="click">${report.clickNum}</td>
				<td class="center" name="clickRate">${report.clickRate} %</td>
				<td class="center" name="mobilePv">${report.mobilePvNum }</td>
				<td class="center" name="mobileClick">${report.mobileClickNum }</td>
			    <td class="center" name="close">${report.closeNum}</td>
			    <td class="center">${report.unitPrice}</td>
			    <td class="center" name="totalAmount">${report.totalAmount}</td>
			</tr>
		</c:forEach>
		<tr id="sumNum"  bgColor="#DDDDDD">
			<td  width = "3%"></td>
				<td>统计结果</td>
				<td class="center" style="color:blue"></td>
				<td class="center" id="pvSum" style="color:blue"></td>
				<td class="center" id="uvSum" style="color:blue"></td>
				<td class="center" id="ipSum" style="color:blue"></td>
				<td class="center" id="clickSum" style="color:blue"></td>
				<td class="center" id="clickRateSum" style="color:blue"></td>
				<td class="center" id="mobilePvSum" style="color:blue"></td>
				<td class="center" id="mobileClickSum" style="color:blue"></td>
				<td class="center" id="closeSum" style="color:blue"></td>
				<td class="center">--</td>
			    <td class="center" id="totalAmount" style="color:blue"></td>
		</tr>
	</c:if>
 </tbody>
</table>   
        