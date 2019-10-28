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
<script type="text/javascript" src="<%=basePath %>js/owned/download.js"></script>
<script type="text/javascript">
	var jqGrid = Class.create(BaseJqGrid,{  
		pager: "pager", //分页工具栏  
		   multiselect : true,	
		    multiboxonly:true,
	    url: _path+'organizationAction/list.do',
	    datatype: "json",
	    width:'1500',
	    height:465,
	    colModel:[
	            {label:'',name:'id',hidden:true},
	            {label:'部门编号',name:'agencyNo',index:'agency_no', width:150,align:"center"},
	            {label:'名称',name:'name',sortable:false, width:125,align:"center"},
	            {label:'备注',name:'remark',sortable:false, width:150,align:"center"},
	            {label:'操作',name:'',sortable:false, width:30,align:"center",formatter:addCommConfig}
	    ]
	});
	
	
	function addCommConfig(cellvalue, options, rowObject){
		return "<span class='btn_st01'><img src='../image/btn_zj_small.png' onclick='toPage("+rowObject.id+")' /></span>";
	}
	function toPage(id){
		console_log(id);
		showWindow('职位配置', 850,570,
				_path+'organizationAction/cinfigView.do?orgId='+id);
	}

	jQuery(function($) {
		var jGrid = new jqGrid();
		loadJqGrid("#tabGrid", "#pager", jGrid);
		var btnzj = jQuery("#insert");
		var btnbj = jQuery("#modify");
		var btnsc = jQuery("#delete");
		var btnex = jQuery("#export");
		// 绑定增加点击事件
		if (btnzj != null) {
			btnzj.click(function() {
				showWindow('添加部门', 700,350,
						_path+'organizationAction/addView.do');
			});
		}
		
		if (btnbj != null) {
			btnbj.click(function() {
				var id = getChecked();
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
				showWindow('更新部门', 700,350,
						_path+'organizationAction/modifyView.do?id='
								+ id);
			});
		}
		if (btnex != null) {
			btnex.click(function() {
				jQuery.ajax({
					url :  '<%=basePath%>organizationAction/exportOrgList.do',
					type : "post",
					async : false,
					success : function(data) {
						jQuery("html").downloads({fileName:data,type:"ex"});
					}
				});
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
				var r=confirm("确定删除该部门吗?");
				if(r){
					var affirm = false;
	
							jQuery.ajax({
								url : _path + 'organizationAction/getDataByOrgId.do',
								type : "post",
								data : {'orgId' : row.id},
								async : false,
								dataType : 'json',
								success : function(data) {
									if (data != null) {
										alert("该部门下有配置职位,无法删除!<br/> <span style='color:green;'>(请删除该职位配置之后，重试操作。)</span>");
										return;
									}
									affirm=true;
								}
							});
					
					
					if(affirm){
						jQuery.ajax({
							url:_path+'organizationAction/delete.do?id='+ id,
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
		map["agencyNo"]=jQuery("#agencyNo").val();
		map["name"]=jQuery("#name").val();
		search('tabGrid',map);
	}
 </script>
</head>

<body>

	<div class="biaoge_head">
		<div class="biaoge_tittle02">部门管理</div>
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
					<th>部门编号:</th>
					<td><input type="text" id="agencyNo" name="agencyNo"
						class="search_jk" value=""></td>
					<th>部门名称:</th>
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
