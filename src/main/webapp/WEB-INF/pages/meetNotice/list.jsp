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
<script type="text/javascript">
	var jqGrid = Class.create(BaseJqGrid, {
		pager : "pager", //分页工具栏  
		multiselect : true,
		multiboxonly : true,
		url : _path + 'meetNotice/list.do',
		datatype : "json",
		width : '1500',
		height : 465,
		colModel : [ {
			label : '通知名称',
			name : 'meetNoticeName',
			sortable : false,
			width : 250,
			align : "center", formatter: function (cellvalue, options, rowObject) {
                if (cellvalue.length > 20) {
                    return cellvalue.substring(0,20)+"...";
                } else {
                    return cellvalue;
                }
            }
		}, {
			label : '通知内容',
			name : 'meetNoticeContent',
			sortable : false,
			width : 550,
			align : "center", formatter: function (cellvalue, options, rowObject) {
                if (cellvalue.length > 48) {
                    return cellvalue.substring(0,48)+"...";
                } else {
                    return cellvalue;
                }
            }
		}, {
			label : '播放会议室',
			name : 'meetRoomName',
			sortable : false,
			width : 100,
			align : "center"
		}, {
			label : '状态',
			name : 'status',
			index : 'status',
			sortable : false,
			width : 100,
			align : "center",
			unformat : valFormat,
			formatter : function(cellvalue, options, rowObject) {
				if (cellvalue == 0) {
					return "<span val='"+cellvalue+"'>已发布</span>";
				}
				if (cellvalue == 1) {
					return "<span val='"+cellvalue+"'>未发布</span>";
				}
				return "-";
			}
		}]
	});

	function valFormat(cellvalue, options, cell) {
		return jQuery("span", cell).attr("val");
	}

	jQuery(function($) {
		var jGrid = new jqGrid();
		loadJqGrid("#tabGrid", "#pager", jGrid);
		var btnzj = jQuery("#insert");
		var btnbj = jQuery("#modify");
		var btnzx = jQuery("#pushOff");
		var btncx = jQuery("#push");
		var btnsc = jQuery("#delete");
		// 绑定增加点击事件
		if (btnzj != null) {
			btnzj.click(function() {
				showWindow('添加会议室屏幕通知栏', 640, 580, _path + 'meetNotice/addView.do');
			});
		}

		if (btnbj != null) {
			btnbj.click(function() {
				var id = getChecked();
				if (id.length != 1) {
					alert('请选定一条记录!');
					return;
				}
				showWindow('更新会议室屏幕通知栏', 660, 580, _path
						+ 'meetNotice/modifyView.do?id=' + id);
			});
		}

		// 绑定增加点击事件
		if (btnzx != null) {
			btnzx.click(function() {
				var id = getChecked();
				if (id.length != 1) {
					alert('请选定一条记录!');
					return;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				var statu = jQuery("#tabGrid").getCell(id, 'status');
				if (statu == "1") {
					alert("撤消发布!!!");
					return;
				}
				jQuery.ajax({
					url : _path + 'meetNotice/logOut.do?id=' + id,
					type : "post",
					async : false,
					success : function(data) {
						if (data == "success") {
							alert_auto("撤消发布成功");
						} else if (data == "fail") {
							alert("撤消发布失败");
						} else {
							alert("异常！！请联系管理员");
						}
					}
				});
				jQuery(window.document).find('#searchResult').click();
			});
		}

		// 绑定增加点击事件
		if (btncx != null) {
			btncx.click(function() {
				var id = getChecked();
				if (id.length != 1) {
					alert('请选定一条记录!');
					return;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				var statu = jQuery("#tabGrid").getCell(id, 'status');
				if (statu == "0") {
					alert("已发布!!!");
					return;
				}
				jQuery.ajax({
					url : _path + 'meetNotice/backOut.do?id=' + id,
					type : "post",
					async : false,
					success : function(data) {
						if (data == "success") {
							alert_auto("发布成功");
						} else if (data == "fail") {
							alert("发布失败");

						} else {
							alert("异常！！请联系管理员");
						}
					}
				});
				jQuery(window.document).find('#searchResult').click();
			});
		}

		if (btnsc != null) {
			btnsc.click(function() {
				var id = getChecked();
				if (id.length != 1) {
					alert('请选定一条记录!');
					return;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				var r = confirm("确定删除该会议室屏幕通知栏信息吗?");
				if (r) {
					jQuery.ajax({
						url : _path + 'meetNotice/delete.do?id=' + id,
						type : "post",
						async : false,
						success : function(data) {
							if (data == "success") {
								alert_auto("删除成功");
							}else if (data == "fail") {
								alert("删除失败");

							} else {
								alert("异常！！请联系管理员");
							}
						}
					});
					jQuery(window.document).find('#searchResult').click();
				}
			});
		}

	});
	function searchResult() {
		var map = {};
		map["meetRoomID"] = jQuery("#meetRoomID").val();
		map["meetNoticeName"] = jQuery("#meetNoticeName").val();
		search('tabGrid', map);
	}
</script>
</head>

<body>

	<div class="biaoge_head">
		<div class="biaoge_tittle02">会议室屏幕通知栏</div>
	</div>

	<ul class="biaoge_butn">
		<c:forEach items="${btns}" var="b">
			<li><a id="${b.btnId}"><i class="${b.btnCss}"></i>${b.btnText}</a></li>
		</c:forEach>
	</ul>
	<div class="biaoge_tittle03" style="border: 0;">
		<table class="inputTable02">
			<tbody>
				<tr>
					<th>通知名称:</th>
					<td><input type="text" id="meetNoticeName" name="meetNoticeName"
						class="search_jk" value=""></td>
					<th>所属会议室：</th>
					<td><select name=meetRoomID id="meetRoomID"
						class="search_jk">
							<option value="">全部</option>
							<c:forEach items="${meetRoomList}" var="type">
								<option value="${type.id }">${type.meetRoomName }</option>
							</c:forEach>
					</select></td>
					<td>
						<div class="button_weizhi">
							<input name="" class="searchButton" value="" type="button"
								id="searchResult" onclick="searchResult()"></input>
						</div>
					</td>
				</tr>

			</tbody>
		</table>

	</div>
	<center>
		<table id="tabGrid"></table>
		<div id="pager"></div>
	</center>
</body>
</html>
