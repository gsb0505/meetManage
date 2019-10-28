package com.kd.manage.listener;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.kd.manage.support.DataDictDefault;

public class InitDataListener implements InitializingBean, ServletContextAware{
		  
    private Logger log = Logger.getLogger(InitDataListener.class);  
      
  
    @Override  
    public void afterPropertiesSet() throws Exception {  
          
    }  
    @Override  
    public void setServletContext(ServletContext arg0) {  
          
        try {  
        	DataDictDefault.sync();
        } catch (Exception e) {  
            log.error("spring initial error" + e.getMessage());  
            e.printStackTrace();
        }  
          
	  
	}  
}
