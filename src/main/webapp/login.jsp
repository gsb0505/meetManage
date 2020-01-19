<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<meta content="zh-CN" http-equiv="Content-Language"></meta>

<meta content="IE=edge" http-equiv="X-UA-Compatible"></meta>
<meta content="" name="description"></meta>
<meta content="后台，管理，服务，平台，查账" name="keywords"></meta>
<meta content="上海电科智能系统股份有限公司版权所有" name="Copyright"></meta>
<%@ include file="/WEB-INF/pages/head/login.jsp"%>
<title>用户登录</title>
<script type="text/javascript">
	document.onkeyup = function(e) {
		if (e == null) { // ie  
			_key = event.keyCode;
		} else { // firefox              //获取你按下键的keyCode  
			_key = e.which; //每个键的keyCode是不一样的  
		}

		if (_key == 13) { //判断keyCode是否是13，也就是回车键(回车的keyCode是13)  
			//if (validator(document.loginform)){ //这个因该是调用了一个验证函数  
			document.getElementById('btnLogin').click(); //验证成功触发一个Id为btnLogin的  
			//}                                                                        //按钮的click事件，达到提交表单的目的  
		}
	}
	
	// 消除session过期打会login页面跳不出html frameset框架的问题
	if (window.self != window.top) {
		top.location.href = location.href;
    }
	
	(function($){
		jQuery.ajaxSetup ({cache:true});
		//已登入跳转
		var user=jQuery.cookie('user');
		if(user){
			jQuery.ajax({
				url : _path + 'loginAction/validUser.do?userId='
					+ user,
				async : false,
				success : function(data) {
					if(data=="true"){
						window.location.href = _path + "loginAction/login.do";
					}
				}
			});
		}
		jQuery.ajaxSetup ({cache:false});
	});
	
	var flag = "";
	function validateRandom(randomValue) {

		if (randomValue.length == 4) {
			jQuery.ajax({
				url : _path + 'loginAction/validRandom.do?randomValue='
						+ randomValue,
				async : false,
				success : function(data) {
					flag = data;
					if (data == "success") {
						jQuery("#isSuccess").text("验证成功");
						jQuery("#isSuccess").attr("class", "tishi02");
					} else if (data == "fail") {
						jQuery("#isSuccess").text("验证失败");
						jQuery("#isSuccess").attr("class", "tishi01");
					} else {
						jQuery("#isSuccess").text("重新输入");
						jQuery("#isSuccess").attr("class", "tishi01");
					}
				}
			});
			return;
		}

		if (randomValue.length < 4) {
			flag = "fail";
			jQuery("#isSuccess").text("");
		}

		/* if(randSuccess=="fail" && randomValue.length<4){
		  	jQuery("#isSuccess").text("");
		  	 return;
		  } */

	}

	function check() {
		var isDis = jQuery("#containRandom").css("display");
		var randomValue = jQuery("#random").val();
		
		var loginPSW=jQuery("#loginPSW").val();
		var userId=jQuery("#userId").val();
		
		if(userId==null||userId==""){
			alert("请输入用户名/邮箱/手机号");
			return;
		}
		if(loginPSW==null||loginPSW==""){
			alert("请输入密码");
			return;
		}
		
		var ssg = sessJudge();

		if (isDis == "none") {
			var user = jQuery("#user").serialize();
			jQuery.ajax({
				url : _path + 'loginAction/validLogin.do',
				data : user,
				async : false,
				dataType:'text',
				success : function(data) {
					if (data == "success") {
						window.location.href = _path + "loginAction/login.do";
					}else if(data == "fail_agent"){
						alert("登陆失败，该商户用户未审核或已被注销!");
					} else if (data == "fail" || data.indexOf("fail") != -1) {
						if(data.indexOf(",")!=-1){
							var res=data.split(",");
							jQuery("#isLogin").text("账号或密码错误  (提示:密码错误次数超过五次,账号将被锁定!),剩余次数:"+res[1]);
							jQuery("#containRandom").css("display", "block");
							loadimage();
							jQuery("#isSuccess").text("重新输入");
							jQuery("#isSuccess").attr("class", "tishi01");
						}else{
							jQuery("#isLogin").text("账号或密码错误 (提示:密码错误次数超过五次,账号将被锁定!)");
						}
						flag = data;
					} else if (data == 'logout') {
						alert("用户已注销，登陆失败!");
					}else if(data == 'errout'){
						alert("用户已锁定，请于管理员联系！");
					}else {
						alert("账号或密码错误 (提示:密码错误次数超过五次,账号将被锁定!)");
					}
				}
			});
			return;

		}
		if (isDis == "block" && flag == "success") {
			var user = jQuery("#user").serialize();
			jQuery.ajax({
				url : _path + 'loginAction/validLogin.do',
				data : user,
				async : false,
				success : function(data) {
					if (data == "success") {
						window.location.href = _path + "loginAction/login.do";
					} else if (data != null && data.indexOf(",") != -1) {
						var res=data.split(",");
						jQuery("#isLogin").text("账号或密码有误  (提示:密码错误次数超过五次,账号将被锁定!),剩余次数:"+res[1]);
						loadimage();
						jQuery("#isSuccess").text("重新输入");
						jQuery("#isSuccess").attr("class", "tishi01");
					} else if (data == 'logout') {
						alert("用户已注销，登陆失败!");
					}else if(data == 'errout'){
						alert("用户已锁定，请于管理员联系！");
					}else if(data == "fail_agent"){
						alert("登陆失败，该商户用户未审核或已被注销!");
					}else {
						alert("账号或密码错误 (提示:密码错误次数超过五次,账号将被锁定!)");
					}
				}
			});
			return;
		}

		if (isDis == "block" && flag == "fail") {
			alert("请输入正确的验证码");
			return;
		}

	}
	
	jQuery("#randImage").click(function(){loadimage();});
	
	var wjmmFlag = true;
	
	function wjmm(){
		showWindow("重置密码",500,250,"resetPwd.jsp");
		
		/*
		if(wjmmFlag == false){
			return ;
		}
		wjmmFlag = false;
		var username = jQuery("#userId").val();
		if(username == ''){
			return ;
		}
		jQuery.ajax({
			url:_path+'loginAction/resetPwd.do?userId='+username,
			async:false,
			success:function(data){
				alert(data);
				wjmmFlag = true;
			}
		});
		*/
	}
	
	function sessJudge(){
		var flag;
		jQuery.ajax({
			url:_path+'loginAction/sessJudge.do?',
			async:false,
			dataType:'text',
			success:function(data){
				flag = data;
			}
		});
		return flag;
	}
	
