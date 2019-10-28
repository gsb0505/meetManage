package com.kd.core.dto;

import javax.xml.bind.annotation.XmlRootElement;

import com.kd.manage.entity.BaseEntity;

/**
 *
 *@类名称：AgentInAndOutDto.java
 *@类描述：

 *@创建时间：2015年3月19日-下午12:59:42
 *@修改备注:
 *@version 
 */
@XmlRootElement
public class AgentInAndOutDto extends BaseEntity{
	
	private String agentId;
	private String collectPeople;
	private String interfaceAccountName;//接口账户名称
	private String interfaceaccount_id; //接口账户编号
	private String productId;
	private String productName;
	private String busniessHandle;//业务操作
	private long accountLogAmount;//账户日志总计金额
	private long detailAmount;//明细总计金额
	private long balance;//数据差额
	private long  beforeRecordAmount;//之前记录当日处理金额
	private long todayRecord;//当日记录之后处理金额
	private long historyBalance;//历史处理金额
	private String enterDateFrom;
	private String enterDateTo;
	private String pName;
	private String statisticsTime;//统计时间
	private String time; //创建时间
	private String chargeTime;
	public String getInterfaceAccountName() {
		return interfaceAccountName;
	}
	public void setInterfaceAccountName(String interfaceAccountName) {
		this.interfaceAccountName = interfaceAccountName;
	}
	public String getBusniessHandle() {
		return busniessHandle;
	}
	public void setBusniessHandle(String busniessHandle) {
		this.busniessHandle = busniessHandle;
	}
	public long getAccountLogAmount() {
		return accountLogAmount;
	}
	public void setAccountLogAmount(long accountLogAmount) {
		this.accountLogAmount = accountLogAmount;
	}
	public long getDetailAmount() {
		return detailAmount;
	}
	public void setDetailAmount(long detailAmount) {
		this.detailAmount = detailAmount;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public long getBeforeRecordAmount() {
		return beforeRecordAmount;
	}
	public void setBeforeRecordAmount(long beforeRecordAmount) {
		this.beforeRecordAmount = beforeRecordAmount;
	}
	public long getTodayRecord() {
		return todayRecord;
	}
	public void setTodayRecord(long todayRecord) {
		this.todayRecord = todayRecord;
	}
	public long getHistoryBalance() {
		return historyBalance;
	}
	public void setHistoryBalance(long historyBalance) {
		this.historyBalance = historyBalance;
	}
	
	
	public String getEnterDateFrom() {
		return enterDateFrom;
	}
	public void setEnterDateFrom(String enterDateFrom) {
		this.enterDateFrom = enterDateFrom;
	}
	public String getEnterDateTo() {
		return enterDateTo;
	}
	public void setEnterDateTo(String enterDateTo) {
		this.enterDateTo = enterDateTo;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getStatisticsTime() {
		return statisticsTime;
	}
	public void setStatisticsTime(String statisticsTime) {
		this.statisticsTime = statisticsTime;
	}
	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}
	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	/**
	 * @return the interfaceaccount_id
	 */
	public String getInterfaceaccount_id() {
		return interfaceaccount_id;
	}
	/**
	 * @param interfaceaccount_id the interfaceaccount_id to set
	 */
	public void setInterfaceaccount_id(String interfaceaccount_id) {
		this.interfaceaccount_id = interfaceaccount_id;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}
	public String getCollectPeople() {
		return collectPeople;
	}
	public void setCollectPeople(String collectPeople) {
		this.collectPeople = collectPeople;
	}

}
