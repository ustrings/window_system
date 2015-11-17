<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<fieldset>
	<div class="control-group">
		<input type="hidden" value="${jsession}" id="jsession"/>
		<label class="control-label" for="materialName">广告物料名称：</label>
		<div class="controls">
			<input type="text" class="input-xlarge focused" id="materialName" name="materialName" maxlength="50"> <span id="nameTips" class="help-inline" style="display:none"></span>
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
	<div id="pic_flash_div">
		 <div class="control-group">
			<label class="control-label" for="selectError">规格：</label>
			<div class="controls">
			  <select id="pixel" data-rel="chosen" name="materialSize">
				<c:if test="${not empty meterTempList}">
					<c:forEach var="obj" items="${meterTempList}" varStatus="status">
						<option value="${obj.attrCode}">
							${obj.attrValue}	
					   </option>
												
					</c:forEach>
			  </c:if>
			  </select>
			</div>
		 </div>
	
		<div class="control-group" id="fileType">
			<label class="control-label" for="materialType">图片文件：</label>
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
			<label class="control-label">图片地址：</label>
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
			<label class="control-label" for="materialName">点击跳转链接：</label>
			<div class="controls">
				<input type="text" class="input-xlarge focused" name="targetUrl" id="targetUrl"  maxlength="255" value="http://"> <span id="targetUrlTips" class="help-inline" style="display:none"></span>
			</div>
		</div>
	</div>

	<div class="control-group" style="display: none" id="richText">
		<label class="control-label" for="richText">富文本：</label>
		<div class="controls">
			<script id="editor" type="text/plain"></script>
			<input type="hidden" name="richText" id="textContent"/>
			<span id="richTextTips" class="help-inline" style="display:none"></span>
		</div>
	</div>
	
	<div class="control-group">
			<label class="control-label" for="monitor" style="margin-left:25%;">
				<input type="checkbox" id="monitor" name="thirdMonitor" value="1" style="margin-top:-2px;">设置第三方监控</label>
			<div class="controls"></div>
	</div>
	<div class="control-group" style="display: none" id="monitorDiv">
		<label class="control-label" for="monitorlink">监控链接：</label>
		<div class="controls">
			<input type="text" class="input-xlarge focused" id="monitorlink" name="monitorLink"  maxlength="255" value="http://"> <span id="monitorlinkTips" class="help-inline" style="display:none"></span>
		</div>
	</div>
	
	<div class="control-group" >
		<div class="controls">
			<button type="button" class="btn btn-primary" id="confirmBtn">确定</button>
			<button type="button" class="btn" id="cancelBtn">取消</button>
		</div>
	</div>
</fieldset>
<script src="/resources/js/jquery.uploadify-3.1.min.js"></script>
<script type="text/javascript" src="/resources/ueditor/editor_config.js"></script>
<script type="text/javascript" src="/resources/ueditor/editor_all.js"></script>
<script>
$(document).ready(function(){
	UE.getEditor('editor',{
        initialFrameWidth : 350,
        initialFrameHeight: 120,
        imageUrl:"/uploadFile;jsessionid="+$("#jsession").val(), 
        imagePath:"",
        lang:'zh-cn',
        initialContent:''
    });
	
  	//实例化编辑器
  	var ue = UE.getEditor("editor");
	
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
}).on("click","input[name='materialType']",function(){
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
	
}).on("click","#monitor",function(){
	var _$this = $(this);
	if (_$this.attr("checked") == 'checked'){
		$("#monitorDiv").show();
	} else{
		$("#monitorDiv").hide();
	}
});
</script>