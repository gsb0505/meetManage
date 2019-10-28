package com.kd.manage.controller.util;

import java.lang.reflect.Field;
import java.util.List;


import com.kd.manage.entity.PageCount;

/**
 *
 * @类名称：ManageUtil.java
 * @类描述：

 * @创建时间：2015年1月30日-下午12:39:39
 * @修改备注:
 * @version
 */
public class ManageUtil {

	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	public static Object getValueByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if (field != null) {
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	/**
	 * @描述：包装分页
	
	 * @创建时间：2015年1月30日 下午12:39:21
	 * @修改人：
	 * @修改时间：
	 * @修改描述：
	 */
	public static PageCount packPage(List<?> dtoList, PageCount pageCount)
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		PageCount pCount = null;
		if (dtoList.size() > 0) {
			Object parameterObject = dtoList.get(0);
			Field pageField = getFieldByFieldName(parameterObject, "pageCount");
			if (pageField != null) {
				pCount = (PageCount) getValueByFieldName(parameterObject,
						"pageCount");
			}
			pageCount.setCurrentPage(pCount.getCurrentPage());
			pageCount.setTotalResult(pCount.getTotalResult());
			pageCount.setCurrentResult(pCount.getCurrentResult());
			pageCount.setTotalPage(pCount.getTotalPage());
			pageCount.setShowCount(pCount.getShowCount());
			pageCount.setRows(dtoList);
		}
		return pageCount;

	}

}
