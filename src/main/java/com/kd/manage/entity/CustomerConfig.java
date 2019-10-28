package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
*
*@类名称：CustomerConfig.java
*@类描述：客户配置
*@创建人：zlm
*@创建时间：2015年5月23日-上午9:40:32
*@修改备注:
*@version 
*/
@XmlRootElement
public class CustomerConfig extends BaseEntity {
	
	//用户名称
	private String userName;
	//邮箱
	private String email;
	//告警类型（type,type）
	private String type;
	//类型名称(name,name)
	private String typeName;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
