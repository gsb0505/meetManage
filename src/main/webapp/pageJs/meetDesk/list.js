function turnPage(url){
		jQuery.ajax({
			url:url,
			type:"post",
			dataType:"text",
			success:function(data){
				var str=data;
				if(str.indexOf("5646546465465465465")>-1){
					window.location.href=_path;
					return ;
				}
				jQuery("#iframe").html(data);
				setTimeout( "if( jQuery.isFunction(jQuery.fn.hideLoading) ){  jQuery('#loading').hideLoading(); }", 800 );
			},beforeSend:function(XHR){
				if( jQuery.isFunction(jQuery.fn.showLoading) ){ jQuery('#loading').showLoading(); }
			},complete:function(XHR, TS){
				setTimeout( "if( jQuery.isFunction(jQuery.fn.hideLoading) ){  jQuery('#loading').hideLoading(); }", 800 );
			}
		});
	
	}