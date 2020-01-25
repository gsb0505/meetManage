<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="../head/modifyhead.jsp" %>

<script type="text/javascript">
    function clearNoNum(obj) {
        obj.value = obj.value.replace(/[^\d.]/g, "");
        obj.value = obj.value.replace(/^\./g, "");
        obj.value = obj.value.replace(/\.{2,}/g, "");
    }
</script>
</head>
<body style="min-width: 540px; overflow: auto; overflow: hidden">
<div class="inputTable">

    <form id="user" action="" method="post">
        <table class="inputTable_liebiao inputTable_validate clear">
            <tbody>
            <tr>
                <th>会议室编号:</th>
                <td>
                    <input type="text" id="meetRoomID" class="formText" name="meetRoomID" maxlength="20">
  
                </td>
                <td width="100px"><span style="color:red;font-size:14px">*</span></td>
                      <th>会议室名称:</th>
                <td>
                    <input type="text" name="meetRoomName" class="formText"
                           id="meetRoomName" maxlength="20">
                </td>
               
                <td><span style="color:red;font-size:20px">*</span></td>

            </tr>
            <tr>                
                <th>会议室类型:</th>
                <td>
                    <select name="meetRoomType" id="meetRoomType" class="formText">
                        <option value="">-请选择-</option>
                        <c:forEach items="${typeList }" var="type">
                            <option value="${type.code }">${type.name }</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
             <th>负责人:</th>
                <td>
                    <select name="personID" id="personID" class="formText">
                        <option value="">-请选择-</option>
                        <c:forEach items="${userList }" var="type">
                            <option value="${type.id }">${type.userId }</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
                
            </tr>
            <tr>

                <th>终端号:</th>
                <td><input type="text" name="terminalNo" class="formText"
                           id="terminalNo" maxlength="8"></input>
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
                <th>状态</th>
                <td>
                    <select name="status" class="formText">
                        <option value="0">启用</option>
                        <option value="1">禁用</option>
                    </select>
                </td>
                <td width="80px"></td>
            </tr>
            <tr>
                <th>会议室图片:</th>
                <td>
                    <input type="hidden" name="photoUrl" id="photoUrl"/>
                    <input type="file" name="photoFile" class="formText"
                           id="photoFile" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" />
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
            </tr>
        </table>
        <div class="tanchu_box_button">

            <input type="submit" name="submitName" class="tanchu_button03" value="保存"/> <input
                type="button" name="closeButton" class="tanchu_button04" value="取消"
                onClick="iFClose()"/>

        </div>
    </form>


</div>
<script type="text/javascript" src="<%=basePath%>pageJs/meetRoom/add.js?vs=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript">

jQuery().ready(function() {
	rule();
	
});
function rule(){
	<c:forEach items="${timeRange}" var="time">
	jQuery("#t${time.code}").rules("add",{timeRange:true,timeRangelrunlv:true});
	</c:forEach>
}
</script>
</body>
</html>
