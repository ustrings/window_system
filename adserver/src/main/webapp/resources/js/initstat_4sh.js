var iscollect = document.getElementById("isCollect").value;
var adId = document.getElementById("adId").value;
var impuuid = document.getElementById("impuuid").value;
var userId = document.getElementById("userId").value;

var jsParam = getJsParam();
var ver = jsParam["ver"];

var isneedesc = isExecStatCode();

if (isneedesc != "-1" && iscollect == "1") {
	document.write("<div style='display: none;' id='statdiv' >"+ isneedesc + "<\div>");
	document.write(unescape("%3Cscript src='http://static.1haofan.com/static/js/stat_4sh.js?ver="+ver+" type='text/javascript'%3E%3C/script%3E"));
	
}

function getJsParam(){
	var sc = document.getElementsByTagName("script");	
	var params = sc[sc.length-1].src.split("?")[1].split("&");
	var args={};
	for(var i=0;i<params.length;i++){
		var param = params[i].split("=");
		var key = param[0];
		var val = param[1];
		if(typeof args[key] == "undefined"){
			args[key] = val;
		}else if(typeof args[key]=="string"){
			args[key]=[args[key]];
			args[key].push(val);
		}else{
			args[key].push(val);
		}
	}
	return args;	
}

function isExecStatCode() {

	//获取页面来源的referer 
	var adforcerReferer = document.getElementById("adforcerReferer").value;
	var referer = "";
    if (null == adforcerReferer || "" == adforcerReferer){
    	//Document对象数据
    	if(document) {
    		referer = document.referrer || ''; 
    	}   
	}
    else{
    	referer = adforcerReferer;
	}
	
	var sxh="";
	var isneedesc = "";
	if (window.ActiveXObject) {
		sxh = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		sxh = new XMLHttpRequest();
	}
	var ts = new Date().getTime();
	//测试环境 判断是否需要执行 第三方 统计 代码
	sxh.open("GET", "/ad/pvneed?impuuid=" + impuuid + "&isHaveNHTStat=&adId=" + adId + "&ts=" + ts + "&referer=" + referer+"&userId="+userId , false);
	sxh.onreadystatechange = function() {
		if (sxh.readyState == 4 && sxh.status == 200) {
			isneedesc = sxh.responseText;
		}
	};
	
	sxh.send("");
	return isneedesc;
}