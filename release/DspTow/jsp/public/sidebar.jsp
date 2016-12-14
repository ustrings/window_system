<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	$(function(){
		$(".sidebar-menu").find("li:gt(0)").css({"cursor":"pointer"});
		$("#toAdSelectPage").click(function(){
			$(this).parent().find("li").attr("class","");
			$(this).attr("class","active");
			$.post("/DspTow/dspAdAction/toAdSelectPage.do",function(result){
				$(".content-wrapper").html(result);
			});
		});
		$("#toDomainPage").click(function(){
			$(this).parent().find("li").attr("class","");
			$(this).attr("class","active");
			$.post("/DspTow/dspDomainAction/toDomainPage.do",function(result){
				$(".content-wrapper").html(result);
			});
		});
		$("#toAdpage").click(function(){
			
			wayes(this);
			
			$.post("/DspTow/dspAdAction/toAdpage.do",function(result){
				$(".content-wrapper").html(result);
			});
		});
		
	});
	
	function wayes(htmlobj){
				$(htmlobj).parent().find("li:gt(0)").attr("class","treeview");
				$.each($(htmlobj).parent().find("li"),function(i,obj){
					if($(this).find("ul")!=undefined){
						$(this).find("ul").attr("class","treeview-menu").hide();
					}
				});
				$(htmlobj).attr("class","active treeview");
			}
</script>
     <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="/DspTow/statics/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
                    <p>${sessionScope.user.nickname}</p>

                    <a><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu">
                <li class="header">主导航</li>
                <li class="active treeview">
                    <a href="dspUserAction/toSystemPage.do">
                        <i class="fa fa-dashboard"></i> <span>系统</span> <i class="fa fa-angle-left pull-right"></i>
                    </a>
                </li>
                <li class="treeview" id="toAdpage">
                    <a >
                        <i class="fa fa-pie-chart"></i>
                        <span>需求</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                </li>
                <li class="treeview">
                    <a href="#">
                        <i class="fa fa-table"></i> <span>统计</span>
                        <i class="fa fa-angle-left pull-right"></i>
                    </a>
                    <ul class="treeview-menu">
                        <li class="active" id="toAdSelectPage"><a><i class="fa fa-circle-o"></i>需求统计</a></li>
                        <li id="toDomainPage"><a><i class="fa fa-circle-o"></i>域名统计</a></li>
                    </ul>
                </li>
            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>