var total_pv = 0;
var total_uv = 0;
var total_ip = 0;
var total_click = 0;
var total_mobilePv = 0;
var total_mobileClick = 0;
var total_close =0;
var total_amount = 0;
$(document).ready(function(){
	 //去除checkbox 所有样式
	$("input[type='checkbox']").removeAttr("style");
	$("input[type='checkbox']").removeAttr("checked");
	//$("span.checked").removeClass("checked");
	$("div.checker").removeClass("checker");
	
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
	var ids = getCheckAdIds();
	drawChart(2, $("#start").val(), $("#end").val(), 1, ids);
	
}).on("click","#time-range a",function(){
	//点击使总数变为0 --zxm
   	init_Sum();
	$("a.none").removeClass("none");
	$("#time-range a").attr("style","");
	$(this).attr("style", "text-decoration:none;color:#000;cursor:default");
	changeTimeNode($(this).attr("index"));
	$(this).addClass("none");
	$("#confirm").click();
}).on("click", "#confirm", function(){
	//点击使总数变为0 --zxm
	init_Sum();
	var start = $("#start").val();
	var end = $("#end").val();
	var index = $("a.none").attr("index");
	var type = $("input[name='type']:checked").val();
	var id = $("#id").length == 0 ? 0 : $("#id").val();
	var ids = getCheckAdIds();
	var checkAll = getCheckAll();
	$.ajax({
		url:"/ad/template",
		data:{start:start,end:end,index:index,id:id},
		async:false,
		type:'GET',
		dataType:'text',
		success:function(data){
			$("#listTable").empty();
			$("#listTable").html(data);
		}
	});
	
	
	$.ajax({
		url:"/ad/statistics",
		data:{start:start,end:end,id:id},
		async:false,
		type:'GET',
		dataType:'json',
		success:function(data){
			$("#totalImpressNum").text(data.totalImpressNum);
			$("#totalClickNum").text(data.totalClickNum);
			$("#clickRate").text(data.clickRate + "%");
			$("#cpmPrice").text(data.cpmPrice);
			$("#totalamount").text(data.totalAmount);
		}
	});
	
	drawChart(index, start, end, type, ids);
	setDefault(ids);
	setCheckAll(checkAll);

}).on("click","input:radio",function(){
	var start = $("#start").val();
	var end = $("#end").val();
	var index = $("a.none").attr("index");
	var type = $(this).val();
	var ids = getCheckAdIds();
	drawChart(index, start, end, type, ids);
}).on("click","input[name='checkedAll']",function(){
	if($(this).attr("checked")){
		$("input[name='check_name']").each(function(){
			$(this).attr("checked",true);
		});
		$("#confirm").click();
		Sum();
		$("#sumNum").show();
	}else{
		$("input[name='check_name']").each(function(){
			$(this).attr("checked",false);
		});
		$("#confirm").click();
		init_Sum();
		//$("#sumNum").hide();
	}
	
}).on("click","input[name='check_name']",function(){
	
	var start = $("#start").val();
	var end = $("#end").val();
	var index = $("a.none").attr("index");
	var type = $("input[name='type']:checked").val();
	var id = $("#id").length == 0 ? 0 : $("#id").val();
	var ids = getCheckAdIds();
	var checkAll = getCheckAll();
	$.ajax({
		url:"/ad/template",
		data:{start:start,end:end,index:index,id:id},
		async:false,
		type:'GET',
		dataType:'text',
		success:function(data){
			$("#listTable").empty();
			$("#listTable").html(data);
		}
	});
	
	drawChart(index, start, end, type, ids);
	setDefault(ids);
	setCheckAll(checkAll);
	
	//================
	var pv = $(this).parents("td").siblings("[name='pv']").text();
	var uv = $(this).parents("td").siblings("[name='uv']").text();
	var ip = $(this).parents("td").siblings("[name='ip']").text();
	var click = $(this).parents("td").siblings("[name='click']").text();
	var mobilePv = $(this).parents("td").siblings("[name='mobilePv']").text();
	var mobileClick = $(this).parents("td").siblings("[name='mobileClick']").text();
	var close = $(this).parents("td").siblings("[name='close']").text();
	
	if($(this).attr("checked")){
		total_pv += parseInt(pv);
		total_uv += parseInt(uv);
		total_ip += parseInt(ip);
		total_click += parseInt(click);
		total_mobilePv += parseInt(mobilePv);
		total_mobileClick += parseInt(mobileClick);
		total_close += parseInt(close);
		
	}else{
		total_pv -= parseInt(pv);
		total_uv -= parseInt(uv);
		total_ip -= parseInt(ip);
		total_click -= parseInt(click);
		total_mobilePv -= parseInt(mobilePv);
		total_mobileClick -= parseInt(mobileClick);
		total_close -= parseInt(close);
	}
	
	$("#pvSum").text(total_pv);
	$("#uvSum").text(total_uv);
	$("#ipSum").text(total_ip);
	$("#clickSum").text(total_click);
	$("#mobilePvSum").text(total_mobilePv);
	$("#mobileClickSum").text(total_mobileClick);
	$("#clickRateSum").text(total_pv);
	$("#closeSum").text(total_close);
	
	if(((total_click / total_pv) * 100).toFixed(4) == 0.0000 || total_pv == '0'){
		$("#clickRateSum").text('0.0%');
	}else{
		$("#clickRateSum").text(((total_click / total_pv) * 100).toFixed(4) + "%");
	}
	//================
	
	$("#sumNum").show();
//	if(!isAllChecked()) {
//	$("#sumNum").hide();
//	}
	
}) .on("click" , "#download" , function(){
		var $start = $("#start").val();
		var $end = $("#end").val();
		var $id = $("#id").length == 0 ? 0 : $("#id").val();
		$("#start_ex").val($start);
		$("#end_ex").val($end);
		$("#id_ex").val($id);
		$("#exportFrom").submit();
}); 

