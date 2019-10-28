/**
 * 
 */
package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 *@类名称：BaseData.java
 *@类描述：基础数据对象

 *@创建时间：2015年1月23日-上午9:40:32
 *@修改备注:增加一个remark备注，用于标注字段的说明
 *@version 
 */
@XmlRootElement(name = "baseData")
public class BaseData extends BaseEntity{
	private String name;//名称
	private String typeId;//关联表id
	private String code;//业务码
	private String typeName;//归属表的名称
	private String remark;//备注
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	
	
}
