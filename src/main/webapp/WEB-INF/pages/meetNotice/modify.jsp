<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
String systemPath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path+"/" ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="<%=basePath %>plugin/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/datePicker.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/home.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/styles.css" rel="stylesheet" type="text/css" />
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
seajs.config({ 
	'base': '<%=basePath %>js/'
});
var _path = "<%=systemPath %>";
</script>
</head>
<body style="min-width: 540px; overflow: auto; overflow: hidden">
<OBJECT id="rd" codeBase="cardDll.cab" WIDTH="0" HEIGHT="0" classid="clsid:638B238E-EB84-4933-B3C8-854B86140668"></OBJECT>
	<div style="display:;" class="inputTable">
		<form id="user" action="" method="post">
			<input type="hidden" name="id" value="${t.id}">
			<table class="inputTable_liebiao inputTable_validate clear">
				<tbody>
					<tr>
						<th>通知名称:</th>
						<td><input type="text" name="meetNoticeName" id="meetNoticeName" class="formText"  value="${t.meetNoticeName }"/></input>
						</td>
						<td width="80px"><span style="color:red;font-size:14px">*</span></td>
						
						<th style="width:80px;">播放会议室:</th>
						<td>
							<select name="meetRoomID" id="meetRoomID" class="formText">
                        <option value="">-请选择-</option>
                        <c:forEach items="${meetRoomList }" var="type">
                            <option  <c:if test="${type.id==t.meetRoomID }"> selected="selected"</c:if> value="${type.id }">${type.meetRoomName }</option>
                        </c:forEach>
                    </select>		
						</td>
						<td><span style="color:red;font-size:20px">*</span></td>
					</tr>
					<tr>

						<th>通知内容:</th>
						
						  <td colspan="6">
     
                    <textarea  class="formText" id="meetNoticeContent" name="meetNoticeContent" rows="10" cols="50" style="width: 488px; height: 155px;">${t.meetNoticeContent }</textarea>
                <span style="color:red;font-size:20px">*</span>
						</td>
		
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
<script type="text/javascript" src="<%=basePath%>pageJs/meetNotice/modify.js?vs=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
