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
        var jqGrid = Class.create(BaseJqGrid, {
            pager: "pager", //分页工具栏
            multiselect: true,
            multiboxonly: true,
            url: _path + 'goodsDetail/list.do',
            datatype: "json",
            width: '1500',
            height: 465,
            colModel: [
				{label: '预约时间', name: 'creator', sortable: false,width: 150, align: "center"},
                {label: '流水号', name: 'typeCode', sortable: false,width: 150, align: "center"},
                {label: '所属商家', name: 'storeCode', sortable: false, width: 150, align: "center"},
                {label: '商品名称', name: 'goodsName', sortable: false, width: 150, align: "center",editable : true,editoptions : {size : 10}},
                {label: '购买次数', name: 'num', sortable: false, width: 150, align: "center"},
                {label: '单价', name: 'price', sortable: false, width: 150, align: "center"},
                {label: '商品总价', name: 'amount', sortable: false, width: 150, align: "center"}
            ]
        });

        function valFormat(cellvalue, options, cell) {
            return jQuery("span", cell).attr("val");
        }

        function searchResult() {
            var map = {};
            map["goodsName"] = jQuery("#goodsName").val();
            map["storeCode"] = jQuery("#storeCode").val();
            search('tabGrid', map);
        }
    </script>
</head>

<body>

<div class="biaoge_head">
    <div class="biaoge_tittle02">预约商品明细</div>
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
