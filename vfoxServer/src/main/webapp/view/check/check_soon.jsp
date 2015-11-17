<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/view/inc/directive.inc"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width" />
	<title>精准弹窗控制平台待审核管理</title>
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
					<div class="module_content">
						<div class="seach_one">
							<span class="search_span">广告名称：</span>
							<input type="text" name="text" id="adName" class="search_inp" />
							<span class="search_span">审核状态：</span>
							<select name="checksts" id="checksts">
								<option value="">--请选择--</option>
								<option value="1" checked=true>待审核</option>
							</select>
						</div>
						<div class="seach_one">
							<div class="search_DIV_time" style="width:90%;">
								<span class="search_span">起止时间：</span>
								<input id="start_date" name="startTime" type="text" class="Wdate" value="" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end_date\')}'})" readonly="readonly" />
								<span class="search_span_value">至</span>
								<input id="end_date" name="endTime" type="text" class="Wdate" value="" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start_date\')}'})" readonly="readonly" />
								<input type="button" value="查询" class="alt_btn" id="searchBtn" style="margin-left:30px">
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<input type="submit" value="批量审核" id="all_check" style="float:left;margin: 20px 3% 0 3%;">
					<div id="tabDIV" class="tabDIV_list">
					
					</div>					
				</section>
				<div id="floatBoxBg"></div>
				<!--点击审核弹层开始-->
				<div class="tcLsyer" style="display:none;" id="adCheck">
				
				</div>
				<!--点击审核弹层结束-->
				<!--点击批量审核弹层开始-->
				<div class="tcLsyer_all" style="display:none;">
					<div class="tcLsyer_title">
						<span>批量审核</span>
						<a href="#" class="close_all"></a>
					</div>
					<div class="tcLsyer_content">
						<div class="content_DIV">
							<span class="is_check">是否通过审核：</span>
							<input type="radio" name="batch_radio" value="2" class="radio_btn" id="is_all_yes"/>
							<span class="value_span">是</span>
							<input type="radio" name="batch_radio" value="3" class="radio_btn" id="radio_all_no" />
							<span  class="value_span">否</span>
						</div>
						<div class="content_DIV_sugess" id="suggess_all" style="display:none;">
							<span class="is_check">审核意见：</span>
							<textarea cols="30" rows="5" id="checkDesc"></textarea>
						</div>
						<div class="content_DIV">
							<input type="button" value="确定" class="alt_btn" id="batchCheckBtn" style="margin-left:30%;"/>
						    <input type="button" value="重置" id="cancelBatchBtn"/>
						</div>
					</div>			
				</div>
				<!--点击批量审核弹层结束-->
			</div>
		</div>
