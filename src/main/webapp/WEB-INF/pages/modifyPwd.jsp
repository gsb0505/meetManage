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
<%@ include file="/WEB-INF/pages/head/login.ini"%>
<link href="<%=basePath %>plugin/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/datePicker.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/home.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/styles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/jquery/1.7.2/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugin/ymPrompt/ymPrompt.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath %>js/common/messages_cn.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<title>修改密码</title>
<script type="text/javascript">
jQuery().ready(function() {
	/**
	**	编号名称验证
	**/
	jQuery.validator.addMethod("isUnique", function(value, element) {
		var result=false;

		// 设置同步
        jQuery.ajaxSetup({
            async: false
        });
        jQuery.ajax({
    		url : _path + 'userAction/verifyOldPwd.do',
    		data : {'oldPwd':value},
    		type : "post",
    		success : function(data) {
	    		result=(data=="true"?true:false);
    		}
    	});
        // 恢复异步
        jQuery.ajaxSetup({
            async: true
        });
		return this.optional(element) || (result);

	}, "老密码不正确!");

	jQuery.validator.addMethod("isNewPwd", function(value, element) {

		var newPwd=/^[a-zA-Z]\w{5,17}$/;
		return this.optional(element) || (newPwd.test(value));
	}, "新密码以字母开头6~18位长度，只能包含字符、数字和下划线");

	jQuery.validator.addMethod("isAffirmNewPwd", function(value, element) {

		var affirmNewPwd=/^[a-zA-Z]\w{5,17}$/;
		return this.optional(element) || (affirmNewPwd.test(value));
	}, "新密码以字母开头6~18位长度，只能包含字符、数字和下划线");
	//表单提交验证
	jQuery("#form").validate({
		debug: true,
		errorElement: "em",
		errorPlacement: function(error, element) {
			error.appendTo(element.parent("td").next("td"));
		},
		success: function(label) {
			label.addClass("valid");
		},
	    rules: {
	    	oldPwd: {
			    required: true,
			    isUnique : true
			},
			newPwd: {
			    required: true,
			    minlength:5,
			    isNewPwd:true

			},
			affirmNewPwd: {
			    required: true,
			    isAffirmNewPwd:true,
			    equalTo: "#newPwd"
			}
	    }, messages: {
             affirmNewPwd: {
                 required: "*请输入确认密码",
                 equalTo: "*请再次输入相同的值"
             }
	    },
		submitHandler : function(form) {
			jQuery.ajax({
				url : _path + 'userAction/modifyPwd.do?newPwd='+jQuery("#newPwd").val(),
				async : false,
				success : function(data) {
					if (data == "success") {
						alert("修改成功！");
						setTimeout("iFClose();", 1000);
					} else if (data == "fail") {
						alert("修改失败！请重试！");
					} else{
						alert("出现异常！请联系管理员");
					}

				}
			});
		}

	});
});
</script>
<style>
.search_jk{
	margin-right:10px;
	height: 30px;
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
	<form id="form" action="" method="post">
		<table class="inputTable_liebiao inputTable_validate clear" border="0">
			<tr style="width: 100px">
				<th><span class="login_title">原密码：</span></th>
				<td><input type="password" value="" class="search_jk" id="oldPwd" name="oldPwd" maxlength="18"/></td>
				<td><span style="color:red;font-size:20px; float:left;">*</span></td>
			</tr>
			<tr>
				<th><span class="login_title">新密码：</span></th>
				<td><input type="password" name="newPwd" id="newPwd" value="" class="search_jk" maxlength="18" /></td>
				<td width="100px;"><span style="color:red;font-size:20px">*</span></td>
			</tr>
			<tr>
				<th><span class="login_title">确认新密码：</span></th>
				<td><input type="password" name="affirmNewPwd" id="affirmNewPwd" value="" class="search_jk" maxlength="18" /></td>
				<td width="100px;"><span style="color:red;font-size:20px">*</span></td>
			</tr>
			<tr>
				<td ><input type="button" id="" value="取消"  onclick="iFClose();" class="tanchu_button03" /></td>
				<td style="padding-left: 30px;"><input type="submit" value="确认"  class="tanchu_button03" /></td>
			</tr>
		</table>
	</form>
	</div>
</body>
</html>
