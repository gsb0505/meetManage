/**
 * 
 */
package com.kd.core.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.kd.manage.entity.BaseEntity;

/**
 *
 *@类名称：UserRolesDto.java
 *@类描述：用户角色关系传输对象

 *@创建时间：2015年1月15日-下午2:50:32
 *@修改备注:
 *@version 
 */
@XmlRootElement(name="userRolesDto")
public class UserRolesDto extends BaseEntity {
	
	private String userId;
	private String userName;
	private String roleCode;
	private String roleDesc;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	
	
	
	
	
	
}
