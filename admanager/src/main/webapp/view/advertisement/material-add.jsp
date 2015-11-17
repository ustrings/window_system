<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!DOCTYPE html>
<head>
<meta charset="utf-8">
	<title>广告物料</title>
<!-- The styles -->
<link id="bs-css" href="/resources/css/bootstrap-cerulean.css" rel="stylesheet">
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
.uploadify-queue{display:none}
</style>
<link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
<link href="/resources/css/charisma-app.css" rel="stylesheet">
<link href="/resources/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
<link href='/resources/css/fullcalendar.css' rel='stylesheet'>
<link href='/resources/css/fullcalendar.print.css' rel='stylesheet' media='print'>
<link href='/resources/css/chosen.css' rel='stylesheet'>
<link href='/resources/css/uniform.default.css' rel='stylesheet'>
<link href='/resources/css/colorbox.css' rel='stylesheet'>
<link href='/resources/css/jquery.cleditor.css' rel='stylesheet'>
<link href='/resources/css/jquery.noty.css' rel='stylesheet'>
<link href='/resources/css/noty_theme_default.css' rel='stylesheet'>
<link href='/resources/css/elfinder.min.css' rel='stylesheet'>
<link href='/resources/css/elfinder.theme.css' rel='stylesheet'>
<link href='/resources/css/jquery.iphone.toggle.css' rel='stylesheet'>
<link href='/resources/css/opa-icons.css' rel='stylesheet'>
<link href='/resources/css/uploadify.css' rel='stylesheet'>

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon" href="/resources/img/favicon.ico">

</head>

