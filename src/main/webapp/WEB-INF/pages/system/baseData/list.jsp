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
	    url: _path+'baseDataAction/list.do',
	    datatype: "json",
	    width:'1500',
	    height:465,
	    colModel:[
	            {label:'ID',name:'id',index:'id', width:60,hidden:true},
	            {label:'名称',name:'name',index:'name', width:60},
	            {label:'归属表',name:'typeName',index:'type_name', width:90},
	            {label:'业务码',name:'code',index:'code', width:150},
	            {label:'备注',name:'remark',index:'remark', width:150}
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
				showWindow('添加基础数据', 530,370,_path+'baseDataAction/addView.do');
			});
		}
		//绑定更新点击事件
		if (btnbj != null) {
			btnbj.click(function() {
				var id = getChecked();
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				showWindow('更新基础数据', 600,300,_path+'baseDataAction/modifyView.do?id='+ row.id);
			});
		}
		//绑定删除点击事件
		if (btnsc != null) {
			btnsc.click(function() {
				var id = getChecked();
				if(id.length != 1){
					alert('请选定一条记录!');
					return;
				}
				var row = jQuery("#tabGrid").jqGrid('getRowData', id);
				var r = confirm("确定要删除此基础数据吗!");
				if(r){
				jQuery.ajax({
					url:_path+'baseDataAction/delete.do?id='+ row.id,
					type:"post",
					async:false,
					success:function(data){
						if(data=="success"){
							alert_auto("删除成功");
						}else if(data == "fail"){
							alert("删除失败");
						}else if(data == "false"){
							alert("删除失败！此数据正在被使用");
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
		map["name"] = jQuery("#name").val();
		map["typeId"] = jQuery("#typeId").val();	
		search('tabGrid',map);
	}
	
	
	/* function modify(type) {
		if(type=='update'){
			var id = getChecked();
			if(id.length != 1){
				alert('请选定一条记录!');
				return;
			}
			var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			showWindow('更新基础数据', 600,300,_path+'baseDataAction/modifyView.do?id='+ row.id);
		}else{
			showWindow('添加基础数据', 600,300,_path+'baseDataAction/addView.do');
		}
		
	}
	function deleteBaseData() {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', id);
		jQuery.ajax({
			url:_path+'baseDataAction/delete.do?id='+ row.id,
			type:"post",
			async:false,
			success:function(data){
				if(data=="success"){
					alert("删除成功");
				}else if(data == "fail"){
					alert("删除失败");
					
				}else{
					alert("异常！！请联系管理员");
				}
			}
	});
  		jQuery(window.document).find('#searchResult').click();
	} 
	
	jQuery(function($) {
		var jGrid = new jqGrid();
		loadJqGrid("#tabGrid", "#pager", jGrid);
		var btnzj = jQuery("#insert");
		var btnsc = jQuery("#delete");
		var btnmd = jQuery("#modify");
		// 绑定增加点击事件
		if (btnzj != null) {
			btnzj.click(function() {
				modify('add');
			});
		}
		if (btnsc != null) {
			btnsc.click(function() {
				deleteBaseData();
			});
		}
		if (btnmd != null) {
			btnmd.click(function() {
				modify('update');
			});
		}
	}); */
</script>
</head>
<body >



<!-- 右侧内容区域 -->
           <!-- 标题 -->
            <div class="biaoge_head">
             <div class="biaoge_tittle02">基础数据维护</div>
            </div>
            <!-- 头部按钮 -->


             <ul class="biaoge_butn">
           				 <c:forEach items="${btns}" var="b" >
           		  <li><a id="${b.btnId}" ><i class="${b.btnCss}"></i>${b.btnText}</a></li>
           </c:forEach>
           </ul>
            <!-- 填写搜索区域 -->
                    <div class="biaoge_tittle03" style="border:0;">
                        <table class="inputTable02">
							<tbody>

                                <tr>
									<th>
										名称：
									</th>
									<td>
												 <input type="text" class="search_jk" id="name"name="name"> 
												
									</td>
									<th>
										归属表：
									</th>
									<td>
												 <select class="search_jk" name="typeId" id="typeId">
				   											<option value="">全部</option>
                   						   				<c:forEach items="${list}" var="l" varStatus="status">
                    										  <option value="${l.typeId}" >${l.typeName}</option>
                   										</c:forEach>
											</select> 
												
									</td>
									<td>
									 <div class="button_weizhi">
                         				 <input name="" class="searchButton" value="" type="button" id="searchResult"  onclick="searchResult()"></input>
                       				 </div>
									</td>
								</tr>
                                
                            </tbody>
                        </table>
                        <!-- 搜索按钮 -->
                       
                  </div>
				<table id="tabGrid"></table>
				<div id="pager"></div>
   
    
            
             <!--<div class="biaoge_tittle03">
             <form action="" method="post">
             </form>
                名称：<input type="text" class="search_jk" id="name"name="name"> 
		归属表：

		 
		 <select class="search_jk" name="typeId" id="typeId">
				   							<option value="">全部</option>
                   						   <c:forEach items="${list}" var="l" varStatus="status">
                    						  <option value="${l.typeId}" >${l.typeName}</option>
                   							</c:forEach>
		</select> 

	
		 
		<input name="" class="searchButton" value="查询" id="searchResult" type="button" onclick="searchResult()"></input>
        <input name="" class="searchButton" value="添加"  type="button" onclick="modify('add')"></input>
        <input name="" class="searchButton" value="编辑"  type="button" onclick="modify('update')"></input>
		<input name="" class="searchButton" value="删除"  type="button" onclick="deleteBaseData()"></input>
		
             </div>
            <input type="hidden" id="id">
            <center>
				<table id="tabGrid"></table>
				<div id="pager"></div>
			</center>-->
   
	
</body>
</html>
