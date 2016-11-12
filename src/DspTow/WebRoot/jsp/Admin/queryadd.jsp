<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script>
    $(function () {
         $('#reportrange span').html(moment().subtract('days', 7).format('YYYY-MM-DD HH:mm:ss') + ' - ' + moment().format('YYYY-MM-DD HH:mm:ss'));  
          
                    $('#reportrange').daterangepicker(  
                            {  
                                // startDate: moment().startOf('day'),  
                                //endDate: moment(),  
                                minDate: new Date(),    //最小时间  
                                dateLimit : {  
                                    days : 365  
                                }, //起止时间的最大间隔  
                                showDropdowns : true,  
                                showWeekNumbers : false, //是否显示第几周  
                                timePicker : true, //是否显示小时和分钟  
                                timePickerIncrement : 10, //时间的增量，单位为分钟  
                                timePicker12Hour : false, //是否使用12小时制来显示时间  
                                ranges : {  
                                    //'最近1小时': [moment().subtract('hours',1), moment()],  
                                    '今日': [moment().startOf('day'), moment()],  
                                    '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],  
                                    '最近7日': [moment().subtract('days', 6), moment()],  
                                    '最近30日': [moment().subtract('days', 29), moment()]  
                                },  
                                opens : 'right', //日期选择框的弹出位置  
                                buttonClasses : [ 'btn btn-default' ],  
                                applyClass : 'btn-small btn-primary blue',  
                                cancelClass : 'btn-small',  
                                format : 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式  
                                separator : ' to ',  
                                locale : {  
                                    applyLabel : '确定',  
                                    cancelLabel : '取消',  
                                    fromLabel : '起始时间',  
                                    toLabel : '结束时间',  
                                    customRangeLabel : '自定义',  
                                    daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],  
                                    monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',  
                                            '七月', '八月', '九月', '十月', '十一月', '十二月' ],  
                                    firstDay : 1  
                                }  
                            }, function(start, end, label) {//格式化日期显示框  
                                  
                                $('#reportrange span').html(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));  
                           });  
        $.post("<%=path%>/dspAdAction/queryAdPrio.do",function(result){
        	for ( var int = 0; int < result.length; int++) {
				$("#prio").append("<option value="+result[int].prio+">"+result[int].prio+"</option>");
			}
        });
        $("#touType").val("${adlist[0].push_method}");
        if($("#touType").val()=="0"){
        	$("#ceLui").css("display","none");
        }
        $("#touType").change(function(){
        	if($(this).val()=="0"){
        		$("#ceLui").hide();
        	}else{
        		$("#ceLui").show();
        	}
        });
        
        
        $("#submitBtn").click(function(){
        	if($("#xuQiuId").val()==""){
        		$("#xuQiuId").val(0);
        	}
        	$("#time").val($("#searchDateRange").text());
        	$.post("<%=path%>/dspAdAction/doInsterAd.do",$("#adform").serializeObject(),
        	function(result){
        		if(result=="0"){
							bootbox.alert({size: "small", message: "操作失败"});
						}else{
							$(".content-wrapper").html(result);
						}
        	});
        	
        	
        });
    });
</script>
        <!-- Main content -->
        <section class="content">
            <ol class="breadcrumb">
                <li><a href="DspUserAction!doSelectLogin.action?username=${user.username}&password=${user.password}"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li><a>需求管理</a></li>
                <li class="active">添加</li>
            </ol>

            <form id="adform">
                <!-- Default box -->
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">基本信息</h3>
                    </div>
                    <div class="panel-body form-horizontal">
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">需求名称：</label>
                            <div class="col-sm-8">
                            	<input type="hidden" value="${adlist[0].id}" id="xuQiuId" name="id">
                                <input type="text" class="form-control" name="name" value="${adlist[0].name}" require>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="prio" class="col-sm-2 control-label">优先等级：</label>
                            <div class="col-sm-8">
                                <select class="form-control" id="prio" name="prio" autovalue="${adlist[0].prio}" require>
                                    <c:if test="${adlist[0].prio != ''}">
	                                    <option value="${adlist[0].prio}">${adlist[0].prio}</option>
	                                </c:if>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="push_method" class="col-sm-2 control-label">投放类型：</label>
                            <div class="col-sm-8">
                                <select class="form-control" id="touType" name="push_method">
                                    <option value="0">普投</option>
                                    <option value="1">定向</option>
                                </select>
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
                        <div class="form-group">
                            <label for="set_num" class="col-sm-2 control-label">期望PV：</label>
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <input type="text" class="form-control" name="set_num" value="${adlist[0].set_num}" require>
                                    <span class="input-group-addon">千个</span>
                                </div>
                                <span class="help-block">0表示不限制</span>
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
                        <div class="form-group">  
			                    <label class="col-sm-2 control-label">  
			                       	 投放时间：
			                    </label>  
			                    <div class=" col-sm-8">
			                    	<div class="input-group" style="margin-top:7px;"> 
			                		<input type="hidden" name="time" id="time"> 
				                    <div id="reportrange" class="pull-left dateRange" style="width:350px">  
				                        <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>  
				                        <span id="searchDateRange">${adlist[0].time}</span>  
				                        <b class="caret"></b>  
				                    </div>  
               					</div>  
			                    
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

                        <div class="form-group">
                            <label for="url" class="col-sm-2 control-label">物料地址：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="url" value="${adlist[0].url}" require>
                                <span class="help-block">以http开头，目前只支持一个</span>
                            </div>

                        </div>


                    </div><!-- /.box-body -->
                </div>

                <!-- Default box -->
                <div class="box" id="ceLui">
                    <div class="box-header with-border">
                        <h3 class="box-title">策略</h3>
                    </div>
                    <div class="panel-body form-horizontal">
                        <div class="form-group col-sm-20">
                            <label for="domain" class="col-sm-2 control-label">URL：</label>

                            <div class="col-sm-8">
                                <textarea id="domain" class="form-control" rows="5" placeholder="http:// ..."
                                          name="domain" >${adlist[0].domain}</textarea>
                                <span class="help-block">定向模式生效,以http开头，域名请使用","隔开</span>
                            </div>
                        </div>

                    </div><!-- /.box-body -->
                </div>

                <div class="row">
                    <div class="col-xs-8">
                    </div><!-- /.col -->
                    <div class="col-xs-1">
                        <button type="button" id="submitBtn" class="btn btn-primary btn-block btn-flat">提交</button>
                    </div><!-- /.col -->
                </div>
            </form>

        </section><!-- /.content -->

<!-- Page script -->

