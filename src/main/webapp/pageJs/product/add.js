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
				},
                photoFile:{
                    isPhoto:true
                }
			},
			submitHandler : function(form) {
				//jQuery("#goodsName").val(trim(jQuery("#goodsName").val())); //去空格
				//var product = jQuery("#product").serialize();

				var form = new FormData(jQuery("#product")[0]);
                if(jQuery("#photoFile").val()) {
                    form.append("photoFile", jQuery("#photoFile")[0].files[0]);
                }
				jQuery.ajax({
					url : _path + 'product/add.do',
					type : "post",
					data : form,
                    processData: false,
                    contentType: false,
					async : false,
					success : function(data) {
						if (data == "success") {
							alert("添加成功！");
							setTimeout("iFClose()", 1000);
						} else if (data == "fail" || data == "exception") {
							alert("添加失败！请重试！");
						} else if (data == "exsit") {
							alert("该商品已经存在!");
						} else{
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
		
});