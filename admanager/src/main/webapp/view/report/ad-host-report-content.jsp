<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>广告投放网址</title>

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
			<noscript>
				<div class="alert alert-block span10">
					<h4 class="alert-heading">Warning!</h4>
					<p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
				</div>
			</noscript>
			
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
								<c:if test="${not empty adInstanceList}">								   								  
								   <select name="selectAdName" id="selectAdName"  style="width: 180px;">
								       <option value="0">---------------请选择广告---------------</option>
								      <c:forEach items="${adInstanceList}" var="adList">
								         <option value="${adList.id}">${adList.adName}</option>
								      </c:forEach>
								   </select>
								</c:if>
								<c:if test="${not empty urlHostList}">							   
								    <select name="selecturlhost" id="selecturlhost"  style="width: 180px;">
								       <option value="0">---------------请选择域名---------------</option>
								       <c:forEach items="${urlHostList}" var="urlhost">
								           <option value="${urlhost.urlHost}">${urlhost.urlHost}</option>
								       </c:forEach>
								    </select>
								</c:if>
								<button type="button" id="confirm" class="btn btn-primary" style="margin-top:0px">确定</button>
							</div>
						</div>
						<div class="row-fluid" id="listTable">
							<c:if test="${not empty adInstanceList}"><%@include file="./ad-host-template.jsp"%></c:if>
							<c:if test="${not empty urlHostList}"><%@include file="./ad-host-show.jsp"%></c:if>						             
						</div>
						<c:choose>
						   <c:when test="${not empty adId}"><input type="hidden" id="selectadid" value="${adId}"/></c:when>
						   <c:otherwise><input type="hidden" id="selectadid" value="0"/></c:otherwise>
						</c:choose>											
	</div>
<form class="form-horizontal" method="post" action="/exporturl/excel" id="exportForm">
			<input name="startExport" id="startExport" type="hidden"/>
			<input name="endExport"  id="endExport" type="hidden"/>
			<input name="adIdExport" id="adIdExport" type="hidden"/>
			<input name="exportType" id="exportType" type="hidden"/>
			<input name="urlHost" id="urlHost" type="hidden"/>
</form>	
<%@include file="../inc/footer.jsp"%>
</body>
<script type="text/javascript" src="/resources/js/common/common.js"></script>
<script>
$(document).ready(function(){
	$(".datepicker").datepicker({
		numberOfMonths:1,//显示几个月  
	    showButtonPanel:true,//是否显示按钮面板  
	    dateFormat: 'yy-mm-dd',//日期格式  
	    clearText:"清除",//清除日期的按钮名称  
	    closeText:"关闭",//关闭选择框的按钮名称  
	    yearSuffix: '年', //年的后缀,
	    currentText:'今天',
	    showMonthAfterYear:true,//是否把月放在年的后面  
	    monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],  
	    dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
	    dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
	    dayNamesMin: ['日','一','二','三','四','五','六']
	});
	
	// 默认日期
	var current = new Date();
	var last = current.setDate(current.getDate() - 0);
	last = new Date(last);
	
	$("#start").val(last.Format("yyyy-MM-dd"));
	$("#end").val(last.Format("yyyy-MM-dd"));
	
	$("#time-range a[index='2']").attr("style", "text-decoration:none;color:#000;cursor:default");
	$("#time-range a[index='2']").addClass("none");
	
	var urlhost = "${urlHost}";
	if(urlhost!=''||urlhost!=null){
		
		$("#selecturlhost option[value='"+urlhost+"']").attr("selected","selected");
	}
}).on("click","#time-range a",function(){
	$("a.none").removeClass("none");
	$("#time-range a").attr("style","");
	$(this).attr("style", "text-decoration:none;color:#000;cursor:default");
	changeTimeNode($(this).attr("index"));
	$(this).addClass("none");
	$("#confirm").click();
}).on("click", "#confirm", function(){
	var start = $("#start").val();
	var end = $("#end").val();	
	var selectadId = $("#selectadid").val();
	if(selectadId=="0"){
		var adId = $("#selectAdName").val();
		$.ajax({
			url:"/urlCount/reportResult",
			data:{start:start,end:end,adId:adId},
			async:false,
			type:'GET',
			dataType:'html',
			success:function(data){
				$("#listTable").empty();
				$("#listTable").html(data);
			}
		});
	}
	if(selectadId!="0"){
		var urlhost = $("#selecturlhost").val();
		$.ajax({
			url:"/urlCount/hostShowResult",
			data:{start:start,end:end,adId:selectadId,urlHost:urlhost},
			async:false,
			type:'GET',
			dataType:'html',
			success:function(data){
				$("#listTable").empty();
				$("#listTable").html(data);
			}
		});
	}	
}).on("click","#downloadurl",function(){
	var start = $("#start").val();
	var end = $("#end").val(); 
	var adId=$("#selectAdName").val();
	
	$("#exportType").val("0");
	$("#startExport").val(start);
	$("#endExport").val(end); 
	$("#adIdExport").val(adId);
	
	$("#exportForm").submit();	
}).on("click","#downloadhost",function(){
	var start = $("#start").val();
	var end = $("#end").val();
	var adId = $("#selectadid").val();
	var urlhost = $("#selecturlhost").val();
	
	$("#startExport").val(start);
	$("#endExport").val(end); 
	$("#adIdExport").val(adId);
	$("#urlHost").val(urlhost);
	$("#exportType").val("1");
	
	$("#exportForm").submit();	
});