function isAllChecked() {
    var adIds = "";
    $("input:checkbox[name=check_name]:checked'").each(function(i){
        if(0==i){
            adIds = $(this).val();
        }else{
            adIds += (","+$(this).val());
        }
    });
    if(adIds=="") {
        return false;
    }
    return true;
}

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
		month = month == 0 ? 12 : month;
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
	// 清空 checkbox，清空值。
	setCheckCancel();
	init_Sum();
}

//总数初始化
function init_Sum(){
	total_pv = 0;
	total_uv = 0;
	total_ip = 0;
	total_click = 0;
	total_mobilePv = 0;
	total_mobileClick = 0;
	total_close =0;
	total_amount = 0;
}
function Sum(){
	//展现量总数 
	$("td[name='pv']").each(function(){
		var pv = $(this).text();
		total_pv += parseInt(pv);
	});
	$("#pvSum").text(total_pv);
	//独立访客总数
	$("td[name='uv']").each(function(){
		var uv = $(this).text();
		total_uv += parseInt(uv);
	});
	$("#uvSum").text(total_uv);
	//独立IP总数
	$("td[name='ip']").each(function(){
		var ip = $(this).text();
		total_ip += parseInt(ip);
	});
	$("#ipSum").text(total_ip);
	//点击量总数 
	$("td[name='click']").each(function(){
		var click = $(this).text();
		total_click += parseInt(click);
	});
	$("#clickSum").text(total_click);
	//点击率总数 
	if(total_click!=0){
		var clickRates = (total_click / total_pv) * 100;
		if(clickRates.toFixed(4) == 0.0000){
			$("#clickRateSum").text('0.0%');
		}else{
			$("#clickRateSum").text(clickRates.toFixed(4) + "%");
		}
	}else{
		$("#clickRateSum").text('0.0%');
	}
	//移动端展现量总数 
	$("td[name='mobilePv']").each(function(){
		var mobilePv = $(this).text();
		total_mobilePv += parseInt(mobilePv);
	});
	$("#mobilePvSum").text(total_mobilePv);
	//移动端点击量总数 
	$("td[name='mobileClick']").each(function(){
		var mobileClick = $(this).text();
		total_mobileClick += parseInt(mobileClick);
	});
	$("#mobileClickSum").text(total_mobileClick);
	
	$("td[name='close']").each(function(){
		var close = $(this).text();
		total_close += parseInt(close);
	});
	$("#closeSum").text(total_close);
	
	$("td[name='totalAmount']").each(function(){
		var totalAmount = $(this).text();
		total_amount += parseFloat(totalAmount);
	});
	$("#totalAmount").text(total_amount);
}
function drawChart(index, start, end, type, ids){
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
	
	
	//var index = $("a.none").attr("index");
 	var index ;
	if(start == end){
		index = 1;
	}else{
		index = 6;
	} 
	var id = $("#id").length == 0 ? 0 : $("#id").val();
	var dataArr = [];
	$.ajax({
		url:"/material/chart",
		data:{start:start,end:end,index:index,type:type,reportType:2,id:id, ids:ids},
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
	//alert(chartResult);
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


function getCheckAdIds() {
    var adIds = "";
    $("input:checkbox[name=check_name]:checked'").each(function(i){
        if(0==i){
            adIds = $(this).val();
        }else{
            adIds += (","+$(this).val());
        }
    });
    return adIds;
}

//设置 下面 table checkbox 选中
function setDefault(ids) {
    if(ids!=null && ids != "") {
        var idarr = ids.split(",");
        for(var i=0; i < idarr.length; i++) {
            $("[name = check_name][value$="+ idarr[i] +"]:checkbox").attr("checked", true);
        }
    }
}

// 获取 checkAll 选中状态
function getCheckAll() {
    var check = $("[name = checkedAll]:checkbox").attr("checked");
    if(check == null) {
        check = "notCheck";
    }
    return check;
}

// 设置 checkAll 选中
function setCheckAll(sts) {
	   if(sts!='notCheck') {
           $("[name = checkedAll]:checkbox").attr("checked", true);
       }
}

//设置 checkAll 选中
function setCheckCancel() {
	
	 $("input[name='checkedAll']").each(function(){
		   $(this).attr("checked",false);
	 });
	 
	 $("input[name='check_name']").each(function(){
		   $(this).attr("checked",false);
	 });
	
}
