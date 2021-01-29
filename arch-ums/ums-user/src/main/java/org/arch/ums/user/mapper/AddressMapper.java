package org.arch.ums.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.user.entity.Address;

/**
 * 用户地址表(Address) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:07:25
 * @since 1.0.0
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {

}