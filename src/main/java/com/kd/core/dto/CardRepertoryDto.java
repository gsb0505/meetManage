package com.kd.core.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CardRepertoryDto {

	private List<String> cardTypes;
	private List<String> cardMolds;
	private List<String> parks;
	private List<String> employeeClasss;
	
	
	public List<String> getCardTypes() {
		return cardTypes;
	}
	public void setCardTypes(List<String> cardTypes) {
		this.cardTypes = cardTypes;
	}
	public List<String> getCardMolds() {
		return cardMolds;
	}
	public void setCardMolds(List<String> cardMolds) {
		this.cardMolds = cardMolds;
	}
	public List<String> getParks() {
		return parks;
	}
	public void setParks(List<String> parks) {
		this.parks = parks;
	}
	public List<String> getEmployeeClasss() {
		return employeeClasss;
	}
	public void setEmployeeClasss(List<String> employeeClasss) {
		this.employeeClasss = employeeClasss;
	}
	
	
}
