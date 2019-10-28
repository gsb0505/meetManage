function commit(){
		var roles = jQuery("#roles").serialize();
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
		
		
	jQuery.ajax({
		url:_path+'rolesAction/add.do',
		data:roles,
		type:"post",
		async:false,
		success:function(data){
			if(data=="success"){
				alert("添加成功");
				setTimeout("iFClose();", 1000);
			}else if(data == "fail"){
				alert("添加失败！");
			}else if(data == "exsit"){
				alert("该角色编号或角色名称已经存在！");
				
			}else{
				alert("异常！！请联系管理员");
				iFClose();
			}
		}
});
	jQuery(parent.window.document).find('#searchResult').click();
}

function fillNumOnly(obj) {  
    var str = obj.value;  
    if (trim(str) == "")  
        return;  
    if (/[^0-9]/g.test(str)) {  
        obj.value = str.substr(0, str.length - 1);  
    }  
}