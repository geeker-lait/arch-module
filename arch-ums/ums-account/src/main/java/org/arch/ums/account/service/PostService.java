package org.arch.ums.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.entity.Post;
import org.springframework.stereotype.Service;

/**
 * 账号-岗位(Post) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:19:12
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PostService extends CrudService<Post, java.lang.Long> {

}