<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <title>诏兰数据广告平台</title>
    <meta name="description"
          content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
    <!-- The styles -->
    <link id="bs-css" href="/resources/css/bootstrap-cerulean.css"
          rel="stylesheet">
    <style type="text/css">
        body {
            padding-bottom: 40px;
        }

        .sidebar-nav {
            padding: 9px 0;
        }

        .hourselno {
            width: 20px;
            height: 20px;
            text-align: center;
            background-color: gray;
        }

        .hourselyes {
            width: 15px;
            height: 15px;
            text-align: center;
            border-style: solid;
        }

        .hourselview {
            width: 15px;
            height: 15px;
            text-align: center;
            border-style: double;
        }

        .hourSum {
            color: red;
        }
        .uploadify-queue{display:none}
    </style>
    <link href="/resources/css/bootstrap-responsive.css" rel="stylesheet" />
    <link href="/resources/css/charisma-app.css" rel="stylesheet" />
    <link href="/resources/css/jquery-ui-1.8.21.custom.css"
          rel="stylesheet" />
    <link href='/resources/css/fullcalendar.css' rel='stylesheet' />
    <link href='/resources/css/fullcalendar.print.css' rel='stylesheet'
          media='print' />
    <link href='/resources/css/chosen.css' rel='stylesheet' />
    <link href='/resources/css/uniform.default.css' rel='stylesheet' />
    <link href='/resources/css/colorbox.css' rel='stylesheet' />
    <link href='/resources/css/jquery.cleditor.css' rel='stylesheet' />
    <link href='/resources/css/jquery.noty.css' rel='stylesheet' />
    <link href='/resources/css/noty_theme_default.css' rel='stylesheet' />
    <link href='/resources/css/elfinder.min.css' rel='stylesheet' />
    <link href='/resources/css/elfinder.theme.css' rel='stylesheet' />
    <link href='/resources/css/jquery.iphone.toggle.css' rel='stylesheet' />
    <link href='/resources/css/opa-icons.css' rel='stylesheet' />
    <link href='/resources/css/uploadify.css' rel='stylesheet' />


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

<div id="content" class="span10">
<!-- content starts -->
<div>
    <ul class="breadcrumb">
        <li><a href="#">首页</a> <span class="divider">/</span></li>
        <li><a href="#">制定移动广告计划</a></li>
    </ul>
</div>

<div class="row-fluid">
<div class="box span12">
<div class="box-header well">
    <h2>
        <i class="icon-edit"></i>移动广告计划定制
    </h2>
    <div class="box-icon">
        <a href="#" class="btn btn-setting btn-round"><i
                class="icon-cog"></i></a> <a href="#"
                                             class="btn btn-minimize btn-round"><i
            class="icon-chevron-up"></i></a> <a href="#"
                                                class="btn btn-close btn-round"><i class="icon-remove"></i></a>
    </div>
</div>
<div class="box-content">

<form class="form-horizontal" method="post" action="/mobileadplan/add" id="adplanFrom">
<input id="adInstanceDto.adType" name="adInstanceDto.adType" type="hidden" value="1" />
<fieldset>
    <legend id="namePOS">广告基本信息</legend>
    <div class="control-group">
        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>广告名称</label>
        <div class="controls">
            <input class="input-xlarge focused" id="adInstanceDto.adName"
                   name="adInstanceDto.adName" type="text" value="" />
            <span id="adNameTips" class="help-inline" style="display:none"></span>
        </div>

    </div>
    <div class="control-group">
        <label class="control-label">广告描述</label>
        <div class="controls">
            <textarea class="autogrow" id="adInstanceDto.adDesc"
                      name="adInstanceDto.adDesc" style="width:320px;"></textarea>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label"><b style="color: #FF0000;">*</b>起始时间</label>
        <div class="controls">
            <input class="input-small datepicker autogrow"
                   id="adInstanceDto.startTime" name="adInstanceDto.startTime"></input>
            <span id="startTimeTips" class="help-inline" style="display:none"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label"><b style="color: #FF0000;">*</b>截止时间</label>
        <div class="controls">
            <input class="autogrow input-small datepicker"
                   id="adInstanceDto.endTime" name="adInstanceDto.endTime"></input>
            <span id="endTimeTips" class="help-inline" style="display:none"></span>
            <span id="esTimeTips" class="help-inline" style="display:none;color: #BD4247"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">付费方式</label>
        <div class="controls">
            <select
                    id="adInstanceDto.chargeType" name="adInstanceDto.chargeType"
                    >
                <option value="1">按展示付费(CPM)</option>
                <option value="2">按点击付费(CPC)</option>
            </select>
        </div>
    </div>
    
     <div class="control-group">
    <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>计划曝光数</label>
        <div class="controls">
            <input class="input-small focused" id="adInstanceDto.total"
                   name="adInstanceDto.total" type="text" value="" />
            <span id="adTotalTips" class="help-inline" style="display:none"></span>
        </div>
        </div>

    <div class="control-group">
        <label class="control-label"><b style="color: #FF0000;">*</b>预算(￥)</label>
        <div class="controls">

            <div class="input-prepend input-append">
                <span class="add-on">￥</span><input style="width:185px;"
                    id="adInstanceDto.allBudget" name="adInstanceDto.allBudget" size="8" type="text" />
                <!-- <span class="add-on">.00</span> -->
                <span id="allBudgetTips" class="help-inline" style="display:none"></span>
            </div>


        </div>
    </div>

    <div class="control-group">
        <label class="control-label"><b style="color: #FF0000;">*</b>日预算(￥)</label>
        <div class="controls">


            <div class="input-prepend input-append">
                <span class="add-on">￥</span><input style="width:185px;"
                    id="adInstanceDto.dayBudget" name="adInstanceDto.dayBudget" size="8" type="text" />
                <!-- <span class="add-on">.00</span> -->
                <span id="dayBudgetTips" class="help-inline" style="display:none"></span>
            </div>
        </div>
    </div>
    <!-- 渠道选择 不需要设置直接是 3 -->
       <!-- 渠道选择 -->
        <div class="control-group">
        <label class="control-label"><b style="color: #FF0000;">*</b>渠道选择：</label>
        <div class="controls">
            <div class="input-prepend input-append">
                <input  id="adInstanceDtoChannel1" name="adInstanceDto.channel"  value="1"  size="8" type="radio" checked="checked"/>tanx
                <input  id="adInstanceDtoChannel2" name="adInstanceDto.channel"  value="3"  size="8" type="radio" />芒果
            </div>
        </div>
    </div>  
    <div class="control-group" id="max_cpm_bidpriceDiv" >
        <label class="control-label">最高出价(分/cpm)：</label>
        <div class="controls">
            <div class="input-prepend input-append">
                <input  id="max_cpm_bidprice" name="adInstanceDto.maxCpmBidprice"  value=""  type="text" checked="checked"/>
                 <span id="max_cpm_bidpriceTips" class="help-inline" style="display:none"></span>
            </div>
        </div>
    </div>
</fieldset>

