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
		<meta name="description"
			content="Charisma, a fully featured, responsive, HTML5, Bootstrap admin template.">
			<meta name="author" content="Muhammad Usman">

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
					<p>
						You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.
					</p>
				</div>
			</noscript>

			<div id="content" class="span10">
				<!-- content starts -->


				<div>
					<ul class="breadcrumb">
						<li><a href="#">首页</a> <span class="divider">/</span></li>
						<li><a href="#">受众人群列表</a></li>
					</ul>
				</div>
				
				<div class="demo-container">
					<div id="placeholder" class="center" style="height:300px" ></div>
				</div>

                         <a href="/crowd/toAddCrowd" class="btn btn-primary" id="urlOceanSel" >新增受众人群</a>
                         <a href="#" onclick="updateCrowdSts('S')" class="btn btn-primary" id="urlOceanSel" >暂停</a>
                         <a href="#" onclick="updateCrowdSts('A')" class="btn btn-primary" id="urlOceanSel" >重新启动</a>
                         <a href="#" onclick="updateCrowdSts('D')" class="btn btn-primary" id="urlOceanSel" >删除</a>

				<div class="row-fluid sortable">
					<div class="box span12">
						<div class="box-header well" data-original-title>
							<h2>
								<i class="icon-user"></i> 受众人群列表
							</h2>
<!-- 							<div class="box-icon"> -->
<!-- 								<a href="#" class="btn btn-setting btn-round"><i -->
<!-- 									class="icon-cog"></i></a> <a href="#" -->
<!-- 									class="btn btn-minimize btn-round"><i -->
<!-- 									class="icon-chevron-up"></i></a> <a href="#" -->
<!-- 									class="btn btn-close btn-round"><i class="icon-remove"></i></a> -->
<!-- 							</div> -->
						</div>
						<div class="box-content">
							
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

			
	</div>
	<!--/.fluid-container-->
	<footer>
<p class="pull-leftt">
<a target="_blank" href="http://www.vaolan.com">Vaolan Corp. 2014</a>
</p>
<p class="pull-right_t">
Powered by:
<a href="http://www.vaolan.com">Vaolan Corp.</a>
</p>
</footer>
	<%@include file="../inc/footer.jsp"%>
</body>

<script>
$(document).ready(function(){
	initCrowdList();
	drawChart("");
});

function afterCheckBoxPress() {
	var crowdIds = getCheckAdIds("subCheckBox");
	drawChart(crowdIds);
}

// 图表的初始化参数
var options = {
        lines: {
            show: true
        },
        points: {
            show: true
        },
        xaxis: {
            tickDecimals: 0,
            tickSize: 1
        },
        grid: { hoverable: true}// 开启 hoverable 事件
    };
    
    function drawChart(crowdIds) {
    	//alert("调用到了！");
    	var dataArr = [];
    	$.ajax({
    		url:"/crowd/chart",
    		data:{crowdIds:crowdIds},
    		async:false,
    		type:'POST',
    		dataType:'json',
    		success:function(data){
    			if (typeof data != 'undefined'){
    				$.each(data,function(key, value) {
						//alert(key + " : " + value);
						key = key + "";
    					//dataArr.push("label:\'" + key + "\',data:" + value+ "");
    					
    					dataArr.push(eval("("+"{label:\'" + key + "\',data:" + value + "}"+")"));
    					//dataArr.push("{label:\'" + key + "\',data:" + value + "}");
					});
    			}
    		}
    	});

    	if (dataArr.length == 0){
    		dataArr.push(eval("("+"{label:\'\',data:[]}"+")"));
    	}
    	// example
    	//var str = '{label: "", data: [[1, 2], [2, 3], [3, 1], [4, 1], [5, 6], [6, 3], [7, 4], [8, 5], [9, 1], [10, 2], [11, 3], [12, 7],[13,1],[14,1],[15,1],[16,1],[17,1],[18,1],[19,1],[20,1],[21,1],[22,1],[23,1],[24,1]]}';
    	//alert(chartResult);
        // 为图添加数据
    	$.plot("#placeholder", dataArr,options);

        // 节点提示
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
        $("#placeholder").bind("plothover", function (event, pos, item) {
            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;
                    $("#tooltip").remove();
                    var y = item.datapoint[1].toFixed(0);

                    var tip = "人数：";
                    showTooltip(item.pageX, item.pageY,tip+y);
                }
            }
            else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });
    }



function initCrowdList() {
	//alert("查询数据");
	WaitingBar.show();
	var url = "/crowd/initCrowdList";
	$.ajax({
		url:url,
		type:'GET',
		dataType:'html',
		async:true,
		success: function(result){
			$(".box-content").empty();
			$(".box-content").append(result);
			WaitingBar.hide();
		},error:function(){
			alert("error");
			WaitingBar.hide();
		}
	});
}

/**
 * 修改人群的状态
 */
function updateCrowdSts(stat) {
	

	//alert("查询数据");
	var ids  = getCheckAdIds('subCheckBox');
	if(ids==""){
		alert("请选择人群");
		return;
	}
	WaitingBar.show();
	var url = "/crowd/updateCrowStat";
	$.ajax({
		url:url,
		data:{ids:ids, stat:stat},
		type:'GET',
		dataType:'json',
		async:true,
		success: function(data){
			if(data.result) {
				alert("操作成功！");
				initCrowdList();
				WaitingBar.hide();
			} else{
				alert("操作失败！");
				WaitingBar.hide();
			}
		},error:function(){
			alert("操作失败！");
			WaitingBar.hide();
		}
	});
}

/**
 * 全选实现
 * @param all 全选 checkbox 本身
 * @param items 被选择的子 checkbox 名称
 */
function checkAll(all,elementsName) {
    var state = all.checked;
    var items = document.getElementsByName(elementsName);
    if(items.length) {
        for(var i=0;i<items.length;i++) {
            items[i].checked = state;
        }
    }
    afterCheckBoxPress();
}

/**
 * 获取被选中的子 checkbox 值集合字符串
 * @param elementsName 子 checkbox 元素名称
 */
function getCheckAdIds(elementsName) {
    var adIds = "";
    $("input:checkbox[name=" + elementsName + "]:checked'").each(function(i){
        if(0==i){
            adIds = $(this).val();
        }else{
            adIds += (","+$(this).val());
        }
    });
    //alert(adIds);
    return adIds;
}

</script>
</html>
