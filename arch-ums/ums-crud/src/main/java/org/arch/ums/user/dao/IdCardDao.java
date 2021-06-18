package org.arch.ums.user.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.arch.ums.user.entity.IdCard;
import org.arch.ums.user.mapper.IdCardMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户身份证表(IdCard) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 23:06:37
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class IdCardDao extends CrudServiceImpl<IdCardMapper, IdCard> implements CrudDao<IdCard> {
    private final IdCardMapper idCardMapper;
}