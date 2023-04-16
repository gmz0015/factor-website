package org.noah.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@TableName(value = "factor_data_log", autoResultMap=true)
public class FactorDataLogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long logId;

    private Long dataId;

    public FactorDataLogEntity() {
    }

    public FactorDataLogEntity(Long id, Long logId, Long dataId) {
        this.id = id;
        this.logId = logId;
        this.dataId = dataId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }
}