<body>
	<%@include file="../inc/header.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<%@include file="../inc/menu.jsp"%>
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.
					</p>
				</div>
			</noscript>
			<div id="content" class="span10">
				<!-- content starts -->
				<div>
					<ul class="breadcrumb">
						<li><a href="/">首页</a> <span class="divider">/</span></li>
						<li><a href="#">广告物料</a></li>
					</ul>
				</div>
				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-edit"></i> 新建广告物料
							</h2>
						</div>
						<div class="box-content">
							<input type="hidden" value="${jsession}" id="jsession"/>
							<form class="form-horizontal" method="post" action="/material/add">
								<input type="hidden" name="usefulType" value="N">
								<fieldset>
									<div class="control-group" id="namePos">
										<label class="control-label" for="materialName"><b style="color: #FF0000;">*</b>广告物料名称：</label>
										<div class="controls">
											<input type="text" class="input-xlarge focused" id="materialName" name="materialName" maxlength="50" onfocus="checkMaterialName"> <span id="nameTips" class="help-inline" style="display:none"></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" for="mType">类型：</label>
										<div class="controls">
											<label class="radio"> <input type="radio" name="mType" id="mType1" value="1" checked />图片
											</label>
											<label class="radio" style="padding-top: 5px;">
												<input type="radio" name="mType" id="mType2" value="2" />Flash
											</label> 
											<label class="radio" style="padding-top: 5px;"> 
												<input type="radio" name="mType" id="mType3" value="3" />富媒体
											</label>
										</div>
									</div>
									<!--  <div id="pic_flash_div"> -->
										 <div class="control-group">
											<label class="control-label" for="selectError">规格：</label>
											<div class="controls">
									<select id="pixel" data-rel="chosen" name="materialSize">
										<c:if test="${not empty meterSizeList}">
											<c:forEach var="obj" items="${meterSizeList}" varStatus="status">
												<option value="${obj.attrCode}">
															${obj.attrValue}	
												</option>
												
											</c:forEach>
										</c:if>
									</select>
											  <!--加入隐藏域  周晓明  -->
											  <input type="hidden" id="materialValue" name="materialValue" vaule="" />
											</div>
										 </div>
									
									<div id="pic_flash_div">
										<div class="control-group" id="fileType">
											<label class="control-label" for="materialType" id="pictuerPos"><b style="color: #FF0000;">*</b>图片文件：</label>
											<div class="controls">
												<label class="radio"> 
													<input type="radio" name="materialType" id="materialType1" value="1" checked />上传
												</label>
												<label class="radio" style="padding-top: 5px;">
													<input type="radio" name="materialType" id="materialType2" value="2" />远程
												</label>
											</div>
										</div>

										<div class="control-group" id="upload">
											<label class="control-label"></label>
											<div class="controls">
												<input data-no-uniform="true" type="file" name="file_upload" id="file_upload" /> <span id="file_uploadTips" class="help-inline" style="display:none"></span>
											</div>
											
											<div class="controls">
												<div id="uploadImg" style="dipslay:none"><img src=""/></div>
											</div>
										</div>
										
										<div class="control-group" id="uploadFlash" style="display:none">
											<label class="control-label"></label>
											<div class="controls">
												<input data-no-uniform="true" type="file" name="flash_upload" id="flash_upload" /> <span id="flash_uploadTips" class="help-inline" style="display:none"></span>
											</div>
											
											<div class="controls">
												<div>
													<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0">
														<param name="movie" value="0.swf">
														<param name="quality" value="high">
														<embed src="" id="flash" style="display:none" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"/>
													</object>
												</div>
											</div>
										</div>

										<div class="control-group" id="remotePath" style="display: none">
											<label class="control-label" id="picturePos"><b style="color: #FF0000;">*</b>图片地址：</label>
											<div class="controls">
												<input type="text" class="input-xlarge focused" id="filePath" value="http://"> <span id="filePathTips" class="help-inline" style="display:none"></span>
											</div>
										</div>

										<div class="control-group">
											<label class="control-label" for="materialName">描述：</label>
											<div class="controls">
												<input type="hidden" id="linkUrl" name="linkUrl"/>
												<input type="text" class="input-xlarge focused" id="materialDesc" name="materialDesc" maxlength="255"> <span id="materialDescTips" class="help-inline" style="display:none"></span>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" for="materialName" id="targetUrlPos"><b style="color: #FF0000;">*</b>点击跳转链接：</label>
											<div class="controls">
												<input type="text" class="input-xlarge focused" name="targetUrl" id="targetUrl"  maxlength="255" value="http://"> <span id="targetUrlTips" class="help-inline" style="display:none"></span>
											</div>
										</div>
									</div>

									<div class="control-group" style="display: none" id="richText">
										<label class="control-label" for="richText" id="textPos">富文本：</label>
										<div class="controls">
											<script id="editor" type="text/plain"></script>
											<input type="hidden" name="richText" id="textContent"/>
											<span id="richTextTips" class="help-inline" style="display:none"></span>
										</div>
									</div>
									
									<div class="control-group">
											<label class="control-label" for="monitor" style="margin-left:9%;">
												<input type="checkbox" id="monitor" name="thirdMonitor" value="1">设置第三方监控</label>
											<div class="controls"></div>
									</div>
									<div class="control-group" style="display: none" id="monitorDiv">
										<label class="control-label" for="monitorlink" id="monitorlinkPos"><b style="color: #FF0000;">*</b>监控链接：</label>
										<div class="controls">
											<input type="text" class="input-xlarge focused" id="monitorlink" name="monitorLink"  maxlength="255" value="http://"> <span id="monitorlinkTips" class="help-inline" style="display:none"></span>
										</div>
									</div>
									
									
									<div class="control-group">
											<label class="control-label" for="coverFlagCB" style="margin-left:9%;">
												<input type="checkbox" id="coverFlagCB" name="coverFlagCB" >启用flash原生嵌入的连接</label>
												<input type="hidden" id="coverFlagValue" name="coverFlag" value="0"/>
											<div class="controls"></div>
									</div>
									
									
									
									
									<div class="form-actions">
										<button type="button" class="btn btn-primary">保存</button>
										<button type="button" class="btn" id="back">返回</button>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
					<!--/span-->

				</div>
				<!--/row-->
				<!-- content ends -->
			</div>
			<!--/#content.span10-->
		</div>
		<!--/fluid-row-->
		<hr>
	</div>
	<!--/.fluid-container-->
	<%@include file="../inc/footer.jsp"%>
	<script type="text/javascript" charset="utf-8" src="/resources/ueditor/editor_config.js"></script>
	<script type="text/javascript" charset="utf-8" src="/resources/ueditor/editor_all.js"></script>
