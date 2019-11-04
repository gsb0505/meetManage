<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/" ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>
	<link rel="stylesheet" href="<%=basePath%>plugin/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="<%=basePath%>js/jquery/1.4.4/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugin/zTreeStyle/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugin/zTreeStyle/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugin/zTreeStyle/js/jquery.ztree.exhide-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>pageJs/common/orgDesc.js"></script>
	<SCRIPT type="text/javascript">
	var setting = {
			check: {
				enable: true,
				//chkboxType :{ "Y" : "", "N" : "" }
				chkStyle:"radio",
				radioType:"all"
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
				onCheck: onCheck/* ,
				beforeCheck:beforeCheck */
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
		
/* 		function beforeCheck(treeId, treeNode){
			var aa = treeNode.getParentNode();
			alert1(aa.length)
			//alert1(JSON.stringify(treeNode.getParentNode()));
		} */

		function setTitle(node) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
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
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			checkCount = zTree.getCheckedNodes(true).length,
			nocheckCount = zTree.getCheckedNodes(false).length,
			hiddenNodes = zTree.getNodesByParam("isHidden", true),
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
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getNodesByParam("isHidden", true);
			zTree.showNodes(nodes);
			setTitle();
			count();
		}
		function hideNodes() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				alert("请至少选择一个节点");
				return;
			}
			zTree.hideNodes(nodes);
			setTitle();
			count();
		}

		 //加载ztree
		  function onloadZTree(){
		      var ztreeNodes;
		      var uri;
		      var orgId="<%=request.getAttribute("orgId")%>";
		      if(orgId!=null&&orgId.indexOf(",")>0){
		    	  uri=_path+"orgDescAction/orgLists.do?orgId="+orgId;
		      }else{
		    	  uri=_path+"orgDescAction/orgList.do?orgId="+orgId;
		      }
		      //省份查询
	    	  var type=$("#type").val();
		      if(type!=null&&type!=""&&type=="1"){
		    	  uri=_path+"orgDescAction/orgPLists.do?orgId="+orgId;
		      }
		      $.ajax( {
		       async : true, //是否异步
		       cache : false, //是否使用缓存
		       type : 'post', //请求方式,post
		       dataType : "json", //数据传输格式
		       url : uri,
		       beforeSend:function(){
		    	   $(parent.window.ymPrompt.getPage().message).mask('正在初始化地域...');
		    	   //alert(parent.window.ymPrompt.win);
		       },
		       error : function() {
		         alert('亲，崩溃了！');
		       },
		       success : function(data) {
			       ztreeNodes = data;//eval( "["+data+"]" ); //将string类型转换成json对象
		         $.fn.zTree.init($( "#treeDemo"), setting, ztreeNodes);
		         $("#hideNodesBtn").bind("click", {type:"rename"}, hideNodes);
					$("#showNodesBtn").bind("click", {type:"icon"}, showNodes);
					setTitle();
					count();
		       },
		       complete:function(XHR, TS){
		    	   $(parent.window.ymPrompt.getPage().message).unmask();
		       }
		     });
		  }
		  
		 //加载选中
		 function getChildNodes(){
			 
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var checkedNodes = treeObj.getCheckedNodes(true);//id  pid 的集合
					var orgdescs = [];
					var orgIds = [];
					 var type=$("#type").val();
				      
					for (var  i = 0;i < checkedNodes.length ;i++ ){ 
				//		orgdescs += checkedNodes[i].name + ",";
				//		orgIds += checkedNodes[i].id + ",";
						orgdescs.push(checkedNodes[i].name);
						orgIds.push(checkedNodes[i].id);
					}
					 m_close();
					window.parent.document.getElementById("orgDesc").value = orgdescs;
					alert(type);
					if(type!=null&&type!=""&&type=="1"){
						window.parent.document.getElementById("province").value = orgIds;
				    }else{
						window.parent.document.getElementById("orgId").value = orgIds;
				    }
					 
		 }
		 
		 
		   //初始化操作
		  $(document).ready( function(){
		     onloadZTree();
		  });
		   //关闭窗口
		  function m_close(){
				parent.window.ymPrompt.close();
			}
			
		   
	</SCRIPT>
 </HEAD>

<BODY style="min-width: 350px;min-height:225px;">
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
        <!-- 提交授权操作表单 -->   
        <form>  
        	<input type="hidden" name="orgId" value="${orgId }" />
        	<input type="hidden" name="type" id="type" value="${type }" />
            <table id="butTable"  style="float:right; margin-left:100px; width:70%; height:auto; position:fixed;top:90%; ">  
                <tr>  
                    <td width="100%" align="right">  
                        <input type="button" style="width:60px;" onclick = "getChildNodes()" class="btn" value="保存"/>&nbsp;  
                        <input type="button"  style="width:60px;"  class="btn" value="关闭" onclick="m_close()"/>  
                    </td>  
                </tr>  
            </table>  
        </form>  
                          
	</div>
</div>
</BODY>
</HTML>