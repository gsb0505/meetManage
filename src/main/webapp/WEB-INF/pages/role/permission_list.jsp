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
<link href="<%=basePath %>css/ui.jqgrid.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath %>css/jquery-ui-1.7.1.custom.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath %>js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery-ui-1.7.2.custom.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/grid.locale-cn.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>plugin/ymPrompt/ymPrompt.js"></script>
		<link href="<%=basePath %>plugin/ymPrompt/skin/zfstyle/ymPrompt.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath %>js/common/common.js"></script>
<script type="text/javascript">
		  $(document).ready(function () {
			  jQuery("#list").jqGrid({
	        		url: '<%=basePath%>pages/role/list.do',
	                datatype: "json",
	                width:'1500',
	                colNames:['id','角色代码','角色描述', '备注'],
	                colModel:[
	                        {name:'id',index:'id', width:60},
	                        {name:'roleCode',index:'roleCode', width:60},
	                        {name:'roleDesc',index:'roleDesc', width:90},
	                        {name:'remark',index:'remark', width:150},
	                ],
			       	rowNum:10,
			        rowList: [10, 20, 30],
	                pager: '#pager',
	                sortname: 'id',
	                viewrecords:true,
	                sortorder: "desc",
	                onSelectRow: function(id){ 
	                	  showWindow('权限赋值',610,500,"<%=basePath%>pages/role/permission.do?id="+id);
	                },
	                jsonReader: {
			        	id: "0",
			        	repeatitems: false
			        	},
	                caption: "角色列表",
	                height: '100%',
	                editParams:{
	                    aftersavefunc: function( rowid, response ){
	                    	alert("回调成功");
	                    }
	                }
	        });
		jQuery("#list").jqGrid('navGrid','#pager',{edit:false,add:false,del:false});
			
			    });     
	
		 
		 

</script>
</head>
<body >

	<center>
	<table id="list" ></table>
	<div id="pager"></div>
	</center>
</body>
</html>