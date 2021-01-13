package org.arch.ums.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.user.entity.UserEducation;
import org.arch.ums.mapper.UserEducationMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户学历信息(user_education)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class UserEducationDao extends ServiceImpl<UserEducationMapper, UserEducation> {

}