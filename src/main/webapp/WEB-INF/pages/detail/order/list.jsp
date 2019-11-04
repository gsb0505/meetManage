<%@ page language="java" contentType="text/html; charset=UTF-8" %>
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
            url: _path + 'orderDetailAction/list.do',
            datatype: "json",
            width: '1500',
            height: 465,
            colModel: [
                {label: 'id', name: 'id', index: 'id', width: 60, hidden: true},
                {label: '预订号', name: 'glideNo', index: 'glideNo', width: 120, align: "center"},
                {label: '会议室', name: 'meetRoomName', index: 'meetRoomName', width: 120, align: "center"},
                {label: '会议主题', name: 'meetName', index: 'meetName', width: 120, align: "center"},
                {label: '会议日期', name: 'meetDate', index: 'meetDate', width: 125, align: "center"},
                {label: '会议开始时间', name: 'meetStartTime', index: 'meetStartTime', width: 150, align: "center"},
                {label: '会议结束时间', name: 'meetEndTime', index: 'meetEndTime', width: 125, align: "center"},
                
                {
                    label: '联系人',
                    name: 'creator',
                    index: 'creator',
                    width: 90
                },
                {
                    label: '联系人电话',
                    name: 'phone',
                    index: 'phone',
                    width: 90
                },
                {label: '审核状态', name: 'errCode', index: 'errCode', width: 90,
                	 formatter: function (cellvalue, options, rowObject) {
                         if (cellvalue == '1') {
                             return '审核中'
                         } else if (cellvalue == '2') {
                             return '预约成功'
                         } else if (cellvalue == '3')  {
                             return '预约注销';
                         }
                     }}
            ]
        });


        jQuery(function ($) {
            var jGrid = new jqGrid();
            loadJqGrid("#tabGrid", "#pager", jGrid);
            var btnbj = jQuery("#modify");
            var btnzx = jQuery("#logout");
         // 绑定注销点击事件
            if (btnzx != null) {
                btnzx.click(function () {
                    var id = getChecked();
                    if (id.length != 1) {
                        alert('请选定一条记录!');
                        return;
                    }
                    var row = jQuery("#tabGrid").jqGrid('getRowData', id);
                    jQuery.ajax({
                        url: _path + 'orderDetailAction/logOut.do?glideNo=' + row.glideNo,
                        type: "post",
                        async: false,
                        success: function (data) {
                            if (data == "success") {
                                alert_auto("注销成功");
                            } else if (data == "fail") {
                                alert("注销失败");

                            } else {
                                alert("异常！！请联系管理员");
                            }
                        }
                    });
                    jQuery(window.document).find('#searchResult').click();
                });
            }
         
          //绑定修改点击事件
            if (btnbj != null) {
                btnbj.click(function () {
                	
                    var id = getChecked();
                    if (id.length != 1) {
                        alert('请选定一条记录!');
                        return;
                    }
                    var row = jQuery("#tabGrid").jqGrid('getRowData', id);
                    if (row.errCode=='预约注销'){
                    	 alert('预约注销记录无法进行修改!');
                         return;
                    }else{
                    showWindow('修改会议预约信息', 720, 600,
                        _path + 'orderDetailAction/modifyView.do?ordId='
                        + row.glideNo);
                    }
                });
            }

        });

        function searchResult() {
            var map = {};
            map["meetName"] = jQuery("#meetName").val();
            map["meetRoomID"] = jQuery("#meetRoomID").val();
            map["meetDate"] = jQuery("#meetDate").val();
            map["errCode"] = jQuery("#errCode").val();
            search('tabGrid', map);
        }
        

    </script>
</head>
<body>
<div class="biaoge_head">
    <div class="biaoge_tittle02">会议预约信息</div>
</div>

<ul class="biaoge_butn">
    <c:forEach items="${btns}" var="b">
        <li><a id="${b.btnId}"><i class="${b.btnCss}"></i>${b.btnText}</a></li>
    </c:forEach>
</ul>
<div class="biaoge_tittle03" style="border:0;">
    <table class="inputTable02">
        <tbody>
        <tr>
            <th>
               会议名称：
            </th>
            <td>
                <input type="text" class="search_jk" id="meetName" name="meetName">
                

            </td>
            <th>
                会议室：
            </th>
            <td>
               <select name="meetRoomID" id="meetRoomID" class="search_jk" style="width: 240px; height:30px; ">
                        <option value="">-请选择-</option>
                        <c:forEach items="${meetRoomList }" var="type">
                            <option value="${type.id }">${type.meetRoomName }</option>
                        </c:forEach>
                    </select>

            </td>
            <th>
                预约时间：
            </th>
            <td>
                <input type="text" value="" class="search_jk" id="meetDate" name="meetDate"
                       onClick="WdatePicker()"/>

            </td>
                        <th>
                预约状态：
            </th>
                        <td>
               <select name="errCode" id="errCode" class="search_jk" style="width: 240px; height:30px; ">
                        <option value="">-请选择-</option>
                            <option value="1">审核中</option>
                            <option value="2">预约成功</option>
                            <option value="3">预约注销</option>
                    </select>

            </td>
            <td>
                <div class="button_weizhi">
                    <input name="" class="searchButton" value="" type="button" id="searchResult"
                           onclick="searchResult()"></input>
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