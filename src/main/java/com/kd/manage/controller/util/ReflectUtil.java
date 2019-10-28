package com.kd.manage.controller.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 反射工具类
 * @author zlm
 * @version 17-03-17 修订版
 */
public class ReflectUtil {
	
	/**
	 * 获取Field的值，支持深层的字段
	 * @param m
	 * @param fieldName 某 field名称,或者field.field深层名称
	 * @return
	 */
	public static <M> Object getFieldValue(M m, String fieldName) {
		Object value = null;
		try {
			Field field = null;
			if(fieldName.contains(".")){
				field = getField(m,fieldName);
				String[] fieldColunms = fieldName.split("\\.");
				
				if(field != null){
					String[] newField = Arrays.copyOf(fieldColunms, fieldColunms.length-1);
					Object obj = getChildFieldObj(m,newField,0);
					
					if (field != null) {
						field.setAccessible(true);
						value = field.get(obj);
					}
				}
			}else{
				field =	m.getClass().getDeclaredField(fieldName);
				if (field != null) {
					field.setAccessible(true);
					
					value = field.get(m);
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	
	public static <M> Object getSuperFieldValue(M m, String fieldName) {
		Object value = null;
		try {
			Class<?> clazz=m.getClass().getSuperclass();  
			Field field=clazz.getDeclaredField(fieldName);  
			if (field != null) {
				field.setAccessible(true);
				
				if (field.getGenericType().toString().equals(  
                        "class java.util.Date")) {  
                    Method mt = (Method) clazz.getMethod(  
                            "get" + getMethodName(field.getName()));
                    Date val = (Date) mt.invoke(clazz.cast(m));  
                    if (val != null) {  
                    	value = DateUtil.format(val);
                    }  
                }else
                	value = field.get(m);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	/**
	 * 字段名不区分大小写
	 * 性能比getFieldValue低
	 * @param m
	 * @param fieldName
	 * @return
	 */
	public static <M> Object getFieldValueNoCaseSensitive(M m, String fieldName) {
		Object value = null;
		try {
			Field f = null;
			Field f2 = null;
			
			f = getField(m,fieldName);
			
			if(f == null){
				Class<?> clazz=m.getClass().getSuperclass();  
			    Field[] fields2 = clazz.getDeclaredFields();
				
				for(Field f1 : fields2){
					if(f1.getName().toLowerCase().equals(fieldName.toLowerCase())){
						f2 = f1;
						break;
					}
				}
			}
			if(f!=null)
				value = getFieldValue(m, f.getName());
			else if(f2 != null)
				value = getSuperFieldValue(m, f2.getName());
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	//获取字段属性
	//fieldName 某 field名称,或者field.field深层名称
	private static <M> Field getField(M m,String fieldName){
		
		if(fieldName.contains(".")){
			int i = 0;
			String[] fieldColunms = fieldName.split("\\.");
			Field field = getChildField(m,fieldColunms,i);
			if(field != null){
				return field;
			}
		}
		
		return getAutoLocal(m,fieldName);
	}
	
	//自检字段名(未完善)
	private static <M> Field getAutoLocal(M m,String fieldName){
		Field[] fields = m.getClass().getDeclaredFields();
		for(Field f1 : fields){
			if(f1.getName().toLowerCase().equals(fieldName.toLowerCase())){
				return f1;
			}
			Class<?> obj = f1.getDeclaringClass();
			
			if(obj.isLocalClass())
				getAutoLocal(obj,fieldName);
		}
		return null;
	}
	
	//指定字段检索
	@Deprecated
	private static <M> Field getChildM(M m,String[] fieldNames,int i){
		Field[] fields = m.getClass().getDeclaredFields();
		Field f = null;
		for(Field f1 : fields){
			if(f1.getName().toLowerCase().equals(fieldNames[i].toLowerCase())){
				if(fieldNames.length > i+1 && fieldNames[i+1] != null)
					f = getChildM(m,fieldNames,++i);
				else
					return f1;
			}
		}
		return f;
	}
	
	//递归查询指定字段的Field
	private static <M,T> Field getChildField(M m,String[] fieldNames,int i){
		Field[] fields = m.getClass().getDeclaredFields();
		
		Field f = null;
		for(Field f1 : fields){
			if(f1.getName().toLowerCase().equals(fieldNames[i].toLowerCase())){
				if(fieldNames.length > i+1 && fieldNames[i+1] != null)
					try {
						Class<?> clazz = m.getClass();
						f1.setAccessible(true);
			            Method mt = (Method) clazz.getMethod(  
			                    "get" + getMethodName(f1.getType().getSimpleName()));
			            Object cla =  mt.invoke(clazz.cast(m));  
			            
			            f = getChildField((cla),fieldNames,++i);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				else
					return f1;
			}
		}
		return f;
	}
	
	//递归查询某字段
	private static <M> Object getChildFieldObj(M m,String[] fieldNames,int i) throws NoSuchMethodException, SecurityException, Exception{
		Field[] fields = m.getClass().getDeclaredFields();
		
		Object o = null;
		for(Field f1 : fields){
			if(f1.getName().toLowerCase().equals(fieldNames[i].toLowerCase())){
				if(fieldNames.length > i+1 && fieldNames[i+1] != null){
						Class<?> clazz = m.getClass();
						f1.setAccessible(true);
			            Method mt = (Method) clazz.getMethod(  
			                    "get" + getMethodName(f1.getType().getSimpleName()));
			            Object obj =  mt.invoke(clazz.cast(m));  
			            
			            o = getChildFieldObj(obj,fieldNames,++i);
				}else if(fieldNames.length >= i && fieldNames[i] != null){
					Class<?> clazz = m.getClass();
					f1.setAccessible(true);
		            Method mt = (Method) clazz.getMethod(  
		                    "get" + getMethodName(f1.getType().getSimpleName()));
		            Object obj =  mt.invoke(clazz.cast(m));  
		            
		            return obj;
				}else{
					break;
				}
			}
		}
		return o;
	}
	
	/**
	 * 设字段值
	 * @param m
	 * @param fieldName
	 * @param fieldValue
	 * @throws Exception 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public static <M> void setFieldValue(M m, String fieldName,
			Object fieldValue) {
		Field field = null;
		try {
			if(fieldName.contains(".")){
				field = getField(m,fieldName);
				String[] fieldColunms = fieldName.split("\\.");
				
				if(field != null){
					String[] newField = Arrays.copyOf(fieldColunms, fieldColunms.length-1);
					Object obj = getChildFieldObj(m,newField,0);
					
					if (field != null) {
						field.setAccessible(true);
						field.set(obj, fieldValue);
					}
				}
			}else{
				Field[] fields = m.getClass().getDeclaredFields();
				if (fields != null) {
					for (Field f : fields) {
						f.setAccessible(true);
						if (f.getName().toLowerCase()
								.equals(fieldName.trim().toLowerCase())) {
							f.set(m, fieldValue);
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 把一个字符串的第一个字母大写、效率是最高的、  
    private static String getMethodName(String fildeName) throws Exception{  
       /* byte[] items = fildeName.getBytes();  
        items[0] = (byte) ((char) items[0] - 'a' + 'A');  
        return new String(items);  */
        StringBuffer stringbf = new StringBuffer();
        Matcher m = Pattern.compile("([a-z])([a-z]*)",
        Pattern.CASE_INSENSITIVE).matcher(fildeName);
        while (m.find()) {
           m.appendReplacement(stringbf, 
           m.group(1).toUpperCase() + m.group(2));
        }
        return m.appendTail(stringbf).toString();
    }

	public static Class<?> forName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

}
