package org.noah.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.noah.entity.UserRoleEntity;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleEntity> {
}
