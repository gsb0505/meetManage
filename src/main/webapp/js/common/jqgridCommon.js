
/*
 *  作者:chaibo
 *  
 * jqGrid 封装jqGrid基类,依赖prototype.js
 */
var initValue =  0;//初始化值为0，点击查询值为1

BaseJqGrid = Class.create({
	datatype : "json",  //将这里改为使用JSON，默认查询数据
	mtype : 'POST',
	height : 385,
	autowidth : true, // 自动调整宽度
	rowNum : 20, // 每页显示记录数
	viewrecords : true, // 是否显示行数
	rowList : [10, 15,20, 30, 50, 100], // 可调整每页显示的记录数
	multiselect : true, // 是否支持多选
	loadComplete: function () { 
		// 如果数据不存在，提示信息
	},
    /*jsonReader: {      
    	repeatitems: false
	},*/
	gridview : true,
	jsonReader: {      
		root: "rows",
		page: "currentPage",
		total: "totalPage",
		records: "totalResult",    
		repeatitems : false      
	},
	prmNames : {
		page : "currentPage",
		rows : "showCount",
		order : "sortOrder",
		sort : "sortName"
	},
	userDataOnFooter : false, // 总计
	//altRows : true, //
	footerrow : false,
	rownumbers: true,
	rownumWidth: 40,
	loadError: function(xhr,status,error){  
		alert("数据加载异常,请重试!");    
	}
});
/*
 * jqGrid 封装jqGrid基类,依赖prototype.js,
 * 使用环境：同一个页面中有多个表格使用此方法。
 * 具体使用方法可以参考cxJcjbxxIndex.jsp页面。
 */
function BaseJqGrid(id){
	var BaseGrid = Class.create({
		datatype : "local",  //将这里改为使用JSON，默认查询数据
		mtype : 'POST',
		height : 385,
		autowidth : true, // 自动调整宽度
		rowNum : 15, // 每页显示记录数
		viewrecords : true, // 是否显示行数
		rowList : [10, 15,20, 30, 50, 100], // 可调整每页显示的记录数
		multiselect : true, // 是否支持多选
		loadComplete: function () { 
		},
		gridview : true,
		jsonReader: {      
			root: "rows",
			page: "currentPage",
			total: "totalPage",
			records: "totalResult",    
			repeatitems : false      
		},
		prmNames : {
			page : "currentPage",
			rows : "showCount",
			order : "sortOrder",
			sort : "sortName"
		},
		userDataOnFooter : false, // 总计
		//altRows : true, //
		footerrow : false,
		rownumbers: true,
		rownumWidth: 40,
		loadError: function(xhr,status,error){  
			alert("数据加载异常,请重试!");    
		}
	});
	
	return BaseGrid;
}


/**
 * 加载JqGrid表格
 * @param tableID : jqGrid列表id
 * @param pagerId : jqGrid列表pager分页导航id
 * @param obj : jqGrid列表对象,如userGrid
 * @param percent : jqGrid列表宽度显示的百分比,非必填
 * @param width : jqGrid列表宽度减去的宽度,非必填
 */

function loadJqGrid(tableID, pagerId, obj, percent, width) {
	jQuery(tableID).jqGrid(obj).navGrid(pagerId, {
		del : false,
		add : false,
		edit : false,
		search : false,
		refresh : true
	}, {}, {}, {}, {
		multipleSearch : true
	});
}
function loadYhfpJqGrid(tableID, pagerId, obj, percent, width) {
	jQuery(tableID).jqGrid(obj).navGrid(pagerId, {
		del : false,
		add : false,
		edit : false,
		search : false,
		refresh : false
	}, {}, {}, {}, {
		multipleSearch : true
	});
}

/**
 * 自定义要显示的列
 * 
 */
