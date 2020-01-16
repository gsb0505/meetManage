<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path+"/" ;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<meta content="zh-CN" http-equiv="Content-Language"></meta>

<meta content="IE=edge" http-equiv="X-UA-Compatible"></meta>
<meta content="后台管理中心" name="description"></meta>
<meta content="后台，管理，服务，平台，中心" name="keywords"></meta>
<meta content="版权所有" name="Copyright"></meta>
<title>后台管理中心</title>
<%@ include file="/WEB-INF/pages/head/pagehead.jsp"%>
<script type="text/javascript" src="<%=basePath %>js/menu-mal.js"></script> 
<script type="text/javascript">
var userId = "${user.userId}";
</script>
</head>
<style>
    .coolscrollbar{scrollbar-arrow-color:#fafcff;scrollbar-base-color:#fafcff;}
</style>
 <body id="loading">
<input type="hidden" value="${userId}" name="userId" id="userId">
<input type="hidden" value="${userId}" id="index_user_id">
<input type="hidden"  id="sc_menuId">
<input type="hidden"  id="sc_menuUrl">
    <div class="head">
       <div class="head_content">

          <div class="head_logo">
             <a href="<%=basePath %>" target="_blank"><img src="../image/logo.jpg" width="60%"></a>
          </div>
          <input type = "hidden" id = "roleId"/>

          <div class="head_set" >
            <ul>
                <li>
                    <i class=""></i>
                    <a href="#">登录名:${userName}</a>
                </li>
               <li>
               <i class="head_set_icon_01"></i>
                  <a href="#" onclick="resetPwd()">修改密码</a>
               </li>
               <li>
                  <i class="head_set_icon_04"></i>
                  <a href="#" onclick="logout()">注销</a>
               </li>
            </ul>
          </div>
          <div class="head_user">
                <span style="float:left; margin-right:5px;"><img src="../image/user_headerimg.png" width="32" height="32" /></span>
                <p class="fz_f01"><label  id="contexttt"></label></p>
           </div>
       </div>
    </div>
       <div id="menu" class="menu_con">
            <ul id="topMenu">
            </ul>
       </div>
       
    
    <div class="content_box" id="left_menu">
      <!--左侧-->
	
	 <span class="ss_icon" id="showhide" onclick="showhide()"></span>
       <div class="con_left" id="con_left">
          <div class="con_tittle">
               <p class="tittle_style" id="titleOne"></p>
          </div>
            <!--左侧列表栏-->
                    <ul id="expmenu-freebie">
                        <li>
                            <ul class="expmenu" id="leftM" >
                            </ul>
                        </li> 
                    </ul>
            <!--end 左侧列表栏-->
       </div>
	</div>
      <!--右侧内容-->
        <div class="con_right" id="iframe" style="width:84.4%;">

            <div class="">

                <p>&nbsp;&nbsp;欢迎您使用会议预约系统</p>

            </div>
            <p>&nbsp;</p>
        </div>
    </div>


 
<div class="footer_content">

   <div class="cscopy">
     <p>版权所有：杭州铭业管网科技有限公司&nbsp;&nbsp;浙ICP备20001587号 </p>
    <!--  <p>联系我们：</p> -->
   </div>
 </div>
</body> 
<script type="text/javascript" src="<%=basePath%>pageJs/index.js?vs=<%=System.currentTimeMillis()%>"></script>
</html>
      