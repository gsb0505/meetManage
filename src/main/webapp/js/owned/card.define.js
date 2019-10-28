define(function(require, exports, module) {

	//读卡(已激活)
	exports.cardOper = function() {

			try
			{ 
				CardSellVar = readCardInfo();			
				cardSign(CardSellVar.paySign,CardSellVar.enableTime,CardSellVar.validEndTime);
				jQuery("#cardNo").data("val",CardSellVar.cardApp);
				return true;
			}
			catch(err)
			{
				jQuery("#cardNo").removeData("val");
				alert(_ie_alert_txt + err);
				return false;
     		}
			
			return false;
		
    };
    //读卡
    exports.cardOper2 = function() {
    	
    	try
    	{ 
    		CardSellVar = readCardInfo();			
    		jQuery("#cardNo").data("val",CardSellVar.cardApp);
    		return true;
    	}
    	catch(err)
    	{
    		jQuery("#cardNo").removeData("val");
    		alert(_ie_alert_txt + err);
    		return false;
    	}
    	
    	return false;
    	
    };
  
	
    exports.prompt=function(txt,dis) {
    	if(dis==true){
    		jQuery("#prompt").show();
    	}else{
    		jQuery("#prompt").hide();
    	}
    	jQuery("#prompt .content").html(txt);
	};
	
	//读终端号
	exports.readTerminalNo=function(){
		try
		{ 
			var result=getTerminal();
			console_log("终端号:"+result);
			jQuery("#terminalId").val(result);
			return true;
		}catch(err)
		{
			jQuery("#terminalId").val("");
			alert(err+",&nbsp;&nbsp;读取终端号失败!");
			return false;
 		}
		return false;
	}
	
	//对psam卡及终端判断
	exports.judgePSAMTerNo=function(success_function,fail_function){
		var ter = jQuery("#terminalId").val();
		jQuery.ajax({
			url:_path +"terminal/queryTerNOPSAM.do",
			type : "post",
			data : {no:ter,psam:PSAMVar.psamNo},
			async : false,
			dataType: "text",
			beforeSend: function(xmlHttp){
			    xmlHttp.setRequestHeader("If-Modified-Since","0"); 
		        xmlHttp.setRequestHeader("Cache-Control","no-cache");
			},
			success : function(data) {
				if(data == "true"){
					success_function;
				}else{
					fail_function(data);
				}
			}
    	});
	}
	
    
});

