<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
</head>
<script>
function bodyInit(){
	var result = document.getElementById("uploadresult").value;
	parent.uploadCallBack(result);
	
}
</script>
<body onload="bodyInit()">
	<input id="uploadresult" value="${result}"/>
</body>
</html>
