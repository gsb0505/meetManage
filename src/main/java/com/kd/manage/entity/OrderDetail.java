package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="orderDetail")
public class OrderDetail extends BaseEntity {

	public String getGlideNo() {
		return glideNo;
	}

	public void setGlideNo(String glideNo) {
		this.glideNo = glideNo;
	}

	public String getMeetName() {
		return meetName;
	}

	public void setMeetName(String meetName) {
		this.meetName = meetName;
	}

	public String getMeetDate() {
		return meetDate;
	}

	public void setMeetDate(String meetDate) {
		this.meetDate = meetDate;
	}

	public String getMeetStartTime() {
		return meetStartTime;
	}

	public void setMeetStartTime(String meetStartTime) {
		this.meetStartTime = meetStartTime;
	}

	public String getMeetEndTime() {
		return meetEndTime;
	}

	public void setMeetEndTime(String meetEndTime) {
		this.meetEndTime = meetEndTime;
	}

	public String getSpecialdemand() {
		return specialdemand;
	}

	public void setSpecialdemand(String specialdemand) {
		this.specialdemand = specialdemand;
	}

	public String getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(String emailNotification) {
		this.emailNotification = emailNotification;
	}

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getErrCode() {
		return errCode;
	}

	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}

	//流水号
    private String glideNo;
    //预约名称
    private String meetName;

    //会议时间
    private String meetDate;
    //会议开始时间
    private String meetStartTime;
    //会议结束时间
    private String meetEndTime;
    
	//特别需求
    private String specialdemand;
    //邮件额外通知
    private String emailNotification;


	//会议室ID
    private String meetRoomID;
    //会议室名称
    private String meetRoomName;
    //备注
    private String remark;
    //状态
    private Integer errCode;
    private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
