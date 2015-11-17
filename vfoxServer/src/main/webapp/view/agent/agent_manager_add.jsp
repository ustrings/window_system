<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<link  rel="stylesheet" type="text/css" href="/resources/css/uploadify.css"/>
<script type="text/javascript" src="/resources/js/jquery.uploadify-3.1.min.js"></script>
<script src="/resources/js/common.js" type="text/javascript"></script>

                    <form action="/agent/add" method="post" id="addForm">
                       <div class="tcLsyer_title">
							<span>新增代理商账户</span>
							<a href="javascript:;" class="close"></a>
						</div>
						<div class="tcLayer_content_whiteList">
							<div class="content_DIV">
								<span class="is_check">公司名称：</span>
								<input type="text" name="companyName" class="tc_inp" id="userDto.companyName_add"/>
								<span class="tips" style="display: none;" id="companyName_addTips">*公司名称不能为空</span>
							</div>
							<div class="content_DIV">
								<span class="is_check">公司法人：</span>
								<input type="text" name="companyLeader" id="userDto.companyLeader_add" class="tc_inp" />
								<span class="tips" style="display: none;" id="companyLeader_addTips">*公司法人不能为空</span>
							</div>
							<div class="content_DIV">
								<span class="is_check">联系人电话：</span>
								<input type="text" name="telNbr" class="tc_inp" id="userDto.telNbr_add"/>
								<span class="tips" style="display: none;" id="telNbr_addTips">*联系人电话不能为空</span>
							</div>
							<div class="content_DIV">
								<span class="is_check">登陆账号：</span>
								<input type="text" name="userName" class="tc_inp" id="userDto.userName_add"/>
								<span class="tips" style="display: none;" id="userName_addTips">*账号密码不能为空</span>
							</div>
							<div class="content_DIV">
								<span class="is_check">账号密码：</span>
								<input type="password" name="passWord" class="tc_inp" id="userDto.passWord_add"/>
								<span class="tips" style="display: none;" id="passWord_addTips">*账号密码不能为空</span>
							</div>							
							<!-- 证件上传   start -->
							<div class="content_DIV_agent">
								<span class="is_check">身份证：</span>
								<div class="up_DIV">
									<input type="file" class="g-u" id="uploadIdCard" value="上传图片" name="Filedata">
									<div style="display: none">
								       <div class="cont_one">
									      <span class="name">文件路径：</span>
									      <input type="text" id="idCardPath" name="idCardPath"/>
								       </div>
								       <div class="cont_one">
									      <span class="name">jessionId：</span>
									      <input type="text" id="jsession" name="jsession" value="${jsession}"/>
								       </div>
							        </div>
								</div>
							</div>						
							<div style="display:none;" id="upload1">
							   <img src="" id="idCardImg" style="margin-left: 120px;"/>
							</div>							
							<div class="content_DIV_agent">
								<span class="is_check">营业执照：</span>
								<div class="up_DIV">
									<input type="file" class="g-u" id="uploadYingye" value="上传图片" name="Filedata" accept="image/*" >									
								</div>
								<div style="display: none">
								    <div class="cont_one">
									    <span class="name">文件路径：</span>
									    <input type="text" id="yingyeCardPath" name="yingyeCardPath"/>
								     </div>
								     <div class="cont_one">
									    <span class="name">jessionId：</span>
									    <input type="text" id="jsession" name="jsession" value="${jsession}"/>
								     </div>
							    </div>
							</div>
							<div style="display:none;" id="upload2">
							   <img src="" id="yingyeCardImg" style="margin-left: 120px;"/>
							</div>
							<div class="content_DIV_agent">
								<span class="is_check">税务登记证：</span>
								<div class="up_DIV">
									<input type="file" class="g-u" id="uploadTaxCard" value="上传图片" name="Filedata" accept="image/*" >									
								</div>
								<div style="display: none">
								    <div class="cont_one">
									    <span class="name">文件路径：</span>
									    <input type="text" id="taxCardPath" name="taxCardPath"/>
								     </div>
								     <div class="cont_one">
									    <span class="name">jessionId：</span>
									    <input type="text" id="jsession" name="jsession" value="${jsession}"/>
								     </div>
							    </div>								
							</div>
							<div style="display:none;" id="upload3">
							   <img src="" id="taxCardImg" style="margin-left: 120px;"/>
							</div>
							<div class="content_DIV_agent">
								<span class="is_check">组织机构代码证：</span>
								<div class="up_DIV">
									<input type="file" class="g-u" id="uploadOrgCard" value="上传图片" name="Filedata" accept="image/*" >									
								</div>
								<div style="display: none">
								    <div class="cont_one">
									    <span class="name">文件路径：</span>
									    <input type="text" id="orgCardPath" name="orgCardPath"/>
								     </div>
								     <div class="cont_one">
									    <span class="name">jessionId：</span>
									    <input type="text" id="jsession" name="jsession" value="${jsession}"/>
								     </div>
							    </div>							
							</div>
							<div style="display:none;" id="upload4">
							   <img src="" id="orgCardImg" style="margin-left: 120px;"/>
							</div>
							<!-- 证件上传   end -->
							<div class="content_DIV">
								<input type="button" value="确定"  style="margin-left:40%;" id="okAdd" class="submitClass"/>
								<input type="button" value="取消" id="closeAdd" class="submitClass"/>
							</div>
						</div>
					</form>
