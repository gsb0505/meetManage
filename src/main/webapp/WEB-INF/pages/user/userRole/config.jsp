<%@ page language="java" contentType="text/html; charset=utf-8"
    import="java.util.*"
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

		var jqGrid = Class.create(BaseJqGrid,{  
			pager: "pager", //分页工具栏  
		    multiselect : true,
		    url: _path+'rolesAction/list.do',
		    width:'200',
		    datatype: "json",
		    colModel:[
		            {label:'编号',name:'id',sortable:false, width:60,hidden:true},
		            {label:'角色编码',name:'roleCode',sortable:false, width:30},
		            {label:'角色名称',name:'roleDesc',sortable:false, width:100},
		            {label:'备注',name:'remark',sortable:false, width:40}
		    ],
		    loadComplete: function () { 
		    	var userId = jQuery("#userId").val();
		    	jQuery.ajax({
					type:"POST",
					url:_path+'userRolesAction/getRole.do?userId='+encodeURI(userId, "UTF-8"),
					 async:false, 
					success:function(data){
						if(data=="exception"){
							alert("异常！！请联系管理员");
						}
						var ids =[];
						ids = data.split(",");
						for(i=0;i<ids.length;i++){
							jQuery('#tabGrid').jqGrid('setSelection',ids[i]);
						}
						
					}
			});
		    	
			}
		});
		
		
		jQuery(function($) {
			var jGrid = new jqGrid();
			loadJqGrid("#tabGrid", "#pager", jGrid);
		});
		
		
		function searchResult() {
			var map = {};
			map["roleDesc"] = jQuery("#roleDesc").val()
			search('tabGrid',map);
		}
		
		
		function configUserRole(){
			 
			var list = getRowData();
			var userId = jQuery("#userId").val();
			if(list.length<=0){
				
				jQuery.ajax({
					type:"POST",
					url:_path+'userRolesAction/configNull.do?userId='+encodeURI(userId),
					 async:false, 
					success:function(data){
						if(data=="success"){
							alert("修改成功");
							setTimeout("iFClose();", 1000);
						}else if(data == "fail"){
							alert1("修改失败");
							
						}else{
							alert1("异常！！请联系管理员");
							iFClose();
						}
					}
			});
				
			}
			if(list.length>0){
				var codeList = [];
				for(i=0;i<list.length;i++){
					var code = list[i].roleCode;
					codeList.push(code);
				}
				
				jQuery.ajax({
					type:"POST",
					url:_path+'userRolesAction/config.do?userId='+encodeURI(userId),
					data:{"codeList":codeList},
					 async:false, 
					success:function(data){
						if(data=="success"){
							alert("保存成功");
							setTimeout("iFClose();", 1000);
						}else if(data == "fail"){
							alert("保存失败");
						}else{
							alert("异常！！请联系管理员");
							setTimeout("iFClose();", 1000);
						}
					}
			});
			
			}
			     
				 jQuery(parent.window.document).find('#searchResult').click(); 
				
				
			}
</script>
</head>
<body>

           
           <ul class="biaoge_butn">
               <li><a id="configUserRole" onclick="configUserRole()"><i class="r_button r_button02 common_icon"  ></i>保存</a></li>
           </ul>
           
                       <div class="biaoge_tittle03" style="border:0;">
                        <table class="inputTable02">
							<tbody>
								<tr>
									<th>
										角色名称：
									</th>
									<td>
											<input type="text" id="roleDesc" name="roleDesc" class="search_jk" value="">
												
									</td>
									<td>
										 <div class="button_weizhi">
                          					<input name="" class="searchButton" value="" type="button" id="searchResult"  onclick="searchResult()"></input>
                       					 </div>
									</td>									
								</tr>
                                
                            </tbody>
                        </table>
                        <input type="hidden" value="${userId}" name="userId" id="userId">
             </div>
              <center>
				<table id="tabGrid"></table>
				<div id="pager"></div>
			</center>

</body>

</html>