<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>广告计划</title>

	<link id="bs-css" href="/resources/css/bootstrap-cerulean.css" rel="stylesheet">
	<style type="text/css">
	  body {
		padding-bottom: 40px;
	  }
	  .sidebar-nav {
		padding: 9px 0;
	  }
	</style>
	<link href="/resources/css/bootstrap-responsive.css" rel="stylesheet">
	<link href="/resources/css/charisma-app.css" rel="stylesheet">
	<link href="/resources/css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
	<link href='/resources/css/fullcalendar.css' rel='stylesheet'>
	<link href='/resources/css/fullcalendar.print.css' rel='stylesheet'  media='print'>
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
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			
			<div id="content" class="span10">
			<div>
				<ul class="breadcrumb">
					<li>
						<a href="#">首页</a> <span class="divider">/</span>
					</li>
					<li>
						<a href="#">广告计划</a>
					</li>
				</ul>
			</div>
			
			
			<div class="row-fluid sortable">		
				<div class="box span12">
					<div class="box-header well" data-original-title>
						<h2><i></i> 广告计划列表</h2>
					</div>
					<div class="box-content">
						<div class="row-fluid" style="margin-bottom:10px;">
							<button type="button" id="addnew" class="btn btn-primary">新建广告计划</button>							
						</div>
						<table class="table table-striped table-bordered bootstrap-datatable datatable">
						  <thead>
							  <tr>
							      <th>广告id</th>
								  <th>广告名称</th>
								  <th>投放周期</th>
								  <th>每日期望PV</th>
								  <th>创意类型</th>
								  <th>投放状态</th>
								  <th>模拟预览</th>
								  <th>操作</th>
							  </tr>
						  </thead>   
						  <tbody>
							<c:if test="${not empty adList}">
								<c:forEach var="ad" items="${adList}">
									<tr>
									    <td>${ad.adId}</td>
										<td>${ad.adName}</td>
										<td class="center">${ad.startTime}~${ad.endTime }</td>
										<td class="center">${ad.dayLimit}</td>
										<c:choose>  
											<c:when test="${ad.linkType == 'M'}">  
											     <td>广告物料 </td>
											</c:when> 
											<c:when test="${ad.linkType == 'E'}">  
											     <td>投放链接</td>
											</c:when>  
											<c:otherwise>  
											     <td>JS代码</td>
											</c:otherwise>    
										</c:choose>
										<td>
											${ad.statue}
										</td>
										<td class="center">
										
										
										<c:choose>  
											<c:when test="${ad.linkType == 'M'}">  
													<a href="javascript:;" name="urlA" onclick="checkAdurl('${ad.adName}','${ad.adId}','${ad.linkType}','${ad.adUrl}')">模拟预览</a>
											</c:when> 
											<c:when test="${ad.linkType == 'E'}">  
										            <a href="javascript:;" name="urlA" onclick="checkAdurl('${ad.adName}','${ad.adId}','${ad.linkType}','${ad.throwUrl}')">模拟预览</a>
											</c:when>  
											<c:otherwise>  
											     <a href="javascript:;" name="urlA" onclick="checkAdurl('${ad.adName}','${ad.adId}','${ad.linkType}','jsCode')">模拟预览</a>
											</c:otherwise>    
										</c:choose>
										
										
										 
										  
										</td>
										<td>
											<c:choose>  
											        <c:when test="${ad.linkType == 'M'}">  
											            <a href="${ad.adUrl}${'?type=view'}" target="_blank">
															<i class="icon-zoom-in icon-white"></i> 预览
														</a>
											        </c:when> 
											         <c:when test="${ad.linkType == 'E'}">  
											              	<a href="${ad.throwUrl}" target="_blank">
																<i class="icon-zoom-in icon-white"></i> 预览
															</a>
											        </c:when>  
											        <c:otherwise>  
											        	<a href="/adplan/jsShow?adId=${ad.adId}" target="_blank">
															<i class="icon-zoom-in icon-white"></i> 预览
														</a>
											        </c:otherwise>    
											</c:choose>											
											<a href="/ad/singleAdReportIndex/${ad.adId}">
												<i class="icon-info-sign icon-white"></i> 报告
											</a>
										<c:if test="${ad.adToufangSts != '2' and ad.adToufangSts !='4' and ad.adToufangSts !='6'}">
											<a  href="/adplan/initedit/${ad.adId}">
												<i class="icon-edit icon-white"></i>  
												编辑                                            
											</a>
										</c:if>
										<c:if test="${ad.adToufangSts != '2' and ad.adToufangSts !='3'}">
											<a href="javascript:;" onclick="deleteAd('${ad.adId}')">
												<i class="icon-trash icon-white"></i> 
												删除
											</a> 
										 </c:if>
										 <c:if test="${ad.adToufangSts =='-1'}">
										  |  <a href="/adplan/submitCheck/${ad.adId}">
										                      提交审核
										     </a>
										 </c:if>
										</td>
									</tr>
								</c:forEach>
							</c:if>
						  </tbody>
					  </table> 
					  <div id="adLinks" style="display:none;float:left;">
						  <span id="adNAME"></span>
						  <span id="adURL" style="color:#317eac"></span>
						  <input type="button" name="closeTr" style="margin-left: 50px;" value="隐藏链接"/>							
					  </div>             
					</div>
				</div><!--/span-->
			</div><!--/row-->    
					<!-- content ends -->
			</div><!--/#content.span10-->
				</div><!--/fluid-row-->
		
	</div><!--/.fluid-container-->
	
	
	
	<div class="modal hide fade" id="viewInPage">

		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<h3>模拟预览:<span id="viewAdName"></span></h3>
		</div>

		<div class="modal-body">
		
			<div class="control-group">
       		 	<label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>创意类型：  <span id="creativeType" ></span></label>
		       
           </div>
           
           <div class="control-group">
       		 	<label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>广告连接：<span id="adDisUrl" ></span></label>
		       
           </div>
           
		 	<div class="control-group">
       		 	<label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>请输入预览的页面：</label>
		        <div class="control">
		            <input class="input-xlarge focused" id="pageUrl"
		                   name="pageUrl" type="text" value="" />
		            <span id="pageUrlTips" class="help-inline" style="display:none"></span>
		            
		            <input  type="hidden" id="viewAdId" ></input>
		            <input  type="hidden" id="viewAdLinkType" ></input>
		        </div>

           </div>
           
           
           <div class="control-group">
		        <div class="control">
		        <a href="javascript:void(0)" target="_blank" id="pageGo">
						 <input type="button" value="模拟预览" />
				</a>
														
		        </div>

           </div>
			
		</div>

		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
		</div>

	</div>
	
	
	
	
	
	<footer>
    <p class="pull-leftt" >
        <a href="http://www.vaolan.com" target="_blank">Vaolan Corp. 2014</a>
    </p>
    <p class="pull-right_t">
        Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
    </p>
