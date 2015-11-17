<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/view/inc/directive.inc"%>
<link rel="stylesheet" href="/resources/css/kkpager.css" type="text/css" />
<script src="/resources/js/kkpager.min.js" type="text/javascript"></script>
<article class="module width_3_quarter">
 <div class="tab_container">
  <div id="tab1" class="tab_content">							
   <table class="tablesorter" cellspacing="0"> 
	   <thead> 
		 <tr> 
		  <th><input type="checkbox" id="select_all"/></th> 
    	  <th>角色名称</th> 
    	  <th>用户姓名</th> 
    	  <th>所属部门</th> 
    	  <th>绑定账号</th> 
    	  <th>手机号码</th> 
    	  <th>Email</th> 
    	  <th>更新时间</th> 
    	  <th>操作</th> 
		 </tr> 
		 </thead> 
		 <tbody> 
		   <c:forEach items="${pager.result}" var="rolePage">
			  <tr class="tbody_tr"> 
   				<td><input type="checkbox" name="roleList"/></td> 
    			<td>${rolePage.checkRoleName }</td> 
    			<td>${rolePage.userName}</td> 
    			<td>${rolePage.deptName}</td> 
    			<td>${rolePage.userAcctRel}</td> 
    			<td>${rolePage.telNbr}</td> 
    			<td>${rolePage.email}</td> 
    			<td>${fn:substringBefore(rolePage.stsDate,".0")}</td> 
    			<td>	
    			  <c:if test="${sessionScope.user.userName == 'nj_admin'}">		   
    			    <input type="image" src="/resources/images/icn_edit.png" title="编辑" onclick="editRole('${rolePage.id}')">
    			    <input type="image" src="/resources/images/icn_trash.png" title="删除" onclick="deleteRole('${rolePage.id}')">
    			  </c:if>
    			</td> 
			  </tr>  
		   </c:forEach>
	    </tbody> 
    </table>
   </div>
  </div>			
</article>
<div style="display: none;">
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
			searchRolePageInfo(n);
		}
	});
});
</script>