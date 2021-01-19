package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.UserIdcard;
import code.mapper.UserIdcardMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户-身份信息(user_idcard)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class UserIdcardDao extends ServiceImpl<UserIdcardMapper, UserIdcard> {

}