package org.noah.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.noah.entity.RoleEntity;
import org.noah.entity.UserEntity;

import java.util.List;
import java.util.Set;

public interface RoleService extends IService<RoleEntity> {
    public RoleEntity getByName(String name);

    public RoleEntity getDefaultRole();
}
