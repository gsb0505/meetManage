<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>

</head>
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
<script type="text/javascript">
    var winSign="";
    var productCount = 1;
    var d = new Date();
    function addzero(v) {if (v < 10) return '0' + v;return v.toString();}
    var s = addzero(d.getHours()) +":"+ addzero(d.getMinutes());
    document.getElementById('meetStartTime').value=s;

    function productTr(i,n,p){
        if(!i) i="";
        if(!n) n="";
        if(!p) p="0 ";

        return '' +
            '                <th style="min-width: 40px">商品名称:</th>\n' +
            '                <td>' +
            '					<input type="hidden" name="goodsDetailList[][id]" value="'+i+'" />' +
            '					<input type="text" name="goodsDetailList[][productName]" value="'+n+'" class="formText" readonly /></td>\n' +
            '                <td><a onclick="openProWin(this)" >【选择商品】</a></td>\n' +
            '                <th style="min-width: 20px">单价:</th>\n' +
            '                <td><input name="goodsDetailList[][price]" class="formText" style="width: 50px;color: #a9ccff;" value="'+p+'" readonly /></td>\n' +
            '                <th style="min-width: 20px">预定数量:</th>\n' +
            '                <td><input type="text" name="goodsDetailList[][num]" class="formText" style="width: 80px" value="0" onkeyup="opnumber(this)"/></td>\n' +
            '                <th style="min-width: 20px">库存:</th>\n' +
            '                <td><p name="goodsDetailList[][count]" style="width: 50px">0</p></td>\n' +
            '                <th style="min-width: 20px">总价:</th>\n' +
            '                <td><input name="goodsDetailList[][amount]" class="formText" style="width: 50px" value="0" readonly /></td>'+
            '                <th style="min-width: 20px">备注:</th>\n' +
            '                <td><textarea name="goodsDetailList[][remark]" class="formText" style="width: 100px" /></td>\n' +
            '                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="delProduct" >【删除行】</a></td>\n' +
            '            ';
    }

    jQuery().ready(function() {
        rule();
    });
    function rule(){
        <c:forEach items="${timeRange}" var="time">
        jQuery("#t${time.code}").rules("add",{timeRange:true,timeRangelrunlv:true});
        </c:forEach>
    }
    function rollbackOK(param){
        console.log("点击回调函数："+JSON.stringify(param));
        //获取商品信息
        var obj = jQuery("."+winSign);

        obj.find("input[name='goodsDetailList[][id]']").val(param[0].id);
        obj.find("input[name='goodsDetailList[][productName]']").val(param[0].goodsName);
        obj.find("input[name='goodsDetailList[][price]']").val(param[0].price);
        obj.find("p[name='goodsDetailList[][count]']").html(param[0].count);
        if(param.length > 1){
            for (var i = 1; i < param.length; i++) {
                obj.parent().append("<tr class='tr-pro0"+productCount+"'> "+productTr(param[i].id, param[i].goodsName, param[i].price) +"</tr>");
                productCount++;
            }
        }
        window.ymPrompt.close();
    }
    //打开商品窗口
    //jQuery(".showProductWin").live("click",openProWin);
    function openProWin(o) {
        var parent=jQuery(o).parents("tr");
        winSign=parent.attr("class");
        showWindow('查找商品', 700,350,
            _path+'product/listView.do?menuId=wf_');
    }
    function addProFuntion(){
        var parent=jQuery(this).parents("tr");
        parent.parent().append("<tr class='tr-pro0"+productCount+"'> "+productTr() +"</tr>");
        productCount++;
    }
    function opnumber(a) {
        var price = jQuery(a).parents("tr").find("input[name='goodsDetailList[][price]']").val();
        var number = jQuery(a).val();
        jQuery(a).parents("tr").find("input[name='goodsDetailList[][amount]']").val(price * number);
        jQuery(a).val(a.value.replace(/[^\d]/g,''));
    }