<script type="text/javascript">
$(document).ready(function(){
	<!--单个审核编辑-->
    $("#radio_yes").click(function(){
		$("#sugess_one").hide();
	});
	<!--批量审核编辑-->
	$("#is_all_yes").click(function(){
		$("#suggess_all").hide();
	});
	
	searchCheckPageInfo(1);
}).on("mouseover",".materialName",function(e){
	var top=$(this).position().top+20;
	var left=$(this).position().left+30;
	$("#materialViewDiv").css({"position":"absolute","top":top+"px","left":left+"px"}).show();
}).on("mouseout",".materialName",function(){
	//var id = $(this).attr("materialId");
	$("#materialViewDiv").hide();
}).on("click","input[name='check_ad']",function(){   //单个审核弹层	
	var checkProcessId = $(this).attr("value");
	
	$.ajax({
		url:"/check/checkadDIV",
	    data:{checkProcessId:checkProcessId},
	    type:'GET',
	    dataType:'html',
		async:true,
		success:function(result){
			$("#adCheck").empty();
			$("#adCheck").append(result);
		},error:function(){
		}
	});
	showBackGround();
	$(".tcLsyer").show();
	showDiv(".tcLsyer");
}).on("click","input[name='checkAd']",function(){ //单个审核  操作
	var adCheckProcessPk = $(this).attr("id");
	var yes_no_radio = document.getElementsByName("radio");
	var radioValue = "";
	var checkdesc = $("#checkDesc").val();
	for(var i=0;i<yes_no_radio.length;i++){
		if(yes_no_radio[i].checked==true){
			radioValue = yes_no_radio[i].value;
			break;
		}
	}
	if(radioValue==""){
		alert("请选择审核是否通过");
		return ;
	}
	$.ajax({
		url:"/check/checkonead",
		data:{adCheckProcessPk:adCheckProcessPk,radioValue:radioValue,checkdesc:checkdesc},
		type:'POST',
		async:true,
		success:function(result){
			$(".tcLsyer").hide();
        	$("#floatBoxBg").hide(); 
			searchCheckPageInfo(1);
			ssdbLoad();
		},error:function(){
		}
	});
}).on("click","#searchBtn",function(){  //查询
	
	searchCheckPageInfo(1);
}).on("click","#select_all",function(){ //多选框选中事件
	var selectall = document.getElementById("select_all");
	var adcheck = document.getElementsByName("adchecklist");
	
	if(selectall.checked == true){
		 for(var i=0;i<adcheck.length;i++){
			 adcheck[i].checked = "checked";
		 }
	 }else{
		for(var i=0;i<adcheck.length;i++){
			adcheck[i].checked = false;
		}
	 }	
}).on("click","input[name='adchecklist']",function(){   //多选框选中事件
	var selectall = document.getElementById("select_all");
	var adcheck = document.getElementsByName("adchecklist");
	
	for(var i=0;i<adcheck.length;i++){
		if(adcheck[i].checked==false){
			selectall.checked=false;
			break;
		}else{
			selectall.checked="checked";
		}
	 }
}).on("click","#all_check",function(){   //批量审核弹层
	var adcheck = document.getElementsByName("adchecklist");
	var adCheckProcessId="";
	for(var i=0;i<adcheck.length;i++){
		if(adcheck[i].checked==true){
			adCheckProcessId = adCheckProcessId+adcheck[i].id+",";
		}
	}	
	if(adCheckProcessId.trim()==''){
		alert("你没有选中任何要审核的广告");
		return ;
	}else{
		showBackGround();
		$(".tcLsyer_all").show();
		showDiv(".tcLsyer_all");
	}
	
}).on("click","#batchCheckBtn",function(){	  //批量审核 操作
	var adcheck = document.getElementsByName("adchecklist");
	var adCheckProcessId="";
	for(var i=0;i<adcheck.length;i++){
		if(adcheck[i].checked==true){
			adCheckProcessId = adCheckProcessId+adcheck[i].id+",";
		}
	}	
	if(adCheckProcessId.trim()!=""){
		adCheckProcessId = adCheckProcessId.trim().substring(0,adCheckProcessId.length-1);	
		var yes_no_radio = document.getElementsByName("batch_radio");
		var radioValue = "";
		var checkdesc = $("#checkDesc").val();
		for(var i=0;i<yes_no_radio.length;i++){
			if(yes_no_radio[i].checked==true){
				radioValue = yes_no_radio[i].value;
				break;
			}
		}	
		if(radioValue==""){
			alert("请选择是否审核通过");
			return ;
		}
		$.ajax({
			url:"/check/batchCheckAd",
			data:{adCheckProcessId:adCheckProcessId,radioValue:radioValue,checkdesc:checkdesc},
			type:'POST',
			async:true,
			success:function(result){
				$(".tcLsyer_all").hide();
            	$("#floatBoxBg").hide(); 
				searchCheckPageInfo(1);
				ssdbLoad();
			},error:function(){
			}
		});
	}
});
function searchCheckPageInfo(curPage){   //分页展示
   $("#tabDIV").empty();
   $("#tabDIV").append("<input type='image' class='loading_img'  src='/resources/images/load.gif' title='加载中,请稍后...'>");
   var adName = $("#adName").val();
   var checksts = $("#checksts").val();
   var startTime = $("#start_date").val();
   var endTime = $("#end_date").val();
   var roleId = "${checkRoleId}";
   $.ajax({
		url:"/check/qryCheckPageList",
		data:{roleId:roleId,curPage:curPage,adName:adName,checksts:checksts,startTime:startTime,endTime:endTime},
		type:'POST',
		dataType:'html',
		async:true,
		success:function(result){
			$("#tabDIV").empty();
			$("#tabDIV").append(result);
		},error:function(){
		}
	});
}
function ssdbLoad(){
	$.ajax({
		url:'http://118.26.145.20:8060/reload ',
		type:'GET',
		dataType:'json',
		async:true,
		success: function(result){
			
		},error:function(){
			
		}
	});
}
</script>		
</body>
</html>