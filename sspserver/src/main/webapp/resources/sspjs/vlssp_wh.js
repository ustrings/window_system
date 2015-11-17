var vframesh;

var IframeOnClick = {
	resolution : 200,
	iframes : [],
	interval : null,
	Iframe : function() {
		this.element = arguments[0];
		this.cb = arguments[1];
		this.hasTracked = false;
	},
	track : function(element, cb) {
		this.iframes.push(new this.Iframe(element, cb));
		if (!this.interval) {
			var _this = this;
			this.interval = setInterval(function() {
				_this.checkClick();
			}, this.resolution);
		}
	},

	checkClick : function() {
		if (document.activeElement) {
			var activeElement = document.activeElement;
			for ( var i in this.iframes) {
				if (activeElement === this.iframes[i].element) {
					if (this.iframes[i].hasTracked == false) {
						this.iframes[i].cb.apply(window, []);
						this.iframes[i].hasTracked = true;
					}
				} else {
					this.iframes[i].hasTracked = false;
				}
			}
		}
	}
};

var sc = document.getElementsByTagName("script");
var node = sc[sc.length - 1];
node.parentNode.removeChild(node);

// adType=1 表示诏兰精准广告，
if (adType == "1") {
	adShow(w, h, userId, adId, ref);

	recordShow("1", adId, ref);
	// adType= 2 表示非诏兰精准广告
} else {

	if (vizury == "1") {
		// 先去请求vizury
		var ts = new Date().getTime();
		var adurl = "http://rtb-china-1.vizury.com/bidrequest/rtbpassback?publ=Volan&width=300&height=250&id=6358&_="
				+ ts;
		document.write("<script id='vscript' type='text/javascript' src='"
				+ adurl + "'><\/script>");

	} else {
		if (mangtou != "0") {
			// 有盲投广告，直接用盲投广告填充
			adShow(w, h, userId, adId, ref);
			recordShow("1", adId, ref);
		} else {
			recordShow("0", "0", ref);
		}

	}

}

// 如果showType=1则是展示广告了，如果=0则是空打
function recordShow(showType, adId, ref) {
	var timestamp = new Date().getTime();
	var click_img = new Image(1, 1);
	click_img.src = "http://180.96.26.130:8060/showad?showType=" + showType
			+ "&ref=" + ref + "&adId=" + adId + "&impId=" + impId + "&area="
			+ area + "&_=" + timestamp;

}

function adShow(width, height, userId, adId, ref) {

		if (linkType == "J") {
			document.write(extLink);
			document
					.write('<iframe id="jframe"  src="http://ad.adtina.com/ad/'
							+ userId
							+ '/'
							+ adId
							+ '?adforcerReferer='
							+ ref
							+ '&ad_acct='
							+ adAcct
							+ '" width="0px" height="0px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');
	
			var jframe = document.getElementById("jframe");
			setTimeout("jframe.parentNode.removeChild(jframe)", 1000);
			return;
		} 

		if (closeType == '0') {
			document.write('<img  src="http://img.alimama.cn/p/close1.gif" onclick="closeAdDiv();" onmouseover="this.src=\'http://img.alimama.cn/p/close2.gif\'" onmouseout="this.src=\'http://img.alimama.cn/p/close1.gif\'" style="height:13px;font-size:14px;float:right;width:43px;cursor:pointer;position:absolute;top:-16px;right:0">');
		} else if (closeType == '1') {
			document.write('<div id="close" style="POSITION: absolute; Z-INDEX: 9500; border:none; RIGHT: 4px;"> ');
			document.write('<input type="button" value="X" onclick="closeAdDiv();" style="cursor:pointer; background-color: transparent; border:0; font-weight:bold; color:#3C3C3C; font-size:14px; font-family:Arial;"/>');
			document.write('</div>');
		} else if (closeType == '2') {
			document.write('<div id="close" style="POSITION: absolute; Z-INDEX: 9500; border:none; RIGHT: 4px;"> ');
			document.write('<input type="button" value="X" onclick="closeDeadAdDiv();" style="cursor:pointer; background-color: transparent; border:0; font-weight:bold; color:#3C3C3C; font-size:14px; font-family:Arial;"/>');
			document.write('<form style="margin:0px;padding:0px;" id="deadForm" method="get" action="/deadx" target="_blank"><input type="hidden" name="adId" value="'
							+ adId + '" /></form>');
			document.write('</div>');
		} else if (closeType == 'R') {
			var ts = new Date().getTime();
			if (ts % 2 == 0) {
				document.write('<img  src="http://img.alimama.cn/p/close1.gif" onclick="closeAdDiv();" onmouseover="this.src=\'http://img.alimama.cn/p/close2.gif\'" onmouseout="this.src=\'http://img.alimama.cn/p/close1.gif\'" style="height:13px;font-size:14px;float:right;width:43px;cursor:pointer;position:absolute;top:-16px;right:0">');
			} else {
				document.write('<div id="close" style="POSITION: absolute; Z-INDEX: 9500; border:none; RIGHT: 4px;"> ');
				document.write('<input type="button" value="X" onclick="closeAdDiv();" style="cursor:pointer; background-color: transparent; border:0; font-weight:bold; color:#3C3C3C; font-size:14px; font-family:Arial;"/>');
				document.write('</div>');
			}
		}

		if (linkType == 'M') {
			document.write('<iframe  src="http://ad.adtina.com/ad/'
							+ userId
							+ '/'
							+ adId
							+ '?adforcerReferer='
							+ ref
							+ '&ad_acct='
							+ adAcct
							+ '" width="'
							+ width
							+ 'px" height="'
							+ height
							+ 'px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');

		} else if (linkType == 'E') {
			if (extLink != '') {
				document.write('<iframe  id="extframe" src="'
								+ extLink
								+ '" width="'
								+ width
								+ 'px" height="'
								+ height
								+ 'px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');
				document.write('<iframe  id="eframe" src="http://ad.adtina.com/ad/'
								+ userId
								+ '/'
								+ adId
								+ '?adforcerReferer='
								+ ref
								+ '&ad_acct='
								+ adAcct
								+ '" width="0px" height="0px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');

				if (document.getElementById("extframe")) {
					IframeOnClick.track(
									document.getElementById("extframe"),
									function(e) {

										// 通过Image对象请求后端脚本
										var args = getArgs();
										var click_img = new Image(1, 1);
										click_img.src = "http://stat.mdlkt.com:8900/statclick?"
												+ args + "&pos=extframe";
									});
				}

				var eframe = document.getElementById("eframe");
				setTimeout("eframe.parentNode.removeChild(eframe)", 1000);

			}

		} else if (linkType == 'L') {
			document.write(extLink);
			document.write('<iframe id="lframe"  src="http://ad.adtina.com/ad/'
							+ userId
							+ '/'
							+ adId
							+ '?adforcerReferer='
							+ ref
							+ '&ad_acct='
							+ adAcct
							+ '" width="0px" height="0px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');

			var lframe = document.getElementById("lframe");
			setTimeout("lframe.parentNode.removeChild(lframe)", 1000);
		}
}