function changeTimeNode(index){
	var current = new Date();
	var start = end = 0;
	if (index == 1){
		// 昨天
		start = current.setDate(current.getDate() - 1);
		start = new Date(start);
		end = start;
	} else if (index == 2){
		// 今天
		start = end = current;
	} else if (index == 3){
		// 上周
		var week = current.getDay();
		// 0、1、2、3、4、5、6 周日至周六
		var minus = 0;
		switch(week){
			case 0: minus = 7;break;
			case 1: minus = 1;break;
			case 2: minus = 2;break;
			case 3: minus = 3;break;
			case 4: minus = 4;break;
			case 5: minus = 5;break;
			case 6: minus = 6;break;
			default:break;
		}
		// 上周日
		end = current.setDate(current.getDate() - minus);
		end = new Date(end);
		start = current.setDate(current.getDate() - 6);
		start = new Date(start);
	} else if (index == 4){
		// 本周
		var week = current.getDay();
		// 0、1、2、3、4、5、6 周日至周六
		var plus = 0;
		switch(week){
			case 0: plus = 0;break;
			case 1: plus = 6;break;
			case 2: plus = 5;break;
			case 3: plus = 4;break;
			case 4: plus = 3;break;
			case 5: plus = 2;break;
			case 6: plus = 1;break;
			default:break;
		}
		// 上周日
		end = current.setDate(current.getDate() + plus);
		end = new Date(end);
		start = current.setDate(current.getDate() - 6);
		start = new Date(start);
	} else if (index == 5){
		// 上月
		var month = current.getMonth();
		month = (month == 0 ? 12 : month);
		var year = current.getFullYear();
		year = (month == 12 ? year - 1 : year);
		month = month < 10 ? "0"+month : month;
		start = new Date(year + "-" + month + "-01");
		end = new Date(year, month, 0);
		
	} else if (index == 6){
		// 本月
		var month = current.getMonth() + 1;
		var year = current.getFullYear();
		month = month < 10 ? "0"+month : month;
		start = new Date(year + "-" + month + "-01");
		end = new Date(year, month, 0);
	}
	// 设置日期
	$("#start").val(start.Format("yyyy-MM-dd"));
	$("#end").val(end.Format("yyyy-MM-dd"));
}
</script>
</html>
