<%
    String systemPath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<script type="text/javascript" src="<%=basePath %>js/jquery/1.8.2/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/common/login.js"></script>
<script type="text/javascript" src="<%=basePath %>js/common/common.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=basePath %>plugin/ymPrompt/ymPrompt.js"></script>
<link href="<%=basePath %>plugin/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css"/>
<link type="text/css" rel="stylesheet" href="<%=basePath %>css/login.css"/>
<script type="text/javascript">
    var _path = "<%=systemPath %>";
    jQuery.noConflict();
</script>
