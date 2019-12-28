jQuery().ready(function () {
    var orderDetailForm = jQuery("#user").serializeJSON();

    //商品项添加删除
    jQuery("#addProduct").click(addProFuntion);
    jQuery(".delProduct").live("click", function () {
        var parent = jQuery(this).parents("tr");
        var clas = parent.attr("class");
        if (clas == "tr-pro0" || clas === "tr-pro0") {
        } else {
            productCount--;
            parent.remove();
        }
    });

    jQuery("#clearItem").click(function () {
        //确定清空内容？
        jQuery("input[name^='goodsDetailList[]']").val("");
        jQuery("p[name^='goodsDetailList[]']").html("");
        jQuery("#productCount").html("0");
        jQuery("#productAmount").html("0");

    });

    function addProFuntion() {
        var parent = jQuery(this).parents("tr");
        parent.parent().append("<tr class='tr-pro0" + trCount + "'> " + productTr() + "</tr>");
        trCount++;
    }


    jQuery.validator.addMethod("isUnique", function (value, element) {
        var result = checkForm();

        return this.optional(element) || (result);

    }, "该时间已被预约!");
    jQuery.validator.addMethod("dateVali", function (value, element) {
        var result = false;
        result = checkDate();
        return this.optional(element) || (result);

    }, "请输入大于当前的预约时间!");
    jQuery.validator.addMethod("dateVali2", function (value, element) {
        var result = false;
        result = checkDate2();
        return this.optional(element) || (result);

    }, "结束时间不能小于开始时间!");

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
            meetName: {
                required: true,
                maxlength: 20
            },
            meetDate: {
                required: true,
                isUnique: true
            },
            meetStartTime: {
                required: true,
                isUnique: true
            }, meetEndTime: {
                required: true,
                isUnique: true,
                checkDate2:true
            }, meetRoomID: {
                required: true,
                isUnique: true
            }
        },
        submitHandler: function (form) {
            jQuery("#meetName").val(trim(jQuery("#meetName").val())); //去空格
            var user = jQuery("#user").serializeJSON();
            if (JSON.stringify(user) == JSON.stringify(orderDetailForm)) {
                alert("请修改后再保存!");
                return;
            }
            if (checkForm() == false) {
                alert("该时间已被预约!");
                return;
            }
            var nums = jQuery("input[name='goodsDetailList[][num]']");
            for (var i = 0; i < nums.length; i++) {
                var _this = jQuery(nums[0]);
                if (_this.val() == "" || _this.val() == undefined) {
                    alert("请输入预约的商品数量!");
                    return;
                }
            }
            jQuery.ajax({
                url : _path + 'orderDetailAction/modify.do',
                type : "post",
                data :JSON.stringify(user),
                async : false,
               contentType: "application/json",
                success : function(data) {
                    if (data != null && data != "fail") {
                        alert(data);
                        if(data.indexOf("成功") != -1){
                            jQuery("#user")[0].reset();
                            setTimeout("iFClose();", 1000);
                        }
                    } else if (data == "fail") {
                        alert("修改失败！请重试！");
                    }else{
                        alert(data);
                    }
                },
                fail:function(data){
                    alert("3_fail");
                }

            });
            jQuery(parent.window.document).find('#searchResult').click();
        }

    });

});
seajs.use('common/common.form.js', function (a) {
});

function checkDate1(){
    var meetDate = jQuery("#meetDate").val();
    var meetStartTime = jQuery("#meetStartTime").val();
    var meetEndTime = jQuery("#meetEndTime").val();
    var meetRoomID = jQuery("#meetRoomID").val();
    if(!meetDate || !meetStartTime || !meetEndTime || !meetRoomID){
        return true;
    }
    var result = false;
    var d = new Date();
    var year = d.getFullYear();       //年
    var month = d.getMonth() + 1;     //月
    var day = d.getDate();            //日

    var hh = d.getHours();            //时
    var mm = d.getMinutes();          //分
    var curdate = year + '-' + addzero(month) + '-' + addzero(day);
    var curhhmm = addzero(hh) + ':' + addzero(mm);
    if (meetDate.toString() == curdate.toString()) {
        if (meetStartTime.toString() < curhhmm.toString()) {
            //alert(meetStartTime+'--'+curhhmm);
            result = false;
        } else {
            result = true;
        }
    } else if (meetDate < curdate) {
        result = false;
    } else {
        result = true;
    }
    return result;
}
function checkDate2(){
    var meetStartTime = jQuery("#meetStartTime").val();
    var meetEndTime = jQuery("#meetEndTime").val();
    var result = false;
    if (meetStartTime.toString() < meetEndTime.toString()) {
        result = true;
    }

    return result;
}
function checkForm() {
    var result = false;
    var meetDate = jQuery("#meetDate").val();
    var meetStartTime = jQuery("#meetStartTime").val();
    var meetEndTime = jQuery("#meetEndTime").val();
    var meetRoomID = jQuery("#meetRoomID").val();
    var glideNo = jQuery("#glideNo").val();

    // 设置同步
    jQuery.ajaxSetup({async: false});
    jQuery.ajax({
        url: _path + 'orderDetailAction/isUnique.do',
        data: {
            'meetDate': meetDate,
            'meetStartTime': meetStartTime,
            'meetEndTime': meetEndTime,
            'meetRoomID': meetRoomID,
            'glideNo': glideNo
        },
        type: "get",
        contentType: "application/json",
        success: function (data) {
            if (data != "exception") {
                result = (data=="true"?true:false);
            }
            else {
                data = "会议预约验证合法性异常，请重试！";
            }
        }
    });
    //       }
    // 恢复异步
    jQuery.ajaxSetup({async: true});
    return result
}

function addzero(v) {
    if (v < 10) return '0' + v;
    return v.toString();
}