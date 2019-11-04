<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
<%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>
<script type="text/javascript">
				var jqGrid = Class.create(BaseJqGrid,{  
					pager: "pager", //分页工具栏  
					   multiselect : true,	
					    multiboxonly:true,
				    url: _path+'userRolesAction/list.do',
				    datatype: "json",
				    height:465,
				    jsonReader: {   
				    	id:"userId",
						root: "rows",
						page: "currentPage",
						total: "totalPage",
						records: "totalResult",    
						repeatitems : false      
					},
				    colModel:[
				            {label:'姓名',name:'userId',index:'userId', width:60,align:"center"},
				            {label:'拥有角色',name:'roleDesc',index:'roleDesc', width:125,align:"center"}
				           
				    ]
				});
				
				
				jQuery(function($) {
					var jGrid = new jqGrid();
					loadJqGrid("#tabGrid", "#pager", jGrid);
					var btnpz = jQuery("#config");
					// 绑定增加点击事件
					if (btnpz != null) {
						btnpz.click(function() {
							 var userId = getChecked();
							 if(userId.length != 1){
								alert('请选定一条记录!');
								return;
							 }
							 if($("#"+userId+" td:last").text()=="商户角色"){
								 alert("商户角色用户不能配置和修改角色");
							 }else{
								
								showWindow('配置角色',1050,590,
										_path+'userRolesAction/configView.do?userId='
												+ userId);
							 }
						});
					}

				});
				
				
				function searchResult() {
					var map = {};
					map["userId"] = jQuery("#userIdRelation").val()
					map["roleCode"] = jQuery("#roleCode").val()
					search('tabGrid',map);
				}

</script>
</head>
<body >
           <div class="biaoge_head">
             <div class="biaoge_tittle02">用户角色关系维护</div>
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
										用户名：
									</th>
									<td>
												 <input type="text" value="" id="userIdRelation"name="userId" class="search_jk">
												
									</td>
									<td>
										 <div class="button_weizhi">
                          					<input name="" class="searchButton" value="" type="button" id="searchResult"  onclick="searchResult()"></input>
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
</body>
</html>