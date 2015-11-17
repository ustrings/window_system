<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../inc/directive.inc"%>

<div class="box-content">

	<form class="form-horizontal" id="crowdNewFrom" method="post">
		<fieldset>
			<legend>基本信息</legend>
			<div class="control-group">
				<label class="control-label" for="focusedInput">受众人群名称</label>
				<div class="controls">
				    <a name="crowdNamePos" id="crowdNamePos"></a>
					<input class="input-xlarge focused" id="crowdName"
						name="crowdName" type="text" value="" onfocus=""/>
				</div>

			</div>
			<div class="control-group">
				<label class="control-label">描述</label>
				<div class="controls">
					<textarea class="autogrow" id="crowdDesc" name="crowdDesc"></textarea>
				</div>
			</div>
		</fieldset>

		<fieldset>
			<legend>
				搜索关键词设置 <input type="checkbox" id="searchKwSwitch"
					name="searchKwSwitch" value="S" checked="checked"/>
			</legend>

			<table>

				<tr>
					<td><a href="#" class="btn btn-primary "
						id="searchKwOceanSel">从关键词海洋中选取</a><a class="btn btn-danger"
						href="#" id="searchKwOceanDel" name="searchKwOceanDel">
							<i class="icon-trash icon-white"></i>移除
					</a></td>

					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

					<td>自定义关键词:</td>

				</tr>


				<tr>
					<td><select multiple="multiple"
						id="searchKwFromOceanSel" name="searchKwFromOceanSel"
						size="15">
			
					</select> <input type="hidden" id="searchKwFromOcean"
						name="searchKwFromOcean" /></td>

					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

					<td><textarea
							style="resize: none; width: 300px; height: 240px;"
							id="searchKwFormCust" name="searchKwFormCust"></textarea></td>

				</tr>
				<tr>


					<td colspan="3">匹配<input type="text" name="searchKwNum"
						style="width: 15px" id="searchKwNum" /> 个关键词,介于 现在 和<input
						type="text" name="searchKwTimeNum" style="width: 15px"
						id="searchKwTimeNum" /><select id="searchKwTimeType"
						name="searchKwTimeType" style="width: 80px">
							<option value="H">小时</option>
							<option value="D">天</option>
							<option value="M">分钟</option>

					</select> 之前
					</td>
				</tr>

			</table>


		</fieldset>


		<fieldset>
			<legend>
				URL设置 <input type="checkbox" id="urlSwitch" name="urlSwitch"
					checked="checked" value="U">
			</legend>



			<table >

				<tr>
					<td><a href="#" class="btn btn-primary"
						id="urlOceanSel">从URL海洋中选取</a><a class="btn btn-danger"
						href="#" id="urlOceanDel" name="urlOceanDel"> <i
							class="icon-trash icon-white"></i>移除
					</a></td>

					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

					<td>自定义url:</td>

				</tr>


				<tr>
					<td><select multiple="multiple" id="urlFromOceanSel"
						name="urlFromOceanSel" size="15" style="width: 400px;">
					</select> <input type="hidden" id="urlFromOcean" name="urlFromOcean" />
					</td>

					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

					<td><textarea
							style="resize: none; width: 400px; height: 240px;"
							id="urlFromCust" name="urlFromCust"></textarea></td>

				</tr>
				<tr>


					<td colspan="3">匹配<input type="text" name="urlNum"
						style="width: 15px" id="urlNum" /> 个url,介 现在 和<input
						type="text" name="urlTimeNum" style="width: 15px"
						id="urlTimeNum" /><select id="urlTimeType"
						name="urlTimeType" style="width: 80px">
							<option value="H">小时</option>
							<option value="D">天</option>
							<option value="M">分钟</option>

					</select> 之前
					</td>
				</tr>

			</table>

		</fieldset>




		<div class="form-actions">
			<button type="button" class="btn btn-primary" id="crowdAddSave">保存</button>
			<button class="btn">取消</button>
		</div>

	</form>

</div>



<div class="modal" id="keywordOceanModal" style="display: none;">

	<div class="modal-header">
		<button type="button" class="close" id="keywordOceanModalClose">×</button>
		<h3>关键词海洋</h3>
	</div>


	<div class="modal-body">
		<div class="row-fluid">
			<div class="box span12">

				<div class="box-content">
					<input class="input-xlarge" id="searchKw" name="searchKw"
						type="text" value="" />

					<table
						class="table table-striped table-bordered bootstrap-datatable datatable">
						<thead>
							<tr>
								<th>关键词</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody id="keywordBody">

						</tbody>
					</table>
				</div>
			</div>


		</div>
	</div>

	<div class="modal-footer">
		<a href="#" class="btn" data-dismiss="modal">关闭</a>
	</div>