function selectCol(){
	 var columnArray = jQuery("#tabGrid").jqGrid('getGridParam','colModel');
	var  columnArrayJson =  JSON.stringify(columnArray);
	showWindow('选择显示列', 450,350,_path+'selectColumn/selectColView.do?columnArrayJson='+ encodeURI(encodeURI(columnArrayJson)));
}

/**
 * 刷新结果集
 * @param tabId
 */
function refershGrid(tabId) {
	jQuery("#"+tabId).jqGrid().trigger('reloadGrid');
}

/**
* 通用查询脚本
* @param tabId
* @param jsonMap
*/
function search(tabId,jsonMap) {
	initValue =  1;
	jQuery("#"+tabId).jqGrid('setGridParam',{postData : jsonMap,datatype:'json'}).trigger('reloadGrid');
	
}

/**
 * 获取jqGrid中的选中行
 */
function getChecked() {
	return jQuery("#tabGrid").jqGrid('getGridParam', 'selarrrow');
}

/**
 * 返回指定行的数据
 * 返回的是数组array name:value
 * name为colModel中的名称
 */
function getRowData() {
	var selr = getChecked();
	var orders  = new Array();  //将多选行的数据放到数组中去
    if(selr.length) {
       for(var i=0;i<selr.length;i++) {
            var order = jQuery('#tabGrid').jqGrid('getRowData',selr[i]);
            orders.push(order);
       }
    }
    return orders;
}


function getRowData1111() {
	var selr = getChecked();
	var orders  = [];  //将多选行的数据放到数组中去
    if(selr.length) {
       for(var i=0;i<selr.length;i++) {
            var order = jQuery('#tabGrid').jqGrid('getRowData',selr[i]);
            orders.push(order);
       }
    }
}



/**
 * 返回指定行的数据
 * 返回的是数组array name:value
 * rowId为colModel中的key
 */
function getOneRowData(rowId) {
	var orders = [];
	var order = jQuery('#tabGrid').jqGrid('getRowData',rowId);
	orders.push(order);
	return orders;
}

/**
 * 获取选中行的数据
 * 返回的是数组array name:value
 */
function getSelectRowData(){
	var id = jQuery("#tabGrid").jqGrid('getGridParam','selarrrow');
	if (id.length != 1) {
		alert('请选定一条记录!');
		return "";
	}
	var order = jQuery('#tabGrid').jqGrid('getRowData',id[0]);
	return order;
}
/**
 * 获取普通页面数据表格选中的checkbox值
 * @param name  页面checkbox name名称
 * @returns {Array}
 */
function getSelectValue(name){
	 var ary = document.getElementsByName(name);
	 var orders  = new Array();  //将多选行的数据放到数组中去
     for(i=0; i<ary.length; i++){
        if(ary[i].checked){
            orders.push(ary[i].value);
        }
     }
	 return orders;
}

/**
 * 批量操作
 * @param url
 * @param msg
 */
function plcz(url,msg){
	var ids = getChecked();

	if (ids.length == 0){
		alert('请选择您要'+msg+'的记录！');
	} else {
		
		var _do = function(){
			jQuery.ajaxSetup({async:false});
			jQuery.post(url,{ids:ids.toString()},function(data){
				alert(data.toString());
				refershGrid('tabGrid');
			},'json');
			jQuery.ajaxSetup({async:true});
		}
		
		showConfirmDivLayer('您确定要'+msg+'选择的记录吗？',{'okFun':_do})
	}
}

function refershParent(){
	jQuery(parent.window.document).find('#searchResult').click();
	iFClose();
}

/**
 * 列值为空处理
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns
 */
function emptyMark(cellvalue, options, rowObject){
	if(cellvalue)
		return cellvalue;
	return "-";
}
/**
 * 列值金额处理
 * @param cellvalue
 * @param options
 * @param rowObject
 * @returns
 */
function cashFormat(cellvalue, options, rowObject){
	if(cellvalue)
		return (cellvalue/100).toFixed(2);
	return 0;
}

