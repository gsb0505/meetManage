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
<script type="text/javascript">
seajs.use('common/common.form.js', function(a) {});
		/**
		* 获取业务（code）
		**/
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
		
		jQuery().ready(function() {
			 var agentRole = jQuery("#baseData").serialize();
				//表单提交验证
				jQuery("#baseData").validate({
					debug: true,
					errorElement: "em",
					errorPlacement: function(error, element) {
						error.appendTo(element.parent("td").next("td"));
					},
					success: function(label) {
						label.addClass("valid");
					},
				    rules: {
				    	name: {
						    required: true,
						    nameUnique:true
						},
						typeId: {
						    required: true
						}
				    },
				    submitHandler:function(form){
				    	var agentRole = jQuery("#baseData").serialize();
						jQuery.ajax({
							url:_path+'baseDataAction/add.do',
							data:agentRole,
							type:"post",
							async:false,
							success:function(data){
								if(data=="success"){
									alert_auto.call(window,"添加成功");
								}else if(data == "fail"){
									alert("添加失败");
								}else{
									alert("异常！！请联系管理员");
								}
							}
						});
			  			jQuery(parent.window.document).find('#searchResult').click();
				    }
				});
			 
				
				jQuery.validator.addMethod("nameUnique", function(value, element) {
					var result=false;
					
					// 设置同步
			        jQuery.ajaxSetup({
			            async: false
			        });
			        jQuery.ajax({
			    		url : _path + 'baseDataAction/isUnique_param.do',
			    		data : {'name':value,'typeId':jQuery("#typeId").val()},
			    		type : "post",
			    		success : function(data) {
			    			if(data!="exception"){
				    			result=(data=="success"?true:false);
			    			}
			    		}
			    	});
			        // 恢复异步
			        jQuery.ajaxSetup({
			            async: true
			        });
		    		return this.optional(element) || (result);
					
				}, "该名称被注册!");
				
				
		 });
</script>
</head>
<body style="min-width:540px;overflow: auto; overflow:hidden">
	<div style="display:;" class="inputTable">
             	  <form  id="baseData" method="post">
						<table class="inputTable_liebiao inputTable_validate clear" style="width: 480px;">
							<tbody>
								<tr>
									<th>
										归属表: 
									</th>
									<td>
										<select class="formText" name="typeId" id="typeId" onchange="getCode();">
				   							<option value="">全部</option>
                   						   <c:forEach items="${list}" var="l" varStatus="status">
                    						  <option value="${l.typeId}" >${l.typeName}</option>
                   							</c:forEach>
										</select> 
									</td>
									<td><span style="color:red;font-size:20px">*</span></td>
								</tr>
								<tr>
									<th>
										名称: 
									</th>
									<td width="165px;">
												<input type="text"  class="formText" name="name" id="name" maxlength="20">
													
									</td>
									<td width="130px;"><span style="color:red;font-size:20px">*</span></td>
								</tr>	
								<tr>
									<th>
										业务码: 
									</th>
									<td>
                                      <input  type="text" class="formText"  id="code" disabled></input>
                                      <input type="hidden" id="nextCode" name="code"><span style="color: red; font-size: 12px">(选择相应的归属表)</span>
									</td>
								</tr>
								<tr>
									<th>
										备注: 
									</th>
									<td>
                                      <input  type="text" class="formText"  name="remark" maxlength="50"></input>
									</td>
								</tr>
							<tr>
								
                                <th>
                                </th>
                                <!-- <td><input type="button" class="submitButton" value="保存" onclick="check()"/></td> -->
                                <td><input type="submit" class="submitButton" value="保存"/></td>
							</tr>
						</tbody>
                       </table>
		     </form>

</div>
<!-------------------------------------------------------------------------------  -->













</body>
</html>