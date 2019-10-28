<%@ taglib prefix="t" uri="/ui-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>

<c:set var="webRoot" value="<%=basePath%>" />
<c:set var="commonType1" value="1" />
<c:set var="commonType2" value="2" />
<c:set var="commonType3" value="3" />
<c:set var="commonType4" value="4" />
<c:set var="commonType5" value="5" />
<c:set var="commonType6" value="6" />
<c:set var="commonType7" value="7" />
<c:set var="commonType8" value="8" />
<c:set var="commonType9" value="9" />
<c:set var="commonType13" value="13" />
<c:set var="commonType14" value="14" />
<c:set var="commonType18" value="18" />