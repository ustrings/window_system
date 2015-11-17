<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<div class="row-fluid sortable">
	<div class="box span8">
		<div class="box-header well" data-original-title>
			<h2>广告物料</h2>
		</div>
		<div class="box-content">
			  <div class="page-header">
				  <h1>${material.materialName} 
				  	<span class="label label-success">
				  		<c:choose>
							<c:when test="${material.MType == 1}">图片</c:when>
							<c:when test="${material.MType == 2}">Flash</c:when>
							<c:otherwise>富媒体</c:otherwise>
						</c:choose>
				  	</span>
				  </h1>
			  </div>     
			  <div class="row-fluid ">            
				  <div class="span8">
					<c:choose>
						<c:when test="${material.MType == 1}">
							<div><img src="${material.linkUrl}"/> </div>
						</c:when>
						<c:when test="${material.MType == 2}">
							<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0">
								<param name="movie" value="0.swf">
								<param name="quality" value="high">
								<embed src="${material.linkUrl}" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"/>
							</object>
						</c:when>
						<c:otherwise>
							<div>${material.richText}</div>
						</c:otherwise>
					</c:choose>
				  </div>
			  </div><!--/row -->                           
		  </div>
	</div><!--/span-->
</div><!--/row-->
