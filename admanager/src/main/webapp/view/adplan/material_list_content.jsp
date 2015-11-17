<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
									<div class="" id="selectedDiv" style="width: 100%">
										<input type="hidden" name="adMaterialIds" id="adMaterialIds"/>
										<label class="" style="float:left">已选广告物料：</label>
										<input type="button" value="批量审核" onclick="batchForCheck()"/>
										<div class="row-fluid sortable" style="width: 100%">
											<div class="box span6" style="width: 80%">
												<table class="table" style="width: 100%">
												<tr>
												<td><input type="checkbox" name="checkAll" onclick="selectAll(this)">全选</td>
												<td>广告内容</td>
												<td>类型</td>
												<td>状态</td>
												<td>备注</td>
												<td>操作</td>
												</tr>
													<tbody id="selectedMaterial">
														<c:forEach var="m" items="${adMLinkDtoList}">
															<tr>
															<td> 
																<c:choose>
																		<c:when test="${
																		m.checkStatus eq 'WAITING' or m.checkStatus eq 'SENT' or m.checkStatus eq 'CHECKERROR' or 
																		m.checkStatus eq 'PASS' or m.checkStatus eq 'REFUSE'}">
																		<input name="check" type="checkbox"  disabled="disabled"/>${m.id}
																		</c:when>
																		<c:when test="${empty m.checkStatus or m.checkStatus eq 'SENDERROR'}"><input name="check" type="checkbox" value="${m.id}"/></c:when>
																	</c:choose>
															</td>
																<td>
																	<input type="hidden" name="materialId" value="${m.id}"/>
																	<span>${m.materialName}</span>
																	<c:choose>
																		<c:when test="${m.MType == 1}">
																			<div><img style="width:50px;height:50px;" src="${m.linkUrl}"/> </div>
																		</c:when>
																		<c:when test="${m.MType == 2}">
																			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0">
																				<param name="movie" value="0.swf">
																				<param name="quality" value="high">
																				<embed src="${m.linkUrl}" style="width:50px;height:50px;" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"/>
																			</object>
																		</c:when>
																		<c:when test="${m.MType == 3}">${m.richText}</c:when>
																		<c:otherwise>-</c:otherwise>
																	</c:choose>
																</td>
																<td class="center">
																	<c:choose>
																		<c:when test="${m.MType == 1}">图片</c:when>
																		<c:when test="${m.MType == 2}">Flash</c:when>
																		<c:otherwise>富媒体</c:otherwise>
																	</c:choose>
																</td>
																<td class="center"> 
																	<c:choose>
																		<c:when test="${m.checkStatus eq 'PASS'}"><span style="font-size: 12px" class="label label-success">审核通过</span></c:when>
																		<c:when test="${m.checkStatus eq 'REFUSE'}"><span style="font-size: 12px" class="label label-important">审核拒绝</span></c:when>
																		<c:when test="${m.checkStatus eq 'WAITING' or m.checkStatus eq 'SENT' or m.checkStatus eq 'CHECKERROR'}"><span style="font-size: 12px" class="label label-warning">审核等待中</span></c:when>
																		<c:when test="${m.checkStatus eq 'NOTSEND' or empty m.checkStatus}"><span style="font-size: 12px" class="label">未申请审核</span> </c:when>
																		<c:when test="${m.checkStatus eq 'SENDERROR'}"><span style="font-size: 12px" class="label label-important">审核出错</span></c:when>
																	</c:choose>
																</td>
																<td>
																	<c:choose>
																		<c:when test="${m.checkStatus == 'REFUSE'}">${m.comment}</c:when>
																	</c:choose>
																</td>
																<td class="center">
																	<c:choose>
<%-- 																		<c:when test="${m.checkStatus eq 'PASS'}">审核通过</c:when> --%>
<%-- 																		<c:when test="${m.checkStatus eq 'REFUSE'}">${m.comment}</c:when> --%>
<%-- 																		<c:when test="${m.checkStatus eq 'WAITING' or m.checkStatus eq 'SENT' or m.checkStatus eq 'CHECKERROR'}">审核等待</c:when> --%>
																		<c:when test="${empty m.checkStatus or m.checkStatus eq 'SENDERROR'}"><a class="btn btn-success" href="javascript:void()" onclick="sendForCheck(${m.id}, this)">申请审核</a></c:when>
																	</c:choose>
																</td>                                     
															</tr>
														</c:forEach>
													</tbody>
												 </table>  
											</div>
										</div>
									</div>
