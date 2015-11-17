<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<link rel="stylesheet" type="text/css"  href="/resources/css/kkpager.css" />
<script type="text/javascript" src="/resources/js/kkpager.min.js"></script>
			<article class="module width_3_quarter">
				<div class="tab_container" id="contentDIV">
					<table class="tablesorter" cellspacing="0"> 
					<thead> 
						<tr> 
							<th><input type="checkbox" id="checkedAll"/></th> 
							<th>广告名称</th>
							<th>投放周期</th>
							<th>展现量</th> 
							<th>点击量</th> 
							<!-- <th>移动端展现量</th>  -->
							<!-- <th>移动端点击量</th>  -->
							<th>独立访客</th>
							<th>独立IP</th> 		
							<th>点击率</th> 					
							<th>累计消耗金额</th>
							<th>操作</th>							
						</tr> 
					</thead> 
					<tbody> 
						<c:forEach var="adReportDetail" items="${pager.result}" varStatus="status">
							<tr class="tbody_tr"> 
								<td><input type="checkbox" value="${adReportDetail.adId }" name="checkBox"/></td> 
								<c:if test="${not empty adReportDetail.adUrl}">
									<td><a href="${adReportDetail.adUrl }" target="_blank">${adReportDetail.adName }</a></td>
								</c:if>
								<c:if test="${empty adReportDetail.adUrl}">
									<td><a href="javascript:;" target="_blank">${adReportDetail.adName }</a></td>
								</c:if>
								<td>${fn:substring(adReportDetail.startTime,0,10)}~${fn:substring(adReportDetail.endTime,0,10) }</td>
								<td>${adReportDetail.pvNums }</td> 
								<td>${adReportDetail.clickNums }</td> 
								<td>${adReportDetail.uvNums }</td> 
								<td>${adReportDetail.ipNums }</td> 
								<td>${adReportDetail.rateNums }%</td> 
								<td>${adReportDetail.totalMoney }</td>
								<td><a href="javascript:;" name="details" value="${adReportDetail.adId }" adName="${adReportDetail.adName }">查看广告详情</a></td> 
							</tr> 
						</c:forEach>
					</tbody>
				</table>
				</div>
			</article>
				
				<!-- 存放当前页数 -->
				<div style="display: none">
				<input type="text" id="curPage" name="curPage" value="${pager.curPage}"/>
				<input type="text" id="allPage" name="allPage" value="${pager.allPage}"/>
				<input type="text" id="allRecord" name="allRecord" value="${pager.allRecord}"/>
				<input type="text" id="pageRecord" name="pageRecord" value="${pager.pageRecord}"/>
				</div>
				
				<!--分页开始-->
				<div id="kkpager"></div>
			
<script>
//init
$(function(){
	var totalPage = $('#allPage').val();
	var totalRecords = $('#allRecord').val();
	var pageNo = $('#curPage').val();
	if(!pageNo){
		pageNo = 1;
	}
	//生成分页
	//有些参数是可选的，比如lang，若不传有默认值
	kkpager.generPageHtml({
		pno : pageNo,
		//总页码
		total : totalPage,
		//总数据条数
		totalRecords : totalRecords,
		/* //链接前部
		hrefFormer : 'pager_test',
		//链接尾部
		hrefLatter : '.html', */
		mode : 'click',//默认值是link，可选link或者click
		click : function(n){
			$("#contentDIV").empty();
			searchPageInfo(n) ;
		}
	});
});
</script>