package org.arch.ums.mapper;

import org.arch.ums.entity.UserPhone;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户电话信息(user_phone)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface UserPhoneMapper extends BaseMapper<UserPhone> {

}
