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
    <script type="text/javascript" src="<%=basePath%>pageJs/organization/org.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body style="min-width: 200px; overflow: auto; overflow: hidden">
<div style="display:;" class="inputTable">

    <form id="user" action="" method="post" enctype="multipart/form-data">
        <table class="inputTable_liebiao inputTable_validate clear">
            <tbody>
            <tr>
                <th>姓名:</th>
                <td><input type="text" name="userId" class="formText"
                           id="userId" maxlength="10"></input>
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
                <th>部门:</th>
                <td>
                    <select name="orgId" id="organ" class="formText">
                        <option value="">-请选择-</option>
                        <c:forEach items="${orgList }" var="org">
                            <option value="${org.id }">${org.name }</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="100px"><span style="color:red;font-size:20px">*</span></td>

            </tr>
            <tr>
                <th>职位：</th>
                <td>
                    <select name="depId" id="department" class="search-box formText">
                        <option value="">-请选择职位-</option>
                    </select>
                </td>
                <td><span style="color:red;font-size:20px">*</span></td>
                <th>登录密码:</th>
                <td><input name="loginPSW" type="password" class="formText" maxlength="18"></input>
                </td>
                <td><span style="color:red;font-size:20px">*</span></td>
            </tr>
            <tr>
                <th>联系方式:</th>
                <td><input name="phone" type="text" class="formText"
                           id="phone" maxlength="11"></input>
                </td>
                <td><span style="color:red;font-size:20px">*</span></td>
                <th>邮箱:</th>
                <td><input name="email" type="text" class="formText" maxlength="100"></input>
                </td>
                <td><span style="color:red;font-size:20px">*</span></td>
            </tr>
            <tr>
                <th>备注:</th>
                <td><input name="remark" type="text" class="formText" maxlength="20"></input>
                </td>
                <td></td>
                <th>头像:</th>
                <td><input name="photoUrl" id="photoUrl" type="file" class="formText" maxlength="500"></input>
                </td>
                <td></td>
            </tr>
            </tbody>
        </table>
        <div class="tanchu_box_button">

            <input type="submit" class="tanchu_button03" name="submit" value="保存"/> <input
                type="button" class="tanchu_button04" name="button" value="取消"
                onclick="iFClose()"/>

        </div>
    </form>


</div>
<script type="text/javascript" src="<%=basePath%>pageJs/user/user/add.js?vs=<%=System.currentTimeMillis()%>"></script>

</body>
</html>
