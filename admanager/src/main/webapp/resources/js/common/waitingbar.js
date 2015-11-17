
function WaitingBarSysConfig() {
	this.isActive = true;
}

var WaitingBarConfig = new WaitingBarSysConfig();

var WaitingBar = {
	show : /*public*/ function(){
		if(!WaitingBarConfig.isActive){
			return;
		}
		
		var width = 150;
		var height = 30;
		var commonMaskIndexz = 100;
		var waitingbarLoadingDiv = document.getElementById("waitingbarLoadingDiv");
		if(!waitingbarLoadingDiv){
			_scrollWidth = Math.max(document.body.scrollWidth, document.documentElement.scrollWidth);
	    	
			_scrollHeight =Math.max(document.body.scrollHeight, document.documentElement.scrollHeight)-5;
			
			var commonMaskDiv = document.createElement("div");
			commonMaskDiv.setAttribute('id', "waitingbarLoadingDiv");
			commonMaskDiv.style.position = "absolute";
			commonMaskDiv.style.top = "0";
			commonMaskDiv.style.left = "0";
			commonMaskDiv.style.background = "#333";
			commonMaskDiv.style.filter = "Alpha(opacity=20);";
			commonMaskDiv.style.opacity = "0.2";
			commonMaskDiv.style.width = _scrollWidth + "px";
			if(_scrollHeight < 0){
			
				_scrollHeight = 500;
			}
			commonMaskDiv.style.height = _scrollHeight + "px";
			commonMaskDiv.style.zIndex = "10000";
			document.body.appendChild(commonMaskDiv);
			
			var v_left = (document.body.clientWidth - width)/2; 
			var v_top = (document.body.clientHeight - height)/3;
			
			commonMaskIndexz = commonMaskIndexz + 10;
			
	        var componentDiv = document.createElement("div");
	        componentDiv.setAttribute('id', "waitingbarLoadingDivContent");
	        componentDiv.style.position = "absolute";
	        componentDiv.style.background = "#fff";
	        componentDiv.style.border = "1px #9C9C9C solid";
	        componentDiv.style.top ="40%";
			componentDiv.style.left = "47%";
			componentDiv.style.width = "60px";
			componentDiv.style.textAlign="center";
			componentDiv.style.zIndex = "10100";
			componentDiv.innerHTML = "<div style='text-align:center;margin-top:0px;'><img src='/resources/js/common/images/basic/loadingPic.gif' width='32' height='32' border='0' /><br />加载中...</div>";
			
		    window.scrollTo(0,0);  //滚动条  至顶
			document.body.appendChild(componentDiv);	
		
		}
	},
	
	hide : /*public*/ function(){
		if(!WaitingBarConfig.isActive){
			return;
		}
		
		$("#waitingbarLoadingDiv").remove();
		$("#waitingbarLoadingDivContent").remove();
	},
	
	remove : /*public*/ function(){
		
	}
}