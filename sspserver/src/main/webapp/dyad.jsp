<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<style>
* {
	margin: 0px;
	padding: 0px;
}
</style>

</head>
<body>


	<c:choose>
	
	   <c:when test="${not empty referrer}">
	   	<iframe
			src="http://ad.adtina.com/ad/${displayUserId}/${displayAdId}?adforcerReferer=${referrer}&ad_acct=${ad_acct}"
			width="${width}px" height="${height}px" border="0" marginwidth="0"
			marginheight="0" frameborder="0" hspace="0" scrolling="no" vspace="0"> </iframe>
	   </c:when>
	   
	   <c:otherwise> 
	   
	   
	   <iframe
			src="http://ad.adtina.com/ad/${displayUserId}/${displayAdId}"
			width="${width}px" height="${height}px" border="0" marginwidth="0"
			marginheight="0" frameborder="0" hspace="0" scrolling="no" vspace="0"> </iframe>
	   	
	   </c:otherwise>
	  
	</c:choose>
	
	<div id="shStatCode" style="display: none;">
	  ${statCode}
	</div>

	
	<input type="hidden" id="impuuid" name="impuuid" value="${impuuid}">
	<input type="hidden" id="pageId" name="pageId" value="${pageId}">
	<input type="hidden" id="referrer" name="referrer" value="${referrer}">
	<input type="hidden" id="adId" name="adId" value="${displayAdId}">

</body>
</html>

<script type="text/javascript">

var impuuid = document.getElementById("impuuid").value;
var pageId = document.getElementById("pageId").value;
var referrer = document.getElementById("referrer").value;
var adId = document.getElementById("adId").value;

function stat() {
	var sxh;
	var isneedesc = "";
	if (window.ActiveXObject) {
		sxh = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		sxh = new XMLHttpRequest();
	}
	var ts = new Date().getTime();
	sxh.open("GET", "/pvneed?impuuid=" + impuuid + "&pageId=" + pageId + "&referrer="+referrer+"&adId="+adId+"&ts=" + ts, false);
	sxh.onreadystatechange = function() {
		if (sxh.readyState == 4 && sxh.status == 200) {
			var statcode = sxh.responseText;
			document.write("<div style='display: none;' id='statdiv' >"+ statcode + "<\div>");
		}
	}
	
	sxh.send("");
}

stat();

</script>
