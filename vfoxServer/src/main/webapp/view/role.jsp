<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta charset="utf-8"/>
	<title>精准弹窗控制平台角色管理</title>
	
	<link rel="stylesheet" href="/resources/css/layout.css" type="text/css" media="screen" />
	<!--[if lt IE 9]>
	<link rel="stylesheet" href="css/ie.css" type="text/css" media="screen" />
	
	<![endif]-->
	<script src="/resources/js/jquery-1.5.2.min.js" type="text/javascript"></script>
	<script src="/resources/js/hideshow.js" type="text/javascript"></script>
	<script src="/resources/js/jquery.tablesorter.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="/resources/js/jquery.equalHeight.js"></script>
	<script src="/resources/js/common.js" type="text/javascript"></script>
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

	<header id="header">
		<hgroup>
			<h1 class="site_title"><a href="/view/role.jsp"><img src="/resources/assets/index_logo.png" style="position:relative;top:8px; width:300px;"/></a></h1>
			<h2 class="section_title"></h2><div class="btn_view_site"><a href="login.html">注销</a></div>
		</hgroup>
	</header> <!-- end of header bar -->
	
	<section id="secondary_bar">
		<div class="user">
			<p>John Doe (<a href="#">3 Messages</a>)</p>
			<!-- <a class="logout_user" href="#" title="Logout">Logout</a> -->
		</div>
		<div class="breadcrumbs_container">
			<article class="breadcrumbs"><a href="/view/role.jsp">首页</a> <div class="breadcrumb_divider"></div> <a class="current">角色维护</a></article>
		</div>
	</section><!-- end of secondary bar -->
	
	<%@include file="/view/inc/menu.jsp"%>
	
	<section id="main" class="column">
		<div class="module_content">
			<span class="search_span">角色名称：</span>
			<input type="text" name="text" class="search_inp" />
			<span class="search_span">用户姓名：</span>
			<input type="text" name="text" class="search_inp" />
			<span class="search_span">所属部门：</span>
			<select>
				<option>--请选择--</option>
			</select>	
		</div>
		<div class="module_content">
			<span class="search_span">手机号码：</span>
			<input type="text" name="text" class="search_inp" />
			<input type="submit" value="查询" class="alt_btn">
			<input type="submit" value="角色添加" id="add_role">
		</div>
		<article class="module width_3_quarter">
			<div class="tab_container">
			<div id="tab1" class="tab_content">
			<table class="tablesorter" cellspacing="0"> 
			<thead> 
				<tr> 
   					<th><input type="checkbox" /></th> 
    				<th>角色名称</th> 
    				<th>用户姓名</th> 
    				<th>所属部门</th> 
    				<th>绑定账号</th> 
    				<th>手机号码</th> 
    				<th>Email</th> 
    				<th>更新时间</th> 
    				<th>操作</th> 
				</tr> 
			</thead> 
			<tbody> 
				<tr class="tbody_tr"> 
   					<td><input type="checkbox" /></td> 
    				<td>系统管理员</td> 
    				<td>张三</td> 
    				<td>研发部</td> 
    				<td>zs123123</td> 
    				<td>13521487745</td> 
    				<td>zs@hotmail.com</td> 
    				<td>2014-5-11 15:20:36</td> 
    				<td><input type="image" id="update_role" src="/resources/images/icn_edit.png" title="编辑"><input type="image" src="/resources/images/icn_trash.png" title="删除"></td> 
				</tr> 
				<tr class="tbody_tr"> 
   					<td><input type="checkbox"></td> 
    				<td>产品经理</td> 
    				<td>张三</td> 
    				<td>研发部</td> 
    				<td>zs123123</td> 
    				<td>13521487745</td> 
    				<td>zs@hotmail.com</td> 
    				<td>2014-5-11 15:20:36</td> 
    				<td><input type="image" id="update_role" src="/resources/images/icn_edit.png" title="编辑"><input type="image" src="/resources/images/icn_trash.png" title="删除"></td> 
				</tr>
				<tr class="tbody_tr"> 
   					<td><input type="checkbox"></td> 
    				<td>市场研究专员</td> 
    				<td>张三</td> 
    				<td>研发部</td> 
    				<td>zs123123</td> 
    				<td>13521487745</td> 
    				<td>zs@hotmail.com</td> 
    				<td>2014-5-11 15:20:36</td> 
    				<td><input type="image" id="update_role" src="/resources/images/icn_edit.png" title="编辑"><input type="image" src="/resources/images/icn_trash.png" title="删除"></td> 
				</tr> 
				<tr class="tbody_tr"> 
   					<td><input type="checkbox"></td> 
    				<td>项目经理</td> 
    				<td>张三</td> 
    				<td>研发部</td> 
    				<td>zs123123</td> 
    				<td>13521487745</td> 
    				<td>zs@hotmail.com</td> 
    				<td>2014-5-11 15:20:36</td> 
    				<td><input type="image" id="update_role" src="/resources/images/icn_edit.png" title="编辑"><input type="image" src="/resources/images/icn_trash.png" title="删除"></td> 
				</tr>
				<tr class="tbody_tr"> 
   					<td><input type="checkbox"></td> 
    				<td>销售总监</td> 
    				<td>张三</td> 
    				<td>研发部</td> 
    				<td>zs123123</td> 
    				<td>13521487745</td> 
    				<td>zs@hotmail.com</td> 
    				<td>2014-5-11 15:20:36</td> 
    				<td><input type="image" id="update_role" src="/resources/images/icn_edit.png" title="编辑"><input type="image" src="/resources/images/icn_trash.png" title="删除"></td> 
				</tr>  
			</tbody> 
			</table>
			</div>
		</div>
		
		</article>
		
	</section>
		<div id="floatBoxBg"></div>
		<!--添加弹层-->
		<form action="" method="get">
		<div class="tcLsyer" style="display:none;">
			<div class="tcLsyer_title">
				<span class="is_check">角色添加</span>
				<a href="#" class="close"></a>
			</div>
			<div class="tcLsyer_content">
				<div class="content_DIV">
					<span class="span">角色名称：</span>
					<input type="text" name="text" class="tc_inp" />
					<span class="tips">*角色名称不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="span">用户姓名：</span>
					<input type="text" name="text" class="tc_inp" />
					<span class="tips">*用户姓名不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="span">所属部门：</span>
					<select>
						<option>--请选择--</option>
					</select>
					<span class="tips">*所属部门不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="span">绑定账号：</span>
					<input type="text" name="text" class="tc_inp" />
					<span class="tips">*账号不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="span">手机号码：</span>
					<input type="text" name="text" class="tc_inp" />
					<span class="tips">*手机号码不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="span">Email：</span>
					<input type="text" name="text" class="tc_inp" />
					<span class="tips">*邮箱不能为空</span>
				</div>
				<div class="content_DIV">
					<input type="submit" value="确定" class="alt_btn" style="margin-left:20%;"/>
					<input type="reset" value="重置" />
				</div>
			</div>
		</div>
		</form>
			
		
		<!--修改弹层-->
		
			<div class="tcLsyer_all" style="display:none;">
				<div class="tcLsyer_title">
					<span class="is_check">角色修改</span>
					<a href="#" class="close_all"></a>
				</div>
				<div class="tcLsyer_content">
					<div class="content_DIV">
						<span class="span">角色名称：</span>
						<input type="text" name="text" class="tc_inp" value="放风筝的人" />
						<span class="tips">*角色名称不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="span">用户姓名：</span>
						<input type="text" name="text" class="tc_inp" value="放风筝的人" />
						<span class="tips">*用户姓名不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="span">所属部门：</span>
						<select>
							<option>业务部门</option>
						</select>
						<span class="tips">*所属部门不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="span">绑定账号：</span>
						<input type="text" name="text" class="tc_inp" value="23433453"/>
						<span class="tips">*账号不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="span">手机号码：</span>
						<input type="text" name="text" class="tc_inp" value="1324534578"/>
						<span class="tips">*手机号码不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="span">Email：</span>
						<input type="text" name="text" class="tc_inp" value="1233@hotmail.com"/>
						<span class="tips">*邮箱不能为空</span>
					</div>
					<div class="content_DIV">
						<input type="submit" value="确定" class="alt_btn" style="margin-left:20%;"/>
						<input type="reset" name="button" value="重置" />
					</div>
				</div>
			</div>
		
</body>

</html>