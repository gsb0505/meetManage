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
			var user = jQuery("#user").serialize();
			jQuery.ajax({
				url : _path + 'meetNotice/modify.do',
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
