<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!--
		Charisma v1.0.0

		Copyright 2012 Muhammad Usman
		Licensed under the Apache License v2.0
		http://www.apache.org/licenses/LICENSE-2.0

		http://usman.it
		http://twitter.com/halalit_usman
	-->
<meta charset="utf-8">
	<title>人群</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template."/>
		<meta name="author" content="Muhammad Usman"/>

				<!-- The styles -->
				<link id="bs-css" href="/resources/css/bootstrap-cerulean.css"
					rel="stylesheet">
					<style type="text/css">
body {
	padding-bottom: 40px;
}

div.radio {
    float: left;
    position: relative;
}

.sidebar-nav {
	padding: 9px 0;
}
</style>
	<link href="/resources/css/bootstrap-responsive.css" rel="stylesheet"/>
	<link href="/resources/css/add_person.css" rel="stylesheet"/>
	<link href="/resources/css/charisma-app.css" rel="stylesheet"/>
	<link href="/resources/css/jquery-ui-1.8.21.custom.css" rel="stylesheet"/>
	<link href='/resources/css/fullcalendar.css' rel='stylesheet'/>
	<link href='/resources/css/fullcalendar.print.css' rel='stylesheet'  media='print'/>
	<link href='/resources/css/chosen.css' rel='stylesheet'/>
	<link href='/resources/css/uniform.default.css' rel='stylesheet'/>
	<link href='/resources/css/colorbox.css' rel='stylesheet'/>
	<link href='/resources/css/jquery.cleditor.css' rel='stylesheet'/>
	<link href='/resources/css/jquery.noty.css' rel='stylesheet'/>
	<link href='/resources/css/noty_theme_default.css' rel='stylesheet'/>
	<link href='/resources/css/elfinder.min.css' rel='stylesheet'/>
	<link href='/resources/css/elfinder.theme.css' rel='stylesheet'/>
	<link href='/resources/css/jquery.iphone.toggle.css' rel='stylesheet'/>
	<link href='/resources/css/opa-icons.css' rel='stylesheet'/>
	<link href='/resources/css/uploadify.css' rel='stylesheet'/>

<link rel="stylesheet" href="/resources/date/themes/base/jquery.ui.all.css"/>
<script src="/resources/date/js/jquery-1.7.2.min.js"></script>
<script src="/resources/date/js/date.init.js"></script>
<script src="/resources/date/js/jquery.ui.core.js"></script>
<script src="/resources/date/js/jquery.ui.widget.js"></script>
<script src="/resources/date/js/jquery.ui.datepicker.js"></script>
<link href='/resources/css/zTreeStyle/zTreeStyle.css' rel='stylesheet'>

<!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

