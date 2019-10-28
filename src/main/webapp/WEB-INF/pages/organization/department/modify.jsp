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
<%@ include file="/WEB-INF/pages/head/pagehead.ini"%>
<script type="text/javascript">
seajs.use('common/common.form.js', function(a) {});
jQuery().ready(function() {

	jQuery.validator.addMethod("checkName", function(value, element) {  
		var result=false;
		var id = jQuery("#id").val();
		jQuery.ajaxSetup({ async: false  });
		jQuery.ajax({
			url:_path +"departmentAction/getDepByNo.do",
			type : "post",
			data : {'name':value,'id':id},
			async : false,
			success : function(data) {
				if(data!=null ){
					result=(data=="true"?true:false);
				}
			}
		});
		jQuery.ajaxSetup({ async: true  });
		return this.optional(element) || result;     
	}, "该职位名称已存在");
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
			departmentNo : {
				required: true,
				maxlength:10
			},
			name : {
				required : true,
				maxlength:10,
				checkName:true
			}
		},
		submitHandler : function(form) {
			var user = jQuery("#user").serialize();
			jQuery.ajax({
				url : _path + 'departmentAction/modify.do',
				type : "post",
				data : user,
				async : false,
				success : function(data) {
					if (data == "success") {
						alert("修改成功！");
						setTimeout("iFClose();", 1000);
					} else if (data == "fail") {
						alert("修改失败！请重试");
					} else {
						if(data!="exception"){
							alert(data);
						}else{
							alert("异常！！请联系管理员");
						}
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
			<input type="hidden" name="id" value="${a.id }"  id="id"/>
			<input type="hidden" name="departmentNo" value="${a.departmentNo}" />
			<table class="inputTable_liebiao inputTable_validate clear">
				<tbody>
					<tr>
						<th>职位编号:</th>
						<td><input type="text" value="${a.departmentNo}"
							class="formText" name="department_no" disabled="disabled" maxlength="10"/></td>
						<td width="80px"><span style="color:red;font-size:12px;">(不予修改)</span></td>
						<th>职位名称:</th>
						<td><input type="text" value="${a.name}"
							class="formText" name="name" maxlength="10"></td>
						<td><span style="color:red;font-size:20px">*</span></td>
					</tr>
					<tr>
						<th>备注:</th>
						<td><input type="text" value="${a.remark}"
							class="formText" name="remark" maxlength="16"></input></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			<div class="tanchu_box_button">

				<input type="submit" class="tanchu_button03" value="保存"
					></input> <input type="button"
					class="tanchu_button04" value="取消" onclick="iFClose()"></input>

			</div>
		</form>

	</div>
</body>
</html>
