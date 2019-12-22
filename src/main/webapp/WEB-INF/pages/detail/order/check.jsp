<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/WEB-INF/pages/head/pagehead.jsp" %>
    <title>addUser</title>
    <script type="text/javascript">
        var mydata = [
            <c:if test="${orderDetail.goodsDetailList != null && orderDetail.goodsDetailList.size() > 0}">
                <c:forEach items="${orderDetail.goodsDetailList }" var="item" varStatus="idxStatus">
                    {ginfoId:'${item.ginfoId}',goodsName:'${item.goodsName}',price:'${item.price}',num:'${item.num}',amount:'${item.amount}',typeCode:'${item.typeCode}',storeCode:'${item.storeCode}'},
                </c:forEach>
            </c:if>
        ];

        var jqGrid = Class.create(BaseJqGrid, {
            width: '480',
            height: 100,
            datatype : "local",
            caption : "商品信息",
            colModel: [
                {label: 'id', name: 'ginfoId', hidden: true},
                {label: '商品类型', name: 'typeCode',hidden:true},
                {label: '商家编号', name: 'storeCode',hidden:true},
                {label: '商品名称', name: 'goodsName', index: 'goodsName', width: 90, sortable: false,},
                {label: '单价', name: 'price', index: 'price', width: 90, sortable: false,},
                {label: '预定数量', name: 'num', index: 'num', width: 90, sortable: false,},
                {label: '小计', name: 'amount', index: 'amount', width: 90, sortable: false},
                //{label: '备注', name: 'remark', width: 15},
            ]
        });

        jQuery(function ($) {
            var jGrid = new jqGrid();
            loadJqGrid("#tabGrid",null, jGrid);
            for ( var i = 0; i <= mydata.length; i++){
                jQuery("#tabGrid").jqGrid('addRowData', i + 1, mydata[i]);
            }
        });


        function commit() {
            var user = jQuery("#user").serialize();
            var meetDate = jQuery("#meetDate").val();
            var meetStartTime = jQuery("#meetStartTime").val();
            var meetEndTime = jQuery("#meetEndTime").val();
            var meetRoomID = jQuery("#meetRoomID").val();

            jQuery.ajax({
                url: _path + 'orderDetailAction/isUnique.do',
                data: {
                    'meetDate': meetDate,
                    'meetStartTime': meetStartTime,
                    'meetEndTime': meetEndTime,
                    'meetRoomID': meetRoomID
                },
                type: "post",
                success: function (data) {
                    if (data != "exception") {
                        if (data == "true") {
                            jQuery.ajax({
                                url: _path + 'orderDetailAction/check.do',
                                data: user,
                                type: "post",
                                async: false,
                                success: function (data) {
                                    if (data == "success") {
                                        alert("审核成功");
                                        setTimeout("iFClose();", 1000);
                                    } else if (data == "fail") {
                                        alert("审核失败");
                                    } else {
                                        alert("异常！！请联系管理员");
                                    }
                                }
                            });
                            jQuery(parent.window.document).find('#searchResult').click();
                        } else {
                            alert("该时间已被预约！");
                        }
                    } else {
                        alert("会议预约验证合法性异常，请重试！");
                    }
                }
            });


        }


    </script>
    <style>
        body {
            SCROLLBAR-HIGHLIGHT-COLOR: #303880;
            SCROLLBAR-SHADOW-COLOR: #303880;
            SCROLLBAR-ARROW-COLOR: #ffff00;
            SCROLLBAR-DARKSHADOW-COLOR: #303880;
            SCROLLBAR-BASE-COLOR: #303880;
        }
    </style>
</head>
<body style="min-width:380px;overflow: auto; overflow:hidden">
<div style="display:;" class="inputTable">

    <form id="user" action="" method="post">
        <table class="inputTable_liebiao inputTable_validate clear">
            <tbody>
            <tr>
                <th>
                    预约编号:
                </th>
                <td width="180px">
                    ${orderDetail.glideNo}
                    <input type="hidden" value="${orderDetail.glideNo}" name="glideNo" id="glideNo">
                </td>
                <th>
                    会议主题:
                </th>
                <td width="180px">
                    ${orderDetail.meetName}
                    <input type="hidden" value="${orderDetail.meetName}" class="formText" name="meetName">

                </td>
            </tr>
            <tr>
                <th>
                    会议室:
                </th>
                <td>
                    ${orderDetail.meetRoomName}
                    <input type="hidden" value="${orderDetail.meetRoomName}" class="formText"
                           name="meetRoomName"></input>
                    <input type="hidden" value="${orderDetail.meetRoomID}" class="formText" name="meetRoomID"></input>
                </td>
                <th>
                    会议时间:
                </th>
                <td>
                    ${orderDetail.meetDate} ${orderDetail.meetStartTime}--${orderDetail.meetEndTime}
                    <input type="hidden" value="${orderDetail.meetDate}" class="formText" name="meetDate"></input>
                    <input type="hidden" value="${orderDetail.meetStartTime}" class="formText"
                           name="meetStartTime"></input>
                    <input type="hidden" value="${orderDetail.meetEndTime}" class="formText" name="meetEndTime"></input>

                </td>
            </tr>
            <tr>
                <th>
                    会议联系人:
                </th>
                <td>
                    ${orderDetail.creator}
                    <input type="hidden" value="${orderDetail.creator}" class="formText" name="creator"></input>
                </td>
                <th>
                    联系人电话:
                </th>
                <td>
                    ${orderDetail.phone}
                    <input type="hidden" value="${orderDetail.phone}" class="formText" name="phone"></input>
                </td>
            </tr>
            <tr>
                <th>
                    特别需求:
                </th>
                <td>
                    ${orderDetail.specialdemand}
                    <input type="hidden" value="${orderDetail.specialdemand}" class="formText"
                           name="specialdemand"></input>
                </td>
                <th>

                </th>
                <td>
                </td>
            </tr>
            <tr>

                <th>邮件通知:</th>
                <td>
                    ${orderDetail.emailNotification}
                    <input type="hidden" name="emailNotification" value="${orderDetail.emailNotification }"
                           class="formText"></td>
                <th>

                </th>
                <td>
                </td>
            </tr>

            </tbody>
        </table>
        <c:if test="${orderDetail.goodsDetailList != null && orderDetail.goodsDetailList.size() > 0}">
            <center>
                <table id="tabGrid"></table>
            </center>
        </c:if>

        <div class="tanchu_box_button">

            <input type="button" class="tanchu_button03" value="审核通过" onclick="commit()"></input>

            <input type="button" class="tanchu_button04" value="取消" onclick="iFClose()"></input>

        </div>
    </form>


</div>
</body>
</html>
