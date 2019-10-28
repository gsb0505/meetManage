/**
 * 
 */
package com.kd.core.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 *@类名称：ButtonDto.java
 *@类描述：按钮传输对象

 *@创建时间：2015年1月29日-下午3:49:35
 *@修改备注:
 *@version
 */
@XmlRootElement(name="buttonDto")
public class ButtonDto {
	private String menuId;//归属菜单按钮
	private String btnId;//按钮id
	private String btnText;//按钮文字
	private String btnIcon;//按钮图标
	private String btnCss;//按钮样式
	private String btnProp;//按钮属性
	private String roleId;//角色id
	
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getBtnId() {
		return btnId;
	}
	public void setBtnId(String btnId) {
		this.btnId = btnId;
	}
	public String getBtnText() {
		return btnText;
	}
	public void setBtnText(String btnText) {
		this.btnText = btnText;
	}
	public String getBtnIcon() {
		return btnIcon;
	}
	public void setBtnIcon(String btnIcon) {
		this.btnIcon = btnIcon;
	}
	public String getBtnCss() {
		return btnCss;
	}
	public void setBtnCss(String btnCss) {
		this.btnCss = btnCss;
	}
	public String getBtnProp() {
		return btnProp;
	}
	public void setBtnProp(String btnProp) {
		this.btnProp = btnProp;
	}
	

	
	
}
