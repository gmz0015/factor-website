package org.noah.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.noah.entity.RoleEntity;
import org.noah.mapper.RoleMapper;
import org.noah.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
    @Override
    public RoleEntity getByName(String name) {
        LambdaQueryWrapper<RoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleEntity::getName, name);
        return getOne(queryWrapper);
    }

    @Override
    public RoleEntity getDefaultRole() {
        LambdaQueryWrapper<RoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleEntity::getName, "visitor");
        return getOne(queryWrapper);
    }
}
