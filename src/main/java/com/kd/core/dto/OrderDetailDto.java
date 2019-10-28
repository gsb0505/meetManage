package com.kd.core.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 交易明细--dto
 * @author zlm
 *
 */
@XmlRootElement
public class OrderDetailDto {
	
	//商户ids
	private List<String> agentIds;
	
	private List<String> parks;
	
	private List<String> branchNos;
	
	private List<String> cardTypes;

	public List<String> getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(List<String> agentIds) {
		this.agentIds = agentIds;
	}

	public List<String> getParks() {
		return parks;
	}

	public void setParks(List<String> parks) {
		this.parks = parks;
	}

	public List<String> getBranchNos() {
		return branchNos;
	}

	public void setBranchNos(List<String> branchNos) {
		this.branchNos = branchNos;
	}

	public List<String> getCardTypes() {
		return cardTypes;
	}

	public void setCardTypes(List<String> cardTypes) {
		this.cardTypes = cardTypes;
	}



	
	
	
}
