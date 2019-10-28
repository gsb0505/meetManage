
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
				  maxBtn:false,
				  minBtn:false,
				  iframe:true,
				  showShadow:false,
				  useSlide:false,//淡入淡出
				  maskAlphaColor:"#FFFFFF",
				  maskAlpha:0.3,
				  handler:handler
			}
	);
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
	$(parent.window.document).find('#searchResult').click();
	iFClose();
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
 * 去除字符串空格 lt 2011-3-7
 */
function trim(str) { 
	return str.replace(/(^\s*)|(\s*$)/g, "");   
}