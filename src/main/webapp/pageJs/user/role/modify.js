function commit(){
	var roleCode = jQuery("#roleCode").val();
	var roleDesc = jQuery("#roleDesc").val();
	var reg=/^[1-9]\d*$|^0$/;
	if(!reg.test(roleCode)){
		alert("角色编号只能是数字!");
		return ;
	}
	if (trim(roleDesc)==""){
		alert("角色名称不能为空!");
		return ;
	}
	var roles = jQuery("#roles").serialize();
	jQuery.ajax({
		url:_path+'rolesAction/modify.do',
		data:roles,
		type:"post",
		async:false,
		success:function(data){
			if(data=="success"){
				alert("修改成功");
				setTimeout("iFClose();", 1000);
			}else if(data == "fail"){
				alert("修改失败！");
			}else{
				alert("异常！！请联系管理员");
				iFClose();
			}
		}
	});
	jQuery(parent.window.document).find('#searchResult').click();
}