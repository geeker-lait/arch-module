package org.arch.ums.conf.mapper;

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.conf.entity.MobileSegment;

/**
 * 手机号段信息(MobileSegment) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:48:35
 * @since 1.0.0
 */
@Mapper
public interface MobileSegmentMapper extends CrudMapper<MobileSegment> {

}
