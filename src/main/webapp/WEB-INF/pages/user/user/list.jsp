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
	var jqGrid = Class.create(BaseJqGrid,{  
		pager: "pager", //分页工具栏  
		   multiselect : true,	
		    multiboxonly:true,
	    url: _path+'userAction/list.do',
	    datatype: "json",
	    width:'1500',
	    height:465,
	    colModel:[

	            {label:'姓名',name:'userId',index:'userId', width:90,align:"center"},
	            {label:'部门',name:'orgName',index:'orgName', width:150,align:"center"},
	            {label:'职位',name:'depName',index:'depName', width:150,align:"center"},
	            {label:'联系方式',name:'phone',index:'phone', width:125,align:"center"},
	            {label:'邮箱',name:'email',index:'e_mail', width:150,align:"center"},
	            {label:'角色',name:'roles',index:'roles', width:125,align:"center"},
	            {label:'备注',name:'remark',index:'remark', width:90,align:"center"},
	            {label:'状态',name:'flag',index:'flag', width:80,align:"center",formatter:function(cellvalue, options, rowObject){  
                    if(cellvalue==0) {
                    	return "注销";
                    }
                    if(cellvalue==1) {//1为正常  0为注销
                    	return "正常";
                    }
                }     },
                {label:'是否加锁',name:'errNum', width:80,align:"center",sortable:false}
	           /*  {label:'操作者',name:'creatorName',index:'creatorName', width:125,align:"center"},
	            {label:'用户编号',name:'userNO',index:'userNO', width:90,align:"center"},
	            {label:'上级用户编号',name:'pUserNO',index:'pUserNO', width:90,align:"center"} */
	    ]
	});
	
	
	

	jQuery(function($) {
		var jGrid = new jqGrid();
		loadJqGrid("#tabGrid", "#pager", jGrid);
		var btnzj = jQuery("#insert");
		var btnbj = jQuery("#modify");
		var btnzx = jQuery("#logout");
		var btncx = jQuery("#activation");
		var reset = jQuery("#reset");
		var btnjs = jQuery("#unlock");
		// 绑定增加点击事件
		if (btnzj != null) {
			btnzj.click(function() {
				showWindow('添加员工', 700,350,
						_path+'userAction/addView.do');
			});
		}
		
		if (btnbj != null) {
			btnbj.click(function() {
				var id = getChecked();
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
				showWindow('更新员工', 700,350,
						_path+'userAction/modifyView.do?id='
								+ id);
			});
		}
		
		// 绑定解锁点击事件
		if (btnjs != null) {
			btnjs.click(function() {
				var id = getChecked();
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				var errNum= jQuery("#tabGrid").getCell(id,'errNum');
				if(errNum == "未锁"){
					alert("员工未锁定， 无需解锁!");
					return; 
				}
				jQuery.ajax({
					url:_path+'userAction/jiesuo.do',
					type:"post",
					data:{userId:row.userId},
					async:false,
					success:function(data){
						if(data=="success"){
							alert("解锁成功");
						}else if(data == "fail"){
							alert("解锁失败");
							
						}else{
							alert("异常！！请联系管理员");
						}
					}
			}); 
		  		jQuery(window.document).find('#searchResult').click();
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
			 jQuery.ajax({
					url:_path+'userAction/logOut.do?id='+id,
					type:"post",
					async:false,
					success:function(data){
						if(data=="success"){
							alert("注销成功");
						}else if(data == "fail"){
							alert("注销失败");
						}else if(data=="current"){
							alert("当前登入员工，不能注销！");
						}else{
							alert("异常！！请联系管理员");
						}
					}
			}); 
		  		jQuery(window.document).find('#searchResult').click();
			});
		}
		
		// 绑定增加点击事件
		if (reset != null) {
			reset.click(function() {
				if(!confirm("真的要重置吗？？？")){
					 return;
				 }
				var id = getChecked();
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			 jQuery.ajax({
					url:_path+'userAction/reset.do?id='+id,
					type:"post",
					async:false,
					success:function(data){
						if(data=="success"){
							alert("重置密码成功");
						}else if(data == "fail"){
							alert("重置密码失败");
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
			 jQuery.ajax({
					url:_path+'userAction/backOut.do?id='+id,
					type:"post",
					async:false,
					success:function(data){
						if(data=="success"){
							alert("启用成功");
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

	});

 	function searchResult() {
		var map = {};
		map["userId"] = jQuery("#userId1").val();
		map["flag"] = jQuery("#flag").val();
		search('tabGrid',map);
	}

 </script>
</head>

<body>

	<div class="biaoge_head">
		<div class="biaoge_tittle02">员工管理</div>
	</div>

	<ul class="biaoge_butn">
		<c:forEach items="${btns}" var="b">
			<li><a id="${b.btnId}"><i class="${b.btnCss}"></i>${b.btnText}</a></li>
		</c:forEach>
	</ul>
	<!--  <input type="button" value="重置密码" id="reset"/> -->
	<div class="biaoge_tittle03" style="border: 0;">
		<table class="inputTable02">
			<tbody>
				<tr>
					<th>姓名:</th>
					<td><input type="text" id="userId1" name="userId1"
						class="search_jk" value=""></td>

					<th>状态：</th>
					<td><select name="flag" id="flag" class="search_jk">
							<option value="">全部</option>
							<option value="1">正常</option>
							<option value="0">已注销</option>
					</select></td>
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


	<script type="text/javascript" src="<%=basePath%>pageJs/common/list.view.frame.js"></script>
</body>
</html>
