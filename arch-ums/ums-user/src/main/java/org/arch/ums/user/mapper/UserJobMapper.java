package org.arch.ums.mapper;

import org.arch.ums.entity.UserJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户工作信息(user_job)数据Mapper
 *
 * @author lait
 * @since 2020-12-26 21:57:25
 * @description 
*/
@Mapper
public interface UserJobMapper extends BaseMapper<UserJob> {

}
