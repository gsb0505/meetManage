package com.kd.manage.support;

import com.kd.manage.base.BaseClient;
import com.kd.manage.base.BaseController;
import com.kd.manage.base.BaseUri;
import com.kd.manage.controller.util.PropertiesUtil;

import javax.servlet.*;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author: latham
 * @Date: 2019/10/31 22:04
 **/
public class InitServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        Field[] fields = BaseUri.class.getDeclaredFields();
        Class<?> bc = null;
        String allname,pname;
        try {
            bc = Class.forName("com.kd.manage.base.BaseUri",true,BaseUri.class.getClassLoader());
            if(bc != null){
                for (Field field : fields) {
                    allname = field.getName();
                    pname = PropertiesUtil.readValue(allname);
                    System.out.println(allname);
                    field.setAccessible(true);
                    //pname=PropertiesUtil.getBaseUri()+pname;
                    if(field.getType() == String.class){
                        field.set(bc,pname);
                    }

                    BaseUri.webTarget.put(pname, BaseClient.getClient().target(pname));
                }
//                Field baseField = BaseUri.class.getDeclaredField("corePath");
//                String uri = PropertiesUtil.getBaseUri("base.uri");
//                String project = PropertiesUtil.getBaseUri("base.project");
//                baseField.setAccessible(true);
//                baseField.set(bc,uri + project);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
