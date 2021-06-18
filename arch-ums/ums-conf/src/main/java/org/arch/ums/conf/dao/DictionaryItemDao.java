package org.arch.ums.conf.dao;

import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.conf.entity.DictionaryItem;
import org.arch.ums.conf.mapper.DictionaryItemMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 数据字典明细(DictionaryItem) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:22:50
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class DictionaryItemDao extends CrudServiceImpl<DictionaryItemMapper, DictionaryItem> implements CrudDao<DictionaryItem> {
    private final DictionaryItemMapper dictionaryItemMapper;
}
