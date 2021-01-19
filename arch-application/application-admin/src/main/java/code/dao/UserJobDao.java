package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.UserJob;
import code.mapper.UserJobMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户-工作信息(user_job)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class UserJobDao extends ServiceImpl<UserJobMapper, UserJob> {

}