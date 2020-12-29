package org.arch.ums.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.entity.UserIdCard;
import org.arch.ums.mapper.UserIdCardMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户身份证表(user_id_card)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class UserIdCardDao extends ServiceImpl<UserIdCardMapper, UserIdCard> {

}