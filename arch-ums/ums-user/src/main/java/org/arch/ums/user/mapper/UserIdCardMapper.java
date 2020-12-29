package org.arch.ums.mapper;

import org.arch.ums.entity.UserIdCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户身份证表(user_id_card)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface UserIdCardMapper extends BaseMapper<UserIdCard> {

}
