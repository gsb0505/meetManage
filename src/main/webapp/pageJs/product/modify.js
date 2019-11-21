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
            if(jQuery("#photoFile").val()){
                formData.append('photoFile', jQuery("#photoFile")[0].files[0]);
            }
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
	

	
});

