package org.noah.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.noah.entity.FactorEntity;
import org.noah.entity.FactorLogEntity;
import org.noah.entity.PageEntity;
import org.noah.mapper.FactorLogMapper;
import org.noah.service.FactorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FactorLogServiceImpl extends ServiceImpl<FactorLogMapper, FactorLogEntity> implements FactorLogService {
    @Autowired
    private FactorLogMapper factorLogMapper;

    @Override
    public IPage<FactorLogEntity> getListByPage(PageEntity page) {
        Page<FactorLogEntity> faceServiceLogPage = new Page<FactorLogEntity>(page.getPage(), page.getLimit());
        faceServiceLogPage.addOrder(OrderItem.desc("time"));
        IPage<FactorLogEntity> factorLogEntityIPage = page(faceServiceLogPage);
        return factorLogEntityIPage;
    }

    @Override
    public List<FactorLogEntity> getList() {
        return factorLogMapper.getGroupList();
    }
}
