jQuery("#searchResult").live("click",function(){
	searchResult();
});
jQuery("select[name='reportType']").on("change",function(){
	var $this = jQuery(this);
	jQuery("#require").empty();
	if($this.val()=="1"){
		jQuery("#tmpday").tmpl().appendTo('#require');
	}else if($this.val()=="2"){
		jQuery("#tmpmonth").tmpl().appendTo('#require');
	}else{
		jQuery("#tmpoptional").tmpl().appendTo('#require');
	}
	window.multipleSelAll = jQuery('.multipleSelAll').SumoSelect();
});
window.multipleSelAll = jQuery('.multipleSelAll').SumoSelect();

function setDateTime(map){
	var type = jQuery("#reportType").val();
	if(type){
		switch(type){
		case "1"://day
			//map["createTime"] = year + '-' + map["createTime"];
			//map["updateTime"] = year + '-' + map["updateTime"];
            map["serchType"] = 1;
			break;
		case "2"://month
			//map["createTime"] = year + '-' + map["createTime"] + "-01";
			//map["updateTime"] = year + '-' + map["updateTime"] + "-31";
			if(map["updateTime"] && map["createTime"])
				getFirstAndLastMonthDay(map);
            map["serchType"] = 2;
			break;
		case "3"://year
			map["createTime"] = map["createTime"] + "-01" + "-01";
			map["updateTime"] = map["updateTime"] + "-12" + "-31";
            map["serchType"] = 3;
			break;
		default:
			break;
		}
	}
}
function getFirstAndLastMonthDay(map){

    var arrCT = map["createTime"].split("-");
	var arrUT = map["updateTime"].split("-");
	var yearCT = arrCT[0], monthCT = arrCT[1];
    var yearUT = arrUT[0], monthUT = arrUT[1];

    var firstdate = yearCT + '-' + monthCT + '-01';
    var day = new Date(yearUT,monthUT,0);
    var lastdate = yearUT + '-' + monthUT + '-' + day.getDate();//获取当月最后一天日期
    
    map["createTime"] = firstdate;
    map["updateTime"] = lastdate;
 }  

