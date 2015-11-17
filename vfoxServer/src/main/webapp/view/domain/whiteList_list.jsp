<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<link rel="stylesheet" type="text/css"  href="/resources/css/kkpager.css" />
<script type="text/javascript" src="/resources/js/kkpager.min.js"></script>
<script src="/resources/js/common.js" type="text/javascript"></script>
				<article class="module width_3_quarter">
					<div class="tab_container">
						<div id="tab1" class="tab_content">
							<table class="tablesorter" cellspacing="0"> 
				<thead> 
					<tr> 
						<th><input type="checkbox" id="checkedAll"/></th>
						<th>序号</th>
						<th>域名名称</th> 
						<th>域名URL</th> 
						<th>更新时间</th>
						<th>操作</th> 
					</tr> 
				</thead> 
				<tbody> 
					<c:if test="${not empty pager.result }">
						<c:forEach items="${pager.result }" var="whiteDomain" varStatus="status" >
							<tr class="tbody_tr"> 
								<td><input type="checkbox" value="${whiteDomain.id }" name="checkBox"/></td> 
								<td>${(pager.curPage - 1)*10 + 1 + status.index }</td>
								<td>${whiteDomain.domainName }</td> 
								<td><a href="${whiteDomain.domainUrl }" target="_blank">${whiteDomain.domainUrl }</a></td>
								<td>${fn:substringBefore(whiteDomain.stsDate,".0") }</td> 
								<td><input type="image" id="${whiteDomain.id  }" name="updateWhiteDomain" src="/resources/images/icn_edit.png" title="编辑"><input type="image" id="${whiteDomain.id }" name="deleteWhiteDomain" src="/resources/images/icn_trash.png" title="删除"></td> 
							</tr> 
						</c:forEach>
					</c:if>
				</tbody> 
				</table>
							<!-- 表格内容 -->
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