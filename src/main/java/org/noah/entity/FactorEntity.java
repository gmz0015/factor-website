package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "factor")
public class FactorEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long  id;

    private String name;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
