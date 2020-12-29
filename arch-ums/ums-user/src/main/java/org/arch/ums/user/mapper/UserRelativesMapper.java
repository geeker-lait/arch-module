package org.arch.ums.mapper;

import org.arch.ums.entity.UserRelatives;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户亲朋信息(user_relatives)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface UserRelativesMapper extends BaseMapper<UserRelatives> {

}
