<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/view/inc/directive.inc"%>
<script src="/resources/js/common.js" type="text/javascript"></script>            
				<div class="tcLsyer_title">
					<span class="is_check">角色修改</span>
					<a href="#" class="close_all"></a>
				</div>
				<div class="tcLsyer_content">
					<div class="content_DIV">
						<span class="span">角色名称：</span>
						<input type="text" name="text" class="tc_inp" id="editCheckRoleName" value="${adCheckConfigDto.checkRoleName}" />
						<span class="tips">*角色名称不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="span">用户姓名：</span>
						<input type="text" name="text" class="tc_inp" id="editUserName" value="${adCheckConfigDto.userName}" />
						<span class="tips">*用户姓名不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="span">所属部门：</span>
						<input type="text" name="deptName" class="tc_inp" id="editDeptName" value="${adCheckConfigDto.deptName}"/>
						<span class="tips">*所属部门不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="span">绑定账号：</span>
						<input type="text" name="text" class="tc_inp" id="editUserAcctRel" value="${adCheckConfigDto.userAcctRel}"/>
						<span class="tips">*账号不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="span">手机号码：</span>
						<input type="text" name="text" class="tc_inp" id="editTelNbr" value="${adCheckConfigDto.telNbr}"/>
						<span class="tips">*手机号码不能为空</span>
					</div>
					<div class="content_DIV">
						<span class="span">Email：</span>
						<input type="text" name="text" class="tc_inp" id="editEmail" value="${adCheckConfigDto.email}"/>
						<span class="tips">*邮箱不能为空</span>
					</div>
					<div class="content_DIV">
						<input type="button" value="修改" class="alt_btn" style="margin-left:20%;" id="${adCheckConfigDto.id}" name="edit_role_config_btn"/>
						<input type="reset" name="button" value="重置" />
					</div>
				</div>
			