</div>



<div class="modal" id="urlOceanModal" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" >×</button>
		<h3>url海洋</h3>
	</div>


	<div class="modal-body">
		<div class="row-fluid">
			<div class="box span12">

				<div class="box-content">
					<input class="input-xlarge" id="searchUrlContent" name="searchUrlContent"
						type="text" value="" />

					<table
						class="table table-striped table-bordered bootstrap-datatable datatable">
						<thead>
							<tr>
							    <th>url</th>
								<th>内容</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody id="urlBody">


						</tbody>
					</table>
				</div>
			</div>


		</div>
	</div>

	<div class="modal-footer">
		<a href="#" class="btn" >关闭</a>
	</div>

</div>

	<%@include file="../inc/footer.jsp"%>
<script>
	$(document)
			.on(
					"click",
					"#crowdAddSave",
					function() {

						var crowdName = $("#crowdName").val();

						if (crowdName == '') {
							var $crowdNamerequired = $('<font color="red">名称不能为空</font>'); //创建元素  
							$("#crowdName").after($crowdNamerequired);
							
						    var scroll_offset = $("#crowdNamePos").offset();
						    $("#crowdNewModal .modal-body").animate({
							   scrollTop:scroll_offset.top  //让body的scrollTop等于pos的top，就实现了滚动
							},0);
							  
							  
						} else {

							var oceanKeywords = "";
							$("#searchKwFromOceanSel option").each(function() {

								var kw = $(this).val();
								oceanKeywords += kw + ",";

							});

							if (oceanKeywords != "") {
								oceanKeywords = oceanKeywords.substring(0,
										oceanKeywords.length - 1);
							}

							$("#searchKwFromOcean")
									.attr("value", oceanKeywords);

							var oceanUrls = "";
							$("#urlFromOceanSel option").each(function() {

								var url = $(this).val();
								oceanUrls += url + ",";

							});

							if (oceanUrls != "") {
								oceanUrls = oceanUrls.substring(0,
										oceanUrls.length - 1);
							}

							$("#urlFromOcean").attr("value", oceanUrls);

							 $.ajax({
								url : '/crowd/crowdaddfapsaveaction',
								type : 'post',
								data : $('#crowdNewFrom').serialize(),
								success : function(data) {
									
									
									var crowdSeledIds = new Array();
								    $("#crowdSeled input[name=crowdSeledId]").each(function(){
								    	var crowdSeledId = $(this).val();
								    	crowdSeledIds.push(crowdSeledId);
								    });
								    
									
								    var crowdId = data.crowdId;
								    var crowdName = data.crowdName;
								    
									var crowdHtml = "<span class='label label-success' style='border-style:double;' >"+crowdName+"<a href='javascript:void(0);' name='crowdClose'>"
									        +"<span class='icon icon-orange icon-close'  ></span></a>"
											+ "<input type='hidden' name='crowdSeledId' value='"+crowdId+"'/></span>";
							
									//不在被选择列表中的 crowd 才被加入到选中列表中
									if($.inArray(crowdId, crowdSeledIds) < 0){
										//添加这个元素，并为这个元素里面的a[name=crowdClose]的绑定点击事件，点击，当前span remove
										$("#crowdSeled").append(crowdHtml);	
										
										$("#crowdSeled").find(" input[value="+crowdId+"]").parent().find("a[name=crowdClose]").click(function(){
											$(this).parent().remove();
										});
									}
									
									$('body').removeClass('modal-open');
									$('body .modal-backdrop').remove();
									
									$("#crowdNewModal").hide();
								
									
									//$("#crowdNewModal").hide();
								}
							}); 
					
						}

					}).on("click", "#searchKwSwitch", function() {

				

			}).on("click", "#urlSwitch", function() {

				

			}).on("click", "#searchKwOceanDel", function() {

				if ($("#searchKwFromOceanSel option:selected").length > 0) {

					$("#searchKwFromOceanSel option:selected").each(function() {
						$(this).remove();
					});

				}

			}).on("click", "#urlOceanDel", function() {

				if ($("#urlFromOceanSel option:selected").length > 0) {

					$("#urlFromOceanSel option:selected").each(function() {
						$(this).remove();
					});
				}
			}).on("click", "#searchKwOceanSel", function(e) {

				
				e.preventDefault();
				
				//在新建受众人群的弹层上，再弹出选择关键词的层，且这个层后面的蒙版z-index 要高于受众人群的弹层(受众人群的层的z-index:1050)上。
		 		var backdrop = $('<div class="modal-backdrop" />');
				backdrop.appendTo($("#crowdNewModal"));
				backdrop.addClass("in");
				backdrop.attr("style","z-index : 1060");
				 
				//关键词的层要 比新建受众人群的层和上面的蒙版都要高
				$("#keywordOceanModal").attr("style","z-index : 1070");
				$("#keywordOceanModal").show();
				//$("#keywordOceanModal").modal('show');
				
				$("#searchKw").focus();
				
			}).on("keydown", "#searchKw", function(e) {

				if (e.which == 13) {

					$.ajax({
						url : '/crowd/keywordoceanaction',
						type : 'get',
						data : "searchKw="+$("#searchKw").val(),
						success : function(data) {

							$("#keywordBody").html(data);
						}
					});

				}
			}).on("click","#urlOceanSel",function(e){
				
				e.preventDefault();
				
				//在新建受众人群的弹层上，再弹出选择关键词的层，且这个层后面的蒙版z-index 要高于受众人群的弹层(受众人群的层的z-index:1050)上。
		 		var backdrop = $('<div class="modal-backdrop" />');
				backdrop.appendTo($("#crowdNewModal"));
				backdrop.addClass("in");
				backdrop.attr("style","z-index : 1060");
				 
				//关键词的层要 比新建受众人群的层和上面的蒙版都要高
				
				
				
				$("#urlOceanModal").show();
				
				$("#urlOceanModal").css("z-index","1070");
 				$("#urlOceanModal").css("width","80%");
				$("#urlOceanModal").css("left","30%");
				
				
				
			}).on("keydown","#searchUrlContent",function(e){
				if (e.which == 13) {

					$.ajax({
						url : '/crowd/urloceanaction',
						type : 'get',
						data : "searchUrlContent="+$("#searchUrlContent").val(),
						success : function(data) {

							$("#urlBody").html(data);
						}
					});

				}
			}).on("click","#keywordOceanModal .modal-header .close",function(){
				
				
				//关键词的层关闭的时候，把新建受众人群层上的蒙版也关闭, 关闭这个层，把新建受众的层也会关闭，暂时没找到原因为什么，所以再手动把新建受众人群的层再打开
				$('#crowdNewModal .modal-backdrop').remove();
				
			    $('body').addClass('modal-open');
				var backdrop = $('<div class="modal-backdrop" />');
				backdrop.appendTo(document.body);
				backdrop.addClass("in");
				
				$('#crowdNewModal').show();
				
				$("#keywordOceanModal").hide();
				
			}).on("click","#keywordOceanModal .modal-footer .btn",function(){
				//关键词的层关闭的时候，把新建受众人群层上的蒙版也关闭, 关闭这个层，把新建受众的层也会关闭，暂时没找到原因为什么，所以再手动把新建受众人群的层再打开
				$('#crowdNewModal .modal-backdrop').remove();
				
			    $('body').addClass('modal-open');
				var backdrop = $('<div class="modal-backdrop" />');
				backdrop.appendTo(document.body);
				backdrop.addClass("in");
				
				$('#crowdNewModal').show();
				
				$("#keywordOceanModal").hide();
				
			}).on("click","#urlOceanModal .modal-header .close",function(){
				
				
				//关键词的层关闭的时候，把新建受众人群层上的蒙版也关闭, 关闭这个层，把新建受众的层也会关闭，暂时没找到原因为什么，所以再手动把新建受众人群的层再打开
				$('#crowdNewModal .modal-backdrop').remove();
				
			    $('body').addClass('modal-open');
				var backdrop = $('<div class="modal-backdrop" />');
				backdrop.appendTo(document.body);
				backdrop.addClass("in");
				
				$('#crowdNewModal').show();
				
				$("#urlOceanModal").hide();
				
			}).on("click","#urlOceanModal .modal-footer .btn",function(){
				//关键词的层关闭的时候，把新建受众人群层上的蒙版也关闭, 关闭这个层，把新建受众的层也会关闭，暂时没找到原因为什么，所以再手动把新建受众人群的层再打开
				$('#crowdNewModal .modal-backdrop').remove();
				
			    $('body').addClass('modal-open');
				var backdrop = $('<div class="modal-backdrop" />');
				backdrop.appendTo(document.body);
				backdrop.addClass("in");
				
				$('#crowdNewModal').show();
				
				$("#urlOceanModal").hide();
			});
</script>
