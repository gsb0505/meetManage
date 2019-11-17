package com.kd.manage.base;

import java.io.Serializable;

/** Respnse返回封装对象
 * @author: latham
 * @Date: 2019/11/15 22:14
 **/
public class BtResponse implements Serializable {
    private static final long serialVersionUID = 1611654533642807051L;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 消息内容
     */
    private String message;

    public BtResponse() {
    }

    public BtResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BtResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
