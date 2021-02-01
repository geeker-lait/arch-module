package org.arch.ums.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.account.entity.RoleGroup;

/**
 * 账号-角色组织或机构(RoleGroup) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:25:31
 * @since 1.0.0
 */
@Mapper
public interface RoleGroupMapper extends BaseMapper<RoleGroup> {

}