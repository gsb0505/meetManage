/**
 * 
 */
package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *@类名称：UserRoles.java
 *@类描述：用户角色关系

 *@修改备注:
 *@version 
 */
@XmlRootElement(name = "roles")
public class Role extends BaseEntity{


	private String roleDesc;//角色描述
	private String  remark;//备注
	private String roleCode;//角色代码
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
}