<script>
$('#uploadIdCard').uploadify({
	'swf'      : '/resources/misc/uploadify.swf',
	'uploader' : '/uploadFile;jsessionid='+$("#jsession").val(),
	'buttonText':' 上传证件 ',
	'buttonClass':'btn btn-mini btn-primary',
	'height': '30px',
    'width': '120px',
    'multi': false,
    'file_types': '*.jpg;*.gif;*.png',
    onUploadSuccess : function(file,data,response){
    	if (response){
    		var obj = eval('('+data+')');
    		if (obj.state == 'SUCCESS'){
    			var img_url = obj.url;	 
	    		$("#idCardPath").val(img_url);
	    		$("#upload1").show();
	    		document.getElementById("idCardImg").src=obj.url;
    		} else{ 
    			alert("上传失败");
    		}
    	}
    },
    onSelect : function(file){
    	$("#uploadIdCard").uploadify("settings", "formData",{});
    }
});

$('#uploadYingye').uploadify({
	'swf'      : '/resources/misc/uploadify.swf',
	'uploader' : '/uploadFile;jsessionid='+$("#jsession").val(),
	'buttonText':' 上传营业执照 ',
	'buttonClass':'btn btn-mini btn-primary',
	'height': '30px',
    'width': '120px',
    'multi': false,
    'file_types': '*.jpg;*.gif;*.png',
    onUploadSuccess : function(file,data,response){
    	if (response){
    		var obj = eval('('+data+')');
    		if (obj.state == 'SUCCESS'){
    			var img_url = obj.url;	 
	    		$("#yingyeCardPath").val(img_url);
	    		$("#upload2").show();
	    		document.getElementById("yingyeCardImg").src=obj.url;
    		} else{ 
    			alert("上传失败");
    		}
    	}
    },
    onSelect : function(file){
    	$("#uploadYingye").uploadify("settings", "formData",{});
    }
});

$('#uploadTaxCard').uploadify({
	'swf'      : '/resources/misc/uploadify.swf',
	'uploader' : '/uploadFile;jsessionid='+$("#jsession").val(),
	'buttonText':' 上传税务登记证 ',
	'buttonClass':'btn btn-mini btn-primary',
	'height': '30px',
    'width': '120px',
    'multi': false,
    'file_types': '*.jpg;*.gif;*.png',
    onUploadSuccess : function(file,data,response){
    	if (response){
    		var obj = eval('('+data+')');
    		if (obj.state == 'SUCCESS'){
    			var img_url = obj.url;	 
	    		$("#taxCardPath").val(img_url);
	    		$("#upload3").show();
	    		document.getElementById("taxCardImg").src=obj.url;
    		} else{
    			alert("上传失败");
    		}
    	}
    },
    onSelect : function(file){
    	$("#uploadTaxCard").uploadify("settings", "formData",{});
    }
});

$('#uploadOrgCard').uploadify({
	'swf'      : '/resources/misc/uploadify.swf',
	'uploader' : '/uploadFile;jsessionid='+$("#jsession").val(),
	'buttonText':' 上传组织机构代码证 ',
	'buttonClass':'btn btn-mini btn-primary',
	'height': '30px',
    'width': '120px',
    'multi': false,
    'file_types': '*.jpg;*.gif;*.png',
    onUploadSuccess : function(file,data,response){
    	if (response){
    		var obj = eval('('+data+')');
    		if (obj.state == 'SUCCESS'){
    			var img_url = obj.url;	 
	    		$("#orgCardPath").val(img_url);
	    		$("#upload4").show();
	    		document.getElementById("orgCardImg").src=obj.url;
    		} else{ 
    			alert("上传失败");
    		}
    	}
    },
    onSelect : function(file){
    	$("#uploadOrgCard").uploadify("settings", "formData",{});
    }
});
</script>