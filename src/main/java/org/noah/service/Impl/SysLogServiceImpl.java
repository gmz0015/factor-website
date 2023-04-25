package org.noah.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.noah.entity.PageEntity;
import org.noah.entity.RoleEntity;
import org.noah.entity.SysLogEntity;
import org.noah.mapper.SysLogMapper;
import org.noah.service.SysLogService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogEntity> implements SysLogService {
    @Override
    public IPage<SysLogEntity> getListByPage(PageEntity page) {
        IPage<SysLogEntity> sysLogEntityIPage = page(new Page<SysLogEntity>(page.getPage(), page.getLimit()));
        return sysLogEntityIPage;
    }

    @Override
    public SysLogEntity getLastLoginDateByUserId(Long userId) {
        LambdaQueryWrapper<SysLogEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysLogEntity::getType, 0);
        queryWrapper.eq(SysLogEntity::getUserId, userId);
        queryWrapper.orderByDesc(SysLogEntity::getTime);
        queryWrapper.last("limit 1");
        return getOne(queryWrapper);
    }
}