</footer>
<%@include file="../inc/footer.jsp"%>
</body>
<script>
$(document).ready(function(){
	$('.datatable').dataTable({
		"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType": "bootstrap",
		 "oLanguage": {
             "sProcessing": "正在加载中......",
             "sLengthMenu": "每页显示 _MENU_ 条记录",
             "sZeroRecords": "对不起，查询不到相关数据！",
             "sEmptyTable": "表中无数据存在！",
             "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
             "sInfoEmpty":"当前显示 0 到 0 条，共 0 条记录",
             "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
             "sSearch": "搜索",
             "oPaginate": {
                 "sFirst": "首页",
                 "sPrevious": "上一页",
                 "sNext": "下一页",
                 "sLast": "末页"
             }
         }
	} );
}).on("click","#addnew",function(){
	window.location.href = "/adplan/initadd";
}).on("click","input[name='closeTr']", function(){
	$("#adLinks").hide();
}).on("click","#pageGo",function(){
	var pageUrl = $("#pageUrl").val();
	if($.trim(pageUrl) == ''){
		
        $("#pageUrlTips").text("url不能为空");
        $("#pageUrlTips").parent().parent().addClass("error");
        $("#pageUrlTips").show();
    }else{
    	
    	var adId = $("#viewAdId").val();
    	var linkType = $("#viewAdLinkType").val();
    	var pageUrl = $("#pageUrl").val();
    	$("#pageGo").attr("href","/adplan/simulationShow?adId="+adId+"&linkType="+linkType+"&pageUrl="+pageUrl);
    }
	
}).on("focus","#pageUrl",function(){
	$("#pageUrlTips").parent().parent().removeClass("error");
    $("#pageUrlTips").hide();
});


function deleteAd(id){
	if(window.confirm("你确定要删除")){
		$.ajax({
			url:'/adplan/delete',
			data:{adId:id},
			async:false,
	        type:'GET',
	        success:function(data){
	            if(data=='0'){
	              window.location.href="/adplan/list";
	            }else{
	                alert("删除失败");
	            }
	        }
		});
	}
}

function checkAdurl(adName,adId,linkType,url){
	//$("#adLinks").show();
	//$("#adNAME").text(adName+"的广告链接:");
	//$("#adURL").text(url);
	
	$("#viewInPage").modal('show');
	$("#viewAdId").val(adId);
	$("#viewAdName").text(adName);
	$("#viewAdLinkType").val(linkType);
	$("#pageUrl").val("");
	if(linkType =='M'){
		$("#creativeType").text("平台管理素材");	
		$("#adDisUrl").text(url);
	}else if (linkType =="E"){
		$("#creativeType").text("广告主的广告url");	
		$("#adDisUrl").text(url);
	}else {
		$("#creativeType").text("原生js代码");
	}
	
}
</script>
</html>
