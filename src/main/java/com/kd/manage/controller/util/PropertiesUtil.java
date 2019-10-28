/**
 * cloud-channel

 * UserController.java
 * 
 * 2015年1月7日-下午3:34:06
 *  2015杭州宽达信息技术有限公司-版权所有
 *
 */
package com.kd.manage.controller.util;

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
	private static Properties config = null;
	static {
		InputStream in = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream("config/manage.properties");
		config = new Properties();
		try {
			config.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println("No manage.properties defined error");
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

	public static void main(String args[]) {
		String serverUri = PropertiesUtil.readValue("serverUri"); //
		System.out.println(serverUri); 
		PropertiesUtil.readAllProperties();
	}
}
