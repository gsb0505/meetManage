var alert1=window.alert;
var conLogDebug = true;
/**
 * ymPormpt式弹出
 * @param title
 * @param width
 * @param height
 * @param url
 */
function showWindow(title,width,height,url,handler){
	ymPrompt.win({message:url,
				  width:width,
				  height:height,
				  title:title,
				  maxBtn:true,
				  minBtn:true,
				  iframe:true,
				  showShadow:false,
				  useSlide:false,//淡入淡出
				  maskAlphaColor:"#FFFFFF",
				  maskAlpha:0.3,
				  handler:handler
			}
	);
}

function showSuccess(message){
	parent.window.ymPrompt.succeedInfo({
				title:"系统提示",
				message:message
			});
		jQuery(parent.window.document).find("#searchResult").click(); 
}



function showError(message){
	parent.window.ymPrompt.errorInfo({
				title:"系统提示",
				message:message
				
			});
}

function showErrorWindow(message){
	ymPrompt.errorInfo({
				title:"系统提示",
				message:message
				
			});
}

function showAlertWindow(message) {
	ymPrompt.alert({
		title:"系统提示",
		useSlide:true,
		maskAlphaColor:"#FFFFFF",
		maskAlpha:0.3,
		message:message,
		height:200,
	});
	
}


function alertConfirm(message,handler){
	 ymPrompt.confirmInfo({title:'系统提示',
			  message:message,
			  width:300,
			  height:200,
			  handler:handler
			  }); 
}

/**
 * 弹出ymPormpt窗口关闭
 */
function iFClose(){
	parent.window.ymPrompt.close();
}

/**
 * 在弹出iframe窗口中刷新父页面，并关闭窗口
 */
function refershParent(){
	jQuery(parent.window.document).find('#query').click();
	iFClose();
}

/**
 * 弹出警告框
 */
function showAlertDivLayer() {
	var argumentsArr = Array.prototype.slice.call(arguments);
	
	if(argumentsArr[0] == null) return;
	
	var clickFun = null;
	
	if (argumentsArr.length == 3){
		clickFun = argumentsArr[2]["clkFun"];
	}
	ymPrompt.alert({
		title:"系统提示",
		useSlide:true,
		maskAlphaColor:"#FFFFFF",
		maskAlpha:0.3,
		message:argumentsArr[0],
		/*width:10,*/
		height:200,
		//showMask:false,
		handler:clickFun
	});
	//setTimeout(function(){ymPrompt.doHandler();},3000);
}

window.alert = showAlertDivLayer;

function alert_auto(info){
	alert(info);
	setTimeout("refershParent();", 1000);
}


/**
 * 刷新并提交当前表单
 * 
 * @param url
 * 
 */
function subForm(url) {
		if (url != null && url != '') {
			document.forms[0].action = url;
		}
		document.forms[0].submit();
}

/**
 * 弹出询问框
 */
function showConfirmDivLayer() {
	
	var argumentsArr = Array.prototype.slice.call(arguments);
	var _fun = null;
	
	ymPrompt.confirmInfo({
			title:"系统提示",
			message:argumentsArr[0],
			width:300,
			height:200,
			maskAlphaColor:"#FFFFFF",
			maskAlpha:0.3,
			useSlide:true,
			handler:function(type){
				if (type=="ok"){
					_fun = argumentsArr[1]["okFun"];
				} else if (type == "cancel"){
					_fun = argumentsArr[1]["cancelFun"];
				}
				
				if (_fun != null){
					_fun();
				}
				
			}
		}
	);
}

/**
 * 去除字符串空格 lt 2011-3-7
 */
function trim(str) { 
	return str.replace(/(^\s*)|(\s*$)/g, "");   
}
/**
 * 验证网址格式是否正确
 * @param str_url
 * @returns {Boolean}
 */
