/**
 * 
 */
package com.kd.manage.interceptor;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;







import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;

import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
















import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseClient;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.user.LoginController;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.UserInfo;
import com.kd.manage.entity.UserLog;

/**
 *
 *@类名称：LoginInterceptor.java
 *@类描述：

 *@创建时间：2015年1月26日-下午12:38:15
 *@修改备注:
 *@version 
 */
public class LoginInterceptor extends HandlerInterceptorAdapter  {
//, "/Login/","/loginAction/login.do"
	private static final String[] IGNORE_URI = {"loginAction/validLogin.do","/loginAction/validRandom.do","/login.jsp","/loginAction/logout.do","loginAction/resetPwd.do","sessJudge.do"};
//	private static Map<String,String> menuMap=new HashMap<String, String>();//这个集合表示数据库需要进行日志记录的Action  TODO
	private final String WEB_URI="/meetManage";
	private static String logUri="";
	private static WebTarget target;
	private static final String[] IGNORE_URI1 = { "loginAction/validLogin.do",
		"/userRolesAction/getUserRolesList.do",
		"/userMenu/queryOneLevel.do", "/card/add_1.do","userMenu/queryOtherLevel.do" };
	static {
		//menuMap=PropertiesUtil.readActionProperties();
		//ConfigUtil.configXmlValue(menuMap, "operateUrl");
		logUri=PropertiesUtil.readValue("userLogServerUri");
		target = BaseController.webTarget.get(BaseUri.userLogServerUri).path("insert");
	}
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html");
    	
    	
    	boolean flag = false;
    	boolean flag1 = false;
        boolean skip=false; 
        String url = request.getRequestURL().toString();
        String requestType = request.getHeader("X-Requested-With");

        System.out.println(">>>: " + url);
        
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            UserInfo user = (UserInfo) request.getSession().getAttribute(LoginController.CURRENT_USER);
            if (user != null){
            	flag = true;
            }else{
            	if(StringUtils.isNotBlank(requestType) && requestType.equalsIgnoreCase("XMLHttpRequest")){
            		response.setHeader("sessionstatus", "timeout");  
            		response.sendError(518, "session timeout.");
            		skip=true; 
            	}
            }
            //insertUserLog(request);
        }
        if(!flag&&!skip){
        	String str = "<script language='javascript'> var yes = confirm('由于您长时间没有操作, 页面已过期, 请重新登录.'); if (yes) { "
                    + "window.top.location.href='"
                    + WEB_URI
                    + "'}</script>";
            response.setContentType("text/html;charset=UTF-8");// 解决中文乱码
            Cookie cookie = new Cookie("user", null);
    		cookie.setMaxAge(0);
    		cookie.setPath("/");
    		response.addCookie(cookie);
            try {
                PrintWriter writer = response.getWriter();
                writer.write(str);
                writer.flush();
                //writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    		//response.sendRedirect("/cloud-manage/loginAction/logout.do");
        }
        for (String s : IGNORE_URI1) {
			if (url.contains(s)) {
				flag1 = true;
				break;
			}
		}
		if (!flag1) {
			insertUserLog(request);
		}
        return flag;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
	
	
    /**
     * 
     *@描述  ：插入用户日志
    
     *@创建时间：2015年3月16日 下午6:17:43
     *@修改人：
     *@修改时间：
     *@修改描述：
     *@param request
     */
    
    @SuppressWarnings("unchecked")
	public static void insertUserLog(HttpServletRequest request) {
		String requesturl = request.getServletPath();
		try {
			//需要获取的参数 userName+actionName+ip+
			String actionName = requesturl;			
//			String menuName = menuMap.get(actionName);

			 UserInfo user = (UserInfo) request.getSession().getAttribute(LoginController.CURRENT_USER);
				String userId="";
				if(user!=null){
					userId = user.getUserId();
				}
				
				if (userId == null) {
					userId = request.getParameter("userId");
				}

				Map<String, String[]> map = new HashMap<String, String[]>();
				try {
					map = request.getParameterMap();
				} catch (Exception e) {
					e.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				for (Entry<String, String[]> en : map.entrySet()) {
					String key = en.getKey();
					String val = request.getParameter(en.getKey());
					sb.append(key + "=" + val + "&");
				}
				String remark = sb.toString();

				System.out.println("代理商请求参数:：" + remark);
				if (remark.length() > 450) {
					remark = remark.substring(0, 450);
				}

				String userIp = request.getRemoteAddr(); 
						LoginInterceptor.getLocalIpAddr(request);
				try {
					//调用接口发送书写日志
				
					UserLog log=new UserLog();
					log.setAction(actionName);
					log.setType("0");//后台日志
					log.setUserIp(userIp);
					log.setUserId(userId);
					log.setCreateTime(new Date());
					log.setRemark(remark);
					log.setResult("操作成功");
					if(!actionName.equals("/loginAction/validLogin.do")){
						Response res=target.request().post(Entity.entity(log, MediaType.APPLICATION_XML));
						String value=res.readEntity(String.class);
						res.close();
						System.out.println("日志插入："+value);
					}
				} catch (WebServiceException e) {
					e.printStackTrace();
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    public static String getLocalIpAddr(HttpServletRequest request) {
		String ip =request.getHeader("X-Real-IP") ;
		System.out.println("X-Real-IP ip="+ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			System.out.println("Proxy-Client-IP ip="+ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			System.out.println("X-Forwarded-For ip="+ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			System.out.println("WL-Proxy-Client-IP ip="+ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			System.out.println("ip="+ip);
		}
		return isboolIP(ip);
	}
	
	public static String isboolIP(String ipAddress){
		String returnIP="";
	String ip="(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
	
		Pattern pattern=Pattern.compile(ip);
		Matcher matcher=pattern.matcher(ipAddress);
		if (matcher.matches()) {
			returnIP= ipAddress;
		}else{
			returnIP="";
		}
		return returnIP;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
