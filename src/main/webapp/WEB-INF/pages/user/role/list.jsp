<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/" ;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<Html>
<Head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/pages/head/pagehead.ini"%>
		<script type="text/javascript">
			 var jqGrid = Class.create(BaseJqGrid,{  
					pager: "pager", //分页工具栏  
					   multiselect : true,	
					    multiboxonly:true,
				    url: _path+'rolesAction/list.do',
				    datatype: "json",
				    width:'1500',
				    height:465,
				    colModel:[
				            {label:'角色编码',name:'roleCode',index:'role_code', width:90,align:"center"},
				            {label:'角色名称',name:'roleDesc',index:'role_desc', width:150,align:"center"},
				            {label:'备注',name:'remark',index:'remark', width:90,align:"center"}
				    ],
				    ondblClickRow: function(id){ 
	                	  showWindow('权限赋值',610,500,_path+"pages/role/permission.do?id="+id);
	                }
				});


			 jQuery(function($) {
					var jGrid = new jqGrid();
					loadJqGrid("#tabGrid", "#pager", jGrid);
					var btnzj = jQuery("#insert");
					var btnpz = jQuery("#configPer");
					var btnbj = jQuery("#modify");
					var btnsc = jQuery("#delete");
					// 绑定增加点击事件
					if (btnzj != null) {
						btnzj.click(function() {
							showWindow('添加角色',600,295,_path+"rolesAction/addView.do");
						});
					}

					if (btnpz != null) {
						btnpz.click(function() {
							var id = getChecked();
							if(id.length != 1){
								alert('请选定一条记录!');
								return;
							}
							showWindow('权限赋值',610,500,_path+"pages/role/permission.do?id="+id);
						});
					}
					
					// 绑定修改点击事件
					if (btnbj != null) {
						btnbj.click(function() {
							var id = getChecked();
							if(id.length != 1){
								alert('请选定一条记录!');
								return;
							}
							var row = jQuery("#tabGrid").jqGrid('getRowData', id);
							showWindow('更新用户', 700,350,
									_path+'rolesAction/modifyView.do?roleCode='
											+ row.roleCode);
						});
					}
					
					if(btnsc!=null){
						btnsc.click(function() {
							var id = getChecked();
							if(id.length != 1){
								alert('请选定一条记录!');
								return;
							}
							var row = jQuery("#tabGrid").jqGrid('getRowData', id);
							var r=confirm("确定删除该角色吗?");
							if(r){
								jQuery.ajax({
									url:_path+'rolesAction/deleteByRoleID.do?rid='+ id,
									type:"post",
									async:false,
									success:function(data){
										if(data=="success"){
											alert_auto("删除成功");
										}else if(data == "exsit"){
											alert("该角色下存在用户,删除失败!");
										}else if(data == "fail"){
											alert("删除失败");
										}else{
											alert("异常！！请联系管理员");
										}
									}
								});
						  		jQuery(window.document).find('#searchResult').click();
							}
						});
					}
				});
		  
				function searchResult() {
					var map = {};
					map["roleDesc"] = jQuery("#roleDesc").val();
					search('tabGrid',map);
				}
		
		</script>
</HEAD>
<BODY>
           <div class="biaoge_head">
             <div class="biaoge_tittle02">角色管理</div>
           </div>
           
           <ul class="biaoge_butn">
                 <c:forEach items="${btns}" var="b" >
           		  	<li><a id="${b.btnId}" ><i class="${b.btnCss}"></i>${b.btnText}</a></li>
           		 </c:forEach>
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
                          					<input  class="searchButton"  type="button" id="searchResult"  onclick="searchResult()"></input>
                       					 </div>
									</td>
								</tr>
                                
                            </tbody>
                        </table>
                       
             </div>
              <center>
				<table id="tabGrid"></table>
				<div id="pager"></div>
			</center>
</BODY>
</HTML>
