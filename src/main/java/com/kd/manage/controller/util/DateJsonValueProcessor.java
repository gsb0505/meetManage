package com.kd.manage.controller.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * JSON日期格式转换
 * 
 * @author zlm
 *
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	private String format = "yyyy-MM-dd HH:mm:ss";

	public DateJsonValueProcessor() {
		super();
	}

	public DateJsonValueProcessor(String format) {
		super();
		this.format = format;
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {

		 return process(value); 
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {

		return process(value);  
	}
	
	private Object process(Object value) {  
        try {  
            if (value instanceof Date) {  
                SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.UK); 
                return sdf.format((Date) value);  
            }  
            return value == null ? "" : value.toString();  
        } catch (Exception e) {  
            return "";  
        }  
   
    } 

	public String getFormat() {

		return format;
	}

	public void setFormat(String format) {

		this.format = format;
	}

}
