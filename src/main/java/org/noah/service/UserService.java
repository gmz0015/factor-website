package org.noah.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.noah.entity.CommonException;
import org.noah.entity.PageEntity;
import org.noah.entity.UserEntity;

public interface UserService extends IService<UserEntity> {
    public UserEntity getByUserName(String userName);

    public UserEntity register(String username, String password) throws CommonException;

    public IPage<UserEntity> getListByPage(PageEntity page);

    public boolean deleteById(Long userId);
}
