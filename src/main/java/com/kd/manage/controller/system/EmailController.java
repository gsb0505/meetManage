package com.kd.manage.controller.system;

import com.kd.manage.controller.util.StringUtil;
import com.kd.manage.util.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/** 暴露邮件发送
 * @author: latham
 * @Date: 2019/11/22 21:04
 **/
@RequestMapping("/semail")
@Controller
public class EmailController {

    @RequestMapping(value = "/sendEmail.do",method = {RequestMethod.POST,RequestMethod.GET})
            //produces = {MediaType.APPLICATION_JSON,"application/json;charset=UTF-8"},
            //consumes = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    //@ResponseBody
    public void sendEmail(@RequestParam("meetName")String meetName, @RequestParam("meetRoomName")String meetRoomName,
                          @RequestParam("meetStartTime")String meetStartTime, @RequestParam("e_mail")String e_mail,
                          HttpServletRequest request, HttpServletResponse response){
        if(StringUtil.isEmpty(meetName) || StringUtil.isEmpty(meetRoomName) ||
                StringUtil.isEmpty(meetStartTime) || StringUtil.isEmpty(e_mail)){
            return;
        }
        try {
            new Thread(new SendEmailThreed(e_mail, meetName, meetStartTime, meetRoomName)).start();
            response.getWriter().print("");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class SendEmailThreed implements Runnable{

        String e_mail;
        String meetName;
        String meetStartTime;
        String meetRoomName;

        public SendEmailThreed(String e_mail, String meetName, String meetStartTime, String meetRoomName) {
            this.e_mail = e_mail;
            this.meetName = meetName;
            this.meetStartTime = meetStartTime;
            this.meetRoomName = meetRoomName;
        }

        @Override
        public void run() {
            Mail.respMeetNoticeEmail(e_mail,meetName,meetStartTime,meetRoomName, Mail.systemName());
        }
    }


}
