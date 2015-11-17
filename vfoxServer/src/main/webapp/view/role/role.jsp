<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/view/inc/directive.inc"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="utf-8"/>
	<title>精准弹窗控制平台角色管理</title>
	
	<link rel="stylesheet" href="/resources/css/layout.css" type="text/css" media="screen" />
	<!--[if lt IE 9]>
	<link rel="stylesheet" href="css/ie.css" type="text/css" media="screen" />
	
	<![endif]-->
	<script src="/resources/js/jquery-1.8.2.min.js" type="text/javascript"></script>
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
	<div class="index_all">
		<%@include file="/view/inc/header.jsp" %>
	<div class="index_content_all">
		<%@include file="/view/inc/menu.jsp"%>
		<section id="main" class="column">
			<div class="module_content">
				<span class="search_span">角色名称：</span>
				<input type="text" name="checkRoleName" id="checkRoleName" class="search_inp" />
				<span class="search_span">用户姓名：</span>
				<input type="text" name="userName" id="userName" class="search_inp" />
				<span class="search_span">所属部门：</span>
			    <input type="text" name="deptName" id="deptName"　class="search_inp" style="width: 195px; padding: 5px;"/>
			</div>
			<div class="module_content">
				<span class="search_span">手机号码：</span>
				<input type="text" name="telName" id="telName" class="search_inp" />
				<input type="submit" value="查询" class="alt_btn" id="searchRoleInfo">
			    <c:if test="${sessionScope.user.userName == 'nj_admin'}">
				   <input type="submit" value="角色添加" id="add_role_btn">
				</c:if>
			</div>
			<div id="tabDIV" class="tabDIV_list">
			
			</div>
		</section>
		<div id="floatBoxBg"></div>
		<!--添加弹层-->
		<div class="tcLsyer" style="display:none;">
			<div class="tcLsyer_title">
				<span class="is_check">角色添加</span>
				<a href="#" class="close"></a>
			</div>
			<div class="tcLsyer_content">
				<div class="content_DIV">
					<span class="span">角色名称：</span>
					<input type="text" name="checkRoleName" class="tc_inp"  id="addCheckRoleName"/>
					<span class="tips">*角色名称不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="span">用户姓名：</span>
					<input type="text" name="userName" class="tc_inp"  id="addUserName"/>
					<span class="tips">*用户姓名不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="span">所属部门：</span>
					<input type="text" name="deptName" class="tc_inp"  id="addDeptName"/>
					<span class="tips">*所属部门不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="span">绑定账号：</span>
					<input type="text" name="userAcctRel" class="tc_inp"  id="addUserAcctRel"/>
					<span class="tips">*账号不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="span">手机号码：</span>
					<input type="text" name="telNbr" class="tc_inp" id="addTelNbr"/>
					<span class="tips">*手机号码不能为空</span>
				</div>
				<div class="content_DIV">
					<span class="span">Email：</span>
					<input type="text" name="email" class="tc_inp"  id="addEmail"/>
					<span class="tips">*邮箱不能为空</span>
				</div>
				<div class="content_DIV">
					<input type="button" value="确定" class="alt_btn" style="margin-left:20%;" id="sub_role_config_btn"/>
					<input type="button" value="重置" id="restore_btn"/>
				</div>
			</div>
		</div>
			
		
		<!--修改弹层-->
		<div class="tcLsyer_all" style="display:none;" id="editRole"></div>
	</div>