<!-- The fav icon -->
<link rel="shortcut icon" href="/resources/img/favicon.ico"/>
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
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript"
							target="_blank">JavaScript</a> enabled to use this site.
					</p>
				</div>
			</noscript>

			<div id="content" class="span10">
				<!-- content starts -->


				<div>
					<ul class="breadcrumb">
						<li><a href="#">首页</a> <span class="divider">/</span></li>
						<li><a href="#">修改人群</a></li>
					</ul>
				</div>
				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well">
							<h2>
								<i class="icon-user"></i> 受众人群信息
							</h2>
						</div>
		<div class="box-content">
		<form>
	<div class="add_person">
		<div class="box_header">
			<h2>
				<i class="icon-edit"></i>
				修改人群
			</h2>
			<div class="box_icon">
				<a class="btn btn-setting btn_round" href="#">
					<i class="icon_cog"></i>
				</a>
				<a class="btn btn-minimize btn_round" href="#">
					<i class="icon_chevron_up"></i>
				</a>
				<a class="btn btn-close btn_round" href="#">
					<i class="icon_remove"></i>
				</a>
			</div>
		</div>
		<div class="box_content">
			<div class="content_title">
				<span>修改人群基本信息</span>
			</div>
			<div class="control_group" id="crowdNameDiv">
				<label class="control_label">
					<b style="color: #FF0000;">*</b>
					人群名称：
				</label>
				<div class="controls">
					<input class="input_xlarge" type="hidden"  name="crowdId" id="crowdId" value="${crowd.crowdId}"/>
					<input class="input_xlarge" type="text"  name="crowdName" id="crowdName" value="${crowd.crowdName}"/>
					<span id="crowdNameError" class="help_inline" style="display:none">人群名称不能为空</span>
				</div>
			</div>
			<div class="control_group" id="expressDateDiv">
				<label class="control_label">
					<b style="color: #FF0000;">*</b>
					到期时间：
				</label>
				<div class="controls">
					<input class="input_small input_datepicker" type="text"  name="expressDate" id="expressDate" value="${crowd.expressDate}"/>
					<span id="expressDateError" class="help_inline" style="display:none">请选择到期时间</span>
				</div>
			</div>
			
			<div class="control_group" >
				<label class="control_label">
					<b style="color: #FF0000;">*</b>
					定向关系：
				</label>
				<div class="controls">
					<div class="input_prepend input_append">
							<div class="radio">
								<span>
									<input type="radio" size="8" value="0" name="direct_type" 
									<c:choose>
										<c:when test="${crowd.directType =='0'}">checked="true" </c:when>
										<c:when test="${crowd.directType =='1'}"></c:when>
										<c:otherwise> checked="true" </c:otherwise>
									</c:choose>
									/>
								</span>
							</div>
							<div style="float:left"><span class="radio" style="line-height:20px;">或</span></div>
							<div>
								<span>
									<input type="radio" size="8" value="1" name="direct_type" 
									<c:choose>
										<c:when test="${crowd.directType =='0'}"></c:when>
										<c:when test="${crowd.directType =='1'}">checked="true" </c:when>
									</c:choose>
									/>
								</span>
							</div>
							<div style="float:left"><span class="radio" style="line-height:20px;">与</span></div>
						</div>
				</div>
			</div>
			
			<!--关键词定向开始-->
			<div class="controls_data">
				<div class="controls_icon">
					<img src="/resources/person-images/icon.png" class="icon" id="img_keyword_strong" style="cursor: pointer;"/><strong id="keyword_strong">关键词定向</strong>
				</div>
				<div class="controls_content" id="keyword" style="display:none;">
					<div class="control_group">
						<label class="control_label">
							<b style="color: #FF0000;">*</b>
							启动即搜即投：
						</label>
						<div class="controls">
							<div class="input_prepend input_append">
								<div class="radio">
									<span>
										<input type="radio" size="8" value="1" name="isJisoujitou" 
										<c:choose>
											<c:when test="${keyWordDirectDto.isJisoujitou =='1'}">checked="true" </c:when>
											<c:when test="${keyWordDirectDto.isJisoujitou =='2'}"></c:when>
											<c:otherwise> checked="true" </c:otherwise>
										</c:choose>
										/>
									</span>
								</div>
								<div style="float:left"><span class="radio" style="line-height:20px;">是</span></div>
								<div>
									<span>
										<input type="radio" size="8" value="2" name="isJisoujitou" 
										<c:choose>
											<c:when test="${keyWordDirectDto.isJisoujitou =='1'}"></c:when>
											<c:when test="${keyWordDirectDto.isJisoujitou =='2'}">checked="true" </c:when>
										</c:choose>
										/>
									</span>
								</div>
								<div style="float:left"><span class="radio" style="line-height:20px;">否</span></div>
								<span class="help_inline">请选择是否即搜即投</span>
							</div>
						</div>
					</div>
					<!--点击即搜即投为“是”时，显示下面的数据层开始-->
					<div class="opera_DIV"  id="opera_DIV">
						<div class="control_group">
							<div style="height:30px;width:80%;margin-left:5px;">
							<label class="control_label" style="width:115px">
								<b style="color: #FF0000;">*</b>
								提取周期：
							</label>
							<div class="controls">
								<span style="absolute;margin-top:-12px;">
									<table cellspacing="0" cellpadding="0" width="100%" border="0">
										<tr>
											<td align="left">
									
												<span style="position:absolute;border:1pt solid #c1c1c1;overflow:hidden;width:130px;height:24px;clip:rect(-1px 190px 120px 100px);">
													<select id="kwUrlFetchCicle" name="kwUrlFetchCicle" style="width:130px;height:26px;margin:-1px;" onChange="selectOnchange('kwUrlFetchCicle')">
														<option value="" style="color:#c2c2c2;">---请选择---</option>
														<option value="1" selected="selected">1天</option>
														<option value="3" <c:if test="${keyWordDirectDto.fetchCicle=='3'}"> selected="selected"</c:if>>3天</option>
														<option value="7" <c:if test="${keyWordDirectDto.fetchCicle=='7'}"> selected="selected"</c:if>>7天</option>
														<option value="15" <c:if test="${keyWordDirectDto.fetchCicle=='15'}"> selected="selected"</c:if>>15天</option>
														<option value="30" <c:if test="${keyWordDirectDto.fetchCicle=='30'}"> selected="selected"</c:if>>一个月</option>
													</select>
												</span>
												<span style="position:absolute;border-top:1pt solid #c1c1c1;border-left:1pt solid #c1c1c1;border-bottom:1pt solid #c1c1c1;width:110px;height:24px;" >
													<input type="text" id="kwUrlFetchCicleInput"  
													<c:choose>
														<c:when test="${not empty keyWordDirectDto.fetchCicle}">
															value="${keyWordDirectDto.fetchCicle}天" 
														</c:when>
														<c:otherwise>value="1天" </c:otherwise>
													</c:choose>
													 style=" border: 0 none;height: 21px;line-height: 21px;margin: 0; padding: 0;text-align: center; width: 110px;" />
												</span>
										
											</td>
										</tr>
									</table>
								</span>
							</div>
						</div>	
						</div>
						<div class="control_group">
							<label class="control_label">
								<b style="color: #FF0000;">*</b>
								搜索类型：
							</label>
							<div class="controls">
								<div class="input_prepend input_append">
									<div class="checker">
										<span>
											<input type="checkbox" value="1" name="searchType" id="searchType" 
											<c:choose>
												<c:when test="${keyWordDirectDto.searchType =='1'}">checked="checked"</c:when>
												<c:when test="${keyWordDirectDto.searchType =='3'}">checked="checked" </c:when>
												<c:otherwise>checked="checked"</c:otherwise>
											</c:choose>
											/>
										</span>
									</div>
										<label style="line-height:20px;display:block;float:left;margin-right: 5px;">搜索引擎</label>
									<div class="checker">
										<span>
											<input type="checkbox" value="2" name="searchType" id="searchType" 
											<c:choose>
												<c:when test="${keyWordDirectDto.searchType =='2'}">checked="checked" </c:when>
												<c:when test="${keyWordDirectDto.searchType =='3'}">checked="checked" </c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
											/>
										</span>
									</div>
										<label style="line-height:25px;display:block;float:left;position:relative;top:-3px;left:-1px">电商</label>
								</div>
							</div>
						</div>
						<div class="control_group">
							<label class="control_label">
								<b style="color: #FF0000;">*</b>
								关键词：
							</label>
							<div class="controls">
								<textarea cols="85" rows="10" name="keyWd" id="keyWd" style="width:320px;">${keyWordDirectDto.keyWds}</textarea>
								<div class="controls_tips">
									<label class="time_label">匹配关系:</label>
									<input type="radio" name="keyWdMatchType"  value="1"
										<c:if test="${keyWordDirectDto.keyWdMatchType =='1'}">checked="checked" </c:if>
									/><label class="label_02">精准</label>
									<input type="radio" name="keyWdMatchType"   value="2"
									<c:if test="${keyWordDirectDto.keyWdMatchType =='2'}">checked="checked" </c:if>
									/><label class="label_02">中心</label>
								</div>
								<div class="controls_tips"><input type="text" name="keyWdSearchDate" id="keyWdSearchDate" class="input_time" value="${keyWordDirectDto.keywordSearchDate}"/>
								<label class="time_label">小时内</label><label class="label_02">匹配</label>
								<input type="text" name="keyWdSearchNum" id="keyWdSearchNum" class="input_time" value="${keyWordDirectDto.keywordSearchNum}"/><label class="label_02">次数</label></div>
							</div>
						</div>
					
						<div class="control_group">
							<label class="control_label">
								<b style="color: #FF0000;">*</b>
								URL：
							</label>
							<div class="controls">
								<textarea cols="85" rows="10" id="urlContent" name="urlContent"  style="width:320px;">${keyWordDirectDto.urls}</textarea>
								<div class="controls_tips">
									<label class="time_label">匹配关系:</label>
									<input type="radio" name="urlMatchType"  value="1"
										<c:if test="${keyWordDirectDto.urlMatchType =='1'}">checked="checked" </c:if>
									/><label class="label_02">精准</label>
									<input type="radio" name="urlMatchType"   value="2"
									<c:if test="${keyWordDirectDto.urlMatchType =='2'}">checked="checked" </c:if>
									/><label class="label_02">中心</label>
								</div>
								<div class="controls_tips">
								<input type="text" name="urlMatchDate" id="urlMatchDate" class="input_time" value="${keyWordDirectDto.urlSearchDate}"/>
								<label class="time_label">小时内</label><label class="label_02">匹配</label>
								<input type="text" name="urlMatchNum" id="urlMatchNum" class="input_time"  value="${keyWordDirectDto.urlSearchNum}"/><label class="label_02">次数</label></div>
							</div>
						</div>
					</div>
					<!--点击即搜即投为“是”时，显示下面的数据层结束-->
					
				</div>
			</div>
			<!--关键词定向结束-->
			<!--智能模型人群定向开始-->
			<div class="controls_data">
				<div class="controls_icon">
					<img src="/resources/person-images/icon.png" class="icon" id="img_show_part" style="cursor: pointer;" /><strong id="show_part">智能模型人群定向</strong>
				</div>
				<div class="controls_content" id="show_person" style="display:none;">
					<div class="controls_part">
						<img src="/resources/person-images/icon_01.png" class="icon_01" /><label>智能模型人群定向</label>
					</div>
					<div class="control_group">
						<div style="height:30px;width:80%;margin-left:5px;">
							<label class="control_label" style="width:115px">
								<b style="color: #FF0000;">*</b>
								提取周期${integerModelCrowdDto.fetchCicle}：
							</label>
							<div class="controls">
								<span style="absolute;margin-top:-12px;">
									<table cellspacing="0" cellpadding="0" width="100%" border="0">
										<tr>
											<td align="left">
									
												<span style="position:absolute;border:1pt solid #c1c1c1;overflow:hidden;width:130px;height:24px;clip:rect(-1px 190px 120px 100px);">
													<select name="aabb" id="modelFetchCicle" name="modelFetchCicle"  style="width:130px;height:26px;margin:-1px;" onChange="selectOnchange('modelFetchCicle')">
														<option value="1" selected="selected">1天</option>
														<option value="3" <c:if test="${integerModelCrowdDto.fetchCicle=='3'}"> selected="selected"</c:if>>3天</option>
														<option value="7" <c:if test="${integerModelCrowdDto.fetchCicle=='7'}"> selected="selected"</c:if>>7天</option>
														<option value="15" <c:if test="${integerModelCrowdDto.fetchCicle=='15'}"> selected="selected"</c:if>>15天</option>
														<option value="30" <c:if test="${integerModelCrowdDto.fetchCicle=='30'}"> selected="selected"</c:if>>一个月</option>
													</select>
												</span>
												<span style="position:absolute;border-top:1pt solid #c1c1c1;border-left:1pt solid #c1c1c1;border-bottom:1pt solid #c1c1c1;width:110px;height:24px;"  >
													<input type="text"  id="modelFetchCicleInput"  
													<c:choose>
														<c:when test="${not empty integerModelCrowdDto.fetchCicle}">
															value="${integerModelCrowdDto.fetchCicle}天" 
														</c:when>
														<c:otherwise>value="1天" </c:otherwise>
													</c:choose>
													style=" border: 0 none;height: 21px;line-height: 21px;margin: 0; padding: 0;text-align: center; width: 110px;" />
												</span>
										
											</td>
										</tr>
									</table>
								</span>
							</div>
						</div>
						</div>
					<div class="controls_model">
					<table>
					<tr>
						<td style="padding-bottom: 7px">
						<input type="radio" name="modelType" value="1" style="opacity: 0;" 
							<c:choose>
								<c:when test="${integerModelCrowdDto.modelType =='1'}">checked="checked"  </c:when>
								<c:when test="${integerModelCrowdDto.modelType =='2'}"></c:when>
								<c:when test="${integerModelCrowdDto.modelType =='3'}"></c:when>
								<c:otherwise>checked="checked" </c:otherwise>
							</c:choose>
						/></td>
						<td><label>点击模型</label></td>
						<td><img src="/resources/person-images/answer_icon.png" class="answer_icon" style="margin-bottom: 7px" alt="点击模型"/></td>
					</tr>
					<tr>
						<td style="padding-bottom: 7px"><input type="radio" name="modelType" 
						<c:choose>
								<c:when test="${integerModelCrowdDto.modelType =='2'}">checked="checked"  </c:when>
							</c:choose>
						value="2" style="opacity: 0;" /></td>
						<td><label>注册模型</label></td>
						<td><img src="/resources/person-images/answer_icon.png" class="answer_icon" style="margin-bottom: 7px" alt="注册模型"/></td>
					</tr>
					<tr>
						<td style="padding-bottom: 7px"><input type="radio" name="modelType" value="3" 
						<c:choose>
								<c:when test="${integerModelCrowdDto.modelType =='3'}">checked="checked"  </c:when>
							</c:choose>
						style="opacity: 0;" /></td>
						<td><label>购买模型</label></td>
						<td><img src="/resources/person-images/answer_icon.png" class="answer_icon" style="margin-bottom: 7px" alt="购买模型"/></td>
					</tr>
					</table>

					</div>
					<div class="controls_model" style="margin-left:40px;">
						<label class="model_label">
							<b style="color: #FF0000;">*</b>
							URL：
						</label>
						<div class="models">
							<textarea cols="85" rows="10" name="modelMatchUrl" id="modelMatchUrl"  style="width:320px;">${integerModelCrowdDto.matchUrl}</textarea>
						</div>
					</div>
				</div>
			</div>
			<!--智能模型人群定向结束-->
			<!--兴趣标签定向开始-->
			<div class="controls_data" style="">
				<div class="controls_icon">
					<img src="/resources/person-images/icon.png" class="icon" id="img_habbit_strong" style="cursor: pointer;" /><strong id="habbit_strong">兴趣标签定向</strong>
				</div>
				<div class="controls_content" id="show_habbit" style="display:none;">
					
					<div class="controls_part">
						<img src="/resources/person-images/icon_01.png" class="icon_01" /><label>请选择产品所属的兴趣标签</label>
					</div>
					<div style="height:30px;width:80%;margin-left:5px;">
							<label class="control_label" style="width:115px">
								<b style="color: #FF0000;">*</b>
								提取周期
							</label>
							<div class="controls">
								<span style="absolute;margin-top:-12px;">
									<table cellspacing="0" cellpadding="0" width="100%" border="0">
										<tr>
											<td align="left">
									
												<span style="position:absolute;border:1pt solid #c1c1c1;overflow:hidden;width:130px;height:24px;clip:rect(-1px 190px 120px 100px);">
													<select name="aabb" id="interestlabelCircle"  name="interestlabelCircle"  style="width:130px;height:26px;margin:-1px;" onChange="selectOnchange('interestlabelCircle')">
															<option value="1" selected="selected">1天</option>
															<option value="3" <c:if test="${intrestLabel.fetchCicle=='3'}"> selected="selected"</c:if>>3天</option>
															<option value="7" <c:if test="${intrestLabel.fetchCicle=='7'}"> selected="selected"</c:if>>7天</option>
															<option value="15" <c:if test="${intrestLabel.fetchCicle=='15'}"> selected="selected"</c:if>>15天</option>
															<option value="30" <c:if test="${intrestLabel.fetchCicle=='30'}"> selected="selected"</c:if>>一个月</option>
													</select>
												</span>
												<span style="position:absolute;border-top:1pt solid #c1c1c1;border-left:1pt solid #c1c1c1;border-bottom:1pt solid #c1c1c1;width:110px;height:24px;"  >
													<input type="text"  id="interestlabelCircleInput" 
													<c:choose>
														<c:when test="${not empty intrestLabel.fetchCicle}">
															value="${intrestLabel.fetchCicle}天" 
														</c:when>
														<c:otherwise>value="1天" </c:otherwise>
													</c:choose>
													 style=" border: 0 none;height: 21px;line-height: 21px;margin: 0; padding: 0;text-align: center; width: 110px;" />
												</span>
										
											</td>
										</tr>
									</table>
								</span>
							</div>
						</div>	
					<div class="controls_model" >
						<div class="controls_box">
							<div id="interstLabeTree" class="ztree" style="display: none；;float:left;">
							
							</div>
							
							<div class="select_Data_DIV" id="interest_div"  
								<c:choose>
									<c:when test="${not empty intrestLabelList}">style="display: block;float:right;"</c:when>
									<c:otherwise>style="display: none;float:right;"</c:otherwise>
								</c:choose>
								>
								<c:forEach var="obj" items="${intrestLabelList}">
									<span class="data_span" style="margin-left:0px">
									<label id="click_show">${obj.label}</label>
									<img src="/resources/person-images/delete_button.png" objid="${obj.objid}" labelname="${obj.labelName}" class="delete_icon" id="del_btn" onclick="removeNode(this)">
									</span>
								</c:forEach>
							</div>
							
						</div>
					</div>
				</div>
			</div>
			<!--兴趣标签定向结束-->
			
			<!--人群画像定向开始-->
			<div class="controls_data">
				<div class="controls_icon">
					<img src="/resources/person-images/icon.png" class="icon" id="img_show_sex" style="cursor: pointer;" /><strong id="show_sex">人群画像定向</strong>
				</div>
				<div class="sex_DIV" id="sex_DIV" style="display:none;">
					<span class="habbit_type">
						<label class="sex_label">性别：</label>
						<input type="checkbox" name="sex" class="sex_singel"  value="2"  <c:if test="${crowdPortrait.sex=='1'}"> checked="checked"</c:if><c:if test="${crowdPortrait.sex=='2'}"> checked="checked"</c:if>/><label class="habbit_label">男</label>
						<input type="checkbox" name="sex" class="sex_singel"  value="3" <c:if test="${crowdPortrait.sex=='1'}"> checked="checked"</c:if><c:if test="${crowdPortrait.sex=='3'}"> checked="checked"</c:if>/><label class="habbit_label">女</label>
					</span>
					<span class="habbit_type">
						<label class="sex_label">年龄段：</label>
						<input type="checkbox" name="age_range" value="1"
						<c:if test="${not empty crowdPortraitList}">
								<c:forEach var="crowdPortrait" items="${crowdPortraitList}">
									<c:if test="${crowdPortrait.ageRange=='1'}"> checked="checked"</c:if>
								</c:forEach>
						</c:if>
						/><label class="habbit_label">10~20岁</label>
						<input type="checkbox" name="age_range" value="2"
						<c:if test="${not empty crowdPortraitList}">
								<c:forEach var="crowdPortrait" items="${crowdPortraitList}">
									<c:if test="${crowdPortrait.ageRange=='2'}"> checked="checked"</c:if>
								</c:forEach>
						</c:if>
						/><label class="habbit_label">21~30岁</label>
						<input type="checkbox" name="age_range" value="3"
						<c:if test="${not empty crowdPortraitList}">
								<c:forEach var="crowdPortrait" items="${crowdPortraitList}">
									<c:if test="${crowdPortrait.ageRange=='3'}"> checked="checked"</c:if>
								</c:forEach>
						</c:if>
						/><label class="habbit_label">31~40岁</label>
						<input type="checkbox" name="age_range" value="4"
						<c:if test="${not empty crowdPortraitList}">
								<c:forEach var="crowdPortrait" items="${crowdPortraitList}">
									<c:if test="${crowdPortrait.ageRange=='4'}"> checked="checked"</c:if>
								</c:forEach>
						</c:if>
						/><label class="habbit_label">大于40岁</label>
					</span>
					
				</div>
			</div>
			<!--人群画像定向结束-->
			
			<!--LBS位置定向开始-->
			<div class="controls_data">
				<div class="controls_icon">
					<img src="/resources/person-images/icon.png" class="icon"  id="img_show_LBS" style="cursor: pointer;"/><strong id="show_LBS">LBS位置定向</strong>
				</div>
				<div id="LBS_DIV" style="display:none;" class="LBS_DIV">
				
					<div class="sex_DIV">
						<div class="controls_part">
							<img src="/resources/person-images/icon_01.png" class="icon_01" /><label>GIS定位</label>
						</div>
						<div style="height:30px;width:80%;margin-left:5px;">
							<label class="control_label" style="width:115px">
								<b style="color: #FF0000;">*</b>
								提取周期：
							</label>
							<div class="controls">
								<span style="absolute;margin-top:-12px;">
									<table cellspacing="0" cellpadding="0" width="100%" border="0">
										<tr>
											<td align="left">
									
												<span style="position:absolute;border:1pt solid #c1c1c1;overflow:hidden;width:130px;height:24px;clip:rect(-1px 190px 120px 100px);">
													<select name="aabb" id="lbsFetchCicle" name="lbsFetchCicle"  style="width:130px;height:26px;margin:-1px;" onChange="selectOnchange('lbsFetchCicle')">
														<option value="1"  selected="selected">1天</option>
														<option value="3" <c:if test="${gisCrowdDto.fetchCicle=='3'}"> selected="selected"</c:if>>3天</option>
														<option value="7" <c:if test="${gisCrowdDto.fetchCicle=='7'}"> selected="selected"</c:if>>7天</option>
														<option value="15" <c:if test="${gisCrowdDto.fetchCicle=='15'}"> selected="selected"</c:if>>15天</option>
														<option value="30" <c:if test="${gisCrowdDto.fetchCicle=='30'}"> selected="selected"</c:if>>一个月</option>
													</select>
												</span>
												<span style="position:absolute;border-top:1pt solid #c1c1c1;border-left:1pt solid #c1c1c1;border-bottom:1pt solid #c1c1c1;width:110px;height:24px;" name="lbsFetchCicle" id="lbsFetchCicle">
													<input type="text" id="lbsFetchCicleInput" 
													<c:choose>
														<c:when test="${not empty gisCrowdDto.fetchCicle}">
															value="${gisCrowdDto.fetchCicle}天" 
														</c:when>
														<c:otherwise>value="1天" </c:otherwise>
													</c:choose>
													style=" border: 0 none;height: 21px;line-height: 21px;margin: 0; padding: 0;text-align: center; width: 110px;" />
												</span>
										
											</td>
										</tr>
									</table>
								</span>
							</div>
						</div>
						<div class="LBS_type_DIV" id="LBS_type_DIV">
						<table id="tbPosition">
						<tr>
							<td>
								<label class="sex_label">中心位置：</label>
								<input type="text" name="centryAddr1" class="lbs_input"  value="${gisCrowdDto.centryAddr}"style="width:100px;" id="centryAddr1"  onblur="checkAddress(this)"/>
							</td>
							<td>
								<label class="LBS_label">半径长度：</label>
								<input type="text" name="distanceValue1" class="lbs_input"  value="${gisCrowdDto.distanceValue}" style="width:100px;"  id="distanceValue1"/>
								<label class="LBS_label">公里</label>
							</td>
							<td>
							<img src="/resources/person-images/add_icon.png" class="add_img_icon" id="add_icon" onclick="addElmement()" style="padding-bottom: 20px;padding-right: 10px"/>
						<img src="/resources/person-images/delete1.png" class="add_img_icon" id="del_icon" onclick="delElmement()"/>
							</td>
						</tr>
						</table>
						</div>
						
						<div class="sex_DIV">
						<div class="controls_part">
							<img src="/resources/person-images/icon_01.png" class="icon_01" /><label>场所定位</label>
						</div>
						<div class="location_DIV">
							<div class="location_type">
								<input type="checkbox" name="checkbox" class="location_checkbox"   disabled="disabled" checked="checked" style="margin-top:6px;"/><label class="location_label">商场</label>
							</div>
							<div class="location_type">
								<input type="checkbox" name="checkbox" class="location_checkbox"  disabled="disabled"  /><label class="location_label">学校</label>
							</div>
							<div class="location_type">
								<input type="checkbox" name="checkbox" class="location_checkbox"  disabled="disabled"  /><label class="location_label">火车站</label>
							</div>
							<div class="location_type">
								<input type="checkbox" name="checkbox" class="location_checkbox"  disabled="disabled"  /><label class="location_label">飞机场</label>
							</div>
							<div class="location_type">
								<input type="checkbox" name="checkbox" class="location_checkbox"  disabled="disabled"  /><label class="location_label">咖啡厅</label>
							</div>
							<div class="location_type">
								<input type="checkbox" name="checkbox" class="location_checkbox"  disabled="disabled"  /><label class="location_label">步行街</label>
							</div>
						</div>
					</div>
						
					</div>
				</div>	
			</div>
			<input type="button" name="sub" onclick="add()" class="sure_button" value="提交" style="width:91px;"/>
			<input type="reset" name="reset" class="reset_button" value="取消" style="width:91px;"/>
		</div>
	</div>
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

			<div class="modal hide fade" id="myModal">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">Ã</button>
					<h3>Settings</h3>
				</div>
				<div class="modal-body">
					<p>Here settings can be configured...</p>
				</div>
				<div class="modal-footer">
					<a href="#" class="btn" data-dismiss="modal">Close</a> <a href="#"
						class="btn btn-primary">Save changes</a>
				</div>
			</div>

			<footer>
			<p class="pull-leftt" style="font-size:14px;">
				<a href="http://www.vaolan.com" target="_blank">Vaolan Corp.
					2014</a>
			</p>
			<p class="pull-right_t" style="font-size:14px;">
				Powered by: <a href="http://www.vaolan.com">Vaolan Corp.</a>
			</p>
			</footer>
	</div>
	<!--/.fluid-container-->
	<%@include file="../inc/footer.jsp"%>

