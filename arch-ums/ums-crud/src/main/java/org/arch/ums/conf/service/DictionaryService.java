package org.arch.ums.conf.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudService;
import org.arch.ums.conf.dao.DictionaryDao;
import org.arch.ums.conf.entity.Dictionary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典(Dictionary) 表服务层
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:22:53
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DictionaryService extends CrudService<Dictionary, java.lang.Long> {

    private final DictionaryDao dictionaryDao;

    /**
     * 逻辑删除
     *
     * @param id 主键 id
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(@NonNull java.lang.Long id) {
        Dictionary entity = new Dictionary();
        entity.setId(id);
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Dictionary> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Dictionary::getDeleted, 1);
        return dictionaryDao.update(updateWrapper);
    }

    /**
     * 逻辑删除
     *
     * @param entity 实体
     * @return 是否删除成功
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteById(Dictionary entity) {
        entity.setDeleted(Boolean.FALSE);
        LambdaUpdateWrapper<Dictionary> updateWrapper = Wrappers.lambdaUpdate(entity)
                .set(Dictionary::getDeleted, 1);
        // 逻辑删除
        return dictionaryDao.update(updateWrapper);
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

        LambdaUpdateWrapper<Dictionary> updateWrapper = Wrappers.<Dictionary>lambdaUpdate()
                .eq(Dictionary::getDeleted, 0)
                .in(Dictionary::getId, ids)
                .set(Dictionary::getDeleted, 1);

        // 逻辑删除
        return dictionaryDao.update(updateWrapper);
    }
}
