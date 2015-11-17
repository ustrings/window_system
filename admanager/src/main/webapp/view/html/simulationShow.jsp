<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,user-scalable=no">
<style>
* {
	margin: 0px;
	padding: 0px;
}
</style>
</head>
<body>

	<iframe id="cn" style="width: 100%; height: 100%; position: absolute;"
		frameborder="no" src="${pageUrl} "></iframe>


	<c:if test="${urlOrJs =='URL' }">
		<div id="addiv"
			style="position: fixed; Z-INDEX: 9200; right: 0px; bottom: 0px;width:${w}px;height: ${h }px">

			<iframe src="${url }" width="${w }px" height="${h }px" border="0"
				marginwidth="0" marginheight="0" frameborder="0" hspace="0"
				scrolling="no" vspace="0"></iframe>
			</div>
	</c:if>
	
	
	<c:if test="${urlOrJs =='JS' }">
		<c:if test="${jsLocation =='J' }">
			${jsCode }
		</c:if>
		
		<c:if test="${jsLocation =='L' }">
			<div id="addiv"
			style="position: fixed; Z-INDEX: 9200; right: 0px; bottom: 0px;width:${w}px;height:${h }px">

			${jsCode }
			</div>
		</c:if>
		
		
	</c:if>


</body>
</html>

</script>