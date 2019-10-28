package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * 
* @ClassName: StatementReport 
* @Description: 结算报表实体类
* @author Giles 
* @date 2016年11月14日 下午2:26:51 
*
 */
@XmlRootElement(name="userSReport")
public class UserSReport extends BaseEntity{
	
	//交易类型
	private String tradeType;
	//交易笔数
	private String tradeCount;
	//交易金额
	private String tradeAmount;
	//交易押金
	private String tradeDeposit;
	//交易模式
	private String tradePattern;
	//终端
	private MeetRoom terminal;
	/**
	 * @return the tradeType
	 */
	public String getTradeType() {
		return tradeType;
	}
	/**
	 * @param tradeType the tradeType to set
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	/**
	 * @return the tradeCount
	 */
	public String getTradeCount() {
		return tradeCount;
	}
	/**
	 * @param tradeCount the tradeCount to set
	 */
	public void setTradeCount(String tradeCount) {
		this.tradeCount = tradeCount;
	}
	/**
	 * @return the tradeAmount
	 */
	public String getTradeAmount() {
		return tradeAmount;
	}
	/**
	 * @param tradeAmount the tradeAmount to set
	 */
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	/**
	 * @return the tradeDeposit
	 */
	public String getTradeDeposit() {
		return tradeDeposit;
	}
	/**
	 * @param tradeDeposit the tradeDeposit to set
	 */
	public void setTradeDeposit(String tradeDeposit) {
		this.tradeDeposit = tradeDeposit;
	}
	public String getTradePattern() {
		return tradePattern;
	}
	public void setTradePattern(String tradePattern) {
		this.tradePattern = tradePattern;
	}
	public MeetRoom getTerminal() {
		return terminal;
	}
	public void setTerminal(MeetRoom terminal) {
		this.terminal = terminal;
	} 
	
	
}
