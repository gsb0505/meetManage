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
<%@ include file="/WEB-INF/pages/head/pagehead.ini"%>
<title>modifyBaseData</title>
<script type="text/javascript">
seajs.use('common/common.form.js', function(a) {});

		function commit(){
			
			var nextCode=jQuery("#nextCode").val();
			if(nextCode == ''){
				alert("业务码不能为空！");
				return;				
			}
			
			if(isNaN(nextCode)){
				alert("业务码必须为数字！");
				return;
			}
			
			var agentRole = jQuery("#baseData").serialize();
	  		jQuery.ajax({
				url:_path+'baseDataAction/modify.do',
				data:agentRole,
				type:"post",
				async:false,
				success:function(data){
					if(data=="success"){
						alert("更新成功");
						setTimeout("iFClose();",1000);
					}else if(data == "fail"){
						alert("更新失败");
					}else{
						alert("异常！！请联系管理员");
					}
				}
		});
	  		jQuery(parent.window.document).find('#searchResult').click();
		}
		
		function getCode(){
			var typeId=jQuery('#typeId').val();
			if(typeId!==''){
				jQuery.ajax({
					url:_path+'baseDataAction/getCode.do?typeId='+typeId,
					type:"post",
					async:false,
					success:function(data){
						if(data!==null&&data!==''&&data!==undefined){
							jQuery("#code").val(data);
							jQuery("#nextCode").val(data);
						}else{
							alert1('自动业务码获取失败！');
						}
					}
				});
				jQuery("#baseData").validate().element(jQuery("#name"));	
			}else{
				jQuery("#code").val("");
				jQuery("#nextCode").val("");
			}
			
		}
</script>
</head>
<body style="min-width:540px;overflow: auto; overflow:hidden">
	<div style="display:;" class="inputTable">
                
             	  <form  id="baseData" method="post">
             	  <input type="hidden" name="id" value="${baseData.id}">
						<table class="inputTable_liebiao clear">
							<tbody>
								<tr>
									<th>
										归属表: 
									</th>
									<td>
										<input type="hidden" name="typeId" value="${baseData.typeId }"  >${baseData.typeName }		
         									<c:forEach items="${list}" var="l" varStatus="status">
              									<c:if test="${l.typeId==baseData.typeId}">
  													  ${l.typeName}
									 			</c:if >	    											
             								</c:forEach>										
										
										<!-- 
										<select class="formText" name="typeId" id="typeId" onchange="getCode();">
											<option value="">全部</option>
             									<c:forEach items="${list}" var="l" varStatus="status">
                  									 <c:if test="${l.typeId==baseData.typeId}">
      													  <option value='${l.typeId}' selected="selected">${l.typeName}</option>
													 </c:if >	
												 <c:if test="${l.typeId!=baseData.typeId}">
												 	<option value="${l.typeId}"  >${l.typeName}</option>
												 </c:if>     											
                 								</c:forEach>
               							</select>  	
										 -->
									</td>
								</tr>
								<tr>
									<th>
										名称: 
									</th>
									<td>
										<input type="text" name="name" class="formText" id="name" value="${baseData.name}" maxlength="20"/>			
									</td>
								</tr>
								<tr>
									<th>
										业务码: 
									</th>
									<td>
                                      <input type="text" value="${baseData.code}" id="nextCode" class="formText"  name="code" disabled="disabled" />
                                      <!-- <span style="color: red; font-size: 12px">(选择相应的归属表)</span> -->
									</td>
								</tr>
								<tr>
									<th>
										备注: 
									</th>
									<td>
                                      <input  type="text" class="formText"  value="${baseData.remark}" name="remark" maxlength="50"></input>
									</td>
								</tr>
							<tr>
								<th></th>
                                <td>
                                	<input type="button" class="submitButton" value="保存" onclick="commit()"></input>
								</td>
							</tr>
						</tbody>
                       </table>
		     </form>

</div>
</body>
</html>
