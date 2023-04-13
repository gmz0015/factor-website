package org.noah.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.noah.entity.FactorDataLogEntity;

import java.util.List;

public interface FactorDataLogService extends IService<FactorDataLogEntity> {
    public List<Long> getDataIdByLogId(Long logId);
}
