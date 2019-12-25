package com.kd.core.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


/**
 *
 * @类名称：ResponseMessage.java
 * @类描述: 返回类

 * @创建时间：2015年3月30日-下午1:08:35
 * @修改备注:
 * @version
 */
@XmlRootElement
public class MessageDto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3454078007166185569L;
    private String retcode;
    private String message;
    public String getRetcode() {
        return retcode;
    }
    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public MessageDto(String retcode, String message) {
        super();
        this.retcode = retcode;
        this.message = message;
    }
    public MessageDto() {
        super();
    }

}