</body>


<script>
//赋值  (给图片规格赋值 )
 function ValuationmaterialValue(){
	  
	    var standardSize = $('#pixel').find("option:selected").text().trim();
		$("#materialValue").val(standardSize);
}

</script>
<script>
//聚焦错误位置 函数
	function moveHtml(id){
		var scroll_offset = $("#" + id).offset().top;
		$("body,html").animate({
			scrollTop : scroll_offset 
		//让body的scrollTop等于pos的top，就实现了滚动
		}, 500);
	}
</script>
<script>

<!-- 失去焦点事件：判断文本框内容不可以为空的函数。     周晓明-->
$(document).ready(function(){
	//检查广告名称 
	$("#materialName").focusout (function(){
		var $materialName = $("#materialName").val();
		if ($.trim($materialName) == ''){
			$("#nameTips").text("广告物料名称不能为空");
			$("#nameTips").parent().parent().addClass("error");
			$("#nameTips").show();
		} 
	});
	
	//检查描述(当类型选择的为图片和Flash时)
	/* var $mType = $("input[name='mType']:checked").val();
    $("#materialDesc").focusout(function(){
    	var $materialDesc = $("#materialDesc").val();
    	 if ($mType != 3 && $.trim($materialDesc) == ''){
    			$("#materialDescTips").text("描述不能为空");
    			$("#materialDescTips").parent().parent().addClass("error");
    			$("#materialDescTips").show();
    	 }
    	 
    }); */
	//检查富文本(当类型选择的为富媒体时)
     $(".controls").focusout(function(){
    	var $richText = UE.getEditor('editor').getContent();
    	if($mType == 3 && $.trim($richText) == ''){
   		 $("#richTextTips").text("富文本内容不能为空");
   		 $("#richTextTips").parent().parent().addClass("error");
   		 $("#richTextTips").show();
   	 }
    }); 
    var $mType = $("input[name='mType']:checked").val();
    
    //var regex_ = /^http:\/\/([\w-]+\.)+[\w-]+(\/[\w-.\/?%&=]*)?$/;
    var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
        + "|" // 允许IP和DOMAIN（域名）
        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
        + "[a-z]{2,6})" // first level domain- .com or .museum
        + "(:[0-9]{1,4})?" // 端口- :80
        + "((/?)|" // a slash isn't required if there is no file name
        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        var regex_ =new RegExp(strRegex);
        
        
	//检查点击跳转链接(当类型选择的为图片和Flash时)
	$("#targetUrl").focusout(function(){
		var $targetUrl = $("#targetUrl").val();
		if ($mType != 3 && $.trim($targetUrl) == ''){
			$("#targetUrlTips").text("点击跳转链接不能为空");
			$("#targetUrlTips").parent().parent().addClass("error");
			$("#targetUrlTips").show();
		}
		//点击跳转url不在校验格式
		/**else if ($mType != 3 && !regex_.test($targetUrl)){
			$("#targetUrlTips").text("点击跳转链接格式错误");
			$("#targetUrlTips").parent().parent().addClass("error");
			$("#targetUrlTips").show();
		} **/
	});
    //检查监控连接(监控连接)
    $("#monitorlink").focusout(function(){
    	var $monitor = $("#monitor").attr("checked");
    	var $monitorlink = $("#monitorlink").val();
    	if($monitor == 'checked' && $.trim($monitorlink) == ''){
    		$("#monitorlinkTips").text("监控链接不能为空");
    		$("#monitorlinkTips").parent().parent().addClass("error");
    		$("#monitorlinkTips").show();
    	}else if ($monitor == 'checked' && !regex_.test($monitorlink)){
    		$("#monitorlinkTips").text("监控链接格式错误");
    		$("#monitorlinkTips").parent().parent().addClass("error");
    		$("#monitorlinkTips").show();
    	}
    });
    //图片 AND Falsh 地址检查 
    $("#filePath").focusout(function(){
    	var $filePath = $("#filePath").val();
    	var $materialTypeVal = $("input[name='materialType']:checked").val();
    	var $mType = $("input[name='mType']:checked").val();
    	if($mType == '1' && $materialTypeVal == '2' && $.trim($filePath) == ''){
    		$("#filePathTips").text("图片地址不能为空");
    		$("#filePathTips").parent().parent().addClass("error");
    		$("#filePathTips").show();
    	}else if($mType == '1' && $materialTypeVal == '2' && !regex_.test($filePath)){
    		$("#filePathTips").text("图片地址格式错误");
    		$("#filePathTips").parent().parent().addClass("error");
    		$("#filePathTips").show();
    	}else if($mType == '2' && $materialTypeVal == '2' && $.trim($filePath) == ''){
    		$("#filePathTips").text("Falsh地址不能为空");
    		$("#filePathTips").parent().parent().addClass("error");
    		$("#filePathTips").show();
    	}else if($mType == '2' && $materialTypeVal == '2' && !regex_.test($filePath)){
    		$("#filePathTips").text("Falsh地址格式错误");
    		$("#filePathTips").parent().parent().addClass("error");
    		$("#filePathTips").show();
    	}
    	
    });
});


	
$(document).on("click","input[name='materialType']",function(){
	var val = $(this).val();
	var mTypeVal = $("input[name='mType']:checked").val();
	
	if (val == '1'){
		$("#remotePath").hide();
		$("#upload").show();
		$("#uploadFlash").show();
	} else if (val == '2'){
		$("#remotePath").show();
		$("#upload").hide();
		$("#uploadFlash").hide();
	}
	
	if (mTypeVal == '1'){
		$("#remotePath .control-label").text("图片地址：");
		$("#uploadFlash").hide();
	} else if (mTypeVal == '2'){
		$("#remotePath .control-label").text("Flash地址：");
		$("#upload").hide();
	}
	
}).on("click","input[name='mType']",function(){
	var val = $(this).val();
	if (val == '1'){
		$("#pic_flash_div").show();
		$("#richText").hide();
		$("#upload").show();
		$("#uploadFlash").hide();
		$("#fileType .control-label").text("图片文件：");
		$("#remotePath .control-label").text("图片地址：");
	} else if (val == '2'){
		$("#pic_flash_div").show();
		$("#richText").hide();
		$("#upload").hide();
		$("#uploadFlash").show();
		$("#fileType .control-label").text("Flash文件：");
		$("#remotePath .control-label").text("Flash地址：");
	} else if (val == '3'){
		$("#pic_flash_div").hide();
		$("#richText").show();
	}
	
	var mTypeVal = $("input[name='materialType']:checked").val();
	if (mTypeVal == '2'){
		$("#uploadFlash").hide();
		$("#upload").hide();
	}
	
}).on("click","#coverFlagCB",function(){
	var _$this = $(this);
	if (_$this.parent().attr("class") == 'checked'){
		$("#coverFlagValue").attr("value",'1');;
	} else{
		$("#coverFlagValue").attr("value",'0');;
	}
}).on("click","#monitor",function(){
	var _$this = $(this);
	if (_$this.parent().attr("class") == 'checked'){
		$("#monitorDiv").show();
	} else{
		$("#monitorDiv").hide();
	}
}).ready(function(){
    UE.getEditor('editor',{
        initialFrameWidth : 500,
        initialFrameHeight: 300,
        imageUrl:"/uploadFile;jsessionid="+$("#jsession").val(), 
        imagePath:"",
        lang:'zh-cn',
        initialContent:''
    });
  	//实例化编辑器
  	var ue = UE.getEditor('editor');
	
    ue.addListener('ready',function(){
        this.focus();
    });
    
    ue.addListener('click',function(){
    	$("#richTextTips").parent().parent().removeClass("error");
		$("#richTextTips").hide();
    });
    
	// 图片上传
	$('#file_upload').uploadify({
		'swf'      : '/resources/misc/uploadify.swf',
		'uploader' : '/uploadFile;jsessionid='+$("#jsession").val(),
		'buttonText':' 上传图片 ',
		'buttonClass':'btn btn-mini btn-primary',
		'height': '23px',
	    'width': '120px',
	    'multi': false,
	    'file_types': '*.jpg;*.gif;*.png',
	    onUploadSuccess : function(file,data,response){
	    	if (response){
	    		var obj = eval('('+data+')');
	    		if (obj.state == 'SUCCESS'){
	    			var img_url = obj.url;
		    		$("#uploadImg img").attr("src",img_url);
		    		$("#uploadImg").show();
		    		$("#linkUrl").val(img_url);
	    		} else if (obj.state == 'SIZE_NOT_MATCH'){
	    			var size = $('#pixel').val();
	    			var standardSize = $('#pixel').find("option:selected").text().trim();
	    			alert("上传图片大小必须为"+standardSize);
	    		}
	    	}
	    },
	    onSelect : function(file){
	    	$("#file_upload").uploadify("settings", "formData",{'size':$('#pixel').val(),'xsize':$('#pixel').find("option:selected").text().trim()});
	    }
	});
	
	// flash上传
	$('#flash_upload').uploadify({
		'swf'      : '/resources/misc/uploadify.swf',
		'uploader' : '/uploadFile;jsessionid='+$("#jsession").val(),
		'buttonText':' 上传Flash ',
		'buttonClass':'btn btn-mini btn-primary',
		'height': '23px',
	    'width': '120px',
	    'multi': false,
	    'file_types': '*.swf',
	    onUploadSuccess : function(file,data,response){
	    	if (response){
	    		var obj = eval('('+data+')');
	    		var flash_url = obj.url;
	    		$("#flash").show();
	    		$("#flash").attr("src",flash_url);
	    		$("#linkUrl").val(flash_url);
	    	}
	    }
	});
	var uploadButton = $(".uploadify-button");
	$(uploadButton).removeClass("uploadify-button");
	$(uploadButton).attr("style","line-height: 23px;");
}).on("click",".form-actions .btn.btn-primary",function(){
	var materialTypeVal = $("input[name='materialType']:checked").val(); //上传 或者 远程
	var mTypeVal = $("input[name='mType']:checked").val(); //图片 flash 富文本 
	if(materialTypeVal == 2 && (mTypeVal == 2 || mTypeVal == 1)){
		$("#linkUrl").val($("#filePath").val());
	}
	else if(mTypeVal == 3){
		$("#textContent").val(UE.getEditor('editor').getContent());
	}
	if (checkInput()){
		ValuationmaterialValue();
		$(".form-horizontal").submit();
	}
}).on("click","#back",function(){
	window.location.href = "/material/list";
}).on("focus","#materialName",function(){
	$("#nameTips").parent().parent().removeClass("error");
	$("#nameTips").hide();
}).on("focus","#filePath",function(){
	$("#filePathTips").parent().parent().removeClass("error");
	$("#filePathTips").hide();
}).on("focus","#materialDesc",function(){
	$("#materialDescTips").parent().parent().removeClass("error");
	$("#materialDescTips").hide();
}).on("focus","#monitorlink",function(){
	$("#monitorlinkTips").parent().parent().removeClass("error");
	$("#monitorlinkTips").hide();
}).on("focus","#file_upload",function(){
	$("#file_uploadTips").parent().parent().removeClass("error");
	$("#file_uploadTips").hide();
}).on("focus","#flash_upload",function(){
	$("#flash_uploadTips").parent().parent().removeClass("error");
	$("#flash_uploadTips").hide();
}).on("focus","#targetUrl",function(){
	$("#targetUrlTips").parent().parent().removeClass("error");
	$("#targetUrlTips").hide();
});

