jQuery().ready(function() {
	jQuery("#organ").orgSelect();
	jQuery.validator.addMethod("isPhoneCode", function(value, element) {
		//验证电话号码
		//验证规则：区号+号码，区号以0开头，3位或4位
		//号码由7位或8位数字组成
		//区号与号码之间可以无连接符，也可以“-”连接
		//如01088888888,010-88888888,0955-7777777 
		var tel = /^0\d{2,3}-?\d{7,8}$/;
		//电话号码与手机号码同时验证
		var tel2=/(^(\d{3,4}-)?\d{7,8})$|([1][3-8]+\d{9})$/;
		return this.optional(element) || (tel2.test(value));
	}, "请正确填写您的电话号码");

	// jQuery.validator.addMethod("isloginPSW", function(value, element) {
	// 	//电话号码与手机号码同时验证
	// 	var tel2=/^[a-zA-Z]\w{5,17}$/;
	// 	return this.optional(element) || (tel2.test(value));
	// }, "登录密码以字母开头6~18位长度，只能包含字符、数字和下划线");

    jQuery.validator.addMethod("isloginPSW", function(value, element) {
        //电话号码与手机号码同时验证
        var tel2=/^[a-zA-Z]?\w{5,17}$/;
        return this.optional(element) || (tel2.test(value));
    }, "登录密码6~18位长度，只能包含字符、数字和下划线");

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
			email : {
				required : true,
				email : true
			},
			loginPSW:{
				required : true,
				isloginPSW:true
			},
			phone : {
				required : true,
				isPhoneCode : true
			},orgId:{
				required : true
			},
			depId:{
				required : true
			},
            photoFile:{
                isPhoto:true
			}
		},
		submitHandler : function(form) {
			var user = jQuery("#user");
            var formData = new FormData(user[0]);
            if(jQuery("#photoFile").val()){
            	formData.append('photoFile', jQuery("#photoFile")[0].files[0]);
            }
            jQuery.ajax({
				url : _path + 'userAction/modify.do',
				type : "post",
                enctype: 'multipart/form-data',
                processData: false,
                contentType: false,
				data : formData,
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
seajs.use('common/common.form.js', function(a) {
	  
});