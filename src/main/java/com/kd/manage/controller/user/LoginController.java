/**
 * 
 */
package com.kd.manage.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.kd.manage.interceptor.LoginInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.kd.common.unit.util.EncryptUtils;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.Config;
import com.kd.manage.entity.UserInfo;
import com.kd.manage.entity.UserLog;
import com.kd.manage.util.Mail;

/**
 *
 * @类名称：LoginAction.java
 * @类描述：
 * 
 * @创建时间：2015年1月26日-下午12:58:07
 * @修改备注:
 * @version
 */
@Controller
@RequestMapping(value = "/loginAction")
public class LoginController extends BaseController {

	/**
	 * 获取允许密码错误次数
	 */
	private static String errNum = "";
	/**
	 * 登入用户标识
	 */
	public static final String CURRENT_USER = "user";
	/**
	 * 登入用户ID标识
	 */
	public static final String CURRENT_USER_ID = "userId";
	
	private static WebTarget usu;
	private static WebTarget ulsu;
	private static WebTarget ausu;

	static {
		errNum = PropertiesUtil.readValue("configuri.LoginAction.queryById");
		
		usu = BaseUri.webTarget.get(BaseUri.userServerUri);
		ulsu = BaseUri.webTarget.get(BaseUri.userLogServerUri);
		ausu = BaseUri.webTarget.get(BaseUri.agentUserServerUri);
	}

	/**
	 * @描述:账号密码验证
	 * @创建时间：2015年2月12日 上午10:07:29
	 * @修改人：
	 * @修改时间：
	 * @修改描述：
	 *
	 * @param randomValue
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/validRandom.do")
	public void validateRandom(String randomValue, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String ran = (String) request.getSession().getAttribute("random");// 得到Random.jsp中的随机验证码
		if (!randomValue.equals(ran)) {
			out.print("fail");
		}
		if (randomValue.equals(ran)) {
			out.print("success");
		}
	}

	/**
	 * @描述：验证码校验
	 * @创建时间：2015年2月12日 上午10:07:54
	 * @修改人：
	 * @修改时间：
	 * @修改描述：
	 *
	 * @param user
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/validLogin.do")
	public void validateRandom(UserInfo user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		EncryptUtils loginE = new EncryptUtils(user.getUserId(), "MD5");
		// System.out.println(loginE.encode(user.getLoginPSW())+"}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
		String inPws = loginE.encode(user.getLoginPSW());
		user.setLoginPSW(inPws);
		UserInfo user1 = new UserInfo();
		user1.setUserId(user.getUserId());
		String sendData = new Gson().toJson(user1);
		WebTarget target = usu.path("validUser").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
		Response res = target.request().get();
		UserInfo userRec = res.readEntity(UserInfo.class);
		res.close();
		WebTarget tar_config = csu.path("queryById").queryParam("id", LoginController.errNum); // 配置密码输入错误次数-system_config表
		Config config = tar_config.request().get(new GenericType<Config>() {});

		PrintWriter out = response.getWriter();
		UserLog log = new UserLog();

		log.setAction("loginAction/validLogin.do");
		log.setType("0");// 后台日志

		if (userRec != null) {
			loginVali(user, request, response, inPws, userRec, config, out,
					log);
		} else {
			// 用户名错误
			session.setAttribute("randomCheckSuccess", "fail");
			out.print("fail");
			log.setResult("登陆失败");
		}

		log.setUserIp(request.getRemoteAddr());
		log.setUserId(user.getUserId());
		log.setCreateTime(new Date());
		WebTarget target2 = ulsu.path("insert");
		Response res2 = target2.request().post(Entity.entity(log, MediaType.APPLICATION_XML));
		res2.close();

	}

	/**
	 * 登入操作
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param inPws
	 * @param userRec
	 * @param config
	 * @param out
	 * @param log
	 * @throws UnsupportedEncodingException 
	 */
	private void loginVali(UserInfo user, HttpServletRequest request,
			HttpServletResponse response, String inPws, UserInfo userRec,
			Config config, PrintWriter out, UserLog log) throws UnsupportedEncodingException {
		if (userRec.getFlag().equals("0")) {
			out.print("logout");
			log.setResult("用户已注销，登陆失败");
		} else if (config != null
				&& userRec.getErrNum() != null
				&& Integer.parseInt(userRec.getErrNum()) > Integer
						.parseInt(config.getNum())) {
			out.print("errout");
			log.setResult("密码错误次数超过允许范围，登陆失败");
		} else {
			if (inPws.equals(userRec.getLoginPSW())) {
				request.getSession().setAttribute(CURRENT_USER, userRec);
				request.getSession().setAttribute(CURRENT_USER_ID,
						user.getUserId());
				request.getSession().setMaxInactiveInterval(3600); // 设置session周期
				out.print("success");
				log.setResult("登陆成功");
				// 密码错误次数清空
				userRec.setErrNum("0");
				WebTarget target3 = usu.path("modifyError");
				Response res3 = target3.request().put(Entity.entity(userRec, MediaType.APPLICATION_XML));
				Cookie cookie = new Cookie(CURRENT_USER, URLEncoder.encode(user.getUserId(), "UTF-8")); // (key,value)
				cookie.setPath("/"); // 这个要设置
				// cookie.setDomain(".aotori.com");//这样设置，能实现两个网站共用
				cookie.setMaxAge(365 * 24 * 60 * 60);// 不设置的话，则cookies不写入硬盘,而是写在内存,只在当前页面有用,以秒为单位
														// //
				response.addCookie(cookie);
				res3.close();
			} else {
				// 密码错误次数+1
				if (userRec.getErrNum() != null) {
					userRec.setErrNum((Integer.parseInt(userRec.getErrNum()) + 1)
							+ "");
				} else {
					userRec.setErrNum("1");
				}
				// 密码错误
				HttpSession session = request.getSession();
				session.setAttribute("randomCheckSuccess", "fail");
				out.print(FAIL
						+ ","
						+ (Integer.parseInt(config.getNum())
								- Integer.parseInt(userRec.getErrNum()) + 1));
				log.setResult("登陆失败");
				WebTarget target3 = usu.path("modifyError");
				Response res3 = target3.request().put(
						Entity.entity(userRec, MediaType.APPLICATION_XML));
				res3.close();
			}
		}
	}

