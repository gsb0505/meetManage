<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<meta content="zh-CN" http-equiv="Content-Language"></meta>

<meta content="IE=edge" http-equiv="X-UA-Compatible"></meta>
<meta content="" name="description"></meta>
<meta content="上海电科智能系统股份有限公司版权所有" name="Copyright"></meta>
<%@ include file="/WEB-INF/pages/head/login.jsp"%>
<link href="<%=basePath %>plugin/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/datePicker.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/home.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/styles.css" rel="stylesheet" type="text/css" />
<title>密码重置</title>
<script type="text/javascript">

	function resetPwd(){
		var userId = jQuery("#userId").val();
		if(userId){			
			jQuery.ajax({
				url:_path+'loginAction/resetPwd.do?userId='+userId,
				method:"POST",		
				async:false,
				success:function(data){
					showAlertWindow(data);
					setTimeout("iFClose()",5000);
				}
			});
			
		}else{
			showAlertWindow("登录帐号不能为空");
		}
	}
	
</script>
<style>
.search_jk{
	margin-right:30px;
	height: 30px;
	width: 80%;
	font-size: 12px;
	border: 1px solid #CCC;
	padding-left: 5px;
	background-color: #F9FAFB;
	}
.login_title{
	font-size: 14px;
}
</style>
</head>

<body style="min-width: 540px; overflow: auto; overflow: hidden">
	<div class="inputTable" >
	<table class="inputTable_liebiao inputTable_validate clear">
		<tr>
			<th><span class="login_title">登录帐号：</span></th 	>
			<td><input type="text" id="userId" value="" class="search_jk" ></td>
		</tr>
		<tr>
			<td><input type="button" id="" value="取消"  onclick="iFClose();" class="tanchu_button03" ></td>
			<td><input type="button" id="resetPwd" value="确认"  onclick="resetPwd();" class="tanchu_button03" ></td>
		</tr>
	</table>
	</div>
</body>
</html>
