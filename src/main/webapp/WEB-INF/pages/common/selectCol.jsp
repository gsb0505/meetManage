<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/WEB-INF/pages/head/pagehead.ini"%>
<script type="text/javascript">

	function commit() {

		var selectCol = jQuery("#selectCol").serialize();
		jQuery.ajax({
			url : _path + 'selectColumn/saveCol.do',
			type : "post",
			data : selectCol,
			async : false,
			success : function(data) {
				var colArray = [];
				colArray = data.split(",");
				var columnArray = jQuery(parent.window.document).find(
						'#tabGrid').jqGrid('getGridParam', 'colModel');
				var tabGrid=jQuery(parent.window.document).find('#tabGrid');
				for (var i = 0; i < columnArray.length; i++) {
					tabGrid.setGridParam().hideCol(columnArray[i].name)
							.trigger("reloadGrid");
				}
				for (var i = 0; i < colArray.length; i++) {
					tabGrid.setGridParam().showCol(colArray[i]).trigger(
									"reloadGrid");
				}
				iFClose();
			}
		});
		jQuery(parent.window.document).find('#searchResult').click();

	}
</script>

<style>
#selectCol tr{
height:30px;
} 

#selectCol tr input[type="button"]{
width:100px;
height:35px;
border:1px solid #eee;
background-color:RGB(103,201,0);
color:#fff;
}

</style>

</head>
<body style="min-width:0; padding:10px 20px; background-color:#F7F9FC;">
	<form action="" id="selectCol">
		<table id="colConcat">
			<c:forEach items="${columnArray}" var="col" varStatus="status">
				<c:if test="${col.label !=null }">
					<c:if test="${col.show == null }">
					<c:choose>
						<c:when test="${col.hidden == false}">
							<tr>
								<td>
									<input type="checkBox" name="columnName" checked value="${col.name}" />${col.label}
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td>
									<input type="checkBox" name="columnName" value="${col.name}" />${col.label}
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
					</c:if>
					<!-- 必须显示列 -->
					<c:if test="${col.show == true }">				
						<input type="hidden" name="columnName" value="${col.name}"  />
					</c:if>
					<!-- 
						无需显示列
					<c:if test="${col.show == false }">
							
					</c:if>					
					 -->
				</c:if>
				<c:if test="${col.label == null }">
					<input type="hidden" name="columnName" checked value="${col.name}" />
				</c:if>		
			</c:forEach>
			<tr>
				<td><input type="button" value="提交" onclick="commit()"></td>
			</tr>
		</table>

	</form>
</body>
</html>
