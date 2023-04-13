package org.noah.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModelRequestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double onlineTime;

    private List<String> text;

    private List<String> img;
}
