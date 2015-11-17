<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
        <meta charset="utf-8">
        <title>adshow</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
* {
	margin: 0px;
	padding: 0px;
}
</style>

</head>
<body scrolling="no">




	<iframe id="frm" src="<%=request.getParameter("adforcerReferer") %>" width="100%" height="100%"
		frameborder="0" style="position: fixed; z-index: -1;"></iframe>
		
		

</body>
</html>

<div id="addiv" style="position: fixed; Z-INDEX: 9200; right: 0px; bottom: 0px;width: 400px;height: 300px">
<div id="close" style="POSITION: absolute; Z-INDEX: 9500; border:none; RIGHT: 4px;">
<input type="button" value="X" onclick="closeAdDiv();" style="cursor:pointer; background-color: transparent; border:0; font-weight:bold; color:#3C3C3C; font-size:14px;
font-family:Arial;"/>
</div>
<iframe  src="http://ad.adtina.com/ad/9/<%=request.getParameter("adId") %>" width="400px" height="300px" border="0"   marginwidth="0" marginheight="0"  frameborder="0"
hspace="0" scrolling="no" vspace="0" />
</div>
