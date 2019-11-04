<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/" ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>
<script type="text/javascript">
		
		
</script>
</head>
<body style="min-width:540px;overflow: auto; overflow:hidden">
<div style="" class="inputTable">
             	  <form id="roles" action="" method="post">
						<table class="inputTable_liebiao clear">
							<tbody>
								<tr>
									<th>角色编号:</th>
									<td>
										<input type="text" id="roleCode" class="formText" value="${role.roleCode }" name="roleCode" maxlength="8" />
										<span style="color:red;font-size:16px">*</span>
									</td>
									
									<th>角色名称:</th>
									<td>
										<input type="text" class="formText" id="roleDesc" name="roleDesc" value="${role.roleDesc }" />
									</td>
								</tr>
								<tr>
									<th>备注:</th>
									<td>
										<input type="text" class="formText" name="remark" maxlength="50" value="${role.remark }" />
									</td>
								</tr>
								<tr>
						</tbody>
                       </table>
                       <div class="tanchu_box_button">
                       	<input type="button" class="tanchu_button03" value="保存" onclick="commit()"></input>
                       	<input type="button" class="tanchu_button04" value="取消" onclick="iFClose()" ></input>	
                       </div>
                       <input type="hidden"  name="id" value="${role.id}" />
		     </form>

</div> 
<script type="text/javascript" src="<%=basePath%>pageJs/user/role/modify.js?vs=<%=System.currentTimeMillis()%>"></script>
</body>
</html>