/*
 * jQuery Validation custom - v1.0.zlm
 * 
 */
/**
 * //验证电话号码
	//验证规则：区号+号码，区号以0开头，3位或4位
	//号码由7位或8位数字组成
	//区号与号码之间可以无连接符，也可以“-”连接
	//如01088888888,010-88888888,0955-7777777 
	//电话号码与手机号码同时验证
 */
jQuery.validator.addMethod("isPhoneCode", function(value, element) {
	var tel = /^0\d{2,3}-?\d{7,8}$/;
	
	var tel2=/(^(\d{3,4}-)?\d{7,8})$|([1][3-8]+\d{9})$/;
	return this.optional(element) || (tel2.test(value));
}, "请正确填写您的电话号码");

jQuery.validator.addMethod("isId", function(value, element) {       
	var cus = new customValidate();   
	return this.optional(element) || (cus.IdentityCodeValid(value));     
}, "身份证格式不正确");

jQuery.validator.addMethod("uId", function(value, element) {  
	var result=false;
	jQuery.ajax({
		url:_path +"cardOperate/getCardAccount.do",
		type : "post",
		data : {'no':value},
		async : false,
		success : function(data) {
			if(data!=null){
				result=(data=="true"?true:false);
			}
		}
	});
	return this.optional(element) || result;  
}, "该证件号码已开卡");

jQuery.validator.addMethod("usCode", function(value, element) {  
	var result=false;
	jQuery.ajaxSetup({ async: false  });
	jQuery.ajax({
		url:_path +"ConfigAction/findByCode.do",
		type : "post",
		data : {'code':value},
		async : false,
		success : function(data) {
			if(data!=null){
				result=(data=="true"?true:false);
			}
		}
	});
	jQuery.ajaxSetup({ async: true  });
	return this.optional(element) || result;  
}, "该code已存在");

jQuery.validator.addMethod("uPNo", function(value, element) {  
	var result=false;
	jQuery.ajaxSetup({ async: false  });
	jQuery.ajax({
		url:_path +"cardOperate/getCardAccPeopleNo.do",
		type : "post",
		data : {'no':value},
		async : false,
		success : function(data) {
			if(data!=null ){
				result=(data=="true"?true:false);
			}
		}
	});
	jQuery.ajaxSetup({ async: true  });
	return this.optional(element) || result;     
}, "该人员编号已存在");

jQuery.validator.addMethod("orgNo", function(value, element) {  
	var result=false;
	jQuery.ajaxSetup({ async: false  });
	jQuery.ajax({
		url:_path +"organizationAction/getOrgByNo.do",
		type : "post",
		data : {'agencyNo':value},
		async : false,
		success : function(data) {
			if(data!=null ){
				result=(data=="true"?true:false);
			}
		}
	});
	jQuery.ajaxSetup({ async: true  });
	return this.optional(element) || result;     
}, "该机关编号已存在");
jQuery.validator.addMethod("departmentNo", function(value, element) {  
	var result=false;
	jQuery.ajaxSetup({ async: false  });
	jQuery.ajax({
		url:_path +"departmentAction/getDepByNo.do",
		type : "post",
		data : {'departmentNo':value},
		async : false,
		success : function(data) {
			if(data!=null ){
				result=(data=="true"?true:false);
			}
		}
	});
	jQuery.ajaxSetup({ async: true  });
	return this.optional(element) || result;     
}, "该科室编号已存在");


jQuery.validator.addMethod("jgC", function(value, element) {  
	var result = false;
	var reg = /南字第(\d{8})号|北字第(\d{8})号|沈字第(\d{8})号|兰字第(\d{8})号|成字第(\d{8})号|济字第(\d{8})号|广字第(\d{8})号|海字第(\d{8})号|空字第(\d{8})号|参字第(\d{8})号|政字第(\d{8})号|后字第(\d{8})号|装字第(\d{8})号/; 
	value = value.replace(/(^\s*)|(\s*$)/g,"");  
	if (reg.test(value) === false) { 
		result = false; 
	}else{ 
		result = true; 
	}
    return this.optional(element) || result;
}, "军官证格式不正确!");

