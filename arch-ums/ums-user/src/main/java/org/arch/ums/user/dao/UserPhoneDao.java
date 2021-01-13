package org.arch.ums.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.user.entity.UserPhone;
import org.arch.ums.mapper.UserPhoneMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户电话信息(user_phone)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class UserPhoneDao extends ServiceImpl<UserPhoneMapper, UserPhone> {

}