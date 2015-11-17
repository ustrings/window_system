function getArgs() {
	var params = {};
	params.adId = adId||'';
	params.materialId = '';
	params.channel = '';
	params.domain = '';
	params.size = '';
	params.viewscreen = '';
	params.userId = '';
	params.impuuid = '';
	params.userIdType = '';
	params.referrer = document.referrer || '';


	// navigator对象数据
	if (navigator) {
		// params.lang = navigator.language || '';
	}
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



function getPosition(ev) {
	ev = ev || window.event;
	var point = {
		x : 0,
		y : 0
	};
	if (ev.pageX || ev.pageY) {
		point.x = ev.pageX;
		point.y = ev.pageY;
	} else {// 兼容ie
		point.x = ev.clientX + document.body.scrollLeft
				- document.body.clientLeft;
		point.y = ev.clientY + document.documentElement.scrollTop;
	}
	return point;
}



function addEvent(o, type, fn) {
	o.attachEvent ? o.attachEvent('on' + type, fn) : o.addEventListener(type,
			fn, false);
}

function closeAdDiv() {
	document.getElementById("addiv").innerHTML = '';
	document.getElementById("addiv").style.visibility = 'hidden';
	document.getElementById("addiv").style.display = 'none';
}

function adShow() {

	document
			.write('<div id="addiv" style="position: fixed; Z-INDEX: 9200; left: 0px; bottom: 0px;width: 100%;height: auto">');

	document.write('<img  src="http://static.sxite.com/static/image/banner_close.png" onclick="closeAdDiv();"  style="height:auto;float:right;width:5%;cursor:pointer;position:absolute;top:0;right:0">');

	document
			.write('<a href="http://item.taobao.com/item.htm?id=43062432065&spm=a310v.4.88.1" target="_top" >');
	document
			.write('<img src="http://creative.sxite.com/img/a5113a65c12441f89e647f45bb53712c.jpg" id="responseUrl" alt="" border=0 style="width:100%;height:auto" />');
	document.write('</a>');

	document.write('</div>');

	var args = getArgs();
	var pv_img = new Image(1, 1);
	pv_img.src = "http://statistic.mdlkt.com/statpv?" + args;
	
	if (document.getElementById("vframe")) {
		IframeOnClick.track(document.getElementById("vframe"), function(e) {

			// 通过Image对象请求后端脚本
			var args = getArgs();
			var click_img = new Image(1, 1);
			click_img.src = click_collect_url + args + "&pos=vframe";
		});
	}

	addEvent(document.getElementById("responseUrl"), "click", function(e) {
			var point = getPosition(e);
			var pos = point.x + "," + point.y;
			var args = getArgs();

			// 通过Image对象请求后端脚本
			var click_img = new Image(1, 1);
			click_img.src = "http://statistic.mdlkt.com/statclick?" + args + "&pos=" + pos;
	});

}
//tiantian
var adId="987";
adShow();
