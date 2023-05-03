package org.noah.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.noah.entity.*;
import org.noah.mapper.UserMapper;
import org.noah.service.RoleService;
import org.noah.service.UserRoleService;
import org.noah.service.UserService;
import org.noah.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserEntity getByUserName(String userName) {
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUsername, userName);
        return getOne(queryWrapper);
    }

    @Override
    public UserEntity register(String username, String password) throws CommonException {
        // 检测用户是否已注册过
        UserEntity userEntity = getByUserName(username);
        if (userEntity == null) {
            // 用户未注册过
            String salt = JwtUtils.createSalt(32);
            password = JwtUtils.md5(password, salt);
            userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userEntity.setSalt(salt);
            userEntity.setOnlineTime(0D);
            baseMapper.insert(userEntity);

            // 赋予默认角色
            RoleEntity roleEntity = roleService.getDefaultRole();

            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userEntity.getId());
            userRoleEntity.setRoleId(roleEntity.getId());
            userRoleService.save(userRoleEntity);
        }

        return userEntity;
    }

    @Override
    public IPage<UserEntity> getListByPage(PageEntity page) {
        IPage<UserEntity> userEntityIPage = page(new Page<UserEntity>(page.getPage(), page.getLimit()));
        return userEntityIPage;
    }
}
