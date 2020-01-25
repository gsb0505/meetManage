<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/WEB-INF/pages/head/modifyhead.jsp" %>
    <script type="text/javascript" src="<%=basePath%>pageJs/organization/org.js"></script>
    <script type="text/javascript">
        jQuery().ready(function() {
            jQuery("#viewPhone").click(function () {
                var url = jQuery("#photoUrl").val();
                if(!url || url == ""){
                    alert("没有上传图片，无法查看！");
                    return;
                }
                window.open(_core_path_reource + url);
            })
        });

    </script>
</head>
<body style="min-width: 540px; overflow: auto; overflow: hidden">
<div style="display:;" class="inputTable">
    <form id="user" action="" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="${user.id}">
        <input type="hidden" name="flag" value="${user.flag}">
        <input type="hidden" name="userId" value="${user.userId}">
        <table class="inputTable_liebiao inputTable_validate clear">
            <tbody>
            <tr>
                <th>姓名:</th>
                <td><input type="text" value="${user.userId}"
                           class="formText" name="userId1" readonly="readonly" disabled="disabled"
                ></td>
                <td width="80px"><span style="color:red;font-size:12px;">(不予修改)</span></td>
                <th>部门:</th>
                <td><select name="orgId" id="organ" class="formText" dep="${user.depId}">
                    <option value="">-请选择-</option>
                    <c:forEach items="${orgList }" var="org">
                        <option value="${org.id }"  <c:if
                                test="${user.orgId==org.id }"> selected="selected" </c:if>  >${org.name }</option>
                    </c:forEach>
                </select></td>
                <td><span style="color:red;font-size:20px">*</span></td>
            </tr>
            <tr>
                <th>职位:</th>
                <td><select name="depId" id="department" class="formText">
                    <option value="">-请选择科室-</option>
                    <c:forEach items="${departList }" var="depart">
                        <option value="${depart.id }"  <c:if
                                test="${user.depId==depart.id }"> selected="selected" </c:if>  >${depart.name }</option>
                    </c:forEach>
                </select></td>
                <td><span style="color:red;font-size:20px">*</span></td>
                <th>登录密码:</th>
                <td><input type="password" value="${(user.loginPSW!=null&&user.loginPSW!='')?'default':''}"
                           class="formText" name="loginPSW" maxlength="18"></input></td>
                <td><span style="color:red;font-size:20px">*</span></td>


            </tr>
            <tr>
                <th>联系方式:</th>
                <td><input name="phone" type="text" class="formText" value="${user.phone}"
                           id="phone" maxlength="11"></input>
                </td>

                <td><span style="color:red;font-size:20px">*</span></td>
                <th>邮箱:</th>
                <td><input type="text" value="${user.email}" class="formText"
                           name="email" maxlength="100"></input></td>
                <td><span style="color:red;font-size:20px">*</span></td>
            </tr>
            <tr>
                <th>备注:</th>
                <td><input type="text" value="${user.remark}"
                           class="formText" name="remark" maxlength="20"></input></td>
                <td></td>
                <th>头像:</th>
                <td>
                    <input name="photoUrl" id="photoUrl" type="hidden" value="${user.photoUrl}">
                    <input id="photoFile" name="photoFile"  type="file" class="formText" maxlength="500" accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" />
                </td>
                <td><a style="font-size: 8px;" id="viewPhone" href="#">[头像浏览]</a></td>
            </tr>
            </tbody>
        </table>
        <div class="tanchu_box_button">
            <input type="submit" class="tanchu_button03" value="保存"></input>
            <input type="button" class="tanchu_button04" value="取消" onclick="iFClose()"></input>

        </div>
    </form>

</div>
<script type="text/javascript"
        src="<%=basePath%>pageJs/user/user/modify.js?vs=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
