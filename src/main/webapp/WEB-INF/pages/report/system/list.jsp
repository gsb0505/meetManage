<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<Html>
<Head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=basePath %>plugin/jquery.fn/jquery.sumoselect/sumoselect.css" rel="stylesheet" type="text/css" />
<%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>
<script type="text/javascript" src="<%=basePath %>js/owned/download.js"></script>
<script type="text/javascript" src="<%=basePath%>plugin/jquery.fn/jquery.sumoselect/jquery.sumoselect.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/owned/jquery.datalink2.js"></script>
<script type="text/javascript" src="<%=basePath%>js/owned/jquery.tmpl2.js"></script>
<script type="text/javascript" src="<%=basePath%>pageJs/report/common.js"></script>
<script type="text/javascript">

jQuery(function($) {
	jQuery("#tabGrid").jqGrid({
		url: '<%=basePath%>report/systemList.do',
			datatype : "json",
			autowidth:true,
			colNames : [ '','id', '会议时间', '会议室', '部门', '预约次数' ],
			colModel : [ {
				name : '',
				sortable:false,
				width : 150,
				align : "center"
			},{	name : 'id',
				sortable:false,
				hidden:true,
				align : "center"
			}, {
				name : 'meetDate',
				width : 150,
				sortable:false,
				align : "center"
			},{name : 'meetRoomName',
				sortable:false,
				align : "center",
				width : 150
			},{
				name : 'orgname',
				sortable:false,
				align : "center",
				width : 150
			},{
				name : 'meetCount',
				sortable:false,
				align : "center",
				width : 150
			} ],
			rowNum : 20,
			rowList : [ 10, 15, 20, 30, 50, 100 ],
			pager : '#pager',
			gridview : true,
			sortname : 'id',
			viewrecords : true,
			sortorder : "asc",
			jsonReader : {
				root : "rows",
				page : "currentPage",
				total : "totalPage",
				records : "totalResult",
				repeatitems : false
			},
			prmNames : {
				page : "currentPage",
				rows : "showCount"
			},
			userDataOnFooter : false, // 总计
			//altRows : true, //
			footerrow : false,
			rownumbers : true,
			rownumWidth : 40,
			loadError : function(xhr, status, error) {
				alert("数据加载异常,请重试!");
			},
			height : 455,
			grouping: true,
			 groupingView : {
	        	 groupColumnShow : [false, false],
	          	 groupField : ['meetDate','meetRoomName']
	    	},postData : {reportType:'1'}
	});

	//公共调用方法
	function Merger(gridName, CellName) {
		//得到显示到界面的id集合
		var mya = $("#" + gridName + "").getDataIDs();
		//当前显示多少条
		var length = mya.length;
		for (var i = 0; i < length; i++) {
			//从上到下获取一条信息
			var before = $("#" + gridName + "").jqGrid(
					'getRowData', mya[i]);
			//定义合并行数
			var rowSpanTaxCount = 1;
			for (j = i + 1; j <= length; j++) {
				//和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
				var end = $("#" + gridName + "").jqGrid(
						'getRowData', mya[j]);
				if (before[CellName] == end[CellName]) {
					rowSpanTaxCount++;
					$("#" + gridName + "").setCell(mya[j],
							CellName, '', {
								display : 'none'
							});
				} else {
					rowSpanTaxCount = 1;
					break;
				}
				$("#" + CellName + "" + mya[i] + "").attr(
						"rowspan", rowSpanTaxCount);
			}
		}
	}

});

function exportAgent(){
	var map = getFormJson("#inputForm");
	setDateTime(map);
	jQuery.ajax({
		url :  '<%=basePath%>report/exportSystemList.do',
		type : "post",
		data:map,
		async : false,
		success : function(data) {
			jQuery("html").downloads({fileName:data,type:"ex"});
		}
	});
}

function searchResult() {
	var map = getFormJson("#inputForm");
	setDateTime(map);
	search('tabGrid',map);
}

