<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<link rel="stylesheet" href="/resources/css/kkpager.css" type="text/css" />
<script src="/resources/js/kkpager.min.js" type="text/javascript"></script>
<article class="module width_3_quarter">
  <div class="tab_container">
	<div id="tab1" class="tab_content">					
     <table class="tablesorter" cellspacing="0"> 
	   <thead> 
		 <tr> 
    	    <th>广告名称</th> 
    		<th>投放周期</th> 
    		<th>每日期望PV</th>
		    <th>投放策略</th>
		    <th>单价</th>  
    		<th>审核状态</th> 
    		<th>创建时间</th>
    		<th>操作</th> 
		 </tr> 
	   </thead> 
	  <tbody> 
	  <c:forEach items="${pager.result}" var="adCheckHistory">
		<tr class="tbody_tr"> 
    		<c:choose>  
				<c:when test="${adCheckHistory.linkType == 'M' or adCheckHistory.linkType=='E'}">  
				    <td><a target="_blank" href="${adCheckHistory.adUrl}">${adCheckHistory.adName}</a></td> 
				</c:when> 			 
				<c:otherwise>  
		        	<td><a target="_blank" href="/checkProgress/jsShow?adId=${adCheckHistory.adId}">${adCheckHistory.adName}</a></td>
     	        </c:otherwise>    
			</c:choose> 
			<td>${fn:substring(adCheckHistory.startTime,0,10)} ~ ${fn:substring(adCheckHistory.endTime,0,10)}</td>
			<td>${adCheckHistory.dayLimit}</td>
			<td>${adCheckHistory.adStrategy}</td>
			<td>
			    ${adCheckHistory.unitPrice}￥/
			    <c:if test="${adCheckHistory.chargeType =='1'}">CPM</c:if>
			    <c:if test="${adCheckHistory.chargeType =='2'}">CPC</c:if>
			</td>     				
    		<td>
    		<c:if test="${adCheckHistory.adToufangSts =='0'}">待审核</c:if>
    		<c:if test="${adCheckHistory.adToufangSts =='2' or adCheckHistory.adToufangSts =='3' or adCheckHistory.adToufangSts =='4'}">审核成功</c:if>
    		<c:if test="${adCheckHistory.adToufangSts =='5'}">审核失败</c:if>
    		</td> 
    		<td>${fn:substring(adCheckHistory.createTime,0,10)}</td>
    		<td><input type="image" title="浏览轨迹查看" name="checkhistory" id="${adCheckHistory.adId}" src="/resources/images/icn_trace.png" /></td>
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
	var pageNo = $('#curPage').val();cd
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
			searchCheckHistoryPageInfo(n);
		}
	});
});
</script>