<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
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
    <%@ include file="/WEB-INF/pages/head/pagehead.jsp" %>
    <script type="text/javascript">
        var typeCode = jQuery.parseJSON('${typeCodes}');
        var storeCode = jQuery.parseJSON('${storeCodes}');
        var jqGrid = Class.create(BaseJqGrid, {
            pager: "pager", //分页工具栏
            multiselect: true,
            multiboxonly: true,
            url: _path + 'product/list.do',
            datatype: "json",
            width: '1500',
            height: 465,
            colModel: [
                {label: '', name: 'id', sortable: false, width: 1, hidden:true},
                {label: '商品名称', name: 'goodsName', sortable: false, width: 150, align: "center"},
                {
                    label: '商品分类',
                    name: 'typeCode',
                    sortable: false,
                    width: 150,
                    align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        if (typeCode == "") {
                            return "-";
                        }
                        var val = typeCode[cellvalue];
                        if (val != null && val != "")
                            return val;
                        else
                            return "-";
                    }
                },
                {label: '商家编号', name: 'storeCode', index: 'storeCode', sortable: false, width: 150, align: "center"},
                {label: '商家名称', name: 'goodsName', index: 'goodsName', sortable: false, width: 150, align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        if (storeCode == "") {
                            return "-";
                        }
                        var val = storeCode[cellvalue];
                        if (val != null && val != "")
                            return val;
                        else
                            return "-";
                    }
                },
                {label: '单价', name: 'price', sortable: false, width: 150, align: "center"},
                {label: '购买次数', name: 'orderNum', sortable: false, width: 150, align: "center"},
                {
                    label: '状态',
                    name: 'status',
                    index: 'status',
                    sortable: false,
                    width: 150,
                    align: "center",
                    unformat: valFormat,
                    formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == 1) {
                            return "<span val='" + cellvalue + "'>上架</span>";
                        }
                        if (cellvalue == 2) {
                            return "<span val='" + cellvalue + "'>下架</span>";
                        }
                        return "-";
                    }
                },
                {label: '商品库存', name: 'count', sortable: false, width: 150, align: "center"}
            ]
        });


        function valFormat(cellvalue, options, cell) {
            return jQuery("span", cell).attr("val");
        }

        jQuery(function ($) {
            var jGrid = new jqGrid();
            loadJqGrid("#tabGrid", "#pager", jGrid);
            var btnzj = jQuery("#insert");
            var btnbj = jQuery("#modify");
            var btncx = jQuery("#activation");
            var btnsc = jQuery("#delete");
            // 绑定增加点击事件
            if (btnzj != null) {
                btnzj.click(function () {
                    showWindow('添加商品', 640, 580,
                        _path + 'product/addView.do');
                });
            }

            if (btnbj != null) {
                btnbj.click(function () {
                    var id = getChecked();
                    if (id.length != 1) {
                        alert('请选定一条记录!');
                        return;
                    }
                    showWindow('更新商品', 660, 580,
                        _path + 'product/modifyView.do?id='
                        + id);
                });
            }

            // 绑定增加点击事件
            if (btnzx != null) {
                btnzx.click(function () {
                    var id = getChecked();
                    if (id.length != 1) {
                        alert('请选定一条记录!');
                        return;
                    }
                    var row = jQuery("#tabGrid").jqGrid('getRowData', id);
                    var statu = jQuery("#tabGrid").getCell(id, 'status');
                    if (statu == "1") {
                        alert("选择的商品已下架!!!");
                        return;
                    }
                    jQuery.ajax({
                        url: _path + 'product/lowerShelf.do?id=' + id,
                        type: "post",
                        async: false,
                        success: function (data) {
                            if (data == "success") {
                                alert_auto("下架成功");
                            } else if (data == "fail") {
                                alert("下架失败");
                            } else {
                                alert("异常！！请联系管理员");
                            }
                        }
                    });
                    jQuery(window.document).find('#searchResult').click();
                });
            }

            if (btnsc != null) {
                btnsc.click(function () {
                    var id = getChecked();
                    if (id.length != 1) {
                        alert('请选定一条记录!');
                        return;
                    }
                    var row = jQuery("#tabGrid").jqGrid('getRowData', id);
                    var r = confirm("删除后商品无法恢复，确定删除该商品吗?");
                    if (r) {
                        jQuery.ajax({
                            url: _path + 'meetRoom/delete.do?id=' + id,
                            type: "post",
                            async: false,
                            success: function (data) {
                                if (data == "success") {
                                    alert_auto("删除成功");
                                } else if (data == "fail") {
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
            //map["meetRoomID"] = jQuery("#meetRoomID").val();
            map["goodsName"] = jQuery("#goodsName").val();
            map["typeCode"] = jQuery("#typeCode").val();
            map["status"] = jQuery("#status").val();
            map["storeCode"] = jQuery("#storeCode").val();
            search('tabGrid', map);
        }
    </script>
</head>

<body>

<div class="biaoge_head">
    <div class="biaoge_tittle02">商品管理</div>
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

            <th>商品名称:</th>
            <td><input type="text" id="goodsName" name="goodsName" class="search_jk" value=""></td>
            <th>
                商品类型：
            </th>
            <td>
                <select name="typeCode" id="typeCode" class="search_jk">
                    <option value="">全部</option>
                    <c:forEach items="${typeCodess}" var="type">
                        <option value="${type.code }">${type.name }</option>
                    </c:forEach>
                </select>
            </td>
            <th>
                商家：
            </th>
            <td>
                <select name="storeCode" id="storeCode" class="search_jk">
                    <option value="">全部</option>
                    <c:forEach items="${storeCodess}" var="type">
                        <option value="${type.code }">${type.name }</option>
                    </c:forEach>
                </select>
            </td>
            <th>
                状态：
            </th>
            <td>
                <select name="status" id="status" class="search_jk">
                    <option value="1" selected>上架</option>
                    <option value="2">下架</option>
                </select>
            </td>
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
