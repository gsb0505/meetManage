/**成员变量**/
var CardVar={
	"hairpinCode":"",//发卡方代码
	"cardApp":"",//卡应用序列号
	"cityCode":"",//城市代码
	"tradeCode":"",//行业代码
	"cardSubType":"",//卡片子类型
	"enableTime":"",//应用开启时间
	"validEndTime":"",//应用有效结束时间
	"paySign":"",//激活标志
	"physicalCard":"", //物理卡号
	"deposit":"",//发售押金
	"saleDevice":"",//发售设备
	"checkTime":"",//年检有效日期
}

	var result; //主要用于返回值
	var lSnr; //本用于取序列号，但在javascript只是当成dc_card函数的一个临时变量
	var rlen; //用于取一些返回值长度，但在javascript只是当成dc_card函数的一个临时变量

	var resetbuff = ""; //返回值

//var log=new Logger("xjCardObj");

 
/**读卡器函数**/

//初始化设备
function initDevice(flag) {
			
	result= rd.dc_init(100, 115200);

	if(result <= 0)
	{
		msg="设备初使化异常:"+result;
		
	//	log.error(msg);
		throw new Error(msg);
	}
				msg="dc init ok</br>";
		//	log.debug(msg);

				result = rd.dc_reset(2);//复位射频
				if(result != 0)
				{
					msg="复位射频失败，错误代码："+result;
					uninitDevice();
				//	log.error(msg);
					throw new Error(msg);
				}
			//	log.debug("dc dc_reset ok</br>");


				result = rd.dc_config_card(65);//配置为A型卡片
				if(result != 0)
				{
					msg="配置为A型卡片，错误代码："+result+"！";
					uninitDevice();
				//	log.error(msg);
					throw new Error(msg);				
				}
				
			//		log.debug("dc dc_config_card ok</br>");
				result = rd.dc_card(0,rlen);//寻卡
				if(result != 0)
				{  msg="寻卡失败，错误代码："+result+"！";
					uninitDevice();
				//	log.error(msg);		
					throw new Error(msg);
					
				}	
					resetbuff = rd.get_bstrRBuffer_asc;
					
					CardVar.physicalCard = getPcardNo(resetbuff);
			//	log.debug("dc dc_card ok</br>");

				result =rd. dc_pro_reset(rlen);//复位卡片
				if(result != 0)
				{
					msg="复位卡片异常，错误代码："+result+"！";
					uninitDevice();
			//		log.error(msg);
				
					throw new Error(msg);
				}
				resetbuff = rd.get_bstrRBuffer_asc;//获取复位信息
		//		log.debug("复位成功" + resetbuff)+"</br>");
		if (flag==true){
		rd.dc_beep(10);
		}
				
	return "9000";
};
//初始化设备
function initDevice2(flag) {
			
	result= rd.dc_init(100, 115200);

	if(result <= 0)
	{
		msg="设备初使化异常:"+result;
		
	//	log.error(msg);
		throw new Error(msg);
	}
		msg="dc init ok</br>";
	
		if (flag==true){
		rd.dc_beep(10);
		}
				
	return "9000";
};


//反初始化读卡器
function uninitDevice() {

    result = rd.dc_exit();

	return result;
};


//读卡
function readCard() {

		//选择用户ADF1文件
		resetbuff = sendCommandlink("00A4040009A00000000386980701",true,false);
		//alert(resetbuff);
		//log.debug(msg);
		//用户0015文件
		resetbuff = sendCommandlink("00B0950028",true,true);
		if (resetbuff ==""){
			throw new Error("该卡为未发行的空白卡");
		}
		//alert(resetbuff);
			//	log.debug(msg);

		CardVar.hairpinCode = resetbuff.substr(0,4);//发卡方代码
		CardVar.cityCode = resetbuff.substr(4,4);//城市代码
		CardVar.tradeCode = resetbuff.substr(8,4);//行业代码
		CardVar.cardSubType = resetbuff.substr(12,4);//卡片子类型
		CardVar.cardApp =  resetbuff.substr(24,16);//卡应用序列号
		
		CardVar.enableTime = resetbuff.substr(40,8);//应用开启时间
		CardVar.validEndTime =  resetbuff.substr(48,8);//应用有效日期
		CardVar.paySign =  resetbuff.substr(56,2);//激活标志
		CardVar.deposit =  resetbuff.substr(58,2);//发售押金
		CardVar.saleDevice =  resetbuff.substr(60,12);//发售设备
		CardVar.checkTime =  resetbuff.substr(72,8);//年检有效日期
		
	
	return CardVar;
}


//获取当前时间，格式YYYYMMDD
    function getNowFormatDate() {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year.toString() + month.toString()  + strDate.toString();
        return currentdate;
    }

	
	//备注中设置设备终端号
function setTerminal(TerminalID) {
	result = initDevice2(true);
	console_log("setTerminal="+TerminalID);
	if (result=="9000")
	{
		rd.put_bstrSBuffer_asc =TerminalID; //传送需要发送的指令数据
		result = rd.dc_swr_eeprom(0,4);//发送指令
		if(result != 0)
		{
			msg = "发送指令失败，错误代码："+result;
		//	log.debug(msg);
			uninitDevice();
			throw new Error(msg);
		}

		uninitDevice();
		return true;
	}else{
		  uninitDevice();
	}
	return false;
}


