<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<c:if test="${not empty uoList}">
	<c:forEach var="uo" items="${uoList}">
		<tr>
			<td class="center"><a href="${uo.url }" target="_blank" data-rel="popover"
				data-content="${uo.url }" title="完整url">${fn:substring(uo.url,0,60)}</a>

			</td>
			<td class="center"><a href="#" data-rel="popover"
				data-content="${uo.content }" title="完整内容">${fn:substring(uo.content,0,15)}</a></td>
				
			<td class="center"><input type="checkbox"  value="${uo.url }"
				name="urlCheck"> 选用</td>
		</tr>
	</c:forEach>
</c:if>
<script type="text/javascript">

$(document).ready(function(){
	$("input[name=urlCheck]").each(function(){
		$(this).click(function(){
			
			if($(this).attr("checked")){
				
				var kwVal = $(this).val();
				
				$("#urlFromOceanSel").append("<option value='"+kwVal+"'>"+kwVal+"</option>");
				
				$(this).attr("disabled","disabled");
			}
			
		});
	});
			
});

</script>
<%@include file="../inc/footer.jsp"%>