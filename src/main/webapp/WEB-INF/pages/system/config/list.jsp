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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="/WEB-INF/pages/head/pagehead.ini"%>
<title>Insert title here</title>
<script>
var jqGrid = Class.create(BaseJqGrid, {
	pager: "pager", //分页工具栏  
    multiselect : true,	
    multiboxonly:true,
    url: _path+'ConfigAction/query.do',
    datatype: "json",
    width:'1520',
    height:465,
    colModel:[
			{label:'ID',name:'id', width:10,hidden:true,show:false},
            {label:'配置Code',name:'code',index:'code', width:60,align:'center'},
            {label:'配置',name:'num',index:'num', width:90,align:'center'},
            {label:'配置描述',name:'remark',index:'remark', width:90,align:'center'},
            {label:'配置备注',name:'mark',index:'mark',hidden:true,width:90,align:'center'}
    ]
})
jQuery(function($) {
	var jGrid = new jqGrid();
	loadJqGrid("#tabGrid", "#pager", jGrid);
	var btnzj = jQuery("#insert");
	var btnbj = jQuery("#modify");
	var btnsc = jQuery("#delete");
	// 绑定增加点击事件
	if (btnzj != null) {
		btnzj.click(function() {
			showWindow('添加数据', 630,320,_path+'ConfigAction/addView.do');
		});
	}
	if(btnbj!=null){
		btnbj.click(function() {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		 var row = jQuery("#tabGrid").jqGrid('getRowData', id);
		 showWindow('更新数据', 610,320,_path+'ConfigAction/modifyView.do?id='+row.id);
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
			var r=confirm("确定删除该系统配置吗?");
			if(r){
			jQuery.ajax({
				url:_path+'ConfigAction/delete.do?id='+ row.id,
				type:"post",
				async:false,
				success:function(data){
					if(data=="success"){
						alert_auto("删除成功");
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
	
})
function searchResult() {
		var map = {};
		map["num"] = jQuery("#num").val();
		map["remark"] = jQuery("#remark").val();	
		search('tabGrid',map);
	}
</script>
</head>
<body>
<div class="biaoge_head">
             <div class="biaoge_tittle02">系统配置维护</div>
            </div>
             <!-- 头部按钮 -->
             <ul class="biaoge_butn">
           		<c:forEach items="${btns}" var="b" >
           		  <li><a id="${b.btnId}" ><i class="${b.btnCss}"></i>${b.btnText}</a></li>
           		</c:forEach>
            </ul>
            <div class="biaoge_tittle03" style="border:0;">
	             <table class="inputTable02">
					<tbody>
	
	                    <tr>
							<td>
								配置:
							</td>
							<td>
										 <input type="text" class="search_jk" id="num"name="num"> 
							</td>
							<td>
								描述：
							</td>
							<td>
										 <input type="text" class="search_jk" id="remark"name="remark"> 
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
     <table id="tabGrid"></table>
     <div id="pager"></div>
</body>
</html>