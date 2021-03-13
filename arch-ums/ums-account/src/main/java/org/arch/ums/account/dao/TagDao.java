package org.arch.ums.account.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.account.entity.Tag;
import org.arch.ums.account.mapper.TagMapper;
import org.springframework.stereotype.Repository;

/**
 * 账号-标签(Tag) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:30:13
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class TagDao extends CrudServiceImpl<TagMapper, Tag> implements CrudDao<Tag> {
    private final TagMapper tagMapper;
}