<fieldset>
<legend> 时间定向 </legend>

    <input type="hidden" id="putHours" name="putHours"/>

    <table id="timeSel">

        <tr>
            <td><input type="checkbox" id="cWork" value="cWork" />工作日</td>
            <td>开始时间：</td>
            <td>
                <select data-rel="chosen" id="workDayStart" name="workDayStart" style="width: 100px">
                    <option value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="21">21</option>
                    <option value="22">22</option>
                    <option value="23">23</option>
                </select>
            </td>
            <td>结束时间：</td>
            <td>
                <select data-rel="chosen" onchange="timeValidater(this.id)" id="workDayEnd" name="workDayEnd"  style="width: 100px">
                    <option value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="21">21</option>
                    <option value="22">22</option>
                    <option value="23" selected>23</option>
                </select>
            </td>

        </tr>
        <tr>
            <td><input type="checkbox" id="cWeekend" value="cWeekend"/>周末</td>
            <td>开始时间：</td>
            <td>
                <select data-rel="chosen" id="weekendDayStart" name="weekendDayStart"  style="width: 100px">
                    <option value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="21">21</option>
                    <option value="22">22</option>
                    <option value="23">23</option>
                </select>
            </td>
            <td>结束时间：</td>
            <td>
                <select data-rel="chosen" onchange="timeValidater(this.id)" id="weekendDayEnd" name="weekendDayEnd"  style="width: 100px">
                    <option value="0">0</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                    <option value="11">11</option>
                    <option value="12">12</option>
                    <option value="13">13</option>
                    <option value="14">14</option>
                    <option value="15">15</option>
                    <option value="16">16</option>
                    <option value="17">17</option>
                    <option value="18">18</option>
                    <option value="19">19</option>
                    <option value="20">20</option>
                    <option value="21">21</option>
                    <option value="22">22</option>
                    <option value="23" selected>23</option>
                </select>
            </td>

        </tr>

    </table>

    <br />

</fieldset>


<fieldset>
    <legend id="DocPos"> 投放策略 </legend>

    <div class="control-group">
        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>时间段</label>
        <div class="controls">
            每 <input class="input-xlarge focused"
                     id="adTimeFrequencyDto.putIntervalNum" name="adTimeFrequencyDto.putIntervalNum"
                     type="text" value="" style="width: 30px" /> <select
                id="adTimeFrequencyDto.putIntervalUnit" name="adTimeFrequencyDto.putIntervalUnit"
                style="width: 80px">
            <option value="W">周</option>
            <option value="D">天</option>
            <option value="H">小时</option>
            <option value="M">分钟</option>
            <option value="S">秒</option>
        </select> 展现一次
            <span id="putIntervalNumTips" class="help-inline" style="display:none"></span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>每日最高投放量</label>
        <div class="controls">

            <input class="input-xlarge focused" id="adTimeFrequencyDto.dayLimit"
                   name="adTimeFrequencyDto.dayLimit"" type="text" value=""
            style="width: 50px" />
            <span id="dayLimitTips" class="help-inline" style="display:none"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>独立访客展现次数</label>
        <div class="controls">
			
			<!-- 
             <select id="adUserFrequencyDto.timeUnit" name="adUserFrequencyDto.timeUnit"
                      style="width: 80px">
            <option value="W">周</option>
            <option value="D">天</option>
            <option value="H">小时</option>
            <option value="M">分钟</option>
            <option value="S">秒</option>
        </select>  
         -->
        <input id="adUserFrequencyDto.timeUnit" name="adUserFrequencyDto.timeUnit" value="D"  type="hidden"/>
        每天最多展现 <input class="input-xlarge focused"
                              id="adUserFrequencyDto.uidImpressNum" name="adUserFrequencyDto.uidImpressNum"
                              type="text" value="" style="width: 30px" /> 次
            <span id="uidImpressNumTips" class="help-inline" style="display:none"></span>
        </div>
    </div>
</fieldset>

<fieldset>
    <legend> 广告物料 </legend>
    <div class="" style="display:none" id="selectedDiv">
        <input type="hidden" name="adMaterialIds" id="adMaterialIds"/>
        <label class="">已选广告物料：</label>
        <div class="row-fluid sortable">
            <div class="box span6">
                <table class="table">
                    <tbody id="selectedMaterial">
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="row-fluid">
        <button type="button" id="addMaterialBtn" class="btn btn-primary">新建广告物料</button>&nbsp;
        <button type="button" id="selectMaterialBtn" class="btn btn-primary">从广告物料库选择</button>
    </div>
    <div class="row-fluid" id="selectMaterialDiv" style="display:none;">
        <div class="box span8" style="margin-left: 105px; margin-top: 2px;">
            <div class="box-header well">
                <h3><i></i> 广告物料列表</h3>
            </div>
            <div class="row-fluid">
                <div class="box-content">
                    <table class="table table-bordered" id="materialTable">
                        <thead>
                        <tr>
                            <th style="width:25px;"></th>
                            <th>广告物料名称</th>
                            <th>类型</th>
                            <th>规格</th>
                            <th>预览</th>
                        </tr>
                        </thead>
                        <tbody id="materialBody">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</fieldset>
<br/>

<fieldset>
    <legend> 广告分类 </legend>
    <div class="" style="" id="adCategory_selectedDiv">
        <input type="hidden" name="adCategoryCodes" id="adCategoryCodes"/>
        <div class="row-fluid sortable">
            <div class="box span6">
                <table>
                    <thead>
                    <tr>
                        <th width="45%">从以下分类中选择（双击可选大类）</th>
                        <th  width="10%" align="center"></th>
                        <th  width="45%"><a style="cursor: pointer;" id="adCategory_selAll">全选</a>/<a id="adCategory_cancelSelAll" style="cursor: pointer;">全不选</a></th>
                    </tr>
                    </thead>
                    <tbody id="">
                    <tr>
                        <td>
                            <select id="adCategory_mediaCateSel"  class="adCategory_mediaCateOp"  multiple="multiple"  style="height:200px;" >
                                <c:if test="${not empty adCategoryDtoLeveOneList}">
                                    <c:forEach var="mcLeveOne" items="${adCategoryDtoLeveOneList}">
                                        <option value="${mcLeveOne.id}" >
                                            ${mcLeveOne.name}
                                        </option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </td>
                        <td style="text-align: center;">
                           <img src="/resources/img/star_06.png"/>
                        </td>
                        <td>
                            <div id="adCategory_mediaCateLeveTwoDiv" style="border: 1px solid  rgb(96,96,96);height: 200px;width: 95%;">

                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            已选择广告分类
                            <div style="height: 120px;overflow: auto; border: 1px solid  rgb(96,96,96);padding: 5px;">
                                <div id="adCategory_mediaCateSelectedDiv" style="height:100px;width: 95%;" >

                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</fieldset>
<br/>

<fieldset>
    <legend id="adTypePOS"> 广告类型 </legend>
    <div class="" style="" id="selectedDiv">
        <input type="hidden" name="mediaCategoryCodes" id="mediaCategoryCodes"/>
        <div class="row-fluid sortable">
                <c:if test="${not empty adTypes}">
                        <c:forEach var="adType" items="${adTypes}">
                            <input name="adType.id" type="radio" value="${adType.id}" />${adType.description}
                        </c:forEach>
                </c:if>
        </div>
    </div>
</fieldset>
<br/>

<fieldset>
    <legend id="appTypePOS"> App 分类 </legend>
        <input type="hidden" name="adAppIds" id="adAppIds"/>
    <div class="" style="" id="selectedDiv">
        <div class="row-fluid sortable">
            <div class="box span6">
                <table>
                    <thead>
                    <tr>
                        <th width="45%">从以下分类中选择</th>
                        <th  width="10%" align="center"></th>
                        <th  width="45%"></th>
                    </tr>
                    </thead>
                    <tbody id="">
                    <tr>
                        <td>
                            <select id="app_f"  multiple="multiple"  style="height:200px;" ondblclick="moveOption(true, 'app_f', 'app_t', 'adAppIds')">
                                <c:if test="${not empty adApps}">
                                    <c:forEach var="adApp" items="${adApps}">
                                        <option value="${adApp.id}" >
                                            ${adApp.category}
                                        </option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </td>
                        <td style="text-align: center;">
                            <input type="button" class="btn" value="添加" onclick="moveOption(true,'app_f', 'app_t' , 'adAppIds')"><br><br>
			                <input type="button" class="btn" value="全选" onclick="moveAllOption(true,'app_f', 'app_t' , 'adAppIds')"><br><br>
			                <input type="button" class="btn" value="删除" onclick="moveOption(false,'app_t', 'app_f' , 'adAppIds')"><br><br>
			                <input type="button" class="btn" value="全删" onclick="moveAllOption(false,'app_t', 'app_f' , 'adAppIds')">
                        </td>
                        <td>
                            <select id="app_t"  multiple="multiple"  style="height:200px;" ondblclick="moveOption(false, 'app_t', 'app_f', 'adAppIds')">
                            </select>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</fieldset>
