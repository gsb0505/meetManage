jQuery().ready(function() {
    //商品项添加删除
    jQuery("#addProduct").click(addProFuntion);
    jQuery(".delProduct").live("click",function(){
        var parent=jQuery(this).parents("tr");
        var clas=parent.attr("class");
    	if(clas == "tr-pro0" || clas=== "tr-pro0"){
		}else{
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



	/**
	**	个位数加零
	**/
	function addzero(v) {
		if (v < 10) return '0' + v;
		return v.toString();
	}

    jQuery.validator.addMethod("isUnique", function (value, element) {
        var result = false;
        result = checkForm();
        return this.optional(element) || (result);

    }, "该时间已被预约!");



    // jQuery.validator.addMethod("checkMeetDate", function(value, element) {
    //     var result=false;
    //     var meetStartTime = jQuery("#meetStartTime").val();
    //     var meetEndTime = jQuery("#meetEndTime").val();
    //     if(!value){
    //         return true;
    //     }
    //
    //     var d = new Date();
    //     var year = d.getFullYear();       //年
    //     var month = d.getMonth() + 1;     //月
    //     var day = d.getDate();            //日
    //
    //     var hh = d.getHours();            //时
    //     var mm = d.getMinutes();          //分
    //     var curdate = year + '-' + addzero(month) + '-' + addzero(day);
    //     var curhhmm = addzero(hh) + ':' + addzero(mm);
		// if (value < curdate) {
    //         result = false;
    //     } else {
    //         result = true;
    //     }
    //     return this.optional(element) || (result);
    //
    // }, "请选择大于当前的日期!");
    // jQuery.validator.addMethod("checkMeetStartTime", function(value, element) {
    //     var result=false;
    //     var meetEndTime = jQuery("#meetEndTime").val();
    //     if(!value){
    //         return true;
    //     }
    //
    //     var d = new Date();
    //     var year = d.getFullYear();       //年
    //     var month = d.getMonth() + 1;     //月
    //     var day = d.getDate();            //日
    //
    //     var hh = d.getHours();            //时
    //     var mm = d.getMinutes();          //分
    //     var curdate = year + '-' + addzero(month) + '-' + addzero(day);
    //     var curhhmm = addzero(hh) + ':' + addzero(mm);
		// if (value.toString() < curhhmm.toString()) {
		// 	result = false;
		// } else {
		// 	result = true;
		// }
    //     return this.optional(element) || (result);
    //
    // }, "请选择大于当前的时间!");
		
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
				meetName : {
					required : true,
					maxlength:20
				},
				meetDate:{
					required : true,
					isUnique : true,
				},
				meetStartTime:{
					required : true,
					isUnique : true,
				},meetEndTime:{
					required : true,
					isUnique : true
				},meetRoomID:{
					required : true,
					isUnique : true
				}
			},
			submitHandler : function(form) {
				jQuery("#meetName").val(trim(jQuery("#meetName").val())); //去空格
				var user = jQuery("#user").serializeJSON();
				if(checkForm() == false){
					alert("该时间已被预约!");
					return;
				}
				var nums = jQuery("input[name='goodsDetailList[][num]']");
				for(var i=0;i< nums.length;i++){
					var _this= jQuery(nums[0]);
					if(_this.val() == "" || _this.val() == undefined){
                        alert("请输入预约的商品数量!");
                        return;
					}
				}
				jQuery.ajax({
					url : _path + 'orderDetailAction/add.do',
					type : "post",
					data :JSON.stringify(user),
					async : false,
                    contentType: "application/json",
					success : function(data) {
						if (data == "success") {
							alert("添加成功！");

                            jQuery("#user")[0].reset();
                            setTimeout("iFClose();", 1000);
						} else if (data == "fail") {
							alert("添加失败！请重试！");
						} else if (data == "exsit") {
							alert("该会议室已经存在!");
						} else{
							alert(data);
						}
					},
					fail:function(data){
						alert("3_fail");
					}

				});

			}

		});
		
});
seajs.use('common/common.form.js', function(a) {});

function checkForm(){
    var result = false;
    var meetDate = jQuery("#meetDate").val();
    var meetStartTime = jQuery("#meetStartTime").val();
    var meetEndTime = jQuery("#meetEndTime").val();
    var meetRoomID = jQuery("#meetRoomID").val();
    if(!meetDate || !meetStartTime || !meetEndTime || !meetRoomID){
        return true;
    }

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
    if (result == true) {
        // 设置同步
        jQuery.ajaxSetup({ async: false });
        var param= {
            'meetDate': meetDate,
            'meetStartTime': meetStartTime,
            'meetEndTime': meetEndTime,
            'meetRoomID': meetRoomID
        }
        console.log("param=>"+JSON.stringify(param));
        jQuery.ajax({
            url: _path + 'orderDetailAction/isUnique.do',
            data: param,
            type: "get",
            contentType: "application/json",
            success: function (data) {
                if (data != "exception") {
                    result = (data == "true" ? true : false);
                } else {
                    data = "会议预约验证合法性异常，请重试！";
                }
            }
        });
        // 恢复异步
        jQuery.ajaxSetup({ async: true });
    }
    return  result;
}