</body>

<!--点击是即搜即投弹出层代码-->
	<script type="text/javascript">
		function open_opera(id){
			document.getElementById("opera_DIV").style.display="block";
		}
	</script>
	<!--点击否即搜即投弹出层代码-->
	<script type="text/javascript">
		function open_opera_02(id){
			document.getElementById("opera_DIV").style.display="none";
		}
	</script>
	
	  <script type="text/javascript">
	  var edit=true;	
	  $(function(){
		<!--点击关键词定位伸缩菜单开始-->
		$("#keyword_strong").click(function(){
			var keyword=$("#keyword").css("display");
			if(keyword=="none"){
				$("#keyword").show();
			}else{
				$("#keyword").hide();
			}
		});
		
		$("#img_keyword_strong").click(function(){
			var keyword=$("#keyword").css("display");
			if(keyword=="none"){
				$("#keyword").show();
			}else{
				$("#keyword").hide();
			}
		});
		
		
		<!--点击关键词定位伸缩菜单结束-->
		<!--智能模型人群开始-->
		$("#show_part").click(function(){
			var person=$("#show_person").css("display");
			if(person=="none"){
				$("#show_person").show();
			}else{
				$("#show_person").hide();
			}
		});
		$("#img_show_part").click(function(){
			var person=$("#show_person").css("display");
			if(person=="none"){
				$("#show_person").show();
			}else{
				$("#show_person").hide();
			}
		});
		
		<!--智能模型人群结束-->
		<!--兴趣标签开始-->
		$("#habbit_strong").click(function(){
			var habbit=$("#show_habbit").css("display");
			if(habbit=="none"){
				$("#show_habbit").show();
			}else{
				$("#show_habbit").hide();
			}
		});
		$("#img_habbit_strong").click(function(){
			var habbit=$("#show_habbit").css("display");
			if(habbit=="none"){
				$("#show_habbit").show();
			}else{
				$("#show_habbit").hide();
			}
		});
		
		<!--兴趣标签结束-->
		<!--人群画像开始-->
		$("#show_sex").click(function(){
			var sexy=$("#sex_DIV").css("display");
			if(sexy=="none"){
				$("#sex_DIV").show();
			}else{
				$("#sex_DIV").hide();
			}
		});
		$("#img_show_sex").click(function(){
			var sexy=$("#sex_DIV").css("display");
			if(sexy=="none"){
				$("#sex_DIV").show();
			}else{
				$("#sex_DIV").hide();
			}
		});
		
		<!--人群画像结束-->
		<!--LBS开始-->
		$("#show_LBS").click(function(){
			var LBS=$("#LBS_DIV").css("display");
			if(LBS=="none"){
				$("#LBS_DIV").show();
			}else{
				$("#LBS_DIV").hide();
			}
		});
		$("#img_show_LBS").click(function(){
			var LBS=$("#LBS_DIV").css("display");
			if(LBS=="none"){
				$("#LBS_DIV").show();
			}else{
				$("#LBS_DIV").hide();
			}
		});
		
		<!--点击option里的数据显示删除按钮代码开始-->
		$(".select_Data_DIV > span > label").each(function(){
			$(this).click(function(){
				$(this).next().css("display","block");
			});
		});
		focusoutCheckNull("crowdName");
		focusCheckNull("crowdName");
		focusCheckNull("expressDate");
		//初始化兴趣标签树
		initIntrestLabelTree();
		removeCheckStyle();//去掉checkbox 样式
		
	  });//初始化方法

	  function focusoutCheckNull(id){
		  $("#"+id).focusout(function(){
	            var $val = $("#"+id).val();
	            if($.trim($val) == ''){
	            	showError(id);
	            }
	        });
	  }
	  function focusCheckNull(id){
		 
		  $("#"+id).focus(function(){
			  	hideError(id);
	        });	 
	  }
	  function showError(id){
		  $("#"+id+"Error").parent().parent().addClass("error");
      	  $("#"+id+"Error").show();
	  }
	  function hideError(id){
		  $("#"+id+"Error").parent().parent().removeClass("error");
          $("#"+id+"Error").hide();
	  }
	  //聚焦错误位置 函数
	    function moveHtml(id){
	        var scroll_offset = $("#" + id).offset().top-110;
	        $("body,html").animate({
	            scrollTop : scroll_offset
	            //让body的scrollTop等于pos的top，就实现了滚动
	        }, 500);
	    }
	  var inputIndex = 2;
	
		// 新增元素
		function addElmement(){
			$("#tbPosition").append("<tr><td><label class='sex_label'>中心位置：</label>"
						+"<input type='text' name='centryAddr" + inputIndex + "' id='centryAddr" + inputIndex + "' class='lbs_input'  style='width:100px;'/>"
						+"</td><td><label class='LBS_label'>半径长度：</label>"
						+"<input type='text' name='distanceValue" + inputIndex + "' id='distanceValue" + inputIndex + "' class='lbs_input'  style='width:100px;'/>"
						+"<label class='LBS_label'>公里</label></td> <td>" +
						"<img src='/resources/person-images/add_icon.png' class='add_img_icon' id='add_icon' onclick='addElmement()' style='padding-bottom: 20px;padding-right: 10px'/>" +
						"<img src='/resources/person-images/delete1.png' class='add_img_icon' id='del_icon' onclick='delElmement()'/>" +
						"</td></tr>");
			inputIndex = inputIndex +1;
		}
		
	  // 删除元素
	  function delElmement() {
		  $('#tbPosition tr:eq(' + (inputIndex-1) + ')').remove();
		  if(inputIndex > 2) {
			inputIndex = inputIndex -1;
		  }
	  }
	  
	  // 新增信息
	  function add() {
		   // 获取所有的中心位置
		   // 人群名称
		   var crowdId = $("#crowdId").val();
		    var crowdName = $("#crowdName").val();
		    if($.trim(crowdName) == ''){
            	showError("crowdName");
            	moveHtml("crowdNameDiv");
            	return;
            }
		    // 有效时间
		    var expressDate = $("#expressDate").val();
		    if($.trim(expressDate) == ''){
            	showError("expressDate");
            	moveHtml("expressDateDiv");
            	return;
            }
		    // 关键词定向
		    // 是否即搜即投
		    var isJisoujitou = $('input[name="isJisoujitou"]:checked').val();
		    //var urlIsJisoujitou = $('input[name="urlIsJisoujitou"]:checked').val();
		    // 提取周期【包括了 关键词 和 url】
		    var kwUrlFetchCicle = $("#kwUrlFetchCicle").val();
		    // 搜索类型
		    var searchType = getCheckAdIds("searchType");
		    // 关键词
		    var keyWd = $("#keyWd").val();
		    // 关键词（xx）小时内
		    var keyWdSearchDate = $("#keyWdSearchDate").val();
		    // 关键词（xx）次投放
		    var keyWdSearchNum = $("#keyWdSearchNum").val();
		    // url 内容
		    var urlContent = $("#urlContent").val();
		    // url（xx）小时内
		    var urlMatchDate = $("#urlMatchDate").val();
		    // url (xx) 次投放
		    var urlMatchNum = $("#urlMatchNum").val();
		    // 智能人群模型定向
		    var modelFetchCicle = $("#modelFetchCicle").val();
		    // 模型类型
		    var modelType = $('input[name="modelType"]:checked').val();
		    // 匹配 url
		    var modelMatchUrl = $("#modelMatchUrl").val();
		    // 兴趣标签定向
			var interestlabelCircle = $("#interestlabelCircle").val();
		    var labelsArray = [];
		    $(".delete_icon").each(function(){
				 var _this = $(this);
				 var deleteid = $(_this).attr("objId");
				 labelsArray.push(deleteid);
			 });
		
		    // lbs 定向
		    // 提取周期
		    var lbsFetchCicle = $("#lbsFetchCicle").val();
		    // 获取中心位置和半径长度
		    //
		    var addrAndDistances =getGisInfo();
    
		    
		    var sexArr= [];
		    var sex = "";
   			$("input[name=sex]").each(function(){
   				if($(this).attr("checked")){
   					sexArr.push($(this).val());
   				}
   			});
			if(sexArr.length==1){
				sex = sexArr[0];
			}else if(sexArr.length==2){
				sex = 1;
			}else{
				sex=""
			}
			
		    var age_rangeArray = [];
		    $("input[name=age_range]").each(function(){
   				if($(this).attr("checked")){
   					age_rangeArray.push($(this).val())
   				}
   			});
		    var direct_type= "";
   			$("input[name=direct_type]").each(function(){
   				if($(this).attr("checked")){
   					direct_type = $(this).val();
   					return;
   				}
   			});
   			var keyWdMatchType="";
   			$("input[name=keyWdMatchType]").each(function(){
   				if($(this).attr("checked")){
   					keyWdMatchType = $(this).val();
   					return;
   				}
   			});
   			var urlMatchType="";
   			$("input[name=urlMatchType]").each(function(){
   				if($(this).attr("checked")){
   					urlMatchType = $(this).val();
   					return;
   				}
   			});
		  // 启用当前页面
			var url = "/crowd/addCrowdNew";
			WaitingBar.show();
			$.ajax({
				url:url,
				data:{crowdName:crowdName,expressDate:expressDate,isJisoujitou:isJisoujitou,
					kwUrlFetchCicle:kwUrlFetchCicle,searchType:searchType,keyWd:keyWd,
					keyWdSearchDate:keyWdSearchDate,keyWdSearchNum:keyWdSearchNum,urlContent:urlContent
					,urlMatchDate:urlMatchDate,urlMatchNum:urlMatchNum,modelType:modelType,
					modelFetchCicle:modelFetchCicle, 
					modelMatchUrl:modelMatchUrl,lbsFetchCicle:lbsFetchCicle,addrAndDistances:addrAndDistances,
					interestlabelCircle:interestlabelCircle, interestLabels:labelsArray.join(","),crowdId:crowdId,sex:sex,age_ranges:age_rangeArray.join(","),direct_type:direct_type,keyWdMatchType:keyWdMatchType,urlMatchType:urlMatchType},
				async:true,
				type:'POST',
				dataType:'json',
				success:function(data){
						if(data.result) {
							alert("修改成功！");
							location="/crowd/crowdlistaction";
						} else{
							WaitingBar.hide();
							alert("修改失败！");
						}
					},
				error:function(){
					WaitingBar.hide();
					alert("修改失败！");
				}
			});
	  }
	  
		
	  
	  //检查输入的地址是不是有效
	  function checkAddress(obj) {
		var centryAddr_val = $(obj).val();
		
		//alert("查询数据");
			var url = "/crowd/checkAddress";
			$.ajax({
				url:url,
				data:{centryAddr:centryAddr_val},
				type:'GET',
				dataType:'json',
				async:true,
				success: function(result){
						//alert(JSON.stringify(result));
					if(result.stat == "true") {
						//alert("查询成功！");
					} else {
						alert("中心位置名称不符合要求，请修改！");
					 	$(obj).val("");
					}
				},error:function(){
					alert("中心位置名称不符合要求！");
					 $(obj).val("");
				}
			});
	  }
		
	   function getCheckAdIds(checkName) {
	        var adIds = "";
	        $("input:checkbox[name=" + checkName + "]:checked'").each(function(i){
	            if(0==i){
	                adIds = $(this).val();
	            }else{
	                adIds += (","+$(this).val());
	            }
	        });
	        //alert(adIds);
	        return adIds;
	    }
	   
	   function getGisInfo() {
		   var content = "";
		   for (var i=1 ;i < inputIndex; i++) {
			   var centryAddr = $("#centryAddr" + i).val();
			   var distanceValue = $("#distanceValue" + i).val();
			   content = content + centryAddr + ":" + distanceValue +",";
		   }
		   content = content.substring(0, content.length -1);
		   //alert(content);
		   return content;
	   }

	   function selectOnchange(selectId){
		   var inputId = selectId+"Input";
		   document.getElementById(inputId).value=document.getElementById(selectId).options[document.getElementById(selectId).selectedIndex].text;
	   }
	  </script>
<script type="text/javascript" src="/resources/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.ztree.exhide-3.5.min.js"></script>
<script type="text/javascript" src="/resources/js/common/intersttree.js"></script>
</html>
