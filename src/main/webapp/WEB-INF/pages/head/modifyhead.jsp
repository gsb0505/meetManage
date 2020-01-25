<%@ page import="com.kd.manage.base.BaseUri" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%
    String systemPath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path+"/" ;
%>
<%
    response.addHeader("Access-Control-Allow-Origin", "*");
    response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
%>
<link href="<%=basePath %>plugin/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/datePicker.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/home.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath %>css/styles.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/jquery/1.7.2/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-ui-1.7.2.custom.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugin/ymPrompt/ymPrompt.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.validate.js"></script>
<script type="text/javascript" src="<%=basePath %>js/common/messages_cn.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/owned/sea.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.fn/jquery.serializejson.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>js/owned/jquery.datalink2.js"></script>--%>
<%--<script type="text/javascript" src="<%=basePath%>js/owned/jquery.tmpl2.js"></script>--%>
<%--<script type="text/javascript" src="<%=basePath%>js/owned/js.js"></script>--%>
<script type="text/javascript">
    var _path = "<%=systemPath %>";
    var _core_path = "<%=BaseUri.corePath %>/";
    var _core_path_reource = _core_path +"/external/";
    jQuery.noConflict();

    seajs.config({
        'base': '<%=basePath %>js/'
    });
</script>
<script type="text/javascript" src="<%=basePath %>js/common/additional-methods.js"></script>