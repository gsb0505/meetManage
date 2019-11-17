package com.kd.manage.entity;


import javax.xml.bind.annotation.XmlRootElement;


/**
 * userbean
 */



@XmlRootElement(name="userInfo") 
public class UserInfo extends BaseEntity{
	
	private String userId;//姓名
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private String roles; //角色
	private String loginPSW;//    登录密码
	private String email;//邮箱
	private String phone;//电话
	private String remark;//备注
	private String flag;//状态：1正常，2注销
	private String userCZ;//  操作员（是谁添加的）	
	private String orgId;//部门 （因运用新疆框架，字段英文与实际不符）
	private String depId;//职位（因运用新疆框架，字段英文与实际不符）
	private String creatorName;//创建人名称
	private String photoUrl;	//头像图片路径
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	private String orgName;
	private String depName;
	
	public String getUserCZ() {
		return userCZ;
	}
	public void setUserCZ(String userCZ) {
		this.userCZ = userCZ;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	private String errNum;//密码错误次数
	

	public String getLoginPSW() {
		return loginPSW;
	}
	public void setLoginPSW(String loginPSW) {
		this.loginPSW = loginPSW;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getErrNum() {
		return errNum;
	}
	public void setErrNum(String errNum) {
		this.errNum = errNum;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}
