<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/" ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>FusionCharts Free Documentation</title>
<%@ include file="/WEB-INF/pages/head/pagehead.ini"%>
<script type="text/javascript">
	var jqGrid = Class.create(BaseJqGrid,{  
		pager: "pager", //分页工具栏  
	    url: _path+'queryLogAction/list.do',
	    datatype: "json",
	    height: 615,
	    width:1500,
	    multiselect : false,
	    colModel:[
	            {label:'用户ID',name:'userId',index:'userId', width:90,align:"center"},
	            {label:'日志类型',name:'type',index:'type', width:90,align:"center",formatter:function(cellvalue, options, rowObject){  
                    if(cellvalue==0) {
                    	return "管理平台";
                    }
                    if(cellvalue==1) {//1为开  2为关
                    	return "外部接口";
                    }
                }     },
	            {label:'用户IP',name:'userIp',index:'user_ip', width:90,align:"center"},
	            {label:'操作时间',name:'operTime',index:'oper_time', width:150,align:"center",formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'}},
	            {label:'跳转action',name:'action',index:'action', width:150,align:"center"},
	            {label:'结果',name:'result',index:'result', width:150,align:"center"},
	            {label:'备注',name:'remark',index:'remark', width:150,align:"center",sortable:false}
	    ]
	});
	
	
	jQuery(function($) {
		var jGrid = new jqGrid();
		loadJqGrid("#tabGrid", "#pager", jGrid);
	});
	
	
	 /* jQuery(function(){
		jQuery('#enterDateFrom').date_input();
		jQuery('#enterDateTo').date_input();
		})  */
	
	function searchResult(){
		 var map = {};
		map["enterDate"] = jQuery("#enterDate").val()
		map["userId"] = jQuery("#userIdLog").val()
		map["type"] = jQuery("#type").val()
		map["enterDateFrom"] = jQuery("#enterDateFrom").val()
		map["enterDateTo"] = jQuery("#enterDateTo").val()
		search('tabGrid',map); 
	}


	
</script>
</head>

<body>
 <div class="biaoge_head">
             <div class="biaoge_tittle02">日志查询</div>
           </div>
           				 <div class="biaoge_tittle03" style="border:0;">
                        <table class="inputTable02">
								<tbody>
								<tr>
									<th>
										选择时间：
									</th>
									<td>
												 <input name="enterDateFrom"  id="enterDateFrom" class="search_jk" type="text" onClick="WdatePicker()">
									</td>
									<td>至</td>
									<td>
													<input name="enterDateTo"  id="enterDateTo" class="search_jk" type="text" onClick="WdatePicker()">
									</td>
									<th>
										日志类型：
									</th>
									<td>
												 <select name="type"  id="type" class="search_jk" >
												 <option value="0">管理平台</option>
												 <option value="1">外部接口</option>
												 </select>
												
									</td>
									<th>
										用户ID：
									</th>
									<td>
												 <input name="userId"  id="userIdLog" class="search_jk" value="" type="text">
												
									</td>
									<td>
										 <div class="button_weizhi">
                          				<input  class="searchButton"  type="button"   onclick="searchResult()"></input>
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
