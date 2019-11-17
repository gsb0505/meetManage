jQuery().ready(function() {

		/**
		**	编号名称验证
		**/
		jQuery.validator.addMethod("isUnique", function(value, element) {
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
	    			}else{
	    				data="会议室名称验证合法性异常，请重试！";	
	    			}
	    		}
	    	});
	        // 恢复异步
	        jQuery.ajaxSetup({
	            async: true
	        });
    		return this.optional(element) || (result);
			
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
                goodsName : {
					required : true,
					maxlength:40
				},
                storeCode:{
					required : true
				},
                doublePrice:{
					required : true
				},count:{
					required : true
				}
			},
			submitHandler : function(form) {
				
				//jQuery("#goodsName").val(trim(jQuery("#goodsName").val())); //去空格
				var product = jQuery("#product").serialize();
				jQuery.ajax({
					url : _path + 'product/add.do',
					type : "post",
					data : product,
					async : false,
					success : function(data) {
						if (data == "success") {
							alert("添加成功！");
						//	setTimeout("iFClose();", 1000);
						} else if (data == "fail") {
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
seajs.use('common/common.form.js', function(a) {});