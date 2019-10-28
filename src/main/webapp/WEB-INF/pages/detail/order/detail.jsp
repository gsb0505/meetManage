<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/pages/head/pagehead.ini"%>
<script type="text/javascript">
</script>
</head>
<body style="min-width: 710px; overflow: auto; overflow: hidden">
	<div style="display:;" class="inputTable">

		<form id="ftpload" action="" method="post">
			<input id="interfaceAccountId" name="interfaceAccountId" type="hidden" value="${id}"/>
			<input id="loadType" name="loadType" type="hidden" value="ftp"/>
			<table width="99%" border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<th>系统流水号：&nbsp;</th>
						<td colspan="2">${d.glideNo }</td>
						<th>订单时间：&nbsp;</th>
						<td colspan="2"><fmt:formatDate value="${d.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<th>卡号：&nbsp;</th>
						<td width="140">${d.cardNo }</td>
						<th>卡类别：&nbsp;</th>
						<td width="140"><t:dataName code="${d.cardType }" type="${commonType3 }"></t:dataName></td>
						<th>卡类型：&nbsp;</th>
						<td width="120"><c:if test="${d.cardMold == null }">--</c:if> ${cardMoldJson[d.cardMold] }</td>	
					</tr>
					<tr>
						<th>交易金额（元）：&nbsp;</th>
						<td>${d.amount/100 }</td>
						<th>交易状态：&nbsp;</th>
						<td>
							<c:if test="${d.errCode==1 }">
								交易成功
							</c:if> 
							<c:if test="${d.errCode==2 }">
								失败未退款
							</c:if> 
							<c:if test="${d.errCode==3 }">
								失败已退款
							</c:if>
							<c:if test="${d.errCode==4 }">
								已冲正
							</c:if>
							<c:if test="${d.errCode==5 }">
								处理中
							</c:if>
						</td>
						<th>交易类型：</th>
						<td>
							<t:dataName code="${d.tradeType }" type="${commonType4 }"></t:dataName>
						</td>
					</tr>
					<tr>
						<th>终端号：&nbsp;</th>
						<td>${d.terminalId }  </td>
						<th>订单描述：&nbsp;</th>
						<td colspan="3">${d.orderInfo }</td>
					</tr>
					<c:if test="${d.tradeType==4001 || d.tradeType==4004  }">
					<tr>
						<th>终端批次号：&nbsp;</th>
						<td>${d.outbatchNo }  </td>
						<th>终端流水号：&nbsp;</th>
						<td>${d.outorderNo }&nbsp;</td>
						<th>消费模式：&nbsp;</th>
						<td><c:if test="${d.amountType == null }">--</c:if> ${terminalAmountJson[d.amountType] }</td>					
					</tr>
					<tr>
						<th>钱包类型：&nbsp;</th>
						<td><c:if test="${d.pursetype == null }">--</c:if>${walletJson[d.pursetype] } </td>
						<th>园区：&nbsp;</th>
						<td><t:dataName code="${d.terminal.parkType }" type="${commonType8 }"></t:dataName></td>
						<c:if test="${d.tradeType==4001}">
						<th>上送状态：</th>
						<td colspan="3">
							<c:if test="${d.requestFlag ==0 }">未上送</c:if>
							<c:if test="${d.requestFlag ==1 }">已上送</c:if>
						</td>
						</c:if>
					</tr>
					<!--旧订单信息开始  -->
					<c:if test="${d.errCode==4 }">
					<tr>
						<th>旧订单时间：&nbsp;</th>
						<td>${d.o_tradeDate }</td>
						<th>旧终端流水号：&nbsp;</th>
						<td>${d.o_outorderNo }</td>
						<th>旧终端批次号：&nbsp;</th>
						<td>${d.o_outbatchNo }</td>
					</tr>
					</c:if>
					<!--旧订单信息结束  -->
					</c:if>
					<c:if test="${d.tradeType!=4001 && d.tradeType!=4004  }">
					<tr>
						<th>押金类型：&nbsp;</th>
						<td><c:if test="${d.depositType == null }">--</c:if>${depositJson[d.depositType] }</td>
						<th>押金：&nbsp;</th>
						<td>${d.deposit/100 }</td>
						<th>创建者：&nbsp;</th>
						<td>${d.creator }</td>
					</tr>
					</c:if>
					<tr>
						<th>网点编码：&nbsp;</th>
						<td>${d.terminal.branchNo}</td>
						<th>PSAM卡号：&nbsp;</th>
						<td>${d.terminal.pcmNo}</td>
					</tr>
					<tr>
						<th>转入卡号：&nbsp;</th>
						<td width="140"><c:if test="${d.turnInCardNo == null }">--</c:if>${d.turnInCardNo }</td>
					</tr>
				</tbody>
			</table>
		</form>


	</div>
</body>
</html>
