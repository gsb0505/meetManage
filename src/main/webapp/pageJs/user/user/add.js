jQuery().ready(function () {
    jQuery("#organ").orgSelect();
    jQuery.validator.addMethod("isPhoneCode", function (value, element) {
        //验证电话号码
        //验证规则：区号+号码，区号以0开头，3位或4位
        //号码由7位或8位数字组成
        //区号与号码之间可以无连接符，也可以“-”连接
        //如01088888888,010-88888888,0955-7777777
        var tel = /^0\d{2,3}-?\d{7,8}$/;
        //电话号码与手机号码同时验证
        var tel2 = /(^(\d{3,4}-)?\d{7,8})$|([1][3-8]+\d{9})$/;
        return this.optional(element) || (tel2.test(value));
    }, "请正确填写您的电话号码");


    jQuery.validator.addMethod("isloginPSW", function (value, element) {

        //电话号码与手机号码同时验证
        var tel2 = /^[a-zA-Z]\w{5,17}$/;
        return this.optional(element) || (tel2.test(value));
    }, "登录密码以字母开头6~18位长度，只能包含字符、数字和下划线");

    /**
     **    编号唯一验证
     **/
    jQuery.validator.addMethod("isUnique2", function (value, element) {
        var result = false;

        // 设置同步
        jQuery.ajaxSetup({
            async: false
        });
        jQuery.ajax({
            url: _path + 'userAction/isUnique.do',
            data: {'userId': value},
            type: "post",
            success: function (data) {
                if (data != "exception") {
                    result = (data == "true" ? true : false);
                }
            }
        });
        // 恢复异步
        jQuery.ajaxSetup({
            async: true
        });
        return this.optional(element) || (result);

    }, "该用户名被注册!");

    jQuery("#user").validate({
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
            userId: {
                required: true,
                isUnique2: true
            },
            email: {
                required: true,
                email: true
            },
            loginPSW: {
                required: true,
                isloginPSW: true
            },
            phone: {
                required: true,
                isPhoneCode: true
            },
            orgId: {
                required: true
            },
            depId: {
                required: true
            },
            photoFile:{
                isPhoto:true
            }
        },
        submitHandler: function (form) {
            jQuery("#userId").val(trim(jQuery("#userId").val())); //去空格
            var user = jQuery("#user")[0];
            var formData = new FormData(user);
            //formData.append('photoFile', jQuery("#photoUrl")[0].files[0]);
            jQuery.ajax({
                url: _path + 'userAction/add.do',
                type: "post",
                data: formData,
                processData: false,
                contentType: false,
                async: false,
                success: function (data) {
                    if (data == "success") {
                        alert("添加成功！");
                        setTimeout("iFClose();", 1000);
                    } else if (data == "fail") {
                        alert("添加失败！请重试！");
                    } else if (data == "exsit") {
                        alert("该用户名已经存在!");
                    } else {
                        if (data != "exception") {
                            alert(data);
                        }
                        alert1("异常！！请联系管理员");
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
                        form.append("type", 1);
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
seajs.use('common/common.form.js', function (a) {

});