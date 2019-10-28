package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
* @ClassName: MeetNotice 会议室屏幕通知栏
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author glt 
* @date 2016年7月11日 下午2:07:03 
*
 */
@XmlRootElement(name="meetNotice")
public class MeetNotice extends BaseEntity{
    
	public String getMeetNoticeName() {
		return meetNoticeName;
	}
	public void setMeetNoticeName(String meetNoticeName) {
		this.meetNoticeName = meetNoticeName;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


	//会议室通知内容
    private String meetNoticeContent;
    //会议室名称
    private String meetNoticeName;
    
    //会议室类型
    private String meetRoomID;
   
	//状态
    private Integer status;
	public String getMeetNoticeContent() {
		return meetNoticeContent;
	}
	public void setMeetNoticeContent(String meetNoticeContent) {
		this.meetNoticeContent = meetNoticeContent;
	}
	public String getMeetRoomID() {
		return meetRoomID;
	}
	public void setMeetRoomID(String meetRoomID) {
		this.meetRoomID = meetRoomID;
	}
    private String meetRoomName;
    public String getMeetRoomName() {
		return meetRoomName;
	}
	public void setMeetRoomName(String meetRoomName) {
		this.meetRoomName = meetRoomName;
	}

    
}