//备注中获取设备终端号
function getTerminal() {
	var resetbuff ="";
	result = initDevice2(true);
	if (result=="9000")
	{
		result = rd.dc_srd_eeprom(0,4);//发送指令
		if(result != 0)
		{
			msg = "发送指令失败，错误代码："+result;
		//	log.debug(msg);
			uninitDevice();
			throw new Error(msg);
		}	
		resetbuff = rd.get_bstrRBuffer_asc ;
	}
		uninitDevice();
		return resetbuff;
}

//随机数
function getRandomNum() {
	resetbuff = sendCommandlink("0084000004",true,true);
	return resetbuff;
}

// 发送指令
function sendCommandlink(putStr,isGetStr,isRcode){
	resetbuff = "";
	len =Math.ceil(putStr.length/2)
	rd.put_bstrSBuffer_asc = putStr;
	console_log(putStr);
	//alert(putStr+"----"+len);
	result = rd.dc_pro_commandlink (len,rlen, 10,56);//发送指令
	if(result != 0)
	{
		
		msg = "发送"+putStr+"指令失败，错误代码："+result;
		//	log.debug(msg);
		console_log(msg);
		uninitDevice();
		throw new Error(msg);
	}	
	if (isGetStr==true){
		resetbuff = rd.get_bstrRBuffer_asc ;
		//console_log("resetbuff="+resetbuff);
		msg = putStr+"返回值 ："+ resetbuff+"</br>";
		console_log(msg);
		if (isRcode==true){
			rCode = resetbuff.substr(resetbuff.length-4,4);
			//console_log("rCode="+rCode);
			//log.debug(msg);
			if (rCode!="9000")
			{
				msg = putStr+"指令返回码失败，错误代码："+rCode;
				uninitDevice();
				throw new Error(msg);
			}	
			resetbuff = resetbuff.substr(0,resetbuff.length-4);
		}
	}
	return resetbuff;
}

//售卡
//modiContent 修改内容
//MAC mac地址
function cardSellWrite(enableTime,validEndTime,checkTime,MAC,cardFlag,deposit,saleDevice) {
	var depositstr=deposit.toString();
	modiContent =enableTime + validEndTime+cardFlag+ padLeft(depositstr,2)+ saleDevice+checkTime;
	console_log("modiContent="+modiContent);
	modiContent = "04D6951418"+modiContent+spliceSpace(MAC,8,0);
	console_log("modiContent="+modiContent);
		//用户卡PIN校验
	resetbuff = sendCommandlink(modiContent,true,true);
	console_log("resetbuff="+resetbuff);
	if ((resetbuff=="9000") || (resetbuff=="")){
		return true;
	}else{
		return false;
	}


}
function spliceSpace(str,len,flag){
//alert("str="+str);
	if (str.length>len){
		if (flag==0){
			str = str.substr(0,len);
			}else{
			str = str.substr(str.length-len,len);
			}
	}
	//alert("str1="+str);
	spaceStr = "";
	for (var i = str.length;i<len;i++){
		spaceStr =spaceStr + "0";
	}
	return spaceStr+str;
}

function padLeft(str,lenght){ 
	if(str.length >= lenght) {
		
	return str; 
	}
	else 
	{
		return padLeft("0" +str,lenght); 
	}
} 



function spliceSpace1(str,strlen,len){
	console_log("spliceSpace1 str ="+str);
		console_log("spliceSpace1 len ="+strlen);
		spaceStr = "";
		for (var i = strlen;i<len;i++){
			spaceStr =spaceStr + "0";
			
		}
		console_log("spliceSpace1 spaceStr ="+spaceStr);
		return spaceStr+str;
	}

function getPcardNo(str){
	pCardNoStr = "";
	var len = str.length/2;
	var lenstr = new Array();
	for (var i = 0;i<len;i++){
		lenstr[i]= str.substr(i*2,2);
	}
	for (var j=len-1 ;j>=0;j--){
		pCardNoStr = pCardNoStr+lenstr[j];
	}
	return pCardNoStr;
}

//个人信息修改前读卡
function BefCardInfoModiInfo() {
		CardVar = readCard();
		resetbuff = sendCommandlink("00B0960029",true,false);
		console_log("00B0960029--:"+resetbuff);
	return CardVar;
}

//个人信息修改
function writeModiCardInfo(IDCard,identityFlag,username,identityNo,certificateType,MAC) {
	modiContent =spliceSpace(IDCard,2,0)+spliceSpace(identityFlag,2,0)+spliceSpace(username,40,0)+ spliceSpace(identityNo,36,0)+spliceSpace(certificateType,2,0);
	console_log(modiContent);	
	modiContent = "04D696002D"+modiContent+spliceSpace(MAC,8,0);
	console_log(modiContent);
		//用户卡PIN校验
	resetbuff = sendCommandlink(modiContent,true,true);
	//alert("resetbuff="+resetbuff);
	if ((resetbuff=="9000") || (resetbuff=="")){
		return true;
	}else{
		return false;
	}
}