</script>
</head>

<body>
	<input type="hidden" value="5646546465465465465" />
	<div class="header">
		<a class="logo" href="<%=basePath%>"><img src="image/logo.jpg" width="70%" /></a>
	</div>
	<div >
		<div class="banner_center">
			<div class="user_login">
				<div class="login">
					<div class="login_little">
						<h2>登录</h2>
					</div>

					<form method="post" action="#" id="user">
						<input name="redirect" value="" type="hidden" />
						<div class="textbox_ui user">
							<input name="userId" id="userId" value=""
								placeholder="用户名/邮箱/手机号" required="" autofocus="" type="text" />
							<i class="login_username_icon login_common_icon"></i>
							<div class="invalid">
								<div class="required">请输入用户名/邮箱/手机号</div>
								<div class="custom"></div>
							</div>
						</div>
						<div class="textbox_ui pass">
							<input name="loginPSW" id="loginPSW" placeholder="密码" value="" required=""
								type="password" /> <i
								class="login_userpassword_icon login_common_icon"></i>
							<div class="invalid">
								<div class="required">请输入密码</div>
								<div class="custom"></div>
							</div>
						</div>
						<div id="isLogin"
							style="color: red; margin-top: 5px; margin-bottom: 10px;"
							class="tishi01"></div>

						<div class="textbox_yanz" id="containRandom"
							style="clear: both; display: none">
							<input type="text" name="random" id="random"
								onkeyup="validateRandom(this.value)" maxlength="4"
								style="clear: both; padding-left: 10px;" />
							<div class="invalid" style="float: left; margin-left: 12px;">
								<img name="randImage" id="randImage" src="Random.jsp" border="2" />
								<!--name="randImage" id="randImage" src="Random.jsp" border="2"-->
							</div>
							<a href="javascript:loadimage();"><font size="2">换一张</font></a>
						</div>
						<span id="isSuccess" class="tishi02" style="display:;"></span>

					</form>
					<input value="登&nbsp录" type="button" onclick="check()"
						id="btnLogin"></input>
					<div id="errorMsg"></div>
					<div class="pas">
						<!-- 
						<a href="#" class="zhuce">注&nbsp;册</a> 
						 -->
						 <a href="<%=basePath %>" id="query" ></a>
						<a href="#" class="wjmm" onclick="wjmm();" >忘记密码?</a>
					</div>


				</div>
			</div>
		</div>
	</div>

	<div class="footer_content">
		<div class="cscopy">
			<p>
				版权所有：杭州铭业管网科技有限公司 <a href="http://beian.miit.gov.cn/" target="_blank">浙ICP备20001587号</a><br />
			</p>
		</div>
	</div>
</body>
</html>
