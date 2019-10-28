/**
 * 
 */
package com.kd.core.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.kd.manage.entity.BaseEntity;


/**
 * ceshi
 *@类名称：AgentRolesDto.java
 *@类描述：商户角色维护实体类
 *@创建人：tuhq
 *@创建时间：2015年1月26日-下午1:07:57
 *@修改备注:
 *@version 
 */
@XmlRootElement(name="agentRolesDto")
public class AgentRolesDto extends BaseEntity{
	
	private String userId;
	private String userName;
	private String agentId;
	private String agentDesc;
	private String agentName;
	private String agentRoleDesc;
	private String agentRoleCode;
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
	
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentDesc() {
		return agentDesc;
	}
	public void setAgentDesc(String agentDesc) {
		this.agentDesc = agentDesc;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentRoleDesc() {
		return agentRoleDesc;
	}
	public void setAgentRoleDesc(String agentRoleDesc) {
		this.agentRoleDesc = agentRoleDesc;
	}
	public String getAgentRoleCode() {
		return agentRoleCode;
	}
	public void setAgentRoleCode(String agentRoleCode) {
		this.agentRoleCode = agentRoleCode;
	}
	
	 
	
	
	
}
