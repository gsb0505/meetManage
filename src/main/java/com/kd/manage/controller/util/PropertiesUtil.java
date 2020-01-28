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
import java.util.Iterator;
import java.util.Map;
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
	private static final String PLACEHOLDER_START = "${";
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

			resolvePlaceHolders(config);
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

	/**
	 * 解析占位符
	 * @param properties
	 */
	private static void resolvePlaceHolders(Properties properties) {
		Iterator itr = properties.entrySet().iterator();
		while ( itr.hasNext() ) {
			final Map.Entry entry = ( Map.Entry ) itr.next();
			final Object value = entry.getValue();
			if ( value != null && String.class.isInstance( value ) ) {
				final String resolved = resolvePlaceHolder(properties, (String)value );
				if ( !value.equals( resolved ) ) {
					if ( resolved == null ) {
						itr.remove();
					}
					else {
						entry.setValue( resolved );
					}
				}
			}
		}
	}

	/**
	 * 解析占位符具体操作
	 * @param prots
	 * @return
	 */
	private static String resolvePlaceHolder(Properties prots,String value) {
		if ( value.indexOf( PLACEHOLDER_START ) < 0 ) {
			return value;
		}
		StringBuffer buff = new StringBuffer();
		char[] chars = value.toCharArray();
		for ( int pos = 0; pos < chars.length; pos++ ) {
			if ( chars[pos] == '$' ) {
				if ( chars[pos+1] == '{' ) {
					String key = "";
					int x = pos + 2;
					for (  ; x < chars.length && chars[x] != '}'; x++ ) {
						key += chars[x];
						if ( x == chars.length - 1 ) {
							throw new IllegalArgumentException( "unmatched placeholder start [" + value + "]" );
						}
					}
					String val = extractFromSystem(prots, key);
					buff.append( val == null ? "" : val );
					pos = x + 1;
					if ( pos >= chars.length ) {
						break;
					}
				}
			}
			buff.append( chars[pos] );
		}
		String rtn = buff.toString();
		return isEmpty( rtn ) ? null : rtn;
	}

	private static String extractFromSystem(Properties prots,String key) {
		try {
			return prots.getProperty(key);
		}
		catch( Throwable t ) {
			return null;
		}
	}

	/**
	 * 判断字符串的空(null或者.length=0)
	 * @param string
	 * @return
	 */
	private static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	public static void main(String args[]) {
		String serverUri = PropertiesUtil.readValue("serverUri"); //
		System.out.println(serverUri); 
		PropertiesUtil.readAllProperties();
	}
}
