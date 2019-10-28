package com.kd.manage.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举
 * @author zlm
 *
 */
public enum BaseEnum {
	//1交易成功，2 退款状态, 3失败状态 ，4 已冲正，5 处理中
	errCode,
	
	//0：未上送，1：已上送
	requestFlag,
	
	//写卡状态 1失败 0成功
	writeCard,
	
	//红山一卡通提交状态（0未提交 1已提交）
	isUpload,
	;
	
	private static Map<BaseEnum,Map<Integer,String>> enumIntMap = new HashMap<BaseEnum, Map<Integer,String>>();
	private static Map<BaseEnum,Map<String,String>> enumStrMap;
	
	
	static{
		Map<Integer,String> map1 = new HashMap<Integer, String>();
		Map<Integer,String> map2 = new HashMap<Integer, String>();
		Map<Integer,String> map3 = new HashMap<Integer, String>();
		Map<Integer,String> map4 = new HashMap<Integer, String>();
		
		map1.put(1, "交易成功");
		map1.put(2, "退款状态");
		map1.put(3, "失败状态");
		map1.put(4, "已冲正");
		map1.put(5, "处理中");
		
		map2.put(0, "未上送");
		map2.put(1, "已上送");
		
		map3.put(1, "失败");
		map3.put(0, "成功");
		
		map4.put(0, "未提交");
		map4.put(1, "已提交");
		
		enumIntMap.put(errCode, map1);
		enumIntMap.put(requestFlag, map2);
		enumIntMap.put(writeCard, map3);
		enumIntMap.put(isUpload, map4);
	}
	
	private BaseEnum(){
	}
	
	public static Map<Integer,String> getEnumIntMap(BaseEnum en){
		return enumIntMap.get(en);
	}
	
	

}
