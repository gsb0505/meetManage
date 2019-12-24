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
                {label: '', name: 'typeCode', sortable: false, width: 1, hidden:true},
                {label: '商品名称', name: 'goodsName', sortable: false, width: 150, align: "center",editable : true,editoptions : {size : 10}},
                {
                    label: '商品分类',
                    name: 'typeCodeName',
                    sortable: false,
                    width: 150,
                    align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        if (typeCode == "") {
                            return "-";
                        }
                        var val = typeCode[rowObject.typeCode];
                        if (val != null && val != "")
                            return val;
                        else
                            return "-";
                    }
                },
                {label: '商家编号', name: 'storeCode', index: 'storeCode', sortable: false, width: 150, align: "center"},
                {label: '商家名称', name: 'storeName', sortable: false, width: 150, align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        if (storeCode == "") {
                            return "-";
                        }
                        var val = storeCode[rowObject.storeCode];
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

        function searchResult() {
            var map = {};
            //map["meetRoomID"] = jQuery("#meetRoomID").val();
            map["goodsName"] = jQuery("#goodsName").val();
            map["typeCode"] = jQuery("#typeCode").val();
            map["status"] = jQuery("#status").val();
            map["storeCode"] = jQuery("#storeCode").val();
            search('tabGrid', map);
        }
        // jQuery("#copy").click(function() {
        //     jQuery("#tabGrid").jqGrid('editGridRow', "new", {
        //         height : 300,
        //         reloadAfterSubmit : false
        //     });
        // });
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
<script type="text/javascript" src="<%=basePath%>pageJs/common/list.view.frame.js"></script>
<script type="text/javascript" src="<%=basePath%>pageJs/product/list.js?vs=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
