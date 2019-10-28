<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
String systemPath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path+"/" ;
%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<Html>
<Head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<%=basePath %>plugin/zTreeStyle/zTreeStyle.css" type="text/css" />
<link href="<%=basePath %>plugin/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/configView.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>js/jquery/1.8.2/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugin/zTreeStyle/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=basePath %>plugin/zTreeStyle/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=basePath %>plugin/zTreeStyle/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=basePath %>js/owned/sea.js"></script>
<script type="text/javascript" src="<%=basePath %>plugin/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>plugin/ymPrompt/ymPrompt.js"></script>
<script type="text/javascript" src="<%=basePath %>js/common/common.js"></script>
<script type="text/javascript" src="<%=basePath %>js/common/map.js.fun.js"></script>
<script type="text/javascript" src="<%=basePath%>pageJs/common/orgDesc.js"></script>
<script type="text/javascript">

	var dep_jobs=new Map();
	var orgId = "${orgId}";
	var curr_departId="";
	var departIdMap=new Map(); 
	var _path      = "<%=systemPath %>";
	var departPrefix = "@";
	//勾选
	function config(departId, type) {
		if(type==null){
			return;
		}
		//if(type.indexOf("del")!=-1 && !findAcc(departId,null)){
		//	return;
		//}
		
		jQuery.ajax({
			url : _path + 'organizationAction/config.do',
			type : "post",
			data : {
				'orgId' : orgId,
				departmentId : departId,
				type : type
			},
			async : false,
			timeout : 3000,
			success : function(data) {
				if (data != null && data != "true") {
					alert("配置失败!");
				}else{
					if(type.indexOf("add")!=-1){
						departIdMap.put(orgId+"#"+departId,"");
						dep_jobs.put(departPrefix+departId,"");
					}else if(type.indexOf("del")!=-1){
						departIdMap.remove(orgId+"#"+departId);
						dep_jobs.remove(departPrefix+departId);
						var treeObj = $.fn.zTree.getZTreeObj("treeList");
						var treeNode = depfilter(departId);
						var childNode;
						if(treeNode.children)
						for(var i=0;i<treeNode.children.length;i++) {
							childNode = treeNode.children[i];
							console_log('childNode.name: '+childNode.name);
							treeObj.checkNode(childNode, false, false,true);
						}
					}
				}
					
			},
			beforeSend:function(XHR){
				$(parent.window.ymPrompt.getPage().message).mask('配置中...');
       			return true;
	       },complete: function (XMLHttpRequest,status) {
	    	   $(parent.window.ymPrompt.getPage().message).unmask();
	       }
		});
	}
	
	
	function findAcc(departId,jobId){
		var data_Json = {};
		var dj="科室";
		var dd=false;
		data_Json["organizationId"]=orgId;
		data_Json["departmentId"]=departId;
		jQuery.ajaxSetup({ async: false  });
		jQuery.ajax({
			url : _path + 'cardAccount/find.do',
			type : "post",
			data : data_Json,
			async : false,
			dataType : 'json',
			success : function(data) {
				if (data != null && data) {
					var treeObj = $.fn.zTree.getZTreeObj("treeList");
					var node ;
					node = depfilter(departId);
					treeObj.checkNode(node, true, true);
					alert("该"+dj+"下有卡用户无法删除!<br/> <span style='color:green;'>(请删除该"+dj+"下的用户后，重试操作。)</span>");
					dd=false;
				}else
					dd=true;
			},error:function(jqXHR, textStatus, errorMsg){
				dd=false;
			}
		});
		jQuery.ajaxSetup({ async: true  });
		return dd;
	}

	function depfilter(id) {
		var treeObj = $.fn.zTree.getZTreeObj("treeList");
		var nodes = treeObj.getNodesByParam("id", id, null);
		for (var i = 0; i < nodes.length; i++) {
			if(nodes[i].level == 0){
				return nodes[i];
			}
		}
	}
	function jobfilter(id,node) {
		var treeObj = $.fn.zTree.getZTreeObj("treeList");
		var nodes = treeObj.getNodesByParam("id", id, node);
		for (var i = 0; i < nodes.length; i++) {
			if(nodes[i].level == 1){
				return nodes[i];
			}
		}
	}
	
	//查询
	function loadDepart() {
		$.ajax({
			url : _path + 'organizationAction/getDataByOrgId.do',
			type : "post",
			data : {
				'orgId' : orgId
			},
			async : false,
			dataType : 'json',
			success : function(data) {
				if (data != null) {
					var treeObj = $.fn.zTree.getZTreeObj("treeList");
					var node ;
					for (var i = 0; i < data.length; i++) {
						node = depfilter(data[i].departmentId);
						treeObj.checkNode(node, true, true);						
						departIdMap.put(orgId+"#"+data[i].departmentId,data[i].departmentId);
					}
				}
			}
		});
	}
	
	var setting = {
			async: {
				enable: false
			},
			check: {
				enable: true,
				chkboxType : { "Y" : "", "N" : "" }
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			view: {
				expandSpeed: ""
			},
			callback: {
				beforeExpand: beforeExpand,
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError,
				onCheck: zTreeOnCheck
			}
		};

		var zNodes =[];
		$.ajax({
			url : _path + 'departmentAction/listTree.do',
			type : "post",
			async : false,
			dataType : 'json',
			success : function(data) {
				if (data != null) {
					zNodes=data;
				}
			}
		});

		var log, className = "dark",
		startTime = 0, endTime = 0, perCount = 100, perTime = 100;

		function beforeExpand(treeId, treeNode) {
			if (!treeNode.isAjaxing) {
				startTime = new Date();
				treeNode.times = 1;
				ajaxGetNodes(treeNode, "refresh");
				return true;
			} else {
				alert("Downloading data, Please wait to expand node...");
				return false;
			}
		}
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			if (!msg || msg.length == 0) {
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj("treeList"),
			totalCount = treeNode.count;
			if (treeNode.children.length < totalCount) {
				setTimeout(function() {ajaxGetNodes(treeNode);}, perTime);
			} else {
				treeNode.icon = "";
				zTree.updateNode(treeNode);
				zTree.selectNode(treeNode.children[0]);
				endTime = new Date();
				var usedTime = (endTime.getTime() - startTime.getTime())/1000;
				className = (className === "dark" ? "":"dark");
				showLog("[ "+getTime()+" ]&nbsp;&nbsp;treeNode:" + treeNode.name );
				showLog("Child node has finished loading, a total of "+ (treeNode.times-1) +" times the asynchronous load, elapsed time: "+ usedTime + " seconds ");
			}
		}
		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			var zTree = $.fn.zTree.getZTreeObj("treeList");
			alert("网络异常...");
			treeNode.icon = "";
			zTree.updateNode(treeNode);
		}
		function ajaxGetNodes(treeNode, reloadType) {
			var zTree = $.fn.zTree.getZTreeObj("treeList");
			zTree.reAsyncChildNodes(treeNode, reloadType, true);
		}
		function showLog(str) {
			if (!log) log = $("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 4) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function zTreeOnCheck(event, treeId, treeNode){
			console_log(treeNode.id+":treeId");
			var fullName;
			if(treeNode.isParent){
				curr_departId=treeNode.id;
				fullName=departPrefix+curr_departId;
			}else
				fullName=departPrefix+treeNode.getParentNode().id;
			
			if(treeNode.checked){
				if(treeNode.isParent){
					if(departIdMap.get(orgId+"#"+treeNode.id)==treeNode.id){
						return;
					}
					config(treeNode.id,"addRow");
				}
			}else{
				if(treeNode.isParent){
					if(departIdMap.get(orgId+"#"+treeNode.id)!=null){
						config(treeNode.id,"delRow");
					}
				}
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeList"), setting, zNodes);
			loadDepart();
		});
		
</script>

</head>

<body style="background:#ccc" >

	<div class="tanchu_box_button">
		<ul id="treeList" class="ztree"></ul>
	</div>
	<div class="tanchu_box_button1">
	</div>
</body>
</html>
