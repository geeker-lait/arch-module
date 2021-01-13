package org.arch.application.demo.crud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.arch.application.demo.crud.entity.RbacCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资源类目表(rbac_category)数据Mapper
 *
 * @author lait
 * @description
 * @since 2020-11-13 10:30:39
 */
@Mapper
public interface RbacCategoryMapper extends BaseMapper<RbacCategory> {

}