function IsURL(str_url){
    var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
    + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
    + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
    + "|" // 允许IP和DOMAIN（域名）
    + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
    + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
    + "[a-z]{2,6})" // first level domain- .com or .museum
    + "(:[0-9]{1,4})?" // 端口- :80
    + "((/?)|" // a slash isn't required if there is no file name
    + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    var re=new RegExp(strRegex);
    //re.test()
    if (re.test(str_url)){
        return (true);
    }else{
        return (false);
    }
}

var inputListTyps = "hidden;checkbox;redio";
var inputLists = document.getElementsByTagName("input");

for (var int = 0; int < inputLists.length; int++) {
	if(inputListTyps.indexOf(inputLists[int].getAttribute("type"))!=-1){
		continue;
	}
	var content = inputLists[int].getAttribute("maxlength");
	if(content==null || content==''){
		inputLists[int].setAttribute("maxlength",50);
	}
}
	
function console_log(msg){
	if (conLogDebug && window["console"]){
		console.log(msg);
	}
}

/**
  * 在页面中任何嵌套层次的窗口中获取顶层窗口
  * @return 当前页面的顶层窗口对象
  */
function getTopWinow(){
	var p = window;
	while(p != p.parent){
		p = p.parent;
	}
	return p; 
}


if(typeof jQuery !='undefined'){
	
	/*var ajaxs = [];
	var oldAjax = jQuery.ajax;

	jQuery.ajax = function() {
	    var args = Array.prototype.slice.call(arguments);
	    var ajax = oldAjax.apply(this, args);
	    ajaxs.push(ajax);
	    return ajax;
	}

	function abortAll() {
		jQuery.each(ajaxs, function(i, ajax) {
	        ajax.abort();
	    });
	}*/
	
	/**
	 * 设置AJAX的全局默认选项
	 **/
	jQuery.ajaxSetup({
	    url: "" , // 默认URL
	    aysnc: false , // 默认同步加载
	    type: "POST" , // 默认使用POST方式
	    dataType:"text",	//设置默认服务器返回数据类型(json)；不设置'text',firefox上获取不到正确的返回结果
	    headers: { // 默认添加请求头
	        "Author": "CodePlayer" ,
	        "Powered-By": "CodePlayer"
	    },
	    timeout:15000,
	    error: function(jqXHR, textStatus, errorMsg){ // 出错时默认的处理函数
	        // jqXHR 是经过jQuery封装的XMLHttpRequest对象
	        // textStatus 可能为： null、"timeout"、"error"、"abort"或"parsererror"
	        // errorMsg 可能为： "Not Found"、"Internal Server Error"等

	        // 提示形如：发送AJAX请求到"/index.html"时出错[404]：Not Found
	        //alert( '发送请求' + '时出错[' + jqXHR.status + ']：<br/>' + "请与管理员联系！" ); 
			//alert("异常！！请联系管理员");

	    	if(textStatus=='timeout'){
	    		jQuery.ajaxSetup({ async: true  });
	    		alert("请求超时...请检查网络");
	    	}else{
	    		alert();
			}
	    },
	    beforeSend:function(XHR){
	    	//XHR.setRequestHeader("If-Modified-Since","0"); 
	    	XHR.setRequestHeader("Cache-Control","no-cache");
            jQuery("form").find("input[type='submit']").attr("disabled","disabled");

	    	return true;
	    },
	    complete: function (XHR, TS) {
            jQuery("form").find("input[type='submit']").removeAttr("disabled");
	    	XHR = null;
	    }
	});
	
	/**
	 * ajax请求请求完成结果function
	 */
	jQuery(document).ajaxComplete(function( event, xhr, settings ) {
		
		var sessionStatus = xhr.getResponseHeader('sessionstatus');
		
		if(sessionStatus == 'timeout') {
		     var top = getTopWinow();
		     var yes = window.confirm('由于您长时间没有操作, 页面已过期, 请重新登录.');
		     if (yes) {
		    	 console_log("go out.");
		    	 setCookie("user", 1, -1);
		         top.location.href = _path+'login.jsp';       
		     }
		 }
	});
	
	
	
	function setCookie(name, value, iDay){   
	    /* iDay 表示过期时间   
	    cookie中 = 号表示添加，不是赋值 */   
	    var oDate=new Date();   
	    oDate.setDate(oDate.getDate()+iDay);       
	    document.cookie=name+'='+value+';expires='+oDate;
	}
	
	/**
	//对Date的扩展，将 Date 转化为指定格式的String
	//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
	//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
	//例子： 
	//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
	//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
	**/ 
	Date.prototype.Format = function (fmt) {
		 var o = {
		     "M+": this.getMonth() + 1, //月份 
		     "d+": this.getDate(), //日 
		     "h+": this.getHours(), //小时 
		     "m+": this.getMinutes(), //分 
		     "s+": this.getSeconds(), //秒 
		     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		     "S": this.getMilliseconds() //毫秒 
		 };
		 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		 for (var k in o)
		 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		 return fmt;
	}
	
	// 如：{Name:'摘取天上星',position:'IT技术'}
	// ps:注意将同名的放在一个数组里
	function getFormJson(form) {
		var o = {};
		var a = jQuery(form).serializeArray();
		jQuery.each(a, function () {
			if (o[this.name] !== undefined) {
				/*if (!o[this.name].push) {
					o[this.name] = [o[this.name]];
				}*/
				if(Object.prototype.toString.call(this.value) != "[object String]")
					o[this.name]+=","+this.value.toString() || null;
					//o[this.name].push(this.value.toString() || '');
				else
					o[this.name]+=","+this.value || null;
					//o[this.name].push(this.value || '');
			} else {
				if(Object.prototype.toString.call(this.value) != "[object String]")
					o[this.name] = this.value.toString();
				else
					o[this.name] = this.value || null;
			}
		});
		if(arguments[1] == "false"){
			return o;
		}
		var b = jQuery(".multipleSelAll",form);
		jQuery.each(b ,function(){
			var $this = jQuery(this);
			if(!o[$this.attr("name")]){
				o[$this.attr("name")] = null;
			}
		});
		
		return o;
	}
	
}

