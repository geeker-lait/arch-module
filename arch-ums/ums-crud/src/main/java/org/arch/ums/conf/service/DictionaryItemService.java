package org.arch.ums.conf.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.conf.dao.DictionaryItemDao;
import org.arch.ums.conf.entity.DictionaryItem;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典明细(DictionaryItem) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:22:50
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DictionaryItemService extends CrudService<DictionaryItem, java.lang.Long> {

    private final DictionaryItemDao dictionaryItemDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        DictionaryItem entity = new DictionaryItem();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<DictionaryItem> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(DictionaryItem::getDeleted, 1);
        return dictionaryItemDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(DictionaryItem entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<DictionaryItem> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(DictionaryItem::getDeleted, 1);
        // 逻辑删除
        return dictionaryItemDao.update(updateWrapper);
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

        LambdaUpdateWrapper<DictionaryItem> updateWrapper = Wrappers.<DictionaryItem>lambdaUpdate()
                .eq(DictionaryItem::getDeleted, 0)
                .in(DictionaryItem::getId, ids)
                .set(DictionaryItem::getDeleted, 1);

        // 逻辑删除
        return dictionaryItemDao.update(updateWrapper);
    }
}
