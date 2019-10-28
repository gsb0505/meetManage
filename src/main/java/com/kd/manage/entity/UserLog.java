package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * 
 *
 *@类名称：UserLog.java
 *@类描述：用户日志

 *@创建时间：2015年3月9日-下午4:28:11
 *@修改备注:
 *@version
 */
@XmlRootElement
public class UserLog extends BaseEntity {

	private String userId; //用户id或者前台商户编号
	private String menuId; //菜单id1
	private String result; //操作名称 写到配置文件
	private String userIp; //用户ip
	private String remark; //备注
	private String operTime; //操作时间
	private String action; //操作的action
	private String type;//日志类型 0前台日志 1后台日志
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
}
