package org.noah.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.noah.entity.FactorEntity;
import org.noah.entity.PageEntity;
import org.noah.mapper.FactorMapper;
import org.noah.service.FactorService;
import org.springframework.stereotype.Service;

@Service
public class FactorServiceImpl extends ServiceImpl<FactorMapper, FactorEntity> implements FactorService {
    @Override
    public IPage<FactorEntity> getListByPage(PageEntity page) {
        IPage<FactorEntity> factorEntityIPage = page(new Page<FactorEntity>(page.getPage(), page.getLimit()));
        return factorEntityIPage;
    }
}