var _ie_alert_txt = " <span style='color:red;'>提示:&nbsp;&nbsp;&nbsp;&nbsp;请使用IE浏览器进行操作!</span><br/>"; 

//商户下拉处理
//jQuery(function($) {
	var rolr = jQuery("#roleId").val();
	if(rolr && rolr == 4){
		jQuery.ajax({
			url:_path +"agentUserAction/get.do",
			type : "post",
			data : {userId:jQuery("#userId").val()},
			async : false,
			dataType:"json",
			success : function(data) {
				if(data && data != "exception"){
					jQuery("#agentId").find("option").each(function(index,element){
						if(element.value==data.agentId){
							jQuery(this).attr("selected","selected");
						}else{
							//jQuery(this).attr("disabled","disabled");
						}
					});
					jQuery("#agentId").attr("disabled","disabled");
					if(jQuery("#hagentId").length>0){
						jQuery("#hagentId").val(data.agentId);
					}else{
						jQuery("form#inputForm").append("<input type='hidden' name='agentName' id='hagentId' class='multipleSelAll' value='"+data.agentId+"' />");
					}
					if(jQuery("#hagentId1").length>0){
						jQuery("#hagentId1").val(data.agentId);
					}else{
						jQuery("form#inputForm").append("<input type='hidden' name='agentId' id='hagentId1' class='multipleSelAll' value='"+data.agentId+"' />");
					}
					if(jQuery("#hagentId2").length>0){
						jQuery("#hagentId2").val(data.agentId);
					}else{
						jQuery("form#inputForm").append("<input type='hidden' name='agentIds' id='hagentId2' class='multipleSelAll' value='"+data.agentId+"' />");
					}
				}
			}
		});
	}
	
//});