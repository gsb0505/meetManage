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
    var trCount = 1;
    var productAmount = 0.00;
    var productCount = 0;
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
            '					<input type="hidden" name="goodsDetailList[][typeCode]" value="" />' +
            '					<input type="hidden" name="goodsDetailList[][storeCode]" value="" />' +
            '					<input type="hidden" name="goodsDetailList[][ginfoId]" value="'+i+'" />' +
            '					<input type="text" name="goodsDetailList[][goodsName]" value="'+n+'" class="formText" readonly /></td>\n' +
            '                <td><a onclick="openProWin(this)" >【选择商品】</a></td>\n' +
            '                <th style="min-width: 20px">单价:</th>\n' +
            '                <td><input name="goodsDetailList[][price]" class="formText" style="width: 50px;color: #a9ccff;" value="'+p+'" readonly /></td>\n' +
            '                <th style="min-width: 20px">预定数量:</th>\n' +
            '                <td><input type="text" name="goodsDetailList[][num]" class="formText" style="width: 80px" value="0" onkeyup="opnumber(this)"/></td>\n' +
            '                <th style="min-width: 20px">库存:</th>\n' +
            '                <td><input type="text" name="goodsDetailList[][count]" style="width: 50px" disabled /></td>\n' +
            '                <th style="min-width: 20px">小计:</th>\n' +
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
        if(param.length > 1){
            for (var i = 1; i < param.length; i++) {
                obj.parent().append("<tr class='tr-pro0"+trCount+"'> "+productTr(param[i].id, param[i].goodsName, param[i].price) +"</tr>");
                trCount++;
            }
        }
        obj.find("input[name='goodsDetailList[][ginfoId]']").val(param[0].id);
        obj.find("input[name='goodsDetailList[][typeCode]']").val(param[0].typeCode);
        obj.find("input[name='goodsDetailList[][storeCode]']").val(param[0].storeCode);
        obj.find("input[name='goodsDetailList[][goodsName]']").val(param[0].goodsName);
        obj.find("input[name='goodsDetailList[][price]']").val(param[0].price);
        obj.find("input[name='goodsDetailList[][count]']").val(param[0].count);
        var bbnum=obj.find("input[name='goodsDetailList[][num]']");
        bbnum.trigger("onkeyup");
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
        parent.parent().append("<tr class='tr-pro0"+trCount+"'> "+productTr() +"</tr>");
        trCount++;
    }
    function opnumber(a) {
        var price = jQuery(a).parents("tr").find("input[name='goodsDetailList[][price]']").val();
        var count = jQuery(a).parents("tr").find("input[name='goodsDetailList[][count]']").val();
        var number = jQuery(a).val();console.log("countcount->"+count);console.log("number->"+number);
        if(parseInt(count) < parseInt(number)){
            jQuery(a).val("");
            alert("当前商品库存不足！");
            return;
        }

        jQuery(a).parents("tr").find("input[name='goodsDetailList[][amount]']").val(price * number);
        jQuery(a).val(a.value.replace(/[^\d]/g,''));

        //更新显示数量、总价
        var goodsAmount = jQuery("#product_tab").find("input[name='goodsDetailList[][amount]']");
        productAmount = 0.0;
        productCount = 0;
        for (var i = 0; i < goodsAmount.length; i++) {
            productAmount +=  parseFloat(goodsAmount[i].value,2);
        }
        var goodsNum = jQuery("#product_tab").find("input[name='goodsDetailList[][num]']");
        for (var i = 0; i < goodsNum.length; i++) {
            productCount +=  parseInt(goodsNum[i].value);
        }
        updateAmountTabel();
    }

    function updateAmountTabel() {
        jQuery("#productAmount").html(productAmount);
        jQuery("#productCount").html(productCount);
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
                    <input type="hidden" name="goodsDetailList[][ginfoId]" />
                    <input type="hidden" name="goodsDetailList[][typeCode]" />
                    <input type="hidden" name="goodsDetailList[][storeCode]" />
                    <input type="text" name="goodsDetailList[][goodsName]" class="formText" readonly/>
                </td>
                <td><a onclick="openProWin(this)" >【选择商品】</a></td>
                <th style="min-width: 20px">单价:</th>
                <td><input name="goodsDetailList[][price]" value="0" class="formText" style="width: 50px;color: #a9ccff;" readonly/></td>
                <th style="min-width: 20px">预定数量:</th>
                <td><input type="text" name="goodsDetailList[][num]" class="formText" style="width: 80px" onkeyup="opnumber(this)"/></td>
                <th style="min-width: 20px">库存:</th>
                <td><input type="text" name="goodsDetailList[][count]" style="width: 50px" disabled /></td>
                <th style="min-width: 20px">小计:</th>
                <td><input class="formText" name="goodsDetailList[][amount]" value="0" style="width: 50px" readonly/></td>
                <th style="min-width: 20px">备注:</th>
                <td><textarea name="goodsDetailList[][remark]" class="formText" style="width: 100px" /></td>
                <td><a id="addProduct">【添加行】</a>&nbsp;<a id="clearItem">【清空所有商品】</a></td>
            </tr>
        </table>
        <div class="tanchu_box_button">
            <input type="submit" class="tanchu_button03" value="保存"/>
        </div>
        <div style="margin: 10px auto; line-height: 45px;"> <a style="width: 150px;font-size: 18px;">预定数量：<em id="productCount">0</em>&nbsp;件.&nbsp;总价：<em id="productAmount">0.00</em></a></div>
    </form>


</div>
<script type="text/javascript" src="<%=basePath%>pageJs/order/add.js?vs=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
