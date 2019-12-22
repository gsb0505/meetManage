package com.kd.manage.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.CharacterEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: latham
 * @Date: 2019/11/4 22:06
 **/
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public void errorHandler(Exception ex, HttpServletResponse response) {
        logger.error("发生异常 => " + ex.toString());
        ex.printStackTrace();
        try {
            PrintWriter printWriter = response.getWriter();
            response.setCharacterEncoding("UTF-8");
            printWriter.print("<a style='color:red'>系统错误，请联系管理员</a>");
        } catch (IOException e) {
        }
    }

}
