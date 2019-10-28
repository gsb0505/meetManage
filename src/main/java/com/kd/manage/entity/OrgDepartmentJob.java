package com.kd.manage.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="orgDepartmentJob")
public class OrgDepartmentJob {
    private Integer id;

    private Integer orgId;

    private Integer departmentId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

}