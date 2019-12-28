<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>

    <script type="text/javascript">
        var winSign="";
        var trCount = 1;
        var productAmount = 0.00;
        var productCount = 0;

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
                '                <td><input type="text" name="goodsDetailList[][count]" style="width: 50px" disabled/></td>\n' +
                '                <th style="min-width: 20px">小计:</th>\n' +
                '                <td><input name="goodsDetailList[][amount]" class="formText" style="width: 50px" value="0" readonly /></td>'+
                '                <th style="min-width: 20px">备注:</th>\n' +
                '                <td><textarea name="goodsDetailList[][remark]" class="formText" style="width: 100px" /></td>\n' +
                '                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="delProduct" >【删除行】</a></td>\n' +
                '            ';
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
        function updateAmountTabel() {
            jQuery("#productAmount").html(productAmount);
            jQuery("#productCount").html(productCount);
        }
        function opnumber(a) {
            var price = jQuery(a).parents("tr").find("input[name='goodsDetailList[][price]']").val();
            var count = jQuery(a).parents("tr").find("input[name='goodsDetailList[][count]']").val();
            var number = jQuery(a).val();
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



    </script>
</head>
<body style="min-width: 570px; overflow: auto; overflow: scroll">
<div class="inputTable">

    <form id="user" action="" method="post">
        <input type="hidden" id="glideNo" class="formText" name="glideNo" maxlength="200" style="width: 495px; height:30px; " value="${orderDetail.glideNo}" >
        <input type="hidden" id="id" name="id" value="${orderDetail.id}" >

        <table class="inputTable_liebiao inputTable_validate clear" border="0">
            <tbody>
            <tr>
                <th>会议主题:</th>
                <td colspan="5" >
                    <input type="text" id="meetName" class="formText" name="meetName" maxlength="200" style="width: 495px; height:30px; " value="${orderDetail.meetName}" >
   
                </td>
                  <td width="100px" ><span style="color:red;font-size:14px">*</span></td>
                  </tr>
                  <tr>
                      <th>会议室时间:</th>
                <td>
                    <input type="text" name="meetDate" id="meetDate" class="formText" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d}'})" value="${orderDetail.meetDate}">
                           </td><th>会议开始时间：</th><td>
                   <input  id="meetStartTime" class="formText"  name="meetStartTime" style="width: 80px"
                           onClick="WdatePicker({dateFmt:'HH:mm'})" value="${orderDetail.meetStartTime}"></td><th>会议结束时间：</th><td><input  id="meetEndTime" class="formText" name="meetEndTime" style="width: 80px"
                           onClick="WdatePicker({dateFmt:'HH:mm',minDate:'#F{$dp.$D(\'meetStartTime\')}'})" value="${orderDetail.meetEndTime}">
                </td>
               
                <td><span style="color:red;font-size:20px">*</span></td>

            </tr>
            <tr>                
                <th>会议室:</th>
                <td colspan="5">
                   <select name="meetRoomID" id="meetRoomID" class="formText" style="width: 495px; height:30px; ">
                        <option value="">-请选择-</option>
                        <c:forEach items="${meetRoomList }" var="type">
                            <option <c:if test="${type.id==orderDetail.meetRoomID }"> selected="selected"</c:if> value="${type.id }">${type.meetRoomName }</option>
                        </c:forEach>
                    </select>
                </td>
                <td width="80px"><span style="color:red;font-size:20px">*</span></td>
                </tr><tr>
                 <th>特别需求:</th>
                <td colspan="5">
                   <textarea  class="formText" id="specialdemand" name="specialdemand" rows="10" cols="50" style="width: 495px; height: 125px; " >${orderDetail.specialdemand}</textarea>
               
                </td>
                <td width="80px"></td>
                
            </tr>
            <tr>

                <th>邮件通知:</th>
                <td colspan="5"> <textarea  class="formText" id="emailNotification" name="emailNotification" rows="10" cols="50" style="width: 495px; height: 125px; " >${orderDetail.emailNotification}</textarea>

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
            <c:if test="${orderDetail.goodsDetailList == null || orderDetail.goodsDetailList.size() == 0}">
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
                    <td><input type="text" name="goodsDetailList[][count]" style="width: 50px" value="0" disabled/></td>
                    <th style="min-width: 20px">小计:</th>
                    <td><input class="formText" name="goodsDetailList[][amount]" value="0" style="width: 50px" readonly/></td>
                    <th style="min-width: 20px">备注:</th>
                    <td><textarea name="goodsDetailList[][remark]" class="formText" style="width: 100px" ></textarea></td>
                    <td><a id="addProduct">【添加行】</a>&nbsp;<a id="clearItem">【清空所有内容】</a></td>
                </tr>
            </c:if>
            <c:forEach items="${orderDetail.goodsDetailList }" var="item" varStatus="idxStatus">
                <tr class="tr-pro${idxStatus.index}">
                    <th style="min-width: 40px">商品名称:</th>
                    <td>
                        <input type="hidden" name="goodsDetailList[][typeCode]" value="${item.typeCode}" />
                        <input type="hidden" name="goodsDetailList[][storeCode]" value="${item.storeCode}" />
                        <input type="hidden" name="goodsDetailList[][ginfoId]" value="${item.ginfoId}" />
                        <input type="text" name="goodsDetailList[][goodsName]" class="formText" value="${item.goodsName}" readonly/>
                    </td>
                    <td><a onclick="openProWin(this)" >【选择商品】</a></td>
                    <th style="min-width: 20px">单价:</th>
                    <td><input name="goodsDetailList[][price]" value="${item.price}" class="formText" style="width: 50px;color: #a9ccff;" readonly/></td>
                    <th style="min-width: 20px">预定数量:</th>
                    <td><input type="text" name="goodsDetailList[][num]" value="${item.num}" class="formText" style="width: 80px" onkeyup="opnumber(this)"/></td>
                    <th style="min-width: 20px">库存:</th>
                    <c:if test="${item.goodsInfo != null}">
                        <td><input type="text" name="goodsDetailList[][count]" style="width: 50px" value="${item.goodsInfo.count}" disabled/></td>
                    </c:if>
                    <c:if test="${item.goodsInfo == null}">
                        <td><input type="text" name="goodsDetailList[][count]" style="width: 50px" value="0" disabled></td>
                    </c:if>
                    <th style="min-width: 20px">小计:</th>
                    <td><input class="formText" name="goodsDetailList[][amount]" value="${item.amount}" style="width: 50px" readonly/></td>
                    <th style="min-width: 20px">备注:</th>
                    <td><textarea name="goodsDetailList[][remark]" class="formText" style="width: 100px" ></textarea></td>
                    <td><a id="addProduct">【添加行】</a>
                </tr>
            </c:forEach>
        </table>
        <div class="tanchu_box_button">

            <input type="submit" class="tanchu_button03" value="保存"/> <input
                type="button" name="closeButton" class="tanchu_button04" value="取消"
                onClick="iFClose()"/>

        </div>
    </form>



</div>
<script type="text/javascript" src="<%=basePath%>pageJs/order/modify.js?vs=<%=System.currentTimeMillis()%>"></script>
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
