package com.kd.core.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SellCardReportDto {
	
	
	private List<String> cardTypes;
	
	private List<String> operTypes;
	
	private List<String> branchNos;
	
	private List<String> creators;

	public List<String> getCardTypes() {
		return cardTypes;
	}

	public void setCardTypes(List<String> cardTypes) {
		this.cardTypes = cardTypes;
	}

	public List<String> getOperTypes() {
		return operTypes;
	}

	public void setOperTypes(List<String> operTypes) {
		this.operTypes = operTypes;
	}


	public List<String> getBranchNos() {
		return branchNos;
	}

	public void setBranchNos(List<String> branchNos) {
		this.branchNos = branchNos;
	}

	public List<String> getCreators() {
		return creators;
	}

	public void setCreators(List<String> creators) {
		this.creators = creators;
	}

	
}