function adShow4Vizury(width, height, userId, adId, ref, adtag) {


	if (vizuryCloseType == '0') {
		document.write('<img  src="http://img.alimama.cn/p/close1.gif" onclick="closeAdDiv();" onmouseover="this.src=\'http://img.alimama.cn/p/close2.gif\'" onmouseout="this.src=\'http://img.alimama.cn/p/close1.gif\'" style="height:13px;font-size:14px;float:right;width:43px;cursor:pointer;position:absolute;top:-16px;right:0">');
	} else {
		document.write('<div id="close" style="POSITION: absolute; Z-INDEX: 9500; border:none; RIGHT: 4px;"> ');
		document.write('<input type="button" value="X" onclick="closeAdDiv();" style="cursor:pointer; background-color: transparent; border:0; font-weight:bold; color:#3C3C3C; font-size:14px; font-family:Arial;"/>');
		document.write('</div>');
	}

	document.write(adtag);
	document.write('<iframe  id="vyframe" src="http://ad.adtina.com/ad/'
					+ userId
					+ '/'
					+ adId
					+ '?adforcerReferer='
					+ ref
					+ '&ad_acct='
					+ adAcct
					+ '" width="1px" height="1px" border="0"   marginwidth="0" marginheight="0"  frameborder="0" hspace="0" scrolling="no" vspace="0" ></iframe>');

	var vyframe = document.getElementById("vyframe");
	setTimeout("vyframe.parentNode.removeChild(vyframe)", 1000);
	vframesh = setInterval("moninotIframe()", "500");

}



function moninotIframe() {

	if (document.getElementById("addiv")) {
		var vzframes = document.getElementById("addiv").getElementsByTagName(
				'iframe');
		if (vzframes.length == 2) {
			for (var i = 0; i < vzframes.length; i++) {
				var vzframe = vzframes[i];
				IframeOnClick
						.track(
								vzframe,
								function(e) {
									var timestamp = new Date().getTime();
									// 通过Image对象请求后端脚本
									var click_img = new Image(1, 1);

									var click_url = "http://stat.mdlkt.com:8900/statclick?adId=304&materialId=278&"
											+ "channel=&domain=&size=&viewscreen=&userId=&impuuid"
											+ "=&userIdType=&referrer=&ts="
											+ timestamp + "&pos=vzframe";
									click_img.src = click_url;
								});
			}

			clearInterval(vframesh);
		}
	}
}

function getArgs() {
	var params = {};
	params.adId = adId || '';
	params.materialId = '';
	params.channel = '';
	params.userId = '';
	params.impuuid = '';
	params.userIdType = '';
	params.referrer = document.referrer || '';

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

function closeAdDiv() {
	document.getElementById("addiv").innerHTML = '';
	document.getElementById("addiv").style.visibility = 'hidden';
	document.getElementById("addiv").style.display = 'none';
}

function closeDeadAdDiv() {
	document.getElementById('deadForm').submit();
	document.getElementById("addiv").innerHTML = '';
	document.getElementById("addiv").style.visibility = 'hidden';
	document.getElementById("addiv").style.display = 'none';
}

function addisplay(adjson) {
	// 如果vizury判定是他们用户则展现他们的广告
	if (adjson.display == "1") {
		var adtag = adjson.code;
		adShow4Vizury(vizuryWidth, vizuryHeight, vizuryUserid, vizuryAdid, ref,
				adtag);

		recordShow("1", vizuryAdid, ref);
	} else {

		if (mangtou != '0') {
			// 有盲投广告，用盲投广告填充
			adShow(w, h, userId, adId, ref);
			recordShow("1", adId, ref);
		} else {
			recordShow("0", "0", ref);
		}
	}

	var vscript = document.getElementById("vscript");
	setTimeout("vscript.parentNode.removeChild(vscript)",1000);
}