function checkInput(){
	var materialName = $("#materialName").val();
	var mType = $("input[name='mType']:checked").val();
	var materialType = $("input[name='materialType']:checked").val();
	var uploadImg = $("#uploadImg img").attr("src");
	var flashSrc = $("#flash").attr("src");
	//var materialDesc = $("#materialDesc").val();
	var targetUrl = $("#targetUrl").val();
	var monitor = $("#monitor").attr("checked");
	var monitorlink = $("#monitorlink").val();
	var richText = UE.getEditor('editor').getContent();
	var linkUrl = $("#filePath").val();
	

	
    //  var regex = /^http:\/\/([\w-]+\.)+[\w-]+(\/[\w-.\/?%&=]*)?$/;

    var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
        + "|" // 允许IP和DOMAIN（域名）
        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
        + "[a-z]{2,6})" // first level domain- .com or .museum
        + "(:[0-9]{1,4})?" // 端口- :80
        + "((/?)|" // a slash isn't required if there is no file name
        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";

    var regex = new RegExp(strRegex);
    
	
	var check = true; 
	if ($.trim(materialName) == ''){
		$("#nameTips").text("广告物料名称不能为空");
		$("#nameTips").parent().parent().addClass("error");
		$("#nameTips").show();
		moveHtml("namePos");
		check = false;
	} else if (mType == 1 && materialType == 1 && uploadImg == ''){
		$("#file_uploadTips").text("请上传图片");
		$("#file_uploadTips").parent().parent().addClass("error");
		$("#file_uploadTips").show();
		moveHtml("pictuerPos");
		check = false;
	} else if (mType == 2 && materialType == 1 && flashSrc == ''){
		$("#flash_uploadTips").text("请上传flash");
		$("#flash_uploadTips").parent().parent().addClass("error");
		$("#flash_uploadTips").show();
		moveHtml("pictuerPos");
		check = false;
	} else if (mType == 3 && $.trim(richText) == ''){
		$("#richTextTips").text("富文本内容不能为空");
		$("#richTextTips").parent().parent().addClass("error");
		$("#richTextTips").show();
		moveHtml("textPos");
		check = false;
	} else if (mType == 1 && materialType == 2 && $.trim(linkUrl) == ''){
		$("#filePathTips").text("图片地址不能为空");
		$("#filePathTips").parent().parent().addClass("error");
		$("#filePathTips").show(); 
		moveHtml("picturePos");
		check = false;
	} else if (mType == 1 && materialType == 2 && !regex.test(linkUrl)){
		$("#filePathTips").text("图片地址格式错误");
		$("#filePathTips").parent().parent().addClass("error");
		$("#filePathTips").show();
		moveHtml("picturePos");
		check = false;
	} else if (mType == 2 && materialType == 2 && $.trim(linkUrl) == ''){
		$("#filePathTips").text("Flash地址不能为空");
		$("#filePathTips").parent().parent().addClass("error");
		$("#filePathTips").show();
		moveHtml("picturePos");
		check = false;
	}else if (mType == 2 && materialType == 2 && !regex.test(linkUrl)){
		$("#filePathTips").text("Flash地址格式错误");
		$("#filePathTips").parent().parent().addClass("error");
		$("#filePathTips").show();
		moveHtml("picturePos");
		check = false;
	}/*  else if (mType != 3 && $.trim(materialDesc) == ''){
		$("#materialDescTips").text("描述不能为空");
		$("#materialDescTips").parent().parent().addClass("error");
		$("#materialDescTips").show();
		check = false;
	} */ else if (mType != 3 && $.trim(targetUrl) == ''){
		$("#targetUrlTips").text("点击跳转链接不能为空");
		$("#targetUrlTips").parent().parent().addClass("error");
		$("#targetUrlTips").show();
		moveHtml("targetUrlPos");
		check = false;
	}/**else if (mType != 3 && !regex.test(targetUrl)){
		$("#targetUrlTips").text("点击跳转链接格式错误");
		$("#targetUrlTips").parent().parent().addClass("error");
		$("#targetUrlTips").show();
		moveHtml("targetUrlPos");
		check = false;
	 点击跳转不在校验
	}**/ else if (monitor == 'checked' && $.trim(monitorlink) == ''){
		$("#monitorlinkTips").text("监控链接不能为空");
		$("#monitorlinkTips").parent().parent().addClass("error");
		$("#monitorlinkTips").show();
		moveHtml("monitorlinkPos");
		check = false;
	}
	else if (monitor == 'checked' && !regex.test(monitorlink)){
		$("#monitorlinkTips").text("监控链接格式错误");
		$("#monitorlinkTips").parent().parent().addClass("error");
		$("#monitorlinkTips").show();
		moveHtml("monitorlinkPos");
		check = false;
	}
	return check;
}
</script>
</html>