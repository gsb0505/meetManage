/**
*
 * @package:com.kd.manage.controller.util
 * @projectName:cloud-manage
 * @CreateTime:2015年2月6日-下午3:05:23
 *  2015杭州宽达信息技术有限公司-版权所有
 *
 */
package com.kd.manage.controller.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @Class:NumberUtil
 * @Autor:glt
 * @CreateTime:2015年2月6日 下午3:05:23
 * @version 1.0.0
 *
 */
public class NumberUtil {
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	public static boolean isNumeric1(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*[|]*[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	public static void main(String[] args){
		System.out.println(NumberUtil.isNumeric1("1001001001|333|4444|4444"));
	}
}
