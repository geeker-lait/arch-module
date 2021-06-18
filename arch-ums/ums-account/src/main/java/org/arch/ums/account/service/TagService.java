package org.arch.ums.account.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.account.dao.TagDao;
import org.arch.ums.account.entity.Tag;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 账号-标签(Tag) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-02-26 13:28:43
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class TagService extends CrudService<Tag, java.lang.Long> {
    private final TagDao tagDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Tag entity = new Tag();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Tag> updateWrapper = Wrappers.<Tag>lambdaUpdate(entity)
                .set(Tag::getDeleted, 1);
        return tagDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Tag entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Tag> updateWrapper = Wrappers.<Tag>lambdaUpdate(entity)
                .set(Tag::getDeleted, 1);
        // 逻辑删除
        return tagDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Tag> updateWrapper = Wrappers.<Tag>lambdaUpdate()
                .eq(Tag::getDeleted, 0)
                .in(Tag::getId, ids)
                .set(Tag::getDeleted, 1);

        // 逻辑删除
        return tagDao.update(updateWrapper);
    }
}
