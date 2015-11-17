<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
										<div style="width: 100%">
												<table class="table table-striped table-bordered bootstrap-datatable datatable" style="width: 100%;">
												<tr>
													<td>序号</td>
													<td>曝光总数</td>
													<td>剩余曝光数</td>
													<c:forEach var="m" items="${dataArr}">
														<td>${m}</td>
													</c:forEach>
												</tr>
												<tr>
													<td>计划曝光数</td>
													<td>${total}</td>
													<td>0</td>
													<c:forEach var="m" items="${dataArr}">
														<td>${avg_exposure}</td>
													</c:forEach>
												</tr>
												<tr>
													<td>实际曝光数</td>
													<td>${total_exposure}</td>
													<td>${remain_exposure}</td>
													<c:forEach var="press" items="${datePressList}">
														<td>${press.press}</td>
													</c:forEach>
												</tr>
												 </table>  
											</div>
