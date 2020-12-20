package org.arch.auth.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.auth.rbac.entity.RbacMenu;

/**
 * 权限表(rbac_menu)数据Mapper
 *
 * @author lait
 * @description
 * @since 2020-11-13 10:30:39
 */
@Mapper
public interface RbacMenuMapper extends BaseMapper<RbacMenu> {

}