</div>
<script type="text/javascript">
	  $(document).ready(function() { 
		  searchRolePageInfo(1);
	  }).on("click","#add_role_btn",function(){
		    //新增弹层   
			showBackGround();
			$(".tcLsyer").show();
			showDiv(".tcLsyer");
	  }).on("click","#searchRoleInfo",function(){
		    //查询
		  searchRolePageInfo(1);
		}).on("click","#sub_role_config_btn",function(){
		    //提交配置角色新增					
			var reg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;			
			var tel= /^[1][358]\d{9}$/;
			
			var checkRoleName = $("#addCheckRoleName").val();
			var userName = $("#addUserName").val();
			var deptName = $("#addDeptName").val();
			var userAcctRel = $("#addUserAcctRel").val();
			var telNbr =$("#addTelNbr").val();
			var email = $("#addEmail").val();
			var flag =true;
			
			if($.trim(checkRoleName) == ''){
				 flag =false;
			}else if($.trim(userName) == ''){
				 flag =false;
			}else if($.trim(deptName) == ''){
				 flag =false;
			}else if($.trim(userAcctRel) == ''){
				 flag =false;
			}else if($.trim(telNbr) == ''){
				 flag =false;
			}else if($.trim(email) == ''){
				 flag =false;
			}
			if(flag==false){
				alert("必填不能为空");
				return ;
			}
			
			if(!tel.test(telNbr)){
				alert("您输入的手机号码格式不正确");
				return ;
			}
			if(!reg.test(email)){
				alert("您输入的邮箱格式不正确");
			    return ;
			}
            $.ajax({
                url : '/role/addAdCheckConfigRole',
                type : 'POST',
                data : {checkRoleName:checkRoleName,userName:userName,deptName:deptName,               	
                	    userAcctRel:userAcctRel,telNbr:telNbr,email:email},
                dataType:'json',
                success : function(data) {
                	if(data=="1"){
                		$(".tcLsyer").hide();
                    	$("#floatBoxBg").hide();  
                    	searchRolePageInfo(1);
                	}else if(data=="2"){
                		alert("该账号已经绑定用户,每个账号只能绑定一个用户");
                	}else if(data=="0"){
                		alert("你填写的绑定账号不存在,请修改");
                	}             	
                },error:function(){
                	alert("error");
                }
            });		
	    }).on("click","#restore_btn",function(){
			$("#addCheckRoleName").val("");
			$("#addUserName").val("");
			$("#addDeptName").val("");
			$("#addUserAcctRel").val("");
			$("#addTelNbr").val("");
			$("#addEmail").val("");
		}).on("click","input[name='edit_role_config_btn']",function(){
			var reg = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;			
			var tel= /^[1][358]\d{9}$/;
			var operationType = "1";
			
			var checkRoleName = $("#editCheckRoleName").val();
			var userName = $("#editUserName").val();
			var deptName = $("#editDeptName").val();
			var userAcctRel = $("#editUserAcctRel").val();
			var telNbr =$("#editTelNbr").val();
			var email = $("#editEmail").val();
			var id=$(this).attr("id");
			var flag =true;
			if($.trim(checkRoleName) == ''){
				 flag =false;
			}else if($.trim(userName) == ''){
				 flag =false;
			}else if($.trim(deptName) == ''){
				 flag =false;
			}else if($.trim(userAcctRel) == ''){
				 flag =false;
			}else if($.trim(telNbr) == ''){
				 flag =false;
			}else if($.trim(email) == ''){
				 flag =false;
			}
			if(flag==false){
				alert("必填不能为空");
				return ;
			}
			
			if(!tel.test(telNbr)){
				alert("您输入的手机号码格式不正确");
				return ;
			}
			if(!reg.test(email)){
				alert("您输入的邮箱格式不正确");
			    return ;
			}
            $.ajax({
                url : '/role/updateAdCheckConfigRole',
                type : 'POST',
                data : {adCheckConfigPk:id,operationType:operationType,checkRoleName:checkRoleName,userName:userName,deptName:deptName,               	
                	    userAcctRel:userAcctRel,telNbr:telNbr,email:email},
                dataType:'json',
                success : function(data) {
                	if(data=="1"){
                		$(".tcLsyer_all").hide();
                    	$("#floatBoxBg").hide();  
                    	searchRolePageInfo(1);
                	}else if(data=="2"){
                		alert("你填写的绑定账号已绑定用户");
                	}else if(data=="0"){
                		alert("修改失败");
                	}else if(data=="3"){
                		alert("你填写的绑定账号不存在,请修改");
                	}             	
                },error:function(){
                	alert("error");
                }
            });		
		}).on("click","#select_all",function(){ //多选框选中事件
			var selectall = document.getElementById("select_all");
			var role = document.getElementsByName("roleList");
			
			if(selectall.checked == true){
				 for(var i=0;i<role.length;i++){
					 role[i].checked = "checked";
				 }
			 }else{
				for(var i=0;i<role.length;i++){
					role[i].checked = false;
				}
			 }	
		}).on("click","input[name='roleList']",function(){   //多选框选中事件
			var selectall = document.getElementById("select_all");
			var role = document.getElementsByName("roleList");
			
			for(var i=0;i<role.length;i++){
				if(role[i].checked==false){
					selectall.checked=false;
					break;
				}else{
					selectall.checked="checked";
				}
			 }
		});
			
function searchRolePageInfo(curPage){
	$("#tabDIV").empty();
	$("#tabDIV").append("<input type='image' class='loading_img'  src='/resources/images/load.gif' title='加载中,请稍后...'>");
    var checkRoleName = $("#checkRoleName").val();
	var userName = $("#userName").val();
	var deptName = $("#deptName").val();
	var telNbr =$("#telName").val();		
		  
	$.ajax({
			url:"/role/qryRolePageList",
			data:{curPage:curPage,checkRoleName:checkRoleName,userName:userName,deptName:deptName,telNbr:telNbr},
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
	  
	  function editRole(id){	
		  $.ajax({
			 url:"/role/initAdCheckConfigRole",
			 data:{adCheckConfigPk:id},
			 type:'GET',
			 dataType:'html',
			 async:true,
			 success:function(result){							
				$("#editRole").empty();
				$("#editRole").append(result);
			 }
		  });
		  showBackGround();
		//  $(".tcLsyer_all").show();
		  showDiv(".tcLsyer_all");
	  }
	  function deleteRole(id){
		  var operationType = "2";
		  $.ajax({
				 url:"/role/updateAdCheckConfigRole",
				 data:{adCheckConfigPk:id,operationType:operationType},
				 type:'POST',
				 async:true,
				 success:function(result){
					 if(result=="1"){
					    searchRolePageInfo(1);
					 }else{
						 alert("删除失败");
					 }
				 },error:function(){					 
				 }
			  });
	  }
</script>			
</body>
</html>