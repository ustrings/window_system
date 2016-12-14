<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
		<script>
			function queryAd(id){
			  $.post("<%=path%>/dspAdAction/doDetailAd.do",{"id":id},function(result){
			  	 if(result=="0"){
							bootbox.alert({size: "small", message: "修改失败"});
						}else{
							$(".content-wrapper").html(result);
						}
			  });
			}
			function toAdAddPage(id){
				$.post("<%=path%>/dspAdAction/toAdAddPage.do",{"id":id},function(result){
			  	if(result=="0"){
							bootbox.alert({size: "small", message: "查询失败"});
						}else{
							$(".content-wrapper").html(result);
						}
			  });
			}
			function toDelete(id){		
				if (confirm("是否确定删除？")){
					$.post("<%=path%>/dspAdAction/doDeleteAd.do",
					{"id":id},function(result){
						if(result=="0"){
							bootbox.alert({size: "small", message: "删除失败"});
						}else{
							$(".content-wrapper").html(result);
						}
					});
				}	
			}
			function doUpdateAd(id,status){
				$.post("<%=path%>/dspAdAction/doUpdateAd.do",{"id":id,"push_status":status},function(result){

					if(result=="0"){
						bootbox.alert({size: "small", message: "修改失败"});
					}else{
						$(".content-wrapper").html(result);
					}
				});
			}
		</script>
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
               	 需求管理
                <small>需求配置</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="DspUserAction!doSelectLogin.action?username=${user.username}&password=${user.password}"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><a>需求</a></li>
                <li class="active">需求配置</li>
            </ol>
        </section>

        <!-- Main content -->
       <section class="content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div style="margin-bottom: 5px;">
                                <button  class="btn btn-primary pull-right btn-circle opt-add" onclick="toAdAddPage()" type="button">
                                    <span class="glyphicon glyphicon-plus"></span>
                                </button>

                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="example1" class="table table-bordered table-striped">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>状态</th>
                                    <th>名称</th>
                                    <th>优先级</th>
                                    <th>投放状态</th>
                                    <th>投放时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <volist name="query" id="vo">
                                   <c:forEach var="adlist" items="${adlist}">
    										<tr>
    											<td>${adlist.id}</td>
                                        		<td>
                                            		<a title="推送状态修改" href="javascript:void(0)" onclick="doUpdateAd(${adlist.id},${adlist.push_status})">
                                            		
                                            		<c:if test="${adlist.push_status == 1}">
                                            			<img src="<%=path%>/statics/dist/img/play.png"/>
                                                	</c:if>
                                                	<c:if test="${adlist.push_status == 0}">
                                            			<img src="<%=path%>/statics/dist/img/pause.png"/>
                                                	</c:if>
                                                	
                                            	</a>
                                       	 	</td>
                                        	<td>${adlist.name}</td>
                                        	<td>${adlist.prio}</td>
                                        	
                                        	<td>
                                        		<c:if test="${adlist.push_status == 1}">
                                            		投放中
                                                </c:if>
                                                <c:if test="${adlist.push_status == 0}">
                                            		已挂起
                                                </c:if>
                                        	
                                        	</td>
                                        	
                                        	<td>
                                        	${adlist.begin_time}至${adlist.end_time}
                                        	</td>
                                        	<td>
                                            	<a title="查看详情" href="javascript:void(0)" onclick="queryAd(${adlist.id})"  ><span
                                                    	class="glyphicon glyphicon-list-alt"></span></a>&nbsp;
                                            	<a title="编辑" class="opt-edit" href="javascript:void(0)" onclick="toAdAddPage(${adlist.id})"
                                               		data-confirm="该需求已通过审核，编辑将需要重新提交审核，确认继续吗？"><span
                                                    	class="glyphicon glyphicon-edit"></span></a>&nbsp;
                                            	<a title="删除" id="deleteId" href="javascript:void(0)" onclick="toDelete(${adlist.id})" ><span
                                                    	class="glyphicon glyphicon-trash"></span></a>&nbsp;
                                        	</td>
    											
    										
    										
    										
    										</tr>
    										
    									</c:forEach>
                                </volist>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>序号</th>
                                    <th>状态</th>
                                    <th>名称</th>
                                    <th>优先级</th>
                                    <th>投放状态</th>
                                    <th>投放时间</th>
                                    <th>操作</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div><!-- /.box-body -->
                    </div><!-- /.box -->
                </div><!-- /.col -->
            </div><!-- /.row -->
        </section><!-- /.content -->
