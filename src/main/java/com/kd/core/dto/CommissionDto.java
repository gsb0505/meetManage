package com.kd.core.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.kd.manage.entity.BaseEntity;


/**
 *
 *@类名称：CommissionDto.java
 *@类描述：

 *@创建时间：2015年3月25日-上午9:35:00
 *@修改备注:
 *@version 
 */
@XmlRootElement
public class CommissionDto extends BaseEntity{
	private String operatorId;//运营商表id
	private String operatorName;
	private String orgId;//地域表id
	private String orgDesc;
	private String commissionTypeId;//佣金方式表ID
	private String commissionTypeName;//佣金方式表ID
	private String cash;//面额
	private String goodsId;//商品id
	private String productCode;//产品标识码
	private String productName;//产品名称
	private String goodsParameter;//私有字段
	private String remark;//备注
	private String commConfig;//佣金配置
	private String starLevelId;//星级表id
	private String starLevelName;
	private String rate;//佣金
	private int  rownum;//显示行号 
	private String goodName;	//自定义商品名称
	private String goodsName; //商品名称（全称）
	
	public String getOperatorId() {
		return operatorId;
	}
	
	public String getGoodsParameter() {
		return goodsParameter;
	}
	public void setGoodsParameter(String goodsParameter) {
		this.goodsParameter = goodsParameter;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCommissionTypeId() {
		return commissionTypeId;
	}
	public void setCommissionTypeId(String commissionTypeId) {
		this.commissionTypeId = commissionTypeId;
	}
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCommConfig() {
		return commConfig;
	}
	public void setCommConfig(String commConfig) {
		this.commConfig = commConfig;
	}
	public String getStarLevelId() {
		return starLevelId;
	}
	public void setStarLevelId(String starLevelId) {
		this.starLevelId = starLevelId;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOrgDesc() {
		return orgDesc;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	

	public String getCommissionTypeName() {
		return commissionTypeName;
	}

	public void setCommissionTypeName(String commissionTypeName) {
		this.commissionTypeName = commissionTypeName;
	}

	public String getStarLevelName() {
		return starLevelName;
	}

	public void setStarLevelName(String starLevelName) {
		this.starLevelName = starLevelName;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}


	
	
}