jQuery.validator.addMethod("hzC", function(value, element) {   
    var tel = /^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$/;
    return this.optional(element) || (tel.test(value));
}, "护照格式不正确!");

 		jQuery.validator.addMethod("isPhone", function(value, element) {   
//		 	验证规则：姑且把邮箱地址分成“第一部分@第二部分”这样
//		 	第一部分：由字母、数字、下划线、短线“-”、点号“.”组成，
//		 	第二部分：为一个域名，域名由字母、数字、短线“-”、域名后缀组成，
//		 	而域名后缀一般为.xxx或.xxx.xx，一区的域名后缀一般为2-4位，如cn,com,net，现在域名有的也会大于4位
		    var tel = /^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$/;
//		 	验证规则：区号+号码，区号以0开头，3位或4位
//		 	号码由7位或8位数字组成
//		 	区号与号码之间可以无连接符，也可以“-”连接
//		 	如01088888888,010-88888888,0955-7777777 
		    var re = /^0\d{2,3}-?\d{7,8}$/;
		    return this.optional(element) || (tel.test(value))||(re.test(value));
		}, "联系电话格式不正确!");
		
		jQuery.validator.addMethod("isEmail", function(value, element) { 
//		 	验证规则：姑且把邮箱地址分成“第一部分@第二部分”这样
//		 	第一部分：由字母、数字、下划线、短线“-”、点号“.”组成，
//		 	第二部分：为一个域名，域名由字母、数字、短线“-”、域名后缀组成，
//		 	而域名后缀一般为.xxx或.xxx.xx，一区的域名后缀一般为2-4位，如cn,com,net，现在域名有的也会大于4位
		    var tel = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
		    return this.optional(element) || (tel.test(value));
		}, "电子邮箱格式不正确!");
		
		jQuery.validator.addMethod("isCash", function(value, element) {   
		    var tel = /^[0-9]+$/;
		    return this.optional(element) || (tel.test(value));
		}, "金额只能是正整数!");
		
		jQuery.validator.addMethod("ispayPSW", function(value, element) {

			//电话号码与手机号码同时验证
			var tel2=/^[a-zA-Z]\w{5,17}$/;
			return this.optional(element) || (tel2.test(value));
		}, "登录密码以字母开头6~18位长度，只能包含字符、数字和下划线");
		
		
		jQuery.validator.addMethod("isuserId", function(value, element) {

			//电话号码与手机号码同时验证
			var tel2=/^[0-9a-zA-Z]{6,10}$/;
			return this.optional(element) || (tel2.test(value));
		}, "用户名由6~10位字母或数字组成");
		
/**
**	编号唯一验证
**/
jQuery.validator.addMethod("isUnique", function(value, element) {
	var result=false;
	
	// 设置同步
    jQuery.ajaxSetup({
        async: false
    });
    jQuery.ajax({
		url : _path + 'agentUserAction/isUnique.do',
		data : {'agentId':value},
		type : "post",
		success : function(data) {
			if(data!="exception"){
    			result=(data=="true"?true:false);
			}
		}
	});
    // 恢复异步
    jQuery.ajaxSetup({
        async: true
    });
	return this.optional(element) || (result);
	
}, "该商户编号被注册!");
		
/**
**	编号唯一验证
**/
jQuery.validator.addMethod("isUnique2", function(value, element) {
	var result=false;
	
	// 设置同步
    jQuery.ajaxSetup({
        async: false
    });
    jQuery.ajax({
    	url : _path + 'userAction/isUnique.do',
		data : {'userId':value},
		type : "post",
		success : function(data) {
			if(data!="exception"){
    			result=(data=="true"?true:false);
			}
		}
	});
    // 恢复异步
    jQuery.ajaxSetup({
        async: true
    });
	return this.optional(element) || (result);
	
}, "该用户名被注册!");



