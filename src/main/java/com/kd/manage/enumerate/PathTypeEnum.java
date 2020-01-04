package com.kd.manage.enumerate;


import com.kd.manage.controller.util.PropertiesUtil;

/** 图片类型枚举
 * @author: latham
 * @Date: 2019/12/29 22:25
 **/
public enum PathTypeEnum {


    PRODUCT("product",PropertiesUtil.readValue("product.prefix")),
    HEADER("header",PropertiesUtil.readValue("head.prefix")),
    MEETROOM("meetroom",PropertiesUtil.readValue("meetRoom.prefix")),


    ;


    private String type;

    private String path;

    PathTypeEnum(String type, String path) {
        this.type = type;
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
