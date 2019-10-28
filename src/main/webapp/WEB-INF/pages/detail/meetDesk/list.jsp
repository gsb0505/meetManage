<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
<%@ include file="/WEB-INF/pages/head/pagehead.ini"%>
<script type="text/javascript" src="<%=basePath%>pageJs/meetDesk/list.js?vs=<%=System.currentTimeMillis()%>"></script>
<style type="text/css">
<!--
.xr td {
	font-family: "Tahoma";
	font-size: 20px;
	font-weight: bold;
	color: #ffffff;
	text-align: center;
	Filter: Alpha(Opacity = 100, FinishOpacity = 0, Style = 1, StartX = 0, StartY =
		 
		 0, FinishX = 0, FinishY = 100);
}

.divcss5-c {
	margin-top: 15px
}

table {
	font-family: "Tahoma";
	font-size: 12px;
}
-->
</style>


</head>
<body>
	<div class="biaoge_head">
		<div class="biaoge_tittle02">会议前台</div>
	</div>

	<center>
		<div style="width: 1600px; height: 720px; overflow: scroll;">
			<table border="0" cellpadding="0" cellspacing="0" bgcolor="#003399">

				<tr bgcolor="#FFFFFF" class="xr" heigh="150">
					<td>
						<table border="0" cellpadding="6" cellspacing="20">
							<tr bgcolor="#FFFFFF" class="xr">
								<%
									int x = 1;
								%>
								<c:forEach items="${meetRoomList }" var="type">

									<c:set var="orderfirst" value="${type.orderDetailList[0]}" />
									<c:set var="curDate" value="${curDate}" />
									<c:choose>
										<c:when test="${orderfirst.meetStartTime != null }">
											<c:choose>
												<c:when test="${orderfirst.meetStartTime < curDate}">
													<c:choose>
														<c:when test="${orderfirst.specialdemand != null}">
															<td bgcolor="#e3c490"
																style="width: 495px; height: 345px;">
														</c:when>
														<c:when
															test="${orderfirst.specialdemand == null}" >
          														<td  bgcolor="#3eb9dd" style="width: 495px; height:345px; ">
         												</c:when >
        											</c:choose>
        											<div class="xr divcss5-c" >目前：${orderfirst.meetStartTime}-${orderfirst.meetEndTime }</div>
        										<c:choose>
        <c:when test ="${orderfirst.specialdemand != null}">
        <div class="xr divcss5-c">服务：${orderfirst.specialdemand }</div>
        </c:when>
		     <c:otherwise>
          <div class="xr divcss5-c">服务：不需要</div>
         </c:otherwise>
        </c:choose>
        										</c:when>
        									 <c:otherwise >
         											<td  bgcolor="#56d3cf" style="width: 495px; height:345px; ">
          											<div class="xr divcss5-c" >目前：空闲</div>
          											<div class="xr divcss5-c">服务：不需要</div>
         									</c:otherwise> 
         									</c:choose>
        									
        </c:when>
        <c:when test="${orderfirst.meetStartTime == null }">
        <td  bgcolor="#56d3cf" style="width: 495px; height:345px; ">
        <div class="xr divcss5-c" >目前：空闲</div>
        <div class="xr divcss5-c">服务：不需要</div>
        </c:when>
        </c:choose>
        
        <div class="divcss5-c" > <font size="20">${type.meetRoomName }</font> </div>
       <div class="xr divcss5-c">__________________</div>
        <div class="xr divcss5-c">负责人：${type.person}</div>
      <c:forEach items="${type.orderDetailList}" var="ordertype">
         <c:if test="${ordertype.meetStartTime != null }">
         <c:choose>
         <c:when test="${ordertype.meetStartTime <= curDate}">
        <div class="xr divcss5-c"> 当前会议：${ordertype.meetStartTime}-${ordertype.meetEndTime }</div>
        </c:when>
        <c:otherwise>
           <div class="xr divcss5-c"> 下一会议：${ordertype.meetStartTime}-${ordertype.meetEndTime }</div>
         </c:otherwise>
        </c:choose>
         </c:if>
         </c:forEach>
          <div  class="divcss5-c" ><a onclick='turnPage("<%=path %>/orderDetailAction/listView.do?menuId=101000100115")'  style="color:red">更多..</a></div>
         </td>
         <%if (x % 3==0){ %>
         </tr><tr>
         <%} 
         x++;
         %>
</c:forEach>
      </tr></table>
     </td> </tr>
      </table>
      </div>
</center>

</body>
</html>