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
         div.radio {
            float: left;
            position: relative;
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
    <link href='/resources/css/zTreeStyle/zTreeStyle.css' rel='stylesheet'>
    <link href='/resources/css/add_adplan.css' rel='stylesheet'/>

    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- The fav icon -->
    <link rel="shortcut icon" href="/resuorces/img/favicon.ico">
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
        <li><a href="#">制定广告计划</a></li>
    </ul>
</div>

<div class="row-fluid">
<div class="box span12">
<div class="box-header well">
    <h2>
        广告计划修改
    </h2>
    
</div>
<div class="box-content">
<form class="form-horizontal" method="post" action="/adplan/kwFileDownload" id="downkwform">
<input type="hidden" id="adPlanId" name="adPlanId" value="${adPlanManageInfo.adInstanceDto.adId }"/>
</form>
<form class="form-horizontal" method="post" action="/adplan/adplaneditsaveaction/${adPlanManageInfo.adInstanceDto.adId }" id="adplanFrom" enctype="multipart/form-data">
<!-- <input id="adInstanceDto.adUsefulType" name="adInstanceDto.adUsefulType" type="hidden" value="N"/> -->
<input id="adInstanceDto.adToufangSts" name="adInstanceDto.adToufangSts" type="hidden" value="0"/>
<fieldset>
    <legend id="namePOS">广告基本信息</legend>
    <div class="control-group">
        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>广告名称：</label>

        <div class="control">
            <input class="input-xlarge focused" id="adInstanceDto.adName"
                   name="adInstanceDto.adName" type="text" value="${adPlanManageInfo.adInstanceDto.adName}" />
            <span id="adNameTips" class="help-inline" style="display:none"></span>
        </div>

    </div>
    
    
    <div class="control-group">
        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>广告类型：</label>

        <div class="control">
               <c:if test="${adPlanManageInfo.adInstanceDto.adType == 0}">
                    PC
                </c:if>
                
                <c:if test="${adPlanManageInfo.adInstanceDto.adType == 1}">
                    移动
                </c:if>
        </div>

    </div>
    <!-- 广告描述删除 
      <div class="control-group">
        <label class="control-label">广告描述：</label>
        <div class="control">
            <textarea class="autogrow" id="adInstanceDto.adDesc" style="width:320px;"
                      name="adInstanceDto.adDesc" >${adPlanManageInfo.adInstanceDto.adDesc }</textarea>
        </div>
    </div> -->
    <div class="control-group">
        <label class="control-label"><b style="color: #FF0000;">*</b>起始时间：</label>
        <div class="control">
            <input class="input-small datepicker autogrow"
                   id="adInstanceDto.startTime" name="adInstanceDto.startTime" value="${adPlanManageInfo.adInstanceDto.startTime }"/>
            <span id="startTimeTips" class="help-inline" style="display:none"></span>
        </div>
    </div>
    <div class="control-group" id="namePOS1">
        <label class="control-label"><b style="color: #FF0000;">*</b>截止时间：</label>
        <div class="control">
            <input class="autogrow input-small datepicker"
                   id="adInstanceDto.endTime" name="adInstanceDto.endTime" value="${adPlanManageInfo.adInstanceDto.endTime }"/>
            <span id="endTimeTips" class="help-inline" style="display:none"></span>
            <span id="esTimeTips" class="help-inline" style="display:none;color: #BD4247"></span>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">付费方式：</label>
        <div class="control">
            <select
                    id="adInstanceDto.chargeType" name="adInstanceDto.chargeType"
                    >
                <option value="1" <c:if test="${adPlanManageInfo.adInstanceDto.chargeType=='1' }">selected</c:if>>按展示付费(CPM)</option>
                <option value="2" <c:if test="${adPlanManageInfo.adInstanceDto.chargeType=='2' }">selected</c:if>>按点击付费(CPC)</option>
            </select>
        </div>
    </div>
    <div class="control-group">
	        <label class="control-label"><b style="color: #FF0000;">*</b>单价(￥)：</label>
	        <div class="control">
	            <div class="input-prepend input-append">
	                <span class="add-on">￥</span>
	                <input id="adInstanceDto.unitPrice" name="adInstanceDto.unitPrice" value="${adPlanManageInfo.adInstanceDto.unitPrice}" size="8" type="text" />
	                <span class="add-on" id="danjia">/cpm</span>
	                <span id="unitPriceTips" class="help-inline" style="display:none"></span>
	            </div>
	        </div>
	 </div>
   <!--   计划曝光数删除
     <div class="control-group">
    <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>计划曝光数：</label>
        <div class="control">
            <input class="input-small focused" id="adInstanceDto.total"
                   name="adInstanceDto.total" type="text" value="${adPlanManageInfo.adInstanceDto.total}" />
            <span id="adTotalTips" class="help-inline" style="display:none"></span>
        </div>
        </div>
 -->
   <!-- 预算删除 
    <div class="control-group">
        <label class="control-label"><b style="color: #FF0000;">*</b>预算(￥)：</label>
        <div class="control">

            <div class="input-prepend input-append">
                <span class="add-on">￥</span><input
                    id="adInstanceDto.allBudget" name="adInstanceDto.allBudget" size="8"  style="width:185px;" type="text" value="${adPlanManageInfo.adInstanceDto.allBudget }" />
                 <span class="add-on">.00</span> 
                <span id="allBudgetTips" class="help-inline" style="display:none"></span>
            </div>
        </div>
    </div>
 -->
   <!--  日预算删除 
     <div class="control-group">
        <label class="control-label"><b style="color: #FF0000;">*</b>日预算(￥)：</label>
        <div class="control">


            <div class="input-prepend input-append">
                <span class="add-on">￥</span><input
                    id="adInstanceDto.dayBudget" name="adInstanceDto.dayBudget" size="8" type="text"  style="width:185px;" value="${adPlanManageInfo.adInstanceDto.dayBudget }"/>
                 <spanclass="add-on">.00</span> 
                <span id="dayBudgetTips" class="help-inline" style="display:none"></span>
            </div>
        </div>
    </div> -->
    
    <!-- 渠道选择 -->
        <%-- <div class="control-group" id="channelDiv">
           <label class="control-label"><b style="color: #FF0000;">*</b>该广告投放渠道:</label>
        <c:if test="${adPlanManageInfo.adInstanceDto.channel == '0'}">
         	上海弹窗
        </c:if>
        <c:if test="${adPlanManageInfo.adInstanceDto.channel == '1'}">
         	TANX
        </c:if>
        <c:if test="${adPlanManageInfo.adInstanceDto.channel == '2'}">
         	诏兰push
        </c:if>
        <c:if test="${adPlanManageInfo.adInstanceDto.channel == '3'}">
         	芒果直投
        </c:if>
        <c:if test="${adPlanManageInfo.adInstanceDto.channel == '4'}">
         	404导航
        </c:if>
        <c:if test="${adPlanManageInfo.adInstanceDto.channel == '5'}">
         	第三方
        </c:if>
        <c:if test="${adPlanManageInfo.adInstanceDto.channel == '6'}">
         	青稞视频
        </c:if>
        <c:if test="${adPlanManageInfo.adInstanceDto.channel == '7'}">
         	上海门户
        </c:if> --%>
    <input type="hidden"  id="adInstanceDtoChannel" name="adInstanceDto.channel" value="${adPlanManageInfo.adInstanceDto.channel }"/>
      
       
    <!-- </div> -->
   <%--  最高出价 删除
    <div class="control-group" id="max_cpm_bidpriceDiv" >
        <label class="control-label">最高出价(分/cpm)：</label>
        <div class="control">
            <div class="input-prepend input-append">
                <input  id="max_cpm_bidprice" name="adInstanceDto.maxCpmBidprice"  value="${adPlanManageInfo.adInstanceDto.maxCpmBidprice }"  type="text" />
                 <span id="max_cpm_bidpriceTips" class="help-inline" style="display:none"></span>
            </div>
        </div>
    </div> --%>
    <!-- 投放策略 -->
   <!-- <div class="control-group">   时间段删除
        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>时间段：</label>
        <div class="control">
            每 <input class="input-xlarge focused"
                     id="adTimeFrequencyDto.putIntervalNum" name="adTimeFrequencyDto.putIntervalNum"
                     type="text" value="${adPlanManageInfo.adTimeFrequencyDto.putIntervalNum }" style="width:70px" /> <select
                id="adTimeFrequencyDto.putIntervalUnit" name="adTimeFrequencyDto.putIntervalUnit"
                style="width: 100px">
            <option value="W"
            <c:if test="${adPlanManageInfo.adTimeFrequencyDto.putIntervalUnit=='W' }">selected</c:if>>周</option>
            <option value="D"
            <c:if test="${adPlanManageInfo.adTimeFrequencyDto.putIntervalUnit=='D' }">selected</c:if>>天</option>
            <option value="H"
            <c:if test="${adPlanManageInfo.adTimeFrequencyDto.putIntervalUnit=='H' }">selected</c:if>>小时</option>
            <option value="M"
            <c:if test="${adPlanManageInfo.adTimeFrequencyDto.putIntervalUnit=='M' }">selected</c:if>>分钟</option>
            <option value="S"
            <c:if test="${adPlanManageInfo.adTimeFrequencyDto.putIntervalUnit=='S' }">selected</c:if>>秒</option>

        </select> 展现一次
            <span id="putIntervalNumTips" class="help-inline" style="display:none"></span>
        </div>
    </div> -->
    <div class="control-group">
        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>每日期望PV：</label>
        <div class="control">

            <input class="input-xlarge focused" id="adTimeFrequencyDto.dayLimit"
                   name="adTimeFrequencyDto.dayLimit" type="text" value="${adPlanManageInfo.adTimeFrequencyDto.dayLimit }"
            style="width: 90px" />
            <span id="dayLimitTips" class="help-inline" style="display:none"></span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label" for="focusedInput"> 
        	<input type="checkbox"  id="adTimeFrequencyDto.isUniform" name="adTimeFrequencyDto.isUniform"  value="${adPlanManageInfo.adTimeFrequencyDto.isUniform}" 
        		onclick="checked_isUniform(this.checked)" >
         	<!-- <input type="hidden" name="adTimeFrequencyDto.isUniform" value="0" /> -->
         		<td>每日均匀投放</td>
         	</input>
         </label>
    <div class="control">
        <td>每分钟最高投放量：</td>
            <input class="input-xlarge focused" id="adTimeFrequencyDto.minLimit"
                   name="adTimeFrequencyDto.minLimit" type="text" value="${adPlanManageInfo.adTimeFrequencyDto.minLimit}"  style="width: 85px" readonly="readonly"/>
            <span id="minLimitTips" class="help-inline" style="display:none"></span>
        </div>
    </div>
    <div class="control-group" id="uidImpressNumPos">
        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>独立访客展现次数：</label>
        <div class="control">

            
            <!-- 
            <select id="adUserFrequencyDto.timeUnit" name="adUserFrequencyDto.timeUnit"
                      style="width: 80px">
            <option value="W"
            <c:if test="${adPlanManageInfo.adUserFrequencyDto.timeUnit =='W' }">selected</c:if>>周</option>
            <option value="D"
            <c:if test="${adPlanManageInfo.adUserFrequencyDto.timeUnit =='D' }">selected</c:if>>天</option>
            <option value="H"
            <c:if test="${adPlanManageInfo.adUserFrequencyDto.timeUnit =='H' }">selected</c:if>>小时</option>
            <option value="M"
            <c:if test="${adPlanManageInfo.adUserFrequencyDto.timeUnit =='M' }">selected</c:if>>分钟</option>
            <option value="S"
            <c:if test="${adPlanManageInfo.adUserFrequencyDto.timeUnit =='S' }">selected</c:if>>秒</option>
        </select> 
         -->
        每天最多展现 
        <input class="input-xlarge focused"
                              id="adUserFrequencyDto.uidImpressNum" name="adUserFrequencyDto.uidImpressNum"
                              type="text" value="${adPlanManageInfo.adUserFrequencyDto.uidImpressNum }" style="width: 40px" /> 次
            <span id="uidImpressNumTips" class="help-inline" style="display:none"></span>
        </div>
    </div>
    <div class="control-group" id="uidIpNumPos">
        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>独立IP展现次数：</label>
        <div class="control">          
        每天最多展现 
        <input class="input-xlarge focused"
                              id="adUserFrequencyDto.uidIpNum" name="adUserFrequencyDto.uidIpNum"
                              type="text" value="${adPlanManageInfo.adUserFrequencyDto.uidIpNum }" style="width: 40px" /> 次
            <span id="uidIpNumTips" class="help-inline" style="display:none"></span>
        </div>
    </div>
</fieldset>
<!-- 终端定向 -->
<%-- <fieldset>     终端定向删除
<legend style="padding-left:10px;">终端定向</legend>
<c:if test="${not empty terminal_map}">
<!-- 设备 device_list -->
<table style="width: 100%;">
    <tr>
        <td width="10%"><span class="label" style="margin-left;50px;display:block;text-align:right;width:130px;">不投放的设备类型：</span></td>
        <td width="90%"><a href="#" class="btn btn-primary "
                           id="deviceSel">选择不投放的设备类型</a></td>
    </tr>
    <tr valign="top">
        <td></td>
        <td colspan="2">
            <input type="hidden" id="deviceSeledIds" name="deviceSeledIds"/>
            <!-- 获取内容 -->
            <div id="deviceSeled">
                <c:if test="${not empty adPlanManageInfo.map.deviceList}">
                    <c:forEach var="device" items="${adPlanManageInfo.map.deviceList}">
																
																<span class='label label-success' style='border-style:double;' >${device.tName }
																<a href='javascript:void(0);' name='deviceClose'>
                                                                    <span class='icon icon-orange icon-close'  ></span></a>
																		<input type='hidden' name='deviceSeledId' value='${device.tbiId }'/></span>

                    </c:forEach>
                </c:if>
            </div>



        </td>
    </tr>
    
    <tr>
        <td></td>
        <td>
            <div class="box span12" id="deviceSBTab"">
            <div class="box-header well" data-original-title>
                <h2>
                    <i class="icon-user"></i> 设备类型列表
                </h2>
            </div>
            <div class="box-content">
                <table
                        class="table table-striped table-bordered bootstrap-datatable datatable">
                    <thead>
                    <tr>
                        <th>选择</th>
                        <th>设备名称</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:if test="${not empty terminal_map.device_list}">
                        <c:forEach var="device" items="${terminal_map.device_list}">
                            <tr>
                                <td><input type="checkbox" value="${device.tbiId}" name="deviceCheck" devicename="${device.tName }" /></td>
                                <td>${device.tName}</td>
                                </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    </tbody>
                </table>
            </div>

        </td>

    </tr>
</table>
<!-- 操作系统  system_list -->
<table style="width: 100%;">
	<tr>
        <td colspan="2"><hr /></td>
    </tr>
    <tr>
        <td width="10%"><span class="label" style="margin-left;50px;display:block;text-align:right;width:130px;">不投放的操作系统：</span></td>
        <td width="90%"><a href="#" class="btn btn-primary "
                           id="systemSel">选择不投放的操作系统</a></td>
    </tr>
    <tr valign="top">
        <td></td>
        <td >
            <input type="hidden" id="systemSeledIds" name="systemSeledIds"/>
            <div id="systemSeled">
                <c:if test="${not empty adPlanManageInfo.map.systemList}">
                    <c:forEach var="system" items="${adPlanManageInfo.map.systemList}">
																
																<span class='label label-success' style='border-style:double;' >${system.tName }
																<a href='javascript:void(0);' name='systemClose'>
                                                                    <span class='icon icon-orange icon-close'  ></span></a>
																		<input type='hidden' name='systemSeledId' value='${system.tbiId }'/></span>

                    </c:forEach>
                </c:if>
            </div>
        </td>

    </tr>
    
    <tr>
        <td></td>
        <td>
            <div class="box span12" id="systemSBTab"">
            <div class="box-header well" data-original-title>
                <h2>
                    <i class="icon-user"></i> 操作系统列表
                </h2>
            </div>
            <div class="box-content">
                <table
                        class="table table-striped table-bordered bootstrap-datatable datatable">
                    <thead>
                    <tr>
                        <th>选择</th>
                        <th>操作系统名称</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:if test="${not empty terminal_map.system_list}">
                        <c:forEach var="system" items="${terminal_map.system_list}">
                            <tr>
                                <td><input type="checkbox"
                                           value="${system.tbiId}" name="systemCheck" systemname="${system.tName }" /></td>
                                <td>${system.tName}</td>
                                </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    </tbody>
                </table>
            </div>

        </td>

    </tr>
</table>
<!-- 浏览器  browser_list -->
<table style="width: 100%;">
  	<tr>
        <td colspan="2"><hr /></td>
    </tr>
    <tr>
        <td width="10%"><span class="label" style="margin-left;50px;display:block;text-align:right;width:130px;">不投放的浏览器：</span></td>
        <td width="90%"><a href="#" class="btn btn-primary " id="browserSel">选择不投放的浏览器</a></td>
    </tr>
    <tr valign="top">
        <td></td>
        <td >
            <input type="hidden" id="browserSeledIds" name="browserSeledIds"/>
            <div id="browserSeled">
                <c:if test="${not empty adPlanManageInfo.map.browserList}">
                    <c:forEach var="browser" items="${adPlanManageInfo.map.browserList}">
																
																<span class='label label-success' style='border-style:double;' >${browser.tName }
																<a href='javascript:void(0);' name='browserClose'>
                                                                    <span class='icon icon-orange icon-close'  ></span></a>
																		<input type='hidden' name='browserSeledId' value='${browser.tbiId }'/></span>

                    </c:forEach>
                </c:if>
            </div>

        </td>

    </tr>
  
   
    <tr>
        <td></td>
        <td>
            <div class="box span12" id="browserSBTab"">
            <div class="box-header well" data-original-title>
                <h2>
                    <i class="icon-user"></i> 浏览器列表
                </h2>
            </div>
            <div class="box-content">
                <table
                        class="table table-striped table-bordered bootstrap-datatable datatable">
                    <thead>
                    <tr>
                        <th>选择</th>
                        <th>浏览器名称</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:if test="${not empty terminal_map.browser_list}">
                        <c:forEach var="browser" items="${terminal_map.browser_list}">
                            <tr>
                                <td><input type="checkbox"
                                           value="${browser.tbiId}" name="browserCheck" browsername="${browser.tName }" /></td>
                                <td>${browser.tName}</td>
                                </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    </tbody>
                </table>
            </div>
        </td>
    </tr>
</table>
</c:if>
</fieldset> --%>
<fieldset>
<legend> 时间定向 </legend>

<input type="hidden" id="putHours" name="putHours" value='${adPlanManageInfo.putHours }'/>


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
                <select data-rel="chosen" onchange="timeValidater(this.id)" id="workDayEnd" name="workDayEnd" style="width: 100px">
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
        </tr>
       <tr><td colspan="6"><br /></td></tr>
        <tr>
            <td><input type="checkbox" id="cWeekend" value="cWeekend"/>周末</td>
            <td>开始时间：</td>
            <td>
                <select data-rel="chosen" id="weekendDayStart" name="weekendDayStart" style="width: 100px">
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
                <select data-rel="chosen" onchange="timeValidater(this.id)" id="weekendDayEnd" name="weekendDayEnd" style="width: 100px">
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

        </tr>

    </table>

    <br />
</fieldset>


<fieldset style="display: none;">
<legend>标签定向</legend>
 <input type="hidden" name="allLabelCodes" id="allLabelCodes"/>
 <input type="hidden" id="crowdId" value="${adPlanManageInfo.adInstanceDto.adId}"/>
<div>						
<div id="interstLabeTree" class="ztree" style="float:left;width:40%;margin-right:2%;height: 500px; overflow-y: auto;">
							
</div>
							
<div class="select_Data_DIV" id="interest_div" 
<c:choose>
<c:when test="${not empty selectLabelPlanList}">style="display:block;float:left;width:50%;height: 500px;overflow-y: auto;" </c:when>
<c:otherwise>style="display: none;float:left;"</c:otherwise>
</c:choose>
>
	<!-- 搜索内容开始 -->
<div style="width:100%;height:auto;overflow:hidden;margin-top:5%;margin-bottom:3%;">
		<input type="button" id="computeCrowdBtn" class="btn btn-primary" value="计算标签受众"/>
		<div class="search_ztree_content" style="display:none;" id="computeResultDiv">
			<!-- <span class="content_span" style="width:25%;">查询用时<font id="takeMsVal"></font></span> -->
			<span class="content_span" style="width:40%;">找到<font style="color:#ff0000;font-weight:bold;" id="pvCountVal"></font>条记录</span>
			<!-- <span class="content_span" style="width:25%;">共<font style="color:#ff0000;font-weight:bold;" id="uvCountVal"></font>个用户</span> -->
		</div>
</div>

<c:forEach var="obj" items="${selectLabelPlanList}">
<span class="data_span" style="margin-left:0px"><label id="click_show" style="display: block;float: left;width: 70%;">${obj.selectLabelShow}</label> 
  <img src="/resources/person-images/delete_button.png" objId="${obj.labelValue}"  labelName="${obj.labelName}"
			  class="delete_icon" id="del_btn" onclick="removeNode(this)" />
</span>	
</c:forEach>



</div>
							
</div>
   
</fieldset>

<fieldset style="display: block;">
<legend>关键词定向</legend>
<!--  
				<div class="controls_content" id="keyword" style="float:left;width:50%;margin-right:2%;height: 500px; overflow-y: auto;">
		-->		
				<!--
					<div class="control_group">
						<label class="control_label" style="color: #666;font-size: 12px;">
							启动即搜即投：
						</label>
						<div class="control">
							<div class="input_prepend input_append">
								 <div class="radio">
									<span> 
										<input type="radio" size="8" value="1" name="keyWordAdPlan.isJisoujitou" 
										  <c:choose>
											<c:when test="${adKeyword.isJisoujitou =='1'}">checked="checked"</c:when>
											<c:when test="${adKeyword.isJisoujitou =='2'}"></c:when>
											<c:otherwise> checked="checked" </c:otherwise>
										</c:choose>
										/>
									 </span>
								</div> 
								<div style="float:left"><span class="radio" style="line-height:20px;color: #666;font-size: 12px;">是</span></div>
								 <div>
									<span> 
										<input type="radio" size="8" value="2" name="keyWordAdPlan.isJisoujitou" 
										 <c:choose>
											<c:when test="${adKeyword.isJisoujitou =='1'}"></c:when>
											<c:when test="${adKeyword.isJisoujitou =='2'}">checked="checked"</c:when>
											<c:otherwise> checked="checked" </c:otherwise>
										</c:choose>
										/>
									 </span>
								</div> 
								<div style="float:left"><span class="radio" style="line-height:20px;color: #666;font-size: 12px;">否</span></div>
							</div>
						</div>
					</div>
					-->
					
					<input type="hidden" value="${adKeyword.isJisoujitou}" name="keyWordAdPlan.isJisoujitou" />
					
					<!--点击即搜即投为“是”时，显示下面的数据层开始-->
					<div class="opera_DIV"  id="opera_DIV">
					
					<!--  
						<div class="control_group">
							<div style="height:30px;width:80%;margin-left:5px;">
							<label class="control_label" style="width:115px;color: #666;font-size: 12px;">				
								提取周期：
							</label>
							<div class="control">
								<span style="absolute;margin-top:-12px;">
									<table cellspacing="0" cellpadding="0" width="100%" border="0">
										<tr>
											<td align="left">								
												<select id="kwUrlFetchCicle"  name="keyWordAdPlan.fetchCicle"  style="width:130px;height:26px;color: #666;font-size: 12px;">
													<option value="1" selected="selected">1天</option>
													<option value="3" <c:if test="${adKeyword.fetchCicle=='3'}"> selected="selected"</c:if>>3天</option>
													<option value="7" <c:if test="${adKeyword.fetchCicle=='7'}"> selected="selected"</c:if>>7天</option>
													<option value="15" <c:if test="${adKeyword.fetchCicle=='15'}"> selected="selected"</c:if>>15天</option>
													<option value="30" <c:if test="${adKeyword.fetchCicle=='30'}"> selected="selected"</c:if>>一个月</option>
												</select>
											</td>
										</tr>
									</table>
								</span>
							</div>
						</div>	
						</div>
						-->
						
						<!--  
						<div class="control_group">
							<label class="control_label" style="color: #666;font-size: 12px;">		
								搜索类型：
							</label>
							<div class="control">
								<div class="input_prepend input_append">
									<div class="checker">
										<span>
											<input type="checkbox" value="1" name="keyWordAdPlan.searchType" id="searchType" 
											  <c:choose>
												<c:when test="${adKeyword.searchType =='1'}">checked="checked" </c:when>
												<c:when test="${adKeyword.searchType =='1,2'}">checked="checked"</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
											/>
										</span>
									</div>
										<label style="line-height:20px;display:block;float:left;margin-right: 5px;">搜索引擎</label>
									<div class="checker">
										<span>
											<input type="checkbox" value="2" name="keyWordAdPlan.searchType" id="searchType" 
											   <c:choose>
												<c:when test="${adKeyword.searchType =='2'}">checked="checked" </c:when>
												<c:when test="${adKeyword.searchType =='1,2'}">checked="checked" </c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
											/>
										</span>
									</div>
										<label style="line-height:25px;display:block;float:left;position:relative;top:-3px;left:-1px">电商</label>
								</div>
							</div>
						</div>
						-->
						<div class="control_group">
							<label class="control_label" style="color: #666;font-size: 13px;">
								关键词：
							</label>
							<div class="control">
								<textarea cols="100" rows="20" name="keyWordAdPlan.keyWd" id="keyWd" style="width:320px;"></textarea>								                      
								<!-- <div style="float:right"><input type="button" id="clearkw" value="清空" style="margin-top: 182px;margin-right:35px;"/></div> -->
								<span id="keywordTips" class="help-inline" style="display:none;color: #bd4247;"></span>
								<div><span class="help_inline" style="font-size: 12px;">输入格式说明 : 一个关键词占一行,关键词之间不要有空格！！！
								 <br />如果很多可以上传文件，txt形式，每行一个关键词</span></div>
								
								<div class="controls_tips">
								<!--
									<label class="time_label" style="color: #666;font-size: 12px;">匹配关系:</label>
									<input type="radio" name="keyWordAdPlan.matchType"  value="1"
									   <c:if test="${adKeyword.matchType =='1'}">checked="checked" </c:if>
									/><label class="label_02" style="color: #666;font-size: 12px;">精准</label>
									<input type="radio" name="keyWordAdPlan.matchType"   value="2"
									   <c:if test="${adKeyword.matchType =='2'}">checked="checked" </c:if>
									/><label class="label_02" style="color: #666;font-size: 12px;">中心</label>
									-->	
								</div>	
														
							</div>
							<div class="control" style="margin-left: 50px;">
							    <span style="color: #666;font-size: 12px;">关键词上传:</span>
							    <input type="file" name="kwfilepath" id="kwfilepath"/>
							    <input type="hidden" name="kwFileName" id="kwFileName" value=""/>
							    <input type="button" name="cancelupload" id="cancelupload" value="取消上传" style="margin-left: 70px;"/>
							</div>
							<div class="control" style="margin-left: 50px;">
							    <span style="color: #666;font-size: 12px;">关键词下载:</span>
							    <input type="button" name="downloadkw" id="downloadkw" value="下载" style="margin-left: 105px; margin-top: 5px; width: 80px;"/>
							</div>
						</div>
						
						
					</div>
					<!--点击即搜即投为“是”时，显示下面的数据层结束-->					
				<!--  
				</div>	
				-->
				<!--  			
	            <div class="select_Data_DIV" id="keyword_div"  style="float:right;width:40%;height: 500px;overflow-y: auto;">
	              
	               <div style="width:100%;height:auto;overflow:hidden;margin-top:5%;margin-bottom:3%;">
		              <input type="button" id="computeKeyWordBtn" class="btn btn-primary" value="计算关键词受众"/>
		              <div class="search_ztree_content" style="display:none;" id="computeKeywordResult">
			             <!-- <span class="content_span" style="width:25%;color: #666;font-size: 12px;">查询用时<font id="tookMsVal"></font></span>
			             <span class="content_span" style="width:25%;color: #666;font-size: 12px;">找到<font style="color:#ff0000;font-weight:bold;" id="totalCountVal"></font>条记录</span>
			             
			             <span class="content_span" style="width:40%;color: #666;font-size: 12px;">共找到<font style="color:#ff0000;font-weight:bold;" id="keywordCountVal"></font>个用户</span>
		              </div>
	              </div>
               </div>
               -->			
			
			<!--关键词定向结束-->
</fieldset>
<fieldset style="display: none;">
<legend>人群画像定向</legend>
<!--人群画像定向开始-->
			<div class="controls_data">			
				<div class="sex_DIV" id="sex_DIV">
					<span class="habbit_type">
						<label class="sex_label" style="color: #666;font-size: 13px;">性别：</label>
						<input type="checkbox" name="adPlanPortrait.sex" class="sex_singel" id="sex2" value="2"
						<c:choose>
						  <c:when test="${portrait.sex =='2'}">checked="checked"</c:when>
						  <c:when test="${portrait.sex =='2,3'}">checked="checked"</c:when>
						  <c:otherwise></c:otherwise>
						</c:choose>
						/><label class="habbit_label" style="color: #666;font-size: 12px;">男</label>
						<input type="checkbox" name="adPlanPortrait.sex" class="sex_singel" id="sex3" value="3"
						<c:choose>
						  <c:when test="${portrait.sex =='3'}">checked="checked"</c:when>
						  <c:when test="${portrait.sex =='2,3'}">checked="checked"</c:when>
						  <c:otherwise></c:otherwise>
						</c:choose>
						/><label class="habbit_label" style="color: #666;font-size: 12px;">女</label>
					</span>
					<span class="habbit_type">
						<label class="sex_label" style="color: #666;font-size: 13px;">年龄段：</label>
						<input type="checkbox" name="adPlanPortrait.ageRange" id="ageRange1" value="1"/><label class="habbit_label" style="color: #666;font-size: 12px;">10~20岁</label>
						<input type="checkbox" name="adPlanPortrait.ageRange" id="ageRange2" value="2"/><label class="habbit_label" style="color: #666;font-size: 12px;">21~30岁</label>
						<input type="checkbox" name="adPlanPortrait.ageRange" id="ageRange3" value="3"/><label class="habbit_label" style="color: #666;font-size: 12px;">31~40岁</label>
						<input type="checkbox" name="adPlanPortrait.ageRange" id="ageRange4" value="4"/><label class="habbit_label" style="color: #666;font-size: 12px;">大于40岁</label>
					</span>
				</div>
			</div>
			<!--人群画像定向结束-->
</fieldset>

<%-- <fieldset>
<legend>域名定向</legend>
        <div>
            <input type="button" value="删除" style="width: 80px; height: 30px; margin-left: 500px;"/>
            <input type="button" value="新增" style="width: 80px; height: 30px; float:right; margin-right: 200px;" onclick="addChannelUrl()"/>
        </div>
        <div class="controls_data">
	        <div class="controls_content" id="channel" style="float:left;width:30%;margin-right:2%;height: 500px; overflow-y: auto;">
	            <ul id="sitemap">			 		
				    <dl>
					   <dt>
					     <input type="checkbox" name="channel" id="channelcheck"/>
					     <span style="color: #666;font-size: 13px;">域名频道</span>
					   </dt>
				    </dl>
				    <ul>	
				      <c:forEach items="${channelBaseList}" var="channelBase">				 					
					   <dl>
						  <dt>
						     <input type="checkbox" name="channelType" value="${channelBase.id}"/>
							 <span style="color: #666;font-size: 13px;">${channelBase.channelName}</span>
						  </dt>
					    </dl>	
					    </c:forEach>																			  				
				     </ul>																																				
		         </ul>		
	          </div>	
	          <div class="select_Data_DIV" id="channel_list" style="float:right;width:60%;height: 500px;overflow-y: auto;border-width: 0px;">
                  <div class="" style="display:none" id="selectedUrl">
		            <input type="hidden" name="channelIds" id="channelIds"/>
		            <label class="">已选域名：</label>
		            <div class="row-fluid">
		              <div class="box-content" style="width: 600px; padding: 0px;">
                        <table class="table table-bordered" id="" style="font-size: 13px;">
		                    <thead>
                                <tr>
                                  <th>域名频道</th>
                                  <th>域名title</th>
                                  <th>域名URL</th>
                                  <th>操作</th>
                               </tr>
                            </thead>
		                    <tbody id="selectedChannelIds">
		                    </tbody>
		                </table>
		              </div>
		           </div>
		         </div>                         
                 <div class="row-fluid" style="display:none" id="addChannelUrl">
		            <div class="control_group">
                       <div class="control"> 
                         <select name="channelTypeList" id="channelTypeList" style="margin-left: 75px;">
                           <c:forEach items="${channelBaseList}" var="channelBaseList">
                             <option value="${channelBaseList.id}">${channelBaseList.channelName}</option>
                           </c:forEach>
                         </select>
                       </div>
                    </div>
                    <div class="control_group">
                       <div class="control">
                         <textarea cols="85" rows="10" name="" id="channelurl" style="width:320px;margin-left: 75px;"></textarea>
                       </div>
                    </div>
                    <div>
                       <input type="button" value="保存" onclick="subChannelUrl()" style="margin-left: 100px; margin-top: 20px; width: 80px; height: 30px;"/>
                       <input type="button" value="取消" onclick="cancelAdd()" style="float:right; margin-top: 20px; width: 80px; height: 30px; margin-right: 260px;"/>
                    </div>
		         </div> 
                 <div class="row-fluid" id="rowFluid" style="display:block">
		            <div class="box span8">
		               <div class="row-fluid">
		                  <div class="box-content" style="width: 600px; padding: 0px;">
                             <table class="table table-bordered" id="channelListTable" style="font-size: 13px;">
                                <thead>
                                 <tr>
                                  <th style="height: 25px;"></th>
                                  <th>域名频道</th>
                                  <th>域名title</th>
                                  <th>域名URL</th>
                                  <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="channelListBody">           
                                </tbody>
                              </table>	
                           </div>
                        </div>
                    </div>
                 </div>  
	          </div>			
          </div>
</fieldset> --%>

<%-- <fieldset>
    <legend>受众人群定向 </legend>
    <table style="width: 100%;">
        <tr valign="top">

            <td><span class="label">受众人群:</span></td>
            <td colspan="2">
                <input type="hidden" id="crowdSeledIds" name="crowdSeledIds"/>
                <div id="crowdSeled">

                    <c:if test="${not empty adPlanManageInfo.adCList}">
                        <c:forEach var="crowd" items="${adPlanManageInfo.adCList}">
																
																<span class='label label-success' style='border-style:double;margin-right:10px;' >${crowd.crowdName }
																<a href='javascript:void(0);' name='crowdClose'>
                                                                    <span class='icon icon-orange icon-close'  ></span></a>
																		<input type='hidden' name='crowdSeledId' value='${crowd.crowdId }'/></span>

                        </c:forEach>
                    </c:if>
                </div><br />

            </td>

        </tr>
        <tr>
            <td width="7%"></td>
<!--             <td width="11%"><a href="#" class="btn btn-primary " -->
<!--                                id="crowdNew">新建受众人群</a></td> -->
            <td width="80%"><a href="#" class="btn btn-primary "
                               id="crowdSel">从受众人群库中选择</a></td>
        </tr>
        <tr>
            <td colspan="3"><hr /></td>
        </tr>
        
        <tr>
            <td></td>
<!--             <td></td> -->
            <td>
                <div class="box span12" id="crowdSBTab"">
                <div class="box-header well" data-original-title>
                    <h2>
                        <i class="icon-user"></i> 受众人群列表
                    </h2>
                </div>
                <div class="box-content">
                    <table
                            class="table table-striped table-bordered bootstrap-datatable datatable">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>受众人群名称</th>
                            <th>创建时间</th>
<!--                             <th>描述</th> -->
                        </tr>
                        </thead>
                        <tbody>

                        <c:if test="${not empty crowdList}">
                            <c:forEach var="crowd" items="${crowdList}">
                                <tr>
                                    <td><input type="checkbox"
                                               value="${crowd.crowdId}" name="crowdCheck" crowdname="${crowd.crowdName }" /></td>
                                    <td>${crowd.crowdName}</td>
                                    <td class="center">${crowd.createDate }</td>
                                    <td class="center">${crowd.crowdDesc }</td>

                                </tr>
                            </c:forEach>
                        </c:if>

                        </tbody>
                    </table>
                </div>
</div> <!--/span-->


</td>

</tr>
</table>

</fieldset> --%>

<!--重定向人群  -->
<%-- <fieldset>
    <legend>重定向人群定向 </legend>
    <table style="width: 100%;">
        <tr valign="top">

            <td><span class="label">重定向人群:</span></td>
            <td colspan="2">
                <input type="hidden" id="visitorSeledIds" name="visitorSeledIds"/>
                <div id="visitorSeled">

                    <c:if test="${not empty adPlanManageInfo.visitorList}">
                        <c:forEach var="visi" items="${adPlanManageInfo.visitorList}">
																
																<span class='label label-success' style='border-style:double;' >${visi.name }
																<a href='javascript:void(0);' name='visitorClose'>
                                                                    <span class='icon icon-orange icon-close'  ></span></a>
																		<input type='hidden' name='visitorSeledId' value='${visi.vcId }'/></span>

                        </c:forEach>
                    </c:if>
                </div>

            </td>

        </tr>
        <tr>
            <td colspan="3"><hr /></td>
        </tr>
        <tr>
            <td width="7%"></td>
            <td width="11%"><a href="/redirection/initadd" class="btn btn-primary "
                               id="">新建重定向人群</a></td>
            <td width="80%"><a href="#" class="btn btn-primary "
                               id="visitorSel">从重定向人群库中选择</a></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>
                <div class="box span12" id="visitorSBTab"">
                <div class="box-header well" data-original-title>
                    <h2>
                        <i class="icon-user"></i> 重定向人群列表
                    </h2>
                </div>
                <div class="box-content">
                    <table
                            class="table table-striped table-bordered bootstrap-datatable datatable">
                        <thead>
                        <tr>
                            <th>选择</th>
                            <th>受众人群名称</th>
                            <th>描述</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:if test="${not empty visitorList}">
                            <c:forEach var="visitor" items="${visitorList}">
                                <tr>
                                    <td><input type="checkbox"
                                               value="${visitor.vcId}" name="visitorCheck" visitorname="${visitor.name }" /></td>
                                    <td>${visitor.name}</td>
                                    <td class="center">${visitor.vcSiteDesc }</td>
                                </tr>
                            </c:forEach>
                        </c:if>

                        </tbody>
                    </table>
                </div>
</div> <!--/span-->


</td>

</tr>
</table>

</fieldset> --%>

<%-- <fieldset>
    <legend> 站点定向 </legend>
    每行输入一个url <br /><br />
    <textarea style="resize: none; width: 80%; height: 240px;"
              id="adSites" name="adSites">${adPlanManageInfo.adSites }</textarea>
    <br />
    <br />
    <div class="alert alert-info" style="width: 60%">

        <strong>说明:</strong> 站点定向用来定位访问特定页面的访客.
        每行输入一个url,以回车换行.
        例如：输入http://www.hexun.com/，则只在url中含有http://www.hexun.com/的页面上展现广告。

    </div>

</fieldset> --%>
<fieldset>
    <legend> 站点定向 </legend>
    每行输入一个url <br /><br />
     <div style="width:100%;height:auto;overflow:hidden;">
	    <div style="float:left;width:40%;height:auto;overflow:hidden;">
			<span style="width:100%;height:30px;line-height:30px;display:block;">  正向站点：</span>
			<textarea style="resize: none; width:80%; height: 240px; id="whiteAdSites" name="whiteAdSites">${adPlanManageInfo.whiteAdSites }</textarea>
	     <div class="clear"></div>
		</div>
		<div style="float:left;width:40%;height:auto;overflow:hidden;">
			<span style="width:100%;height:30px;line-height:30px;display:block;"> 负向站点：</span>
			<textarea style="resize: none; width: 80%; height: 240px; id="blackAdSites" name="blackAdSites">${adPlanManageInfo.blackAdSites }</textarea>
			<div class="clear"></div>
		</div>
	</div>
	 <div class="alert alert-info" style="width: 60%;margin-top:15px;">
        <strong>说明:</strong>  站点定向用来定位访问特定页面的访客.
        每行输入一个url,以回车换行.
        例如：输入http://www.hexun.com/，则只在url中含有http://www.hexun.com/的页面上展现广告。
        <div class="clear"></div>
    </div>
</fieldset> 
<%-- <fieldset>
    <legend> ip定向 </legend>
     正向ip：<br /><br />
    <textarea style="resize: none; width: 40%; height: 240px;"
              id="whiteIps" name="whiteIps">${adPlanManageInfo.whiteIps }</textarea>
    <br />
    <br />
     负向ip：<br /><br />
    <textarea style="resize: none; width: 40%; height: 240px;"
              id="blackIps" name="blackIps">${adPlanManageInfo.blackIps }</textarea>
              <br /><br />
    <div class="alert alert-info" style="width: 60%">
        <strong>说明:</strong> ip定向 正向ip为可投放ip，负向ip为不投放ip.
        每行输入一个ip,以回车换行.
    </div>

</fieldset> --%>
<fieldset>
    <legend> ip定向 </legend>
    <div style="width:100%;height:auto;overflow:hidden;">
	    <div style="float:left;width:40%;height:auto;overflow:hidden;">
			<span style="width:100%;height:30px;line-height:30px;display:block;">  正向ip：</span>
			<textarea style="resize: none; width:80%; height: 240px; id="whiteIps" name="whiteIps">${adPlanManageInfo.whiteIps }</textarea>
	     <div class="clear"></div>
		</div>
		<div style="float:left;width:40%;height:auto;overflow:hidden;">
			<span style="width:100%;height:30px;line-height:30px;display:block;"> 负向ip：</span>
			<textarea style="resize: none; width: 80%; height: 240px; id="blackIps" name="blackIps">${adPlanManageInfo.blackIps }</textarea>
			<div class="clear"></div>
		</div>
	</div>
    <div class="alert alert-info" style="width: 60%;margin-top:15px;">
        <strong>说明:</strong> ip定向 正向ip为可投放ip，负向ip为不投放ip.
        每行输入一个ip,以回车换行.
        <div class="clear"></div>
    </div>

</fieldset>

<%-- <fieldset>
    <legend> 背景页面定向 </legend>
    每行输入一个url <br /><br />
    <textarea style="resize: none; width: 80%; height: 240px;"
              id="backUrlSite" name="backUrlSite">${adPlanManageInfo.backUrlSite}</textarea>
    <br />
    <br />
    <div class="alert alert-info" style="width: 60%">

        <strong>说明:</strong> 站点定向用来定位访问特定页面的访客.
        每行输入一个url,以回车换行.
        例如：输入http://www.hexun.com/，则只在url中含有http://www.hexun.com/的页面上展现广告。

    </div>

</fieldset> --%>

<fieldset>
    <legend> 广告创意 </legend>
    <input type="hidden" value="${adPlanManageInfo.adInstanceDto.linkType }" name="adInstanceDto.linkType" id="adInstanceDto.linkType"></input>
    <ul class="nav nav-tabs">
		<li   id="linkTypeM"><a href="#home" data-toggle="tab" >广告物料</a></li>
		<li   id="linkTypeE"><a href="#profile" data-toggle="tab">投放链接</a></li>
		<li   id="linkTypeJ"><a href="#profile" data-toggle="tab">JS代码</a></li>
	</ul>
		<div id="linkType1" style="display: <c:if test="${adPlanManageInfo.adInstanceDto.linkType == 'M' }">block</c:if><c:if test="${adPlanManageInfo.adInstanceDto.linkType != 'M' }">none</c:if>;">
				<div class="" id="selectedDiv">
		        <input type="hidden" name="adMaterialIds" id="adMaterialIds"/>
		        <label class="">已选广告物料：</label>
		        <div class="row-fluid sortable">
		            <div class="box span6">
		                <table class="table">
		                    <tbody id="selectedMaterial">
		                    <c:forEach var="m" items="${materialList}">
		                        <tr>
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
		                                ${m.materialValue}
		                            </td>
		                            <td class="center">
		                                <a href="javascript:;" id="${m.id}" class="edit">修改</a>&nbsp;<a href="javascript:;" class="del">移除</a>
		                            </td>
		                        </tr>
		                    </c:forEach>
		                    </tbody>
		                </table>
		            </div>
		        </div>
		    </div>
		     <div class="row-fluid">
	        <!-- <button type="button" id="addMaterialBtn" class="btn btn-primary" >新建广告物料</button>&nbsp; -->
	        <button type="button" id="selectMaterialBtn" class="btn btn-primary">从广告物料库选择</button>
	        <span id="materialTips" class="help-inline" style="display:none; color: #bd4247"></span>
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
		</div>
    	    <div class="" id="linkType2" style="display: <c:if test="${adPlanManageInfo.adInstanceDto.linkType == 'E' }">block</c:if><c:if test="${adPlanManageInfo.adInstanceDto.linkType != 'E' }">none</c:if>;">
			    		<div class="control-group" id="throwUrlPOS">
					        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>投放链接：</label>
					        <div class="controls">
					            <input class="input-xlarge focused" id="adExtLink.throwUrl"
					                   name="adExtLink.throwUrl" type="text" value="${adExtLink.throwUrl }"/>
					            <span id="throwUrlTips" class="help-inline" style="display:none"></span>
					        </div>
					    </div>
                    	 <div class="control-group">
							<label class="control-label" for="selectError">规格：</label>
							<div class="controls">
								<select id="pixel" data-rel="chosen" name="adExtLink.picSize" >
									<c:if test="${not empty meterSizeList}">
										<c:forEach var="obj" items="${meterSizeList}" varStatus="status">
											<c:if test="${obj.attrValue == adExtLink.picSize}">
												<option value="${obj.attrCode}" selected="selected">
														${obj.attrValue}	
												</option>
											</c:if>
											<c:if test="${obj.attrValue != adExtLink.picSize}">
												<option value="${obj.attrCode}">
														${obj.attrValue}	
												</option>
											</c:if>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
										
    </div>
    <div class="" id="linkType3" style="display: <c:if test="${adPlanManageInfo.adInstanceDto.linkType == 'J' }">block</c:if><c:if test="${adPlanManageInfo.adInstanceDto.linkType != 'J' }">none</c:if>;">
			    		  <div class="control-group" id="jsContentPOS">
						        <label class="control-label" for="focusedInput"><b style="color: #FF0000;">*</b>JS代码：</label>
						        <div class="controls">
						            <!-- <input class="input-xlarge focused" id="adExtLink.throwUrl"
						                   name="adExtLink.throwUrl" type="text"/> -->
						            <textarea style="resize: none; width: 60%; height: 180px;" id="jsContent" name="jsContent">${adPlanManageInfo.jsContent }</textarea>       
						            <span id="jsContentTips" class="help-inline" style="display:none"></span>
						        </div>
						    </div>
						       <div class="control-group">
								<label class="control-label" for="selectError"><b style="color: #FF0000;">*</b>广告展示位置：</label>
								<div class="controls">
									<div class="input_prepend input_append">
								 <div class="radio">
									<span> 
										<input type="radio" size="8" value="L" name="jsType" id="radioL"/>
									 </span>
								</div> 
								<div style="float:left"><span class="radio" style="line-height:20px;color: #666;font-size: 12px;margin-right:15px;">原始位置(左上角)</span></div>
								 <div>
									<span > 
										<input type="radio" size="8" value="J" name="jsType" id="radioJ"/>
									</span>
								</div> 
								<div style="float:left"><span class="radio" style="line-height:20px;color: #666;font-size: 12px;">绝对位置(右下角)</span></div>
								<span class="help_inline"></span>
							</div>
								</div>
							</div>
						    <div class="control-group">
								<label class="control-label" for="selectError">规格：</label>
								<div class="controls">
									<select id="" data-rel="chosen" name="jsSize">
										<c:if test="${not empty meterSizeList}">
											<c:forEach var="obj" items="${meterSizeList}" varStatus="status">
											<c:if test="${obj.attrValue == adPlanManageInfo.jsSize}">
												<option value="${obj.attrCode}" selected="selected">
														${obj.attrValue}	
												</option>
											</c:if>
											<c:if test="${obj.attrValue != adPlanManageInfo.jsSize}">
												<option value="${obj.attrCode}">
														${obj.attrValue}	
												</option>
											</c:if>
										</c:forEach>
										</c:if>
									</select>
								</div>
							</div>
    </div>
    
    <div class="alert alert-info" style="width: 60%;margin-top:15px;">
        <strong>说明:</strong> 广告物料：既为上传图片、flash、小视频到本系统，由本系统来管理素材，投放链接：既为客户提供的广告url，并指定尺寸，必须是固定位置的(如果是移动端的要铺开)。
        js代码：客户提供的js代码，原始位置(左上角) 要求是固定位置的js代码，  绝对位置(右下角)是绝对位置的js代码。
        <div class="clear"></div>
    </div>
    
</fieldset>

<c:if test="${adPlanManageInfo.adInstanceDto.adType==1 }">

<fieldset id="mobileDeviceTarget">
</c:if>


<c:if test="${adPlanManageInfo.adInstanceDto.adType==0 }">

<fieldset id="mobileDeviceTarget" style="display: none;">
</c:if>

    <legend>手机操作系统</legend>
    <div class="control-group">
    	<div class="input-prepend input-append">
    	
    	
    	
    	
    	<c:forEach items="${adPlanManageInfo.adDeviceLinkList}" var="adDevDto">
    		
    	
    		<input name ="adDevDtoHidenVal" type="hidden" value="${ adDevDto.adDeviceId}"></input>
    	
    	</c:forEach>
    	
    	<table>
    	<tr>
    		<td>
    		
    			<input name="adDeviceIds" type="checkbox"   value="1" />Android 
    		</td>
    		
    		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
    	   
    		<td>
    			<input name="adDeviceIds" type="checkbox"   value="2" />IOS
    		</td>
    	</tr>
    	</table>
              
         <span id="dayBudgetTips" class="help-inline" style="display:none"></span>
        </div>
    </div>
    
     <div class="alert alert-info" style="width: 60%;margin-top:15px;">
        <strong>说明:</strong> 移动端的广告要选择操作系统
        <div class="clear"></div>
    </div>
</fieldset>

<!-- 媒体分类删除 -->
<%-- <fieldset>
    <legend> 媒体分类 </legend>
    <div class="" style="" id="selectedDiv">
        <input type="hidden" name="mediaCategoryCodes" id="mediaCategoryCodes"/>
        <div class="row-fluid sortable">
            <div class="box span6">
                <table>
                    <thead>
                    <tr>
                        <th width="45%">从以下分类中选择（双击可选大类）</th>
                        <th  width="10%" align="center"></th>
                        <th  width="45%"><a style="cursor: pointer;" id="selAll">全选</a>/<a id="cancelSelAll" style="cursor: pointer;">全不选</a></th>
                    </tr>
                    </thead>
                    <tbody id="">
                    <tr>
                        <td>
                            <select id="mediaCateSel"  class="mediaCateOp"  multiple="multiple"  style="height:200px;" >
                                <c:if test="${not empty mediaCategoryDtoLeveOneList}">
                                    <c:forEach var="mcLeveOne" items="${mediaCategoryDtoLeveOneList}">
                                        <option value="${mcLeveOne.code}">${mcLeveOne.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </td>
                        <td style="text-align: center;">
                            <img src="/resources/img/star_06.png"/>
                        </td>
                        <td>
                            <div id="mediaCateLeveTwoDiv" style="border: 1px solid  rgb(96,96,96);height: 200px;width: 95%;">

                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            已选择媒体分类
                            <div style="height: 120px;overflow: auto; border: 1px solid  rgb(96,96,96);padding: 5px;">
                                <div id="mediaCateSelectedDiv" style="height:100px;width: 95%;" >

                                </div>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</fieldset> --%>

<%--  删除广告分类
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
                        <th  width="10%" align="center" style="text-align: center;"></th>
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
 --%>
<fieldset>
    <legend> 区域定向 </legend>
    <div class="" style="" id="region_selectedDiv">
        <input type="hidden" name="regionCodes" id="regionCodes"/>
        <div class="row-fluid sortable">
            <div class="box span6">
                <table>
                    <thead>
                    <tr>
                        <th width="45%">从以下省份中选择（双击可选）</th>
                        <th  width="10%" align="center" style="text-align: center;"></th>
                        <th  width="45%"><a style="cursor: pointer;" id="region_selAll">全选</a>/<a id="region_cancelSelAll" style="cursor: pointer;">全不选</a></th>
                    </tr>
                    </thead>
                    <tbody id="">
                    <tr>
                        <td>
                            <select id="region_mediaCateSel"  class="region_mediaCateOp"  multiple="multiple"  style="height:200px;" >
                                <c:if test="${not empty regionList}">
                                    <c:forEach var="mcLeveOne" items="${regionList}">
                                        <option value="${mcLeveOne.code}" >
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
                            <div id="region_mediaCateLeveTwoDiv" style="border: 1px solid  rgb(96,96,96);height: 200px;width: 95%;">

                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            已选择市
                            <div style="height: 120px;overflow: auto; border: 1px solid  rgb(96,96,96);padding: 5px;">
                                <div id="region_mediaCateSelectedDiv" style="height:100px;width: 95%;" >

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
<fieldset>

    <legend>第三方统计代码</legend>
    <textarea style="resize: none; width: 60%; height: 180px;"
              id="adInstanceDto.ad3statCode" name="adInstanceDto.ad3statCode"
            >${adPlanManageInfo.adInstanceDto.ad3statCode}</textarea>
    <br />
    <br />
</fieldset>


<%-- <fieldset>

    <legend>第三方统计代码_辅助</legend>
    <textarea style="resize: none; width: 60%; height: 180px;"
              id="adInstanceDto.ad3statCodeSub" name="adInstanceDto.ad3statCodeSub">${adPlanManageInfo.adInstanceDto.ad3statCodeSub}</textarea>
    <br />
    <br />
</fieldset>
 --%>

<div class="form-actions">
    <button type="button" class="btn btn-primary" id="adplaneditsave">保存</button>
    <input class="btn" type="button" value="返回" id = "back"></input>
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

<div class="modal" id="crowdNewModal" style="display: none;">

    <div class="modal-header">
        <button type="button" class="close" >×</button>
        <h3>新建受众人群</h3>
    </div>


    <div class="modal-body">

    </div>

    <div class="modal-footer">
        <a href="#" class="btn" >关闭</a>
    </div>

</div>

<!-- <div class="modal hide" id="addMaterialModal" >
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
</div> -->

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
<div id="oldSelectedMediaDiv" style="display: none;">
    <c:if test="${not empty adMediaCategoryDtoList}">
        <c:forEach var="selecetedMedia" items="${adMediaCategoryDtoList}">
            <input name="selectedMediaInput" value="${selecetedMedia.code}" parentCode="${selecetedMedia.parentCode}"/>
        </c:forEach>
    </c:if>
</div>

<div id="adCategory_oldSelectedMediaDiv" style="display: none;">
    <c:if test="${not empty adCategoryDtoList}">
        <c:forEach var="selecetedMedia" items="${adCategoryDtoList}">
            <input name="adCategory_selectedMediaInput" value="${selecetedMedia.id}" parentCode="${selecetedMedia.parentCode}"/>
        </c:forEach>
    </c:if>
</div>

<div id="region_oldSelectedMediaDiv" style="display: none;">
    <c:if test="${not empty adRegionList}">
        <c:forEach var="obj" items="${adRegionList}">
            <input name="region_selectedMediaInput" value="${obj.code}" parentCode="${obj.parentCode}"/>
        </c:forEach>
    </c:if>
</div>
<%@include file="../inc/footer.jsp"%>
</body>

<script>
<!--checkbox isUniform 相关 wx 2014-11-07 pm-->
/*checkbox isUniform onclick事件 */
 regx = /^(\+)?\d+$/; //判断是否是正整数+0
 function checked_isUniform(data){
	 var $checked_uniform = $("#adTimeFrequencyDto\\.isUniform").val();
	 var $dayLimit =  $("#adTimeFrequencyDto\\.dayLimit").val();
	 var minLimit = parseInt(parseInt($dayLimit)/600);
	 if(data){
		 $("#adTimeFrequencyDto\\.minLimit").removeAttr("readonly");
		 $("#adTimeFrequencyDto\\.isUniform").attr("checked",true);
		 if($.trim($dayLimit) == ''&&!regx.test($.trim($dayLimit))){
			 $("#adTimeFrequencyDto\\.minLimit").val(0);
		 	alert(minLimit);
		 }else{
			 $("#adTimeFrequencyDto\\.minLimit").val(minLimit);
		 }
       	$("#minLimitTips").parent().parent().removeClass("error");
           $("#minLimitTips").hide();
		 $("#adTimeFrequencyDto\\.isUniform").val(1);
	 }else{
		 $("#adTimeFrequencyDto\\.isUniform").attr("checked",false);
		 $("#adTimeFrequencyDto\\.minLimit").attr("readonly","readonly");
		 $("#adTimeFrequencyDto\\.isUniform").val(0);
		 $("#adTimeFrequencyDto\\.minLimit").val(0);
		 $("#minLimitTips").parent().parent().removeClass("error");
         $("#minLimitTips").hide();
	 } 
 }
	 /* isUniform_checked_ModifyMinLimit  add by 2014-11-7 13:38:24 checkbox is_Uniform选中后 动态修改每分钟最高投放量  */
	function isUniform_checked_ModifyMinLimit(){
	var $checked_uniform = $("#adTimeFrequencyDto\\.isUniform").val();
		 var $dayLimit =  $("#adTimeFrequencyDto\\.dayLimit").val();
		 var minLimit = parseInt(parseInt($dayLimit)/600);
		 if($checked_uniform == 0){
			 $("#adTimeFrequencyDto\\.minLimit").attr("readonly","readonly");
			 /* $("#adTimeFrequencyDto\\.isUniform").val(0); */
			 $("#adTimeFrequencyDto\\.minLimit").val(0);
		 }else{
			 $("#adTimeFrequencyDto\\.minLimit").removeAttr("readonly");
	       	$("#adTimeFrequencyDto\\.minLimit").val(minLimit);
	       	$("#minLimitTips").parent().parent().removeClass("error");
	           $("#minLimitTips").hide();
		 }    	  
	}
</script>

<script>
    <!--当输入内容获取焦点是 事件   周晓明 -->
    $(document).ready(function(){
    	var site = '${adPlanManageInfo.whiteAdSites }';
    	 //jsType 的选择
		   	 var jsType = '${adPlanManageInfo.jsType}';
		   	 if(jsType == 'J'){
		   		 $("#radioJ").attr("checked","true");
		   		 $("#radioJ").parent().addClass("checked");
		   		 $("#radioL").parent().removeClass();
		   	 }else if(jsType == 'L'){
		   		 $("#radioL").attr("checked","true");
		   		 $("#radioL").parent().addClass("checked");
		   		 $("#radioJ").parent().removeClass();
		   	 }
		   	 /*************************************************/
    	var linkType = '${adPlanManageInfo.adInstanceDto.linkType }';
    	if(linkType == 'M'){
    		$("#linkTypeM").addClass("active");
    	}else if(linkType == 'E'){
    		$("#linkTypeE").addClass("active");
    	}else if(linkType == 'J'){
    		$("#linkTypeJ").addClass("active");
    	}
        /* var $startTime = $("#adInstanceDto\\.startTime").val();
         var $endTime = $("#adInstanceDto\\.endTime").val();
         if($.trim($startTime) != ''){
         $("#startTimeTips").parent().parent().removeClass("error");
         $("#startTimeTips").hide();
         $("#esTimeTips").parent().parent().removeClass("error");
         $("#esTimeTips").hide();
         }
         if($.trim($endTime) != ''){
         $("#endTimeTips").parent().parent().removeClass("error");
         $("#endTimeTips").hide();
         $("#esTimeTips").parent().parent().removeClass("error");
         $("#esTimeTips").hide();
         } */     
        
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
        /* $("#adTimeFrequencyDto\\.putIntervalNum").focus(function(){
            $("#putIntervalNumTips").parent().parent().removeClass("error");
            $("#putIntervalNumTips").hide();
        }); */
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
      //独立IP展现次数
        $("#adUserFrequencyDto\\.uidIpNum").focus(function(){
            $("#uidIpNumTips").parent().parent().removeClass("error");
            $("#uidIpNumTips").hide();
        });

        /* //预算
        $("#adInstanceDto\\.allBudget").focus(function(){
            $("#allBudgetTips").parent().parent().parent().removeClass("error");
            $("#allBudgetTips").hide();
        });

        //日预算
        $("#adInstanceDto\\.dayBudget").focus(function(){
            $("#dayBudgetTips").parent().parent().parent().removeClass("error");
            $("#dayBudgetTips").hide();
        }); */
        
        
        /* //最高出价
        $("#max_cpm_bidprice").focus(function(){
            $("#max_cpm_bidpriceTips").parent().parent().parent().removeClass("error");
            $("#max_cpm_bidpriceTips").hide();
        }); */
        
      //关键词
        $("#keyWd").focus(function(){
        	$("#keywordTips").parent().parent().parent().parent().removeClass("error");
        	$("#keywordTips").hide();
        });
     	 //投放链接 
        $("#adExtLink\\.throwUrl").focus(function(){
       	 $("#throwUrlTips").parent().parent().removeClass("error");
            $("#throwUrlTips").hide();
        });
        //JS代码
        $("#jsContent").focus(function(){
       	 $("#jsContentTips").parent().parent().removeClass("error");
            $("#jsContentTips").hide();
        });
        //单价 
        $("#adInstanceDto\\.unitPrice").focus(function(){
           $("#unitPriceTips").parent().parent().parent().removeClass("error");
           $("#unitPriceTips").hide();
       }); 
        //初始化兴趣标签树
		initIntrestLabelTree();
        //点击绑定事件
         initAllLabelEvernt();
         $("#computeCrowdBtn").click();
         
 /*******************************************人群画像定向的初始化********ssq*********************************************/      
         var ageRanges = "${portrait.ageRange}";
         var ageRange = ageRanges.split(",");
         for(var i=0;i<ageRange.length;i++){
             $("input[name='adPlanPortrait.ageRange']").each(function(){
            	 if(ageRange[i]==$(this).attr("value")){
            		 $(this).parent().attr("class","checked");
            		 $(this).attr("checked","checked");
            	 }
             });
         }
 /********************************************************************************************************/
    }).on("click","#linkTypeM",function(){
    	$("#adInstanceDto\\.linkType").val('M');
    	$("#linkType2").hide();
    	$("#linkType3").hide();
    	$("#linkType1").show();
    }).on("click","#linkTypeE",function(){
    	$("#adInstanceDto\\.linkType").val('E');
    	$("#linkType1").hide();
    	$("#linkType3").hide();
    	$("#linkType2").show();
    }).on("click","#linkTypeJ",function(){
    	$("#adInstanceDto\\.linkType").val('J');
    	$("#linkType1").hide();
    	$("#linkType2").hide();
    	$("#linkType3").show();
    }).on("change","#adInstanceDto\\.chargeType", function(){
    	var type = $(this).children('option:selected').attr("value");
    	if(type=="1"){
    		$("#danjia").text("/cpm");
    	}else if(type == "2"){
    		$("#danjia").text("/cpc");
    	}
    
   	}).on("click","#cancelupload",function(){
    	$("#kwfilepath").parent().find("span").each(function(){
	    	   if($(this).attr("class")=="filename"){
	    		   $(this).text("No file selected");
	    		   $("#kwFileName").val("");
	    		   $("#kwfilepath").val("");
	    	   }
	       });
    }).on("click","#downloadkw",function(){
    	/* var adPlanId = $("#adPlanId").val();   
    	$.ajax({
    		url:'/adplan/kwFileDownload',
    	    data:{adPlanId:adPlanId},
    	    async:false,
            type:'POST',
            success:function(data){
                if(data=='0'){
                    alert("文件已成功下载到D盘!!");
                }else{
                    alert("下载失败");
                }
            }    	    
    	}); */
    	$("#downkwform").submit();
    }).on("click","#clearkw",function(){
    	$("#keyWd").val("");
    });
</script>
<script>
    <!--验证输入框的内容是否合法，是否为空    周晓明-->
    $(document).ready(function(){
    	//投放链接 
        $("#adExtLink\\.throwUrl").focusout(function(){
            var $throwUrl = $("#adExtLink\\.throwUrl").val();
            if($.trim($throwUrl) == ''){
                $("#throwUrlTips").text("投放链接不能为空");
                $("#throwUrlTips").parent().parent().addClass("error");
                $("#throwUrlTips").show();
            }
        });
    	
      //JS代码 
    	$("#jsContent").focusout(function(){
            var $jsContent = $("#jsContent").val();
            if($.trim($jsContent) == ''){
                $("#jsContentTips").text("JS代码不能为空");
                $("#jsContentTips").parent().parent().addClass("error");
                $("#jsContentTips").show();
            }
        });
        //广告名称
        $("#adInstanceDto\\.adName").focusout(function(){
            var $adName = $("#adInstanceDto\\.adName").val();
            if($.trim($adName) == ''){
                $("#adNameTips").text("广告名称不能为空");
                $("#adNameTips").parent().parent().addClass("error");
                $("#adNameTips").show();
            }
        });
     /*    var regx = /^(\+|-)?\d+$/; //判断是否是正整数 */
        regx = /^(\+)?\d+$/; //判断是否是正整数+0
        //时间段
       /*  $("#adTimeFrequencyDto\\.putIntervalNum").focusout(function(){
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
        }); */
        //每日最高投放量
        $("#adTimeFrequencyDto\\.dayLimit").focusout(function(){
            var $dayLimit = $("#adTimeFrequencyDto\\.dayLimit").val();
            if($.trim($dayLimit) == ''){
                $("#dayLimitTips").text("每日期望PV量不能为空");
                $("#dayLimitTips").parent().parent().addClass("error");
                $("#dayLimitTips").show();
                $("#adTimeFrequencyDto\\.minLimit").val(0);
                $("#minLimitTips").parent().parent().removeClass("error");
                $("#minLimitTips").hide();
            }else if(!regx.test($.trim($dayLimit))){
                $("#dayLimitTips").text("每日期望PV输入不合法");
                $("#dayLimitTips").parent().parent().addClass("error");
                $("#dayLimitTips").show();
                $("#adTimeFrequencyDto\\.minLimit").val(0);
                $("#minLimitTips").parent().parent().removeClass("error");
                $("#minLimitTips").hide();
            }else{
            	isUniform_checked_ModifyMinLimit();
            	/* $("#adTimeFrequencyDto\\.minLimit").val(0); */
	       		/* var minLimit = parseInt(parseInt($dayLimit)/600);
            	$("#adTimeFrequencyDto\\.minLimit").val(minLimit);
            	$("#minLimitTips").parent().parent().removeClass("error");
                $("#minLimitTips").hide(); */
            }
        });

      //每分钟最高投放量
        $("#adTimeFrequencyDto\\.minLimit").focusout(function(){
            var $minLimit = $("#adTimeFrequencyDto\\.minLimit").val();
            if($.trim($minLimit) == ''){
                $("#minLimitTips").text("每分钟最高投放量不能为空");
                $("#minLimitTips").parent().parent().addClass("error");
                $("#minLimitTips").show();
            }else if(!regx.test($.trim($minLimit))){
                $("#minLimitTips").text("每分钟最高投放量输入不合法");
                $("#minLimitTips").parent().parent().addClass("error");
                $("#minLimitTips").show();
            } else{
            	 $("#minLimitTips").parent().parent().removeClass("error");
                 $("#minLimitTips").hide();
	       		/* var minLimit = parseInt(parseInt($dayLimit)/600);
          
            	$("#adTimeFrequencyDto\\.minLimit").val(minLimit);
            	$("#minLimitTips").parent().parent().removeClass("error");
                $("#minLimitTips").hide(); */
            } 
        });
        //单价 
       	/* regx_price = /[0-9]+/;  */
        /* regx_price =/^(\+)?[0-9]+$/; */
         regx_price = /^\d+(\.\d{1,2})?$/;//0-9整数，或带1-2位小数的浮点数
         $("#adInstanceDto\\.unitPrice").focusout(function(){
            var $unitPrice = $("#adInstanceDto\\.unitPrice").val();
            if($.trim($unitPrice) == ''){
                $("#unitPriceTips").text("单价不能为空");
                $("#unitPriceTips").parent().parent().parent().addClass("error");
                $("#unitPriceTips").show();
            }else if (!regx_price.test($.trim($unitPrice))){
                $("#unitPriceTips").text("单价输入不合法");
                $("#unitPriceTips").parent().parent().parent().addClass("error");
                $("#unitPriceTips").show();
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
      //独立IP展现次数
        $("#adUserFrequencyDto\\.uidIpNum").focusout(function(){
            var $uidImpressNum = $("#adUserFrequencyDto\\.uidIpNum").val();
            if($.trim($uidImpressNum) == ''){
                $("#uidIpNumTips").text("独立IP展现次数不能为空");
                $("#uidIpNumTips").parent().parent().addClass("error");
                $("#uidIpNumTips").show();
            }else if (!regx.test($.trim($uidImpressNum))){
                $("#uidIpNumTips").text("独立IP展现次数输入不合法");
                $("#uidIpNumTips").parent().parent().addClass("error");
                $("#uidIpNumTips").show();
            }
        });

        //预算
        /* regx_price = /[0-9]+/; */
        /* regx_price =/^(\+)?[0-9]+$/; */
       /*   regx_price = /^\d+(\.\d{1,2})?$/;//0-9整数，或带1-2位小数的浮点数
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
         */
    });

</script>
<script>
    //聚焦错误位置 函数
    function moveHtml(id){
        var scroll_offset = $(id).offset().top;
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
        /* regx_price = /[0-9]+.[0-9]{2}/; */
        /* regx_price = /[0-9]+/; */
        regx_price = /^\d+(\.\d{1,2})?$/;//0-9整数，或带1-2位小数的浮点数
        regx = /^(\+|-)?\d+$/; //判断是否是正整数
        var $adName = $("#adInstanceDto\\.adName").val();
        var $startTime = $("#adInstanceDto\\.startTime").val();
        var $endTime = $("#adInstanceDto\\.endTime").val();
 //     var $putIntervalNum = $("#adTimeFrequencyDto\\.putIntervalNum").val();
        var $dayLimit = $("#adTimeFrequencyDto\\.dayLimit").val();
        var $minLimit = $("#adTimeFrequencyDto\\.minLimit").val();
        var $uidImpressNum = $("#adUserFrequencyDto\\.uidImpressNum").val();
        var $uidIpNum = $("#adUserFrequencyDto\\.uidIpNum").val();
        /*
          var $dayBudget = $("#adInstanceDto\\.dayBudget").val();
          var $allBudget = $("#adInstanceDto\\.allBudget").val();
        */
        var $unitPrice = $("#adInstanceDto\\.unitPrice").val();
        var $linkType = $("#adInstanceDto\\.linkType").val();
        var $throwUrl = $("#adExtLink\\.throwUrl").val();
        var $jsContent = $("#jsContent").val();
        var $adMaterialIds = $("#adMaterialIds").val();
        
        //var channel = $("#adInstanceDtoChannel").val();
        //var $max_cpm_bidprice = $("#max_cpm_bidprice").val();

        //关键词上传验证
        var adId = "${adPlanManageInfo.adInstanceDto.adId }";
        var keywords = $("#keyWd").val().replace(/[\r\n]/g,"");
        var kwfilepath = $("#kwfilepath").val();
        var kwFileName = $("#kwFileName").val();
        if(keywords!=''&&(kwfilepath!=''||kwFileName!='')){
        	alert("您只能选择一种方式来输入关键词!!!");
        	flag=false;
        }
        if(keywords==''&&kwfilepath!=''){      	
           var filetypes = kwfilepath.split(".");
           if($.trim(kwfilepath) !=''&&filetypes[1]!="txt"){
               alert("您上传的关键词文件格式不正确,请上传txt格式的文件");
        	   flag=false;
           }
           var type="2";
           if($.trim(kwfilepath)!=''&&filetypes[1]=="txt"){      	
        	  $.ajax({
        	    url:'/adplan/checkkw',
        	    data:{kwfilepath:kwfilepath,adId:adId,type:type},
        	    async:false,
   	            type:'POST',
   	            success:function(data){
        		   if(data=='0'){
        			   alert("您上传的关键词文件已存在,请修改文件名!!!");
        			   flag=false;
        		   }else{
        			   $("#kwFileName").val(kwfilepath);       
        		   }
        	    }
        	  });
           }
        }
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
        
        if($.trim($uidIpNum) == ''){
	        $("#uidIpNumTips").text("独立IP展现次数不能为空");
            $("#uidIpNumTips").parent().parent().addClass("error");
            $("#uidIpNumTips").show();
            moveHtml("uidIpNumPos");
            flag = false;
        }else if (!regx.test($.trim($uidIpNum))){
            $("#uidIpNumTips").text("独立IP展现次数输入不合法");
            $("#uidIpNumTips").parent().parent().addClass("error");
            $("#uidIpNumTips").show();
            moveHtml("uidIpNumPos");
            flag = false;
        }
        
        
        if($.trim($uidImpressNum) == ''){
	        $("#uidImpressNumTips").text("独立访客展现次数不能为空");
            $("#uidImpressNumTips").parent().parent().addClass("error");
            $("#uidImpressNumTips").show();
            moveHtml("uidImpressNumPos");
            flag = false;
        }else if (!regx.test($.trim($uidImpressNum))){
            $("#uidImpressNumTips").text("独立访客展现次数输入不合法");
            $("#uidImpressNumTips").parent().parent().addClass("error");
            $("#uidImpressNumTips").show();
            moveHtml("uidImpressNumPos");
            flag = false;
        }else if($.trim($adName) == ''){
        	//alert("a")
            $("#adNameTips").text("广告名称不能为空");
            $("#adNameTips").parent().parent().addClass("error");
            $("#adNameTips").show();
            moveHtml("#adInstanceDto\\.adName");
            flag = false;
        }else if($.trim($startTime) == ''){
        	//alert("b")
            $("#startTimeTips").text("起始时间不能为空");
            $("#startTimeTips").parent().parent().addClass("error");
            $("#startTimeTips").show();
            moveHtml("#adInstanceDto\\.startTime");
            flag = false;
        }else if($.trim($endTime) == ''){
        	
        	//alert("c")
            $("#endTimeTips").text("结束时间不能为空");
            $("#endTimeTips").parent().parent().addClass("error");
            $("#endTimeTips").show();
            moveHtml("#adInstanceDto\\.startTime");
            flag = false;
        }else if($.trim($startTime) != '' && !$.trim($endTime) == '' && $endTime < $startTime){
        	//alert("d")
            $("#esTimeTips").text("结束时间不能小于起始时间");
            $("#esTimeTips").parent().parent().addClass("error");
            $("#esTimeTips").show();
            moveHtml("#adInstanceDto\\.startTime");
            flag = false;
        }/* else if($.trim($putIntervalNum) == ''){
        	//alert("e")
            $("#putIntervalNumTips").text("时间段不能为空");
            $("#putIntervalNumTips").parent().parent().addClass("error");
            $("#putIntervalNumTips").show();
            moveHtml("#DocPos");
            flag = false;
        } */
        /* else if(!regx.test($.trim($putIntervalNum))){
        	//alert("f")
            $("#putIntervalNumTips").text("时间段输入不合法");
            $("#putIntervalNumTips").parent().parent().addClass("error");
            $("#putIntervalNumTips").show();
            moveHtml("#DocPos");
            flag = false;
        } */
        else if ($.trim($unitPrice) == '') {
			$("#unitPriceTips").text("单价不能为空");
			$("#unitPriceTips").parent().parent().parent().addClass("error");
			$("#unitPriceTips").show();
			moveHtml("#adInstanceDto\\.startTime");
			flag = false;
		} else if (!regx_price.test($.trim($unitPrice))) {
			$("#unitPriceTipss").text("单价输入不合法");
			$("#unitPriceTips").parent().parent().parent().addClass("error");
			$("#unitPriceTips").show();
			moveHtml("#adInstanceDto\\.startTime");
			flag = false;
		}else if($.trim($dayLimit) == ''){
        	//alert("g")
            $("#dayLimitTips").text("每日期望PV不能为空");
            $("#dayLimitTips").parent().parent().addClass("error");
            $("#dayLimitTips").show();
            moveHtml("#adInstanceDto\\.startTime");
            flag = false;
        }else if(!regx.test($.trim($dayLimit))){
        	//alert("h")
            $("#dayLimitTips").text("每日期望PV输入不合法");
            $("#dayLimitTips").parent().parent().addClass("error");
            $("#dayLimitTips").show();
            moveHtml("#adInstanceDto\\.startTime");
            flag = false;
        }else if($.trim($minLimit) == ''){
    	 //alert("j")
         $("#minLimitTips").text("每分钟最高投放量不能为空");
         $("#minLimitTips").parent().parent().addClass("error");
         $("#minLimitTips").show();
         moveHtml("#namePOS1");
         flag = false;
     }else if(!regx.test($.trim($minLimit))){
    	 //alert("k")
         $("#minLimitTips").text("每分钟最高投放量输入不合法");
         $("#minLimitTips").parent().parent().addClass("error");
         $("#minLimitTips").show();
         moveHtml("#namePOS1");
         flag = false;
     }else if($linkType == 'E'){
     	if($.trim($throwUrl) == ''){
   		 $("#throwUrlTips").text("投放链接不能为空 ");
            $("#throwUrlTips").parent().parent().addClass("error");
            $("#throwUrlTips").show();
            moveHtml("#linkType2");
            flag = false;
   		}
	 }else if($linkType == 'J'){
     	if($.trim($jsContent) == ''){
     		 $("#jsContentTips").text("JS代码不能为空");
     		 $("#jsContentTips").parent().parent().addClass("error");
            $("#jsContentTips").show();
            moveHtml("jsContentPOS");
            flag = false;
      	}
  	 }else if($linkType == 'M'){
		   	if($.trim($adMaterialIds) == ''){
		  		 $("#materialTips").text("请选择广告物料 ");
		           $("#materialTips").show();
		           moveHtml("#linkType1"); 
		           flag = false;
		   	}
	  	}
        /* }else if($.trim($allBudget) == ''){
            $("#allBudgetTips").text("预算不能为空");
            $("#allBudgetTips").parent().parent().parent().addClass("error");
            $("#allBudgetTips").show();
            moveHtml("#adInstanceDto\\.adName");
            flag = false;
        }else if(!regx_price.test($.trim($allBudget))){
            $("#allBudgetTips").text("预算输入不合法");
            $("#allBudgetTips").parent().parent().parent().addClass("error");
            $("#allBudgetTips").show();
            moveHtml("#adInstanceDto\\.adName");
            flag = false;
        }else if($.trim($dayBudget) == ''){
            $("#dayBudgetTips").text("日预算不能为空");
            $("#dayBudgetTips").parent().parent().parent().addClass("error");
            $("#dayBudgetTips").show();
            moveHtml("#adInstanceDto\\.adName");
            flag = false;
        }else if(!regx_price.test($.trim($dayBudget))){
            $("#dayBudgetTips").text("日预算输入不合法");
            $("#dayBudgetTips").parent().parent().parent().addClass("error");
            $("#dayBudgetTips").show();
            moveHtml("#adInstanceDto\\.adName");
            flag = false; */
        /* else if (channel=="1"&&$.trim($max_cpm_bidprice)!=''&&!regx_price.test($.trim($max_cpm_bidprice))){
            $("#max_cpm_bidpriceTips").text("最高出价不合法");
            $("#max_cpm_bidpriceTips").parent().parent().parent().addClass("error");
            $("#max_cpm_bidpriceTips").show();
            moveHtml("#channelDiv");
            flag = false;
        } */
        
        /* if(keywords!=""){
        	var keyword = keywords.split(" ");
        	if(keyword.length>0){
        		$("#keywordTips").text("   关键词格式输入不正确");
        		$("#keywordTips").parent().parent().parent().parent().parent().addClass("error");
        		$("#keywordTips").show();
        		moveHtml("namePOS");
        		flag = false;
        	}
        } */
		//alert(flag)
        return flag;
    }
</script>
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
            materialValue
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

/* 页面加载时初始化checkbox is_Uniform的选中与否的状态 2014-11-7 pm*/
$(document).ready(function(){
	 var $checked_uniform = $("#adTimeFrequencyDto\\.isUniform").val();
	 if($checked_uniform == 1){
		 /* alert($checked_uniform); */
		 /* var $checked_uniform_span =  $("#adTimeFrequencyDto\\.isUniform").parent('.class'); */
		  $("#adTimeFrequencyDto\\.isUniform").parent().attr("class","checked");		
		 $("#adTimeFrequencyDto\\.isUniform").attr("checked",true);
		 $("#adTimeFrequencyDto\\.minLimit").removeAttr("readonly");
	}else{
		 $("#adTimeFrequencyDto\\.isUniform").parent().removeAttr("class","checked");
		 $("#adTimeFrequencyDto\\.isUniform").attr("checked",false);
	 	//初始化checkbox选中状态后赋值
		 $("#adTimeFrequencyDto\\.minLimit").val(0); 
		}
/**********************************************计算***初始化关键词**ssq*****************************/
      
	  var keywds = "${adKeyword.keyWd}";
	  var kwType = "${adKeyword.kwType}";
	  if(keywds!=''&&kwType=="1"){
	     var keywd = keywds.split(",");
	     var keyword ="";
	     for(var i=0;i<keywd.length;i++){
	    	 keyword += keywd[i] +"\n";
	     }
	     $("#keyWd").val(keyword); 
	    
	     setTimeout(function(){ 	    	
	    	    $("#computeKeyWordBtn").click();
	     }, 10);
	  }else if(keywds!=''&&kwType=="2"){	 
	       $("#kwFileName").val(keywds);
	       $("#kwfilepath").parent().find("span").each(function(){
	    	   if($(this).attr("class")=="filename"){
	    		   $(this).text(keywds);
	    	   }
	       });
	  } 
	     
/*****************************************************************************************/
});

function renderIsUniform(){
	var $checked_uniform = $("#adTimeFrequencyDto\\.isUniform").val();
	alert($checked_uniform);
}

function renderHours(){
	 // 数据格式 workDay:true%start:1#end:2;weekendDay:true%start:1#end:2
    var str = document.getElementById("putHours").value;
    if (str!="") {
        var arr = str.split(";");
        // 如果不为空就 解析字符串
        if(arr[0] != null) {
            var subStr = arr[0];
            var subArr = subStr.split("%");
            var subArr1 = subArr[0].split(":");
            if(subArr1[1] == "true") {
                // 如果是工作日就操作工作日的控件
                if(subArr1[0] == "workDay") {
                     document.getElementById("cWork").checked = true;
                	 $("#cWork").parent().addClass("checked");
                    var subStr2 = subArr[1];
                    var subStr3 = subStr2.split("#");
                    var startIndex = parseInt(subStr3[0].split(":")[1]);
                    var endIndex = parseInt(subStr3[1].split(":")[1]);
                    $("#workDayStart_chzn > a:nth-child(1) > span:nth-child(1)").text(startIndex);
                    $("#workDayEnd_chzn > a:nth-child(1) > span:nth-child(1)").text(endIndex);
                    document.getElementById("workDayStart").selectedIndex = startIndex;
                    document.getElementById("workDayEnd").selectedIndex = endIndex;
                } else {
                    document.getElementById("cWeekend").checked = true;
                    $("#cWeekend").parent().addClass("checked");
                    var subStr2 = subArr[1];
                    var subStr3 = subStr2.split("#");
                    var startIndex = parseInt(subStr3[0].split(":")[1]);
                    var endIndex = parseInt(subStr3[1].split(":")[1]);
                    $("#weekendDayStart_chzn > a:nth-child(1) > span:nth-child(1)").text(startIndex);
                    $("#weekendDayEnd_chzn > a:nth-child(1) > span:nth-child(1)").text(endIndex);
                    document.getElementById("weekendDayStart").selectedIndex = startIndex;
                    document.getElementById("weekendDayEnd").selectedIndex = endIndex;
                }
            }
        }

        if(arr[1] != null) {
            var subStr = arr[1];
            var subArr = subStr.split("%");
            var subArr1 = subArr[0].split(":");
            if(subArr1[1] == "true") {
                // 如果是工作日就操作工作日的控件
                if(subArr1[0] == "workDay") {
                    document.getElementById("cWork").checked = true;
                	$("#cWork").parent().addClass("checked");
                    var subStr2 = subArr[1];
                    var subStr3 = subStr2.split("#");
                    var startIndex = parseInt(subStr3[0].split(":")[1]);
                    var endIndex = parseInt(subStr3[1].split(":")[1]);
                    $("#workDayStart_chzn > a:nth-child(1) > span:nth-child(1)").text(startIndex);
                    $("#workDayEnd_chzn > a:nth-child(1) > span:nth-child(1)").text(endIndex);
                    document.getElementById("workDayStart").selectedIndex = startIndex;
                    document.getElementById("workDayEnd").selectedIndex = endIndex;
                   
                } else {
                	$("#cWeekend").parent().addClass("checked");
                    document.getElementById("cWeekend").checked = true;
                    var subStr2 = subArr[1];
                    var subStr3 = subStr2.split("#");
                    var startIndex = parseInt(subStr3[0].split(":")[1]);
                    var endIndex = parseInt(subStr3[1].split(":")[1]);
                    $("#weekendDayStart_chzn > a:nth-child(1) > span:nth-child(1)").text(startIndex);
                    $("#weekendDayEnd_chzn > a:nth-child(1) > span:nth-child(1)").text(endIndex);
                    document.getElementById("weekendDayStart").selectedIndex = startIndex;
                    document.getElementById("weekendDayEnd").selectedIndex = endIndex;
                }
            }
        }
    }
}

function renderCrowdSeled(){
    $("#crowdSeled").find("a[name=crowdClose]").click(function(){
        $(this).parent().remove();
    });
}
//重定向。。。
function renderVisitorSeled(){
    $("#visitorSeled").find("a[name=visitorClose]").click(function(){
        $(this).parent().remove();
    });
}
//设备
function renderDeviceSeled(){
    $("#deviceSeled").find("a[name=deviceClose]").click(function(){
        $(this).parent().remove();
    });
}
//浏览器
function renderBrowserSeled(){
    $("#browserSeled").find("a[name=browserClose]").click(function(){
        $(this).parent().remove();
    });
}
//系统
function renderSystemSeled(){
    $("#systemSeled").find("a[name=systemClose]").click(function(){
        $(this).parent().remove();
    });
}

function timeValidater(id) {
	//$("#weekendDayStart_chzn > a:nth-child(1) > span:nth-child(1)").text(startIndex);
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

function getCrowdSelIds(){
    var crowdSeledIds = new Array();
    $("#crowdSeled input[name=crowdSeledId]").each(function(){
        var crowdSeledId = $(this).val();
        crowdSeledIds.push(crowdSeledId);
    });

    if(crowdSeledIds.length > 0 ){
        return crowdSeledIds.join(",");
    }else{
        return "";
    }

}

function getVisitorSelIds(){
    var visitorSeledIds = new Array();
    $("#visitorSeled input[name=visitorSeledId]").each(function(){
        var visitorSeledId = $(this).val();
        visitorSeledIds.push(visitorSeledId);
    });

    if(visitorSeledIds.length > 0 ){
        return visitorSeledIds.join(",");
    }else{
        return "";
    }

}
//设备
/* function getDeviceSelIds(){
    var deviceSeledIds = new Array();
    $("#deviceSeled input[name=deviceSeledId]").each(function(){
        var deviceSeledId = $(this).val();
        deviceSeledIds.push(deviceSeledId);
    });

    if(deviceSeledIds.length > 0 ){
        return deviceSeledIds.join(",");
    }else{
        return "";
    }

} */
//系统
/* function getSystemSelIds(){
    var systemSeledIds = new Array();
    $("#systemSeled input[name=systemSeledId]").each(function(){
        var systemSeledId = $(this).val();
        systemSeledIds.push(systemSeledId);
    });

    if(systemSeledIds.length > 0 ){
        return systemSeledIds.join(",");
    }else{
        return "";
    }

} */
//浏览器
/* function getBrowserSelIds(){
    var browserSeledIds = new Array();
    $("#browserSeled input[name=browserSeledId]").each(function(){
        var browserSeledId = $(this).val();
        browserSeledIds.push(browserSeledId);
    });

    if(browserSeledIds.length > 0 ){
        return browserSeledIds.join(",");
    }else{
        return "";
    }

}
 */
function initChannel() {
	 var index = $("#adInstanceDtoChannel").val();
     $("#adInstanceDtoChannel" + index).attr("checked","checked");
     $(" #uniform-adInstanceDtoChannel"+index+" > span:nth-child(1)").addClass("checked");
}

$(document)
        .ready(
        function() {
            initAdCate();//初始化广告分类
            adCategory_initMediaSelected();//初始化查询已选中广告分类

            initMediaSelected();//初始化查询已选媒体分类  add by sunly

            initRegion();//初始化地域
           region_initMediaSelected();
            //修改广告计划页面，初始化，给已选的受众人群绑定事件
            renderCrowdSeled();
            renderHours();
            
            // 初始化渠道选择信息
            initChannel();

            $("#crowdSBTab").hide();

            //弹出层不用 框架自带的 出现，隐藏。我们手动设置
            $("#crowdNewModal").hide();

            $("#crowdSel").click(function() {
                $("#crowdSBTab").toggle();

            });

            $("#crowdSBTab table tbody tr td:nth-child(1)").each(function() {

                $(this).find("input[name=crowdCheck]").click(function() {


                    var crowdSeledIds = new Array();
                    $("#crowdSeled input[name=crowdSeledId]").each(function(){
                        var crowdSeledId = $(this).val();
                        crowdSeledIds.push(crowdSeledId);
                    });


                    var crowdId = $(this).val();
                    var crowdName = $(this).attr("crowdname");

                    var crowdHtml = "<span class='label label-success' style='border-style:double;' >"+crowdName+"<a href='javascript:void(0);' name='crowdClose'>"
                            +"<span class='icon icon-orange icon-close'  ></span></a>"
                            + "<input type='hidden' name='crowdSeledId' value='"+crowdId+"'/></span>";

                    //不在被选择列表中的 crowd 才被加入到选中列表中
                    if($.inArray(crowdId, crowdSeledIds) < 0){
                        //添加这个元素，并为这个元素里面的a[name=crowdClose]的绑定点击事件，点击，当前span remove
                        $("#crowdSeled").append(crowdHtml);

                        $("#crowdSeled").find(" input[value="+crowdId+"]").parent().find("a[name=crowdClose]").click(function(){
                            $(this).parent().remove();
                        });
                    }



                });
            });


            //重定向人群
            /* renderVisitorSeled();
            $("#visitorSBTab").hide();
            $("#visitorSel").click(function() {
                $("#visitorSBTab").toggle();

            });
            $("#visitorSBTab table tbody tr td:nth-child(1)").each(function() {
                $(this).find("input[name=visitorCheck]").click(function() {
                    var visitorSeledIds = new Array();
                    $("#visitorSeled input[name=visitorSeledId]").each(function(){
                        var visitorSeledId = $(this).val();
                        visitorSeledIds.push(visitorSeledId);
                    });


                    var visitorId = $(this).val();
                    var visitorName = $(this).attr("visitorname");

                    var visitorHtml = "<span class='label label-success' style='border-style:double;' >"+visitorName+"<a href='javascript:void(0);' name='visitorClose'>"
                            +"<span class='icon icon-orange icon-close'  ></span></a>"
                            + "<input type='hidden' name='visitorSeledId' value='"+visitorId+"'/></span>";

                    //不在被选择列表中的 crowd 才被加入到选中列表中
                    if($.inArray(visitorId, visitorSeledIds) < 0){
                        //添加这个元素，并为这个元素里面的a[name=crowdClose]的绑定点击事件，点击，当前span remove
                        $("#visitorSeled").append(visitorHtml);

                        $("#visitorSeled").find(" input[value="+visitorId+"]").parent().find("a[name=visitorClose]").click(function(){
                            $(this).parent().remove();
                        });
                    }



                });
            }); */

            //浏览器
           /*  renderBrowserSeled();
            $("#browserSBTab").hide();
            $("#browserSel").click(function() {
                $("#browserSBTab").toggle();

            });
            $("#browserSBTab table tbody tr td:nth-child(1)").each(function() {
                $(this).find("input[name=browserCheck]").click(function() {
                    var browserSeledIds = new Array();
                    $("#browserSeled input[name=browserSeledId]").each(function(){
                        var browserSeledId = $(this).val();
                        browserSeledIds.push(browserSeledId);
                    });


                    var browserId = $(this).val();
                    var browserName = $(this).attr("browsername");

                    var browserHtml = "<span class='label label-success' style='border-style:double;' >"+browserName+"<a href='javascript:void(0);' name='browserClose'>"
                            +"<span class='icon icon-orange icon-close'  ></span></a>"
                            + "<input type='hidden' name='browserSeledId' value='"+browserId+"'/></span>";

                    //不在被选择列表中的 crowd 才被加入到选中列表中
                    if($.inArray(browserId, browserSeledIds) < 0){
                        //添加这个元素，并为这个元素里面的a[name=crowdClose]的绑定点击事件，点击，当前span remove
                        $("#browserSeled").append(browserHtml);

                        $("#browserSeled").find(" input[value="+browserId+"]").parent().find("a[name=browserClose]").click(function(){
                            $(this).parent().remove();
                        });
                    }



                });
            });
 */

            //系统
           /*  renderSystemSeled();
            $("#systemSBTab").hide();
            $("#systemSel").click(function() {
                $("#systemSBTab").toggle();

            });
            $("#systemSBTab table tbody tr td:nth-child(1)").each(function() {
                $(this).find("input[name=systemCheck]").click(function() {
                    var systemSeledIds = new Array();
                    $("#systemSeled input[name=systemSeledId]").each(function(){
                        var systemSeledId = $(this).val();
                        systemSeledIds.push(systemSeledId);
                    });


                    var systemId = $(this).val();
                    var systemName = $(this).attr("systemname");

                    var systemHtml = "<span class='label label-success' style='border-style:double;' >"+systemName+"<a href='javascript:void(0);' name='systemClose'>"
                            +"<span class='icon icon-orange icon-close'  ></span></a>"
                            + "<input type='hidden' name='systemSeledId' value='"+systemId+"'/></span>";

                    //不在被选择列表中的 crowd 才被加入到选中列表中
                    if($.inArray(systemId, systemSeledIds) < 0){
                        //添加这个元素，并为这个元素里面的a[name=crowdClose]的绑定点击事件，点击，当前span remove
                        $("#systemSeled").append(systemHtml);

                        $("#systemSeled").find(" input[value="+systemId+"]").parent().find("a[name=systemClose]").click(function(){
                            $(this).parent().remove();
                        });
                    }



                });
            }); */
            //设备
           /*  renderDeviceSeled();
            $("#deviceSBTab").hide();
            $("#deviceSel").click(function() {
                $("#deviceSBTab").toggle();

            });
            $("#deviceSBTab table tbody tr td:nth-child(1)").each(function() {
                $(this).find("input[name=deviceCheck]").click(function() {
                    var deviceSeledIds = new Array();
                    $("#deviceSeled input[name=deviceSeledId]").each(function(){
                        var deviceSeledId = $(this).val();
                        deviceSeledIds.push(deviceSeledId);
                    });


                    var deviceId = $(this).val();
                    var deviceName = $(this).attr("devicename");

                    var deviceHtml = "<span class='label label-success' style='border-style:double;' >"+deviceName+"<a href='javascript:void(0);' name='deviceClose'>"
                            +"<span class='icon icon-orange icon-close'  ></span></a>"
                            + "<input type='hidden' name='deviceSeledId' value='"+deviceId+"'/></span>";

                    //不在被选择列表中的 crowd 才被加入到选中列表中
                    if($.inArray(deviceId, deviceSeledIds) < 0){
                        //添加这个元素，并为这个元素里面的a[name=crowdClose]的绑定点击事件，点击，当前span remove
                        $("#deviceSeled").append(deviceHtml);

                        $("#deviceSeled").find(" input[value="+deviceId+"]").parent().find("a[name=deviceClose]").click(function(){
                            $(this).parent().remove();
                        });
                    }



                });
            });

 */
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

/******************************初始化域名频道*****ssq******************************************/
            //var adId = "${adId}";
            
            /* $.ajax({
                  url:"/adplan/initChannelurl",
                  data:{adId:adId},
                  async:false,
                  type:'GET',
                  success:function(data){
                	  $("#selectedChannelIds").empty();
                      $("#selectedChannelIds").html(data);
                      $("#selectedUrl").show();
                    }
            });  */

/*****************************************************************************************/
  
            

        }).on("click", "#adplaneditsave", function() {

            var putHours = getTime();
            var crowdSeledIds = getCrowdSelIds();
   //         var visitorSeledIds = getVisitorSelIds();
            var materialIds = getMaterialIds();
            //设备
   //         var deviceSeledIds = getDeviceSelIds();
            //系统
   //         var systemSeledIds = getSystemSelIds();
            //浏览器
   //         var browserSeledIds = getBrowserSelIds;
            var mediaCategoryCodes = getMediaCategoryCodes();
		    var adCategoryCodes = adCategory_getMediaCategoryCodes();
		    
		    var regionCodes = region_getMediaCategoryCodes();

            $("#putHours").val(putHours);
            $("#crowdSeledIds").val(crowdSeledIds);
   //         $("#visitorSeledIds").val(visitorSeledIds);
   //         $("#deviceSeledIds").val(deviceSeledIds);
   //         $("#systemSeledIds").val(systemSeledIds);
   //         $("#browserSeledIds").val(browserSeledIds);
            $("#adMaterialIds").val(materialIds);
            $("#mediaCategoryCodes").val(mediaCategoryCodes);
            $("#adCategoryCodes").val(adCategoryCodes);
            $("#regionCodes").val(regionCodes);
            //传递标签值
            var labelsArray = [];
		    $(".delete_icon").each(function(){
				 var _this = $(this);
				 var deleteid = $(_this).attr("objId");
				 labelsArray.push(deleteid);
			 });
		    $("#allLabelCodes").val(labelsArray.join(","));
            if(checkInput_adplan()){           	
                $("#adplanFrom").submit();
            }

        }).on("click","#crowdNew",function(e){

            $.ajax({
                url : '/crowd/crowdinitaddaction',
                type : 'get',
                data : "source=adplan",
                success : function(data) {

                    $("#crowdNewModal .modal-body").html(data);
                }
            });

            e.preventDefault();

            $('body').addClass('modal-open');
            var backdrop = $('<div class="modal-backdrop" />');
            backdrop.appendTo(document.body);
            backdrop.addClass("in");



            $("#crowdNewModal .modal-body").attr("style","max-height: 450px");
            $("#crowdNewModal").attr("style","width: 96%");


            $("#crowdNewModal").css("margin","0 0 0 0");
            $("#crowdNewModal").css("left","2%");
            $("#crowdNewModal").css("top","5%");

            $("#crowdNewModal").show();

        }).on("click","#crowdNewModal .modal-header .close",function(){

            $('body').removeClass('modal-open');
            $('body .modal-backdrop').remove();

            $("#crowdNewModal").hide();

        }).on("click","#crowdNewModal .modal-footer .btn",function(){

            $('body').removeClass('modal-open');
            $('body .modal-backdrop').remove();

            $("#crowdNewModal").hide();
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
			$("#materialTips").hide();
            var display = $("#selectMaterialDiv").css("display");
            if (display == 'block'){
                $("#selectMaterialDiv").hide();
                return;
            }
            $.ajax({
                url:"/material/table-template",
                data:{},
                async:false,
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
        }).on("dblclick",".mediaCateOp",function(){//点击一级媒体分类  查询下一级媒体分类 add by sunly
            var code = $(this).val();
            queryMediaAjax(code);
        }).on("click",".mediaCateCheck",function(){//选择媒体分类  add by sunly
            var code = $(this).val();
            var name = $(this).attr("name");
            var parentCode = $(this).attr("parentCode");
            if($(this).attr("checked")){
                addMediaCate(code,name,parentCode);
            }else{
                removeMediaCate(code,name,parentCode);
            }
        }).on("click","#selAll",function(){
            //全选
            $(".mediaCateCheck").each(function(){
                var code= $(this).val();
                var parentCode = $(this).attr("parentCode");
                var name = $(this).attr("name");
                $(this).attr("checked","checked");
                addMediaCate(code, name, parentCode);

            });
        }).on("click","#cancelSelAll",function(){
            //取消选择
            var parentCode = "";
            $(".mediaCateCheck").each(function(){
                parentCode = $(this).attr("parentCode");
                $(this).removeAttr("checked");
            });
            if(parentCode!=""){
                delMediaSpanByParentCode(parentCode);
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
        }).on("click", "#back",function(){
        	
            window.location.href = "/adplan/list";
            
        }).on("click","#selectUrlBtn",function(){
        	var channelIds="";
        	var checkBoxChannel = document.getElementsByName("checkBoxChannel");
        	for(var i=0;i<checkBoxChannel.length;i++){
        		if(checkBoxChannel[i].checked==true){
        			channelIds += checkBoxChannel[i].value +",";
        		}
        	}
        	if(channelIds!=""){
               channelIds = channelIds.trim().substring(0,channelIds.length-1);
        	   $("#channelIds").val(channelIds);
        	   
        	   $.ajax({
        		  url:"/adplan/selectChannelUrl",
        	      data:{channelIds:channelIds},
        	      async:false,
        	      type:'GET',
        	      success:function(data){
        	    	  $("#selectedChannelIds").empty();
                      $("#selectedChannelIds").html(data);
                      $("#selectedUrl").show();
        	      }
        	   });
        	}else{
        		$("#selectedUrl").hide();
        	}
        }).on("click","#cancelSelectUrlBtn",function(){
        	
        }).on("click",".removeUrl",function(){  
           $(this).parents("tr").remove();         
           var checkBoxChannel = document.getElementsByName("checkBoxChannel");
           var channelIds="";
       	   for(var i=0;i<checkBoxChannel.length;i++){
       		  if(checkBoxChannel[i].value==$(this).attr("value")){
       		 	 checkBoxChannel[i].checked=false;
       		  }
       		  if(checkBoxChannel[i].checked==true){
    			channelIds += checkBoxChannel[i].value +",";
    		  }
       	   }
           if ($("#selectedUrl table tr").length == 1){
             $("#selectedUrl").hide();
           }else{
        	   channelIds = channelIds.trim().substring(0,channelIds.length-1);
        	   $("#channelIds").val(channelIds);   
           }          
        });
/**
 *初始化媒体选中 add by sunly
 */
function initMediaSelected(){


    $("input[name=selectedMediaInput]").each(function(){
        var parentObj = {};
        var childrenArr;
        var parentCode = $(this).attr("parentCode");
        var childCode = $(this).val();
        var existFlag = false;
        for(var i=0;i<mediaParentArr.length;i++){
            if(mediaParentArr[i].parentCode==parentCode){
                existFlag = true;
                parentObj = mediaParentArr[i];
                mediaParentArr.splice(i,1);
                break;
            }
        }
        if(existFlag){
            childrenArr = parentObj.children;
        }else{
            childrenArr = new Array();
        }
        childrenArr.push(childCode);
        parentObj.parentCode=parentCode;
        parentObj.children=childrenArr;
        mediaParentArr.push(parentObj);
    });

    for(var i=0;i<mediaParentArr.length;i++){
        var obj = mediaParentArr[i];
        var parentCode = obj.parentCode;
        childrenArr = obj.children;
        var $span = $("<span></span>");
        $span.attr("id",prefix+parentCode);
        $span.attr("name",prefix);
        $span.attr("style","border: 1px solid #5CACEE;background-color: #B0E0E6;padding: 2px;margin:1px;cursor:pointer;");
        $span.attr("onclick","queryMediaAjax("+parentCode+")");

        var $delA = $("<a style='cursor: pointer;text-decoration:none;'>x</a>");
        var subMedisCateNum = childrenArr.length;
        var totalSubMediaCateNum = 0;
        $.ajax({
            url:'/adplan/getSubMediaCateNum/'+parentCode,
            type:'GET',
            dataType:'text',
            async:false,
            success: function(result){
                totalSubMediaCateNum=result;
            },error:function(){
                alert("error");
            }
        });
        $span.append("<span>"+getParentNameByCode(parentCode)+"("+subMedisCateNum+"/"+totalSubMediaCateNum+")</span>");
        $span.append("&nbsp;&nbsp;");
        $delA.attr("onclick","delMediaSpanByParentCode("+parentCode+")");

        $span.append($delA);
        $span.append("&nbsp;");
        $("#mediaCateSelectedDiv").append($span);
    }
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
            parentName =  $(this).text();
            return;
        }
    });
    return parentName;
}
//媒体分类常量 add by sunly
var mediaParentArr  =new Array();
var prefix = "mediaCateSelectedSpan";
/**
 * 删除数组中父媒体 add by sunly
 */
function delSelMediaCateByParentCode(code){
    for(var i=0;i<mediaParentArr.length;i++){
        var mcObj = mediaParentArr[i];
        if(mcObj.parentCode==code){
            mediaParentArr.splice(i,1);
            break;
        }
    }
}
/**
 * 根据父 媒体分类code获取父媒体分类 add by sunly
 */
function getSelMediaCateByParentCode(code){
    for(var i=0;i<mediaParentArr.length;i++){
        var mcObj = mediaParentArr[i];
        if(mcObj.parentCode==code){
            mediaParentArr.splice(i,1);
            return mcObj;
        }
    }
}
/*
 * 选择媒体分类 add by sunly
 */
function addMediaCate(code,name,parentCode){

    var parentName = getParentNameByCode(parentCode);
    var $spanList  = $("span[name="+prefix+"]");
    var addFlag = true;

    if($spanList.length>0){
        $spanList.each(function(){
            if($(this).attr("id")==(prefix+parentCode)){
                addFlag =false;
            }
        });
    }
    var parentObj = {};
    var childrenArr = new Array();
    if(addFlag){//添加
        childrenArr.push(code);
        var $span = $("<span></span>");
        $span.attr("id",prefix+parentCode);
        $span.attr("name",prefix);
        $span.attr("style","border: 1px solid #5CACEE;background-color: #B0E0E6;padding: 2px;margin:1px;cursor:pointer;");
        $span.click(function(){
            queryMediaAjax(parentCode);//初始化选中
        });
        var $delA = $("<a style='cursor: pointer;text-decoration:none;'>x</a>");
        var subMedisCateNum = $(".mediaCateCheck").length;

        $span.append("<span>"+parentName+"(1/"+subMedisCateNum+")</span>");
        $span.append("&nbsp;&nbsp;");
        $delA.click(function(){
            //清空当前选中状态
            delMediaSpanByParentCode(parentCode);
        });
        $span.append($delA);
        $span.append("&nbsp;");
        $("#mediaCateSelectedDiv").append($span);
    }else{//更新
        var $span = $("#"+prefix+parentCode);
        var subSpan = $span.find("span");
        var subMediaCateNum = $(".mediaCateCheck").length;
        var selectedSubMedisCateNum = 0;

        delSelMediaCateByParentCode(parentCode);
        $(".mediaCateCheck").each(function(){
            if($(this).attr("checked")){
                childrenArr.push($(this).val());
                selectedSubMedisCateNum++;
            }
        });
        subSpan.text(parentName+"("+selectedSubMedisCateNum+"/"+subMediaCateNum+")");
    }
    parentObj.parentCode=parentCode;
    parentObj.children=childrenArr;
    mediaParentArr.push(parentObj);
}

/*
 * 去除媒体分类 add by sunly
 */
function removeMediaCate(code,name,parentCode){

    var parentObj = {};
    parentObj = getSelMediaCateByParentCode(parentCode);//删除原来的
    var childrenArr = parentObj.children;

    for(var i=0;i<childrenArr.length;i++){
        if(childrenArr[i]==code){
            childrenArr.splice(i,1);//删除子媒体分类
        }
    }
    if(childrenArr.length>0){
        parentObj.children=childrenArr;
        mediaParentArr.push(parentObj);

        var $span = $("#"+prefix+parentCode);
        var subSpan = $span.find("span");
        var subMediaCateNum = $(".mediaCateCheck").length;
        var selectedSubMedisCateNum = 0;

        $(".mediaCateCheck").each(function(){
            if($(this).attr("checked")){
                selectedSubMedisCateNum++;
            }
        });
        var parentName = getParentNameByCode(parentCode);
        subSpan.text(parentName+"("+selectedSubMedisCateNum+"/"+subMediaCateNum+")");
    }else{
        delMediaSpanByParentCode(parentCode);
    }
}

/**
 * 初始化选中
 */
function initMediaCateSel(parentCode){
    for(var i=0;i<mediaParentArr.length;i++){
        var parentObj = mediaParentArr[i];
        if(parentObj.parentCode==parentCode){
            var childrenArr = parentObj.children;
            for(var j=0;j<childrenArr.length;j++){
                $(".mediaCateCheck").each(function(){
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
function delMediaSpanByParentCode(parentCode){
    delSelMediaCateByParentCode(parentCode);//删除选中的媒体分类
    $("#"+prefix+parentCode).remove();//删除父媒体分类
    $(".mediaCateCheck").removeAttr("checked");//去掉所有选中
}
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
// add by sunly 
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

//add by sunly  start
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
//媒体分类常量 add by sunly
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

/**
 * add by sunly
 */
function getMediaCategoryCodes(){
    var idArray = [];

    for(var i=0;i<mediaParentArr.length;i++){
        var chidlrenArr = mediaParentArr[i].children;
        var childrenIds = chidlrenArr.join(",");
        idArray.push(childrenIds);
    }
    return idArray.join(",");
}

/**
 *初始化广告选中 add by sunly
 */
function adCategory_initMediaSelected(){


    $("input[name=adCategory_selectedMediaInput]").each(function(){
        var parentObj = {};
        var childrenArr;
        var parentCode = $(this).attr("parentCode");
        var childCode = $(this).val();
        var existFlag = false;
        for(var i=0;i<adCategory_mediaParentArr.length;i++){
            if(adCategory_mediaParentArr[i].parentCode==parentCode){
                existFlag = true;
                parentObj = adCategory_mediaParentArr[i];
                adCategory_mediaParentArr.splice(i,1);
                break;
            }
        }
        if(existFlag){
            childrenArr = parentObj.children;
        }else{
            childrenArr = new Array();
        }
        childrenArr.push(childCode);
        parentObj.parentCode=parentCode;
        parentObj.children=childrenArr;
        adCategory_mediaParentArr.push(parentObj);
    });

    for(var i=0;i<adCategory_mediaParentArr.length;i++){
        var obj = adCategory_mediaParentArr[i];
        var parentCode = obj.parentCode;
        childrenArr = obj.children;
        var $span = $("<span></span>");
        $span.attr("id",adCategory_prefix+parentCode);
        $span.attr("name",adCategory_prefix);
        $span.attr("style","border: 1px solid #5CACEE;background-color: #B0E0E6;padding: 2px;margin:1px;cursor:pointer;");
        $span.attr("onclick","adCategory_queryMediaAjax("+parentCode+")");

        var $delA = $("<a style='cursor: pointer;text-decoration:none;'>x</a>");
        var subMedisCateNum = childrenArr.length;
        var totalSubMediaCateNum = 0;
        $.ajax({
            url:'/adplan/getSubAdCateNum/'+parentCode,
            type:'GET',
            dataType:'text',
            async:false,
            success: function(result){
                totalSubMediaCateNum=result;
            },error:function(){
                alert("error");
            }
        });
        $span.append("<span>"+adCategory_getParentNameByCode(parentCode)+"("+subMedisCateNum+"/"+totalSubMediaCateNum+")</span>");
        $span.append("&nbsp;&nbsp;");
        $delA.attr("onclick","adCategory_delMediaSpanByParentCode("+parentCode+")");

        $span.append($delA);
        $span.append("&nbsp;");
        $("#adCategory_mediaCateSelectedDiv").append($span);
    }
}
// add by sunly end

/*************************************************区域定向开始*****************************************/
 // add by sunly 
/**
 */
function initRegion(){

    $(".region_mediaCateOp").on("dblclick",function(){//点击一级媒体分类  查询下一级媒体分类 add by sunly
        var code = $(this).val();
        region_queryMediaAjax(code);
    });
    $("#region_selAll").on("click",function(){
        //全选
        $(".region_mediaCateCheck").each(function(){
            var code= $(this).val();
            var parentCode = $(this).attr("parentCode");
            var name = $(this).attr("name");
            $(this).attr("checked","checked");
            region_addMediaCate(code, name, parentCode);
        });
    });
    $("#region_cancelSelAll").on("click",function(){
        //取消选择
        var parentCode = "";
        $(".region_mediaCateCheck").each(function(){
            parentCode = $(this).attr("parentCode");
            $(this).removeAttr("checked");
        });
        if(parentCode!=""){
            region_delMediaSpanByParentCode(parentCode);
        }
    });
}

//add by sunly  start
function region_queryMediaAjax(parentCode){
    $.ajax({
        url:'/adplan/regionLeveTwo-template/'+parentCode,
        type:'GET',
        success: function(result){
            $("#region_mediaCateLeveTwoDiv").html(result);
            region_initMediaCateSel(parentCode);
        },error:function(){
            alert("error");
        }
    });
}
/*
 * 根据父分类code 获取分类名称 add by sunly
 */
function region_getParentNameByCode(parentCode){
    var parentName = "";
    $(".region_mediaCateOp option").each(function(){
        if($(this).val()==parentCode){
            parentName = $(this).text();
            return;
        }
    });
    return parentName;
}
//媒体分类常量 add by sunly
var region_mediaParentArr  =new Array();
var region_prefix = "region_SelectedSpan";
/**
 * 删除数组中父媒体 add by sunly
 */
function region_delSelMediaCateByParentCode(code){
    for(var i=0;i<region_mediaParentArr.length;i++){
        var mcObj = region_mediaParentArr[i];
        if(mcObj.parentCode==code){
            region_mediaParentArr.splice(i,1);
            break;
        }
    }
}
/**
 * 根据父 媒体分类code获取父媒体分类 add by sunly
 */
function region_getSelMediaCateByParentCode(code){
    for(var i=0;i<region_mediaParentArr.length;i++){
        var mcObj = region_mediaParentArr[i];
        if(mcObj.parentCode==code){
            region_mediaParentArr.splice(i,1);
            return mcObj;
        }
    }
}
/*
 * 选择媒体分类 add by sunly
 */
function region_addMediaCate(code,name,parentCode){

    var parentName = region_getParentNameByCode(parentCode);
    var $spanList  = $("span[name="+region_prefix+"]");
    var addFlag = true;

    if($spanList.length>0){
        $spanList.each(function(){
            if($(this).attr("id")==(region_prefix+parentCode)){
                addFlag =false;
            }
        });
    }

    var parentObj = {};
    var childrenArr = new Array();

    if(addFlag){//添加
        childrenArr.push(code);
        var $span = $("<span></span>");
        $span.attr("id",region_prefix+parentCode);
        $span.attr("name",region_prefix);
        $span.attr("style","border: 1px solid #5CACEE;background-color: #B0E0E6;margin:2px;padding: 2px;margin:1px;cursor:pointer;");
        $span.click(function(){
            region_queryMediaAjax(parentCode);//初始化选中
        });
        var $delA = $("<a style='cursor: pointer;text-decoration:none;'>x</a>");
        var subMedisCateNum = $(".region_mediaCateCheck").length;
        $span.append("<span>"+parentName+"(1/"+subMedisCateNum+")</span>");
        $span.append("&nbsp;&nbsp;");
        $delA.click(function(){
            //清空当前选中状态
            region_delMediaSpanByParentCode(parentCode);
        });
        $span.append($delA);
        $span.append("&nbsp;");
        $("#region_mediaCateSelectedDiv").append($span);
    }else{//更新
        var $span = $("#"+region_prefix+parentCode);
        var subSpan = $span.find("span");
        var subMediaCateNum = $(".region_mediaCateCheck").length;
        var selectedSubMedisCateNum = 0;

        region_delSelMediaCateByParentCode(parentCode);
        $(".region_mediaCateCheck").each(function(){
            if($(this).attr("checked")){
                childrenArr.push($(this).val());
                selectedSubMedisCateNum++;
            }
        });
        subSpan.text(parentName+"("+selectedSubMedisCateNum+"/"+subMediaCateNum+")");
    }
    parentObj.parentCode=parentCode;
    parentObj.children=childrenArr;
    region_mediaParentArr.push(parentObj);
}

/*
 * 去除媒体分类 add by sunly
 */
function region_removeMediaCate(code,name,parentCode){

    var parentObj = {};
    parentObj = region_getSelMediaCateByParentCode(parentCode);//删除原来的
    var childrenArr = parentObj.children;

    for(var i=0;i<childrenArr.length;i++){
        if(childrenArr[i]==code){
            childrenArr.splice(i,1);//删除子媒体分类
        }
    }
    if(childrenArr.length>0){
        parentObj.children=childrenArr;
        region_mediaParentArr.push(parentObj);

        var $span = $("#"+region_prefix+parentCode);
        var subSpan = $span.find("span");
        var subMediaCateNum = $(".region_mediaCateCheck").length;
        var selectedSubMedisCateNum = 0;

        $(".region_mediaCateCheck").each(function(){
            if($(this).attr("checked")){
                selectedSubMedisCateNum++;
            }
        });
        var parentName = region_getParentNameByCode(parentCode);
        subSpan.text(parentName+"("+selectedSubMedisCateNum+"/"+subMediaCateNum+")");
    }else{
        region_delMediaSpanByParentCode(parentCode);
    }
}

/**
 * 初始化选中
 */
function region_initMediaCateSel(parentCode){
    for(var i=0;i<region_mediaParentArr.length;i++){
        var parentObj = region_mediaParentArr[i];
        if(parentObj.parentCode==parentCode){
            var childrenArr = parentObj.children;
            for(var j=0;j<childrenArr.length;j++){
                $(".region_mediaCateCheck").each(function(){
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
function region_delMediaSpanByParentCode(parentCode){
    region_delSelMediaCateByParentCode(parentCode);//删除选中的媒体分类
    $("#"+region_prefix+parentCode).remove();//删除父媒体分类
    $(".region_mediaCateCheck").removeAttr("checked");//去掉所有选中
}

/**
 * add by sunly
 */
function region_getMediaCategoryCodes(){
    var idArray = [];

    for(var i=0;i<region_mediaParentArr.length;i++){
        var chidlrenArr = region_mediaParentArr[i].children;
        var childrenIds = chidlrenArr.join(",");
        idArray.push(childrenIds);
    }
    return idArray.join(",");
}

/**
 * add by sunly
 */
function getMediaCategoryCodes(){
    var idArray = [];

    for(var i=0;i<mediaParentArr.length;i++){
        var chidlrenArr = mediaParentArr[i].children;
        var childrenIds = chidlrenArr.join(",");
        idArray.push(childrenIds);
    }
    return idArray.join(",");
}

/**
 * 初始化标签树对应的数据
 */
function initAllLabelEvernt()
{
	 $("#computeCrowdBtn").on("click",function(){
	
		 //获取已选择的标签值
		 //传递标签值
            var labelsArray = [];
		    $(".delete_icon").each(function(){
				 var _this = $(this);
				 var deleteid = $(_this).attr("objId");
				 labelsArray.push(deleteid);
			 });
		    
		    allLabels  =labelsArray.join(",");
		 
	       $.ajax({
		        url:'/adplan/caluteLabelCrowdNum',
		        type:'GET',
		        data:{'allLables':allLabels},
		        success: function(result){
		        	$("#computeResultDiv").show();
		           changeLabelComputeResultDiv("0",result.pvCount,"0");
		        },error:function(){
		            alert("error");
		        }
		       });
	       });
}

/**
 *初始化广告选中 add by sunly
 */
function region_initMediaSelected(){


    $("input[name=region_selectedMediaInput]").each(function(){
        var parentObj = {};
        var childrenArr;
        var parentCode = $(this).attr("parentCode");
        var childCode = $(this).val();
        var existFlag = false;
        for(var i=0;i<region_mediaParentArr.length;i++){
            if(region_mediaParentArr[i].parentCode==parentCode){
                existFlag = true;
                parentObj = region_mediaParentArr[i];
                region_mediaParentArr.splice(i,1);
                break;
            }
        }
        if(existFlag){
            childrenArr = parentObj.children;
        }else{
            childrenArr = new Array();
        }
        childrenArr.push(childCode);
        parentObj.parentCode=parentCode;
        parentObj.children=childrenArr;
        region_mediaParentArr.push(parentObj);
    });

    for(var i=0;i<region_mediaParentArr.length;i++){
        var obj = region_mediaParentArr[i];
        var parentCode = obj.parentCode;
        childrenArr = obj.children;
        var $span = $("<span></span>");
        $span.attr("id",region_prefix+parentCode);
        $span.attr("name",region_prefix);
        $span.attr("style","border: 1px solid #5CACEE;background-color: #B0E0E6;padding: 2px;margin:1px;cursor:pointer;");
        $span.attr("onclick","region_queryMediaAjax("+parentCode+")");

        var $delA = $("<a style='cursor: pointer;text-decoration:none;'>x</a>");
        var subMedisCateNum = childrenArr.length;
        var totalSubMediaCateNum = 0;
        $.ajax({
            url:'/adplan/getSubAdRegionNum/'+parentCode,
            type:'GET',
            dataType:'text',
            async:false,
            success: function(result){
                totalSubMediaCateNum=result;
            },error:function(){
                alert("error");
            }
        });
        $span.append("<span>"+region_getParentNameByCode(parentCode)+"("+subMedisCateNum+"/"+totalSubMediaCateNum+")</span>");
        $span.append("&nbsp;&nbsp;");
        $delA.attr("onclick","region_delMediaSpanByParentCode("+parentCode+")");

        $span.append($delA);
        $span.append("&nbsp;");
        $("#region_mediaCateSelectedDiv").append($span);
    }
}
/****************************************区域定向结束end*******************************************/

/**
 * add by zhoubin
 */
function changeLabelComputeResultDiv(takeMs,pvCount,uvCount){
    
	/* $("#takeMsVal").text(takeMs+'ms'); */
	
	$("#pvCountVal").text(pvCount);

	/* $("#uvCountVal").text(uvCount); */
}

/****************************************关键词定向  ssq*********************************************/
$("#computeKeyWordBtn").on("click",function(){
	 var keywords = $("#keyWd").val();
	 var kwUrlFetchCicle = $("#kwUrlFetchCicle").val();
	 var matchType="";    //匹配类型  1是精准  2是中心
	 $("input[name='keyWordAdPlan.matchType']").each(function(){
	     if($(this).parent().attr("class")=="checked"){
	    	 matchType = $(this).attr("value");
	     }
	 });
	/* if(matchType ==""){
		alert("请选择匹配关系");
		return ;
	} */
	if(keywords!=""){
		$.ajax({
			url:'/adplan/searchKeywords',
	        type:'POST',
	        data:{keywords:keywords,kwUrlFetchCicle:kwUrlFetchCicle,matchType:matchType},
	        success: function(result){
	        	$("#computeKeywordResult").show();
	        	searchKeywordResultDiv("0","0",result.keyWordCount);
	        },error:function(){
	            alert("error");
	       }
		});	
	}   	
});

function searchKeywordResultDiv(takeMs,totalCount,keywordCount){   
	/* $("#tookMsVal").text(takeMs+'ms');	
	$("#totalCountVal").text(totalCount); */
	$("#keywordCountVal").text(keywordCount);
}
/******************************************关键词定向 end**************************************************/

/*********************************域名定向***ssq********************************************************/
var channel = document.getElementById("channelcheck");
var channelTypes= document.getElementsByName("channelType");

$("#channelcheck").click(function(){
	document.getElementById("rowFluid").style.display="block";
	document.getElementById("addChannelUrl").style.display="none";
	
	if($("#channelcheck").parent().attr("class")==null||$("#channelcheck").parent().attr("class")==""){
		 $("input[name='channelType']").each(function(){
			   $(this).parent().addClass("checked");
		 });
		 $(this).parent().addClass("checked");
	}else{
	   $("input[name='channelType']").each(function(){
		   $(this).parent().removeClass("checked");
	   });
	   $(this).parent().attr("class","");
	}
	var channels = "";
	 $("input[name='channelType']").each(function(){
		if($(this).parent().attr("class")=="checked"){
			channels = channels + $(this).attr("value") + ",";
		}
	 });
	 if(channels.length>0){		 
       $.ajax({
          url:"/adplan/channelList",
          data:{channels:channels},
          async:false,
          type:'GET',
          success:function(data){
             $("#channelListBody").empty();
             $("#channelListBody").html(data);

             $('#channelListTable').dataTable({
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
                "sPaginationType": "bootstrap",
                "bRetrieve": true,
                "oLanguage": {
                    "sProcessing": "正在加载中......",
                    "sLengthMenu": "每页显示 _MENU_ 条记录",
                    "sZeroRecords": "对不起，查询不到相关数据！",
                    "sEmptyTable": "表中无数据存在！",
                    "sInfo": '<button type="button" class="btn btn-primary" id="selectUrlBtn">确定</button>&nbsp;&nbsp;<button type="button" class="btn" id="cancelSelectUrlBtn">取消</button>',
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
	}else{
	    $("#channelListBody").empty();
	}
});


function huitianadDevIds(){
	
	 var dBeforeIds = [];
	$("input[name='adDevDtoHidenVal']").each(function(){
		dBeforeIds.push($(this).val());
	});
	
	

	$("input[name='adDeviceIds']").each(function(){
		var val = $(this).val();
		
		for(var i=0;i<dBeforeIds.length;i++){
			if(val==dBeforeIds[i]){
				$(this).attr("checked",true);				
			}
		}
	});
	
}

huitianadDevIds();



$("input[name='channelType']").click(function(){
	document.getElementById("rowFluid").style.display="block";
	document.getElementById("addChannelUrl").style.display="none";
	
	var check = true;
	setTimeout(function(){
	    $("input[name='channelType']").each(function(){
	       if($(this).parent().attr("class")==null||$(this).parent().attr("class")==""){
	    	   $("#channelcheck").parent().attr("class","");
	    	   return check=false;
	       }
	    });	
	    if(check){
		    $("#channelcheck").parent().addClass("checked");
	    }
	    var channels = "";
	    $("input[name='channelType']").each(function(){
		    if($(this).parent().attr("class")=="checked"){
			    channels = channels + $(this).attr("value") + ",";
		    }
	    });
	 if(channels.length>0){
        $.ajax({
           url:"/adplan/channelList",
           data:{channels:channels},
           async:false,
           type:'GET',
           success:function(data){
               $("#channelListBody").empty();
               $("#channelListBody").html(data);

               $('#channelListTable').dataTable({
                  "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
                  "sPaginationType": "bootstrap",
                  "bRetrieve": true,
                  "oLanguage": {
                      "sProcessing": "正在加载中......",
                      "sLengthMenu": "每页显示 _MENU_ 条记录",
                      "sZeroRecords": "对不起，查询不到相关数据！",
                      "sEmptyTable": "表中无数据存在！",
                      "sInfo": '<button type="button" class="btn btn-primary" id="selectUrlBtn">确定</button>&nbsp;&nbsp;<button type="button" class="btn" id="cancelSelectUrlBtn">取消</button>',
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
	  }else{
		  $("#channelListBody").empty();
	  }
	}, 10);
});


function addChannelUrl(){
	document.getElementById("selectedUrl").style.display="none";
	document.getElementById("rowFluid").style.display="none";
	document.getElementById("addChannelUrl").style.display="block";	
}

function subChannelUrl(){
	
    var reg =  /^([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/;
    var channelurl = $("#channelurl").val();
    var channelTypeSelected = $("#channelTypeSelected").val();
    if(channelurl!=""){
      var urls = channelurl.split("\n");
      for(var i=0;i<urls.length;i++){
    	var line = i +1;
    	if(reg.test(urls[i])==false){
    		alert("你第"+line+"行输入的域名格式不正确");
    	    return ;
    	}
      }
    }else{
    	alert("你没有填入任何域名");
    	return ;
    }
    $.ajax({
    	url:"/adplan/setChannelUrl",
    	data:{channelurl:channelurl,channelTypeSelected:channelTypeSelected},
    	async:false,
        type:'GET',
        success:function(data){
            if(data.result>0){
                alert("新增成功");
            }else{
                alert("新增失败");
            }
        }
    });
}

function cancelAdd(){
	$("#channelurl").val("");
}
/*********************************域名定向***end********************************************************/
</script>
<script type="text/javascript" src="/resources/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.ztree.exhide-3.5.min.js"></script>
<script type="text/javascript" src="/resources/js/common/allbaselabeltree.js"></script>
<script type="text/javascript" src="/resources/js/common/waitingbar.js"></script>
</html>
