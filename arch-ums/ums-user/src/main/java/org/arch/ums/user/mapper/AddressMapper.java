package org.arch.ums.user.mapper;

import org.arch.framework.crud.CrudMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.user.entity.Address;

/**
 * 用户地址表(Address) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date   2021-03-17 22:31:29
 * @since  1.0.0
 */
@Mapper
public interface AddressMapper extends CrudMapper<Address> {

}
