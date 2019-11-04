package com.kd.manage.support;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import com.google.gson.Gson;
import com.kd.manage.base.BaseClient;
import com.kd.manage.controller.util.ObjectUtil;
import com.kd.manage.controller.util.PropertiesUtil;
import com.kd.manage.entity.BaseData;
import com.kd.manage.entity.PageCount;

/**
 * 数据字典
 * @author zlm
 *
 */
public class DataDictDefault{
	private static Map<String,List<BaseData>> data = new LinkedHashMap<String, List<BaseData>>();
	private static boolean isLoaded = false;
	public static Map<String,String> types= new HashMap<String, String>();
	
	public final static String commonUrl = PropertiesUtil.getBaseUri()+PropertiesUtil.readValue("commonServerUri");
	/**
	 * 会议室类型表type-id
	 */
	public final static String type1 = PropertiesUtil.readValue("commonServerUri.type1");
	/**
	 * 卡状态表type-id
	 */
	public final static String type2 = PropertiesUtil.readValue("commonServerUri.type2");
	/**
	 * 卡类型表type-id
	 */
	public final static String type3 = PropertiesUtil.readValue("commonServerUri.type3");
	/**
	 * 交易类型表type-id
	 */
	public final static String type4 = PropertiesUtil.readValue("commonServerUri.type4");
	/**
	 * 钱包类型表type-id
	 */
	public final static String type5 = PropertiesUtil.readValue("commonServerUri.type5");
	/**
	 * 时间段表type-id
	 */
	public final static String type6 = PropertiesUtil.readValue("commonServerUri.type6");
	/**
	 * 终端类型表type-id
	 */
	public final static String type7 = PropertiesUtil.getBaseUri()+PropertiesUtil.readValue("commonServerUri.type7");
	/**
	 * 园区表type-id
	 */
	public final static String type8 = PropertiesUtil.readValue("commonServerUri.type8");
	/**
	 * 餐补发放周期type-id
	 */
	public final static String type11 = PropertiesUtil.readValue("commonServerUri.type11");
	/**
	 * 证件类型表type-id
	 */
	public final static String type9 = PropertiesUtil.readValue("commonServerUri.type9");
	/**
	 * 押金类型表type-id
	 */
	public final static String type14 = PropertiesUtil.readValue("commonServerUri.type14");
	/**
	 * 卡类别表type-id
	 */
	public final static String type13 = PropertiesUtil.readValue("commonServerUri.type13");
	/**
	 * 账户状态表type-id
	 */
	public final static String type15 = PropertiesUtil.readValue("commonServerUri.type15");
	/**
	 * 卡片厂商表type-id
	 */
	public final static String type17 = PropertiesUtil.readValue("commonServerUri.type17");
	/**
	 * 员工种类表type-id
	 */
	public final static String type18 = PropertiesUtil.readValue("commonServerUri.type18");
	/**
	 * 操作类型表type-id
	 */
	public final static String type19 = PropertiesUtil.readValue("commonServerUri.type19");
	
	private static WebTarget com;
	
	static{
		types.put(type1, type1);
		types.put(type2, type2);
		types.put(type3, type3);
		types.put(type4, type4);
		types.put(type5, type5);
		types.put(type6, type6);
		types.put(type7, type7);
		types.put(type8, type8);
		types.put(type9, type9);
		types.put(type11, type11);
		types.put(type13, type13);
		types.put(type14, type14);
		types.put(type15, type15);
		types.put(type17, type17);
		types.put(type18, type18);
		types.put(type19, type19);
		
		com = BaseClient.getWebTarget(commonUrl);
	}
	
	public static synchronized boolean sync() {
		BaseData bd=new BaseData();
		PageCount page_count=new PageCount();
		page_count.setShowCount(1000);
		page_count.setCurrentPage(1);
		page_count.setEntityOrField(false);
		bd.setTypeName("1");
		bd.setPageCount(page_count);
		String sendaData = new Gson().toJson(bd);
		WebTarget target = null;
		try {
			target = com.path("queryData").queryParam("baseData", URLEncoder.encode(sendaData, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<BaseData> lstData = target==null?null:target.request().get(new GenericType<List<BaseData>>() {});
		
		data.clear();
		for(BaseData dd : lstData){
			
			if(types.containsKey(dd.getTypeId())){
				List<BaseData> obj = data.get(dd.getTypeId().toString());
				List<BaseData> list = null;
				if(ObjectUtil.isNotNull(obj)){
					list = obj;
				}else{
					list = new LinkedList<BaseData>();
				}
				
				list.add(dd);
				data.put(dd.getTypeId(), list);
			}
			
		}
		isLoaded = true;
		System.out.println("数据字典同步成功");
		return true;
	}

	public static List<BaseData> getList(String key) {
		if(!isLoaded)
			sync();
		if(data.get(key) == null || data.get(key).size() == 0)
			sync();
		return  data.get(key);
	}

	public static void put(String key, Object data) {
		if(!isLoaded)
			sync();
	}
	
	public static BaseData getListValue(String key,String index){
		if(key == null || index == null)
			return null;
		
		if(!isLoaded)
			sync();
		if(data.get(key) == null || data.get(key).size() == 0)
			sync();
		List<BaseData> list = data.get(key);
		
		for (BaseData baseData : list) {
			if(baseData.getCode().equals(index)){
				return baseData;
			}
		}
		return null;
	}

}