<script >
// 批量审核
function batchForCheck() {
	// 检查是不是有记录被选中
	var items = $("input[name='check']:checked");
    if(items.length <= 0) {
       alert("请选择要审核的记录！");
       return ;
    }
	// 获取要审核的所有的记录
	for(var i=0; i<items.length; i++) {
		// 获取 id
		var obj = items[i];
		// 获取 后面的链接
		var id = obj.value;
		//var objUrl = $($(obj).parent().parent().parent().parent().children()[5]).children()[0];
		var objUrl = $($(obj).parent().parent().children()[5]).children()[0];
		//$($(obj).parent().parent().children()[2]).html(arrMessage[1]);
		//alert(id+"\n" + $(objUrl).attr("href"));
		sendForCheck(id, objUrl);
	}
	
}

function selectAll(all) {
    var state = all.checked;
    var items = $("input[name='check']");
    if(items.length) {
        for(var i=0;i<items.length;i++) {
        	if( $(items[i]).attr("disabled")!="disabled"){
           		$(items[i]).attr("checked",state);
        	}
        }
    }
    var trs = $("#selectedMaterial tr").length;
    for(var j=1; j<=trs; j++) {
    	if( $(items[j-1]).attr("disabled")!="disabled"){
	    	if (state==true) {
	    		$("#selectedMaterial > tr:nth-child(" + j + ") > td:nth-child(1) > div:nth-child(1) > span:nth-child(1)").addClass("checked");
	    	} else {
	    		$("#selectedMaterial > tr:nth-child(" + j + ") > td:nth-child(1) > div:nth-child(1) > span:nth-child(1)").removeClass("checked");
	    	}
    	}
     //$($(obj).parent().parent().children()[2]).html(arrMessage[1]);
   // var itemSpans = $("#selectedMaterial > tr > td > div")
    	
    }
}


function sendForCheck(id, obj) {
	// 检查广告是不是有设置广告类目，有就继续，否则就提示设置广告类目
	 var date = new Date().getMilliseconds();
     var url = "/adCheck/checkAdboardTypeList/" + id + "?time=" + date;
	$.ajax({
		url:url,
		type:'GET',
		dataType:'text',
		async:false,
		success: function(result){
			if (result=="no") {
				alert("此创意对应的广告没有设置类目，请设置！");
			} else {
				// 通过 ajax 请求去发送申请
				sendCheck(id, obj);
			}
			totalSubMediaCateNum=result;
		},error:function(){
			alert("error");
		}
	});
}

function sendCheck(id, obj) {
	// 检查广告是不是有设置广告类目，有就继续，否则就提示设置广告类目
	var date = new Date().getMilliseconds();
     var url = "/adCheck/check/" + id + "?time=" + date;
	$.ajax({
		url:url,
		type:'GET',
		dataType:'text',
		async:true,
		success: function(result){
			//sendStatus:true;message:发送成功，请等待审核！
			
			var arr = result.split(";");
	        var arrStatus = arr[0].split(":");
	        var arrMessage = arr[1].split(":");
	       // alert(result);
	        // 发送成功，就把发送审核功能给去掉
	        if(arrStatus[1]=='true') {
	        	var msg = "<span style='font-size: 12px' class='label label-warning'>" +arrMessage[1] +  "</span>";
	        	
	        	//设置 div 添加 checker disabled class属性
	        	//$($($($(obj).parent().parent().children()[0]).children()[0]).children()[0]).children()[0].disabled=true;
	        	$($(obj).parent().parent().children()[0]).children()[0].disabled=true;
	        	//alert("执行到此");
	        	$($(obj).parent().parent().children()[3]).html(msg);
	        	// 不让它使用全选的功能：设置真正的 checkbox
	        	//$($($(obj).parent().parent().children()[0]).children()[0]).removeClass();
	        	//$($($(obj).parent().parent().children()[0]).children()[0]).addClass("checker disabled");
	        	//设置 checkbox 属性为
	          // $($($($( $(obj).parent().parent().parent().parent().children()[0] ).children()[0]).children()[0]).children()[0]).removeAttr("checked");
	            $($(obj).parent()).html("");
	        } else {
	        	alert("审核失败" + arrMessage[1] );
	        	var msg = "<span style='font-size: 12px' class='label label-important'>" +arrMessage[1] +  "</span>";
	            $($(obj).parent().parent().children()[3]).html(msg);
	        }
		},error:function(){
			alert("error");
		}
	});
}
</script>
