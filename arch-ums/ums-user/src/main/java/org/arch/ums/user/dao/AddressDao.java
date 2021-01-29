package org.arch.ums.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.user.entity.Address;
import org.arch.ums.user.mapper.AddressMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户地址表(Address) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:07:18
 * @since 1.0.0
 */
@Slf4j
@Repository
public class AddressDao extends ServiceImpl<AddressMapper, Address> implements CrudDao<Address> {

}