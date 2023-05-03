package org.noah.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.noah.entity.FactorDataEntity;
import org.noah.mapper.FactorDataMapper;
import org.noah.service.FactorDataService;
import org.springframework.stereotype.Service;

@Service
public class FactorDataServiceImpl extends ServiceImpl<FactorDataMapper, FactorDataEntity> implements FactorDataService {
}
