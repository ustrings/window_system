<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <script type="text/javascript">
 	$(function(){
 		
 		$("#domainstatTable").bootstrapTable({
	        url:"<%=path%>/dspDomainAction/doSelectDomain.do",
	        dataType: "json",
	        method: 'post',
	        pageSize: 15,
	        pageNumber: 1,
	        search:true,
	        striped: true,
	        pagination: true,
	        clickToSelect: true,
	        singleSelect: true,
	        showRefresh: true,
	        showColumns: true,
	        cache: false,
	        showToggle:true,
	        queryParams:function(params) {
				return getQueryParam("searchForm",params);
			},
	        queryParamsType: "limit",
	        
	        toolbar: "#toolBar",
	        
	    });
	     $('#searchBtn').on('click', function (params) {
        $("#domainstatTable").bootstrapTable('refresh');
    });
 	});
 </script>

    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        域名统计
        <small>投放统计</small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="DspUserAction!doSelectLogin.action?username=${user.username}&password=${user.password}"><i class="fa fa-dashboard"></i> 首页</a></li>
        <li><a>统计</a></li>
        <li class="active">域名统计</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12"><div class="box">
                <div class="box-body">
                    <div id="toolBar">
                        
                       <form id="searchForm" class="form-inline">
                        <div class="form-group">
                            <span>域名</span>
                            <input type="text" class="form-control" id="NameEdit"
                                   name="name" placeholder="请输入域名">
                        </div>
                        <form class="form-group">
                            <button id="searchBtn" type="button" style="background-color: #367fa9;border:0px;" class="btn btn-danger">查询</button>
                        </form>
                    </form>

                    </div>
                    <table id="domainstatTable" class="table table-bordered table-hover table-striped" style="white-space: nowrap;">
                        <thead>
                        <tr>
                            <th data-field="id" data-sortable="true">序号</th>
                            <th data-field="ad_id" data-sortable="true">需求号</th>
                            <th data-field="domain" data-sortable="true">域名</th>
                            <th data-field="push_num" data-sortable="true">实际投放</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div><!-- /.box -->
            </div><!-- /.col -->
          </div><!-- /.row -->
        </section><!-- /.content -->
