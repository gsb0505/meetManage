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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>
<script type="text/javascript" src="<%=basePath%>js/jquery.fn/jquery.validate.custom.js"></script>
<script type="text/javascript">
seajs.use('common/common.form.js', function(a) {});
	jQuery().ready(function() {

		jQuery.validator.addMethod("checkName", function(value, element) {  
			var result=false;
			jQuery.ajaxSetup({ async: false  });
			jQuery.ajax({
				url:_path +"organizationAction/getOrgByNo.do",
				type : "post",
				data : {'name':value},
				async : false,
				success : function(data) {
					if(data!=null ){
						result=(data=="true"?true:false);
					}
				}
			});
			jQuery.ajaxSetup({ async: true  });
			return this.optional(element) || result;     
		}, "该部门名称已存在");
		
		jQuery("#user").validate({
			debug : true,
			errorElement : "em",
			errorPlacement : function(error, element) {
				var dd=element.parent("td").next("td");
				error.appendTo(dd);
			},
			success : function(label) {
				label.addClass("valid");
			},
			rules : {
				agencyNo : {
					required: true,
					orgNo:true,
					maxlength:10
				},
				name : {
					required : true
					,maxlength:10
					,checkName:true
				}
			},
			submitHandler : function(form) {
				var user = jQuery("#user").serialize();
				jQuery.ajax({
					url : _path + 'organizationAction/add.do',
					type : "post",
					data : user,
					async : false,
					success : function(data) {
						if (data == "success") {
							alert_auto.call(window,"添加成功！");
						} else if (data == "fail") {
							alert("添加失败！请重试！");
						} else if (data == "exsit") {
							alert("该部门已经存在!");
						} else{
							if(data!="exception")
								alert(data);
							else
								alert("异常！！请联系管理员");
						}

					}
				});
				jQuery(parent.window.document).find('#searchResult').click();
			}

		});

	});
</script>
</head>
<body style="min-width: 540px; overflow: auto; overflow: hidden">
	<div style="display:;" class="inputTable">

		<form id="user" action="" method="post">
			<table class="inputTable_liebiao inputTable_validate clear">
				<tbody>
					<tr>
						<th>部门编号:</th>
						<td><input type="text" name="agencyNo" class="formText"
							value="" id="agencyNo" maxlength="10"></input>
						</td>
						<td width="80px"><span style="color:red;font-size:20px">*</span></td>
						<th>部门名称:</th>
						<td><input type="text" name="name" class="formText"
							value="" maxlength="10"></input></td>
						<td><span style="color:red;font-size:20px">*</span></td>
					</tr>
					<tr>
						<th>备注:</th>
						<td><input name="remark" type="text" class="formText" maxlength="16"></input>
						</td>
						<td></td>
					</tr>
				</tbody>
			</table>
			<div class="tanchu_box_button">

				<input type="submit" class="tanchu_button03" name="submit" value="保存" /> <input
					type="button" class="tanchu_button04" name="button" value="取消"
					onclick="iFClose()" />

			</div>
		</form>


	</div>
</body>
</html>
