<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<Html>
<Head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/pages/head/pagehead.ini"%>
<script type="text/javascript" src="<%=basePath %>js/owned/download.js" ></script>
<script type="text/javascript">
jQuery(function($) {
	  jQuery("#tabGrid").jqGrid({
		url: '<%=basePath%>report/systemList.do?${sarechStr}',
			datatype : "json",
			colNames : [ '','id', '会议时间', '会议室', '部门', '预约次数' ],
			colModel : [ {
				name : '',
				sortable:false,
				width : 450,
				align : "center"
			},{	name : 'id',
				sortable:false,
				hidden:true,
				align : "center"
			}, {
				name : 'meetDate',
				width : 450,
				sortable:false,
				align : "center"
			},{name : 'meetRoomName',
				sortable:false,
				align : "center",
				width : 450
			},{
				name : 'orgname',
				sortable:false,
				align : "center",
				width : 450
			},{
				name : 'meetCount',
				sortable:false,
				align : "center",
				width : 250
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
	
	$("#print").click(function(){
		
		window.print();
		return false;
		
	});
});
</script>
</head>

<body>

	<ul class="biaoge_butn">
		<li><input type="button" value="打印" id="print" /></li>
	</ul>
	<div class="biaoge_tittle03" style="border: 0;">
		<table class="inputTable02">
			<tbody>
				<tr>
					<td><input type="hidden" value="${tradeDate }"  id="tradeDate" name="tradeDate" ></td>
				</tr>

			</tbody>
		</table>

	</div>
<style type="text/css">
div.w{
	margin:auto;
	border-left: 1px #8DB6CD solid;
	border-right: 1px #8DB6CD solid;
	width: 1150px;
	height: 78px;
}
div.w div{
	border-top: 1px #8DB6CD solid;
	width: 1150px;
	height: 25px;
	line-height: 25px;
	font-weight: bold;
	text-align: center;
}
</style>
	<div class="w" >
		<div>会议系统统计</div>
		<div>交易时间：${createTime } 到 ${updateTime }</div>
		<div>制表时间：${createTableDate }</div>
	</div>	
	<center>
		<table id="tabGrid"></table>
		<div id="pager"></div>
	</center>


</body>
</html>
