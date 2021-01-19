package code.mapper;

import code.entity.UserEducation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户-学历信息(user_education)数据Mapper
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
*/
@Mapper
public interface UserEducationMapper extends BaseMapper<UserEducation> {

}
