package com.kd.core.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.kd.manage.entity.BaseEntity;
@XmlRootElement
public class AgentAccountDto extends BaseEntity{
	private String agentId;//商户id
	private String accountCode; // 代理商账户代码
	private String agentDesc;//商户名称
	private String agentName;//代理商姓名
	private String money; // 金额(保存的时候小数点后面3位)
	private String trustedCash; // 受信额度
	private String freezeCash; // 冻结资金
	private String flag; // 账户状态
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
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

	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTrustedCash() {
		return trustedCash;
	}
	public void setTrustedCash(String trustedCash) {
		this.trustedCash = trustedCash;
	}
	public String getFreezeCash() {
		return freezeCash;
	}
	public void setFreezeCash(String freezeCash) {
		this.freezeCash = freezeCash;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}
