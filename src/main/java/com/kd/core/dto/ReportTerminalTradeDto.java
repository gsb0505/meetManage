package com.kd.core.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wzf on 2017/3/11.
 */
@XmlRootElement
public class ReportTerminalTradeDto {
    private List<String> tradeTypes;

    public List<String> getTradeTypes() {
        return tradeTypes;
    }

    public void setTradeTypes(List<String> tradeTypes) {
        this.tradeTypes = tradeTypes;
    }
}
