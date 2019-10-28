/**
 * 
 */
package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 *@类名称：CommissionConfig.java
 *@类描述：佣金配置实体类

 *@创建时间：2015年1月21日-下午2:46:10
 *@修改备注:
 *@version
 */
@XmlRootElement(name="commissionConfig")
public class CommissionConfig extends BaseEntity{
	private String starLevelId;//星级表id
	private String commissionTypeId;//佣金方式表ID
	private String rate;//佣金
	private String remark;//备注
	private String goodsId;
	
	public String getStarLevelId() {
		return starLevelId;
	}
	public void setStarLevelId(String starLevelId) {
		this.starLevelId = starLevelId;
	}
	public String getCommissionTypeId() {
		return commissionTypeId;
	}
	public void setCommissionTypeId(String commissionTypeId) {
		this.commissionTypeId = commissionTypeId;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	
}
