package com.kd.manage.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GoodsDetailSReport extends BaseEntity{
	

	private String buildTime;
	
	private String upTime;
	
	private String storeCode;
	
	private String storeName;
	
	private String goodsName;
	
	private String subcount;
	
	private String subtotal;

	private String reportType;
	
	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * @return the upTime
	 */
	public String getUpTime() {
		return upTime;
	}

	/**
	 * @param upTime the upTime to set
	 */
	public void setUpTime(String upTime) {
		this.upTime = upTime;
	}

	/**
	 * @return the buildTime
	 */
	public String getBuildTime() {
		return buildTime;
	}

	/**
	 * @param buildTime the buildTime to set
	 */
	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	/**
	 * @return the storeCode
	 */
	public String getStoreCode() {
		return storeCode;
	}

	/**
	 * @param storeCode the storeCode to set
	 */
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	/**
	 * @return the storeName
	 */
	public String getStoreName() {
		return storeName;
	}

	/**
	 * @param storeName the storeName to set
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	/**
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * @return the subcount
	 */
	public String getSubcount() {
		return subcount;
	}

	/**
	 * @param subcount the subcount to set
	 */
	public void setSubcount(String subcount) {
		this.subcount = subcount;
	}

	/**
	 * @return the subtotal
	 */
	public String getSubtotal() {
		return subtotal;
	}

	/**
	 * @param subtotal the subtotal to set
	 */
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	
	
}
