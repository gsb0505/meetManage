var setting = {
	check: {
		enable: true
	},
	data: {
		key: {
			title: "title"
		},
		simpleData: {
			enable: true
		}
	},
	callback: {
		onCheck: onCheck
	}
};

//随滚动条滚动
/* $(window).scroll(function () {  
    var top = $(window).scrollTop() + ($(window).height()*0.9);  
    var left = $(window).scrollLeft() + 320;  
    $("#butTable").animate({ "top": top }, 30);  //方式一  效果比较理想  
}); */

function onCheck(e, treeId, treeNode) {
	count();
}

function setTitle(node) {
	var zTree = $.fn.zTree.getZTreeObj("ztree");
	var nodes = node ? [node]:zTree.transformToArray(zTree.getNodes());
	for (var i=0, l=nodes.length; i<l; i++) {
		var n = nodes[i];
		n.title = "[" + n.id + "] isFirstNode = " + n.isFirstNode + ", isLastNode = " + n.isLastNode;
		zTree.updateNode(n);
	}
}
function count() {
	function isForceHidden(node) {
		if (!node.parentTId) return false;
		var p = node.getParentNode();
		return !!p.isHidden ? true : isForceHidden(p);
	}
	var zTree = $.fn.zTree.getZTreeObj("ztree");
	checkCount = zTree.getCheckedNodes(true).length;
	nocheckCount = zTree.getCheckedNodes(false).length;
	hiddenNodes = zTree.getNodesByParam("isHidden", true);
	hiddenCount = hiddenNodes.length;

	for (var i=0, j=hiddenNodes.length; i<j; i++) {
		var n = hiddenNodes[i];
		if (isForceHidden(n)) {
			hiddenCount -= 1;
		} else if (n.isParent) {
			hiddenCount += zTree.transformToArray(n.children).length;
		}
	}

	$("#isHiddenCount").text(hiddenNodes.length);
	$("#hiddenCount").text(hiddenCount);
	$("#checkCount").text(checkCount);
	$("#nocheckCount").text(nocheckCount);
}
function showNodes() {
	var zTree = $.fn.zTree.getZTreeObj("ztree");
	nodes = zTree.getNodesByParam("isHidden", true);
	zTree.showNodes(nodes);
	setTitle();
	count();
}
function hideNodes() {
	var zTree = $.fn.zTree.getZTreeObj("ztree");
	nodes = zTree.getSelectedNodes();
	if (nodes.length == 0) {
		alert("请至少选择一个节点");
		return;
	}
	zTree.hideNodes(nodes);
	setTitle();
	count();
}

  
 //加载选中
 function getChildNodes(){
			var treeObj = $.fn.zTree.getZTreeObj("ztree");
			var checkedNodes = treeObj.getCheckedNodes(true);//id  pid 的集合
			var test="";
			//alert(JSON.stringify(checkedNodes));
			for (var  i = 0;i < checkedNodes.length ;i++ ){ 
				test += checkedNodes[i].btnId+"#"+checkedNodes[i].pId+"#"+checkedNodes[i].isbtn+",";
			}
			document.getElementById("checkedNodes").value = test;
			
			 var nodes =  treeObj.getNodes();
		 	 treeObj.expandNode(nodes[0], false, true, true);
 }
 
 //提交
 function submitForm(){
	 $(parent.window.ymPrompt.getPage().message).mask('正在保存数据,请稍后...');
	 $("#submit").attr({ disabled: "disabled" });
	 getChildNodes();
	 setTimeout("subcommit();", 500);
 }
 
 
   //初始化操作
  $(document).ready( function(){
	  $(parent.window.ymPrompt.getPage().message).mask('权限列表加载中...');
      onloadZTree();
	  $(parent.window.ymPrompt.getPage().message).unmask();
  });
   //关闭窗口
  function m_close(){
		parent.window.ymPrompt.close();
	}
   