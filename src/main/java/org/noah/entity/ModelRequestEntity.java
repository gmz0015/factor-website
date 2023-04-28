package org.noah.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

public class ModelRequestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double online_time;

    private List<String> text;

    private List<String> img;

    @Override
    public String toString() {
        return "ModelRequestEntity{" +
                "online_time=" + online_time +
                ", text=" + text +
                ", img=" + img +
                '}';
    }

    public Double getOnline_time() {
        return online_time;
    }

    public void setOnline_time(Double online_time) {
        this.online_time = online_time;
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
