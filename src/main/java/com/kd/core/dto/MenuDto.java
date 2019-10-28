/**
 * 
 */
package com.kd.core.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 *@类名称：MenuDto.java
 *@类描述：菜单传输对象
 *@创建人：zlm
 *@创建时间：2015年1月29日-下午3:49:52
 *@修改备注:
 *@version
 */
@XmlRootElement(name="menuDto")
public class MenuDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String menuId;//菜单id
	private String parentMenuId;//上级菜单id
	private String menuName;//菜单名称
	private String menuUrl;//菜单地址
	private String iconUrl;//图片地址
	private String path;//资源路径
	private String menuRemark;//备注
	private String menuSort;//排序
	private String userId; //用户ID
	private String status; //顺序
	private String roleId;
	private String btnId;
	private String flag; //是否被添加到常用功能
	private String cymenuId; //常用菜单ID
	
	
	public String getBtnId() {
		return btnId;
	}
	public void setBtnId(String btnId) {
		this.btnId = btnId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMenuRemark() {
		return menuRemark;
	}
	public void setMenuRemark(String menuRemark) {
		this.menuRemark = menuRemark;
	}
	public String getMenuSort() {
		return menuSort;
	}
	public void setMenuSort(String menuSort) {
		this.menuSort = menuSort;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCymenuId() {
		return cymenuId;
	}
	public void setCymenuId(String cymenuId) {
		this.cymenuId = cymenuId;
	}
	
	
}
