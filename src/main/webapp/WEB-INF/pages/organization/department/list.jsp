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
<%@ include file="/WEB-INF/pages/head/pagehead.ini"%>
<script type="text/javascript">
	var jqGrid = Class.create(BaseJqGrid,{  
		pager: "pager", //分页工具栏  
		   multiselect : true,	
		    multiboxonly:true,
	    url: _path+'departmentAction/list.do',
	    datatype: "json",
	    width:'1500',
	    height:465,
	    colModel:[
	            {label:'',name:'id',sortable:false,hidden:true},
	            {label:'职位编号',name:'departmentNo',index:'department_no', width:150,align:"center"},
	            {label:'职位名称',name:'name',sortable:false ,width:125,align:"center"},
	            {label:'备注',name:'remark',sortable:false, width:90,align:"center"}
	    ]
	});
	
	
	

	jQuery(function($) {
		var jGrid = new jqGrid();
		loadJqGrid("#tabGrid", "#pager", jGrid);
		var btnzj = jQuery("#insert");
		var btnbj = jQuery("#modify");
		var btnsc = jQuery("#delete");
		// 绑定增加点击事件
		if (btnzj != null) {
			btnzj.click(function() {
				showWindow('添加职位', 630,350,
						_path+'departmentAction/addView.do');
			});
		}
		
		if (btnbj != null) {
			btnbj.click(function() {
				var id = getChecked();
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
				showWindow('更新职位', 620,350,
						_path+'departmentAction/modifyView.do?id='
								+ id);
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
				var r=confirm("确定删除该职位吗?");
				if(r){
					var affirm = false;
					
							jQuery.ajax({
								url : _path + 'organizationAction/getDataByOrgId.do',
								type : "post",
								data : {'departmentId' : row.id},
								async : false,
								dataType : 'json',
								success : function(data) {
									if (data != null) {
										alert("该职位有配置部门,无法删除!<br/> <span style='color:green;'>(请删除部门配置之后，重试操作。)</span>");
										return;
									}
									affirm=true;
								}
							});
		
					if(affirm){
						jQuery.ajax({
							url:_path+'departmentAction/delete.do?id='+ id,
							type:"post",
							async:false,
							success:function(data){
								if(data=="success"){
									alert("删除成功");
									setTimeout("iFClose();", 1000);
								}else if(data == "fail"){
									alert("删除失败");
								}else{
									alert("异常！！请联系管理员");
								}
							}
						});
					}
					
		  			jQuery(window.document).find('#searchResult').click();
				}
			});
		}
		

	});

 	function searchResult() {
		var map = {};
		map["departmentNo"]=jQuery("#departmentNo").val();
		map["name"]=jQuery("#name").val();
		search('tabGrid',map);
	}
 </script>
</head>

<body>

	<div class="biaoge_head">
		<div class="biaoge_tittle02">职位管理</div>
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
					<th>职位编号:</th>
					<td><input type="text" id="departmentNo" name="departmentNo"
						class="search_jk" value=""></td>
					<th>职位名称:</th>
					<td><input type="text" id="name" name="name"
						class="search_jk" value=""></td>

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
