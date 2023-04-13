package org.noah.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.noah.entity.FactorLogEntity;

import java.util.List;

@Mapper
public interface FactorLogMapper extends BaseMapper<FactorLogEntity> {
    @Select("SELECT *, sum(`score`) as `total_score`, max(`online_time`) as `total_online_time` FROM `factor_log` group by `user_id` order by `total_score` desc;")
    public List<FactorLogEntity> getGroupList();
}
