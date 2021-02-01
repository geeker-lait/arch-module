package org.arch.ums.account.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.account.entity.Post;
import org.arch.ums.account.mapper.PostMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-岗位(Post) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:19:09
 * @since 1.0.0
 */
@Slf4j
@Repository
public class PostDao extends ServiceImpl<PostMapper, Post> implements CrudDao<Post> {

}