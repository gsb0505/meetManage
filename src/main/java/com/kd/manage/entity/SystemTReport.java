package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SystemTReport extends BaseEntity {
	//会议预约时间
	private String meetDate;
	//会议室ID
	private String meetRoomID;
	//会议室名称
	private String meetRoomName;
	//机构ID
	private String orgId;
	//机构名称
	private  String orgname	;
	//会议总合
	private String meetCount;
	
	//报表类型
	private String reportType;

	public String getMeetDate() {
		return meetDate;
	}

	public void setMeetDate(String meetDate) {
		this.meetDate = meetDate;
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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getMeetCount() {
		return meetCount;
	}

	public void setMeetCount(String meetCount) {
		this.meetCount = meetCount;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	
	
}
