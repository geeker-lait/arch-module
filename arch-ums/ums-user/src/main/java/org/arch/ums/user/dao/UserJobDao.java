package org.arch.ums.dao;

import lombok.extern.slf4j.Slf4j;
import org.arch.ums.entity.UserJob;
import org.arch.ums.mapper.UserJobMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户工作信息(user_job)数据DAO
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
 */
@Slf4j
@Repository
public class UserJobDao extends ServiceImpl<UserJobMapper, UserJob> {

}