<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/jquery.fn/jquery.validate.custom.js"></script>
<script type="text/javascript" src="<%=basePath%>js/owned/js.js"></script>
<style>
form.baseData {
	width: 50em;
}

em.error {
	background: url("../image/btn_sc_small.png") no-repeat 0px 0px;
	padding-left: 16px;
	font-size: 12px;
	font-style: normal;
}

em.valid {
	background: url("../image/bg_yes_button_zc.png") no-repeat 0px 0px;
	padding-left: 16px;
	color: green;
	font-size: 12px;
	font-style: normal;
}

form.baseData label.error {
	margin-left: auto;
	width: 250px;
}

em.error {
	color: #666;
}
</style>
<script type="text/javascript">
	jQuery().ready(function() {
		var code = "${config.code }";
		jQuery.validator.addMethod("rusCode", function(value, element) {  
			if(code == value){
				return true;
			}
			var result=false;
			jQuery.ajaxSetup({ async: false  });
			jQuery.ajax({
				url:_path +"ConfigAction/findByCode.do",
				type : "post",
				data : {'code':value},
				async : false,
				success : function(data) {
					if(data!=null){
						result=(data=="true"?true:false);
					}
				}
			});
			jQuery.ajaxSetup({ async: true  });
			return this.optional(element) || result;  
		}, "该code已存在");
		
		jQuery("#baseData").validate({
			debug : true,
			errorElement : "em",
			errorPlacement : function(error, element) {
				error.appendTo(element.parent("td").next("td").css("min-width","90px"));
			},
			success : function(label) {
				label.addClass("valid");
			},
			rules : {
				code:{
					required : true,
					digits:true,
					rusCode:true
				},
				num : {
					required : true
				},
				remark : {
					required : true
				}

			},
			messages: {
				num: {
					required: "请输入配置"
				},
				mark: {
					required: "请输入说明"
				}
			},
			submitHandler : function(form) {

				//iFClose();
				var agentRole = jQuery("#baseData").serialize();
				jQuery.ajax({
					url : _path + 'ConfigAction/modify.do',
					data : agentRole,
					type : "post",
					async : false,
					success : function(data) {
						if (data == "success") {
							alert_auto.call(window,"修改成功");
						} else if (data == "fail") {
							alert("修改失败");

						} else {
							alert("异常！！请联系管理员");
						}
					}
				});
				jQuery(parent.window.document).find('#searchResult').click();

			}

		});

	});
</script>
<style>
form.baseData {
	width: 50em;
}

em.error {
	background: url("../image/btn_sc_small.png") no-repeat 0px 0px;
	padding-left: 16px;
	font-size: 12px;
	font-style: normal;
}

em.valid {
	background: url("../image/bg_yes_button_zc.png") no-repeat 0px 0px;
	padding-left: 16px;
	color: green;
	font-size: 12px;
	font-style: normal;
}

form.baseData label.error {
	margin-left: auto;
	width: 250px;
}

em.error {
	color: #666;
}
</style>
</head>
<body style="min-width:300px;">
	<div style="display:;" class="inputTable" >
             	  <form  id="baseData" method="post">
						<table class="inputTable_liebiao inputTable_validate clear" style="height:100%">
							<tbody>
								<tr>
									<th>
										code： 
									</th>
									<td>
										<input type="text" class="formText number-limit" name="code" maxlength="5" style="width:350px;" value="${config.code }">
									</td>
									<td width="80px"><span style="color:red;font-size:20px">*</span></td>
								</tr>
								<tr>
									<th style="margin-left:15px;">
										配置： 
									</th>
									<td>
										<input type="text"  class="formText" name="num" maxlength="200" style="width:350px;" value="${config.num }">
										<input type="hidden" class="frimText" name="id" value="${config.id }" />
									</td>
									<td></td>
								</tr>
								<tr>
									<th>
										配置描述：
									</th>
									<td>
                                     	<textarea  class="formText"  name="remark" rows="5" cols="50" style="width: 350px; height: 65px;" >${config.remark}</textarea>
									</td>
									<td></td>
								</tr>
								
						</tbody>
                       </table>
                       <div class="tanchu_box_button">
                             <input type="submit" class="submitButton" value="保存" style="margin-left:50px;margin-top:10px;"/>
                       </div>
		     </form>
	</div>
	<!-------------------------------------------------------------------------------  -->



<script type="text/javascript" src="<%=basePath%>js/owned/js.js"></script>
</body>
</html>
