/**成员变量**/
var PSAMVar={
	"psamNo":"",//终端编号
}

	var result; //主要用于返回值
	var lSnr; //本用于取序列号，但在javascript只是当成dc_card函数的一个临时变量
	var rlen; //用于取一些返回值长度，但在javascript只是当成dc_card函数的一个临时变量

	var resetbuff = ""; //返回值

//var log=new Logger("xjPsamObj");

 
//psam配置
function initPsamDevice() {
			
	result= rd.dc_init(100, 115200);

	if(result <= 0)
	{
		msg="设备初使化异常:"+result;
		
	//	log.error(msg);
		throw new Error(msg);
	}
				msg="dc init ok</br>";
		//	log.debug(msg);
		result = rd.dc_setcpu(13);//设置要操作的SAM卡座
				if(result < 0)
				{
					msg="设置要操作的SAM卡座，错误代码："+result;
					uninitDevice();
				//	log.error(msg);
					throw new Error(msg);
				}
//	log.debug("dc dc_reset ok</br>");


				result = rd.dc_setcpupara(13,0,20);//设置PSam卡参数
				if(result < 0)
				{
					msg="设置PSam卡参数，错误代码："+result+"！";
					uninitDevice();
				//	log.error(msg);
					throw new Error(msg);
				}
				
			//		log.debug("dc dc_config_card ok</br>");
				result = rd.dc_cpureset(rlen);//Psam卡上电复位
				if(result < 0)
				{
					msg="Psam卡上电复位，错误代码："+result+"！";
					uninitDevice();
					log.error(msg);
					throw new Error(msg);
				}	
				return "9000";
}

	//初始化psam设备
function readPSAM() {

	result = initPsamDevice();
	if (result=="9000")
	{
		//PSAM选择MF
		rd.put_bstrSBuffer_asc = "00A40000023F00";//传送需要发送的指令数据
		result = rd.dc_cpuapdu (7,rlen);//发送指令
		//alert("PSAM选择MF"+result);
		if(result < 0)
		{
			msg = "发送指令失败，错误代码："+result;
		//	log.debug(msg);
			uninitDevice();
			throw new Error(msg);
		}	
		resetbuff = rd.get_bstrRBuffer_asc ;
		//alert("PSAM选择MF"+resetbuff);
		msg = "选择PSAM MF文件成功，</br>值为 ："+ resetbuff+"</br>";

		//log.debug(msg);
		//PSAM读0015文件

		rd.put_bstrSBuffer_asc = "00B095000E";//传送需要发送的指令数据
		result = rd.dc_cpuapdu (5,rlen);//发送指令
		if(result < 0)
		{
			msg = "发送PSAM读0015文件指令失败，错误代码："+result;
		//	log.debug(msg);
			uninitDevice();
			throw new Error(msg);
		}	

		resetbuff = rd.get_bstrRBuffer_asc ;
		
		msg = "PSAM读0015文件成功，</br>值为 ："+ resetbuff+"</br>";
//PSAM读0016文件
		//alert(msg);
		rd.put_bstrSBuffer_asc = "00B0960006";//传送需要发送的指令数据
		result = rd.dc_cpuapdu (5,rlen);//发送指令
		if(result < 0)
		{
			msg = "发送指令失败，错误代码："+result;
		//	log.debug(msg);
			uninitDevice();
			throw new Error(msg);
		}	
		resetbuff = rd.get_bstrRBuffer_asc ;
		msg = "选择PSAM读0016文件成功，</br>值为 ："+ resetbuff+"</br>";
		returnstr= resetbuff.substr(resetbuff.length-4,4);
		if (returnstr=="9000"){
		PSAMVar.psamNo = resetbuff.substr(0,resetbuff.length-4);//发卡方代码
		}
	//lert(PSAMVar.psamNo);
		//log.debug(msg);

		//PSAM选择ADF文件

		rd.put_bstrSBuffer_asc = "00A4040006BDA8C9E8B2BF";//传送需要发送的指令数据
		result = rd.dc_cpuapdu (11,rlen);//发送指令
		if(result < 0)
		{
			msg = "发送PSAM选择ADF文件指令失败，错误代码："+result;
		//	log.debug(msg);
			uninitDevice();
			throw new Error(msg);
		}	
		resetbuff = rd.get_bstrRBuffer_asc ;
		msg = "PSAM选择ADF文件成功，</br>值为 ："+ resetbuff+"</br>";
	//	log.debug(msg);

	//PSAM选择0017文件

		rd.put_bstrSBuffer_asc = "00B0970001";//传送需要发送的指令数据
		result = rd.dc_cpuapdu (5,rlen);//发送指令
		if(result < 0)
		{
			msg = "发送PSAM选择0017文件指令失败，错误代码："+result;
		//	log.debug(msg);
			uninitDevice();
			throw new Error(msg);
		}	
		resetbuff = rd.get_bstrRBuffer_asc ;
		msg = "PSAM选择0017文件成功，</br>值为 ："+ resetbuff+"</br>";
		PSAMVar.tradeKey = resetbuff;
	//	alert(PSAMVar.tradeKey);
		//log.debug(msg);
	}
  //  uninitDevice();
	return PSAMVar;
}
