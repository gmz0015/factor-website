package org.noah.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

public class ModelRequestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double onlineTime;

    private List<String> text;

    private List<String> img;

    public Double getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Double onlineTime) {
        this.onlineTime = onlineTime;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
}
