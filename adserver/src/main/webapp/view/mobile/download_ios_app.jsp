<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
       <meta http-equiv="Content-Type" content="text/html; charset= tf-8" /> 
       <meta name="viewport" content=" ser-scalable=no, width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/> 
       <script type="text/javascript" src="http://cdn.tanx.com/t/tanxmobile/mraid.js"></script>
<style type="text/css">
html,body{
margin:0;
padding:0;
}
.logo_tag {
	position: absolute;
	top: 0px;
	left: ${logo_tag_left}px;
	z-index:999;
	width:29px;
    height:20px;
	background:url(http://static.vaolan.com/static/image/logo_tag.png);
	_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='http://static.vaolan.com/static/image/logo_tag.png');
	_background:none;
}
.logo_name {
	position: absolute;
	top: 0px;
	left: ${logo_name_left}px;
	z-index:999;
	display:none;
	width:86px;
	height:20px;
	background:url(http://static.vaolan.com/static/image/logo_name.png);
	_filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='http://static.vaolan.com/static/image/logo_name.png');
	_background:none;
}
</style>
</head>
<body>
	<img id="demo" style="width:100%;height:100%" alt="" src="">
	<script>
		if ((mraid.getState() == "loading")) {
    		mraid.addEventListener('ready', loadAndDisplayAd);
		} else {
    		loadAndDisplayAd();
		}
		function loadAndDisplayAd(){
			var img = document.getElementById("demo");
			img.onload = function(){
				document.body.appendChild(img);
				mraid.show();//在图片加载完成后，用户看到的不再是天窗，这时候调用mraid.show()，表示创意渲染完毕。可进行展示
			}
			img.src ="${material.link_url}";
			img.onclick = function(){
				var property={
						"url":"${material.target}",
						"type" : "default"
				}
				mraid.download(property);
				return false;
			}
		}
	</script>	
	<a class="logo_tag" id="ltag" onmouseover="logoTagOver();"></a> 
	<a class="logo_name" id="lname" onmouseout="logoNameOut();" href="http://www.vaolan.com" target="_blank"></a>

	<input type="hidden" id='channel' name="channel" value="${channel}" />
	<input type="hidden" id='materialId' name="materialId" value="${material.ad_m_id}" />
	<input type="hidden" id='adId' name='adId' value="${adId}" />
	<input type="hidden" id='pv_collect_url' name='pv_collect_url' value="${pv_collect_url}" />
	<input type="hidden" id='click_collect_url' name='click_collect_url' value="${click_collect_url}" />
	
</body>
</html>
<script  type="text/javascript">
	function logoTagOver() {
		document.getElementById("ltag").style.display = "none";
		document.getElementById("lname").style.display = "block";
	}

	function logoNameOut() {
		document.getElementById("ltag").style.display = "block";
		document.getElementById("lname").style.display = "none";
	}
</script>
<script type="text/javascript" src="http://static.vaolan.com/static/js/vlstat_4adm.js?ver=${ver}"></script>