/**
 * cloud-channel

 * UserController.java
 * 
 * 2015年1月7日-下午3:34:06
 *  杭州铭业管网科技有限公司-版权所有
 *
 */
package com.kd.manage.controller.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
*
* 
* glt
* 2015年1月7日 下午3:34:06
* 
* @version 1.0.0
*
*/
public class PropertiesUtil {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static Properties config = null;
	private static Properties configBase = null;
	static {
		InputStream in = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("config/manage.properties");
		InputStream ini = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("config/uri-base.properties");
		config = new Properties();
		configBase = new Properties();
		try {
			config.load(in);
			configBase.load(ini);
			in.close();
			ini.close();
		} catch (IOException e) {
			logger.error("No manage.properties defined error");
		}
	}

	public static String readValue(String key) {
		try {
			String value = config.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ConfigInfoError" + e.toString());
			return null;
		}
	} 

	@SuppressWarnings("rawtypes")
	public static void readAllProperties() {
		try {
			Enumeration en = config.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = config.getProperty(key);
				System.out.println(key +"="+ Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ConfigInfoError" + e.toString());
		}
	}

	public static String getBaseUri(){
		try {
			String value = configBase.getProperty("base.uri");
			return value;
		} catch (Exception e) {
			logger.error("ConfigInfoError" + e.toString());
			return null;
		}
	}

	public static String getBaseUri(String name){
		try {
			String value = configBase.getProperty(name);
			return value;
		} catch (Exception e) {
			logger.error("ConfigInfoError" + e.toString());
			return null;
		}
	}

	public static void main(String args[]) {
		String serverUri = PropertiesUtil.readValue("serverUri"); //
		System.out.println(serverUri); 
		PropertiesUtil.readAllProperties();
	}
}
