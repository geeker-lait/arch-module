package org.arch.ums.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.user.entity.Education;

/**
 * 用户学历信息(Education) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:04:35
 * @since 1.0.0
 */
@Mapper
public interface EducationMapper extends BaseMapper<Education> {

}