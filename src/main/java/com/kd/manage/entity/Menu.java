package com.kd.manage.entity;

/**
 *
 * @package:com.kd.core.entity
 * @projectName:cloud-core
 * @CreateTime:2015骞�鏈�5鏃�涓嬪崍3:45:11
 *  2015鏉窞璇烘櫉淇℃伅鎶�湳鑲′唤鏈夐檺鍏徃-鐗堟潈鎵�湁
 *
 */

import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 *@类名称：Menu.java
 *@类描述：菜单类

 *@创建时间：2015年3月17日-下午5:00:51
 *@修改备注:
 *@version 
 */
@XmlRootElement()
public class Menu {
	private String id; //ztree使用
	private String targetId;
	private String pId;  //ztree使用
	private String targetPId;
	private String name; //ztree使用
	private String open;  //ztree使用
	private String checked; //ztree使用
	private String roleId;
	private String typeId;
	private String btnId;
	private String isbtn; //ztree使用
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getTargetPId() {
		return targetPId;
	}
	public void setTargetPId(String targetPId) {
		this.targetPId = targetPId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getBtnId() {
		return btnId;
	}
	public void setBtnId(String btnId) {
		this.btnId = btnId;
	}
	public String getIsbtn() {
		return isbtn;
	}
	public void setIsbtn(String isbtn) {
		this.isbtn = isbtn;
	} 
	


}
