jQuery().ready(function() {

	jQuery("#product").validate({
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
            goodsName : {
                required : true,
                maxlength:40
            },
            storeCode:{
                required : true
            },
            price:{
                required : true
            },count:{
                required : true
			},photoFile:{
                isPhoto : true
			}
		},
		submitHandler : function(form) {
            var user = jQuery("#product");
            var formData = new FormData(user[0]);
            // if(jQuery("#photoFile").val()){
            //     formData.append('photoFile', jQuery("#photoFile")[0].files[0]);
            // }
			jQuery.ajax({
				url : _path + 'product/modify.do',
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
                        form.append("type", 0);
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

