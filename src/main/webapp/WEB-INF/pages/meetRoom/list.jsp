<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<Html>
<Head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>
<script type="text/javascript">
	var meetRoomType=jQuery.parseJSON('${meetRoomTypes}');	
	var jqGrid = Class.create(BaseJqGrid,{  
		pager: "pager", //分页工具栏  
	    multiselect : true,	
	    multiboxonly:true,
	    url: _path+'meetRoom/list.do',
	    datatype: "json",
	    width:'1500',
	    height:465,
	    colModel:[
	            {label:'会议室编号',name:'meetRoomID',sortable:false, width:150,align:"center"},
	            {label:'会议室名称',name:'meetRoomName',sortable:false, width:150,align:"center"},
	            {label:'会议室类型',name:'meetRoomType',index:'meetRoomType',sortable:false, width:150,align:"center",formatter:function(cellvalue, options, rowObject){
	            	if(meetRoomType==""){
	            		return "-";
	            	}
	            	var val=meetRoomType[cellvalue];
	            	if(val!=null && val!="")
	            		return val;
	            	else
	            		return "-";
               	  }     
	            },
	            {label:'负责人',name:'person',sortable:false, width:150,align:"center"},
	            {label:'终端号',name:'terminalNo',sortable:false, width:150,align:"center"},
	            {label:'状态',name:'status',index:'status',sortable:false, width:150,align:"center",unformat:valFormat,formatter:function(cellvalue, options, rowObject){  
	                    if(cellvalue==0) {
	                    	return  "<span val='"+cellvalue+"'>启用</span>";
	                    }
	                    if(cellvalue==1) {
	                    	return "<span val='"+cellvalue+"'>禁用</span>";
	                    }
	                    return "-";
	            	}
	           	},
	           
	            {label:'创建人',name:'creator',sortable:false, width:150,align:"center"}
	    ]
	});
	
	
	function valFormat(cellvalue, options, cell){
		return jQuery("span",cell).attr("val");
	}

	jQuery(function($) {
		var jGrid = new jqGrid();
		loadJqGrid("#tabGrid", "#pager", jGrid);
		var btnzj = jQuery("#insert");
		var btnbj = jQuery("#modify");
		var btnzx = jQuery("#logout");
		var btncx = jQuery("#activation");
		var btnsc = jQuery("#delete");
		// 绑定增加点击事件
		if (btnzj != null) {
			btnzj.click(function() {
				showWindow('添加会议室', 640,580,
						_path+'meetRoom/addView.do');
			});
		}
		
		if (btnbj != null) {
			btnbj.click(function() {
				var id = getChecked();
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
				showWindow('更新会议室', 660,580,
						_path+'meetRoom/modifyView.do?id='
								+ id);
			});
		}
		
		// 绑定增加点击事件
		if (btnzx != null) {
			btnzx.click(function() {
				var id = getChecked();
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
			 var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			 var statu= jQuery("#tabGrid").getCell(id,'status');
			 if(statu=="1"){
				 alert("已注销!!!");
				 return;
			 }
			 jQuery.ajax({
					url:_path+'meetRoom/logOut.do?id='+id,
					type:"post",
					async:false,
					success:function(data){
						if(data=="success"){
							alert_auto("注销成功");
						}else if(data == "fail"){
							alert("注销失败");
						}else{
							alert("异常！！请联系管理员");
						}
					}
			}); 
		  		jQuery(window.document).find('#searchResult').click();
			});
		}
		
		// 绑定增加点击事件
		if (btncx != null) {
			btncx.click(function() {
				var id = getChecked();
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				var statu= jQuery("#tabGrid").getCell(id,'status');
				 if(statu=="0"){
					 alert("已启用!!!");
					 return;
				 }
			 jQuery.ajax({
					url:_path+'meetRoom/backOut.do?id='+id,
					type:"post",
					async:false,
					success:function(data){
						if(data=="success"){
							alert_auto("启用成功");
						}else if(data == "fail"){
							alert("启用失败");
							
						}else{
							alert("异常！！请联系管理员");
						}
					}
			}); 
		  		jQuery(window.document).find('#searchResult').click();
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
				var r=confirm("确定删除该会议室吗?");
				if(r){
				jQuery.ajax({
					url:_path+'meetRoom/delete.do?id='+ id,
					type:"post",
					async:false,
					success:function(data){
						if(data=="success"){
							alert_auto("删除成功");
						}else if(data == "exsit"){
							alert("该会议室存在交易记录,删除失败!");
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
		map["meetRoomID"] = jQuery("#meetRoomID").val();
		map["meetRoomName"] = jQuery("#meetRoomName").val();
		map["meetRoomType"] = jQuery("#meetRoomType").val();
		search('tabGrid',map);
	}
 </script>
</head>

<body>

	<div class="biaoge_head">
		<div class="biaoge_tittle02">会议室管理</div>
	</div>

	<ul class="biaoge_butn">
		<c:forEach items="${btns}" var="b">
			<li><a id="${b.btnId}"><i class="${b.btnCss}"></i>${b.btnText}</a></li>
		</c:forEach>
	</ul>
	<div class="biaoge_tittle03" style="border: 0;">
		<table class="inputTable02">
			<tbody>
				<tr>
					<th>会议室编号:</th>
					<td><input type="text" id="meetRoomID" name="meetRoomID" class="search_jk" value=""></td>
					
					<th>会议室名称:</th>
					<td><input type="text" id="meetRoomName" name="meetRoomName" class="search_jk" value=""></td>
				    	<th>
					会议室类型：
					</th>
					<td>
						<select name=meetRoomType id="meetRoomType" class="search_jk">
							<option value="">全部</option>
							<c:forEach items="${meetRoomTypess}" var="type">
								<option value="${type.code }" >${type.name }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<div class="button_weizhi">
							<input name="" class="searchButton" value="" type="button"
								id="searchResult" onclick="searchResult()"></input>
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
