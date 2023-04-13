package org.noah.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.noah.entity.FactorEntity;
import org.noah.entity.FactorLogEntity;
import org.noah.entity.PageEntity;

import java.util.List;

public interface FactorLogService extends IService<FactorLogEntity> {
    public IPage<FactorLogEntity> getListByPage(PageEntity page);

    public List<FactorLogEntity> getList();
}
