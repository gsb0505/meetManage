<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>

</head>
<script>
   var d = new Date();
     function addzero(v) {if (v < 10) return '0' + v;return v.toString();}
     var s = addzero(d.getHours()) +":"+ addzero(d.getMinutes());
     document.getElementById('meetStartTime').value=s;
</script>
<body style="min-width: 540px; overflow: auto; overflow: hidden">
<div style="display:;" class="inputTable">

    <form id="user" action="" method="post">
        <table class="inputTable_liebiao inputTable_validate clear" border="0">
            <tbody>
            <tr>
                <th>会议主题:</th>
                <td colspan="5">
                    <input type="text" id="meetName" class="formText" name="meetName" maxlength="200"
                           style="width: 495px; height:30px; ">

                </td>
                <td width="100px"><span style="color:red;font-size:14px">*</span></td>
            </tr>
            <tr>
                <th>会议室时间:</th>
                <td>
                    <input type="text" name="meetDate" id="meetDate" class="formText"
                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d}'})">
                </td>
                <th>会议开始时间：</th>
                <td>
                    <input id="meetStartTime" class="formText" name="meetStartTime" style="width: 80px"
                           onClick="WdatePicker({minDate:'HH:mm',dateFmt:'HH:mm'})"></td>
                <th>会议结束时间：</th>
                <td><input id="meetEndTime" class="formText" name="meetEndTime" style="width: 80px"
                           onClick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'meetStartTime\')}'})">
                </td>

                <td><span style="color:red;font-size:20px">*</span></td>

            </tr>
            <tr>
                <th>会议室:</th>
                <td colspan="5">
                    <select name="meetRoomID" id="meetRoomID" class="formText" style="width: 495px; height:30px; ">
                        <option value="">-请选择-</option>
                        <c:forEach items="${meetRoomList }" var="type">
                            <option value="${type.id }">${type.meetRoomName }</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
            </tr>
            <tr>
                <th>特别需求:</th>
                <td colspan="5">
                    <textarea class="formText" id="specialdemand" name="specialdemand" rows="10" cols="50"
                              style="width: 495px; height: 125px; "></textarea>
                </td>
                <td width="80px"></td>

            </tr>
            <tr>

                <th>邮件通知:</th>
                <td colspan="5"><textarea class="formText" id="emailNotification" name="emailNotification" rows="10"
                                          cols="50" style="width: 495px; height: 125px; "></textarea>
                </td>
                <td width="80px"></td>
            </tr>
            <tr>
                <th>商品名称:</th>
                <td><input type="text" name="productName" class="formText" readonly /></td>
                <td><a href="#" id="product" >【查看商品】</a></td>
            </tr>
        </table>
        <div class="tanchu_box_button">

            <input type="submit" name="submitName" class="tanchu_button03" value="保存"/> <input
                type="button" name="closeButton" class="tanchu_button04" value="取消"
                onClick="iFClose()"/>

        </div>
    </form>


</div>
<script type="text/javascript" src="<%=basePath%>pageJs/order/add.js?vs=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript">

jQuery().ready(function() {
	rule();
});
function rule(){
	<c:forEach items="${timeRange}" var="time">
	jQuery("#t${time.code}").rules("add",{timeRange:true,timeRangelrunlv:true});
	</c:forEach>
}
function rollbackOK(params){
    console.log(JSON.stringify(params));
}
</script>
</body>
</html>