function customValidate(){
		this.name="zlm";
		var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
		var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
		this.getName = function() {
	      return this.name;
	   };
		
//身份证号合法性验证 
//支持15位和18位身份证号
//支持地址编码、出生日期、校验位验证
this.IdentityCodeValid=function(idCard) {
	idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
    if (idCard.length == 15) {   
        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");                // 得到身份证数组   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }  
};

/**  
 * 判断身份证号码为18位时最后的验证位是否正确  
 * @param a_idCard 身份证号码数组  
 * @return  
 */  
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0;                             // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];            // 加权求和   
    }   
    valCodePosition = sum % 11;                // 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
};
/**  
  * 验证18位数身份证号码中的生日是否是有效生日  
  * @param idCard 18位书身份证字符串  
  * @return  
  */  
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
};
  /**  
   * 验证15位数身份证号码中的生日是否是有效生日  
   * @param idCard15 15位书身份证字符串  
   * @return  
   */  
function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  };
//去掉字符串头尾空格   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
};

/**
 * luhmCheck验证
 * @param bankno
 * @returns {Boolean}
 */
this.luhmCheck=function(bankno){
	if (bankno.length < 16 || bankno.length > 19) {
		//$("#banknoInfo").html("银行卡号长度必须在16到19之间");
		return false;
	}
	var num = /^\d*$/;  //全数字
	if (!num.exec(bankno)) {
		//$("#banknoInfo").html("银行卡号必须全为数字");
		return false;
	}
	//开头6位
	var strBin="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";    
	if (strBin.indexOf(bankno.substring(0, 2))== -1) {
		//$("#banknoInfo").html("银行卡号开头6位不符合规范");
		return false;
	}
    var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）

    var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
    var newArr=new Array();
    for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
        newArr.push(first15Num.substr(i,1));
    }
    var arrJiShu=new Array();  //奇数位*2的积 <9
    var arrJiShu2=new Array(); //奇数位*2的积 >9
    
    var arrOuShu=new Array();  //偶数位数组
    for(var j=0;j<newArr.length;j++){
        if((j+1)%2==1){//奇数位
            if(parseInt(newArr[j])*2<9)
            arrJiShu.push(parseInt(newArr[j])*2);
            else
            arrJiShu2.push(parseInt(newArr[j])*2);
        }
        else //偶数位
        arrOuShu.push(newArr[j]);
    }
    
    var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
    var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
    for(var h=0;h<arrJiShu2.length;h++){
        jishu_child1.push(parseInt(arrJiShu2[h])%10);
        jishu_child2.push(parseInt(arrJiShu2[h])/10);
    }        
    
    var sumJiShu=0; //奇数位*2 < 9 的数组之和
    var sumOuShu=0; //偶数位数组之和
    var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
    var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
    var sumTotal=0;
    for(var m=0;m<arrJiShu.length;m++){
        sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
    }
    
    for(var n=0;n<arrOuShu.length;n++){
        sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
    }
    
    for(var p=0;p<jishu_child1.length;p++){
        sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
        sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
    }      
    //计算总和
    sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);
    
    //计算Luhm值
    var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
    var luhm= 10-k;
    
    if(lastNum==luhm){
        //$("#banknoInfo").html("Luhm验证通过");
        return true;
    }
    else{
        //$("#banknoInfo").html("银行卡号必须符合Luhm校验");
        return false;
    }        
};

/**
 * 验证手机号码
 * @returns {Boolean}
 */
this.checkMobile=function(value){
	var re='1[3|4|5|8][0-9]\d{4,8}';
	var pattern = new RegExp(re, "g");
    if(pattern.test(value) || value.length!=11){ 
        //alert("不是完整的11位手机号或者正确的手机号前七位"); 
        //document.mobileform.mobile.focus(); 
        return false; 
    } 
    return true;
};


}