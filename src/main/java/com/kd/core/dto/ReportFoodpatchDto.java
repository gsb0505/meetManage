package com.kd.core.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by wzf on 2017/3/7.
 */
@XmlRootElement
public class ReportFoodpatchDto {
    private List<String> parkNames;

    public List<String> getParkNames() {
        return parkNames;
    }

    public void setParkNames(List<String> parkNames) {
        this.parkNames = parkNames;
    }
    private List<String> organNames;

    public List<String> getOrganNames() {
        return organNames;
    }

    public void setOrganNames(List<String> organNames) {
        this.organNames = organNames;
    }
}
