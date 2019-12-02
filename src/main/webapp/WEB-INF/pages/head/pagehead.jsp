<%
    String systemPath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>


<link href="<%=basePath %>css/FusionCharts.css" rel="stylesheet" type="text/css"/>

<link href="<%=basePath %>css/ui.jqgrid.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath %>css/jquery-ui-1.7.1.custom.css" rel="stylesheet" type="text/css"/>
<link href="<%=basePath%>plugin/showLoading/showLoading.css" rel="stylesheet" media="screen"/>
<link href="<%=basePath %>plugin/ymPrompt/skin/qq/ymPrompt.css" rel="stylesheet" type="text/css"/>

<link href="<%=basePath %>css/datePicker.css" rel="stylesheet" type="text/css"/>

<link href="<%=basePath %>css/home.css" rel="stylesheet" type="text/css"/>

<link href="<%=basePath %>css/styles.css" rel="stylesheet" type="text/css"/>


<script type="text/javascript" src="<%=basePath %>js/FusionCharts.js"></script>

<script type="text/javascript" src="<%=basePath %>js/jquery/1.8.2/jquery-1.8.2.min.js"></script>

<script type="text/javascript" src="<%=basePath %>js/jquery.tools.min.js"></script>

<script type="text/javascript" src="<%=basePath %>js/jquery-ui-1.7.2.custom.min.js"></script>

<script type="text/javascript" src="<%=basePath %>js/prototype.js"></script>

<script type="text/javascript" src="<%=basePath %>js/grid.locale-cn.js"></script>


<script type="text/javascript" src="<%=basePath %>js/jquery.validate.min.js"></script>

<script type="text/javascript" src="<%=basePath %>js/common/messages_cn.js"></script>

<script type="text/javascript" src="<%=basePath %>js/jquery.jqGrid.min.js"></script>

<!--<script type="text/javascript" src="<%=basePath %>js/jquery.metadata.js"></script>-->

<script type="text/javascript" src="<%=basePath %>js/jquery.date_input.pack.js"></script>

<script type="text/javascript" src="<%=basePath %>plugin/my97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=basePath %>plugin/ymPrompt/ymPrompt.js"></script>
<script type="text/javascript" src="<%=basePath %>js/owned/sea.js"></script>


<script type="text/javascript" src="<%=basePath %>js/common/common.js"></script>

<script type="text/javascript" src="<%=basePath %>js/common/jqgridCommon.js"></script>

<script type="text/javascript" src="<%=basePath %>js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=basePath%>plugin/showLoading/jquery.showLoading.js"></script>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
    var _path = "<%=systemPath %>";
    jQuery.noConflict();

    seajs.config({
        'base': '<%=basePath %>js/'
    });
</script>
<script type="text/javascript" src="<%=basePath %>js/common/additional-methods.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jquery.fn/jquery.serializejson.js"></script>