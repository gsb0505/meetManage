OOP.ns("PC.park");

PC.park = {
	h_j_a : "CardInfoModiReqInfo.",
	a_h_j_a : "cardSellSubInfo.",
	reqInfo : {
		"CardInfoModiReqInfo.cardno" : " ",// 交易卡的逻辑卡号
		"CardInfoModiReqInfo.pCardno" : "sb",// 交易卡的物理卡号
		"CardInfoModiReqInfo.username" : " ",// 票卡子类型
		"CardInfoModiReqInfo.identityNo" : " ", // 面值
		"CardInfoModiReqInfo.certificateType" : " ", // 押金
		"CardInfoModiReqInfo.gender" : " ", // 随机数
		"CardInfoModiReqInfo.address" : " ", // 操作类型 1：正常 2：补卡售卡
		"CardInfoModiReqInfo.zipCode" : " ",// 旧交易卡的逻辑卡号
		"CardInfoModiReqInfo.fixedTelephone" : " ",// 01现金 02 充值券 03 银联卡
		"CardInfoModiReqInfo.mobilePhone" : " ", // 身份证号
		"CardInfoModiReqInfo.Fax" : "sb",// 操作员
		"CardInfoModiReqInfo.email" : " ",// 卡类标识 “03”Cpu 寻卡时获取
		"CardInfoModiReqInfo.randomNum" : " ",// 发行流水 glide_no
		"CardInfoModiReqInfo.operType" : " ",// 卡认证码
		"CardInfoModiReqInfo.oldCardno" : " "// 扇区分散码
	},
	
	affirmInfo : {
		"cardSellSubInfo.writeCard" : "0",// 写卡结果
		"cardSellSubInfo.hsglideNo" : " ",// 系统参照号
		"cardSellSubInfo.hairpinMainCode" : " ",// 发卡方主编码
		"cardSellSubInfo.hairpinSubCode" : " ",// 发卡方子编码
		"cardSellSubInfo.cardNo" : " ",// 交易卡的逻辑卡号
		"cardSellSubInfo.pCardNo" : " ",// 交易卡的物理卡号
		"cardSellSubInfo.cardSubType" : " ",// 票卡子类型
		"cardSellSubInfo.operType" : "1",// 操作类型 1：正常 2：补卡售卡
		"cardSellSubInfo.oldCardNo" : " "
	},

	// //售卡info
	set_reqInfo : function(form,flag) {
		var result = "9000";
		var t = this;
		if (flag){
			result = initDevice(flag);
		}
		try {
			if (result == "9000") {
				CardSellVar = readCardSell();
				if (CardSellVar.cardApp!=form.cardNo){
					alert("表单与读卡器上的卡片不一致");
					return null;
				}
				if (CardSellVar.paySign != "01") {
					// throw new Error ("卡片未缴活！");
					console_log("卡片未缴活");
					var RandomNum = getRandomNum();
					t.reqInfo[t.h_j_a + "cardno"] = CardSellVar.cardApp;
					t.reqInfo[t.h_j_a + "pCardno"] = CardSellVar.physicalCard;
					t.reqInfo[t.h_j_a + "cardSubType"] = CardSellVar.cardSubType
							.substring(CardSellVar.cardSubType.length - 2,
									CardSellVar.cardSubType.length);
					t.reqInfo[t.h_j_a + "deposit"] = "0";
					t.reqInfo[t.h_j_a + "randomNum"] = RandomNum;
					t.reqInfo[t.h_j_a + "operType"] = "1";
					t.reqInfo[t.h_j_a + "paytype"] = "01";
					t.reqInfo[t.h_j_a + "IDNumber"] = form.identityNo;
					t.reqInfo[t.h_j_a + "IDCard"] = "03";
					t.reqInfo[t.h_j_a + "TerminalNo"] = form.terminalId;
					t.reqInfo[t.h_j_a + "cardAuth"] = "0";
					t.reqInfo[t.h_j_a + "denomination"] = "0";
					t.reqInfo[t.h_j_a + "sectorCode"] = "0";
				}else{
					if (flag){
						alert("该卡已经激活，无法办理!");
					}
					console_log("卡片缴活");
					return null;
				}
			}
		} catch (err) {
			alert(err.message);
			t.reqInfo = {};
		}
		return t.reqInfo;
	},

	set_affirmInfo : function (form) {
		var t = this;
		t.affirmInfo[this.a_h_j_a + "hairpinMainCode"] = CardSellVar.hairpinCode;
		t.affirmInfo[this.a_h_j_a + "hairpinSubCode"] = "0000";
		t.affirmInfo[this.a_h_j_a + "cardNo"] = form.cardNo;
		t.affirmInfo[this.a_h_j_a + "pCardNo"] = CardSellVar.physicalCard;
		t.affirmInfo[this.a_h_j_a + "cardSubType"] = t.reqInfo[t.h_j_a
				+ "cardSubType"];
		return t.affirmInfo;
	},

	// //换卡info
	change_set_reqInfo : function (form) {
		console_log("--------------换卡info before--------------");
		this.set_reqInfo(form,true);
		this.reqInfo[this.h_j_a + "operType"] = "2";
		this.reqInfo[this.h_j_a + "oldcardno"] = form.oldCardNo;
		console_log("--------------换卡info end--------------");
		return this.reqInfo;
	},
	change_set_affirmInfo : function (form) {
		this.set_affirmInfo(form);
		this.affirmInfo[this.a_h_j_a + "oldCardNo"] = form.oldCardNo;
		return this.affirmInfo;
	},

	// 请求
	card_request : function (user, json, para) {
		// 调郭的售卡申请
		// 售卡申请返回后马上调 cardSell
		var card_request_ajax = jQuery.ajax({
			url : _path + "cardOperate/openCard.do",
			type : "post",
			data : user + '&' + para,
			async : false,
			dataType : "json",
			beforeSend : function(xmlHttp) {
				xmlHttp.setRequestHeader("If-Modified-Since", "0");
				xmlHttp.setRequestHeader("Cache-Control", "no-cache");
				subButon("sub",false);
			},
			success : function(data) {
				PC.park.send_card_request(data, json, para);
			}
		});

	},
	send_card_request : function (data, json, para) {
		if (data != null) {
			var data1 = data.code;
			if (data1.responseCode == "000000") {
				// startDate 启用时间
				// endDate 有效时间
				// yearDate 年检时间
				// MAC MAC售卡申请返回
				// cardFlag 卡类标识
				console_log("--------------写卡状态before--------------");
				var flag = writeCardSell(data1.enableTime, data1.validEndTime,
						data1.checkTime, data1.MAC, "01", data1.deposit, data1.psamNo);
				console_log("写卡状态="+flag);

				console_log("--------------写卡状态end--------------"+flag);
				if (this.set_reqInfo(json,false) == null){
					this.affirmInfo[this.a_h_j_a + "writeCard"] = "0";
					console_log("发卡成功");
				}else{
					this.affirmInfo[this.a_h_j_a + "writeCard"] = "1";
					console_log("发卡失败");
				}
				console_log("--------------写卡状态end##--------------"+flag);
				//this.affirmInfo[this.a_h_j_a + "writeCard"] = "0";
				this.affirmInfo[this.a_h_j_a + "hsglideNo"]=data1.hsglideNo;
				console.log(JSON.stringify(data1));
				this.card_affirm(data1, this.set_affirmInfo(json), para);
			}else{
				if(data.result)
					alert(data.result);
				else{
					alert("请求售卡申请失败!");
					jQuery("#reset").click();
				}
			}
		} else {
			alert("异常！！请联系管理员");
			jQuery("#reset").click();
		}
	},

	// 确认请求
	card_affirm : function (datas, json, para) {
		var card_affirm_ajax = jQuery.ajax({
			url : _path + "cardOperate/openCard_affirm.do",
			type : "post",
			data : jQuery.param(json) + "&" + para,
			async : false,
			dataType : "json",
			timeout : 30000,
			beforeSend : function(xmlHttp) {
				xmlHttp.setRequestHeader("If-Modified-Since", "0");
				xmlHttp.setRequestHeader("Cache-Control", "no-cache");
			},
			success : function(data){
				PC.park.send_card_affirm(data,json,para);
			},
			complete : function(XMLHttpRequest,status){
				if(status=='timeout'){
					//card_affirm_ajax.abort();
					console_log("确认开卡请求超时");
					PC.park.saveRedis(json,para);
				}
		    	subButon("sub",true);
			}
		});
	},
	send_card_affirm : function(data,json,para) {
		if (data != null) {
			var data1 = data.code;
			if (data.responseCode == "000000") {
				subButon("sub", false);
			} 
			if(this.affirmInfo[this.a_h_j_a + "writeCard"] == "0"){
				jQuery("#cardNo").removeData("val");
				jQuery("#reset").click();
				alert("开卡成功!");
			}else{
				alert("开卡失败!");
			}
		} else {
			console_log("确认开卡请求异常");
			this.saveRedis(json,para);
			alert("异常！！请联系管理员");
			jQuery("#reset").click();
		}
	},
	saveRedis : function(json,para){
		jQuery.ajax({
			url : _path + "cardOperate/opencard_affirm_timeout.do",
			type : "post",
			data : jQuery.param(json) + "&" + para,
			async : false,
			dataType : "json",
			beforeSend : function(xmlHttp) {
				xmlHttp.setRequestHeader("Cache-Control", "no-cache");
			},
			success : function(data) {
				jQuery("#cardNo").removeData("val");
				jQuery("#reset").click();
				if(PC.park.affirmInfo[PC.park.a_h_j_a + "writeCard"] == "0"){
					alert("开卡成功!");
				}else{
					alert("开卡失败!");
				}
			}
		});
	},

	// ===================================换卡======================================================

	card_change_request : function (user, json, para) {
		var card_change_request_ajax = jQuery.ajax({
			url : _path + "cardOperate/changeCard.do",
			type : "post",
			data : para,
			async : false,
			dataType : "json",
			success : function(data) {
				PC.park.send_card_change_request(data, json, para);
			},
			beforeSend : function(xmlHttp) {
				xmlHttp.setRequestHeader("Cache-Control", "no-cache");
				subButon("sub",false);
			}
		});
	},
	send_card_change_request : function (data, json, para) {
		if (data != null) {
			if (data.code == "000000") {
				// startDate 启用时间
				// endDate 有效时间
				// yearDate 年检时间
				// MAC MAC售卡申请返回
				// cardFlag 卡类标识
				/*var flag = writeCardSell(data.enableTime, data.validEndTime,
						data.checkTime, data.MAC, "01", deposits, data.psamNo);
				if (flag == true) {
					affirmInfo[a_h_j_a + "writeCard"] = "0";
					console_log("发卡成功");
				} else {
					affirmInfo[a_h_j_a + "writeCard"] = "1";
					console_log("发卡失败");
				}
				affirmInfo[a_h_j_a + "hsglideNo"]=data.hsglideNo;*/
				this.affirm_card_change(data, json, para);
			} else {
				if(data.result)
					alert(data.result);
				else
					alert("请求售卡申请失败!");
			}
		} else
			alert("异常！！请联系管理员");
	},

	affirm_card_change : function (result, json, para) {
		jQuery.ajax({
			url : _path + "cardOperate/upOrexchangeCard_affirm.do",
			type : "post",
			data : para,
			async : false,
			dataType : "json",
			success : this.send_affirm_card_change_request,
			complete : function(XMLHttpRequest,status){
		    	subButon("sub",true);
			}
		});
	},
	send_affirm_card_change_request : function (data) {
		if (data != null) {
			if (data.code == "000000") {
				jQuery("#cardNo").removeData("val");
				jQuery("#reset").click();
				alert("补卡成功!");
			}else
				if(data.result)
					alert(data.result);
				else
					alert("补卡失败!");
		} else
			alert("异常！！请联系管理员");
	}
}






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
	"randomNum":"",//随机数
}
//个人信息修改前读卡
function CardInfoModiBefInfo(){
	result = initDevice(true);
	if (result=="9000")
	{
		
		PSAMVar =readPSAM();
		CardSellVar.psamNo= PSAMVar.psamNo;
		CardSellVar.tradeKey= PSAMVar.tradeKey;
		//读取卡信息
		CardVar = BefCardInfoModiInfo();
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
		cardStatus(CardSellVar,true);
		CardSellVar.randomNum= getRandomNum();
		console_log(PSAMVar.tradeKey+"---------" +PSAMVar.psamNo);
		
	}
	
	return CardSellVar;

}
//username 用户名
//IDNumber 身份证
//IDType 身份类型
//MAC MAC售卡申请返回
function CardInfoModiWriteInfo(username,IDNumber,IDType,MAC){
	if (username.length >10 ){
		username = username.substr(0,10);
	}
	username = $URL.encode(username);
	IDNumber= $URL.encode(IDNumber);
	writeModiCardInfo("03","00",username,IDNumber,IDType,MAC);
}

