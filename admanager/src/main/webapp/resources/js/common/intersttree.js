 //初始化兴趣标签树
 function initIntrestLabelTree()
 {
	
	  var setting = {
			  async: {
					enable: true,
					url: "/crowd/findTreeNextChild",
					autoParam: ["id", "name","halfCheck","crowdId"]
				},
			    check: {
			        enable: true
			    },
			    data: {
			        simpleData: {
			            enable: true
			        }
			    },
			    callback: {
			    	onExpand:onClick,
//			    	onClick: onClick,
			    	onCheck: onCheck
				}
	  };
	  var zNodes= {};
	  var crowdId =$("#crowdId").val();
	  if(!crowdId){
		  crowdId="";
	  }
	
	  $.ajax({
			url : '/crowd/initInterestTree',
			type : 'post',
			async : true,
			data : {crowdId:crowdId},
			success : function(data) {                   
				zNodes = data;
				var zTree = $.fn.zTree.init($("#interstLabeTree"), setting, zNodes);
				var nodes = zTree.getNodesByFilter(filter);
				//遍历每一个节点然后动态更新nocheck属性值
				var jdNode;
				for (var i = 0; i < nodes.length; i++) {
					var node = nodes[i];
					
				 	node.nocheck = true; //不显示显示checkbox
				 	node.isParent=true;
				 	zTree.updateNode(node);
				 	
				 	zTree.refresh();
				 	if(node.id=="jd"){
				 		jdNode = node;
					}
				}
			}
		});
 }
 
 //filter node
 function filter(node) {
	 return node;
 }

 //点击加载数据
 function onClick(event, treeId, treeNode, clickFlag) {

	var zTree = $.fn.zTree.getZTreeObj("interstLabeTree");
	
	if (treeNode.children.length > 0) {
		var childNodes = treeNode.children;
		for (var i = 0; i < childNodes.length; ++i) {

			if (treeNode.checked && !treeNode.halfCheck) {
				childNodes[i].checked = true;
				childNodes[i].halfCheck = false;
			}
			childNodes[i].isParent = true;
		}
	} else {
		treeNode.isParent = false;
	}
	
	zTree.updateNode(treeNode);
	zTree.refresh();
	return;
		
//	if (treeNode.id == "all") {
//		var nodes = zTree.getNodesByFilter(filter);
//		// 遍历每一个节点然后动态更新nocheck属性值
//		for (var i = 0; i < nodes.length; i++) {
//			var node = nodes[i];
//			node.nocheck = true; // 不显示显示checkbox
//			node.isParent = true;
//			zTree.updateNode(node);
//		}
//		return;
//	}
//
//	var childNodes = {};
//	$.ajax({
//		url : '/crowd/findTreeNextChild',
//		type : 'post',
//		async : true,
//		data : {
//			'curLabel' : treeNode.id
//		},
//		success : function(data) {
//			alert("ajax");
//			childNodes = data;
//			if (childNodes.length > 0) {
//				for (var i = 0; i < childNodes.length; ++i) {
//					if (treeNode.checked) {
//						childNodes[i].checked = true;
//					}
//					childNodes[i].isParent=true;
//				}
//				treeNode.children = childNodes;
//				treeNode.open = false;
//
//				zTree.updateNode(treeNode);
//
//				zTree.refresh();
//			}
//
//		}
//	});
	  
}
 
 function removeNode(obj) {
	 $(obj).parent().remove();
	 if($("#interest_div>span").length==0) {
		 $("#interest_div").hide();
	 }
	 
	 var zTree = $.fn.zTree.getZTreeObj("interstLabeTree");
	 var deleteid = $(obj).attr("objId");
	 var nodes = zTree.getCheckedNodes(true);
	 for (var i = 0; i < nodes.length; i++) {
		 var node = nodes[i];
		 if (node.id == deleteid) {
			 node.checked = false;
			 node.halfCheck=false;//半选状态
			 zTree.updateNode(node);
			 removeChildNode(node);
			 removeParentNode(node);
		 }
	 }
 }
 //选中元素添加到左侧
 function onCheck(e, treeId, treeNode) {
	 //  获取 labelType
	 var id = treeNode.id;
	 //var labelName = treeNode.name;
	 //var labelType = treeNode.labelType;
	 var checkType = treeNode.checked;
	 
	 if(checkType) {
		  checkOn(treeNode);
	 } else {
		 // 移除被取消选中的元素
		 var exists = [];
		 $(".delete_icon").each(function(){
			 var _this = $(this);
			 var deleteid = $(_this).attr("objId");
			 if (deleteid == id) {
				 $(_this).parent().remove();
			 }
			 exists[deleteid] = true;
		 });
			 
		 var parentNode = treeNode.getParentNode();
		 removeParent(parentNode);
		 removeChild(treeNode);
		 
		 if($("#interest_div>span").length==0) {
			 $("#interest_div").hide();
		 }
		 var zTree = $.fn.zTree.getZTreeObj("interstLabeTree");
		 treeNode.halfCheck=false;//半选状态
		 zTree.updateNode(treeNode);
		
		 checkOffChildrenHalfCheck(treeNode);
		
		 var parent = treeNode.getParentNode();
		 if (parent.checked){
			 parent.halfCheck=true;//半选状态
		 }else{
			 parent.halfCheck=false;//半选状态
		 }
		 zTree.updateNode(parent);
		 var neghbours = parent.children;
		 var nocheck = 0;
		 var review = [];
		 for (var j = 0; j < neghbours.length; ++j){
			 if (!neghbours[j].checked){
				 nocheck++;
			 } else {
				 if (!exists[neghbours[j].id]) {
					 review.push(neghbours[j]);
				 }
			 }
		 }
		 
		 if (nocheck == 1){
			 
			 for (var i = 0; i < review.length; ++i){
				 appendLabel(review[i]);
			 }
		 }
		 
		 if($("#interest_div>span").length>0) {
			 $("#interest_div").show();
		 }
	 }
}		
 
 function appendLabel(node){
	 var namearray = [];
	 getParentsNode(namearray, node);
	 namearray.pop();
	 var labels = namearray.reverse().join(" > ");
	 $("#interest_div").append(
			  "<span class='data_span' style='margin-left:0px'><label id='click_show' >" + labels + " > " + node.name + "</label>" +
			  "<img src='/resources/person-images/delete_button.png' " + " objId='"  + node.id + "' labelName='"+ node.name + "' " +
			  "class='delete_icon' id='del_btn' onclick='removeNode(this)' /></span>"	  
	 );
 }
 
 function getParentsNode(namearray, node){
	 parentNode = node.getParentNode();
	 if (parentNode != null) {
		 namearray.push(parentNode.name);
		 return getParentsNode(namearray, parentNode);
	 }
 }
 
 function removeParent(parentNode){
	 var id = parentNode.id;
	 $(".delete_icon").each(function(){
		 var _this = $(this);
		 var deleteid = $(_this).attr("objId");
		 if (deleteid == id) {
			 $(_this).parent().remove();
		 }
	 });
	 var pparentNode = parentNode.getParentNode();
	 if(pparentNode){
		 removeParent(pparentNode);
	 }
 }
 /**
  * 选中方法
  * @param treeNode
  */
 function checkOn(treeNode){
	 
	 $("#interest_div").show();
	  appendLabel(treeNode);
	  checkOnParentHalfCheck(treeNode);
 }
 /**
  * 取消选中  去掉子节点半选状态 样式
  * @param treeNode
  */
 function checkOffChildrenHalfCheck(treeNode){
	 var children = treeNode.children;
	 var zTree = $.fn.zTree.getZTreeObj("interstLabeTree");
	 for(var i=0;i<children.length;i++){
		var child =  children[i];
		child.halfCheck=false;//半选状态
		zTree.updateNode(child);
		if(child.children.length>0){
			checkOffChildrenHalfCheck(child);
		}
	 }
 }
 /**
  * 去掉选中孩子
  * @param treeNode
  */
 function removeChild(treeNode){
	 var childs = treeNode.children;
	 for (var i = 0; i < childs.length; ++i){
		 $(".delete_icon").each(function(){
			 var _this = $(this);
			 var deleteid = $(_this).attr("objId");
			 if (deleteid == childs[i].id) {
				 $(_this).parent().remove();
			 }
		 });
		 removeChild(childs[i]);
	 }
 }
 /**
  * 点击右侧选中 去掉树孩子节点
  * @param treeNode
  */
 function removeChildNode(treeNode){
	 var zTree = $.fn.zTree.getZTreeObj("interstLabeTree");
	 
	 var children = treeNode.children;
	 for (var i = 0; i < children.length; i++) {
		 var child = children[i];
		 child.checked = false;
		 child.halfCheck=false;//半选状态
		 zTree.updateNode(child);
		 removeChildNode(child);
	 }
 }
 /**
  * 
  * 点击右侧选中  删除树父node 
  */
 function removeParentNode(node){
	 var zTree = $.fn.zTree.getZTreeObj("interstLabeTree");
	 var parentNode = node.getParentNode();
	
	 if(parentNode){
		 var neighbours = parentNode.children;
		 var removeFlag = true;
		 for(var i=0;i<neighbours.length;i++){
			 if(neighbours[i].checked){
				 removeFlag = false;
				 i= neighbours.length;
			 }
		 }
		
		 if(removeFlag){
			 parentNode.checked=false;
			 parentNode.halfCheck=false;
			 zTree.updateNode(parentNode);
			 removeParentNode(parentNode);
		 }
	 }
 }
function checkOnParentHalfCheck(treeNode){
	
	var parent = treeNode.getParentNode();
	if(parent){
		var neghbours = parent.children;
		 var checkNum = 0;
		 for (var j = 0; j < neghbours.length; ++j){
			 if (neghbours[j].checked&&!neghbours[j].halfCheck){
				 checkNum++;
			 }
		 }
		 
		 var zTree = $.fn.zTree.getZTreeObj("interstLabeTree");
		 if(checkNum==neghbours.length){
			 parent.halfCheck=false;//半选状态
			 removeChild(parent);
			 appendLabel(parent);
		 }else{
			 parent.halfCheck=true;//半选状态
		 }
		 treeNode.halfCheck=false;//半选状态
		 zTree.updateNode(treeNode);
		 zTree.updateNode(parent);
		 
		 if(parent.getParentNode()){
			 checkOnParentHalfCheck(parent);
		 }
	}
}