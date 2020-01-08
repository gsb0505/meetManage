package com.kd.manage.base;


import javax.ws.rs.client.WebTarget;
import java.util.HashMap;
import java.util.Map;

/**
 * Base uri
 * @author zlm
 *
 */
public class BaseUri {

	private BaseUri(){}

	/**
	 * core端路径
	 */
	public static String corePath;

	/**
	 * 权限
	 */
	public static String rolesServerUri;
	/**
	 * 用户
	 */
	public static String userServerUri;
	/**
	 * 用户权限
	 */
	public static String userRolesServerUri;
	/**
	 * 菜单
	 */
	public static String menuServerUri;
	/**
	 * 按钮
	 */
	public static String buttonServerUri;
	/**
	 * 商户用户
	 */
	public static String agentUserServerUri;
	/**
	 * 公共表
	 */
	public static String commonServerUri;
	/**
	 * 列名
	 */
	public static String columnServerUri;
	/**
	 * 系统配置
	 */
	public static String configServerUri;
	/**
	 * 商户公共
	 */
	public static String agentCommServerUri;
	/**
	 * 账户日志
	 */
	public static String accountLogServerUri;
	/**
	 * 账户日志old
	 */
	public static String accountLogOldServerUri;
	/**
	 * 
	 */
	public static String agentIncomeServerUri;
	public static String agentExpendServerUri;
	public static String balanceServerUri;
	/**
	 * 用户日志
	 */
	public static String userLogServerUri;
	public static String fundServerUri;
	public static String serviceInfoUri;
	/**
	 * 卡
	 */
	public static String cardServiceUri;
	/**
	 * 终端
	 */
	public static String meetRoomServiceUri;
	/**
	 * 会议室屏幕通知栏
	 */
	public static String meetNoticeServiceUri;
	/**
	 * 订单明细
	 */
	public static String orderDetailServerUri;
	/**
	 * 订单明细old
	 */
	public static String orderDetailOldServerUri;
	/**
	 * 报表
	 */
	public static String reportServiceUri;
	/**
	 * 卡账户
	 */
	public static String cardRepertoryServiceUri;
	/**
	 * 机关
	 */
	public static String organizationServiceUrl;
	/**
	 * 科室
	 */
	public static String departmentServiceUrl;
	/**
	 * 职位
	 */
	public static String jobServiceUrl;
	/**
	 * 餐补周期
	 */
	public static String foodpatchCycleServerUri;
	/**
	 * 餐补配置
	 */
	public static String foodpatchConfigServerUri;
	public static String voiceConfigServerUri;
	/**
	 * 清空餐补周期
	 */
	public static String cleanFoodpatchServerUri;
	/**
	 * 清空餐补配置
	 */
	public static String cleanFoodpatchConfigServerUri;
	/**
	 * 结算 
	 */
	public static String settleDetailServerUri;
	/**
	 * 商品
	 */
	public static String productServiceUri;

	/**
	 * 预约商品明细
	 */
	public static String goodsDetailServiceUri;

	/**
	 * URI Map
	 */
	public final static Map<String,WebTarget> webTarget = new HashMap<String, WebTarget>();
	
	
}
