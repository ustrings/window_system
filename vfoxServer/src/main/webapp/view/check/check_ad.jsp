<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>
<script src="/resources/js/common.js" type="text/javascript"></script>
		<div class="tcLsyer_title">
			<span>审核</span>
			<a href="javascript:;" class="close"></a>
		</div>
		<div class="tcLsyer_content">
			<div class="content_DIV">
				<span class="is_check">广告名称：</span>
				<input type="text" name="text" value="${adInstanceDto.adName}" class="tc_inp" readonly="readonly"/>
			</div>
			<div class="content_DIV">
				<span class="is_check">投放链接：</span>
				<input type="text" name="text" value="${adInstanceDto.adUrl}" class="tc_inp" readonly="readonly"/>
			</div>
			<div class="content_DIV">
				<span class="is_check">是否通过审核：</span>
				<input type="radio" name="radio" class="radio_btn" value="2" id="radio_yes"/>
				<span class="value_span">是</span>
				<input type="radio" name="radio" class="radio_btn" value="3" id="radio_no"/>
				<span  class="value_span">否</span>
			</div>
			<div class="content_DIV_sugess" id="sugess_one" style="display:none;">
				<span class="is_check">审核意见：</span>
				<textarea cols="30" rows="5" id="checkDesc"></textarea>
			</div>
			<div class="content_DIV">
				<input type="button" value="确定" class="alt_btn" name="checkAd" id="${adCheckProcessPk}" style="margin-left:30%;"/>
				<input type="reset" value="重置" />
			</div>
		</div>