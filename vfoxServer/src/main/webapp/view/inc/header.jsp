<%@ page contentType="text/html;charset=UTF-8"%>

	<header id="header">
		<hgroup>
			<h1 class="site_title"><a href="role.html"><img src="/resources/assets/index_logo.png" style="position:relative;top:8px; width:300px;"/></a></h1>
			<h2 class="section_title"></h2><div class="btn_view_site"><a href="/logout">注销</a></div>
		</hgroup>
	</header> <!-- end of header bar -->
	
	<section id="secondary_bar">
		<div class="user">
			 <p>欢迎 ${sessionScope.user.userName}用户 </p>
			<!-- <a class="logout_user" href="#" title="Logout">Logout</a> -->
		</div>
		<div class="breadcrumbs_container">
			<article class="breadcrumbs"><a href="javascript:;" id="daohang1"></a> <div class="breadcrumb_divider"></div><a class="current" id="daohang2"></a> </article>
			<a class="current_time" id="bgclock"></a>
		</div>
		
	</section>
	
<script type="text/javascript">
$(document).ready(function(){
	var first = "";
	var second = "";
	clockon('bgclock');
	var navigation = "${requestScope.navigation}";
	if(navigation == 'ggshgl_1'){
		first = "广告审核管理:";
		second = "角色维护";
	}else if(navigation == 'ggshgl_2'){
		first = "广告审核管理:";
		second = "待审核管理";
	}else if(navigation == 'ggshgl_3'){
		first = "广告审核管理:";
		second = "审核进度查看";
	}else if(navigation == 'ggtjgl_1'){
		first = "广告统计管理:";
		second = "广告统计列表查看";
	}else if(navigation == 'ggtjgl_3'){
		first = "广告统计管理:";
		second = "广告投放报告";
	}else if(navigation == 'ggtfgl_1'){
		first = "广告投放管理:";
		second = "域名白名单管理";
	}else if(navigation == 'ggtfgl_2'){
		first = "广告投放管理:";
		second = "用户白名单管理";
	}else if(navigation == 'ggtfgl_3'){
		first = "广告投放管理:";
		second = "投放控制设置";
	}else if(navigation == 'zhgl_1'){
		first = "账户管理:";
		second = "代理商子账户管理";
	}else if(navigation == 'zhgl_2'){
		first = "账户管理:";
		second = "管理员账户管理";
	}
	
	$("#daohang1").text(first);
	$("#daohang2").text(second);
});
function clockon(bgclock){
	 var now=new Date();
	 var year=now.getFullYear();
	 var month=now.getMonth();
	 var date=now.getDate();
	 var day=now.getDay();
	 var hour=now.getHours();
	 var minu=now.getMinutes();
	 var sec=now.getSeconds();
	 var week;
	 month=month+1;
	 if(month<10) month="0"+month;
	 if(date<10) date="0"+date;
	 if(hour<10) hour="0"+hour;
	 if(minu<10) minu="0"+minu;
	 if(sec<10) sec="0"+sec;
	 var arr_week=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");

	 week=arr_week[day];
	 var time="";
	 time=year+"年"+month+"月"+date+"日"+"    "+week+"    "+hour+":"+minu+":"+sec;
	 $("#"+bgclock).text(time);
	 setTimeout("clockon('bgclock')",1000);
	} 
	
	
</script>