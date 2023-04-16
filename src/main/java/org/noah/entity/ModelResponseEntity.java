package org.noah.entity;

import lombok.Data;

import java.io.Serializable;

public class ModelResponseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resCode;

    private String resDesc;

    private Double score;

    public ModelResponseEntity() {
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
