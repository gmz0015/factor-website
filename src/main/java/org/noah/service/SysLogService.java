package org.noah.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.noah.entity.PageEntity;
import org.noah.entity.SysLogEntity;

public interface SysLogService extends IService<SysLogEntity> {
    public IPage<SysLogEntity> getListByPage(PageEntity page);

    public SysLogEntity getLastLoginDateByUserId(Long userId);
}
