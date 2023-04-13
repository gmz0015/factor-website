package org.noah.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.noah.entity.UserRoleEntity;
import org.noah.mapper.UserRoleMapper;
import org.noah.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleEntity> implements UserRoleService {
    @Override
    public List<UserRoleEntity> getByUserId(Long userId) {
        LambdaQueryWrapper<UserRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRoleEntity::getUserId, userId);
        return list(queryWrapper);
    }

    @Override
    public Boolean deleteByUserId(Long userId) {
        LambdaQueryWrapper<UserRoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRoleEntity::getUserId, userId);
        return remove(queryWrapper);
    }
}
