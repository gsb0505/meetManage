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
			},photoFile:{
                isPhoto:true
            }
		},
		submitHandler : function(form) {
			//var user = jQuery("#user").serialize();
            var formData = new FormData(jQuery("#user")[0]);
            if(jQuery("#photoFile").val()){
                formData.append('photoFile', jQuery("#photoFile")[0].files[0]);
			}
			jQuery.ajax({
				url : _path + 'meetRoom/modify.do',
				type : "post",
				data : formData,
                processData: false,
                contentType: false,
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

    jQuery("#photoFile").change(function () {
        ymPrompt.confirmInfo({
            message: '是否上传图片?', handler: function (type) {
                if (type == "ok") {
                    var form = new FormData();
                    if (jQuery("#photoFile").val()) {
                        form.append("photoFile", jQuery("#photoFile")[0].files[0]);
                        form.append("type", 2);
                    }
                    jQuery.ajax({
                        url: _core_path + 'external/uploadImage',
                        type: "post",
                        data: form,
                        processData: false,
                        contentType: false,
                        crossDomain: true,
                        dataType: 'json',
                        async: false,
                        success: function (data) {
                            if (data && data.retcode) {
                                var code = data.retcode;
                                if (code == "200") {
                                    jQuery("#photoUrl").val(data.message);
                                    jQuery("input[name='photoUrl']").val(data.message);
                                    alert("上传成功");
                                } else {
                                    alert(data.message);
                                }
                            }
                        }
                    });
                } else {
                    jQuery("#photoFile").val("");
                }
            }
        })
    });
	
});

seajs.use('common/common.form.js', function(a) {});
