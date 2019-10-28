package com.kd.manage.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.springframework.stereotype.Component;

import com.kd.manage.base.BaseClient;
import com.kd.manage.controller.user.LoginController;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.UserInfo;
import com.kd.manage.entity.UserLog;

@Component  
@Aspect  
public class LogAopInterceptor {
	
	private static Logger log = Logger.getLogger(LogAopInterceptor.class);
	
	private static String logUri="";
	
	static {
		logUri=PropertiesUtil.readValue("userLogServerUri");
	}
	
    private Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {  
    	
    	log.info("执行spring aop 用户日志添加。。。。。。。。。。。");
    	
        Object[] args = joinPoint.getArgs();  
        HttpServletRequest request = null;  
        //通过分析aop监听参数分析出request等信息  
        for (int i = 0; i < args.length; i++) {  
            if (args[i] instanceof HttpServletRequest) {  
                request = (HttpServletRequest) args[i];  
            }
        }
        
        UserInfo user = (UserInfo) request.getSession().getAttribute(LoginController.CURRENT_USER);
		String userId="";
		if(user!=null){
			userId = user.getUserId();
		}
		System.out.println("userId:"+userId);
		
		String userIp = request.getRemoteAddr(); 
		String requesturl = request.getServletPath();
		
		UserLog userLog=new UserLog();
		userLog.setAction(requesturl);
		userLog.setResult("操作成功");
		userLog.setCreateTime(new Date());
		userLog.setUserId(userId);
		userLog.setUserIp(userIp);
		userLog.setType("1");
		
		JerseyWebTarget target=BaseClient.getWebTarget(logUri+"insert");
		Response res=target.request().post(Entity.entity(userLog, MediaType.APPLICATION_XML));
		String value=res.readEntity(String.class);
		
		Object result = joinPoint.proceed();
		log.info("日志插入："+value);
		return result;
    }  
  
}
