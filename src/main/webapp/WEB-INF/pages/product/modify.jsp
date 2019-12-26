<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8" %>
<%@ include file="../head/modifyhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<script type="text/javascript">

jQuery().ready(function() {
    jQuery("#viewPhone").click(function () {
        var url = "${t.photoUrl}";
        if(!url || url == ""){
            alert("没有上传图片，无法查看！");
            return;
        }
        window.open(_path + "${t.photoUrl}");
    })
});
</script>
</head>
<body style="min-width: 540px; overflow: auto; overflow: hidden">
	<div style="display:;" class="inputTable">
		<form id="product" action="" method="post">
			<table class="inputTable_liebiao inputTable_validate clear">
				<tbody>
				<tr>
					<th>商家:</th>
					<td>
						<select name="storeCode" id="storeCode" class="formText">
							<option value="">-请选择-</option>
							<c:forEach items="${storeList }" var="type">
								<option value="${type.code }" <c:if test="${type.code==t.storeCode }"> selected="selected"</c:if>>${type.name }</option>
							</c:forEach>
						</select>
					</td>
					<td width="100px"><span style="color:red;font-size:14px">*</span></td>
					<th>商品名称:</th>
					<td>
						<input type="text" name="goodsName" class="formText"
							   id="goodsName" maxlength="40" value="${t.goodsName}">
					</td>
					<td><span style="color:red;font-size:20px">*</span></td>

				</tr>
				<tr>
					<th>商品类型:</th>
					<td>
						<select name="typeCode" id="typeCode" class="formText">
							<option value="">-请选择-</option>
							<c:forEach items="${typeList }" var="type">
								<option value="${type.code }"  <c:if test="${type.code==t.typeCode }"> selected="selected"</c:if>>${type.name }</option>
							</c:forEach>
						</select>
					</td>
					<td width="80px"><span style="color:red;font-size:20px">*</span></td>
					<th>单价:</th>
					<td>
						<input type="text" name="price" class="formText" maxlength="8" value="${t.price}"
							   onkeyup="value=value.replace(/[^\d.]/g,'')"  onblur="value=value.replace(/[^\d.]/g,'')" />
					</td>
					<td width="80px"><span style="color:red;font-size:20px">*</span></td>

				</tr>
				<tr>
					<th>库存:</th>
					<td><input type="text" name="count" class="formText" maxlength="8" value="${t.count}"
							   onkeyup="value=value.replace(/[^\d]/g,'')"  onblur="value=value.replace(/[^\d]/g,'')" />
					</td>
					<td width="80px"><span style="color:red;font-size:20px">*</span></td>
					<th>商品图片:</th>
					<td>
						<input name="photoUrl" type="hidden" value="${t.photoUrl}">
						<input id="photoFile" name="photoFile" type="file" value="${t.photoUrl}" class="formText" maxlength="500"></input>
					</td>
					<td><a style="font-size: 8px;" id="viewPhone" href="#" >[图片浏览]</a></td>
					<%--<th>状态</th>--%>
					<%--<td>--%>
						<%--<select name="status" class="formText">--%>
							<%--<option value="1" <c:if test="${t.status=='1' }"> selected="selected"</c:if>>上架中</option>--%>
							<%--<option value="2" <c:if test="${t.status=='2' }"> selected="selected"</c:if>>已下架</option>--%>
						<%--</select>--%>
					<%--</td>--%>
					<%--<td width="80px"></td>--%>
				</tr>
			</table>
			<div class="tanchu_box_button">
				<input type="hidden" name="id" value="${t.id}"/>
				<input type="submit" name="submitName" class="tanchu_button03" value="保存"/> <input
					type="button" name="closeButton" class="tanchu_button04" value="取消"
					onClick="iFClose()"/>

			</div>
		</form>

	</div>
<script type="text/javascript" src="<%=basePath%>pageJs/product/modify.js?vs=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
