<%@ page contentType="text/html;charset=UTF-8"%>
		<aside id="sidebar" class="sidebar">
			<a class="header_toggle" style="float:right;"><img src="/resources/images/slow.png" class="slow_img"/></a>
			<hr/>
			<div class="menu_line">		
				<h3>广告审核管理</h3>
				<ul class="toggle">
					<li class="icn_view_users"><a href="/role/init?navigation=ggshgl_1" class="none_current" id="ggshgl_1">角色维护</a></li>
					<li class="icn_new_article"><a href="/check/init?navigation=ggshgl_2" class="none_current" id="ggshgl_2">待审核管理</a></li>
					<!--<li class="icn_edit_article"><a href="#" class="none_current">审核失败管理</a></li>-->
					<li class="icn_categories"><a href="/checkProgress/init?navigation=ggshgl_3" class="none_current" id="ggshgl_3">审核进度查看</a></li>
					<!--<li class="icn_tags"><a href="#">Tags</a></li>-->
				</ul>
				<h3>广告统计管理</h3>
				<ul class="toggle">
					<li class="icn_folder"><a href="/ad_count/init?navigation=ggtjgl_1" class="none_current" id="ggtjgl_1">广告统计列表查看</a></li>
					<!-- <li class="icn_photo"><a href="put_detail.html" class="none_current" id="ggtjgl_2">广告投放明细</a></li> -->
					<li class="icn_audio"><a href="/report/init?navigation=ggtjgl_3" class="none_current" id="ggtjgl_3">广告投放报告</a></li>
				</ul>
				<h3>广告投放管理</h3>
				<ul class="toggle">
					<li class="icn_settings"><a href="/domain/init?navigation=ggtfgl_1" class="none_current" id="ggtfgl_1">域名白名单管理</a></li>
					<li class="icn_security_2"><a href="/people/init?navigation=ggtfgl_2" class="none_current" id="ggtfgl_2">用户白名单管理</a></li>
					<li class="icn_jump_back"><a href="/control/init?navigation=ggtfgl_3" class="none_current" id="ggtfgl_3">投放控制设置</a></li>
				</ul>
				<h3>账户管理</h3>
				<ul class="toggle">
					<li class="icn_settings"><a href="/agent/init?navigation=zhgl_1" class="none_current" id="zhgl_1">代理商子账户管理</a></li>
					<li class="icn_security"><a href="/system/init?navigation=zhgl_2" class="none_current" id="zhgl_2">管理员账户管理</a></li>
					<!--<li class="icn_jump_back"><a href="#">Logout</a></li>-->
				</ul>
			</div>
		</aside><!-- end of sidebar -->
<script>
	$(document).ready(function(){
		//导航显示 
		var navigation = "${requestScope.navigation}";
		$("#" + navigation).removeClass().addClass("current_a");
	});
</script>
