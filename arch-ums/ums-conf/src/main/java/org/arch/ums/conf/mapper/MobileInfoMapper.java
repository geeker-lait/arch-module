package org.arch.ums.conf.mapper;

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.conf.entity.MobileInfo;

/**
 * 手机号归属地信息(MobileInfo) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-03-15 21:48:37
 * @since 1.0.0
 */
@Mapper
public interface MobileInfoMapper extends CrudMapper<MobileInfo> {

}
