package org.noah.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.noah.entity.FactorDataEntity;
import org.noah.mapper.FactorDataMapper;
import org.noah.service.FactorDataService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FactorDataServiceImpl extends ServiceImpl<FactorDataMapper, FactorDataEntity> implements FactorDataService {
}
