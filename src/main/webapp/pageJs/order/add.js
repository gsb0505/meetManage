jQuery().ready(function() {

		/**
		**	编号名称验证
		**/
	  function addzero(v) {if (v < 10) return '0' + v;return v.toString();}
		jQuery.validator.addMethod("isUnique", function(value, element) {
			var result=false;
			var meetDate = jQuery("#meetDate").val();
			var meetStartTime =jQuery("#meetStartTime").val(); 
			var meetEndTime =jQuery("#meetEndTime").val(); 
			var meetRoomID=jQuery("#meetRoomID").val();
			
			var d = new Date();
			var year = d.getFullYear();       //年
	        var month = d.getMonth() + 1;     //月
	        var day = d.getDate();            //日
	       
	        var hh = d.getHours();            //时
	        var mm = d.getMinutes();          //分
	        var curdate=year+'-'+addzero(month)+'-'+addzero(day);
			var curhhmm=addzero(hh)+':'+addzero(mm);
			if (meetDate.toString() == curdate.toString()){
				if (meetStartTime.toString()< curhhmm.toString()){
					//alert(meetStartTime+'--'+curhhmm);
					result=false;	
				}else{
				result=true;	
				}
			}else if (meetDate < curdate){
				result=false;
			}else{			
				result=true;
			}
	        if (result==true){
			// 设置同步
	        jQuery.ajaxSetup({
	            async: false
	        });
	        jQuery.ajax({
	    		url : _path + 'orderDetailAction/isUnique.do',
	    		data : {'meetDate':meetDate,'meetStartTime':meetStartTime,'meetEndTime':meetEndTime,'meetRoomID':meetRoomID},
	    		type : "post",
	    		success : function(data) {
	    			if(data!="exception"){
		    			result=(data=="true"?true:false);
	    			}else{
	    				data="会议预约验证合法性异常，请重试！";	
	    			}
	    		}
	    	});
	        // 恢复异步
	        jQuery.ajaxSetup({
	            async: true
	        });
	        }
    		return this.optional(element) || (result);
			
		}, "该时间已被预约!");
		
//		jQuery.validator.addMethod("isUniquedate", function(value, element) {
//			var result=false;
//			var d = new Date();
//			var year = d.getFullYear();       //年
//	        var month = d.getMonth() + 1;     //月
//	        var day = d.getDate();            //日
//	       
//	        var hh = d.getHours();            //时
//	        var mm = d.getMinutes();          //分
//	        
//			var meetDate = jQuery("#meetDate").val();
//			var meetStartTime =jQuery("#meetStartTime").val(); 
//			var meetEndTime =jQuery("#meetEndTime").val(); 
//			var curdate=year+'-'+month+'-'+day;
//			var curhhmm=hh+':'+mm;
//			if (meetDate == curdate){
//				if (meetStartTime<curhhmm){
//					result=false;	
//				}
//			}else if (meetDate < curdate){
//				result=false;
//			}else{			
//				result=true;
//			}
//    		return this.optional(element) || (result);
//			
//		}, "该时间已过期!");
		
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
					isUnique : true
				},
				meetStartTime:{
					required : true,
					isUnique : true
					//isUniquedate:true
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
				var user = jQuery("#user").serialize();
				jQuery.ajax({
					url : _path + 'orderDetailAction/add.do',
					type : "post",
					data :user,
					async : false,
					success : function(data) {
						if (data == "success") {
							alert("添加成功！");
							
							jQuery("#meetName").val(""); //去空格
							jQuery("#meetDate").val(""); //去空格
							jQuery("#meetStartTime").val(""); //去空格
							jQuery("#meetEndTime").val(""); //去空格
							jQuery("#meetRoomID").val(""); //去空格
							jQuery("#emailNotification").val(""); //去空格
							jQuery("#specialdemand").val(""); //去空格
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