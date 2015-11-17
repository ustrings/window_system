<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<link rel="stylesheet" type="text/css"  href="/resources/css/kkpager.css" />
<script type="text/javascript" src="/resources/js/kkpager.min.js"></script>
			<!-- 	<div class="detail_Layer_title">
				<input type="hidden" id="adId" value=""/>
				<input type="hidden" id="adName" value=""/>
				<span class="is_check">学大-红色-0919-403</span>
				<a href="javascript:;" class="close"></a>
				</div>
			<div class="detail_content"> -->
				<table class="tablesorter" cellspacing="0"> 
					<thead> 
						<tr> 
							<th>编号</th>
							<th>广告名称</th>
							<th>投放时间</th>
							<th>展现量</th> 
							<th>点击量</th> 
							<th>独立访客</th> 
							<th>独立IP</th> 
							<th>点击率</th> 
							<th>累计消耗金额</th>							
						</tr> 
					</thead> 
					<tbody> 
						<c:if test="${not empty list }">
							<c:forEach items="${list }" var="adReportDetail" varStatus="status">
								<tr class="tbody_tr"> 
									<td>${status.index + 1 }</td> 
									<td>${adReportDetail.adName }</td>
									<td>${fn:substring(adReportDetail.startTime,0,10)}</td>
									<td>${adReportDetail.pvNums }</td> 
									<td>${adReportDetail.clickNums }</td> 
									<td>${adReportDetail.uvNums }</td> 
									<td>${adReportDetail.ipNums }</td> 
									<td>${adReportDetail.rateNums }%</td> 
									<td>${adReportDetail.totalMoney }</td> 
								</tr> 
							</c:forEach>
						</c:if>
					</tbody> 
				</table>
				<div style="width:98%;height:30px;line-height:30px;margin:0 auto;">选择时间</div>
				<c:if test="${not empty months }">
					<c:forEach items="${months }" var="monthDto">
						<span style="height:auto;overflow:hidden;line-height:30px;font-size:12px;color:#3399cc;display:block;width:98%;margin:0 auto;">
							<a value="${monthDto.yuefen }" href="javascript:;" name="monthA" style="float:left;margin-right:10px;color:#0099cc;">${monthDto.yuefen}</a>
						</span>	
					</c:forEach>
				</c:if>
