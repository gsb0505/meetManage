/**成员变量**/
var CardSellVar={
	"hairpinCode":"",//发卡方代码
	"cardApp":"",//卡应用序列号
	"cityCode":"",//城市代码
	"tradeCode":"",//行业代码
	"cardSubType":"",//卡片子类型
	"enableTime":"",//应用开启时间
	"validEndTime":"",//应用有效结束时间
	"paySign":"",//激活标志
	"physicalCard":"", //物理卡号
	"checkTime":"", //年检有效日期
	"deposit":"",//发售押金
	"saleDevice":"",//发售设备
	"psamNo":"",//终端编号
	"tradeKey":"", //消费密钥
	"randomNum":"",//随机数
}
function readCardInfo(){
	result = initDevice(true);
	if (result=="9000")
	{
		CardVar = readCard();
		CardSellVar.hairpinCode= CardVar.hairpinCode;
		CardSellVar.cardApp= CardVar.cardApp;
		CardSellVar.cityCode= CardVar.cityCode;
		CardSellVar.tradeCode= CardVar.tradeCode;
		CardSellVar.cardSubType= CardVar.cardSubType;
		CardSellVar.validEndTime= CardVar.validEndTime;
		CardSellVar.checkTime= CardVar.checkTime;
		CardSellVar.paySign= CardVar.paySign;
		CardSellVar.physicalCard= CardVar.physicalCard;
		CardSellVar.enableTime= CardVar.enableTime;
		CardSellVar.deposit= CardVar.deposit;
		CardSellVar.saleDevice= CardVar.saleDevice;
		CardSellVar.randomNum= getRandomNum();
		uninitDevice();
	}
		PSAMVar =readPSAM();
		CardSellVar.psamNo= PSAMVar.psamNo;
		CardSellVar.tradeKey= PSAMVar.tradeKey;

	
	return CardSellVar;
}
//读售卡信息
function readCardSell() {
	CardVar = readCard();
	CardSellVar.hairpinCode= CardVar.hairpinCode;
	CardSellVar.cardApp= CardVar.cardApp;
	CardSellVar.cityCode= CardVar.cityCode;
	CardSellVar.tradeCode= CardVar.tradeCode;
	CardSellVar.cardSubType= CardVar.cardSubType;
	CardSellVar.validEndTime= CardVar.validEndTime;
	CardSellVar.checkTime= CardVar.checkTime;
	CardSellVar.paySign= CardVar.paySign;
	CardSellVar.physicalCard= CardVar.physicalCard;
	CardSellVar.enableTime= CardVar.enableTime;
	CardSellVar.deposit= CardVar.deposit;
	CardSellVar.saleDevice= CardVar.saleDevice;
	return CardSellVar;
}

function tradeWriteCard(){
	CardSellVar =readCardSell();
	writeCard(CardSellVar.tradeKey,CardSellVar.psamNo);
}
//writeCardSell售卡写卡
//startDate 启用时间
//endDate 有效时间
//yearDate 年检时间
//MAC MAC售卡申请返回
//cardFlag 卡类标识
function writeCardSell(enableTime,validEndTime,checkTime,MAC,cardFlag,deposit,saleDevice){
	console_log(enableTime+'--'+validEndTime+'--'+checkTime+'--mac='+MAC+'--'+cardFlag+'---'+deposit+'--'+saleDevice);
	return cardSellWrite(enableTime,validEndTime,checkTime,MAC,cardFlag,deposit,saleDevice);
}

function cardSign(paySign,startDate,endDate){
		if (paySign!="01")
		{
			throw new Error ("卡片未缴活！");
		}
	
	curDate = getNowFormatDate();
	//0：字符串相等

	//-1：字符串STRING_A<STRING_B.

	//1：字符串STRING_A>STRING_B
	var number1 = startDate.localeCompare(curDate);
				var number2 = endDate.localeCompare(curDate);
				if ((number1 == 1) || (number2 == -1)) {
					throw new Error ("卡片有效期异常");
				}
}
