package com.kd.core.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 卡账户日志--Dto
 * @author zlm
 *
 */
@XmlRootElement
public class CardAccLogDto {
	
	private List<String> tradeTypes;
	private List<String> cardModelTypes;
	private List<String> psamNos; 
	private List<String> parkTypes;
	private List<String> cardTypes;
	
	public List<String> getTradeTypes() {
		return tradeTypes;
	}
	public void setTradeTypes(List<String> tradeTypes) {
		this.tradeTypes = tradeTypes;
	}
	public List<String> getCardModelTypes() {
		return cardModelTypes;
	}
	public void setCardModelTypes(List<String> cardModelTypes) {
		this.cardModelTypes = cardModelTypes;
	}
	public List<String> getPsamNos() {
		return psamNos;
	}
	public void setPsamNos(List<String> psamNos) {
		this.psamNos = psamNos;
	}
	public List<String> getParkTypes() {
		return parkTypes;
	}
	public void setParkTypes(List<String> parkTypes) {
		this.parkTypes = parkTypes;
	}
	public List<String> getCardTypes() {
		return cardTypes;
	}
	public void setCardTypes(List<String> cardTypes) {
		this.cardTypes = cardTypes;
	}
	
	
	
	
}