	/**
	 * @描述：跳转到index页面
	 * @创建时间：2015年2月12日 上午10:08:18
	 * @修改人：
	 * @修改时间：
	 * @修改描述：
	 *
	 * @return
	 */
	@RequestMapping(value = "/login.do")
	public String login(HttpServletRequest request) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(CURRENT_USER);
		request.setAttribute("userName",userInfo.getUserId());
		return "index";
	}

	/**
	 * @描述:登入账号验证(判断进入主页的用户是否是登入用户)
	 * @创建人：zlm
	 * @创建时间：2015年5月29日 上午10:07:29
	 * @修改人：
	 * @修改时间：
	 * @修改描述：
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/validUser.do")
	public void validateCurrentUser(String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String getId = (String) request.getSession().getAttribute(
				CURRENT_USER_ID);
		// request.getSession().setAttribute(AJAX_URI_SUBMIT,null);
		if (getId != null && userId != null) {
			// 验证当前是否是登入用户
			if (getId.equals(userId)) {
				out.print("true");
			} else {
				// request.getSession().setAttribute(AJAX_URI_SUBMIT,request.getRequestURL().toString());
				out.print("false");
			}
		} else if (getId == null) {
			out.print("login"); // 未登入
		}
		out.close();
	}

	/**
	 * @描述：注销session
	 * @创建时间：2015年3月31日 下午7:55:54
	 * @修改人：
	 * @修改时间：
	 * @修改描述：
	 */
	@RequestMapping(value = "/logout.do")
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession(false);
		Cookie cookie = new Cookie(CURRENT_USER, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect(LoginInterceptor.WEB_URI);
	}
	
	/**
	 * 重复登入验证
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value = "/sessJudge.do")
	public void loginSessionJudge(HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpSession session = request.getSession();
		String cui = (String) session.getAttribute(CURRENT_USER_ID);
		out(response,cui);
	}
	
	@RequestMapping(value = "/modifyPwd.do")
	public String view_resetPwd(){
		return "modifyPwd";
	}

	@RequestMapping(value = "/resetPwd.do", method = RequestMethod.POST)
	public void resetPwd(String userId, HttpServletResponse response)
			throws IOException {

		System.out.println("kao..");
		user = new UserInfo();
		user.setUserId(userId);
		String sendData = new Gson().toJson(user);
		WebTarget target = usu.path("queryModel").queryParam("user", URLEncoder.encode(sendData, "utf-8"));
		Response res = target.request().get();
		UserInfo user = res.readEntity(UserInfo.class);
		PrintWriter out = response.getWriter();
		try {

			if (user == null) {
				throw new IOException("此账户不存在");
			}

			if (StringUtils.isEmpty(user.getEmail())) {
				throw new IOException("不支持找回密码");
			}

			EncryptUtils loginE = new EncryptUtils(user.getUserId(), "MD5");
			String pwd = (int) (Math.random() * 1000000) + "";
			user.setLoginPSW(loginE.encode(pwd));
			WebTarget tar = usu.path("modify");
			Response r = tar.request().post(Entity.entity(user, MediaType.APPLICATION_XML));
			String value = r.readEntity(String.class);
			res.close();

			if ("true".equals(value) && Mail.respPwdEmail(user.getEmail(), pwd)) {
				out.print("密码重置成功，已发送至您的 " + getEmailName(user.getEmail())
						+ " 邮箱");
			} else if ("false".equals(value)) {
				out.print(FAIL);
			} else {
				out.print(EXCEPTION);
			}
		} catch (IOException e) {
			out.print(e.getMessage());
		} catch (Exception e) {
			out.print(EXCEPTION);
			logException(e);
		} finally {
			out.flush();
			out.close();
		}
	}

	public String getEmailName(String email) {
		char[] c = email.toCharArray();
		StringBuffer eamilName = new StringBuffer();
		boolean flag = false;
		char x = '@';
		char y = '.';
		for (int i = 0; i < c.length; i++) {
			if (c[i] == x) {
				flag = true;
				continue;
			}
			if (c[i] == y) {
				break;
			}
			if (flag) {
				eamilName.append(c[i]);
			}
		}
		return eamilName.toString();

	}
	
	
}
