package org.arch.ums.mapper;

import org.arch.ums.user.entity.UserEducation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户学历信息(user_education)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface UserEducationMapper extends BaseMapper<UserEducation> {

}
