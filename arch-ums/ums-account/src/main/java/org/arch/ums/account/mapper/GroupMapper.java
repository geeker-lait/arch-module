package org.arch.ums.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.account.entity.Group;

/**
 * 账号-组织机构(Group) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:01:49
 * @since 1.0.0
 */
@Mapper
public interface GroupMapper extends BaseMapper<Group> {

}