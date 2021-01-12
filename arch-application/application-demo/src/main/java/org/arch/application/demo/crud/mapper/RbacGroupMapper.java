package org.arch.application.demo.crud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.arch.application.demo.crud.entity.RbacGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * 组织机构表(rbac_group)数据Mapper
 *
 * @author lait
 * @since 2020-11-13 10:30:39
 * @description 
*/
@Mapper
public interface RbacGroupMapper extends BaseMapper<RbacGroup> {

}
