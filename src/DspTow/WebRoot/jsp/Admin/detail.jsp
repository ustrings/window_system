<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!-- Main content -->
        <section class="content">
            <ol class="breadcrumb">
                <li><a href="DspUserAction!doSelectLogin.action?username=${user.username}&password=${user.password}"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><a>需求管理</a></li>
                <li class="active">详情</li>
            </ol>

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">基本信息</h3>
                </div>
                <div class="panel-body form-horizontal">


                    <div class="form-group col-sm-6">
                        <label class="col-sm-4 control-label">需求名称：</label>
                        <div class="col-sm-8">
                            <p class="form-control-static">${adDetaillist[0].name}</p>
                        </div>
                    </div>

                    <div class="form-group col-sm-6">
                        <label class="col-sm-4 control-label">优先等级：</label>
                        <div class="col-sm-8">
                            <p class="form-control-static">${adDetaillist[0].prio}</p>
                        </div>
                    </div>

                    <div class="form-group col-sm-6">
                        <label class="col-sm-4 control-label">投放状态：</label>
                        <div class="col-sm-8">
                            <p class="form-control-static">
                            	<c:if test="${adDetaillist[0].push_status == 1}">
                                                                                                投放中
                                </c:if>
                                <c:if test="${adDetaillist[0].push_status == 0}">
                                	已挂起
                                </c:if>
                            </p>
                        </div>
                    </div>

                    <div class="form-group col-sm-6">
                        <label class="col-sm-4 control-label">投放类型：</label>
                        <div class="col-sm-8">
                            <p class="form-control-static">
                            	<c:if test="${adDetaillist[0].push_method == 1}">
                                                                                                定向
                                </c:if>
                                <c:if test="${adDetaillist[0].push_method == 0}">
                                	普推
                                </c:if>
                            </p>
                        </div>
                    </div>

                </div><!-- /.box-body -->
            </div>

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">结算信息</h3>
                </div>
                <div class="panel-body form-horizontal">


                    <div class="form-group col-sm-6">
                        <label class="col-sm-4 control-label">期望PV：</label>
                        <div class="col-sm-8">
                            <p class="form-control-static">${adDetaillist[0].set_num}千个</p>
                        </div>
                    </div>

                </div><!-- /.box-body -->
            </div>

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">投放选项</h3>
                </div>
                <div class="panel-body form-horizontal">


                    <div class="form-group col-sm-6">
                        <label class="col-sm-4 control-label">开始时间：</label>
                        <div class="col-sm-8">
                            <p class="form-control-static">
                            ${adDetaillist[0].begin_time}
                            </p>
                        </div>
                    </div>

                    <div class="form-group col-sm-6">
                        <label class="col-sm-4 control-label">结束时间：</label>
                        <div class="col-sm-8">
                            <p class="form-control-static">
                            ${adDetaillist[0].end_time}
                            </p>
                        </div>
                    </div>

                </div><!-- /.box-body -->
            </div>

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">物料</h3>
                </div>
                <div class="panel-body form-horizontal">

                    <div class="form-group col-sm-12">
                        <label class="col-sm-2 control-label">模板：</label>
                        <div class="col-sm-8">
                            <p class="form-control-static">右下角弹窗_300x250</p>
                        </div>
                    </div>

                    <div class="form-group col-sm-12">
                        <label class="col-sm-2 control-label">物料地址：</label>
                        <div class="col-sm-8">
                            <pre>${adDetaillist[0].url}</pre>
                        </div>
                    </div>
                </div><!-- /.box-body -->
            </div>

            <!-- Default box -->
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">策略</h3>
                </div>
                <div class="panel-body form-horizontal">
                    <div class="form-group col-sm-12">
                        <label class="col-sm-2 control-label">URL：</label>
                        <div class="col-sm-8">
                            <pre>${domain}</pre>
                        </div>
                    </div>

                </div><!-- /.box-body -->
            </div>
</section><!-- /.content -->
