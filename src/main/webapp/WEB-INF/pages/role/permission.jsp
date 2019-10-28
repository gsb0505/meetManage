<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/" ;
	String role_id = (String)request.getAttribute("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<%-- <link rel="stylesheet" href="<%=basePath%>plugin/zTreeStyle/role.css" type="text/css"> --%>
	<link rel="stylesheet" href="<%=basePath%>plugin/zTreeStyle/zTreeStyle.css" type="text/css">
	<link href="<%=basePath %>plugin/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath%>js/jquery/1.4.4/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugin/zTreeStyle/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugin/zTreeStyle/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugin/zTreeStyle/js/jquery.ztree.exhide-3.5.js"></script>
	<script type="text/javascript" src="<%=basePath%>pageJs/common/orgDesc.js"></script>
	<script type="text/javascript" src="<%=basePath %>plugin/ymPrompt/ymPrompt.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/common/common.js"></script>
	<SCRIPT type="text/javascript">
	 //加载ztree
	  function onloadZTree(){
	      var ztreeNodes;
	     $.ajax( {
	       async : true, //是否异步
	       cache : false, //是否使用缓存
	       type : 'post', //请求方式,post
	       dataType : "json", //数据传输格式
	       url : "<%=basePath%>pages/role/allLibrary.do?typeId=0&roleId="+<%=role_id%>, //请求链接
	       error : function() {
	         alert('亲，崩溃了！');
	       },
	       success : function(data) {
	       ztreeNodes = data;//eval( "["+data+"]" ); //将string类型转换成json对象
	    //   alert(JSON.stringify(ztreeNodes));
	         $.fn.zTree.init($("#ztree"), setting, ztreeNodes);
	        /*  $("#hideNodesBtn").bind("click", {type:"rename"}, hideNodes);
				$("#showNodesBtn").bind("click", {type:"icon"}, showNodes);
				setTitle();
				count(); */
	       }
	     });
	  }
	 
	  function subcommit(){
			 var contentform=$("#contentform").serialize();
			 $.ajax({
			       async : false, //是否异步
			       cache : false, //是否使用缓存
			       type : 'post', //请求方式,post
			       dataType : "json", //数据传输格式
			       data:contentform,
			       url : "<%=basePath %>pages/role/updateLibrary.do?typeId=0",
			       error : function() {
			    	   $(parent.window.ymPrompt.getPage().message).unmask();
			       		$("#submit").removeAttr("disabled");
			         	alert('亲，崩溃了！');
			       },
			       beforeSend:function(XHR){
			       		return true;
			       },
			       success : function(data) {
			    	   if(data==true){
			    	   		alert("保存成功");
			    	   	}else{
			    	   		alert("保存失败");
			    	   	}
			    	   	setTimeout("m_close();", 1000);
			       },
			       complete: function () {
			    	   $(parent.window.ymPrompt.getPage().message).unmask();
			       		$("#submit").removeAttr("disabled");
			       }
			 });
		 }
	</SCRIPT>
 </HEAD>

<BODY style="min-width: 450px; min-height:450px;">
<div class="content_wrap">
	<div class="zTreeDemoBackground">
		<div style="float: left;"><ul id="ztree" class="ztree"></ul></div>
		
        <!-- 提交授权操作表单 -->   
        <div><form id="contentform" action = "" method="post">  
            <table id="butTable" style="margin-left:120px; width:100%-120px; height:auto; position:fixed; top:90%;">  
                <tr>  
                    <td><input type="hidden" id = "checkedNodes" name="checkedNodes" value = "" /></td>  
                    <td><input type="hidden" id = "roleId" name="role_id" value="<%=role_id %>"/></td>  
                </tr>  
                <tr>  
                    <td width="100%" align="center">  
                        <input type="button" id="submit" style="width:60px;" onclick = "submitForm();" class="btn" value="保存"/>&nbsp;  
                        <input type="button"  style="width:60px;"  class="btn" value="关闭" onclick="m_close()"/>  
                    </td>  
                </tr>  
            </table>  
        </form>  
         </div>                 
	</div>
</div>
<script type="text/javascript" src="<%=basePath%>pageJs/role/permission.js?vs=<%=System.currentTimeMillis()%>"></script>
</BODY>
</HTML>