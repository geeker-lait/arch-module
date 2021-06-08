package org.arch.ums.user.mapper;

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.user.entity.Phone;

/**
 * 用户电话信息(Phone) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-03-17 22:31:28
 * @since 1.0.0
 */
@Mapper
public interface PhoneMapper extends CrudMapper<Phone> {

}
