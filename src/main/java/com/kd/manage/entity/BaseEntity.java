/**
 * 
 */
package com.kd.manage.entity;

import java.util.Date; 

import javax.validation.groups.Default;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;







/**
 *
 *@类名称：BaseEntity.java
 *@类描述：基础实体，用来封装所有共有的属性

 *@创建时间：2015年1月14日-下午6:42:59
 *@修改备注:
 *@version 
 */
public class BaseEntity {
	private String id;//主键id
	private String creator;//创建人
	private String auditor;//修改人
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	@XmlTransient
	public PageCount pageCount;
	
	/**
	 * 保存验证组
	 */
	public interface Save extends Default {

	}

	/**
	 * 更新验证组
	 */
	public interface Update extends Default {

	}
	
	public String getCreator() {
		return creator;
	}
	
	/**
	 * id
	 *
	 * @return  the id
	 * @since   1.0.0
	 */
	
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	/*public PageCount getPageCount() {
		return pageCount;
	}*/

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setPageCount(PageCount pageCount) {
		this.pageCount = pageCount;
	}

	public PageCount getPageCount() {
		return pageCount;
	}
	
	
	
	

}
