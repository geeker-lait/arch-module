package org.arch.framework.automate.from.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.automate.from.entity.FromBytearray;
import org.arch.framework.automate.from.mapper.FromBytearrayMapper;
import org.arch.framework.crud.CrudDao;
import org.arch.framework.crud.CrudServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 表单数据(FromBytearray) 表数据库访问层
 *
 * @author lait
 * @date 2021-02-08 13:25:30
 * @since 1.0.0
 */
@Slf4j
@RequiredArgsConstructor
@Repository
public class FromBytearrayDao extends CrudServiceImpl<FromBytearrayMapper, FromBytearray> implements CrudDao<FromBytearray> {
    private final FromBytearrayMapper fromBytearrayMapper;
}
