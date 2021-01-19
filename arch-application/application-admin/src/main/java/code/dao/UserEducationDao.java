package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.UserEducation;
import code.mapper.UserEducationMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户-学历信息(user_education)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class UserEducationDao extends ServiceImpl<UserEducationMapper, UserEducation> {

}