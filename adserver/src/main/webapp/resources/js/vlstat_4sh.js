/* vaolan stat for ad
 */
(function() {
	
	var adId = document.getElementById("adId").value;
	var materialId = document.getElementById("materialId").value;
	var pv_collect_url = document.getElementById("pv_collect_url").value;
	var click_collect_url = document.getElementById("click_collect_url").value;
	//var channel = document.getElementById("channel").value;
	//var domain = document.getElementById("domain").value;
	//var size = document.getElementById("size").value;
	//var viewscreen = document.getElementById("viewscreen").value;
	var userId = document.getElementById("userId").value;
	//var userIdType = document.getElementById("userIdType").value;
	var adforcerReferer = document.getElementById("adforcerReferer").value;
	var impuuid = document.getElementById("impuuid").value;


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
	//当物料无iframe时，点击请求 
	if (document.getElementById("vframe")) {
		IframeOnClick.track(document.getElementById("vframe"), function(e) {

			// 通过Image对象请求后端脚本
			var args = getArgs();
			var click_img = new Image(1, 1);
			click_img.src = click_collect_url + args + "&pos=vframe";
		});
	}
	
	//点击请求（物料为图片的）
	addEvent(document.getElementById("responseUrl"), "click", function(e) {
			var point = getPosition(e);
			var pos = point.x + "," + point.y;
			var args = getArgs();
			// 通过Image对象请求后端脚本
			var click_img = new Image(1, 1);
			click_img.src = click_collect_url + args + "&pos=" + pos;
	});

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

	function getArgs() {
		var params = {};
		params.adId = adId || '';
		params.materialId = materialId || '';
		//params.channel = channel || '';
		//params.domain = domain || '';
		//params.size = size || '';
		//params.viewscreen = viewscreen || '';
		params.userId = userId || '';
		params.impuuid = impuuid || '';
		//params.userIdType = userIdType || '';
		
		if (null == adforcerReferer || "" == adforcerReferer) {
			// Document对象数据
			if (document) {
				params.referrer = document.referrer || '';
			}
		} else {
			params.referrer = adforcerReferer;
		}

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

	var args = getArgs();
	// 通过Image对象请求后端脚本
	var pv_img = new Image(1, 1);
	pv_img.src = pv_collect_url + args;

	function addEvent(o, type, fn) {
		o.attachEvent ? o.attachEvent('on' + type, fn) : o.addEventListener(
				type, fn, false);
	}
})();
