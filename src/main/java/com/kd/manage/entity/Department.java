package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="department")
public class Department extends BaseEntity{

    private String departmentNo;

    private String name;

    private String remark;


    public String getDepartmentNo() {
        return departmentNo;
    }

    public void setDepartmentNo(String departmentNo) {
        this.departmentNo = departmentNo == null ? null : departmentNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}