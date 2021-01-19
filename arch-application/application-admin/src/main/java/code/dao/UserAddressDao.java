package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.UserAddress;
import code.mapper.UserAddressMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 用户-地址信息(user_address)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class UserAddressDao extends ServiceImpl<UserAddressMapper, UserAddress> {

}