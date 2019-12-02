package com.kd.manage.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
* @ClassName: meetRoom 会议室管理
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author glt 
* @date 2016年7月11日 下午2:07:03 
*
 */
@XmlRootElement(name="meetRoom")
public class MeetRoom extends BaseEntity{
    public String getMeetRoomID() {
		return meetRoomID;
	}
	public void setMeetRoomID(String meetRoomID) {
		this.meetRoomID = meetRoomID;
	}
	public String getMeetRoomName() {
		return meetRoomName;
	}
	public void setMeetRoomName(String meetRoomName) {
		this.meetRoomName = meetRoomName;
	}
	public String getMeetRoomType() {
		return meetRoomType;
	}
	public void setMeetRoomType(String meetRoomType) {
		this.meetRoomType = meetRoomType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	//会议室编号
    private String meetRoomID;
    //会议室名称
    private String meetRoomName;
    
    //会议室类型
    private String meetRoomType;
    private String meetRoomTypeName;
    public String getMeetRoomTypeName() {
		return meetRoomTypeName;
	}
	public void setMeetRoomTypeName(String meetRoomTypeName) {
		this.meetRoomTypeName = meetRoomTypeName;
	}

	//状态
    private Integer status;
   //负责人 
    private String person;
  //负责人 
    private Integer personID;
    //图片
	private String photoUrl;
    
    public Integer getPersonID() {
		return personID;
	}
	public void setPersonID(Integer personID) {
		this.personID = personID;
	}

	//终端编号
    private String terminalNo;  
    private List<OrderDetail> orderDetailList;
   	public List<OrderDetail> getOrderDetailList() {
   		return orderDetailList;
   	}
   	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
   		this.orderDetailList = orderDetailList;
   	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
}

