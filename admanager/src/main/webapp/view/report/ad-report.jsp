<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
			
					<div class="box-content">
						<div class="row-fluid navbar">
							<div id="time-range">
								<a href="#" index="1">昨天</a><span class="divider-vertical"></span>
								<a href="#" index="2">今天</a><span class="divider-vertical"></span>
								<a href="#" index="3">上周</a><span class="divider-vertical"></span>
								<a href="#" index="4">本周</a><span class="divider-vertical"></span>
								<a href="#" index="5">上月</a><span class="divider-vertical"></span>
								<a href="#" index="6">本月</a>
								&nbsp;<input type="text" class="input-small datepicker" id="start" readonly>&nbsp;至&nbsp;<input type="text" class="input-small datepicker" id="end" readonly>
								<button type="button" id="confirm" class="btn btn-primary" style="margin-top:0px">确定</button>
							</div>
						</div>
						
						<div class="sortable row-fluid" style="margin-left: 0px;">
							<a data-rel="tooltip" class="well span2 top-block" href="#">
								<div>总展现量</div>
								<div id="totalImpressNum">
									<c:choose>
										<c:when test="${empty viewDto.totalImpressNum}">0.0</c:when>
										<c:otherwise>${viewDto.totalImpressNum}</c:otherwise>
									</c:choose>
								</div>
							</a>
			
							<a data-rel="tooltip" class="well span2 top-block" href="#">
								<div>总点击量</div>
								<div id="totalClickNum">
									<c:choose>
										<c:when test="${empty viewDto.totalClickNum}">0.0</c:when>
										<c:otherwise>${viewDto.totalClickNum}</c:otherwise>
									</c:choose>
								</div>
							</a>
			
							<a data-rel="tooltip"  class="well span2 top-block" href="#">
								<div>点击率</div>
								<div id="clickRate">
									<c:choose>
										<c:when test="${empty viewDto.clickRate}">0.0%</c:when>
										<c:otherwise>${viewDto.clickRate}%</c:otherwise>
									</c:choose>
								</div>
							</a>
							
							<a data-rel="tooltip" class="well span2 top-block" href="#">
								<div>每千次展现价格</div>
								<div id="cpmPrice">
									<c:choose>
										<c:when test="${empty viewDto.cpm_price}">0.0</c:when>
										<c:otherwise>${viewDto.cpm_price}</c:otherwise>
									</c:choose>
								</div>
							</a>
							<a data-rel="tooltip" class="well span2 top-block" href="#">
								<div>消耗金额</div>
								<div id="totalamount">
									<c:choose>
										<c:when test="${empty viewDto.totalAmount}">0.0</c:when>
										<c:otherwise>${viewDto.totalAmount}</c:otherwise>
									</c:choose>
								</div>
							</a>
						</div>
						
						<c:if test="${not empty adInstance}">
							<div class="page-header">
								<input type="hidden" value="${adInstance.adId}" id="id"/>
								<span class="label label-success" style="font-size:22px;margin-left:10px;">${adInstance.adName}</span>
							</div>
						</c:if>
						
						<div class="control-group">
							<div class="controls">
								<label class="radio"> <input type="radio" name="type" value="1" checked />展现量 
								</label>
								<label class="radio" style="padding-top: 5px;">
									<input type="radio" name="type" value="2" />点击量
								</label> 
								
							</div>
						</div>
						<div id="reportChart"  class="center" style="height:300px" ></div>
						<br></br>
						<div class="row-fluid" id="listTable">
							<%@include file="./ad-template.jsp" %>           
						</div>
					</div>
		<form class="form-horizontal" method="post" action="/export/excel" id="exportFrom">
			<input name="start" id="start_ex" type="hidden"/>
			<input name="end"  id="end_ex" type="hidden"/>
			<input name="index"  id="index_ex" type="hidden"/>
			<input name="id"  id="id_ex" type="hidden"/>
			<input name="type"  id="type_ex" type="hidden"/>
		</form>
