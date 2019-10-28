define(function(require, exports, module) {
	module.exports = {
	    name: 'opera',
	    DTO:'',
	    doSomething: function(){
	    	console_log("efunctionxecute read_card."+arguments[0]);
	    	console_log("efunctionxecute read_carda."+arguments[1]);
	    	var read_card_define = require("owned/card.define");
	    	
	    	if(arguments!=null && arguments[0]==true)
	    	{
	    		 flag = read_card_define.cardOper2();//(已激活)
	    	}
	    	else{
	    		 flag = read_card_define.cardOper();
	    	}
	    	if (flag == false)
	    		return null;
	    	if(!read_card_define.readTerminalNo())
	    		return false;
	    	
	    	var val = jQuery("#cardNo").data("val");
			if(val==null || val==""){
				return null;
			}
			jQuery("#cardNo_2").val(val);
			jQuery("#cardNo").val(val);
			
			read_card_define.judgePSAMTerNo(success_function(arguments[1]),fail_function);
	    	
	    }
	  };
	
	function subButon(but,dis){
	   if(dis==false){
		   jQuery("#"+but).attr("class","tanchu_button04").attr('disabled','disabled');
	   }else{
		   jQuery("#"+but).attr("class","tanchu_button03").removeAttr('disabled');
	   }
 	}
	function setCARDDTO(exce,data){
		module.DTO = data;
		if(!data)alert("<span style='color:red;'>库存不存在该卡片!</span>");
		exce(data);
	}
	function success_function(exce){ 
    	jQuery.ajax({
			url:_path +"cardOperate/getCard.do",
			type : "post",
			data : {cardNo:CardSellVar.cardApp},
			async : false,
			dataType: "json",
			beforeSend: function(xmlHttp){
			    xmlHttp.setRequestHeader("If-Modified-Since","0"); 
		        xmlHttp.setRequestHeader("Cache-Control","no-cache");
		        subButon("read_card",false);
			},
			success : function(data) {
				setCARDDTO(exce,data);
			},complete:function(xhr,ts){
				subButon("read_card",true);
			},error: function(jqXHR, textStatus, errorMsg){
				if(textStatus == "error"){
					setTimeout(alert("<span style='color:red;'>未知异常，查询库存失败!</span>"),1000);
				}
			}
    	});
    	
	} 
	function fail_function(jptn){
		if(jptn){
			if(jptn == "fail"){
				setTimeout(alert("终端不对应,该PSAM卡号!"),1000);
				return false;
			}else if(jptn == "false"){
				setTimeout(alert("该web终端号不存在!"),1000);
				return false;
			}
		}
	}
});
