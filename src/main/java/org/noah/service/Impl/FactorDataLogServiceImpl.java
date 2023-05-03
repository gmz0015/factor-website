package org.noah.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.noah.entity.FactorDataLogEntity;
import org.noah.mapper.FactorDataLogMapper;
import org.noah.service.FactorDataLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FactorDataLogServiceImpl extends ServiceImpl<FactorDataLogMapper, FactorDataLogEntity> implements FactorDataLogService {
    @Override
    public List<Long> getDataIdByLogId(Long logId) {
        QueryWrapper<FactorDataLogEntity> wrapper = new QueryWrapper();
        wrapper.eq("log_id", logId);
        List<FactorDataLogEntity> factorDataLogEntityList = list(wrapper);
        return factorDataLogEntityList.parallelStream().map(FactorDataLogEntity::getDataId).collect(Collectors.toList());
    }
}
