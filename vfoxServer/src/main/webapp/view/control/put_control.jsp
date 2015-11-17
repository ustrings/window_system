<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/view/inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>
	<meta charset="utf-8"/>
	<title>精准弹窗控制平台广告统计列表查看</title>
	
	<link rel="stylesheet" href="/resources/css/layout.css" type="text/css" media="screen" />
	<!--[if lt IE 9]>
	<link rel="stylesheet" href="css/ie.css" type="text/css" media="screen" />
	
	<![endif]-->
	<script src="/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<script src="/resources/js/hideshow.js" type="text/javascript"></script>
	<script src="/resources/js/jquery.tablesorter.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="/resources/js/jquery.equalHeight.js"></script>
	<script src="/resources/js/common.js" type="text/javascript"></script>
	<script type="text/javascript" src="/resources/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	$(document).ready(function() 
    	{ 
      	  $(".tablesorter").tablesorter(); 
   	 } 
	);
	$(document).ready(function() {

	//When page loads...
	$(".tab_content").hide(); //Hide all content
	$("ul.tabs li:first").addClass("active").show(); //Activate first tab
	$(".tab_content:first").show(); //Show first tab content

	//On Click Event
	$("ul.tabs li").click(function() {

		$("ul.tabs li").removeClass("active"); //Remove any "active" class
		$(this).addClass("active"); //Add "active" class to selected tab
		$(".tab_content").hide(); //Hide all tab content

		var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content
		$(activeTab).fadeIn(); //Fade in the active ID content
		return false;
	});

});
    </script>
    <script type="text/javascript">
    $(function(){
        $('.column').equalHeight();
    });
</script>

</head>


<body>
	<div class="index_all">
	<%@include file="/view/inc/header.jsp" %>
	<div class="index_content_all">
	<%@include file="/view/inc/menu.jsp"%>
	
	<section id="main" class="column">
			<div class="module_content" style="width:96%;height:100%;">
				<div class="control_DIV">
					<div class="control_one">
						<span class="control_span">用户推送频次：</span>
						<input type="text" name="text" class="control_inp" id="frequencyDay"  readonly="readonly" value="${adControlDto.frequencyDay }"/>
						<span class="day_span">/天</span>
						<input type="image" title="编辑" src="/resources/images/icn_edit.png" class="control_opera" style="margin-left:3%;"/>
						<span class="control_tips">投放频次就是对单个用户一天的投放次数</span>
					</div>
					<div class="control_one">
						<span class="control_span">用户推送间隔：</span>
						<input type="text" name="text" class="control_inp" id="spacingMin"  readonly="readonly" value="${adControlDto.spacingMin }"/>
						<span class="day_span">/分钟</span>
						<input id="edit_two" type="image" title="编辑" src="/resources/images/icn_edit.png" class="control_opera" />
						<span class="control_tips">投放时间间隔就是多长时间投放一次</span>
					</div>
					<div class="control_one">
						<span class="control_span">用户推送总量：</span>
						<input type="text" name="text" class="control_inp" id="pvTotal"  readonly="readonly" value="${adControlDto.pvTotal }"/>
						<span class="day_span">PV</span>
						<input id="edit_three" type="image" title="编辑" src="/resources/images/icn_edit.png" class="control_opera" style="margin-left:3%;"/>
						<span class="control_tips">投放总量就是一天总的投放次数</span>
					</div>
					<input type="button" value="确定" class="buttonClass" style="margin-left:300px; margin-top: 30px;" id="okEdit"/>
				</div>
					
			</div>
		</section>

	<form action="/control/edit" method="post" id="formEdit">
		<input type="hidden" name="frequencyDay" value="">
		<input type="hidden" name="spacingMin" value="">
		<input type="hidden" name="pvTotal" value="">
	</form>
	</div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	
}).on("click","input[type='image']",function(){
	$(this).parent().find("input[type='text']").attr("readonly",false);
}).on("click","#okEdit",function(){
	if(checkInput()){
		$("#formEdit").submit();
	}
});

function checkInput(){
	flag = true;
	regx = /^(\+)?[0-9]*$/;// 判断是否是正整数+0
	var frequencyDay = $("#frequencyDay").val();
	var spacingMin = $("#spacingMin").val();
	var pvTotal = $("#pvTotal").val();
	
	if($.trim(frequencyDay) != ""){
		if(!regx.test($.trim(frequencyDay))){
			alert("用户推送频次输入不合法。");
			flag = false;
			return flag;
		}else{
			$("input[name=frequencyDay]").val($.trim(frequencyDay));
		}
	}
	if($.trim(spacingMin) != ""){
		if(!regx.test($.trim(spacingMin))){
			alert("用户推送间隔输入不合法。");
			flag = false;
			return flag;
		}else{
			$("input[name=spacingMin]").val($.trim(spacingMin));
		}
	}
	if($.trim(pvTotal) != ""){
		if(!regx.test($.trim(pvTotal))){
			alert("用户推送总量输入不合法。");
			flag = false;
			return flag;
		}else{
			$("input[name=pvTotal]").val($.trim(pvTotal));
		}
	}
	
	return flag;
}

</script>
</html>