package org.arch.ums.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.entity.UserAddress;
import org.arch.ums.mapper.UserAddressMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户地址表(user_address)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class UserAddressDao extends ServiceImpl<UserAddressMapper, UserAddress> {

}