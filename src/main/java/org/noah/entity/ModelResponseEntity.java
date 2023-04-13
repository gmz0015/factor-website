package org.noah.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModelResponseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resCode;

    private String resDesc;

    private Double score;
}
