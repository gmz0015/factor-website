package org.noah.entity;

import lombok.Data;

import java.io.Serializable;

public class ModelResponseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String res_code;

    private String res_desc;

    private Double score;

    public ModelResponseEntity() {
    }

    @Override
    public String toString() {
        return "ModelResponseEntity{" +
                "res_code='" + res_code + '\'' +
                ", res_desc='" + res_desc + '\'' +
                ", score=" + score +
                '}';
    }

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public String getRes_desc() {
        return res_desc;
    }

    public void setRes_desc(String res_desc) {
        this.res_desc = res_desc;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
