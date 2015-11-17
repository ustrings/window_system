<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>

							<table
								class="table table-striped table-bordered bootstrap-datatable datatable">
								<thead>
									<tr>
										<th style="width: 8%; "><input type="checkbox" onclick="checkAll(this,'subCheckBox')"/> 全选</th>
										<th>人群名称</th>
										<th>状态</th>
										<th>人数估计</th>
										<th>创建时间</th>
										<th>过期时间</th>
										<th>最新更新时间</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${not empty crowdList}">
										<c:forEach var="crowd" items="${crowdList}">
											<tr>
												<td style="text-align: center;"><input name="subCheckBox"  onclick="afterCheckBoxPress()" type="checkbox" value="${crowd.crowdId }" /></td>
												<td>${crowd.crowdName}</td>
												<td class="center">
													<c:if test="${crowd.crowdSts eq 'A'}">运行</c:if>
													<c:if test="${crowd.crowdSts eq 'S'}">暂停</c:if>
													<c:if test="${crowd.crowdSts eq 'N'}">到期</c:if>
													<c:if test="${crowd.crowdSts eq 'D'}">删除</c:if>
												</td>
												<td class="center">${crowd.crowdNum }</td>
												<td class="center">${crowd.createDate }</td>
												<td class="center">${crowd.expressDate }</td>
												<td class="center">${crowd.updateTime }</td>
												<td class="center"> 
												<a href="/crowd/toEditCrowd/${crowd.crowdId}" class="btn btn-primary" id="editBtn" >修改</a>
												</td>
											</tr>
										</c:forEach>
									</c:if>

								</tbody>
							</table>
						
<script>
$(document).ready(function(){
	$('.datatable').dataTable({
		"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
		"sPaginationType": "bootstrap",
		 "oLanguage": {
             "sProcessing": "正在加载中......",
             "sLengthMenu": "每页显示 _MENU_ 条记录",
             "sZeroRecords": "对不起，查询不到相关数据！",
             "sEmptyTable": "表中无数据存在！",
             "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
             "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
             "sSearch": "搜索",
             "oPaginate": {
                 "sFirst": "首页",
                 "sPrevious": "上一页",
                 "sNext": "下一页",
                 "sLast": "末页"
             }
         }
	} );
});

</script>