<br/>

<fieldset>
    <legend id="adClickTypePOS">广告点击类型</legend>
<!-- 渠道选择 -->
        <div class="control-group">
            <div class="input-prepend input-append" style="height:25px;margin-bottom:5px;">
                  <c:if test="${not empty adClicks}">
                        <c:forEach var="adClick" items="${adClicks}" varStatus="sts">
                            <input name="adClick.id" type="radio" onclick="setDeny(this)" value="${adClick.id}" id="adClickId${sts.index}" />${adClick.type}
                        </c:forEach>
                </c:if>
                <span id="dayBudgetTips" class="help-inline" style="display:none"></span>
        </div>
        <div class="controls" id="adClickTarget" style="margin-left: 0px;padding: 5px">
        <span class="add-on" id="adClickTargetSpan">输入邮件地址：</span>
            <input class="input-xlarge focused" id="adClick.target"
                   name="adClick.target" type="text" value="" />
            <span id="adClickTargetTips" class="help-inline" ></span>
        </div>
    </div>
</fieldset>
<br />

<fieldset>
    <legend id="adDeviceTypesPOS">设备类型</legend>
<!-- 渠道选择 -->
        <div class="control-group">

            <div class="input-prepend input-append">
              <c:if test="${not empty adDevices}">
                        <c:forEach var="adDevice" items="${adDevices}">
                     <c:choose>
	                        <c:when test="${adDevice.id==4}">
                            	<input name="adDeviceIds" type="checkbox" disabled="disabled"  value="${adDevice.id}" />${adDevice.type}
	                        </c:when>
	                            <c:when test="${adDevice.id==6}">
                            	<input name="adDeviceIds" type="checkbox" disabled="disabled"  value="${adDevice.id}" />${adDevice.type}
	                        </c:when>
	                        <c:otherwise>
                            	<input name="adDeviceIds" type="checkbox"  value="${adDevice.id}" />${adDevice.type}
	                        </c:otherwise>
                        </c:choose>
                        </c:forEach>
                </c:if>
               
                <span id="dayBudgetTips" class="help-inline" style="display:none"></span>
        </div>
    </div>
</fieldset>
<br />

<fieldset>

    <legend>第三方统计代码</legend>
    <textarea style="resize: none; width: 60%; height: 180px;"
              id="adInstanceDto.ad3statCode" name="adInstanceDto.ad3statCode"></textarea>
    <br />
    <br />
</fieldset>

<fieldset>

    <legend>第三方统计代码_辅助</legend>
    <textarea style="resize: none; width: 60%; height: 180px;"
              id="adInstanceDto.ad3statCodeSub" name="adInstanceDto.ad3statCodeSub"></textarea>
    <br />
    <br />
</fieldset>

<div class="form-actions">
    <button type="button" class="btn" id="adplanadd">保存</button>
    <button class="btn"  type="reset">取消</button>
</div>

</form>

</div>
</div>
</div>

</div>
<!--/row-->
</div>
<!--/#content.span10-->
</div>
<!--/fluid-row-->


<div class="modal hide" id="addMaterialModal" >
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>新建广告物料</h3>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <div class="box-content">
                <form class="form-horizontal" id="materialForm" method="post" action="/material/addNew">

                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal hide" id="editMaterialModal" >
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>修改广告物料</h3>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <div class="box-content">
                <form class="form-horizontal" id="materialEditForm" method="post" action="/material/addNew">
                </form>
            </div>
        </div>
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
<!--/.fluid-container-->

<%@include file="../inc/footer.jsp"%>
</body>
<script id="tmpl" type="text/x-jsrender">
    <tr>
        <td>
            <input type="hidden" name="materialId" value="{{: id}}"/>
            <span>{{: name}}</span>
            {{if mType == 1}}
            <div><img style="width:50px;height:50px;" src="{{: linkUrl}}"/> </div>
            {{else mType == 2}}
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0">
                <param name="movie" value="0.swf">
                <param name="quality" value="high">
                <embed src="{{: linkUrl}}" style="width:50px;height:50px;" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"/>
            </object>
            {{else}}
            <div>{{: richText}}</div>
            {{/if}}
        </td>
        <td class="center">{{: type}}</td>
        <td class="center">{{: materialValue}}</td>
        <td class="center">
            <a href="javascript:;" id="{{: id}}" class="edit">修改</a>&nbsp;<a href="javascript:;" class="del">移除</a>
        </td>
    </tr>
