package com.kd.core.dto;


import javax.xml.bind.annotation.XmlRootElement;

import com.kd.manage.entity.BaseEntity;

@XmlRootElement
public class AgentAccountLogDto extends BaseEntity{
	private String glideNo; //交易流水号
	private String createDate;//操作时间
	private String accountCode; // 代理商账户代码
	private String agentId; // 代理商账户代码
	private String preAcc; // 操作前余额
	private String rearAcc; // 操作后余额
	private String agentUserName; // 代理商姓名
	private String flagName; // 状态名称
	private String agentDesc;//商户名称
	private String agentName;//代理商姓名
	private String productName;//产品名称
	private String productCode;//产品代码
	private String cash;//操作金额
	private String money;//操作后余额
	private String discount;//折扣价
	private String detail;//详情
	
	public String getGlideNo() {
		return glideNo;
	}
	public void setGlideNo(String glideNo) {
		this.glideNo = glideNo;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAgentDesc() {
		return agentDesc;
	}
	public void setAgentDesc(String agentDesc) {
		this.agentDesc = agentDesc;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
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
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getPreAcc() {
		return preAcc;
	}
	public void setPreAcc(String preAcc) {
		this.preAcc = preAcc;
	}
	public String getRearAcc() {
		return rearAcc;
	}
	public void setRearAcc(String rearAcc) {
		this.rearAcc = rearAcc;
	}
	public String getAgentUserName() {
		return agentUserName;
	}
	public void setAgentUserName(String agentUserName) {
		this.agentUserName = agentUserName;
	}
	public String getFlagName() {
		return flagName;
	}
	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	

}
