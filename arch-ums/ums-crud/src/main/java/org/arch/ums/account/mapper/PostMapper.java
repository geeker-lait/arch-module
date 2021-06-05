package org.arch.ums.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.arch.framework.crud.CrudMapper;
import org.arch.ums.account.entity.Post;

/**
 * 账号-岗位(Post) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:19:16
 * @since 1.0.0
 */
@Mapper
public interface PostMapper extends CrudMapper<Post> {

}