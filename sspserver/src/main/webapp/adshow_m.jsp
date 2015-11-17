<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,user-scalable=no">
<style>
* {
	margin: 0px;
	padding: 0px;
}
</style>
</head>
<body>

 <iframe id="cn" style="width:100%;height:100%;position:absolute;" frameborder="no" src="<%=request.getParameter("adforcerReferer")%>"></iframe>

</body>
</html>


<%-- 
<script type='text/javascript'
	src="/center?adforcerReferer=<%=request.getParameter("adforcerReferer")%>&advId=<%=request.getParameter("advId")%>&radius=<%=request.getParameter("radius")%>">
</script>
--%>

<script type='text/javascript'>
	var src_url="<%=request.getParameter("adforcerReferer")%>";
	var rus="";
	var divda="";
	var ptey="";
	var area="";
	var ts = new Date().getTime();
	ts = ts/60/60/24;
	document.write('<script type="text/javascript" charset="utf-8" src="'
			+ ('https:' == document.location.protocol ? 'https://' : 'http://')
			+ 'static.sxite.com/static/js/m.splv.js?_='+ts+'"></scr'+'ipt>');
</script>