</script>
<script id="addMeterialTmpl" type="text/x-jsrender">
    <tr>
        <td>
            <input type="hidden" name="materialId" value="{{: id}}"/>
            <span>{{: materialName}}</span>
            {{if mtype == 1}}
            <div><img style="width:50px;height:50px;" src="{{: linkUrl}}"/> </div>
            {{else mtype == 2}}
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0">
                <param name="movie" value="0.swf">
                <param name="quality" value="high">
                <embed src="{{: linkUrl}}" style="width:50px;height:50px;" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash"/>
            </object>
            {{else}}
            <div>{{: richText}}</div>
            {{/if}}
        </td>
        <td class="center">
            {{if mtype == 1}}图片
            {{else mtype == 2}}flash
            {{else}}富媒体
            {{/if}}
        </td>
        <td class="center">
            {{: {material_size_value}}
        </td>
        <td class="center">
            <a href="javascript:;" id="{{: id}}" class="edit">修改</a>&nbsp;<a href="javascript:;" class="del">移除</a>
        </td>
    </tr>
</script>
<script type="text/javascript" src="/resources/js/common/common.js"></script>
<script type="text/javascript" src="/resources/js/jquey.form.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.json.min.js"></script>
<script type="text/javascript" src="/resources/js/jsrender.min.js"></script>
<script>

    <!--当输入内容获取焦点是 事件   周晓明 -->
    $(document).ready(function(){
        $("#adInstanceDto\\.startTime").focus(function(){
            $("#startTimeTips").parent().parent().removeClass("error");
            $("#startTimeTips").hide();
            $("#esTimeTips").parent().parent().removeClass("error");
            $("#esTimeTips").hide();
        });
        $("#adInstanceDto\\.endTime").focus(function(){
            $("#endTimeTips").parent().parent().removeClass("error");
            $("#endTimeTips").hide();
            $("#esTimeTips").parent().parent().removeClass("error");
            $("#esTimeTips").hide();
        });
        //广告名称
        $("#adInstanceDto\\.adName").focus(function(){
            $("#adNameTips").parent().parent().removeClass("error");
            $("#adNameTips").hide();
        });
        //时间段
        $("#adTimeFrequencyDto\\.putIntervalNum").focus(function(){
            $("#putIntervalNumTips").parent().parent().removeClass("error");
            $("#putIntervalNumTips").hide();
        });
        //每日最高投放量
        $("#adTimeFrequencyDto\\.dayLimit").focus(function(){
            $("#dayLimitTips").parent().parent().removeClass("error");
            $("#dayLimitTips").hide();
        });

        //独立访客展现次数
        $("#adUserFrequencyDto\\.uidImpressNum").focus(function(){
            $("#uidImpressNumTips").parent().parent().removeClass("error");
            $("#uidImpressNumTips").hide();
        });

        //预算
        $("#adInstanceDto\\.allBudget").focus(function(){
            $("#allBudgetTips").parent().parent().parent().removeClass("error");
            $("#allBudgetTips").hide();
        });

        //日预算
        $("#adInstanceDto\\.dayBudget").focus(function(){
            $("#dayBudgetTips").parent().parent().parent().removeClass("error");
            $("#dayBudgetTips").hide();
        });
        
        //最高出价
        $("#max_cpm_bidprice").focus(function(){
            $("#max_cpm_bidpriceTips").parent().parent().parent().removeClass("error");
            $("#max_cpm_bidpriceTips").hide();
        });
    });
    
    function setDeny(obj) {
    	 var radioValue = $(obj).val();
         $('input[name="adDeviceIds"]').each(function () {
             $(this).attr("disabled", false);
             $(this).attr("checked", false);
             $(this).parent().removeClass("checked");
         });
          $("input[name$='adDeviceIds'][value$='4']").attr('disabled',true);
          $("input[name$='adDeviceIds'][value$='6']").attr('disabled',true);
          if(radioValue==2) {
              $("input[name$='adDeviceIds'][value$='1']").attr('disabled',true);
              $("input[name$='adDeviceIds'][value$='3']").attr('disabled',true);
          } else if (radioValue==4||radioValue==5) {
             $("input[name$='adDeviceIds'][value$='3']").attr('disabled',true);
             $("input[name$='adDeviceIds'][value$='5']").attr('disabled',true);
         } else if(radioValue==6) {
        	 $("input[name$='adDeviceIds'][value$='2']").attr('disabled',true);
             $("input[name$='adDeviceIds'][value$='5']").attr('disabled',true);
         }
	}
</script>
<script>
    <!--验证输入框的内容是否合法，是否为空    周晓明-->
    $(document).ready(function(){
        //广告名称
        $("#adInstanceDto\\.adName").focusout(function(){
            var $adName = $("#adInstanceDto\\.adName").val();
            if($.trim($adName) == ''){
                $("#adNameTips").text("广告名称不能为空");
                $("#adNameTips").parent().parent().addClass("error");
                $("#adNameTips").show();
            }
        });
        regx = /^(\+|-)?\d+$/; //判断是否是正整数
        //时间段
        $("#adTimeFrequencyDto\\.putIntervalNum").focusout(function(){
            var $putIntervalNum = $("#adTimeFrequencyDto\\.putIntervalNum").val();
            if($.trim($putIntervalNum) == ''){
                $("#putIntervalNumTips").text("时间段不能为空");
                $("#putIntervalNumTips").parent().parent().addClass("error");
                $("#putIntervalNumTips").show();
            }else if(!regx.test($.trim($putIntervalNum))){
                $("#putIntervalNumTips").text("时间段输入不合法");
                $("#putIntervalNumTips").parent().parent().addClass("error");
                $("#putIntervalNumTips").show();
            }
        });
        //每日最高投放量
        $("#adTimeFrequencyDto\\.dayLimit").focusout(function(){
            var $dayLimit = $("#adTimeFrequencyDto\\.dayLimit").val();
            if($.trim($dayLimit) == ''){
                $("#dayLimitTips").text("每日最高投放量不能为空");
                $("#dayLimitTips").parent().parent().addClass("error");
                $("#dayLimitTips").show();
            }else if(!regx.test($.trim($dayLimit))){
                $("#dayLimitTips").text("每日最高投放量输入不合法");
                $("#dayLimitTips").parent().parent().addClass("error");
                $("#dayLimitTips").show();
            }
        });

        //独立访客展现次数
        $("#adUserFrequencyDto\\.uidImpressNum").focusout(function(){
            var $uidImpressNum = $("#adUserFrequencyDto\\.uidImpressNum").val();
            if($.trim($uidImpressNum) == ''){
                $("#uidImpressNumTips").text("独立访客展现次数不能为空");
                $("#uidImpressNumTips").parent().parent().addClass("error");
                $("#uidImpressNumTips").show();
            }else if (!regx.test($.trim($uidImpressNum))){
                $("#uidImpressNumTips").text("独立访客展现次数输入不合法");
                $("#uidImpressNumTips").parent().parent().addClass("error");
                $("#uidImpressNumTips").show();
            }
        });
        //预算
        regx_price = /[0-9]+/;

        $("#adInstanceDto\\.allBudget").focusout(function(){
            var $allBudget = $("#adInstanceDto\\.allBudget").val();
            if($.trim($allBudget) == ''){
                $("#allBudgetTips").text("预算不能为空");
                $("#allBudgetTips").parent().parent().parent().addClass("error");
                $("#allBudgetTips").show();
            }else if (!regx_price.test($.trim($allBudget))){
                $("#allBudgetTips").text("预算输入不合法");
                $("#allBudgetTips").parent().parent().parent().addClass("error");
                $("#allBudgetTips").show();
            }
        });

        //日预算
        $("#adInstanceDto\\.dayBudget").focusout(function(){
            var $dayBudget = $("#adInstanceDto\\.dayBudget").val();
            if($.trim($dayBudget) == ''){
                $("#dayBudgetTips").text("日预算不能为空");
                $("#dayBudgetTips").parent().parent().parent().addClass("error");
                $("#dayBudgetTips").show();
            }else if (!regx_price.test($.trim($dayBudget))){
                $("#dayBudgetTips").text("日预算输入不合法");
                $("#dayBudgetTips").parent().parent().parent().addClass("error");
                $("#dayBudgetTips").show();
            }
        });

        //日预算
        $("#max_cpm_bidprice").focusout(function(){
            var $dayBudget = $("#max_cpm_bidprice").val();
            if($.trim($dayBudget) == ''){
              
            }else if (!regx_price.test($.trim($dayBudget))){
                $("#max_cpm_bidpriceTips").text("最高出价不合法");
                $("#max_cpm_bidpriceTips").parent().parent().parent().addClass("error");
                $("#max_cpm_bidpriceTips").show();
            }
        });
        
    });

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
    <!--在保存广告计划时，要判断所填写的内容是否合法    周晓明 -->
    function checkInput_adplan(){
        var flag = true;
        regx = /^(\+|-)?\d+$/; //判断是否是正整数
        regx_price = /[0-9]+/;
        var $adName = $("#adInstanceDto\\.adName").val();
        var $startTime = $("#adInstanceDto\\.startTime").val();
        var $endTime = $("#adInstanceDto\\.endTime").val();
        var $putIntervalNum = $("#adTimeFrequencyDto\\.putIntervalNum").val();
        var $dayLimit = $("#adTimeFrequencyDto\\.dayLimit").val();
        var $uidImpressNum = $("#adUserFrequencyDto\\.uidImpressNum").val();
        var $dayBudget = $("#adInstanceDto\\.dayBudget").val();
        var $allBudget = $("#adInstanceDto\\.allBudget").val();
        
        var channel = $("#adInstanceDtoChannel").val();
        var $max_cpm_bidprice = $("#max_cpm_bidprice").val();
        
        if($.trim($startTime) != ''){
            $("#startTimeTips").parent().parent().removeClass("error");
            $("#startTimeTips").hide();
        }
        if($.trim($endTime) != ''){
            $("#endTimeTips").parent().parent().removeClass("error");
            $("#endTimeTips").hide();
        }
        if(!($.trim($startTime) > $.trim($endTime))){
            $("#esTimeTips").parent().parent().removeClass("error");
            $("#esTimeTips").hide();
        }
        if($.trim($adName) == ''){
            $("#adNameTips").text("广告名称不能为空");
            $("#adNameTips").parent().parent().addClass("error");
            $("#adNameTips").show();
            moveHtml("namePOS");
            flag = false;
        }else if($.trim($startTime) == ''){
            $("#startTimeTips").text("起始时间不能为空");
            $("#startTimeTips").parent().parent().addClass("error");
            $("#startTimeTips").show();
            moveHtml("namePOS");
            flag = false;
        }else if($.trim($endTime) == ''){
            $("#endTimeTips").text("结束时间不能为空");
            $("#endTimeTips").parent().parent().addClass("error");
            $("#endTimeTips").show();
            moveHtml("namePOS");
            flag = false;
        }else if($.trim($startTime) != '' && !$.trim($endTime) == '' && $endTime < $startTime){
            $("#esTimeTips").text("结束时间不能小于起始时间");
            $("#esTimeTips").parent().parent().addClass("error");
            $("#esTimeTips").show();
            moveHtml("namePOS");
            flag = false;
        }else if($.trim($putIntervalNum) == ''){
            $("#putIntervalNumTips").text("时间段不能为空");
            $("#putIntervalNumTips").parent().parent().addClass("error");
            $("#putIntervalNumTips").show();
            moveHtml("DocPos");
            flag = false;
        }else if(!regx.test($.trim($putIntervalNum))){
            $("#putIntervalNumTips").text("时间段输入不合法");
            $("#putIntervalNumTips").parent().parent().addClass("error");
            $("#putIntervalNumTips").show();
            moveHtml("DocPos");
            flag = false;
        }else if($.trim($dayLimit) == ''){
            $("#dayLimitTips").text("每日最高投放量不能为空");
            $("#dayLimitTips").parent().parent().addClass("error");
            $("#dayLimitTips").show();
            moveHtml("DocPos");
            flag = false;
        }else if(!regx.test($.trim($dayLimit))){
            $("#dayLimitTips").text("每日最高投放量输入不合法");
            $("#dayLimitTips").parent().parent().addClass("error");
            $("#dayLimitTips").show();
            moveHtml("DocPos");
            flag = false;
        }else if($.trim($uidImpressNum) == ''){
            $("#uidImpressNumTips").text("独立访客展现次数不能为空");
            $("#uidImpressNumTips").parent().parent().addClass("error");
            $("#uidImpressNumTips").show();
            moveHtml("DocPos");
            flag = false;
        }else if (!regx.test($.trim($uidImpressNum))){
            $("#uidImpressNumTips").text("独立访客展现次数输入不合法");
            $("#uidImpressNumTips").parent().parent().addClass("error");
            $("#uidImpressNumTips").show();
            moveHtml("DocPos");
            flag = false;
        }else if($.trim($allBudget) == ''){
            $("#allBudgetTips").text("预算不能为空");
            $("#allBudgetTips").parent().parent().parent().addClass("error");
            $("#allBudgetTips").show();
            moveHtml("namePOS");
            flag = false;
        }else if(!regx_price.test($.trim($allBudget))){
            $("#allBudgetTips").text("预算输入不合法");
            $("#allBudgetTips").parent().parent().parent().addClass("error");
            $("#allBudgetTips").show();
            moveHtml("namePOS");
            flag = false;
        }else if($.trim($dayBudget) == ''){
            $("#dayBudgetTips").text("日预算不能为空");
            $("#dayBudgetTips").parent().parent().parent().addClass("error");
            $("#dayBudgetTips").show();
            moveHtml("namePOS");
            flag = false;
        }else if(!regx_price.test($.trim($dayBudget))){
            $("#dayBudgetTips").text("日预算输入不合法");
            $("#dayBudgetTips").parent().parent().parent().addClass("error");
            $("#dayBudgetTips").show();
            moveHtml("namePOS");
            flag = false;
        }else if (channel=="1"&&$.trim($max_cpm_bidprice)!=''&&!regx_price.test($.trim($max_cpm_bidprice))){
            $("#max_cpm_bidpriceTips").text("最高出价不合法");
            $("#max_cpm_bidpriceTips").parent().parent().parent().addClass("error");
            $("#max_cpm_bidpriceTips").show();
            moveHtml("namePOS");
            flag = false;
        }

        return flag;
    }
</script>
<script>

function timeValidater(id) {
    var objEnd = document.getElementById(id);
    var endId = id.substr(0,id.length-3) + "Start";
    var objStart = document.getElementById(endId + "");
    // 比较数值的时候需要转换一下
    if (parseInt(objEnd.value) < parseInt(objStart.value)) {
        alert("结束时间要比开始时间大");
        objEnd.selectedIndex = objStart.selectedIndex;
        $("#" + id + "_chzn > a:nth-child(1) > span:nth-child(1)").text(objStart.selectedIndex);
        return;
    }
}

function getTime() {
    // 数据格式 workDay:true$start:1#end:2;weekendDay:true$start:1#end:2;

    var timeStr = "";
    timeStr += "workDay:";
    // 1. 获取工作日的状态
    var cWork = document.getElementById("cWork");
    if(cWork.checked == true) {
        timeStr += "true";
        //alert("工作日被选中");
        // 如果工作日的结束时间为 0 提示用户要选中时间
        var timeWorkEnd = "workDayEnd";
        var endTime = document.getElementById(timeWorkEnd).value;
        if(parseInt(endTime) == 0) {
            alert("请选择工作日的结束时间！");
            document.getElementById(timeWorkEnd).focus();
            return "error";
        } else {
            // 验证是不是符合大小顺序
            timeValidater(timeWorkEnd);
            var starTime = document.getElementById("workDayStart").value;
            var endTime = document.getElementById("workDayEnd").value;
            timeStr += "%start:";
            timeStr += starTime;
            timeStr += "#end:";
            timeStr += endTime;
        }
    } else {
        timeStr += "false";
    }
    timeStr +=";weekendDay:";
    // 数据格式 workDay:true%start:1#end:2;weekendDay:true%start:1#end:2;
    // 2. 获取周末的状态
    var cWeekend = document.getElementById("cWeekend");
    if(cWeekend.checked == true) {
        timeStr += "true";
        //alert("周末被选中");
        // 如果周末的结束时间为 0 提示用户要选中时间
        var timeWorkEnd = "weekendDayEnd";
        var endTime = document.getElementById(timeWorkEnd).value;
        if(parseInt(endTime) == 0) {
            alert("请选择周末的结束时间！");
            document.getElementById(timeWorkEnd).focus();
            return "error";
        } else {
            // 验证是不是符合大小顺序
            timeValidater(timeWorkEnd);
            var starTime = document.getElementById("weekendDayStart").value;
            var endTime = document.getElementById("weekendDayEnd").value;
            timeStr += "%start:";
            timeStr += starTime;
            timeStr += "#end:";
            timeStr += endTime;
        }
    } else {
        timeStr += "false";
    }
    return timeStr;
}

// 记录当前被点击的 radioId
var radioIdVal;

$(document)
        .ready(
        function() {
        	initAdCate();//初始化广告类型	        	
            //弹出层不用 框架自带的 出现，隐藏。我们手动设置
            $("#crowdNewModal").hide();

            $("#crowdSel").click(function() {
                $("#crowdSBTab").toggle();

            });

            $('.datatable')
                    .dataTable(
                    {
                        "sDom" : "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
                        "sPaginationType" : "bootstrap",
                        "oLanguage" : {
                            "sProcessing" : "正在加载中......",
                            "sLengthMenu" : "每页显示 _MENU_ 条记录",
                            "sZeroRecords" : "对不起，查询不到相关数据！",
                            "sEmptyTable" : "表中无数据存在！",
                            "sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                            "sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
                            "sSearch" : "搜索",
                            "oPaginate" : {
                                "sFirst" : "首页",
                                "sPrevious" : "上一页",
                                "sNext" : "下一页",
                                "sLast" : "末页"
                            }
                        }
                    });

            $(".datepicker").datepicker(
                    {
                        numberOfMonths : 1,//显示几个月
                        showButtonPanel : true,//是否显示按钮面板
                        dateFormat : 'yy-mm-dd',//日期格式
                        clearText : "清除",//清除日期的按钮名称
                        closeText : "关闭",//关闭选择框的按钮名称
                        yearSuffix : '年', //年的后缀,
                        currentText : '今天',
                        showMonthAfterYear : true,//是否把月放在年的后面
                        monthNames : [ '一月', '二月', '三月', '四月',
                            '五月', '六月', '七月', '八月', '九月', '十月',
                            '十一月', '十二月' ],
                        dayNames : [ '星期日', '星期一', '星期二', '星期三',
                            '星期四', '星期五', '星期六' ],
                        dayNamesShort : [ '周日', '周一', '周二', '周三',
                            '周四', '周五', '周六' ],
                        dayNamesMin : [ '日', '一', '二', '三', '四',
                            '五', '六' ]
                    });

        }).on("click", "#adplanadd", function() {
        	var putHours = getTime();
        	if(putHours=="error" ) {
        		return;
        	}
        	
		    var materialIds = getMaterialIds();
			$("#putHours").val(putHours);
            $("#adMaterialIds").val(materialIds);
            if(checkInput_adplan()){
            
            	// 验证广告行业分类是不是为空
//             	var adIndustryIds = $("#adIndustryIds").val();
//             	if (adIndustryIds==null||adIndustryIds=="") {
//     				alert("广告行业为空，请选择！");
//     				moveHtml("adCategorryPOS");
//     				return;
//             	}
            	
            	// 验证广告类型有没有被选择
       		   var adType= $('input[name="adType\\.id"]:checked').val();
               if (adType==null || adType=="") {
                       alert("广告类型没有选择，请选择！");
                       moveHtml("adTypePOS");
                       return;
               }
            	
            	// 验证App 分类
             	var adAppIds = $("#adAppIds").val();
            	if (adAppIds==null||adAppIds=="") {
    				alert("App分类为空，请选择！");
    				moveHtml("appTypePOS");
    				return;
            	}
            	
            	// 验证设备类型
            	if (!getDeviceTypes() ) {
            		alert("请选择设备类型！");
            		moveHtml("adDeviceTypesPOS");
            		return ;
            	}
            	
            	// 验证点击类型 adClick.id
            	var adClick= $('input[name="adClick\\.id"]:checked').val();
               if (adClick==null || adClick=="") {
                       alert("广告点击类型没有选择，请选择！");
                       moveHtml("adClickTypePOS");
                       return;
               }
               
               var adCategoryCodes = adCategory_getMediaCategoryCodes();
               $("#adCategoryCodes").val(adCategoryCodes);
            	$("#adplanFrom").submit();
            }

        }).on("click","#addMaterialBtn",function(e){
            $.ajax({
                url:'/material/add-template/',
                type:'get',
                success: function(result){
                    $("#materialForm").empty();
                    $("#materialEditForm").empty();
                    $("#materialForm").html(result);
                    bindEvent();
                }
            });
            $("#addMaterialModal").modal('show');
        }).on("click","#selectMaterialBtn",function(){

            var display = $("#selectMaterialDiv").css("display");
            if (display == 'block'){
                $("#selectMaterialDiv").hide();
                return;
            }
            $.ajax({
                url:"/material/table-template",
                data:{},
                async:true,
                type:'GET',
                success:function(data){
                    $("#materialBody").empty();
                    $("#materialBody").html(data);
                    $("#selectMaterialDiv").show();

                    $('#materialTable').dataTable({
                        "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
                        "sPaginationType": "bootstrap",
                        "bRetrieve": true,
                        "oLanguage": {
                            "sProcessing": "正在加载中......",
                            "sLengthMenu": "每页显示 _MENU_ 条记录",
                            "sZeroRecords": "对不起，查询不到相关数据！",
                            "sEmptyTable": "表中无数据存在！",
                            "sInfo": '<button type="button" class="btn btn-primary" id="selectBtn">确定</button>&nbsp;&nbsp;<button type="button" class="btn" id="cancelSelectBtn">取消</button>',
                            "sInfoEmpty":"当前显示 0 到 0 条，共 0 条记录",
                            "sInfoFiltered": "",
                            "sSearch": "搜索",
                            "oPaginate": {
                                "sFirst": "首页",
                                "sPrevious": "上一页",
                                "sNext": "下一页",
                                "sLast": "末页"
                            }
                        }
                    });
                }
            });
        }).on("click","#selectBtn",function(){
            var selects = $("input[name='materialCheck']:checked");
            if (selects.length == 0){
                return;
            }

            $("input[name='materialCheck']:checked").each(function(){
                var _this = $(this);
                var id = _this.val();

                var selected = false;
                // 检查物料是否已选
                $("#selectedMaterial tr input[name='materialId']").each(function(){
                    var value = $(this).val();
                    if (value == id){
                        selected = true;
                    }
                });
                if (!selected){
                    var parentTR = _this.parents("tr");
                    var linkUrl = parentTR.find("input[name='linkUrl']").val();
                    var mType = parentTR.find("input[name='mType']").val();
                    var materialValue = parentTR.find("td").eq(3).text();
                    var name = parentTR.find("td").eq(1).text();
                    var type = parentTR.find("td").eq(2).text();

                    var material = new Material(id, linkUrl, type, materialValue, name, type, mType);
                    var html = $("#tmpl").render(jQuery.evalJSON(jQuery.toJSON(material)));
                    $("#selectedMaterial").append(html);
                    $("#selectedDiv").show();
                    _this.removeAttr("checked");
                }
            });

            $("#selectMaterialDiv").hide();

        }).on("click","#cancelSelectBtn",function(){
            $("#selectMaterialDiv").hide();
        }).on("click",".del",function(){
            $(this).parents("tr").remove();
            if ($("#selectedDiv table tr").length == 0){
                $("#selectedDiv").hide();
            }
        }).on("click",".edit",function(){
            var id = $(this).attr("id");
            $.ajax({
                url:'/material/edit-template/'+id,
                type:'get',
                success: function(result){
                    $("#materialEditForm").empty();
                    $("#materialForm").empty();
                    $("#materialEditForm").html(result);
                    bindEvent();
                }
            });
            $("#editMaterialModal").modal('show');
        }).on("click","input[name='adClick\\.id']",function(){
            if(radioIdVal==null || radioIdVal=="") {
            	radioIdVal = $(this).attr("id");
            } else {
                if(radioIdVal != $(this).attr("id")) {
                	radioIdVal = $(this).attr("id");
                    $("#adClick\\.target").val("");
                }
            }
            if($(this).val()==4||$(this).val()==5) {
                $("#adClickTargetSpan").text("请输入手机号：");
            } else if($(this).val()==1) {
                $("#adClickTargetSpan").text("请输入邮件地址：");
            } else if($(this).val()==2) {
                $("#adClickTargetSpan").text("请输入App地址：");
            } else if($(this).val()==3) {
                $("#adClickTargetSpan").text("请输入网页地址：");
            } else if($(this).val()==6) {
                $("#adClickTargetSpan").text("请输入AppStore App地址：");
            } 
        }).on("click",".adCategory_mediaCateCheck",function(){//选择媒体分类  add by sunly
            var code = $(this).val();
            var name = $(this).attr("name");
            var parentCode = $(this).attr("parentCode");
            if($(this).attr("checked")){
                adCategory_addMediaCate(code,name,parentCode);
            }else{
                adCategory_removeMediaCate(code,name,parentCode);
            }
        }).on("click",".region_mediaCateCheck",function(){//选择媒体分类  add by sunly
            var code = $(this).val();
            var name = $(this).attr("name");
            var parentCode = $(this).attr("parentCode");
            if($(this).attr("checked")){
            	region_addMediaCate(code,name,parentCode);
            }else{
            	region_removeMediaCate(code,name,parentCode);
            }
        });
        
     
        
function getDeviceTypes() {
    var adIds = "";
    $("input:checkbox[name=adDeviceIds]:checked'").each(function(i){
        if(0==i){
            adIds = $(this).val();
        }else{
            adIds += (","+$(this).val());
        }
    });
    if(adIds == null || adIds == "") {
       return  false;
    }
    return true;
}
        
//add by sunly  start
function queryMediaAjax(parentCode){
    $.ajax({
        url:'/adplan/mediaCateLeveTwo-template/'+parentCode,
        type:'GET',
        success: function(result){
            $("#mediaCateLeveTwoDiv").html(result);
            initMediaCateSel(parentCode);
        },error:function(){
            alert("error");
        }
    });
}
/*
 * 根据父分类code 获取分类名称 add by sunly
 */
function getParentNameByCode(parentCode){
    var parentName = "";
    $(".mediaCateOp option").each(function(){
        if($(this).val()==parentCode){
            parentName = $(this).text();
            return;
        }
    });
    return parentName;
}
//媒体分类常量 add by sunly
var mediaParentArr  =new Array();
var prefix = "mediaCateSelectedSpan";

//add by sunly end
function checkInput(){
    var materialName = $("#materialName").val();
    var mType = $("input[name='mType']:checked").val();
    var materialType = $("input[name='materialType']:checked").val();
    var uploadImg = $("#uploadImg img").attr("src");
    var flashSrc = $("#flash").attr("src");
    var materialDesc = $("#materialDesc").val();
    var targetUrl = $("#targetUrl").val();
    var monitor = $("#monitor").attr("checked");
    var monitorlink = $("#monitorlink").val();
    var richText = UE.getEditor('editor').getContent();
    var linkUrl = $("#filePath").val();

    var regex = /^http:\/\/([\w-]+\.)+[\w-]+(\/[\w-.\/?%&=]*)?$/;

    var check = true;
    if ($.trim(materialName) == ''){
        $("#nameTips").text("广告物料名称不能为空");
        $("#nameTips").parent().parent().addClass("error");
        $("#nameTips").show();
        check = false;
    } else if (mType == 1 && materialType == 1 && uploadImg == ''){
        $("#file_uploadTips").text("请上传图片");
        $("#file_uploadTips").parent().parent().addClass("error");
        $("#file_uploadTips").show();
        check = false;
    } else if (mType == 2 && materialType == 1 && flashSrc == ''){
        $("#flash_uploadTips").text("请上传flash");
        $("#flash_uploadTips").parent().parent().addClass("error");
        $("#flash_uploadTips").show();
        check = false;
    } else if (mType == 3 && $.trim(richText) == ''){
        $("#richTextTips").text("富文本内容不能为空");
        $("#richTextTips").parent().parent().addClass("error");
        $("#richTextTips").show();
        check = false;
    } else if (mType == 1 && materialType == 2 && $.trim(linkUrl) == ''){
        $("#filePathTips").text("图片地址不能为空");
        $("#filePathTips").parent().parent().addClass("error");
        $("#filePathTips").show();
        check = false;
    } else if (mType == 1 && materialType == 2 && !regex.test(linkUrl)){
        $("#filePathTips").text("图片地址格式错误");
        $("#filePathTips").parent().parent().addClass("error");
        $("#filePathTips").show();
        check = false;
    } else if (mType == 2 && materialType == 2 && $.trim(linkUrl) == ''){
        $("#filePathTips").text("Flash地址不能为空");
        $("#filePathTips").parent().parent().addClass("error");
        check = false;
    }else if (mType == 2 && materialType == 2 && !regex.test(linkUrl)){
        $("#filePathTips").text("Flash地址格式错误");
        $("#filePathTips").parent().parent().addClass("error");
        $("#filePathTips").show();
        check = false;
    } else if (mType != 3 && $.trim(materialDesc) == ''){
        $("#materialDescTips").text("描述不能为空");
        $("#materialDescTips").parent().parent().addClass("error");
        $("#materialDescTips").show();
        check = false;
    } else if (mType != 3 && $.trim(targetUrl) == ''){
        $("#targetUrlTips").text("点击跳转链接不能为空");
        $("#targetUrlTips").parent().parent().addClass("error");
        $("#targetUrlTips").show();
        check = false;
    }else if (mType != 3 && !regex.test(targetUrl)){
        $("#targetUrlTips").text("点击跳转链接格式错误");
        $("#targetUrlTips").parent().parent().addClass("error");
        $("#targetUrlTips").show();
        check = false;
    } else if (monitor == 'checked' && $.trim(monitorlink) == ''){
        $("#monitorlinkTips").text("监控链接不能为空");
        $("#monitorlinkTips").parent().parent().addClass("error");
        $("#monitorlinkTips").show();
        check = false;
    }
    else if (monitor == 'checked' && !regex.test(monitorlink)){
        $("#monitorlinkTips").text("监控链接格式错误");
        $("#monitorlinkTips").parent().parent().addClass("error");
        $("#monitorlinkTips").show();
        check = false;
    }
    return check;
};

function bindEvent(){
    $("#cancelBtn").on("click",function(){
        $("#addMaterialModal").modal('hide');
        $("#editMaterialModal").modal('hide');
    });
    $("#confirmBtn").on("click",function(){
        editOrAddMaterial(1);
    });

    $("#editConfirmBtn").on("click",function(){
        editOrAddMaterial(2);
    });
}

function editOrAddMaterial(type){
    var materialTypeVal = $("input[name='materialType']:checked").val();
    var mTypeVal = $("input[name='mType']:checked").val();
    if(materialTypeVal == 2 && (mTypeVal == 2 || mTypeVal == 1)){
        $("#linkUrl").val($("#filePath").val());
    }
    else if(mTypeVal == 3){
        $("#textContent").val(UE.getEditor('editor').getContent());
    }

    var form = "materialForm";
    if (type == 2){
        form = "materialEditForm";
    }

    if (checkInput()){
        $("#"+form).ajaxSubmit({
            type: 'post',
            url: "/material/addNew",
            dataType:'json',
            clearForm: true,
            success: function(result){
                if (typeof result.data != 'undefined'){
                    $("#addMaterialModal").modal('hide');
                    $("#editMaterialModal").modal('hide');
                    var html = $("#addMeterialTmpl").render(result.data);
                    // add
                    if (type == 1){
                        $("#selectedMaterial").append(html);
                    } else if (type == 2){
                        // edit
                        // 修改的tr节点
                        var id = $("#" + form + " input[name='id']").val();
                        $("#selectedMaterial tr").each(function(){
                            var _this = $(this);
                            var value = _this.find("input[name='materialId']").val();
                            if (value == id){
                                $(html).insertBefore(_this);
                                _this.remove();
                            }
                        });
                    }
                    $("#selectedDiv").show();
                }
            }
        });
    }
}

function Material(id, linkUrl, type, materialValue, name, type, mType){
    this.id = id;
    this.linkUrl = linkUrl;
    this.type = type;
    this.materialValue = materialValue;
    this.name = name;
    this.type = type;
    this.mType = mType;
}

function getMaterialIds(){
    var idArray = [];
    $("#selectedMaterial tr input[name='materialId']").each(function(){
        idArray.push($(this).val());
    });
    return idArray.join(",");
}

/**
*
* @param isAdd 是否是添加，添加的话就取 t 里面的值，如果是移除就取 f 里面的值
* @param f
* @param t
* @param v
*/
function moveOption(isAdd, f1, t1, v1){
   var f = document.getElementById(f1);
   var t = document.getElementById(t1);
   var v = document.getElementById(v1);
   try{
       for(var i = 0; i < f.options.length; i++){
           if(f.options[i].selected){
               var e = f.options[i];
               t.options.add(new Option(e.text, e.value));
               f.remove(i);
               i = i - 1;
           }
       }
       if(isAdd) {
           v.value=getvalue(t);
       } else {
           v.value=getvalue(f);
       }
   } catch(e){}
}
function getvalue(geto){
   var allvalue = "";
   for(var i = 0; i < geto.options.length; i++){
       allvalue += geto.options[i].value + ",";
   }
   return allvalue;
}

/**
*
* @param isAdd 是否是添加，添加的话就取 t 里面的值，如果是移除就取 f 里面的值
* @param f
* @param t
* @param v
*/
function moveAllOption(isAdd, f1, t1, v1){
   var f = document.getElementById(f1);
   var t = document.getElementById(t1);
   var v = document.getElementById(v1);
   try{
       for(var i = 0;i < f.options.length; i++){
           var e = f.options[i];
           t.options.add(new Option(e.text, e.value));
           f.remove(i);
           i = i - 1;
       }
       if(isAdd) {
           v.value=getvalue(t);
       } else {
           v.value=getvalue(f);
       }

   } catch(e){}
}




/**********广告分类start**************/
/**
 * 初始化 广告分类 add by sunly
 */
function initAdCate(){

    $(".adCategory_mediaCateOp").on("dblclick",function(){//点击一级媒体分类  查询下一级媒体分类 add by sunly
        var code = $(this).val();
        adCategory_queryMediaAjax(code);
    });
    $("#adCategory_selAll").on("click",function(){
        //全选
        $(".adCategory_mediaCateCheck").each(function(){
            var code= $(this).val();
            var parentCode = $(this).attr("parentCode");
            var name = $(this).attr("name");
            $(this).attr("checked","checked");
            adCategory_addMediaCate(code, name, parentCode);
        });
    });
    $("#adCategory_cancelSelAll").on("click",function(){
        //取消选择
        var parentCode = "";
        $(".adCategory_mediaCateCheck").each(function(){
            parentCode = $(this).attr("parentCode");
            $(this).removeAttr("checked");
        });
        if(parentCode!=""){
            adCategory_delMediaSpanByParentCode(parentCode);
        }
    });
}


function adCategory_queryMediaAjax(parentCode){
    $.ajax({
        url:'/adplan/adCategoryLeveTwo-template/'+parentCode,
        type:'GET',
        success: function(result){
            $("#adCategory_mediaCateLeveTwoDiv").html(result);
            adCategory_initMediaCateSel(parentCode);
        },error:function(){
            alert("error");
        }
    });
}
/*
 * 根据父分类code 获取分类名称 add by sunly
 */
function adCategory_getParentNameByCode(parentCode){
    var parentName = "";
    $(".adCategory_mediaCateOp option").each(function(){
        if($(this).val()==parentCode){
            parentName = $(this).text();
            return;
        }
    });
    return parentName;
}
//广告分类常量 add by sunly
var adCategory_mediaParentArr  =new Array();
var adCategory_prefix = "adCategory_SelectedSpan";
/**
 * 删除数组中父媒体 add by sunly
 */
function adCategory_delSelMediaCateByParentCode(code){
    for(var i=0;i<adCategory_mediaParentArr.length;i++){
        var mcObj = adCategory_mediaParentArr[i];
        if(mcObj.parentCode==code){
            adCategory_mediaParentArr.splice(i,1);
            break;
        }
    }
}
/**
 * 根据父 媒体分类code获取父媒体分类 add by sunly
 */
function adCategory_getSelMediaCateByParentCode(code){
    for(var i=0;i<adCategory_mediaParentArr.length;i++){
        var mcObj = adCategory_mediaParentArr[i];
        if(mcObj.parentCode==code){
            adCategory_mediaParentArr.splice(i,1);
            return mcObj;
        }
    }
}
/*
 * 选择媒体分类 add by sunly
 */
function adCategory_addMediaCate(code,name,parentCode){

    var parentName = adCategory_getParentNameByCode(parentCode);
    var $spanList  = $("span[name="+adCategory_prefix+"]");
    var addFlag = true;

    if($spanList.length>0){
        $spanList.each(function(){
            if($(this).attr("id")==(adCategory_prefix+parentCode)){
                addFlag =false;
            }
        });
    }

    var parentObj = {};
    var childrenArr = new Array();

    if(addFlag){//添加
        childrenArr.push(code);
        var $span = $("<span></span>");
        $span.attr("id",adCategory_prefix+parentCode);
        $span.attr("name",adCategory_prefix);
        $span.attr("style","border: 1px solid #5CACEE;background-color: #B0E0E6;margin:2px;padding: 2px;margin:1px;cursor:pointer;");
        $span.click(function(){
            adCategory_queryMediaAjax(parentCode);//初始化选中
        });
        var $delA = $("<a style='cursor: pointer;text-decoration:none;'>x</a>");
        var subMedisCateNum = $(".adCategory_mediaCateCheck").length;
        $span.append("<span>"+parentName+"(1/"+subMedisCateNum+")</span>");
        $span.append("&nbsp;&nbsp;");
        $delA.click(function(){
            //清空当前选中状态
            adCategory_delMediaSpanByParentCode(parentCode);
        });
        $span.append($delA);
        $span.append("&nbsp;");
        $("#adCategory_mediaCateSelectedDiv").append($span);
    }else{//更新
        var $span = $("#"+adCategory_prefix+parentCode);
        var subSpan = $span.find("span");
        var subMediaCateNum = $(".adCategory_mediaCateCheck").length;
        var selectedSubMedisCateNum = 0;

        adCategory_delSelMediaCateByParentCode(parentCode);
        $(".adCategory_mediaCateCheck").each(function(){
            if($(this).attr("checked")){
                childrenArr.push($(this).val());
                selectedSubMedisCateNum++;
            }
        });
        subSpan.text(parentName+"("+selectedSubMedisCateNum+"/"+subMediaCateNum+")");
    }
    parentObj.parentCode=parentCode;
    parentObj.children=childrenArr;
    adCategory_mediaParentArr.push(parentObj);
}

/*
 * 去除媒体分类 add by sunly
 */
function adCategory_removeMediaCate(code,name,parentCode){

    var parentObj = {};
    parentObj = adCategory_getSelMediaCateByParentCode(parentCode);//删除原来的
    var childrenArr = parentObj.children;

    for(var i=0;i<childrenArr.length;i++){
        if(childrenArr[i]==code){
            childrenArr.splice(i,1);//删除子媒体分类
        }
    }
    if(childrenArr.length>0){
        parentObj.children=childrenArr;
        adCategory_mediaParentArr.push(parentObj);

        var $span = $("#"+adCategory_prefix+parentCode);
        var subSpan = $span.find("span");
        var subMediaCateNum = $(".adCategory_mediaCateCheck").length;
        var selectedSubMedisCateNum = 0;

        $(".adCategory_mediaCateCheck").each(function(){
            if($(this).attr("checked")){
                selectedSubMedisCateNum++;
            }
        });
        var parentName = adCategory_getParentNameByCode(parentCode);
        subSpan.text(parentName+"("+selectedSubMedisCateNum+"/"+subMediaCateNum+")");
    }else{
        adCategory_delMediaSpanByParentCode(parentCode);
    }
}

/**
 * 初始化选中
 */
function adCategory_initMediaCateSel(parentCode){
    for(var i=0;i<adCategory_mediaParentArr.length;i++){
        var parentObj = adCategory_mediaParentArr[i];
        if(parentObj.parentCode==parentCode){
            var childrenArr = parentObj.children;
            for(var j=0;j<childrenArr.length;j++){
                $(".adCategory_mediaCateCheck").each(function(){
                    if($(this).val()==childrenArr[j]){
                        $(this).attr("checked","checked");
                    }
                });
            }
            return false;
        }
    }
}
/**
 * 删除选中媒体分类span
 */
function adCategory_delMediaSpanByParentCode(parentCode){
    adCategory_delSelMediaCateByParentCode(parentCode);//删除选中的媒体分类
    $("#"+adCategory_prefix+parentCode).remove();//删除父媒体分类
    $(".adCategory_mediaCateCheck").removeAttr("checked");//去掉所有选中
}

/**
 * add by sunly
 */
function adCategory_getMediaCategoryCodes(){
    var idArray = [];

    for(var i=0;i<adCategory_mediaParentArr.length;i++){
        var chidlrenArr = adCategory_mediaParentArr[i].children;
        var childrenIds = chidlrenArr.join(",");
        idArray.push(childrenIds);
    }
    return idArray.join(",");
}
/**********广告分类end**************/

</script>
</html>
