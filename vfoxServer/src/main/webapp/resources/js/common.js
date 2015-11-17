$(function(){
	
	/**
	* 添加的弹窗方法
	*
	**/
	$("#add_role").click(function(){
			showBackGround();
			$(".tcLsyer").show();
			showDiv(".tcLsyer");
	});
	/**
	* 修改的弹窗方法
	*
	**/
	$("#update_role").click(function(){
			showBackGround();
			$(".tcLsyer_all").show();
			showDiv(".tcLsyer_all");
	});
	/**
	 * 导出确认弹层方法
	 *//*
	$("#reportExcel").click(function(){
		showBackGround();
		$(".sure_export").show();
		showDiv(".sure_export");
	});*/
	/**
	 * 导出取消弹层方法
	 */
	$(".reset_btn").click(function(){
		$(".sure_export").hide();
		$("#floatBoxBg").hide();
		
	});
	/**
	 * 报告管理点击详情弹层方法
	 *//*
	$("#details").click(function(){
		showBackGround();
		$(".detail_layer").show();
		//showDiv(".detail_layer");
		
	});
	*//**
	 * 报告管理详情关闭
	 *//*
	$(".close").click(function(){
		$(".detail_layer").hide();
		$("#floatBoxBg").hide();
	});*/
	/**
	*批量审核
	*
	**/
	/*$("#all_check").click(function(){
			showBackGround();
			$(".tcLsyer_all").show();
			showDiv(".tcLsyer_all");
	});*/
	/**
	* 关闭弹窗方法
	*
	**/
	$(".close").click(function(){
		$(".tcLsyer").hide();
		$("#floatBoxBg").hide();
	});
	/**
	* 批量审核关闭弹窗方法
	*
	**/
	$(".close_all").click(function(){
		$(".tcLsyer_all").hide();
		$("#floatBoxBg").hide();
	});
	/**
		单个审核点击否出现意见
	**/
	$("#radio_no").click(function(){
		var content_sugess=$("#sugess_one").css("display");
		if(content_sugess=="none"){
			$("#sugess_one").show();
		}else{
			$("#sugess_one").hide();
		}
	});
	/**
		批量审核点击否出现意见
	**/
	$("#radio_all_no").click(function(){
		var content_sugess_all=$("#suggess_all").css("display")
		if(content_sugess_all=="none"){
			$("#suggess_all").show();
		}else{
			$("#suggess_all").hide();
		}
	});
	
	/**
		点击编辑 光标定位input 标签上
	**/
	$(".control_opera").click(function(){
		$(this).siblings("input[type='text']").focus();
	});
	
	/**
	 * 左侧菜单点击伸缩功能
	 */
	/**$(".header_toggle").click(function(){
		var displayStr=$("#sidebarDiv").css("display");
		if(displayStr=="block"){
			$("#sidebarDiv").slideUp("slow");  //要用div才可以
			$(".header_toggle img").attr("src","/resources/images/slow.png");  //你自己换一下图片
			$("#main").addClass("column_slow").removeClass("column");;
		}else{
			$("#sidebarDiv").slideDown("slow");  
			$(".header_toggle img").attr("src","/resources/images/slow.png");  //你自己换一下图片
			$("#main").addClass("column").removeClass("column_slow");
		}
	});**/
	
	
	
	$(".header_toggle").toggle(
			  function () {
				  var leftPos = $(".header_toggle").position().left-10;
					$("#sidebar").animate({left:-leftPos},1000);  
				$("#main").addClass("column_slow").removeClass("column").animate({left:-leftPos},1000);
			  },
			  function () {
				$("#sidebar").animate({left:0},1000);  
				$("#main").addClass("column").removeClass("column_slow").animate({left:0},1000);
			  }
			);
	
});
	
	/**
	*	弹出背景层
	**/
	function showBackGround(){
		$("#floatBoxBg").css("height",$(document).height());  
		$("#floatBoxBg").css("width",$(document).width());  
		$("#floatBoxBg").show();
	}
	/**
	 * 居中显示
	 */
	function showDiv(obj){
		$(obj).show();
		center(obj);
		$(window).scroll(function(){
			center(obj);
		});
		$(window).resize(function(){
			center(obj);
		});
	}

	function center(obj){
		var windowWidth = document.documentElement.clientWidth;
		var windowHeight = document.documentElement.clientHeight;
		
		var popupHeight = $(obj).height();
		var popupWidth = $(obj).width();
		
		$(obj).css({
			"position":"absolute",
			"top":(windowHeight-popupHeight)/2+$(document).scrollTop(),
			"left":(windowWidth-popupWidth)/2
		});
	}
	