<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<link rel="stylesheet" type="text/css"  href="/resources/css/kkpager.css" />
<script type="text/javascript" src="/resources/js/kkpager.min.js"></script>
				<article class="module width_3_quarter">
					<div class="tab_container">
						<div id="tab1" class="tab_content">
							<table class="tablesorter" cellspacing="0"> 
					<thead> 
						<tr> 
		    				<th>广告名称</th> 
		    				<th>投放周期</th> 
		    				<th>投放策略</th> 
		    				<th>投放状态</th> 
		    				<th>当天投放量</th>
		    				<th>当天点击量</th>
		    				<th>当天CTR</th>
		    				<th>操作</th>
						</tr> 
					</thead> 
					<tbody> 
						<c:forEach var="adCountDetail" items="${pager.result}" varStatus="status">
							<tr class="tbody_tr"> 
								<c:choose>  
											        <c:when test="${adCountDetail.linkType == 'M'}">  
											        	<td><a target="_blank" href="${adCountDetail.adUrl }">${adCountDetail.adName }</a></td> 
											        </c:when> 
											         <c:when test="${adCountDetail.linkType == 'E'}">  
											             <td><a target="_blank" href="${adCountDetail.throwUrl }">${adCountDetail.adName }</a></td>
											        </c:when>  
											        <c:otherwise>  
											        	<td><a target="_blank" href="/ad_count/jsShow?adId=${adCountDetail.adId }">${adCountDetail.adName }</a></td>
											        </c:otherwise>    
								</c:choose>
								<td>${fn:substring(adCountDetail.startTime,0,10)} ~ ${fn:substring(adCountDetail.endTime,0,10)}</td> 
								<td>${adCountDetail.adStrategy }</td>
			    				<td>
			    					<c:if test="${adCountDetail.adTFsts == 2}">
			    						<span class="btn-oval-open" name="planSts" value="2" id="${adCountDetail.adId }"></span>
			    					</c:if>
			    					<c:if test="${adCountDetail.adTFsts == 3}">
			    						<span class="btn-oval-close" name="planSts" value="3" id="${adCountDetail.adId }"></span>
			    					</c:if>
			    					<c:if test="${adCountDetail.adTFsts == 4}">
			    						<span value="4">投放结束</span>
			    					</c:if>
			    					<c:if test="${adCountDetail.adTFsts == 6}">
			    						<span value="6">废除广告</span>
			    					</c:if>
								</td> 
			    				<td>${adCountDetail.pvNum }</td> 
			    				<td>${adCountDetail.clickNum }</td> 
			    				<td>${adCountDetail.ctrNum }%</td> 
			    				<td>
			    					<c:if test="${adCountDetail.adTFsts != 6 && adCountDetail.adTFsts != 4}">
			    						<a href="javascript:;" name="stopA" value="${adCountDetail.adId }">废除广告</a>
			    					</c:if>
			    				</td>
							</tr> 
						</c:forEach>						
					</tbody> 
				</table>
						</div>
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
			searchPageInfo(n) ;
		}
	});
});
</script>