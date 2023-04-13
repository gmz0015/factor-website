package org.noah.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.noah.entity.FactorEntity;
import org.noah.entity.PageEntity;
import org.noah.entity.UserEntity;

public interface FactorService extends IService<FactorEntity> {
    public IPage<FactorEntity> getListByPage(PageEntity page);
}
