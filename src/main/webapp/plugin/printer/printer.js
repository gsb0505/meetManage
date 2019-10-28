/***
 * 网打 小票封装
 * @author zlm
 */

(function($) {
	
$.fn.plugprint = function(options) {    
    // build main options before element iteration    
    var opts = $.extend({}, $.fn.plugprint.defaults, options);    
    // iterate and reformat each matched element    
    return this.each(function() {    
      $this = $(this);    
      // build element specific options    
      var printObj = $.meta ? $.extend({}, opts, $this.data()) : opts;    
       
	  $.fn.plugprint.Invoke(printObj);
    });    
 };  
  
  function debug($obj) {    
    if (window.console && window.console.log)    
      window.console.log($obj);    
  };    

  $.fn.plugprint.Invoke = function(printObj) {    
    var info = "";
	var row_size = printObj.rowSize;
	var arr_info;
	var maxLength = 0;

	//action
	var dif = 0 , dif2 = 0, difv = "";
	var difH = 0 ;//对齐标记
	
	if(!printObj || !printObj.printOcx){
		alert("打印插件未载入，请刷新页面后重试!");
		return;
	}

	if(printObj.model == 2){
		arr_info = $.fn.plugprint.model.iniInfo2();
	}else if(printObj.model == 3){
		arr_info = $.fn.plugprint.model.iniInfo3();
	}else if(printObj.model == 4){
		arr_info = $.fn.plugprint.model.iniInfo4();
	}else if(printObj.model == 5){
		arr_info = $.fn.plugprint.model.iniInfo5();
	}else{
		arr_info = $.fn.plugprint.model.iniInfo();
	}
	
	
	for(var int = 0,len = arr_info.length;int < len ; int++){
		//repace parameter
		arr_info[int] = repPara(arr_info[int],printObj.parintPara);
		
		if(printObj.center && GetLength(arr_info[int]) < row_size){
			dif = row_size - GetLength(arr_info[int]);
			if(dif>2){
				dif2 = Math.ceil(Math.ceil(dif)/2);
				difv = ""; 
				for(var j = 0 ; j < dif2 ; j++){
					difv+=" ";
				}
				if(int == 0){
					difH = difv;
				}
				if(printObj.leftAlignIndex){
					if(printObj.leftAlignIndex.indexOf(int.toString()) != -1){
						arr_info[int]=difH + arr_info[int];
					}else{
						arr_info[int]=difv + arr_info[int];
					}
				}
				
			}
		}
		
	}
	

	for(var int = 0,len = arr_info.length;int < len ; int++){
		info += arr_info[int];
		maxLength+=GetLength(arr_info[int]);
	}
	
	debug(info);
	
	//排队执行
	if(printObj.printOcx && pirntStatus == 0){
		pirntStatus=2;
		/*pirnt*/
		printAction(info,maxLength,printObj);
	}
	
  }; 
  
function repPara(arr_info,paras){
	var reg,info = arr_info;
	//debug(arr_info);
	for(var key in paras){  
		reg = new RegExp('\\${'+(key)+'\\}');
		info = arr_info.replace(reg, paras[key]); 
		if(info != arr_info){
			return info;
		}
	}  
	reg = /\${.+?}/;
	return arr_info.replace(reg, "0");
};


/**
	1:打印进行中
	0:未打印
	
	2:打印字符串
	3:走纸
	4:设置字符倍数
	5:切纸
*/
var pirntStatus = 0;

/**
	print main
*/
function printAction(info,maxLength,printObj){
	var result = -1;
	var fun_print = setInterval(function(){
		if(pirntStatus == 0){
			clearInterval(fun_print);
		}else if(pirntStatus == 2){
			printString(printObj,info,maxLength);
		}else if(pirntStatus == 3){
			FeedPaper(printObj);
		}else if(pirntStatus == 5){
			CutPaper(printObj);
		}else{
			clearInterval(fun_print);
		}
	},200);
	
};

//printString
function printString(obj,info,length){
	var result = -1;
	result = obj.printOcx.PrintString(obj.portNo,obj.BAUD,info,length);
	if(result==0){
		pirntStatus = 3;
		return result;
	}
	return result;
};

//FeedPaper
function FeedPaper(printObj){
	var result2 = -1;
	result2 = printObj.printOcx.FeedPaper(printObj.portNo,printObj.BAUD,10);		
	if(result2==0){
		pirntStatus = 5;
		return result2;
	}
	return result2;
};
//CutPaper
function CutPaper(printObj){
	var result3 = -1;
	result3 = printObj.printOcx.CutPaper(printObj.portNo,printObj.BAUD);	
	if(result3 == 0){
		pirntStatus = 0;
		return result3;
	}
	return result3;
};
//SetFontSize
function SetFontSize(printObj,xSize,ySize){
	var result3 = -1;
	result3 = printObj.printOcx.SetFontSize(printObj.portNo,printObj.BAUD,xSize,ySize);	
	if(result3 == 0){
		pirntStatus = 0;
		return result3;
	}
	return result3;
};

///获得字符串实际长度，中文2，英文1
///要获得长度的字符串
function GetLength(str) {
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
};
/**
 * 获取打印状态
 */
$.fn.plugprint.getStatus = function(){
	return pirntStatus;
};

 $.fn.plugprint.defaults = {    
    printOcx : null,
	portNo : 2,	//com口
	BAUD : 115200,//波特率
	strSize : 475-23, //info长250
	rowSize : 45,	//行长度
	leftAlignIndex : "2,3,4,5,6", //左对齐行索引
	center : true,		//行居中
	model: 1,	//打印模板
	parintPara:{
		glide:"201610270000001",
		trade:"园区卡充值",
		moeny:"100",
		result:"充值成功",
		data:"2016-10-27 13:22:00",
		phone:"0571-00000000"
	}
}; 
 
 $.fn.plugprint.model = {
		//print info
		 iniInfo:function(){
		 	var arr_info = new Array();
		 	arr_info[0]="益民大厦红山一卡通消费系统凭条\n";
		 	arr_info[1]="--------------------------------------\n\n";
		 	arr_info[2]="流水号：${glide}\n";
		 	arr_info[3]="卡号：${cardNo}\n";
		 	arr_info[4]="交易信息：${trade}\n";
		 	arr_info[5]="卡内余额：${moeny} 元\n";
		 	arr_info[6]="押金金额：${deposit} 元\n";
		 	arr_info[7]="实收金额：${fact} 元\n";
		 	arr_info[8]="交易结果：${result}\n";
		 	
		 	arr_info[9]="业务网点：${branchNo}\n";
		 	arr_info[10]="业务人员：${user}\n";
		 	arr_info[11]="交易时间：${data}\n\n";
		 	arr_info[12]="--------------------------------------\n";
		 	arr_info[13]="欢迎使用益民大厦红山一卡通消费系统\n";
		 	arr_info[14]="客服电话：${phone}\n";
		 	arr_info[15]="乌鲁木齐红山一卡通有限公司\n";
		 	arr_info[16]="新疆城科智能科技股份有限公司承建";
		 	
		 	return arr_info;
		 },iniInfo5:function(){
			 	var arr_info = new Array();
			 	arr_info[0]="益民大厦红山一卡通消费系统凭条\n";
			 	arr_info[1]="--------------------------------------\n\n";
			 	arr_info[2]="流水号：${glide}\n";
			 	arr_info[3]="卡号：${cardNo}\n";
			 	arr_info[4]="交易信息：${trade}\n";
			 	arr_info[5]="交易金额：${moeny} 元\n";
			 	arr_info[6]="交易结果：${result}\n";
			 	arr_info[7]="业务网点：${branchNo}\n";
			 	arr_info[8]="业务人员：${user}\n";
			 	arr_info[9]="交易时间：${data}\n\n";
			 	arr_info[10]="--------------------------------------\n";
			 	arr_info[11]="欢迎使用益民大厦红山一卡通消费系统\n";
			 	arr_info[12]="客服电话：${phone}\n";
			 	arr_info[13]="乌鲁木齐红山一卡通有限公司\n";
			 	arr_info[14]="新疆城科智能科技股份有限公司承建";
			 	
			 	return arr_info;
			 },iniInfo4:function(){
			 	var arr_info = new Array();
			 	arr_info[0]="益民大厦红山一卡通消费系统凭条\n";
			 	arr_info[1]="--------------------------------------\n\n";
			 	arr_info[2]="业务类型：普通钱包充值\n";
			 	arr_info[3]="卡号：${cardNo}\n";
			 	arr_info[4]="流水号：${glide}\n";
			 	arr_info[5]="充值前余额：${beforeCash} 元\n";
			 	arr_info[6]="交易金额：${moeny} 元\n";
			 	arr_info[7]="充值后余额：${afterCash} 元\n";
			 	arr_info[8]="交易结果：${result}\n";
			 	arr_info[9]="业务网点：${branchNo}\n";
			 	arr_info[10]="业务人员：${user}\n";
			 	arr_info[11]="交易时间：${data}\n\n";
			 	arr_info[12]="--------------------------------------\n";
			 	arr_info[13]="欢迎使用益民大厦红山一卡通消费系统\n";
			 	arr_info[14]="客服电话：${phone}\n";
			 	arr_info[15]="乌鲁木齐红山一卡通有限公司\n";
			 	arr_info[16]="新疆城科智能科技股份有限公司承建";
			 	return arr_info;
		 },
		  iniInfo2:function(){
		 	var arr_info = new Array();
		 	arr_info[0]="		红山一卡通业务回执		 \n\n";
		 	arr_info[1]="业务类型：${tradeType}\n";
		 	arr_info[2]="结算日期：${tdate}\n\n";
		 	
		 	arr_info[3]="临时卡售卡笔数：${count} 张\n";
		 	arr_info[4]="临时卡售卡金额：${moeny} 元\n";
		 	arr_info[5]="充值笔数：${renum}\n";
		 	arr_info[6]="充值金额：${remoney}\n";
		 	arr_info[7]="退卡笔数：${rcount} 元\n";
		 	arr_info[8]="退卡金额：${rmoney} 元\n\n";
		 	
		 	arr_info[9]="园区卡开卡笔数：${garoc}\n";
		 	arr_info[10]="园区卡开卡金额：${garom}\n";
		 	arr_info[11]="园区卡补卡数量：${ycount}\n";
		 	arr_info[12]="园区卡补卡金额：${ymoney}\n";
		 	arr_info[13]="园区卡退卡数量：${yrcount}\n";
		 	arr_info[14]="园区卡退卡金额：${yrmoney}\n\n";
		 	
		 	arr_info[15]="收入总金额：${sum}\n\n";
		 	
		 	arr_info[16]="业务网点：${branchNo}\n";
		 	//arr_info[15]="终端编号：${terNo}\n";
		 	arr_info[17]="业务人员：${user}\n";
		 	arr_info[18]="打印时间：${date}\n";
		 	arr_info[19]="特别说明：\n\n";
		 	return arr_info;
		 },
		 iniInfo3:function(){
		 	var arr_info = new Array();
		 	arr_info[0]="		红山一卡通业务回执		\n\n";
		 	arr_info[1]="业务类型：${tradeType}\n";
		 	arr_info[2]="日结流水号：${glide}\n";
		 	arr_info[3]="日结开始时间：${bdate}\n";
		 	arr_info[4]="日结结束时间：${edate}\n\n";
		 	
		 	arr_info[5]="临时卡售卡笔数：${anum}\n";
		 	arr_info[6]="临时卡售卡金额：${amoney}\n";
		 	arr_info[7]="充值笔数：${renum}\n";
		 	arr_info[8]="充值金额：${remoney}\n";
		 	arr_info[9]="退卡笔数：${rrenum}\n";
		 	arr_info[10]="退卡金额：${rremoney}\n\n";
		 	
		 	
		 	arr_info[11]="园区卡开卡笔数：${garoc}\n";
		 	arr_info[12]="园区卡开卡金额：${garom}\n";
		 	arr_info[13]="园区卡退卡数量：${prernum}\n";
		 	arr_info[14]="园区卡退卡金额：${prermoney}\n";
		 	arr_info[15]="园区卡补卡数量：${prenum}\n";
		 	arr_info[16]="园区卡补卡金额：${premoney}\n\n";
		 	
		 	arr_info[17]="收入总金额：${sum}\n";
		 	arr_info[18]="业务网点：${branchNo}\n";
		 	//arr_info[17]="终端编号：${terNo}\n";
		 	arr_info[19]="业务人员：${user}\n";
		 	arr_info[20]="打印时间：${date}\n";
		 	return arr_info;
		 }
 }

})(jQuery);
