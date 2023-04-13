package org.noah.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.noah.entity.UserRoleEntity;

import java.util.List;

public interface UserRoleService extends IService<UserRoleEntity> {
    public List<UserRoleEntity> getByUserId(Long userId);

    public Boolean deleteByUserId(Long userId);
}