</script>
<body style="min-width: 540px;">
<div class="inputTable" style="overflow: scroll; height: 770px;">

    <form id="user" action="" method="post">
        <table class="inputTable_liebiao inputTable_validate clear" border="0">
            <tbody>
            <tr>
                <th>会议主题:</th>
                <td colspan="5">
                    <input type="text" id="meetName" class="formText" name="meetName" maxlength="200"
                           style="width: 495px; height:30px; ">

                </td>
                <td width="100px"><span style="color:red;font-size:14px">*</span></td>
            </tr>
            <tr>
                <th>会议室时间:</th>
                <td>
                    <input type="text" name="meetDate" id="meetDate" class="formText"
                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d}'})">
                </td>
                <th>会议开始时间：</th>
                <td>
                    <input id="meetStartTime" class="formText" name="meetStartTime" style="width: 80px"
                           onClick="WdatePicker({minDate:'HH:mm',dateFmt:'HH:mm'})"></td>
                <th>会议结束时间：</th>
                <td><input id="meetEndTime" class="formText" name="meetEndTime" style="width: 80px"
                           onClick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'meetStartTime\')}'})">
                </td>

                <td><span style="color:red;font-size:20px">*</span></td>

            </tr>
            <tr>
                <th>会议室:</th>
                <td colspan="5">
                    <select name="meetRoomID" id="meetRoomID" class="formText" style="width: 495px; height:30px; ">
                        <option value="">-请选择-</option>
                        <c:forEach items="${meetRoomList }" var="type">
                            <option value="${type.id }">${type.meetRoomName }</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
            </tr>
            <tr>
                <th>特别需求:</th>
                <td colspan="5">
                    <textarea class="formText" id="specialdemand" name="specialdemand" rows="10" cols="50"
                              style="width: 495px; height: 125px; "></textarea>
                </td>
                <td width="80px"></td>

            </tr>
            <tr>

                <th>邮件通知:</th>
                <td colspan="5"><textarea class="formText" id="emailNotification" name="emailNotification" rows="10"
                                          cols="50" style="width: 495px; height: 125px; "></textarea>
                </td>
                <td width="80px"></td>
            </tr>
        </table>
        <table id="product_tab" class="inputTable_liebiao inputTable_validate" style="margin:0 auto;" border="0">
            <tr>
                <th style="background-color: #ffe4c4">&nbsp;</th>
                <td colspan="12" align="center" style="background-color: #ffe4c4">添加商品信息</td>
                <td colspan="2" align="center" style="background-color: #a9ccff">操作</td>
            </tr>
            <tr class="tr-pro0">
                <th style="min-width: 40px">商品名称:</th>
                <td>
                    <input type="hidden" name="goodsDetailList[][id]" />
                    <input type="text" name="goodsDetailList[][productName]" class="formText" readonly/>
                </td>
                <td><a onclick="openProWin(this)" >【选择商品】</a></td>
                <th style="min-width: 20px">单价:</th>
                <td><input name="goodsDetailList[][price]" value="0" class="formText" style="width: 50px;color: #a9ccff;" readonly/></td>
                <th style="min-width: 20px">预定数量:</th>
                <td><input type="text" name="goodsDetailList[][num]" class="formText" style="width: 80px" onkeyup="opnumber(this)"/></td>
                <th style="min-width: 20px">库存:</th>
                <td><p name="goodsDetailList[][count]" style="width: 50px">0</p></td>
                <th style="min-width: 20px">总价:</th>
                <td><input class="formText" name="goodsDetailList[][amount]" value="0" style="width: 50px" readonly/></td>
                <th style="min-width: 20px">备注:</th>
                <td><textarea name="goodsDetailList[][remark]" class="formText" style="width: 100px" /></td>
                <td><a id="addProduct">【添加行】</a>&nbsp;<a id="clearItem">【清空所有内容】</a></td>
            </tr>
        </table>
        <div class="tanchu_box_button">

            <input type="submit" class="tanchu_button03" value="保存"/> <input
                type="button" name="closeButton" class="tanchu_button04" value="取消"
                onClick="iFClose()"/>

        </div>
    </form>


</div>
<script type="text/javascript" src="<%=basePath%>pageJs/order/add.js?vs=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
