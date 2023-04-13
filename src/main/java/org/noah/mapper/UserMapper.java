package org.noah.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.noah.entity.UserEntity;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