function printResult(){
	var map = getFormJson("#inputForm");
    setDateTime(map);
	map["printPage"] = "system";
	window.open("<%=basePath %>report/printViews.do?maps="+encodeURI(JSON.stringify(map)));
}
</script>
<script id="tmpday" type="text/x-jquery-tmpl">
					<th>开始日期：</th>
					<td>
						<input name="createTime"  id="createTime" class="search_jk" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d}'})" value="${currentYear}-${currentMonth }-${currentDay}"
						style="width:150px;">
					</td>
					<th>
						结束日期：
					</th>
					<td>
						<input class="search_jk" id = "updateTime" class="formText"  name="updateTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d}+1',minDate:'#F{$dp.$D(\'createTime\')}'})" value="${currentYear}-${currentMonth }-${currentDay}"
						style="width:150px;"/>
					</td>
					<th>&nbsp;</th>
					<td>
						 <div class="button_weizhi">
          					<input name="" class="searchButton" value="" type="button" id="searchResult" ></input>
       					 </div>
					</td>
</script>
<script id="tmpmonth" type="text/x-jquery-tmpl">
					<th>开始日期：</th>
					<td>
						<input name="createTime"  id="createTime" class="search_jk" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M-{%d}+1'})" value="${currentYear}-${currentMonth }"
						style="width:150px;">
					</td>
					<th>
						结束日期：
					</th>
					<td>
						<input class="search_jk" id = "updateTime" class="formText"  name="updateTime"  onClick="WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-%M-{%d}+1',minDate:'#F{$dp.$D(\'createTime\')}'})" value="${currentYear}-${currentMonth }"
						style="width:150px;"/>
					</td>
					<th>&nbsp;</th>
					<td>
						 <div class="button_weizhi">
          					<input name="" class="searchButton" value="" type="button" id="searchResult" ></input>
       					 </div>
					</td>
</script>
<script id="tmpoptional" type="text/x-jquery-tmpl">
					<th>
						开始日期：
					</th>
					<td>
						<input class="search_jk" id = "createTime" class="formText"  name="createTime" onClick="WdatePicker({dateFmt:'yyyy',maxDate:'%y'})" value="${currentYear}"
						style="width:150px;"/>
					</td>
					<th>
						结束日期：
					</th>
					<td>
						<input class="search_jk" id = "updateTime" class="formText"  name="updateTime"  onClick="WdatePicker({dateFmt:'yyyy',maxDate:'%y',minDate:'#F{$dp.$D(\'createTime\')}'})" value="${currentYear}"
						style="width:150px;"/>
					</td>
					<th>&nbsp;</th>
					<td>
						 <div class="button_weizhi">
          					<input name="" class="searchButton" value="" type="button" id="searchResult"  ></input>
       					 </div>
					</td>
</script>
</head>

<body>

	<div class="biaoge_head">
		<div class="biaoge_tittle02">会议统计报表</div>
	</div>

	<ul class="biaoge_butn">
		<li><a id="export" onclick="exportAgent();" ><i class="r_button r_button05 common_icon"></i>导出</a></li>

	</ul>
	<!--  <input type="button" value="重置密码" id="reset"/> -->
	<div class="biaoge_tittle03" style="border: 0;">
		<form id="inputForm">
		<table class="inputTable02">
			<tbody>
				<tr>
					<th>
						查询规则：
					</th>
					<td>
						<select name="reportType" id="reportType" class="search_jk" style="width:150px;">
							<option value="1" >按日统计</option>
							<option value="2" >按月统计</option>
							<option value="3" >按年统计</option>
						</select>
					</td>
					<th>
						会议室:
					</th>
					<td>
						<select multiple="multiple" placeholder="会议室多选" class="multipleSelAll" name="meetRoomID" id="meetRoomID" style="width:150px;">
								<c:forEach items="${meetRoomList }" var="type">
									 <option value="${type.id }">${type.meetRoomName }</option>
								</c:forEach>
       					</select>
					</td>
					</tr>
		<tr id="require">
					<th>开始日期：</th>
					<td>
						<input name="createTime"  id="createTime" class="search_jk" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d}'})"  value="${currentYear}-${currentMonth }-${currentDay}"style="width:150px;">
					</td>
					<th>
						结束日期：
					</th>
					<td>
						<input class="search_jk" id = "updateTime" class="formText"  name="updateTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-{%d}+1',minDate:'#F{$dp.$D(\'createTime\')}'})" value="${currentYear}-${currentMonth }-${currentDay}"
							   style="width:150px;"/>
					</td>
					<th>&nbsp;</th>
					<td>
						<div class="button_weizhi">
							<input name="" class="searchButton" value="" type="button" id="searchResult"  ></input>
	            		</div>
					</td>
				</tr>

			</tbody>
		</table>
		</form>
	</div>
	<center>
		<table id="tabGrid"></table>
		<div id="pager"></div>
	</center>



</body>
</html>
