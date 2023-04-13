package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.noah.handler.BlobTypeHandler;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "factor_log", autoResultMap=true)
public class FactorLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /*
    标签
     */
    private String tag;


    private Long userId;

    private Double score;

    private Double onlineTime;

    private Date time;

    @TableField(exist = false)
    private String totalScore;

    @TableField(exist = false)
    private String totalOnlineTime;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private List<FactorDataEntity> datas;
}
