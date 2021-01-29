package org.arch.ums.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.user.entity.Relatives;

/**
 * 用户亲朋信息(Relatives) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:10:11
 * @since 1.0.0
 */
@Mapper
public interface RelativesMapper extends BaseMapper<Relatives> {

}