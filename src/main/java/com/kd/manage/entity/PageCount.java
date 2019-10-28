/**
 * manage
 * PageCount.java
 * 
 * 2015年1月8日-上午10:21:16
 *  2015杭州宽达信息技术有限公司-版权所有
 *
 */
package com.kd.manage.entity;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * PageCount
 * 
 * glt
 * glt
 * 2015年1月8日 上午10:21:16
 * 
 * @version 1.0.0
 *
 */
@XmlRootElement
public class PageCount {
	
	private int showCount = 15; //每页显示记录数
	private int totalPage;		//总页数
	private int totalResult;	//总记录数
	private int currentPage;	//当前页
	private int currentResult;	//当前记录起始索引
	private boolean entityOrField;	//true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	//private String pageStr;		//最终页面显示的底部翻页导航，详细见：getPageStr();
	private String sortName;
	private String sortOrder;
	
	private Map<String,String> userdata;
	
	
	
	public Map<String, String> getUserdata() {
		return userdata;
	}

	public void setUserdata(Map<String, String> userdata) {
		this.userdata = userdata;
	}

	@SuppressWarnings("rawtypes")
	private List rows;
	
	
	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows;
	}

	public void setRows(@SuppressWarnings("rawtypes") List rows) {
		this.rows = rows;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getShowCount() {
		return showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getCurrentResult() {
		return currentResult;
	}

	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}

	public boolean isEntityOrField() {
		return entityOrField;
	}

	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
