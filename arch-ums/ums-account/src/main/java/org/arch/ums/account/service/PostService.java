package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.PostDao;
import org.arch.ums.account.entity.Post;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号-岗位(Post) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:09
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PostService extends CrudService<Post, java.lang.Long> {
    private final PostDao postDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Post entity = new Post();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Post> updateWrapper = Wrappers.<Post>lambdaUpdate(entity)
                .set(Post::getDeleted, 1);
        return postDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Post entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Post> updateWrapper = Wrappers.<Post>lambdaUpdate(entity)
                .set(Post::getDeleted, 1);
        // 逻辑删除
        return postDao.update(updateWrapper);
    }

    /**
     * 批量逻辑删除
     *
     * @param ids 主键集合
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public boolean deleteAllById(@NonNull List<java.lang.Long> ids) {

        LambdaUpdateWrapper<Post> updateWrapper = Wrappers.<Post>lambdaUpdate()
                .eq(Post::getDeleted, 0)
                .and(w -> w.in(Post::getId, ids))
                .set(Post::getDeleted, 1);

        // 逻辑删除
        return postDao.update(updateWrapper);
    }
}
