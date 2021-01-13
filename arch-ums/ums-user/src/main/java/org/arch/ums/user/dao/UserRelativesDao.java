package org.arch.ums.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.user.entity.UserRelatives;
import org.arch.ums.mapper.UserRelativesMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户亲朋信息(user_relatives)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class UserRelativesDao extends ServiceImpl<UserRelativesMapper, UserRelatives> {

}