<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>广告物料</title>

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
								<button type="button" id="confirm" class="btn btn-primary" style="margin-top:0px">确定</button>
							</div>
						</div>
						
						<c:if test="${not empty adMaterial}">
							<div class="page-header">
								<input type="hidden" value="${adMaterial.id}" id="id"/>
								<span class="label label-success" style="font-size:22px;margin-left:10px;">${adMaterial.materialName}</span>
							</div>
						</c:if>
						<div class="control-group">
							<div class="controls">
								<label class="radio"> <input type="radio" name="type" value="1" checked="checked" />展现量
								</label>
								<label class="radio" style="padding-top: 5px;">
									<input type="radio" name="type" value="2" />点击量
								</label> 
							</div>
						</div>
						<div id="reportChart"  class="center" style="height:300px" ></div>
					
						<div class="row-fluid" id="listTable">
							<%@include file="./ad-material-template.jsp" %>           
						</div>
						
					</div>
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
	drawChart(1, $("#start").val(), $("#end").val(), 1);
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
	var index = $("a.none").attr("index");
	var type = $("input[name='type']:checked").val();
	var id = $("#id").length == 0 ? 0 : $("#id").val();
	$.ajax({
		url:"/material/reportResult",
		data:{start:start,end:end,index:index,id:id},
		async:false,
		type:'GET',
		dataType:'text',
		success:function(data){
			$("#listTable").empty();
			$("#listTable").html(data);
		}
	});
	drawChart(index, start, end, type);
}).on("click","input:radio",function(){
	var start = $("#start").val();
	var end = $("#end").val();
	var index = $("a.none").attr("index");
	var type = $(this).val();
	drawChart(index, start, end, type);
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

function drawChart(index, start, end, type){
	var x_ticks =[];
	var xMax = 24;
	var yMax = 10;
	if (start == end){
		// 按每天整点绘制图表
		for (var i = 1; i <= 24; ++i){
			var temp = [];
			temp.push(i);
			temp.push((i < 10 ? ("0"+(i-1)): i-1) + ":00");
			x_ticks.push(temp);
		}
	} else {
		// 按天绘制图表
		var startTime = new Date(start);
		var endTime = new Date(end);
		var diff = dateDiff(startTime, endTime);
		var firstDay = startTime;
		for (var i = 0; i < diff; ++i){
			var temp = [];
			temp.push(i+1);
			temp.push(new Date(firstDay.setDate(firstDay.getDate() + (i == 0 ? 0 : 1))).Format("yyyy-MM-dd"));
			x_ticks.push(temp);
		}
		xMax = diff;
	}
	
	var start = $("#start").val();
	var end = $("#end").val();
	//var index = $("a.none").attr("index");
	var index;
	if(start == end){
		index = 1;
	}else{
		index = 6;
	}
	var id = $("#id").length == 0 ? 0 : $("#id").val();
	var dataArr = [];
	$.ajax({
		url:"/material/chart",
		data:{start:start,end:end,index:index,type:type,reportType:1,id:id},
		async:false,
		type:'GET',
		dataType:'json',
		success:function(data){
			if (typeof data.result != 'undefined'){
				var objArray = data.result;
				yMax = data.yMax;
				for (var i = 0; i < objArray.length; ++i){
					var obj = objArray[i];
					dataArr.push(eval("("+"{label:\'" + obj.labelName + "\',data:" + obj.data + "}"+")"));
				}
			}
		}
	});

	if (dataArr.length == 0){
		dataArr.push(eval("("+"{label:\'\',data:[]}"+")"));
	}
	// example
	//var str = '{label: "", data: [[1, 2], [2, 3], [3, 1], [4, 1], [5, 6], [6, 3], [7, 4], [8, 5], [9, 1], [10, 2], [11, 3], [12, 7],[13,1],[14,1],[15,1],[16,1],[17,1],[18,1],[19,1],[20,1],[21,1],[22,1],[23,1],[24,1]]}';
	
	var y_ticks = yMax / 5 == 0 ? 1 : 5;
	$.plot($("#reportChart"),
		dataArr,
		{
			series: { lines: { show: true }, points: { show: true} },
			grid: { hoverable: true},
			xaxis: { ticks: x_ticks, min: 1, max: xMax},
			yaxis: { ticks: y_ticks, min: 0, max: yMax}
		}
	);
	
	// 节点tip
	function showTooltip(x, y, contents) {
		$('<div id="tooltip">' + contents + '</div>').css( {
			position: 'absolute',
			display: 'none',
			top: y + 10,
			left: x + 10,
			border: '1px solid #fdd',
			padding: '2px',
			'background-color': '#dfeffc',
			opacity: 0.80
		}).appendTo("body").fadeIn(200);
	}

	var previousPoint = null;
	$("#reportChart").bind("plothover", function (event, pos, item) {
		if (item) {
			if (previousPoint != item.dataIndex) {
				previousPoint = item.dataIndex;
				$("#tooltip").remove();
				var y = item.datapoint[1].toFixed(0);
	
				var tip = "展现量：";
				if ($(":radio:checked").val() == 2){
					tip = "点击量：";
				}
				showTooltip(item.pageX, item.pageY,tip+y);
			}
		}
		else {
			$("#tooltip").remove();
			previousPoint = null;
		}
	});
}
</script>
</html>
