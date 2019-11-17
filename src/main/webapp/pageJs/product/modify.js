jQuery().ready(function() {
	
	/**
	**	编号名称验证
	**/
	jQuery.validator.addMethod("isUnique", function(value, element) {
		if(ss!=null && ss!='' && value==ss)
			return true;
		
		var result=false;
		// 设置同步
        jQuery.ajaxSetup({
            async: false
        });
        jQuery.ajax({
    		url : _path + 'meetRoom/isUnique.do',
    		data : {'name':value},
    		type : "post",
    		success : function(data) {
    			if(data!="exception"){
	    			result=(data=="true"?true:false);
    			}
    			else{
    				data="会议室名称验证合法性异常，请重试！";	
    			}
    		}
    	});
        // 恢复异步
        jQuery.ajaxSetup({
            async: true
        });
		return this.optional(element) || (result) ;
		
	}, "该名称已被注册!");

	
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
			meetRoomName : {
				required : true,
				isUnique : true,
				maxlength:20
			},
			meetRoomID:{
				required : true,
				maxlength:20
			},
			meetRoomType:{
				required : true
			},personID:{
				required : true
			},terminalNo:{
				required : true,
				maxlength:20
			}
		},
		submitHandler : function(form) {
			var user = jQuery("#user").serialize();
			jQuery.ajax({
				url : _path + 'meetRoom/modify.do',
				type : "post",
				data : user,
				async : false,
				success : function(data) {
					if (data == "success") {
					    alert("更新成功！");
						setTimeout("iFClose();", 1000);
					} else if (data == "fail") {
						alert("更新失败！请重试");
					} else {
						alert(data);
					}

				}
			});

			jQuery(parent.window.document).find('#searchResult').click();
		}

	});
	

	
});

seajs.use('common/common.form.js', function(a) {});
