<%
    String systemPath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>


<link href="<%=basePath%>plugin/showLoading/showLoading.css" rel="stylesheet" media="screen"/>
<link href="<%=basePath %>plugin/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>css/datePicker.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>css/home.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>css/styles.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="<%=basePath %>js/jquery/1.8.2/jquery-1.8.2.min.js"></script>

<script type="text/javascript" src="<%=basePath %>js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=basePath%>plugin/showLoading/jquery.showLoading.js"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		
