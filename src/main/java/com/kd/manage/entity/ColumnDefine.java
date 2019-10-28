package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class ColumnDefine extends BaseEntity {
	private String columnEN;//字段英文名
	private String columnCN;//字段中文名
	private String labelName;//html标签名
	private String labelValue;//标签值
	private String productCode;//产品编号
	public String getColumnEN() {
		return columnEN;
	}
	public void setColumnEN(String columnEN) {
		this.columnEN = columnEN;
	}
	public String getColumnCN() {
		return columnCN;
	}
	public void setColumnCN(String columnCN) {
		this.columnCN = columnCN;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getLabelValue() {
		return labelValue;
	}
	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	

	

}
