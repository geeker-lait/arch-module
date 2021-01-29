package org.arch.ums.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.user.entity.Phone;

/**
 * 用户电话信息(Phone) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:09:10
 * @since 1.0.0
 */
@Mapper
public interface PhoneMapper extends BaseMapper<Phone> {

}