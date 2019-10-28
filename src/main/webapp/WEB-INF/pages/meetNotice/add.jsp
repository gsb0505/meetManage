<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%
    String systemPath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="<%=basePath %>plugin/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath %>css/datePicker.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath %>css/home.css" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath %>css/styles.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery/1.7.2/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery-ui-1.7.2.custom.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>plugin/ymPrompt/ymPrompt.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery.validate.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/common/messages_cn.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/owned/sea.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/owned/jquery.datalink2.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/owned/jquery.tmpl2.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/owned/js.js"></script>
    <script src="<%=basePath%>js/card.operator/xjcardObj.js"></script>
  
    <script type="text/javascript">
        var _path = "<%=systemPath %>";
        seajs.config({
            'base': '<%=basePath %>js/'
        });
        var models = "${models}";
        function clearNoNum(obj) {
            obj.value = obj.value.replace(/[^\d.]/g, "");
            obj.value = obj.value.replace(/^\./g, "");
            obj.value = obj.value.replace(/\.{2,}/g, "");
        }
        
        
    </script>
</head>
<body style="min-width: 540px; overflow: auto; overflow: hidden">
<div style="display:;" class="inputTable">

    <form id="user" action="" method="post">
        <table class="inputTable_liebiao inputTable_validate clear">
            <tbody>
            <tr>
                <th>通知名称:</th>
                <td>
                    <input type="text" id="meetNoticeName" class="formText" name="meetNoticeName" maxlength="20">
  
                </td>
                
                <td width="100px"><span style="color:red;font-size:14px">*</span></td>
                  <th>播放会议室:</th>
                <td>
                    <select name="meetRoomID" id="meetRoomID" class="formText">
                        <option value="">-请选择-</option>
                        <c:forEach items="${meetRoomList }" var="type">
                            <option value="${type.id }">${type.meetRoomName }</option>
                        </c:forEach>
                    </select>
                </td>

                <td><span style="color:red;font-size:20px">*</span></td>

            </tr>
            <tr>                
                                    <th>通知内容:</th>
                <td colspan="6">
     
                    <textarea  class="formText" id="meetNoticeContent" name="meetNoticeContent" rows="10" cols="50" style="width: 488px; height: 155px; " ></textarea>
                <span style="color:red;font-size:20px">*</span>
                </td>
            </tr>
        </table>
        <div class="tanchu_box_button">

            <input type="submit" name="submitName" class="tanchu_button03" value="保存"/> <input
                type="button" name="closeButton" class="tanchu_button04" value="取消"
                onClick="iFClose()"/>

        </div>
    </form>


</div>
<script type="text/javascript" src="<%=basePath%>pageJs/meetNotice/add.js?vs=<%=System.currentTimeMillis()%>"></script>
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
