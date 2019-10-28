jQuery().ready(function() {

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
				meetNoticeName : {
					required : true,
					maxlength:20
				},meetNoticeContent:{
					required : true,
					maxlength:200
				},meetRoomID:{
					required : true
				}
			},
			submitHandler : function(form) {
				
				jQuery("#meetNoticeName").val(trim(jQuery("#meetNoticeName").val())); //去空格
				jQuery("#meetNoticeContent").val(trim(jQuery("#meetNoticeContent").val())); //去空格
				var user = jQuery("#user").serialize();
				jQuery.ajax({
					url : _path + 'meetNotice/add.do',
					type : "post",
					data : user,
					async : false,
					success : function(data) {
						if (data == "success") {
							alert("添加成功！");
							setTimeout("iFClose();", 1000);
						} else if (data == "fail") {
							alert("添加失败！请重试！");
						} else if (data == "exsit") {
							alert("该会议室已经存在!");
						} else{
							alert(data);
						}

					}
				});

				jQuery(parent.window.document).find('#searchResult').click();
			}

		});
		
});
seajs.use('common/common.form.js', function(a) {});