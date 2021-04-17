package org.arch.ums.conf.dao;

import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.conf.entity.Dictionary;
import org.arch.ums.conf.mapper.DictionaryMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.arch.framework.crud.CrudDao;
import org.springframework.stereotype.Repository;

/**
 * 数据字典(Dictionary) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-04-17 15:22:53
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class DictionaryDao extends CrudServiceImpl<DictionaryMapper, Dictionary> implements CrudDao<Dictionary> {
    private final DictionaryMapper dictionaryMapper;
}
