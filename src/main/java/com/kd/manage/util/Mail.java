package com.kd.manage.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.user.LoginController;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.Config;

public class Mail extends BaseController {
	private static Properties props = new Properties();
	/**
	 * 获取用户名ID
	 */
	private static String mailUserID = "";
	/**
	 * 获取密码ID
	 */
	private static String mailPswID = "";
	/**
	 * 获取会议主题ID
	 */
	private static String mailmeetMailID = "";
	/**
	 * 获取会议内容ID
	 */
	private static String mailConnentMailID = "";
	/**
	 * 获取用户名
	 */
	private static String mailUser = "";
	/**
	 * 获取密码
	 */
	private static String mailPsw = "";
	
	protected static WebTarget csu;
	static{
		mailUserID = PropertiesUtil.readValue("configuri.EmailUserAction.queryById");
		mailPswID = PropertiesUtil.readValue("configuri.EmailPswAction.queryById");
		mailmeetMailID = PropertiesUtil.readValue("configuri.EmailTitleAction.queryById");
		mailConnentMailID = PropertiesUtil.readValue("configuri.EmailContentAction.queryById");
		csu = BaseUri.webTarget.get(BaseUri.configServerUri);
		InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("config/email.properties");
		props = new Properties();
		try {
			props.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println("No email.properties defined error");
		}
		
    	WebTarget tar_config = csu.path("queryById").queryParam("id", mailUserID); // 配置邮件用户名-system_config表
		Config config = tar_config.request().get(new GenericType<Config>() {});
		WebTarget tar_config1 = csu.path("queryById").queryParam("id", mailPswID); // 配置邮件密码-system_config表
		Config config1 = tar_config1.request().get(new GenericType<Config>() {});
		mailUser = config.getNum();
		mailPsw = config1.getNum();
	}
	
	public static Session createSession(){
		// 配置发送邮件的环境属性
		/*
		 * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
		 * mail.user / mail.from
		 */
        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
              //  String userName = props.getProperty("mail.user");
             //  String password = props.getProperty("mail.password");
              //  return new PasswordAuthentication(userName, password);
            	
    
            	 return new PasswordAuthentication(mailUser, mailPsw);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        return Session.getInstance(props, authenticator);
	}
	
	
	public static boolean sendEmail(String email,String title,String content){


		try {
			Session mailSession = createSession();
			// 创建邮件消息
			MimeMessage message = new MimeMessage(mailSession);
			// 设置发件人
		//	InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
			InternetAddress form = new InternetAddress(mailUser);
			message.setFrom(form);

			// 设置收件人
			InternetAddress to = new InternetAddress(email);
			message.setRecipient(RecipientType.TO, to);

			// 设置抄送
//        InternetAddress cc = new InternetAddress("luo_aaaaa@yeah.net");
//        message.setRecipient(RecipientType.CC, cc);

			// 设置密送，其他的收件人不能看到密送的邮件地址
//        InternetAddress bcc = new InternetAddress("aaaaa@163.com");
//        message.setRecipient(RecipientType.CC, bcc);

			// 设置邮件标题
			message.setSubject(title);

			// 设置邮件的内容体
			message.setContent(content, "text/html;charset=UTF-8");

			// 发送邮件
			Transport.send(message);
			return true;
		} catch (MessagingException  e) {
			return false;
		}
    		
	}
	
	public static boolean respPwdEmail(String email,String pwd){
		
		return sendEmail(email, props.getProperty("mail.title"), props.getProperty("mail.content").replace("{password}", pwd));
		
	}
	
	public static boolean respMeetNoticeEmail(String email,String meetName ,String meetDate,String meetRoomName,String meetPromoter){
	
		WebTarget tar_config = csu.path("queryById").queryParam("id", mailmeetMailID); // 配置邮件用户名-system_config表
		Config config = tar_config.request().get(new GenericType<Config>() {});
		WebTarget tar_config1 = csu.path("queryById").queryParam("id", mailConnentMailID); // 配置邮件密码-system_config表
		Config config1 = tar_config1.request().get(new GenericType<Config>() {});
	
	//	return sendEmail(email, props.getProperty("mail.meettitle"), props.getProperty("mail.meetcontent").replace("{meetName}", meetName).replace("{meetPromoter}", meetPromoter).replace("{meetRoomName}", meetRoomName).replace("{meetDate}", meetDate));		
			return sendEmail(email, config.getNum(), config1.getNum().replace("{meetName}", meetName).replace("{meetPromoter}", meetPromoter).replace("{meetRoomName}", meetRoomName).replace("{meetDate}", meetDate));		
	}
	
	public static void main(String[] args) {
		System.out.println(respPwdEmail("626513648@qq.com", "123456"));
	}
	
}
