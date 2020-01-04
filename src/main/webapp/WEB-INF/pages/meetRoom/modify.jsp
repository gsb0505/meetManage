<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="../head/modifyhead.jsp" %>

    <script type="text/javascript">
        var ss = '${t.meetRoomName}';


        jQuery().ready(function () {
            jQuery("#viewPhone").click(function () {
                var url = "${t.photoUrl}";
                if (!url || url == "") {
                    alert("没有上传图片，无法查看！");
                    return;
                }
                window.open(_path + "${t.photoUrl}");
            })
        });
    </script>
</head>
<body style="min-width: 540px; overflow: auto; overflow: hidden">
<OBJECT id="rd" codeBase="cardDll.cab" WIDTH="0" HEIGHT="0"
        classid="clsid:638B238E-EB84-4933-B3C8-854B86140668"></OBJECT>
<div style="display:;" class="inputTable">
    <form id="user" action="" method="post">
        <input type="hidden" name="id" value="${t.id}">
        <table class="inputTable_liebiao inputTable_validate clear">
            <tbody>
            <tr>
                <th>会议室编号:</th>
                <td><input type="text" name="meetRoomID" id="meetRoomID" class="formText"
                           value="${t.meetRoomID }"/></input>
                </td>
                <td width="80px"><span style="color:red;font-size:14px">*</span></td>

                <th style="width:80px;">会议室名称:</th>
                <td>
                    <input type="text" name="meetRoomName" class="formText"
                           id="meetRoomName" maxlength="20" value="${t.meetRoomName }">
                </td>
                <td><span style="color:red;font-size:20px">*</span></td>
            </tr>
            <tr>

                <th>会议室类型:</th>
                <td>
                    <select name="meetRoomType" id="meetRoomType" class="formText">
                        <option value="">-请选择-</option>
                        <c:forEach items="${typeList }" var="type">
                            <option  <c:if test="${type.code==t.meetRoomType }"> selected="selected"</c:if>
                                    value="${type.code }">${type.name }</option>
                        </c:forEach>
                    </select></td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>

                <th>负责人:</th>
                <td>
                    <select name="personID" id="personID" class="formText">
                        <option value="">-请选择-</option>
                        <c:forEach items="${userList }" var="type">
                            <option <c:if test="${type.id==t.personID }"> selected="selected"</c:if>
                                    value="${type.id }">${type.userId }</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
            </tr>
            <tr>
                <th>终端号:</th>
                <td><input type="text" name="terminalNo" class="formText"
                           id="terminalNo" value="${t.terminalNo }" maxlength="8"></input>
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>

                <th>状态:</th>
                <td>
                    <select name="status" class="formText">
                        <option value="0" <c:if test="${'0'==t.status }"> selected="selected"</c:if>>启用</option>
                        <option value="1" <c:if test="${'1'==t.status }"> selected="selected"</c:if>>禁用</option>
                    </select>
                </td>
                <td width="80px"></td>
            </tr>
            <tr>
                <th>会议室图片:</th>
                <td>
                    <input name="photoUrl" type="hidden" value="${t.photoUrl}">
                    <input id="photoFile" name="photoFile" type="file" value="${t.photoUrl}" class="formText" maxlength="500" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg"></input>
                </td>
                <td><a style="font-size: 8px;" id="viewPhone" href="#" >[图片浏览]</a></td>
            </tr>


            </tbody>

        </table>
        <div class="tanchu_box_button">

            <input type="submit" name="submit" class="tanchu_button03" value="保存"
            ></input> <input type="button"
                             class="tanchu_button04" name="button" value="取消" onclick="iFClose()"></input>

        </div>
    </form>

</div>
<script type="text/javascript" src="<%=basePath%>pageJs/meetRoom/modify.js?vs=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
