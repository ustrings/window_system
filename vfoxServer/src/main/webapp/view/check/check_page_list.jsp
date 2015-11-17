<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/view/inc/directive.inc"%>
<link rel="stylesheet" href="/resources/css/kkpager.css" type="text/css" />
<script src="/resources/js/kkpager.min.js" type="text/javascript"></script>
<article class="module width_3_quarter" style="margin-top:5px;">
  <div class="tab_container">
	<div id="tab1" class="tab_content">								 								 							
     <table class="tablesorter" cellspacing="0"> 
	 <thead> 
		<tr> 
		   <th><input type="checkbox" id="select_all"/></th>
		   <th>广告名称</th> 
		   <th>投放周期</th> 
		   <th>每日期望PV</th>
		   <th>投放策略</th>
		   <th>单价</th>
		   <th>审核状态</th>
		   <th>更新时间</th> 
		   <th>操作</th> 
	    </tr>
	 </thead> 
	 <tbody> 
	   <c:forEach items="${pager.result}" var="adCheck">
		 <tr class="tbody_tr">
			<td><input type="checkbox" id="${adCheck.checkProcessId}" name="adchecklist"/></td>
			<c:choose>  
				<c:when test="${adCheck.linkType == 'M' or adCheck.linkType=='E'}">  
				    <td><a target="_blank" href="${adCheck.throwUrl}">${adCheck.adName}</a></td> 
				</c:when> 			 
				<c:otherwise>  
		        	<td><a target="_blank" href="/check/jsShow?adId=${adCheck.adId}">${adCheck.adName}</a></td>
     	        </c:otherwise>    
			</c:choose> 
			<td>${fn:substring(adCheck.startTime,0,10)} ~ ${fn:substring(adCheck.endTime,0,10)}</td>
			<td>${adCheck.dayLimit}</td>
			<td>${adCheck.adStrategy}</td>
			<td>
			    ${adCheck.unitPrice}￥/
			    <c:if test="${adCheck.chargeType =='1'}">CPM</c:if>
			    <c:if test="${adCheck.chargeType =='2'}">CPC</c:if>
			</td>
			<td><c:if test="${adCheck.checkSts=='1'}">待审核</c:if><c:if test="${adCheck.checkSts=='2'}">审核通过</c:if></td> 
			<td>${fn:substringBefore(adCheck.updateDate,".0")}</td>
			<c:if test="${not empty roleId}">
			   <td><input type="image" title="审核" name="check_ad" value="${adCheck.checkProcessId}" src="/resources/images/icn_check.png" ></td> 
		    </c:if>
		 </tr> 
	    </c:forEach>													
	   </tbody> 
     </table>
   </div>
  </div>
</article>
<div style="display: none">
	<input type="text" id="curPage" name="curPage" value="${pager.curPage}"/>
	<input type="text" id="allPage" name="allPage" value="${pager.allPage}"/>
	<input type="text" id="allRecord" name="allRecord" value="${pager.allRecord}"/>
    <input type="text" id="pageRecord" name="pageRecord" value="${pager.pageRecord}"/>
</div>						
<div id="kkpager"></div>
<script type="text/javascript">
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
		total : totalPage,
		totalRecords : totalRecords,
		mode : 'click',
		click : function(n){
			searchCheckPageInfo(n);
		}
	});
});
</script>