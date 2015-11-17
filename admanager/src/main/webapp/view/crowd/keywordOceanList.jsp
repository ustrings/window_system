<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<c:if test="${not empty kwoList}">
	<c:forEach var="kw" items="${kwoList}">
		<tr>
			<td class="center"><a href="#" class="btn btn-primary">${kw.keyword }</a></td>
			<td class="center"><input type="checkbox" 
				name="kwCheck" value="${kw.keyword}"> 选用</td>
		</tr>
	</c:forEach>
</c:if>
<script type="text/javascript">

$(document).ready(function(){
	$("input[name=kwCheck]").each(function(){
		$(this).click(function(){
			
			if($(this).attr("checked")){
				
				var kwVal = $(this).val();
				
				$("#searchKwFromOceanSel").append("<option value='"+kwVal+"'>"+kwVal+"</option>");
				
				$(this).attr("disabled","disabled");
			}
			
		});
	});
			
});

</script>
<%@include file="../inc/footer.jsp"%>
