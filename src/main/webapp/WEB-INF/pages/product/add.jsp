<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="../head/modifyhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript">
        function clearNoNum(obj) {
            obj.value = obj.value.replace(/[^\d.]/g, "");
            obj.value = obj.value.replace(/^\./g, "");
            obj.value = obj.value.replace(/\.{2,}/g, "");
        }
        
        
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
                            <option value="${type.code }">${type.name }</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="100px"><span style="color:red;font-size:14px">*</span></td>
                <th>商品名称:</th>
                <td>
                    <input type="text" name="goodsName" class="formText"
                           id="goodsName" maxlength="40">
                </td>
                <td><span style="color:red;font-size:20px">*</span></td>

            </tr>
            <tr>
                <th>商品类型:</th>
                <td>
                    <select name="typeCode" id="typeCode" class="formText">
                        <option value="">-请选择-</option>
                        <c:forEach items="${typeList }" var="type">
                            <option value="${type.code }">${type.name }</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
                <th>单价:</th>
                <td>
                    <input type="text" name="price" class="formText" maxlength="8"
                           onkeyup="value=value.replace(/[^\d.]/g,'')"  onblur="value=value.replace(/[^\d.]/g,'')" />
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>

            </tr>
            <tr>
                <th>库存:</th>
                <td><input type="text" name="count" class="formText" maxlength="8"
                           onkeyup="value=value.replace(/[^\d]/g,'')"  onblur="value=value.replace(/[^\d]/g,'')" />
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
                <th>商品图片:</th>
                <td><input type="file" name="photoUrl" id="photoUrl" class="formText" />
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
            </tr>
        </table>
        <div class="tanchu_box_button">
            <input type="hidden" name="status" value="2" />
            <input type="submit" name="submitName" class="tanchu_button03" value="保存"/> <input
                type="button" name="closeButton" class="tanchu_button04" value="取消"
                onClick="iFClose()"/>

        </div>
    </form>



</div>
<script type="text/javascript" src="<%=basePath%>pageJs/product/add.js?vs=<%=System.currentTimeMillis()%>"></script>
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
