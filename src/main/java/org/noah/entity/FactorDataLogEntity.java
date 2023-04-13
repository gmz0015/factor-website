package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "factor_data_log", autoResultMap=true)
public class FactorDataLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long logId;

    private Long dataId;
}
