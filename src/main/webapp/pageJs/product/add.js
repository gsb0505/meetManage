jQuery().ready(function () {

    jQuery.validator.addMethod("pnameUnique", function(value, element) {
        var result=false;

        // 设置同步
        jQuery.ajaxSetup({
            async: false
        });
        var store = jQuery("#storeCode").val();
        jQuery.ajax({
            url : _path + 'product/'+store+'/product.do',
            data : {'product':value},
            type : "get",
            success : function(data) {
                if(data!="exception"){
                    result=(data=="exsit"?false:true);
                }else{
                    data="商品名称验证合法性异常，请重试！";
                }
            }
        });
        // 恢复异步
        jQuery.ajaxSetup({ async: true });
        return this.optional(element) || (result);

    }, "该商品名称已存在!");

    jQuery("#product").validate({
        debug: true,
        errorElement: "em",
        errorPlacement: function (error, element) {
            var dd = element.parent("td").next("td");
            error.appendTo(dd);
        },
        success: function (label) {
            label.addClass("valid");
        },
        rules: {
            goodsName: {
                required: true,
                maxlength: 40,
                pnameUnique:true
            },
            storeCode: {
                required: true
            },
            price: {
                required: true
            }, count: {
                required: true
            },
            photoFile: {
                isPhoto: true
            }
        },
        submitHandler: function (form) {
            jQuery("#goodsName").val(trim(jQuery("#goodsName").val())); //去空格

            var form = new FormData(jQuery("#product")[0]);
            // if (jQuery("#photoFile").val()) {
            //     form.append("photoFile", jQuery("#photoFile")[0].files[0]);
            // }
            jQuery.ajax({
                url: _path + 'product/add.do',
                type: "post",
                data: form,
                processData: false,
                contentType: false,
                async: false,
                success: function (data) {
                    if (data == "success") {
                        alert("添加成功！");
                        setTimeout("iFClose()", 1000);
                    } else if (data == "fail" || data == "exception") {
                        alert("添加失败！请重试！");
                    } else if (data == "exsit") {
                        alert("该商品已经存在!");
                    } else {
                        alert(data);
                    }

                }
            });

            jQuery(parent.window.document).find('#searchResult').click();
        }

    });


    // jQuery.ajax({
    // 	url : _path + 'meetRoom/getId.do',
    // 	type : "post",
    // 	async : false,
    // 	success : function(data) {
    // 		if(data==null || data==""){
    // 			return "获取会议室号失败.";
    // 		}
    // 		return jQuery(".meetRoomID").val(data);
    // 	}
    // });

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
                        //url: _path + 'transferTo.do',
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
                                    alert("上传成功");
                                } else {
                                    alert(data.message);
                                }
                            }
                        }
                    });
                } else {
                    jQuery("#photoUrl").val("");
                    jQuery("#photoFile").val("");
                }
            }
        })
    });

});